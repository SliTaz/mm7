/*     */ package com.zbensoft.mmsmp.common.ra.common.db.entity;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class VasserviceReserveInfo
/*     */   implements Serializable
/*     */ {
/*     */   private String recordsequenceid;
/*     */   private String specProductid;
/*     */   private String spProductid;
/*     */   private Integer confirm;
/*     */   private Integer presenter;
/*     */   private String confirmprompt;
/*     */   private String successprompt;
/*     */   private String cancelprompt;
/*     */   private String popularizestart;
/*     */   private String popularizestop;
/*     */   private Integer freeuseflag;
/*     */   private Integer freeusetime;
/*     */   private String billingid;
/*     */   private String discountDes;
/*     */   private String discountid;
/*     */   private Integer needconfm;
/*     */   private Integer maxusetimes;
/*     */   private Integer maxusetime;
/*     */   private Integer sendnum;
/*     */   private Integer ordercmdmatch;
/*     */   private String orderacc;
/*     */   private Integer orderaccmatch;
/*     */   private Integer cancelcmdmatch;
/*     */   private String cancelacc;
/*     */   private Integer cancelaccmatch;
/*     */   private Integer requestcmdmatch;
/*     */   private String requestacc;
/*     */   private Integer requestaccmatch;
/*     */   private String effdate;
/*     */   private String expdate;
/*     */   private Integer vacsub;
/*     */   private Integer notifytype;
/*     */   private String productcity;
/*     */   private String productperiodgrade;
/*     */   private String productservicegrade;
/*     */   private Integer productcredit;
/*     */   private Integer productstatus;
/*     */   private String productid;
/*     */   private Integer productExtendId;
/*     */   private String productExpandCode;
/*     */   private String reportMessageUrl;
/*     */ 
/*     */   public VasserviceReserveInfo()
/*     */   {
/*     */   }
/*     */ 
/*     */   public VasserviceReserveInfo(String recordsequenceid)
/*     */   {
/*  63 */     this.recordsequenceid = recordsequenceid;
/*     */   }
/*     */ 
/*     */   public VasserviceReserveInfo(String recordsequenceid, String specProductid, String spProductid, Integer confirm, Integer presenter, String confirmprompt, String successprompt, String cancelprompt, String popularizestart, String popularizestop, Integer freeuseflag, Integer freeusetime, String billingid, String discountDes, String discountid, Integer needconfm, Integer maxusetimes, Integer maxusetime, Integer sendnum, Integer ordercmdmatch, String orderacc, Integer orderaccmatch, Integer cancelcmdmatch, String cancelacc, Integer cancelaccmatch, Integer requestcmdmatch, String requestacc, Integer requestaccmatch, String effdate, String expdate, Integer vacsub, Integer notifytype, String productcity, String productperiodgrade, String productservicegrade, Integer productcredit, Integer productstatus, String productid, Integer productExtendId, String productExpandCode, String reportMessageUrl)
/*     */   {
/*  74 */     this.recordsequenceid = recordsequenceid;
/*  75 */     this.specProductid = specProductid;
/*  76 */     this.spProductid = spProductid;
/*  77 */     this.confirm = confirm;
/*  78 */     this.presenter = presenter;
/*  79 */     this.confirmprompt = confirmprompt;
/*  80 */     this.successprompt = successprompt;
/*  81 */     this.cancelprompt = cancelprompt;
/*  82 */     this.popularizestart = popularizestart;
/*  83 */     this.popularizestop = popularizestop;
/*  84 */     this.freeuseflag = freeuseflag;
/*  85 */     this.freeusetime = freeusetime;
/*  86 */     this.billingid = billingid;
/*  87 */     this.discountDes = discountDes;
/*  88 */     this.discountid = discountid;
/*  89 */     this.needconfm = needconfm;
/*  90 */     this.maxusetimes = maxusetimes;
/*  91 */     this.maxusetime = maxusetime;
/*  92 */     this.sendnum = sendnum;
/*  93 */     this.ordercmdmatch = ordercmdmatch;
/*  94 */     this.orderacc = orderacc;
/*  95 */     this.orderaccmatch = orderaccmatch;
/*  96 */     this.cancelcmdmatch = cancelcmdmatch;
/*  97 */     this.cancelacc = cancelacc;
/*  98 */     this.cancelaccmatch = cancelaccmatch;
/*  99 */     this.requestcmdmatch = requestcmdmatch;
/* 100 */     this.requestacc = requestacc;
/* 101 */     this.requestaccmatch = requestaccmatch;
/* 102 */     this.effdate = effdate;
/* 103 */     this.expdate = expdate;
/* 104 */     this.vacsub = vacsub;
/* 105 */     this.notifytype = notifytype;
/* 106 */     this.productcity = productcity;
/* 107 */     this.productperiodgrade = productperiodgrade;
/* 108 */     this.productservicegrade = productservicegrade;
/* 109 */     this.productcredit = productcredit;
/* 110 */     this.productstatus = productstatus;
/* 111 */     this.productid = productid;
/* 112 */     this.productExtendId = productExtendId;
/* 113 */     this.productExpandCode = productExpandCode;
/* 114 */     this.reportMessageUrl = reportMessageUrl;
/*     */   }
/*     */ 
/*     */   public String getRecordsequenceid()
/*     */   {
/* 120 */     return this.recordsequenceid;
/*     */   }
/*     */ 
/*     */   public void setRecordsequenceid(String recordsequenceid) {
/* 124 */     this.recordsequenceid = recordsequenceid;
/*     */   }
/*     */ 
/*     */   public String getSpecProductid() {
/* 128 */     return this.specProductid;
/*     */   }
/*     */ 
/*     */   public void setSpecProductid(String specProductid) {
/* 132 */     this.specProductid = specProductid;
/*     */   }
/*     */ 
/*     */   public String getSpProductid() {
/* 136 */     return this.spProductid;
/*     */   }
/*     */ 
/*     */   public void setSpProductid(String spProductid) {
/* 140 */     this.spProductid = spProductid;
/*     */   }
/*     */ 
/*     */   public Integer getConfirm() {
/* 144 */     return this.confirm;
/*     */   }
/*     */ 
/*     */   public void setConfirm(Integer confirm) {
/* 148 */     this.confirm = confirm;
/*     */   }
/*     */ 
/*     */   public Integer getPresenter() {
/* 152 */     return this.presenter;
/*     */   }
/*     */ 
/*     */   public void setPresenter(Integer presenter) {
/* 156 */     this.presenter = presenter;
/*     */   }
/*     */ 
/*     */   public String getConfirmprompt() {
/* 160 */     return this.confirmprompt;
/*     */   }
/*     */ 
/*     */   public void setConfirmprompt(String confirmprompt) {
/* 164 */     this.confirmprompt = confirmprompt;
/*     */   }
/*     */ 
/*     */   public String getSuccessprompt() {
/* 168 */     return this.successprompt;
/*     */   }
/*     */ 
/*     */   public void setSuccessprompt(String successprompt) {
/* 172 */     this.successprompt = successprompt;
/*     */   }
/*     */ 
/*     */   public String getCancelprompt() {
/* 176 */     return this.cancelprompt;
/*     */   }
/*     */ 
/*     */   public void setCancelprompt(String cancelprompt) {
/* 180 */     this.cancelprompt = cancelprompt;
/*     */   }
/*     */ 
/*     */   public String getPopularizestart() {
/* 184 */     return this.popularizestart;
/*     */   }
/*     */ 
/*     */   public void setPopularizestart(String popularizestart) {
/* 188 */     this.popularizestart = popularizestart;
/*     */   }
/*     */ 
/*     */   public String getPopularizestop() {
/* 192 */     return this.popularizestop;
/*     */   }
/*     */ 
/*     */   public void setPopularizestop(String popularizestop) {
/* 196 */     this.popularizestop = popularizestop;
/*     */   }
/*     */ 
/*     */   public Integer getFreeuseflag() {
/* 200 */     return this.freeuseflag;
/*     */   }
/*     */ 
/*     */   public void setFreeuseflag(Integer freeuseflag) {
/* 204 */     this.freeuseflag = freeuseflag;
/*     */   }
/*     */ 
/*     */   public Integer getFreeusetime() {
/* 208 */     return this.freeusetime;
/*     */   }
/*     */ 
/*     */   public void setFreeusetime(Integer freeusetime) {
/* 212 */     this.freeusetime = freeusetime;
/*     */   }
/*     */ 
/*     */   public String getBillingid() {
/* 216 */     return this.billingid;
/*     */   }
/*     */ 
/*     */   public void setBillingid(String billingid) {
/* 220 */     this.billingid = billingid;
/*     */   }
/*     */ 
/*     */   public String getDiscountDes() {
/* 224 */     return this.discountDes;
/*     */   }
/*     */ 
/*     */   public void setDiscountDes(String discountDes) {
/* 228 */     this.discountDes = discountDes;
/*     */   }
/*     */ 
/*     */   public String getDiscountid() {
/* 232 */     return this.discountid;
/*     */   }
/*     */ 
/*     */   public void setDiscountid(String discountid) {
/* 236 */     this.discountid = discountid;
/*     */   }
/*     */ 
/*     */   public Integer getNeedconfm() {
/* 240 */     return this.needconfm;
/*     */   }
/*     */ 
/*     */   public void setNeedconfm(Integer needconfm) {
/* 244 */     this.needconfm = needconfm;
/*     */   }
/*     */ 
/*     */   public Integer getMaxusetimes() {
/* 248 */     return this.maxusetimes;
/*     */   }
/*     */ 
/*     */   public void setMaxusetimes(Integer maxusetimes) {
/* 252 */     this.maxusetimes = maxusetimes;
/*     */   }
/*     */ 
/*     */   public Integer getMaxusetime() {
/* 256 */     return this.maxusetime;
/*     */   }
/*     */ 
/*     */   public void setMaxusetime(Integer maxusetime) {
/* 260 */     this.maxusetime = maxusetime;
/*     */   }
/*     */ 
/*     */   public Integer getSendnum() {
/* 264 */     return this.sendnum;
/*     */   }
/*     */ 
/*     */   public void setSendnum(Integer sendnum) {
/* 268 */     this.sendnum = sendnum;
/*     */   }
/*     */ 
/*     */   public Integer getOrdercmdmatch() {
/* 272 */     return this.ordercmdmatch;
/*     */   }
/*     */ 
/*     */   public void setOrdercmdmatch(Integer ordercmdmatch) {
/* 276 */     this.ordercmdmatch = ordercmdmatch;
/*     */   }
/*     */ 
/*     */   public String getOrderacc() {
/* 280 */     return this.orderacc;
/*     */   }
/*     */ 
/*     */   public void setOrderacc(String orderacc) {
/* 284 */     this.orderacc = orderacc;
/*     */   }
/*     */ 
/*     */   public Integer getOrderaccmatch() {
/* 288 */     return this.orderaccmatch;
/*     */   }
/*     */ 
/*     */   public void setOrderaccmatch(Integer orderaccmatch) {
/* 292 */     this.orderaccmatch = orderaccmatch;
/*     */   }
/*     */ 
/*     */   public Integer getCancelcmdmatch() {
/* 296 */     return this.cancelcmdmatch;
/*     */   }
/*     */ 
/*     */   public void setCancelcmdmatch(Integer cancelcmdmatch) {
/* 300 */     this.cancelcmdmatch = cancelcmdmatch;
/*     */   }
/*     */ 
/*     */   public String getCancelacc() {
/* 304 */     return this.cancelacc;
/*     */   }
/*     */ 
/*     */   public void setCancelacc(String cancelacc) {
/* 308 */     this.cancelacc = cancelacc;
/*     */   }
/*     */ 
/*     */   public Integer getCancelaccmatch() {
/* 312 */     return this.cancelaccmatch;
/*     */   }
/*     */ 
/*     */   public void setCancelaccmatch(Integer cancelaccmatch) {
/* 316 */     this.cancelaccmatch = cancelaccmatch;
/*     */   }
/*     */ 
/*     */   public Integer getRequestcmdmatch() {
/* 320 */     return this.requestcmdmatch;
/*     */   }
/*     */ 
/*     */   public void setRequestcmdmatch(Integer requestcmdmatch) {
/* 324 */     this.requestcmdmatch = requestcmdmatch;
/*     */   }
/*     */ 
/*     */   public String getRequestacc() {
/* 328 */     return this.requestacc;
/*     */   }
/*     */ 
/*     */   public void setRequestacc(String requestacc) {
/* 332 */     this.requestacc = requestacc;
/*     */   }
/*     */ 
/*     */   public Integer getRequestaccmatch() {
/* 336 */     return this.requestaccmatch;
/*     */   }
/*     */ 
/*     */   public void setRequestaccmatch(Integer requestaccmatch) {
/* 340 */     this.requestaccmatch = requestaccmatch;
/*     */   }
/*     */ 
/*     */   public String getEffdate() {
/* 344 */     return this.effdate;
/*     */   }
/*     */ 
/*     */   public void setEffdate(String effdate) {
/* 348 */     this.effdate = effdate;
/*     */   }
/*     */ 
/*     */   public String getExpdate() {
/* 352 */     return this.expdate;
/*     */   }
/*     */ 
/*     */   public void setExpdate(String expdate) {
/* 356 */     this.expdate = expdate;
/*     */   }
/*     */ 
/*     */   public Integer getVacsub() {
/* 360 */     return this.vacsub;
/*     */   }
/*     */ 
/*     */   public void setVacsub(Integer vacsub) {
/* 364 */     this.vacsub = vacsub;
/*     */   }
/*     */ 
/*     */   public Integer getNotifytype() {
/* 368 */     return this.notifytype;
/*     */   }
/*     */ 
/*     */   public void setNotifytype(Integer notifytype) {
/* 372 */     this.notifytype = notifytype;
/*     */   }
/*     */ 
/*     */   public String getProductcity() {
/* 376 */     return this.productcity;
/*     */   }
/*     */ 
/*     */   public void setProductcity(String productcity) {
/* 380 */     this.productcity = productcity;
/*     */   }
/*     */ 
/*     */   public String getProductperiodgrade() {
/* 384 */     return this.productperiodgrade;
/*     */   }
/*     */ 
/*     */   public void setProductperiodgrade(String productperiodgrade) {
/* 388 */     this.productperiodgrade = productperiodgrade;
/*     */   }
/*     */ 
/*     */   public String getProductservicegrade() {
/* 392 */     return this.productservicegrade;
/*     */   }
/*     */ 
/*     */   public void setProductservicegrade(String productservicegrade) {
/* 396 */     this.productservicegrade = productservicegrade;
/*     */   }
/*     */ 
/*     */   public Integer getProductcredit() {
/* 400 */     return this.productcredit;
/*     */   }
/*     */ 
/*     */   public void setProductcredit(Integer productcredit) {
/* 404 */     this.productcredit = productcredit;
/*     */   }
/*     */ 
/*     */   public Integer getProductstatus() {
/* 408 */     return this.productstatus;
/*     */   }
/*     */ 
/*     */   public void setProductstatus(Integer productstatus) {
/* 412 */     this.productstatus = productstatus;
/*     */   }
/*     */ 
/*     */   public String getProductid() {
/* 416 */     return this.productid;
/*     */   }
/*     */ 
/*     */   public void setProductid(String productid) {
/* 420 */     this.productid = productid;
/*     */   }
/*     */ 
/*     */   public Integer getProductExtendId() {
/* 424 */     return this.productExtendId;
/*     */   }
/*     */ 
/*     */   public void setProductExtendId(Integer productExtendId) {
/* 428 */     this.productExtendId = productExtendId;
/*     */   }
/*     */ 
/*     */   public String getProductExpandCode() {
/* 432 */     return this.productExpandCode;
/*     */   }
/*     */ 
/*     */   public void setProductExpandCode(String productExpandCode) {
/* 436 */     this.productExpandCode = productExpandCode;
/*     */   }
/*     */ 
/*     */   public String getReportMessageUrl() {
/* 440 */     return this.reportMessageUrl;
/*     */   }
/*     */ 
/*     */   public void setReportMessageUrl(String reportMessageUrl) {
/* 444 */     this.reportMessageUrl = reportMessageUrl;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object other) {
/* 448 */     if (this == other)
/* 449 */       return true;
/* 450 */     if (other == null)
/* 451 */       return false;
/* 452 */     if (!(other instanceof VasserviceReserveInfo))
/* 453 */       return false;
/* 454 */     VasserviceReserveInfo castOther = (VasserviceReserveInfo)other;
/*     */ 
/* 537 */     return ((getRecordsequenceid() == castOther.getRecordsequenceid()) || ((getRecordsequenceid() != null) && 
/* 457 */       (castOther.getRecordsequenceid() != null) && (getRecordsequenceid().equals(castOther.getRecordsequenceid())))) && 
/* 458 */       ((getSpecProductid() == castOther.getSpecProductid()) || ((getSpecProductid() != null) && (castOther.getSpecProductid() != null) && 
/* 459 */       (getSpecProductid().equals(castOther.getSpecProductid())))) && 
/* 460 */       ((getSpProductid() == castOther.getSpProductid()) || ((getSpProductid() != null) && (castOther.getSpProductid() != null) && 
/* 461 */       (getSpProductid().equals(castOther.getSpProductid())))) && 
/* 462 */       ((getConfirm() == castOther.getConfirm()) || ((getConfirm() != null) && (castOther.getConfirm() != null) && 
/* 463 */       (getConfirm()
/* 463 */       .equals(castOther.getConfirm())))) && 
/* 464 */       ((getPresenter() == castOther.getPresenter()) || ((getPresenter() != null) && (castOther.getPresenter() != null) && 
/* 465 */       (getPresenter().equals(castOther.getPresenter())))) && 
/* 466 */       ((getConfirmprompt() == castOther.getConfirmprompt()) || ((getConfirmprompt() != null) && (castOther.getConfirmprompt() != null) && 
/* 467 */       (getConfirmprompt().equals(castOther.getConfirmprompt())))) && 
/* 468 */       ((getSuccessprompt() == castOther.getSuccessprompt()) || ((getSuccessprompt() != null) && (castOther.getSuccessprompt() != null) && 
/* 469 */       (getSuccessprompt().equals(castOther.getSuccessprompt())))) && 
/* 470 */       ((getCancelprompt() == castOther.getCancelprompt()) || ((getCancelprompt() != null) && (castOther.getCancelprompt() != null) && 
/* 471 */       (getCancelprompt().equals(castOther.getCancelprompt())))) && 
/* 472 */       ((getPopularizestart() == castOther.getPopularizestart()) || ((getPopularizestart() != null) && 
/* 473 */       (castOther.getPopularizestart() != null) && (getPopularizestart().equals(castOther.getPopularizestart())))) && 
/* 474 */       ((getPopularizestop() == castOther.getPopularizestop()) || ((getPopularizestop() != null) && (castOther.getPopularizestop() != null) && 
/* 475 */       (getPopularizestop().equals(castOther.getPopularizestop())))) && 
/* 476 */       ((getFreeuseflag() == castOther.getFreeuseflag()) || ((getFreeuseflag() != null) && (castOther.getFreeuseflag() != null) && 
/* 477 */       (getFreeuseflag().equals(castOther.getFreeuseflag())))) && 
/* 478 */       ((getFreeusetime() == castOther.getFreeusetime()) || ((getFreeusetime() != null) && (castOther.getFreeusetime() != null) && 
/* 479 */       (getFreeusetime().equals(castOther.getFreeusetime())))) && 
/* 480 */       ((getBillingid() == castOther.getBillingid()) || ((getBillingid() != null) && (castOther.getBillingid() != null) && 
/* 481 */       (getBillingid().equals(castOther.getBillingid())))) && 
/* 482 */       ((getDiscountDes() == castOther.getDiscountDes()) || ((getDiscountDes() != null) && (castOther.getDiscountDes() != null) && 
/* 483 */       (getDiscountDes().equals(castOther.getDiscountDes())))) && 
/* 484 */       ((getDiscountid() == castOther.getDiscountid()) || ((getDiscountid() != null) && (castOther.getDiscountid() != null) && 
/* 485 */       (getDiscountid().equals(castOther.getDiscountid())))) && 
/* 486 */       ((getNeedconfm() == castOther.getNeedconfm()) || ((getNeedconfm() != null) && (castOther.getNeedconfm() != null) && 
/* 487 */       (getNeedconfm().equals(castOther.getNeedconfm())))) && 
/* 488 */       ((getMaxusetimes() == castOther.getMaxusetimes()) || ((getMaxusetimes() != null) && (castOther.getMaxusetimes() != null) && 
/* 489 */       (getMaxusetimes().equals(castOther.getMaxusetimes())))) && 
/* 490 */       ((getMaxusetime() == castOther.getMaxusetime()) || ((getMaxusetime() != null) && (castOther.getMaxusetime() != null) && 
/* 491 */       (getMaxusetime().equals(castOther.getMaxusetime())))) && 
/* 492 */       ((getSendnum() == castOther.getSendnum()) || ((getSendnum() != null) && (castOther.getSendnum() != null) && 
/* 493 */       (getSendnum()
/* 493 */       .equals(castOther.getSendnum())))) && 
/* 494 */       ((getOrdercmdmatch() == castOther.getOrdercmdmatch()) || ((getOrdercmdmatch() != null) && (castOther.getOrdercmdmatch() != null) && 
/* 495 */       (getOrdercmdmatch().equals(castOther.getOrdercmdmatch())))) && 
/* 496 */       ((getOrderacc() == castOther.getOrderacc()) || ((getOrderacc() != null) && (castOther.getOrderacc() != null) && 
/* 497 */       (getOrderacc().equals(castOther.getOrderacc())))) && 
/* 498 */       ((getOrderaccmatch() == castOther.getOrderaccmatch()) || ((getOrderaccmatch() != null) && (castOther.getOrderaccmatch() != null) && 
/* 499 */       (getOrderaccmatch().equals(castOther.getOrderaccmatch())))) && 
/* 500 */       ((getCancelcmdmatch() == castOther.getCancelcmdmatch()) || ((getCancelcmdmatch() != null) && (castOther.getCancelcmdmatch() != null) && 
/* 501 */       (getCancelcmdmatch().equals(castOther.getCancelcmdmatch())))) && 
/* 502 */       ((getCancelacc() == castOther.getCancelacc()) || ((getCancelacc() != null) && (castOther.getCancelacc() != null) && 
/* 503 */       (getCancelacc().equals(castOther.getCancelacc())))) && 
/* 504 */       ((getCancelaccmatch() == castOther.getCancelaccmatch()) || ((getCancelaccmatch() != null) && (castOther.getCancelaccmatch() != null) && 
/* 505 */       (getCancelaccmatch().equals(castOther.getCancelaccmatch())))) && 
/* 506 */       ((getRequestcmdmatch() == castOther.getRequestcmdmatch()) || ((getRequestcmdmatch() != null) && 
/* 507 */       (castOther.getRequestcmdmatch() != null) && (getRequestcmdmatch().equals(castOther.getRequestcmdmatch())))) && 
/* 508 */       ((getRequestacc() == castOther.getRequestacc()) || ((getRequestacc() != null) && (castOther.getRequestacc() != null) && 
/* 509 */       (getRequestacc().equals(castOther.getRequestacc())))) && 
/* 510 */       ((getRequestaccmatch() == castOther.getRequestaccmatch()) || ((getRequestaccmatch() != null) && 
/* 511 */       (castOther.getRequestaccmatch() != null) && (getRequestaccmatch().equals(castOther.getRequestaccmatch())))) && 
/* 512 */       ((getEffdate() == castOther.getEffdate()) || ((getEffdate() != null) && (castOther.getEffdate() != null) && 
/* 513 */       (getEffdate()
/* 513 */       .equals(castOther.getEffdate())))) && 
/* 514 */       ((getExpdate() == castOther.getExpdate()) || ((getExpdate() != null) && (castOther.getExpdate() != null) && 
/* 515 */       (getExpdate()
/* 515 */       .equals(castOther.getExpdate())))) && 
/* 516 */       ((getVacsub() == castOther.getVacsub()) || ((getVacsub() != null) && (castOther.getVacsub() != null) && 
/* 517 */       (getVacsub()
/* 517 */       .equals(castOther.getVacsub())))) && 
/* 518 */       ((getNotifytype() == castOther.getNotifytype()) || ((getNotifytype() != null) && (castOther.getNotifytype() != null) && 
/* 519 */       (getNotifytype().equals(castOther.getNotifytype())))) && 
/* 520 */       ((getProductcity() == castOther.getProductcity()) || ((getProductcity() != null) && (castOther.getProductcity() != null) && 
/* 521 */       (getProductcity().equals(castOther.getProductcity())))) && 
/* 522 */       ((getProductperiodgrade() == castOther.getProductperiodgrade()) || ((getProductperiodgrade() != null) && 
/* 523 */       (castOther.getProductperiodgrade() != null) && (getProductperiodgrade().equals(castOther.getProductperiodgrade())))) && 
/* 524 */       ((getProductservicegrade() == castOther.getProductservicegrade()) || ((getProductservicegrade() != null) && 
/* 525 */       (castOther.getProductservicegrade() != null) && (getProductservicegrade().equals(castOther.getProductservicegrade())))) && 
/* 526 */       ((getProductcredit() == castOther.getProductcredit()) || ((getProductcredit() != null) && (castOther.getProductcredit() != null) && 
/* 527 */       (getProductcredit().equals(castOther.getProductcredit())))) && 
/* 528 */       ((getProductstatus() == castOther.getProductstatus()) || ((getProductstatus() != null) && (castOther.getProductstatus() != null) && 
/* 529 */       (getProductstatus().equals(castOther.getProductstatus())))) && 
/* 530 */       ((getProductid() == castOther.getProductid()) || ((getProductid() != null) && (castOther.getProductid() != null) && 
/* 531 */       (getProductid().equals(castOther.getProductid())))) && 
/* 532 */       ((getProductExtendId() == castOther.getProductExtendId()) || ((getProductExtendId() != null) && 
/* 533 */       (castOther.getProductExtendId() != null) && (getProductExtendId().equals(castOther.getProductExtendId())))) && 
/* 534 */       ((getProductExpandCode() == castOther.getProductExpandCode()) || ((getProductExpandCode() != null) && 
/* 535 */       (castOther.getProductExpandCode() != null) && (getProductExpandCode().equals(castOther.getProductExpandCode())))) && (
/* 536 */       (getReportMessageUrl() == castOther.getReportMessageUrl()) || ((getReportMessageUrl() != null) && 
/* 537 */       (castOther.getReportMessageUrl() != null) && (getReportMessageUrl().equals(castOther.getReportMessageUrl()))));
/*     */   }
/*     */ 
/*     */   public int hashCode() {
/* 541 */     int result = 17;
/*     */ 
/* 543 */     result = 37 * result + (getRecordsequenceid() == null ? 0 : getRecordsequenceid().hashCode());
/* 544 */     result = 37 * result + (getSpecProductid() == null ? 0 : getSpecProductid().hashCode());
/* 545 */     result = 37 * result + (getSpProductid() == null ? 0 : getSpProductid().hashCode());
/* 546 */     result = 37 * result + (getConfirm() == null ? 0 : getConfirm().hashCode());
/* 547 */     result = 37 * result + (getPresenter() == null ? 0 : getPresenter().hashCode());
/* 548 */     result = 37 * result + (getConfirmprompt() == null ? 0 : getConfirmprompt().hashCode());
/* 549 */     result = 37 * result + (getSuccessprompt() == null ? 0 : getSuccessprompt().hashCode());
/* 550 */     result = 37 * result + (getCancelprompt() == null ? 0 : getCancelprompt().hashCode());
/* 551 */     result = 37 * result + (getPopularizestart() == null ? 0 : getPopularizestart().hashCode());
/* 552 */     result = 37 * result + (getPopularizestop() == null ? 0 : getPopularizestop().hashCode());
/* 553 */     result = 37 * result + (getFreeuseflag() == null ? 0 : getFreeuseflag().hashCode());
/* 554 */     result = 37 * result + (getFreeusetime() == null ? 0 : getFreeusetime().hashCode());
/* 555 */     result = 37 * result + (getBillingid() == null ? 0 : getBillingid().hashCode());
/* 556 */     result = 37 * result + (getDiscountDes() == null ? 0 : getDiscountDes().hashCode());
/* 557 */     result = 37 * result + (getDiscountid() == null ? 0 : getDiscountid().hashCode());
/* 558 */     result = 37 * result + (getNeedconfm() == null ? 0 : getNeedconfm().hashCode());
/* 559 */     result = 37 * result + (getMaxusetimes() == null ? 0 : getMaxusetimes().hashCode());
/* 560 */     result = 37 * result + (getMaxusetime() == null ? 0 : getMaxusetime().hashCode());
/* 561 */     result = 37 * result + (getSendnum() == null ? 0 : getSendnum().hashCode());
/* 562 */     result = 37 * result + (getOrdercmdmatch() == null ? 0 : getOrdercmdmatch().hashCode());
/* 563 */     result = 37 * result + (getOrderacc() == null ? 0 : getOrderacc().hashCode());
/* 564 */     result = 37 * result + (getOrderaccmatch() == null ? 0 : getOrderaccmatch().hashCode());
/* 565 */     result = 37 * result + (getCancelcmdmatch() == null ? 0 : getCancelcmdmatch().hashCode());
/* 566 */     result = 37 * result + (getCancelacc() == null ? 0 : getCancelacc().hashCode());
/* 567 */     result = 37 * result + (getCancelaccmatch() == null ? 0 : getCancelaccmatch().hashCode());
/* 568 */     result = 37 * result + (getRequestcmdmatch() == null ? 0 : getRequestcmdmatch().hashCode());
/* 569 */     result = 37 * result + (getRequestacc() == null ? 0 : getRequestacc().hashCode());
/* 570 */     result = 37 * result + (getRequestaccmatch() == null ? 0 : getRequestaccmatch().hashCode());
/* 571 */     result = 37 * result + (getEffdate() == null ? 0 : getEffdate().hashCode());
/* 572 */     result = 37 * result + (getExpdate() == null ? 0 : getExpdate().hashCode());
/* 573 */     result = 37 * result + (getVacsub() == null ? 0 : getVacsub().hashCode());
/* 574 */     result = 37 * result + (getNotifytype() == null ? 0 : getNotifytype().hashCode());
/* 575 */     result = 37 * result + (getProductcity() == null ? 0 : getProductcity().hashCode());
/* 576 */     result = 37 * result + (getProductperiodgrade() == null ? 0 : getProductperiodgrade().hashCode());
/* 577 */     result = 37 * result + (getProductservicegrade() == null ? 0 : getProductservicegrade().hashCode());
/* 578 */     result = 37 * result + (getProductcredit() == null ? 0 : getProductcredit().hashCode());
/* 579 */     result = 37 * result + (getProductstatus() == null ? 0 : getProductstatus().hashCode());
/* 580 */     result = 37 * result + (getProductid() == null ? 0 : getProductid().hashCode());
/* 581 */     result = 37 * result + (getProductExtendId() == null ? 0 : getProductExtendId().hashCode());
/* 582 */     result = 37 * result + (getProductExpandCode() == null ? 0 : getProductExpandCode().hashCode());
/* 583 */     result = 37 * result + (getReportMessageUrl() == null ? 0 : getReportMessageUrl().hashCode());
/* 584 */     return result;
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.db.entity.VasserviceReserveInfo
 * JD-Core Version:    0.6.0
 */