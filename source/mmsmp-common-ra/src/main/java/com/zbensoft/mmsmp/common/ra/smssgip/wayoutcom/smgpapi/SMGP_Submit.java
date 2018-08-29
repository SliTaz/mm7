package com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.smgpapi;

import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.common.TypeConvert;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SMGP_Submit extends SMGP_Command {
    private String priority;
    private String serviceID;
    private String feeType;
    private String feeCode;
    private String fixedFee;
    private String msgFormat;
    private String validTime;
    private String atTime;
    private String srcTermID;
    private String chargeTermID;
    private String destTermIDCount;
    private String destTermID;
    private String msgLength;
    private String msgContent;

    public SMGP_Submit(ByteBuffer buf) {
        this.buf = buf;
    }

    public SMGP_Submit(SMGP_Submit submit, int seq) {
        byte[] pac = new byte[submit.getPacketLength()];
        ByteBuffer buf = ByteBuffer.wrap(pac);
        this.buf = buf;
        submit.buf.clear();
        this.buf.put(submit.buf);
        this.setSeqID(seq);
        this.setNeedReport(true);
    }

    public int getNeedReport() {
        return this.buf.get(13);
    }

    public void setNeedReport(boolean report) {
        if (report) {
            this.buf.put(13, (byte)1);
        } else {
            this.buf.put(13, (byte)0);
        }

    }

    public SMGP_Submit(int msgType, int needReport, int priority, String serviceId, String feeType, String feeCode, String fixedFee, int msgFormat, Date validTime, Date atTime, String srcTermId, String chargeTermId, String[] destTermId, String msgContent, String reserve) {
        byte[] msgBytes = (byte[])null;

        try {
            if (msgFormat == 8) {
                msgBytes = msgContent.getBytes("UTF-16BE");
            } else if (msgFormat == 15) {
                msgBytes = msgContent.getBytes("GBK");
            } else {
                msgBytes = msgContent.getBytes();
            }
        } catch (UnsupportedEncodingException var23) {
            msgBytes = msgContent.getBytes();
            var23.printStackTrace();
        }

        int len = 126 + 21 * destTermId.length + msgBytes.length;
        byte[] buf = new byte[len];
        TypeConvert.int2byte(len, buf, 0);
        TypeConvert.int2byte(2, buf, 4);
        int seq = ProduceSeq.getSeq();
        TypeConvert.int2byte(seq, buf, 8);
        buf[12] = (byte)msgType;
        buf[13] = (byte)needReport;
        buf[14] = (byte)priority;
        System.arraycopy(serviceId.getBytes(), 0, buf, 15, serviceId.length());
        System.arraycopy(feeType.getBytes(), 0, buf, 25, feeType.length());
        System.arraycopy(feeCode.getBytes(), 0, buf, 27, feeCode.length());
        System.arraycopy(fixedFee.getBytes(), 0, buf, 33, fixedFee.length());
        buf[39] = (byte)msgFormat;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMddHHmmss");
        String tmpTime;
        if (validTime != null) {
            tmpTime = String.valueOf(String.valueOf(dateFormat.format(validTime))).concat("032+");
            System.arraycopy(tmpTime.getBytes(), 0, buf, 40, 16);
        }

        if (atTime != null) {
            tmpTime = String.valueOf(String.valueOf(dateFormat.format(atTime))).concat("032+");
            System.arraycopy(tmpTime.getBytes(), 0, buf, 57, 16);
        }

        System.arraycopy(srcTermId.getBytes(), 0, buf, 74, srcTermId.length());
        System.arraycopy(chargeTermId.getBytes(), 0, buf, 95, chargeTermId.length());
        buf[116] = (byte)destTermId.length;

        int i;
        for(i = 0; i < destTermId.length; ++i) {
            System.arraycopy(destTermId[i].getBytes(), 0, buf, 117 + i * 21, destTermId[i].length());
        }

        int loc = 117 + i * 21;
        buf[loc] = (byte)msgBytes.length;
        System.arraycopy(msgBytes, 0, buf, loc + 1, msgBytes.length);
        loc = loc + 1 + msgBytes.length;
        if (reserve != null) {
            System.arraycopy(reserve.getBytes(), 0, buf, loc, reserve.length());
        }

        this.buf = ByteBuffer.wrap(buf);
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("SMGP_Submit:Seq=");
        sb.append(this.getSeqID());
        sb.append(",SrcTermId=");
        sb.append(this.getSrcTermID());
        sb.append(",msg=");
        sb.append(this.getMsgContent());
        return "SMGP_Submit:Seq=" + this.getSeqID();
    }

    public String getAtTime() {
        return this.atTime;
    }

    public String getChargeTermID() {
        byte[] dst = new byte[21];
        this.buf.position(95);
        this.buf.get(dst);
        return new String(dst);
    }

    public String getDestTermID() {
        return this.destTermID;
    }

    public int getDestTermIDCount() {
        this.buf.position(116);
        return this.buf.get();
    }

    public String getFeeCode() {
        return this.feeCode;
    }

    public String getFeeType() {
        return this.feeType;
    }

    public String getFixedFee() {
        return this.fixedFee;
    }

    public String getMsgContent() {
        this.buf.position(118 + 21 * this.getDestTermIDCount());
        byte[] dst = new byte[this.getMsgLength()];
        this.buf.get(dst);

        try {
            if (this.getMsgFormat() == 15) {
                return new String(dst, "GBK");
            } else {
                return this.getMsgFormat() == 8 ? new String(dst, "UCS2") : new String(dst, "iso-8859-1");
            }
        } catch (Exception var3) {
            var3.printStackTrace();
            return new String(dst);
        }
    }

    public byte getMsgFormat() {
        this.buf.position(39);
        return this.buf.get();
    }

    public int getMsgLength() {
        this.buf.position(117 + 21 * this.getDestTermIDCount());
        return this.buf.get();
    }

    public int getMsgType() {
        this.buf.position(12);
        return this.buf.get();
    }

    public String getPriority() {
        this.buf.position(14);
        return this.priority;
    }

    public String getServiceID() {
        return this.serviceID;
    }

    public String getSrcTermID() {
        byte[] dst = new byte[21];
        this.buf.position(74);
        this.buf.get(dst);
        return new String(dst);
    }

    public String getValidTime() {
        return this.validTime;
    }
}
