

package com.zbensoft.mmsmp.ownbiz.ra.mms;

import com.cmcc.mm7.vasp.common.MMContent;
import com.cmcc.mm7.vasp.common.MMConstants.ContentType;
import com.cmcc.mm7.vasp.conf.MM7Config;
import com.cmcc.mm7.vasp.message.MM7RSRes;
import com.cmcc.mm7.vasp.message.MM7SubmitReq;
import com.cmcc.mm7.vasp.message.MM7SubmitRes;
import com.cmcc.mm7.vasp.service.MM7Sender;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;

public class MMSSend extends Thread {
    public MMSSend() {
    }

    public void run() {
        while(true) {
            String mm7ConfigName = "./mm7Config.xml";
            String mm7ConnConfigName = "./ConnConfig.xml";
            MM7Config mm7Config = new MM7Config(mm7ConfigName);
            mm7Config.setConnConfigName(mm7ConnConfigName);
            MM7Sender mm7Sender = null;

            try {
                mm7Sender = new MM7Sender(mm7Config);
            } catch (Exception var15) {
                var15.printStackTrace();
            }

            System.out.println("sender:=  " + mm7Sender);
            MM7SubmitReq submit = new MM7SubmitReq();
            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            submit.setTransactionID("1000000");
            submit.addTo("13980000035");
            submit.setVASID("5555");
            submit.setVASPID("805555");
            submit.setServiceCode("000000");
            submit.setSenderAddress("5555");
            submit.setSubject("MMS测试");
            submit.setChargedPartyID("13980000035");
            submit.setChargedParty((byte)4);
            submit.setDeliveryReport(true);
            MMContent content = new MMContent();
            content.setContentType(ContentType.MULTIPART_RELATED);
            content.setContentID("mm7Test");
            String ss = "这是一个测试MMS";
            byte[] bb = ss.getBytes();
            InputStream input = new ByteArrayInputStream(bb);
            MMContent sub2 = MMContent.createFromStream(input);
            sub2.setContentID("2.txt");
            sub2.setContentType(ContentType.TEXT);
            content.addSubContent(sub2);
            submit.setContent(content);
            MM7RSRes rsRes = null;
            rsRes = mm7Sender.send(submit);
            if (rsRes instanceof MM7SubmitRes) {
                MM7SubmitRes submitRes = (MM7SubmitRes)rsRes;
                System.out.println(rsRes.getTransactionID());
                System.out.println("after!!submitRes.statuscode=" + rsRes.getStatusCode() + ";submitRes.statusText=" + rsRes.getStatusText());
            } else {
                System.out.println("不正确消息！rsRes.statuscode=" + rsRes.getStatusCode() + ";rsRes.statusText=" + rsRes.getStatusText());
            }

            try {
                Thread.currentThread();
                Thread.sleep(1000L);
            } catch (InterruptedException var14) {
                var14.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        (new MMSSend()).start();
        Thread.currentThread();
        Thread.sleep(100L);
        (new MMSSend()).start();
        Thread.currentThread();
        Thread.sleep(100L);
        (new MMSSend()).start();
    }
}
