package com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.cngpapi;

import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.common.TypeConvert;

import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CNGP_Submit extends CNGP_Command {
    private StringBuffer strBuf;

    public CNGP_Submit() {
        byte[] buf = new byte[100];
        this.buf = ByteBuffer.wrap(buf);
    }

    public CNGP_Submit(ByteBuffer buf) {
        this.buf = buf;
    }

    public CNGP_Submit(CNGP_Submit submit, int seq) {
        byte[] pac = new byte[submit.getPacketLength()];
        ByteBuffer buf = ByteBuffer.wrap(pac);
        this.buf = buf;
        submit.buf.clear();
        this.buf.put(submit.buf);
        this.setSeqID(seq);
        this.setNeedReport(true);
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

    public String getsrcTermId() {
        byte[] srcTermId = new byte[21];
        this.buf.position(83);
        this.buf.get(srcTermId, 0, 21);
        return new String(srcTermId);
    }

    public String getchargeTermId() {
        byte[] chargeTermId = new byte[21];
        this.buf.position(104);
        this.buf.get(chargeTermId, 0, 21);
        return new String(chargeTermId);
    }

    public byte getdestTermIdCount() {
        return this.buf.get(125);
    }

    public String[] getdestTermId() {
        byte[][] destTermId = new byte[this.getdestTermIdCount()][];
        String[] id = new String[this.getdestTermIdCount()];

        for(int i = 0; i < this.getdestTermIdCount(); ++i) {
            this.buf.position(126 + i * 21);
            destTermId[i] = new byte[21];
            this.buf.get(destTermId[i], 0, 21);
            id[i] = new String(destTermId[i]);
        }

        return id;
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

    public CNGP_Submit(String SPId, int subType, int needReport, int priority, String serviceId, String feeType, int feeUserType, String feeCode, int msgFormat, Date validTime, Date atTime, String srcTermId, String chargeTermId, String[] destTermId, String content, int protocolValue) {
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
        } catch (Exception var26) {
            System.out.println("sms content code error:" + var26.toString());
        }

        int msgLength = msgContent.length;
        int len = 132 + 21 * destTermIdCount + msgContent.length;
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
        System.arraycopy(chargeTermId.getBytes(), 0, buf, 104, chargeTermId.length());
        buf[125] = (byte)destTermIdCount;

        for(int i = 0; i < destTermIdCount; ++i) {
            System.arraycopy(destTermId[i].getBytes(), 0, buf, 126 + i * 21, destTermId[i].length());
        }

        int pos = 126 + 21 * destTermIdCount;
        buf[pos] = (byte)msgLength;
        ++pos;
        System.arraycopy(msgContent, 0, buf, pos, msgContent.length);
        pos += msgContent.length;
        TypeConvert.short2byte(256, buf, pos);
        pos += 2;
        TypeConvert.short2byte(1, buf, pos);
        pos += 2;
        buf[pos] = (byte)protocolValue;
        this.buf = ByteBuffer.wrap(buf);
        this.setSeqID(ProduceSeq.getSeq());
        this.setPacketLength(buf.length);
        this.setCommandID(2);
        this.strBuf = new StringBuffer(600);
        this.strBuf.append(",SPId=".concat(String.valueOf(String.valueOf(SPId))));
        this.strBuf.append(",subType=".concat(String.valueOf(String.valueOf(subType))));
        this.strBuf.append(",NeedReport=".concat(String.valueOf(String.valueOf(needReport))));
        this.strBuf.append(",Priority=".concat(String.valueOf(String.valueOf(priority))));
        this.strBuf.append(",ServiceID=".concat(String.valueOf(String.valueOf(serviceId))));
        this.strBuf.append(",FeeType=".concat(String.valueOf(String.valueOf(feeType))));
        this.strBuf.append(",feeUserType=".concat(String.valueOf(String.valueOf(feeUserType))));
        this.strBuf.append(",FeeCode=".concat(String.valueOf(String.valueOf(feeCode))));
        this.strBuf.append(",msgFormat=".concat(String.valueOf(String.valueOf(msgFormat))));
        this.strBuf.append(",validTime=".concat(String.valueOf(String.valueOf(validTime))));
        this.strBuf.append(",atTime=".concat(String.valueOf(String.valueOf(atTime))));
        this.strBuf.append(",SrcTermID=".concat(String.valueOf(String.valueOf(srcTermId))));
        this.strBuf.append(",ChargeTermID=".concat(String.valueOf(String.valueOf(chargeTermId))));
        this.strBuf.append(",DestTermIDCount=".concat(String.valueOf(String.valueOf(destTermIdCount))));

        for(int t = 0; t < destTermIdCount; ++t) {
            this.strBuf.append(String.valueOf(String.valueOf((new StringBuffer(",DestTermID[")).append(t).append("]=").append(destTermId[t]))));
        }

        this.strBuf.append(",MsgLength=".concat(String.valueOf(String.valueOf(content.length()))));
        this.strBuf.append(",MsgContent=".concat(String.valueOf(String.valueOf(content))));
        this.strBuf.append(",protocolValue=".concat(String.valueOf(String.valueOf(protocolValue))));
    }

    public String toString() {
        StringBuffer outBuf = new StringBuffer(600);
        outBuf.append("CNGPSubmitMessage: ");
        outBuf.append("PacketLength=".concat(String.valueOf(String.valueOf(this.getPacketLength()))));
        outBuf.append(",RequestID=".concat(String.valueOf(String.valueOf(this.getCommandID()))));
        outBuf.append(",Status=".concat(String.valueOf(String.valueOf(this.getStatus()))));
        outBuf.append(",SequenceID=".concat(String.valueOf(String.valueOf(this.getSeqID()))));
        if (this.strBuf == null) {
            this.strBuf = new StringBuffer(600);
            this.strBuf.append(",SPId=".concat(String.valueOf(this.getSPID()).trim()));
            this.strBuf.append(",subType=".concat(String.valueOf(this.getSubType()).trim()));
            this.strBuf.append(",NeedReport=".concat(String.valueOf(String.valueOf(this.getNeedReport())).trim()));
            this.strBuf.append(",Priority=".concat(String.valueOf(String.valueOf(this.getPriority())).trim()));
            this.strBuf.append(",ServiceID=".concat(String.valueOf(String.valueOf(this.getServiceID())).trim()));
            this.strBuf.append(",FeeType=".concat(String.valueOf(String.valueOf(this.getFeeType())).trim()));
            this.strBuf.append(",feeUserType=".concat(String.valueOf(String.valueOf(this.getFeeUserType())).trim()));
            this.strBuf.append(",FeeCode=".concat(String.valueOf(String.valueOf(this.getFeeCode())).trim()));
            this.strBuf.append(",msgFormat=".concat(String.valueOf(String.valueOf(this.getMsgFormat())).trim()));
            this.strBuf.append(",validTime=".concat(String.valueOf(String.valueOf(this.getValidTime())).trim()));
            this.strBuf.append(",atTime=".concat(String.valueOf(String.valueOf(this.getAtTime())).trim()));
            this.strBuf.append(",SrcTermID=".concat(String.valueOf(String.valueOf(this.getsrcTermId())).trim()));
            this.strBuf.append(",ChargeTermID=".concat(String.valueOf(String.valueOf(this.getchargeTermId())).trim()));
            this.strBuf.append(",DestTermIDCount=".concat(String.valueOf(String.valueOf(this.getdestTermIdCount())).trim()));

            for(int t = 0; t < this.getdestTermIdCount(); ++t) {
                this.strBuf.append(String.valueOf(String.valueOf((new StringBuffer(",DestTermID[")).append(t).append("]=").append(this.getdestTermId()[t].trim()))));
            }

            this.strBuf.append(",MsgLength=".concat(String.valueOf(String.valueOf(this.getMsgLength())).trim()));
            this.strBuf.append(",MsgContent=".concat(String.valueOf(String.valueOf(this.getMsgContent())).trim()));
        }

        outBuf.append(this.strBuf.toString());
        return outBuf.toString().trim();
    }
}
