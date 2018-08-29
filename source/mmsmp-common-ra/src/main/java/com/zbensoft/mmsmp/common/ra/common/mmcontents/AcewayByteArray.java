/*     */ package com.zbensoft.mmsmp.common.ra.common.mmcontents;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ 
/*     */ public class AcewayByteArray
/*     */ {
/*  19 */   private byte[] m_ba = null;
/*     */   private int m_size;
/*     */   private int m_iLast;
/*     */   private int m_iCur;
/*     */ 
/*     */   public static boolean allAscii(String text)
/*     */   {
/*  26 */     if (text == null)
/*  27 */       return true;
/*  28 */     int l = text.length();
/*     */ 
/*  30 */     for (int i = 0; i < l; i++)
/*     */     {
/*  32 */       int ch = text.charAt(i);
/*  33 */       if ((ch >= 127) || ((ch < 32) && (ch != 13) && (ch != 10) && (ch != 9)))
/*  34 */         return false;
/*     */     }
/*  36 */     return true;
/*     */   }
/*     */ 
/*     */   public static boolean notAscii(byte b)
/*     */   {
/*  41 */     return (b < 32) && (b != 13) && (b != 10) && (b != 9);
/*     */   }
/*     */ 
/*     */   public static boolean hasNoneAsciiByte(byte[] content) {
/*  45 */     if (content == null) {
/*  46 */       return false;
/*     */     }
/*  48 */     return hasNoneAsciiByte(content, content.length);
/*     */   }
/*     */ 
/*     */   public static boolean hasNoneAsciiByte(byte[] content, int len)
/*     */   {
/*  53 */     if (content == null)
/*  54 */       return false;
/*  55 */     if (len > content.length)
/*  56 */       len = content.length;
/*  57 */     for (int i = 0; i < len; i++)
/*     */     {
/*  59 */       if ((content[i] < 32) && (content[i] != 13) && (content[i] != 10) && (content[i] != 9))
/*  60 */         return true;
/*     */     }
/*  62 */     return false;
/*     */   }
/*     */ 
/*     */   public AcewayByteArray(byte[] ba)
/*     */   {
/*  71 */     this.m_ba = ba;
/*  72 */     this.m_iLast = 0;
/*  73 */     this.m_iCur = 0;
/*  74 */     this.m_size = this.m_ba.length;
/*     */   }
/*     */ 
/*     */   public AcewayByteArray(ByteArrayInputStream bis) throws IOException {
/*     */     try {
/*  79 */       this.m_ba = new byte[bis.available()];
/*  80 */       bis.read(this.m_ba);
/*     */     } catch (IOException ioe) {
/*  82 */       throw ioe;
/*     */     }
/*  84 */     this.m_iLast = 0;
/*  85 */     this.m_iCur = 0;
/*  86 */     this.m_size = this.m_ba.length;
/*     */   }
/*     */ 
/*     */   public int getSize() {
/*  90 */     return this.m_size;
/*     */   }
/*     */ 
/*     */   public byte[] getByteArray() {
/*  94 */     return this.m_ba;
/*     */   }
/*     */ 
/*     */   public byte[] readLine()
/*     */   {
/* 132 */     if (this.m_iCur >= this.m_size) {
/* 133 */       return null;
/*     */     }
/*     */     do
/*     */     {
/* 137 */       if (this.m_ba[(this.m_iCur++)] != 10)
/*     */         continue;
/* 139 */       byte[] line = new byte[this.m_iCur - this.m_iLast];
/* 140 */       System.arraycopy(this.m_ba, this.m_iLast, line, 0, this.m_iCur - this.m_iLast);
/* 141 */       this.m_iLast = this.m_iCur;
/* 142 */       return line;
/*     */     }
/* 135 */     while (this.m_iCur < this.m_size);
/*     */ 
/* 146 */     if (this.m_iCur - this.m_iLast <= 0) {
/* 147 */       return null;
/*     */     }
/* 149 */     byte[] line = new byte[this.m_iCur - this.m_iLast];
/* 150 */     System.arraycopy(this.m_ba, this.m_iLast, line, 0, this.m_iCur - this.m_iLast);
/* 151 */     return line;
/*     */   }
/*     */ 
/*     */   public byte[] readLeft() {
/* 155 */     byte[] left = new byte[this.m_size - this.m_iCur];
/* 156 */     System.arraycopy(this.m_ba, this.m_iCur, left, 0, this.m_size - this.m_iCur);
/* 157 */     return left;
/*     */   }
/*     */ 
/*     */   public void locate(int index)
/*     */   {
/* 162 */     if (this.m_size < index)
/* 163 */       return;
/* 164 */     this.m_iCur = index;
/* 165 */     this.m_iLast = this.m_iCur;
/*     */   }
/*     */ 
/*     */   public byte[] read(int len)
/*     */   {
/* 170 */     if (this.m_size - this.m_iCur < len)
/* 171 */       return null;
/* 172 */     byte[] retba = new byte[len];
/* 173 */     System.arraycopy(this.m_ba, this.m_iCur, retba, 0, len);
/* 174 */     this.m_iCur += len;
/* 175 */     this.m_iLast = this.m_iCur;
/* 176 */     return retba;
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.mmcontents.AcewayByteArray
 * JD-Core Version:    0.6.0
 */