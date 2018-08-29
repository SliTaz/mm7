/*    */ package com.zbensoft.mmsmp.common.ra.common.mmcontents;
/*    */ 
/*    */ import java.io.BufferedInputStream;
/*    */ import java.io.File;
/*    */ import java.io.FileInputStream;
/*    */ import java.io.PrintStream;
/*    */ import java.util.List;
/*    */ import org.dom4j.Document;
/*    */ import org.dom4j.io.SAXReader;
/*    */ 
/*    */ public class ReadMMSFileExample
/*    */ {
/*    */   public static void main(String[] args)
/*    */     throws Exception
/*    */   {
/* 19 */     AcewayMMContents contents1 = new AcewayMMContents();
/* 20 */     contents1.decode(new AcewayByteArray(getFileData("D:\\9159720080626135511203.eml")));
/*    */ 
/* 22 */     for (int i = 0; i < contents1.getNumParts(); i++) {
/* 23 */       AcewayMMContent acewayMMContent = contents1.getPart(i);
/* 24 */       System.out.println("part " + i + ":" + acewayMMContent.getContentType());
/* 25 */       System.out.println("part " + i + ":" + acewayMMContent.getMimeContentType().toString());
/* 26 */       System.out.println("part " + i + ":" + acewayMMContent.getHeaders().getHeader("Content-ID")[0]);
/* 27 */       System.out.println("part " + i + ":" + new String(acewayMMContent.getContent(), "UTF-8"));
/* 28 */       if (acewayMMContent.getContentType().indexOf("smil") < 0)
/*    */       {
/*    */         continue;
/*    */       }
/* 32 */       Document dom = null;
/* 33 */       SAXReader reader = new SAXReader(false);
/* 34 */       dom = reader.read(new File("D:/" + acewayMMContent.getHeaders().getHeader("Content-ID")[0].replace("<", "").replace(">", "")));
/* 35 */       List list = dom.selectNodes("//region");
/*    */ 
/* 37 */       System.out.println("hello" + list.size());
/*    */     }
/*    */ 
/* 41 */     System.out.println("finished");
/*    */   }
/*    */ 
/*    */   private static byte[] getFileData(String fileName) throws Exception {
/* 45 */     File file = new File(fileName);
/* 46 */     if (!file.exists()) {
/* 47 */       return null;
/*    */     }
/* 49 */     byte[] result = new byte[(int)file.length()];
/* 50 */     BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
/*    */ 
/* 52 */     int readedBytes = 0;
/*    */ 
/* 54 */     while (readedBytes < result.length) {
/* 55 */       readedBytes += in.read(result, readedBytes, in.available());
/*    */     }
/*    */ 
/* 58 */     in.close();
/* 59 */     return result;
/*    */   }
/*    */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.mmcontents.ReadMMSFileExample
 * JD-Core Version:    0.6.0
 */