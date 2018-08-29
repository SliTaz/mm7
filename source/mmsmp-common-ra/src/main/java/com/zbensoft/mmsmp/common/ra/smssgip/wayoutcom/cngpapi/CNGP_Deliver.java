package com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.cngpapi;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.Calendar;
import java.util.Date;

public class CNGP_Deliver extends CNGP_Command {
    public CNGP_Deliver() {
    }

    public CNGP_Deliver(ByteBuffer buf) {
        this.buf = buf;
    }

    public CNGP_Deliver(CNGP_Deliver deliver, byte[] msgID) {
        byte[] pac = new byte[deliver.getPacketLength()];
        ByteBuffer buf = ByteBuffer.wrap(pac);
        this.buf = buf;
        deliver.buf.clear();
        this.buf.put(deliver.buf);
        this.setReportMsgID(msgID);
    }

    public byte[] getMsgID() {
        byte[] msgID = new byte[10];
        this.buf.position(16);
        this.buf.get(msgID, 0, 10);
        return msgID;
    }

    public void setMsgID(byte[] msgID) {
        this.buf.position(16);
        this.buf.put(msgID, 0, 10);
    }

    public int getIsReport() {
        return this.buf.get(26);
    }

    public int getMsgFormat() {
        return this.buf.get(27);
    }

    public Date getRecvTime() {
        try {
            int tmpYear = TypeConvert.byte2int(this.buf.array(), 27);
            byte[] tmpbyte = new byte[2];
            System.arraycopy(this.buf, 31, tmpbyte, 0, 2);
            String tmpstr = new String(tmpbyte);
            int tmpMonth = Integer.parseInt(tmpstr);
            System.arraycopy(this.buf, 33, tmpbyte, 0, 2);
            tmpstr = new String(tmpbyte);
            int tmpDay = Integer.parseInt(tmpstr);
            System.arraycopy(this.buf, 35, tmpbyte, 0, 2);
            tmpstr = new String(tmpbyte);
            int tmpHour = Integer.parseInt(tmpstr);
            System.arraycopy(this.buf, 37, tmpbyte, 0, 2);
            tmpstr = new String(tmpbyte);
            int tmpMinute = Integer.parseInt(tmpstr);
            System.arraycopy(this.buf, 39, tmpbyte, 0, 2);
            tmpstr = new String(tmpbyte);
            int tmpSecond = Integer.parseInt(tmpstr);
            Calendar calendar = Calendar.getInstance();
            calendar.set(tmpYear, tmpMonth, tmpDay, tmpHour, tmpMinute, tmpSecond);
            Date date1 = calendar.getTime();
            return date1;
        } catch (Exception var12) {
            Date date = null;
            return (Date)date;
        }
    }

    public String getSrcTermID() {
        byte[] srcTermId = new byte[21];
        System.arraycopy(this.buf.array(), 42, srcTermId, 0, 21);
        return (new String(srcTermId)).trim();
    }

    public String getDestTermID() {
        byte[] destTermId = new byte[21];
        System.arraycopy(this.buf.array(), 63, destTermId, 0, 21);
        return (new String(destTermId)).trim();
    }

    public byte[] getReportMsgID() {
        byte[] msgID = new byte[10];
        if (this.getIsReport() == 1) {
            System.arraycopy(this.getContent(), 3, msgID, 0, msgID.length);
        }

        return msgID;
    }

    public void setReportMsgID(byte[] msgID) {
        if (this.getIsReport() == 1) {
            this.buf.position(88);
            this.buf.put(msgID, 0, 10);
        }

    }

    public String getDelivercontent() {
        if (this.getIsReport() == 0) {
            try {
                if (this.getMsgFormat() == 15) {
                    return (new String(this.getContent(), "GBK")).trim();
                } else {
                    return this.getMsgFormat() == 8 ? (new String(this.getContent(), "UTF-16BE")).trim() : (new String(this.getContent())).trim();
                }
            } catch (UnsupportedEncodingException var2) {
                var2.printStackTrace();
                return "";
            }
        } else {
            return "";
        }
    }

    public int getMsgLength() {
        return this.buf.get(84) & 255;
    }

    public byte[] getContent() {
        int msgLength = this.getMsgLength();
        byte[] con = new byte[msgLength];
        byte[] byteBuf = this.buf.array();
        System.arraycopy(byteBuf, 85, con, 0, msgLength);
        return con;
    }

    public String toString() {
        StringBuffer strBuf = new StringBuffer(600);
        strBuf.append("CNGPDeliverMessage: ");
        strBuf.append("PacketLength=".concat(String.valueOf(String.valueOf(this.getPacketLength()))));
        strBuf.append(",RequestID=".concat(String.valueOf(String.valueOf(this.getCommandID()))));
        strBuf.append(",Status=".concat(String.valueOf(String.valueOf(this.getStatus()))));
        strBuf.append(",Sequence_Id=".concat(String.valueOf(String.valueOf(this.getSeqID()))));
        strBuf.append(",MsgID=".concat(String.valueOf(String.valueOf(new String(this.getMsgID())))));
        strBuf.append(",IsReport=".concat(String.valueOf(String.valueOf(this.getIsReport()))));
        strBuf.append(",MsgFormat=".concat(String.valueOf(String.valueOf(this.getMsgFormat()))));
        strBuf.append(",RecvTime=".concat(String.valueOf(String.valueOf(this.getRecvTime()))));
        strBuf.append(",SrcTermID=".concat(String.valueOf(String.valueOf(this.getSrcTermID()))));
        strBuf.append(",DestTermID=".concat(String.valueOf(String.valueOf(this.getDestTermID()))));
        strBuf.append(",MsgLength=".concat(String.valueOf(String.valueOf(this.getMsgLength()))));
        strBuf.append(",MsgContent=".concat(String.valueOf(String.valueOf(new String(this.getContent())))));
        return strBuf.toString().trim();
    }

    public boolean isReport() {
        return this.getIsReport() == 1;
    }

    public String getReportStat() {
        if (this.getIsReport() == 1) {
            String content = new String(this.getContent());
            int begin = content.indexOf("stat:");
            return content.substring(begin + 5, begin + 11);
        } else {
            return "";
        }
    }
}
