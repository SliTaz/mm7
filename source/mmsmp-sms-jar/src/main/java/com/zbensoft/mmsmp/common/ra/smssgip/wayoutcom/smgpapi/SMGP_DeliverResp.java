package com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.smgpapi;

import java.nio.ByteBuffer;

public class SMGP_DeliverResp extends SMGP_Command {
    public SMGP_DeliverResp() {
    }

    public SMGP_DeliverResp(ByteBuffer buf) {
        this.buf = buf;
    }

    public SMGP_DeliverResp(SMGP_Deliver deliver) {
        byte[] pac = new byte[26];
        ByteBuffer buf = ByteBuffer.wrap(pac);
        this.buf = buf;
        this.setPacketLength(26);
        this.setCommandID(-2147483645);
        this.setSeqID(deliver.getSeqID());
        this.setMsgID(deliver.getMsgID());
        this.setStatus(0);
    }

    public SMGP_DeliverResp(SMGP_Deliver msg, int i) {
        byte[] pac = new byte[26];
        ByteBuffer buf = ByteBuffer.wrap(pac);
        this.buf = buf;
        this.setPacketLength(26);
        this.setCommandID(-2147483645);
        this.setSeqID(msg.getSeqID());
        this.setMsgID(msg.getMsgID());
        this.setStatus(i);
    }

    public byte[] getMsgID() {
        byte[] msgID = new byte[10];
        this.buf.position(12);
        this.buf.get(msgID, 0, 10);
        return msgID;
    }

    public void setMsgID(byte[] msgID) {
        this.buf.position(12);
        this.buf.put(msgID, 0, 10);
    }

    public int getStatus() {
        return this.buf.getInt(22);
    }

    public void setStatus(int status) {
        this.buf.putInt(22, status);
    }

    public String toString() {
        return "SMGP_DeliverResp:Seq=" + this.getSeqID() + " Status=" + this.getStatus();
    }
}
