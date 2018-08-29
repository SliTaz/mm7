/*     */ package com.zbensoft.mmsmp.common.ra.common.mmcontents;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ public class AcewayMMPartInfo
/*     */ {
/*     */   public static final String FIELD_DELIMITER = "|";
/*     */   public static final String PART_DELIMITER = "::";
/*     */   private String m_Type;
/*     */   private int m_Start;
/*     */   private int m_End;
/*     */ 
/*     */   public AcewayMMPartInfo(String type, int start, int end)
/*     */   {
/*  26 */     setType(type);
/*  27 */     this.m_Start = start;
/*  28 */     this.m_End = end;
/*     */   }
/*     */ 
/*     */   public AcewayMMPartInfo()
/*     */   {
/*     */   }
/*     */ 
/*     */   public String getType()
/*     */   {
/*  37 */     return this.m_Type;
/*     */   }
/*     */ 
/*     */   public int getStart()
/*     */   {
/*  46 */     return this.m_Start;
/*     */   }
/*     */ 
/*     */   public int getEnd()
/*     */   {
/*  55 */     return this.m_End;
/*     */   }
/*     */ 
/*     */   public void setType(String type)
/*     */   {
/*  60 */     this.m_Type = type;
/*  61 */     if (this.m_Type != null)
/*     */     {
/*  63 */       int index = this.m_Type.indexOf(';');
/*  64 */       if (index != -1)
/*  65 */         this.m_Type = this.m_Type.substring(0, index);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setStart(int start) {
/*  70 */     this.m_Start = start;
/*     */   }
/*     */ 
/*     */   public void setEnd(int end) {
/*  74 */     this.m_End = end;
/*     */   }
/*     */ 
/*     */   public String getTypeSize()
/*     */   {
/*  79 */     return this.m_Type + "|" + (this.m_End - this.m_Start + 1);
/*     */   }
/*     */ 
/*     */   public String encode()
/*     */   {
/*  84 */     StringBuffer sb = new StringBuffer();
/*  85 */     sb.append(this.m_Type);
/*  86 */     sb.append("|" + this.m_Start);
/*  87 */     sb.append("|" + this.m_End);
/*  88 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public boolean decode(String strInfo)
/*     */   {
/*  93 */     int iStart = 0;
/*  94 */     int iPos = strInfo.indexOf("|");
/*  95 */     if (iPos > 0)
/*  96 */       setType(strInfo.substring(iStart, iPos));
/*     */     else {
/*  98 */       return false;
/*     */     }
/* 100 */     iStart = iPos + 1;
/* 101 */     iPos = strInfo.indexOf("|", iStart);
/* 102 */     if (iPos > 0)
/* 103 */       setStart(Integer.parseInt(strInfo.substring(iStart, iPos)));
/*     */     else {
/* 105 */       return false;
/*     */     }
/* 107 */     iStart = iPos + "|".length();
/* 108 */     setEnd(Integer.parseInt(strInfo.substring(iStart)));
/*     */ 
/* 110 */     return true;
/*     */   }
/*     */ 
/*     */   public static String getPartsTypeSize(ArrayList partsInfo)
/*     */   {
/* 115 */     AcewayMMPartInfo partInfo = null;
/* 116 */     StringBuffer sb = new StringBuffer();
/* 117 */     for (int i = 0; i < partsInfo.size(); i++)
/*     */     {
/* 119 */       if (i != 0)
/* 120 */         sb.append("::");
/* 121 */       partInfo = (AcewayMMPartInfo)partsInfo.get(i);
/* 122 */       sb.append(partInfo.getTypeSize());
/*     */     }
/* 124 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static String array2Str(ArrayList partsInfo)
/*     */   {
/* 129 */     AcewayMMPartInfo partInfo = null;
/* 130 */     StringBuffer sb = new StringBuffer();
/* 131 */     for (int i = 0; i < partsInfo.size(); i++)
/*     */     {
/* 133 */       if (i != 0)
/* 134 */         sb.append("::");
/* 135 */       partInfo = (AcewayMMPartInfo)partsInfo.get(i);
/* 136 */       sb.append(partInfo.encode());
/*     */     }
/* 138 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static ArrayList<AcewayMMPartInfo> str2Array(String strInfo)
/*     */   {
/* 143 */     if (strInfo == null) {
/* 144 */       return new ArrayList();
/*     */     }
/* 146 */     ArrayList partsInfo = new ArrayList();
/* 147 */     String strPartInfo = null;
/* 148 */     boolean loop = true;
/* 149 */     int iStart = 0;
/* 150 */     while (loop)
/*     */     {
/* 152 */       int iPos = strInfo.indexOf("::", iStart);
/* 153 */       if (iPos > 0) {
/* 154 */         strPartInfo = strInfo.substring(iStart, iPos);
/*     */       }
/*     */       else {
/* 157 */         loop = false;
/* 158 */         strPartInfo = strInfo.substring(iStart);
/*     */       }
/* 160 */       AcewayMMPartInfo mmPartInfo = new AcewayMMPartInfo();
/* 161 */       mmPartInfo.decode(strPartInfo);
/* 162 */       partsInfo.add(mmPartInfo);
/* 163 */       iStart = iPos + "::".length();
/*     */     }
/* 165 */     return partsInfo;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 243 */     StringBuffer sb = new StringBuffer();
/*     */ 
/* 245 */     sb.append("Type:" + this.m_Type + "; ");
/* 246 */     sb.append("Start:" + this.m_Start + "; ");
/* 247 */     sb.append("End:" + this.m_End);
/*     */ 
/* 249 */     return sb.toString();
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.mmcontents.AcewayMMPartInfo
 * JD-Core Version:    0.6.0
 */