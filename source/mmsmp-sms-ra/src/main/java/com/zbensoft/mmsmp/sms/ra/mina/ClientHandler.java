package com.zbensoft.mmsmp.sms.ra.mina;

import com.zbensoft.mmsmp.sms.ra.listener.ConnectManager;
import com.zbensoft.mmsmp.sms.ra.log.SMS_LOG;
import com.zbensoft.mmsmp.sms.ra.thread.ReceiveMessageThread;
import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

public class ClientHandler extends IoHandlerAdapter {
    private static final Logger logger = Logger.getLogger(ClientHandler.class);

    public ClientHandler() {
    }

    public void messageReceived(IoSession session, Object message) throws Exception {
        SMS_LOG.INFO("server return code : " + message);
    }

    public void sessionOpened(IoSession session) {
        session.getConfig().setIdleTime(IdleStatus.BOTH_IDLE, 30);
        ReceiveMessageThread.session = session;
    }

    public void sessionClosed(IoSession session) {
        (new ConnectManager()).start();
        SMS_LOG.ERROR("Close connection, start ConnectManager.", (Throwable)null);
    }

    public void sessionIdle(IoSession session, IdleStatus status) {
        session.write("SmsAgent smsclient send headset");
        if (session.getIdleCount(IdleStatus.READER_IDLE) >= 29) {
            session.close(true);
        }

    }

    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        SMS_LOG.ERROR(" smsclient exceptionCaught(IoSession, Throwable)", cause);
        session.close();
    }
}