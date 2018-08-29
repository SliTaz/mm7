/*     */ package com.zbensoft.mmsmp.common.ra.vas.xml4j.core;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;

import com.zbensoft.mmsmp.common.ra.vas.xml4j.converter.Converter;
import com.zbensoft.mmsmp.common.ra.vas.xml4j.converter.DateConverter;
import com.zbensoft.mmsmp.common.ra.vas.xml4j.converter.DefaultConverter;
/*     */ 
/*     */ public class XmlConfig
/*     */ {
/*     */   private static XmlConfig instance;
/*  19 */   private final Map<String, Converter> converterMap = new HashMap();
/*  20 */   private final Map<String, ObjectMap> objectNameMap = new HashMap();
/*  21 */   private final Map<String, PropertyMap> propertyNameMap = new HashMap();
/*  22 */   private ObjectFactory objectFactory = new ObjectFactory();
/*     */ 
/*     */   public XmlConfig() {
/*  25 */     init();
/*     */   }
/*     */ 
/*     */   public static XmlConfig getInstance() {
/*  29 */     if (instance == null)
/*  30 */       createInstance();
/*  31 */     return instance;
/*     */   }
/*     */ 
/*     */   private static synchronized void createInstance() {
/*  35 */     instance = new XmlConfig();
/*     */   }
/*     */ 
/*     */   private void init() {
/*  39 */     List props = new LinkedList();
/*  40 */     addObjectMap(new ObjectMap("object", "ObjectMap", props));
/*  41 */     addObjectMap(new ObjectMap("property", "PropertyMap", props));
/*     */ 
/*  43 */     String defaultConverterName = DefaultConverter.class.getName();
/*     */ 
/*  46 */     addConverter(
/*  47 */       new Converter("boolean", defaultConverterName, 
/*  47 */       "parseBooleanPrimative", "printBooleanPrimative"));
/*  48 */     addConverter(
/*  49 */       new Converter("byte", defaultConverterName, 
/*  49 */       "parseBytePrimative", "printBytePrimative"));
/*  50 */     addConverter(
/*  51 */       new Converter("char", defaultConverterName, 
/*  51 */       "parseCharPrimative", "printCharPrimative"));
/*  52 */     addConverter(
/*  53 */       new Converter("double", defaultConverterName, 
/*  53 */       "parseDoublePrimative", "printDoublePrimative"));
/*  54 */     addConverter(
/*  55 */       new Converter("float", defaultConverterName, 
/*  55 */       "parseFloatPrimative", "printFloatPrimative"));
/*  56 */     addConverter(
/*  57 */       new Converter("int", defaultConverterName, 
/*  57 */       "parseIntegerPrimative", "printIntegerPrimative"));
/*  58 */     addConverter(
/*  59 */       new Converter("long", defaultConverterName, 
/*  59 */       "parseLongPrimative", "printLongPrimative"));
/*  60 */     addConverter(
/*  61 */       new Converter("short", defaultConverterName, 
/*  61 */       "parseShortPrimative", "printShortPrimative"));
/*     */ 
/*  64 */     addConverter(
/*  65 */       new Converter(Boolean.class.getName(), 
/*  65 */       defaultConverterName, "parseBoolean", "printBoolean"));
/*  66 */     addConverter(
/*  67 */       new Converter(Byte.class.getName(), defaultConverterName, 
/*  67 */       "parseByte", "printByte"));
/*  68 */     addConverter(
/*  69 */       new Converter(Double.class.getName(), 
/*  69 */       defaultConverterName, "parseDouble", "printDouble"));
/*  70 */     addConverter(
/*  71 */       new Converter(Character.class.getName(), 
/*  71 */       defaultConverterName, "parseChar", "printChar"));
/*  72 */     addConverter(
/*  73 */       new Converter(Float.class.getName(), defaultConverterName, 
/*  73 */       "parseFloat", "printFloat"));
/*  74 */     addConverter(
/*  75 */       new Converter(Integer.class.getName(), 
/*  75 */       defaultConverterName, "parseInteger", "printInteger"));
/*  76 */     addConverter(
/*  77 */       new Converter(Long.class.getName(), defaultConverterName, 
/*  77 */       "parseLong", "printLong"));
/*  78 */     addConverter(
/*  79 */       new Converter(Short.class.getName(), defaultConverterName, 
/*  79 */       "parseShort", "printShort"));
/*  80 */     addConverter(
/*  81 */       new Converter(String.class.getName(), 
/*  81 */       defaultConverterName, "parseString", "printString"));
/*  82 */     addConverter(
/*  83 */       new Converter(Date.class.getName(), 
/*  83 */       DateConverter.class.getName(), "parseDate", "printDate"));
/*     */   }
/*     */ 
/*     */   public void reset() {
/*  87 */     init();
/*     */   }
/*     */ 
/*     */   public PropertyMap getPropertyMapByName(String name)
/*     */   {
/*  92 */     if (name == null)
/*  93 */       return null;
/*  94 */     return (PropertyMap)this.propertyNameMap.get(name);
/*     */   }
/*     */ 
/*     */   public ObjectMap getObjectMapByName(String name)
/*     */   {
/*  99 */     if (name == null)
/* 100 */       return null;
/* 101 */     return (ObjectMap)this.objectNameMap.get(name);
/*     */   }
/*     */ 
/*     */   public void addPropertyMap(PropertyMap map) {
/* 105 */     if ((map != null) && 
/* 106 */       (map.getPropertyName() != null))
/* 107 */       this.propertyNameMap.put(map.getPropertyName(), map);
/*     */   }
/*     */ 
/*     */   public List<ObjectMap> getObjectMap()
/*     */   {
/* 112 */     List result = new ArrayList();
/* 113 */     Set keys = this.objectNameMap.keySet();
/* 114 */     for (Iterator i = keys.iterator(); i.hasNext(); ) {
/* 115 */       String key = (String)i.next();
/* 116 */       result.add((ObjectMap)this.objectNameMap.get(key));
/*     */     }
/* 118 */     return result;
/*     */   }
/*     */ 
/*     */   public void addObjectMap(ObjectMap map) {
/* 122 */     if (map == null)
/* 123 */       return;
/* 124 */     if (map.getName() != null)
/* 125 */       this.objectNameMap.put(map.getName(), map);
/*     */   }
/*     */ 
/*     */   public ObjectMap getMappedObject(String name) {
/* 129 */     if (this.objectNameMap == null)
/* 130 */       return null;
/* 131 */     return (ObjectMap)this.objectNameMap.get(name);
/*     */   }
/*     */ 
/*     */   public ObjectMap addClassAsMappedObject(Class theClass)
/*     */   {
/* 139 */     if ((theClass == null) || (this.objectNameMap == null))
/* 140 */       return null;
/* 141 */     ObjectMap om = ObjectMap.createFromClass(theClass);
/* 142 */     if ((om == null) || (om.getName() == null))
/* 143 */       return null;
/* 144 */     this.objectNameMap.put(om.getName(), om);
/* 145 */     return om;
/*     */   }
/*     */ 
/*     */   public List<Converter> getConverter() {
/* 149 */     List result = new LinkedList();
/* 150 */     Set keys = this.converterMap.keySet();
/* 151 */     for (Iterator i = keys.iterator(); i.hasNext(); ) {
/* 152 */       String key = (String)i.next();
/* 153 */       Converter conv = (Converter)this.converterMap.get(key);
/* 154 */       result.add(conv);
/*     */     }
/* 156 */     return result;
/*     */   }
/*     */ 
/*     */   public void addConverter(Converter converter) {
/* 160 */     if (converter.getType() != null)
/* 161 */       this.converterMap.put(converter.getType(), converter);
/*     */   }
/*     */ 
/*     */   public Converter getConverter(String type) {
/* 165 */     return (Converter)this.converterMap.get(type);
/*     */   }
/*     */ 
/*     */   public void setObjectFactory(String implementationName) {
/* 169 */     if (this.objectFactory == null)
/* 170 */       return;
/* 171 */     Object factory = this.objectFactory.getInstance(implementationName);
/* 172 */     if (factory == null)
/* 173 */       return;
/*     */   }
/*     */ 
/*     */   public ObjectFactory getObjectFactory() {
/* 177 */     return this.objectFactory;
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\xml4j.jar
 * Qualified Name:     com.aceway.vas.xml4j.core.XmlConfig
 * JD-Core Version:    0.6.0
 */