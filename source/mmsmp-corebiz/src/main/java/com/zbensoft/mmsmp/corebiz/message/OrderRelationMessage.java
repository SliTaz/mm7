 package com.zbensoft.mmsmp.corebiz.message;
 
 import com.zbensoft.mmsmp.common.ra.common.message.AbstractMessage;
 import com.zbensoft.mmsmp.common.ra.common.message.DecodeMessageException;
 import com.zbensoft.mmsmp.common.ra.common.message.MessageType;
 import com.zbensoft.mmsmp.vas.sjb.unibusiness.OrderRelationRequest;
 
 
 
 
 
 public class OrderRelationMessage
   extends AbstractMessage
 {
   private static final long serialVersionUID = 1L;
   OrderRelationRequest orderRelationRequest;
   String messageId;
   
   public void decodeString(String message)
     throws DecodeMessageException
   {}
   
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
   
   public OrderRelationRequest getOrderRelationRequest() {
     return this.orderRelationRequest;
   }
   
   public void setOrderRelationRequest(OrderRelationRequest orderRelationRequest) {
     this.orderRelationRequest = orderRelationRequest;
   }
   
   public String getMessageId() {
     return this.messageId;
   }
   
   public void setMessageId(String messageId) {
     this.messageId = messageId;
   }
 }





