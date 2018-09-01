 package com.zbensoft.mmsmp.vas.sjb.data;
 
 import java.io.Serializable;
 
 
 
 
 
 
 
 
 
 
 public class SysParameter
   implements Serializable
 {
   private String name;
   private String value;
   private String description;
   
   public SysParameter() {}
   
   public SysParameter(String name, String value)
   {
     this.name = name;
     this.value = value;
   }
   
   public SysParameter(String name, String value, String description)
   {
     this.name = name;
     this.value = value;
     this.description = description;
   }
   
 
   public String getName()
   {
     return this.name;
   }
   
   public void setName(String name) {
     this.name = name;
   }
   
   public String getValue() {
     return this.value;
   }
   
   public void setValue(String value) {
     this.value = value;
   }
   
   public String getDescription() {
     return this.description;
   }
   
   public void setDescription(String description) {
     this.description = description;
   }
 }





