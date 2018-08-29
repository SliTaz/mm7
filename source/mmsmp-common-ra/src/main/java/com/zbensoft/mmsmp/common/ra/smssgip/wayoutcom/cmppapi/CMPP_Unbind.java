package com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.cmppapi;

import java.nio.ByteBuffer;

public class CMPP_Unbind extends CMPP_Command {
    public CMPP_Unbind(ByteBuffer buf) {
        this.buf = buf;
    }

    public CMPP_Unbind() {
        this.buf = ByteBuffer.allocate(12);
        this.setPacketLength(12);
        this.setCommandID(2);
    }

    public String toString() {
        return "CMPP_Unbind:Seq=" + this.getSeqID();
    }
}