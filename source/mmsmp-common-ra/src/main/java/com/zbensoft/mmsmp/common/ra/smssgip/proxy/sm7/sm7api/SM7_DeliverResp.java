package com.zbensoft.mmsmp.common.ra.smssgip.proxy.sm7.sm7api;

import java.nio.ByteBuffer;

public class SM7_DeliverResp extends SM7_Command {
    public SM7_DeliverResp() {
    }

    public SM7_DeliverResp(ByteBuffer buf) {
        this.buf = buf;
    }

    public SM7_DeliverResp(SM7_Deliver deliver, int congestionState) {
        byte[] pac = new byte[31];
        ByteBuffer buf = ByteBuffer.wrap(pac);
        this.buf = buf;
        this.setPacketLength(31);
        this.setCommandID(-2147483645);
        this.setStatus(deliver.getStatus());
        this.setSeqID(deliver.getSeqID());
        this.setMsgID(deliver.getSMGW());
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
}
