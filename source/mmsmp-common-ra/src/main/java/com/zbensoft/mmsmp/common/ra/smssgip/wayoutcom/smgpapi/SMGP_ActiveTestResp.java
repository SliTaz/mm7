package com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.smgpapi;


import java.nio.ByteBuffer;

public class SMGP_ActiveTestResp extends SMGP_Command {
    public SMGP_ActiveTestResp(ByteBuffer buf) {
        this.buf = buf;
    }

    public SMGP_ActiveTestResp(SMGP_ActiveTest test) {
        this.buf = ByteBuffer.allocate(12);
        this.setPacketLength(12);
        this.setCommandID(-2147483644);
        this.setSeqID(test.getSeqID());
    }

    public String toString() {
        return "SMGP_ActiveTestResp:Seq=" + this.getSeqID();
    }
}