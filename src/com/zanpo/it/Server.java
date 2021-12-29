package com.zanpo.it;

import com.zanpo.it.enums.EventType;
import com.zanpo.it.event.AcceptEventHandler;
import com.zanpo.it.event.AeEventLoop;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;

/**
 * 添加类说明信息
 *
 * @author cg
 * @date 2021/12/29 10:28
 */
public class Server {


    public static void main(String[] args) throws IOException {

        ServerSocketChannel socketChannel = null;
        try {
            socketChannel = ServerSocketChannel.open();
            socketChannel.bind(new InetSocketAddress("172.16.2.38", 9000));
            socketChannel.configureBlocking(false);
        } catch (Exception e) {
            System.out.println("初始化错误");
            e.printStackTrace();
        }

        AeEventLoop aeEventLoop = new AeEventLoop();
        aeEventLoop.aeCreateFileEvent(socketChannel, EventType.AE_ACCEPT, new AcceptEventHandler(), null);

        // 开始处理
        aeEventLoop.aeProcessEvents();
    }
}
