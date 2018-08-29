package com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.cngpapi;

import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.common.SecurityTools;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.common.TypeConvert;

import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CNGP_Bind extends CNGP_Command {
    public CNGP_Bind(ByteBuffer buf) {
        this.buf = buf;
    }

    public CNGP_Bind(String clientId, String shared_Secret, int loginMode, Date timeStamp, int version) {
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

    public static void main(String[] args) {
        String str = "123";
        ByteBuffer buf = ByteBuffer.allocate(100);
        SecurityTools.md5("qwe123456".getBytes(), 0, 9, buf.array(), 0);
        System.out.println((new String(buf.array())).trim());
    }
}
