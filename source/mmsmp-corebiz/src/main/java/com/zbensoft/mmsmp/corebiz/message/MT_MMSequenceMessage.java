 package com.zbensoft.mmsmp.corebiz.message;
 
 import com.zbensoft.mmsmp.common.ra.common.message.AbstractMessage;
 import com.zbensoft.mmsmp.common.ra.common.message.DecodeMessageException;
 import com.zbensoft.mmsmp.common.ra.common.message.MessageType;
 import java.io.Serializable;
 
 
 
 
 
 
 
 
 
 public class MT_MMSequenceMessage extends AbstractMessage implements Serializable
 {
   private String sequence;
   private String phone;
   private String serviceCode;
   private String sourceMessageid;
   private String spUrl;
   
   public String getSpUrl()
   {
     return this.spUrl;
   }
   
   public void setSpUrl(String spUrl) {
     this.spUrl = spUrl;
   }
   
   public String getSourceMessageid() {
     return this.sourceMessageid;
   }
   
   public void setSourceMessageid(String sourceMessageid) {
     this.sourceMessageid = sourceMessageid;
   }
   
   public String getSequence() {
     return this.sequence;
   }
   
   public void setSequence(String sequence) {
     this.sequence = sequence;
   }
   
   public String getPhone() {
     return this.phone;
   }
   
   public void setPhone(String phone) {
     this.phone = phone;
   }
   
   public String getServiceCode() {
     return this.serviceCode;
   }
   
   public void setServiceCode(String serviceCode) {
     this.serviceCode = serviceCode;
   }
   
 
   public void decodeString(String paramString)
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
 }





