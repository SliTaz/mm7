/*     */ package com.zbensoft.mmsmp.common.ra.mail.util;
/*     */ 
/*     */ import javax.mail.Authenticator;
/*     */ import javax.mail.PasswordAuthentication;
/*     */ 
/*     */ class MailAuthenticator extends Authenticator
/*     */ {
/* 138 */   private String username = null;
/* 139 */   private String userpasswd = null;
/*     */ 
/*     */   public MailAuthenticator() {
/*     */   }
/*     */   public MailAuthenticator(String username, String userpasswd) {
/* 144 */     this.username = username;
/* 145 */     this.userpasswd = userpasswd;
/*     */   }
/*     */ 
/*     */   public void setUserName(String username) {
/* 149 */     this.username = username;
/*     */   }
/*     */ 
/*     */   public void setPassword(String password) {
/* 153 */     this.userpasswd = password;
/*     */   }
/*     */ 
/*     */   public PasswordAuthentication getPasswordAuthentication() {
/* 157 */     return new PasswordAuthentication(this.username, this.userpasswd);
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.mail.util.MailAuthenticator
 * JD-Core Version:    0.6.0
 */