/*     */ package com.zbensoft.mmsmp.common.ra.common.message;
/*     */ 
/*     */

import org.apache.axis.description.ElementDesc;
import org.apache.axis.description.TypeDesc;
import org.apache.axis.encoding.Deserializer;
import org.apache.axis.encoding.Serializer;
import org.apache.axis.encoding.ser.BeanDeserializer;
import org.apache.axis.encoding.ser.BeanSerializer;

import javax.xml.namespace.QName;

/*     */
/*     */
/*     */
/*     */
/*     */
/*     */

/*     */
/*     */ public class OrderRelationUpdateNotifyResponse extends AbstractMessage
/*     */ {
/*     */   private String recordSequenceId;
/*     */   private int resultCode;
/*     */   private OrderRelationUpdateNotifyRequest orderRequest;
/*  75 */   private Object __equalsCalc = null;
/*     */ 
/*  95 */   private boolean __hashCodeCalc = false;
/*     */ 
/* 112 */   private static TypeDesc typeDesc = new TypeDesc(OrderRelationUpdateNotifyResponse.class, true);
/*     */ 
/*     */   static {
/* 115 */     typeDesc.setXmlType(new QName("http://rsp.sync.soap.bossagent.vac.unicom.com", "OrderRelationUpdateNotifyResponse"));
/* 116 */     ElementDesc elemField = new ElementDesc();
/* 117 */     elemField.setFieldName("recordSequenceId");
/* 118 */     elemField.setXmlName(new QName("", "recordSequenceId"));
/* 119 */     elemField.setXmlType(new QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
/* 120 */     elemField.setNillable(true);
/* 121 */     typeDesc.addFieldDesc(elemField);
/* 122 */     elemField = new ElementDesc();
/* 123 */     elemField.setFieldName("resultCode");
/* 124 */     elemField.setXmlName(new QName("", "resultCode"));
/* 125 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
/* 126 */     elemField.setNillable(false);
/* 127 */     typeDesc.addFieldDesc(elemField);
/*     */   }
/*     */ 
/*     */   public OrderRelationUpdateNotifyRequest getOrderRequest()
/*     */   {
/*  18 */     return this.orderRequest;
/*     */   }
/*     */ 
/*     */   public void setOrderRequest(OrderRelationUpdateNotifyRequest orderRequest) {
/*  22 */     this.orderRequest = orderRequest;
/*     */   }
/*     */ 
/*     */   public OrderRelationUpdateNotifyResponse()
/*     */   {
/*     */   }
/*     */ 
/*     */   public OrderRelationUpdateNotifyResponse(String recordSequenceId, int resultCode)
/*     */   {
/*  31 */     this.recordSequenceId = recordSequenceId;
/*  32 */     this.resultCode = resultCode;
/*     */   }
/*     */ 
/*     */   public String getRecordSequenceId()
/*     */   {
/*  42 */     return this.recordSequenceId;
/*     */   }
/*     */ 
/*     */   public void setRecordSequenceId(String recordSequenceId)
/*     */   {
/*  52 */     this.recordSequenceId = recordSequenceId;
/*     */   }
/*     */ 
/*     */   public int getResultCode()
/*     */   {
/*  62 */     return this.resultCode;
/*     */   }
/*     */ 
/*     */   public void setResultCode(int resultCode)
/*     */   {
/*  72 */     this.resultCode = resultCode;
/*     */   }
/*     */ 
/*     */   public synchronized boolean equals(Object obj)
/*     */   {
/*  77 */     if (!(obj instanceof OrderRelationUpdateNotifyResponse)) return false;
/*  78 */     OrderRelationUpdateNotifyResponse other = (OrderRelationUpdateNotifyResponse)obj;
/*  79 */     if (obj == null) return false;
/*  80 */     if (this == obj) return true;
/*  81 */     if (this.__equalsCalc != null) {
/*  82 */       return this.__equalsCalc == obj;
/*     */     }
/*  84 */     this.__equalsCalc = obj;
/*     */ 
/*  86 */     boolean _equals = 
/*  87 */       ((this.recordSequenceId == null) && (other.getRecordSequenceId() == null)) || (
/*  88 */       (this.recordSequenceId != null) && 
/*  89 */       (this.recordSequenceId.equals(other.getRecordSequenceId())) && 
/*  90 */       (this.resultCode == other.getResultCode()));
/*  91 */     this.__equalsCalc = null;
/*  92 */     return _equals;
/*     */   }
/*     */ 
/*     */   public synchronized int hashCode()
/*     */   {
/*  97 */     if (this.__hashCodeCalc) {
/*  98 */       return 0;
/*     */     }
/* 100 */     this.__hashCodeCalc = true;
/* 101 */     int _hashCode = 1;
/* 102 */     if (getRecordSequenceId() != null) {
/* 103 */       _hashCode += getRecordSequenceId().hashCode();
/*     */     }
/* 105 */     _hashCode += getResultCode();
/* 106 */     this.__hashCodeCalc = false;
/* 107 */     return _hashCode;
/*     */   }
/*     */ 
/*     */   public static TypeDesc getTypeDesc()
/*     */   {
/* 134 */     return typeDesc;
/*     */   }
/*     */ 
/*     */   public static Serializer getSerializer(String mechType, Class _javaType, QName _xmlType)
/*     */   {
/* 144 */     return 
/* 145 */       new BeanSerializer(
/* 146 */       _javaType, _xmlType, typeDesc);
/*     */   }
/*     */ 
/*     */   public static Deserializer getDeserializer(String mechType, Class _javaType, QName _xmlType)
/*     */   {
/* 156 */     return 
/* 157 */       new BeanDeserializer(
/* 158 */       _javaType, _xmlType, typeDesc);
/*     */   }
/*     */ 
/*     */   public void decodeString(String message)
/*     */     throws DecodeMessageException
/*     */   {
/*     */   }
/*     */ 
/*     */   public String encodeString()
/*     */   {
/* 168 */     return null;
/*     */   }
/*     */ 
/*     */   public MessageType getMessageType()
/*     */   {
/* 173 */     return null;
/*     */   }
/*     */ 
/*     */   public int getServiceId()
/*     */   {
/* 178 */     return 0;
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.message.OrderRelationUpdateNotifyResponse
 * JD-Core Version:    0.6.0
 */