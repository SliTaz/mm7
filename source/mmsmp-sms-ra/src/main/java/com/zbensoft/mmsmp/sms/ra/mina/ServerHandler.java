package com.zbensoft.mmsmp.sms.ra.mina;

import com.zbensoft.mmsmp.common.ra.common.message.MT_SMMessage;
import com.zbensoft.mmsmp.sms.ra.utils.SmsMessageQuene;
import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

public class ServerHandler extends IoHandlerAdapter {
    private static final Logger logger = Logger.getLogger(ServerHandler.class);
    private SmsMessageQuene quence;
    private static final String SUCCESS_CODE = "1";
    private static final String FAILED_CODE = "2";

    public ServerHandler(SmsMessageQuene quence) {
        this.quence = quence;
    }

    public void messageReceived(IoSession session, Object message) throws Exception {
        if (message instanceof MT_SMMessage) {
            MT_SMMessage mtmsg = (MT_SMMessage)message;
            logger.info("receive MT_SMMessage from  corebiz...." + mtmsg);
            this.quence.getMtQuence().put(mtmsg);
            session.write("1");
            logger.info("put the MT_SMMessage to mtquence....");
        } else {
            logger.info("the message is not MT_SMMessage..." + message);
        }

    }

    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        logger.error(" server exceptionCaught(IoSession, Throwable)", cause);
    }

    public void sessionOpened(IoSession session) {
        session.getConfig().setIdleTime(IdleStatus.BOTH_IDLE, 30);
    }

    public void sessionIdle(IoSession session, IdleStatus status) {
        session.write("SmsAgent server writer idle, send handset.");
    }
}
