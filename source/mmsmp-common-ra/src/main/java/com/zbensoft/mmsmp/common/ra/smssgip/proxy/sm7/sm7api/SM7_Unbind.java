package com.zbensoft.mmsmp.common.ra.smssgip.proxy.sm7.sm7api;


import java.nio.ByteBuffer;

public class SM7_Unbind extends SM7_Command {
    public SM7_Unbind(ByteBuffer buf) {
        this.buf = buf;
    }

    public SM7_Unbind() {
        this.buf = ByteBuffer.allocate(16);
        this.setPacketLength(16);
        this.setCommandID(6);
    }
}
