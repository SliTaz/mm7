/*     */ package com.zbensoft.mmsmp.common.ra.common.mmcontents;
/*     */ 
/*     */ import javax.mail.internet.ContentType;
/*     */ import javax.mail.internet.HeaderTokenizer;
/*     */ import javax.mail.internet.HeaderTokenizer.Token;
/*     */ import javax.mail.internet.MimeUtility;
/*     */ import javax.mail.internet.ParameterList;
/*     */ import javax.mail.internet.ParseException;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class AcewayMMContentType extends ContentType
/*     */ {
/*  18 */   private static final Logger logger = Logger.getLogger(AcewayMMContentType.class);
/*     */   public static final String MimeCharset_UTF16 = "iso-10646-ucs-2";
/*     */   public static final String MimeCharset_UTF16_HuaWei = "iso-10646-bytes-2";
/*     */   public static final String JavaCharset_UTF16 = "UTF16";
/*     */   public static final String MimeCharset_ShiftJIS = "shift_jis";
/*     */   public static final String JavaCharset_ShiftJIS = "SJIS";
/*     */ 
/*     */   public static String getMime2JavaCharset(String MIMECharset)
/*     */   {
/*  35 */     if (MIMECharset == null) {
/*  36 */       return null;
/*     */     }
/*  38 */     if ((MIMECharset.equalsIgnoreCase("iso-10646-ucs-2")) || (MIMECharset.equalsIgnoreCase("iso-10646-bytes-2")))
/*  39 */       return "UTF16";
/*  40 */     if (MIMECharset.equalsIgnoreCase("shift_jis")) {
/*  41 */       return "SJIS";
/*     */     }
/*  43 */     return MimeUtility.javaCharset(MIMECharset);
/*     */   }
/*     */ 
/*     */   public static String getJava2MimeCharset(String javaCharset)
/*     */   {
/*  54 */     if (javaCharset == null) {
/*  55 */       return null;
/*     */     }
/*  57 */     if ((javaCharset.equalsIgnoreCase("UTF8")) || (javaCharset.equalsIgnoreCase("UTF-8"))) {
/*  58 */       return "utf-8";
/*     */     }
/*  60 */     return MimeUtility.mimeCharset(javaCharset);
/*     */   }
/*     */ 
/*     */   public AcewayMMContentType()
/*     */   {
/*     */   }
/*     */ 
/*     */   public AcewayMMContentType(String arg1)
/*     */     throws ParseException
/*     */   {
/*  81 */     this();
/*  82 */     HeaderTokenizer h = new HeaderTokenizer(arg1, "()<>@,;:\\\"\t []/?=");
/*     */ 
/*  86 */     HeaderTokenizer.Token tk = h.next();
/*     */ 
/*  88 */     if (tk.getType() != -1) {
/*  89 */       throw new ParseException(arg1);
/*     */     }
/*  91 */     setPrimaryType(tk.getValue());
/*     */ 
/*  94 */     tk = h.next();
/*     */ 
/*  96 */     if ((char)tk.getType() != '/') {
/*  97 */       throw new ParseException(arg1);
/*     */     }
/*     */ 
/* 100 */     tk = h.next();
/*     */ 
/* 102 */     if (tk.getType() != -1) {
/* 103 */       throw new ParseException(arg1);
/*     */     }
/* 105 */     setSubType(tk.getValue());
/*     */ 
/* 108 */     String rem = h.getRemainder();
/*     */ 
/* 110 */     if (rem != null)
/* 111 */       setParameterList(parseParameters(rem));
/*     */   }
/*     */ 
/*     */   public AcewayMMContentType(String arg1, String arg2, ParameterList arg3)
/*     */   {
/* 122 */     super(arg1, arg2, arg3);
/*     */   }
/*     */ 
/*     */   public static ParameterList parseParameters(String para)
/*     */     throws ParseException
/*     */   {
/* 135 */     ParameterList list = null;
/*     */     try
/*     */     {
/* 139 */       list = new ParameterList(para); } catch (Exception e) {
/* 140 */       logger.error("Invalid Parameters: " + para + ": " + e.getMessage());
/*     */     }
/* 142 */     if (list == null)
/*     */     {
/* 144 */       list = new ParameterList();
/*     */ 
/* 146 */       if (para == null) {
/* 147 */         return list;
/*     */       }
/* 149 */       int index = 0;
/*     */ 
/* 151 */       if (para.startsWith(";")) {
/* 152 */         index++;
/*     */       }
/* 154 */       while (index < para.length())
/*     */       {
/* 156 */         int index1 = para.indexOf(';', index);
/*     */ 
/* 158 */         if (index1 == -1) {
/* 159 */           index1 = para.length();
/* 160 */         } else if (index1 == index)
/*     */         {
/* 162 */           index++;
/* 163 */           continue;
/*     */         }
/*     */ 
/* 166 */         int index2 = para.indexOf('=', index);
/*     */ 
/* 168 */         if (index2 == -1) {
/* 169 */           throw new ParseException("Invalid Parameters: " + para);
/*     */         }
/* 171 */         String name = para.substring(index, index2).trim();
/* 172 */         String value = para.substring(index2 + 1, index1).trim();
/*     */ 
/* 174 */         if ("".equals(value)) {
/* 175 */           throw new ParseException("Invalid Parameters: " + name + ", " + para);
/*     */         }
/*     */ 
/* 180 */         list.set(name, value);
/*     */ 
/* 182 */         index = index1 + 1;
/*     */       }
/*     */     }
/*     */ 
/* 186 */     return list;
/*     */   }
/*     */ 
/*     */   public boolean hasBoundary()
/*     */   {
/* 195 */     String tmp_str = getParameter("boundary");
/*     */ 
/* 198 */     return tmp_str != null;
/*     */   }
/*     */ 
/*     */   public void addBoundary()
/*     */   {
/* 209 */     StringBuffer s = new StringBuffer();
/*     */ 
/* 214 */     s.append("----_Part_").append(s.hashCode()).append(".").append(System.currentTimeMillis());
/* 215 */     String str = s.toString();
/* 216 */     setParameter("boundary", str);
/*     */   }
/*     */ 
/*     */   public String getBoundary()
/*     */   {
/* 226 */     String tmp_str = getParameter("boundary");
/*     */ 
/* 228 */     if (tmp_str == null) {
/* 229 */       return null;
/*     */     }
/*     */ 
/* 232 */     if (tmp_str.startsWith("\"")) {
/* 233 */       tmp_str = tmp_str.substring(1);
/*     */     }
/* 235 */     if (tmp_str.endsWith("\"")) {
/* 236 */       tmp_str = tmp_str.substring(0, tmp_str.length() - 1);
/*     */     }
/* 238 */     return tmp_str;
/*     */   }
/*     */ 
/*     */   public boolean isMultipart()
/*     */   {
/* 247 */     String tmp_str = getPrimaryType().toLowerCase();
/* 248 */     return tmp_str.startsWith("multipart");
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 253 */     String tmp_str = super.toString();
/*     */ 
/* 255 */     if (tmp_str == null) {
/* 256 */       return null;
/*     */     }
/* 258 */     StringBuffer tmp_buf = new StringBuffer();
/*     */ 
/* 260 */     for (int n = 0; n < tmp_str.length(); n++)
/*     */     {
/* 262 */       if ((tmp_str.charAt(n) == '\r') || (tmp_str.charAt(n) == '\n')) {
/*     */         continue;
/*     */       }
/* 265 */       tmp_buf.append(tmp_str.charAt(n));
/*     */     }
/*     */ 
/* 268 */     if (tmp_buf.length() == 0) {
/* 269 */       return null;
/*     */     }
/* 271 */     return tmp_buf.toString();
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.mmcontents.AcewayMMContentType
 * JD-Core Version:    0.6.0
 */