package com.zbensoft.mmsmp.sms.ra.http.req;

public class DeliverReq extends BaseReq {
    private String TP_pid;
    private String TP_udhi;
    private String MessageCoding;
    private int MessageContent;

    @Override
    public String toString() {
        return super.toString() + "<TP_pid>=" + "" + "<" + TP_pid + ">" + "<TP_udhi>=" + "" + "<" + TP_udhi + ">" + "<MessageCoding>=" + "" + "<" + MessageCoding + ">" + "<MessageContent>=" + "" + "<" + MessageContent + ">";
    }

}
