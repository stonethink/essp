package server.essp.pms.psr.test;

import junit.framework.*;

import java.util.*;
import com.wits.util.comDate;
import server.essp.pms.psr.logic.*;

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
public class TestSplitWeek extends TestCase {
    private SplitWeek splitWeek = null;
    MyCustomFixture myCustomFixture;

    public TestSplitWeek(String name) {
        super(name);
    }

    protected void setUp() throws Exception {
        super.setUp();
        splitWeek = new SplitWeek();
        myCustomFixture = new MyCustomFixture(this);

        myCustomFixture.setUp();
    }

    protected void tearDown() throws Exception {
        splitWeek = null;
        myCustomFixture.tearDown();

        myCustomFixture = null;
        super.tearDown();
    }

    public void testGetWeekPeriod() {
        int i = 0;
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        //把系统时间调到10.21.-10.27.运行测试即为下面的输出
        c1.set(2006, 9, 14, 0, 0, 0);
        c2.set(2006, 9, 20, 0, 0, 0);

        Date[] expectedReturn = new Date[2];
        expectedReturn[0] = c1.getTime();
        expectedReturn[1] = c2.getTime();
        Calendar cal = Calendar.getInstance();
        cal.set(2006, 9, 26);
        Date[] actualReturn = splitWeek.getWeekPeriod(cal, i);

        assertEquals("start date equals", comDate.dateToString(expectedReturn[0]),
                     comDate.dateToString(actualReturn[0]));
        assertEquals("finish data equals",
                     comDate.dateToString(expectedReturn[1]),
                     comDate.dateToString(actualReturn[1]));
    }

    public void testSplitWeek() {
        splitWeek = new SplitWeek();
    }

}
