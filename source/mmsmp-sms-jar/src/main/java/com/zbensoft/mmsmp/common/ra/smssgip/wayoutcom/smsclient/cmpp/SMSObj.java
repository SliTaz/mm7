package com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.smsclient.cmpp;

import java.sql.Timestamp;

public class SMSObj {
    private String from;
    private String to;
    private String content;
    private String pushID;
    private String opID;
    private String pushFlag;
    private String errorType;
    private String pushType;
    private Timestamp pushTime;

    public SMSObj(String from, String to, String content) {
        this.from = from;
        this.to = to;
        this.content = content;
    }

    public SMSObj(String from, String to, String content, String pushID, String opID, String pushFlag, String errorType, String pushType, Timestamp pushTime) {
        this.from = from;
        this.to = to;
        this.content = content;
        this.pushID = pushID;
        this.opID = opID;
        this.pushFlag = pushFlag;
        this.errorType = errorType;
        this.pushType = pushType;
        this.pushTime = pushTime;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getErrorType() {
        return this.errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public String getFrom() {
        return this.from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getOpID() {
        return this.opID;
    }

    public void setOpID(String opID) {
        this.opID = opID;
    }

    public String getPushFlag() {
        return this.pushFlag;
    }

    public void setPushFlag(String pushFlag) {
        this.pushFlag = pushFlag;
    }

    public String getPushID() {
        return this.pushID;
    }

    public void setPushID(String pushID) {
        this.pushID = pushID;
    }

    public Timestamp getPushTime() {
        return this.pushTime;
    }

    public void setPushTime(Timestamp pushTime) {
        this.pushTime = pushTime;
    }

    public String getPushType() {
        return this.pushType;
    }

    public void setPushType(String pushType) {
        this.pushType = pushType;
    }

    public String getTo() {
        return this.to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
