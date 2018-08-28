package com.zbensoft.mmsmp.sms.ra.sgip.pkg.body;

import com.zbensoft.mmsmp.sms.ra.sgip.pkg.SgipBody;
import com.zbensoft.mmsmp.sms.ra.sgip.pkg.SgipField;

public class ModifySMGBody extends SgipBody {
    public ModifySMGBody() {
        super(items);
    }

    private String oldSMGId;
    private String newSMGId;
    private String smgIp;
    private String loginName;
    private String loginPassowrd;
    private String reserve;
    private static final SgipField[] items = new SgipField[]{
            new SgipField("oldSMGId", 6, '\0', SgipField.FillSide.RIGHT),
            new SgipField("newSMGId", 6, '\0', SgipField.FillSide.RIGHT),
            new SgipField("smgIp", 6, '\0', SgipField.FillSide.RIGHT),
            new SgipField("loginName", 16, '\0', SgipField.FillSide.RIGHT),
            new SgipField("loginPassowrd", 16, '\0', SgipField.FillSide.RIGHT),
            new SgipField("reserve", 8, '\0', SgipField.FillSide.RIGHT)
    };

    public String getOldSMGId() {
        return oldSMGId;
    }

    public void setOldSMGId(String oldSMGId) {
        this.oldSMGId = oldSMGId;
    }

    public String getNewSMGId() {
        return newSMGId;
    }

    public void setNewSMGId(String newSMGId) {
        this.newSMGId = newSMGId;
    }

    public String getSmgIp() {
        return smgIp;
    }

    public void setSmgIp(String smgIp) {
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
