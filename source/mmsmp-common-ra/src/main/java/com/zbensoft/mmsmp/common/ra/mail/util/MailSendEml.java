/*     */ package com.zbensoft.mmsmp.common.ra.mail.util;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import javax.activation.DataHandler;
/*     */ import javax.mail.BodyPart;
/*     */ import javax.mail.Multipart;
/*     */ import javax.mail.internet.InternetHeaders;
/*     */ import javax.mail.internet.MimeBodyPart;
/*     */ import javax.mail.internet.MimeMultipart;
/*     */ import javax.mail.util.ByteArrayDataSource;

import com.zbensoft.mmsmp.common.ra.common.mmcontents.AcewayByteArray;
import com.zbensoft.mmsmp.common.ra.common.mmcontents.AcewayMMContent;
import com.zbensoft.mmsmp.common.ra.common.mmcontents.AcewayMMContents;
/*     */ 
/*     */ class MailSendEml extends SendMail
/*     */ {
/*     */   public MailSendEml(String smtpHost, String username, String password)
/*     */   {
/* 164 */     super(smtpHost, username, password);
/* 165 */     this.multipart = new MimeMultipart("related");
/*     */   }
/*     */   public void setMailContent(String emlContent) {
/* 168 */     processEml(emlContent);
/*     */   }
/*     */ 
/*     */   private void processEml(String emlContent) {
/* 172 */     ArrayList messageBodyList = new ArrayList();
/*     */     try {
/* 174 */       AcewayMMContents acewayMMContents = new AcewayMMContents();
/* 175 */       acewayMMContents.decode(new AcewayByteArray(getFileData(emlContent)));
/* 176 */       int contentCount = acewayMMContents.getNumParts();
/* 177 */       StringBuffer textContent = new StringBuffer();
/* 178 */       for (int i = 0; i < contentCount; i++) {
/* 179 */         AcewayMMContent acewayMMContent = acewayMMContents.getPart(i);
/* 180 */         String contentType = acewayMMContent.getHeaders().getHeader("Content-Type")[0];
/* 181 */         String contentId = acewayMMContent.getHeaders().getHeader("Content-ID")[0];
/* 182 */         String[] contentencoding = acewayMMContent.getHeaders().getHeader("Content-Transfer-Encoding");
/*     */ 
/* 184 */         if (contentType.indexOf("text/plain") != -1) {
/* 185 */           textContent.append(new String(acewayMMContent.getContent(), "utf-8")).append("\r\n\r\n");
/*     */         } else {
/* 187 */           if (contentType.indexOf("application/smil") != -1)
/*     */           {
/*     */             continue;
/*     */           }
/* 191 */           this.messageBodyPart = new MimeBodyPart();
/* 192 */           this.messageBodyPart.setHeader("Content-ID", contentId);
/* 193 */           this.messageBodyPart.setHeader("Content-Type", contentType);
/* 194 */           System.out.println(contentType);
/* 195 */           ByteArrayDataSource dataSource = new ByteArrayDataSource(acewayMMContent.getContent(), contentType);
/*     */ 
/* 200 */           this.messageBodyPart.setDataHandler(new DataHandler(dataSource));
/* 201 */           messageBodyList.add(this.messageBodyPart);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 206 */       this.messageBodyPart = new MimeBodyPart();
/* 207 */       this.messageBodyPart.setText(textContent.toString());
/* 208 */       this.multipart.addBodyPart(this.messageBodyPart);
/*     */ 
/* 211 */       for (int i = 0; i < messageBodyList.size(); i++) {
/* 212 */         this.multipart.addBodyPart((MimeBodyPart)messageBodyList.get(i));
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 217 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   private byte[] getFileData(String fileName) throws Exception {
/* 222 */     File file = new File(fileName);
/* 223 */     if (!file.exists()) {
/* 224 */       return new byte[0];
/*     */     }
/* 226 */     byte[] buff = new byte[10240];
/* 227 */     BufferedInputStream in = new BufferedInputStream(
/* 228 */       new FileInputStream(file));
/* 229 */     ByteArrayOutputStream baos = new ByteArrayOutputStream();
/* 230 */     int readedBytes = 0;
/* 231 */     while ((readedBytes = in.read(buff)) > 0) {
/* 232 */       baos.write(buff, 0, readedBytes);
/*     */     }
/* 234 */     in.close();
/* 235 */     byte[] result = baos.toByteArray();
/* 236 */     baos.close();
/* 237 */     return result;
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.mail.util.MailSendEml
 * JD-Core Version:    0.6.0
 */