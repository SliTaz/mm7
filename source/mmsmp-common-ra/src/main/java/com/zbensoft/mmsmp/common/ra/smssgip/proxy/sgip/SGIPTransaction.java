package com.zbensoft.mmsmp.common.ra.smssgip.proxy.sgip;

import com.zbensoft.mmsmp.common.ra.smssgip.proxy.comm.SMSException;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.comm.SMSLayer;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.util.Debug;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.sgipapi.SGIP_Command;
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SGIPTransaction extends SMSLayer {
    private Object receive;
    private long src_nodeid;
    private int timestamp;
    private int sequenceId;
    private Logger log = Logger.getLogger(SGIPTransaction.class);

    public SGIPTransaction(SMSLayer connection) {
        super(connection);
        this.sequenceId = super.id;
    }

    public synchronized void onReceive(Object msg) {
        SGIP_Command cmd = (SGIP_Command)msg;
        this.sequenceId = cmd.getHead().GetSeq_3();
        if (SGIPConstant.debug) {
            Debug.dump(cmd.getBodyByte());
        }

        this.log.debug(msg);
        this.receive = cmd;
        this.notifyAll();
    }

    public void send(Object message) throws SMSException {
        SGIP_Command cmd = (SGIP_Command)message;
        if (cmd.getHead().GetSeq_2() == 0) {
            Date nowtime = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMddHHmmss");
            String tmpTime = dateFormat.format(nowtime);
            cmd.getHead().SetSeq_2(Integer.parseInt(tmpTime));
        }

        cmd.getHead().SetSeq_3(this.sequenceId);
        super.parent.send(message);
        if (SGIPConstant.debug) {
            Debug.dump(cmd.getBodyByte());
        }

        this.log.debug(cmd);
    }

    public Object getResponse() {
        return this.receive;
    }

    public boolean isChildOf(SMSLayer connection) {
        if (super.parent == null) {
            return false;
        } else {
            return connection == super.parent;
        }
    }

    public synchronized void waitResponse() {
        if (this.receive == null) {
            try {
                this.wait((long)((SGIPConnection)super.parent).getTransactionTimeout());
            } catch (InterruptedException var2) {
                ;
            }
        }

    }
}

