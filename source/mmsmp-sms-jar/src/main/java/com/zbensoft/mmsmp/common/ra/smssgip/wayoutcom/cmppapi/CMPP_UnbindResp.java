package com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.cmppapi;

import java.nio.ByteBuffer;

public class CMPP_UnbindResp extends CMPP_Command {
    public CMPP_UnbindResp(ByteBuffer buf) {
        this.buf = buf;
    }

    public CMPP_UnbindResp(CMPP_Unbind unbind) {
        this.buf = ByteBuffer.allocate(12);
        this.setPacketLength(12);
        this.setCommandID(-2147483646);
        this.setSeqID(unbind.getSeqID());
    }
}