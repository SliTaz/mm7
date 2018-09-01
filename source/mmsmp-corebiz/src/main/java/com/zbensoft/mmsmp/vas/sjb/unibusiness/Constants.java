 package com.zbensoft.mmsmp.vas.sjb.unibusiness;
 
 
 
 
 
 
 
 
 
 
 public class Constants
 {
   public static int ManageRequest_Operate_Normal = 0;
   public static int ManageRequest_Operate_Apply = 1;
   public static int ManageRequest_Operate_Pause = 2;
   public static int ManageRequest_Operate_PreCancel = 3;
   public static int ManageRequest_Operate_Cancel = 4;
   
 
   public static int OrderRelation_UserType_MSISDN = 1;
   public static int OrderRelation_UserType_PHS = 2;
   public static int OrderRelation_UserType_PSTN = 3;
   public static int OrderRelation_UserType_OTHER = 4;
   
   public static int OrderType_Product = 1;
   public static int OrderType_Package = 2;
   
   public static int OrderRelation_Status_Order = 0;
   public static int OrderRelation_Status_Pause = 1;
   public static int OrderRelation_Status_Resume = 2;
   public static int OrderRelation_Status_Cancel = 3;
   public static int OrderRelation_Status_CancelSP = 4;
   public static int OrderRelation_Status_PauseSP = 5;
   public static int OrderRelation_Status_Replace = 6;
   public static int OrderRelation_Status_Demand = 7;
   
 
   public static String ResponseCode_Sucess = "0";
   public static String ResponseCode_UserPhoneError = "12";
   public static String ResponseCode_UserTypeError = "13";
   public static String ResponseCode_ProductCodeError = "14";
   public static String ResponseCode_PackageCodeError = "15";
   
   public static String ResponseCode_StatusError = "21";
   
   public static String ResponseCode_IDError = "19";
   
   public static String ResponseCode_OtherError = "99";
   
   public static int ResponseCodeType_MMSMP = 1;
   public static int ResponseCodeType_VAC = 2;
   public static int ResponseCodeType_SMS = 3;
   public static int ResponseCodeType_MMS = 4;
   public static int ResponseCodeType_WO = 5;
   public static int ResponseCodeType_SP = 6;
   
   public static String ResponseCode_ChannelTypeIsNullError = "1";
   public static String ResponseCode_ChannelTypeError = "1";
   public static final int Channel_WEB = 1;
   public static final int Channel_WAP = 2;
   public static final int Channel_IPhone = 3;
   public static final int Channel_SMS = 4;
   public static final int Channel_MMS = 5;
   public static final int Channel_IPhone_CANCEL = 6;
 }





