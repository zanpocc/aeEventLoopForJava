package com.zanpo.it.event;

/**
 * 注册事件
 *
 * @author cg
 * @date 2021/12/29 9:51
 */
public class AeFileEvent {
    private int eventType;

    private AbsEventHandler writeHandler;

    private AbsEventHandler readHandler;

    private Object clientData;

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public AbsEventHandler getWriteHandler() {
        return writeHandler;
    }

    public void setWriteHandler(AbsEventHandler writeHandler) {
        this.writeHandler = writeHandler;
    }

    public AbsEventHandler getReadHandler() {
        return readHandler;
    }

    public void setReadHandler(AbsEventHandler readHandler) {
        this.readHandler = readHandler;
    }

    public Object getClientData() {
        return clientData;
    }

    public void setClientData(Object clientData) {
        this.clientData = clientData;
    }
}
