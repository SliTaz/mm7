/*     */ package com.zbensoft.mmsmp.common.ra.util;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.util.List;

/*     */ import org.apache.log4j.Logger;
/*     */ import org.dom4j.Document;
/*     */ import org.dom4j.DocumentException;
/*     */ import org.dom4j.DocumentHelper;
/*     */ import org.dom4j.Element;
/*     */ import org.dom4j.io.SAXReader;
/*     */ import org.dom4j.io.XMLWriter;

import com.zbensoft.mmsmp.common.ra.bo.FileInfo;
import com.zbensoft.mmsmp.common.ra.bo.OrderRelationUpdateInfo;
/*     */ 
/*     */ public class XMLUtil
/*     */ {
/*  19 */   private static final Logger log = Logger.getLogger(XMLUtil.class);
/*     */ 
/*     */   public static FileInfo parseReqFile(String fileName)
/*     */   {
/*  28 */     log.info("---------------------begin to parse REQ file -----------------------");
/*  29 */     SAXReader reader = new SAXReader();
/*  30 */     FileInfo fileInfo = new FileInfo();
/*     */     try {
/*  32 */       Document document = reader.read(new File(ReadConfig.getLocalPath() + fileName));
/*  33 */       List streamNos = document.selectNodes("//streamingNo");
/*  34 */       List timestamps = document.selectNodes("//timestamp");
/*     */ 
/*  36 */       List userIDs = document.selectNodes("//userID");
/*  37 */       List userIDTypes = document.selectNodes("//userIDType");
/*  38 */       List productIDs = document.selectNodes("//productID");
/*  39 */       List packageIDs = document.selectNodes("//packageID");
/*  40 */       List OPTypes = document.selectNodes("//OPType");
/*  41 */       List SUBStreamingNos = document.selectNodes("//SUBStreamingNo");
/*  42 */       if (streamNos != null) {
/*  43 */         Element streamingNo = (Element)streamNos.get(0);
/*  44 */         fileInfo.setStreamingNo(streamingNo.getText());
/*     */       }
/*  46 */       if (timestamps != null) {
/*  47 */         Element timestamp = (Element)timestamps.get(0);
/*  48 */         fileInfo.setTimkeStamp(timestamp.getText());
/*     */       }
/*     */ 
/*  51 */       if ((userIDs != null) && (userIDs.size() > 0)) {
/*  52 */         OrderRelationUpdateInfo[] ors = new OrderRelationUpdateInfo[userIDs.size()];
/*  53 */         for (int i = 0; i < userIDs.size(); i++) {
/*  54 */           OrderRelationUpdateInfo or = new OrderRelationUpdateInfo();
/*  55 */           Element userID = (Element)userIDs.get(i);
/*  56 */           Element userIDType = (Element)userIDTypes.get(i);
/*  57 */           Element productId = (Element)productIDs.get(i);
/*  58 */           Element SUBStreamingNo = (Element)SUBStreamingNos.get(i);
/*  59 */           Element OPType = (Element)OPTypes.get(i);
/*  60 */           String packageId = "";
/*  61 */           StringBuilder sb = new StringBuilder("subStreamNo:   " + SUBStreamingNo + "  userId:  " + userID)
/*  62 */             .append("productId :   " + productId)
/*  63 */             .append("OPType:    ").append(userIDType);
/*  64 */           log.info(sb.toString());
/*  65 */           or.setPackageID(packageId);
/*  66 */           or.setUserID(userID.getText());
/*  67 */           or.setUserIDType(Integer.valueOf(Integer.parseInt(userIDType.getText())));
/*  68 */           or.setProductID(productId.getText());
/*  69 */           or.setSUBStreamingNo(SUBStreamingNo.getText());
/*  70 */           or.setOPType(Integer.valueOf(Integer.parseInt(OPType.getText())));
/*  71 */           ors[i] = or;
/*     */         }
/*  73 */         fileInfo.setOrderRelationUpdateInfo(ors);
/*     */       }
/*     */     }
/*     */     catch (DocumentException e) {
/*  77 */       e.printStackTrace();
/*  78 */       log.info("parse req file error:   " + e.getMessage());
/*  79 */       return null;
/*     */     }
/*  81 */     log.info("---------------------parse REQ file success  - --------------------------");
/*     */ 
/*  83 */     return fileInfo;
/*     */   }
/*     */ 
/*     */   public static String createResponseFile(String fileName, int resultCode, String streamNo)
/*     */   {
/*  93 */     log.info("------------------------begin to create response file -------------------------");
/*  94 */     Document doc = DocumentHelper.createDocument();
/*  95 */     Element root = doc.addElement("root");
/*  96 */     root.addElement("streamingNo").setText(streamNo);
/*  97 */     root.addElement("resultCode").setText(String.valueOf(resultCode));
/*  98 */     String resFileName = fileName.substring(0, fileName.lastIndexOf(".")) + ".RSP";
/*     */     try
/*     */     {
/* 104 */       XMLWriter xmlWriter = new XMLWriter(
/* 106 */         new FileOutputStream(ReadConfig.getLocalPath() + resFileName));
/*     */ 
/* 108 */       xmlWriter.write(doc);
/*     */ 
/* 110 */       xmlWriter.close();
/*     */     }
/*     */     catch (Exception e) {
/* 113 */       log.info("----------------create response file error:  " + e.getMessage());
/* 114 */       return "";
/*     */     }
/* 116 */     log.info("--------------------------create response file :" + resFileName + " ------------");
/* 117 */     return resFileName;
/*     */   }
/*     */ 
/*     */   public static String createREQFile(String streamNo, String timestamp)
/*     */   {
/* 122 */     Document doc = DocumentHelper.createDocument();
/* 123 */     Element root = doc.addElement("root");
/* 124 */     root.addElement("streamingNo").setText(streamNo);
/* 125 */     root.addElement("timeStamp").setText(timestamp);
/* 126 */     String resFileName = "SPSUBSCRIPTION20090307.111.REQ";
/*     */     try
/*     */     {
/* 132 */       XMLWriter xmlWriter = new XMLWriter(
/* 134 */         new FileOutputStream(ReadConfig.getLocalPath() + resFileName));
/*     */ 
/* 136 */       xmlWriter.write(doc);
/*     */ 
/* 138 */       xmlWriter.close();
/*     */     }
/*     */     catch (Exception localException)
/*     */     {
/*     */     }
/*     */ 
/* 145 */     return resFileName;
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/* 152 */     String fileName = "SPSUBSCRIPTION20090307.111.REQ";
/* 153 */     log.info(fileName.substring(0, fileName.lastIndexOf(".")));
/* 154 */     XMLUtil xml = new XMLUtil();
/* 155 */     createResponseFile("SPSUBSCRIPTION20090307.111.REQ", 0, "11111111111111111111111");
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.util.XMLUtil
 * JD-Core Version:    0.6.0
 */