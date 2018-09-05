

package com.zbensoft.mmsmp.ownbiz.ra.own.mina.thread;

import com.zbensoft.mmsmp.common.ra.common.message.AbstractMessage;
import com.zbensoft.mmsmp.corebiz.message.ProxyPayMessage;
import com.zbensoft.mmsmp.ownbiz.ra.own.queue.MessageQuene;
import org.apache.log4j.Logger;
import org.apache.mina.core.session.IoSession;

public class SendRequestMessageThread implements Runnable {
    private static final Logger _log = Logger.getLogger(SendRequestMessageThread.class);
    public static IoSession _session = null;
    public MessageQuene messageQuene = null;

    public SendRequestMessageThread(MessageQuene messageQuene) {
        this.messageQuene = messageQuene;
    }

    public void run() {
        if (this.messageQuene == null) {
            this.messageQuene = MessageQuene.getInstance();
        }

        while(true) {
            while(true) {
                try {
                    while(true) {
                        AbstractMessage msg;
                        do {
                            do {
                                do {
                                    msg = this.messageQuene.takeRequestMessage();
                                } while(msg == null);
                            } while(_session == null);

                            _log.debug(String.format("SendRequestMessageThread 线程收到AbstractMessage消息，消息类型：%s.", msg.getClass().getName()));
                        } while(!(msg instanceof ProxyPayMessage));

                        if (!_session.isConnected()) {
                            this.messageQuene.getRequestQuence().add(msg);
                            Thread.currentThread();
                            Thread.sleep(50L);
                        } else {
                            _log.debug("发送请ProxyPayMessage求消息（ProxyPayMessage）到corebiz 开始 .....");
                            ProxyPayMessage ppmsg = (ProxyPayMessage) msg;
                            _log.debug(String.format("本次发送请求消息（ProxyPayMessage）的消息体：\n %s.", ppmsg.toString()));
                            _session.write(ppmsg);
                            _log.info("发送请求消息（ProxyPayMessage）到 corebiz 结束.");
                        }
                    }
                } catch (Exception var3) {
                    _log.error(var3.getMessage(), var3);
                }
            }
        }
    }

    public static void setSmsSession(IoSession session) {
        _session = session;
    }
}
