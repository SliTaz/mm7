/*     */ package com.zbensoft.mmsmp.common.ra.vas.commons.xml4j.core;
/*     */ 
/*     */

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

/*     */
/*     */
/*     */
/*     */
/*     */
/*     */

/*     */
/*     */ public class ObjectMap
/*     */ {
/*     */   private String name;
/*  13 */   private List<PropertyMap> properties = new LinkedList();
/*  14 */   private Map<String, PropertyMap> propertyNameMap = new HashMap();
/*     */ 
/*     */   public ObjectMap()
/*     */   {
/*     */   }
/*     */ 
/*     */   public ObjectMap(String xmlName, String javaName, List<PropertyMap> propertyMap) {
/*  21 */     this.name = javaName;
/*  22 */     propertyMapList(propertyMap);
/*     */   }
/*     */ 
/*     */   public void createFromClass(Class theClass, boolean onlyMethods, boolean asAttribute)
/*     */   {
/*  31 */     if (theClass == null)
/*  32 */       return;
/*  33 */     if ((getName() == null) || ("".equals(getName()))) {
/*  34 */       setName(nameWithoutPackage(theClass.getName()));
/*     */     }
/*  36 */     setName(nameWithoutPackage(theClass.getName()));
/*  37 */     Method[] methods = theClass.getMethods();
/*  38 */     for (int i = 0; i < methods.length; i++) {
/*  39 */       Method method = methods[i];
/*  40 */       String methodName = method.getName();
/*  41 */       if (!Modifier.isPublic(method.getModifiers()))
/*     */         continue;
/*  43 */       if (Modifier.isStatic(method.getModifiers()))
/*     */         continue;
/*  45 */       if (Modifier.isTransient(method.getModifiers()))
/*     */         continue;
/*  47 */       String propertyName = PropertyMap.trimPropertyName(methodName);
/*  48 */       PropertyMap pMap = getPropertyMapFromName(propertyName);
/*  49 */       boolean addIt = false;
/*  50 */       if (pMap == null)
/*  51 */         pMap = new PropertyMap();
/*  52 */       pMap.setWriteAsAttribute(asAttribute);
/*     */ 
/*  54 */       if (((methodName.startsWith("get")) || (methodName.startsWith("is"))) && 
/*  55 */         (method.getParameterTypes().length == 0) && 
/*  56 */         (method.getReturnType() != null) && 
/*  57 */         (!"getClass".equals(methodName))) {
/*  58 */         if (!onlyMethods)
/*  59 */           pMap.createReadableProperty(methodName, method);
/*     */         else
/*  61 */           pMap.setGetter(method);
/*  62 */         addIt = true;
/*     */       }
/*     */ 
/*  65 */       if (((methodName.startsWith("set")) || (methodName.startsWith("add"))) && 
/*  66 */         (method.getParameterTypes().length == 1) && 
/*  67 */         ("void".equals(method.getReturnType().getName()))) {
/*  68 */         if (!onlyMethods)
/*  69 */           pMap.createMutableProperty(methodName, method);
/*     */         else
/*  71 */           pMap.setSetter(method);
/*  72 */         addIt = true;
/*     */       }
/*  74 */       if ((addIt) && (getPropertyMapFromName(propertyName) == null))
/*  75 */         addPropertyMap(pMap);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void addPropertyMap(PropertyMap property)
/*     */   {
/*  81 */     this.properties.add(property);
/*  82 */     if (property.getPropertyName() != null)
/*  83 */       this.propertyNameMap.put(property.getPropertyName(), property);
/*     */   }
/*     */ 
/*     */   public void propertyMapList(List<PropertyMap> properties)
/*     */   {
/*  88 */     if (properties == null)
/*  89 */       return;
/*  90 */     for (Iterator i = properties.iterator(); i.hasNext(); ) {
/*  91 */       Object test = i.next();
/*  92 */       if ((test instanceof PropertyMap)) {
/*  93 */         PropertyMap map = (PropertyMap)test;
/*  94 */         addPropertyMap(map);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public PropertyMap getPropertyMapFromName(String name) {
/* 100 */     if (this.propertyNameMap != null)
/* 101 */       return (PropertyMap)this.propertyNameMap.get(name);
/* 102 */     return null;
/*     */   }
/*     */ 
/*     */   public static ObjectMap createFromClassAsAttributes(Class theClass) {
/* 106 */     if (theClass == null)
/* 107 */       return null;
/* 108 */     ObjectMap oMap = new ObjectMap();
/* 109 */     oMap.createFromClass(theClass, false, true);
/* 110 */     return oMap;
/*     */   }
/*     */ 
/*     */   public static ObjectMap createFromClass(Class theClass) {
/* 114 */     if (theClass == null)
/* 115 */       return null;
/* 116 */     ObjectMap oMap = new ObjectMap();
/* 117 */     oMap.createFromClass(theClass, false, false);
/* 118 */     return oMap;
/*     */   }
/*     */ 
/*     */   public void createFromClass(Class theClass, boolean onlyMethods) {
/* 122 */     createFromClass(theClass, onlyMethods, false);
/*     */   }
/*     */ 
/*     */   private static String nameWithoutPackage(String source) {
/* 126 */     int index = source.lastIndexOf(".");
/* 127 */     if ((index < 0) || (index == source.length()))
/* 128 */       return source;
/* 129 */     return source.substring(index + 1);
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/* 134 */     return this.name;
/*     */   }
/*     */ 
/*     */   public void setName(String name)
/*     */   {
/* 139 */     this.name = name;
/*     */   }
/*     */ 
/*     */   public List getPropertyMap() {
/* 143 */     return this.properties;
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\xml4j.jar
 * Qualified Name:     com.aceway.vas.xml4j.core.ObjectMap
 * JD-Core Version:    0.6.0
 */