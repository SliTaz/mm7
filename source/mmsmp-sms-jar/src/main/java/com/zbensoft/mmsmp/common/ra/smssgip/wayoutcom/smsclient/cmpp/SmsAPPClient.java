package com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.smsclient.cmpp;

import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.cmppapi.CMPP_Command;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.cmppapi.CMPP_Submit;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.cmppapi.CMPP_SubmitResp;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.socket.ClientSocket;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.*;

public class SmsAPPClient extends ClientSocket {
    protected InetSocketAddress isa = null;
    protected static Timer timer = null;
    private static Logger log = Logger.getLogger(SmsAPPClient.class);

    public SmsAPPClient(String host, int port) {
        super(host, port);
    }

    protected int sendtoSMS(Object objSMS, Socket sc) {
        boolean var3 = true;

        try {
            if (sc == null) {
                return -1;
            }

            CMPP_Submit submit = (CMPP_Submit)objSMS;
            submit.write(sc);
        } catch (IOException var5) {
            log.error("ClientSocket,There is an IOException in sendtoSMS ", var5);
            return -1;
        }

        return this.readResp(sc) ? 1 : -1;
    }

    private boolean readResp(Socket sc) {
        CMPP_Command smgp_temp = null;

        try {
            smgp_temp = CMPP_Command.read(sc);
            log.debug("**************2收到smsAgentserver的响应在smsAPP.sendtoSMS********************");
        } catch (SocketTimeoutException var9) {
            log.error(var9);
            return false;
        } catch (Exception var10) {
            log.error("SMS,SPClient read error", var10);
            return false;
        }

        if (smgp_temp != null) {
            switch(smgp_temp.getCommandID()) {
                case -2147483644:
                    CMPP_SubmitResp submitresp = (CMPP_SubmitResp)smgp_temp;
                    int seq = submitresp.getSeqID();
                    long msgID = submitresp.getMsgID();
                    Map var7 = SmsClient.msgIDSeq;
                    synchronized(SmsClient.msgIDSeq) {
                        SmsClient.msgIDSeq.put(new Long(msgID), new Integer(submitresp.getSeqID()));
                    }
            }
        }

        return true;
    }

    protected boolean Login(Socket sc) {
        return true;
    }

    public static void main(String[] arg) {
        SmsAPPServer.startAppServer(7992);
        String smghost = "localhost";
        int smgport = 7813;
        SmsAPPClient client = new SmsAPPClient(smghost, smgport);
        Logger logger = Logger.getLogger("LOG");
        client.setLogHandler(logger);
        client.start(false);
        int count = 0;

        while(true) {
            CMPP_Submit ssubmit = null;
            String[] destTermId = new String[]{"01087654321"};
            new Date();
            Calendar cal = new GregorianCalendar();
            cal.set(2008, 10, 1);

            try {
                ssubmit = new CMPP_Submit(1, 1, 1, 1, "-MF", 0, "", 0, 0, 0, 0, "901238", "01", "0", cal.getTime(), new Date(), "09999", destTermId, 0, "test".getBytes(), "");
                client.send(ssubmit);
                ++count;
                if (count % 2000 == 0) {
                    System.out.println("在APPClient已经发送count:--->" + count);
                    return;
                }

                Thread.sleep(5L);
            } catch (Exception var11) {
                ;
            }
        }
    }
}

