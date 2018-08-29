/*    */ package com.zbensoft.mmsmp.common.ra.mail.util;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ 
/*    */ public class ConverFromGBKToUTF8
/*    */ {
/*    */   public static void main(String[] args)
/*    */   {
/*    */     try
/*    */     {
/*  8 */       ConverFromGBKToUTF8 convert = new ConverFromGBKToUTF8();
/*  9 */       byte[] fullByte = convert.gbk2utf8("彩信测试");
/* 10 */       String fullStr = new String(fullByte, "UTF-8");
/* 11 */       System.out.println("string from GBK to UTF-8 byte:  " + fullStr);
/*    */     }
/*    */     catch (Exception e) {
/* 14 */       e.printStackTrace();
/*    */     }
/*    */   }
/*    */ 
/*    */   public byte[] gbk2utf8(String chenese) {
/* 19 */     char[] c = chenese.toCharArray();
/* 20 */     byte[] fullByte = new byte[3 * c.length];
/* 21 */     for (int i = 0; i < c.length; i++) {
/* 22 */       int m = c[i];
/* 23 */       String word = Integer.toBinaryString(m);
/*    */ 
/* 26 */       StringBuffer sb = new StringBuffer();
/* 27 */       int len = 16 - word.length();
/*    */ 
/* 29 */       for (int j = 0; j < len; j++) {
/* 30 */         sb.append("0");
/*    */       }
/* 32 */       sb.append(word);
/* 33 */       sb.insert(0, "1110");
/* 34 */       sb.insert(8, "10");
/* 35 */       sb.insert(16, "10");
/*    */ 
/* 39 */       String s1 = sb.substring(0, 8);
/* 40 */       String s2 = sb.substring(8, 16);
/* 41 */       String s3 = sb.substring(16);
/*    */ 
/* 43 */       byte b0 = Integer.valueOf(s1, 2).byteValue();
/* 44 */       byte b1 = Integer.valueOf(s2, 2).byteValue();
/* 45 */       byte b2 = Integer.valueOf(s3, 2).byteValue();
/* 46 */       byte[] bf = new byte[3];
/* 47 */       bf[0] = b0;
/* 48 */       fullByte[(i * 3)] = bf[0];
/* 49 */       bf[1] = b1;
/* 50 */       fullByte[(i * 3 + 1)] = bf[1];
/* 51 */       bf[2] = b2;
/* 52 */       fullByte[(i * 3 + 2)] = bf[2];
/*    */     }
/*    */ 
/* 55 */     return fullByte;
/*    */   }
/*    */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.mail.util.ConverFromGBKToUTF8
 * JD-Core Version:    0.6.0
 */