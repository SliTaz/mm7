package com.zbensoft.mmsmp.common.ra.smssgip.smsclient;

import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.smgpapi.SMGP_Command;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.smgpapi.SMGP_Deliver;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.smgpapi.SMGP_DeliverResp;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class SMGPServer {
    private static Logger log = Logger.getLogger(SMGPServer.class);
    ServerSocket server = null;
    Socket client = null;
    private static Thread serverThread = null;
    private String localhost;
    private int port;

    public SMGPServer(String localIP, int localPort) {
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
                        ReadThread sread = new ReadThread(this.client);
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
                    SMGPServer.this.mainThread();
                } catch (InterruptedException var2) {
                    SMGPServer.log.error("SMS,Server,  server thread error");
                    var2.printStackTrace();
                } catch (IOException var3) {
                    SMGPServer.log.error("SMS,Server,  server thread error");
                    var3.printStackTrace();
                }

            }
        });
        serverThread.start();
    }

    public static void main(String[] arg) {
        (new SMGPServer("127.0.0.1", 8992)).start();
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
                SMGP_Command cmd = null;

                try {
                    cmd = SMGP_Command.read(this.sc);
                    if (cmd == null) {
                        return;
                    }
                } catch (Exception var4) {
                    SMGPServer.log.error("SMS,Server, SMGP__Command.read(sc) error");
                    var4.printStackTrace();
                    this.sc.close();
                    return;
                }

                switch(cmd.getCommandID()) {
                    case 3:
                        SMGP_Deliver deliver = (SMGP_Deliver)cmd;
                        SMGP_DeliverResp deliverresp = new SMGP_DeliverResp(deliver);
                        deliverresp.setStatus(0);
                        deliverresp.write(this.sc);
                        if (deliver.isReport()) {
                            SMGPServer.log.info("Report msg: " + deliver.getReportMsgID() + "-" + deliver.getReportStat());
                            break;
                        }

                        SMGPServer.log.info("Deliver msg: " + deliver.getSeqID() + "-" + deliver.getDestTermID() + "-" + deliver.getSrcTermID() + "-" + deliver.getDelivercontent());
                        break;
                    default:
                        SMGPServer.log.info("Command msg: " + cmd);
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
