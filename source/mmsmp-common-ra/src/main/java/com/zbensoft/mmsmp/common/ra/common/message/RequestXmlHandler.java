/*    */ package com.zbensoft.mmsmp.common.ra.common.message;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ import java.lang.reflect.Field;
/*    */ import java.util.regex.Matcher;
/*    */ import java.util.regex.Pattern;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class RequestXmlHandler
/*    */ {
/* 10 */   private static final Logger logger = Logger.getLogger(RequestXmlHandler.class);
/*    */   private String xml_string;
/*    */ 
/*    */   public String getNodeValue(String str, String start_pattern, String end_pattern)
/*    */   {
/* 14 */     if ((start_pattern == null) || (end_pattern == null)) throw new IllegalArgumentException("node_pattern cannot be null");
/* 15 */     Matcher matcher_start = Pattern.compile("<([a-zA-Z0-9_]*:)?" + start_pattern + "( [^>]*)?>", 2).matcher(str);
/* 16 */     if (matcher_start.find()) {
/* 17 */       int start = matcher_start.start() + matcher_start.group().length();
/* 18 */       logger.debug("getNodeValue(String, String) - string matcher_start.group()=" + matcher_start.group());
/* 19 */       Matcher matcher_end = Pattern.compile("</([a-zA-Z0-9_]*:)?" + end_pattern + ">", 2).matcher(str.substring(start));
/* 20 */       if (matcher_end.find()) {
/* 21 */         int end = matcher_end.start();
/* 22 */         logger.debug("getNodeValue(String, String) - string matcher_end.group()=" + matcher_end.group());
/* 23 */         return str.substring(start, start + end);
/*    */       }
/*    */ 
/* 26 */       return getOtherValue(str, end_pattern);
/*    */     }
/*    */ 
/* 30 */     return "";
/*    */   }
/*    */ 
/*    */   private String getOtherValue(String string, String end_pattern)
/*    */   {
/* 41 */     Matcher matcher_href = Pattern.compile("<([a-zA-Z0-9_]*:)?" + end_pattern + " [^>]*/>", 2).matcher(string);
/* 42 */     if (matcher_href.find()) {
/* 43 */       int _start = matcher_href.start();
/* 44 */       int end = matcher_href.start() + matcher_href.group().length();
/* 45 */       String sub_str = string.substring(_start, end);
/* 46 */       System.out.println("sub_str=" + sub_str);
/*    */ 
/* 48 */       Matcher matcher_str = Pattern.compile(" href=\"#", 2).matcher(sub_str);
/* 49 */       if (matcher_str.find()) {
/* 50 */         int start_ = matcher_str.start() + 8;
/* 51 */         String idx = sub_str.substring(start_, start_ + sub_str.substring(start_).indexOf("\""));
/* 52 */         return getNodeValue(this.xml_string, "multiRef id=\"" + idx + "\"", "multiRef");
/*    */       }
/* 54 */       return null;
/*    */     }
/*    */ 
/* 57 */     throw new IllegalStateException("No match found by pattern_string end/" + end_pattern + "/");
/*    */   }
/*    */ 
/*    */   public void initlizeClass(String bottom_scope, Object o)
/*    */     throws Exception
/*    */   {
/* 68 */     if (o == null) throw new IllegalArgumentException("object cannnot be null");
/* 69 */     String str = getNodeValue(this.xml_string, bottom_scope, bottom_scope);
/* 70 */     Matcher matcher = Pattern.compile("<([a-zA-Z0-9_]*:)?[a-zA-Z0-9_]+ ", 2).matcher(str);
/*    */ 
/* 72 */     while (matcher.find()) {
/* 73 */       int start = matcher.start();
/* 74 */       int end = matcher.end();
/* 75 */       String key = str.substring(start + 1, end - 1);
/* 76 */       System.out.println(key);
/* 77 */       String value = getNodeValue(str, key, key);
/* 78 */       logger.debug("initlizeClass(String, String, Object) - String key:value=" + key + ":" + value);
/* 79 */       o.getClass().getField(key).set(o, value);
/*    */     }
/*    */   }
/*    */ 
/*    */   public String getXml_string() {
/* 84 */     return this.xml_string;
/*    */   }
/*    */ 
/*    */   public void setXml_string(String xml_string) {
/* 88 */     this.xml_string = xml_string;
/*    */   }
/*    */ 
/*    */   public static void main(String[] args)
/*    */   {
/*    */   }
/*    */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.message.RequestXmlHandler
 * JD-Core Version:    0.6.0
 */