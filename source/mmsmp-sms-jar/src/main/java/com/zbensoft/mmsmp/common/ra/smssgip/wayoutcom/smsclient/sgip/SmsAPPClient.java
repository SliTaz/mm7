package com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.smsclient.sgip;

import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.common.SmsHandler;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.dbAccess.OperData;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.sgipapi.*;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.smsagent.sgip.SmsCenter;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.smsagent.sgip.SmsDataTrans;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
            this.sc.setSoTimeout(10000);
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
                    SmsAPPClient.this.sendThread();
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
        SGIP_Command sgip_Cmd = null;
        SGIP_Command sgip_temp = new SGIP_Command();
        byte nBytes = 1;

        try {
            OutputStream writer = this.sc.getOutputStream();
            ByteBuffer bf = ByteBuffer.wrap(((ByteBuffer)objSMS).array());
            byte[] ar = bf.array();
            System.out.println("**************1送到smsAgentserver在smsAPP.sendtoSMS********************");
            writer.write(ar);
        } catch (IOException var9) {
            ;
        }

        System.out.println("SMS,SPClient Sendthread, send data over");

        try {
            sgip_Cmd = sgip_temp.read(this.sc);
            System.out.println("**************2收到smsAgentserver的响应在smsAPP.sendtoSMS********************");
        } catch (Exception var8) {
            System.out.println("SMS,SPClient read error");
            return -1;
        }

        switch(sgip_Cmd.getCommandID()) {
            case -2147483647:
                SGIP_BindResp bindresp = new SGIP_BindResp(sgip_Cmd);
                bindresp.readbody();
            default:
                return nBytes;
            case -2147483646:
                SGIP_Command.bBound = false;
                new SGIP_UnbindResp(sgip_Cmd);
                return 1;
            case -2147483645:
                SGIP_SubmitResp submitresp = new SGIP_SubmitResp(sgip_Cmd);
                submitresp.readbody();
                String strLog1 = "[Len]: " + sgip_Cmd.getTotalLength() + "[CmdID]: " + sgip_Cmd.getCommandID() + "[SQ]: " + sgip_Cmd.getSeqno_1() + "-" + sgip_Cmd.getSeqno_2() + "-" + sgip_Cmd.getSeqno_3() + "[SR]: " + submitresp.getResult();
                if (submitresp.getResult() == 0) {
                    (new StringBuffer("SPClient(SOK) ")).append(strLog1).toString();
                    return 1;
                } else {
                    (new StringBuffer("SPClient(SFailed) ")).append(strLog1).toString();
                    return -1;
                }
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
        OperData data = new OperData();
        SmsCenter.login = data.loadLogin();
        new SGIP_Unbind(Long.parseLong((String)SmsCenter.login.get("SrcNodeID")));
        new SGIP_BindResp();

        try {
            while(!this.sc.isClosed()) {
                this.sc.close();
            }

            System.out.println("sc closed!");
            String strLog = "SMS,SPClient, Current Session end, disconnected with SMG OK";
            Thread.sleep(2000L);
        } catch (Exception var9) {
            var9.printStackTrace();
            String var5 = "SMS,SPClient, Current Session end, Socket Channel Close Error: " + var9.getMessage();
        } finally {
            this.sc = null;
        }

        this.createChannel();
    }

    public static void main(String[] arg) {
        String smghost = "172.16.0.242";
        int smgport = 9813;
        int count = 0;
        SmsAPPClient client = new SmsAPPClient(smghost, smgport);
        client.start();
        String strUserPhone = "8613011111111";
        String strServiceType = SmsDataTrans.SV_Active;
        int nFeeType = 1;
        String strFeeValue = "0";
        int Fee = 0;
        String strGivenValue = "0";
        int nMOMTFlag = 3;
        int nReportFlag = 1;
        String strSMSContent = "9992";
        SGIP_Submit ssubmit0 = null;

        while(true) {
            try {
                strSMSContent = Integer.toString(count);
                strFeeValue = String.valueOf(Fee);
                ssubmit0 = new SGIP_Submit(9992L, SmsDataTrans.strSPNumber, strUserPhone, 1, strUserPhone, SmsDataTrans.strCorpID, strServiceType, nFeeType, strFeeValue, strGivenValue, 1, nMOMTFlag, 0, "", "", nReportFlag, 0, 0, 15, 0, strSMSContent.length(), strSMSContent);
            } catch (Exception var18) {
                var18.printStackTrace();
            }

            ByteBuffer sendByte21 = ByteBuffer.allocate(ssubmit0.getTotalLength());
            sendByte21.clear();
            sendByte21.position(0);
            ssubmit0.getHead().GetHeadByte().position(0);
            sendByte21.put(ssubmit0.getHead().GetHeadByte());
            sendByte21.position(20);
            ssubmit0.getBodyByte().position(0);
            sendByte21.put(ssubmit0.getBodyByte());
            Connection conn = null;
            PreparedStatement pstm = null;
            client.send(sendByte21);
            ++count;
            if (count % 10 == 0) {
                System.out.println("在APPClient已经发送count:--->" + count);
                return;
            }

            ++Fee;
        }
    }
}

