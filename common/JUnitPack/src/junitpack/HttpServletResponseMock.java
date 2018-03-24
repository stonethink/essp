/*
 * 
 * CreatedDate: 2004-8-3
 * $Revision: 1.2 $ <br>
 * $Date: 2005/10/13 07:09:22 $
 */
package junitpack;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 *<br>
 * @author yerj
 * 
 * @version   
 * $Revision: 1.2 $ <br>
 * $Date: 2005/10/13 07:09:22 $
 */
public class HttpServletResponseMock implements HttpServletResponse {

	/**
	 * 
	 */
	public HttpServletResponseMock() {
		super();
	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.http.HttpServletResponse#addCookie(javax.servlet.http.Cookie)
	 */
	public void addCookie(Cookie arg0) {

	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.http.HttpServletResponse#containsHeader(java.lang.String)
	 */
	public boolean containsHeader(String arg0) {
		return false;
	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.http.HttpServletResponse#encodeURL(java.lang.String)
	 */
	public String encodeURL(String arg0) {
		return null;
	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.http.HttpServletResponse#encodeRedirectURL(java.lang.String)
	 */
	public String encodeRedirectURL(String arg0) {
		return null;
	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.http.HttpServletResponse#encodeUrl(java.lang.String)
	 */
	public String encodeUrl(String arg0) {
		return null;
	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.http.HttpServletResponse#encodeRedirectUrl(java.lang.String)
	 */
	public String encodeRedirectUrl(String arg0) {
		return null;
	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.http.HttpServletResponse#sendError(int, java.lang.String)
	 */
	public void sendError(int arg0, String arg1) throws IOException {

	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.http.HttpServletResponse#sendError(int)
	 */
	public void sendError(int arg0) throws IOException {

	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.http.HttpServletResponse#sendRedirect(java.lang.String)
	 */
	public void sendRedirect(String arg0) throws IOException {

	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.http.HttpServletResponse#setDateHeader(java.lang.String, long)
	 */
	public void setDateHeader(String arg0, long arg1) {

	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.http.HttpServletResponse#addDateHeader(java.lang.String, long)
	 */
	public void addDateHeader(String arg0, long arg1) {

	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.http.HttpServletResponse#setHeader(java.lang.String, java.lang.String)
	 */
	public void setHeader(String arg0, String arg1) {

	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.http.HttpServletResponse#addHeader(java.lang.String, java.lang.String)
	 */
	public void addHeader(String arg0, String arg1) {

	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.http.HttpServletResponse#setIntHeader(java.lang.String, int)
	 */
	public void setIntHeader(String arg0, int arg1) {

	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.http.HttpServletResponse#addIntHeader(java.lang.String, int)
	 */
	public void addIntHeader(String arg0, int arg1) {

	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.http.HttpServletResponse#setStatus(int)
	 */
	public void setStatus(int arg0) {

	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.http.HttpServletResponse#setStatus(int, java.lang.String)
	 */
	public void setStatus(int arg0, String arg1) {

	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.ServletResponse#getCharacterEncoding()
	 */
	public String getCharacterEncoding() {
		return null;
	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.ServletResponse#getContentType()
	 */
	public String getContentType() {
		return null;
	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.ServletResponse#getOutputStream()
	 */
	public ServletOutputStream getOutputStream() throws IOException {
		return null;
	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.ServletResponse#getWriter()
	 */
	public PrintWriter getWriter() throws IOException {
		return null;
	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.ServletResponse#setCharacterEncoding(java.lang.String)
	 */
	public void setCharacterEncoding(String arg0) {

	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.ServletResponse#setContentLength(int)
	 */
	public void setContentLength(int arg0) {

	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.ServletResponse#setContentType(java.lang.String)
	 */
	public void setContentType(String arg0) {

	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.ServletResponse#setBufferSize(int)
	 */
	public void setBufferSize(int arg0) {

	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.ServletResponse#getBufferSize()
	 */
	public int getBufferSize() {
		return 0;
	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.ServletResponse#flushBuffer()
	 */
	public void flushBuffer() throws IOException {

	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.ServletResponse#resetBuffer()
	 */
	public void resetBuffer() {

	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.ServletResponse#isCommitted()
	 */
	public boolean isCommitted() {
		return false;
	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.ServletResponse#reset()
	 */
	public void reset() {

	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.ServletResponse#setLocale(java.util.Locale)
	 */
	public void setLocale(Locale arg0) {

	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.ServletResponse#getLocale()
	 */
	public Locale getLocale() {
		return null;
	}

}
