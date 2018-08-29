package com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.smgpapi;

import java.nio.ByteBuffer;

public class SMGP_ActiveTest extends SMGP_Command {
    public SMGP_ActiveTest(ByteBuffer buf) {
        this.buf = buf;
    }

    public SMGP_ActiveTest() {
        this.buf = ByteBuffer.allocate(12);
        this.setPacketLength(12);
        this.setCommandID(4);
    }

    public String toString() {
        return "SMGP_ActiveTest:Seq=" + this.getSeqID() + "CommandID=" + this.getCommandID();
    }
}
