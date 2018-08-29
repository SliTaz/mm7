/*     */ package com.zbensoft.mmsmp.common.ra.common.message;
/*     */ 
/*     */ import javax.xml.namespace.QName;
/*     */ import org.apache.axis.description.ElementDesc;
/*     */ import org.apache.axis.description.TypeDesc;
/*     */ import org.apache.axis.encoding.Deserializer;
/*     */ import org.apache.axis.encoding.Serializer;
/*     */ import org.apache.axis.encoding.ser.BeanDeserializer;
/*     */ import org.apache.axis.encoding.ser.BeanSerializer;
/*     */ 
/*     */ public class OrderRelationUpdateNotifyRequest extends AbstractMessage
/*     */ {
/*     */   private String recordSequenceId;
/*     */   private Integer userIdType;
/*     */   private String userId;
/*     */   private String serviceType;
/*     */   private String spId;
/*     */   private String productId;
/*     */   private Integer updateType;
/*     */   private String updateTime;
/*     */   private String updateDesc;
/*     */   private String linkId;
/*     */   private String content;
/*     */   private String effectiveDate;
/*     */   private String expireDate;
/*     */   private String time_stamp;
/*     */   private String encodeStr;
/*     */   private String notifySPURL;
/* 387 */   private Object __equalsCalc = null;
/*     */ 
/* 448 */   private boolean __hashCodeCalc = false;
/*     */ 
/* 506 */   private static TypeDesc typeDesc = new TypeDesc(OrderRelationUpdateNotifyRequest.class, true);
/*     */ 
/*     */   static {
/* 509 */     typeDesc.setXmlType(new QName("http://req.sync.soap.bossagent.vac.unicom.com", "OrderRelationUpdateNotifyRequest"));
/* 510 */     ElementDesc elemField = new ElementDesc();
/* 511 */     elemField.setFieldName("recordSequenceId");
/* 512 */     elemField.setXmlName(new QName("", "recordSequenceId"));
/* 513 */     elemField.setXmlType(new QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
/* 514 */     elemField.setNillable(true);
/* 515 */     typeDesc.addFieldDesc(elemField);
/* 516 */     elemField = new ElementDesc();
/* 517 */     elemField.setFieldName("userIdType");
/* 518 */     elemField.setXmlName(new QName("", "userIdType"));
/* 519 */     elemField.setXmlType(new QName("http://schemas.xmlsoap.org/soap/encoding/", "int"));
/* 520 */     elemField.setNillable(true);
/* 521 */     typeDesc.addFieldDesc(elemField);
/* 522 */     elemField = new ElementDesc();
/* 523 */     elemField.setFieldName("userId");
/* 524 */     elemField.setXmlName(new QName("", "userId"));
/* 525 */     elemField.setXmlType(new QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
/* 526 */     elemField.setNillable(true);
/* 527 */     typeDesc.addFieldDesc(elemField);
/* 528 */     elemField = new ElementDesc();
/* 529 */     elemField.setFieldName("serviceType");
/* 530 */     elemField.setXmlName(new QName("", "serviceType"));
/* 531 */     elemField.setXmlType(new QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
/* 532 */     elemField.setNillable(true);
/* 533 */     typeDesc.addFieldDesc(elemField);
/* 534 */     elemField = new ElementDesc();
/* 535 */     elemField.setFieldName("spId");
/* 536 */     elemField.setXmlName(new QName("", "spId"));
/* 537 */     elemField.setXmlType(new QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
/* 538 */     elemField.setNillable(true);
/* 539 */     typeDesc.addFieldDesc(elemField);
/* 540 */     elemField = new ElementDesc();
/* 541 */     elemField.setFieldName("productId");
/* 542 */     elemField.setXmlName(new QName("", "productId"));
/* 543 */     elemField.setXmlType(new QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
/* 544 */     elemField.setNillable(true);
/* 545 */     typeDesc.addFieldDesc(elemField);
/* 546 */     elemField = new ElementDesc();
/* 547 */     elemField.setFieldName("updateType");
/* 548 */     elemField.setXmlName(new QName("", "updateType"));
/* 549 */     elemField.setXmlType(new QName("http://schemas.xmlsoap.org/soap/encoding/", "int"));
/* 550 */     elemField.setNillable(true);
/* 551 */     typeDesc.addFieldDesc(elemField);
/* 552 */     elemField = new ElementDesc();
/* 553 */     elemField.setFieldName("updateTime");
/* 554 */     elemField.setXmlName(new QName("", "updateTime"));
/* 555 */     elemField.setXmlType(new QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
/* 556 */     elemField.setNillable(true);
/* 557 */     typeDesc.addFieldDesc(elemField);
/* 558 */     elemField = new ElementDesc();
/* 559 */     elemField.setFieldName("updateDesc");
/* 560 */     elemField.setXmlName(new QName("", "updateDesc"));
/* 561 */     elemField.setXmlType(new QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
/* 562 */     elemField.setNillable(true);
/* 563 */     typeDesc.addFieldDesc(elemField);
/* 564 */     elemField = new ElementDesc();
/* 565 */     elemField.setFieldName("linkId");
/* 566 */     elemField.setXmlName(new QName("", "linkId"));
/* 567 */     elemField.setXmlType(new QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
/* 568 */     elemField.setNillable(true);
/* 569 */     typeDesc.addFieldDesc(elemField);
/* 570 */     elemField = new ElementDesc();
/* 571 */     elemField.setFieldName("content");
/* 572 */     elemField.setXmlName(new QName("", "content"));
/* 573 */     elemField.setXmlType(new QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
/* 574 */     elemField.setNillable(true);
/* 575 */     typeDesc.addFieldDesc(elemField);
/* 576 */     elemField = new ElementDesc();
/* 577 */     elemField.setFieldName("effectiveDate");
/* 578 */     elemField.setXmlName(new QName("", "effectiveDate"));
/* 579 */     elemField.setXmlType(new QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
/* 580 */     elemField.setNillable(true);
/* 581 */     typeDesc.addFieldDesc(elemField);
/* 582 */     elemField = new ElementDesc();
/* 583 */     elemField.setFieldName("expireDate");
/* 584 */     elemField.setXmlName(new QName("", "expireDate"));
/* 585 */     elemField.setXmlType(new QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
/* 586 */     elemField.setNillable(true);
/* 587 */     typeDesc.addFieldDesc(elemField);
/* 588 */     elemField = new ElementDesc();
/* 589 */     elemField.setFieldName("time_stamp");
/* 590 */     elemField.setXmlName(new QName("", "time_stamp"));
/* 591 */     elemField.setXmlType(new QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
/* 592 */     elemField.setNillable(true);
/* 593 */     typeDesc.addFieldDesc(elemField);
/* 594 */     elemField = new ElementDesc();
/* 595 */     elemField.setFieldName("encodeStr");
/* 596 */     elemField.setXmlName(new QName("", "encodeStr"));
/* 597 */     elemField.setXmlType(new QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
/* 598 */     elemField.setNillable(true);
/* 599 */     typeDesc.addFieldDesc(elemField);
/*     */   }
/*     */ 
/*     */   public String getNotifySPURL()
/*     */   {
/*  44 */     return this.notifySPURL;
/*     */   }
/*     */ 
/*     */   public void setNotifySPURL(String notifySPURL) {
/*  48 */     this.notifySPURL = notifySPURL;
/*     */   }
/*     */ 
/*     */   public OrderRelationUpdateNotifyRequest()
/*     */   {
/*     */   }
/*     */ 
/*     */   public OrderRelationUpdateNotifyRequest(String recordSequenceId, Integer userIdType, String userId, String serviceType, String spId, String productId, Integer updateType, String updateTime, String updateDesc, String linkId, String content, String effectiveDate, String expireDate, String time_stamp, String encodeStr)
/*     */   {
/*  70 */     this.recordSequenceId = recordSequenceId;
/*  71 */     this.userIdType = userIdType;
/*  72 */     this.userId = userId;
/*  73 */     this.serviceType = serviceType;
/*  74 */     this.spId = spId;
/*  75 */     this.productId = productId;
/*  76 */     this.updateType = updateType;
/*  77 */     this.updateTime = updateTime;
/*  78 */     this.updateDesc = updateDesc;
/*  79 */     this.linkId = linkId;
/*  80 */     this.content = content;
/*  81 */     this.effectiveDate = effectiveDate;
/*  82 */     this.expireDate = expireDate;
/*  83 */     this.time_stamp = time_stamp;
/*  84 */     this.encodeStr = encodeStr;
/*     */   }
/*     */ 
/*     */   public String getRecordSequenceId()
/*     */   {
/*  94 */     return this.recordSequenceId;
/*     */   }
/*     */ 
/*     */   public void setRecordSequenceId(String recordSequenceId)
/*     */   {
/* 104 */     this.recordSequenceId = recordSequenceId;
/*     */   }
/*     */ 
/*     */   public Integer getUserIdType()
/*     */   {
/* 114 */     return this.userIdType;
/*     */   }
/*     */ 
/*     */   public void setUserIdType(Integer userIdType)
/*     */   {
/* 124 */     this.userIdType = userIdType;
/*     */   }
/*     */ 
/*     */   public String getUserId()
/*     */   {
/* 134 */     return this.userId;
/*     */   }
/*     */ 
/*     */   public void setUserId(String userId)
/*     */   {
/* 144 */     this.userId = userId;
/*     */   }
/*     */ 
/*     */   public String getServiceType()
/*     */   {
/* 154 */     return this.serviceType;
/*     */   }
/*     */ 
/*     */   public void setServiceType(String serviceType)
/*     */   {
/* 164 */     this.serviceType = serviceType;
/*     */   }
/*     */ 
/*     */   public String getSpId()
/*     */   {
/* 174 */     return this.spId;
/*     */   }
/*     */ 
/*     */   public void setSpId(String spId)
/*     */   {
/* 184 */     this.spId = spId;
/*     */   }
/*     */ 
/*     */   public String getProductId()
/*     */   {
/* 194 */     return this.productId;
/*     */   }
/*     */ 
/*     */   public void setProductId(String productId)
/*     */   {
/* 204 */     this.productId = productId;
/*     */   }
/*     */ 
/*     */   public Integer getUpdateType()
/*     */   {
/* 214 */     return this.updateType;
/*     */   }
/*     */ 
/*     */   public void setUpdateType(Integer updateType)
/*     */   {
/* 224 */     this.updateType = updateType;
/*     */   }
/*     */ 
/*     */   public String getUpdateTime()
/*     */   {
/* 234 */     return this.updateTime;
/*     */   }
/*     */ 
/*     */   public void setUpdateTime(String updateTime)
/*     */   {
/* 244 */     this.updateTime = updateTime;
/*     */   }
/*     */ 
/*     */   public String getUpdateDesc()
/*     */   {
/* 254 */     return this.updateDesc;
/*     */   }
/*     */ 
/*     */   public void setUpdateDesc(String updateDesc)
/*     */   {
/* 264 */     this.updateDesc = updateDesc;
/*     */   }
/*     */ 
/*     */   public String getLinkId()
/*     */   {
/* 274 */     return this.linkId;
/*     */   }
/*     */ 
/*     */   public void setLinkId(String linkId)
/*     */   {
/* 284 */     this.linkId = linkId;
/*     */   }
/*     */ 
/*     */   public String getContent()
/*     */   {
/* 294 */     return this.content;
/*     */   }
/*     */ 
/*     */   public void setContent(String content)
/*     */   {
/* 304 */     this.content = content;
/*     */   }
/*     */ 
/*     */   public String getEffectiveDate()
/*     */   {
/* 314 */     return this.effectiveDate;
/*     */   }
/*     */ 
/*     */   public void setEffectiveDate(String effectiveDate)
/*     */   {
/* 324 */     this.effectiveDate = effectiveDate;
/*     */   }
/*     */ 
/*     */   public String getExpireDate()
/*     */   {
/* 334 */     return this.expireDate;
/*     */   }
/*     */ 
/*     */   public void setExpireDate(String expireDate)
/*     */   {
/* 344 */     this.expireDate = expireDate;
/*     */   }
/*     */ 
/*     */   public String getTime_stamp()
/*     */   {
/* 354 */     return this.time_stamp;
/*     */   }
/*     */ 
/*     */   public void setTime_stamp(String time_stamp)
/*     */   {
/* 364 */     this.time_stamp = time_stamp;
/*     */   }
/*     */ 
/*     */   public String getEncodeStr()
/*     */   {
/* 374 */     return this.encodeStr;
/*     */   }
/*     */ 
/*     */   public void setEncodeStr(String encodeStr)
/*     */   {
/* 384 */     this.encodeStr = encodeStr;
/*     */   }
/*     */ 
/*     */   public synchronized boolean equals(Object obj)
/*     */   {
/* 389 */     if (!(obj instanceof OrderRelationUpdateNotifyRequest)) return false;
/* 390 */     OrderRelationUpdateNotifyRequest other = (OrderRelationUpdateNotifyRequest)obj;
/* 391 */     if (obj == null) return false;
/* 392 */     if (this == obj) return true;
/* 393 */     if (this.__equalsCalc != null) {
/* 394 */       return this.__equalsCalc == obj;
/*     */     }
/* 396 */     this.__equalsCalc = obj;
/*     */ 
/* 398 */     boolean _equals = 
/* 399 */       ((this.recordSequenceId == null) && (other.getRecordSequenceId() == null)) || (
/* 400 */       (this.recordSequenceId != null) && 
/* 401 */       (this.recordSequenceId.equals(other.getRecordSequenceId())) && (
/* 402 */       ((this.userIdType == null) && (other.getUserIdType() == null)) || (
/* 403 */       (this.userIdType != null) && 
/* 404 */       (this.userIdType.equals(other.getUserIdType())) && (
/* 405 */       ((this.userId == null) && (other.getUserId() == null)) || (
/* 406 */       (this.userId != null) && 
/* 407 */       (this.userId.equals(other.getUserId())) && (
/* 408 */       ((this.serviceType == null) && (other.getServiceType() == null)) || (
/* 409 */       (this.serviceType != null) && 
/* 410 */       (this.serviceType.equals(other.getServiceType())) && (
/* 411 */       ((this.spId == null) && (other.getSpId() == null)) || (
/* 412 */       (this.spId != null) && 
/* 413 */       (this.spId.equals(other.getSpId())) && (
/* 414 */       ((this.productId == null) && (other.getProductId() == null)) || (
/* 415 */       (this.productId != null) && 
/* 416 */       (this.productId.equals(other.getProductId())) && (
/* 417 */       ((this.updateType == null) && (other.getUpdateType() == null)) || (
/* 418 */       (this.updateType != null) && 
/* 419 */       (this.updateType.equals(other.getUpdateType())) && (
/* 420 */       ((this.updateTime == null) && (other.getUpdateTime() == null)) || (
/* 421 */       (this.updateTime != null) && 
/* 422 */       (this.updateTime.equals(other.getUpdateTime())) && (
/* 423 */       ((this.updateDesc == null) && (other.getUpdateDesc() == null)) || (
/* 424 */       (this.updateDesc != null) && 
/* 425 */       (this.updateDesc.equals(other.getUpdateDesc())) && (
/* 426 */       ((this.linkId == null) && (other.getLinkId() == null)) || (
/* 427 */       (this.linkId != null) && 
/* 428 */       (this.linkId.equals(other.getLinkId())) && (
/* 429 */       ((this.content == null) && (other.getContent() == null)) || (
/* 430 */       (this.content != null) && 
/* 431 */       (this.content.equals(other.getContent())) && (
/* 432 */       ((this.effectiveDate == null) && (other.getEffectiveDate() == null)) || (
/* 433 */       (this.effectiveDate != null) && 
/* 434 */       (this.effectiveDate.equals(other.getEffectiveDate())) && (
/* 435 */       ((this.expireDate == null) && (other.getExpireDate() == null)) || (
/* 436 */       (this.expireDate != null) && 
/* 437 */       (this.expireDate.equals(other.getExpireDate())) && (
/* 438 */       ((this.time_stamp == null) && (other.getTime_stamp() == null)) || (
/* 439 */       (this.time_stamp != null) && 
/* 440 */       (this.time_stamp.equals(other.getTime_stamp())) && (
/* 441 */       ((this.encodeStr == null) && (other.getEncodeStr() == null)) || (
/* 442 */       (this.encodeStr != null) && 
/* 443 */       (this.encodeStr.equals(other.getEncodeStr())))))))))))))))))))))))))))))));
/* 444 */     this.__equalsCalc = null;
/* 445 */     return _equals;
/*     */   }
/*     */ 
/*     */   public synchronized int hashCode()
/*     */   {
/* 450 */     if (this.__hashCodeCalc) {
/* 451 */       return 0;
/*     */     }
/* 453 */     this.__hashCodeCalc = true;
/* 454 */     int _hashCode = 1;
/* 455 */     if (getRecordSequenceId() != null) {
/* 456 */       _hashCode += getRecordSequenceId().hashCode();
/*     */     }
/* 458 */     if (getUserIdType() != null) {
/* 459 */       _hashCode += getUserIdType().hashCode();
/*     */     }
/* 461 */     if (getUserId() != null) {
/* 462 */       _hashCode += getUserId().hashCode();
/*     */     }
/* 464 */     if (getServiceType() != null) {
/* 465 */       _hashCode += getServiceType().hashCode();
/*     */     }
/* 467 */     if (getSpId() != null) {
/* 468 */       _hashCode += getSpId().hashCode();
/*     */     }
/* 470 */     if (getProductId() != null) {
/* 471 */       _hashCode += getProductId().hashCode();
/*     */     }
/* 473 */     if (getUpdateType() != null) {
/* 474 */       _hashCode += getUpdateType().hashCode();
/*     */     }
/* 476 */     if (getUpdateTime() != null) {
/* 477 */       _hashCode += getUpdateTime().hashCode();
/*     */     }
/* 479 */     if (getUpdateDesc() != null) {
/* 480 */       _hashCode += getUpdateDesc().hashCode();
/*     */     }
/* 482 */     if (getLinkId() != null) {
/* 483 */       _hashCode += getLinkId().hashCode();
/*     */     }
/* 485 */     if (getContent() != null) {
/* 486 */       _hashCode += getContent().hashCode();
/*     */     }
/* 488 */     if (getEffectiveDate() != null) {
/* 489 */       _hashCode += getEffectiveDate().hashCode();
/*     */     }
/* 491 */     if (getExpireDate() != null) {
/* 492 */       _hashCode += getExpireDate().hashCode();
/*     */     }
/* 494 */     if (getTime_stamp() != null) {
/* 495 */       _hashCode += getTime_stamp().hashCode();
/*     */     }
/* 497 */     if (getEncodeStr() != null) {
/* 498 */       _hashCode += getEncodeStr().hashCode();
/*     */     }
/* 500 */     this.__hashCodeCalc = false;
/* 501 */     return _hashCode;
/*     */   }
/*     */ 
/*     */   public static TypeDesc getTypeDesc()
/*     */   {
/* 606 */     return typeDesc;
/*     */   }
/*     */ 
/*     */   public static Serializer getSerializer(String mechType, Class _javaType, QName _xmlType)
/*     */   {
/* 616 */     return 
/* 617 */       new BeanSerializer(
/* 618 */       _javaType, _xmlType, typeDesc);
/*     */   }
/*     */ 
/*     */   public static Deserializer getDeserializer(String mechType, Class _javaType, QName _xmlType)
/*     */   {
/* 628 */     return 
/* 629 */       new BeanDeserializer(
/* 630 */       _javaType, _xmlType, typeDesc);
/*     */   }
/*     */ 
/*     */   public void decodeString(String message)
/*     */     throws DecodeMessageException
/*     */   {
/*     */   }
/*     */ 
/*     */   public String encodeString()
/*     */   {
/* 640 */     return null;
/*     */   }
/*     */ 
/*     */   public MessageType getMessageType()
/*     */   {
/* 645 */     return null;
/*     */   }
/*     */ 
/*     */   public int getServiceId()
/*     */   {
/* 650 */     return 0;
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.message.OrderRelationUpdateNotifyRequest
 * JD-Core Version:    0.6.0
 */