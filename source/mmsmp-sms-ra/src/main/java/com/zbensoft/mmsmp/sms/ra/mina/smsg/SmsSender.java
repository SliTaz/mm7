package com.zbensoft.mmsmp.sms.ra.mina.smsg;

import com.zbensoft.mmsmp.common.ra.common.config.configbean.SGIPConnect;
import com.zbensoft.mmsmp.common.ra.common.config.configbean.SgipSubmit;
import com.zbensoft.mmsmp.common.ra.common.config.util.ConfigUtil;
import com.zbensoft.mmsmp.common.ra.common.message.*;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.sgip.SGIPSMSProxy;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.util.Resource;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.sgipapi.*;
import com.zbensoft.mmsmp.sms.ra.mina.utils.SmsMessageQuene;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;

public class SmsSender extends SGIPSMSProxy {
    private static final Logger logger = Logger.getLogger(SmsSender.class);
    private SgipSubmit sgipSubmit;
    private SGIPConnect sgipConnect;
    private boolean isConnect = false;
    private LinkedBlockingQueue<AbstractMessage> mtQuence;
    private LinkedBlockingQueue<AbstractMessage> moQuence;

    public SmsSender() {
        super((new Resource("config")).getParameter("SGIPConnect/*").set("block_send", true));
        this.init();
        this.moQuence = SmsMessageQuene.getInstance().getMoQuence();
        this.mtQuence = SmsMessageQuene.getInstance().getMtQuence();
    }

    public boolean init() {
        try {
            this.sgipSubmit = ConfigUtil.getInstance().getSgipSubmit();
            this.sgipConnect = ConfigUtil.getInstance().getSGIPConnect();

            try {
                logger.info("----->loginname:" + this.sgipConnect.getLogin_name() + "   ----->loginpass:" + this.sgipConnect.getLogin_pass());
                this.isConnect = this.connect(this.sgipConnect.getLogin_name(), this.sgipConnect.getLogin_pass());
            } catch (Exception var2) {
                logger.error("", var2);
            }

            this.startService(this.sgipConnect.getLocalHost(), this.sgipConnect.getLocalPort());
            if (this.isConnect) {
                logger.info("=====>connect smsgw successful");
                return true;
            }

            logger.error("=====>connect smsgw failed");
        } catch (Exception var3) {
            logger.error("", var3);
        }

        return false;
    }

    public String send(MT_SMMessage msg) {
        String spNumber = this.sgipSubmit.getSpNumber();
        int agentFlag = this.sgipSubmit.getAgentFlag();
        String expireTime = this.sgipSubmit.getExpireTime();
        String scheduleTime = this.sgipSubmit.getScheduleTime();
        int reportFlag = this.sgipSubmit.getReportFlag();
        int tp_pid = this.sgipSubmit.getTp_pid();
        int tp_udhi = this.sgipSubmit.getTp_udhi();
        int messageCoding = this.sgipSubmit.getMessageCoding();
        int messageType = this.sgipSubmit.getMessageType();
        String corpId = this.sgipSubmit.getCorpId();
        String serviceType = this.sgipSubmit.getServiceType();
        String givenValue = this.sgipSubmit.getGivenValue();
        long srcNode = this.sgipSubmit.getSrcNode();
        int morelatetoMTFla = 0;
        String msgContext = msg.getSmsText();
        boolean flag = false;
        String[] desTermId = msg.getRcvAddresses();
        boolean isMonthOrder = true;
        this.getFeeType(isMonthOrder);
        int feeType = 1;
        int sgipMsgPriority = 1;
        String feeValue = "000000";
        long msgID = 0L;

        try {
            SGIP_Submit submit = new SGIP_Submit(srcNode, spNumber, "000000000000000000000", desTermId, corpId, serviceType, feeType, feeValue, givenValue, agentFlag, morelatetoMTFla, sgipMsgPriority, expireTime, scheduleTime, reportFlag, tp_pid, tp_udhi, messageCoding, messageType, msgContext, "");
            logger.info("=====>send content to smsc:" + msgContext);
            SGIP_SubmitResp res = (SGIP_SubmitResp)super.send(submit);
            msgID = this.getSubmitSeq(submit);
        } catch (Exception var28) {
            flag = true;
            logger.error("=====>send sms to smsc error:" + msgContext, var28);
            return String.valueOf(msgID);
        }

        return flag ? null : String.valueOf(msgID);
    }

    public void sendSMS(MT_SMMessage mtSms) {
        String trantId = null;
        logger.info("------------begin to send sms...-----------------");

        try {
            String[] phone = mtSms.getRcvAddresses();

            for(int i = 0; i < phone.length; ++i) {
                if (!phone[i].startsWith("86") && phone[i].length() == 11) {
                    phone[i] = "86" + phone[i];
                }
            }

            mtSms.setRcvAddresses(phone);
            trantId = this.send(mtSms);
            logger.info("------------ send sms is end... trantId = " + trantId + "-----------------");
        } catch (Exception var5) {
            var5.printStackTrace();
            logger.error("------------send sms is error...-----------------");
        }

        logger.info("deal MT_ReportMessage....");
        MT_ReportMessage rm = new MT_ReportMessage();
        rm.setCorrelator(mtSms.getMtTranId());
        rm.setReqId(trantId);
        rm.setServiceId(String.valueOf(mtSms.getServiceId()));
        rm.setContentid(mtSms.getContentid());
        rm.setMtDate(new Date());
        rm.setRcvAddresses(mtSms.getRcvAddresses());
        if (trantId == null) {
            rm.setStatus("7");
        } else {
            rm.setStatus("6");
        }

    }

    private int getFeeType(boolean isMonthOrder) {
        int feeType;
        if (isMonthOrder) {
            feeType = 3;
        } else {
            feeType = 2;
        }

        return feeType;
    }

    private long getSubmitSeq(SGIP_Command msg) {
        SGIP_Submit submit = (SGIP_Submit)msg;
        long seqID = submit.getSeqno_1() + (long)submit.getSeqno_2() + (long)submit.getSeqno_3();
        return seqID;
    }

    public SGIP_Command onDeliver(SGIP_Deliver msg) {
        logger.info("=====>receive SGIP Deliver Message: " + msg);
        logger.info("begin change SGIP Deliver Message to MO_SMMessage");
        String userNumber = msg.getUserNumber();
        if (userNumber.length() > 11) {
            userNumber = userNumber.substring(userNumber.length() - 11);
        }

        MO_SMMessage momsg = new MO_SMMessage();
        momsg.setSendAddress(userNumber);
        momsg.setSmsText(msg.getMessageContent());
        momsg.setVasId(msg.getSPNumber());
        momsg.setLinkId(msg.getLinkId());
        momsg.setGlobalMessageid(this.getMessageid());
        momsg.setGlobalCreateTime(System.currentTimeMillis());
        momsg.setSequence_Number_1((int)(msg.getSeqno_1() >> 0));
        momsg.setSequence_Number_2(msg.getSeqno_2());
        momsg.setSequence_Number_3(msg.getSeqno_3());
        momsg.setMessageCoding((byte)msg.getMessageCoding());
        momsg.setTP_pid((byte)msg.getTP_pid());
        momsg.setTP_udhi((byte)msg.getTP_udhi());
        momsg.setContentLength(msg.getMessageLength());

        try {
            this.moQuence.put(momsg);
            logger.info("put mosms to moquene success. usernumber:" + userNumber + "  messageid:" + momsg.getGlobalMessageid());
        } catch (Exception var5) {
            logger.info("put mosms to moquene failed, usernumber:" + userNumber);
            logger.error("usernumber:" + userNumber, var5);
        }

        return super.onDeliver(msg);
    }

    private String getMessageid() {
        return "sms" + UUID.randomUUID().toString().replace("-", "");
    }

    public SGIP_Command onReport(SGIP_Report msg) {
        MO_ReportMessage moSmmessage = new MO_ReportMessage();
        moSmmessage.setSendAddress(msg.getUserNumber());
        moSmmessage.setStatus(this.changeStatus(msg.getState()));
        long value = this.getReportSeq(msg);
        moSmmessage.setCorrelator(String.valueOf(value));
        String host = ConfigUtil.getInstance().getAdminConfig().getNotifyMessageIP();
        int port = ConfigUtil.getInstance().getCorebizConfig().getMoQueueListenPort();
        logger.info("receive Report: " + moSmmessage);
        return super.onReport(msg);
    }

    private String changeStatus(int state) {
        String status = null;
        switch(state) {
            case 0:
                status = "5";
                break;
            case 1:
                status = "8";
                break;
            case 2:
                status = "7";
        }

        return status;
    }

    private long getReportSeq(SGIP_Command msg) {
        SGIP_Report report = (SGIP_Report)msg;
        long seqID = report.getSeq_1() + (long)report.getSeq_2() + (long)report.getSeq_3();
        return seqID;
    }

    public static void main(String[] args) {
        SmsSender sms = new SmsSender();
        MT_SMMessage send = new MT_SMMessage();
        send.setSmsText("TEST");
        send.setRcvAddresses(new String[]{"13311001100"});
        sms.send(send);
    }
}
