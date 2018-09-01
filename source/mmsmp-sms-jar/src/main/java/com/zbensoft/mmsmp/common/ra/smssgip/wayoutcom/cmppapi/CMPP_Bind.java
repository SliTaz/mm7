package com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.cmppapi;

import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.common.SecurityTools;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.common.TypeConvert;

import java.nio.ByteBuffer;
import java.util.Date;

public class CMPP_Bind extends CMPP_Command {
    public CMPP_Bind(ByteBuffer buf) {
        this.buf = buf;
    }

    public CMPP_Bind(String source_Addr, int version, String shared_Secret, Date timestamp) {
        int len = 39;
        byte[] buf = new byte[len];
        TypeConvert.int2byte(len, buf, 0);
        TypeConvert.int2byte(1, buf, 4);
        System.arraycopy(source_Addr.getBytes(), 0, buf, 12, source_Addr.length());
        if (shared_Secret != null) {
            len = source_Addr.length() + 19 + shared_Secret.length();
        } else {
            len = source_Addr.length() + 19;
        }

        byte[] tmpbuf = new byte[len];
        System.arraycopy(source_Addr.getBytes(), 0, tmpbuf, 0, source_Addr.length());
        int tmploc = source_Addr.length() + 9;
        if (shared_Secret != null) {
            System.arraycopy(shared_Secret.getBytes(), 0, tmpbuf, tmploc, shared_Secret.length());
            tmploc += shared_Secret.length();
        }

        String tmptime = "0008080808";
        System.arraycopy(tmptime.getBytes(), 0, tmpbuf, tmploc, 10);
        SecurityTools.md5(tmpbuf, 0, len, buf, 18);
        buf[34] = (byte)version;
        TypeConvert.int2byte(8080808, buf, 35);
        this.buf = ByteBuffer.wrap(buf);
    }

    public String toString() {
        return "CMPP_Bind:Seq=" + this.getSeqID();
    }
}
