package com.zanpo.it.event;

import java.nio.channels.spi.AbstractSelectableChannel;

/**
 * 事件处理接口
 *
 * @author cg
 * @date 2021/12/29 10:22
 */
public abstract class AbsEventHandler {

    /**
     * 事件处理
     *
     * @param aeEventLoop 事件循环
     * @param s           客户端连接Socket
     * @param clientData  客户端数据
     * @param eventType   事件类型
     */
    public abstract void process(AeEventLoop aeEventLoop, AbstractSelectableChannel s, Object clientData, int eventType);

}
