package com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.smgpapi;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

public class SMGP_Deliver extends SMGP_Command {
    public SMGP_Deliver() {
    }

    public SMGP_Deliver(ByteBuffer buf) {
        this.buf = buf;
    }

    public SMGP_Deliver(SMGP_Deliver deliver, byte[] msgID) {
        byte[] pac = new byte[deliver.getPacketLength()];
        ByteBuffer buf = ByteBuffer.wrap(pac);
        this.buf = buf;
        deliver.buf.clear();
        this.buf.put(deliver.buf);
        this.setReportMsgID(msgID);
    }

    public byte[] getMsgID() {
        byte[] msgID = new byte[10];
        this.buf.position(12);
        this.buf.get(msgID, 0, 10);
        return msgID;
    }

    private void setMsgID(byte[] msgID) {
        this.buf.position(12);
        this.buf.put(msgID, 0, 10);
    }

    public int getIsReport() {
        return this.buf.get(22);
    }

    public byte[] getReportMsgID() {
        byte[] msgID = new byte[10];
        if (this.getIsReport() == 1) {
            System.arraycopy(this.getContent(), 0, msgID, 0, 10);
        }

        return msgID;
    }

    public void setReportMsgID(byte[] msgID) {
        if (this.getIsReport() == 1) {
            this.buf.position(81);
            this.buf.put(msgID, 0, 10);
        }

    }

    public String getReportStat() {
        return this.getIsReport() == 1 ? new String(this.getContent(), 36, 7) : "";
    }

    public String getReportErr() {
        return this.getIsReport() == 1 ? new String(this.getContent(), 43, 3) : "";
    }

    public String getDelivercontent() {
        if (this.getIsReport() == 0) {
            int format = this.getMsgFormat();

            try {
                if (format == 15) {
                    return (new String(this.getContent(), "GBK")).trim();
                }

                if (format == 8) {
                    return (new String(this.getContent(), "UTF-16BE")).trim();
                }
            } catch (UnsupportedEncodingException var3) {
                var3.printStackTrace();
            }

            return (new String(this.getContent())).trim();
        } else {
            return "";
        }
    }

    public int getMsgFormat() {
        return 0;
    }

    public byte[] getContent() {
        int msgLength = this.buf.get(80) & 255;
        byte[] con = new byte[msgLength];
        byte[] byteBuf = this.buf.array();
        System.arraycopy(byteBuf, 81, con, 0, msgLength);
        return con;
    }

    public String getDestTermID() {
        byte[] destTermId = new byte[21];
        System.arraycopy(this.buf.array(), 59, destTermId, 0, 21);
        return (new String(destTermId)).trim();
    }

    public boolean isReport() {
        return this.getIsReport() == 1;
    }

    public String getSrcTermID() {
        byte[] srcTermId = new byte[21];
        System.arraycopy(this.buf.array(), 38, srcTermId, 0, 11);
        return (new String(srcTermId)).trim();
    }

    public String toString() {
        return "SMGP_Deliver:Seq=" + this.getSeqID();
    }
}
