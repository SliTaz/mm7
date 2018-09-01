package com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.common;

import org.apache.log4j.Logger;

public abstract class SmsHandler {
    public SmsHandler UpHandler;
    public SmsHandler DownHandler;
    public Logger LogHandler;
    SmsHandler TraceHandler;

    public SmsHandler() {
    }

    public void sendLog(Object object) {
    }

    public void send(Object object) {
    }

    public int sendtoSMS(Object objSMS) {
        return 0;
    }

    public void receive(Object object) {
    }

    public void setUpHandler(SmsHandler uphandler) {
        this.UpHandler = uphandler;
    }

    public void setDownHandler(SmsHandler downhandler) {
        this.DownHandler = downhandler;
    }

    public void setLogHandler(Logger loghandler) {
        this.LogHandler = loghandler;
    }

    public void setTraceHandler(SmsHandler tracehandler) {
        this.TraceHandler = tracehandler;
    }
}

