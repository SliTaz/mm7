package com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.smsclient.cmpp;

import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.cmppapi.CMPP_Command;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.cmppapi.CMPP_Deliver;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.cmppapi.CMPP_DeliverResp;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.common.SmsHandler;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.produce_consume.Channel;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SmsAPPServer extends SmsHandler {
    int port = 8801;
    String localhost = null;
    List packetBuffer = new ArrayList();
    int count = 0;
    ServerSocket server = null;
    Socket client = null;
    public static int error = 0;
    private static Thread serverThread = null;
    private Channel socketQueue = new Channel(50);
    private static Logger log = Logger.getLogger(SmsAPPServer.class);

    public SmsAPPServer(String localIP, int localPort) {
        this.port = localPort;
        this.localhost = localIP;
    }

    public boolean initialize() {
        log.info("SMS,AgentServer initialization");

        try {
            this.server = new ServerSocket();
            InetSocketAddress isa = new InetSocketAddress(this.port);
            this.server.bind(isa);
            return true;
        } catch (Exception var2) {
            log.error("AgentServer初始化失败", var2);
            return false;
        }
    }

    public void finalize() throws IOException {
        if (!this.server.isClosed()) {
            this.server.close();
        }

        log.error("SMS,Server close the socket channel");
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
                        log.debug("客户端接入异常，client=null");
                    } else {
                        log.debug("有客户端接入，远端PORT 是：" + this.client.getPort());
                        this.socketQueue.put(this.client);
                    }
                }
            }
        }

    }

    public void start() {
        serverThread = new Thread(new Runnable() {
            public void run() {
                try {
                    SmsAPPServer.this.mainThread();
                } catch (InterruptedException var2) {
                    SmsAPPServer.log.error("SMS,Server,  server thread error", var2);
                } catch (IOException var3) {
                    SmsAPPServer.log.error("SMS,Server,  server thread error", var3);
                }

            }
        });
        serverThread.setName("serverThread");
        serverThread.start();
        sreadThread sread = new sreadThread(this.socketQueue);
        sread.setName("sreadThread");
        sread.start();
    }

    public static void main(String[] arg) {
        SmsAPPServer app = new SmsAPPServer("127.0.0.1", 7992);
        Logger logger = Logger.getLogger("log4j.properties");
        logger.setLevel(Level.DEBUG);
        app.setLogHandler(logger);
        app.start();
    }

    public static void startAppServer(int port) {
        SmsAPPServer app = new SmsAPPServer("127.0.0.1", port);
        app.start();
    }

    public class sreadThread extends Thread {
        boolean brun = false;
        Channel socketQueue;

        public sreadThread(Channel socketQueue) {
            this.socketQueue = socketQueue;
        }

        public void readFromChannel() throws IOException, InterruptedException {
            while(true) {
                CMPP_Command sgip_Cmd = null;
                CMPP_Command sgip_tmp = null;
                Socket sc = null;

                try {
                    sc = (Socket)this.socketQueue.remove();
                    sgip_Cmd = CMPP_Command.read(sc);
                    if (sgip_Cmd == null) {
                        sc.close();
                        continue;
                    }
                } catch (Exception var11) {
                    SmsAPPServer.log.error("SMS,Server, sgip_Cmd.read(sc) error", var11);
                    sc.close();
                    SmsAPPServer.log.error("SMS,Server close the socket");
                    continue;
                }

                switch(sgip_Cmd.getCommandID()) {
                    case 5:
                        CMPP_Deliver deliver = (CMPP_Deliver)sgip_Cmd;
                        CMPP_DeliverResp deliverResp = new CMPP_DeliverResp(deliver);
                        deliverResp.write(sc);
                        int isReport = deliver.getIsReport();
                        switch(isReport) {
                            case 0:
                                new Integer(deliver.getSeqID());
                                break;
                            case 1:
                                long msgID = deliver.getReportMsgID();
                                Map var9 = SmsClient.msgIDStatus;
                                synchronized(SmsClient.msgIDStatus) {
                                    SmsClient.msgIDStatus.put(new Long(msgID), deliver.getReportStat());
                                }
                        }
                }
            }
        }

        public void run() {
            String var2;
            try {
                SmsAPPServer.log.info("SMS,Server, listen thread start...");
                this.readFromChannel();
                SmsAPPServer.log.info("SMS,Server, listen thread stop and exit...ok");
                String strlog = "SMS,Server, Listen thread stop and exit";
                return;
            } catch (InterruptedException var3) {
                SmsAPPServer.log.error("SMS,Server, sreadthread error", var3);
                var2 = "SMS,Server, Run InterruptedException";
            } catch (IOException var4) {
                SmsAPPServer.log.error("SMS,Server, sreadthread error", var4);
                var2 = "SMS,Server, Run IOException";
            }

        }
    }
}

