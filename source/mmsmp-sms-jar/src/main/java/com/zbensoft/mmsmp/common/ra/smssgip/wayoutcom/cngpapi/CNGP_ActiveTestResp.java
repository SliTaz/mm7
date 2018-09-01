package com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.cngpapi;

import java.nio.ByteBuffer;

public class CNGP_ActiveTestResp extends CNGP_Command {
    public CNGP_ActiveTestResp(ByteBuffer buf) {
        this.buf = buf;
    }

    public CNGP_ActiveTestResp(CNGP_ActiveTest test) {
        this.buf = ByteBuffer.allocate(16);
        this.setPacketLength(16);
        this.setCommandID(-2147483644);
        this.setSeqID(test.getSeqID());
    }
}