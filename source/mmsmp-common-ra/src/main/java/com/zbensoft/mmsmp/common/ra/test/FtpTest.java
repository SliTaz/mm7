/*    */ package com.zbensoft.mmsmp.common.ra.test;
/*    */ import java.io.File;
/*    */ import java.io.FileInputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.net.SocketException;
/*    */ import org.apache.commons.net.ftp.FTPClient;
/*    */ import org.apache.commons.net.ftp.FTPFile;
/*    */ import org.apache.log4j.Logger;

import com.zbensoft.mmsmp.common.ra.util.ReadConfig;
import com.zbensoft.mmsmp.common.ra.util.XMLUtil;
/*    */ 
/*    */ public class FtpTest
/*    */ {
/* 20 */   private static final Logger log = Logger.getLogger(FtpTest.class);
/*    */ 
/*    */   public static void main(String[] argv)
/*    */   {
/* 25 */     FTPClient ftp = new FTPClient();
/*    */     try {
/* 27 */       ftp.connect(ReadConfig.getServer());
/* 28 */       ftp.login(ReadConfig.getUser(), ReadConfig.getPasswd());
/* 29 */       if (ftp.isConnected())
/*    */       {
/* 31 */         ftp.enterLocalPassiveMode();
/* 32 */         log.info("login to server success!!");
/* 33 */         log.info(ReadConfig.getFtpPath());
/* 34 */         ftp.changeWorkingDirectory(ReadConfig.getFtpPath());
/* 35 */         FTPFile[] files = ftp.listFiles();
/* 36 */         String reqfile = XMLUtil.createREQFile("11111111111", "23232323");
/* 37 */         File file = new File(ReadConfig.getLocalPath() + reqfile);
/* 38 */         InputStream in = new FileInputStream(file);
/*    */ 
/* 40 */         ftp.setFileType(2);
/* 41 */         ftp.setControlEncoding("gbk");
/* 42 */         ftp.setRemoteVerificationEnabled(true);
/* 43 */         boolean mark = ftp.storeFile(reqfile, in);
/* 44 */         if (mark)
/* 45 */           log.info("upload file success!");
/*    */         else {
/* 47 */           log.info("upload file failed!1");
/*    */         }
/*    */ 
/* 58 */         ftp.logout();
/* 59 */         ftp.disconnect();
/*    */ 
/* 61 */         File myfile = new File(ReadConfig.getLocalPath() + reqfile);
/* 62 */         log.info(Boolean.valueOf(myfile.exists()));
/* 63 */         myfile.delete();
/*    */       }
/*    */ 
/*    */     }
/*    */     catch (SocketException e)
/*    */     {
/* 74 */       e.printStackTrace();
/*    */     } catch (IOException e) {
/* 76 */       e.printStackTrace();
/*    */     }
/*    */   }
/*    */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.test.FtpTest
 * JD-Core Version:    0.6.0
 */