/*     */ package com.zbensoft.mmsmp.common.ra.common.mmcontents;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.util.ArrayList;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class AcewayMMContents
/*     */   implements Cloneable
/*     */ {
/*  17 */   private static final Logger logger = Logger.getLogger(AcewayMMContents.class);
/*     */ 
/*  19 */   private ArrayList<AcewayMMContent> m_Contents = new ArrayList();
/*  20 */   static int m_Index = 0;
/*     */ 
/*     */   public Object clone() throws CloneNotSupportedException
/*     */   {
/*  24 */     return super.clone();
/*     */   }
/*     */ 
/*     */   public void removepart(int i)
/*     */   {
/*  41 */     this.m_Contents.remove(i);
/*     */   }
/*     */ 
/*     */   public void addPart(AcewayMMContent part)
/*     */   {
/*  51 */     this.m_Contents.add(part);
/*     */   }
/*     */ 
/*     */   public void addPart(int index, AcewayMMContent part)
/*     */   {
/*  61 */     if (index > this.m_Contents.size())
/*  62 */       this.m_Contents.add(part);
/*  63 */     else if (index < 0)
/*  64 */       this.m_Contents.add(0, part);
/*     */     else
/*  66 */       this.m_Contents.add(index, part);
/*     */   }
/*     */ 
/*     */   public void decode(AcewayByteArray ba, AcewayMMContentType contentType)
/*     */     throws Exception
/*     */   {
/*  81 */     if (contentType.getBoundary() == null) {
/*  82 */       throw new AcewayMMContentsException("MMContents.decode() error: Unfound boundary in multipart message!");
/*  85 */     }
/*     */ String boundary = "--" + contentType.getBoundary();
/*  86 */     String end_boundary = boundary + "--";
/*     */ 
/*  88 */     boundary.length();
/*  89 */     end_boundary.length();
/*     */     String str_line0;
/*     */     do {
/*  96 */       byte[] ba_line0 = ba.readLine();
/*     */ 
/*  98 */       if (ba_line0 == null) {
/*  99 */         throw new AcewayMMContentsException("MMContents.decode() error: Unfound started boundary!");
/*     */       }
/* 101 */       str_line0 = new String(ba_line0).trim();
/*     */     }
/* 103 */     while (!boundary.equals(str_line0));
/*     */ 
/* 107 */     boolean end_flag = false;
/* 108 */     boolean in_part = true;
/*     */     while (true)
/*     */     {
/* 112 */       ByteArrayOutputStream bos = new ByteArrayOutputStream();
/*     */       do
/*     */       {
/* 118 */         byte[] ba_line = ba.readLine();
/*     */ 
/* 120 */         if (ba_line == null)
/*     */         {
/* 123 */           if (in_part)
/*     */           {
/* 125 */             bos.close();
/* 126 */             throw new AcewayMMContentsException("MMContents::decode() error: boundary skip body!");
/*     */           }
/* 128 */           end_flag = true;
/* 129 */           break;
/*     */         }
/*     */ 
/* 132 */         String str_line = new String(ba_line).trim();
/*     */ 
/* 135 */         if ((boundary.equals(str_line)) || (end_boundary.equals(str_line)))
/*     */         {
/* 138 */           byte[] tmp_buf = new byte[bos.toByteArray().length - 2];
/* 139 */           System.arraycopy(bos.toByteArray(), 0, tmp_buf, 0, tmp_buf.length);
/* 140 */           ByteArrayInputStream bis = new ByteArrayInputStream(tmp_buf);
/*     */ 
/* 143 */           AcewayMMContent part = new AcewayMMContent();
/* 144 */           part.decode(bis);
/*     */ 
/* 146 */           addPart(part);
/* 147 */           bis.close();
/*     */ 
/* 149 */           in_part = false;
/*     */ 
/* 152 */           if (end_boundary.equals(str_line)) {
/* 153 */             end_flag = true;
/* 154 */             break;
/*     */           }
/*     */ 
/* 158 */           bos.write(ba_line);
/*     */         }
/* 160 */         bos.close();
/* 161 */       }while (!end_flag);
/*     */     }
/*     */   }
/*     */ 
/*     */   public AcewayMMSInfo decode(AcewayByteArray ba)
/*     */     throws Exception
/*     */   {
/* 171 */     AcewayMMSInfo mmsInfo = new AcewayMMSInfo();
/*     */ 
/* 202 */     String boundary = "------_Part_1_15020296.1182758071171";
/* 203 */     String end_boundary = boundary + "--";
/*     */ 
/* 205 */     boundary.length();
/* 206 */     end_boundary.length();
/*     */     String str_line0;
/*     */     do
/*     */     {
/* 213 */       byte[] ba_line0 = ba.readLine();
/* 214 */       if (ba_line0 == null) {
/* 215 */         throw new AcewayMMContentsException("MMContents.decode() error: Unfound started boundary!");
/*     */       }
/* 217 */       str_line0 = new String(ba_line0).trim();
/* 218 */     }while (!boundary.equals(str_line0));
/*     */ 
/* 222 */     boolean end_flag = false;
/* 223 */     boolean in_part = true;
/*     */     do
/*     */     {
/* 227 */       ByteArrayOutputStream bos = new ByteArrayOutputStream();
/*     */       while (true)
/*     */       {
/* 232 */         byte[] ba_line = ba.readLine();
/*     */ 
/* 234 */         if (ba_line == null)
/*     */         {
/* 237 */           if (in_part)
/*     */           {
/* 239 */             bos.close();
/* 240 */             throw new AcewayMMContentsException("MMContents::decode() error: boundary skip body!");
/*     */           }
/* 242 */           end_flag = true;
/* 243 */           break;
/*     */         }
/*     */ 
/* 246 */         String str_line = new String(ba_line).trim();
/*     */ 
/* 249 */         if ((boundary.equals(str_line)) || (end_boundary.equals(str_line)))
/*     */         {
/* 252 */           byte[] tmp_buf = new byte[bos.toByteArray().length - 2];
/* 253 */           System.arraycopy(bos.toByteArray(), 0, tmp_buf, 0, tmp_buf.length);
/* 254 */           ByteArrayInputStream bis = new ByteArrayInputStream(tmp_buf);
/*     */ 
/* 257 */           AcewayMMContent part = new AcewayMMContent();
/* 258 */           part.decode(bis);
/*     */ 
/* 260 */           addPart(part);
/* 261 */           bis.close();
/* 262 */           in_part = false;
/*     */ 
/* 265 */           if (!end_boundary.equals(str_line)) break;
/* 266 */           end_flag = true;
/*     */ 
/* 268 */           break;
/*     */         }
/*     */ 
/* 272 */         bos.write(ba_line);
/*     */       }
/* 274 */       bos.close();
/* 275 */     }while (!end_flag);
/*     */ 
/* 279 */     return mmsInfo;
/*     */   }
/*     */ 
/*     */   public void decode(AcewayByteArray ba, AcewayMMContentType contentType, AcewayMMContentInfo mmContentInfo)
/*     */     throws Exception
/*     */   {
/* 293 */     if (contentType.getBoundary() == null) {
/* 294 */       throw new AcewayMMContentsException("MMContents.decode() error: boundary not found in content-type!");
/*     */     }
/*     */ 
/* 296 */     String boundary = "--" + contentType.getBoundary();
/* 297 */     String end_boundary = boundary + "--";
/*     */     String str_line0;
/*     */     do
/*     */     {
/* 306 */       byte[] ba_line0 = ba.readLine();
/* 307 */       if (ba_line0 == null) {
/* 308 */         throw new AcewayMMContentsException("MMContents.decode() error: boundary not found in message!");
/*     */       }
/* 310 */       str_line0 = new String(ba_line0).trim();
/* 311 */     }while (!boundary.equals(str_line0));
/*     */ 
/* 315 */     ArrayList partsInfo = mmContentInfo.getMMPartsInfo();
/*     */ 
/* 317 */     for (int i = 0; i < partsInfo.size(); i++)
/*     */     {
/* 319 */       AcewayMMPartInfo mmPartInfo = (AcewayMMPartInfo)partsInfo.get(i);
/* 320 */       byte[] partBuf = ba.read(mmPartInfo.getEnd() - mmPartInfo.getStart() + 1);
/* 321 */       ByteArrayInputStream bis = new ByteArrayInputStream(partBuf);
/*     */ 
/* 324 */       AcewayMMContent part = new AcewayMMContent();
/* 325 */       part.decode(bis);
/*     */ 
/* 327 */       addPart(part);
/* 328 */       bis.close();
/*     */ 
/* 331 */       ba.readLine();
/* 332 */       String str_line = new String(ba.readLine()).trim();
/* 333 */       if ((boundary.equals(str_line)) || (end_boundary.equals(str_line)))
/*     */       {
/*     */         continue;
/*     */       }
/* 337 */       throw new AcewayMMContentsException("MMContents::decode(): Content syntax error, no boundary found after content part!");
/*     */     }
/*     */   }
/*     */ 
/*     */   public void encode(ByteArrayOutputStream bos, AcewayMMContentType contentType)
/*     */     throws Exception
/*     */   {
/* 351 */     if (contentType == null)
/*     */     {
/* 353 */       logger.warn("In MMContents::encode(): pass null contenttype!");
/* 354 */       return;
/*     */     }
/* 356 */     String boundary_str = null;
/*     */ 
/* 358 */     if (contentType.isMultipart())
/*     */     {
/* 360 */       boundary_str = "--" + contentType.getBoundary();
/*     */ 
/* 363 */       if (boundary_str != null)
/*     */       {
/* 366 */         bos.write(boundary_str.getBytes());
/* 367 */         bos.write("\r\n".getBytes());
/*     */ 
/* 370 */         for (int i = 0; i < getNumParts(); i++)
/*     */         {
/* 372 */           AcewayMMContent part = getPart(i);
/*     */ 
/* 376 */           if (part != null)
/* 377 */             part.encode(bos);
/* 378 */           else if (logger.isInfoEnabled()) {
/* 379 */             logger.info("In MMContents::encode(): get null Part #!" + i);
/*     */           }
/*     */ 
/* 382 */           bos.write("\r\n".getBytes());
/* 383 */           bos.write(boundary_str.getBytes());
/*     */ 
/* 385 */           if (i == getNumParts() - 1)
/*     */           {
/* 387 */             bos.write("--".getBytes());
/*     */           }
/* 389 */           bos.write("\r\n".getBytes());
/*     */         }
/*     */       }
/*     */       else {
/* 393 */         throw new AcewayMMContentsException("No boundary string found in Content-Type!");
/*     */       }
/*     */     }
/*     */     else {
/* 397 */       AcewayMMContent part = getPart(0);
/*     */ 
/* 400 */       if (part != null)
/* 401 */         bos.write(part.getContent());
/*     */       else
/* 403 */         logger.warn("In MMContents::encode(): get content failed!");
/*     */     }
/*     */   }
/*     */ 
/*     */   public void encode(AcewayMMContentInfo mmContentInfo, AcewayMMContentType contentType)
/*     */     throws Exception
/*     */   {
/* 416 */     if (contentType == null)
/*     */     {
/* 418 */       logger.warn("In MMContents::encode(): pass null contenttype!");
/* 419 */       return;
/*     */     }
/*     */ 
/* 422 */     ByteArrayOutputStream bos = mmContentInfo.getBos();
/*     */ 
/* 424 */     String boundary_str = null;
/*     */ 
/* 427 */     if (contentType.isMultipart())
/*     */     {
/* 429 */       boundary_str = "--" + contentType.getBoundary();
/*     */ 
/* 432 */       if (boundary_str != null)
/*     */       {
/* 435 */         bos.write(boundary_str.getBytes());
/* 436 */         bos.write("\r\n".getBytes());
/*     */ 
/* 439 */         for (int i = 0; i < getNumParts(); i++)
/*     */         {
/* 441 */           AcewayMMContent part = getPart(i);
/*     */ 
/* 444 */           if (part != null)
/*     */           {
/* 446 */             int beginSize = bos.size();
/* 447 */             part.encode(bos);
/* 448 */             int endSize = bos.size();
/* 449 */             AcewayMMPartInfo mmPartInfo = new AcewayMMPartInfo(part.getContentType(), beginSize, endSize - 1);
/*     */ 
/* 452 */             mmContentInfo.addPartInfo(mmPartInfo);
/*     */           }
/* 454 */           else if (logger.isDebugEnabled()) {
/* 455 */             logger.debug("In MMContents::encode(): get null Part #!" + i);
/*     */           }
/*     */ 
/* 458 */           bos.write("\r\n".getBytes());
/* 459 */           bos.write(boundary_str.getBytes());
/*     */ 
/* 461 */           if (i == getNumParts() - 1) {
/* 462 */             bos.write("--".getBytes());
/*     */           }
/* 464 */           bos.write("\r\n".getBytes());
/*     */         }
/*     */       }
/*     */       else {
/* 468 */         throw new AcewayMMContentsException("No boundary string found in Content-Type!");
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 473 */       int beginSize = bos.size();
/* 474 */       AcewayMMContent part = getPart(0);
/*     */ 
/* 477 */       if (part != null)
/* 478 */         bos.write(part.getContent());
/*     */       else {
/* 480 */         logger.warn("In MMContents::encode(): get content failed!");
/*     */       }
/*     */ 
/* 483 */       int endSize = bos.size();
/* 484 */       AcewayMMPartInfo mmPartInfo = new AcewayMMPartInfo(contentType.toString(), beginSize, endSize - 1);
/*     */ 
/* 487 */       mmContentInfo.addPartInfo(mmPartInfo);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void encode(ByteArrayOutputStream baos, boolean addFlag, String addName)
/*     */     throws Exception
/*     */   {
/* 499 */     String boundary_str = "------_Part_1_15020296.1182758071171";
/* 500 */     baos.write(boundary_str.getBytes());
/* 501 */     baos.write("\r\n".getBytes());
/*     */ 
/* 503 */     for (int i = 0; i < getNumParts(); i++)
/*     */     {
/* 505 */       AcewayMMContent part = getPart(i);
/*     */ 
/* 508 */       if (part != null)
/*     */       {
/* 510 */         if (!addFlag)
/* 511 */           part.encode(baos, false);
/* 512 */         else if ((part.getContentType().contains(addName)) || (part.getContentType().contains("smil")))
/* 513 */           part.encode(baos);
/*     */         else {
/* 515 */           part.encode(baos, false);
/*     */         }
/*     */       }
/* 518 */       else if (logger.isInfoEnabled()) {
/* 519 */         logger.info("In MMContents::encode(): get null Part #!" + i);
/*     */       }
/*     */ 
/* 522 */       baos.write("\r\n".getBytes());
/* 523 */       baos.write(boundary_str.getBytes());
/*     */ 
/* 525 */       if (i == getNumParts() - 1)
/*     */       {
/* 527 */         baos.write("--".getBytes());
/*     */       }
/* 529 */       baos.write("\r\n".getBytes());
/*     */     }
/*     */   }
/*     */ 
/*     */   public int getNumParts()
/*     */   {
/* 541 */     return this.m_Contents.size();
/*     */   }
/*     */ 
/*     */   public AcewayMMContent getPart(int index)
/*     */   {
/* 552 */     return (AcewayMMContent)this.m_Contents.get(index);
/*     */   }
/*     */ 
/*     */   public String toInfoString()
/*     */     throws Exception
/*     */   {
/* 562 */     StringBuffer sb = new StringBuffer();
/* 563 */     sb.append("*** Number of Content :" + getNumParts() + "\n");
/*     */ 
/* 565 */     for (int i = 0; i < getNumParts(); i++)
/*     */     {
/* 567 */       sb.append("****** Content Part#" + (i + 1) + "\n");
/* 568 */       AcewayMMContent part = getPart(i);
/* 569 */       sb.append(part.toInfoString() + "\n");
/*     */     }
/* 571 */     if (sb.length() == 0) {
/* 572 */       return null;
/*     */     }
/* 574 */     return sb.toString();
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.mmcontents.AcewayMMContents
 * JD-Core Version:    0.6.0
 */