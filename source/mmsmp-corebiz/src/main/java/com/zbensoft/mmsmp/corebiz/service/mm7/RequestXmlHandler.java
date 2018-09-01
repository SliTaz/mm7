 package com.zbensoft.mmsmp.corebiz.service.mm7;
 
 import java.io.PrintStream;
 import java.util.regex.Matcher;
 import java.util.regex.Pattern;
 import org.apache.log4j.Logger;
 
 public class RequestXmlHandler
 {
   private static final Logger logger = Logger.getLogger(RequestXmlHandler.class);
   private String xml_string;
   
   public String getNodeValue(String str, String start_pattern, String end_pattern) {
     if ((start_pattern == null) || (end_pattern == null)) throw new IllegalArgumentException("node_pattern cannot be null");
     Matcher matcher_start = Pattern.compile("<([a-zA-Z0-9_]*:)?" + start_pattern + "( [^>]*)?>", 2).matcher(str);
     if (matcher_start.find()) {
       int start = matcher_start.start() + matcher_start.group().length();
       logger.debug("getNodeValue(String, String) - string matcher_start.group()=" + matcher_start.group());
       Matcher matcher_end = Pattern.compile("</([a-zA-Z0-9_]*:)?" + end_pattern + ">", 2).matcher(str.substring(start));
       if (matcher_end.find()) {
         int end = matcher_end.start();
         logger.debug("getNodeValue(String, String) - string matcher_end.group()=" + matcher_end.group());
         return str.substring(start, start + end);
       }
       
       return getOtherValue(str, end_pattern);
     }
     
 
     return "";
   }
   
 
 
 
 
 
 
   private String getOtherValue(String string, String end_pattern)
   {
	   Matcher matcher_href = Pattern.compile("<([a-zA-Z0-9_]*:)?" + end_pattern + " [^>]*/>", 2).matcher(string);
     if (matcher_href.find()) {
       int _start = matcher_href.start();
       int end = matcher_href.start() + matcher_href.group().length();
       String sub_str = string.substring(_start, end);
       System.out.println("sub_str=" + sub_str);
       
       Matcher matcher_str = Pattern.compile(" href=\"#", 2).matcher(sub_str);
       if (matcher_str.find()) {
         int start_ = matcher_str.start() + 8;
         String idx = sub_str.substring(start_, start_ + sub_str.substring(start_).indexOf("\""));
         return getNodeValue(this.xml_string, "multiRef id=\"" + idx + "\"", "multiRef");
       }
       return null;
     }
     
     throw new IllegalStateException("No match found by pattern_string end/" + end_pattern + "/");
   }
   
 
 
 
 
 
   public void initlizeClass(String bottom_scope, Object o)
     throws Exception
   {
     if (o == null) throw new IllegalArgumentException("object cannnot be null");
     String str = getNodeValue(this.xml_string, bottom_scope, bottom_scope);
     Matcher matcher = Pattern.compile("<([a-zA-Z0-9_]*:)?[a-zA-Z0-9_]+ ", 2).matcher(str);
     
     while (matcher.find()) {
       int start = matcher.start();
       int end = matcher.end();
       String key = str.substring(start + 1, end - 1);
       System.out.println(key);
       String value = getNodeValue(str, key, key);
       logger.debug("initlizeClass(String, String, Object) - String key:value=" + key + ":" + value);
       o.getClass().getField(key).set(o, value);
     }
   }
   
   public String getXml_string() {
     return this.xml_string;
   }
   
   public void setXml_string(String xml_string) {
     this.xml_string = xml_string;
   }
   
   public static void main(String[] args) {}
 }





