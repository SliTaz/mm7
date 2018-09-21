package com.zbensoft.mmsmp.ftp.util;

import com.zbensoft.mmsmp.ftp.sysc.spftp.req.SpAccessReq;
import com.zbensoft.mmsmp.ftp.sysc.spftp.req.SpInfoReq;
import org.junit.Test;

import java.io.IOException;

public class HttpHelperTest {
    @Test
    public void testSpDao() throws IOException, InterruptedException {


        //  testInsert();
//
//        Thread.sleep(5 * 1000);
        // testUpdate();
//        Thread.sleep(5 * 1000);
        HttpHelper.deleteSpInfo("zbensoft-sp-update-test");

    }

    private void testUpdate() throws IOException {
        SpInfoReq spInfoReq = new SpInfoReq();
        spInfoReq.setSpInfoId("zbensoft-sp-update-test");
        spInfoReq.setProvince("bb");

        SpAccessReq aa = new SpAccessReq();
        aa.setSpInfoId(spInfoReq.getSpInfoId());
        aa.setOrderKey("bb");
        spInfoReq.setSpAccess(aa);
        HttpHelper.updateSp(spInfoReq, 2);
    }

    private void testInsert() throws IOException {
        //insert
        SpInfoReq spInfoReq = new SpInfoReq();
        spInfoReq.setSpInfoId("zbensoft-sp-update-test");
        spInfoReq.setProvince("aaa");

        SpAccessReq aa = new SpAccessReq();
        aa.setSpInfoId(spInfoReq.getSpInfoId());
        aa.setOrderKey("aaa");
        spInfoReq.setSpAccess(aa);
        HttpHelper.updateSp(spInfoReq, 1);
    }
}
