package com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.smgpapi;

import java.nio.ByteBuffer;

public class SMGP_SubmitResp extends SMGP_Command {
    public SMGP_SubmitResp() {
    }

    public SMGP_SubmitResp(ByteBuffer buf) {
        this.buf = buf;
    }

    public SMGP_SubmitResp(SMGP_Submit submit, byte[] msgID) {
        byte[] pac = new byte[26];
        ByteBuffer buf = ByteBuffer.wrap(pac);
        this.buf = buf;
        this.setPacketLength(26);
        this.setCommandID(-2147483646);
        this.setSeqID(submit.getSeqID());
        this.setMsgID(msgID);
        this.setStatus(0);
    }

    public SMGP_SubmitResp(SMGP_Submit submit, byte[] msgId, int congestionState) {
        byte[] pac = new byte[31];
        ByteBuffer buf = ByteBuffer.wrap(pac);
        this.buf = buf;
        this.setPacketLength(pac.length);
        this.setCommandID(-2147483646);
        this.setSeqID(submit.getSeqID());
        this.setMsgID(msgId);
        this.buf.position(26);
        this.buf.putInt(congestionState);
    }

    public byte[] getMsgID() {
        byte[] msgID = new byte[10];
        this.buf.position(12);
        this.buf.get(msgID, 0, 10);
        return msgID;
    }

    public int getStatus() {
        return this.buf.getInt(22);
    }

    public void setStatus(int status) {
        this.buf.putInt(22, status);
    }

    public void setMsgID(byte[] msgID) {
        this.buf.position(12);
        this.buf.put(msgID, 0, 10);
    }

    public String toString() {
        return "SMGP_SubmitResp:Seq=" + this.getSeqID();
    }
}
