/*    */ package com.zbensoft.mmsmp.common.ra.common.mmcontents;
/*    */ 
/*    */ import java.io.ByteArrayOutputStream;
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ public class AcewayMMContentInfo
/*    */ {
/* 19 */   private ArrayList<AcewayMMPartInfo> m_PartsInfo = new ArrayList();
/* 20 */   private ByteArrayOutputStream m_Bos = new ByteArrayOutputStream();
/*    */ 
/*    */   public ArrayList getMMPartsInfo()
/*    */   {
/* 28 */     return this.m_PartsInfo;
/*    */   }
/*    */ 
/*    */   public ByteArrayOutputStream getBos()
/*    */   {
/* 33 */     return this.m_Bos;
/*    */   }
/*    */ 
/*    */   public void addPartInfo(AcewayMMPartInfo mmPartInfo)
/*    */   {
/* 38 */     this.m_PartsInfo.add(mmPartInfo);
/*    */   }
/*    */ 
/*    */   public void adapt(int offset)
/*    */   {
/* 43 */     AcewayMMPartInfo mmPartInfo = null;
/* 44 */     for (int i = 0; i < this.m_PartsInfo.size(); i++)
/*    */     {
/* 46 */       mmPartInfo = (AcewayMMPartInfo)this.m_PartsInfo.get(i);
/* 47 */       mmPartInfo.setStart(mmPartInfo.getStart() + offset);
/* 48 */       mmPartInfo.setEnd(mmPartInfo.getEnd() + offset);
/*    */     }
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 54 */     return encode();
/*    */   }
/*    */ 
/*    */   public String encode()
/*    */   {
/* 59 */     return AcewayMMPartInfo.array2Str(this.m_PartsInfo);
/*    */   }
/*    */ 
/*    */   public String getPartsTypeSize() {
/* 63 */     return AcewayMMPartInfo.getPartsTypeSize(this.m_PartsInfo);
/*    */   }
/*    */ 
/*    */   public static AcewayMMContentInfo decode(String str)
/*    */   {
/* 68 */     AcewayMMContentInfo info = new AcewayMMContentInfo();
/* 69 */     info.m_PartsInfo = AcewayMMPartInfo.str2Array(str);
/* 70 */     return info;
/*    */   }
/*    */ 
/*    */   public int getStartPositionofFirstPart()
/*    */   {
/* 75 */     int index = 30720;
/* 76 */     AcewayMMPartInfo partInfo = null;
/* 77 */     for (int i = 0; i < this.m_PartsInfo.size(); i++)
/*    */     {
/* 79 */       partInfo = (AcewayMMPartInfo)this.m_PartsInfo.get(i);
/* 80 */       if (index > partInfo.getStart()) {
/* 81 */         index = partInfo.getStart();
/*    */       }
/*    */     }
/* 84 */     if (partInfo != null) {
/* 85 */       return index;
/*    */     }
/* 87 */     return -1;
/*    */   }
/*    */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.mmcontents.AcewayMMContentInfo
 * JD-Core Version:    0.6.0
 */