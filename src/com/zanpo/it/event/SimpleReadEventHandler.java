package com.zanpo.it.event;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.AbstractSelectableChannel;

/**
 * 简单的Read事件Handler
 *
 * @author cg
 * @date 2021/12/29 13:43
 */
public class SimpleReadEventHandler extends AbsEventHandler {
    @Override
    public void process(AeEventLoop aeEventLoop, AbstractSelectableChannel s, Object clientData, int eventType) {
        SocketChannel socketChannel = (SocketChannel) s;
        ByteBuffer byteBuffer = ByteBuffer.allocate(255);
        try {
            int length = socketChannel.read(byteBuffer);
            if(length > 0){
                byte[] data = new byte[length];
                byteBuffer.flip();
                byteBuffer.get(data, 0, data.length);
                System.out.print(new String(data));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
