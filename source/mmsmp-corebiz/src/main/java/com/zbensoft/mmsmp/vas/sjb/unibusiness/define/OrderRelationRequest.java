 package com.zbensoft.mmsmp.vas.sjb.unibusiness.define;
 
 
 
 
 
 
 
 
 
 
 public class OrderRelationRequest
   extends CommonRequest
 {
   public static int UserType_MSISDN = 1;
   public static int UserType_PHS = 2;
   public static int UserType_PSTN = 3;
   public static int UserType_OTHER = 4;
   
   public static int OrderType_Product = 1;
   public static int OrderType_Package = 2;
   
   public static int Status_Order = 0;
   public static int Status_Pause = 1;
   public static int Status_Resume = 2;
   public static int Status_Cancel = 3;
   public static int Status_CancelSP = 4;
   public static int Status_PauseSP = 5;
   public static int Status_Replace = 6;
   
 
   private String userPhone;
   
 
   private int userType;
   
 
   private int orderType;
   
 
   private String productID;
   
 
   private String oldProduceID;
   
   private int status;
   
   private String spCode;
   
 
   public String getSpCode()
   {
     return this.spCode;
   }
   
   public void setSpCode(String spCode) { this.spCode = spCode; }
   
   public String getOldProduceID() {
     return this.oldProduceID;
   }
   
   public void setOldProduceID(String oldProduceID) { this.oldProduceID = oldProduceID; }
   
   public int getOrderType() {
     return this.orderType;
   }
   
   public void setOrderType(int orderType) { this.orderType = orderType; }
   
   public String getProductID() {
     return this.productID;
   }
   
   public void setProductID(String productID) { this.productID = productID; }
   
   public int getStatus() {
     return this.status;
   }
   
   public void setStatus(int status) { this.status = status; }
   
   public String getUserPhone()
   {
     return this.userPhone;
   }
   
   public void setUserPhone(String userPhone) { this.userPhone = userPhone; }
   
   public int getUserType() {
     return this.userType;
   }
   
   public void setUserType(int userType) { this.userType = userType; }
 }





