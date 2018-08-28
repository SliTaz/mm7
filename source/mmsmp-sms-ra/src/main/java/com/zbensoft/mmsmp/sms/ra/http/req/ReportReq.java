package com.zbensoft.mmsmp.sms.ra.http.req;

public class ReportReq extends BaseReq {
    private String SubmitSequenceNumber;
    private String ReportType;
    private String State;
    private String ErrorCode;

    @Override
    public String toString() {
        return super.toString() + "<SubmitSequenceNumber>=" + "" + "<" + SubmitSequenceNumber + ">" + "<ReportType>=" + "" + "<" + ReportType + ">" + "<State>=" + "" + "<" + State + ">" + "<ErrorCode>=" + "" + "<" + ErrorCode + ">";
    }
}
