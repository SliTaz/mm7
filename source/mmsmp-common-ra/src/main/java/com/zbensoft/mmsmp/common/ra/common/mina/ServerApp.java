/*    */ package com.zbensoft.mmsmp.common.ra.common.mina;
import com.zbensoft.mmsmp.common.ra.common.mina.server.MessageQueueTcpServer;
/*    */ 
/*    */ public class ServerApp
/*    */ {
/*    */   public static void main(String[] args)
/*    */   {
/* 21 */     MessageQueueTcpServer server = new MessageQueueTcpServer();
/* 22 */     server.startListener(1234);
/*    */   }
/*    */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.mina.ServerApp
 * JD-Core Version:    0.6.0
 */