 package com.zbensoft.mmsmp.corebiz.handle.impl.sms;
 
 public class OperatorType
 {
   private EnumOptype type;
   private String cmdtext;
   private String service_id;
   private String product_id;
   private String spid;
   private String servicename;
   private Double fee;
   private Integer uniqueid;
   private String vaspname = "";
   private String businessphone = "";
   
   public Integer getUniqueid() {
     return this.uniqueid;
   }
   
   public void setUniqueid(Integer uniqueid) {
     this.uniqueid = uniqueid;
   }
   
   public String getServicename() {
     return this.servicename;
   }
   
   public void setServicename(String servicename) {
     this.servicename = servicename;
   }
   
   public Double getFee() {
     return this.fee;
   }
   
   public void setFee(Double fee) {
     this.fee = fee;
   }
   
   public OperatorType(EnumOptype enumOptype) {
     this.type = enumOptype;
   }
   
   public String getSpid() {
     return this.spid;
   }
   
   public void setSpid(String spid) {
     this.spid = spid;
   }
   
   public EnumOptype getType() {
     return this.type;
   }
   
   public void setType(EnumOptype type) {
     this.type = type;
   }
   
   public String getCmdtext() {
     return this.cmdtext;
   }
   
   public void setCmdtext(String cmdtext) {
     this.cmdtext = cmdtext;
   }
   
   public String getService_id() {
     return this.service_id;
   }
   
   public void setService_id(String service_id) {
     this.service_id = service_id;
   }
   
   public String getProduct_id() {
     return this.product_id;
   }
   
   public void setProduct_id(String product_id) {
     this.product_id = product_id;
   }
   
   public String getVaspname() {
     return this.vaspname;
   }
   
   public void setVaspname(String vaspname) {
     this.vaspname = vaspname;
   }
   
   public String getBusinessphone() {
     return this.businessphone;
   }
   
   public void setBusinessphone(String businessphone) {
     this.businessphone = businessphone;
   }
 }





