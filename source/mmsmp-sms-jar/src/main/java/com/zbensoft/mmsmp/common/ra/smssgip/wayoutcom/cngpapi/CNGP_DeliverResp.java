package com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.cngpapi;

import java.nio.ByteBuffer;

public class CNGP_DeliverResp extends CNGP_Command {
    public CNGP_DeliverResp() {
    }

    public CNGP_DeliverResp(ByteBuffer buf) {
        this.buf = buf;
    }

    public CNGP_DeliverResp(CNGP_Deliver deliver, int congestionState) {
        byte[] pac = new byte[31];
        ByteBuffer buf = ByteBuffer.wrap(pac);
        this.buf = buf;
        this.setPacketLength(31);
        this.setCommandID(-2147483645);
        this.setStatus(deliver.getStatus());
        this.setSeqID(deliver.getSeqID());
        this.setMsgID(deliver.getMsgID());
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

