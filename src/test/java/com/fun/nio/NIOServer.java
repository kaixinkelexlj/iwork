package com.fun.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NIOServer {
    private static final Logger LOGGER = LoggerFactory.getLogger(NIOServer.class);
    private Selector selector;

    public void initServer(int port) throws IOException {
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.configureBlocking(false);
        //绑定端口
        serverChannel.socket().bind(new InetSocketAddress(port));
        this.selector = Selector.open();
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);
    }

    public void listen() throws Exception {
        LOGGER.info("服务器端程序启动");
        while (true) {
            selector.select();
            for (Iterator<SelectionKey> itr = this.selector.selectedKeys().iterator(); itr.hasNext(); ) {
                SelectionKey key = itr.next();
                itr.remove();
                if (key.isAcceptable()) {
                    Thread.sleep(100);
                    ServerSocketChannel chanel = (ServerSocketChannel)key.channel();
                    SocketChannel socket = chanel.accept();
                    socket.configureBlocking(false);
                    socket.write(ByteBuffer.wrap("Hello,客户端,消息收到啦!".getBytes()));
                    socket.register(this.selector, SelectionKey.OP_READ);
                } else if (key.isReadable()) {
                    Thread.sleep(100);
                    SocketChannel socket = (SocketChannel)key.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(4096);
                    socket.read(buffer);
                    if (buffer.array().length > 0) {
                        String msg = new String(buffer.array(), Charset.forName("utf-8"));
                        LOGGER.info("服务器收到:" + msg);
                        socket.write(ByteBuffer.wrap("[isReadable]Hello,客户端,又收到啦!".getBytes()));
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        NIOServer server = new NIOServer();
        server.initServer(8800);
        server.listen();
    }
}
