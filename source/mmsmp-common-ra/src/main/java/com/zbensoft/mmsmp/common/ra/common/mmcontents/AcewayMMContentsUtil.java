/*     */ package com.zbensoft.mmsmp.common.ra.common.mmcontents;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class AcewayMMContentsUtil
/*     */ {
/*  20 */   private static final Logger log = Logger.getLogger(AcewayMMContentsUtil.class);
/*     */ 
/*     */   public static byte[] encodeContent(Object content, AcewayMMContentType contentType)
/*     */     throws Exception
/*     */   {
/*  31 */     if ((content == null) || (contentType == null)) {
/*  32 */       throw new Exception("Pass null content or content-type!");
/*     */     }
/*  34 */     ByteArrayOutputStream bos = new ByteArrayOutputStream();
/*     */     try
/*     */     {
/*  38 */       if ((content instanceof byte[]))
/*  39 */         bos.write((byte[])content);
/*  40 */       else if ((content instanceof String))
/*  41 */         bos.write(((String)content).getBytes());
/*  42 */       else if ((content instanceof AcewayMMContent))
/*  43 */         ((AcewayMMContent)content).encode(bos);
/*  44 */       else if ((content instanceof AcewayMMContents))
/*  45 */         ((AcewayMMContents)content).encode(bos, contentType);
/*     */       else
/*  47 */         throw new Exception("Unsupported type of content!");
/*     */     }
/*     */     catch (IOException ioe)
/*     */     {
/*     */       try
/*     */       {
/*  53 */         bos.close();
/*     */       }
/*     */       catch (IOException localIOException1)
/*     */       {
/*     */       }
/*  58 */       throw new Exception(ioe.getMessage());
/*     */     }
/*     */     catch (AcewayMMContentException mce)
/*     */     {
/*     */       try
/*     */       {
/*  64 */         bos.close();
/*     */       }
/*     */       catch (IOException localIOException2)
/*     */       {
/*     */       }
/*  69 */       throw new Exception(mce.getMessage());
/*     */     }
/*     */     catch (AcewayMMContentsException mcse)
/*     */     {
/*     */       try
/*     */       {
/*  75 */         bos.close();
/*     */       }
/*     */       catch (IOException localIOException3)
/*     */       {
/*     */       }
/*  80 */       throw new Exception(mcse.getMessage());
/*     */     }
/*  82 */     byte[] buf = bos.toByteArray();
/*     */     try
/*     */     {
/*  86 */       bos.close();
/*     */     }
/*     */     catch (IOException localIOException4)
/*     */     {
/*     */     }
/*  91 */     return buf;
/*     */   }
/*     */ 
/*     */   public static AcewayMMContentInfo encodeContentWithInfo(Object content, AcewayMMContentType contentType)
/*     */     throws Exception
/*     */   {
/* 103 */     if ((content == null) || (contentType == null)) {
/* 104 */       throw new Exception("Pass null content or content-type!");
/*     */     }
/* 106 */     log.debug(" $$$$$$$$ encodeContentWithInfo(): ");
/* 107 */     AcewayMMContentInfo mmContentInfo = new AcewayMMContentInfo();
/*     */     try
/*     */     {
/* 111 */       if ((content instanceof byte[]))
/*     */       {
/* 113 */         AcewayMMPartInfo mmPartInfo = new AcewayMMPartInfo(contentType.toString(), 0, ((byte[])content).length - 1);
/*     */ 
/* 115 */         mmContentInfo.addPartInfo(mmPartInfo);
/* 116 */         mmContentInfo.getBos().write((byte[])content);
/*     */       }
/* 119 */       else if ((content instanceof String))
/*     */       {
/* 121 */         AcewayMMPartInfo mmPartInfo = new AcewayMMPartInfo(contentType.toString(), 0, ((String)content).getBytes().length - 1);
/*     */ 
/* 123 */         mmContentInfo.addPartInfo(mmPartInfo);
/* 124 */         mmContentInfo.getBos().write(((String)content).getBytes());
/*     */       }
/* 127 */       else if ((content instanceof AcewayMMContent))
/*     */       {
/* 129 */         ((AcewayMMContent)content).encode(mmContentInfo);
/*     */       }
/* 132 */       else if ((content instanceof AcewayMMContents))
/*     */       {
/* 134 */         ((AcewayMMContents)content).encode(mmContentInfo, contentType);
/*     */       }
/*     */       else
/*     */       {
/* 138 */         throw new Exception("Unsupported type of content!");
/*     */       }
/*     */     }
/*     */     catch (IOException ioe) {
/* 142 */       throw new Exception(ioe.getMessage());
/*     */     }
/*     */     catch (AcewayMMContentException mce)
/*     */     {
/* 146 */       throw new Exception(mce.getMessage());
/*     */     }
/*     */     catch (AcewayMMContentsException mcse)
/*     */     {
/* 150 */       throw new Exception(mcse.getMessage());
/*     */     }
/* 152 */     return mmContentInfo;
/*     */   }
/*     */ 
/*     */   public static AcewayMMContents decodeContent(AcewayByteArray baContent, AcewayMMContentType contentType)
/*     */     throws Exception
/*     */   {
/* 164 */     if ((baContent == null) || (contentType == null)) {
/* 165 */       throw new Exception("Pass null content or content-type!");
/*     */     }
/* 167 */     if (contentType.isMultipart())
/*     */     {
/* 169 */       AcewayMMContents contents = new AcewayMMContents();
/*     */       try
/*     */       {
/* 173 */         contents.decode(baContent, contentType);
/* 174 */         return contents;
/*     */       }
/*     */       catch (AcewayMMContentsException me)
/*     */       {
/* 178 */         throw new Exception(me.getMessage());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 183 */     AcewayMMContent part = new AcewayMMContent();
/* 184 */     part.setContentType(contentType.toString());
/* 185 */     ByteArrayInputStream bis = new ByteArrayInputStream(baContent.readLeft());
/* 186 */     part.decode(bis);
/* 187 */     bis.close();
/*     */ 
/* 189 */     AcewayMMContents contents = new AcewayMMContents();
/* 190 */     contents.addPart(part);
/* 191 */     return contents;
/*     */   }
/*     */ 
/*     */   public static AcewayMMContents decodeContent(AcewayByteArray baContent, AcewayMMContentType contentType, AcewayMMContentInfo partsInfo)
/*     */     throws Exception
/*     */   {
/* 205 */     if (partsInfo == null) {
/* 206 */       return decodeContent(baContent, contentType);
/*     */     }
/* 208 */     if ((baContent == null) || (contentType == null)) {
/* 209 */       throw new Exception("Pass null content or content-type!");
/*     */     }
/* 211 */     if (contentType.isMultipart())
/*     */     {
/* 213 */       AcewayMMContents contents = new AcewayMMContents();
/* 214 */       contents.decode(baContent, contentType, partsInfo);
/* 215 */       return contents;
/*     */     }
/*     */ 
/* 219 */     AcewayMMContent part = new AcewayMMContent();
/* 220 */     part.setContentType(contentType);
/* 221 */     baContent.locate(partsInfo.getStartPositionofFirstPart());
/* 222 */     part.setContent(baContent.readLeft());
/* 223 */     AcewayMMContents contents = new AcewayMMContents();
/* 224 */     contents.addPart(part);
/* 225 */     return contents;
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.mmcontents.AcewayMMContentsUtil
 * JD-Core Version:    0.6.0
 */