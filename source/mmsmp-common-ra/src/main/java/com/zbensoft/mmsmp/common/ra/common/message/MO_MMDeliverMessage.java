 package com.zbensoft.mmsmp.common.ra.common.message;
 
 import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.cmcc.mm7.vasp.common.MMContent;
 
 public class MO_MMDeliverMessage extends AbstractMessage
   implements Serializable
 {
//   private HttpServletRequest request;
   private List bcclist;
   private List cclist;
   private MMContent Content;
   private String LinkedID;
   private String MM7Version;
   private String MMSRelayServerID;
   private String ReplyChargingID;
   private String Sender;
   private String Subject;
   private Date TimeStamp;
   private List<String> To;
   private String TransactionID;
   private String contentType;
   private String sendurl;
 
   public String getSendurl()
   {
     return this.sendurl;
   }
 
   public void setSendurl(String sendurl) {
     this.sendurl = sendurl;
   }
 
   public String getContentType() {
     return this.contentType;
   }
 
   public void setContentType(String contentType) {
     this.contentType = contentType;
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
 
   public List getBcclist() {
     return this.bcclist;
   }
 
   public void setBcclist(List bcclist) {
     this.bcclist = bcclist;
   }
 
   public List getCclist() {
     return this.cclist;
   }
 
   public void setCclist(List cclist) {
     this.cclist = cclist;
   }
 
   public MMContent getContent() {
     return this.Content;
   }
 
   public void setContent(MMContent content) {
     this.Content = content;
   }
 
   public String getLinkedID() {
     return this.LinkedID;
   }
 
   public void setLinkedID(String linkedID) {
     this.LinkedID = linkedID;
   }
 
   public String getMM7Version() {
     return this.MM7Version;
   }
 
   public void setMM7Version(String mM7Version) {
     this.MM7Version = mM7Version;
   }
 
   public String getMMSRelayServerID() {
     return this.MMSRelayServerID;
   }
 
   public void setMMSRelayServerID(String mMSRelayServerID) {
     this.MMSRelayServerID = mMSRelayServerID;
   }
 
   public String getReplyChargingID()
   {
     return this.ReplyChargingID;
   }
 
   public void setReplyChargingID(String replyChargingID) {
     this.ReplyChargingID = replyChargingID;
   }
 
   public String getSender() {
     return this.Sender;
   }
 
   public void setSender(String sender) {
     this.Sender = sender;
   }
 
   public String getSubject() {
     return this.Subject;
   }
 
   public void setSubject(String subject) {
     this.Subject = subject;
   }
 
   public Date getTimeStamp() {
     return this.TimeStamp;
   }
 
   public void setTimeStamp(Date timeStamp) {
     this.TimeStamp = timeStamp;
   }
 
   public List<String> getTo() {
     return this.To;
   }
 
   public void setTo(List<String> to) {
     this.To = to;
   }
 
   public String getTransactionID() {
     return this.TransactionID;
   }
 
   public void setTransactionID(String transactionID) {
     this.TransactionID = transactionID;
   }

   public HttpServletRequest getRequest() {
	// TODO Auto-generated method stub
	return null;
   }
 
//   public HttpServletRequest getRequest() {
//     return this.request;
//   }
// 
//   public void setRequest(HttpServletRequest request) {
//     this.request = request;
//   }
 }
