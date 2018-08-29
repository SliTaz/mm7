package com.zbensoft.mmsmp.common.ra.smssgip.proxy.cngp;

import com.zbensoft.mmsmp.common.ra.smssgip.proxy.comm.SMSException;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.comm.SMSLayer;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.util.Debug;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.cngpapi.CNGP_Command;
import org.apache.log4j.Logger;

public class CNGPTransaction extends SMSLayer {
    private CNGP_Command receive;
    private int sequenceId;
    private Logger log = Logger.getLogger(CNGPTransaction.class);

    public CNGPTransaction(SMSLayer connection) {
        super(connection);
        this.sequenceId = super.id;
    }

    public synchronized void onReceive(Object msg) {
        this.receive = (CNGP_Command)msg;
        this.sequenceId = this.receive.getSeqID();
        if (CNGPConstant.debug) {
            Debug.dump(this.receive.getBuf().array());
        }

        this.log.debug(this.receive.toString() + "\n");
        this.notifyAll();
    }

    public void send(Object message) throws SMSException {
        CNGP_Command msg = (CNGP_Command)message;
        msg.setSeqID(this.sequenceId);
        super.parent.send(message);
        if (CNGPConstant.debug) {
            Debug.dump(msg.getBuf().array());
        }

        this.log.debug(msg.toString() + "\n");
    }

    public CNGP_Command getResponse() {
        return this.receive;
    }

    public synchronized void waitResponse() {
        if (this.receive == null) {
            try {
                this.wait((long)((CNGPConnection)super.parent).getTransactionTimeout());
            } catch (InterruptedException var2) {
                ;
            }
        }

    }
}

