package com.essp.cvsreport.test;

import junit.framework.TestCase;
import java.io.PrintStream;
import com.essp.cvsreport.JavaClass;

public class JavaClassTest extends TestCase {
  JavaClass f;
  PrintStream out = System.out;

  protected void setUp() throws Exception {
    f = new JavaClass();
  }

  public void testSetFullName3() {
    try {
      f.setFullName("essp2/pms/buildCommon.java");
    } catch (Exception ex) {
      out.println(ex.getMessage());
      return;
    }

    fail("Expected exception.But there's none.");
  }

  public void testSetFullName4() {
    try {
      f.setFullName("essp2/pms/src/buildCommon.xml");
    } catch (Exception ex) {
      out.println(ex.getMessage());
      return;
    }

    fail("Expected exception.But there's none.");
  }

  public void testSetFullName5() {
    f.setFullName("essp2/pms/src/buildCommon.java");
    assertEquals("pms", f.getProjectInfo());
    assertEquals("src", f.getPackageInfo());
    assertEquals("buildCommon.java", f.getName());
    assertEquals("java", f.getExtend());
    assertEquals("buildCommon", f.getClassName());
    assertEquals("buildCommon", f.getFullClassName());
  }

  public void testSetFullName6() {
    f.setFullName("essp2/pms/src/c2s/buildCommon.java");
    assertEquals("buildCommon", f.getClassName());
    assertEquals("c2s.buildCommon", f.getFullClassName());
  }




}
