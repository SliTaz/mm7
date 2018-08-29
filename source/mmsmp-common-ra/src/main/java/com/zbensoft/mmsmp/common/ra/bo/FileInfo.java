/*    */ package com.zbensoft.mmsmp.common.ra.bo;
/*    */ 
/*    */ public class FileInfo
/*    */ {
/*    */   private String streamingNo;
/*    */   private String timkeStamp;
/*    */   private OrderRelationUpdateInfo[] orderRelationUpdateInfo;
/*    */ 
/*    */   public String getStreamingNo()
/*    */   {
/* 10 */     return this.streamingNo;
/*    */   }
/*    */   public void setStreamingNo(String streamingNo) {
/* 13 */     this.streamingNo = streamingNo;
/*    */   }
/*    */ 
/*    */   public String getTimkeStamp() {
/* 17 */     return this.timkeStamp;
/*    */   }
/*    */   public void setTimkeStamp(String timkeStamp) {
/* 20 */     this.timkeStamp = timkeStamp;
/*    */   }
/*    */   public OrderRelationUpdateInfo[] getOrderRelationUpdateInfo() {
/* 23 */     return this.orderRelationUpdateInfo;
/*    */   }
/*    */ 
/*    */   public void setOrderRelationUpdateInfo(OrderRelationUpdateInfo[] orderRelationUpdateInfo) {
/* 27 */     this.orderRelationUpdateInfo = orderRelationUpdateInfo;
/*    */   }
/*    */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.bo.FileInfo
 * JD-Core Version:    0.6.0
 */