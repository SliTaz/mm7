/*     */ package com.zbensoft.mmsmp.common.ra.vas.xml4j.core;
/*     */ 
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.URL;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import javax.xml.parsers.SAXParser;
/*     */ import javax.xml.parsers.SAXParserFactory;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ public class XmlReader
/*     */ {
/*     */   public static final String EX_FILE = "Error reading file: ";
/*     */   public static final String EX_URL = "Error reading url: ";
/*     */   public static final String EX_PARSING = "Error parsing xml. ";
/*     */   public static final String EX_NULL_READER = "Reader object is null. ";
/*     */   private String source;
/*     */   private Object rootObject;
/*  29 */   private XmlConfig config = XmlConfig.getInstance();
/*     */ 
/*     */   public XmlReader(String fileName, String targetPackage)
/*     */     throws XmlException
/*     */   {
/*     */     try
/*     */     {
/*  39 */       readXml(new FileInputStream(fileName), targetPackage);
/*  40 */       this.source = fileName;
/*     */     } catch (IOException ioe) {
/*  42 */       throw new XmlException("Error reading file: " + fileName + ", " + ioe.toString());
/*     */     }
/*     */   }
/*     */ 
/*     */   public XmlReader(URL url, String targetPackage)
/*     */     throws XmlException
/*     */   {
/*     */     try
/*     */     {
/*  54 */       readXml(url.openStream(), targetPackage);
/*  55 */       this.source = url.toString();
/*     */     } catch (IOException ioe) {
/*  57 */       throw new XmlException("Error reading url: " + this.source + ", " + ioe.toString());
/*     */     }
/*     */   }
/*     */ 
/*     */   public XmlReader(InputStream reader, String targetPackage)
/*     */     throws XmlException
/*     */   {
/*  68 */     if (reader == null)
/*  69 */       throw new XmlException("Reader object is null. ");
/*  70 */     readXml(reader, targetPackage);
/*  71 */     this.source = null;
/*     */   }
/*     */ 
/*     */   private boolean readXml(InputStream inputStream, String targetPackage)
/*     */     throws XmlException
/*     */   {
/*  83 */     boolean result = false;
/*  84 */     if (inputStream == null) {
/*  85 */       throw new XmlException("Reader object is null. ");
/*     */     }
/*  87 */     if (targetPackage == null) {
/*  88 */       targetPackage = "";
/*     */     }
/*  90 */     if ((!"".equals(targetPackage)) && (!targetPackage.endsWith("."))) {
/*  91 */       targetPackage = targetPackage + ".";
/*     */     }
/*  93 */     this.rootObject = null;
/*     */     try {
/*  95 */       SAXParserFactory factory = SAXParserFactory.newInstance();
/*  96 */       factory.setValidating(true);
/*  97 */       factory.setNamespaceAware(true);
/*  98 */       ReaderContentHandler handler = new ReaderContentHandler(targetPackage);
/*     */       try {
/* 100 */         SAXParser parser = factory.newSAXParser();
/* 101 */         parser.parse(inputStream, handler);
/*     */       } catch (ParserConfigurationException pce) {
/*     */         try {
/* 104 */           factory.setValidating(false);
/* 105 */           factory.setNamespaceAware(false);
/* 106 */           SAXParser parser = factory.newSAXParser();
/* 107 */           parser.parse(inputStream, handler);
/*     */         } catch (Exception e) {
/* 109 */           throw new XmlException("Error parsing xml. " + e.toString());
/*     */         }
/*     */       } catch (SAXException se) {
/* 112 */         if ((se.getException() instanceof XmlException)) {
/* 113 */           throw ((XmlException)se.getException());
/*     */         }
/* 115 */         se.printStackTrace();
/* 116 */         throw new XmlException("Error parsing xml. " + se.toString());
/*     */       }
/*     */       catch (IOException ioe) {
/* 119 */         throw new XmlException("Error parsing xml. " + ioe.toString());
/*     */       }
/* 121 */       this.rootObject = handler.getResult();
/* 122 */       handler = null;
/* 123 */       result = true;
/*     */     }
/*     */     finally {
/*     */       try {
/* 127 */         inputStream.close();
/*     */       } catch (IOException ioe) {
/* 129 */         ioe.printStackTrace();
/*     */       }
/*     */     }
/* 132 */     return result;
/*     */   }
/*     */ 
/*     */   public Object getRootObject() {
/* 136 */     return this.rootObject;
/*     */   }
/*     */ 
/*     */   public XmlConfig getConfig() {
/* 140 */     return this.config;
/*     */   }
/*     */ 
/*     */   public void setConfig(XmlConfig config) {
/* 144 */     if (config != null)
/* 145 */       this.config = config;
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\xml4j.jar
 * Qualified Name:     com.aceway.vas.xml4j.core.XmlReader
 * JD-Core Version:    0.6.0
 */