package com.essp.cvsreport.test;

import junit.framework.*;

public class TestAll extends TestCase{
  public TestAll() {

  }

  public static Test suite(){
    TestSuite suite = new TestSuite();
    suite.addTestSuite(CvsFileTest.class);
    suite.addTestSuite(CvsModuleTest.class);
    suite.addTestSuite(JavaClassTest.class);
    suite.addTestSuite(JavaRootTest.class);
    suite.addTestSuite(ReportDateTest.class);
    return suite;
  }

}
