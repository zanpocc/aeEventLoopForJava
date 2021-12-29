package com.zanpo.it.event;

import com.zanpo.it.enums.EventType;

import java.io.IOException;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.AbstractSelectableChannel;

/**
 * 处理接收事件
 *
 * @author cg
 * @date 2021/12/29 13:42
 */
public class AcceptEventHandler extends AbsEventHandler {
    @Override
    public void process(AeEventLoop aeEventLoop, AbstractSelectableChannel s, Object clientData, int eventType) {
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) s;

        try {
            SocketChannel clientChannel = serverSocketChannel.accept();
            clientChannel.configureBlocking(false);
            System.out.println("接收到新连接:" + clientChannel.getRemoteAddress());
            aeEventLoop.aeCreateFileEvent(clientChannel, EventType.AE_READ, new SimpleReadEventHandler(), null);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
