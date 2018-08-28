package com.zbensoft.mmsmp.sms.ra.http.req;

public class SubmitReq extends BaseReq {
    private String SPNumber;
    private String ChargeNumber;
    private String CorpId;
    private String ServiceType;
    private String FeeType;
    private String FeeValue;
    private String GivenValue;
    private String AgentFlag;
    private String MorelatetoMTFlag;
    private String Priority;
    private String ExpireTime;
    private String ScheduleTime;
    private String ReportFlag;
    private String MessageType;
    private String TP_pid;
    private String TP_udhi;
    private String MessageCoding;
    private String MessageContent;

    @Override
    public String toString() {
        return super.toString() + "<SPNumber>=" + "" + "<" + SPNumber + ">" + "<ChargeNumber>=" + "" + "<" + ChargeNumber + ">" + "<CorpId>=" + "" + "<" + CorpId + ">" + "<ServiceType>=" + "" + "<" + ServiceType + ">" + "<FeeType>=" + "" + "<" + FeeType + ">" + "<FeeValue>=" + "" + "<" + FeeValue + ">"
                + "<GivenValue>=" + "" + "<" + GivenValue + ">"  + "<AgentFlag>=" + "" + "<" + AgentFlag + ">" + "<MorelatetoMTFlag>=" + "" + "<" + MorelatetoMTFlag + ">" + "<Priority>=" + "" + "<" + Priority + ">" + "<ExpireTime>=" + "" + "<" + ExpireTime + ">" + "<ScheduleTime>=" + "" + "<" + ScheduleTime + ">"
                + "<ReportFlag>=" + "" + "<" + ReportFlag + ">"  + "<MessageType>=" + "" + "<" + MessageType + ">" + "<TP_pid>=" + "" + "<" + TP_pid + ">" + "<TP_udhi>=" + "" + "<" + TP_udhi + ">" + "<MessageCoding>=" + "" + "<" + MessageCoding + ">" + "<MessageContent>=" + "" + "<" + MessageContent + ">";
    }
}
