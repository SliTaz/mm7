package com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.smsagent.smgp;

import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.dbAccess.OperData4smgp;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.smgpapi.*;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.socket.ClientSocket;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.*;

public class SmsSPClientSocket extends ClientSocket {
    private LinkedHashMap sendBuffer = new LinkedHashMap();
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
                try {
                    while(true) {
                        if (SmsSPClientSocket.this.activeTestThread.canRun()) {
                            SmsSPClientSocket.this.readResp(SmsSPClientSocket.this.sc);
                        }
                    }
                } catch (Exception var2) {
                    ;
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
            SMGP_Command comm = (SMGP_Command)objSMS;
            int seq = comm.getSeqID();
            Calendar cal = new GregorianCalendar();
            long time = cal.getTimeInMillis();
            Long Time = new Long(time);
            Object[] obj = new Object[]{Time, comm};

            try {
                LinkedHashMap var11 = this.sendBuffer;
                synchronized(this.sendBuffer) {
                    try {
                        while(this.sendBuffer.size() >= 16) {
                            this.sendBuffer.wait();
                        }
                    } catch (InterruptedException var23) {
                        ;
                    }

                    this.sendBuffer.put(new Integer(seq), obj);
                }

                comm.write(sc);
                ret = 1;
                Integer cyc2smg = (Integer)SmsCenter.parameter.get("Cyc2smg");
                Thread.sleep((long)cyc2smg);
            } catch (Exception var25) {
                this.LogHandler.error("ClientSocket,There is an IOException in sendtoSMS ");
                var25.printStackTrace();
                ret = -1;
            } finally {
                if (ret == -1) {
                    LinkedHashMap var14 = this.sendBuffer;
                    synchronized(this.sendBuffer) {
                        this.sendBuffer.remove(new Integer(seq));
                    }
                }

            }

            return ret;
        }
    }

    private void rmvUnresp() {
        int len = this.sendBuffer.size();
        LinkedHashMap var2 = this.sendBuffer;
        synchronized(this.sendBuffer) {
            Collection c = this.sendBuffer.values();
            Iterator it = c.iterator();

            while(it.hasNext()) {
                Object[] value = (Object[])it.next();
                Long time = (Long)value[0];
                Calendar cal = new GregorianCalendar();
                long now = cal.getTimeInMillis();
                long differ = now - time;
                if (len < 16 || differ / 1000L < 10L) {
                    break;
                }

                it.remove();
            }

            this.sendBuffer.notifyAll();
        }
    }

    private boolean readResp(Socket sc) {
        try {
            SMGP_Command resp = SMGP_Command.read(sc);
            if (resp == null) {
                Thread.sleep(1000L);
                return true;
            } else {
                this.dealResp(resp, sc);
                return true;
            }
        } catch (SocketTimeoutException var3) {
            return true;
        } catch (Exception var4) {
            var4.printStackTrace();
            this.activeTestThread.setActiveFalse();
            return false;
        }
    }

    private void dealResp(SMGP_Command resp, Socket sc) throws IOException {
        int requestID = resp.getCommandID();
        switch(requestID) {
            case -2147483646:
                SMGP_SubmitResp com = (SMGP_SubmitResp)resp;
                int seq = com.getSeqID();
                byte[] msgID = com.getMsgID();
                int status = com.getStatus();
                LinkedHashMap var8 = this.sendBuffer;
                synchronized(this.sendBuffer) {
                    if (this.sendBuffer.remove(new Integer(seq)) != null) {
                        this.sendBuffer.notifyAll();
                    }
                }

                boolean flag = false;
                Map var9 = SmsProcess.submitQueue;
                synchronized(SmsProcess.submitQueue) {
                    Set entry = SmsProcess.submitQueue.entrySet();
                    Iterator it = entry.iterator();

                    while(it.hasNext()) {
                        Map.Entry en = (Map.Entry)it.next();
                        SubmitKey key = (SubmitKey)en.getKey();
                        Object value = en.getValue();
                        SMGP_Command submit = key.getSubmit();
                        if (submit.getSeqID() == seq && key.getMsgID() == null) {
                            key = (SubmitKey)key.clone();
                            key.setMsgID(msgID);
                            it.remove();
                            SmsProcess.submitQueue.put(key, value);
                            flag = true;
                            break;
                        }
                    }
                }

                if (!flag) {
                    Object[] obj = new Object[]{new Integer(seq), msgID};
                    OperData4smgp data = new OperData4smgp();
                    data.submitTimeoutResp(obj);
                }
                break;
            case 3:
                SMGP_Deliver deliver = (SMGP_Deliver)resp;
                SMGP_DeliverResp deliverResp = new SMGP_DeliverResp(deliver);
                deliverResp.write(sc);
                this.DownHandler.receive(deliver);
                break;
            case 4:
                SMGP_ActiveTestResp activeResp = new SMGP_ActiveTestResp((SMGP_ActiveTest)resp);
                activeResp.write(sc);
                break;
            case 6:
                SMGP_UnbindResp unbindResp = new SMGP_UnbindResp((SMGP_Unbind)resp);
                unbindResp.write(sc);
                this.activeTestThread.setActiveFalse();
        }

    }

    protected boolean Login(Socket sc) {
        SMGP_Bind cmd_Bind = null;
        SMGP_BindResp cmd_BindResp = null;
        String clientId = (String)SmsCenter.login.get("ClientId");
        String shared_Secret = (String)SmsCenter.login.get("Secret");
        int loginMode = Integer.parseInt((String)SmsCenter.login.get("LoginMode"));
        int version = Integer.parseInt((String)SmsCenter.login.get("Version"), 16);
        cmd_Bind = new SMGP_Bind(clientId, shared_Secret, loginMode, new Date(), version);

        try {
            cmd_Bind.write(sc);
            cmd_BindResp = (SMGP_BindResp)SMGP_Command.read(sc);
            if (cmd_BindResp.getStatus() == 0) {
                this.LogHandler.info(sc + " in spclientSocket Login!");
                return true;
            } else {
                return false;
            }
        } catch (IOException var9) {
            this.LogHandler.error("ClientSocket,There is an IOException in Login " + this.name);
            return false;
        } catch (Exception var10) {
            this.LogHandler.error("ClientSocket,There is an Exception in Login " + this.name);
            return false;
        }
    }

    protected void sendActiveTest() throws Exception {
        (new SMGP_ActiveTest()).write(this.sc);
    }

    public static void main(String[] arg) {
        String smghost = "172.16.0.103";
        SmsSPClientSocket client = new SmsSPClientSocket("127.0.0.1", 8890);
        Logger logger = Logger.getLogger("LOG");
        client.setLogHandler(logger);
        client.start(true);
        SMGP_Submit ssubmit = null;
        String[] destTermId = new String[]{"01087654321"};

        try {
            ssubmit = new SMGP_Submit(6, 1, 1, "wayout", "00", "", "", 15, new Date(), new Date(), "118", "01012345678", destTermId, "test", "");
        } catch (Exception var10) {
            var10.printStackTrace();
        }

        int count = 0;

        while(true) {
            client.send(ssubmit);
            ++count;
            if (count % 1 == 0) {
                System.out.println("在APPClient已经发送count:--->" + count);
                return;
            }

            try {
                Thread.sleep(100L);
            } catch (Exception var9) {
                ;
            }
        }
    }
}

