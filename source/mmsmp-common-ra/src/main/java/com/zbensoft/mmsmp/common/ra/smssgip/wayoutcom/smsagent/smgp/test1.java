package com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.smsagent.smgp;

import java.util.HashMap;

public class test1 {
    test1() {
    }

    public int hashCode() {
        return 1;
    }

    public boolean equals(Object o) {
        return true;
    }

    public static void main(String[] arg) {
        HashMap hm = new HashMap();
        hm.put(new test1(), new test2());
        hm.remove(new test2());
    }
}