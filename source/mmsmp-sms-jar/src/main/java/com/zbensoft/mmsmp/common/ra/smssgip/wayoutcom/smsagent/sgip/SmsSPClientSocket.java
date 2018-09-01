package com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.smsagent.sgip;

import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.cmppapi.CMPP_ActiveTest;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.sgipapi.SGIP_Bind;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.sgipapi.SGIP_BindResp;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.sgipapi.SGIP_Command;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.sgipapi.SGIP_Submit;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.socket.ClientSocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;
import java.util.*;

public class SmsSPClientSocket extends ClientSocket {
    private LinkedHashSet sendBuffer = new LinkedHashSet();
    private final int submitRespSize = 29;
    private final int bufferSum = 16;
    private final int byteBufferSize = 464;
    int addcount = 0;
    int rmvcount = 0;

    public SmsSPClientSocket(String host, int port) {
        super(host, port);
    }

    protected String getClientName() {
        return this.getClass().getName();
    }

    protected void recvResp() {
        (new Timer()).schedule(new TimerTask() {
            public void run() {
                SmsSPClientSocket.this.rmvUnresp();
            }
        }, 0L, 60000L);
        (new Thread(new Runnable() {
            public void run() {
                while(true) {
                    if (!SmsSPClientSocket.this.readResp(SmsSPClientSocket.this.sc)) {
                        try {
                            Thread.sleep(1000L);
                            SmsSPClientSocket.this.refreshSocket(SmsSPClientSocket.this.sc);
                        } catch (Exception var2) {
                            ;
                        }
                    }
                }
            }
        })).start();
    }

    protected int sendtoSMSL(Object objSMS, Socket sc) {
        int ret = -1;
        if (sc == null) {
            ret = -1;
            return ret;
        } else {
            try {
                OutputStream writer = sc.getOutputStream();
                byte[] ar = ((ByteBuffer)objSMS).array();
                LinkedHashSet var6 = this.sendBuffer;
                synchronized(this.sendBuffer) {
                    try {
                        while(this.sendBuffer.size() >= 16) {
                            this.sendBuffer.wait();
                        }
                    } catch (InterruptedException var18) {
                        ;
                    }

                    this.sendBuffer.add(this.getSeq((ByteBuffer)objSMS));
                }

                writer.write(ar);
                ret = 1;
            } catch (Exception var20) {
                this.LogHandler.error("ClientSocket,There is an IOException in sendtoSMS ");
                var20.printStackTrace();
                ret = -1;
            } finally {
                if (ret == -1) {
                    LinkedHashSet var9 = this.sendBuffer;
                    synchronized(this.sendBuffer) {
                        this.sendBuffer.remove(this.getSeq((ByteBuffer)objSMS));
                    }
                }

            }

            return ret;
        }
    }

    private void rmvUnresp() {
        LinkedHashSet var1 = this.sendBuffer;
        synchronized(this.sendBuffer) {
            Iterator it = this.sendBuffer.iterator();

            while(it.hasNext()) {
                SeqObject seq = (SeqObject)it.next();
                int seq2 = seq.getseq2();
                Calendar cal = new GregorianCalendar();
                int now = (cal.get(2) + 1) * 100000000 + cal.get(5) * 1000000 + cal.get(11) * 10000 + cal.get(12) * 100 + cal.get(13);
                int differ = now - seq2;
                if (differ / 1 < 100) {
                    break;
                }

                it.remove();
            }

            this.sendBuffer.notifyAll();
        }
    }

    private boolean readResp(Socket sc) {
        try {
            InputStream reader = sc.getInputStream();
            byte[] buffer = new byte[464];
            int len = reader.read(buffer);
            if (len == -1) {
                this.rmvUnresp();
                Thread.sleep(1000L);
                return true;
            } else {
                this.dealBuffer(buffer, len);
                return true;
            }
        } catch (SocketTimeoutException var5) {
            var5.printStackTrace();
            this.LogHandler.error(var5);
            return true;
        } catch (Exception var6) {
            var6.printStackTrace();
            this.LogHandler.error(var6);
            return false;
        }
    }

    protected void sendActiveTest() throws Exception {
        (new CMPP_ActiveTest()).write(this.sc);
    }

    private void dealBuffer(byte[] buffer, int len) {
        int count = len / 29;
        SeqObject[] seq = new SeqObject[count];
        ByteBuffer byteBuf = ByteBuffer.wrap(buffer);

        for(int i = 0; i < count; ++i) {
            long seq1 = (long)byteBuf.getInt(i * 29 + 8);
            int seq2 = byteBuf.getInt(i * 29 + 12);
            int seq3 = byteBuf.getInt(i * 29 + 16);
            byteBuf.getInt(i * 29 + 20);
            seq[i] = new SeqObject(seq1, seq2, seq3);
        }

        LinkedHashSet var13 = this.sendBuffer;
        synchronized(this.sendBuffer) {
            for(int i = 0; i < seq.length; ++i) {
                if (this.sendBuffer.remove(seq[i])) {
                    this.sendBuffer.notifyAll();
                }
            }

        }
    }

    SeqObject getSeq(ByteBuffer objSMS) {
        long seq1 = (long)objSMS.getInt(8);
        int seq2 = objSMS.getInt(12);
        int seq3 = objSMS.getInt(16);
        return new SeqObject(seq1, seq2, seq3);
    }

    protected boolean Login(Socket sc) {
        SGIP_Bind cmd_Bind = null;
        SGIP_BindResp cmd_BindResp = null;
        long nodeID = Long.parseLong((String)SmsCenter.login.get("SrcNodeID"));
        int LoginType = Integer.parseInt((String)SmsCenter.login.get("LoginType"), 10);
        String strLoginName = (String)SmsCenter.login.get("LoginName");
        String strLoginPwd = (String)SmsCenter.login.get("LoginPwd");
        SGIP_Command.bBound = false;
        cmd_Bind = new SGIP_Bind(nodeID, LoginType, strLoginName, strLoginPwd);

        try {
            cmd_Bind.write(sc);
            cmd_BindResp = new SGIP_BindResp();
            cmd_BindResp = (SGIP_BindResp)cmd_BindResp.read(sc);
            if (cmd_BindResp != null && cmd_BindResp.getResult() == 0) {
                SGIP_Command.bBound = true;
                return true;
            } else {
                return false;
            }
        } catch (IOException var10) {
            this.LogHandler.error("ClientSocket,There is an IOException in Login " + this.name);
            return false;
        } catch (Exception var11) {
            this.LogHandler.error("ClientSocket,There is an Exception in Login " + this.name);
            return false;
        }
    }

    public static void main(String[] arg) {
        String smghost = "172.16.0.103";
        int smgport = 9811;
        ClientSocket client = new SmsSPClientSocket(smghost, smgport);
        client.start(true);
        String strUserPhone = "8613011111111";
        String strServiceType = SmsDataTrans.SV_Active;
        int nFeeType = 1;
        String strFeeValue = SmsDataTrans.strCommFee;
        String strGivenValue = "0";
        int nMOMTFlag = 3;
        int nReportFlag = 0;
        String strSMSContent = "test";
        SGIP_Submit ssubmit0 = null;

        try {
            ssubmit0 = new SGIP_Submit(Long.parseLong((String)SmsCenter.login.get("SrcNodeID")), SmsDataTrans.strSPNumber, strUserPhone, 1, strUserPhone, SmsDataTrans.strCorpID, strServiceType, nFeeType, strFeeValue, strGivenValue, 1, nMOMTFlag, 0, "", "", nReportFlag, 0, 0, 15, 0, strSMSContent.length(), strSMSContent);
        } catch (Exception var17) {
            var17.printStackTrace();
        }

        ByteBuffer sendByte21 = ByteBuffer.allocate(ssubmit0.getTotalLength());
        sendByte21.clear();
        sendByte21.position(0);
        ssubmit0.getHead().GetHeadByte().position(0);
        sendByte21.put(ssubmit0.getHead().GetHeadByte());
        sendByte21.position(20);
        ssubmit0.getBodyByte().position(0);
        sendByte21.put(ssubmit0.getBodyByte());
        int count = 0;

        while(true) {
            client.send(sendByte21);
            ++count;
            if (count % 10 == 0) {
                System.out.println("在APPClient已经发送count:--->" + count);
                return;
            }

            try {
                Thread.sleep(100L);
            } catch (Exception var16) {
                ;
            }
        }
    }

    private class SeqObject {
        private long seq1;
        private int seq2;
        private int seq3;

        public SeqObject(long seq1, int seq2, int seq3) {
            this.seq1 = seq1;
            this.seq2 = seq2;
            this.seq3 = seq3;
        }

        public int getseq2() {
            return this.seq2;
        }

        public String toString() {
            return this.seq1 + " " + this.seq2 + " " + this.seq3;
        }

        public int hashCode() {
            return this.seq2 << 2 + this.seq3;
        }

        public boolean equals(Object o) {
            return o instanceof SeqObject && this.seq1 == this.seq1 && this.seq2 == this.seq2 && this.seq3 == this.seq3;
        }
    }
}

