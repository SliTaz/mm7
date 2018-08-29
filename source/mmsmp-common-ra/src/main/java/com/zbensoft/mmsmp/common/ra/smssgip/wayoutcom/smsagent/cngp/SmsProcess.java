package com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.smsagent.cngp;

import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.cmppapi.ProduceSeq;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.cngpapi.CNGP_Deliver;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.cngpapi.CNGP_Submit;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.common.SmsHandler;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.common.log4Command;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.dbAccess.OperData4cngp;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.produce_consume.Channel;
import com.zbensoft.mmsmp.common.ra.vas.commons.tcp.impl.TypeConvert;

import java.nio.ByteBuffer;
import java.util.*;

public class SmsProcess extends SmsHandler {
    private Channel ComdQ;
    private Channel RespQ;
    private Thread RecvThread;
    private Thread SendThread;
    private int countRecv;
    private int countSend;
    public static Map submitQueue = new LinkedHashMap();
    private LinkedList submitResp;
    private LinkedList submitUnResp;
    private LinkedList submitResend;
    public static LinkedList submitTimeoutResp = new LinkedList();
    private LinkedList submitTimeout;
    public static Map deliverQueue = new LinkedHashMap();
    public static LinkedList deliverResp = new LinkedList();
    public static LinkedList deliverResend = new LinkedList();
    private LinkedList deliverUnResp;
    private static int CHANNEL_SIZE = 100;
    int submitresend;

    public SmsProcess() {
        this.ComdQ = new Channel(CHANNEL_SIZE);
        this.RespQ = new Channel(CHANNEL_SIZE);
        this.RecvThread = null;
        this.SendThread = null;
        this.countRecv = 0;
        this.countSend = 0;
        this.submitResp = new LinkedList();
        this.submitUnResp = new LinkedList();
        this.submitResend = new LinkedList();
        this.submitTimeout = new LinkedList();
        this.deliverUnResp = new LinkedList();
        this.submitresend = 0;
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

    private void thisLayerReceive(Object cngp_Cmd) throws InterruptedException {
        this.ComdQ.put(cngp_Cmd);
    }

    private void thisLayerSend(Object request) throws InterruptedException {
        this.RespQ.put(request);
    }

    private int dealCommand(Object s) {
        CNGP_Deliver deliver = (CNGP_Deliver)s;
        String strContent = null;
        this.LogHandler.debug(deliver.toString());
        int isReport = deliver.getIsReport();
        switch(isReport) {
            case 0:
                Integer key = new Integer(deliver.getSeqID());
                Object[] obj = new Object[]{new GregorianCalendar(), deliver};
                Map var20 = deliverQueue;
                synchronized(deliverQueue) {
                    deliverQueue.put(key, obj);
                }

                this.DownHandler.send(deliver);
                break;
            case 1:
                byte[] msgID = deliver.getReportMsgID();
                byte[] id = deliver.getMsgID();
                SubmitKey msg = new SubmitKey((CNGP_Submit)null, msgID);
                Map var9 = submitQueue;
                Object[] value;
                synchronized(submitQueue) {
                    value = (Object[])submitQueue.remove(msg);
                }

                LinkedList var21;
                if (value != null) {
                    var21 = this.submitResp;
                    synchronized(this.submitResp) {
                        this.submitResp.addLast(value[0]);
                    }
                } else {
                    var21 = this.submitResend;
                    synchronized(this.submitResend) {
                        this.submitResend.addLast(msgID);
                    }

                    System.err.println("********************submitresend:" + ++this.submitresend);
                }

                if (value != null) {
                    msgID = (byte[])value[1];
                } else {
                    OperData4cngp data = new OperData4cngp(this.LogHandler);
                    msgID = data.getMsgID(msgID);
                }

                if (msgID != null) {
                    CNGP_Deliver report = new CNGP_Deliver(deliver, msgID);
                    int key2 = report.getSeqID();
                    Object[] obj2 = new Object[]{new GregorianCalendar(), report};
                    Map var12 = deliverQueue;
                    synchronized(deliverQueue) {
                        deliverQueue.put(new Integer(key2), obj2);
                    }

                    this.DownHandler.send(report);
                }
        }

        return 0;
    }

    private Object[] checkCommand() throws InterruptedException {
        Object[] tmp = (Object[])null;
        tmp = this.ComdQ.removeAll();
        return tmp;
    }

    private void dealResp(Object s) {
        Object[] obj = (Object[])s;
        CNGP_Submit ss = (CNGP_Submit)obj[0];
        this.LogHandler.debug(ss.toString());
        int seq = ProduceSeq.getSeq();
        CNGP_Submit ssubmit = new CNGP_Submit(ss, seq);
        SubmitKey key = new SubmitKey(ssubmit, (byte[])null);
        Map var7 = submitQueue;
        synchronized(submitQueue) {
            submitQueue.put(key, s);
        }

        this.UpHandler.send(ssubmit);
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
                SmsProcess.this.submitresend();
                SmsProcess.this.deliverTimeout();
                SmsProcess.this.submitResend2DB();
                SmsProcess.this.submitresendResp2DB();
                SmsProcess.this.deliverUnresp2DB();
                SmsProcess.this.deliverresend2DB();
            }
        }, 0L, (long)(checkFreq * 60 * 1000));
        Thread.currentThread().setPriority(5);
        (new Timer()).schedule(new TimerTask() {
            public void run() {
                SmsProcess.this.submitresp2DB();
                SmsProcess.this.deliverresp2DB();
            }
        }, 0L, (long)(dataFreq * 60 * 1000));
        this.LogHandler.info("SMS, database handle start");
    }

    private void resendTask() {
        OperData4cngp data = new OperData4cngp(this.LogHandler);
        List list = data.submitResend();

        int i;
        for(i = 0; i < list.size(); ++i) {
            this.UpHandler.send(new CNGP_Submit(ByteBuffer.wrap((byte[])list.get(i))));
        }

        list = data.deliverResend();

        for(i = 0; i < list.size(); ++i) {
            this.DownHandler.send(new CNGP_Deliver(ByteBuffer.wrap((byte[])list.get(i))));
        }

    }

    private void moveTask() {
        OperData4cngp data = new OperData4cngp(this.LogHandler);
        data.moveData();
    }

    private List collectTimeout(int timeout, boolean flag) {
        List tmp = new ArrayList();
        Map var4 = submitQueue;
        synchronized(submitQueue) {
            Set entry = submitQueue.entrySet();
            Iterator it = entry.iterator();

            while(it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                SubmitKey key = (SubmitKey)pair.getKey();
                Object[] value = (Object[])pair.getValue();
                boolean con = true;
                if (con) {
                    byte[] msgID = (byte[])value[1];
                    byte[] time = new byte[4];
                    System.arraycopy(msgID, 3, time, 0, 4);
                    int totalMin = 0;
                    int exp = 1;
                    int len = time.length;

                    int differ;
                    for(int i = 0; i < len; ++i) {
                        byte[] temp = new byte[]{time[len - 1 - i]};
                        differ = TypeConvert.BCDtoINT(temp);
                        totalMin += differ * exp;
                        switch(i) {
                            case 0:
                                exp *= 60;
                                break;
                            case 1:
                                exp *= 24;
                                break;
                            case 2:
                                exp *= 30;
                        }
                    }

                    Calendar cal = new GregorianCalendar();
                    int now = (cal.get(2) + 1) * 30 * 24 * 60;
                    now += cal.get(5) * 24 * 60;
                    now += cal.get(11) * 60;
                    now += cal.get(12);
                    differ = now - totalMin;
                    if (differ <= timeout) {
                        break;
                    }

                    if (flag) {
                        Object[] obj = new Object[]{key.getSubmit(), value};
                        tmp.add(obj);
                        it.remove();
                    } else {
                        tmp.add(key.getSubmit());
                    }
                }
            }

            return tmp;
        }
    }

    private void submitTimeout() {
    }

    private void submitresend() {
        new ArrayList();
        Integer hm = (Integer)SmsCenter.parameter.get("submit_Timeout");
        int submit_Timeout = hm;
        List tmp = this.collectTimeout(submit_Timeout, true);
        LinkedList var4 = this.submitUnResp;
        synchronized(this.submitUnResp) {
            this.submitUnResp.addAll(tmp);
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
                Object[] value = (Object[])pair.getValue();
                Calendar cal = (Calendar)value[0];
                CNGP_Deliver deliver = (CNGP_Deliver)value[1];
                int[] time = new int[]{cal.get(12), cal.get(11), cal.get(5), cal.get(2) + 1};
                int totalMin = 0;
                int exp = 1;
                int len = time.length;

                int i;
                int differ;
                for(i = 0; i < len; ++i) {
                    differ = time[i];
                    totalMin += differ * exp;
                    switch(i) {
                        case 0:
                            exp *= 60;
                            break;
                        case 1:
                            exp *= 24;
                            break;
                        case 2:
                            exp *= 30;
                    }
                }

                Calendar cal2 = new GregorianCalendar();
                i = (cal2.get(2) + 1) * 30 * 24 * 60;
                i += cal2.get(5) * 24 * 60;
                i += cal2.get(11) * 60;
                i += cal2.get(12);
                differ = i - totalMin;
                if (differ <= deliver_Timeout) {
                    break;
                }

                tmp.add(deliver);
                it.remove();
            }
        }

        LinkedList var20 = this.deliverUnResp;
        synchronized(this.deliverUnResp) {
            this.deliverUnResp.addAll(tmp);
        }
    }

    private void submitResend2DB() {
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
                Object[] obj = (Object[])unresp[i];
                CNGP_Submit submit = (CNGP_Submit)obj[0];
                int seq = submit.getSeqID();
                ByteBuffer postBuf = submit.getBuf();
                Object[] value = (Object[])obj[1];
                ByteBuffer preBuf = ((CNGP_Submit)value[0]).getBuf();
                byte[] msgID = (byte[])value[1];
                Object[] object = new Object[]{postBuf, preBuf, new Integer(seq), msgID};
                list.add(object);
            }

            OperData4cngp data = new OperData4cngp(this.LogHandler);
            data.insertSubmitResend(list);
        }
    }

    private void submitUnresp2DB() {
        Object[] unresp = (Object[])null;
        LinkedList var2 = this.submitTimeout;
        synchronized(this.submitTimeout) {
            unresp = this.submitTimeout.toArray();
            this.submitTimeout.clear();
        }

        if (unresp.length != 0) {
            this.LogHandler.debug("***************reportUnResp:" + unresp.length);
            List list = new ArrayList();

            for(int i = 0; i < unresp.length; ++i) {
                CNGP_Submit submit = (CNGP_Submit)unresp[i];
                int seq = submit.getSeqID();
                ByteBuffer buf = submit.getBuf();
                Object[] obj = new Object[]{buf, new Integer(seq)};
                list.add(obj);
            }

            OperData4cngp data = new OperData4cngp(this.LogHandler);
            data.insertSubmitUnresp(list);
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
                CNGP_Deliver deliver = (CNGP_Deliver)unresp[i];
                int seq = deliver.getSeqID();
                ByteBuffer buf = deliver.getBuf();
                Object[] obj = new Object[]{buf, new Integer(seq)};
                list.add(obj);
            }

            OperData4cngp data = new OperData4cngp(this.LogHandler);
            data.insertDeliverUnresp(list);
        }
    }

    private void submitresp2DB() {
        CNGP_Submit[] resp = (CNGP_Submit[])null;
        LinkedList var2 = this.submitResp;
        synchronized(this.submitResp) {
            resp = (CNGP_Submit[])this.submitResp.toArray(new CNGP_Submit[0]);
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

    private void deliverresp2DB() {
        CNGP_Deliver[] resp = (CNGP_Deliver[])null;
        LinkedList var2 = deliverResp;
        synchronized(deliverResp) {
            CNGP_Deliver[] com = new CNGP_Deliver[deliverResp.size()];
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

    private void submitresendResp2DB() {
        Object[] resp = (Object[])null;
        LinkedList var2 = this.submitResend;
        synchronized(this.submitResend) {
            resp = this.submitResend.toArray();
            this.submitResend.clear();
        }

        if (resp.length != 0) {
            this.LogHandler.debug("***************submitResend:" + resp.length);
            OperData4cngp data = new OperData4cngp(this.LogHandler);
            data.submitResp(resp);
        }
    }

    private void submitTimeoutResp2DB() {
        Object[] resp = (Object[])null;
        LinkedList var2 = submitTimeoutResp;
        synchronized(submitTimeoutResp) {
            resp = submitTimeoutResp.toArray();
            submitTimeoutResp.clear();
        }

        if (resp.length != 0) {
            this.LogHandler.debug("***************reportResend:" + resp.length);
            OperData4cngp data = new OperData4cngp(this.LogHandler);
            data.submitTimeoutResp(resp);
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
            OperData4cngp data = new OperData4cngp(this.LogHandler);
            data.deliverResp(resp);
        }
    }
}
