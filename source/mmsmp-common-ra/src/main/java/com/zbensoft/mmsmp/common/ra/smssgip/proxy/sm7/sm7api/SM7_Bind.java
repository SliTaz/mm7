package com.zbensoft.mmsmp.common.ra.smssgip.proxy.sm7.sm7api;

import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.common.SecurityTools;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.common.TypeConvert;

import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SM7_Bind extends SM7_Command {
    private StringBuffer strBuf;

    public SM7_Bind(ByteBuffer buf) {
        this.buf = buf;
    }

    public String getClientID() {
        byte[] clientID = new byte[10];
        this.buf.position(16);
        this.buf.get(clientID, 0, 10);
        return new String(clientID);
    }

    public String getShared_Secret() {
        byte[] shared_Secret = new byte[16];
        this.buf.position(26);
        this.buf.get(shared_Secret, 0, 16);
        return new String(shared_Secret);
    }

    public int getLoginMode() {
        return this.buf.get(32);
    }

    public String getTimeStamp() {
        byte[] timeStamp = new byte[4];
        this.buf.position(33);
        this.buf.get(timeStamp, 0, 4);
        return new String(timeStamp);
    }

    public int getVersion() {
        return this.buf.get(37);
    }

    public SM7_Bind(String clientId, String shared_Secret, int loginMode, Date timeStamp, int version) {
        int len = 48;
        byte[] buf = new byte[len];
        System.arraycopy(clientId.getBytes(), 0, buf, 16, clientId.length());
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

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMddHHmmss");
        String strTimeStamp = dateFormat.format(timeStamp).substring(2);
        System.arraycopy(strTimeStamp.toString().getBytes(), 0, tmpbuf, tmploc, 10);
        SecurityTools.md5(tmpbuf, 0, len, buf, 26);
        buf[42] = (byte)loginMode;
        TypeConvert.int2byte(Integer.parseInt(strTimeStamp), buf, 43);
        buf[47] = (byte)version;
        this.buf = ByteBuffer.wrap(buf);
        this.setPacketLength(buf.length);
        this.setCommandID(1);
    }

    public String toString() {
        StringBuffer outBuf = new StringBuffer(600);
        outBuf.append("SM7bindMessage: ");
        outBuf.append("PacketLength=" + this.getPacketLength());
        outBuf.append(",RequestID=" + this.getCommandID());
        outBuf.append(",Status=" + this.getStatus());
        outBuf.append(",SequenceID=" + this.getSeqID());
        this.strBuf = null;
        if (this.strBuf == null) {
            this.strBuf = new StringBuffer(600);
            this.strBuf.append(",clientId = " + this.getClientID());
            this.strBuf.append(",shared_Secret = " + this.getShared_Secret());
            this.strBuf.append(",loginMode = " + this.getLoginMode());
            this.strBuf.append(",timeStamp = " + this.getTimeStamp());
            this.strBuf.append(",version = " + this.getVersion());
        }

        outBuf.append(this.strBuf.toString());
        return outBuf.toString().trim();
    }

    public static void main(String[] args) {
        String str = "123";
        ByteBuffer buf = ByteBuffer.allocate(100);
        SecurityTools.md5("qwe123456".getBytes(), 0, 9, buf.array(), 0);
        byte[] bb = SecurityTools.md5(buf.array(), 0, 9);

        for(int i = 0; i < bb.length; ++i) {
            System.out.print(bb[i]);
        }

        System.out.println((new String(buf.array())).trim());
    }
}