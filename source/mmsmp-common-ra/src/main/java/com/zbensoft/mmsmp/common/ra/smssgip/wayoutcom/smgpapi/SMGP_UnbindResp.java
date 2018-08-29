package com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.smgpapi;

import java.nio.ByteBuffer;

public class SMGP_UnbindResp extends SMGP_Command {
    public SMGP_UnbindResp(ByteBuffer buf) {
        this.buf = buf;
    }

    public SMGP_UnbindResp(SMGP_Unbind unbind) {
        this.buf = ByteBuffer.allocate(12);
        this.setPacketLength(12);
        this.setCommandID(-2147483642);
        this.setSeqID(unbind.getSeqID());
    }

    public String toString() {
        return "SMGP_UnbindResp:Seq=" + this.getSeqID();
    }
}
