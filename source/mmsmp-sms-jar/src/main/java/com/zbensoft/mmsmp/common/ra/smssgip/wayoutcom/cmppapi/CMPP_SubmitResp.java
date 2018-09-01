package com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.cmppapi;

import java.nio.ByteBuffer;

public class CMPP_SubmitResp extends CMPP_Command {
    public CMPP_SubmitResp() {
    }

    public CMPP_SubmitResp(ByteBuffer buf) {
        this.buf = buf;
    }

    public CMPP_SubmitResp(CMPP_Submit submit, long msgID) {
        byte[] pac = new byte[24];
        ByteBuffer buf = ByteBuffer.wrap(pac);
        this.buf = buf;
        this.setPacketLength(pac.length);
        this.setCommandID(-2147483644);
        this.setSeqID(submit.getSeqID());
        this.setMsgID(msgID);
        this.setStatus(0);
    }

    public long getMsgID() {
        return this.buf.getLong(12);
    }

    public int getStatus() {
        return this.buf.getInt(20);
    }

    public void setStatus(int status) {
        this.buf.putInt(20, status);
    }

    public void setMsgID(long msgID) {
        this.buf.putLong(12, msgID);
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("CMPP_SubmitResp:Seq=");
        buffer.append(this.getSeqID());
        buffer.append(" status=");
        buffer.append(this.getStatus());
        return buffer.toString();
    }
}

