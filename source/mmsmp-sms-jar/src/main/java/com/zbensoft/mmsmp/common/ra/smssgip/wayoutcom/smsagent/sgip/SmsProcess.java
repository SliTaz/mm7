package com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.smsagent.sgip;

import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.common.SmsHandler;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.common.log4Command;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.dbAccess.OperData;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.produce_consume.Channel;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.sgipapi.SGIP_Command;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.sgipapi.SGIP_Deliver;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.sgipapi.SGIP_Report;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.sgipapi.SGIP_Submit;

import java.nio.ByteBuffer;
import java.util.*;

public class SmsProcess extends SmsHandler {
    private Channel ComdQ;
    private Channel RespQ;
    private Thread RecvThread;
    private Thread SendThread;
    private int countRecv;
    private int countSend;
    private Map submitQueue;
    private LinkedList submitResp;
    private LinkedList submitUnResp;
    private LinkedList submitResend;
    public static Map reportQueue = new LinkedHashMap();
    public static LinkedList reportResp = new LinkedList();
    public static LinkedList reportResend = new LinkedList();
    private LinkedList reportUnResp;
    public static Map deliverQueue = new LinkedHashMap();
    public static LinkedList deliverResp = new LinkedList();
    public static LinkedList deliverResend = new LinkedList();
    private LinkedList deliverUnResp;
    private static int CHANNEL_SIZE = 10;

    public SmsProcess() {
        this.ComdQ = new Channel(CHANNEL_SIZE);
        this.RespQ = new Channel(CHANNEL_SIZE);
        this.RecvThread = null;
        this.SendThread = null;
        this.countRecv = 0;
        this.countSend = 0;
        this.submitQueue = new LinkedHashMap();
        this.submitResp = new LinkedList();
        this.submitUnResp = new LinkedList();
        this.submitResend = new LinkedList();
        this.reportUnResp = new LinkedList();
        this.deliverUnResp = new LinkedList();
    }

    public void receive(Object object) {
        try {
            this.thisLayerReceive(object);
        } catch (InterruptedException var3) {
            this.LogHandler.error("SmsProcess receive message error");
        }

    }

    public void send(Object object) {
        try {
            this.thisLayerSend(object);
        } catch (InterruptedException var3) {
            this.LogHandler.error("SmsProcess receive message error");
        }

    }

    private void thisLayerReceive(Object sgip_Cmd) throws InterruptedException {
        this.ComdQ.put(sgip_Cmd);
    }

    private void thisLayerSend(Object request) throws InterruptedException {
        this.RespQ.put(request);
    }

    private int dealCommand(Object s) {
        String strDestPhone = null;
        String strSMSContent = null;
        String strLog = null;
        boolean bSubmit = false;
        String strContent = null;
        SGIP_Command sms = (SGIP_Command)s;
        int nCmdID = sms.getCommandID();

        try {
            switch(nCmdID) {
                case 4:
                    String key = "" + sms.getSeqno_1() + sms.getSeqno_2() + sms.getSeqno_3();
                    Map var24 = deliverQueue;
                    synchronized(deliverQueue) {
                        deliverQueue.put(key, sms);
                    }

                    ByteBuffer sendByte = this.cmd2ByteBuffer(sms);
                    this.DownHandler.send(sendByte);
                    break;
                case 5:
                    SGIP_Report report = (SGIP_Report)sms;
                    report.readbody();
                    SGIP_Command value = null;
                    boolean flag = false;
                    Map var13 = this.submitQueue;
                    synchronized(this.submitQueue) {
                        flag = this.submitQueue.containsKey(report);
                        if (flag) {
                            value = (SGIP_Command)this.submitQueue.remove(report);
                        }
                    }

                    LinkedList var14;
                    long[] pre;
                    if (flag) {
                        if (value == null) {
                            pre = new long[]{report.getSeq_1(), (long)report.getSeq_2(), (long)report.getSeq_3()};
                            var14 = this.submitResend;
                            synchronized(this.submitResend) {
                                this.submitResend.addLast(pre);
                            }
                        } else {
                            LinkedList var27 = this.submitResp;
                            synchronized(this.submitResp) {
                                this.submitResp.addLast(value);
                            }
                        }
                    } else {
                        pre = new long[]{report.getSeq_1(), (long)report.getSeq_2(), (long)report.getSeq_3()};
                        var14 = this.submitResend;
                        synchronized(this.submitResend) {
                            this.submitResend.addLast(pre);
                        }
                    }

                    pre = new long[3];
                    if (flag) {
                        pre[0] = value.getSeqno_1();
                        pre[1] = (long)value.getSeqno_2();
                        pre[2] = (long)value.getSeqno_3();
                    } else {
                        OperData data = new OperData();
                        pre = data.getSeq(report.getSeq_1(), report.getSeq_2(), report.getSeq_3());
                        if (pre == null) {
                            return 0;
                        }
                    }

                    report.setSeq_1(pre[0]);
                    report.setSeq_2((int)pre[1]);
                    report.setSeq_3((int)pre[2]);
                    String key2 = "" + report.getSeqno_1() + report.getSeqno_2() + report.getSeqno_3();
                    Map var15 = reportQueue;
                    synchronized(reportQueue) {
                        reportQueue.put(key2, report);
                    }

                    ByteBuffer sendByte2 = this.cmd2ByteBuffer(report);
                    this.DownHandler.send(sendByte2);
            }

            return 0;
        } catch (Exception var22) {
            var22.printStackTrace();
            return -1;
        }
    }

    private ByteBuffer cmd2ByteBuffer(SGIP_Command sms) {
        int nLen = sms.getTotalLength();
        ByteBuffer sendByte = ByteBuffer.allocate(nLen);
        sendByte.clear();
        ByteBuffer head = sms.getHead().GetHeadByte();
        head.clear();
        sendByte.put(head);
        sendByte.position(20);
        ByteBuffer body = sms.getBodyByte();
        body.clear();
        sendByte.put(body);
        sendByte.flip();
        return sendByte;
    }

    private Object[] checkCommand() throws InterruptedException {
        Object[] tmp = (Object[])null;
        tmp = this.ComdQ.removeAll();
        return tmp;
    }

    private void dealResp(Object s) {
        SGIP_Command ss = (SGIP_Submit)s;
        long nodeID = Long.parseLong((String)SmsCenter.login.get("SrcNodeID"));
        SGIP_Command ssubmit = new SGIP_Submit(nodeID, (SGIP_Submit)s);
        Map var6 = this.submitQueue;
        synchronized(this.submitQueue) {
            this.submitQueue.put(ssubmit, ss);
        }

        ByteBuffer sendByte = this.cmd2ByteBuffer(ssubmit);
        this.UpHandler.send(sendByte);
    }

    private Object[] checkResp() throws InterruptedException {
        Object[] tmp = (Object[])null;
        tmp = this.RespQ.removeAll();
        return tmp;
    }

    public void recvThread() {
        Object[] tmp_comdQ = (Object[])null;

        while(true) {
            while(true) {
                try {
                    tmp_comdQ = this.checkCommand();
                    if (tmp_comdQ == null) {
                        continue;
                    }
                } catch (InterruptedException var5) {
                    this.LogHandler.error("Business" + var5.getMessage());
                    continue;
                }

                for(int i = 0; i < tmp_comdQ.length; ++i) {
                    Object b_command = tmp_comdQ[i];
                    this.dealCommand(b_command);
                }
            }
        }
    }

    public void sendThread() {
        Object[] tmp_respQ = (Object[])null;

        while(true) {
            while(true) {
                try {
                    tmp_respQ = this.checkResp();
                    if (tmp_respQ == null) {
                        continue;
                    }
                } catch (InterruptedException var4) {
                    this.LogHandler.error("Business" + var4.getMessage());
                    continue;
                }

                for(int i = 0; i < tmp_respQ.length; ++i) {
                    Object b_command = tmp_respQ[i];
                    this.dealResp(b_command);
                }
            }
        }
    }

    public void start() {
        this.RecvThread = new Thread(new Runnable() {
            public void run() {
                SmsProcess.this.recvThread();
            }
        });
        this.RecvThread.start();
        this.LogHandler.info("SMS, smsProcess recv start");
        this.SendThread = new Thread(new Runnable() {
            public void run() {
                SmsProcess.this.sendThread();
            }
        });
        this.SendThread.start();
        this.LogHandler.info("SMS, smsProcess send start");
        Integer hm = (Integer)SmsCenter.parameter.get("data_Check_Freq");
        int checkFreq = hm;
        hm = (Integer)SmsCenter.parameter.get("data_Import_Freq");
        int dataFreq = hm;
        Thread.currentThread().setPriority(6);
        (new Timer()).schedule(new TimerTask() {
            public void run() {
                SmsProcess.this.resendTask();
                SmsProcess.this.moveTask();
                SmsProcess.this.submitTimeout();
                SmsProcess.this.reportTimeout();
                SmsProcess.this.deliverTimeout();
                SmsProcess.this.submitUnresp2DB();
                SmsProcess.this.submitresend2DB();
                SmsProcess.this.reportUnresp2DB();
                SmsProcess.this.reportresend2DB();
                SmsProcess.this.deliverUnresp2DB();
                SmsProcess.this.deliverresend2DB();
            }
        }, 0L, (long)(checkFreq * 60 * 1000));
        Thread.currentThread().setPriority(5);
        (new Timer()).schedule(new TimerTask() {
            public void run() {
                SmsProcess.this.submitresp2DB();
                SmsProcess.this.reportresp2DB();
                SmsProcess.this.deliverresp2DB();
            }
        }, 0L, (long)(dataFreq * 60 * 1000));
        this.LogHandler.info("SMS, database handle start");
    }

    private void resendTask() {
        OperData data = new OperData();
        List list = data.submitResend();

        int i;
        for(i = 0; i < list.size(); ++i) {
            this.UpHandler.send(ByteBuffer.wrap((byte[])list.get(i)));
        }

        list = data.deliverResend();

        for(i = 0; i < list.size(); ++i) {
            this.DownHandler.send(ByteBuffer.wrap((byte[])list.get(i)));
        }

        list = data.reportResend();

        for(i = 0; i < list.size(); ++i) {
            this.DownHandler.send(ByteBuffer.wrap((byte[])list.get(i)));
        }

    }

    private void moveTask() {
        OperData data = new OperData();
        data.moveData();
    }

    private void submitTimeout() {
        List tmp = new ArrayList();
        Integer hm = (Integer)SmsCenter.parameter.get("submit_Timeout");
        int submit_Timeout = hm;
        Map var4 = this.submitQueue;
        synchronized(this.submitQueue) {
            Set entry = this.submitQueue.entrySet();
            Iterator it = entry.iterator();

            while(it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                SGIP_Command key = (SGIP_Command)pair.getKey();
                SGIP_Command value = (SGIP_Command)pair.getValue();
                int seq2 = key.getSeqno_2();
                Calendar cal = new GregorianCalendar();
                int now = (cal.get(2) + 1) * 100000000 + cal.get(5) * 1000000 + cal.get(11) * 10000 + cal.get(12) * 100 + cal.get(13);
                int differ = now - seq2;
                if (differ / 10000 >= submit_Timeout) {
                    tmp.add(pair);
                    it.remove();
                    continue;
                }
            }
        }

        LinkedList var17 = this.submitUnResp;
        synchronized(this.submitUnResp) {
            this.submitUnResp.addAll(tmp);
        }
    }

    private void reportTimeout() {
        List tmp = new ArrayList();
        Integer hm = (Integer)SmsCenter.parameter.get("report_Timeout");
        int report_Timeout = hm;
        Map var4 = reportQueue;
        synchronized(reportQueue) {
            Set entry = reportQueue.entrySet();
            Iterator it = entry.iterator();

            while(it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                SGIP_Command value = (SGIP_Command)pair.getValue();
                int seq2 = value.getSeqno_2();
                Calendar cal = new GregorianCalendar();
                int now = (cal.get(2) + 1) * 100000000 + cal.get(5) * 1000000 + cal.get(11) * 10000 + cal.get(12) * 100 + cal.get(13);
                int differ = now - seq2;
                if (differ / 100 >= report_Timeout) {
                    tmp.add(value);
                    it.remove();
                    continue;
                }
            }
        }

        LinkedList var16 = this.reportUnResp;
        synchronized(this.reportUnResp) {
            this.reportUnResp.addAll(tmp);
        }
    }

    private void deliverTimeout() {
        List tmp = new ArrayList();
        Integer hm = (Integer)SmsCenter.parameter.get("deliver_Timeout");
        int deliver_Timeout = hm;
        Map var4 = deliverQueue;
        synchronized(deliverQueue) {
            Set entry = deliverQueue.entrySet();
            Iterator it = entry.iterator();

            while(it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                SGIP_Command value = (SGIP_Command)pair.getValue();
                int seq2 = value.getSeqno_2();
                Calendar cal = new GregorianCalendar();
                int now = (cal.get(2) + 1) * 100000000 + cal.get(5) * 1000000 + cal.get(11) * 10000 + cal.get(12) * 100 + cal.get(13);
                int differ = now - seq2;
                if (differ / 100 >= deliver_Timeout) {
                    tmp.add(value);
                    it.remove();
                    continue;
                }
            }
        }

        LinkedList var16 = this.deliverUnResp;
        synchronized(this.deliverUnResp) {
            this.deliverUnResp.addAll(tmp);
        }
    }

    private void submitUnresp2DB() {
        Object[] unresp = (Object[])null;
        LinkedList var2 = this.submitUnResp;
        synchronized(this.submitUnResp) {
            unresp = this.submitUnResp.toArray();
            this.submitUnResp.clear();
        }

        if (unresp.length != 0) {
            this.LogHandler.debug("***************submitUnResp:" + unresp.length);
            List list = new ArrayList();

            for(int i = 0; i < unresp.length; ++i) {
                Map.Entry entry = (Map.Entry)unresp[i];
                SGIP_Command post = (SGIP_Command)entry.getKey();
                SGIP_Command pre = (SGIP_Command)entry.getValue();
                ByteBuffer postsendByte = this.cmd2ByteBuffer(post);
                ByteBuffer presendByte = this.cmd2ByteBuffer(pre);
                long[] seq = new long[]{post.getSeqno_1(), (long)post.getSeqno_2(), (long)post.getSeqno_3(), pre.getSeqno_1(), (long)pre.getSeqno_2(), (long)pre.getSeqno_3()};
                Object[] obj = new Object[]{postsendByte, presendByte, seq};
                list.add(obj);
            }

            OperData data = new OperData();
            data.insertSubmitUnresp(list);
        }
    }

    private void reportUnresp2DB() {
        Object[] unresp = (Object[])null;
        LinkedList var2 = this.reportUnResp;
        synchronized(this.reportUnResp) {
            unresp = this.reportUnResp.toArray();
            this.reportUnResp.clear();
        }

        if (unresp.length != 0) {
            this.LogHandler.debug("***************reportUnResp:" + unresp.length);
            List list = new ArrayList();

            for(int i = 0; i < unresp.length; ++i) {
                SGIP_Report value = (SGIP_Report)unresp[i];
                ByteBuffer sendByte = this.cmd2ByteBuffer(value);
                long[] seq = new long[]{value.getSeqno_1(), (long)value.getSeqno_2(), (long)value.getSeqno_3(), value.getSeq_1(), (long)value.getSeq_2(), (long)value.getSeq_3()};
                Object[] obj = new Object[]{sendByte, seq};
                list.add(obj);
            }

            OperData data = new OperData();
            data.insertReportUnresp(list);
        }
    }

    private void deliverUnresp2DB() {
        Object[] unresp = (Object[])null;
        LinkedList var2 = this.deliverUnResp;
        synchronized(this.deliverUnResp) {
            unresp = this.deliverUnResp.toArray();
            this.deliverUnResp.clear();
        }

        if (unresp.length != 0) {
            this.LogHandler.debug("***************deliverUnResp:" + unresp.length);
            List list = new ArrayList();

            for(int i = 0; i < unresp.length; ++i) {
                SGIP_Command value = (SGIP_Command)unresp[i];
                ByteBuffer sendByte = this.cmd2ByteBuffer(value);
                long[] seq = new long[]{value.getSeqno_1(), (long)value.getSeqno_2(), (long)value.getSeqno_3()};
                Object[] obj = new Object[]{sendByte, seq};
                list.add(obj);
            }

            OperData data = new OperData();
            data.insertDeliverUnresp(list);
        }
    }

    private void submitresp2DB() {
        SGIP_Submit[] resp = (SGIP_Submit[])null;
        LinkedList var2 = this.submitResp;
        synchronized(this.submitResp) {
            resp = (SGIP_Submit[])this.submitResp.toArray(new SGIP_Submit[0]);
            this.submitResp.clear();
        }

        if (resp.length != 0) {
            this.LogHandler.debug("***************submitresp:" + resp.length);
            log4Command data = new log4Command(resp);
            String info = "";

            try {
                info = data.toLog();
            } catch (Exception var5) {
                info = var5.getMessage();
            }

            SmsCenter.submitLog.info(info);
        }
    }

    private void reportresp2DB() {
        SGIP_Report[] resp = new SGIP_Report[0];
        LinkedList var2 = reportResp;
        synchronized(reportResp) {
            resp = (SGIP_Report[])reportResp.toArray(resp);
            reportResp.clear();
        }

        if (resp.length != 0) {
            this.LogHandler.debug("***************reportResp:" + resp.length);
            log4Command data = new log4Command(resp);
            String info = "";

            try {
                info = data.toLog();
            } catch (Exception var5) {
                info = var5.getMessage();
            }

            SmsCenter.reportLog.info(info);
        }
    }

    private void deliverresp2DB() {
        SGIP_Deliver[] resp = (SGIP_Deliver[])null;
        LinkedList var2 = deliverResp;
        synchronized(deliverResp) {
            SGIP_Deliver[] com = new SGIP_Deliver[deliverResp.size()];
            deliverResp.toArray(com);
            resp = com;
            deliverResp.clear();
        }

        if (resp.length != 0) {
            this.LogHandler.debug("***************deliverResp:" + resp.length);
            log4Command data = new log4Command(resp);
            String info = "";

            try {
                info = data.toLog();
            } catch (Exception var5) {
                info = var5.getMessage();
            }

            SmsCenter.deliverLog.info(info);
        }
    }

    private void submitresend2DB() {
        Object[] resp = (Object[])null;
        LinkedList var2 = this.submitResend;
        synchronized(this.submitResend) {
            resp = this.submitResend.toArray();
            this.submitResend.clear();
        }

        if (resp.length != 0) {
            this.LogHandler.debug("***************submitResend:" + resp.length);
            OperData data = new OperData();
            data.submitResp(resp);
        }
    }

    private void reportresend2DB() {
        Object[] resp = (Object[])null;
        LinkedList var2 = reportResend;
        synchronized(reportResend) {
            resp = reportResend.toArray();
            reportResend.clear();
        }

        if (resp.length != 0) {
            this.LogHandler.debug("***************reportResend:" + resp.length);
            OperData data = new OperData();
            data.reportResp(resp);
        }
    }

    private void deliverresend2DB() {
        Object[] resp = (Object[])null;
        LinkedList var2 = deliverResend;
        synchronized(deliverResend) {
            resp = deliverResend.toArray();
            deliverResend.clear();
        }

        if (resp.length != 0) {
            this.LogHandler.debug("***************deliverResend:" + resp.length);
            OperData data = new OperData();
            data.deliverResp(resp);
        }
    }
}
