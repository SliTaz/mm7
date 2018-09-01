package com.zbensoft.mmsmp.common.ra.smssgip.proxy.sm7;

import com.zbensoft.mmsmp.common.ra.smssgip.proxy.comm.SMSException;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.comm.SMSLayer;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.sm7.sm7api.SM7_Command;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.util.Debug;
import org.apache.log4j.Logger;

public class SM7Transaction extends SMSLayer {
    private SM7_Command receive;
    private int sequenceId;
    private Logger log = Logger.getLogger(SM7Transaction.class);

    public SM7Transaction(SMSLayer connection) {
        super(connection);
        this.sequenceId = super.id;
    }

    public synchronized void onReceive(Object msg) {
        this.receive = (SM7_Command)msg;
        this.sequenceId = this.receive.getSeqID();
        if (Sm7Constant.debug) {
            Debug.dump(this.receive.getBuf().array());
        }

        this.log.debug(this.receive.toString() + "\n");
        this.notifyAll();
    }

    public void send(Object message) throws SMSException {
        SM7_Command msg = (SM7_Command)message;
        msg.setSeqID(this.sequenceId);
        super.parent.send(message);
        if (Sm7Constant.debug) {
            Debug.dump(msg.getBuf().array());
        }

        this.log.debug(msg.toString() + "\n");
    }

    public SM7_Command getResponse() {
        return this.receive;
    }

    public synchronized void waitResponse() {
        if (this.receive == null) {
            try {
                this.wait((long)((SM7Connection)super.parent).getTransactionTimeout());
            } catch (InterruptedException var2) {
                ;
            }
        }

    }
}
