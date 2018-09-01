package com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.smsclient.cmpp;

import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.produce_consume.Channel;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.smsclient.cmpp.dbaccess.DbAccess;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.smsclient.cmpp.dbaccess.OperData;
import org.apache.log4j.Logger;

import java.io.*;
import java.sql.Timestamp;
import java.util.*;

public class File2SMS {
    private static Logger log = Logger.getLogger(File2SMS.class);
    private String srcPath;
    private String destPath;
    private Channel ch = new Channel(100);
    private String pushType = DbAccess.getProperty("pushType");
    private String contentSeperator = DbAccess.getProperty("contentSeperator");
    private String smsSuccessType = DbAccess.getProperty("smsSuccessType");
    private String smsSuccessFlag = DbAccess.getProperty("smsSuccessFlag");

    public File2SMS(Channel ch, String srcPath, String destPath) {
        this.ch = ch;
        this.srcPath = srcPath;
        this.destPath = destPath;
    }

    private void searchFile() throws Exception {
        SearchDir dir = new SearchDir(this.srcPath, new SearchDir.MyDealFile() {
            public void doFile(File file) {
                String attr = "";
                attr = file.isFile() ? "normal    file:" : "Directory file:";
                if (file.isFile()) {
                    File2SMS.this.readSms(File2SMS.this.ch, file);
                    File destFile = new File(File2SMS.this.destPath + file.getName());
                    if (file.renameTo(destFile)) {
                        File2SMS.log.info("remove " + attr + file.getAbsolutePath());
                    } else {
                        File tmpFile = new File(destFile.getAbsolutePath() + "." + System.currentTimeMillis());
                        if (!file.renameTo(tmpFile)) {
                            File2SMS.log.error("failed to remove " + attr + file.getAbsolutePath());
                        } else {
                            File2SMS.log.info("remove " + attr + file.getAbsolutePath() + " to:" + tmpFile.getAbsolutePath());
                        }
                    }
                }

            }
        }, new FilenameFilter() {
            public boolean accept(File dir, String name) {
                File namePath = new File(dir.getAbsolutePath() + File.separator + name);
                return namePath.isDirectory() || name.toLowerCase().startsWith(File2SMS.this.pushType);
            }
        });
        dir.doit();
    }

    private void readSms(Channel ch, File file) {
        if (ch != null && file != null) {
            String opID = file.getName();
            int index = opID.lastIndexOf(46);
            opID = opID.substring(index + 1);
            log.debug("Read file:" + file.getAbsolutePath());
            BufferedReader bufferedReader = null;

            try {
                bufferedReader = new BufferedReader(new FileReader(file));
                String from = bufferedReader.readLine();
                if (StringCheck.allDigital(from)) {
                    StringBuffer content = new StringBuffer();

                    while(true) {
                        String to = bufferedReader.readLine();
                        if (to != null && to.equals(this.contentSeperator)) {
                            content.deleteCharAt(content.length() - 1);
                            to = "";

                            while(true) {
                                to = bufferedReader.readLine();
                                if (to == null) {
                                    return;
                                }

                                if (!to.equals("")) {
                                    if (!StringCheck.allDigital(to)) {
                                        log.error("to:" + to + " is not validated in File:" + file.getAbsolutePath());
                                    } else {
                                        try {
                                            ch.put(new SMSObj(from, to, content.toString(), (String)null, opID, (String)null, (String)null, this.pushType, (Timestamp)null));
                                        } catch (InterruptedException var20) {
                                            log.error(var20);
                                        }
                                    }
                                }
                            }
                        }

                        content.append(to);
                        content.append("\n");
                    }
                }

                log.error("from:" + from + " is not validated in File:" + file.getAbsolutePath());
            } catch (IOException var21) {
                log.error(var21);
                return;
            } finally {
                try {
                    bufferedReader.close();
                    log.debug("Leave file:" + file.getAbsolutePath());
                } catch (IOException var19) {
                    log.error(var19);
                }

            }

        }
    }

    private void statistic() {
        Set keys = SmsClient.msgIDStatus.keySet();
        Object[] objs = keys.toArray();
        List smsObj = new ArrayList();

        for(int i = 0; i < objs.length; ++i) {
            Long key = (Long)objs[i];
            String status = (String)SmsClient.msgIDStatus.get(key);
            if (status != null) {
                SmsClient.msgIDStatus.remove(key);
            }

            Integer seq = (Integer)SmsClient.msgIDSeq.get(key);
            if (seq != null) {
                SmsClient.msgIDSeq.remove(key);
            }

            SMSObj obj = (SMSObj)SmsClient.seqContent.get(seq);
            if (obj != null) {
                SmsClient.seqContent.remove(seq);
                obj.setPushID(seq.toString());
                obj.setErrorType(status);
                if (status.equals(this.smsSuccessType)) {
                    obj.setPushFlag(this.smsSuccessFlag);
                }

                smsObj.add(obj);
                String print = "";
                print = "\nfrom:" + obj.getFrom() + "\nto:" + obj.getTo() + "\ncontent:\n" + obj.getContent() + "\nstatus:" + status;
                log.info(print);
            }
        }

        OperData.insertSMSLog(smsObj);
        this.monitor();
    }

    private void monitor() {
        log.info("There are " + SmsClient.msgIDStatus.size() + " Obj in msgIDStatus.");
        log.info("There are " + SmsClient.msgIDSeq.size() + " Obj in msgIDSeq.");
        log.info("There are " + SmsClient.seqContent.size() + " Obj in seqContent.");
        log.info("There are " + this.ch.getSize() + " Obj in SMS Queue.");
    }

    public void startStatistic() {
        (new Timer()).schedule(new StatisticTask(), 0L, (long)Integer.parseInt(DbAccess.getProperty("statisticCyc")));
    }

    public void startSearchFile() {
        new SearchFileThread().start();
    }

    public void startSendSMS() {
        String serverIP = DbAccess.getProperty("serverIP");
        int serverPort = Integer.parseInt(DbAccess.getProperty("serverPort"));
        int listeningPort = Integer.parseInt(DbAccess.getProperty("listeningPort"));
        SmsClient client = SmsClient.getSmsClient(serverIP, serverPort, listeningPort);
        SendThread send = new SendThread(this.ch, client);
        send.start();
    }

    public static void main(String[] args) {
        Channel ch = new Channel(100);
        File2SMS sms = new File2SMS(ch, DbAccess.getProperty("fileSrc"), DbAccess.getProperty("fileDest"));
        sms.startSearchFile();
        sms.startStatistic();
        sms.startSendSMS();
    }

    private class SearchFileThread extends Thread {
        private SearchFileThread() {
        }

        public void run() {
            this.setName("SearchFileThread");
            this.setPriority(1);
            int searchFileCyc = Integer.parseInt(DbAccess.getProperty("searchFileCyc"));

            while(true) {
                while(true) {
                    try {
                        File2SMS.this.searchFile();
                        Thread.sleep((long)searchFileCyc);
                    } catch (Exception var3) {
                        File2SMS.log.error("Exception", var3);
                    }
                }
            }
        }
    }

    private class SendThread extends Thread {
        private Channel ch;
        private SmsClient client;

        public SendThread(Channel ch, SmsClient client) {
            super("SendThread");
            this.ch = ch;
            this.client = client;
        }

        public void run() {
            while(true) {
                SMSObj obj = null;

                try {
                    obj = (SMSObj)this.ch.remove();
                } catch (InterruptedException var3) {
                    File2SMS.log.error(var3);
                    File2SMS.log.error("Thread SendThread exits......");
                    return;
                }

                obj.setPushTime(new Timestamp(Calendar.getInstance().getTimeInMillis()));
                this.client.sendSMS(obj);
            }
        }
    }

    private class StatisticTask extends TimerTask {
        private StatisticTask() {
        }

        public void run() {
            try {
                File2SMS.this.statistic();
            } catch (Exception var2) {
                ;
            }

        }
    }
}

