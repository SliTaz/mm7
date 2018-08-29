package com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.cngpapi;

import java.nio.ByteBuffer;

public class CNGP_SubmitResp extends CNGP_Command {
    public CNGP_SubmitResp() {
    }

    public CNGP_SubmitResp(ByteBuffer buf) {
        this.buf = buf;
    }

    public CNGP_SubmitResp(CNGP_Submit submit, byte[] msgID, int congestionState) {
        byte[] pac = new byte[31];
        ByteBuffer buf = ByteBuffer.wrap(pac);
        this.buf = buf;
        this.setPacketLength(pac.length);
        this.setCommandID(-2147483646);
        this.setStatus(submit.getStatus());
        this.setSeqID(submit.getSeqID());
        this.setMsgID(msgID);
        this.buf.position(30);
        this.buf.put((byte)congestionState);
    }

    public byte[] getMsgID() {
        byte[] msgID = new byte[10];
        this.buf.position(16);
        this.buf.get(msgID, 0, 10);
        return msgID;
    }

    public void setMsgID(byte[] msgID) {
        this.buf.position(16);
        this.buf.put(msgID, 0, 10);
    }

    public String toString() {
        return "CNGP_SubmitResp: result = " + this.getResult();
    }

    private int getResult() {
        this.buf.position(8);
        return this.buf.getInt();
    }
}
