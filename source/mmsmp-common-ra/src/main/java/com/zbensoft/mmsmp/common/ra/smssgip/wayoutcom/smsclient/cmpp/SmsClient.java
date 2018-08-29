package com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.smsclient.cmpp;

import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.cmppapi.CMPP_Submit;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.smsclient.cmpp.dbaccess.DbAccess;
import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.util.*;

public class SmsClient {
    private SmsAPPClient client;
    private static SmsClient smsClient;
    private static Logger log = Logger.getLogger(SmsClient.class);
    private int lenPerSms = Integer.parseInt(DbAccess.getProperty("lenPerSms"));
    public static Map seqContent = Collections.synchronizedMap(new LinkedHashMap());
    public static Map msgIDSeq = Collections.synchronizedMap(new LinkedHashMap());
    public static Map msgIDStatus = Collections.synchronizedMap(new LinkedHashMap());

    private SmsClient(String serverIP, int serverPort, int listeningPort) {
        SmsAPPServer.startAppServer(listeningPort);
        this.client = new SmsAPPClient(serverIP, serverPort);
        this.client.start(false);
    }

    public static SmsClient getSmsClient(String serverIP, int serverPort, int listeningPort) {
        if (smsClient == null) {
            smsClient = new SmsClient(serverIP, serverPort, listeningPort);
        }

        return smsClient;
    }

    public void sendSMS(SMSObj obj) {
        int seq = this.sendSMS(obj.getFrom(), obj.getTo(), obj.getContent());
        seqContent.put(new Integer(seq), obj);
    }

    private int sendSMS(String from, String to, byte[] content) {
        CMPP_Submit ssubmit = null;
        String[] destTermId = new String[]{to};
        new Date();
        Calendar cal = new GregorianCalendar();
        ssubmit = new CMPP_Submit(1, 1, 1, 1, "-MF", 0, "", 0, 0, 0, 8, "901238", "01", "0", cal.getTime(), new Date(), from, destTermId, 0, content, "");
        this.client.send(ssubmit);
        return ssubmit.getSeqID();
    }

    public int sendSMS(String from, String to, String content) {
        int len = content.length();
        int count = len / this.lenPerSms;
        List contents = new ArrayList();

        int rest;
        String con;
        int seq;
        for(rest = 0; rest < count; ++rest) {
            seq = rest * this.lenPerSms;
            con = content.substring(seq, seq + this.lenPerSms);
            contents.add(con);
        }

        rest = len % this.lenPerSms;
        if (rest != 0) {
            con = content.substring(len - rest);
            contents.add(con);
        }

        int size = contents.size();
        seq = 0;

        for(int i = 0; i < size; ++i) {
            String con2 = (String)contents.get(i);
            byte[] bytes = (byte[])null;

            try {
                bytes = con2.getBytes("UNICODE");
            } catch (UnsupportedEncodingException var14) {
                log.error(var14);
            }

            byte[] uniBytes = new byte[bytes.length - 2];
            System.arraycopy(bytes, 2, uniBytes, 0, uniBytes.length);
            seq = this.sendSMS(from, to, uniBytes);
        }

        return seq;
    }

    public void setHashMap(Map seqContent, Map msgIDSeq, Map msgIDStatus) {
        seqContent = Collections.synchronizedMap(seqContent);
        msgIDSeq = Collections.synchronizedMap(msgIDSeq);
        msgIDStatus = Collections.synchronizedMap(msgIDStatus);
    }

    public static void main(String[] args) {
        SmsClient client = getSmsClient("127.0.0.1", 7813, 7992);

        int i;
        for(i = 0; i < 30; ++i) {
            client.sendSMS("130", "13012345678", "Hello SMS!");
        }

        int i2 = 0;
        i2 = i2 + 1;
    }
}

