package com.zbensoft.mmsmp.sms.ra.sgip.pkg.body;

import com.zbensoft.mmsmp.sms.ra.sgip.pkg.SgipBody;
import com.zbensoft.mmsmp.sms.ra.sgip.pkg.SgipField;

public class AddSMGBody extends SgipBody {

    private String smgId;
    private int smgIp;
    private String loginName;
    private String loginPassowrd;
    private String reserve;


    public AddSMGBody() {
        super(items);
    }

    private static final SgipField[] items = new SgipField[]{
            new SgipField("smgId", 6, '\0', SgipField.FillSide.RIGHT),
            new SgipField("smgIp", 4, '\0', SgipField.FillSide.RIGHT),
            new SgipField("loginName", 16, '\0', SgipField.FillSide.RIGHT),
            new SgipField("loginPassowrd", 16, '\0', SgipField.FillSide.RIGHT),
            new SgipField("reserve", 8, '\0', SgipField.FillSide.RIGHT),
    };


    public String getSmgId() {
        return smgId;
    }

    public void setSmgId(String smgId) {
        this.smgId = smgId;
    }

    public int getSmgIp() {
        return smgIp;
    }

    public void setSmgIp(int smgIp) {
        this.smgIp = smgIp;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getLoginPassowrd() {
        return loginPassowrd;
    }

    public void setLoginPassowrd(String loginPassowrd) {
        this.loginPassowrd = loginPassowrd;
    }

    public String getReserve() {
        return reserve;
    }

    public void setReserve(String reserve) {
        this.reserve = reserve;
    }
}
