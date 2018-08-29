package com.zbensoft.mmsmp.sms.ra.mina.utils;

import com.zbensoft.mmsmp.common.ra.common.config.util.ConfigUtil;
import com.zbensoft.mmsmp.common.ra.common.message.AbstractMessage;

import java.util.concurrent.LinkedBlockingQueue;

public class SmsMessageQuene {
    private LinkedBlockingQueue<AbstractMessage> mtQuence;
    private LinkedBlockingQueue<AbstractMessage> moQuence;
    private static int quencesize = ConfigUtil.getInstance().getAgentConfig().getSmsQuenceSize();
    private static SmsMessageQuene smsQuence = null;

    public SmsMessageQuene(int num) {
        this.mtQuence = new LinkedBlockingQueue(num);
        this.moQuence = new LinkedBlockingQueue(num);
    }

    public static SmsMessageQuene getInstance() {
        if (smsQuence == null) {
            smsQuence = new SmsMessageQuene(quencesize);
        }

        return smsQuence;
    }

    public AbstractMessage takeMOMessage() {
        AbstractMessage message = null;

        try {
            message = (AbstractMessage)this.moQuence.take();
        } catch (InterruptedException var3) {
            var3.printStackTrace();
        }

        return message;
    }

    public AbstractMessage takeMTMessage() {
        AbstractMessage message = null;

        try {
            message = (AbstractMessage)this.mtQuence.take();
        } catch (InterruptedException var3) {
            var3.printStackTrace();
        }

        return message;
    }

    public LinkedBlockingQueue<AbstractMessage> getMtQuence() {
        return this.mtQuence;
    }

    public void setMtQuence(LinkedBlockingQueue<AbstractMessage> mtQuence) {
        this.mtQuence = mtQuence;
    }

    public LinkedBlockingQueue<AbstractMessage> getMoQuence() {
        return this.moQuence;
    }

    public void setMoQuence(LinkedBlockingQueue<AbstractMessage> moQuence) {
        this.moQuence = moQuence;
    }
}

