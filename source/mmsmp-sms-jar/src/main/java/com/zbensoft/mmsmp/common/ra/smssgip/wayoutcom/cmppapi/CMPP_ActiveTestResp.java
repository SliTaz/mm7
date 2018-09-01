package com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.cmppapi;

import java.nio.ByteBuffer;

public class CMPP_ActiveTestResp extends CMPP_Command {
    public CMPP_ActiveTestResp(ByteBuffer buf) {
        this.buf = buf;
    }

    public CMPP_ActiveTestResp(CMPP_ActiveTest test) {
        this.buf = ByteBuffer.allocate(12);
        this.setPacketLength(12);
        this.setCommandID(-2147483640);
        this.setSeqID(test.getSeqID());
    }

    public String toString() {
        return "CMPP_ActiveTestResp:Seq=" + this.getSeqID();
    }
}