package com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.smsagent.sgip;

import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.sgipapi.*;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.socket.SocketListen;

import java.io.IOException;
import java.net.Socket;

public class SmsSPServerSocket extends SocketListen {
    public SmsSPServerSocket(String localIP, int localPort) {
        super(localIP, localPort);
    }

    protected String getServerName() {
        return this.getClass().getName();
    }

    protected boolean accessCheck(Socket client) {
        return true;
    }

    private void addsmgCmdQ(Object sgipCmd) {
        this.DownHandler.receive(sgipCmd);
    }

    protected void startThread(Socket client) {
        SmsSPServerSocket.ReadThread sread = new SmsSPServerSocket.ReadThread(client);
        sread.start();
    }

    public static void main(String[] arg) {
        (new SmsSPServerSocket("127.0.0.1", 9812)).start();
    }

    public class ReadThread extends SocketListen.ReadThread {
        public ReadThread(Socket sc) {
            super(sc);
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
                } catch (Exception var5) {
                    SmsSPServerSocket.this.LogHandler.error(var5);
                    this.sc.close();
                    SmsSPServerSocket.this.LogHandler.error("close the socket");
                    return;
                }

                switch(sgip_Cmd.getCommandID()) {
                    case 1:
                        SmsSPServerSocket.this.LogHandler.debug(">>>>SMS,Server,It is a Bind!");
                        SGIP_BindResp resp = new SGIP_BindResp(sgip_Cmd.getHead());
                        resp.SetResult(0);
                        resp.write(this.sc);
                        break;
                    case 2:
                        SmsSPServerSocket.this.LogHandler.debug(">>>>SMS,Server,It is a UnBind!");
                        SGIP_Unbind sunbid = (SGIP_Unbind)sgip_Cmd;
                        SGIP_UnbindResp unbindresp = new SGIP_UnbindResp(sgip_Cmd.getHead());
                        unbindresp.write(this.sc);
                        this.sc.close();
                        return;
                    case 4:
                        SmsSPServerSocket.this.LogHandler.debug(">>>>SMS,Server,It is a DELIVER!");
                        SGIP_Deliver deliver = (SGIP_Deliver)sgip_Cmd;
                        SGIP_DeliverResp deliverresp = new SGIP_DeliverResp(sgip_Cmd.getHead());
                        deliverresp.SetResult(0);
                        deliverresp.write(this.sc);
                        SmsSPServerSocket.this.addsmgCmdQ(sgip_Cmd);
                        break;
                    case 5:
                        SmsSPServerSocket.this.LogHandler.debug(">>>>NioServer,It is a REPORT!");
                        SGIP_Report report = (SGIP_Report)sgip_Cmd;
                        SGIP_ReportResp reportresp = new SGIP_ReportResp(sgip_Cmd.getHead());
                        reportresp.SetResult(0);
                        reportresp.write(this.sc);
                        SmsSPServerSocket.this.addsmgCmdQ(sgip_Cmd);
                        break;
                    case 17:
                        SmsSPServerSocket.this.LogHandler.debug(">>>>NioServer,It is a USERRPT!");
                        SGIP_UserReport userrpt = (SGIP_UserReport)sgip_Cmd;
                        SGIP_UserReportResp userrptresp = new SGIP_UserReportResp(sgip_Cmd.getHead());
                        userrptresp.SetResult(0);
                        userrptresp.write(this.sc);
                        SmsSPServerSocket.this.addsmgCmdQ(sgip_Cmd);
                }
            }
        }
    }
}

