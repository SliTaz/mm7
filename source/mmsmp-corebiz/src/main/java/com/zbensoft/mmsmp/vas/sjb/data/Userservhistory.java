 package com.zbensoft.mmsmp.vas.sjb.data;
 
 import java.io.Serializable;
 import java.util.Date;
 import org.apache.commons.lang3.builder.EqualsBuilder;
 import org.apache.commons.lang3.builder.HashCodeBuilder;
 import org.apache.commons.lang3.builder.ToStringBuilder;
 import org.apache.commons.lang3.builder.ToStringStyle;
 
 
 public class Userservhistory
   implements Serializable
 {
   private static final long serialVersionUID = -1970420460249213128L;
   protected String mttranid;
   protected String reqid;
   protected String cellphonenumber;
   protected String chargeparty;
   protected int servuniqueid;
   private String serviceCode;
   protected int contentid;
   protected Date mttime;
   protected String status;
   protected String mtkind;
   protected Date statustime;
   protected int sendtype;
   protected int mtrelationid;
   protected String spid;
   protected String messageid;
   
   public String getMttranid()
   {
     return this.mttranid;
   }
   
 
 
   public void setMttranid(String mttranid)
   {
     this.mttranid = mttranid;
   }
   
 
 
 
   public String getReqid()
   {
     return this.reqid;
   }
   
 
 
   public void setReqid(String reqid)
   {
     this.reqid = reqid;
   }
   
 
 
 
   public String getCellphonenumber()
   {
     return this.cellphonenumber;
   }
   
 
 
   public void setCellphonenumber(String cellphonenumber)
   {
     this.cellphonenumber = cellphonenumber;
   }
   
 
 
 
   public String getChargeparty()
   {
     return this.chargeparty;
   }
   
 
 
   public void setChargeparty(String chargeparty)
   {
     this.chargeparty = chargeparty;
   }
   
 
 
 
   public int getServuniqueid()
   {
     return this.servuniqueid;
   }
   
 
 
   public void setServuniqueid(int servuniqueid)
   {
     this.servuniqueid = servuniqueid;
   }
   
 
 
 
   public int getContentid()
   {
     return this.contentid;
   }
   
 
 
   public void setContentid(int contentid)
   {
     this.contentid = contentid;
   }
   
 
 
 
   public Date getMttime()
   {
     return this.mttime;
   }
   
 
 
   public void setMttime(Date mttime)
   {
     this.mttime = mttime;
   }
   
 
 
 
   public String getStatus()
   {
     return this.status;
   }
   
 
 
   public void setStatus(String status)
   {
     this.status = status;
   }
   
 
 
 
   public String getMtkind()
   {
     return this.mtkind;
   }
   
 
 
   public void setMtkind(String mtkind)
   {
     this.mtkind = mtkind;
   }
   
 
 
 
   public Date getStatustime()
   {
     return this.statustime;
   }
   
 
 
   public void setStatustime(Date statustime)
   {
     this.statustime = statustime;
   }
   
 
 
 
   public int getSendtype()
   {
     return this.sendtype;
   }
   
 
 
   public void setSendtype(int sendtype)
   {
     this.sendtype = sendtype;
   }
   
 
 
 
   public int getMtrelationid()
   {
     return this.mtrelationid;
   }
   
 
 
   public void setMtrelationid(int mtrelationid)
   {
     this.mtrelationid = mtrelationid;
   }
   
 
 
 
 
   public String toString()
   {
     return 
     
 
 
 
 
 
 
 
 
 
 
 
       new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("mttranid", this.mttranid).append("reqid", this.reqid).append("cellphonenumber", this.cellphonenumber).append("chargeparty", this.chargeparty).append("servuniqueid", this.servuniqueid).append("contentid", this.contentid).append("mttime", this.mttime).append("status", this.status).append("mtkind", this.mtkind).append("statustime", this.statustime).append("mtservtype", this.sendtype).append("mtrelationid", this.mtrelationid).toString();
   }
   
 
 
 
 
   public boolean equals(Object object)
   {
     if (!(object instanceof Userservhistory)) {
       return false;
     }
     
     Userservhistory userservhistory = (Userservhistory)object;
     
     return new EqualsBuilder()
       .append(this.mttranid, userservhistory.mttranid)
       .append(this.reqid, userservhistory.reqid)
       .append(this.cellphonenumber, userservhistory.cellphonenumber)
       .append(this.chargeparty, userservhistory.chargeparty)
       .append(this.servuniqueid, userservhistory.servuniqueid)
       .append(this.contentid, userservhistory.contentid)
       .append(this.mttime, userservhistory.mttime)
       .append(this.status, userservhistory.status)
       .append(this.mtkind, userservhistory.mtkind)
       .append(this.statustime, userservhistory.statustime)
       .append(this.sendtype, userservhistory.sendtype)
       .append(this.mtrelationid, userservhistory.mtrelationid)
       .isEquals();
   }
   
 
 
 
 
   public int hashCode()
   {
     return 
     
 
 
 
 
 
 
 
 
 
 
 
       new HashCodeBuilder(-426830461, 631494429).append(this.mttranid).append(this.reqid).append(this.cellphonenumber).append(this.chargeparty).append(this.servuniqueid).append(this.contentid).append(this.mttime).append(this.status).append(this.mtkind).append(this.statustime).append(this.sendtype).append(this.mtrelationid).toHashCode();
   }
   
 
   public String getServiceCode()
   {
     return this.serviceCode;
   }
   
   public void setServiceCode(String serviceCode)
   {
     this.serviceCode = serviceCode;
   }
   
   public String getSpid()
   {
     return this.spid;
   }
   
   public void setSpid(String spid)
   {
     this.spid = spid;
   }
   
   public String getMessageid()
   {
     return this.messageid;
   }
   
   public void setMessageid(String messageid)
   {
     this.messageid = messageid;
   }
 }





