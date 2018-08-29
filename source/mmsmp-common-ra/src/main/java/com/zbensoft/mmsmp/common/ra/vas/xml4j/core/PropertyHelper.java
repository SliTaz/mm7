/*     */ package com.zbensoft.mmsmp.common.ra.vas.xml4j.core;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;

import com.zbensoft.mmsmp.common.ra.vas.xml4j.converter.Converter;
/*     */ 
/*     */ public class PropertyHelper
/*     */ {
/*  10 */   private XmlConfig config = XmlConfig.getInstance();
/*     */ 
/*  12 */   private static PropertyHelper instance = new PropertyHelper();
/*     */ 
/*     */   public static PropertyHelper getInstance() {
/*  15 */     return instance;
/*     */   }
/*     */ 
/*     */   public void setAttribute(PropertyMap pmap, Object object, Method method, Object value)
/*     */     throws XmlException
/*     */   {
/*  23 */     if (object == null)
/*  24 */       throw new XmlException(
/*  25 */         "PropertyHelper setAttribute方法 Object参数不能为空");
/*  26 */     Class paramClass = null;
/*  27 */     String paramClassName = null;
/*  28 */     Class[] paramTypes = (Class[])null;
/*  29 */     if (method != null) {
/*  30 */       paramTypes = method.getParameterTypes();
/*  31 */       if ((paramTypes != null) && (paramTypes.length == 1)) {
/*  32 */         paramClass = paramTypes[0];
/*  33 */         paramClassName = paramClass.getName();
/*     */       }
/*     */     }
/*  36 */     Converter converter = pmap.getConverter();
/*  37 */     if (converter == null)
/*  38 */       converter = this.config.getConverter(paramClassName);
/*  39 */     if ((converter == null) && 
/*  40 */       ((value instanceof String))) {
/*  41 */       return;
/*     */     }
/*     */ 
/*  44 */     Object[] params = (Object[])null;
/*  45 */     if ((value != null) && (paramClassName != null)) {
/*  46 */       params = new Object[1];
/*  47 */       if (converter == null) {
/*  48 */         params[0] = value;
/*     */       } else {
/*  50 */         Object converterImpl = null;
/*  51 */         if (converter.getClass().getName().equals(converter.getClassname())) {
/*  52 */           converterImpl = converter;
/*     */         } else {
/*  54 */           ObjectFactory factory = this.config.getObjectFactory();
/*  55 */           converterImpl = factory.getInstance(converter.getClassname());
/*     */         }
/*     */         try {
/*  58 */           Class[] convTypes = new Class[1];
/*  59 */           convTypes[0] = value.getClass();
/*  60 */           Method parseMethod = converterImpl.getClass().getMethod(
/*  61 */             converter.getParseMethod(), convTypes);
/*  62 */           Object[] convParams = new Object[1];
/*  63 */           convParams[0] = value;
/*  64 */           params[0] = parseMethod.invoke(converterImpl, convParams);
/*     */         } catch (NoSuchMethodException localNoSuchMethodException) {
/*     */         }
/*     */         catch (InvocationTargetException localInvocationTargetException) {
/*     */         }
/*     */         catch (Exception localException) {
/*     */         }
/*     */       }
/*     */     }
/*  73 */     else if (paramClassName != null) {
/*  74 */       params = new Object[1];
/*  75 */       params[0] = null;
/*     */     }
/*     */     try {
/*  78 */       method.invoke(object, params);
/*     */     }
/*     */     catch (IllegalArgumentException localIllegalArgumentException)
/*     */     {
/*     */     }
/*     */     catch (IllegalAccessException localIllegalAccessException) {
/*     */     }
/*     */     catch (InvocationTargetException localInvocationTargetException1) {
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getPropertyNameFromAlias(String classAlias, String alias) {
/*  90 */     if (alias == null) {
/*  91 */       return null;
/*     */     }
/*  93 */     String result = alias;
/*  94 */     if (this.config.getPropertyMapByName(alias) != null) {
/*  95 */       PropertyMap map = this.config.getPropertyMapByName(alias);
/*  96 */       if ((map != null) && (map.getPropertyName() != null)) {
/*  97 */         result = map.getPropertyName();
/*     */       }
/*     */     }
/* 100 */     if (this.config.getObjectMapByName(alias) != null) {
/* 101 */       ObjectMap map = this.config.getObjectMapByName(alias);
/* 102 */       result = map.getName();
/* 103 */       return result;
/*     */     }
/* 105 */     if (classAlias == null) {
/* 106 */       return result;
/*     */     }
/* 108 */     if (this.config.getObjectMapByName(classAlias) != null) {
/* 109 */       ObjectMap map = this.config.getObjectMapByName(classAlias);
/* 110 */       if ((map != null) && (map.getPropertyMapFromName(alias) != null)) {
/* 111 */         PropertyMap pMap = map.getPropertyMapFromName(alias);
/* 112 */         if (pMap.getPropertyName() != null) {
/* 113 */           result = pMap.getPropertyName();
/*     */         }
/*     */       }
/*     */     }
/* 117 */     if (result != null) result.length();
/*     */ 
/* 120 */     return result;
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\xml4j.jar
 * Qualified Name:     com.aceway.vas.xml4j.core.PropertyHelper
 * JD-Core Version:    0.6.0
 */