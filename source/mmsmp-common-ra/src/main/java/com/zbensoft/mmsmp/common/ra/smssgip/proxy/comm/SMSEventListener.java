package com.zbensoft.mmsmp.common.ra.smssgip.proxy.comm;

public class SMSEventListener implements EventListener {
    public SMSEventListener() {
    }

    public void handle(Event e) {
        switch(e.getType()) {
            case 1:
                this.created();
                break;
            case 2:
                this.childCreated((SMSLayer)e.getData());
                break;
            case 8:
                this.messageSendSuccess(e.getData());
                break;
            case 16:
                this.messageSendError(e.getData());
                break;
            case 32:
                this.messageDispatchSuccess(e.getData());
                break;
            case 64:
                this.messageDispatchFail(e.getData());
            case 4:
                this.deleted();
        }

    }

    public void childCreated(SMSLayer layer) {
    }

    public void messageSendError(Object pmessage) {
    }

    public void messageSendSuccess(Object pmessage) {
    }

    public void messageDispatchFail(Object pmessage) {
    }

    public void messageDispatchSuccess(Object pmessage) {
    }

    public void created() {
    }

    public void deleted() {
    }
}
