/*
 * Created on 2003/06/18
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.wits.util;

import java.io.*;


/**
 * @version
 * $Revision: 1.1.1.1 $ <br>
 * $Date: 2005/08/15 06:10:55 $
 * @author Administrator
 *
 */
public class LogStackTrace {

	public static String parse(Exception ex){
		String losString="";
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		ex.printStackTrace(pw);
		losString=sw.toString();
		pw.close();
		try{
			sw.close();
		}catch(IOException ie){
		}
		return losString;
	}
}
