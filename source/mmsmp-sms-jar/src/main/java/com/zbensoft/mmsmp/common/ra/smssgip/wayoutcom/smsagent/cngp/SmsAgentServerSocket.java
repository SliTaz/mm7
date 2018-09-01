package com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.smsagent.cngp;

import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.cmppapi.ProduceMsgID;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.cngpapi.*;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.common.TypeConvert;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.dbAccess.OperData4cngp;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.socket.SocketListen;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;

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
        OperData4cngp data = new OperData4cngp(this.LogHandler);
        if (!data.IPCheck(IPaddress)) {
            this.LogHandler.info("ServerSocket,Illegal IP access, and rejected:" + IPaddress + this.name);
            return false;
        } else {
            return true;
        }
    }

    private void addsmgCmdQ(Object cngpCmd) {
        this.DownHandler.send(cngpCmd);
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
                CNGP_Command cngp_Cmd = null;

                try {
                    cngp_Cmd = CNGP_Command.read(this.sc);
                    if (cngp_Cmd == null) {
                        this.sc.close();
                        return;
                    }

                    this.dealSubmit(cngp_Cmd, this.sc);
                } catch (SocketTimeoutException var3) {
                    this.sc.close();
                    SmsAgentServerSocket.this.LogHandler.error("SMS,AgentServer, SocketTimeoutException error");
                    return;
                } catch (SocketException var4) {
                    this.sc.close();
                    SmsAgentServerSocket.this.LogHandler.error("SMS,Server, cngp_Cmd.read(sc) error for " + var4.getMessage());
                    return;
                } catch (Exception var5) {
                    SmsAgentServerSocket.this.LogHandler.error("SMS,Server, cngp_Cmd.read(sc) error");
                    var5.printStackTrace();
                    this.sc.close();
                    return;
                }
            }
        }

        private void dealSubmit(CNGP_Command cngp_Cmd, Socket sc) throws IOException {
            switch(cngp_Cmd.getCommandID()) {
                case 1:
                    byte[] b = new byte[33];
                    CNGP_BindResp resp = new CNGP_BindResp(ByteBuffer.wrap(b));
                    resp.setCommandID(-2147483647);
                    resp.setPacketLength(33);
                    resp.setSeqID(cngp_Cmd.getSeqID());
                    resp.write(sc);
                    break;
                case 2:
                    CNGP_Submit submit = (CNGP_Submit)cngp_Cmd;
                    InetAddress add = sc.getInetAddress();
                    String IPaddress = add.getHostAddress();
                    Integer appID = (Integer)SmsCenter.submit.get(IPaddress);

                    byte[] msgID = TypeConvert.long2byte(ProduceMsgID.getMsgID(appID));
                    CNGP_SubmitResp submitresp = new CNGP_SubmitResp(submit, msgID, 0);
                    submitresp.write(sc);
                    Object[] obj = new Object[]{submit, msgID};
                    SmsAgentServerSocket.this.addsmgCmdQ(obj);
                    break;
                case 3:
                case 5:
                default:
                    SmsAgentServerSocket.this.LogHandler.error("ServerSocket unsupported command in readFromChannel ");
                    break;
                case 4:
                    CNGP_ActiveTestResp respxx = new CNGP_ActiveTestResp((CNGP_ActiveTest)cngp_Cmd);
                    respxx.write(sc);
                    break;
                case 6:
                    CNGP_UnbindResp respx = new CNGP_UnbindResp((CNGP_Unbind)cngp_Cmd);
                    respx.write(sc);
            }

        }
    }
}

