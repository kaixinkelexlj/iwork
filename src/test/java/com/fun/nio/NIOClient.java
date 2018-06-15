package com.fun.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NIOClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(NIOClient.class);
    private Selector selector;

    public void initClient(String ip, int port) throws IOException {
        SocketChannel channel = SocketChannel.open();
        channel.configureBlocking(false);
        this.selector = Selector.open();
        channel.connect(new InetSocketAddress(ip, port));
        channel.register(selector, SelectionKey.OP_CONNECT);
    }

    public void listen() throws Exception {
        while (true) {
            selector.select();
            for (Iterator<SelectionKey> itr = this.selector.selectedKeys().iterator(); itr.hasNext(); ) {
                SelectionKey key = itr.next();
                itr.remove();
                if (key.isConnectable()) {
                    Thread.sleep(100);
                    SocketChannel socket = (SocketChannel)key.channel();
                    if (socket.isConnectionPending()) {
                        if (socket.isConnected()) {
                            socket.finishConnect();
                        } else {
                            //System.out.println("not connected!");
                            continue;
                        }
                    }
                    socket.configureBlocking(false);
                    socket.write(ByteBuffer.wrap("I love you,Server".getBytes()));
                    socket.register(this.selector, SelectionKey.OP_READ);
                } else if (key.isReadable()) {
                    Thread.sleep(100);
                    SocketChannel socket = (SocketChannel)key.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(4096);
                    socket.read(buffer);
                    if (buffer.array().length > 0) {
                        String msg = new String(buffer.array(), Charset.forName("utf-8"));
                        LOGGER.info("客户端收到:" + msg);
                        socket.write(ByteBuffer.wrap("[isReadable]I love you,Server".getBytes()));
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 20; i++) {
            NIOClient client = new NIOClient();
            client.initClient("localhost", 8800);
            client.listen();
        }
    }
}
