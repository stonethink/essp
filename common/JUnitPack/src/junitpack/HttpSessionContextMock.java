/*
 * 
 * CreatedDate: 2004-8-3
 * $Revision: 1.2 $ <br>
 * $Date: 2005/10/13 07:09:22 $
 */
package junitpack;

import java.util.Enumeration;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import java.util.HashMap;
import java.util.Iterator;
/**
 * 
 *<br>
 * @author yerj
 * 
 * @version   
 * $Revision: 1.2 $ <br>
 * $Date: 2005/10/13 07:09:22 $
 */
public class HttpSessionContextMock extends HashMap implements HttpSessionContext {

	/**
	 * 
	 */
	public HttpSessionContextMock() {
		super();
	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.http.HttpSessionContext#getSession(java.lang.String)
	 */
	public HttpSession getSession(String arg0) {
		return (HttpSession)get(arg0);
	}

	/* 
	 * CreatedDate: 2004-8-3
	 * Creater: yerj
	 * @see javax.servlet.http.HttpSessionContext#getIds()
	 */
	public Enumeration getIds() {
		return new KeyEnum(keySet().iterator());
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

