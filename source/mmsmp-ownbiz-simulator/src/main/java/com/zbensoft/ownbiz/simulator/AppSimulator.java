package com.zbensoft.ownbiz.simulator;

import com.alibaba.fastjson.JSONObject;

import java.io.IOException;

public class AppSimulator {
    public static void main(String[] args) throws IOException, InterruptedException {

        String accountId = "12345";
        String mobile = "13312345678";
        String cooperKey = "45678";
        String productId = "111";
        String instruction = "12345";
        String messageId = "";
        String validateCode = "";

        Distributor distributor = new Distributor() ;
        JSONObject object = distributor.appBill(accountId, mobile, cooperKey,productId,instruction);
        validateCode = object.getString("validate");
        messageId = object.getString("globalMessageId");
        Thread.sleep(10000);


        System.out.println(validateCode+ "========" +messageId);
        distributor.confirm(cooperKey,validateCode,messageId);
        System.out.println(object.toJSONString());
    }
}
