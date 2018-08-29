/*    */ package com.zbensoft.mmsmp.common.ra.common.db.entity;
/*    */ 
/*    */ public class TestInterface
/*    */ {
/*    */   public static void main(String[] args)
/*    */   {
/* 11 */     ServiceCapacityDAO cd = new ServiceCapacityDAO();
/* 12 */     cd.updateAllByCapacity("04", "4");
/* 13 */     cd.updateAllByVas("4", "4");
/* 14 */     cd.updateAllByVasService(Integer.valueOf(4), "4");
/*    */   }
/*    */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.db.entity.TestInterface
 * JD-Core Version:    0.6.0
 */