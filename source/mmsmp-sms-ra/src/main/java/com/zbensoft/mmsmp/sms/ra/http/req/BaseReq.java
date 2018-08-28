package com.zbensoft.mmsmp.sms.ra.http.req;

public class BaseReq {
    private int CommandId;
    private int SequenceNumber;
    private String Reserve;
    private String UserNumber;

    @Override
    public String toString() {
        return "<CommandId>=" + "" + "<" + CommandId + ">" +
                "<SequenceNumber>=" + "" + "<" + SequenceNumber + ">" +
                "<Reserve>=" + "" + "<" + Reserve + ">" +
                "<UserNumber>=" + "" + "<" + UserNumber + ">";

    }
}
