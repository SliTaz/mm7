package com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.cngpapi;

import java.nio.ByteBuffer;

public class CNGP_Unbind extends CNGP_Command {
    public CNGP_Unbind(ByteBuffer buf) {
        this.buf = buf;
    }

    public CNGP_Unbind() {
        this.buf = ByteBuffer.allocate(16);
        this.setPacketLength(16);
        this.setCommandID(6);
    }
}