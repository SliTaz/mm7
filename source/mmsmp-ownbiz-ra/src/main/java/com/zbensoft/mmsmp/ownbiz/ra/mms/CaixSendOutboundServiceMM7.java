

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
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CaixSendOutboundServiceMM7 {
    protected static final Log log = LogFactory.getLog(CaixSendOutboundServiceMM7.class);
    private MM7Config _mm7Config = null;
    private MM7Sender _mm7Sender = null;
    private String mm7ConfigName = "test/mms/mm7Config.xml";
    private String mm7ConnConfigName = "test/mms/ConnConfig.xml";

    public CaixSendOutboundServiceMM7() {
    }

    public void init() {
        if (this._mm7Sender == null) {
            if (this._mm7Config == null) {
                String configSrc = CaixSendOutboundServiceMM7.class.getClassLoader().getResource(this.mm7ConfigName).getPath();
                String connSrc = CaixSendOutboundServiceMM7.class.getClassLoader().getResource(this.mm7ConnConfigName).getPath();
                System.out.println(configSrc);
                this._mm7Config = new MM7Config(configSrc);
                this._mm7Config.setConnConfigName(connSrc);
            }

            if (this._mm7Config != null) {
                try {
                    this._mm7Sender = new MM7Sender(this._mm7Config);
                } catch (Exception var3) {
                    log.error("this._mm7Sender new error." + var3.getMessage());
                    this._mm7Sender = null;
                }
            }
        }

    }

    public void testA() {
        try {
            this.init();
            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            MM7SubmitReq submit = new MM7SubmitReq();
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
            MM7Sender mm7Sender = new MM7Sender(this._mm7Config);
            MM7RSRes rsRes = null;
            rsRes = mm7Sender.send(submit);
            if (rsRes instanceof MM7SubmitRes) {
                MM7SubmitRes submitRes = (MM7SubmitRes)rsRes;
                System.out.println(rsRes.getTransactionID());
                System.out.println("after!!submitRes.statuscode=" + rsRes.getStatusCode() + ";submitRes.statusText=" + rsRes.getStatusText());
            } else {
                System.out.println("不正确消息！rsRes.statuscode=" + rsRes.getStatusCode() + ";rsRes.statusText=" + rsRes.getStatusText());
            }
        } catch (Exception var11) {
            System.out.println(var11.getMessage());
        }

    }

    public static void main(String[] args) {
        (new CaixSendOutboundServiceMM7()).testA();
    }
}
