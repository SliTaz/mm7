package com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.cmppapi;

import java.nio.ByteBuffer;

public class CMPP_DeliverResp extends CMPP_Command {
    public CMPP_DeliverResp() {
    }

    public CMPP_DeliverResp(ByteBuffer buf) {
        this.buf = buf;
    }

    public CMPP_DeliverResp(CMPP_Deliver deliver) {
        byte[] pac = new byte[24];
        ByteBuffer buf = ByteBuffer.wrap(pac);
        this.buf = buf;
        this.setPacketLength(pac.length);
        this.setCommandID(-2147483643);
        this.setSeqID(deliver.getSeqID());
        this.setMsgID(deliver.getMsgID());
        this.setStatus(0);
    }

    public long getMsgID() {
        long msgID = 0L;
        msgID = this.buf.getLong(12);
        return msgID;
    }

    public void setMsgID(long msgID) {
        this.buf.putLong(12, msgID);
    }

    public int getStatus() {
        return this.buf.getInt(20);
    }

    public void setStatus(int status) {
        this.buf.putInt(20, status);
    }

    public String toString() {
        return "CMPP_DeliverResp:Seq=" + this.getSeqID() + " status=" + this.getStatus();
    }
}

