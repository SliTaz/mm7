 package com.zbensoft.mmsmp.common.ra.common.message;
 
 import java.io.ByteArrayInputStream;
 import java.io.ByteArrayOutputStream;
 import java.io.InputStream;
 import javax.servlet.ServletInputStream;
 import javax.servlet.http.HttpServletRequest;
 
 public class HttpRequest extends AbstractMessage
 {
   private String ContentType;
   private String Content_Transfer_Encoding;
   private String Authorization;
   private String SOAPAction;
   private String MM7APIVersion;
   private String Mime_Version;
   private HttpServletRequest request;
   private String response;
   private ByteArrayOutputStream _out;
 
   private void saveRequest(HttpServletRequest req)
     throws Exception
   {
     this.ContentType = req.getContentType();
     this.Authorization = req.getHeader("Authorization");
     this.Content_Transfer_Encoding = req.getHeader("Content-Transfer-Encoding");
     this.SOAPAction = req.getHeader("SOAPAction");
     this.MM7APIVersion = req.getHeader("MM7APIVersion");
     this.Mime_Version = req.getHeader("Mime-Version");
     byte[] buf = new byte[1024];
     int n = 0;
     ServletInputStream in = req.getInputStream();
     while ((n = in.read(buf)) != -1)
       this._out.write(buf, 0, n);
   }
 
   public HttpServletRequest getRequest()
   {
     return this.request;
   }
   public HttpRequest() {
     this._out = new ByteArrayOutputStream();
   }
 
   public String getContentType() {
     return this.ContentType;
   }
 
   public String getAuthorization() {
     return this.Authorization;
   }
   public String getContent_Transfer_Encoding() {
     return this.Content_Transfer_Encoding;
   }
 
   public String getSOAPAction() {
     return this.SOAPAction;
   }
 
   public String getMM7APIVersion() {
     return this.MM7APIVersion;
   }
 
   public String getMime_Version() {
     return this.Mime_Version;
   }
 
   public void setContentType(String contentType) {
     this.ContentType = contentType;
   }
   public void setRequest(HttpServletRequest req) {
     try {
       saveRequest(req);
     } catch (Exception e) {
       e.printStackTrace();
     }
     this.request = req;
   }
 
   public InputStream getInputStream()
   {
     return new ByteArrayInputStream(this._out.toByteArray());
   }
 
   public String getContent() {
     return new String(this._out.toByteArray());
   }
 
   public byte[] getByteContent() {
     return this._out.toByteArray();
   }
 
   public String getResponse() {
     return this.response;
   }
 
   public void setResponse(String response) {
     this.response = response;
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
 }
