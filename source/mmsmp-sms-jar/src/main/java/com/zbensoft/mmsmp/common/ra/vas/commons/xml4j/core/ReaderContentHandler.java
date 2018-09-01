/*     */ package com.zbensoft.mmsmp.common.ra.vas.commons.xml4j.core;
/*     */

import com.zbensoft.mmsmp.common.ra.vas.commons.xml4j.util.StringHelper;
import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */

/*     */
/*     */ public class ReaderContentHandler extends DefaultHandler
/*     */ {
/*     */   public static final String EX_SETTER_NAME = "Cannot find appropriate setter name";
/*     */   public static final String EX_SETTER = "Cannot find appropriate setter";
/*     */   public static final String EX_CREATE_OBJECT = "Could not create object ";
/*     */   public static final String EX_CORRECT_SETTER = "Unable to find correct setter: ";
/*     */   public static final String EX_WRONG_NUM_PARAMS = "Wrong number of parameters in method.";
/*     */   public static final String EX_STACK_NOT_EMPTY = "Parser stack not empty at the end of the document.";
/*     */   private Stack<String> configStack;
/*     */   private Map<String, LinkedList<Object>> configObjects;
/*     */   private Object root;
/*  32 */   private final Map<String, Boolean> elementProcessed = new HashMap();
/*  33 */   private final Stack<Boolean> isObject = new Stack();
/*     */   private List<String> targetPackage;
/*     */   private String currentPackage;
/*  37 */   private String lastCharacters = "";
/*  38 */   private final XmlConfig config = XmlConfig.getInstance();
/*  39 */   private final PropertyHelper propertyHelper = PropertyHelper.getInstance();
/*  40 */   private Locator locator = null;
/*     */ 
/*     */   public void startDocument() throws SAXException
/*     */   {
/*  44 */     this.configStack = new Stack();
/*  45 */     this.configObjects = new HashMap();
/*  46 */     this.root = null;
/*     */   }
/*     */ 
/*     */   public void startElement(String namespaceUri, String localName, String qName, Attributes atttributes) throws SAXException
/*     */   {
/*  51 */     qName = getJavaName(qName);
/*  52 */     this.lastCharacters = "";
/*  53 */     String element = null;
/*  54 */     if (this.configStack.size() > 0) {
/*  55 */       element = (String)this.configStack.peek();
/*     */     }
/*  57 */     if (element != null) {
/*  58 */       this.elementProcessed.put(element, Boolean.FALSE);
/*     */     }
/*  60 */     if (this.config.getObjectMapByName(qName) != null) {
/*  61 */       qName = this.config.getObjectMapByName(qName).getName();
/*     */     }
/*  63 */     Object obj = createObject(qName);
/*  64 */     if (obj != null)
/*  65 */       this.isObject.push(Boolean.TRUE);
/*     */     else {
/*  67 */       this.isObject.push(Boolean.FALSE);
/*     */     }
/*  69 */     if (this.root == null)
/*  70 */       this.root = obj;
/*  71 */     if ((obj != null) && (this.config.getObjectMapByName(qName) == null))
/*  72 */       this.config.addClassAsMappedObject(obj.getClass());
/*  73 */     this.configStack.push(qName);
/*     */   }
/*     */ 
/*     */   public void endElement(String namespaceURI, String localName, String qName) throws SAXException
/*     */   {
/*  78 */     String name = (String)this.configStack.pop();
/*  79 */     if ((name == null) || ("".equals(name)))
/*  80 */       return;
/*  81 */     if ((name.equals("null")) && 
/*  82 */       (attributeOnStack())) {
/*  83 */       String attributeName = (String)this.configStack.peek();
/*  84 */       setCurrentAttribute(null);
/*  85 */       this.elementProcessed.put(attributeName, Boolean.TRUE);
/*  86 */       return;
/*     */     }
/*     */ 
/*  89 */     if (name != null) {
/*  90 */       name = getJavaName(name);
/*  91 */       this.configStack.push(name);
/*     */     }
/*  93 */     Boolean objectFlag = (Boolean)this.isObject.pop();
/*  94 */     if (objectFlag == Boolean.FALSE) {
/*  95 */       Boolean called = (Boolean)this.elementProcessed.get(name);
/*  96 */       if ((called == null) || (called == Boolean.FALSE))
/*  97 */         setCurrentAttribute(this.lastCharacters);
/*  98 */       this.configStack.pop();
/*  99 */       this.elementProcessed.remove(name);
/* 100 */       return;
/*     */     }
/* 102 */     Object object = getObject(name);
/* 103 */     if (object == null)
/* 104 */       object = createObject(name);
/* 105 */     if (object == null)
/* 106 */       return;
/* 107 */     removeObject(name);
/* 108 */     setCurrentAttribute(object);
/* 109 */     this.configStack.pop();
/*     */   }
/*     */ 
/*     */   public void characters(char[] ch, int start, int length) throws SAXException
/*     */   {
/* 114 */     if (!attributeOnStack())
/* 115 */       return;
/* 116 */     String attributeName = (String)this.configStack.peek();
/* 117 */     attributeName = getJavaName(attributeName);
/* 118 */     Boolean called = (Boolean)this.elementProcessed.get(attributeName);
/* 119 */     if ((called != null) && (called == Boolean.TRUE))
/* 120 */       return;
/* 121 */     String value = new String(ch, start, length);
/* 122 */     StringBuffer temp = new StringBuffer();
/* 123 */     if (this.lastCharacters != null)
/* 124 */       temp.append(this.lastCharacters);
/* 125 */     temp.append(value);
/* 126 */     this.lastCharacters = temp.toString();
/*     */   }
/*     */ 
/*     */   public void endDocument() throws SAXException {
/* 130 */     if (!this.configStack.empty())
/* 131 */       throw new SAXException("Parser stack not empty at the end of the document." + getLocation() + getRemaining());
/*     */   }
/*     */ 
/*     */   public ReaderContentHandler(String thePackage)
/*     */   {
/* 139 */     this.targetPackage = new LinkedList();
/* 140 */     if (thePackage == null)
/* 141 */       thePackage = "";
/* 142 */     StringTokenizer st = new StringTokenizer(thePackage, ":");
/* 143 */     while (st.hasMoreElements()) {
/* 144 */       String path = (String)st.nextElement();
/* 145 */       path = correctPackageEnding(path);
/* 146 */       this.targetPackage.add(path);
/*     */     }
/*     */   }
/*     */ 
/*     */   private String correctPackageEnding(String thePackage) {
/* 151 */     if ((thePackage == null) || ("".equals(thePackage)))
/* 152 */       return thePackage;
/* 153 */     if (!thePackage.endsWith(".")) {
/* 154 */       StringBuffer test = new StringBuffer(thePackage);
/* 155 */       test.append(".");
/* 156 */       thePackage = test.toString();
/* 157 */       test = null;
/*     */     }
/* 159 */     return thePackage;
/*     */   }
/*     */ 
/*     */   private String getJavaName(String xmlName)
/*     */   {
/* 164 */     if (xmlName == null)
/* 165 */       return null;
/* 166 */     String result = null;
/* 167 */     String className = null;
/* 168 */     if (this.configStack.size() > 0) {
/* 169 */       className = (String)this.configStack.pop();
/*     */     }
/* 171 */     if (className != null) {
/* 172 */       this.configStack.push(className);
/*     */     }
/* 174 */     if (this.config.getPropertyMapByName(xmlName) != null) {
/* 175 */       result = this.config.getPropertyMapByName(xmlName).getPropertyName();
/*     */     }
/* 177 */     if ((result == null) && (className != null)) {
/* 178 */       ObjectMap oMap = this.config.getObjectMapByName(className);
/* 179 */       if (oMap == null) {
/* 180 */         className = StringHelper.capitalizeFirst(className);
/* 181 */         oMap = this.config.getObjectMapByName(className);
/*     */       }
/* 183 */       if ((oMap != null) && (oMap.getPropertyMapFromName(xmlName) != null))
/* 184 */         result = oMap.getPropertyMapFromName(xmlName).getPropertyName();
/*     */     }
/* 186 */     if (result == null)
/* 187 */       result = xmlName;
/* 188 */     return result;
/*     */   }
/*     */ 
/*     */   private boolean attributeOnStack() {
/* 192 */     String attributeName = null;
/* 193 */     if (this.configStack.size() > 0) {
/* 194 */       attributeName = (String)this.configStack.peek();
/*     */     }
/* 196 */     return !Character.isUpperCase(attributeName.charAt(0));
/*     */   }
/*     */ 
/*     */   private void setCurrentAttribute(Object value)
/*     */     throws SAXException
/*     */   {
/* 201 */     String attributeName = null;
/* 202 */     String objectName = null;
/* 203 */     if (this.configStack.size() > 0)
/* 204 */       attributeName = (String)this.configStack.pop();
/* 205 */     if (this.configStack.size() > 0)
/* 206 */       objectName = (String)this.configStack.pop();
/* 207 */     if (attributeName == null)
/* 208 */       return;
/* 209 */     if (objectName == null) {
/* 210 */       this.configStack.push(attributeName);
/* 211 */       return;
/*     */     }
/*     */ 
/* 214 */     this.configStack.push(objectName);
/* 215 */     this.configStack.push(attributeName);
/* 216 */     if ((attributeName.equals("")) || (objectName.equals(""))) {
/* 217 */       return;
/*     */     }
/* 219 */     Object object = getObject(objectName);
/* 220 */     if (object == null) {
/* 221 */       object = createObject(objectName);
/* 222 */       if (object == null)
/* 223 */         throw new SAXException("Could not create object " + objectName + getLocation());
/*     */     }
/* 225 */     String methodName = attributeName;
/*     */ 
/* 227 */     ObjectMap omap = this.config.getObjectMapByName(objectName);
/* 228 */     if (omap == null)
/* 229 */       omap = this.config.getObjectMapByName(StringHelper.classNameWithoutPackage(objectName));
/* 230 */     if (omap == null) {
/* 231 */       omap = ObjectMap.createFromClass(object.getClass());
/* 232 */       this.config.addObjectMap(omap);
/*     */     }
/*     */ 
/* 235 */     Method method = null;
/* 236 */     boolean global = false;
/*     */ 
/* 238 */     PropertyMap pmap = omap.getPropertyMapFromName(methodName);
/*     */ 
/* 243 */     if (pmap == null)
/* 244 */       pmap = omap.getPropertyMapFromName(StringHelper.lowerCaseFirst(methodName));
/* 245 */     if (pmap == null)
/* 246 */       pmap = omap.getPropertyMapFromName(StringHelper.capitalizeFirst(methodName));
/* 247 */     if (pmap == null) {
/* 248 */       pmap = omap.getPropertyMapFromName(StringHelper.lowerCaseFirst(methodName + "s"));
/*     */     }
/* 250 */     StringBuffer trace = new StringBuffer(methodName);
/*     */ 
/* 252 */     if (pmap == null) {
/* 253 */       String error = "Cannot find appropriate setter name for " + trace.toString() + " in " + 
/* 254 */         objectName;
/* 255 */       throw new SAXException(error);
/*     */     }
/* 257 */     if ((pmap != null) && (pmap.getPropertyType() != null) && 
/* 258 */       (pmap.getPropertyType().toString().indexOf("List") != -1)) {
/* 259 */       method = pmap.getGetter();
/*     */       try {
/* 261 */         Object[] nullObject = (Object[])null;
/* 262 */         Object obj = method.invoke(object, nullObject);
/* 263 */         Class[] cArray = new Class[1];
/*     */         try {
/* 265 */           Method addMethod = obj.getClass().getMethod("add", new Class[] { Object.class });
/* 266 */           addMethod.invoke(obj, new Object[] { value });
/*     */         } catch (SecurityException localSecurityException) {
/*     */         } catch (NoSuchMethodException localNoSuchMethodException) {
/*     */         }
/*     */       } catch (IllegalArgumentException localIllegalArgumentException) {
/*     */       } catch (IllegalAccessException localIllegalAccessException) {
/*     */       } catch (InvocationTargetException localInvocationTargetException) {
/*     */       }
/*     */     } else {
/* 275 */       method = pmap.getSetter();
/*     */       try
/*     */       {
/* 278 */         this.propertyHelper.setAttribute(pmap, object, method, value);
/*     */       } catch (XmlException e) {
/* 280 */         throw new SAXException(e.toString() + getLocation());
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private Object instantiateObject(String objectName)
/*     */   {
/* 288 */     if (objectName == null)
/* 289 */       return null;
/* 290 */     Object object = this.config.getObjectFactory().getInstance(objectName);
/* 291 */     if (object != null)
/* 292 */       return object;
/* 293 */     for (Iterator i = this.targetPackage.iterator(); i.hasNext(); ) {
/* 294 */       this.currentPackage = ((String)i.next());
/* 295 */       String className = this.currentPackage + objectName;
/* 296 */       object = this.config.getObjectFactory().getInstance(className);
/* 297 */       if (object != null)
/* 298 */         return object;
/*     */     }
/* 300 */     return null;
/*     */   }
/*     */ 
/*     */   private Object createObject(String objectName)
/*     */   {
/* 305 */     Object object = instantiateObject(objectName);
/* 306 */     if (object != null) {
/* 307 */       putObject(objectName, object);
/* 308 */       return object;
/*     */     }
/* 310 */     return object;
/*     */   }
/*     */ 
/*     */   private void putObject(String objectName, Object object) {
/* 314 */     LinkedList coll = (LinkedList)this.configObjects.get(objectName);
/* 315 */     if (coll == null)
/* 316 */       coll = new LinkedList();
/* 317 */     coll.add(object);
/* 318 */     this.configObjects.put(objectName, coll);
/*     */   }
/*     */ 
/*     */   private Object getObject(String objectName) {
/* 322 */     Object result = null;
/* 323 */     LinkedList coll = (LinkedList)this.configObjects.get(objectName);
/* 324 */     if ((coll != null) && (coll.size() > 0))
/* 325 */       result = coll.getLast();
/* 326 */     return result;
/*     */   }
/*     */ 
/*     */   private void removeObject(String objectName) {
/* 330 */     LinkedList coll = (LinkedList)this.configObjects.get(objectName);
/* 331 */     if (coll != null) {
/* 332 */       Object last = coll.getLast();
/* 333 */       coll.remove(last);
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getRemaining()
/*     */   {
/* 339 */     StringBuffer result = new StringBuffer();
/* 340 */     String sep = "";
/* 341 */     while (!this.configStack.isEmpty()) {
/* 342 */       String item = (String)this.configStack.pop();
/* 343 */       result.append(sep);
/* 344 */       result.append(item);
/* 345 */       sep = ",";
/*     */     }
/* 347 */     return result.toString();
/*     */   }
/*     */ 
/*     */   public Object getResult()
/*     */   {
/* 352 */     return this.root;
/*     */   }
/*     */ 
/*     */   private String getLocation() {
/* 356 */     if (this.locator == null)
/* 357 */       return "";
/* 358 */     StringBuffer result = new StringBuffer();
/* 359 */     result.append(" - line = ");
/* 360 */     result.append(this.locator.getLineNumber());
/* 361 */     result.append(", column = ");
/* 362 */     result.append(this.locator.getColumnNumber());
/* 363 */     return result.toString();
/*     */   }
/*     */ 
/*     */   public void setDocumentLocator(Locator locator) {
/* 367 */     if (locator == null)
/* 368 */       return;
/* 369 */     this.locator = locator;
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\xml4j.jar
 * Qualified Name:     com.aceway.vas.xml4j.core.ReaderContentHandler
 * JD-Core Version:    0.6.0
 */