/*     */ package com.zbensoft.mmsmp.common.ra.common.message;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.StringTokenizer;
/*     */ import java.util.UUID;
/*     */ 
/*     */ public abstract class AbstractMessage
/*     */   implements IMessage
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  13 */   private Map<ExtendMessageField, String> extendFields = new HashMap();
/*  14 */   protected int source = 4;
/*     */   private Integer contentid;
/*     */   private int priority;
/*     */   private int sourceType;
/*  22 */   private boolean resend = false;
/*     */   private int operatorsType;
/*     */   private String provinceCode;
/*     */   private String linkId;
/*     */   private String globalMessageid;
/*     */   private String globalReportUrl;
/*     */   private long globalMessageTime;
/*     */   private long globalCreateTime;
/*     */   private int globalStep;
/*     */   private String chargetNumber;
/*     */ 
/*     */   public String getChargetNumber()
/*     */   {
/*  39 */     return this.chargetNumber;
/*     */   }
/*     */ 
/*     */   public void setChargetNumber(String chargetNumber)
/*     */   {
/*  44 */     this.chargetNumber = chargetNumber;
/*     */   }
/*     */ 
/*     */   public static String generateUUID(String source)
/*     */   {
/*  50 */     String id = source == null ? "" : source.trim();
/*     */ 
/*  52 */     UUID uuid = UUID.randomUUID();
/*  53 */     id = id + uuid.toString().replaceAll("-", "");
/*     */ 
/*  55 */     return id.toUpperCase();
/*     */   }
/*     */ 
/*     */   public String getGlobalReportUrl()
/*     */   {
/*  60 */     return this.globalReportUrl;
/*     */   }
/*     */ 
/*     */   public void setGlobalReportUrl(String globalReportUrl) {
/*  64 */     this.globalReportUrl = globalReportUrl;
/*     */   }
/*     */ 
/*     */   public int getGlobalStep() {
/*  68 */     return this.globalStep;
/*     */   }
/*     */ 
/*     */   public void setGlobalStep(int globalStep) {
/*  72 */     this.globalStep = globalStep;
/*     */   }
/*     */ 
/*     */   public long getGlobalCreateTime() {
/*  76 */     return this.globalCreateTime;
/*     */   }
/*     */ 
/*     */   public void setGlobalCreateTime(long globalCreateTime) {
/*  80 */     this.globalCreateTime = globalCreateTime;
/*     */   }
/*     */ 
/*     */   public String getGlobalMessageid() {
/*  84 */     return this.globalMessageid;
/*     */   }
/*     */ 
/*     */   public void setGlobalMessageid(String globalMessageid) {
/*  88 */     this.globalMessageid = globalMessageid;
/*  89 */     setGlobalCreateTime(System.currentTimeMillis());
/*     */   }
/*     */ 
/*     */   public long getGlobalMessageTime() {
/*  93 */     return this.globalMessageTime;
/*     */   }
/*     */ 
/*     */   public void setGlobalMessageTime(long globalMessageTime) {
/*  97 */     this.globalMessageTime = globalMessageTime;
/*     */   }
/*     */ 
/*     */   public String getLinkId() {
/* 101 */     return this.linkId;
/*     */   }
/*     */ 
/*     */   public void setLinkId(String linkId) {
/* 105 */     this.linkId = linkId;
/*     */   }
/*     */ 
/*     */   public int getPriority() {
/* 109 */     return this.priority;
/*     */   }
/*     */ 
/*     */   public void setPriority(int priority) {
/* 113 */     this.priority = priority;
/*     */   }
/*     */ 
/*     */   public Integer getContentid() {
/* 117 */     return this.contentid;
/*     */   }
/*     */ 
/*     */   public void setContentid(Integer contentid) {
/* 121 */     this.contentid = contentid;
/*     */   }
/*     */ 
/*     */   public int getSource() {
/* 125 */     return this.source;
/*     */   }
/*     */ 
/*     */   public void setSource(int source) {
/* 129 */     this.source = source;
/*     */   }
/*     */ 
/*     */   public void setExtendFieldValue(ExtendMessageField field, String value)
/*     */   {
/* 139 */     this.extendFields.put(field, value);
/*     */   }
/*     */ 
/*     */   public String getExtendFieldValue(ExtendMessageField id)
/*     */   {
/* 149 */     return (String)this.extendFields.get(id);
/*     */   }
/*     */ 
/*     */   public Map<ExtendMessageField, String> getExtendFields()
/*     */   {
/* 158 */     return this.extendFields;
/*     */   }
/*     */ 
/*     */   public void copyExtendFields(AbstractMessage source)
/*     */   {
/* 167 */     this.extendFields.putAll(source.getExtendFields());
/*     */   }
/*     */ 
/*     */   public String decodeExtendFields(String message)
/*     */     throws Exception
/*     */   {
/* 178 */     int pos = message.indexOf("\n\n");
/* 179 */     this.extendFields.clear();
/*     */ 
/* 181 */     if (pos > 0)
/*     */     {
/* 183 */       String customMessage = message.substring(0, pos);
/* 184 */       message = message.substring(pos + 2);
/* 185 */       String token = null;
/* 186 */       String name = null;
/* 187 */       String value = null;
/* 188 */       ExtendMessageField field = null;
/*     */ 
/* 190 */       for (StringTokenizer tokens = new StringTokenizer(customMessage, "\n"); tokens.hasMoreTokens(); )
/*     */       {
/* 192 */         token = tokens.nextToken();
/* 193 */         pos = token.indexOf(':');
/*     */ 
/* 195 */         if (pos < 0)
/*     */           continue;
/* 197 */         name = token.substring(0, pos);
/* 198 */         value = token.substring(pos + 1);
/*     */ 
/* 200 */         field = ExtendMessageField.parseString(name);
/*     */ 
/* 202 */         if (field == null) {
/*     */           continue;
/*     */         }
/* 205 */         this.extendFields.put(field, value);
/*     */       }
/*     */ 
/* 208 */       return message;
/*     */     }
/*     */ 
/* 211 */     return message;
/*     */   }
/*     */ 
/*     */   public StringBuffer encodeExtendFields()
/*     */   {
/* 220 */     StringBuffer result = new StringBuffer();
/* 221 */     ExtendMessageField field = null;
/*     */ 
/* 223 */     for (Iterator iter = this.extendFields.keySet().iterator(); iter.hasNext(); )
/*     */     {
/* 225 */       field = (ExtendMessageField)iter.next();
/* 226 */       result.append(field.toString() + ":" + (String)this.extendFields.get(field) + "\n");
/*     */     }
/*     */ 
/* 229 */     if (result.length() != 0) {
/* 230 */       result.append("\n");
/*     */     }
/* 232 */     return result;
/*     */   }
/*     */ 
/*     */   public int getSourceType() {
/* 236 */     return this.sourceType;
/*     */   }
/*     */ 
/*     */   public void setSourceType(int sourceType) {
/* 240 */     this.sourceType = sourceType;
/*     */   }
/*     */ 
/*     */   public String getProvinceCode() {
/* 244 */     return this.provinceCode;
/*     */   }
/*     */ 
/*     */   public void setProvinceCode(String provinceCode) {
/* 248 */     this.provinceCode = provinceCode;
/*     */   }
/*     */ 
/*     */   public boolean isResend() {
/* 252 */     return this.resend;
/*     */   }
/*     */ 
/*     */   public void setResend(boolean resend) {
/* 256 */     this.resend = resend;
/*     */   }
/*     */ 
/*     */   public int getOperatorsType() {
/* 260 */     return this.operatorsType;
/*     */   }
/*     */ 
/*     */   public void setOperatorsType(int operatorsType) {
/* 264 */     this.operatorsType = operatorsType;
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.message.AbstractMessage
 * JD-Core Version:    0.6.0
 */