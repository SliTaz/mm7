/*     */ package com.zbensoft.mmsmp.common.ra.common.db.entity;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Date;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class ContentInfo
/*     */   implements Serializable
/*     */ {
/*     */   private Integer contentid;
/*     */   private Integer serviceId;
/*     */   private String contentcode;
/*     */   private String contentname;
/*     */   private String contentdesc;
/*     */   private Integer cpid;
/*     */   private Date createdate;
/*     */   private String authorname;
/*     */   private String status;
/*     */   private String contentpath;
/*     */   private String contenttype;
/*     */   private String keywords;
/*     */   private String isrealtime;
/*     */   private String realtimecontenurl;
/*     */   private String rejectdesc;
/*     */   private String deleteapply;
/*     */   private String viewpic;
/*     */   private Date validstarttime;
/*     */   private Date validendtime;
/*     */   private String singer;
/*     */   private String featurestr;
/*     */   private String isadapter;
/*     */   private Integer versionId;
/*     */   private String approver;
/*     */   private String smsText;
/*     */   private String sendFlag;
/*     */   private Map<Integer, ContentInfoAdapter> adapterContentMap;
/*     */   private Map<String, ContentInfoRelation> provinceContent;
/*     */ 
/*     */   public String getSendFlag()
/*     */   {
/*  49 */     return this.sendFlag;
/*     */   }
/*     */ 
/*     */   public Map<Integer, ContentInfoAdapter> getAdapterContentMap()
/*     */   {
/*  54 */     return this.adapterContentMap;
/*     */   }
/*     */ 
/*     */   public void setAdapterContentMap(Map<Integer, ContentInfoAdapter> adapterContentMap)
/*     */   {
/*  60 */     this.adapterContentMap = adapterContentMap;
/*     */   }
/*     */ 
/*     */   public Map<String, ContentInfoRelation> getProvinceContent()
/*     */   {
/*  65 */     return this.provinceContent;
/*     */   }
/*     */ 
/*     */   public void setProvinceContent(Map<String, ContentInfoRelation> provinceContent)
/*     */   {
/*  70 */     this.provinceContent = provinceContent;
/*     */   }
/*     */ 
/*     */   public void setSendFlag(String sendFlag)
/*     */   {
/*  75 */     this.sendFlag = sendFlag;
/*     */   }
/*     */ 
/*     */   public String getSmsText()
/*     */   {
/*  80 */     return this.smsText;
/*     */   }
/*     */ 
/*     */   public void setSmsText(String smsText)
/*     */   {
/*  85 */     this.smsText = smsText;
/*     */   }
/*     */ 
/*     */   public String getApprover()
/*     */   {
/*  90 */     return this.approver;
/*     */   }
/*     */ 
/*     */   public void setApprover(String approver)
/*     */   {
/*  95 */     this.approver = approver;
/*     */   }
/*     */ 
/*     */   public Integer getServiceId()
/*     */   {
/* 100 */     return this.serviceId;
/*     */   }
/*     */ 
/*     */   public void setServiceId(Integer serviceId) {
/* 104 */     this.serviceId = serviceId;
/*     */   }
/*     */ 
/*     */   public Integer getVersionId() {
/* 108 */     return this.versionId;
/*     */   }
/*     */ 
/*     */   public void setVersionId(Integer versionId) {
/* 112 */     this.versionId = versionId;
/*     */   }
/*     */ 
/*     */   public Integer getContentid()
/*     */   {
/* 124 */     return this.contentid;
/*     */   }
/*     */ 
/*     */   public void setContentid(Integer contentid) {
/* 128 */     this.contentid = contentid;
/*     */   }
/*     */ 
/*     */   public String getContentcode() {
/* 132 */     return this.contentcode;
/*     */   }
/*     */ 
/*     */   public void setContentcode(String contentcode) {
/* 136 */     this.contentcode = contentcode;
/*     */   }
/*     */ 
/*     */   public String getContentname() {
/* 140 */     return this.contentname;
/*     */   }
/*     */ 
/*     */   public void setContentname(String contentname) {
/* 144 */     this.contentname = contentname;
/*     */   }
/*     */ 
/*     */   public String getContentdesc() {
/* 148 */     return this.contentdesc;
/*     */   }
/*     */ 
/*     */   public void setContentdesc(String contentdesc) {
/* 152 */     this.contentdesc = contentdesc;
/*     */   }
/*     */ 
/*     */   public Integer getCpid() {
/* 156 */     return this.cpid;
/*     */   }
/*     */ 
/*     */   public void setCpid(Integer cpid) {
/* 160 */     this.cpid = cpid;
/*     */   }
/*     */ 
/*     */   public Date getCreatedate() {
/* 164 */     return this.createdate;
/*     */   }
/*     */ 
/*     */   public void setCreatedate(Date createdate) {
/* 168 */     this.createdate = createdate;
/*     */   }
/*     */ 
/*     */   public String getAuthorname() {
/* 172 */     return this.authorname;
/*     */   }
/*     */ 
/*     */   public void setAuthorname(String authorname) {
/* 176 */     this.authorname = authorname;
/*     */   }
/*     */ 
/*     */   public String getStatus() {
/* 180 */     return this.status;
/*     */   }
/*     */ 
/*     */   public void setStatus(String status) {
/* 184 */     this.status = status;
/*     */   }
/*     */ 
/*     */   public String getContentpath() {
/* 188 */     return this.contentpath;
/*     */   }
/*     */ 
/*     */   public void setContentpath(String contentpath) {
/* 192 */     this.contentpath = contentpath;
/*     */   }
/*     */ 
/*     */   public String getContenttype() {
/* 196 */     return this.contenttype;
/*     */   }
/*     */ 
/*     */   public void setContenttype(String contenttype) {
/* 200 */     this.contenttype = contenttype;
/*     */   }
/*     */ 
/*     */   public String getKeywords() {
/* 204 */     return this.keywords;
/*     */   }
/*     */ 
/*     */   public void setKeywords(String keywords) {
/* 208 */     this.keywords = keywords;
/*     */   }
/*     */ 
/*     */   public String getIsrealtime() {
/* 212 */     return this.isrealtime;
/*     */   }
/*     */ 
/*     */   public void setIsrealtime(String isrealtime) {
/* 216 */     this.isrealtime = isrealtime;
/*     */   }
/*     */ 
/*     */   public String getRealtimecontenurl() {
/* 220 */     return this.realtimecontenurl;
/*     */   }
/*     */ 
/*     */   public void setRealtimecontenurl(String realtimecontenurl) {
/* 224 */     this.realtimecontenurl = realtimecontenurl;
/*     */   }
/*     */ 
/*     */   public String getRejectdesc() {
/* 228 */     return this.rejectdesc;
/*     */   }
/*     */ 
/*     */   public void setRejectdesc(String rejectdesc) {
/* 232 */     this.rejectdesc = rejectdesc;
/*     */   }
/*     */ 
/*     */   public String getDeleteapply()
/*     */   {
/* 237 */     return this.deleteapply;
/*     */   }
/*     */ 
/*     */   public void setDeleteapply(String deleteapply) {
/* 241 */     this.deleteapply = deleteapply;
/*     */   }
/*     */ 
/*     */   public String getViewpic() {
/* 245 */     return this.viewpic;
/*     */   }
/*     */ 
/*     */   public void setViewpic(String viewpic) {
/* 249 */     this.viewpic = viewpic;
/*     */   }
/*     */ 
/*     */   public Date getValidstarttime() {
/* 253 */     return this.validstarttime;
/*     */   }
/*     */ 
/*     */   public void setValidstarttime(Date validstarttime) {
/* 257 */     this.validstarttime = validstarttime;
/*     */   }
/*     */ 
/*     */   public Date getValidendtime() {
/* 261 */     return this.validendtime;
/*     */   }
/*     */ 
/*     */   public void setValidendtime(Date validendtime) {
/* 265 */     this.validendtime = validendtime;
/*     */   }
/*     */ 
/*     */   public String getSinger() {
/* 269 */     return this.singer;
/*     */   }
/*     */ 
/*     */   public void setSinger(String singer) {
/* 273 */     this.singer = singer;
/*     */   }
/*     */ 
/*     */   public String getFeaturestr() {
/* 277 */     return this.featurestr;
/*     */   }
/*     */ 
/*     */   public void setFeaturestr(String featurestr) {
/* 281 */     this.featurestr = featurestr;
/*     */   }
/*     */ 
/*     */   public String getIsadapter() {
/* 285 */     return this.isadapter;
/*     */   }
/*     */ 
/*     */   public void setIsadapter(String isadapter) {
/* 289 */     this.isadapter = isadapter;
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.db.entity.ContentInfo
 * JD-Core Version:    0.6.0
 */