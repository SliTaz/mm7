 package com.zbensoft.mmsmp.vas.sjb.unibusiness;
 
 import javax.xml.bind.annotation.XmlAccessType;
 import javax.xml.bind.annotation.XmlAccessorType;
 import javax.xml.bind.annotation.XmlElement;
 import javax.xml.bind.annotation.XmlType;
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 @XmlAccessorType(XmlAccessType.FIELD)
 @XmlType(name="ServiceCapabilityManageRequest", propOrder={"operate", "serviceCapabilityID", "spID"})
 public class ServiceCapabilityManageRequest
   extends CommonRequest
 {
   protected int operate;
   @XmlElement(required=true, nillable=true)
   protected String serviceCapabilityID;
   @XmlElement(required=true, nillable=true)
   protected String spID;
   
   public int getOperate()
   {
     return this.operate;
   }
   
 
 
 
   public void setOperate(int value)
   {
     this.operate = value;
   }
   
 
 
 
 
 
 
 
   public String getServiceCapabilityID()
   {
     return this.serviceCapabilityID;
   }
   
 
 
 
 
 
 
 
   public void setServiceCapabilityID(String value)
   {
     this.serviceCapabilityID = value;
   }
   
 
 
 
 
 
 
 
   public String getSpID()
   {
     return this.spID;
   }
   
 
 
 
 
 
 
 
   public void setSpID(String value)
   {
     this.spID = value;
   }
 }





