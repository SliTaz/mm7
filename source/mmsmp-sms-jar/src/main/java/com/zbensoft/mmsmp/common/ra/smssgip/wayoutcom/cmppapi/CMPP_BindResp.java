package com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.cmppapi;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

public class CMPP_BindResp extends CMPP_Command {
    public CMPP_BindResp(ByteBuffer buf) {
        this.buf = buf;
    }

    public int getStatus() {
        return this.buf.getInt(12);
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
        return "CMPP_BindResp:Seq=" + this.getSeqID() + " Status=" + this.getStatus();
    }
}
