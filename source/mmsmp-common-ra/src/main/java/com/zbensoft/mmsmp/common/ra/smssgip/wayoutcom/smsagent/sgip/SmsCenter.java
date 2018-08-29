package com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.smsagent.sgip;

import org.apache.log4j.Logger;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class SmsCenter {
    public static HashMap report = new HashMap();
    public static HashMap deliver = new HashMap();
    public static HashMap parameter = new HashMap();
    public static HashMap login = new HashMap();
    public static Logger submitLog = null;
    public static Logger reportLog = null;
    public static Logger deliverLog = null;

    public SmsCenter() {
    }

    private void AppInfoLoad() {
        OperData data = new OperData();
        data.loadAppInfo(report, deliver);
    }

    private void loadCheckTask() {
        int checkFreq = false;
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
        submitLog = Logger.getLogger("SUBMIT");
        reportLog = Logger.getLogger("REPORT");
        deliverLog = Logger.getLogger("DELIVER");
        OperData data = new OperData();
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
                System.out.println("SPClient address null");
            } else {
                ip = address.getAddress().getHostAddress();
                port = address.getPort();
                SmsSPClientSocket client = new SmsSPClientSocket(ip, port);
                address = (InetSocketAddress)info.get("SPServer");
                if (address == null) {
                    System.out.println("SPServer address null");
                } else {
                    ip = address.getAddress().getHostAddress();
                    port = address.getPort();
                    SmsSPServerSocket server = new SmsSPServerSocket(ip, port);
                    client.setLogHandler(logger);
                    SmsProcess smsProcess = new SmsProcess();
                    server.setDownHandler(smsProcess);
                    server.setLogHandler(logger);
                    smsProcess.setUpHandler(client);
                    smsProcess.setDownHandler(smsAgentClient);
                    smsProcess.setLogHandler(logger);
                    smsAgentServer.setDownHandler(smsProcess);
                    smsAgentServer.setLogHandler(logger);
                    smsAgentClient.setLogHandler(logger);
                    smsProcess.start();
                    server.start();
                    client.start(false);
                    smsAgentServer.start();
                    smsAgentClient.start(false);
                }
            }
        }
    }
}

