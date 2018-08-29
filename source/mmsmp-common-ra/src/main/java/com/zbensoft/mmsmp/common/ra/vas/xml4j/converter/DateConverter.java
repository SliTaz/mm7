/*    */ package com.zbensoft.mmsmp.common.ra.vas.xml4j.converter;
/*    */ 
/*    */ import java.text.DateFormat;
/*    */ import java.text.SimpleDateFormat;
/*    */ import java.util.Date;
/*    */ import java.util.TimeZone;
/*    */ import java.util.logging.Logger;
/*    */ 
/*    */ public class DateConverter extends Converter
/*    */ {
/*    */   public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
/* 13 */   private String format = "yyyy-MM-dd HH:mm:ss";
/* 14 */   private Logger logger = Logger.getLogger(getClass().getName());
/*    */ 
/*    */   private void init() {
/* 17 */     setType(Date.class.getName());
/* 18 */     setClassname(getClass().getName());
/* 19 */     setParseMethod("parseDate");
/* 20 */     setPrintMethod("printDate");
/* 21 */     this.instance = this;
/*    */   }
/*    */ 
/*    */   public DateConverter() {
/* 25 */     init();
/*    */   }
/*    */ 
/*    */   public DateConverter(String format) {
/* 29 */     init();
/* 30 */     this.format = format;
/*    */   }
/*    */ 
/*    */   public Date parseDate(String date) {
/* 34 */     if (date == null)
/* 35 */       date = "";
/* 36 */     if (this.format == null)
/* 37 */       this.format = "";
/* 38 */     Date theDate = null;
/* 39 */     SimpleDateFormat df = new SimpleDateFormat(this.format);
/*    */     try {
/* 41 */       theDate = df.parse(date);
/* 42 */       this.logger.finer("Date parsed using format '" + this.format + "'");
/*    */     } catch (Exception e) {
/* 44 */       this.logger.info("Cannot parse date " + e.toString());
/* 45 */       theDate = null;
/*    */     }
/* 47 */     return theDate;
/*    */   }
/*    */ 
/*    */   public String printDate(Date date) {
/* 51 */     if (date == null)
/* 52 */       return null;
/* 53 */     DateFormat df = new SimpleDateFormat(this.format);
/* 54 */     df.setTimeZone(TimeZone.getDefault());
/* 55 */     this.logger.finer("Pringing date using format '" + this.format + "'");
/* 56 */     return df.format(date);
/*    */   }
/*    */ 
/*    */   public String getFormat() {
/* 60 */     return this.format;
/*    */   }
/*    */ 
/*    */   public void setFormat(String format) {
/* 64 */     this.format = format;
/*    */   }
/*    */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\xml4j.jar
 * Qualified Name:     com.aceway.vas.xml4j.converter.DateConverter
 * JD-Core Version:    0.6.0
 */