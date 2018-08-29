package com.zbensoft.mmsmp.common.ra.smssgip.proxy.sm7.sm7api;

import java.nio.ByteBuffer;

public class SM7_UnbindResp extends SM7_Command {
    public SM7_UnbindResp(ByteBuffer buf) {
        this.buf = buf;
    }

    public SM7_UnbindResp(SM7_Unbind unbind) {
        this.buf = ByteBuffer.allocate(16);
        this.setPacketLength(16);
        this.setCommandID(-2147483642);
        this.setSeqID(unbind.getSeqID());
    }
}
