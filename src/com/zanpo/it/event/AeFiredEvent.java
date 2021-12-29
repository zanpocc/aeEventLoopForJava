package com.zanpo.it.event;

import java.nio.channels.spi.AbstractSelectableChannel;

/**
 * 触发事件
 *
 * @author cg
 * @date 2021/12/29 9:51
 */
public class AeFiredEvent {

    private int eventType;

    private AbstractSelectableChannel s;

    public AeFiredEvent(int eventType, AbstractSelectableChannel s) {
        this.eventType = eventType;
        this.s = s;
    }

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public AbstractSelectableChannel getS() {
        return s;
    }

    public void setS(AbstractSelectableChannel s) {
        this.s = s;
    }
}
