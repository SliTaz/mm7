package com.zbensoft.mmsmp.sp.ra.spagent.test.corebiz;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MoMms {
	public static String getMms(String phone, String accnumber, String vaspid, String vasid, String servicecode,
			String linkid) {
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><env:Envelope xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\"><env:Header><mm7:TransactionID xmlns:mm7=\"http://www.3gpp.org/ftp/Specs/archive/23_series/23.140/schema/REL-6-MM7-1-0\" env:mustUnderstand=\"1\">111111111111111111111</mm7:TransactionID></env:Header><env:Body><DeliverReq xmlns=\"http://www.3gpp.org/ftp/Specs/archive/23_series/23.140/schema/REL-6-MM7-1-0\"><MM7Version>6.3.0</MM7Version><SenderIdentification><VASPID>"
				+ vaspid + "</VASPID>" + "<VASID>" + vasid + "</VASID>" + "<SenderAddress>10658888</SenderAddress>"
				+ "</SenderIdentification>" + "<Recipients>" + "<To><Number>" + accnumber + "</Number></To>"
				+ "</Recipients>" + "<ServiceCode>" + servicecode + "</ServiceCode>" + "<LinkedID>" + linkid
				+ "</LinkedID>" + "<TimeStamp>" + new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date())
				+ "+08:00" + "</TimeStamp>" + "<Priority>Normal</Priority>" + "<Subject>test</Subject>"
				+ "<Sender><Number>" + phone + "</Number></Sender>" + "<MMSRelayServerID>127.0.0.1</MMSRelayServerID>"
				+ "</DeliverReq>" + "</env:Body></env:Envelope>";

		return xml;
	}

	public static String getMms(String chargePhoneNumber, String phone, String accnumber, String vaspid, String vasid,
			String servicecode, String linkid) {
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><env:Envelope xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\"><env:Header><mm7:TransactionID xmlns:mm7=\"http://www.3gpp.org/ftp/Specs/archive/23_series/23.140/schema/REL-6-MM7-1-0\" env:mustUnderstand=\"1\">111111111111111111111</mm7:TransactionID></env:Header><env:Body><DeliverReq xmlns=\"http://www.3gpp.org/ftp/Specs/archive/23_series/23.140/schema/REL-6-MM7-1-0\"><SubmitReq><MM7Version>6.3.0</MM7Version><SenderIdentification><VASPID>"
				+ vaspid + "</VASPID>" + "<VASID>" + vasid + "</VASID>" + "<SenderAddress>10658888</SenderAddress>"
				+ "</SenderIdentification>" + "<Recipients>" + "<To><Number>" + phone + "</Number></To>"
				+ "</Recipients>" + "<ServiceCode>" + servicecode + "</ServiceCode>" + "<LinkedID>" + linkid
				+ "</LinkedID>" + "<TimeStamp>" + new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date())
				+ "+08:00" + "</TimeStamp>" + "<Priority>Normal</Priority>" + "<Subject>test</Subject>"
				+ "<Sender><Number>" + accnumber + "</Number></Sender>"
				+ "<MMSRelayServerID>127.0.0.1</MMSRelayServerID>" + "<ChargedPartyID>" + chargePhoneNumber
				+ "</ChargedPartyID>" + "</SubmitReq>" + "</DeliverReq>" + "</env:Body></env:Envelope>";

		return xml;
	}
}
