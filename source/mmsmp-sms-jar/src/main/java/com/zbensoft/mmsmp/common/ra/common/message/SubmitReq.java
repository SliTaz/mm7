/*     */ package com.zbensoft.mmsmp.common.ra.common.message;
/*     */ 
/*     */

/*     */
/*     */ public class SubmitReq
/*     */ {
/*   4 */   static String mms = "this is a multi-part message in MIME format\r\n\r\n\r\n---------------------------------------------------------NextPart_1\r\nContent-Type:text/xml;charset=\"GB2312\"\r\nContent-Transfer-Encoding:8bit\r\nContent-ID:</tnn-200102/mm7-vasp>\r\n\r\n<?xml version=\"1.0\" encoding=\"GB2312\"?><env:Envelope xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\"><env:Header><mm7:TransactionID xmlns:mm7=\"http://www.3gpp.org/ftp/Specs/archive/23_series/23.140/schema/REL-6-MM7-1-0\" env:mustUnderstand=\"1\">1234567890</mm7:TransactionID></env:Header><env:Body><SubmitReq xmlns=\"http://www.3gpp.org/ftp/Specs/archive/23_series/23.140/schema/REL-6-MM7-1-0\"><MM7Version>6.3.0</MM7Version><SenderIdentification><VASPID>111</VASPID><VASID>4321</VASID><SenderAddress>18910229166</SenderAddress></SenderIdentification><Recipients><To><Number>8613111111111</Number></To></Recipients><ServiceCode>1234</ServiceCode><DeliveryReport>true</DeliveryReport><ReadReply>true</ReadReply><Subject>mmsSubject</Subject><ChargedParty>Recipient</ChargedParty><ChargedPartyID>8613111111111</ChargedPartyID></SubmitReq></env:Body></env:Envelope>\r\n---------------------------------------------------------NextPart_1\r\nContent-Type:multipart/mixed;boundary=\"--------------------------------------------------------SubPart_2\"\r\nContent-ID:<SaturnPics-01020930>\r\nContent-Transfer-Encoding:8bit";
/*     */   private String TransactionID;
/*     */   private String MM7Version;
/*     */   private String VASPID;
/*     */   private String VASID;
/*     */   private String SenderAddress;
/*     */   private String To;
/*     */   private String ServiceCode;
/*     */   private String LinkedID;
/*     */   private String DeliveryReport;
/*     */   private String Subject;
/*     */   private String ChargedParty;
/*     */   private String ChargedPartyID;
/*     */ 
/*     */   public SubmitReq parser(String xml)
/*     */   {
/*  54 */     RequestXmlHandler do_xml = new RequestXmlHandler();
/*  55 */     String value = do_xml.getNodeValue(xml, "env:Envelope", "env:Envelope");
/*  56 */     this.TransactionID = do_xml.getNodeValue(value, "TransactionID", "TransactionID");
/*  57 */     String v = do_xml.getNodeValue(xml, "SubmitReq", "SubmitReq");
/*  58 */     if (v == null) return this;
/*  59 */     this.MM7Version = do_xml.getNodeValue(v, "MM7Version", "MM7Version");
/*  60 */     this.VASPID = do_xml.getNodeValue(v, "VASPID", "VASPID");
/*  61 */     this.VASID = do_xml.getNodeValue(v, "VASID", "VASID");
/*  62 */     this.SenderAddress = do_xml.getNodeValue(v, "SenderAddress", "SenderAddress");
/*  63 */     String _to = do_xml.getNodeValue(v, "To", "To");
/*  64 */     this.To = do_xml.getNodeValue(_to, "Number", "Number");
/*  65 */     this.ServiceCode = do_xml.getNodeValue(v, "ServiceCode", "ServiceCode");
/*  66 */     this.LinkedID = do_xml.getNodeValue(v, "LinkedID", "LinkedID");
/*  67 */     this.DeliveryReport = do_xml.getNodeValue(v, "DeliveryReport", "DeliveryReport");
/*  68 */     this.Subject = do_xml.getNodeValue(v, "Subject", "Subject");
/*  69 */     this.ChargedParty = do_xml.getNodeValue(v, "ChargedParty", "ChargedParty");
/*  70 */     this.ChargedPartyID = do_xml.getNodeValue(v, "ChargedPartyID", "ChargedPartyID");
/*  71 */     return this;
/*     */   }
/*     */ 
/*     */   public String getTransactionID() {
/*  75 */     return this.TransactionID;
/*     */   }
/*     */ 
/*     */   public void setTransactionID(String transactionID) {
/*  79 */     this.TransactionID = transactionID;
/*     */   }
/*     */ 
/*     */   public String getMM7Version() {
/*  83 */     return this.MM7Version;
/*     */   }
/*     */ 
/*     */   public void setMM7Version(String mM7Version) {
/*  87 */     this.MM7Version = mM7Version;
/*     */   }
/*     */ 
/*     */   public String getVASPID() {
/*  91 */     return this.VASPID;
/*     */   }
/*     */ 
/*     */   public void setVASPID(String vASPID) {
/*  95 */     this.VASPID = vASPID;
/*     */   }
/*     */ 
/*     */   public String getVASID() {
/*  99 */     return this.VASID;
/*     */   }
/*     */ 
/*     */   public void setVASID(String vASID) {
/* 103 */     this.VASID = vASID;
/*     */   }
/*     */ 
/*     */   public String getSenderAddress() {
/* 107 */     return this.SenderAddress;
/*     */   }
/*     */ 
/*     */   public void setSenderAddress(String senderAddress) {
/* 111 */     this.SenderAddress = senderAddress;
/*     */   }
/*     */ 
/*     */   public String getTo() {
/* 115 */     return this.To;
/*     */   }
/*     */ 
/*     */   public void setTo(String to) {
/* 119 */     this.To = to;
/*     */   }
/*     */ 
/*     */   public String getServiceCode() {
/* 123 */     return this.ServiceCode;
/*     */   }
/*     */ 
/*     */   public void setServiceCode(String serviceCode) {
/* 127 */     this.ServiceCode = serviceCode;
/*     */   }
/*     */ 
/*     */   public String getDeliveryReport() {
/* 131 */     return this.DeliveryReport;
/*     */   }
/*     */ 
/*     */   public void setDeliveryReport(String deliveryReport) {
/* 135 */     this.DeliveryReport = deliveryReport;
/*     */   }
/*     */ 
/*     */   public String getLinkedID() {
/* 139 */     return this.LinkedID;
/*     */   }
/*     */ 
/*     */   public void setLinkedID(String linkedID) {
/* 143 */     this.LinkedID = linkedID;
/*     */   }
/*     */ 
/*     */   public String getSubject() {
/* 147 */     return this.Subject;
/*     */   }
/*     */ 
/*     */   public void setSubject(String subject) {
/* 151 */     this.Subject = subject;
/*     */   }
/*     */ 
/*     */   public String getChargedParty() {
/* 155 */     return this.ChargedParty;
/*     */   }
/*     */ 
/*     */   public void setChargedParty(String chargedParty) {
/* 159 */     this.ChargedParty = chargedParty;
/*     */   }
/*     */ 
/*     */   public String getChargedPartyID() {
/* 163 */     return this.ChargedPartyID;
/*     */   }
/*     */ 
/*     */   public void setChargedPartyID(String chargedPartyID) {
/* 167 */     this.ChargedPartyID = chargedPartyID;
/*     */   }
/*     */ 
/*     */   public static void main(String[] args) {
/* 171 */     SubmitReq x = new SubmitReq();
/* 172 */     x.parser(mms);
/* 173 */     System.out.println(x);
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 178 */     StringBuffer sb = new StringBuffer(super.toString());
/* 179 */     sb.append(" [");
/* 180 */     sb.append(" TransactionID=").append(this.TransactionID);
/* 181 */     sb.append(" MM7Version=").append(this.MM7Version);
/* 182 */     sb.append(" VASPID=").append(this.VASPID);
/* 183 */     sb.append(" VASID=").append(this.VASID);
/* 184 */     sb.append(" SenderAddress=").append(this.SenderAddress);
/* 185 */     sb.append(" To=").append(this.To);
/* 186 */     sb.append(" ServiceCode=").append(this.ServiceCode);
/* 187 */     sb.append(" DeliveryReport=").append(this.DeliveryReport);
/* 188 */     sb.append(" Subject=").append(this.Subject);
/* 189 */     sb.append(" ChargedParty=").append(this.ChargedParty);
/* 190 */     sb.append(" ChargedPartyID=").append(this.ChargedPartyID);
/* 191 */     sb.append(" ]");
/* 192 */     return sb.toString();
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.message.SubmitReq
 * JD-Core Version:    0.6.0
 */