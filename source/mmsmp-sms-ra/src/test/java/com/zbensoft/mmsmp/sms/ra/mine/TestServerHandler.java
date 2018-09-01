package com.zbensoft.mmsmp.sms.ra.mine;

import com.zbensoft.mmsmp.common.ra.common.message.MO_SMMessage;
import com.zbensoft.mmsmp.sms.ra.utils.SmsMessageQuene;
import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

public class TestServerHandler extends IoHandlerAdapter {
    private static final Logger logger = Logger.getLogger(TestServerHandler.class);
    private SmsMessageQuene quence;
    private static final String SUCCESS_CODE = "1";
    private static final String FAILED_CODE = "2";

    public TestServerHandler() {
    }

    public void messageReceived(IoSession session, Object message) throws Exception {
        if (message instanceof MO_SMMessage) {
            MO_SMMessage momsg = (MO_SMMessage) message;
            logger.info("receive MT_SMMessage from  corebiz...." + momsg);
            session.write("1");
        } else {
            logger.info("the message is not MO_SMMessage..." + message);
        }

    }

    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        logger.error(" server exceptionCaught(IoSession, Throwable)", cause);
    }
}
