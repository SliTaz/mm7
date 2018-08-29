/*     */ package com.zbensoft.mmsmp.common.ra.common.db.entity;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class Vasservice
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4503117296098452409L;
/*     */   private Integer uniqueid;
/*     */   private String vaspid;
/*     */   private String vasid;
/*     */   private String vassmsid;
/*     */   private String servicecode;
/*     */   private String servicename;
/*     */   private String servicetype;
/*     */   private String feetype;
/*     */   private String status;
/*     */   private String runstatus;
/*     */   private String createdate;
/*     */   private String updatedate;
/*     */   private String servicedesc;
/*     */   private Double orderfee;
/*     */   private Double ondemandfee;
/*     */   private String ondemanddesc;
/*     */   private String feedesc;
/*     */   private String mtcontentmode;
/*     */   private String mtmode;
/*     */   private String smskey;
/*     */   private String treeid;
/*     */   private String ordercode;
/*     */   private String cancelordercode;
/*     */   private String viewpic;
/*     */   private String smallpic;
/*     */   private String defaultflag;
/*     */   private String ondemandcode;
/*     */   private Integer featurestrnum;
/*     */   private String featurestrrange;
/*     */   private Integer source;
/*     */   private Integer cpid;
/*     */   private String producttype;
/*     */   private String proorderid;
/*     */   private String bookManName;
/*     */   private String separateScale;
/*     */   private String composeTimeInterval;
/*     */   private String auditTimeInterval;
/*     */   private String proofTimeInterval;
/*     */   private String approver;
/*     */   private String opinion;
/*     */   private String proAuditTimeInterval;
/*     */   private String productdesc;
/*     */   private Set<Vasservice> products;
/*     */   private Set<Vasservice> packages;
/*     */   private String isPackage;
/*     */   private Integer priority;
/*     */   private Integer dealStatus;
/*     */   private VasserviceReserveInfo vasserviceReserveInfo;
/*     */ 
/*     */   public VasserviceReserveInfo getVasserviceReserveInfo()
/*     */   {
/*  81 */     return this.vasserviceReserveInfo;
/*     */   }
/*     */ 
/*     */   public void setVasserviceReserveInfo(VasserviceReserveInfo vasserviceReserveInfo) {
/*  85 */     this.vasserviceReserveInfo = vasserviceReserveInfo;
/*     */   }
/*     */ 
/*     */   public Integer getDealStatus() {
/*  89 */     return this.dealStatus;
/*     */   }
/*     */ 
/*     */   public void setDealStatus(Integer dealStatus) {
/*  93 */     this.dealStatus = dealStatus;
/*     */   }
/*     */ 
/*     */   public String getIsPackage() {
/*  97 */     return this.isPackage;
/*     */   }
/*     */ 
/*     */   public void setIsPackage(String isPackage) {
/* 101 */     this.isPackage = isPackage;
/*     */   }
/*     */ 
/*     */   public String getProductdesc() {
/* 105 */     return this.productdesc;
/*     */   }
/*     */ 
/*     */   public void setProductdesc(String productdesc) {
/* 109 */     this.productdesc = productdesc;
/*     */   }
/*     */ 
/*     */   public String getApprover() {
/* 113 */     return this.approver;
/*     */   }
/*     */ 
/*     */   public void setApprover(String approver) {
/* 117 */     this.approver = approver;
/*     */   }
/*     */ 
/*     */   public String getOpinion() {
/* 121 */     return this.opinion;
/*     */   }
/*     */ 
/*     */   public void setOpinion(String opinion) {
/* 125 */     this.opinion = opinion;
/*     */   }
/*     */ 
/*     */   public String getProAuditTimeInterval() {
/* 129 */     return this.proAuditTimeInterval;
/*     */   }
/*     */ 
/*     */   public void setProAuditTimeInterval(String proAuditTimeInterval) {
/* 133 */     this.proAuditTimeInterval = proAuditTimeInterval;
/*     */   }
/*     */ 
/*     */   public String getAuditTimeInterval() {
/* 137 */     return this.auditTimeInterval;
/*     */   }
/*     */ 
/*     */   public void setAuditTimeInterval(String auditTimeInterval) {
/* 141 */     this.auditTimeInterval = auditTimeInterval;
/*     */   }
/*     */ 
/*     */   public String getBookManName() {
/* 145 */     return this.bookManName;
/*     */   }
/*     */ 
/*     */   public void setBookManName(String bookManName) {
/* 149 */     this.bookManName = bookManName;
/*     */   }
/*     */ 
/*     */   public String getComposeTimeInterval() {
/* 153 */     return this.composeTimeInterval;
/*     */   }
/*     */ 
/*     */   public void setComposeTimeInterval(String composeTimeInterval) {
/* 157 */     this.composeTimeInterval = composeTimeInterval;
/*     */   }
/*     */ 
/*     */   public String getProofTimeInterval() {
/* 161 */     return this.proofTimeInterval;
/*     */   }
/*     */ 
/*     */   public void setProofTimeInterval(String proofTimeInterval) {
/* 165 */     this.proofTimeInterval = proofTimeInterval;
/*     */   }
/*     */ 
/*     */   public String getSeparateScale() {
/* 169 */     return this.separateScale;
/*     */   }
/*     */ 
/*     */   public void setSeparateScale(String separateScale) {
/* 173 */     this.separateScale = separateScale;
/*     */   }
/*     */ 
/*     */   public Integer getCpid() {
/* 177 */     return this.cpid;
/*     */   }
/*     */ 
/*     */   public void setCpid(Integer cpid) {
/* 181 */     this.cpid = cpid;
/*     */   }
/*     */ 
/*     */   public String getProducttype() {
/* 185 */     return this.producttype;
/*     */   }
/*     */ 
/*     */   public void setProducttype(String producttype) {
/* 189 */     this.producttype = producttype;
/*     */   }
/*     */ 
/*     */   public String getProorderid()
/*     */   {
/* 194 */     return this.proorderid;
/*     */   }
/*     */ 
/*     */   public void setProorderid(String proorderid) {
/* 198 */     this.proorderid = proorderid;
/*     */   }
/*     */ 
/*     */   public Integer getSource() {
/* 202 */     return this.source;
/*     */   }
/*     */ 
/*     */   public void setSource(Integer source) {
/* 206 */     this.source = source;
/*     */   }
/*     */ 
/*     */   public String getFeaturestrrange() {
/* 210 */     return this.featurestrrange;
/*     */   }
/*     */ 
/*     */   public void setFeaturestrrange(String featurestrrange) {
/* 214 */     this.featurestrrange = featurestrrange;
/*     */   }
/*     */ 
/*     */   public Vasservice()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Vasservice(String vaspid, String vasid, String servicecode, String runstatus)
/*     */   {
/* 223 */     this.vaspid = vaspid;
/* 224 */     this.vasid = vasid;
/* 225 */     this.servicecode = servicecode;
/* 226 */     this.runstatus = runstatus;
/*     */   }
/*     */ 
/*     */   public Vasservice(String vaspid, String vasid, String servicecode, String servicename, String servicetype, String feetype, String status, String runstatus, String createdate, String updatedate, String servicedesc, Double orderfee, Double ondemandfee, String ondemanddesc, String feedesc, String mtcontentmode, String mtmode, String smskey, String treeid, String ordercode, String cancelordercode, String viewpic, String smallpic, String defaultflag, String ondemandcode, Integer featurestrnum)
/*     */   {
/* 231 */     this.vaspid = vaspid;
/* 232 */     this.vasid = vasid;
/* 233 */     this.servicecode = servicecode;
/* 234 */     this.servicename = servicename;
/* 235 */     this.servicetype = servicetype;
/* 236 */     this.feetype = feetype;
/* 237 */     this.status = status;
/* 238 */     this.runstatus = runstatus;
/* 239 */     this.createdate = createdate;
/* 240 */     this.updatedate = updatedate;
/* 241 */     this.servicedesc = servicedesc;
/* 242 */     this.orderfee = orderfee;
/* 243 */     this.ondemandfee = ondemandfee;
/* 244 */     this.ondemanddesc = ondemanddesc;
/* 245 */     this.feedesc = feedesc;
/* 246 */     this.mtcontentmode = mtcontentmode;
/* 247 */     this.mtmode = mtmode;
/* 248 */     this.smskey = smskey;
/* 249 */     this.treeid = treeid;
/* 250 */     this.ordercode = ordercode;
/* 251 */     this.cancelordercode = cancelordercode;
/* 252 */     this.viewpic = viewpic;
/* 253 */     this.smallpic = smallpic;
/* 254 */     this.defaultflag = defaultflag;
/*     */ 
/* 256 */     this.ondemandcode = ondemandcode;
/* 257 */     this.featurestrnum = featurestrnum;
/*     */   }
/*     */ 
/*     */   public Integer getUniqueid()
/*     */   {
/* 264 */     return this.uniqueid;
/*     */   }
/*     */ 
/*     */   public void setUniqueid(Integer uniqueid) {
/* 268 */     this.uniqueid = uniqueid;
/*     */   }
/*     */ 
/*     */   public String getVaspid() {
/* 272 */     return this.vaspid;
/*     */   }
/*     */ 
/*     */   public void setVaspid(String vaspid) {
/* 276 */     this.vaspid = vaspid;
/*     */   }
/*     */ 
/*     */   public String getVasid() {
/* 280 */     return this.vasid;
/*     */   }
/*     */ 
/*     */   public void setVasid(String vasid) {
/* 284 */     this.vasid = vasid;
/*     */   }
/*     */ 
/*     */   public String getServicecode() {
/* 288 */     return this.servicecode;
/*     */   }
/*     */ 
/*     */   public void setServicecode(String servicecode) {
/* 292 */     this.servicecode = servicecode;
/*     */   }
/*     */ 
/*     */   public String getServicename() {
/* 296 */     return this.servicename;
/*     */   }
/*     */ 
/*     */   public void setServicename(String servicename) {
/* 300 */     this.servicename = servicename;
/*     */   }
/*     */ 
/*     */   public String getServicetype() {
/* 304 */     return this.servicetype;
/*     */   }
/*     */ 
/*     */   public void setServicetype(String servicetype) {
/* 308 */     this.servicetype = servicetype;
/*     */   }
/*     */ 
/*     */   public String getFeetype() {
/* 312 */     return this.feetype;
/*     */   }
/*     */ 
/*     */   public void setFeetype(String feetype) {
/* 316 */     this.feetype = feetype;
/*     */   }
/*     */ 
/*     */   public String getStatus() {
/* 320 */     return this.status;
/*     */   }
/*     */ 
/*     */   public void setStatus(String status) {
/* 324 */     this.status = status;
/*     */   }
/*     */ 
/*     */   public String getRunstatus() {
/* 328 */     return this.runstatus;
/*     */   }
/*     */ 
/*     */   public void setRunstatus(String runstatus) {
/* 332 */     this.runstatus = runstatus;
/*     */   }
/*     */ 
/*     */   public String getCreatedate() {
/* 336 */     return this.createdate;
/*     */   }
/*     */ 
/*     */   public void setCreatedate(String createdate) {
/* 340 */     this.createdate = createdate;
/*     */   }
/*     */ 
/*     */   public String getUpdatedate() {
/* 344 */     return this.updatedate;
/*     */   }
/*     */ 
/*     */   public void setUpdatedate(String updatedate) {
/* 348 */     this.updatedate = updatedate;
/*     */   }
/*     */ 
/*     */   public String getServicedesc() {
/* 352 */     return this.servicedesc;
/*     */   }
/*     */ 
/*     */   public void setServicedesc(String servicedesc) {
/* 356 */     this.servicedesc = servicedesc;
/*     */   }
/*     */ 
/*     */   public Double getOrderfee() {
/* 360 */     return this.orderfee;
/*     */   }
/*     */ 
/*     */   public void setOrderfee(Double orderfee) {
/* 364 */     this.orderfee = orderfee;
/*     */   }
/*     */ 
/*     */   public Double getOndemandfee() {
/* 368 */     return this.ondemandfee;
/*     */   }
/*     */ 
/*     */   public void setOndemandfee(Double ondemandfee) {
/* 372 */     this.ondemandfee = ondemandfee;
/*     */   }
/*     */ 
/*     */   public String getOndemanddesc() {
/* 376 */     return this.ondemanddesc;
/*     */   }
/*     */ 
/*     */   public void setOndemanddesc(String ondemanddesc) {
/* 380 */     this.ondemanddesc = ondemanddesc;
/*     */   }
/*     */ 
/*     */   public String getFeedesc() {
/* 384 */     return this.feedesc;
/*     */   }
/*     */ 
/*     */   public void setFeedesc(String feedesc) {
/* 388 */     this.feedesc = feedesc;
/*     */   }
/*     */ 
/*     */   public String getMtcontentmode() {
/* 392 */     return this.mtcontentmode;
/*     */   }
/*     */ 
/*     */   public void setMtcontentmode(String mtcontentmode) {
/* 396 */     this.mtcontentmode = mtcontentmode;
/*     */   }
/*     */ 
/*     */   public String getMtmode() {
/* 400 */     return this.mtmode;
/*     */   }
/*     */ 
/*     */   public void setMtmode(String mtmode) {
/* 404 */     this.mtmode = mtmode;
/*     */   }
/*     */ 
/*     */   public String getSmskey() {
/* 408 */     return this.smskey;
/*     */   }
/*     */ 
/*     */   public void setSmskey(String smskey) {
/* 412 */     this.smskey = smskey;
/*     */   }
/*     */ 
/*     */   public String getTreeid() {
/* 416 */     return this.treeid;
/*     */   }
/*     */ 
/*     */   public void setTreeid(String treeid) {
/* 420 */     this.treeid = treeid;
/*     */   }
/*     */ 
/*     */   public String getOrdercode() {
/* 424 */     return this.ordercode;
/*     */   }
/*     */ 
/*     */   public void setOrdercode(String ordercode) {
/* 428 */     this.ordercode = ordercode;
/*     */   }
/*     */ 
/*     */   public String getCancelordercode() {
/* 432 */     return this.cancelordercode;
/*     */   }
/*     */ 
/*     */   public void setCancelordercode(String cancelordercode) {
/* 436 */     this.cancelordercode = cancelordercode;
/*     */   }
/*     */ 
/*     */   public String getViewpic() {
/* 440 */     return this.viewpic;
/*     */   }
/*     */ 
/*     */   public void setViewpic(String viewpic) {
/* 444 */     this.viewpic = viewpic;
/*     */   }
/*     */ 
/*     */   public String getSmallpic() {
/* 448 */     return this.smallpic;
/*     */   }
/*     */ 
/*     */   public void setSmallpic(String smallpic) {
/* 452 */     this.smallpic = smallpic;
/*     */   }
/*     */ 
/*     */   public String getDefaultflag() {
/* 456 */     return this.defaultflag;
/*     */   }
/*     */ 
/*     */   public void setDefaultflag(String defaultflag) {
/* 460 */     this.defaultflag = defaultflag;
/*     */   }
/*     */ 
/*     */   public Integer getFeaturestrnum() {
/* 464 */     return this.featurestrnum;
/*     */   }
/*     */ 
/*     */   public void setFeaturestrnum(Integer featurestrnum) {
/* 468 */     this.featurestrnum = featurestrnum;
/*     */   }
/*     */ 
/*     */   public String getOndemandcode() {
/* 472 */     return this.ondemandcode;
/*     */   }
/*     */ 
/*     */   public void setOndemandcode(String ondemandcode) {
/* 476 */     this.ondemandcode = ondemandcode;
/*     */   }
/*     */ 
/*     */   public String getVassmsid() {
/* 480 */     return this.vassmsid;
/*     */   }
/*     */ 
/*     */   public void setVassmsid(String vassmsid) {
/* 484 */     this.vassmsid = vassmsid;
/*     */   }
/*     */ 
/*     */   public Set<Vasservice> getPackages() {
/* 488 */     return this.packages;
/*     */   }
/*     */ 
/*     */   public void setPackages(Set<Vasservice> packages) {
/* 492 */     this.packages = packages;
/*     */   }
/*     */ 
/*     */   public Set<Vasservice> getProducts() {
/* 496 */     return this.products;
/*     */   }
/*     */ 
/*     */   public void setProducts(Set<Vasservice> products) {
/* 500 */     this.products = products;
/*     */   }
/*     */ 
/*     */   public Integer getPriority() {
/* 504 */     return this.priority;
/*     */   }
/*     */ 
/*     */   public void setPriority(Integer priority) {
/* 508 */     this.priority = priority;
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.db.entity.Vasservice
 * JD-Core Version:    0.6.0
 */