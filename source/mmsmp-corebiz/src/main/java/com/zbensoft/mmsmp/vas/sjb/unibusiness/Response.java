 package com.zbensoft.mmsmp.vas.sjb.unibusiness;
 
 import javax.xml.bind.annotation.XmlAccessType;
 import javax.xml.bind.annotation.XmlAccessorType;
 import javax.xml.bind.annotation.XmlElement;
 import javax.xml.bind.annotation.XmlType;
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 @XmlAccessorType(XmlAccessType.FIELD)
 @XmlType(name="Response", propOrder={"desc", "returnCode", "codeType"})
 public class Response
 {
   @XmlElement(required=true, nillable=true)
   protected String desc;
   @XmlElement(required=true, nillable=true)
   protected String returnCode;
   @XmlElement(required=true, nillable=true)
   protected int codeType;
   
   public int getCodeType()
   {
     return this.codeType;
   }
   
 
 
   public void setCodeType(int codeType)
   {
     this.codeType = codeType;
   }
   
 
 
 
 
 
 
 
   public String getDesc()
   {
     return this.desc;
   }
   
 
 
 
 
 
 
 
   public void setDesc(String value)
   {
     this.desc = value;
   }
   
 
 
 
 
 
 
 
   public String getReturnCode()
   {
     return this.returnCode;
   }
   
 
 
 
 
 
 
 
   public void setReturnCode(String value)
   {
     this.returnCode = value;
   }
 }





