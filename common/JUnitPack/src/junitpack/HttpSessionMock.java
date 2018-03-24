/*
 * 
 * CreatedDate: 2004-8-3
 * $Revision: 1.2 $ <br>
 * $Date: 2005/10/13 07:09:22 $
 */
package junitpack;

import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import java.util.Date;
import java.text.FieldPosition;
import java.text.SimpleDateFormat;

/**
 * 
 *<br>
 * @author yerj
 * 
 * @version   
 * $Revision: 1.2 $ <br>
 * $Date: 2005/10/13 07:09:22 $
 */
public class HttpSessionMock implements HttpSession {
	HttpSessionContext sessionContext=new HttpSessionContextMock();
	ServletContext servletContext=new ServletContextMock();
	Date createTime=null;
	Date lastAccessedTime=null;
	String id="";

	/**
	 * 
	 */
	public HttpSessionMock() {
		super();
		createTime=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
		id=sdf.format(createTime,new StringBuffer(),new FieldPosition(0)).toString();
	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.http.HttpSession#getCreationTime()
	 */
	public long getCreationTime() {
		return createTime.getTime();
	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.http.HttpSession#getId()
	 */
	public String getId() {
		return id;
	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.http.HttpSession#getLastAccessedTime()
	 */
	public long getLastAccessedTime() {
		if(lastAccessedTime!=null) {
			return lastAccessedTime.getTime();
		} else {
			return createTime.getTime();
		}
	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.http.HttpSession#getServletContext()
	 */
	public ServletContext getServletContext() {
		return servletContext;
	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.http.HttpSession#setMaxInactiveInterval(int)
	 */
	public void setMaxInactiveInterval(int arg0) {
	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.http.HttpSession#getMaxInactiveInterval()
	 */
	public int getMaxInactiveInterval() {
		return 0;
	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.http.HttpSession#getSessionContext()
	 */
	public HttpSessionContext getSessionContext() {
		return sessionContext;
	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.http.HttpSession#getAttribute(java.lang.String)
	 */
	public Object getAttribute(String arg0) {
		return servletContext.getAttribute(arg0);
	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.http.HttpSession#getValue(java.lang.String)
	 */
	public Object getValue(String arg0) {
		return ((HttpSessionContextMock)sessionContext).get(arg0);
	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.http.HttpSession#getAttributeNames()
	 */
	public Enumeration getAttributeNames() {
		return servletContext.getAttributeNames();
	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.http.HttpSession#getValueNames()
	 */
	public String[] getValueNames() {
		return null;
	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.http.HttpSession#setAttribute(java.lang.String, java.lang.Object)
	 */
	public void setAttribute(String arg0, Object arg1) {
		servletContext.setAttribute(arg0,arg1);
	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.http.HttpSession#putValue(java.lang.String, java.lang.Object)
	 */
	public void putValue(String arg0, Object arg1) {

	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.http.HttpSession#removeAttribute(java.lang.String)
	 */
	public void removeAttribute(String arg0) {
		servletContext.removeAttribute(arg0);
	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.http.HttpSession#removeValue(java.lang.String)
	 */
	public void removeValue(String arg0) {

	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.http.HttpSession#invalidate()
	 */
	public void invalidate() {

	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.http.HttpSession#isNew()
	 */
	public boolean isNew() {
		return false;
	}

}
