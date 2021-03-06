 package com.zbensoft.mmsmp.corebiz.service.mm7;
 
 import java.io.BufferedReader;
 import java.io.ByteArrayOutputStream;
 import java.io.IOException;
 import java.io.InputStream;
 import java.io.PrintStream;
 import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.Enumeration;
 import java.util.Locale;
 import java.util.Map;

import javax.servlet.AsyncContext;
import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
 import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpUpgradeHandler;
import javax.servlet.http.Part;
 
 public class HttpRequest implements HttpServletRequest
 {
   private ByteArrayOutputStream _out;
   private HttpServletRequest hsr;
   private String contentType;
   private String linkId;
   private String sendurl;
   private byte[] b;
   
   public byte[] getB()
   {
     return this.b;
   }
   
   public void setB(byte[] b) { this.b = b; }
   
   public String getSendurl() {
     return this.sendurl;
   }
   
   public void setSendurl(String sendurl) { this.sendurl = sendurl; }
   
   public String getLinkId() {
     return this.linkId;
   }
   
   public void setLinkId(String linkId) { this.linkId = linkId; }
   
 
   public void setContentType(String contentType) { this.contentType = contentType; }
   
   public HttpRequest(HttpServletRequest hsr) {
     this._out = new ByteArrayOutputStream();
     this.hsr = hsr;
     try {
       InputStream in = hsr.getInputStream();
       byte[] b1 = new byte['Ѐ'];
       int count = 0;
       while ((count = in.read(b1)) > 0) {
         this._out.write(b1, 0, count);
       }
       this.b = this._out.toByteArray();
     } catch (IOException e) {
       System.out.println("error occurs:  when build HttpRequest ..." + e.getMessage());
     }
   }
   
   public HttpRequest() {}
   
   public ServletInputStream getInputStream()
     throws IOException
   {
     return new MyByteArrayInputStream(this.b);
   }
   
   public String getContent() {
     return new String(this._out.toByteArray());
   }
   
 
   public String getAuthType()
   {
     return this.hsr.getAuthType();
   }
   
   public String getContextPath() {
     return this.hsr.getContextPath();
   }
   
   public Cookie[] getCookies() {
     return this.hsr.getCookies();
   }
   
   public long getDateHeader(String arg0) {
     return this.hsr.getDateHeader(arg0);
   }
   
   public String getHeader(String arg0) {
     return this.hsr.getHeader(arg0);
   }
   
   public Enumeration getHeaderNames() {
     return this.hsr.getHeaderNames();
   }
   
   public Enumeration getHeaders(String arg0) {
     return this.hsr.getHeaders(arg0);
   }
   
   public int getIntHeader(String arg0) {
     return this.hsr.getIntHeader(arg0);
   }
   
   public String getMethod() {
     return this.hsr.getMethod();
   }
   
   public String getPathInfo() {
     return this.hsr.getPathInfo();
   }
   
   public String getPathTranslated() {
     return this.hsr.getPathTranslated();
   }
   
   public String getQueryString() {
     return this.hsr.getQueryString();
   }
   
   public String getRemoteUser() {
     return this.hsr.getRemoteUser();
   }
   
   public String getRequestURI() {
     return this.hsr.getRequestURI();
   }
   
   public StringBuffer getRequestURL() {
     return this.hsr.getRequestURL();
   }
   
   public String getRequestedSessionId() {
     return this.hsr.getRequestedSessionId();
   }
   
   public String getServletPath() {
     return this.hsr.getServletPath();
   }
   
   public HttpSession getSession() {
     return this.hsr.getSession();
   }
   
   public HttpSession getSession(boolean arg0) {
     return this.hsr.getSession(arg0);
   }
   
   public java.security.Principal getUserPrincipal() {
     return this.hsr.getUserPrincipal();
   }
   
   public boolean isRequestedSessionIdFromCookie() {
     return this.hsr.isRequestedSessionIdFromCookie();
   }
   
   public boolean isRequestedSessionIdFromURL() {
     return this.hsr.isRequestedSessionIdFromURL();
   }
   
   public boolean isRequestedSessionIdFromUrl() {
     return this.hsr.isRequestedSessionIdFromUrl();
   }
   
   public boolean isRequestedSessionIdValid() {
     return this.hsr.isRequestedSessionIdValid();
   }
   
   public boolean isUserInRole(String arg0) {
     return this.hsr.isUserInRole(arg0);
   }
   
   public Object getAttribute(String arg0) {
     return this.hsr.getAttribute(arg0);
   }
   
   public Enumeration getAttributeNames() {
     return this.hsr.getAttributeNames();
   }
   
   public String getCharacterEncoding() {
     return this.hsr.getCharacterEncoding();
   }
   
   public int getContentLength() {
     return this.hsr.getContentLength();
   }
   
   public String getLocalAddr() {
     return this.hsr.getLocalAddr();
   }
   
   public String getLocalName() {
     return this.hsr.getLocalName();
   }
   
   public int getLocalPort() {
     return this.hsr.getLocalPort();
   }
   
   public Locale getLocale() {
     return this.hsr.getLocale();
   }
   
   public Enumeration getLocales() { return this.hsr.getLocales(); }
   
   public String getParameter(String arg0)
   {
     return null;
   }
   
   public Map getParameterMap() { return this.hsr.getParameterMap(); }
   
   public Enumeration getParameterNames() {
     return this.hsr.getParameterNames();
   }
   
   public String[] getParameterValues(String arg0) { return this.hsr.getParameterValues(arg0); }
   
   public String getProtocol() {
     return this.hsr.getProtocol();
   }
   
   public BufferedReader getReader() throws IOException { return this.hsr.getReader(); }
   
   public String getRealPath(String arg0) {
     return this.hsr.getRealPath(arg0);
   }
   
   public String getRemoteAddr() { return this.hsr.getRemoteAddr(); }
   
   public String getRemoteHost() {
     return this.hsr.getRemoteHost();
   }
   
   public int getRemotePort() { return this.hsr.getRemotePort(); }
   
   public RequestDispatcher getRequestDispatcher(String arg0) {
     return this.hsr.getRequestDispatcher(arg0);
   }
   
   public String getScheme() { return this.hsr.getScheme(); }
   
   public String getServerName() {
     return this.hsr.getServerName();
   }
   
   public int getServerPort() { return this.hsr.getServerPort(); }
   
   public boolean isSecure() {
     return this.hsr.isSecure();
   }
   
   public void removeAttribute(String arg0) { this.hsr.removeAttribute(arg0); }
   
   public void setAttribute(String arg0, Object arg1) {
     this.hsr.setAttribute(arg0, arg1);
   }
   
 
   public void setCharacterEncoding(String arg0)
     throws UnsupportedEncodingException
   {
     this.hsr.setCharacterEncoding(arg0);
   }
   
   public String getContentType()
   {
     return this.contentType;
   }

@Override
public long getContentLengthLong() {
	// TODO Auto-generated method stub
	return 0;
}

@Override
public ServletContext getServletContext() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public AsyncContext startAsync() throws IllegalStateException {
	// TODO Auto-generated method stub
	return null;
}

@Override
public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse)
		throws IllegalStateException {
	// TODO Auto-generated method stub
	return null;
}

@Override
public boolean isAsyncStarted() {
	// TODO Auto-generated method stub
	return false;
}

@Override
public boolean isAsyncSupported() {
	// TODO Auto-generated method stub
	return false;
}

@Override
public AsyncContext getAsyncContext() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public DispatcherType getDispatcherType() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public String changeSessionId() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public boolean authenticate(HttpServletResponse response) throws IOException, ServletException {
	// TODO Auto-generated method stub
	return false;
}

@Override
public void login(String username, String password) throws ServletException {
	// TODO Auto-generated method stub
	
}

@Override
public void logout() throws ServletException {
	// TODO Auto-generated method stub
	
}

@Override
public Collection<Part> getParts() throws IOException, ServletException {
	// TODO Auto-generated method stub
	return null;
}

@Override
public Part getPart(String name) throws IOException, ServletException {
	// TODO Auto-generated method stub
	return null;
}

@Override
public <T extends HttpUpgradeHandler> T upgrade(Class<T> httpUpgradeHandlerClass) throws IOException, ServletException {
	// TODO Auto-generated method stub
	return null;
}
 }


