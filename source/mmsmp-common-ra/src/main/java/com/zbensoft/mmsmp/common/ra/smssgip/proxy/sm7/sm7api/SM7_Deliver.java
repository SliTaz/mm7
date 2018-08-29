package com.zbensoft.mmsmp.common.ra.smssgip.proxy.sm7.sm7api;

import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SM7_Deliver extends SM7_Command {
    public SM7_Deliver() {
        byte[] buf = new byte[100];
        this.buf = ByteBuffer.wrap(buf);
    }

    public SM7_Deliver(ByteBuffer buf) {
        this.buf = buf;
    }

    public SM7_Deliver(SM7_Deliver deliver, byte[] msgID) {
        byte[] pac = new byte[deliver.getPacketLength()];
        ByteBuffer buf = ByteBuffer.wrap(pac);
        this.buf = buf;
        deliver.buf.clear();
        this.buf.put(deliver.buf);
        this.setReportMsgID(msgID);
    }

    public byte[] getSMGW() {
        byte[] smgw = new byte[10];
        this.buf.position(16);
        this.buf.get(smgw, 0, 10);
        return smgw;
    }

    public void setSMGW(byte[] smgw) {
        this.buf.position(16);
        this.buf.put(smgw, 0, 10);
    }

    public int getIsReport() {
        return this.buf.get(26);
    }

    public int getMsgFormat() {
        return this.buf.get(27);
    }

    public String getRecvTime() {
        byte[] recvTime = new byte[14];
        this.buf.position(28);
        this.buf.get(recvTime, 0, 14);
        return new String(recvTime);
    }

    public String getSrcTermID() {
        byte[] srcTermId = new byte[21];
        System.arraycopy(this.buf.array(), 42, srcTermId, 0, 21);
        return (new String(srcTermId)).trim();
    }

    public int getSrcTermPlag() {
        return this.buf.get(63);
    }

    public String getDestTermID() {
        byte[] destTermId = new byte[21];
        System.arraycopy(this.buf.array(), 64, destTermId, 0, 21);
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

    public String getSub() {
        byte[] sub = new byte[3];
        if (this.getIsReport() == 1) {
            System.arraycopy(this.getContent(), 13, sub, 0, sub.length);
        }

        return new String(sub);
    }

    public String getSlvrd() {
        byte[] slvrd = new byte[3];
        if (this.getIsReport() == 1) {
            System.arraycopy(this.getContent(), 16, slvrd, 0, slvrd.length);
        }

        return new String(slvrd);
    }

    public String getSubmit_date() {
        byte[] submit_date = new byte[10];
        if (this.getIsReport() == 1) {
            System.arraycopy(this.getContent(), 19, submit_date, 0, submit_date.length);
        }

        return new String(submit_date);
    }

    public String getDone_date() {
        byte[] done_date = new byte[10];
        if (this.getIsReport() == 1) {
            System.arraycopy(this.getContent(), 29, done_date, 0, done_date.length);
        }

        return new String(done_date);
    }

    public String getStat() {
        byte[] stat = new byte[7];
        if (this.getIsReport() == 1) {
            System.arraycopy(this.getContent(), 39, stat, 0, stat.length);
        }

        return new String(stat);
    }

    public String getErr() {
        byte[] err = new byte[3];
        if (this.getIsReport() == 1) {
            System.arraycopy(this.getContent(), 46, err, 0, err.length);
        }

        return new String(err);
    }

    public String getTxt() {
        byte[] txt = new byte[20];
        System.out.println("legth" + this.getContent().length);
        if (this.getIsReport() == 1) {
            System.arraycopy(this.getContent(), 49, txt, 0, txt.length);
        }

        return new String(txt);
    }

    public String getDelivercontent() {
        return this.getIsReport() == 0 ? (new String(this.getContent())).trim() : "";
    }

    public int getMsgLength() {
        return this.buf.get(85) & 255;
    }

    public byte[] getContent() {
        int msgLength = this.getMsgLength();
        byte[] con = new byte[msgLength];
        byte[] byteBuf = this.buf.array();
        System.arraycopy(byteBuf, 86, con, 0, msgLength);
        return con;
    }

    public String getLinkID() {
        int msgLength = this.getMsgLength();
        byte[] linkId = new byte[20];
        this.buf.position(86 + msgLength);
        this.buf.get(linkId, 0, 20);
        return new String(linkId);
    }

    public int getProtocolId() {
        int msgLength = this.getMsgLength();
        return this.buf.get(106 + msgLength + 4);
    }

    public String getSrcTermPseudo() {
        int msgLength = this.getMsgLength();
        byte[] srcTermPseudo = new byte[32];
        this.buf.position(115 + msgLength);
        this.buf.get(srcTermPseudo, 0, 32);
        return new String(srcTermPseudo);
    }

    public String toString() {
        StringBuffer strBuf = new StringBuffer(600);
        strBuf.append("SM7DeliverMessage: ");
        strBuf.append("PacketLength=" + this.getPacketLength());
        strBuf.append(",RequestID=" + this.getCommandID());
        strBuf.append(",Status=" + this.getStatus());
        strBuf.append(",Sequence_Id=" + this.getSeqID());
        strBuf.append(",MsgID=" + new String(this.getSMGW()));
        strBuf.append(",IsReport=" + this.getIsReport());
        strBuf.append(",MsgFormat=" + this.getMsgFormat());
        strBuf.append(",RecvTime=" + this.getRecvTime());
        strBuf.append(",SrcTermID=" + this.getSrcTermID());
        strBuf.append(",DestTermID=" + this.getDestTermID());
        strBuf.append(",MsgLength=" + this.getMsgLength());
        strBuf.append(",MsgContent=" + new String(this.getContent()));
        if (this.getIsReport() == 1) {
            strBuf.append(",id=" + this.getReportMsgID());
            strBuf.append(",sub=" + this.getSub());
            strBuf.append(",slvrd=" + this.getSlvrd());
            strBuf.append(",submit_date=" + this.getSubmit_date());
            strBuf.append(",done_date=" + this.getDone_date());
            strBuf.append(",stat=" + this.getStat());
            strBuf.append(",err=" + this.getErr());
            strBuf.append(",Txt=" + this.getTxt());
        }

        strBuf.append(",LinkID=" + this.getLinkID());
        strBuf.append(",ProtocalID=" + this.getProtocolId());
        strBuf.append(",SrcTermPseudo=" + this.getSrcTermPseudo());
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

    public SM7_Deliver(String smgw, int isReport, int msgFormat, Date recvTime, String srcTermID, int srcTermFlag, String destTermID, int msgLength, String msgCountent, String linkID, int protocolID_value, String srcTermPseudo_value, String id, String sub, String slvrd, String submit_date, String done_date, String stat, String err, String txt) {
        byte[] var21 = (byte[])null;

        try {
            if (msgFormat != 15) {
                throw new Exception("msgFormat must be 15!System exit");
            }

            var21 = msgCountent.getBytes("GBK");
        } catch (Exception var27) {
            System.exit(-1);
        }

        if (isReport == 1) {
            StringBuffer msg1 = new StringBuffer();
            msg1.append("000").append(id).append(sub).append(slvrd).append(submit_date).append(done_date).append(stat).append(err).append(txt);
            msgCountent = msg1.toString();
        }

        msgLength = msgCountent.length();
        int len = 200 + msgLength;
        byte[] buf = new byte[len];
        System.arraycopy(smgw.getBytes(), 0, buf, 16, smgw.length());
        buf[26] = (byte)isReport;
        buf[27] = (byte)msgFormat;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMddHHmmss");
        if (recvTime != null) {
            String tmpTime = String.valueOf(String.valueOf(dateFormat.format(recvTime))).concat("0+");
            System.out.println(tmpTime);
            System.arraycopy(tmpTime.getBytes(), 0, buf, 28, 14);
        }

        System.arraycopy(srcTermID.getBytes(), 0, buf, 42, srcTermID.length());
        buf[63] = (byte)srcTermFlag;
        System.arraycopy(destTermID.getBytes(), 0, buf, 64, destTermID.length());
        buf[85] = (byte)msgLength;
        System.arraycopy(msgCountent.getBytes(), 0, buf, 86, msgCountent.length());
        System.arraycopy(linkID.getBytes(), 0, buf, 86 + msgLength, linkID.length());
        int pos = 86 + msgLength + 20;
        TypeConvert.short2byte(256, buf, pos);
        pos += 2;
        TypeConvert.short2byte(1, buf, pos);
        pos += 2;
        buf[pos] = (byte)protocolID_value;
        ++pos;
        TypeConvert.short2byte(256, buf, pos);
        pos += 2;
        TypeConvert.short2byte(1, buf, pos);
        pos += 2;
        System.arraycopy(srcTermPseudo_value.getBytes(), 0, buf, 86 + msgLength + 20 + 9, srcTermPseudo_value.length());
        this.buf = ByteBuffer.wrap(buf);
        this.setSeqID(ProduceSeq.getSeq());
        this.setPacketLength(buf.length);
        this.setCommandID(2);

        for(int x = 0; x < len; ++x) {
            System.out.print(buf[x]);
        }

        System.out.println();
    }

    public static void main(String[] arg) {
        SM7_Deliver deliver = new SM7_Deliver("1000000000", 0, 15, new Date(), "10000000000000", 1, "100000000000000000000", 3, "123", "10000000000000000000", 9, "22222222222222222222222222222222", "1234567890", "333", "333", "2007101609", "2007101609", "1234567", "444", "55555555555555555555");
        System.out.println(deliver.toString());
    }
}
