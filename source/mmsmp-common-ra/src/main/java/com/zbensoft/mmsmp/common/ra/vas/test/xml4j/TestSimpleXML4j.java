/*    */ package com.zbensoft.mmsmp.common.ra.vas.test.xml4j;
import com.zbensoft.mmsmp.common.ra.vas.test.configbean.ConfigParameter;
import com.zbensoft.mmsmp.common.ra.vas.test.configbean.LogConfig;
import com.zbensoft.mmsmp.common.ra.vas.xml4j.Xml4jHandler;

/*    */ import java.io.PrintStream;
/*    */ 
/*    */ public class TestSimpleXML4j
/*    */ {
/*    */   public static void testSimpleConfig()
/*    */   {
/* 10 */     Xml4jHandler handler = new Xml4jHandler("simpleconfig.xml", "com.aceway.vas.test.configbean");
/* 11 */     ConfigParameter config = (ConfigParameter)handler.getRootConfigObject();
/* 12 */     LogConfig logConfig = config.getLogConfig();
/* 13 */     StringBuffer buffer = new StringBuffer();
/* 14 */     buffer.append("测试简单xml配置,");
/* 15 */     System.out.println(buffer.append(Util.logObj(logConfig)).append("version={").append(config.getVersion()).append("}"));
/*    */   }
/*    */ 
/*    */   public static void main(String[] args) {
/* 19 */     testSimpleConfig();
/*    */   }
/*    */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\xml4j.jar
 * Qualified Name:     com.aceway.vas.test.xml4j.TestSimpleXML4j
 * JD-Core Version:    0.6.0
 */