package com.zbensoft.mmsmp.sms.ra.sgip.pkg;

public class SgipPackage extends AbstractSgipPackage {

    public SgipPackage() {
        super("t1", "t2");
    }

    private SgipHead t1;

    private SgipBody t2;

    public SgipHead getT1() {
        return t1;
    }

    public void setT1(SgipHead t1) {
        this.t1 = t1;
    }

    public SgipBody getT2() {
        return t2;
    }

    public void setT2(SgipBody t2) {
        this.t2 = t2;
    }

}
