/*     */ package com.zbensoft.mmsmp.common.ra.vas.xml4j.core;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Modifier;

import com.zbensoft.mmsmp.common.ra.vas.xml4j.converter.Converter;
/*     */ 
/*     */ public class PropertyMap
/*     */ {
/*     */   private String propertyName;
/*     */   private Class propertyType;
/*     */   private Method setter;
/*     */   private Method getter;
/*  14 */   private boolean writeAsAttribute = false;
/*     */   private Converter converter;
/*     */ 
/*     */   public PropertyMap()
/*     */   {
/*     */   }
/*     */ 
/*     */   public PropertyMap(String javaName)
/*     */   {
/*  21 */     this.propertyName = javaName;
/*     */   }
/*     */ 
/*     */   public Method guessSetter(Object object)
/*     */   {
/*  26 */     Method method = null;
/*  27 */     Method[] methods = object.getClass().getMethods();
/*  28 */     for (int i = 0; i < methods.length; i++) {
/*  29 */       Method check = methods[i];
/*  30 */       String methodName = check.getName();
/*  31 */       if (!hasAttributeModifiers(check))
/*     */         continue;
/*  33 */       if (((!methodName.startsWith("set")) && (!methodName.startsWith("add"))) || 
/*  34 */         (check.getParameterTypes().length != 1) || 
/*  35 */         (!"void".equals(check.getReturnType().getName()))) continue;
/*  36 */       methodName = methodName.substring(3);
/*  37 */       if (methodName.compareToIgnoreCase(this.propertyName) == 0) {
/*  38 */         method = check;
/*  39 */         break;
/*     */       }
/*     */     }
/*     */ 
/*  43 */     return method;
/*     */   }
/*     */ 
/*     */   public Method guessGetter(Object object) {
/*  47 */     Method method = null;
/*  48 */     Method[] methods = object.getClass().getMethods();
/*  49 */     for (int i = 0; i < methods.length; i++) {
/*  50 */       Method check = methods[i];
/*  51 */       String methodName = check.getName();
/*  52 */       if (!hasAttributeModifiers(check))
/*     */         continue;
/*  54 */       if (((!methodName.startsWith("get")) && (!methodName.startsWith("is"))) || 
/*  55 */         (check.getParameterTypes().length != 0) || 
/*  56 */         (check.getReturnType() == null) || 
/*  57 */         ("getClass".equals(methodName))) continue;
/*  58 */       if (methodName.startsWith("get"))
/*  59 */         methodName = methodName.substring(3);
/*  60 */       else if (methodName.startsWith("is"))
/*  61 */         methodName = methodName.substring(2);
/*  62 */       if (methodName.compareToIgnoreCase(this.propertyName) == 0) {
/*  63 */         method = check;
/*  64 */         break;
/*     */       }
/*     */     }
/*     */ 
/*  68 */     return method;
/*     */   }
/*     */ 
/*     */   private boolean hasAttributeModifiers(Method method)
/*     */   {
/*  77 */     boolean result = true;
/*  78 */     if (!Modifier.isPublic(method.getModifiers()))
/*  79 */       result = false;
/*  80 */     if (Modifier.isStatic(method.getModifiers()))
/*  81 */       result = false;
/*  82 */     if (Modifier.isTransient(method.getModifiers()))
/*  83 */       result = false;
/*  84 */     return result;
/*     */   }
/*     */ 
/*     */   public void createReadableProperty(String methodName, Method method) {
/*  88 */     createProperty(methodName);
/*  89 */     setGetter(method);
/*     */   }
/*     */ 
/*     */   public void createMutableProperty(String methodName, Method method)
/*     */   {
/*  98 */     createProperty(methodName);
/*  99 */     setSetter(method);
/* 100 */     Class theClass = null;
/* 101 */     if (method.getParameterTypes().length == 1)
/* 102 */       theClass = method.getParameterTypes()[0];
/* 103 */     else if (method.getReturnType() != null) {
/* 104 */       theClass = method.getReturnType();
/*     */     }
/*     */ 
/* 107 */     setPropertyType(theClass);
/*     */   }
/*     */ 
/*     */   private void createProperty(String methodName) {
/* 111 */     String name = trimPropertyName(methodName);
/* 112 */     setPropertyName(name);
/*     */   }
/*     */ 
/*     */   public static String trimPropertyName(String name)
/*     */   {
/* 121 */     if (name == null)
/* 122 */       return null;
/* 123 */     if ((name.startsWith("get")) || (name.startsWith("set")) || 
/* 124 */       (name.startsWith("add")))
/* 125 */       name = name.substring(3);
/* 126 */     if (name.startsWith("is"))
/* 127 */       name = name.substring(2);
/* 128 */     if (name.length() == 1)
/* 129 */       return name;
/* 130 */     StringBuffer result = new StringBuffer();
/* 131 */     result.append(Character.toLowerCase(name.charAt(0)));
/* 132 */     result.append(name.substring(1));
/* 133 */     return result.toString();
/*     */   }
/*     */   public String getPropertyName() {
/* 136 */     return this.propertyName;
/*     */   }
/*     */ 
/*     */   public void setPropertyName(String propertyName) {
/* 140 */     this.propertyName = propertyName;
/*     */   }
/*     */ 
/*     */   public Class getPropertyType() {
/* 144 */     return this.propertyType;
/*     */   }
/*     */ 
/*     */   public void setPropertyType(Class propertyType) {
/* 148 */     this.propertyType = propertyType;
/*     */   }
/*     */ 
/*     */   public Method getGetter()
/*     */   {
/* 153 */     return this.getter;
/*     */   }
/*     */ 
/*     */   public void setGetter(Method getter) {
/* 157 */     this.getter = getter;
/*     */   }
/*     */ 
/*     */   public Method getSetter()
/*     */   {
/* 162 */     return this.setter;
/*     */   }
/*     */ 
/*     */   public void setSetter(Method setter) {
/* 166 */     this.setter = setter;
/*     */   }
/*     */ 
/*     */   public boolean getWriteAsAttribute() {
/* 170 */     return this.writeAsAttribute;
/*     */   }
/*     */ 
/*     */   public void setWriteAsAttribute(boolean writeAsAttribute) {
/* 174 */     this.writeAsAttribute = writeAsAttribute;
/*     */   }
/*     */ 
/*     */   public Converter getConverter()
/*     */   {
/* 179 */     return this.converter;
/*     */   }
/*     */ 
/*     */   public void setConverter(Converter converter)
/*     */   {
/* 184 */     this.converter = converter;
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\xml4j.jar
 * Qualified Name:     com.aceway.vas.xml4j.core.PropertyMap
 * JD-Core Version:    0.6.0
 */