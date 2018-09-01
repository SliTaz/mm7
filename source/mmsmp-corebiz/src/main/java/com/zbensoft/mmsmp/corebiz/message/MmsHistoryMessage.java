 package com.zbensoft.mmsmp.corebiz.message;
 
 import java.io.Serializable;
 import java.util.Date;
 
 
 
 
 
 
 
 
 
 
 
 public class MmsHistoryMessage
   implements Serializable
 {
   private String globalMessageId;
   private String status;
   private String userPhone;
   private String transactionId;
   private String spId;
   private String serviceId;
   private String messageId;
   private String serviceCode;
   private String mmsGreCode;
   private String reqId;
   private int type;
   private Date receiveDate;
   
   public Date getReceiveDate()
   {
     return this.receiveDate;
   }
   
   public void setReceiveDate(Date receiveDate)
   {
     this.receiveDate = receiveDate;
   }
   
 
   public String getStatus()
   {
     return this.status;
   }
   
   public String getGlobalMessageId()
   {
     return this.globalMessageId;
   }
   
   public void setGlobalMessageId(String globalMessageId)
   {
     this.globalMessageId = globalMessageId;
   }
   
 
   public void setStatus(String status)
   {
     this.status = status;
   }
   
 
   public String getUserPhone()
   {
     return this.userPhone;
   }
   
 
   public void setUserPhone(String userPhone)
   {
     this.userPhone = userPhone;
   }
   
 
   public String getTransactionId()
   {
     return this.transactionId;
   }
   
 
   public void setTransactionId(String transactionId)
   {
     this.transactionId = transactionId;
   }
   
 
   public String getSpId()
   {
     return this.spId;
   }
   
 
   public void setSpId(String spId)
   {
     this.spId = spId;
   }
   
 
   public String getServiceId()
   {
     return this.serviceId;
   }
   
 
   public void setServiceId(String serviceId)
   {
     this.serviceId = serviceId;
   }
   
 
   public String getMessageId()
   {
     return this.messageId;
   }
   
 
   public void setMessageId(String messageId)
   {
     this.messageId = messageId;
   }
   
 
   public String getServiceCode()
   {
     return this.serviceCode;
   }
   
 
   public void setServiceCode(String serviceCode)
   {
     this.serviceCode = serviceCode;
   }
   
 
   public String getMmsGreCode()
   {
     return this.mmsGreCode;
   }
   
 
 
   public void setMmsGreCode(String mmsGreCode)
   {
     this.mmsGreCode = mmsGreCode;
   }
   
 
   public String getReqId()
   {
     return this.reqId;
   }
   
 
 
   public void setReqId(String reqId)
   {
     this.reqId = reqId;
   }
   
   public int getType()
   {
     return this.type;
   }
   
 
   public void setType(int type)
   {
     this.type = type;
   }
 }





