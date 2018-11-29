package com.fun.netty;

import java.net.InetSocketAddress;
import java.util.Timer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import com.alibaba.fastjson.JSON;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author xulujun
 * @date 2018/06/14
 */
public class NettyRemotingServer {

    private static final Logger log = LoggerFactory.getLogger(NettyRemotingServer.class);

    private final ServerBootstrap serverBootstrap;
    private final EventLoopGroup eventLoopGroupWorker;
    private final EventLoopGroup eventLoopGroupBoss;
    private final NettyServerConfig nettyServerConfig;
    // 处理Callback应答器
    private final ExecutorService publicExecutor;
    private final ChannelEventListener channelEventListener;
    // 定时器
    private final Timer timer = new Timer("ServerHouseKeepingService", true);
    private DefaultEventExecutorGroup defaultEventExecutorGroup;

    //private RPCHook rpcHook;

    // 本地server绑定的端口
    private int port = 0;

    public NettyRemotingServer(final NettyServerConfig nettyServerConfig) {
        this(nettyServerConfig, null);
    }

    public NettyRemotingServer(final NettyServerConfig nettyServerConfig,
                               final ChannelEventListener channelEventListener) {
        //TODO lujun.xlj
        /*super(nettyServerConfig.getServerOnewaySemaphoreValue(), nettyServerConfig
            .getServerAsyncSemaphoreValue());*/
        this.serverBootstrap = new ServerBootstrap();
        this.nettyServerConfig = nettyServerConfig;
        this.channelEventListener = channelEventListener;

        int publicThreadNums = nettyServerConfig.getServerCallbackExecutorThreads();
        if (publicThreadNums <= 0) {
            publicThreadNums = 4;
        }

        this.publicExecutor = Executors.newFixedThreadPool(publicThreadNums, new ThreadFactory() {
            private AtomicInteger threadIndex = new AtomicInteger(0);

            public Thread newThread(Runnable r) {
                return new Thread(r, "NettyServerPublicExecutor_" + this.threadIndex.incrementAndGet());
            }
        });

        this.eventLoopGroupBoss = new NioEventLoopGroup(1, new ThreadFactory() {
            private AtomicInteger threadIndex = new AtomicInteger(0);

            public Thread newThread(Runnable r) {
                return new Thread(r,
                    String.format("NettyBossSelector_%d", this.threadIndex.incrementAndGet()));
            }
        });

        this.eventLoopGroupWorker =
            new NioEventLoopGroup(nettyServerConfig.getServerSelectorThreads(), new ThreadFactory() {
                private AtomicInteger threadIndex = new AtomicInteger(0);
                private int threadTotal = nettyServerConfig.getServerSelectorThreads();

                public Thread newThread(Runnable r) {
                    return new Thread(r, String.format("NettyServerSelector_%d_%d", threadTotal,
                        this.threadIndex.incrementAndGet()));
                }
            });
    }

    public void start() {
        this.defaultEventExecutorGroup = new DefaultEventExecutorGroup(//
            nettyServerConfig.getServerWorkerThreads(), //
            new ThreadFactory() {

                private AtomicInteger threadIndex = new AtomicInteger(0);

                public Thread newThread(Runnable r) {
                    return new Thread(r, "NettyServerWorkerThread_" + this.threadIndex.incrementAndGet());
                }
            });

        ServerBootstrap childHandler = //
            this.serverBootstrap.group(this.eventLoopGroupBoss, this.eventLoopGroupWorker)
                .channel(NioServerSocketChannel.class)
                //
                .option(ChannelOption.SO_BACKLOG, 1024)
                //
                .option(ChannelOption.SO_REUSEADDR, true)
                //
                .option(ChannelOption.SO_KEEPALIVE, false)
                //
                .childOption(ChannelOption.TCP_NODELAY, true)
                //
                .option(ChannelOption.SO_SNDBUF, nettyServerConfig.getServerSocketSndBufSize())
                //
                .option(ChannelOption.SO_RCVBUF, nettyServerConfig.getServerSocketRcvBufSize())
                //
                .localAddress(new InetSocketAddress(this.nettyServerConfig.getListenPort()))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(
                            //
                            defaultEventExecutorGroup, //
                            //new NettyEncoder(), //
                            //new NettyDecoder(), //
                            new NettyServerEncoder(),
                            new NettyServerDecoder(Integer.MAX_VALUE),
                            new IdleStateHandler(0, 0, nettyServerConfig
                                .getServerChannelMaxIdleTimeSeconds()),//
                            new NettyConnetManageHandler(), //
                            new NettyServerHandler());
                    }
                });

        if (nettyServerConfig.isServerPooledByteBufAllocatorEnable()) {
            // 这个选项有可能会占用大量堆外内存，暂时不使用。
            childHandler.childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
        }

        try {
            ChannelFuture sync = this.serverBootstrap.bind().sync();
            InetSocketAddress addr = (InetSocketAddress)sync.channel().localAddress();
            this.port = addr.getPort();
        } catch (InterruptedException e1) {
            throw new RuntimeException("this.serverBootstrap.bind().sync() InterruptedException", e1);
        }

        //TODO lujun.xlj
        /*if (this.channelEventListener != null) {
            this.nettyEventExecuter.start();
        }*/

        // 每隔1秒扫描下异步调用超时情况
        /*this.timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                try {
                    NettyRemotingServer.this.scanResponseTable();
                }
                catch (Exception e) {
                    log.error("scanResponseTable exception", e);
                }
            }
        }, 1000 * 3, 1000);*/
    }

    class NettyServerHandler extends SimpleChannelInboundHandler<RemotingCommand> {

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, RemotingCommand msg) throws Exception {
            processMessageReceived(ctx, msg);
        }
    }

    public void processMessageReceived(ChannelHandlerContext ctx, RemotingCommand msg) throws Exception {
        final RemotingCommand cmd = msg;
        if(cmd == null){
            return;
        }
        System.out.println("processMessageReceived==>" + JSON.toJSONString(cmd));
        cmd.setMessage("server receive[" + cmd + "]");
        ctx.channel().writeAndFlush(cmd).addListener((ChannelFutureListener)f -> {
            if (f.isSuccess()) {
                System.out.println("response ok");
                return;
            }
            System.out.println("response fail");
        });
        /*ctx.channel().writeAndFlush(ctx.alloc().directBuffer().writeBytes(">".getBytes())).addListener((ChannelFutureListener)f -> {
            if(f.isSuccess()){
                System.out.println("prompt out ok");
            }else{
                System.out.println("prompt out fail");
                if(f.cause() != null){
                    f.cause().printStackTrace();
                }
            }
        });*/

    }

    //TODO lujun.xlj handle event
    class NettyConnetManageHandler extends ChannelDuplexHandler {
        @Override
        public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
            final String remoteAddress = RemotingUtil.parseChannelRemoteAddr(ctx.channel());
            log.info("NETTY SERVER PIPELINE: channelRegistered {" + remoteAddress + "}");
            super.channelRegistered(ctx);
            ctx.channel().writeAndFlush(ctx.alloc().directBuffer().writeBytes(">".getBytes()));
        }

        @Override
        public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
            final String remoteAddress = RemotingUtil.parseChannelRemoteAddr(ctx.channel());
            log.info("NETTY SERVER PIPELINE: channelUnregistered, the channel[{" + remoteAddress + "}]");
            super.channelUnregistered(ctx);
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            final String remoteAddress = RemotingUtil.parseChannelRemoteAddr(ctx.channel());
            log.info("NETTY SERVER PIPELINE: channelActive, the channel[{" + remoteAddress + "}]");
            super.channelActive(ctx);

            /*if (NettyRemotingServer.this.channelEventListener != null) {
                NettyRemotingServer.this.putNettyEvent(new NettyEvent(NettyEventType.CONNECT, remoteAddress
                    .toString(), ctx.channel()));
            }*/
        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            final String remoteAddress = RemotingUtil.parseChannelRemoteAddr(ctx.channel());
            log.info("NETTY SERVER PIPELINE: channelInactive, the channel[{" + remoteAddress + "}]");
            super.channelInactive(ctx);

            /*if (NettyRemotingServer.this.channelEventListener != null) {
                NettyRemotingServer.this.putNettyEvent(new NettyEvent(NettyEventType.CLOSE, remoteAddress
                    .toString(), ctx.channel()));
            }*/
        }

        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
            if (evt instanceof IdleStateEvent) {
                IdleStateEvent evnet = (IdleStateEvent)evt;
                if (evnet.state().equals(IdleState.ALL_IDLE)) {
                    final String remoteAddress = RemotingUtil.parseChannelRemoteAddr(ctx.channel());
                    log.warn("NETTY SERVER PIPELINE: IDLE exception [{" + remoteAddress + "}]");
                    RemotingUtil.closeChannel(ctx.channel());
                    /*if (NettyRemotingServer.this.channelEventListener != null) {
                        NettyRemotingServer.this.putNettyEvent(new NettyEvent(NettyEventType.IDLE,
                            remoteAddress.toString(), ctx.channel()));
                    }*/
                }
            }

            ctx.fireUserEventTriggered(evt);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            final String remoteAddress = RemotingUtil.parseChannelRemoteAddr(ctx.channel());
            log.warn("NETTY SERVER PIPELINE: exceptionCaught {" + remoteAddress + "}");
            log.warn("NETTY SERVER PIPELINE: exceptionCaught exception.", cause);

            /*if (NettyRemotingServer.this.channelEventListener != null) {
                NettyRemotingServer.this.putNettyEvent(new NettyEvent(NettyEventType.EXCEPTION, remoteAddress
                    .toString(), ctx.channel()));
            }*/
            cause.printStackTrace();
            RemotingUtil.closeChannel(ctx.channel());
        }
    }

}
