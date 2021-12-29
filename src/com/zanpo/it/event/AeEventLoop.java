package com.zanpo.it.event;

import com.zanpo.it.enums.EventType;

import java.io.IOException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.spi.AbstractSelectableChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 事件循环
 *
 * @author cg
 * @date 2021/12/29 9:51
 */
public class AeEventLoop {

    private Selector selector;

    private volatile boolean flag = true;

    /**
     * 已注册事件
     */
    private final Map<AbstractSelectableChannel, AeFileEvent> events = new HashMap<>();

    /**
     * 已触发事件
     */
    private final List<AeFiredEvent> firedEvents = new ArrayList<>();


    public AeEventLoop() {
        try {
            selector = Selector.open();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 对指定的channel添加事件监听
     *
     * @param channel      channel
     * @param eventType    事件类型
     * @param eventHandler 事件处理
     * @param clientData   客户附加数据
     */
    public void aeCreateFileEvent(AbstractSelectableChannel channel, int eventType, AbsEventHandler eventHandler, Object clientData) {
        AeFileEvent aeFileEvent = new AeFileEvent();
        aeFileEvent.setEventType(eventType);
        if ((eventType & (EventType.AE_READ | EventType.AE_ACCEPT)) > 0) {
            aeFileEvent.setReadHandler(eventHandler);
        } else if ((eventType & EventType.AE_WRITE) > 0) {
            aeFileEvent.setWriteHandler(eventHandler);
        } else {
            throw new IllegalArgumentException("Failed to parse eventType");
        }
        aeFileEvent.setClientData(clientData);

        try {
            channel.register(selector, eventType);
        } catch (ClosedChannelException e) {
            e.printStackTrace();
        }

        events.put(channel, aeFileEvent);
    }


    public void aeProcessEvents() {
        while (flag) {
            // 触发消息
            try {
                System.out.println("等待事件触发");
                int select = selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                for (SelectionKey next : selectionKeys) {
                    AbstractSelectableChannel channel = (AbstractSelectableChannel) next.channel();
                    firedEvents.add(new AeFiredEvent(next.readyOps(), channel));

                    // 移除事件
                    selectionKeys.remove(next);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            // 处理消息
            Iterator<AeFiredEvent> iterator = firedEvents.iterator();
            while(iterator.hasNext()){
                AeFiredEvent firedEvent = iterator.next();
                int eventType = firedEvent.getEventType();
                AbstractSelectableChannel s = firedEvent.getS();

                AeFileEvent aeFileEvent = events.get(s);

                if ((EventType.AE_WRITE & eventType) > 0) { // 写事件
                    aeFileEvent.getWriteHandler().process(this, s, aeFileEvent.getClientData(), eventType);
                } else { // 读事件
                    aeFileEvent.getReadHandler().process(this, s, aeFileEvent.getClientData(), eventType);
                }

                // 移除事件
                iterator.remove();
            }

        }
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
