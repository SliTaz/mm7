package com.zbensoft.mmsmp.sms.ra.thread;

import com.zbensoft.mmsmp.common.ra.common.message.AbstractMessage;
import com.zbensoft.mmsmp.common.ra.common.message.MO_SMMessage;
import com.zbensoft.mmsmp.sms.ra.log.SMS_LOG;
import com.zbensoft.mmsmp.sms.ra.utils.SmsMessageQuene;
import org.apache.log4j.Logger;
import org.apache.mina.core.session.IoSession;

public class ReceiveMessageThread implements Runnable {
    private static final Logger logger = Logger.getLogger(ReceiveMessageThread.class);
    public static IoSession session = null;

    public ReceiveMessageThread() {
    }

    public void run() {
        SMS_LOG.INFO("begin send momsg to corebiz.......");

        while(true) {
            AbstractMessage message = SmsMessageQuene.getInstance().takeMOMessage();
            if (message != null && session.isConnected()) {
                MO_SMMessage momsg = (MO_SMMessage)message;
                SMS_LOG.INFO("the sms momessage is ......." + momsg);
                session.write(momsg);
            }

            SMS_LOG.INFO("this time send to corebiz over.......");
        }
    }
}
