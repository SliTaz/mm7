/*    */ package com.zbensoft.mmsmp.common.ra.common.config.configbean;
/*    */ 
/*    */ public class State
/*    */ {
/*    */   private int value;
/*    */   private String name;
/*    */   private int nextState;
/*    */   private int refusedState;
/*    */   private int isFirst;
/*    */ 
/*    */   public int getValue()
/*    */   {
/* 18 */     return this.value;
/*    */   }
/*    */   public void setValue(int value) {
/* 21 */     this.value = value;
/*    */   }
/*    */   public int getIsFirst() {
/* 24 */     return this.isFirst;
/*    */   }
/*    */   public void setIsFirst(int isFirst) {
/* 27 */     this.isFirst = isFirst;
/*    */   }
/*    */   public String getName() {
/* 30 */     return this.name;
/*    */   }
/*    */   public void setName(String name) {
/* 33 */     this.name = name;
/*    */   }
/*    */   public int getNextState() {
/* 36 */     return this.nextState;
/*    */   }
/*    */   public void setNextState(int nextState) {
/* 39 */     this.nextState = nextState;
/*    */   }
/*    */   public int getRefusedState() {
/* 42 */     return this.refusedState;
/*    */   }
/*    */   public void setRefusedState(int refusedState) {
/* 45 */     this.refusedState = refusedState;
/*    */   }
/*    */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.config.configbean.State
 * JD-Core Version:    0.6.0
 */