/*    */ package com.zbensoft.mmsmp.common.ra.vas.commons.xml4j;
/*    */


import com.zbensoft.mmsmp.common.ra.vas.commons.xml4j.core.XmlException;
import com.zbensoft.mmsmp.common.ra.vas.commons.xml4j.core.XmlReader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;

/*    */
/*    */
/*    */

/*    */
/*    */ public class Xml4jHandler
/*    */ {
/*    */   private String fileNameOrPath;
/*    */   private String packageName;
/*    */   private XmlReader xmlReader;
/*    */   private InputStream inputStream;
/*    */ 
/*    */   public Xml4jHandler(String packageName, InputStream inputStream)
/*    */   {
/* 24 */     this.packageName = packageName;
/* 25 */     this.inputStream = inputStream;
/*    */     try {
/* 27 */       this.xmlReader = new XmlReader(inputStream, packageName);
/*    */     } catch (XmlException e) {
/* 29 */       e.printStackTrace();
/*    */     }
/*    */   }
/*    */ 
/*    */   public Xml4jHandler(String fileNameOrPath, String packageName)
/*    */   {
/* 38 */     this.fileNameOrPath = fileNameOrPath;
/* 39 */     this.packageName = packageName;
/* 40 */     String filePath = null;
/* 41 */     if ((fileNameOrPath.indexOf("\\") != -1) || (fileNameOrPath.indexOf("/") != -1)) {
/* 42 */       filePath = fileNameOrPath;
/*    */     } else {
/* 44 */       URL url = Xml4jHandler.class.getResource(fileNameOrPath);
/* 45 */       if (url == null) {
/* 46 */         url = Xml4jHandler.class.getResource("/" + fileNameOrPath);
/*    */       }
/* 48 */       filePath = url.getPath();
/*    */     }
/* 50 */     InputStream in = null;
/*    */     try {
/* 52 */       in = new FileInputStream(filePath);
/* 53 */       this.xmlReader = new XmlReader(in, packageName);
/*    */     } catch (FileNotFoundException e) {
/* 55 */       e.printStackTrace();
/*    */     } catch (XmlException e) {
/* 57 */       e.printStackTrace();
/*    */     }
/*    */   }
/*    */ 
/*    */   public Object getRootConfigObject() {
/* 62 */     if (this.xmlReader != null) {
/* 63 */       return this.xmlReader.getRootObject();
/*    */     }
/* 65 */     return null;
/*    */   }
/*    */ 
/*    */   public String getFileNameOrPath()
/*    */   {
/* 70 */     return this.fileNameOrPath;
/*    */   }
/*    */ 
/*    */   public void setFileNameOrPath(String fileNameOrPath)
/*    */   {
/* 75 */     this.fileNameOrPath = fileNameOrPath;
/*    */   }
/*    */ 
/*    */   public String getPackageName()
/*    */   {
/* 80 */     return this.packageName;
/*    */   }
/*    */ 
/*    */   public void setPackageName(String packageName)
/*    */   {
/* 85 */     this.packageName = packageName;
/*    */   }
/*    */ 
/*    */   public InputStream getInputStream()
/*    */   {
/* 90 */     return this.inputStream;
/*    */   }
/*    */ 
/*    */   public void setInputStream(InputStream inputStream)
/*    */   {
/* 95 */     this.inputStream = inputStream;
/*    */   }
/*    */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\xml4j.jar
 * Qualified Name:     com.aceway.vas.xml4j.Xml4jHandler
 * JD-Core Version:    0.6.0
 */