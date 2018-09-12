package com.zbensoft.ownbiz.simulator;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.codec.digest.DigestUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.SecureRandom;

public class Distributor {
    OkHttpClient okHttpClient = new OkHttpClient();
    String SEND_VALIDATE_CODE = "http://localhost:9098/billing/getVerificationCode?accountId={accountId}&mobile={mobile}&Content={Content}";
    String WEB_BILL = "http://localhost:9098/billing/web?accountId={accountId}&mobile={mobile}&productId={productId}&instruction={instruction}&messageId={messageId}&validateCode={validateCode}&Content={Content}";
    String APP_BILL = "http://localhost:9098/billing/app?accountId={accountId}&mobile={mobile}&productId={productId}&instruction={instruction}&Content={Content}";
    String APP_CONFIRM = "http://localhost:9098/billing/confirm?messageId={messageId}&validateCode={validateCode}&Content={Content}";
    String GET_PHONE = "http://localhost:9098/billing/getMobile?unikey=333333333333333333333333333";

    public JSONObject sendValidateCode(String accountId, String mobile, String cooperKey) throws IOException {
        StringBuffer sb = new StringBuffer();
        String str = SEND_VALIDATE_CODE.replace("{accountId}", accountId).replace("{mobile}", mobile).replace("{Content}", DigestUtils.md5Hex(sb.append(cooperKey).append(accountId).append(mobile).toString()));
        Request request = new Request.Builder().url(str).build();
        Response response = okHttpClient.newCall(request).execute();

        JSONObject object = JSON.parseObject(response.body().string());
        return object;
    }


    public void webBill(String accountId, String mobile, String cooperKey, String productId, String instruction, String messageId, String validateCode) throws IOException {
        StringBuffer sb = new StringBuffer();
        String content = DigestUtils.md5Hex(sb.append(cooperKey).append(productId).append(instruction).append(accountId).append(mobile).append(messageId).append(validateCode).toString());
        String str = WEB_BILL.replace("{accountId}", accountId).replace("{mobile}", mobile).replace("{Content}", content).replace("{productId}", productId).replace("{instruction}", URLEncoder.encode(instruction)).replace("{messageId}", messageId).replace("{validateCode}", validateCode);
        Request request = new Request.Builder().url(str).build();
        Response response = okHttpClient.newCall(request).execute();
        System.out.println(JSON.parseObject(response.body().string()));
    }


    public JSONObject appBill(String accountId, String mobile, String cooperKey, String productId, String instruction) throws IOException {

        String str = mobile;
        long nowTime = System.currentTimeMillis();
        String password = "12345688";
        str = str + "," + nowTime;
        String result = encrypt(str, password);
        String aa = result;
        System.out.println("原始des加密串：" + result);

        try {
            result = URLEncoder.encode(result, "utf-8");
        } catch (UnsupportedEncodingException var8) {
            var8.printStackTrace();
        }

        System.out.println("URLEncoder加密后内容为：" + new String(result));
        String decryResult = null;
        try {
            result = URLDecoder.decode(result, "utf-8");
            decryResult = descrypt(result, password);
            System.out.println("解密后内容为：" + decryResult);
        } catch (Exception var7) {
            var7.printStackTrace();
        }


        StringBuffer sb = new StringBuffer();

        String content = DigestUtils.md5Hex(sb.append(cooperKey).append(accountId).append(aa).append(productId).append(instruction).toString());
        String strUrl = APP_BILL.replace("{accountId}", accountId).replace("{mobile}", result).replace("{Content}", content).replace("{productId}", productId).replace("{instruction}", URLEncoder.encode(instruction));
        Request request = new Request.Builder().url(strUrl).build();
        Response response = okHttpClient.newCall(request).execute();
        return JSON.parseObject(response.body().string());
    }

    public void confirm(String cooperKey, String validateCode, String messageId) throws IOException {
        StringBuffer sb = new StringBuffer();
        String content = DigestUtils.md5Hex(sb.append(cooperKey).append(validateCode).toString());
        String str = APP_CONFIRM.replace("{Content}", content).replace("{messageId}", messageId).replace("{validateCode}", validateCode);
        Request request = new Request.Builder().url(str).build();
        Response response = okHttpClient.newCall(request).execute();
        System.out.println(JSON.parseObject(response.body().string()));
    }


    public void getPhoneNumber() throws IOException {
        StringBuffer sb = new StringBuffer();
        Request request = new Request.Builder().url(GET_PHONE).build();
        Response response = okHttpClient.newCall(request).execute();
        System.out.println(JSON.parseObject(response.body().string()));
    }


    public static String encrypt(String content, String password) {
        try {
            byte[] datasource = content.getBytes();
            SecureRandom random = new SecureRandom();
            DESKeySpec desKey = new DESKeySpec(password.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey securekey = keyFactory.generateSecret(desKey);
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(1, securekey, random);
            return (new BASE64Encoder()).encode(cipher.doFinal(datasource));
        } catch (Exception var8) {
            return null;
        }
    }

    public static String descrypt(String enContent, String password) {
        try {
            byte[] datasource = (new BASE64Decoder()).decodeBuffer(enContent);
            SecureRandom random = new SecureRandom();
            DESKeySpec desKey = new DESKeySpec(password.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey securekey = keyFactory.generateSecret(desKey);
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(2, securekey, random);
            return new String(cipher.doFinal(datasource));
        } catch (Exception var8) {
            return null;
        }
    }

    public static void main(String[] args) throws IOException {
        Distributor d = new Distributor();
        d.getPhoneNumber();
    }
}
