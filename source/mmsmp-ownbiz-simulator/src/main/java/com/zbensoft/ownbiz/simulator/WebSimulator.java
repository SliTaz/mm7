package com.zbensoft.ownbiz.simulator;

import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
public class WebSimulator {
    public static void main(String[] args) throws IOException {

        String accountId = "12345";
        String mobile = "13312345678";
        String cooperKey = "45678";
        String spProductId = "111";
        String instruction = "123456";
        String messageId = "";
        String validateCode = "";

        Distributor distributor = new Distributor() ;
        JSONObject object = distributor.sendValidateCode(accountId, mobile, cooperKey);
        messageId = object.getString("messageId");
        validateCode = object.getString("validateCode");
        distributor.webBill(accountId, mobile, cooperKey,spProductId,instruction,messageId,validateCode);
    }
}
