package com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.smgpapi;

import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.common.SecurityTools;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.common.TypeConvert;

import java.nio.ByteBuffer;
import java.util.Date;

public class SMGP_Bind extends SMGP_Command {
    public SMGP_Bind(ByteBuffer buf) {
        this.buf = buf;
    }

    public SMGP_Bind(String clientId, String shared_Secret, int loginMode, Date timestamp, int version) {
        int len = 42;
        byte[] buf = new byte[len];
        TypeConvert.int2byte(len, buf, 0);
        TypeConvert.int2byte(1, buf, 4);
        System.arraycopy(clientId.getBytes(), 0, buf, 12, clientId.length());
        if (shared_Secret != null) {
            len = clientId.length() + 17 + shared_Secret.length();
        } else {
            len = clientId.length() + 17;
        }

        byte[] tmpbuf = new byte[len];
        System.arraycopy(clientId.getBytes(), 0, tmpbuf, 0, clientId.length());
        int tmploc = clientId.length() + 7;
        if (shared_Secret != null) {
            System.arraycopy(shared_Secret.getBytes(), 0, tmpbuf, tmploc, shared_Secret.length());
            tmploc += shared_Secret.length();
        }

        String tmptime = "0008080808";
        System.arraycopy(tmptime.getBytes(), 0, tmpbuf, tmploc, 10);
        SecurityTools.md5(tmpbuf, 0, len, buf, 20);
        buf[36] = (byte)loginMode;
        TypeConvert.int2byte(8080808, buf, 37);
        buf[41] = (byte)version;
        this.buf = ByteBuffer.wrap(buf);
    }

    public String getClientID() {
        byte[] clientID = new byte[10];
        this.buf.position(12);
        this.buf.get(clientID, 0, 8);
        return new String(clientID);
    }

    public String getAuthenticatorClient() {
        byte[] shared_Secret = new byte[16];
        this.buf.position(20);
        this.buf.get(shared_Secret, 0, 16);
        return new String(shared_Secret);
    }

    public int getLoginMode() {
        return this.buf.get(36);
    }

    public String getTimeStamp() {
        byte[] timeStamp = new byte[4];
        this.buf.position(37);
        this.buf.get(timeStamp, 0, 4);
        return new String(timeStamp);
    }

    public int getVersion() {
        return this.buf.get(41);
    }

    public String toString() {
        StringBuffer outBuf = new StringBuffer(600);
        outBuf.append("SMGPbindMessage: ");
        outBuf.append("PacketLength=");
        outBuf.append(this.getClientID());
        outBuf.append(",SequenceID=");
        outBuf.append(this.getSeqID());
        outBuf.append(",clientId = ");
        outBuf.append(this.getClientID());
        outBuf.append(",AuthenticatorClient = ");
        outBuf.append(this.getAuthenticatorClient());
        outBuf.append(",loginMode = ");
        outBuf.append(this.getLoginMode());
        return outBuf.toString().trim();
    }
}
