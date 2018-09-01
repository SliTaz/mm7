 package com.zbensoft.mmsmp.common.ra.common.message;
 
 import javax.xml.namespace.QName;
 import org.apache.axis.description.ElementDesc;
 import org.apache.axis.description.TypeDesc;
 import org.apache.axis.encoding.Deserializer;
 import org.apache.axis.encoding.Serializer;
 import org.apache.axis.encoding.ser.BeanDeserializer;
 import org.apache.axis.encoding.ser.BeanSerializer;
 
 public class OrderRelationUpdateNotifyRequest extends AbstractMessage
 {
   private String recordSequenceId;
   private Integer userIdType;
   private String userId;
   private String serviceType;
   private String spId;
   private String productId;
   private Integer updateType;
   private String updateTime;
   private String updateDesc;
   private String linkId;
   private String content;
   private String effectiveDate;
   private String expireDate;
   private String time_stamp;
   private String encodeStr;
   private String notifySPURL;
   private Object __equalsCalc = null;
 
   private boolean __hashCodeCalc = false;
 
   private static TypeDesc typeDesc = new TypeDesc(OrderRelationUpdateNotifyRequest.class, true);
 
   static {
     typeDesc.setXmlType(new QName("http://req.sync.soap.bossagent.vac.unicom.com", "OrderRelationUpdateNotifyRequest"));
     ElementDesc elemField = new ElementDesc();
     elemField.setFieldName("recordSequenceId");
     elemField.setXmlName(new QName("", "recordSequenceId"));
     elemField.setXmlType(new QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
     elemField.setNillable(true);
     typeDesc.addFieldDesc(elemField);
     elemField = new ElementDesc();
     elemField.setFieldName("userIdType");
     elemField.setXmlName(new QName("", "userIdType"));
     elemField.setXmlType(new QName("http://schemas.xmlsoap.org/soap/encoding/", "int"));
     elemField.setNillable(true);
     typeDesc.addFieldDesc(elemField);
     elemField = new ElementDesc();
     elemField.setFieldName("userId");
     elemField.setXmlName(new QName("", "userId"));
     elemField.setXmlType(new QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
     elemField.setNillable(true);
     typeDesc.addFieldDesc(elemField);
     elemField = new ElementDesc();
     elemField.setFieldName("serviceType");
     elemField.setXmlName(new QName("", "serviceType"));
     elemField.setXmlType(new QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
     elemField.setNillable(true);
     typeDesc.addFieldDesc(elemField);
     elemField = new ElementDesc();
     elemField.setFieldName("spId");
     elemField.setXmlName(new QName("", "spId"));
     elemField.setXmlType(new QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
     elemField.setNillable(true);
     typeDesc.addFieldDesc(elemField);
     elemField = new ElementDesc();
     elemField.setFieldName("productId");
     elemField.setXmlName(new QName("", "productId"));
     elemField.setXmlType(new QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
     elemField.setNillable(true);
     typeDesc.addFieldDesc(elemField);
     elemField = new ElementDesc();
     elemField.setFieldName("updateType");
     elemField.setXmlName(new QName("", "updateType"));
     elemField.setXmlType(new QName("http://schemas.xmlsoap.org/soap/encoding/", "int"));
     elemField.setNillable(true);
     typeDesc.addFieldDesc(elemField);
     elemField = new ElementDesc();
     elemField.setFieldName("updateTime");
     elemField.setXmlName(new QName("", "updateTime"));
     elemField.setXmlType(new QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
     elemField.setNillable(true);
     typeDesc.addFieldDesc(elemField);
     elemField = new ElementDesc();
     elemField.setFieldName("updateDesc");
     elemField.setXmlName(new QName("", "updateDesc"));
     elemField.setXmlType(new QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
     elemField.setNillable(true);
     typeDesc.addFieldDesc(elemField);
     elemField = new ElementDesc();
     elemField.setFieldName("linkId");
     elemField.setXmlName(new QName("", "linkId"));
     elemField.setXmlType(new QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
     elemField.setNillable(true);
     typeDesc.addFieldDesc(elemField);
     elemField = new ElementDesc();
     elemField.setFieldName("content");
     elemField.setXmlName(new QName("", "content"));
     elemField.setXmlType(new QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
     elemField.setNillable(true);
     typeDesc.addFieldDesc(elemField);
     elemField = new ElementDesc();
     elemField.setFieldName("effectiveDate");
     elemField.setXmlName(new QName("", "effectiveDate"));
     elemField.setXmlType(new QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
     elemField.setNillable(true);
     typeDesc.addFieldDesc(elemField);
     elemField = new ElementDesc();
     elemField.setFieldName("expireDate");
     elemField.setXmlName(new QName("", "expireDate"));
     elemField.setXmlType(new QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
     elemField.setNillable(true);
     typeDesc.addFieldDesc(elemField);
     elemField = new ElementDesc();
     elemField.setFieldName("time_stamp");
     elemField.setXmlName(new QName("", "time_stamp"));
     elemField.setXmlType(new QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
     elemField.setNillable(true);
     typeDesc.addFieldDesc(elemField);
     elemField = new ElementDesc();
     elemField.setFieldName("encodeStr");
     elemField.setXmlName(new QName("", "encodeStr"));
     elemField.setXmlType(new QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
     elemField.setNillable(true);
     typeDesc.addFieldDesc(elemField);
   }
 
   public String getNotifySPURL()
   {
     return this.notifySPURL;
   }
 
   public void setNotifySPURL(String notifySPURL) {
     this.notifySPURL = notifySPURL;
   }
 
   public OrderRelationUpdateNotifyRequest()
   {
   }
 
   public OrderRelationUpdateNotifyRequest(String recordSequenceId, Integer userIdType, String userId, String serviceType, String spId, String productId, Integer updateType, String updateTime, String updateDesc, String linkId, String content, String effectiveDate, String expireDate, String time_stamp, String encodeStr)
   {
     this.recordSequenceId = recordSequenceId;
     this.userIdType = userIdType;
     this.userId = userId;
     this.serviceType = serviceType;
     this.spId = spId;
     this.productId = productId;
     this.updateType = updateType;
     this.updateTime = updateTime;
     this.updateDesc = updateDesc;
     this.linkId = linkId;
     this.content = content;
     this.effectiveDate = effectiveDate;
     this.expireDate = expireDate;
     this.time_stamp = time_stamp;
     this.encodeStr = encodeStr;
   }
 
   public String getRecordSequenceId()
   {
     return this.recordSequenceId;
   }
 
   public void setRecordSequenceId(String recordSequenceId)
   {
     this.recordSequenceId = recordSequenceId;
   }
 
   public Integer getUserIdType()
   {
     return this.userIdType;
   }
 
   public void setUserIdType(Integer userIdType)
   {
     this.userIdType = userIdType;
   }
 
   public String getUserId()
   {
     return this.userId;
   }
 
   public void setUserId(String userId)
   {
     this.userId = userId;
   }
 
   public String getServiceType()
   {
     return this.serviceType;
   }
 
   public void setServiceType(String serviceType)
   {
     this.serviceType = serviceType;
   }
 
   public String getSpId()
   {
     return this.spId;
   }
 
   public void setSpId(String spId)
   {
     this.spId = spId;
   }
 
   public String getProductId()
   {
     return this.productId;
   }
 
   public void setProductId(String productId)
   {
     this.productId = productId;
   }
 
   public Integer getUpdateType()
   {
     return this.updateType;
   }
 
   public void setUpdateType(Integer updateType)
   {
     this.updateType = updateType;
   }
 
   public String getUpdateTime()
   {
     return this.updateTime;
   }
 
   public void setUpdateTime(String updateTime)
   {
     this.updateTime = updateTime;
   }
 
   public String getUpdateDesc()
   {
     return this.updateDesc;
   }
 
   public void setUpdateDesc(String updateDesc)
   {
     this.updateDesc = updateDesc;
   }
 
   public String getLinkId()
   {
     return this.linkId;
   }
 
   public void setLinkId(String linkId)
   {
     this.linkId = linkId;
   }
 
   public String getContent()
   {
     return this.content;
   }
 
   public void setContent(String content)
   {
     this.content = content;
   }
 
   public String getEffectiveDate()
   {
     return this.effectiveDate;
   }
 
   public void setEffectiveDate(String effectiveDate)
   {
     this.effectiveDate = effectiveDate;
   }
 
   public String getExpireDate()
   {
     return this.expireDate;
   }
 
   public void setExpireDate(String expireDate)
   {
     this.expireDate = expireDate;
   }
 
   public String getTime_stamp()
   {
     return this.time_stamp;
   }
 
   public void setTime_stamp(String time_stamp)
   {
     this.time_stamp = time_stamp;
   }
 
   public String getEncodeStr()
   {
     return this.encodeStr;
   }
 
   public void setEncodeStr(String encodeStr)
   {
     this.encodeStr = encodeStr;
   }
 
   public synchronized boolean equals(Object obj)
   {
     if (!(obj instanceof OrderRelationUpdateNotifyRequest)) return false;
     OrderRelationUpdateNotifyRequest other = (OrderRelationUpdateNotifyRequest)obj;
     if (obj == null) return false;
     if (this == obj) return true;
     if (this.__equalsCalc != null) {
       return this.__equalsCalc == obj;
     }
     this.__equalsCalc = obj;
 
     boolean _equals = 
       ((this.recordSequenceId == null) && (other.getRecordSequenceId() == null)) || (
       (this.recordSequenceId != null) && 
       (this.recordSequenceId.equals(other.getRecordSequenceId())) && (
       ((this.userIdType == null) && (other.getUserIdType() == null)) || (
       (this.userIdType != null) && 
       (this.userIdType.equals(other.getUserIdType())) && (
       ((this.userId == null) && (other.getUserId() == null)) || (
       (this.userId != null) && 
       (this.userId.equals(other.getUserId())) && (
       ((this.serviceType == null) && (other.getServiceType() == null)) || (
       (this.serviceType != null) && 
       (this.serviceType.equals(other.getServiceType())) && (
       ((this.spId == null) && (other.getSpId() == null)) || (
       (this.spId != null) && 
       (this.spId.equals(other.getSpId())) && (
       ((this.productId == null) && (other.getProductId() == null)) || (
       (this.productId != null) && 
       (this.productId.equals(other.getProductId())) && (
       ((this.updateType == null) && (other.getUpdateType() == null)) || (
       (this.updateType != null) && 
       (this.updateType.equals(other.getUpdateType())) && (
       ((this.updateTime == null) && (other.getUpdateTime() == null)) || (
       (this.updateTime != null) && 
       (this.updateTime.equals(other.getUpdateTime())) && (
       ((this.updateDesc == null) && (other.getUpdateDesc() == null)) || (
       (this.updateDesc != null) && 
       (this.updateDesc.equals(other.getUpdateDesc())) && (
       ((this.linkId == null) && (other.getLinkId() == null)) || (
       (this.linkId != null) && 
       (this.linkId.equals(other.getLinkId())) && (
       ((this.content == null) && (other.getContent() == null)) || (
       (this.content != null) && 
       (this.content.equals(other.getContent())) && (
       ((this.effectiveDate == null) && (other.getEffectiveDate() == null)) || (
       (this.effectiveDate != null) && 
       (this.effectiveDate.equals(other.getEffectiveDate())) && (
       ((this.expireDate == null) && (other.getExpireDate() == null)) || (
       (this.expireDate != null) && 
       (this.expireDate.equals(other.getExpireDate())) && (
       ((this.time_stamp == null) && (other.getTime_stamp() == null)) || (
       (this.time_stamp != null) && 
       (this.time_stamp.equals(other.getTime_stamp())) && (
       ((this.encodeStr == null) && (other.getEncodeStr() == null)) || (
       (this.encodeStr != null) && 
       (this.encodeStr.equals(other.getEncodeStr())))))))))))))))))))))))))))))));
     this.__equalsCalc = null;
     return _equals;
   }
 
   public synchronized int hashCode()
   {
     if (this.__hashCodeCalc) {
       return 0;
     }
     this.__hashCodeCalc = true;
     int _hashCode = 1;
     if (getRecordSequenceId() != null) {
       _hashCode += getRecordSequenceId().hashCode();
     }
     if (getUserIdType() != null) {
       _hashCode += getUserIdType().hashCode();
     }
     if (getUserId() != null) {
       _hashCode += getUserId().hashCode();
     }
     if (getServiceType() != null) {
       _hashCode += getServiceType().hashCode();
     }
     if (getSpId() != null) {
       _hashCode += getSpId().hashCode();
     }
     if (getProductId() != null) {
       _hashCode += getProductId().hashCode();
     }
     if (getUpdateType() != null) {
       _hashCode += getUpdateType().hashCode();
     }
     if (getUpdateTime() != null) {
       _hashCode += getUpdateTime().hashCode();
     }
     if (getUpdateDesc() != null) {
       _hashCode += getUpdateDesc().hashCode();
     }
     if (getLinkId() != null) {
       _hashCode += getLinkId().hashCode();
     }
     if (getContent() != null) {
       _hashCode += getContent().hashCode();
     }
     if (getEffectiveDate() != null) {
       _hashCode += getEffectiveDate().hashCode();
     }
     if (getExpireDate() != null) {
       _hashCode += getExpireDate().hashCode();
     }
     if (getTime_stamp() != null) {
       _hashCode += getTime_stamp().hashCode();
     }
     if (getEncodeStr() != null) {
       _hashCode += getEncodeStr().hashCode();
     }
     this.__hashCodeCalc = false;
     return _hashCode;
   }
 
   public static TypeDesc getTypeDesc()
   {
     return typeDesc;
   }
 
   public static Serializer getSerializer(String mechType, Class _javaType, QName _xmlType)
   {
     return 
       new BeanSerializer(
       _javaType, _xmlType, typeDesc);
   }
 
   public static Deserializer getDeserializer(String mechType, Class _javaType, QName _xmlType)
   {
     return 
       new BeanDeserializer(
       _javaType, _xmlType, typeDesc);
   }
 
   public void decodeString(String message)
     throws DecodeMessageException
   {
   }
 
   public String encodeString()
   {
     return null;
   }
 
   public MessageType getMessageType()
   {
     return null;
   }
 
   public int getServiceId()
   {
     return 0;
   }
 }
