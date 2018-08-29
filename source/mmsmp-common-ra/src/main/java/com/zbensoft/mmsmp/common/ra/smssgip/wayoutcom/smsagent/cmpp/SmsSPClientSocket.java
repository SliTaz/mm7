package com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.smsagent.cmpp;

import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.cmppapi.*;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.dbAccess.OperData4cmpp;
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
    volatile int rmvcount = 0;

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
            CMPP_Command comm = (CMPP_Command)objSMS;
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

                comm.write(this.sc);
                ++SmsProcess.test1;
                ret = 1;
                Integer cyc2smg = (Integer)SmsCenter.parameter.get("Cyc2smg");
                Thread.sleep((long)cyc2smg);
            } catch (Exception var25) {
                this.LogHandler.error("ClientSocket,There is an IOException in sendtoSMS ");
                var25.printStackTrace();
                ret = -1;
                ++SmsProcess.writeError;
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
        LinkedHashMap var2 = this.sendBuffer;
        synchronized(this.sendBuffer) {
            Collection c = this.sendBuffer.values();

            for(Iterator it = c.iterator(); it.hasNext(); ++this.rmvcount) {
                Object[] value = (Object[])it.next();
                Long time = (Long)value[0];
                Calendar cal = new GregorianCalendar();
                long now = cal.getTimeInMillis();
                long differ = now - time;
                int len = this.sendBuffer.size();
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
            CMPP_Command resp = CMPP_Command.read(sc);
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
            this.LogHandler.error("SmsSPClientSocket", var4);
            this.activeTestThread.setActiveFalse();
            return false;
        }
    }

    private void dealResp(CMPP_Command resp, Socket sc) throws IOException {
        int requestID = resp.getCommandID();
        long msgID;
        switch(requestID) {
            case -2147483644:
                CMPP_SubmitResp com = (CMPP_SubmitResp)resp;
                int seq = com.getSeqID();
                msgID = com.getMsgID();
                int status = com.getStatus();
                LinkedHashMap var9 = this.sendBuffer;
                synchronized(this.sendBuffer) {
                    if (this.sendBuffer.remove(new Integer(seq)) != null) {
                        this.sendBuffer.notifyAll();
                    }
                }

                boolean flag = false;
                Map var10 = SmsProcess.submitQueue;
                synchronized(SmsProcess.submitQueue) {
                    Set entry = SmsProcess.submitQueue.entrySet();
                    Iterator it = entry.iterator();

                    while(it.hasNext()) {
                        Map.Entry en = (Map.Entry)it.next();
                        SubmitKey key = (SubmitKey)en.getKey();
                        Object value = en.getValue();
                        CMPP_Command submit = key.getSubmit();
                        if (submit.getSeqID() == seq && key.getMsgID() == null) {
                            it.remove();
                            key.setMsgID(new Long(msgID));
                            SmsProcess.submitQueue.put(key, value);
                            flag = true;
                            break;
                        }
                    }
                }

                if (!flag) {
                    Object[] obj = new Object[]{new Integer(seq), new Long(msgID)};
                    OperData4cmpp data = new OperData4cmpp();
                    data.submitTimeoutResp(obj);
                }
                break;
            case 2:
                CMPP_UnbindResp unbindResp = new CMPP_UnbindResp((CMPP_Unbind)resp);
                unbindResp.write(sc);
                this.activeTestThread.setActiveFalse();
                break;
            case 5:
                CMPP_Deliver deliver = (CMPP_Deliver)resp;
                CMPP_DeliverResp deliverResp = new CMPP_DeliverResp(deliver);
                deliverResp.write(sc);
                msgID = deliver.getReportMsgID();
                String stat = deliver.getReportStat();
                --SmsProcess.test1;
                this.DownHandler.receive(deliver);
                break;
            case 8:
                CMPP_ActiveTestResp activeResp = new CMPP_ActiveTestResp((CMPP_ActiveTest)resp);
                activeResp.write(sc);
        }

    }

    protected boolean Login(Socket sc) {
        CMPP_Bind cmd_Bind = null;
        CMPP_BindResp cmd_BindResp = null;
        OperData4cmpp data = new OperData4cmpp();
        HashMap info = data.loadConfigInfo();
        SmsCenter.parameter = data.loadParameter();
        SmsCenter.login = data.loadLogin();
        String source_Addr = (String)SmsCenter.login.get("Source_Add");
        int version = Integer.parseInt((String)SmsCenter.login.get("Version"), 16);
        String shared_Secret = (String)SmsCenter.login.get("Secret");
        cmd_Bind = new CMPP_Bind(source_Addr, version, shared_Secret, new Date());

        try {
            cmd_Bind.write(sc);
            cmd_BindResp = (CMPP_BindResp)CMPP_Command.read(sc);
            if (cmd_BindResp.getStatus() == 0) {
                byte[] server = cmd_BindResp.getAuthenticatorServer();
                byte v = cmd_BindResp.getVersion();
                return true;
            } else {
                return false;
            }
        } catch (IOException var11) {
            this.LogHandler.error("ClientSocket,There is an IOException in Login " + this.name);
            return false;
        } catch (Exception var12) {
            this.LogHandler.error("ClientSocket,There is an Exception in Login " + this.name);
            return false;
        }
    }

    protected void sendActiveTest() throws Exception {
        (new CMPP_ActiveTest()).write(this.sc);
    }

    public static void main(String[] arg) {
        String smghost = "localhost";
        SmsSPClientSocket client = new SmsSPClientSocket("localhost", 7890);
        Logger logger = Logger.getLogger("LOG");
        client.setLogHandler(logger);
        client.start(true);
        CMPP_Submit ssubmit = null;
        String[] destTermId = new String[]{"01087654321"};

        try {
            ssubmit = new CMPP_Submit(1, 1, 1, 1, "wayout", 0, "", 0, 0, 0, 0, "910084", "01", "0", new Date(), new Date(), "2688", destTermId, 0, "test".getBytes(), "");
        } catch (Exception var10) {
            var10.printStackTrace();
        }

        int count = 0;

        while(true) {
            client.send(ssubmit);
            ++count;
            if (count % 100 == 0) {
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
