 package com.zbensoft.mmsmp.vas.sjb.unibusiness;
 
 import javax.xml.bind.annotation.XmlAccessType;
 import javax.xml.bind.annotation.XmlAccessorType;
 import javax.xml.bind.annotation.XmlElement;
 import javax.xml.bind.annotation.XmlType;
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 @XmlAccessorType(XmlAccessType.FIELD)
 @XmlType(name="ServiceManageRequest", propOrder={"operate", "serviceID", "spID"})
 public class ServiceManageRequest
   extends CommonRequest
 {
   protected int operate;
   @XmlElement(required=true, nillable=true)
   protected String serviceID;
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
   
 
 
 
 
 
 
 
   public String getServiceID()
   {
     return this.serviceID;
   }
   
 
 
 
 
 
 
 
   public void setServiceID(String value)
   {
     this.serviceID = value;
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





