package suit;


import itf.account.TestAccountFactory;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import server.essp.pms.modifyplan.logic.TestLgWbsNodeCopy;
import server.essp.pms.modifyplan.logic.TestLgActivityNodeCopy;
import server.essp.pms.wbs.process.guideline.logic.TestLgWbsGuideLine;
import server.essp.pms.wbs.process.checklist.logic.TestLgQaCheckPoint;
import server.essp.pms.activity.process.guideline.logic.TestLgActivityGuideLine;
import server.essp.tc.hrmanage.logic.TestLgOrgUtRate;
import server.essp.pms.qa.monitoring.logic.TestLgMonitoring;
import server.essp.pms.qa.monitoring.logic.TestLgNcrIssue;

public class TestSuitePMS extends TestCase {

    public TestSuitePMS(String s) {
        super(s);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTestSuite(TestLgNcrIssue.class);
        suite.addTestSuite(TestLgMonitoring.class);
        suite.addTestSuite(TestLgOrgUtRate.class);
        suite.addTestSuite(TestLgActivityGuideLine.class);
        suite.addTestSuite(TestLgWbsGuideLine.class);
        suite.addTestSuite(TestLgQaCheckPoint.class);
        suite.addTestSuite(server.essp.pms.account.logic.TestLgTemplate.class);

        suite.addTestSuite(TestLgWbsNodeCopy.class);
        suite.addTestSuite(TestLgActivityNodeCopy.class);
        suite.addTestSuite(server.essp.pms.account.logic.
                           TestLgAccountInfoUtilImp.class);
        suite.addTestSuite(server.essp.pms.account.logic.
                           TestLgAccountListUtilImpNoUser.class);
        suite.addTestSuite(server.essp.pms.account.logic.
                           TestLgAccountListUtilImpWithUser.class);
        suite.addTestSuite(c2s.essp.pms.account.TestDtoPmsAcnt.class);
        suite.addTestSuite(TestAccountFactory.class);

        suite.addTestSuite(server.essp.pms.psr.test.TestSplitWeek.class);
        return suite;
    }
}
