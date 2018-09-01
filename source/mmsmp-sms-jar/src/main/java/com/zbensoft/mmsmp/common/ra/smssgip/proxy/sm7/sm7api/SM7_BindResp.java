package com.zbensoft.mmsmp.common.ra.smssgip.proxy.sm7.sm7api;

import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.common.SecurityTools;

import java.nio.ByteBuffer;

public class SM7_BindResp extends SM7_Command {
    public SM7_BindResp(ByteBuffer buf) {
        this.buf = buf;
    }

    public SM7_BindResp(SM7_Bind bind, int result) {
        byte[] pac = new byte[33];
        ByteBuffer buf = ByteBuffer.wrap(pac);
        this.buf = buf;
        this.setPacketLength(pac.length);
        this.setCommandID(-2147483647);
        String auth = "0" + bind.getClientID() + bind.getShared_Secret();
        byte[] md5 = SecurityTools.md5(auth.getBytes(), 0, auth.getBytes().length);

        for(int i = 0; i < md5.length; ++i) {
            pac[15 + i] = md5[i];
        }

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
        sb.append("SM7_BindResp:AuthenticatorServer=" + this.getAuthenticatorServer());
        sb.append(";Version=" + this.getVersion());
        return sb.toString();
    }
}
