package com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.cngpapi;

import java.nio.ByteBuffer;

public class CNGP_ActiveTest extends CNGP_Command {
    public CNGP_ActiveTest(ByteBuffer buf) {
        this.buf = buf;
    }

    public CNGP_ActiveTest() {
        this.buf = ByteBuffer.allocate(16);
        this.setPacketLength(16);
        this.setCommandID(4);
    }
}
