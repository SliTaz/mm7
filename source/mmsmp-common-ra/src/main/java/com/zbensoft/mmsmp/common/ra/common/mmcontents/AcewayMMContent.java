/*     */ package com.zbensoft.mmsmp.common.ra.common.mmcontents;
import com.zbensoft.mmsmp.common.ra.common.config.configbean.CommonConfig;
import com.zbensoft.mmsmp.common.ra.common.config.util.ConfigUtil;

/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.InputStream;
/*     */ import java.util.Enumeration;
/*     */ import javax.mail.internet.InternetHeaders;
/*     */ import javax.mail.internet.ParseException;
/*     */ import org.apache.log4j.Logger;
/*     */ import sun.misc.BASE64Decoder;
/*     */ import sun.misc.BASE64Encoder;
/*     */ 
/*     */ public class AcewayMMContent
/*     */ {
/*  27 */   private static Logger logger = Logger.getLogger(AcewayMMContent.class.getName());
/*     */ 
/*  29 */   private InternetHeaders m_Headers = new InternetHeaders();
/*  30 */   private AcewayMMContentType m_ContentType = null;
/*  31 */   private byte[] m_Content = null;
/*     */   public static final String Key_OctetStreamType = "ardor.application.octet";
/*     */ 
/*     */   public AcewayMMContent()
/*     */   {
/*     */   }
/*     */ 
/*     */   public AcewayMMContent(InputStream arg1)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void addHeader(String name, String value)
/*     */   {
/*  83 */     this.m_Headers.removeHeader(name);
/*  84 */     this.m_Headers.addHeader(name, value);
/*     */   }
/*     */ 
/*     */   public void addHeaderLine(String line)
/*     */   {
/*  93 */     this.m_Headers.removeHeader(line);
/*  94 */     this.m_Headers.addHeaderLine(line);
/*     */   }
/*     */ 
/*     */   public void decode(ByteArrayInputStream bis)
/*     */     throws Exception
/*     */   {
/* 108 */     this.m_Headers = new InternetHeaders(bis);
/*     */ 
/* 111 */     if (this.m_Headers != null)
/*     */     {
/* 114 */       String[] tmp_str = this.m_Headers.getHeader("Content-Type");
/*     */ 
/* 117 */       if (tmp_str != null)
/*     */       {
/* 120 */         this.m_ContentType = new AcewayMMContentType(tmp_str[0]);
/*     */ 
/* 122 */         String name = this.m_ContentType.getParameter("name");
/* 123 */         if (name != null)
/*     */         {
/* 125 */           if (name.length() != name.getBytes().length)
/*     */           {
/* 127 */             String newName = "csxx88";
/* 128 */             int index = name.indexOf('.');
/* 129 */             if (index != -1)
/* 130 */               newName = newName + name.substring(index);
/* 131 */             this.m_ContentType.setParameter("name", newName);
/*     */           }
/* 133 */           if ("application/octet-stream".equalsIgnoreCase(this.m_ContentType.getBaseType()))
/*     */           {
/* 135 */             int index = name.lastIndexOf(".");
/* 136 */             if (index != -1)
/*     */             {
/* 138 */               String extname = name.substring(index).toLowerCase();
/* 139 */               String type = System.getProperty("ardor.application.octet" + extname);
/* 140 */               if (type != null)
/*     */               {
/* 142 */                 index = type.indexOf("/");
/* 143 */                 if (index != -1)
/*     */                 {
/* 145 */                   this.m_ContentType.setPrimaryType(type.substring(0, index));
/* 146 */                   this.m_ContentType.setSubType(type.substring(index + 1));
/* 147 */                   if (logger.isDebugEnabled()) {
/* 148 */                     logger.debug(name + "(application/octet-stream) -->" + this.m_ContentType.getBaseType());
/*     */                   }
/*     */                 }
/*     */               }
/*     */ 
/*     */             }
/*     */ 
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 163 */     String mmsEncoding = ConfigUtil.getInstance().getCommonConfig().getMmsEncoding();
/* 164 */     if ("base64".equalsIgnoreCase(mmsEncoding)) {
/* 165 */       if ((getContentType().indexOf("imag") >= 0) || (getContentType().indexOf("audio") >= 0)) {
/* 166 */         byte[] m_Content_base64 = new byte[bis.available()];
/* 167 */         bis.read(m_Content_base64);
/* 168 */         BASE64Decoder basDecoder = new BASE64Decoder();
/*     */ 
/* 170 */         this.m_Content = basDecoder.decodeBuffer(new String(m_Content_base64));
/*     */       } else {
/* 172 */         this.m_Content = new byte[bis.available()];
/* 173 */         bis.read(this.m_Content);
/*     */       }
/* 175 */     } else if ("binary".equalsIgnoreCase(mmsEncoding)) {
/* 176 */       this.m_Content = new byte[bis.available()];
/* 177 */       bis.read(this.m_Content);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void encode(ByteArrayOutputStream bos)
/*     */     throws Exception
/*     */   {
/* 193 */     if (this.m_ContentType == null) {
/* 194 */       this.m_ContentType = new AcewayMMContentType("text/plain");
/*     */     }
/* 196 */     bos.write("Content-Type: ".getBytes());
/* 197 */     bos.write(this.m_ContentType.toString().getBytes());
/* 198 */     bos.write("\r\n".getBytes());
/*     */ 
/* 200 */     Enumeration e = this.m_Headers.getAllHeaderLines();
/* 201 */     while (e.hasMoreElements())
/*     */     {
/* 203 */       bos.write(((String)e.nextElement()).getBytes());
/* 204 */       bos.write("\r\n".getBytes());
/*     */     }
/* 206 */     bos.write("\r\n".getBytes());
/*     */ 
/* 208 */     BASE64Encoder basEncoder = new BASE64Encoder();
/*     */ 
/* 210 */     if (this.m_Content != null)
/*     */     {
/* 212 */       String mmsEncoding = ConfigUtil.getInstance().getCommonConfig().getMmsEncoding();
/* 213 */       if ("base64".equalsIgnoreCase(mmsEncoding)) {
/* 214 */         if ((getContentType().indexOf("imag") >= 0) || (getContentType().indexOf("audio") >= 0)) {
/* 215 */           byte[] m_Content_base64 = basEncoder.encode(this.m_Content).getBytes();
/* 216 */           bos.write(m_Content_base64);
/*     */         } else {
/* 218 */           bos.write(this.m_Content);
/*     */         }
/* 220 */       } else if ("binary".equalsIgnoreCase(mmsEncoding))
/* 221 */         bos.write(this.m_Content);
/*     */     }
/*     */     else
/*     */     {
/* 225 */       logger.warn("encode(): null content, ContentType: " + this.m_ContentType);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void encode(ByteArrayOutputStream bos, int index)
/*     */     throws Exception
/*     */   {
/* 239 */     if (this.m_ContentType == null) {
/* 240 */       this.m_ContentType = new AcewayMMContentType("text/plain");
/*     */     }
/* 242 */     bos.write("Content-Type: ".getBytes());
/* 243 */     bos.write(this.m_ContentType.toString().getBytes());
/* 244 */     bos.write("\r\n".getBytes());
/*     */ 
/* 246 */     Enumeration e = this.m_Headers.getAllHeaderLines();
/* 247 */     while (e.hasMoreElements())
/*     */     {
/* 249 */       String headAtrribute = (String)e.nextElement();
/* 250 */       bos.write(((String)e.nextElement()).getBytes());
/* 251 */       bos.write("\r\n".getBytes());
/*     */     }
/* 253 */     bos.write("\r\n".getBytes());
/*     */ 
/* 255 */     BASE64Encoder basEncoder = new BASE64Encoder();
/*     */ 
/* 257 */     if (this.m_Content != null) {
/* 258 */       String mmsEncoding = ConfigUtil.getInstance().getCommonConfig().getMmsEncoding();
/* 259 */       if ("base64".equalsIgnoreCase(mmsEncoding)) {
/* 260 */         if ((getContentType().indexOf("imag") >= 0) || (getContentType().indexOf("audio") >= 0)) {
/* 261 */           byte[] m_Content_base64 = basEncoder.encode(this.m_Content).getBytes();
/* 262 */           bos.write(m_Content_base64);
/*     */         } else {
/* 264 */           bos.write(this.m_Content);
/*     */         }
/* 266 */       } else if ("binary".equalsIgnoreCase(mmsEncoding))
/* 267 */         bos.write(this.m_Content);
/*     */     }
/*     */     else
/*     */     {
/* 271 */       logger.warn("encode(): null content, ContentType: " + this.m_ContentType);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void encode(ByteArrayOutputStream bos, boolean typeFlag)
/*     */     throws Exception
/*     */   {
/* 284 */     if (this.m_ContentType == null) {
/* 285 */       this.m_ContentType = new AcewayMMContentType("text/plain");
/*     */     }
/*     */ 
/* 288 */     Enumeration e = this.m_Headers.getAllHeaderLines();
/* 289 */     while (e.hasMoreElements())
/*     */     {
/* 291 */       bos.write(((String)e.nextElement()).getBytes());
/* 292 */       bos.write("\r\n".getBytes());
/*     */     }
/* 294 */     bos.write("\r\n".getBytes());
/*     */ 
/* 296 */     BASE64Encoder basEncoder = new BASE64Encoder();
/*     */ 
/* 298 */     if (this.m_Content != null) {
/* 299 */       String mmsEncoding = ConfigUtil.getInstance().getCommonConfig().getMmsEncoding();
/* 300 */       if ("base64".equalsIgnoreCase(mmsEncoding)) {
/* 301 */         if ((getContentType().indexOf("imag") >= 0) || (getContentType().indexOf("audio") >= 0)) {
/* 302 */           byte[] m_Content_base64 = basEncoder.encode(this.m_Content).getBytes();
/* 303 */           bos.write(m_Content_base64);
/*     */         } else {
/* 305 */           bos.write(this.m_Content);
/*     */         }
/* 307 */       } else if ("binary".equalsIgnoreCase(mmsEncoding))
/* 308 */         bos.write(this.m_Content);
/*     */     }
/*     */     else
/*     */     {
/* 312 */       logger.warn("encode(): null content, ContentType: " + this.m_ContentType);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void encode(AcewayMMContentInfo mmContentInfo)
/*     */     throws Exception
/*     */   {
/* 325 */     ByteArrayOutputStream bos = mmContentInfo.getBos();
/* 326 */     int beginSize = bos.size();
/*     */ 
/* 328 */     if (this.m_ContentType == null) {
/* 329 */       this.m_ContentType = new AcewayMMContentType("text/plain");
/*     */     }
/* 331 */     bos.write("Content-Type: ".getBytes());
/* 332 */     bos.write(this.m_ContentType.toString().getBytes());
/* 333 */     bos.write("\r\n".getBytes());
/*     */ 
/* 336 */     Enumeration e = this.m_Headers.getAllHeaderLines();
/* 337 */     while (e.hasMoreElements()) {
/* 338 */       bos.write(((String)e.nextElement()).getBytes());
/* 339 */       bos.write("\r\n".getBytes());
/*     */     }
/* 341 */     bos.write("\r\n".getBytes());
/*     */ 
/* 344 */     if (this.m_Content != null)
/* 345 */       bos.write(this.m_Content);
/*     */     else {
/* 347 */       logger.warn("encode(): null content");
/*     */     }
/* 349 */     int endSize = bos.size();
/*     */ 
/* 352 */     AcewayMMPartInfo mmPartInfo = new AcewayMMPartInfo(this.m_ContentType.toString(), beginSize, endSize - 1);
/* 353 */     mmContentInfo.addPartInfo(mmPartInfo);
/* 354 */     bos.write("\r\n".getBytes());
/*     */   }
/*     */ 
/*     */   public byte[] getContent()
/*     */   {
/* 363 */     return this.m_Content;
/*     */   }
/*     */ 
/*     */   public AcewayMMContentType getMimeContentType()
/*     */   {
/* 373 */     return this.m_ContentType;
/*     */   }
/*     */ 
/*     */   public String getContentType()
/*     */   {
/* 382 */     if (this.m_ContentType == null) {
/* 383 */       return null;
/*     */     }
/* 385 */     return this.m_ContentType.toString();
/*     */   }
/*     */ 
/*     */   public InternetHeaders getHeaders()
/*     */   {
/* 394 */     return this.m_Headers;
/*     */   }
/*     */ 
/*     */   public void clearHeaders() {
/* 398 */     this.m_Headers = new InternetHeaders();
/*     */   }
/*     */ 
/*     */   public String getStringHeaders()
/*     */   {
/* 403 */     StringBuffer sb = new StringBuffer();
/* 404 */     if (this.m_ContentType != null)
/* 405 */       sb.append("Content-Type: " + this.m_ContentType.toString() + "\r\n");
/* 406 */     for (Enumeration e = this.m_Headers.getAllHeaderLines(); e.hasMoreElements(); )
/*     */     {
/* 408 */       String header_str = (String)e.nextElement();
/* 409 */       sb.append(header_str + "\r\n");
/*     */     }
/*     */ 
/* 412 */     sb.append("\r\n");
/* 413 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toInfoString()
/*     */     throws Exception
/*     */   {
/* 421 */     StringBuffer sb = new StringBuffer();
/* 422 */     sb.append(getStringHeaders());
/* 423 */     if (this.m_Content != null) {
/* 424 */       sb.append(new String(this.m_Content) + "\r\n");
/*     */     }
/* 426 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public void setContent(byte[] content)
/*     */   {
/* 435 */     this.m_Content = content;
/*     */   }
/*     */ 
/*     */   public void setContent(String content)
/*     */   {
/* 444 */     this.m_Content = content.getBytes();
/*     */   }
/*     */ 
/*     */   public void setContentType(String cType)
/*     */     throws ParseException
/*     */   {
/* 454 */     this.m_ContentType = new AcewayMMContentType(cType);
/*     */   }
/*     */ 
/*     */   public void setContentType(AcewayMMContentType contentType) {
/* 458 */     this.m_ContentType = contentType;
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.mmcontents.AcewayMMContent
 * JD-Core Version:    0.6.0
 */