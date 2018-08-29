package com.zbensoft.mmsmp.common.ra.smssgip.proxy.sm7.sm7api;


import java.nio.ByteBuffer;

public class SM7_ActiveTestResp extends SM7_Command {
    public SM7_ActiveTestResp(ByteBuffer buf) {
        this.buf = buf;
    }

    public SM7_ActiveTestResp(SM7_ActiveTest test) {
        this.buf = ByteBuffer.allocate(16);
        this.setPacketLength(16);
        this.setCommandID(-2147483644);
        this.setSeqID(test.getSeqID());
    }
}
