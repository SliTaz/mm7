/*    */ package com.zbensoft.mmsmp.common.ra.vas.test.xml4j;
import com.zbensoft.mmsmp.common.ra.vas.test.configbean.Crbt;
import com.zbensoft.mmsmp.common.ra.vas.test.configbean.CrbtConfig;
import com.zbensoft.mmsmp.common.ra.vas.xml4j.Xml4jHandler;

/*    */ import java.io.PrintStream;
/*    */ import java.util.List;
/*    */ 
/*    */ public class TestComplexXML4j
/*    */ {
/*    */   public static void testComplexXml4j()
/*    */   {
/* 11 */     Xml4jHandler handler = new Xml4jHandler("complexconfig.xml", "com.aceway.vas.test.configbean");
/* 12 */     CrbtConfig config = (CrbtConfig)handler.getRootConfigObject();
/* 13 */     List<Crbt> crbts = config.getCrbts();
/* 14 */     StringBuffer buffer = new StringBuffer();
/* 15 */     buffer.append("测试复杂xml配置,");
/* 16 */     for (Crbt crbt : crbts) {
/* 17 */       buffer.append(Util.logObj(crbt)).append(Util.logObj(crbt.getWSHosts().get(0)));
/*    */     }
/* 19 */     System.out.println(buffer.toString());
/*    */   }
/*    */ 
/*    */   public static void main(String[] args) {
/* 23 */     testComplexXml4j();
/*    */   }
/*    */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\xml4j.jar
 * Qualified Name:     com.aceway.vas.test.xml4j.TestComplexXML4j
 * JD-Core Version:    0.6.0
 */