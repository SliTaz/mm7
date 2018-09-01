package com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.sgipapi;

public class SGIP_UserReport extends SGIP_Command {
    String SpNumber;
    String UserNumber;
    int UserCondition;

    public SGIP_UserReport(SGIP_Head sgiphead) {
        super(sgiphead);
    }

    public SGIP_UserReport(SGIP_Command sgip_command) {
        super(sgip_command);
    }

    public SGIP_UserReport() {
    }

    public int readbody() {
        this.byteBody.clear();
        this.SpNumber = new String(this.byteBody.array(), 0, 21);
        this.UserNumber = new String(this.byteBody.array(), 21, 21);
        this.UserCondition = this.byteBody.get(42);
        return 0;
    }

    public String getSPNumber() {
        return this.SpNumber;
    }

    public String getUserNumber() {
        return this.UserNumber;
    }

    public int getUserCondition() {
        return this.UserCondition;
    }
}

