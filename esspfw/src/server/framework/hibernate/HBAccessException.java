package server.framework.hibernate;

import net.sf.hibernate.HibernateException;


public class HBAccessException extends HibernateException {

//	public HBAccessException(){
//		super();
//	}

	public HBAccessException(Exception e){
		super(e);
	}
	public HBAccessException(String s){
		super(s);
	}

}
