package suite;

import junit.framework.*;

public class TestSuiteAttendance extends TestCase {

    public TestSuiteAttendance(String s) {
        super(s);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTestSuite(server.essp.attendance.overtime.logic.
                           TestLgOverTimeImport.class);
        suite.addTestSuite(server.essp.attendance.overtime.logic.
                           TestLgOverTimeSearch.class);
        suite.addTestSuite(server.essp.attendance.overtime.logic.
                           TestLgOverTimeReport.class);
        suite.addTestSuite(server.essp.attendance.overtime.logic.
                           TestLgOverTimeClean.class);
        return suite;
    }
}
