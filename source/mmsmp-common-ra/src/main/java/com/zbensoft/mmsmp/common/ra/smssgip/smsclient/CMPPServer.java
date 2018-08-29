package com.zbensoft.mmsmp.common.ra.smssgip.smsclient;

import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.cmppapi.CMPP_Command;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.cmppapi.CMPP_Deliver;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.cmppapi.CMPP_DeliverResp;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.cmppapi.CMPP_Query_Resp;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class CMPPServer {
    private static Logger log = Logger.getLogger(CMPPServer.class);
    ServerSocket server = null;
    Socket client = null;
    private static Thread serverThread = null;
    private String localhost;
    private int port;

    public CMPPServer(String localIP, int localPort) {
        this.port = localPort;
        this.localhost = localIP;
    }

    public boolean initialize() {
        log.info("SMS,AgentServer initialization");

        try {
            this.server = new ServerSocket();
            InetSocketAddress isa = new InetSocketAddress(this.localhost, this.port);
            this.server.bind(isa);
            return true;
        } catch (Exception var2) {
            log.error("AgentServer初始化失败!/n" + var2.getLocalizedMessage());
            var2.printStackTrace();
            return false;
        }
    }

    public void finalize() throws IOException {
        if (!this.server.isClosed()) {
            this.server.close();
        }

        log.info("SMS,Server close the socket channel");
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
                        System.out.println("客户端接入异常，smsclient=null");
                    } else {
                        System.out.println("有客户端接入，远端PORT 是：" + this.client.getPort());
                        CMPPServer.ReadThread sread = new CMPPServer.ReadThread(this.client);
                        sread.start();
                    }
                }
            }
        }

    }

    public void start() {
        serverThread = new Thread(new Runnable() {
            public void run() {
                try {
                    CMPPServer.this.mainThread();
                } catch (InterruptedException var2) {
                    CMPPServer.log.error("SMS,Server,  server thread error");
                    var2.printStackTrace();
                } catch (IOException var3) {
                    CMPPServer.log.error("SMS,Server,  server thread error");
                    var3.printStackTrace();
                }

            }
        });
        serverThread.start();
    }

    public static void main(String[] arg) {
        (new CMPPServer("127.0.0.1", 7992)).start();
    }

    public class ReadThread extends Thread {
        Socket sc = null;
        boolean brun = false;

        public ReadThread(Socket sc) {
            this.sc = sc;
            this.brun = true;
        }

        public void readFromChannel() throws IOException, InterruptedException {
            while(true) {
                CMPP_Command cmd = null;

                try {
                    cmd = CMPP_Command.read(this.sc);
                    if (cmd == null) {
                        return;
                    }
                } catch (Exception var4) {
                    CMPPServer.log.error("SMS,Server, CMPP_Command.read(sc) error");
                    var4.printStackTrace();
                    this.sc.close();
                    return;
                }

                switch(cmd.getCommandID()) {
                    case -2147483642:
                        CMPP_Query_Resp resp = (CMPP_Query_Resp)cmd;
                        CMPPServer.log.info("Query Response:" + cmd.getSeqID());
                        break;
                    case 5:
                        CMPP_Deliver deliver = (CMPP_Deliver)cmd;
                        CMPP_DeliverResp deliverresp = new CMPP_DeliverResp(deliver);
                        deliverresp.setStatus(0);
                        deliverresp.write(this.sc);
                        if (deliver.isReport()) {
                            CMPPServer.log.info("Report msg: " + deliver.getReportMsgID() + "-" + deliver.getReportStat());
                        } else {
                            CMPPServer.log.info("Deliver msg: " + deliver.getSeqID() + "-" + deliver.getDesTermID() + "-" + deliver.getSrcTermID() + "-" + deliver.getDeliverMsg());
                        }
                }
            }
        }

        public void run() {
            try {
                this.readFromChannel();
                this.sc.close();
                return;
            } catch (InterruptedException var2) {
                var2.printStackTrace();
            } catch (IOException var3) {
                var3.printStackTrace();
            }

        }
    }


}