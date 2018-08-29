package com.zbensoft.mmsmp.common.ra.smssgip.proxy.cmpp;

import com.zbensoft.mmsmp.common.ra.smssgip.proxy.comm.SMSException;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.comm.SMSLayer;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.util.Debug;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.cmppapi.CMPP_Command;

public class CMPPTransaction extends SMSLayer {
    private CMPP_Command receive;
    private int sequenceId;

    public CMPPTransaction(SMSLayer connection) {
        super(connection);
        this.sequenceId = super.id;
    }

    public synchronized void onReceive(Object msg) {
        this.receive = (CMPP_Command)msg;
        this.sequenceId = this.receive.getSeqID();
        if (CMPPConstant.debug) {
            Debug.dump(this.receive.toString());
        }

        this.notifyAll();
    }

    public void send(Object message) throws SMSException {
        CMPP_Command msg = (CMPP_Command)message;
        msg.setSeqID(this.sequenceId);
        super.parent.send(message);
        if (CMPPConstant.debug) {
            Debug.dump(msg.toString());
        }

    }

    public CMPP_Command getResponse() {
        return this.receive;
    }

    public synchronized void waitResponse() {
        if (this.receive == null) {
            try {
                this.wait((long)((CMPPConnection)super.parent).getTransactionTimeout());
            } catch (InterruptedException var2) {
                ;
            }
        }

    }
}

