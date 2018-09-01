/*    */ package com.zbensoft.mmsmp.common.ra.vas.commons.xml4j.core;
/*    */ 
/*    */

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/*    */

/*    */
/*    */ public class ObjectFactory
/*    */ {
/*    */   public Object getInstance(String implementationName)
/*    */   {
/* 13 */     if ((implementationName == null) || ("".equals(implementationName))) {
/* 14 */       return null;
/*    */     }
/* 16 */     Object instance = null;
/* 17 */     boolean convertClassname = true;
/*    */     try {
/* 19 */       Class.forName(implementationName);
/* 20 */       convertClassname = false;
/*    */     }
/*    */     catch (ClassNotFoundException localClassNotFoundException1)
/*    */     {
/*    */     }
/*    */     catch (NoClassDefFoundError localNoClassDefFoundError1) {
/*    */     }
/* 27 */     if (convertClassname) {
/* 28 */       String pkg = "";
/* 29 */       int index = implementationName.lastIndexOf(".");
/* 30 */       if (index > 0) {
/* 31 */         pkg = implementationName.substring(0, index);
/*    */       }
/* 33 */       String className = implementationName.substring(index + 1);
/* 34 */       if (Character.isLowerCase(className.charAt(0))) {
/* 35 */         StringBuffer filename = new StringBuffer();
/* 36 */         filename.append(pkg);
/* 37 */         if (!"".equals(pkg))
/* 38 */           filename.append(".");
/* 39 */         filename.append(Character.toUpperCase(className.charAt(0)));
/* 40 */         if (className.length() > 1)
/* 41 */           filename.append(className.substring(1));
/* 42 */         return getInstance(filename.toString());
/*    */       }
/*    */     }
/*    */ 
/* 46 */     Class[] paramTypes = (Class[])null;
/* 47 */     Object[] parameters = (Object[])null;
/*    */     try {
/* 49 */       Method theMethod = Class.forName(implementationName).getMethod(
/* 50 */         "getInstance", paramTypes);
/* 51 */       instance = theMethod.invoke(
/* 52 */         Class.forName(implementationName).getClass(), parameters);
/*    */     } catch (NoSuchMethodException localNoSuchMethodException) {
/*    */     }
/*    */     catch (IllegalAccessException localIllegalAccessException) {
/*    */     }
/*    */     catch (InvocationTargetException localInvocationTargetException) {
/*    */     }
/*    */     catch (ClassNotFoundException cnfe) {
/* 60 */       return null;
/*    */     }
/*    */ 
/* 63 */     if (instance != null)
/* 64 */       return instance;
/*    */     try
/*    */     {
/* 67 */       instance = Class.forName(implementationName).newInstance();
/*    */     } catch (IllegalAccessException localIllegalAccessException1) {
/*    */     }
/*    */     catch (InstantiationException localInstantiationException) {
/*    */     }
/*    */     catch (ClassNotFoundException localClassNotFoundException2) {
/*    */     }
/*    */     catch (NoClassDefFoundError ncdf) {
/* 75 */       if (Character.isLowerCase(implementationName.charAt(0))) {
/* 76 */         StringBuffer filename = new StringBuffer(
/* 77 */           Character.toUpperCase(implementationName.charAt(0)));
/* 78 */         if (implementationName.length() > 1)
/* 79 */           filename.append(implementationName.substring(1));
/* 80 */         return getInstance(filename.toString());
/*    */       }
/*    */     }
/* 83 */     return instance;
/*    */   }
/*    */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\xml4j.jar
 * Qualified Name:     com.aceway.vas.xml4j.core.ObjectFactory
 * JD-Core Version:    0.6.0
 */