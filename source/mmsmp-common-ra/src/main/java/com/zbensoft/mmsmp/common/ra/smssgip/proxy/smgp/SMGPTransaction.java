package com.zbensoft.mmsmp.common.ra.smssgip.proxy.smgp;

import com.zbensoft.mmsmp.common.ra.smssgip.proxy.comm.SMSException;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.comm.SMSLayer;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.util.Debug;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.smgpapi.SMGP_Command;
import org.apache.log4j.Logger;

public class SMGPTransaction extends SMSLayer {
    private SMGP_Command receive;
    private int sequenceId;
    private Logger log = Logger.getLogger(SMGPTransaction.class);

    public SMGPTransaction(SMSLayer connection) {
        super(connection);
        this.sequenceId = super.id;
    }

    public synchronized void onReceive(Object msg) {
        this.log.info("收到消息 " + msg);
        this.receive = (SMGP_Command)msg;
        this.sequenceId = this.receive.getSeqID();
        if (SMGPConstant.debug) {
            this.log.info(Debug.dump(this.receive.getBuf().array()));
        }

        this.log.debug(this.receive.toString() + "\n");
        this.notifyAll();
    }

    public void send(Object message) throws SMSException {
        SMGP_Command msg = (SMGP_Command)message;
        msg.setSeqID(this.sequenceId);
        super.parent.send(message);
        this.log.info("发送消息 " + message);
        if (SMGPConstant.debug) {
            this.log.info(Debug.dump(msg.getBuf().array()));
        }

        this.log.debug(msg.toString() + "\n");
    }

    public SMGP_Command getResponse() {
        return this.receive;
    }

    public synchronized void waitResponse() {
        if (this.receive == null) {
            try {
                this.wait((long)((SMGPConnection)super.parent).getTransactionTimeout());
            } catch (InterruptedException var2) {
                ;
            }
        }

    }
}
