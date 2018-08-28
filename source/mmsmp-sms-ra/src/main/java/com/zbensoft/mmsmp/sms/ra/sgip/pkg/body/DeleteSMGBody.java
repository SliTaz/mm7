package com.zbensoft.mmsmp.sms.ra.sgip.pkg.body;

import com.zbensoft.mmsmp.sms.ra.sgip.pkg.SgipBody;
import com.zbensoft.mmsmp.sms.ra.sgip.pkg.SgipField;

public class DeleteSMGBody extends SgipBody {

    public DeleteSMGBody() {
        super(items);
    }

    private String smgId;
    private String reserve;

    private static final SgipField[] items = new SgipField[]{
            new SgipField("smgId", 16, '\0', SgipField.FillSide.RIGHT),
            new SgipField("reserve", 16, '\0', SgipField.FillSide.RIGHT)
    };


    public String getSmgId() {
        return smgId;
    }

    public void setSmgId(String smgId) {
        this.smgId = smgId;
    }

    public String getReserve() {
        return reserve;
    }

    public void setReserve(String reserve) {
        this.reserve = reserve;
    }
}
