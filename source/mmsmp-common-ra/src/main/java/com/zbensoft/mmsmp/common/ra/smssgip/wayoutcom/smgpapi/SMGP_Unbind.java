package com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.smgpapi;


import java.nio.ByteBuffer;

public class SMGP_Unbind extends SMGP_Command {
    public SMGP_Unbind(ByteBuffer buf) {
        this.buf = buf;
    }

    public SMGP_Unbind() {
        this.buf = ByteBuffer.allocate(12);
        this.setPacketLength(12);
        this.setCommandID(6);
    }

    public String toString() {
        return "SMGP_Unbind:Seq=" + this.getSeqID();
    }
}
