/*    */ package com.zbensoft.mmsmp.common.ra.vas.test.configbean;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ public class Crbt
/*    */ {
/*    */   private String ipAddress;
/*    */   private int port;
/*  9 */   private List<WSHost> WSHosts = new ArrayList();
/*    */ 
/*    */   public String getIpAddress() {
/* 12 */     return this.ipAddress;
/*    */   }
/*    */   public void setIpAddress(String ipAddress) {
/* 15 */     this.ipAddress = ipAddress;
/*    */   }
/*    */   public int getPort() {
/* 18 */     return this.port;
/*    */   }
/*    */   public void setPort(int port) {
/* 21 */     this.port = port;
/*    */   }
/*    */   public List<WSHost> getWSHosts() {
/* 24 */     return this.WSHosts;
/*    */   }
/*    */   public void setWSHosts(List<WSHost> hosts) {
/* 27 */     this.WSHosts = hosts;
/*    */   }
/*    */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\xml4j.jar
 * Qualified Name:     com.aceway.vas.test.configbean.Crbt
 * JD-Core Version:    0.6.0
 */