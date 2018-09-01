package com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.smsagent.smgp;

import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.cmppapi.ProduceMsgID;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.dbAccess.OperData4smgp;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.smgpapi.SMGP_Command;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.smgpapi.SMGP_Submit;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.smgpapi.SMGP_SubmitResp;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.socket.SocketListen;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public class SmsAgentServerSocket extends SocketListen {
    public SmsAgentServerSocket(String localIP, int localPort) {
        super(localIP, localPort);
    }

    protected String getServerName() {
        return this.getClass().getName();
    }

    protected boolean accessCheck(Socket client) {
        InetAddress add = client.getInetAddress();
        String IPaddress = add.getHostAddress();
        OperData4smgp data = new OperData4smgp();
        if (!data.IPCheck(IPaddress)) {
            this.LogHandler.info("ServerSocket,Illegal IP access, and rejected:" + IPaddress + this.name);
            return false;
        } else {
            return true;
        }
    }

    private void addsmgCmdQ(Object smgpCmd) {
        this.DownHandler.send(smgpCmd);
    }

    protected void startThread(Socket client) {
        ReadThread sread = new ReadThread(client);
        sread.start();
    }

    public class ReadThread extends SocketListen.ReadThread {
        public ReadThread(Socket sc) {
            super( sc);
        }

        public void readFromChannel() throws IOException, InterruptedException {
            while(true) {
                SMGP_Command smgp_Cmd = null;

                try {
                    smgp_Cmd = SMGP_Command.read(this.sc);
                    if (smgp_Cmd == null) {
                        return;
                    }

                    this.dealSubmit(smgp_Cmd, this.sc);
                } catch (SocketTimeoutException var3) {
                    this.sc.close();
                    SmsAgentServerSocket.this.LogHandler.info("SMS,AgentServer, SocketTimeoutException error");
                    return;
                } catch (SocketException var4) {
                    var4.printStackTrace();
                    SmsAgentServerSocket.this.LogHandler.info("Client has a SocketException:" + var4.getMessage());
                    this.sc.close();
                    return;
                } catch (Exception var5) {
                    SmsAgentServerSocket.this.LogHandler.error("SMS,Server, sgip_Cmd.read(sc) error");
                    var5.printStackTrace();
                    this.sc.close();
                    return;
                }
            }
        }

        private void dealSubmit(SMGP_Command smgp_Cmd, Socket sc) throws IOException {
            switch(smgp_Cmd.getCommandID()) {
                case 2:
                    SMGP_Submit submit = (SMGP_Submit)smgp_Cmd;
                    InetAddress add = sc.getInetAddress();
                    String IPaddress = add.getHostAddress();
                    Integer appID = (Integer)SmsCenter.submit.get(IPaddress);
                    byte[] msgID = com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.common.TypeConvert.long2byte(ProduceMsgID.getMsgID(appID));
                    SMGP_SubmitResp submitresp = new SMGP_SubmitResp(submit, msgID);
                    submitresp.write(sc);
                    Object[] obj = new Object[]{submit, msgID};
                    SmsAgentServerSocket.this.addsmgCmdQ(obj);
                    break;
                default:
                    SmsAgentServerSocket.this.LogHandler.error("ServerSocket unsupported command in readFromChannel ");
            }

        }
    }
}

