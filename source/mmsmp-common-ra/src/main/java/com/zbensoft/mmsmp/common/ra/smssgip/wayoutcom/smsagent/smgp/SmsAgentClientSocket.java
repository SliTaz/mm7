package com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.smsagent.smgp;

import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.smgpapi.SMGP_Command;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.smgpapi.SMGP_Deliver;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.smgpapi.SMGP_DeliverResp;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.socket.ClientSocket;
import com.zbensoft.mmsmp.common.ra.vas.commons.tcp.impl.TypeConvert;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
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
            for(sendcount = 0; nRet <= 0 && sendcount < 3; ++sendcount) {
                nRet = this.sendtoSMS(smsSend);
                if (nRet > 0) {
                    break;
                }
            }

            if (nRet <= 0 && sendcount >= 3) {
                this.LogHandler.error("APPSERVER不可达，发送失败");
            } else {
                this.LogHandler.debug("agentclient发送成功需要的次数:" + sendcount);
            }
        }

    }

    public int sendtoSMS(Object objSMS) {
        Socket sc = null;

        byte nBytes;
        try {
            SMGP_Deliver deliver = (SMGP_Deliver)objSMS;
            sc = this.refreshSocket(deliver);
            if (sc == null) {
                SmsProcess.deliverQueue.remove(new Integer(deliver.getSeqID()));
                return 1;
            }

            deliver.write(sc);
            if (this.readResp(sc)) {
                ;
            }

            nBytes = 1;
        } catch (Exception var14) {
            var14.printStackTrace();
            this.LogHandler.error("SMS,AgentClient read resp error");
            nBytes = -1;
        } finally {
            try {
                while(sc != null && !sc.isClosed()) {
                    sc.close();
                }
            } catch (Exception var13) {
                var13.printStackTrace();
            }

        }

        return nBytes;
    }

    private boolean readResp(Socket sc) {
        try {
            SMGP_DeliverResp resp = (SMGP_DeliverResp)SMGP_Command.read(sc);
            if (resp == null) {
                Thread.sleep(1000L);
                return true;
            } else {
                this.dealResp(resp);
                return true;
            }
        } catch (Exception var3) {
            this.LogHandler.error("ClientSocket,There is an Exception in readResp ");
            return false;
        }
    }

    private int dealResp(SMGP_DeliverResp resp) {
        int result = resp.getStatus();
        if (result == 0) {
            System.err.println("deliverresp。。。。。。。");
            int seq = resp.getSeqID();
            SMGP_Command value = null;
            Map var6 = SmsProcess.deliverQueue;
            Object[] obj;
            synchronized(SmsProcess.deliverQueue) {
                obj = (Object[])SmsProcess.deliverQueue.remove(new Integer(seq));
            }

            LinkedList var10;
            if (obj != null) {
                value = (SMGP_Command)obj[1];
                var10 = SmsProcess.deliverResp;
                synchronized(SmsProcess.deliverResp) {
                    SmsProcess.deliverResp.addLast(value);
                }
            } else {
                var10 = SmsProcess.deliverResend;
                synchronized(SmsProcess.deliverResend) {
                    SmsProcess.deliverResend.addLast(new Integer(seq));
                }
            }

            return 1;
        } else {
            return -1;
        }
    }

    private Socket refreshSocket(SMGP_Deliver command) {
        int isReport = command.getIsReport();
        switch(isReport) {
            case 0:
                String destID = command.getDestTermID();
                InetSocketAddress address = null;
                HashMap deliver = SmsCenter.deliver;
                synchronized(deliver) {
                    address = (InetSocketAddress)deliver.get(destID);
                }

                if (address == null) {
                    this.LogHandler.error("没有相应的deliver目的地");
                    return null;
                }

                return this.createChannel(address);
            case 1:
                byte[] msgID = command.getReportMsgID();
                byte[] appID = new byte[3];
                System.arraycopy(msgID, 0, appID, 0, appID.length);
                int ID = TypeConvert.byte2int(appID);
                InetSocketAddress address2 = null;
                HashMap var7 = SmsCenter.report;
                synchronized(SmsCenter.report) {
                    address2 = (InetSocketAddress)SmsCenter.report.get(new Integer(ID));
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

