package com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.smsclient.smgp;

import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.common.SmsHandler;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.sgipapi.SGIP_Bind;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.sgipapi.SGIP_BindResp;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.smgpapi.SMGP_Command;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.smgpapi.SMGP_Submit;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.smgpapi.SMGP_SubmitResp;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.smsagent.smgp.SmsDataTrans;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Date;
import java.util.Enumeration;
import java.util.Timer;
import java.util.Vector;

public class SmsAPPClient extends SmsHandler {
    protected Socket sc = null;
    protected String host;
    protected int port;
    protected Vector ComdQ = new Vector(0);
    protected Vector RespQ = new Vector(0);
    protected Thread RecvThread = null;
    protected Thread SendThread = null;
    protected SGIP_Bind cmd_Bind = null;
    protected SGIP_BindResp cmd_BindResp = null;
    protected InetSocketAddress isa = null;
    protected static Timer timer = null;

    public SmsAPPClient(String host, int port) {
        this.host = host;
        this.port = port;
        this.isa = new InetSocketAddress(host, port);
    }

    public boolean createChannel() {
        try {
            this.sc = new Socket();

            while(this.sc == null) {
                ;
            }

            while(!this.sc.isConnected()) {
                this.sc.connect(this.isa);
            }

            this.sc.setKeepAlive(true);
            this.sc.setSoTimeout(100000);
            return true;
        } catch (IOException var2) {
            SmsDataTrans.nSockChannelStatus = 0;
            return false;
        } catch (Exception var3) {
            System.out.println(var3.getMessage());
            return false;
        }
    }

    public void send(Object objSMS) {
        try {
            this.thisLayerSend(objSMS);
        } catch (InterruptedException var3) {
            System.out.println("SMS,SPClient Send Obj error!!");
            var3.printStackTrace();
        }

    }

    public void start() {
        System.out.println("SMS,SPClient creating Socket channel...");
        if (this.createChannel()) {
            System.out.println("SMS,SPClient Connect OK!");
            System.out.println("begin to start the Thread");
            this.SendThread = new Thread(new Runnable() {
                public void run() {
                }
            });
            this.SendThread.start();
        } else {
            System.out.println("SMS,SPClient Connect failed!");
        }
    }

    public synchronized void thisLayerSend(Object objSMS) throws InterruptedException {
        this.RespQ.addElement(objSMS);
        this.notifyAll();
    }

    protected void addComdQ(Object sgipCmd) {
        this.UpHandler.receive(sgipCmd);
    }

    protected synchronized Vector checkResponse() throws InterruptedException {
        while(this.RespQ.size() == 0) {
            this.wait();
        }

        Vector tmp = (Vector)this.RespQ.clone();
        this.RespQ.removeAllElements();
        return tmp;
    }

    public int sendtoSMS(Object objSMS) {
        SMGP_Command smgp_Cmd = (SMGP_Command)objSMS;
        SMGP_Command smgp_temp = null;
        byte nBytes = 1;

        try {
            smgp_Cmd.write(this.sc);
        } catch (IOException var9) {
            ;
        }

        System.out.println("SMS,SPClient Sendthread, send data over");

        try {
            smgp_temp = SMGP_Command.read(this.sc);
            System.out.println("**************2收到smsAgentserver的响应在smsAPP.sendtoSMS********************");
        } catch (Exception var8) {
            System.out.println("SMS,SPClient read error");
            return -1;
        }

        switch(smgp_temp.getCommandID()) {
            case -2147483646:
                SMGP_SubmitResp submitresp = (SMGP_SubmitResp)smgp_temp;
                int seq = submitresp.getSeqID();
                byte[] var7 = submitresp.getMsgID();
            default:
                return nBytes;
        }
    }

    protected void sendThread() {
        System.out.println("SMS,SPClient write thread start");
        Vector tmp_respQ = new Vector(0);

        while(true) {
            try {
                tmp_respQ = this.checkResponse();
            } catch (InterruptedException var6) {
                System.out.println("SMS,SPClient sendThread InterruptedException");
            }

            Enumeration resp_item = tmp_respQ.elements();

            while(resp_item.hasMoreElements()) {
                Object smsSend = resp_item.nextElement();
                System.out.println("SMS,SPClient send new message to SMG");
                int nRet = -1;

                for(int var5 = 0; nRet <= 0; ++var5) {
                    System.out.print("SMS,SPClient bind OK!");
                    nRet = this.sendtoSMS(smsSend);
                    if (nRet > 0) {
                        break;
                    }

                    this.DisConnectSMGFull();
                }
            }
        }
    }

    public void DisConnectSMGFull() {
        System.out.println("enter dis full");
        new SGIP_BindResp();

        try {
            while(!this.sc.isClosed()) {
                this.sc.close();
            }

            System.out.println("sc closed!");
            String strLog = "SMS,SPClient, Current Session end, disconnected with SMG OK";
            Thread.sleep(2000L);
        } catch (Exception var7) {
            var7.printStackTrace();
            String var3 = "SMS,SPClient, Current Session end, Socket Channel Close Error: " + var7.getMessage();
        } finally {
            this.sc = null;
        }

        this.createChannel();
    }

    public static void main(String[] arg) {
        String smghost = "127.0.0.1";
        int smgport = 8813;
        SmsAPPClient client = new SmsAPPClient(smghost, smgport);
        client.start();
        Logger logger = Logger.getLogger("LOG");
        client.setLogHandler(logger);
        int count = 0;

        while(true) {
            SMGP_Submit ssubmit = null;
            String[] destTermId = new String[]{"01087654321"};

            try {
                ssubmit = new SMGP_Submit(6, 1, 1, "wayout", "00", "", "", 15, new Date(), new Date(), "118", "01012345678", destTermId, "9992", "");
            } catch (Exception var10) {
                var10.printStackTrace();
            }

            client.sendtoSMS(ssubmit);

            try {
                ++count;
                if (count % 10 == 0) {
                    System.out.println("在APPClient已经发送count:--->" + count);
                    Thread.sleep(5000L);
                    return;
                }

                Thread.sleep(10L);
            } catch (Exception var9) {
                ;
            }
        }
    }
}

