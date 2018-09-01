 package com.zbensoft.mmsmp.corebiz.message;
 
 import com.zbensoft.mmsmp.common.ra.common.message.AbstractMessage;
 import com.zbensoft.mmsmp.common.ra.common.message.DecodeMessageException;
 import com.zbensoft.mmsmp.common.ra.common.message.MessageType;
 import java.io.Serializable;
 
 
 
 
 
 
 
 
 
 
 public class ProxyPayMessage extends AbstractMessage implements Serializable
 {
   private String phoneNumber = null;
   
   private String cpID = null;
   
   private String serviceId = null;
   
   private String serviceName = null;
   
   private String feeType = null;
   
   private double fee = 0.0D;
   
   private String status = null;
   
   private String validateCode = null;
   
   private String accountId = null;
   
   private String spId = null;
   
   private String userType = null;
   
   private String perorid = null;
   
   private String productId = null;
   
   private String productName = null;
   
   private String chargeParty = null;
   
   private String createDate = null;
   
   private String updateDate = null;
   
   private String smsText = null;
   
   private long payRequestTime = 0L;
   
   private String cooperKey = null;
   
   private String notifyURL = null;
   
 
 
 
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
   
   public String getPhoneNumber()
   {
     return this.phoneNumber;
   }
   
 
   public void setPhoneNumber(String phoneNumber)
   {
     this.phoneNumber = phoneNumber;
   }
   
 
   public String getCpID()
   {
     return this.cpID;
   }
   
 
   public void setCpID(String cpID)
   {
     this.cpID = cpID;
   }
   
 
   public String getServiceName()
   {
     return this.serviceName;
   }
   
 
   public void setServiceName(String serviceName)
   {
     this.serviceName = serviceName;
   }
   
 
   public String getFeeType()
   {
     return this.feeType;
   }
   
 
   public void setFeeType(String feeType)
   {
     this.feeType = feeType;
   }
   
   public double getFee()
   {
     return this.fee;
   }
   
 
   public void setFee(double fee)
   {
     this.fee = fee;
   }
   
   public String getStatus()
   {
     return this.status;
   }
   
 
   public void setStatus(String status)
   {
     this.status = status;
   }
   
 
   public String getValidateCode()
   {
     return this.validateCode;
   }
   
 
   public void setValidateCode(String validateCode)
   {
     this.validateCode = validateCode;
   }
   
 
   public String getAccountId()
   {
     return this.accountId;
   }
   
 
   public void setAccountId(String accountId)
   {
     this.accountId = accountId;
   }
   
   public String getSpId()
   {
     return this.spId;
   }
   
 
   public void setSpId(String spId)
   {
     this.spId = spId;
   }
   
 
   public String getUserType()
   {
     return this.userType;
   }
   
 
   public void setUserType(String userType)
   {
     this.userType = userType;
   }
   
 
   public String getPerorid()
   {
     return this.perorid;
   }
   
 
   public void setPerorid(String perorid)
   {
     this.perorid = perorid;
   }
   
 
   public String getProductId()
   {
     return this.productId;
   }
   
 
   public void setProductId(String productId)
   {
     this.productId = productId;
   }
   
   public String getProductName()
   {
     return this.productName;
   }
   
   public void setProductName(String productName)
   {
     this.productName = productName;
   }
   
   public String getChargeParty()
   {
     return this.chargeParty;
   }
   
 
   public void setChargeParty(String chargeParty)
   {
     this.chargeParty = chargeParty;
   }
   
 
   public String getCreateDate()
   {
     return this.createDate;
   }
   
 
   public void setCreateDate(String createDate)
   {
     this.createDate = createDate;
   }
   
   public String getUpdateDate()
   {
     return this.updateDate;
   }
   
 
   public void setUpdateDate(String updateDate)
   {
     this.updateDate = updateDate;
   }
   
 
   public void setServiceId(String serviceId)
   {
     this.serviceId = serviceId;
   }
   
 
   public String getSmsText()
   {
     return this.smsText;
   }
   
 
   public void setSmsText(String smsText)
   {
     this.smsText = smsText;
   }
   
   public int getServiceId()
   {
     try {
       return new Integer(this.serviceId).intValue();
     }
     catch (Exception e) {}
     return -1;
   }
   
 
 
   public long getPayRequestTime()
   {
     return this.payRequestTime;
   }
   
 
   public void setPayRequestTime(long payRequestTime)
   {
     this.payRequestTime = payRequestTime;
   }
   
 
   public String getCooperKey()
   {
     return this.cooperKey;
   }
   
 
   public void setCooperKey(String cooperKey)
   {
     this.cooperKey = cooperKey;
   }
   
 
   public String getNotifyURL()
   {
     return this.notifyURL;
   }
   
 
   public void setNotifyURL(String notifyURL)
   {
     this.notifyURL = notifyURL;
   }
 }





