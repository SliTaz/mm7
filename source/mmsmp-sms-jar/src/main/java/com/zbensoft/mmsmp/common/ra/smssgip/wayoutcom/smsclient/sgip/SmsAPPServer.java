package com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.smsclient.sgip;

import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.common.SmsHandler;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.sgipapi.*;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.smsagent.sgip.SmsDataTrans;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class SmsAPPServer extends SmsHandler {
    int port = 8801;
    String localhost = null;
    List packetBuffer = new ArrayList();
    int count = 0;
    ServerSocket server = null;
    Socket client = null;
    private static Thread serverThread = null;

    public SmsAPPServer(String localIP, int localPort) {
        this.port = localPort;
        this.localhost = localIP;
    }

    public boolean initialize() {
        System.out.println("SMS,AgentServer initialization");

        try {
            this.server = new ServerSocket();
            InetSocketAddress isa = new InetSocketAddress(this.localhost, this.port);
            this.server.bind(isa);
            return true;
        } catch (Exception var2) {
            System.out.println("AgentServer初始化失败");
            var2.printStackTrace();
            return false;
        }
    }

    public void finalize() throws IOException {
        if (!this.server.isClosed()) {
            this.server.close();
        }

        System.out.println("SMS,Server close the socket channel");
        System.exit(1);
    }

    public void mainThread() throws IOException, InterruptedException {
        if (this.initialize()) {
            while(true) {
                while(true) {
                    this.client = this.server.accept();
                    this.client.setKeepAlive(true);
                    this.client.setSoTimeout(0);
                    if (this.client == null) {
                        System.out.println("客户端接入异常，client=null");
                    } else {
                        System.out.println("有客户端接入，远端PORT 是：" + this.client.getPort());
                        sreadThread sread = new sreadThread(this.client);
                        sread.start();
                    }
                }
            }
        }

    }

    public void addsmgCmdQ(Object sgipCmd) {
        List var2 = this.packetBuffer;
        synchronized(this.packetBuffer) {
            SGIP_Command comm = (SGIP_Command)sgipCmd;
            System.out.println("seq2:" + comm.getSeqno_2() + "seq3:" + comm.getSeqno_3());
            if (++this.count % 10 == 0) {
                System.out.println("在SmsAPPServer收到report" + this.count);
            }

        }
    }

    public void start() {
        serverThread = new Thread(new Runnable() {
            public void run() {
                try {
                    SmsAPPServer.this.mainThread();
                } catch (InterruptedException var2) {
                    System.out.println("SMS,Server,  server thread error");
                    var2.printStackTrace();
                } catch (IOException var3) {
                    System.out.println("SMS,Server,  server thread error");
                    var3.printStackTrace();
                }

            }
        });
        serverThread.start();
    }

    public static void main(String[] arg) {
        SmsAPPServer app = new SmsAPPServer("127.0.0.1", 9992);
        Logger logger = Logger.getLogger("log4j.properties");
        logger.setLevel(Level.DEBUG);
        app.setLogHandler(logger);
        app.start();
    }

    public class sreadThread extends Thread {
        Socket sc = null;
        boolean brun = false;

        public sreadThread(Socket sc) {
            this.sc = sc;
            this.brun = true;
        }

        public void readFromChannel() throws IOException, InterruptedException {
            while(true) {
                SGIP_Command sgip_Cmd = null;
                SGIP_Command sgip_tmp = new SGIP_Command();

                try {
                    sgip_Cmd = sgip_tmp.read(this.sc);
                    if (sgip_Cmd == null) {
                        return;
                    }
                } catch (Exception var6) {
                    System.out.println("SMS,Server, sgip_Cmd.read(sc) error");
                    var6.printStackTrace();
                    this.sc.close();
                    System.out.println("SMS,Server close the socket");
                    return;
                }

                switch(sgip_Cmd.getCommandID()) {
                    case 4:
                        SGIP_Deliver deliver = (SGIP_Deliver)sgip_Cmd;
                        System.out.println("**************从SMG收到DELIVER包在SmsSPServer.readFromChannel********************");
                        SGIP_DeliverResp deliverresp = new SGIP_DeliverResp(sgip_Cmd.getHead());
                        deliverresp.SetResult(0);
                        deliverresp.write(this.sc);
                        deliver.readbody();
                        System.out.println("Deliver msg: " + deliver.getCommandID() + "-" + deliver.getUserNumber() + "-" + deliver.getMessageLength() + "-" + deliver.getMessageContent());
                        if (!deliver.getUserNumber().startsWith("86" + SmsDataTrans.strValidNumber) && !SmsDataTrans.strValidNumber.equalsIgnoreCase("NOLIMIT")) {
                            System.out.println(deliver.getUserNumber() + ", It is Invalid number, ignored!");
                        } else {
                            SmsAPPServer.this.addsmgCmdQ(sgip_Cmd);
                        }

                        (new StringBuffer("SPServer(R-Deliver) [SQ]: ")).append(deliver.getSeqno_1()).append("-").append(deliver.getSeqno_2()).append("-").append(deliver.getSeqno_3()).append("[PN]: ").append(deliver.getUserNumber()).append("[MC]: ").append(deliver.getMessageCoding()).append("[ML]: ").append(deliver.getMessageLength()).append("[MC]: ").append(deliver.getMessageContent()).append("[RespSeq] ").append(deliverresp.getSeqno_1()).append("-").append(deliverresp.getSeqno_2()).append("-").append(deliverresp.getSeqno_3()).toString();
                        break;
                    case 5:
                        SGIP_Report report = (SGIP_Report)sgip_Cmd;
                        SGIP_ReportResp reportresp = new SGIP_ReportResp(sgip_Cmd.getHead());
                        reportresp.SetResult(0);
                        reportresp.write(this.sc);
                        report.readbody();
                        SmsAPPServer.this.addsmgCmdQ(sgip_Cmd);
                        (new StringBuffer("SPServer(R-Report) [SQ]: ")).append(report.getSeqno_1()).append("-").append(report.getSeqno_2()).append("-").append(report.getSeqno_3()).append("[PN]: ").append(report.getUserNumber()).append("[RS]: ").append(report.getState()).append("[EC]: ").append(report.getErrorCode()).toString();
                        break;
                    case 17:
                        System.out.println(">>>>NioServer,It is a USERRPT!");
                        SGIP_UserReport userrpt = (SGIP_UserReport)sgip_Cmd;
                        SGIP_UserReportResp userrptresp = new SGIP_UserReportResp(sgip_Cmd.getHead());
                        userrptresp.SetResult(0);
                        userrptresp.write(this.sc);
                        userrpt.readbody();
                        SmsAPPServer.this.addsmgCmdQ(sgip_Cmd);
                }
            }
        }

        public void run() {
            String var2;
            try {
                System.out.println("SMS,Server, listen thread start...");
                this.readFromChannel();
                this.sc.close();
                System.out.println("SMS,Server, listen thread stop and exit...ok");
                String strlog = "SMS,Server, Listen thread stop and exit";
                return;
            } catch (InterruptedException var3) {
                System.out.println("SMS,Server, sreadthread error");
                var3.printStackTrace();
                var2 = "SMS,Server, Run InterruptedException";
            } catch (IOException var4) {
                System.out.println("SMS,Server, sreadthread error");
                var4.printStackTrace();
                var2 = "SMS,Server, Run IOException";
            }

        }
    }
}

