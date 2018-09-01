package com.zbensoft.mmsmp.mms.ra.mmsc;

import com.zbensoft.mmsmp.common.ra.utils.RequestXmlHandler;
import java.io.PrintStream;

public class SubmitReq {
	static String mms = "this is a multi-part message in MIME format\r\n\r\n\r\n---------------------------------------------------------NextPart_1\r\nContent-Type:text/xml;charset=\"GB2312\"\r\nContent-Transfer-Encoding:8bit\r\nContent-ID:</tnn-200102/mm7-vasp>\r\n\r\n<?xml version=\"1.0\" encoding=\"GB2312\"?><env:Envelope xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\"><env:Header><mm7:TransactionID xmlns:mm7=\"http://www.3gpp.org/ftp/Specs/archive/23_series/23.140/schema/REL-6-MM7-1-0\" env:mustUnderstand=\"1\">1234567890</mm7:TransactionID></env:Header><env:Body><SubmitReq xmlns=\"http://www.3gpp.org/ftp/Specs/archive/23_series/23.140/schema/REL-6-MM7-1-0\"><MM7Version>6.3.0</MM7Version><SenderIdentification><VASPID>111</VASPID><VASID>4321</VASID><SenderAddress>18910229166</SenderAddress></SenderIdentification><Recipients><To><Number>8613111111111</Number></To></Recipients><ServiceCode>1234</ServiceCode><DeliveryReport>true</DeliveryReport><ReadReply>true</ReadReply><Subject>mmsSubject</Subject><ChargedParty>Recipient</ChargedParty><ChargedPartyID>8613111111111</ChargedPartyID></SubmitReq></env:Body></env:Envelope>\r\n---------------------------------------------------------NextPart_1\r\nContent-Type:multipart/mixed;boundary=\"--------------------------------------------------------SubPart_2\"\r\nContent-ID:<SaturnPics-01020930>\r\nContent-Transfer-Encoding:8bit";
	private String TransactionID;
	private String MM7Version;
	private String VASPID;
	private String VASID;
	private String SenderAddress;
	private String To;
	private String ServiceCode;
	private String LinkedID;
	private String DeliveryReport;
	private String Subject;
	private String ChargedParty;
	private String ChargedPartyID;

	public SubmitReq parser(String xml) {
		RequestXmlHandler do_xml = new RequestXmlHandler();
		String value = do_xml.getNodeValue(xml, "env:Envelope", "env:Envelope");
		this.TransactionID = do_xml.getNodeValue(value, "TransactionID", "TransactionID");
		String v = do_xml.getNodeValue(xml, "SubmitReq", "SubmitReq");
		if (v == null)
			return this;
		this.MM7Version = do_xml.getNodeValue(v, "MM7Version", "MM7Version");
		this.VASPID = do_xml.getNodeValue(v, "VASPID", "VASPID");
		this.VASID = do_xml.getNodeValue(v, "VASID", "VASID");
		this.SenderAddress = do_xml.getNodeValue(v, "SenderAddress", "SenderAddress");
		String _to = do_xml.getNodeValue(v, "To", "To");
		this.To = do_xml.getNodeValue(_to, "Number", "Number");
		this.ServiceCode = do_xml.getNodeValue(v, "ServiceCode", "ServiceCode");
		this.LinkedID = do_xml.getNodeValue(v, "LinkedID", "LinkedID");
		this.DeliveryReport = do_xml.getNodeValue(v, "DeliveryReport", "DeliveryReport");
		this.Subject = do_xml.getNodeValue(v, "Subject", "Subject");
		this.ChargedParty = do_xml.getNodeValue(v, "ChargedParty", "ChargedParty");
		this.ChargedPartyID = do_xml.getNodeValue(v, "ChargedPartyID", "ChargedPartyID");
		return this;
	}

	public String getTransactionID() {
		return this.TransactionID;
	}

	public void setTransactionID(String transactionID) {
		this.TransactionID = transactionID;
	}

	public String getMM7Version() {
		return this.MM7Version;
	}

	public void setMM7Version(String mM7Version) {
		this.MM7Version = mM7Version;
	}

	public String getVASPID() {
		return this.VASPID;
	}

	public void setVASPID(String vASPID) {
		this.VASPID = vASPID;
	}

	public String getVASID() {
		return this.VASID;
	}

	public void setVASID(String vASID) {
		this.VASID = vASID;
	}

	public String getSenderAddress() {
		return this.SenderAddress;
	}

	public void setSenderAddress(String senderAddress) {
		this.SenderAddress = senderAddress;
	}

	public String getTo() {
		return this.To;
	}

	public void setTo(String to) {
		this.To = to;
	}

	public String getServiceCode() {
		return this.ServiceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.ServiceCode = serviceCode;
	}

	public String getDeliveryReport() {
		return this.DeliveryReport;
	}

	public void setDeliveryReport(String deliveryReport) {
		this.DeliveryReport = deliveryReport;
	}

	public String getLinkedID() {
		return this.LinkedID;
	}

	public void setLinkedID(String linkedID) {
		this.LinkedID = linkedID;
	}

	public String getSubject() {
		return this.Subject;
	}

	public void setSubject(String subject) {
		this.Subject = subject;
	}

	public String getChargedParty() {
		return this.ChargedParty;
	}

	public void setChargedParty(String chargedParty) {
		this.ChargedParty = chargedParty;
	}

	public String getChargedPartyID() {
		return this.ChargedPartyID;
	}

	public void setChargedPartyID(String chargedPartyID) {
		this.ChargedPartyID = chargedPartyID;
	}

	public static void main(String[] args) {
		SubmitReq x = new SubmitReq();
		x.parser(mms);
		System.out.println(x);
	}

	public String toString() {
		StringBuffer sb = new StringBuffer(super.toString());
		sb.append(" [");
		sb.append(" TransactionID=").append(this.TransactionID);
		sb.append(" MM7Version=").append(this.MM7Version);
		sb.append(" VASPID=").append(this.VASPID);
		sb.append(" VASID=").append(this.VASID);
		sb.append(" SenderAddress=").append(this.SenderAddress);
		sb.append(" To=").append(this.To);
		sb.append(" ServiceCode=").append(this.ServiceCode);
		sb.append(" DeliveryReport=").append(this.DeliveryReport);
		sb.append(" Subject=").append(this.Subject);
		sb.append(" ChargedParty=").append(this.ChargedParty);
		sb.append(" ChargedPartyID=").append(this.ChargedPartyID);
		sb.append(" ]");
		return sb.toString();
	}
}
