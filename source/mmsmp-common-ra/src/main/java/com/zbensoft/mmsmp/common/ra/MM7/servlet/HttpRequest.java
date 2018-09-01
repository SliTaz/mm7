package com.zbensoft.mmsmp.common.ra.MM7.servlet;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class HttpRequest implements HttpServletRequest {
	private ByteArrayOutputStream _out;
	private HttpServletRequest hsr;
	private String contentType;
	private String linkId;

	public String getLinkId() {
		return this.linkId;
	}

	public void setLinkId(String linkId) {
		this.linkId = linkId;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public HttpRequest(HttpServletRequest hsr) {
		this._out = new ByteArrayOutputStream();
		this.hsr = hsr;
		try {
			InputStream in = hsr.getInputStream();
			byte[] b = new byte[1024];
			int count = 0;
			while ((count = in.read(b)) > 0)
				this._out.write(b, 0, count);
		} catch (IOException e) {
			System.out.println("error occurs:  when build HttpRequest ..." + e.getMessage());
		}
	}

	public ServletInputStream getInputStream() throws IOException {
		return new MyByteArrayInputStream(this._out.toByteArray());
	}

	public String getContent() {
		return new String(this._out.toByteArray());
	}

	public String getAuthType() {
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

	public Principal getUserPrincipal() {
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

	public Enumeration getLocales() {
		return this.hsr.getLocales();
	}

	public String getParameter(String arg0) {
		return null;
	}

	public Map getParameterMap() {
		return this.hsr.getParameterMap();
	}

	public Enumeration getParameterNames() {
		return this.hsr.getParameterNames();
	}

	public String[] getParameterValues(String arg0) {
		return this.hsr.getParameterValues(arg0);
	}

	public String getProtocol() {
		return this.hsr.getProtocol();
	}

	public BufferedReader getReader() throws IOException {
		return this.hsr.getReader();
	}

	public String getRealPath(String arg0) {
		return this.hsr.getRealPath(arg0);
	}

	public String getRemoteAddr() {
		return this.hsr.getRemoteAddr();
	}

	public String getRemoteHost() {
		return this.hsr.getRemoteHost();
	}

	public int getRemotePort() {
		return this.hsr.getRemotePort();
	}

	public RequestDispatcher getRequestDispatcher(String arg0) {
		return this.hsr.getRequestDispatcher(arg0);
	}

	public String getScheme() {
		return this.hsr.getScheme();
	}

	public String getServerName() {
		return this.hsr.getServerName();
	}

	public int getServerPort() {
		return this.hsr.getServerPort();
	}

	public boolean isSecure() {
		return this.hsr.isSecure();
	}

	public void removeAttribute(String arg0) {
		this.hsr.removeAttribute(arg0);
	}

	public void setAttribute(String arg0, Object arg1) {
		this.hsr.setAttribute(arg0, arg1);
	}

	public void setCharacterEncoding(String arg0) throws UnsupportedEncodingException {
		this.hsr.setCharacterEncoding(arg0);
	}

	public String getContentType() {
		return this.contentType;
	}
}
