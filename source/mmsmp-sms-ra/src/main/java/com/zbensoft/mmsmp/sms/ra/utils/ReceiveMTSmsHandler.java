package com.zbensoft.mmsmp.sms.ra.utils;

import com.zbensoft.mmsmp.common.ra.common.message.MO_SMMessage;
import com.zbensoft.mmsmp.common.ra.common.message.MT_SMMessage;
import com.zbensoft.mmsmp.common.ra.common.queue.messagehandler.AbstractMessageHandler;
import com.zbensoft.mmsmp.common.ra.vas.commons.tcp.IServerHandler;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ReceiveMTSmsHandler implements IServerHandler {
    private static final Logger logger = Logger.getLogger(ReceiveMTSmsHandler.class);
    private SmsMessageQuene quence;
    private Map<Class<?>, AbstractMessageHandler> messageHandlerMap = new HashMap();

    public void registerMessageHandler(Class<?> clazz, AbstractMessageHandler obj) {
        this.messageHandlerMap.put(clazz, obj);
    }

    public ReceiveMTSmsHandler(SmsMessageQuene instance) {
        this.quence = instance;
    }

    public void onConnect(int arg0, String arg1, int arg2) {
    }

    public void onDisconnect(int arg0, Object arg1) {
    }

    public void onReceiveMsg(int arg0, Object arg1, byte[] arg2) {
    }

    public void onReceiveMsg(int arg0, Object arg1, Serializable arg2) {
        if (arg2 instanceof MT_SMMessage) {
            MT_SMMessage mtmsg = (MT_SMMessage)arg2;
            logger.info("receive mt sms message from corebiz success......");

            try {
                this.quence.getMtQuence().put(mtmsg);
                logger.info("put mt sms message to mtquence seccess......");
            } catch (InterruptedException var6) {
                logger.info("put mt sms message to mtquence failed......");
                var6.printStackTrace();
            }
        } else if (arg2 instanceof MO_SMMessage) {
            MO_SMMessage mtmsg = (MO_SMMessage)arg2;
            logger.info("==========" + mtmsg.getSendAddress() + "   " + mtmsg.getSmsText() + "    " + mtmsg.getVasId());
        }

    }

    public void onSendedMsg(int arg0, Object arg1, byte[] arg2) {
    }

    public void onSendedMsg(int arg0, Object arg1, Serializable arg2) {
    }

    public int slice(int arg0, byte[] arg1) {
        return 0;
    }
}
