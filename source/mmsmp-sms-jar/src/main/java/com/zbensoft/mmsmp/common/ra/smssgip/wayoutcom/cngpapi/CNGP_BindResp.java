package com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.cngpapi;

import java.nio.ByteBuffer;

public class CNGP_BindResp extends CNGP_Command {
    public CNGP_BindResp(ByteBuffer buf) {
        this.buf = buf;
    }

    public byte[] getAuthenticatorServer() {
        byte[] tmpbuf = new byte[16];
        System.arraycopy(this.buf.array(), 16, tmpbuf, 0, 16);
        return tmpbuf;
    }

    public byte getVersion() {
        return this.buf.get(32);
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("CNGP_BindResp:AuthenticatorServer=");
        sb.append(this.getAuthenticatorServer());
        sb.append(";Version=");
        sb.append(this.getVersion());
        return sb.toString();
    }
}
