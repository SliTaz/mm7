package com.zbensoft.mmsmp.sms.ra.sgip.pkg.body;

import com.zbensoft.mmsmp.sms.ra.sgip.pkg.SgipBody;
import com.zbensoft.mmsmp.sms.ra.sgip.pkg.SgipField;

public class DeleteSPBody extends SgipBody {
    public DeleteSPBody() {
        super(items);
    }

    private String smgId;
    private String spNumber;
    private String serviceTag;
    private String reserve;

    private static final SgipField[] items = new SgipField[]{
            new SgipField("smgId", 6, '\0', SgipField.FillSide.RIGHT),
            new SgipField("spNumber", 21, '\0', SgipField.FillSide.RIGHT),
            new SgipField("serviceTag", 10, '\0', SgipField.FillSide.RIGHT),
            new SgipField("reserve", 8, '\0', SgipField.FillSide.RIGHT)
    };


    public String getSmgId() {
        return smgId;
    }

    public void setSmgId(String smgId) {
        this.smgId = smgId;
    }

    public String getSpNumber() {
        return spNumber;
    }

    public void setSpNumber(String spNumber) {
        this.spNumber = spNumber;
    }

    public String getServiceTag() {
        return serviceTag;
    }

    public void setServiceTag(String serviceTag) {
        this.serviceTag = serviceTag;
    }

    public String getReserve() {
        return reserve;
    }

    public void setReserve(String reserve) {
        this.reserve = reserve;
    }
}
