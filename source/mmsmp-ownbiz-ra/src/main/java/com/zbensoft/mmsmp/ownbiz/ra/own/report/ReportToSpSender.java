package com.zbensoft.mmsmp.ownbiz.ra.own.report;

import com.zbensoft.mmsmp.common.ra.common.message.MO_ReportMessage;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class ReportToSpSender {
    private static final Logger logger = Logger.getLogger(ReportToSpSender.class);
    private static ReportToSpSender reportToSpSender = null;

    public ReportToSpSender() {
    }

    public static ReportToSpSender getInstance() {
        if (reportToSpSender == null) {
            Class var0 = ReportToSpSender.class;
            synchronized(ReportToSpSender.class) {
                if (reportToSpSender == null) {
                    reportToSpSender = new ReportToSpSender();
                }
            }
        }

        return reportToSpSender;
    }

    public void sendReportToSp(MO_ReportMessage morpt) {
        String reportUrl = morpt.getReportUrl();
        if (reportUrl != null && reportUrl.trim().length() != 0) {
            HttpURLConnection httpURL = null;
            DataOutputStream dos = null;

            try {
                logger.info("=====>own cpreporturl:" + reportUrl);
                URL theURL = new URL(reportUrl);
                httpURL = (HttpURLConnection)theURL.openConnection();
                httpURL.setDoInput(true);
                httpURL.setDoOutput(true);
                httpURL.setRequestProperty("content-type", "text/xml;charset=\"UTF-8\"");
                httpURL.setRequestProperty("Content-Transfer-Encoding", "8bit");
                httpURL.setRequestProperty("Connection", "keep-alive");
                httpURL.setRequestMethod("POST");
                httpURL.setConnectTimeout(1000);
                httpURL.setReadTimeout(1000);
                String reportStr = morpt.getContent();
                InputStream in = new ByteArrayInputStream(reportStr.getBytes("UTF-8"));
                dos = new DataOutputStream(new BufferedOutputStream(httpURL.getOutputStream()));
                byte[] buf = reportStr.getBytes();
                boolean var9 = false;

                int n;
                while((n = in.read(buf)) != -1) {
                    dos.write(buf, 0, n);
                }

                dos.flush();
                logger.info("=====>own reportStr :" + reportStr);
                logger.info("=====>own send report to cp ,return code :" + httpURL.getResponseCode());
            } catch (MalformedURLException var28) {
                logger.info("=====>own send report to cp ,MalformedURLException :", var28);
            } catch (ProtocolException var29) {
                logger.info("=====>own send report to cp ,ProtocolException :", var29);
            } catch (UnsupportedEncodingException var30) {
                logger.info("=====>own send report to cp ,UnsupportedEncodingException :", var30);
            } catch (IOException var31) {
                try {
                    logger.info("=====>own send report to cp ,return code :" + httpURL.getResponseCode());
                } catch (IOException var27) {
                    logger.info("=====>own send report to cp ,IOException :", var31);
                }
            } catch (Exception var32) {
                logger.error("own sendReportToSp is error", var32);
            } finally {
                if (dos != null) {
                    try {
                        dos.close();
                    } catch (Exception var26) {
                        logger.error(var26.getMessage());
                    }
                }

                if (httpURL != null) {
                    httpURL.disconnect();
                }

            }

            logger.info("own send mo report to cp ");
        } else {
            logger.error("own cp report url is null....");
        }
    }

    public static void main(String[] args) {
        MO_ReportMessage morpt = new MO_ReportMessage();
        morpt.setReportUrl("http://127.0.0.1:80/spagent");
        morpt.setContent("qqqqqqqqqqq");
        ReportToSpSender sender = new ReportToSpSender();
        sender.sendReportToSp(morpt);
    }
}
