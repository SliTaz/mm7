package com.zbensoft.mmsmp.sms.ra.sgip.pkg.body;
import com.zbensoft.mmsmp.sms.ra.sgip.pkg.SgipBody;
import com.zbensoft.mmsmp.sms.ra.sgip.pkg.SgipField;

public class BindBody extends SgipBody {

    private int loginType;
    private String loginName;
    private String loginPassword;
    private String reserve;

    public BindBody() {
        super(items);
    }

    private static final SgipField[] items = new SgipField[]{
            new SgipField("loginType", 1, '\0', SgipField.FillSide.RIGHT),
            new SgipField("loginName", 16, '\0', SgipField.FillSide.RIGHT),
            new SgipField("loginPassword", 16, '\0', SgipField.FillSide.RIGHT),
            new SgipField("reserve", 8, '\0', SgipField.FillSide.RIGHT),
    };


    public int getLoginType() {
        return loginType;
    }

    public void setLoginType(int loginType) {
        this.loginType = loginType;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    public String getReserve() {
        return reserve;
    }

    public void setReserve(String reserve) {
        this.reserve = reserve;
    }
}
