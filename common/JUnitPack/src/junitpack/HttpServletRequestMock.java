/*
 * 
 * CreatedDate: 2004-8-3
 * $Revision: 1.2 $ <br>
 * $Date: 2005/10/13 07:09:22 $
 */
package junitpack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 
 *<br>
 * @author yerj
 * 
 * @version   
 * $Revision: 1.2 $ <br>
 * $Date: 2005/10/13 07:09:22 $
 */
public class HttpServletRequestMock implements HttpServletRequest {
	HashMap attributeMap=new HashMap();
	HashMap parameterMap=new HashMap();
	private static HttpSession session=new HttpSessionMock();
	/**
	 * 
	 */
	public HttpServletRequestMock() {
		super();
	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.http.HttpServletRequest#getAuthType()
	 */
	public String getAuthType() {
		return null;
	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.http.HttpServletRequest#getCookies()
	 */
	public Cookie[] getCookies() {
		return null;
	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.http.HttpServletRequest#getDateHeader(java.lang.String)
	 */
	public long getDateHeader(String arg0) {
		return 0;
	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.http.HttpServletRequest#getHeader(java.lang.String)
	 */
	public String getHeader(String arg0) {
		return null;
	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.http.HttpServletRequest#getHeaders(java.lang.String)
	 */
	public Enumeration getHeaders(String arg0) {
		return null;
	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.http.HttpServletRequest#getHeaderNames()
	 */
	public Enumeration getHeaderNames() {
		return null;
	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.http.HttpServletRequest#getIntHeader(java.lang.String)
	 */
	public int getIntHeader(String arg0) {
		return 0;
	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.http.HttpServletRequest#getMethod()
	 */
	public String getMethod() {
		return null;
	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.http.HttpServletRequest#getPathInfo()
	 */
	public String getPathInfo() {
		return null;
	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.http.HttpServletRequest#getPathTranslated()
	 */
	public String getPathTranslated() {
		return null;
	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.http.HttpServletRequest#getContextPath()
	 */
	public String getContextPath() {
		return null;
	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.http.HttpServletRequest#getQueryString()
	 */
	public String getQueryString() {
		return null;
	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.http.HttpServletRequest#getRemoteUser()
	 */
	public String getRemoteUser() {
		return null;
	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.http.HttpServletRequest#isUserInRole(java.lang.String)
	 */
	public boolean isUserInRole(String arg0) {
		return false;
	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.http.HttpServletRequest#getUserPrincipal()
	 */
	public Principal getUserPrincipal() {
		return null;
	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.http.HttpServletRequest#getRequestedSessionId()
	 */
	public String getRequestedSessionId() {
		return null;
	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.http.HttpServletRequest#getRequestURI()
	 */
	public String getRequestURI() {
		return null;
	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.http.HttpServletRequest#getRequestURL()
	 */
	public StringBuffer getRequestURL() {
		return null;
	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.http.HttpServletRequest#getServletPath()
	 */
	public String getServletPath() {
		return null;
	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.http.HttpServletRequest#getSession(boolean)
	 */
	public HttpSession getSession(boolean arg0) {
		return session;
	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.http.HttpServletRequest#getSession()
	 */
	public HttpSession getSession() {
		return session;
	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.http.HttpServletRequest#isRequestedSessionIdValid()
	 */
	public boolean isRequestedSessionIdValid() {
		return true;
	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.http.HttpServletRequest#isRequestedSessionIdFromCookie()
	 */
	public boolean isRequestedSessionIdFromCookie() {
		return false;
	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.http.HttpServletRequest#isRequestedSessionIdFromURL()
	 */
	public boolean isRequestedSessionIdFromURL() {
		return true;
	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.http.HttpServletRequest#isRequestedSessionIdFromUrl()
	 */
	public boolean isRequestedSessionIdFromUrl() {
		return true;
	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.ServletRequest#getAttribute(java.lang.String)
	 */
	public Object getAttribute(String arg0) {
		return attributeMap.get(arg0);
	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.ServletRequest#getAttributeNames()
	 */
	public Enumeration getAttributeNames() {
		return new KeyEnum(attributeMap.keySet().iterator());
	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.ServletRequest#getCharacterEncoding()
	 */
	public String getCharacterEncoding() {
		return "Shift-JIS";
	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.ServletRequest#setCharacterEncoding(java.lang.String)
	 */
	public void setCharacterEncoding(String arg0)
		throws UnsupportedEncodingException {

	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.ServletRequest#getContentLength()
	 */
	public int getContentLength() {
		return 0;
	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.ServletRequest#getContentType()
	 */
	public String getContentType() {
		return null;
	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.ServletRequest#getInputStream()
	 */
	public ServletInputStream getInputStream() throws IOException {
		return null;
	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.ServletRequest#getParameter(java.lang.String)
	 */
	public String getParameter(String arg0) {
		return (String)parameterMap.get(arg0);
	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.ServletRequest#getParameterNames()
	 */
	public Enumeration getParameterNames() {
		return new KeyEnum(parameterMap.keySet().iterator());
	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.ServletRequest#getParameterValues(java.lang.String)
	 */
	public String[] getParameterValues(String arg0) {
		return null;
	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.ServletRequest#getParameterMap()
	 */
	public Map getParameterMap() {
		return parameterMap;
	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.ServletRequest#getProtocol()
	 */
	public String getProtocol() {
		return null;
	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.ServletRequest#getScheme()
	 */
	public String getScheme() {
		return null;
	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.ServletRequest#getServerName()
	 */
	public String getServerName() {
		return null;
	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.ServletRequest#getServerPort()
	 */
	public int getServerPort() {
		return 0;
	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.ServletRequest#getReader()
	 */
	public BufferedReader getReader() throws IOException {
		return null;
	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.ServletRequest#getRemoteAddr()
	 */
	public String getRemoteAddr() {
		return null;
	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.ServletRequest#getRemoteHost()
	 */
	public String getRemoteHost() {
		return null;
	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.ServletRequest#setAttribute(java.lang.String, java.lang.Object)
	 */
	public void setAttribute(String arg0, Object arg1) {
		attributeMap.put(arg0,arg1);
	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.ServletRequest#removeAttribute(java.lang.String)
	 */
	public void removeAttribute(String arg0) {
		attributeMap.remove(arg0);
	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.ServletRequest#getLocale()
	 */
	public Locale getLocale() {
		return null;
	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.ServletRequest#getLocales()
	 */
	public Enumeration getLocales() {
		return null;
	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.ServletRequest#isSecure()
	 */
	public boolean isSecure() {
		return false;
	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.ServletRequest#getRequestDispatcher(java.lang.String)
	 */
	public RequestDispatcher getRequestDispatcher(String arg0) {
		return null;
	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.ServletRequest#getRealPath(java.lang.String)
	 */
	public String getRealPath(String arg0) {
		return null;
	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.ServletRequest#getRemotePort()
	 */
	public int getRemotePort() {
		return 0;
	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.ServletRequest#getLocalName()
	 */
	public String getLocalName() {
		return null;
	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.ServletRequest#getLocalAddr()
	 */
	public String getLocalAddr() {
		return null;
	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.ServletRequest#getLocalPort()
	 */
	public int getLocalPort() {
		return 0;
	}

	class KeyEnum implements Enumeration {
		Iterator data=null;
		public KeyEnum(Iterator it) {
			this.data=it;
		}
		
		public boolean hasMoreElements() {
			if(data.hasNext()) {
				return true;
			} else {
				return false;
			}
		}
		
		public Object nextElement() {
			if(hasMoreElements()) {
				return data.next();
			} else {
				return null;
			}
			
		}
	}
}
