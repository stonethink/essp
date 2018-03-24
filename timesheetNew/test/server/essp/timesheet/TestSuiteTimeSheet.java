package server.essp.timesheet;

import junit.framework.*;
import server.essp.timesheet.account.labor.service.TestLaborServiceImp;
import server.essp.timesheet.account.service.TestAccountServiceImp;
import server.essp.timesheet.calendar.service.TestCalendarServiceImp;
import server.essp.timesheet.code.codetype.service.TestCodeTypeServiceImp;
import server.essp.timesheet.preference.service.TestPreferenceServiceImp;
import server.essp.timesheet.code.codevalue.service.TestCodeValueServiceImp;
import server.essp.timesheet.code.coderelation.service.TestCodeRelationServiceImp;

public class TestSuiteTimeSheet extends TestCase {

    public TestSuiteTimeSheet(String s) {
        super(s);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTestSuite(TestLaborServiceImp.class);
        suite.addTestSuite(TestAccountServiceImp.class);
        suite.addTestSuite(TestCalendarServiceImp.class);
        suite.addTestSuite(TestCodeTypeServiceImp.class);
        suite.addTestSuite(TestPreferenceServiceImp.class);
        suite.addTestSuite(TestCodeValueServiceImp.class);
        suite.addTestSuite(TestCodeRelationServiceImp.class);
        return suite;
    }
}
