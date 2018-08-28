package com.zbensoft.mmsmp.sms.ra.http.resp;

public class BaseResp {
    private int CommandId;
    private String SequenceNumber;
    private String Result;
    private String Reserve;

    @Override
    public String toString() {
        return "<CommandId>=" + "" + "<" + CommandId + ">" + "<SequenceNumber>=" + "" + "<" + SequenceNumber + ">" + "<Result>=" + "" + "<" + Reserve + ">" + "<Result>=" + "" + "<" + Reserve + ">";
    }
}
