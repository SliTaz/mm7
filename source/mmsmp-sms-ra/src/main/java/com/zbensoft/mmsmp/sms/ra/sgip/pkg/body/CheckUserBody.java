package com.zbensoft.mmsmp.sms.ra.sgip.pkg.body;

import com.zbensoft.mmsmp.sms.ra.sgip.pkg.SgipBody;
import com.zbensoft.mmsmp.sms.ra.sgip.pkg.SgipField;

public class CheckUserBody  extends SgipBody {
    public CheckUserBody() {
        super(items);
    }

    private String userName;
    private String passowrd;
    private String userNumber;
    private String reserve;

    private static final SgipField[] items = new SgipField[]{
            new SgipField("userName", 16, '\0', SgipField.FillSide.RIGHT),
            new SgipField("passowrd", 16, '\0', SgipField.FillSide.RIGHT),
            new SgipField("userNumber", 21, '\0', SgipField.FillSide.RIGHT),
            new SgipField("reserve", 8, '\0', SgipField.FillSide.RIGHT),
    };


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassowrd() {
        return passowrd;
    }

    public void setPassowrd(String passowrd) {
        this.passowrd = passowrd;
    }

    public String getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }

    public String getReserve() {
        return reserve;
    }

    public void setReserve(String reserve) {
        this.reserve = reserve;
    }
}
