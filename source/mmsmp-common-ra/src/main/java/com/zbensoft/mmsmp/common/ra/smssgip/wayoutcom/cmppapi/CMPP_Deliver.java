package com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.cmppapi;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

public class CMPP_Deliver extends CMPP_Command {
    public CMPP_Deliver() {
    }

    public CMPP_Deliver(ByteBuffer buf) {
        this.buf = buf;
    }

    public CMPP_Deliver(CMPP_Deliver deliver, long msgID) {
        byte[] pac = new byte[deliver.getPacketLength()];
        ByteBuffer buf = ByteBuffer.wrap(pac);
        this.buf = buf;
        deliver.buf.clear();
        this.buf.put(deliver.buf);
        this.setReportMsgID(msgID);
    }

    public long getMsgID() {
        return this.buf.getLong(12);
    }

    public void setMsgID(long msgID) {
        this.buf.putLong(12, msgID);
    }

    public int getIsReport() {
        return this.buf.get(87);
    }

    public boolean isReport() {
        return this.getIsReport() == 1;
    }

    public long getReportMsgID() {
        long msgID = 0L;
        if (this.getIsReport() == 1) {
            msgID = ByteBuffer.wrap(this.getContent()).getLong(0);
        }

        return msgID;
    }

    public void setReportMsgID(long msgID) {
        if (this.getIsReport() == 1) {
            this.buf.putLong(89, msgID);
        }

    }

    public String getReportStat() {
        return this.getIsReport() == 1 ? (new String(this.getContent(), 8, 7)).trim() : "";
    }

    public byte getMsgFmt() {
        return this.buf.get(54);
    }

    public String getDeliverMsg() {
        byte msgFmt = this.getMsgFmt();
        if (this.getIsReport() == 0) {
            try {
                if (msgFmt == 0) {
                    return new String(this.getContent(), "ISO8859-1");
                } else {
                    return msgFmt == 8 ? new String(this.getContent(), "UTF-16BE") : new String(this.getContent(), "GBK");
                }
            } catch (UnsupportedEncodingException var3) {
                var3.printStackTrace();
                return null;
            }
        } else {
            return "";
        }
    }

    public byte[] getContent() {
        int msgLength = this.buf.get(88) & 255;
        byte[] con = new byte[msgLength];
        byte[] byteBuf = this.buf.array();
        System.arraycopy(byteBuf, 89, con, 0, msgLength);
        return con;
    }

    public String getDesTermID() {
        byte[] destTermId = new byte[21];
        System.arraycopy(this.buf.array(), 20, destTermId, 0, 21);
        return (new String(destTermId)).trim();
    }

    public String getSrcTermID() {
        byte[] srcTermId = new byte[32];
        System.arraycopy(this.buf.array(), 54, srcTermId, 0, 32);
        return (new String(srcTermId)).trim();
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("CMPP_Deliver:seq=");
        buffer.append(this.getSeqID());
        if (this.isReport()) {
            buffer.append(" ResportStat=");
            buffer.append(this.getReportStat());
            buffer.append(" MsgId=");
            buffer.append(this.getMsgID());
        } else {
            buffer.append(" Message=");
            buffer.append(this.getDeliverMsg());
            buffer.append(" DesTermId=");
            buffer.append(this.getDesTermID());
            buffer.append(" SrcTermId=");
            buffer.append(this.getSrcTermID());
        }

        return buffer.toString();
    }
}

