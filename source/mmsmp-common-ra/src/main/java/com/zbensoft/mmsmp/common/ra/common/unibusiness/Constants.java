/*    */ package com.zbensoft.mmsmp.common.ra.common.unibusiness;
/*    */ 
/*    */ public class Constants
/*    */ {
/* 14 */   public static int ManageRequest_Operate_Normal = 0;
/* 15 */   public static int ManageRequest_Operate_Apply = 1;
/* 16 */   public static int ManageRequest_Operate_Pause = 2;
/* 17 */   public static int ManageRequest_Operate_PreCancel = 3;
/* 18 */   public static int ManageRequest_Operate_Cancel = 4;
/*    */ 
/* 21 */   public static int OrderRelation_UserType_MSISDN = 1;
/* 22 */   public static int OrderRelation_UserType_PHS = 2;
/* 23 */   public static int OrderRelation_UserType_PSTN = 3;
/* 24 */   public static int OrderRelation_UserType_OTHER = 4;
/*    */ 
/* 26 */   public static int OrderType_Product = 1;
/* 27 */   public static int OrderType_Package = 2;
/*    */ 
/* 29 */   public static int OrderRelation_Status_Order = 0;
/* 30 */   public static int OrderRelation_Status_Pause = 1;
/* 31 */   public static int OrderRelation_Status_Resume = 2;
/* 32 */   public static int OrderRelation_Status_Cancel = 3;
/* 33 */   public static int OrderRelation_Status_CancelSP = 4;
/* 34 */   public static int OrderRelation_Status_PauseSP = 5;
/* 35 */   public static int OrderRelation_Status_Replace = 6;
/* 36 */   public static int OrderRelation_Status_Demand = 7;
/*    */ 
/* 39 */   public static String ResponseCode_Sucess = "0";
/* 40 */   public static String ResponseCode_UserPhoneError = "12";
/* 41 */   public static String ResponseCode_UserTypeError = "13";
/* 42 */   public static String ResponseCode_ProductCodeError = "14";
/* 43 */   public static String ResponseCode_PackageCodeError = "15";
/*    */ 
/* 45 */   public static String ResponseCode_StatusError = "21";
/*    */ 
/* 47 */   public static String ResponseCode_IDError = "19";
/*    */ 
/* 49 */   public static String ResponseCode_OtherError = "99";
/*    */ 
/* 51 */   public static int ResponseCodeType_MMSMP = 1;
/* 52 */   public static int ResponseCodeType_VAC = 2;
/* 53 */   public static int ResponseCodeType_SMS = 3;
/* 54 */   public static int ResponseCodeType_MMS = 4;
/* 55 */   public static int ResponseCodeType_WO = 5;
/* 56 */   public static int ResponseCodeType_SP = 6;
/*    */ 
/* 58 */   public static String ResponseCode_ChannelTypeIsNullError = "1";
/* 59 */   public static String ResponseCode_ChannelTypeError = "1";
/*    */   public static final int Channel_WEB = 1;
/*    */   public static final int Channel_WAP = 2;
/*    */   public static final int Channel_IPhone = 3;
/*    */   public static final int Channel_SMS = 4;
/*    */   public static final int Channel_MMS = 5;
/*    */   public static final int Channel_IPhone_CANCEL = 6;
/*    */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.unibusiness.Constants
 * JD-Core Version:    0.6.0
 */