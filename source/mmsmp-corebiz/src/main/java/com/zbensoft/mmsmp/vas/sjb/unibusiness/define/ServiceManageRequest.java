 package com.zbensoft.mmsmp.vas.sjb.unibusiness.define;
 
 
 
 
 
 
 
 
 
 public class ServiceManageRequest
   extends CommonRequest
 {
   public static int Operate_Normal = 0;
   public static int Operate_Apply = 1;
   public static int Operate_Pause = 2;
   public static int Operate_PreCancel = 3;
   public static int Operate_Cancel = 4;
   
   private String spID;
   
   private String serviceID;
   private int operate;
   
   public int getOperate()
   {
     return this.operate;
   }
   
   public void setOperate(int operate) { this.operate = operate; }
   
   public String getServiceID()
   {
     return this.serviceID;
   }
   
   public void setServiceID(String serviceID) { this.serviceID = serviceID; }
   
   public String getSpID() {
     return this.spID;
   }
   
   public void setSpID(String spID) { this.spID = spID; }
 }





