package com.zbensoft.mmsmp.ownbiz.ra.own.util;

import com.cmcc.mm7.vasp.common.MMConstants.ContentType;
import com.cmcc.mm7.vasp.common.MMContent;
import com.cmcc.mm7.vasp.conf.MM7Config;
import com.cmcc.mm7.vasp.message.MM7RSRes;
import com.cmcc.mm7.vasp.message.MM7SubmitReq;
import com.cmcc.mm7.vasp.message.MM7SubmitRes;
import com.cmcc.mm7.vasp.service.MM7Sender;
import com.zbensoft.mmsmp.common.ra.common.config.util.ConfigUtil;
import com.zbensoft.mmsmp.ownbiz.ra.own.entity.MmsUserEntity;
import org.apache.log4j.Logger;

public class MmsUtil {
    static final Logger logger = Logger.getLogger(MmsUtil.class);
    static String mm7ConfigFile = "./mm7Config.xml";
    static String mm7ConnConfigName = "./ConnConfig.xml";
    static String path = ConfigUtil.getInstance().getCommonConfig().getContentFilePath();

    public MmsUtil() {
    }

    public int sendMms(MmsUserEntity mms) {
        boolean var2 = false;

        byte flag;
        try {
            MM7Config mm7Config = new MM7Config(mm7ConfigFile);
            mm7Config.setConnConfigName(mm7ConnConfigName);
            MM7SubmitReq submit = new MM7SubmitReq();
            submit.setTransactionID(DateUtil.getAllTime());
            submit.addTo(mms.getUserNumber());
            if (mms.getChargeNumber() != null && mms.getChargeNumber().trim().length() > 0) {
                submit.addTo(mms.getUserNumber() + ":" + mms.getChargeNumber());
            }

            submit.setVASID(mms.getVasId());
            submit.setVASPID(mms.getVaspId());
            submit.setServiceCode(mms.getProductId());
            submit.setSenderAddress(mms.getSendAddress());
            submit.setDeliveryReport(true);
            submit.setReadReply(true);
            submit.setChargedParty((byte)1);
            submit.setSubject(mms.getContentName());
            MMContent content = AceMessageUtil.emlFileToMM7(path + mms.getContentFile());
            content.setCharset("utf-8");
            submit.setContent(content);
            MM7Sender mm7Sender = new MM7Sender(mm7Config);
            MM7RSRes res = mm7Sender.send(submit);
            if (res instanceof MM7SubmitRes) {
                MM7SubmitRes subRes = (MM7SubmitRes)res;
                logger.info("call send mms success[contentid:" + mms.getContentId() + ",messageid:" + subRes.getMessageID() + ",statuscode:" + res.getStatusCode() + ",statustext:" + res.getStatusText() + "]");
                flag = 0;
            } else {
                logger.info("call send mms failure[contentid:" + mms.getContentId() + ",statuscode:" + res.getStatusCode() + ",statustext:" + res.getStatusText() + "]");
                flag = -1;
            }
        } catch (Exception var9) {
            logger.error("call send mms exception", var9);
            flag = -1;
        }

        return flag;
    }

    public int sendMms(MmsUserEntity mms, String linkId) {
        boolean var3 = false;

        byte flag;
        try {
            MM7Config mm7Config = new MM7Config(mm7ConfigFile);
            mm7Config.setConnConfigName(mm7ConnConfigName);
            MM7SubmitReq submit = new MM7SubmitReq();
            submit.setTransactionID(DateUtil.getAllTime());
            submit.addTo(mms.getUserNumber());
            if (mms.getChargeNumber() != null && mms.getChargeNumber().trim().length() > 0) {
                submit.addTo(mms.getUserNumber() + ":" + mms.getChargeNumber());
            }

            submit.setVASID(mms.getVasId());
            submit.setVASPID(mms.getVaspId());
            submit.setServiceCode(mms.getProductId());
            submit.setSenderAddress(mms.getSendAddress());
            logger.info("现在下发彩信信息contentName=" + mms.getContentName() + ",VasId=" + mms.getVasId() + ",vaspId=" + mms.getVaspId() + ",ProductId=" + mms.getProductId() + ",sendAddress=" + mms.getSendAddress() + ",addTo:= " + mms.getUserNumber() + ":" + mms.getChargeNumber());
            submit.setDeliveryReport(true);
            submit.setReadReply(true);
            submit.setChargedParty((byte)1);
            submit.setSubject(mms.getContentName());
            if (linkId != null) {
                submit.setLinkedID(linkId);
            }

            MMContent content = AceMessageUtil.emlFileToMM7(path + mms.getContentFile());
            content.setCharset("utf-8");
            submit.setContent(content);
            MM7Sender mm7Sender = new MM7Sender(mm7Config);
            MM7RSRes res = mm7Sender.send(submit);
            if (res instanceof MM7SubmitRes) {
                MM7SubmitRes subRes = (MM7SubmitRes)res;
                logger.info("call send mms success[contentid:" + mms.getContentId() + ",messageid:" + subRes.getMessageID() + ",statuscode:" + res.getStatusCode() + ",statustext:" + res.getStatusText() + "]");
                flag = 0;
            } else {
                logger.info("call send mms failure[contentid:" + mms.getContentId() + ",statuscode:" + res.getStatusCode() + ",statustext:" + res.getStatusText() + "]");
                flag = -1;
            }
        } catch (Exception var10) {
            logger.error("call send mms exception", var10);
            flag = -1;
        }

        return flag;
    }

    public static void main(String[] args) throws Exception {
        MM7Config mm7Config = new MM7Config(mm7ConfigFile);
        mm7Config.setConnConfigName(mm7ConnConfigName);
        MM7SubmitReq submit = new MM7SubmitReq();
        submit.setTransactionID(DateUtil.getAllTime());
        submit.addTo("18600297907");
        submit.setChargedPartyID("18600297907");
        submit.setVASID("10655598");
        submit.setVASPID("91444");
        submit.setServiceCode("3100224100");
        submit.setSenderAddress("10655598");
        submit.setDeliveryReport(true);
        submit.setReadReply(true);
        submit.setChargedParty((byte)1);
        submit.setSubject("112233");
        submit.setLinkedID("12345");
        MMContent content = AceMessageUtil.emlFileToMM7("D:\\datasms\\content\\9159720140506100609735.eml");
        content.setContentID("mm7Test");
        MMContent sub2 = MMContent.createFromString("you received this message right");
        sub2.setContentType(ContentType.TEXT);
        sub2.setContentID("1.text");
        content.addSubContent(sub2);
        submit.setContent(content);
        MM7Sender mm7Sender = new MM7Sender(mm7Config);
        int sendTotal = 1000;
        int sendNum = 1;
        short sendSpeed = 300;

        do {
            ++sendNum;
            MM7RSRes res = mm7Sender.send(submit);
            if (res instanceof MM7SubmitRes) {
                MM7SubmitRes subRes = (MM7SubmitRes)res;
                logger.info("call send mms success[contentid:,messageid:" + subRes.getMessageID() + ",statuscode:" + res.getStatusCode() + ",statustext:" + res.getStatusText() + "]");
            } else {
                logger.info("call send mms failure[contentid:,statuscode:" + res.getStatusCode() + ",statustext:" + res.getStatusText() + "]");
            }

            logger.info("sendNum:" + sendNum);
            if (sendNum % sendSpeed == 0) {
                Thread.sleep(100L);
            }
        } while(sendNum != sendTotal);

    }
}
