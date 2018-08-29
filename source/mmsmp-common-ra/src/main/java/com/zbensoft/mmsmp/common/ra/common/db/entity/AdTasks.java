/*     */ package com.zbensoft.mmsmp.common.ra.common.db.entity;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Date;
/*     */ 
/*     */ public class AdTasks
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 4728506443000216017L;
/*     */   private Integer taskid;
/*     */   private String taskname;
/*     */   private String taskdesc;
/*     */   private Date createdate;
/*     */   private String adtype;
/*     */   private String startday;
/*     */   private String endday;
/*     */   private String starttime;
/*     */   private String endtime;
/*     */   private String adtext;
/*     */   private String pushurl;
/*     */   private Integer contentid;
/*     */   private String phonesfile;
/*     */   private String status;
/*     */   private Integer priority;
/*     */   private Integer cpid;
/*     */   private String sender;
/*     */   private String chargeparty;
/*     */   private Integer serviceuniqueid;
/*     */   private String m_strAdContentPath;
/*     */ 
/*     */   public AdTasks()
/*     */   {
/*     */   }
/*     */ 
/*     */   public AdTasks(String taskname, Date createdate, String adtype, String startday, String endday, String starttime, String endtime, String phonesfile, String status, Integer priority, String sender, String chargeparty)
/*     */   {
/*  49 */     this.taskname = taskname;
/*  50 */     this.createdate = createdate;
/*  51 */     this.adtype = adtype;
/*  52 */     this.startday = startday;
/*  53 */     this.endday = endday;
/*  54 */     this.starttime = starttime;
/*  55 */     this.endtime = endtime;
/*  56 */     this.phonesfile = phonesfile;
/*  57 */     this.status = status;
/*  58 */     this.priority = priority;
/*  59 */     this.sender = sender;
/*  60 */     this.chargeparty = chargeparty;
/*     */   }
/*     */ 
/*     */   public AdTasks(String taskname, String taskdesc, Date createdate, String adtype, String startday, String endday, String starttime, String endtime, String adtext, String pushurl, Integer contentid, String phonesfile, String status, Integer priority, Integer cpid, String sender, String chargeparty, Integer serviceuniqueid)
/*     */   {
/*  65 */     this.taskname = taskname;
/*  66 */     this.taskdesc = taskdesc;
/*  67 */     this.createdate = createdate;
/*  68 */     this.adtype = adtype;
/*  69 */     this.startday = startday;
/*  70 */     this.endday = endday;
/*  71 */     this.starttime = starttime;
/*  72 */     this.endtime = endtime;
/*  73 */     this.adtext = adtext;
/*  74 */     this.pushurl = pushurl;
/*  75 */     this.contentid = contentid;
/*  76 */     this.phonesfile = phonesfile;
/*  77 */     this.status = status;
/*  78 */     this.priority = priority;
/*  79 */     this.cpid = cpid;
/*  80 */     this.sender = sender;
/*  81 */     this.chargeparty = chargeparty;
/*  82 */     this.serviceuniqueid = serviceuniqueid;
/*     */   }
/*     */ 
/*     */   public Integer getTaskid()
/*     */   {
/*  89 */     return this.taskid;
/*     */   }
/*     */ 
/*     */   public void setTaskid(Integer taskid) {
/*  93 */     this.taskid = taskid;
/*     */   }
/*     */ 
/*     */   public String getTaskname() {
/*  97 */     return this.taskname;
/*     */   }
/*     */ 
/*     */   public void setTaskname(String taskname) {
/* 101 */     this.taskname = taskname;
/*     */   }
/*     */ 
/*     */   public String getTaskdesc() {
/* 105 */     return this.taskdesc;
/*     */   }
/*     */ 
/*     */   public void setTaskdesc(String taskdesc) {
/* 109 */     this.taskdesc = taskdesc;
/*     */   }
/*     */ 
/*     */   public Date getCreatedate() {
/* 113 */     return this.createdate;
/*     */   }
/*     */ 
/*     */   public void setCreatedate(Date createdate) {
/* 117 */     this.createdate = createdate;
/*     */   }
/*     */ 
/*     */   public String getAdtype() {
/* 121 */     return this.adtype;
/*     */   }
/*     */ 
/*     */   public void setAdtype(String adtype) {
/* 125 */     this.adtype = adtype;
/*     */   }
/*     */ 
/*     */   public String getStartday() {
/* 129 */     return this.startday;
/*     */   }
/*     */ 
/*     */   public void setStartday(String startday) {
/* 133 */     this.startday = startday;
/*     */   }
/*     */ 
/*     */   public String getEndday() {
/* 137 */     return this.endday;
/*     */   }
/*     */ 
/*     */   public void setEndday(String endday) {
/* 141 */     this.endday = endday;
/*     */   }
/*     */ 
/*     */   public String getStarttime() {
/* 145 */     return this.starttime;
/*     */   }
/*     */ 
/*     */   public void setStarttime(String starttime) {
/* 149 */     this.starttime = starttime;
/*     */   }
/*     */ 
/*     */   public String getEndtime() {
/* 153 */     return this.endtime;
/*     */   }
/*     */ 
/*     */   public void setEndtime(String endtime) {
/* 157 */     this.endtime = endtime;
/*     */   }
/*     */ 
/*     */   public String getAdtext() {
/* 161 */     return this.adtext;
/*     */   }
/*     */ 
/*     */   public void setAdtext(String adtext) {
/* 165 */     this.adtext = adtext;
/*     */   }
/*     */ 
/*     */   public String getPushurl() {
/* 169 */     return this.pushurl;
/*     */   }
/*     */ 
/*     */   public void setPushurl(String pushurl) {
/* 173 */     this.pushurl = pushurl;
/*     */   }
/*     */ 
/*     */   public Integer getContentid() {
/* 177 */     return this.contentid;
/*     */   }
/*     */ 
/*     */   public void setContentid(Integer contentid) {
/* 181 */     this.contentid = contentid;
/*     */   }
/*     */ 
/*     */   public String getPhonesfile() {
/* 185 */     return this.phonesfile;
/*     */   }
/*     */ 
/*     */   public void setPhonesfile(String phonesfile) {
/* 189 */     this.phonesfile = phonesfile;
/*     */   }
/*     */ 
/*     */   public String getStatus() {
/* 193 */     return this.status;
/*     */   }
/*     */ 
/*     */   public void setStatus(String status) {
/* 197 */     this.status = status;
/*     */   }
/*     */ 
/*     */   public Integer getPriority() {
/* 201 */     return this.priority;
/*     */   }
/*     */ 
/*     */   public void setPriority(Integer priority) {
/* 205 */     this.priority = priority;
/*     */   }
/*     */ 
/*     */   public Integer getCpid() {
/* 209 */     return this.cpid;
/*     */   }
/*     */ 
/*     */   public void setCpid(Integer cpid) {
/* 213 */     this.cpid = cpid;
/*     */   }
/*     */ 
/*     */   public String getSender() {
/* 217 */     return this.sender;
/*     */   }
/*     */ 
/*     */   public void setSender(String sender) {
/* 221 */     this.sender = sender;
/*     */   }
/*     */ 
/*     */   public String getChargeparty() {
/* 225 */     return this.chargeparty;
/*     */   }
/*     */ 
/*     */   public void setChargeparty(String chargeparty) {
/* 229 */     this.chargeparty = chargeparty;
/*     */   }
/*     */ 
/*     */   public Integer getServiceuniqueid() {
/* 233 */     return this.serviceuniqueid;
/*     */   }
/*     */ 
/*     */   public void setServiceuniqueid(Integer serviceuniqueid) {
/* 237 */     this.serviceuniqueid = serviceuniqueid;
/*     */   }
/*     */ 
/*     */   public String getM_strAdContentPath()
/*     */   {
/* 245 */     return this.m_strAdContentPath;
/*     */   }
/*     */ 
/*     */   public void setM_strAdContentPath(String adContentPath)
/*     */   {
/* 253 */     this.m_strAdContentPath = adContentPath;
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.db.entity.AdTasks
 * JD-Core Version:    0.6.0
 */