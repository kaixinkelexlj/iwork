package com.fun.netty;

import java.net.SocketAddress;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.alibaba.fastjson.JSON;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPromise;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author xulujun
 * @date 2018/06/22
 */
public class NettyRemotingClient {

    private static final Logger log = LoggerFactory.getLogger(NettyRemotingClient.class);

    private static final long LockTimeoutMillis = 3000;

    // 缓存所有对外请求
    protected final ConcurrentHashMap<Integer /* opaque */, ResponseFuture> responseTable =
        new ConcurrentHashMap<Integer, ResponseFuture>(256);
    private final NettyClientConfig nettyClientConfig;
    private final Bootstrap bootstrap = new Bootstrap();
    private final EventLoopGroup eventLoopGroupWorker;
    private DefaultEventExecutorGroup defaultEventExecutorGroup;

    private final Lock lockChannelTables = new ReentrantLock();
    private final ConcurrentHashMap<String /* addr */, ChannelWrapper> channelTables =
        new ConcurrentHashMap<String, ChannelWrapper>();

    // 定时器
    private final Timer timer = new Timer("ClientHouseKeepingService", true);

    // Name server相关
    private final AtomicReference<List<String>> namesrvAddrList = new AtomicReference<List<String>>();
    private final AtomicReference<String> namesrvAddrChoosed = new AtomicReference<String>();
    private final AtomicInteger namesrvIndex = new AtomicInteger(initValueIndex());
    private final Lock lockNamesrvChannel = new ReentrantLock();

    // 处理Callback应答器
    private final ExecutorService publicExecutor;

    private final ChannelEventListener channelEventListener;

    public NettyRemotingClient(final NettyClientConfig nettyClientConfig) {
        this(nettyClientConfig, null);
    }

    public NettyRemotingClient(final NettyClientConfig nettyClientConfig,//
                               final ChannelEventListener channelEventListener) {
        /*super(nettyClientConfig.getClientOnewaySemaphoreValue(), nettyClientConfig
            .getClientAsyncSemaphoreValue());*/
        this.nettyClientConfig = nettyClientConfig;
        this.channelEventListener = channelEventListener;

        int publicThreadNums = nettyClientConfig.getClientCallbackExecutorThreads();
        if (publicThreadNums <= 0) {
            publicThreadNums = 4;
        }

        this.publicExecutor = Executors.newFixedThreadPool(publicThreadNums, new ThreadFactory() {
            private AtomicInteger threadIndex = new AtomicInteger(0);

            public Thread newThread(Runnable r) {
                return new Thread(r, "NettyClientPublicExecutor_" + this.threadIndex.incrementAndGet());
            }
        });

        this.eventLoopGroupWorker = new NioEventLoopGroup(1, new ThreadFactory() {
            private AtomicInteger threadIndex = new AtomicInteger(0);

            public Thread newThread(Runnable r) {
                return new Thread(r, String.format("NettyClientSelector_%d",
                    this.threadIndex.incrementAndGet()));
            }
        });
    }

    //private RPCHook rpcHook;

    private void processMessageReceived(ChannelHandlerContext ctx, RemotingCommand cmd) {
        if (cmd == null) {
            return;
        }
        System.out.println("client receive response==>" + JSON.toJSONString(cmd));
        ResponseFuture rep = this.responseTable.get(cmd.getOpaque());
        if (rep != null) {
            rep.setResponseCommand(cmd);
        }
    }

    public void start() {
        this.defaultEventExecutorGroup = new DefaultEventExecutorGroup(//
            nettyClientConfig.getClientWorkerThreads(), //
            new ThreadFactory() {

                private AtomicInteger threadIndex = new AtomicInteger(0);

                public Thread newThread(Runnable r) {
                    return new Thread(r, "NettyClientWorkerThread_" + this.threadIndex.incrementAndGet());
                }
            });

        Bootstrap handler = this.bootstrap.group(this.eventLoopGroupWorker).channel(NioSocketChannel.class)//
            //
            .option(ChannelOption.TCP_NODELAY, true)
            //
            .option(ChannelOption.SO_KEEPALIVE, false)
            //
            .option(ChannelOption.SO_SNDBUF, nettyClientConfig.getClientSocketSndBufSize())
            //
            .option(ChannelOption.SO_RCVBUF, nettyClientConfig.getClientSocketRcvBufSize())
            //
            .handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(//
                        defaultEventExecutorGroup, //
                        //new NettyEncoder(), //
                        //new NettyDecoder(), //
                        new NettyClientEncoder(),
                        new NettyClientDecoder(Integer.MAX_VALUE),
                        new IdleStateHandler(0, 0, nettyClientConfig.getClientChannelMaxIdleTimeSeconds()),//
                        new NettyConnetManageHandler(), //
                        new NettyClientHandler());
                }
            });

        // 每隔1秒扫描下异步调用超时情况
        this.timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                try {
                    NettyRemotingClient.this.scanResponseTable();
                } catch (Exception e) {
                    log.error("scanResponseTable exception", e);
                }
            }
        }, 1000 * 3, 1000);

        /*if (this.channelEventListener != null) {
            this.nettyEventExecuter.start();
        }*/
    }

    public void shutdown() {
        try {
            this.timer.cancel();

            for (ChannelWrapper cw : this.channelTables.values()) {
                this.closeChannel(null, cw.getChannel());
            }

            this.channelTables.clear();

            this.eventLoopGroupWorker.shutdownGracefully();

            /*if (this.nettyEventExecuter != null) {
                this.nettyEventExecuter.shutdown();
            }*/

            if (this.defaultEventExecutorGroup != null) {
                this.defaultEventExecutorGroup.shutdownGracefully();
            }
        } catch (Exception e) {
            log.error("NettyRemotingClient shutdown exception, ", e);
        }

        if (this.publicExecutor != null) {
            try {
                this.publicExecutor.shutdown();
            } catch (Exception e) {
                log.error("NettyRemotingServer shutdown exception, ", e);
            }
        }
    }

    public Channel getAndCreateChannel(final String addr) throws InterruptedException {
        if (null == addr) { return getAndCreateNameserverChannel(); }

        ChannelWrapper cw = this.channelTables.get(addr);
        if (cw != null && cw.isOK()) {
            return cw.getChannel();
        }

        return this.createChannel(addr);
    }

    public RemotingCommand invokeSync(String addr, final RemotingCommand request, long timeoutMillis)
        throws InterruptedException, RemotingConnectException, RemotingSendRequestException,
        RemotingTimeoutException {
        final Channel channel = this.getAndCreateChannel(addr);
        if (channel != null && channel.isActive()) {
            try {
                /*if (this.rpcHook != null) {
                    this.rpcHook.doBeforeRequest(addr, request);
                }*/
                RemotingCommand response = this.invokeSyncImpl(channel, request, timeoutMillis);
                /*if (this.rpcHook != null) {
                    this.rpcHook.doAfterResponse(request, response);
                }*/
                return response;
            } catch (RemotingSendRequestException e) {
                log.warn("invokeSync: send request exception, so close the channel[{" + addr + "}]");
                this.closeChannel(addr, channel);
                throw e;
            } catch (RemotingTimeoutException e) {
                log.warn("invokeSync: wait response timeout exception, the channel[{" + addr + "}]");
                // 超时异常如果关闭连接可能会产生连锁反应
                // this.closeChannel(addr, channel);
                throw e;
            }
        } else {
            this.closeChannel(addr, channel);
            throw new RemotingConnectException(addr);
        }
    }

    private RemotingCommand invokeSyncImpl(final Channel channel, final RemotingCommand request,
                                           final long timeoutMillis) throws InterruptedException, RemotingSendRequestException,
        RemotingTimeoutException {
        try {
            final ResponseFuture responseFuture =
                new ResponseFuture(request.getOpaque(), timeoutMillis);
            this.responseTable.put(request.getOpaque(), responseFuture);
            channel.writeAndFlush(request).addListener(new ChannelFutureListener() {

                public void operationComplete(ChannelFuture f) throws Exception {
                    if (f.isSuccess()) {
                        responseFuture.setSendRequestOK(true);
                        return;
                    } else {
                        responseFuture.setSendRequestOK(false);
                    }

                    responseTable.remove(request.getOpaque());
                    responseFuture.setCause(f.cause());
                    responseFuture.putResponse(null);
                    log.warn("send a request command to channel <" + channel.remoteAddress() + "> failed.");
                    log.warn(request.toString());
                }
            });

            RemotingCommand responseCommand = responseFuture.waitResponse(timeoutMillis);
            if (null == responseCommand) {
                // 发送请求成功，读取应答超时
                if (responseFuture.isSendRequestOK()) {
                    throw new RemotingTimeoutException(RemotingUtil.parseChannelRemoteAddr(channel),
                        timeoutMillis, responseFuture.getCause());
                }
                // 发送请求失败
                else {
                    throw new RemotingSendRequestException(RemotingUtil.parseChannelRemoteAddr(channel),
                        responseFuture.getCause());
                }
            }

            return responseCommand;
        } finally {
            this.responseTable.remove(request.getOpaque());
        }
    }

    public void closeChannel(final Channel channel) {
        if (null == channel) { return; }

        try {
            if (this.lockChannelTables.tryLock(LockTimeoutMillis, TimeUnit.MILLISECONDS)) {
                try {
                    boolean removeItemFromTable = true;
                    ChannelWrapper prevCW = null;
                    String addrRemote = null;
                    for (String key : channelTables.keySet()) {
                        ChannelWrapper prev = this.channelTables.get(key);
                        if (prev.getChannel() != null) {
                            if (prev.getChannel() == channel) {
                                prevCW = prev;
                                addrRemote = key;
                                break;
                            }
                        }
                    }

                    if (null == prevCW) {
                        log.info(
                            "eventCloseChannel: the channel[{" + addrRemote + "}] has been removed from the channel table before");
                        removeItemFromTable = false;
                    }

                    if (removeItemFromTable) {
                        this.channelTables.remove(addrRemote);
                        log.info("closeChannel: the channel[{" + addrRemote + "}] was removed from channel table");
                        RemotingUtil.closeChannel(channel);
                    }
                } catch (Exception e) {
                    log.error("closeChannel: close the channel exception", e);
                } finally {
                    this.lockChannelTables.unlock();
                }
            } else {
                log.warn("closeChannel: try to lock channel table, but timeout, {" + LockTimeoutMillis + "}ms");
            }
        } catch (InterruptedException e) {
            log.error("closeChannel exception", e);
        }
    }

    public void closeChannel(final String addr, final Channel channel) {
        if (null == channel) { return; }

        final String addrRemote = null == addr ? RemotingUtil.parseChannelRemoteAddr(channel) : addr;

        try {
            if (this.lockChannelTables.tryLock(LockTimeoutMillis, TimeUnit.MILLISECONDS)) {
                try {
                    boolean removeItemFromTable = true;
                    final ChannelWrapper prevCW = this.channelTables.get(addrRemote);

                    log.info("closeChannel: begin close the channel[{" + addrRemote + "}] Found: {" + (prevCW != null) + "}");

                    if (null == prevCW) {
                        log.info(
                            "closeChannel: the channel[{" + addrRemote + "}] has been removed from the channel table before");
                        removeItemFromTable = false;
                    } else if (prevCW.getChannel() != channel) {
                        log.info(
                            "closeChannel: the channel[{" + addrRemote + "}] has been closed before, and has been created again, nothing to do.");
                        removeItemFromTable = false;
                    }

                    if (removeItemFromTable) {
                        this.channelTables.remove(addrRemote);
                        log.info("closeChannel: the channel[{" + addrRemote + "}] was removed from channel table");
                    }

                    RemotingUtil.closeChannel(channel);
                } catch (Exception e) {
                    log.error("closeChannel: close the channel exception", e);
                } finally {
                    this.lockChannelTables.unlock();
                }
            } else {
                log.warn("closeChannel: try to lock channel table, but timeout, {" + LockTimeoutMillis + "}ms");
            }
        } catch (InterruptedException e) {
            log.error("closeChannel exception", e);
        }
    }

    private static int initValueIndex() {
        Random r = new Random();

        return Math.abs(r.nextInt() % 999) % 999;
    }

    private Channel getAndCreateNameserverChannel() throws InterruptedException {
        String addr = this.namesrvAddrChoosed.get();
        if (addr != null) {
            ChannelWrapper cw = this.channelTables.get(addr);
            if (cw != null && cw.isOK()) {
                return cw.getChannel();
            }
        }

        final List<String> addrList = this.namesrvAddrList.get();
        // 加锁，尝试创建连接
        if (this.lockNamesrvChannel.tryLock(LockTimeoutMillis, TimeUnit.MILLISECONDS)) {
            try {
                addr = this.namesrvAddrChoosed.get();
                if (addr != null) {
                    ChannelWrapper cw = this.channelTables.get(addr);
                    if (cw != null && cw.isOK()) {
                        return cw.getChannel();
                    }
                }

                if (addrList != null && !addrList.isEmpty()) {
                    for (int i = 0; i < addrList.size(); i++) {
                        int index = this.namesrvIndex.incrementAndGet();
                        index = Math.abs(index);
                        index = index % addrList.size();
                        String newAddr = addrList.get(index);

                        this.namesrvAddrChoosed.set(newAddr);
                        Channel channelNew = this.createChannel(newAddr);
                        if (channelNew != null) { return channelNew; }
                    }
                }
            } catch (Exception e) {
                log.error("getAndCreateNameserverChannel: create name server channel exception", e);
            } finally {
                this.lockNamesrvChannel.unlock();
            }
        } else {
            log.warn("getAndCreateNameserverChannel: try to lock name server, but timeout, {" + LockTimeoutMillis + "}ms");
        }

        return null;
    }

    private Channel createChannel(final String addr) throws InterruptedException {
        ChannelWrapper cw = this.channelTables.get(addr);
        if (cw != null && cw.isOK()) {
            return cw.getChannel();
        }

        // 进入临界区后，不能有阻塞操作，网络连接采用异步方式
        if (this.lockChannelTables.tryLock(LockTimeoutMillis, TimeUnit.MILLISECONDS)) {
            try {
                boolean createNewConnection = false;
                cw = this.channelTables.get(addr);
                if (cw != null) {
                    // channel正常
                    if (cw.isOK()) {
                        return cw.getChannel();
                    }
                    // 正在连接，退出锁等待
                    else if (!cw.getChannelFuture().isDone()) {
                        createNewConnection = false;
                    }
                    // 说明连接不成功
                    else {
                        this.channelTables.remove(addr);
                        createNewConnection = true;
                    }
                }
                // ChannelWrapper不存在
                else {
                    createNewConnection = true;
                }

                if (createNewConnection) {
                    ChannelFuture channelFuture =
                        this.bootstrap.connect(RemotingUtil.string2SocketAddress(addr));
                    log.info("createChannel: begin to connect remote host[{" + addr + "}] asynchronously");
                    cw = new ChannelWrapper(channelFuture);
                    this.channelTables.put(addr, cw);
                }
            } catch (Exception e) {
                log.error("createChannel: create channel exception", e);
            } finally {
                this.lockChannelTables.unlock();
            }
        } else {
            log.warn("createChannel: try to lock channel table, but timeout, {" + LockTimeoutMillis + "}ms");
        }

        if (cw != null) {
            ChannelFuture channelFuture = cw.getChannelFuture();
            if (channelFuture.awaitUninterruptibly(this.nettyClientConfig.getConnectTimeoutMillis())) {
                if (cw.isOK()) {
                    log.info("createChannel: connect remote host[{" + addr + "}] success, {" + channelFuture.toString() + "}");
                    return cw.getChannel();
                } else {
                    log.warn(
                        "createChannel: connect remote host[" + addr + "] failed, "
                            + channelFuture.toString(), channelFuture.cause());
                }
            } else {
                log.warn("createChannel: connect remote host[{" + addr + "}] timeout {" + this.nettyClientConfig.getConnectTimeoutMillis() + "}ms, {" + channelFuture.toString() + "}");
            }
        }

        return null;
    }

    private void scanResponseTable() {
        if (this.responseTable.isEmpty()) {
            return;
        }
        Iterator<Integer> itr = this.responseTable.keySet().iterator();
        for (; itr.hasNext(); ) {
            ResponseFuture rep = this.responseTable.get(itr.next());
            if ((rep.getBeginTimestamp() + rep.getTimeoutMillis() + 1000) <= System.currentTimeMillis()) {
                itr.remove();
            }
        }
    }

    class ChannelWrapper {
        private final ChannelFuture channelFuture;

        public ChannelWrapper(ChannelFuture channelFuture) {
            this.channelFuture = channelFuture;
        }

        public boolean isOK() {
            return (this.channelFuture.channel() != null && this.channelFuture.channel().isActive());
        }

        private Channel getChannel() {
            return this.channelFuture.channel();
        }

        public ChannelFuture getChannelFuture() {
            return channelFuture;
        }
    }

    class NettyClientHandler extends SimpleChannelInboundHandler<RemotingCommand> {

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, RemotingCommand msg) throws Exception {
            processMessageReceived(ctx, msg);

        }
    }

    class NettyConnetManageHandler extends ChannelDuplexHandler {
        @Override
        public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress,
                            SocketAddress localAddress, ChannelPromise promise) throws Exception {
            final String local = localAddress == null ? "UNKNOW" : localAddress.toString();
            final String remote = remoteAddress == null ? "UNKNOW" : remoteAddress.toString();
            log.info("NETTY CLIENT PIPELINE: CONNECT  {" + local + "} => {" + remote + "}");
            super.connect(ctx, remoteAddress, localAddress, promise);

            if (NettyRemotingClient.this.channelEventListener != null) {
                /*NettyRemotingClient.this.putNettyEvent(new NettyEvent(NettyEventType.CONNECT, remoteAddress
                    .toString(), ctx.channel()));*/
            }
        }

        @Override
        public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
            final String remoteAddress = RemotingUtil.parseChannelRemoteAddr(ctx.channel());
            log.info("NETTY CLIENT PIPELINE: DISCONNECT {" + remoteAddress + "}");
            closeChannel(ctx.channel());
            super.disconnect(ctx, promise);

            if (NettyRemotingClient.this.channelEventListener != null) {
                /*NettyRemotingClient.this.putNettyEvent(new NettyEvent(NettyEventType.CLOSE, remoteAddress
                    .toString(), ctx.channel()));*/
            }
        }

        @Override
        public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
            final String remoteAddress = RemotingUtil.parseChannelRemoteAddr(ctx.channel());
            log.info("NETTY CLIENT PIPELINE: CLOSE {" + remoteAddress + "}");
            closeChannel(ctx.channel());
            super.close(ctx, promise);

            if (NettyRemotingClient.this.channelEventListener != null) {
                /*NettyRemotingClient.this.putNettyEvent(new NettyEvent(NettyEventType.CLOSE, remoteAddress
                    .toString(), ctx.channel()));*/
            }
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            final String remoteAddress = RemotingUtil.parseChannelRemoteAddr(ctx.channel());
            log.warn("NETTY CLIENT PIPELINE: exceptionCaught {" + remoteAddress + "}");
            log.warn("NETTY CLIENT PIPELINE: exceptionCaught exception.", cause);
            closeChannel(ctx.channel());
            if (NettyRemotingClient.this.channelEventListener != null) {
                /*NettyRemotingClient.this.putNettyEvent(new NettyEvent(NettyEventType.EXCEPTION, remoteAddress
                    .toString(), ctx.channel()));*/
            }
        }

        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
            if (evt instanceof IdleStateEvent) {
                IdleStateEvent evnet = (IdleStateEvent)evt;
                if (evnet.state().equals(IdleState.ALL_IDLE)) {
                    final String remoteAddress = RemotingUtil.parseChannelRemoteAddr(ctx.channel());
                    log.warn("NETTY CLIENT PIPELINE: IDLE exception [{" + remoteAddress + "}]");
                    closeChannel(ctx.channel());
                    if (NettyRemotingClient.this.channelEventListener != null) {
                        /*NettyRemotingClient.this.putNettyEvent(new NettyEvent(NettyEventType.IDLE,
                            remoteAddress.toString(), ctx.channel()));*/
                    }
                }
            }

            ctx.fireUserEventTriggered(evt);
        }
    }

}
