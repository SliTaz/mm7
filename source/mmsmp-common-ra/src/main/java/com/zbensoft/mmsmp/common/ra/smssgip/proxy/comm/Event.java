package com.zbensoft.mmsmp.common.ra.smssgip.proxy.comm;

public class Event {
    public static final int CREATED = 1;
    public static final int CHILD_CREATED = 2;
    public static final int DELETED = 4;
    public static final int MESSAGE_SEND_SUCCESS = 8;
    public static final int MESSAGE_SEND_FAIL = 16;
    public static final int MESSAGE_DISPATCH_SUCCESS = 32;
    public static final int MESSAGE_DISPATCH_FAIL = 64;
    int type;
    Object source;
    Object data;

    public Event(int type, Object source, Object data) {
        this.type = type;
        this.source = source;
        this.data = data;
    }

    public String toString() {
        return "PEvent:source=" + this.source + ",type=" + this.type + ",data=" + this.data;
    }

    public Object getData() {
        return this.data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Object getSource() {
        return this.source;
    }

    public void setSource(Object source) {
        this.source = source;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }
}

