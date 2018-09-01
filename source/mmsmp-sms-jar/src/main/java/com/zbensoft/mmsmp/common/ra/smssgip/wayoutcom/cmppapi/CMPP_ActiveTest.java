package com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.cmppapi;

import java.nio.ByteBuffer;

public class CMPP_ActiveTest extends CMPP_Command {
    public CMPP_ActiveTest(ByteBuffer buf) {
        this.buf = buf;
    }

    public CMPP_ActiveTest() {
        this.buf = ByteBuffer.allocate(12);
        this.setPacketLength(12);
        this.setCommandID(8);
    }

    public String toString() {
        return "CMPP_ActiveTest:Seq=" + this.getSeqID();
    }
}