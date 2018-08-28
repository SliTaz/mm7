package com.zbensoft.mmsmp.sms.ra.sgip.pkg.body;

import com.zbensoft.mmsmp.sms.ra.sgip.pkg.SgipBody;
import com.zbensoft.mmsmp.sms.ra.sgip.pkg.SgipField;

public class AddTeleSegBody extends SgipBody {

    private String smgId;
    private String teleSeg;
    private String teleType;
    private String areaCode;
    private String reserve;

    private static final SgipField[] items = new SgipField[]{
            new SgipField("smgId", 6, '\0', SgipField.FillSide.RIGHT),
            new SgipField("teleSeg", 7, '\0', SgipField.FillSide.RIGHT),
            new SgipField("teleType", 1, '\0', SgipField.FillSide.RIGHT),
            new SgipField("areaCode", 4, '\0', SgipField.FillSide.RIGHT),
            new SgipField("reserve", 8, '\0', SgipField.FillSide.RIGHT),
    };

    public AddTeleSegBody() {
        super(items);
    }

    public String getSmgId() {
        return smgId;
    }

    public void setSmgId(String smgId) {
        this.smgId = smgId;
    }

    public String getTeleSeg() {
        return teleSeg;
    }

    public void setTeleSeg(String teleSeg) {
        this.teleSeg = teleSeg;
    }

    public String getTeleType() {
        return teleType;
    }

    public void setTeleType(String teleType) {
        this.teleType = teleType;
    }


    public String getReserve() {
        return reserve;
    }

    public void setReserve(String reserve) {
        this.reserve = reserve;
    }


    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }
}
