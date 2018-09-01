package com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.smsagent.smgp;

import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.dbAccess.OperData4smgp;
import org.apache.log4j.Logger;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class SmsCenter {
    public static HashMap submit = new HashMap();
    public static HashMap report = new HashMap();
    public static HashMap deliver = new HashMap();
    public static HashMap parameter = new HashMap();
    public static HashMap login = new HashMap();
    public static Logger submitLog = null;
    public static Logger deliverLog = null;

    public SmsCenter() {
    }

    private void AppInfoLoad() {
        OperData4smgp data = new OperData4smgp();
        data.loadAppInfo(submit, report, deliver);
    }

    private void loadCheckTask() {
        Integer hm = (Integer)parameter.get("apps_Check_Freq");
        int checkFreq = hm;
        Thread.currentThread().setPriority(6);
        (new Timer()).schedule(new TimerTask() {
            public void run() {
                SmsCenter.this.AppInfoLoad();
            }
        }, 0L, (long)(checkFreq * 60 * 1000));
        Thread.currentThread().setPriority(5);
    }

    public static void main(String[] args) {
        Logger logger = Logger.getLogger("LOG");
        submitLog = Logger.getLogger("SMGP_SUBMIT");
        deliverLog = Logger.getLogger("SMGP_DELIVER");
        OperData4smgp data = new OperData4smgp();
        HashMap info = data.loadConfigInfo();
        parameter = data.loadParameter();
        login = data.loadLogin();
        (new SmsCenter()).loadCheckTask();
        InetSocketAddress address = (InetSocketAddress)info.get("AgentServer");
        if (address == null) {
            System.out.println("AgentServer address null");
        } else {
            String ip = address.getAddress().getHostAddress();
            int port = address.getPort();
            SmsAgentServerSocket smsAgentServer = new SmsAgentServerSocket(ip, port);
            SmsAgentClientSocket smsAgentClient = new SmsAgentClientSocket();
            address = (InetSocketAddress)info.get("SPClient");
            if (address == null) {
                System.out.println("AgentServer address null");
            } else {
                ip = address.getAddress().getHostAddress();
                port = address.getPort();
                SmsSPClientSocket client = new SmsSPClientSocket(ip, port);
                client.setLogHandler(logger);
                SmsProcess smsProcess = new SmsProcess();
                client.setDownHandler(smsProcess);
                smsProcess.setUpHandler(client);
                smsProcess.setDownHandler(smsAgentClient);
                smsProcess.setLogHandler(logger);
                smsAgentServer.setDownHandler(smsProcess);
                smsAgentServer.setLogHandler(logger);
                smsAgentClient.setLogHandler(logger);

                try {
                    smsProcess.start();
                } catch (Exception var12) {
                    var12.printStackTrace();
                }

                client.start(true);
                smsAgentServer.start();
                smsAgentClient.start(false);
            }
        }
    }
}
