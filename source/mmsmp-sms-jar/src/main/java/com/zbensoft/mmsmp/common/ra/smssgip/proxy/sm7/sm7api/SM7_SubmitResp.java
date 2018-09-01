package com.zbensoft.mmsmp.common.ra.smssgip.proxy.sm7.sm7api;

import java.nio.ByteBuffer;

public class SM7_SubmitResp extends SM7_Command {
    public SM7_SubmitResp() {
    }

    public SM7_SubmitResp(ByteBuffer buf) {
        this.buf = buf;
    }

    public SM7_SubmitResp(SM7_Submit submit, byte[] msgID, int congestionState) {
        byte[] pac = new byte[31];
        ByteBuffer buf = ByteBuffer.wrap(pac);
        this.buf = buf;
        this.setPacketLength(pac.length);
        this.setCommandID(-2147483646);
        this.setStatus(submit.getStatus());
        this.setSeqID(submit.getSeqID());
        this.setMsgID(msgID);
        this.buf.position(26);
        this.buf.putShort((short)1064);
        this.buf.position(28);
        this.buf.putShort((short)1);
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
        return "SM7_SubmitResp: result = " + this.getResult();
    }

    private int getResult() {
        this.buf.position(8);
        return this.buf.getInt();
    }
}
