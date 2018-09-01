 package com.zbensoft.mmsmp.vas.sjb.unibusiness;
 
 import javax.xml.bind.annotation.XmlAccessType;
 import javax.xml.bind.annotation.XmlAccessorType;
 import javax.xml.bind.annotation.XmlElement;
 import javax.xml.bind.annotation.XmlType;
 
 
 
 
 
 
 
 
 
 
 
 
 
 @XmlAccessorType(XmlAccessType.FIELD)
 @XmlType(name="SubscribeOrunreverseManageRequest", propOrder={"sp_productId", "phone", "sourceType", "processType", "linkId"})
 public class SubscribeOrunreverseManageRequest
 {
   @XmlElement(required=true, nillable=true)
   protected String sp_productId;
   @XmlElement(required=true, nillable=true)
   protected String phone;
   @XmlElement(required=true, type=Integer.class, nillable=true)
   protected Integer sourceType;
   @XmlElement(required=true, nillable=true)
   protected Integer processType;
   @XmlElement(required=false, nillable=false)
   protected Integer linkId;
   
   public String getPhone()
   {
     return this.phone;
   }
   
   public void setPhone(String phone) {
     this.phone = phone;
   }
   
   public Integer getSourceType()
   {
     return this.sourceType;
   }
   
   public void setSourceType(Integer sourceType) {
     this.sourceType = sourceType;
   }
   
   public Integer getProcessType() {
     return this.processType;
   }
   
   public void setProcessType(Integer processType) {
     this.processType = processType;
   }
   
   public Integer getLinkId() {
     return this.linkId;
   }
   
   public void setLinkId(Integer linkId) {
     this.linkId = linkId;
   }
   
   public String getSp_productId() {
     return this.sp_productId;
   }
   
   public void setSp_productId(String spProductId) {
     this.sp_productId = spProductId;
   }
 }





