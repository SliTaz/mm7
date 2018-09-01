 package com.zbensoft.mmsmp.corebiz.service.mm7;
 
 import java.io.PrintStream;
 import org.apache.log4j.Logger;
 
 
 
 public class SubmitResp
 {
   private static final Logger log = Logger.getLogger(SubmitResp.class);
   static String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><soap-env:Envelope xmlns:soap-env=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap-env:Header><TransactionID xmlns=\"http://www.3gpp.org/ftp/Specs/archive/23_series/23.140/schema/REL-5-MM7-1-0\" soap-env:mustUnderstand=\"1\">1234567890</TransactionID></soap-env:Header><soap-env:Body><mm7:SubmitRsp xmlns:mm7=\"http://www.3gpp.org/ftp/Specs/archive/23_series/23.140/schema/REL-5-MM7-1-0\"><MM7Version>5.2.0</MM7Version><Status><StatusCode>1000</StatusCode><StatusText>mmsmp recevice ok</StatusText></Status><MessageID>MessageID001</MessageID></mm7:SubmitRsp></soap-env:Body></soap-env:Envelope>";
   
 
 
   private String TransactionID;
   
 
   private String MM7Version;
   
 
   private String StatusCode;
   
 
   private String StatusText;
   
 
   private String MessageID;
   
 
 
   public static String getSubmitResp(String transactionID, String messageid, String statuscode, String statusText)
	{
		if (statuscode == null) {
			statuscode = "1000";
		}
		if (statusText == null) {
			statusText = "First:Mmsmp Receive Success";
		}
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><soap-env:Envelope xmlns:soap-env=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap-env:Header><TransactionID xmlns=\"http://www.3gpp.org/ftp/Specs/archive/23_series/23.140/schema/REL-5-MM7-1-0\" soap-env:mustUnderstand=\"1\">"
				+transactionID + "</TransactionID>" + "</soap-env:Header>" + "<soap-env:Body>"
				+ "<mm7:SubmitRsp xmlns:mm7=\"http://www.3gpp.org/ftp/Specs/archive/23_series/23.140/schema/REL-5-MM7-1-0\">"
				+ "<MM7Version>5.2.0</MM7Version>" + "<Status><StatusCode>" + statuscode + "</StatusCode>"
				+ "<StatusText>" + statusText + "</StatusText></Status>" + "<MessageID>" + messageid + "</MessageID>"
				+ "</soap-env:Body></soap-env:Envelope>";
		return xml;
	}
   
   public SubmitResp parser(String xml)
   {
     if (xml == null) return this;
     RequestXmlHandler do_xml = new RequestXmlHandler();
     this.TransactionID = do_xml.getNodeValue(xml, "TransactionID", "TransactionID");
     String v = do_xml.getNodeValue(xml, "SubmitRsp", "SubmitRsp");
     this.MM7Version = do_xml.getNodeValue(v, "MM7Version", "MM7Version");
     this.StatusCode = do_xml.getNodeValue(v, "StatusCode", "StatusCode");
     this.StatusText = do_xml.getNodeValue(v, "StatusText", "StatusText");
     this.MessageID = do_xml.getNodeValue(v, "MessageID", "MessageID");
     return this;
   }
   
   public SubmitResp errorXmlParser(String xml) {
     if (xml == null) return this;
     RequestXmlHandler do_xml = new RequestXmlHandler();
     this.TransactionID = do_xml.getNodeValue(xml, "TransactionID", "TransactionID");
     String v = do_xml.getNodeValue(xml, "RSErrorRsp", "RSErrorRsp");
     this.MM7Version = do_xml.getNodeValue(v, "MM7Version", "MM7Version");
     this.StatusCode = do_xml.getNodeValue(v, "StatusCode", "StatusCode");
     this.StatusText = do_xml.getNodeValue(v, "StatusText", "StatusText");
     return this;
   }
   
   public String getTransactionID() {
     return this.TransactionID;
   }
   
   public String getMM7Version() {
     return this.MM7Version;
   }
   
   public String getStatusCode() {
     return this.StatusCode;
   }
   
   public String getStatusText() {
     return this.StatusText;
   }
   
   public String getMessageID() {
     return this.MessageID;
   }
   
   public void setTransactionID(String transactionID) {
     this.TransactionID = transactionID;
   }
   
   public void setMM7Version(String mM7Version) {
     this.MM7Version = mM7Version;
   }
   
   public void setStatusCode(String statusCode) {
     this.StatusCode = statusCode;
   }
   
   public void setStatusText(String statusText) {
     this.StatusText = statusText;
   }
   
   public void setMessageID(String messageID) {
     this.MessageID = messageID;
   }
   
   public static void main(String[] args) {
     SubmitResp x = new SubmitResp();
     x.parser(xml);
     System.out.println(x);
   }
   
   public String toString()
   {
     StringBuffer sb = new StringBuffer(super.toString());
     sb.append(" [");
     sb.append(" TransactionID=").append(this.TransactionID);
     sb.append(" MM7Version=").append(this.MM7Version);
     sb.append(" StatusCode=").append(this.StatusCode);
     sb.append(" StatusText=").append(this.StatusText);
     sb.append(" MessageID=").append(this.MessageID);
     sb.append(" ]");
     return sb.toString();
   }
 }





