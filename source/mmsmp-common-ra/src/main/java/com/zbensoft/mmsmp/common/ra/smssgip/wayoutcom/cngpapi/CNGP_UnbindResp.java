package com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.cngpapi;

import java.nio.ByteBuffer;

public class CNGP_UnbindResp extends CNGP_Command {
    public CNGP_UnbindResp(ByteBuffer buf) {
        this.buf = buf;
    }

    public CNGP_UnbindResp(CNGP_Unbind unbind) {
        this.buf = ByteBuffer.allocate(16);
        this.setPacketLength(16);
        this.setCommandID(-2147483642);
        this.setSeqID(unbind.getSeqID());
    }
}
