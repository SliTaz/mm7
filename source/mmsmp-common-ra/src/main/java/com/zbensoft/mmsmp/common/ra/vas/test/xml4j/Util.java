/*    */ package com.zbensoft.mmsmp.common.ra.vas.test.xml4j;
/*    */ 
/*    */ import java.lang.reflect.Field;
/*    */ import java.lang.reflect.InvocationTargetException;
/*    */ import java.lang.reflect.Method;
/*    */ import java.lang.reflect.Modifier;
/*    */ 
/*    */ public class Util
/*    */ {
/*    */   public static String logObj(Object obj)
/*    */   {
/* 10 */     if (obj == null) {
/* 11 */       return "";
/*    */     }
/* 13 */     Class cls = obj.getClass();
/* 14 */     Field[] fields = cls.getDeclaredFields();
/* 15 */     Class[] c = (Class[])null;
/* 16 */     Object[] o = (Object[])null;
/*    */ 
/* 18 */     StringBuffer logMsg = new StringBuffer()
/* 19 */       .append("【Field Name and Value】: \n");
/* 20 */     for (Field field : fields) {
/* 21 */       String name = field.getName();
/*    */ 
/* 23 */       String nameMaxCase = null;
/* 24 */       if (name.indexOf("serialVersionUID") != -1) {
/*    */         continue;
/*    */       }
/* 27 */       if (name.startsWith("_"))
/*    */       {
/*    */         continue;
/*    */       }
/* 31 */       if ((name.substring(0, 2).equals("is")) && (field.getGenericType().toString().equals("boolean")))
/* 32 */         nameMaxCase = name;
/*    */       else {
/* 34 */         nameMaxCase = (char)(name.charAt(0) - ' ') + name.substring(1);
/*    */       }
/* 36 */       int modifier = field.getModifiers();
/* 37 */       String fieldModifier = Modifier.toString(modifier);
/* 38 */       if ((fieldModifier.startsWith("private static")) || (name.startsWith("this")))
/*    */         continue;
/*    */       try
/*    */       {
/* 42 */         Method methodMiddle = null;
/* 43 */         if ((name.substring(0, 2).equals("is")) && (field.getGenericType().toString().equals("boolean")))
/* 44 */           methodMiddle = cls.getMethod(nameMaxCase, c);
/* 45 */         else if ((field.getGenericType().toString().equals("boolean")) && 
/* 46 */           (!name.substring(0, 2).equals("is")))
/* 47 */           methodMiddle = cls.getMethod("is" + nameMaxCase, c);
/*    */         else {
/* 49 */           methodMiddle = cls.getMethod("get" + nameMaxCase, c);
/*    */         }
/* 51 */         Object object = methodMiddle.invoke(obj, o);
/* 52 */         if (object == null) {
/* 53 */           object = "warn: value is null,请检查参数是否为必选";
/*    */         }
/* 55 */         logMsg.append(name + " = {" + object + "}\n");
/*    */       }
/*    */       catch (SecurityException e) {
/* 58 */         e.printStackTrace();
/*    */       } catch (NoSuchMethodException localNoSuchMethodException) {
/*    */       }
/*    */       catch (IllegalArgumentException e) {
/* 62 */         e.printStackTrace();
/*    */       } catch (IllegalAccessException e) {
/* 64 */         e.printStackTrace();
/*    */       } catch (InvocationTargetException e) {
/* 66 */         e.printStackTrace();
/*    */       }
/*    */     }
/* 69 */     return logMsg.toString();
/*    */   }
/*    */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\xml4j.jar
 * Qualified Name:     com.aceway.vas.test.xml4j.Util
 * JD-Core Version:    0.6.0
 */