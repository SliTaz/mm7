/*     */ package com.zbensoft.mmsmp.common.ra.vas.commons.tcp.impl;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ 
/*     */ public class TypeConvert
/*     */ {
/*     */   public static int byte2int(byte[] b, int offset)
/*     */   {
/*   9 */     return b[(offset + 3)] & 0xFF | (b[(offset + 2)] & 0xFF) << 8 | 
/*  10 */       (b[(offset + 1)] & 0xFF) << 16 | (b[offset] & 0xFF) << 24;
/*     */   }
/*     */ 
/*     */   public static int byte2int(byte[] b) {
/*  14 */     int len = b.length;
/*  15 */     byte[] bb = (byte[])null;
/*     */ 
/*  17 */     if (len < 4) {
/*  18 */       bb = new byte[4];
/*  19 */       System.arraycopy(b, 0, bb, 4 - len, len);
/*  20 */       b = bb;
/*     */     }
/*     */ 
/*  23 */     return b[3] & 0xFF | (b[2] & 0xFF) << 8 | (b[1] & 0xFF) << 16 | 
/*  24 */       (b[0] & 0xFF) << 24;
/*     */   }
/*     */ 
/*     */   public static long byte2long(byte[] b) {
/*  28 */     int len = b.length;
/*  29 */     byte[] bb = (byte[])null;
/*     */ 
/*  31 */     if (len < 8) {
/*  32 */       bb = new byte[8];
/*  33 */       System.arraycopy(b, 0, bb, 8 - len, len);
/*  34 */       b = bb;
/*     */     }
/*     */ 
/*  37 */     return b[7] & 0xFF | (b[6] & 0xFF) << 8 | 
/*  38 */       (b[5] & 0xFF) << 16 | 
/*  39 */       (b[4] & 0xFF) << 24 | 
/*  40 */       (b[3] & 0xFF) << 32 | 
/*  41 */       (b[2] & 0xFF) << 40 | 
/*  42 */       (b[1] & 0xFF) << 48 | b[0] << 56;
/*     */   }
/*     */ 
/*     */   public static long byte2long(byte[] b, int offset) {
/*  46 */     return b[(offset + 7)] & 0xFF | 
/*  47 */       (b[(offset + 6)] & 0xFF) << 8 | 
/*  48 */       (b[(offset + 5)] & 0xFF) << 16 | 
/*  49 */       (b[(offset + 4)] & 0xFF) << 24 | 
/*  50 */       (b[(offset + 3)] & 0xFF) << 32 | 
/*  51 */       (b[(offset + 2)] & 0xFF) << 40 | 
/*  52 */       (b[(offset + 1)] & 0xFF) << 48 | 
/*  53 */       b[offset] << 56;
/*     */   }
/*     */ 
/*     */   public static byte[] int2byte(int n) {
/*  57 */     byte[] b = new byte[4];
/*  58 */     b[0] = (byte)(n >> 24);
/*  59 */     b[1] = (byte)(n >> 16);
/*  60 */     b[2] = (byte)(n >> 8);
/*  61 */     b[3] = (byte)n;
/*  62 */     return b;
/*     */   }
/*     */ 
/*     */   public static void int2byte(int n, byte[] buf, int offset) {
/*  66 */     buf[offset] = (byte)(n >> 24);
/*  67 */     buf[(offset + 1)] = (byte)(n >> 16);
/*  68 */     buf[(offset + 2)] = (byte)(n >> 8);
/*  69 */     buf[(offset + 3)] = (byte)n;
/*     */   }
/*     */ 
/*     */   public static byte[] short2byte(int n) {
/*  73 */     byte[] b = new byte[2];
/*  74 */     b[0] = (byte)(n >> 8);
/*  75 */     b[1] = (byte)n;
/*  76 */     return b;
/*     */   }
/*     */ 
/*     */   public static void short2byte(int n, byte[] buf, int offset) {
/*  80 */     buf[offset] = (byte)(n >> 8);
/*  81 */     buf[(offset + 1)] = (byte)n;
/*     */   }
/*     */ 
/*     */   public static byte[] long2byte(long n) {
/*  85 */     byte[] b = new byte[8];
/*  86 */     b[0] = (byte)(int)(n >> 56);
/*  87 */     b[1] = (byte)(int)(n >> 48);
/*  88 */     b[2] = (byte)(int)(n >> 40);
/*  89 */     b[3] = (byte)(int)(n >> 32);
/*  90 */     b[4] = (byte)(int)(n >> 24);
/*  91 */     b[5] = (byte)(int)(n >> 16);
/*  92 */     b[6] = (byte)(int)(n >> 8);
/*  93 */     b[7] = (byte)(int)n;
/*  94 */     return b;
/*     */   }
/*     */ 
/*     */   public static void long2byte(long n, byte[] buf, int offset) {
/*  98 */     buf[offset] = (byte)(int)(n >> 56);
/*  99 */     buf[(offset + 1)] = (byte)(int)(n >> 48);
/* 100 */     buf[(offset + 2)] = (byte)(int)(n >> 40);
/* 101 */     buf[(offset + 3)] = (byte)(int)(n >> 32);
/* 102 */     buf[(offset + 4)] = (byte)(int)(n >> 24);
/* 103 */     buf[(offset + 5)] = (byte)(int)(n >> 16);
/* 104 */     buf[(offset + 6)] = (byte)(int)(n >> 8);
/* 105 */     buf[(offset + 7)] = (byte)(int)n;
/*     */   }
/*     */ 
/*     */   public static byte[] toBCD(int num) {
/* 109 */     byte[] data = new byte[10];
/*     */ 
/* 111 */     int next = num;
/* 112 */     int count = 0;
/* 113 */     for (int i = 0; next != 0; i++) {
/* 114 */       int index = i / 2;
/* 115 */       byte yu = (byte)(next % 10);
/*     */ 
/* 117 */       if (i % 2 == 0) {
/* 118 */         data[index] = yu;
/*     */       } else {
/* 120 */         int tmp = yu << 4;
/* 121 */         tmp = data[index] + tmp;
/* 122 */         data[index] = (byte)tmp;
/*     */       }
/* 124 */       count++;
/* 125 */       next /= 10;
/*     */     }
/*     */ 
/* 128 */     int size = (count % 2 == 0) && (count != 0) ? count / 2 : count / 2 + 1;
/* 129 */     byte[] result = new byte[size];
/*     */ 
/* 131 */     System.arraycopy(data, 0, result, 0, size);
/*     */ 
/* 133 */     return result;
/*     */   }
/*     */ 
/*     */   public static int BCDtoINT(byte[] num) {
/* 137 */     if (num == null) {
/* 138 */       return 0;
/*     */     }
/* 140 */     int len = num.length;
/* 141 */     byte[] bcd = new byte[len];
/* 142 */     System.arraycopy(num, 0, bcd, 0, len);
/*     */ 
/* 144 */     int result = 0;
/* 145 */     int exp = 1;
/*     */ 
/* 147 */     for (int i = 0; i < len; i++) {
/* 148 */       int low = bcd[i] & 0xF;
/* 149 */       result += low * exp;
/*     */ 
/* 151 */       exp *= 10;
/*     */ 
/* 155 */       int hi = (bcd[i] & 0xF0) >>> 4;
/*     */ 
/* 157 */       result += hi * exp;
/* 158 */       exp *= 10;
/*     */     }
/*     */ 
/* 161 */     return result;
/*     */   }
/*     */ 
/*     */   public static void main(String[] arg)
/*     */   {
/* 166 */     for (int i = 0; i < 123456789; i++) {
/* 167 */       byte[] test = toBCD(i);
/* 168 */       int result = BCDtoINT(test);
/* 169 */       System.out.println(result);
/*     */       try {
/* 171 */         Thread.sleep(10L);
/*     */       }
/*     */       catch (Exception localException)
/*     */       {
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\aceway-nio.jar
 * Qualified Name:     com.aceway.vas.commons.tcp.impl.TypeConvert
 * JD-Core Version:    0.6.0
 */