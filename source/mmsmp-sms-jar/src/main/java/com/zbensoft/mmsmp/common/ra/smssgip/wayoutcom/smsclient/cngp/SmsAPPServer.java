package com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.smsclient.cngp;

import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.cngpapi.CNGP_Command;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.cngpapi.CNGP_Deliver;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.cngpapi.CNGP_DeliverResp;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.common.SmsHandler;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.sgipapi.SGIP_Command;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class SmsAPPServer extends SmsHandler {
    int port = 8801;
    String localhost = null;
    List packetBuffer = new ArrayList();
    int count = 0;
    ServerSocket server = null;
    Socket client = null;
    int datacount = 0;
    private static Thread serverThread = null;

    public SmsAPPServer(String localIP, int localPort) {
        this.port = localPort;
        this.localhost = localIP;
    }

    public boolean initialize() {
        System.out.println("SMS,AgentServer initialization");

        try {
            this.server = new ServerSocket();
            InetSocketAddress isa = new InetSocketAddress(this.port);
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
        SmsAPPServer app = new SmsAPPServer("127.0.0.1", 8992);
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
                CNGP_Command sgip_Cmd = null;
                Object var2 = null;

                try {
                    sgip_Cmd = CNGP_Command.read(this.sc);
                    if (sgip_Cmd == null) {
                        return;
                    }
                } catch (Exception var10) {
                    System.out.println("SMS,Server, sgip_Cmd.read(sc) error");
                    var10.printStackTrace();
                    this.sc.close();
                    System.out.println("SMS,Server close the socket");
                    return;
                }

                switch(sgip_Cmd.getCommandID()) {
                    case 3:
                        CNGP_Deliver deliver = (CNGP_Deliver)sgip_Cmd;
                        CNGP_DeliverResp deliverResp = new CNGP_DeliverResp(deliver, 0);
                        deliverResp.write(this.sc);
                        int isReport = deliver.getIsReport();
                        System.err.println(deliver);
                        switch(isReport) {
                            case 0:
                                new Integer(deliver.getSeqID());
                                break;
                            case 1:
                                byte[] msgID = deliver.getReportMsgID();
                                System.err.println(++SmsAPPServer.this.datacount + " server msgID:" + "status:");
                                String info = "";

                                for(int i = 0; i < msgID.length; ++i) {
                                    info = info + " " + msgID[i];
                                }

                                System.err.println(info);
                                Connection conn = null;
                                Object var9 = null;
                        }
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

