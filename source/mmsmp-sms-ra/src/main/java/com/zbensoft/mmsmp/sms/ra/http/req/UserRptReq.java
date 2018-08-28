package com.zbensoft.mmsmp.sms.ra.http.req;

public class UserRptReq extends BaseReq {
    private String UserCondition;

    @Override
    public String toString() {
        return super.toString() + "<UserCondition>=" + "" + "<" + UserCondition + ">";
    }
}
