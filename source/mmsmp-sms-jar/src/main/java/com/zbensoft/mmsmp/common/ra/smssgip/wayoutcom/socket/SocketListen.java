package com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.socket;

import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.common.SmsHandler;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.dbAccess.OperData;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.sgipapi.*;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.*;

public class SocketListen extends SmsHandler {
    private int port;
    private String localhost = null;
    private ServerSocket server = null;
    private Thread serverThread = null;
    protected Socket client = null;
    protected String name;
    protected  Logger LogHandler = Logger.getLogger(SocketListen.class);

    public SocketListen(String localIP, int localPort) {
        this.port = localPort;
        this.localhost = localIP;
        this.name = this.getServerName();
    }

    protected String getServerName() {
        return "ServerSocket";
    }

    private boolean initialize() {
        try {
            this.server = new ServerSocket();
            InetSocketAddress isa = new InetSocketAddress(this.port);
            this.server.bind(isa);
            this.LogHandler.info("ServerSocket initializated successfully " + this.name);
            return true;
        } catch (Exception var2) {
            this.LogHandler.error("ServerSocket initial failed " + this.name);
            return false;
        }
    }

    public final void finalize() throws IOException {
        if (!this.server.isClosed()) {
            this.server.close();
        }

        this.LogHandler.error("ServerSocket close the socket channel " + this.name);
        System.exit(1);
    }

    private void mainThread() throws IOException, InterruptedException {
        if (this.initialize()) {
            while(true) {
                while(true) {
                    this.client = this.server.accept();
                    this.client.setKeepAlive(true);
                    this.client.setSoTimeout(0);
                    if (this.client == null) {
                        this.LogHandler.info("ServerSocket,Got a null client " + this.name);
                    } else if (!this.accessCheck(this.client)) {
                        if (!this.client.isClosed()) {
                            this.client.close();
                        }
                    } else {
                        this.LogHandler.debug("ServerSocket a SOCKET comming，its remote port：" + this.client.getPort() + this.name);
                        this.startThread(this.client);
                    }
                }
            }
        }

    }

    protected void startThread(Socket client) {
        ReadThread sread = new ReadThread(client);
        sread.start();
    }

    protected boolean accessCheck(Socket client) {
        InetAddress add = client.getInetAddress();
        String IPaddress = add.getHostAddress();
        OperData data = new OperData();
        if (!data.IPCheck(IPaddress)) {
            this.LogHandler.info("ServerSocket,Illegal IP access, and rejected:" + IPaddress + this.name);
            return false;
        } else {
            return true;
        }
    }

    private void addsmgCmdQ(Object sgipCmd) {
        this.DownHandler.send(sgipCmd);
    }

    public final void start() {
        this.serverThread = new Thread(new Runnable() {
            public void run() {
                try {
                    SocketListen.this.mainThread();
                } catch (InterruptedException var2) {
                    System.out.println("ServerSocket,  server thread error " + SocketListen.this.name);
                    var2.printStackTrace();
                } catch (IOException var3) {
                    System.out.println("ServerSocket,  server thread error " + SocketListen.this.name);
                    var3.printStackTrace();
                }

            }
        });
        this.serverThread.start();
    }

    protected class ReadThread extends Thread {
        protected Socket sc = null;

        public ReadThread(Socket sc) {
            this.sc = sc;
        }

        protected void readFromChannel() throws IOException, InterruptedException {
            while(true) {
                SGIP_Command sgip_Cmd = null;
                SGIP_Command sgip_tmp = new SGIP_Command();

                try {
                    sgip_Cmd = sgip_tmp.read(this.sc);
                    if (sgip_Cmd == null) {
                        return;
                    }
                } catch (SocketTimeoutException var5) {
                    SocketListen.this.LogHandler.error("SMS,AgentServer, SocketTimeoutException error");
                    return;
                } catch (SocketException var6) {
                    var6.printStackTrace();
                    SocketListen.this.LogHandler.info(var6.getMessage());
                    return;
                } catch (Exception var7) {
                    var7.printStackTrace();
                    SocketListen.this.LogHandler.error("ServerSocket, sgip_Cmd.read(sc) error " + SocketListen.this.name);
                    return;
                }

                switch(sgip_Cmd.getCommandID()) {
                    case 1:
                        SGIP_BindResp bindresp = new SGIP_BindResp(sgip_Cmd.getHead());
                        bindresp.SetResult(0);
                        bindresp.write(this.sc);
                        break;
                    case 2:
                        SGIP_UnbindResp resp = new SGIP_UnbindResp(sgip_Cmd.getHead());
                        resp.write(this.sc);
                        break;
                    case 3:
                        SGIP_Submit submit = (SGIP_Submit)sgip_Cmd;
                        SGIP_SubmitResp submitresp = new SGIP_SubmitResp(sgip_Cmd.getHead());
                        submitresp.SetResult(0);
                        submitresp.write(this.sc);
                        SocketListen.this.addsmgCmdQ(submit);
                        break;
                    default:
                        SocketListen.this.LogHandler.error("ServerSocket unsupported command in readFromChannel " + SocketListen.this.name);
                }
            }
        }

        public final void run() {
            try {
                this.readFromChannel();
                this.sc.close();
                SocketListen.this.LogHandler.debug("ServerSocket, listen thread stop and exit...ok " + SocketListen.this.name);
                return;
            } catch (InterruptedException var2) {
                SocketListen.this.LogHandler.error("ServerSocket, sreadthread error " + SocketListen.this.name);
            } catch (IOException var3) {
                SocketListen.this.LogHandler.error("ServerSocket, sreadthread error " + SocketListen.this.name);
            }

        }
    }
}
