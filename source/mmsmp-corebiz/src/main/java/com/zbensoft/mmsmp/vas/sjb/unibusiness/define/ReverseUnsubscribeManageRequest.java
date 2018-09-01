 package com.zbensoft.mmsmp.vas.sjb.unibusiness.define;
 
 
 
 
 public class ReverseUnsubscribeManageRequest
   extends CommonRequest
 {
   protected String phone;
   
 
 
   protected int userType;
   
 
 
   protected int idType;
   
 
   protected String id;
   
 
 
   public String getPhone()
   {
     return this.phone;
   }
   
 
   public void setPhone(String phone)
   {
     this.phone = phone;
   }
   
 
   public int getUserType()
   {
     return this.userType;
   }
   
 
   public void setUserType(int userType)
   {
     this.userType = userType;
   }
   
 
 
   public int getIdType()
   {
     return this.idType;
   }
   
 
   public void setIdType(int idType)
   {
     this.idType = idType;
   }
   
 
   public String getId()
   {
     return this.id;
   }
   
 
   public void setId(String id)
   {
     this.id = id;
   }
 }





