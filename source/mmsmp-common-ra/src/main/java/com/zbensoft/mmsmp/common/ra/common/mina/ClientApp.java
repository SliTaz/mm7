/*    */ package com.zbensoft.mmsmp.common.ra.common.mina;
import com.zbensoft.mmsmp.common.ra.common.message.AbstractMessage;
import com.zbensoft.mmsmp.common.ra.common.message.MT_MMMessage;
import com.zbensoft.mmsmp.common.ra.common.mina.client.MessageQueueTcpClient;

/*    */ import java.io.PrintStream;
/*    */ 
/*    */ public class ClientApp
/*    */ {
/*    */   public static void main(String[] args)
/*    */   {
/* 22 */     MessageQueueTcpClient client = new MessageQueueTcpClient();
/* 23 */     AbstractMessage msg = new MT_MMMessage();
/* 24 */     int result = client.connect("172.16.5.56", 1234, msg);
/* 25 */     String resultDesc = result == 0 ? "成功" : "失败";
/* 26 */     System.out.println("连接状态：" + resultDesc);
/* 27 */     client.disConnect();
/*    */   }
/*    */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.mina.ClientApp
 * JD-Core Version:    0.6.0
 */