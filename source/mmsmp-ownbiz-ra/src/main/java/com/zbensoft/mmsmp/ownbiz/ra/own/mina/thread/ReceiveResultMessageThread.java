package com.zbensoft.mmsmp.ownbiz.ra.own.mina.thread;

import com.zbensoft.mmsmp.common.ra.common.message.ProxyPayMessage;
import org.apache.log4j.Logger;

import com.zbensoft.mmsmp.common.ra.common.message.AbstractMessage;
import com.zbensoft.mmsmp.common.ra.common.message.MO_SMMessage;
import com.zbensoft.mmsmp.common.ra.common.message.OrderRelationUpdateNotifyRequest;
import com.zbensoft.mmsmp.ownbiz.ra.own.mina.server.handler.impl.MoSmMessageHandlerImpl;
import com.zbensoft.mmsmp.ownbiz.ra.own.mina.server.handler.impl.OrderRelationUpdateNotifyRequestHandlerImpl;
import com.zbensoft.mmsmp.ownbiz.ra.own.mina.server.handler.impl.ProxyPayMessageHandlerImpl;
import com.zbensoft.mmsmp.ownbiz.ra.own.queue.MessageQuene;

public class ReceiveResultMessageThread implements Runnable {
    private static final Logger _log = Logger.getLogger(ReceiveResultMessageThread.class);
    private ProxyPayMessageHandlerImpl proxyPayMessagehandler;
    private MoSmMessageHandlerImpl moSmMessageHandler;
    private OrderRelationUpdateNotifyRequestHandlerImpl orunRequestHandler;
    private MessageQuene messageQuene;

    public ReceiveResultMessageThread(ProxyPayMessageHandlerImpl proxyPayMessagehandler, MoSmMessageHandlerImpl moSmMessageHandler, OrderRelationUpdateNotifyRequestHandlerImpl orunRequestHandler, MessageQuene messageQuene) {
        this.proxyPayMessagehandler = proxyPayMessagehandler;
        this.moSmMessageHandler = moSmMessageHandler;
        this.orunRequestHandler = orunRequestHandler;
        this.messageQuene = messageQuene;
        this.proxyPayMessagehandler.setMessageQuene(messageQuene);
    }

    public void run() {
        while(true) {
            try {
                if (this.messageQuene == null) {
                    this.messageQuene = MessageQuene.getInstance();
                }

                AbstractMessage msg = this.messageQuene.takeResultMessage();
                if (msg != null) {
                    _log.debug(String.format("ReceiveMessageThread线程收到AbstractMessage消息，消息类型：%s.", msg.getClass().getName()));
                    if (msg instanceof ProxyPayMessage) {
                        this.proxyPayMessagehandler.doHandler((ProxyPayMessage)msg);
                    } else if (msg instanceof MO_SMMessage) {
                        this.moSmMessageHandler.doHandler((MO_SMMessage)msg);
                    } else if (msg instanceof OrderRelationUpdateNotifyRequest) {
                        this.orunRequestHandler.doHandler((OrderRelationUpdateNotifyRequest)msg);
                    } else {
                        _log.info("消息类型错误！");
                    }
                }
            } catch (RuntimeException var2) {
                var2.printStackTrace();
            }
        }
    }
}
