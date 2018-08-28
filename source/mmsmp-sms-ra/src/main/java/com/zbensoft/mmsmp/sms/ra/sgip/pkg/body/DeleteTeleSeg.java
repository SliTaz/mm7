package com.zbensoft.mmsmp.sms.ra.sgip.pkg.body;

import com.zbensoft.mmsmp.sms.ra.sgip.pkg.SgipBody;
import com.zbensoft.mmsmp.sms.ra.sgip.pkg.SgipField;

public class DeleteTeleSeg extends SgipBody {
    public DeleteTeleSeg() {
        super(items);
    }

    private String smgId;
    private String teleSeg;
    private String reserve;

    private static final SgipField[] items = new SgipField[]{
            new SgipField("smgId", 6, '\0', SgipField.FillSide.RIGHT),
            new SgipField("teleSeg", 7, '\0', SgipField.FillSide.RIGHT),
            new SgipField("reserve", 8, '\0', SgipField.FillSide.RIGHT)
    };

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

    public String getReserve() {
        return reserve;
    }

    public void setReserve(String reserve) {
        this.reserve = reserve;
    }
}
