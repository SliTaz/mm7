package com.zbensoft.mmsmp.common.ra.smssgip.proxy.sm7.sm7api;

import com.zbensoft.mmsmp.common.ra.smssgip.proxy.util.Debug;

import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SM7_Submit extends SM7_Command {
    public SM7_Submit(ByteBuffer buf) {
        this.buf = buf;
    }

    public void init(String SPId, int subType, int needReport, int priority, String serviceId, String feeType, int feeUserType, String feeCode, int msgFormat, Date validTime, Date atTime, String srcTermId, String chargeTermId, String[] destTermId, String content, int protocolValue, String linkId) {
        int destTermIdCount = destTermId.length;
        byte[] msgContent = (byte[])null;

        try {
            if (msgFormat == 8) {
                msgContent = content.getBytes("UTF-16BE");
            } else if (msgFormat == 15) {
                msgContent = content.getBytes("GBK");
            } else {
                msgContent = content.getBytes("ISO8859-1");
            }
        } catch (Exception var27) {
            System.out.println("sms content code error:" + var27.toString());
        }

        int msgLength = msgContent.length;
        int len = 132 + 21 * destTermIdCount + msgContent.length + 25;
        byte[] buf = new byte[len];
        System.arraycopy(SPId.getBytes(), 0, buf, 16, SPId.length());
        buf[26] = (byte)subType;
        buf[27] = (byte)needReport;
        buf[28] = (byte)priority;
        System.arraycopy(serviceId.getBytes(), 0, buf, 29, serviceId.length());
        System.arraycopy(feeType.getBytes(), 0, buf, 39, feeType.length());
        buf[41] = (byte)feeUserType;
        System.arraycopy(feeCode.getBytes(), 0, buf, 42, feeCode.length());
        buf[48] = (byte)msgFormat;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMddHHmmss");
        String tmpTime;
        if (validTime != null) {
            tmpTime = String.valueOf(String.valueOf(dateFormat.format(validTime))).concat("032+");
            System.arraycopy(tmpTime.getBytes(), 0, buf, 49, 16);
        }

        if (atTime != null) {
            tmpTime = String.valueOf(String.valueOf(dateFormat.format(atTime))).concat("032+");
            System.arraycopy(tmpTime.getBytes(), 0, buf, 66, 16);
        }

        System.arraycopy(srcTermId.getBytes(), 0, buf, 83, srcTermId.length());
        buf[104] = (byte)destTermIdCount;
        int i;
        for(i = 0; i < destTermIdCount; ++i) {
            System.arraycopy(destTermId[i].getBytes(), 0, buf, 105 + i * 21, destTermId[i].length());
        }

        int pos = 105 + 21 * destTermIdCount;
        System.arraycopy(chargeTermId.getBytes(), 0, buf, pos, chargeTermId.length());
        pos += 21;
        buf[pos] = (byte)msgLength;
        ++pos;
        System.arraycopy(msgContent, 0, buf, pos, msgContent.length);
        pos += msgContent.length;
        byte[] tempBytes;
        if (linkId != null && !linkId.equals("")) {
            tempBytes = linkId.getBytes();

            for(i = 0; i < tempBytes.length && i < 20; ++i) {
                buf[pos + i] = tempBytes[i];
            }
        }

        pos += 20;
        tempBytes = new byte[]{1, 0, 0, 1, (byte)protocolValue, 1, 4, 0, 1, 0};
        System.arraycopy(tempBytes, 0, buf, buf.length - 10, 10);
        this.buf = ByteBuffer.wrap(buf);
        this.setSeqID(ProduceSeq.getSeq());
        this.setPacketLength(buf.length);
        this.setCommandID(2);
    }

    public SM7_Submit(String SPId, int subType, int needReport, int priority, String serviceId, String feeType, int feeUserType, String feeCode, int msgFormat, Date validTime, Date atTime, String srcTermId, String chargeTermId, String[] destTermId, String content, int protocolValue) {
        this.init(SPId, subType, needReport, priority, serviceId, feeType, feeUserType, feeCode, msgFormat, validTime, atTime, srcTermId, chargeTermId, destTermId, content, protocolValue, "");
    }

    public SM7_Submit(String SPId, int subType, int needReport, int priority, String serviceId, String feeType, int feeUserType, String feeCode, int msgFormat, Date validTime, Date atTime, String srcTermId, String chargeTermId, String[] destTermId, String content, int protocolValue, String linkId) {
        this.init(SPId, subType, needReport, priority, serviceId, feeType, feeUserType, feeCode, msgFormat, validTime, atTime, srcTermId, chargeTermId, destTermId, content, protocolValue, linkId);
    }

    public SM7_Submit(SM7_Submit ss, int seq) {
        this.buf = ss.getBuf();
        this.setSeqID(seq);
    }

    public String getchargeTermId() {
        byte[] chargeTermId = new byte[21];
        this.buf.position(105 + this.getdestTermIdCount() * 21);
        this.buf.get(chargeTermId, 0, 21);
        return new String(chargeTermId);
    }

    public byte getdestTermIdCount() {
        return this.buf.get(104);
    }

    public String[] getdestTermId() {
        byte[][] destTermId = new byte[this.getdestTermIdCount()][];
        String[] id = new String[this.getdestTermIdCount()];

        for(int i = 0; i < this.getdestTermIdCount(); ++i) {
            this.buf.position(105 + i * 21);
            destTermId[i] = new byte[21];
            this.buf.get(destTermId[i], 0, 21);
            id[i] = new String(destTermId[i]);
        }

        return id;
    }

    public static void main(String[] args) throws Exception {
        String[] userPhones = new String[]{"188010301115"};
        SM7_Submit submit = new SM7_Submit("77777", 1, 1, 0, "77777", "01", 0, "0", 15, (Date)null, (Date)null, "77777", "188010301115", userPhones, "hello", 0);
        Debug.dump(submit.getBuf().array());
        Debug.dump(submit.toString());
    }

    public String getSPID() {
        byte[] spid = new byte[10];
        this.buf.position(16);
        this.buf.get(spid, 0, 10);
        return new String(spid);
    }

    public byte getSubType() {
        return this.buf.get(26);
    }

    public int getNeedReport() {
        return this.buf.get(27);
    }

    public void setNeedReport(boolean report) {
        if (report) {
            this.buf.put(27, (byte)1);
        } else {
            this.buf.put(27, (byte)0);
        }

    }

    public byte getPriority() {
        return this.buf.get(28);
    }

    public String getServiceID() {
        byte[] serviceid = new byte[10];
        this.buf.position(29);
        this.buf.get(serviceid, 0, 10);
        return new String(serviceid);
    }

    public String getFeeType() {
        byte[] feeType = new byte[2];
        this.buf.position(39);
        this.buf.get(feeType, 0, 2);
        return new String(feeType);
    }

    public byte getFeeUserType() {
        return this.buf.get(41);
    }

    public String getFeeCode() {
        byte[] feeCode = new byte[6];
        this.buf.position(42);
        this.buf.get(feeCode, 0, 6);
        return new String(feeCode);
    }

    public byte getMsgFormat() {
        return this.buf.get(48);
    }

    public String getValidTime() {
        byte[] validTime = new byte[17];
        this.buf.position(49);
        this.buf.get(validTime, 0, 17);
        return new String(validTime);
    }

    public String getAtTime() {
        byte[] atTime = new byte[17];
        this.buf.position(66);
        this.buf.get(atTime, 0, 17);
        return new String(atTime);
    }

    public String getSrcTermId() {
        byte[] srcTermId = new byte[21];
        this.buf.position(83);
        this.buf.get(srcTermId, 0, 21);
        return new String(srcTermId);
    }

    public int getMsgLength() {
        this.buf.position(126 + 21 * this.getdestTermIdCount());
        return 255 & this.buf.get();
    }

    public String getMsgContent() {
        byte[] content = new byte[this.getMsgLength()];
        this.buf.position(126 + 21 * this.getdestTermIdCount() + 1);
        int len;
        if (this.buf.remaining() < content.length) {
            System.err.println("The length of Content is wrong!!! the length is ignored");
            len = this.buf.remaining();
        } else {
            len = content.length;
        }

        this.buf.get(content, 0, len);
        return new String(content);
    }

    public String toString() {
        StringBuffer outBuf = new StringBuffer(600);
        outBuf.append("CNGPSubmitMessage: ");
        outBuf.append("PacketLength=").append(this.getPacketLength());
        outBuf.append(",RequestID=").append(this.getCommandID());
        outBuf.append(",Status=").append(this.getStatus());
        outBuf.append(",SequenceID=").append(this.getSeqID());
        outBuf = new StringBuffer(600);
        outBuf.append(",SPId=").append(this.getSPID());
        outBuf.append(",subType=").append(this.getSubType());
        outBuf.append(",NeedReport=").append(this.getNeedReport());
        outBuf.append(",Priority=").append(this.getPriority());
        outBuf.append(",ServiceID=").append(this.getServiceID());
        outBuf.append(",FeeType=").append(this.getFeeType());
        outBuf.append(",feeUserType=").append(this.getFeeUserType());
        outBuf.append(",FeeCode=").append(this.getFeeCode());
        outBuf.append(",msgFormat=").append(this.getMsgFormat());
        outBuf.append(",validTime=").append(this.getValidTime());
        outBuf.append(",atTime=").append(this.getAtTime());
        outBuf.append(",SrcTermID=").append(this.getSrcTermId());
        outBuf.append(",ChargeTermID=").append(this.getchargeTermId());
        outBuf.append(",DestTermIDCount=").append(this.getdestTermIdCount());

        for(int t = 0; t < this.getdestTermIdCount(); ++t) {
            outBuf.append(",DestTermID[").append(t).append("]=").append(this.getdestTermId()[t]);
        }

        outBuf.append(",MsgLength=").append(this.getMsgLength());
        outBuf.append(",MsgContent=").append(this.getMsgContent());
        return outBuf.toString().trim();
    }
}
