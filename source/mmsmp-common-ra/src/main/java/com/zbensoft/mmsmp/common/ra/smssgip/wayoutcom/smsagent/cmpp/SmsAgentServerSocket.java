package com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.smsagent.cmpp;

import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.cmppapi.*;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.dbAccess.OperData4cmpp;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.produce_consume.Channel;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.socket.SocketListen;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;

public class SmsAgentServerSocket extends SocketListen {
    private Channel socketQueue = new Channel(50);
    SmsAgentServerSocket.ReadThread socketThread = null;

    public SmsAgentServerSocket(String localIP, int localPort) {
        super(localIP, localPort);
    }

    protected String getServerName() {
        return this.getClass().getName();
    }

    protected boolean accessCheck(Socket client) {
        InetAddress add = client.getInetAddress();
        String IPaddress = add.getHostAddress();
        OperData4cmpp data = new OperData4cmpp();
        if (!data.IPCheck(IPaddress)) {
            this.LogHandler.info("ServerSocket,Illegal IP access, and rejected:" + IPaddress + this.name);
            return false;
        } else {
            return true;
        }
    }

    private void addsmgCmdQ(Object cmppCmd) {
        this.DownHandler.send(cmppCmd);
    }

    protected void startThread(Socket client) {
        if (this.socketThread == null) {
            this.socketThread = new SmsAgentServerSocket.ReadThread(this.socketQueue);
            this.socketThread.setName("SmsAgentServerSocket");
            this.socketThread.start();
        }

        try {
            this.socketQueue.put(client);
        } catch (InterruptedException var3) {
            this.LogHandler.error(var3);
        }

    }

    class ReadThread extends Thread {
        boolean brun = false;
        Channel socketQueue;

        public ReadThread(Channel socketQueue) {
            this.socketQueue = socketQueue;
        }

        public void readFromChannel() throws IOException, InterruptedException {
            while (true) {
                CMPP_Command cmpp_Cmd = null;
                Socket sc = null;

                try {
                    sc = (Socket) this.socketQueue.remove();
                    cmpp_Cmd = CMPP_Command.read(sc);
                    if (cmpp_Cmd == null) {
                        sc.close();
                    } else if (cmpp_Cmd instanceof CMPP_Unbind) {
                        sc.close();
                    } else {
                        this.dealSubmit(cmpp_Cmd, sc);
                        this.socketQueue.put(sc);
                    }
                } catch (SocketException var4) {
                    SmsAgentServerSocket.this.LogHandler.info("cmpp_Cmd.read error for " + var4.getMessage());
                } catch (Exception var5) {
                    var5.printStackTrace();
                    sc.close();
                    SmsAgentServerSocket.this.LogHandler.error("SMS,Server, cmpp_Cmd.read( " + sc + " ) error, then closed the socket!");
                }
            }
        }

        private void dealSubmit(CMPP_Command cmpp_Cmd, Socket sc) throws IOException {
            switch (cmpp_Cmd.getCommandID()) {
                case 4:
                    CMPP_Submit submit = (CMPP_Submit) cmpp_Cmd;
                    InetAddress add = sc.getInetAddress();
                    String IPaddress = add.getHostAddress();
                    Integer appID = (Integer) SmsCenter.submit.get(IPaddress);
                    long msgID = ProduceMsgID.getMsgID(appID);
                    CMPP_SubmitResp submitresp = new CMPP_SubmitResp(submit, msgID);
                    submitresp.write(sc);
                    Object[] obj = new Object[]{submit, new Long(msgID)};
                    SmsAgentServerSocket.this.addsmgCmdQ(obj);
                    break;
                default:
                    SmsAgentServerSocket.this.LogHandler.error("ServerSocket unsupported command in readFromChannel ");
            }

        }

        public void run() {
            String var2;
            try {
                SmsAgentServerSocket.this.LogHandler.info("SMS,Server, listen thread start...");
                this.readFromChannel();
                SmsAgentServerSocket.this.LogHandler.info("SMS,Server, listen thread stop and exit...ok");
                String strlog = "SMS,Server, Listen thread stop and exit";
                return;
            } catch (InterruptedException var3) {
                SmsAgentServerSocket.this.LogHandler.error("SMS,Server, sreadthread error", var3);
                var2 = "SMS,Server, Run InterruptedException";
            } catch (IOException var4) {
                SmsAgentServerSocket.this.LogHandler.error("SMS,Server, sreadthread error", var4);
                var2 = "SMS,Server, Run IOException";
            }

        }
    }
}
