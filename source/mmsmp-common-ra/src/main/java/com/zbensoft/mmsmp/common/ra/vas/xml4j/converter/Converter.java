/*    */ package com.zbensoft.mmsmp.common.ra.vas.xml4j.converter;
/*    */ import java.lang.reflect.Method;

import com.zbensoft.mmsmp.common.ra.vas.xml4j.core.XmlException;
/*    */ 
/*    */ public class Converter
/*    */ {
/*    */   private String type;
/*    */   private String classname;
/*    */   private String parseMethod;
/*    */   private String printMethod;
/*    */   protected Object instance;
/*    */ 
/*    */   public Converter()
/*    */   {
/*    */   }
/*    */ 
/*    */   public Converter(String type, String className, String parseMethod, String printMethod)
/*    */   {
/* 20 */     setType(type);
/* 21 */     setClassname(className);
/* 22 */     setParseMethod(parseMethod);
/* 23 */     setPrintMethod(printMethod);
/*    */   }
/*    */ 
/*    */   public String getClassname() {
/* 27 */     return this.classname;
/*    */   }
/*    */ 
/*    */   public void setClassname(String classname) {
/* 31 */     this.classname = classname;
/*    */   }
/*    */ 
/*    */   public String getParseMethod() {
/* 35 */     return this.parseMethod;
/*    */   }
/*    */ 
/*    */   public void setParseMethod(String parseMethod) {
/* 39 */     this.parseMethod = parseMethod;
/*    */   }
/*    */ 
/*    */   public String getPrintMethod() {
/* 43 */     return this.printMethod;
/*    */   }
/*    */ 
/*    */   public void setPrintMethod(String printMethod) {
/* 47 */     this.printMethod = printMethod;
/*    */   }
/*    */ 
/*    */   public String getType() {
/* 51 */     return this.type;
/*    */   }
/*    */ 
/*    */   public void setType(String type) {
/* 55 */     this.type = type;
/*    */   }
/*    */ 
/*    */   public String print(Object value) throws XmlException {
/* 59 */     Object result = new String("");
/* 60 */     if (this.instance == null) {
/*    */       try {
/* 62 */         this.instance = Class.forName(this.classname).newInstance();
/*    */       } catch (Exception e) {
/* 64 */         return "";
/*    */       }
/*    */     }
/* 67 */     String methodName = this.printMethod;
/* 68 */     Class[] param = { value.getClass() };
/* 69 */     Object[] values = { value };
/*    */     try {
/* 71 */       Method method = this.instance.getClass().getMethod(methodName, param);
/* 72 */       if (method != null)
/* 73 */         result = method.invoke(this.instance, values);
/*    */     } catch (Exception e) {
/* 75 */       throw new XmlException("Cannot print value for type " + 
/* 76 */         value.getClass().getName() + ", " + e);
/*    */     }
/* 78 */     if (result == null)
/* 79 */       return "";
/* 80 */     return result.toString();
/*    */   }
/*    */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\xml4j.jar
 * Qualified Name:     com.aceway.vas.xml4j.converter.Converter
 * JD-Core Version:    0.6.0
 */