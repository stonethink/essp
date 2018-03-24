/*
 * 
 * createdDate: 2004-7-14
 * $Revision: 1.2 $ <br>
 * $Date: 2005/10/13 07:09:22 $
 */
package junitpack;

import junit.framework.TestCase;
import java.util.Vector;
/**
 * 
 *<br>
 * @author yerj
 * 
 * @version   
 * $Revision: 1.2 $ <br>
 * $Date: 2005/10/13 07:09:22 $
 */
public class ThingTest extends TestCase {

	/**
	 * Constructor for ThingTest.
	 * @param arg0
	 */
	public ThingTest(String arg0) {
		super(arg0);
	}
	
	public void testSetData() throws Exception {
		String xml = ThingTest.class.getResource(".").toString()
					 + "test.xml";
		System.out.println("xmlpath=" + xml);
		InputDataUtil util=new InputDataUtil();
		util.setClassName("junitpack.Thing");
		Vector vector            = (Vector)util.parse(xml);
		for (int i = 0; i < vector.size(); i++) {
			Thing opt = (Thing) vector.get(i);

					System.out.println("get object:\r\n" + opt);
				}
	}
}
