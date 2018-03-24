package com.essp.cvsreport.test;

import junit.framework.TestCase;
import java.io.PrintStream;
import com.essp.cvsreport.CvsFile;

public class CvsFileTest extends TestCase {
  CvsFile f;
  PrintStream out = System.out;

  public CvsFileTest(){
    super();
  }

  protected void setUp() throws Exception {
    f = new CvsFile();
  }

  public void testSetFullName() {
    try {
      f.setFullName("essp2");
    } catch (Exception ex) {
      out.println(ex.getMessage());

      try {
        f.setFullName("essp2/pms/");
      } catch (Exception ex2) {
        out.println(ex.getMessage());

        try {
          f.setFullName("essp2/pms/src/");
        } catch (Exception ex3) {
          out.println(ex.getMessage());
          return;
        }

        fail("Expected exception.But there's none.");
        return;
      }

      fail("Expected exception.But there's none.");
      return;
    }

    fail("Expected exception.But there's none.");
  }

  public void testSetFullName2() {
    f.setFullName("essp2/buildCommon.xml");
    assertNull(f.getProjectInfo());
    assertNull(f.getPackageInfo());
    assertEquals("buildCommon.xml", f.getName());
    assertEquals("xml", f.getExtend());
  }

  public void testSetFullName3() {
    f.setFullName("essp2/pms/buildCommon.xml");
    assertEquals("pms", f.getProjectInfo());
    assertNull(f.getPackageInfo());
    assertEquals("buildCommon.xml", f.getName());
    assertEquals("xml", f.getExtend());
  }

  public void testSetFullName4() {
    f.setFullName("essp2/pms/src/buildCommon.xml");
    assertEquals("pms", f.getProjectInfo());
    assertEquals("src", f.getPackageInfo());
    assertEquals("buildCommon.xml", f.getName());
    assertEquals("xml", f.getExtend());
  }



}
