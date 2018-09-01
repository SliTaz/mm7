package com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.cmppapi;

import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.common.TypeConvert;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CMPP_Submit extends CMPP_Command {
    int pk_Total;
    private int pk_Number;
    private int registered_Delivery;
    private int msg_Level;
    private String service_Id;
    private int fee_UserType;
    private String fee_Terminal_Id;
    private int fee_Terminal_Type;
    private int tp_Pid;
    private int tp_Udhi;
    private int msg_Fmt;
    private String msg_Src;
    private String fee_Type;
    private String fee_Code;
    private Date valid_Time;
    private Date at_Time;
    private String src_Terminal_Id;
    private String[] dest_Terminal_Id;
    private int dest_Terminal_Type;
    private String msg;
    private String LinkID;

    public CMPP_Submit() {
        byte[] buf = new byte[100];
        this.buf = ByteBuffer.wrap(buf);
    }

    public CMPP_Submit(ByteBuffer buf) {
        this.buf = buf;
    }

    public CMPP_Submit(CMPP_Submit submit, int seq) {
        byte[] pac = new byte[submit.getPacketLength()];
        ByteBuffer buf = ByteBuffer.wrap(pac);
        this.buf = buf;
        submit.buf.clear();
        this.buf.put(submit.buf);
        this.setSeqID(seq);
        this.setNeedReport(true);
    }

    public int getNeedReport() {
        return this.buf.get(22);
    }

    public void setNeedReport(boolean report) {
        if (report) {
            this.buf.put(22, (byte)1);
        } else {
            this.buf.put(22, (byte)0);
        }

    }

    public CMPP_Submit(int pk_Total, int pk_Number, int registered_Delivery, int msg_Level, String service_Id, int fee_UserType, String fee_Terminal_Id, int fee_Terminal_Type, int tp_Pid, int tp_Udhi, int msg_Fmt, String msg_Src, String fee_Type, String fee_Code, Date valid_Time, Date at_Time, String src_Terminal_Id, String[] dest_Terminal_Id, int dest_Terminal_Type, String msg, String LinkID) {
        this.pk_Total = pk_Total;
        this.pk_Number = pk_Number;
        this.registered_Delivery = registered_Delivery;
        this.msg_Level = msg_Level;
        this.service_Id = service_Id;
        this.fee_UserType = fee_UserType;
        this.fee_Terminal_Id = fee_Terminal_Id;
        this.fee_Terminal_Type = fee_Terminal_Type;
        this.tp_Pid = tp_Pid;
        this.tp_Udhi = tp_Udhi;
        this.msg_Fmt = msg_Fmt;
        this.msg_Src = msg_Src;
        this.fee_Type = fee_Type;
        this.fee_Code = fee_Code;
        this.valid_Time = valid_Time;
        this.at_Time = at_Time;
        this.src_Terminal_Id = src_Terminal_Id;
        this.dest_Terminal_Id = dest_Terminal_Id;
        this.dest_Terminal_Type = dest_Terminal_Type;
        this.msg = msg;
        this.LinkID = LinkID;

        try {
            byte[] messageBytes;
            if (msg_Fmt == 8) {
                messageBytes = msg.getBytes("UTF-16BE");
            } else if (msg_Fmt == 15) {
                messageBytes = msg.getBytes("GBK");
            } else {
                messageBytes = msg.getBytes("ISO8859-1");
            }

            this.init(pk_Total, pk_Number, registered_Delivery, msg_Level, service_Id, fee_UserType, fee_Terminal_Id, fee_Terminal_Type, tp_Pid, tp_Udhi, msg_Fmt, msg_Src, fee_Type, fee_Code, valid_Time, at_Time, src_Terminal_Id, dest_Terminal_Id, dest_Terminal_Type, messageBytes, LinkID);
        } catch (UnsupportedEncodingException var23) {
            var23.printStackTrace();
        }

    }

    public CMPP_Submit(int pk_Total, int pk_Number, int registered_Delivery, int msg_Level, String service_Id, int fee_UserType, String fee_Terminal_Id, int fee_Terminal_Type, int tp_Pid, int tp_Udhi, int msg_Fmt, String msg_Src, String fee_Type, String fee_Code, Date valid_Time, Date at_Time, String src_Terminal_Id, String[] dest_Terminal_Id, int dest_Terminal_Type, byte[] msg_Content, String LinkID) {
        this.init(dest_Terminal_Type, dest_Terminal_Type, dest_Terminal_Type, dest_Terminal_Type, LinkID, dest_Terminal_Type, LinkID, dest_Terminal_Type, dest_Terminal_Type, dest_Terminal_Type, dest_Terminal_Type, LinkID, LinkID, LinkID, at_Time, at_Time, LinkID, dest_Terminal_Id, dest_Terminal_Type, msg_Content, LinkID);
    }

    public void init(int pk_Total, int pk_Number, int registered_Delivery, int msg_Level, String service_Id, int fee_UserType, String fee_Terminal_Id, int fee_Terminal_Type, int tp_Pid, int tp_Udhi, int msg_Fmt, String msg_Src, String fee_Type, String fee_Code, Date valid_Time, Date at_Time, String src_Terminal_Id, String[] dest_Terminal_Id, int dest_Terminal_Type, byte[] msg_Content, String LinkID) {
        int len = 163 + 32 * dest_Terminal_Id.length + msg_Content.length;
        byte[] buf = new byte[len];
        TypeConvert.int2byte(len, buf, 0);
        TypeConvert.int2byte(4, buf, 4);
        buf[20] = (byte)pk_Total;
        buf[21] = (byte)pk_Number;
        buf[22] = (byte)registered_Delivery;
        buf[23] = (byte)msg_Level;
        System.arraycopy(service_Id.getBytes(), 0, buf, 24, service_Id.length());
        buf[34] = (byte)fee_UserType;
        System.arraycopy(fee_Terminal_Id.getBytes(), 0, buf, 35, fee_Terminal_Id.length());
        buf[67] = (byte)fee_Terminal_Type;
        buf[68] = (byte)tp_Pid;
        buf[69] = (byte)tp_Udhi;
        buf[70] = (byte)msg_Fmt;
        System.arraycopy(msg_Src.getBytes(), 0, buf, 71, msg_Src.length());
        System.arraycopy(fee_Type.getBytes(), 0, buf, 77, fee_Type.length());
        System.arraycopy(fee_Code.getBytes(), 0, buf, 79, fee_Code.length());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMddHHmmss");
        String tmpTime;
        if (valid_Time != null) {
            tmpTime = String.valueOf(String.valueOf(dateFormat.format(valid_Time))).concat("032+");
            System.arraycopy(tmpTime.getBytes(), 0, buf, 85, 16);
        }

        if (at_Time != null) {
            tmpTime = String.valueOf(String.valueOf(dateFormat.format(at_Time))).concat("032+");
            System.arraycopy(tmpTime.getBytes(), 0, buf, 102, 16);
        }

        System.arraycopy(src_Terminal_Id.getBytes(), 0, buf, 119, src_Terminal_Id.length());
        buf[140] = (byte)dest_Terminal_Id.length;
        int i;
        for(i = 0; i < dest_Terminal_Id.length; ++i) {
            System.arraycopy(dest_Terminal_Id[i].getBytes(), 0, buf, 141 + i * 32, dest_Terminal_Id[i].length());
        }

        int loc = 141 + i * 32;
        buf[loc] = (byte)dest_Terminal_Type;
        ++loc;
        buf[loc] = (byte)msg_Content.length;
        ++loc;
        System.arraycopy(msg_Content, 0, buf, loc, msg_Content.length);
        loc += msg_Content.length;
        System.arraycopy(LinkID.getBytes(), 0, buf, loc, LinkID.length());
        this.buf = ByteBuffer.wrap(buf);
        this.setSeqID(ProduceSeq.getSeq());
    }

    public Date getAt_Time() {
        return this.at_Time;
    }

    public String[] getDest_Terminal_Id() {
        return this.dest_Terminal_Id;
    }

    public int getDest_Terminal_Type() {
        return this.dest_Terminal_Type;
    }

    public String getFee_Code() {
        return this.fee_Code;
    }

    public String getFee_Terminal_Id() {
        return this.fee_Terminal_Id;
    }

    public int getFee_Terminal_Type() {
        return this.fee_Terminal_Type;
    }

    public String getFee_Type() {
        return this.fee_Type;
    }

    public int getFee_UserType() {
        return this.fee_UserType;
    }

    public String getLinkID() {
        return this.LinkID;
    }

    public int getMsg_Fmt() {
        return this.msg_Fmt;
    }

    public int getMsg_Level() {
        return this.msg_Level;
    }

    public String getMsg_Src() {
        return this.msg_Src;
    }

    public int getPk_Number() {
        return this.pk_Number;
    }

    public int getPk_Total() {
        return this.pk_Total;
    }

    public int getRegistered_Delivery() {
        return this.registered_Delivery;
    }

    public String getService_Id() {
        return this.service_Id;
    }

    public String getSrc_Terminal_Id() {
        return this.src_Terminal_Id;
    }

    public int getTp_Pid() {
        return this.tp_Pid;
    }

    public int getTp_Udhi() {
        return this.tp_Udhi;
    }

    public Date getValid_Time() {
        return this.valid_Time;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("CMPP_Submit:Seq=");
        buffer.append(this.getSeqID());
        buffer.append(" destTermId={");

        for(int i = 0; i < this.dest_Terminal_Id.length; ++i) {
            buffer.append(this.dest_Terminal_Id[i] + ",");
        }

        buffer.append("}");
        buffer.append(" MSG=");
        buffer.append(this.getMsg());
        return buffer.toString();
    }

    private String getMsg() {
        return this.msg;
    }
}
