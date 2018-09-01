package com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.cngpapi;

import com.zbensoft.mmsmp.common.ra.smssgip.proxy.util.Debug;

import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ZX_CNGP_Submit extends CNGP_Submit {
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

    public ZX_CNGP_Submit(String SPId, int subType, int needReport, int priority, String serviceId, String feeType, int feeUserType, String feeCode, int msgFormat, Date validTime, Date atTime, String srcTermId, String chargeTermId, String[] destTermId, String content, int protocolValue) {
        this.init(SPId, subType, needReport, priority, serviceId, feeType, feeUserType, feeCode, msgFormat, validTime, atTime, srcTermId, chargeTermId, destTermId, content, protocolValue, "");
    }

    public ZX_CNGP_Submit(String SPId, int subType, int needReport, int priority, String serviceId, String feeType, int feeUserType, String feeCode, int msgFormat, Date validTime, Date atTime, String srcTermId, String chargeTermId, String[] destTermId, String content, int protocolValue, String linkId) {
        this.init(SPId, subType, needReport, priority, serviceId, feeType, feeUserType, feeCode, msgFormat, validTime, atTime, srcTermId, chargeTermId, destTermId, content, protocolValue, linkId);
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
        ZX_CNGP_Submit submit = new ZX_CNGP_Submit("77777", 1, 1, 0, "77777", "01", 0, "0", 15, (Date)null, (Date)null, "77777", "188010301115", userPhones, "hello", 0);
        Debug.dump(submit.getBuf().array());
        Debug.dump(submit.toString());
    }
}
