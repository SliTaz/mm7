package com.zbensoft.mmsmp.common.ra.smssgip.smsclient;

import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.sgipapi.SGIP_Exception;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.sgipapi.SGIP_Submit;

import java.net.InetSocketAddress;
import java.net.Socket;

public class SGIPClient {
    public SGIPClient() {
    }

    public static void main(String[] args) throws Exception {
        Socket sc = new Socket();
        sc.connect(new InetSocketAddress("220.194.48.129", 10114));
        sc.setKeepAlive(true);
        sc.setSoTimeout(10000);
        SGIP_Submit smt = createSubmit(1L, "1", "2", "$ASS", "1", "0", "8613331019461", "8613331019461", "10114", "test asscii code", 0, "12345678");
        smt.write(sc);
        smt.read(sc);
        System.out.println("test ascii:" + smt);
        smt = createSubmit(1L, "1", "2", "$ASS", "1", "0", "8613331019461", "8613331019461", "10114", "11", 0, "12345678");
        smt.write(sc);
        smt.read(sc);
        System.out.println("test GBK:" + smt);
        smt = createSubmit(1L, "1", "2", "$ASS", "1", "0", "8613331019461", "8613331019461", "10114", "测试UCS2编码", 8, "12345678");
        smt.write(sc);
        smt.read(sc);
        System.out.println("test UCS2:" + smt);
    }

    public static SGIP_Submit createSubmit(int srcNode, String chargePhone, String userPhone, String content) {
        return createSubmit(srcNode, "1", "2", "unknown", "1", "0", chargePhone, userPhone, "10114", content, "01234567");
    }

    public static SGIP_Submit createSubmit(int srcNode, String chargePhone, String userPhone, String content, int msgcoding) {
        return createSubmit((long)srcNode, "1", "2", "unknown", "1", "0", chargePhone, userPhone, "10114", content, msgcoding, "01234567");
    }

    public static SGIP_Submit createSubmit(long l, String rvalue, String MoMtFlag, String serviceId, String feetype, String feeCode, String feeTermId, String destTermId, String srcAddr, String msg, int msgcoding, String linkId) {
        SGIP_Submit submit = null;
        long SNodeID = l;
        String SPNumber = srcAddr;
        String ChargeNumber = feeTermId;
        int UserCount = 1;
        String UserNumber = destTermId;
        String CorpId = "00000";
        String ServiceType = serviceId;
        int FeeType = Integer.parseInt(feetype);
        String FeeValue = feeCode;
        String GivenValue = "0";
        int AgentFlag = 1;
        int MorelatetoMTFla = Integer.parseInt(MoMtFlag);
        int Priority = 0;
        String ExpireTime = "";
        String ScheduleTime = "";
        int ReportFlag = Integer.parseInt(rvalue);
        int TP_pid = 0;
        int TP_udhi = 0;
        int MessageCoding = msgcoding;
        int MessageType = 0;
        int MessageLength = msg.getBytes().length;
        String MessageContent = msg;
        String Reserve = linkId;

        try {
            submit = new SGIP_Submit(SNodeID, SPNumber, ChargeNumber, UserCount, UserNumber, CorpId, ServiceType, FeeType, FeeValue, GivenValue, AgentFlag, MorelatetoMTFla, Priority, ExpireTime, ScheduleTime, ReportFlag, TP_pid, TP_udhi, MessageCoding, MessageType, MessageLength, MessageContent, Reserve);
        } catch (SGIP_Exception var39) {
            var39.printStackTrace();
        }

        return submit;
    }

    public static SGIP_Submit createSubmit(int srcNode, String rvalue, String MoMtFlag, String serviceId, String feetype, String feeCode, String feeTermId, String destTermId, String srcAddr, String msg, String linkId) {
        return createSubmit((long)srcNode, rvalue, MoMtFlag, serviceId, feetype, feeCode, feeTermId, destTermId, srcAddr, msg, 15, linkId);
    }
}

