 package com.zbensoft.mmsmp.corebiz.util;
 
 import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
 
 
 
 
 
 
 public class StringUtil
 {
   public static void main(String[] args)
   {
     String uuid = getUUID();
     System.out.println(uuid);
   }
   
 
 
   public static String getUUID()
   {
     UUID uuid = UUID.randomUUID();
     return uuid.toString().replaceAll("-", "").toUpperCase();
   }
   
 
   public static String getPhone11(String number)
   {
     if (number == null)
     {
       return "";
     }
     if (number.startsWith("86"))
     {
       return number.substring(2).trim();
     }
     if (number.startsWith("+86"))
     {
       return number.substring(3).trim();
     }
     
     return number.trim();
   }
   
   public static String getTime0800()
   {
     Calendar now = Calendar.getInstance();
     Date d = new Date();
     String year = String.valueOf(now.get(1));
     String date = String.valueOf(now.get(5));
     String month = String.valueOf(now.get(2) + 1);
     String hour = String.valueOf(now.get(10));
     String min = String.valueOf(now.get(12));
     String sec = String.valueOf(now.get(13));
     if (date.length() < 2) date = "0" + date;
     if (month.length() < 2) month = "0" + month;
     if (hour.length() < 2) hour = "0" + hour;
     if (min.length() < 2) min = "0" + min;
     if (sec.length() < 2) sec = "0" + sec;
     return year + "-" + month + "-" + date + "T" + hour + ":" + min + ":" + sec + "+08:00";
   }
   
   public static String getReportString(String transactionid, String sendnumber, String recipientnumber, String timestamp, String messageid, int returncode, String desc) {
	        String report = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><env:Envelope xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\"><env:Header><mm7:TransactionID xmlns:mm7=\"http://www.3gpp.org/ftp/Specs/archive/23_series/23.140/schema/REL-6-MM7-1-0\" env:mustUnderstand=\"1\">" + 
			
			     transactionid + "</mm7:TransactionID></env:Header><env:Body>" + 
			      "<DeliveryReportReq xmlns=\"http://www.3gpp.org/ftp/Specs/archive/23_series/23.140/schema/REL-6-MM7-1-0\">" + 
			      "<MM7Version>6.3.0</MM7Version><Sender><Number>" + sendnumber + "</Number></Sender>" + 
			      "<Recipient><Number>" + recipientnumber + "</Number></Recipient><TimeStamp>" + timestamp + "</TimeStamp>" + 
			    "<MMSRelayServerID>600003</MMSRelayServerID><MessageID>" + messageid + "</MessageID>" + 
			      "<MMStatus>" + returncode + "</MMStatus><StatusText>" + desc + "</StatusText>" + 
			      "</DeliveryReportReq></env:Body></env:Envelope>";
			   return report;
   }
   
   public static String getReportString(String transactionid, String sendnumber, String recipientnumber, String timestamp, String messageid, int returncode)
   {
	       String report = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><env:Envelope xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\"><env:Header><mm7:TransactionID xmlns:mm7=\"http://www.3gpp.org/ftp/Specs/archive/23_series/23.140/schema/REL-6-MM7-1-0\" env:mustUnderstand=\"1\">" + 
			     
			
		     transactionid + "</mm7:TransactionID></env:Header><env:Body>" + 
			     "<DeliveryReportReq xmlns=\"http://www.3gpp.org/ftp/Specs/archive/23_series/23.140/schema/REL-6-MM7-1-0\">" + 
		      "<MM7Version>6.3.0</MM7Version><Sender><Number>" + sendnumber + "</Number></Sender>" + 
			      "<Recipient><Number>" + recipientnumber + "</Number></Recipient><TimeStamp>" + timestamp + "</TimeStamp>" + 
		     "<MMSRelayServerID>600003</MMSRelayServerID><MessageID>" + messageid + "</MessageID>" + 
		      "<MMStatus>Second:mmssc Retrieved</MMStatus><StatusText>" + returncode + "</StatusText>" + 
			         "</DeliveryReportReq></env:Body></env:Envelope>";
     return report;
   }
   
   public static String getReportString(String transactionid, String sendnumber, String recipientnumber, String timestamp, String messageid)
   {
	   String report = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><env:Envelope xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\"><env:Header><mm7:TransactionID xmlns:mm7=\"http://www.3gpp.org/ftp/Specs/archive/23_series/23.140/schema/REL-6-MM7-1-0\" env:mustUnderstand=\"1\">" + transactionid + "</mm7:TransactionID></env:Header><env:Body><DeliveryReportReq xmlns=\"http://www.3gpp.org/ftp/Specs/archive/23_series/23.140/schema/REL-6-MM7-1-0\"><MM7Version>6.3.0</MM7Version><Sender><Number>" + sendnumber + "</Number></Sender><Recipient><Number>" + recipientnumber + "</Number></Recipient><TimeStamp>" + timestamp + "</TimeStamp><MMSRelayServerID>600003</MMSRelayServerID><MessageID>" + messageid + "</MessageID><MMStatus>Third:User Retrieved</MMStatus><StatusText>1000</StatusText></DeliveryReportReq></env:Body></env:Envelope>";
     return report;
   }
   
 
   public static String getReportString(String transactionid, String sendnumber, String recipientnumber, String timestamp, String messageid, String status)
   {
	   String report = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><env:Envelope xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\"><env:Header><mm7:TransactionID xmlns:mm7=\"http://www.3gpp.org/ftp/Specs/archive/23_series/23.140/schema/REL-6-MM7-1-0\" env:mustUnderstand=\"1\">" + transactionid + "</mm7:TransactionID></env:Header><env:Body><DeliveryReportReq xmlns=\"http://www.3gpp.org/ftp/Specs/archive/23_series/23.140/schema/REL-6-MM7-1-0\"><MM7Version>6.3.0</MM7Version><Sender><Number>" + sendnumber + "</Number></Sender><Recipient><Number>" + recipientnumber + "</Number></Recipient><TimeStamp>" + timestamp + "</TimeStamp><MMSRelayServerID>600003</MMSRelayServerID><MessageID>" + messageid + "</MessageID><MMStatus>Third:User Retrieved</MMStatus><StatusText>" + status + "</StatusText></DeliveryReportReq></env:Body></env:Envelope>";
     return report;
   }
 }


