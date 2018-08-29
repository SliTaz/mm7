package com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.smgpapi;

import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.common.SecurityTools;

import java.nio.ByteBuffer;

public class SMGP_BindResp extends SMGP_Command {
    public SMGP_BindResp(ByteBuffer buf) {
        this.buf = buf;
    }

    public SMGP_BindResp(SMGP_Bind bind, int result) {
        byte[] pac = new byte[33];
        ByteBuffer buf = ByteBuffer.wrap(pac);
        this.buf = buf;
        this.setPacketLength(pac.length);
        this.setCommandID(-2147483647);
        this.setSeqID(bind.getSeqID());
        buf.position(12);
        buf.putInt(result);
        String auth = "0" + bind.getAuthenticatorClient() + "123456";
        byte[] md5 = SecurityTools.md5(auth.getBytes(), 0, auth.getBytes().length);

        for(int i = 0; i < md5.length; ++i) {
            pac[16 + i] = md5[i];
        }

    }

    public int getStatus() {
        return this.buf.getInt(12);
    }

    public void setStatus(int status) {
        this.buf.putInt(12, status);
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
        return "SMGP_BindResp:Seq=" + this.getSeqID() + " Status=" + this.getStatus();
    }
}
