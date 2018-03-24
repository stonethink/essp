package server.essp.pms.psr.test;

import junit.framework.*;
import server.essp.pms.psr.logic.*;
import java.util.*;
import java.io.*;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class TestLgPrepareEVA extends TestCase {
    private LgPrepareEVA lgPrepareEVA = null;
    MyCustomFixture myCustomFixture;

    public TestLgPrepareEVA(String name) {
        super(name);
    }

    protected void setUp() throws Exception {
        super.setUp();
        lgPrepareEVA = new LgPrepareEVA();
        myCustomFixture = new MyCustomFixture(this);

        myCustomFixture.setUp();
    }

    protected void tearDown() throws Exception {
        lgPrepareEVA = null;
        myCustomFixture.tearDown();

        myCustomFixture = null;
        super.tearDown();
    }

    public void testGetEVADataList() {
        List allPSRData = null;
        List expectedReturn = null;
        List actualReturn = lgPrepareEVA.getEVADataList(allPSRData);
        assertEquals("return value", expectedReturn, actualReturn);
        /**@todo fill in the test code*/
    }

    public void testGetLineChartForEVA() {
        List allEVAList = null;
        ByteArrayOutputStream expectedReturn = null;
        ByteArrayOutputStream actualReturn = lgPrepareEVA.getLineChartForEVA(allEVAList);
        assertEquals("return value", expectedReturn, actualReturn);
        /**@todo fill in the test code*/
    }

    public void testGetLineChartForPerformance() {
        List allEVAList = null;
        ByteArrayOutputStream expectedReturn = null;
        ByteArrayOutputStream actualReturn = lgPrepareEVA.getLineChartForPerformance(allEVAList);
        assertEquals("return value", expectedReturn, actualReturn);
        /**@todo fill in the test code*/
    }

    public void testLgPrepareEVA() {
        lgPrepareEVA = new LgPrepareEVA();
        /**@todo fill in the test code*/
    }

}
