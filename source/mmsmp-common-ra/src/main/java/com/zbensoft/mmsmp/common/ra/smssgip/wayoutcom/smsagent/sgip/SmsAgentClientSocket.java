package com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.smsagent.sgip;

import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.sgipapi.SGIP_Command;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.sgipapi.SGIP_DeliverResp;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.sgipapi.SGIP_ReportResp;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.socket.ClientSocket;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class SmsAgentClientSocket extends ClientSocket {
    private int countRecv = 0;

    public SmsAgentClientSocket() {
    }

    protected String getClientName() {
        return this.getClass().getName();
    }

    private Socket createChannel(InetSocketAddress address) {
        Socket sc = null;

        try {
            sc = new Socket();

            while(sc == null) {
                ;
            }

            while(!sc.isConnected()) {
                sc.connect(address);
            }

            sc.setKeepAlive(true);
            sc.setSoTimeout(20000);
            return sc;
        } catch (IOException var5) {
            var5.printStackTrace();
            this.LogHandler.error("SMS,AgentClient, AGENT--->APPSERVER Connect error");
        } catch (Exception var6) {
            this.LogHandler.error(var6.getMessage());
            var6.printStackTrace();
        }

        return null;
    }

    protected void sendObj(Object[] tmp_respQ) {
        for(int i = 0; i < tmp_respQ.length; ++i) {
            Object smsSend = tmp_respQ[i];
            int nRet = -1;

            int sendcount;
            for(sendcount = 1; nRet <= 0 && sendcount <= 3; ++sendcount) {
                nRet = this.sendtoSMS(smsSend);
                if (nRet > 0) {
                    break;
                }
            }

            if (nRet <= 0 && sendcount >= 3) {
                this.LogHandler.error("APPSERVER不可达，发送失败");
            } else {
                this.LogHandler.debug("agentclient发送成功,发送的次数:" + sendcount + "次");
            }
        }

    }

    public int sendtoSMS(Object objSMS) {
        SGIP_Command sgip_Cmd = null;
        SGIP_Command sgip_temp = new SGIP_Command();
        int nBytes;
        Socket sc = null;

        label138: {
            try {
                ByteBuffer sendByte = (ByteBuffer)objSMS;
                sc = this.refreshSocket(sendByte);
                if (sc != null) {
                    OutputStream writer = sc.getOutputStream();
                    byte[] ar = sendByte.array();
                    writer.write(ar);
                    sgip_Cmd = sgip_temp.read(sc);
                    break label138;
                }

                String key = "" + sendByte.getInt(8) + sendByte.getInt(12) + sendByte.getInt(16);
                SmsProcess.deliverQueue.remove(key);
            } catch (Exception var18) {
                this.LogHandler.error("SMS,AgentClient read resp error");
                nBytes = 0;
                break label138;
            } finally {
                try {
                    while(sc != null && !sc.isClosed()) {
                        sc.close();
                    }
                } catch (Exception var17) {
                    var17.printStackTrace();
                }

            }

            return 1;
        }

        if (sgip_Cmd == null) {
            this.LogHandler.error("在AGENTCLIENT里面读取RESP时得到NULL");
            return -1;
        } else {
            int nBytes2 = this.dealResp(sgip_Cmd);
            return nBytes2;
        }
    }

    private int dealResp(SGIP_Command sgip_Cmd) {
        long[] seq;
        String sseq;
        SGIP_Command value;
        boolean flag;
        Map var7;
        LinkedList var15;
        switch(sgip_Cmd.getCommandID()) {
            case -2147483644:
                SGIP_DeliverResp deliverresp = new SGIP_DeliverResp(sgip_Cmd);
                deliverresp.readbody();
                if (deliverresp.getResult() == 0) {
                    seq = new long[]{deliverresp.getSeqno_1(), (long)deliverresp.getSeqno_2(), (long)deliverresp.getSeqno_3()};
                    sseq = "" + seq[0] + seq[1] + seq[2];
                    value = null;
                    flag = false;
                    var7 = SmsProcess.deliverQueue;
                    synchronized(SmsProcess.deliverQueue) {
                        flag = SmsProcess.deliverQueue.containsKey(sseq);
                        if (flag) {
                            value = (SGIP_Command)SmsProcess.deliverQueue.remove(sseq);
                        }
                    }

                    if (flag) {
                        if (value != null) {
                            var15 = SmsProcess.deliverResp;
                            synchronized(SmsProcess.deliverResp) {
                                SmsProcess.deliverResp.addLast(value);
                            }
                        }
                    } else {
                        var15 = SmsProcess.deliverResend;
                        synchronized(SmsProcess.deliverResend) {
                            SmsProcess.deliverResend.addLast(seq);
                        }
                    }

                    return 1;
                } else {
                    return -1;
                }
            case -2147483643:
                SGIP_ReportResp reportresp = new SGIP_ReportResp(sgip_Cmd);
                reportresp.readbody();
                if (reportresp.getResult() == 0) {
                    seq = new long[]{reportresp.getSeqno_1(), (long)reportresp.getSeqno_2(), (long)reportresp.getSeqno_3()};
                    sseq = "" + seq[0] + seq[1] + seq[2];
                    value = null;
                    flag = false;
                    var7 = SmsProcess.reportQueue;
                    synchronized(SmsProcess.reportQueue) {
                        flag = SmsProcess.reportQueue.containsKey(sseq);
                        if (flag) {
                            value = (SGIP_Command)SmsProcess.reportQueue.remove(sseq);
                        }
                    }

                    if (flag) {
                        if (value != null) {
                            var15 = SmsProcess.reportResp;
                            synchronized(SmsProcess.reportResp) {
                                SmsProcess.reportResp.addLast(value);
                            }
                        }
                    } else {
                        var15 = SmsProcess.reportResend;
                        synchronized(SmsProcess.reportResend) {
                            SmsProcess.reportResend.addLast(seq);
                        }
                    }

                    return 1;
                }

                return -1;
            default:
                return -1;
        }
    }

    private Socket refreshSocket(ByteBuffer command) {
        Integer appID;
        switch(command.getInt(4)) {
            case 4:
                int spNumberLen = 21;
                String spNumber = (new String(command.array(), 41, spNumberLen)).trim();
                appID = null;
                HashMap var11 = SmsCenter.deliver;
                InetSocketAddress address;
                synchronized(SmsCenter.deliver) {
                    address = (InetSocketAddress)SmsCenter.deliver.get(spNumber);
                }

                if (address == null) {
                    this.LogHandler.error("没有相应的deliver目的地");
                    return null;
                }

                return this.createChannel(address);
            case 5:
                long seq1 = (long)command.getInt(20);
                appID = new Integer((int)seq1);
                InetSocketAddress address2 = null;
                HashMap var6 = SmsCenter.report;
                synchronized(SmsCenter.report) {
                    this.LogHandler.debug(appID);
                    address2 = (InetSocketAddress)SmsCenter.report.get(appID);
                    this.LogHandler.debug(address2);
                }

                if (address2 == null) {
                    this.LogHandler.error("没有相应的deliver目的地");
                    return null;
                }

                return this.createChannel(address2);
            default:
                return null;
        }
    }

    protected boolean Login() {
        return true;
    }

    public static void main(String[] arg) {
    }
}

