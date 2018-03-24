package com.essp.cvsreport.test;
import junit.framework.TestCase;
import java.io.PrintStream;
import com.essp.cvsreport.*;
import java.util.Calendar;
import java.util.Map.Entry;
import java.util.*;

public class ReportDateTest extends TestCase {
  String af[];
  ReportDate r;
  PrintStream out = System.out;

  protected void setUp() throws Exception {
    r = ReportDate.getInstance();
  }

  public void ttestAddSumFile01() {
    try {
      r.addCvsFile("/home/cvsroot/esspXX/");
    } catch (Exception ex) {
      out.println(ex.getMessage());

      try {
        r.addCvsFile("/home/cvsroot/essp/,XX");
      } catch (Exception ex2) {
        out.println(ex2.getMessage());

        try {
          r.addCvsFile("/home/cvsroot/essp/,v");
        } catch (Exception ex22) {
          out.println(ex22.getMessage());

          r.addCvsFile("/home/cvsroot/essp/essp2/a,v");
          return;
        }

        fail("Expected exception.But there's none.");
      }

      fail("Expected exception.But there's none.");
    }

    fail("Expected exception.But there's none.");
  }

  public void ttestAddSumFile02() {
    r.addCvsFile("/home/cvsroot/essp/essp2/esspfw/t.xml,v");
    r.secondPass();

    JavaRoot m= r.getRoot();
    assertEquals(1, m.getAllFileNum());
    assertEquals(0, m.getAllModuleNum());
    assertEquals(0, m.getAllClassNum());
    assertEquals(0, m.getAllPackageNum());
    assertEquals(1, m.getProjectNum());

    JavaProject project = m.getProject("esspfw");
    assertNotNull(project);
    assertEquals(1, project.getAllFileNum());
    assertEquals(0, project.getAllModuleNum());
    assertEquals(0, project.getAllClassNum());
    assertEquals(0, project.getAllPackageNum());

    CvsFile ef = project.getSubFile("essp2/esspfw/t.xml");
    assertNotNull(ef);
    assertEquals("t.xml", ef.getName());
    assertEquals("essp2/esspfw/t.xml", ef.getFullName());
    assertEquals("xml", ef.getExtend());
    assertEquals("esspfw", ef.getProjectInfo());
    assertNull(ef.getPackageInfo());
    assertEquals(project, ef.getJavaProject());
  }


  public void ttestAddSubFile2() {
    String af[] = {"/home/cvsroot/essp/essp2/pms/t.xml,v"
                  , "/home/cvsroot/essp/essp2/esspfw/tt.xml,v"
                  , "/home/cvsroot/essp/essp2/esspfw/config/s.xml,v"
                  , "/home/cvsroot/essp/essp2/esspfw/config/dir/ss.xml,v"
    };

    for (int i = 0; i < af.length; i++) {
      r.addCvsFile(af[i]);
    }
    r.secondPass();

    JavaRoot m = r.getRoot();
    assertEquals(af.length, m.getAllFileNum());
    assertEquals(2, m.getProjectNum());
    JavaProject ep = m.getProject("esspfw");
    assertNotNull(ep);
    CvsModule eem = ep.getSubModule("essp2/esspfw/config");
    assertNotNull(eem);

    CvsFile ef = eem.getSubFile("essp2/esspfw/config/s.xml");
    assertNotNull(ef);
    assertEquals("s.xml", ef.getName());
    assertEquals("essp2/esspfw/config/s.xml", ef.getFullName());
    assertFalse(ef.isClassFile());
    assertEquals(eem, ef.getCvsModule());
    assertEquals(ep, ef.getJavaProject());
  }

  public void ttestAddSumFile3() {
    String af[] = {"/home/cvsroot/essp/essp2/esspfw/src/c2s/t.java,v"
                  , "/home/cvsroot/essp/essp2/esspfw/src/c2s/tt.xml,v"
                  , "/home/cvsroot/essp/essp2/esspfw/src/s.java,v"
                  , "/home/cvsroot/essp/essp2/esspfw/src/dir/ss.java,v"
                  , "/home/cvsroot/essp/essp2/esspfw/notjava.xml,v"
    };

    for (int i = 0; i < af.length; i++) {
      r.addCvsFile(af[i]);
    }
    r.secondPass();

    JavaRoot m = r.getRoot();

    assertEquals(af.length, m.getAllFileNum());
    assertEquals(1, m.getProjectNum());

    JavaProject ep = m.getProject("esspfw");
    assertNotNull(ep);
    assertEquals(5, ep.getAllFileNum());
    assertEquals(3, ep.getAllModuleNum());
    assertEquals(3, ep.getAllClassNum());
    assertEquals(3, ep.getAllPackageNum());

    CvsModule eem = ep.getSubModule("essp2/esspfw/src");
    assertNotNull(eem);
    assertTrue(eem.isJavaPackage());
    JavaPackage eep = (JavaPackage) eem;
    assertEquals(null, eep.getFullPackageName());
    assertNull(eep.getSuperPackage());
    assertEquals(4, eem.getAllFileNum());
    assertEquals(2, eem.getAllModuleNum());
    assertEquals(3, eem.getAllClassNum());
    assertEquals(2, eem.getAllPackageNum());

    CvsModule eeem = eep.getSubModule("essp2/esspfw/src/c2s");
    assertTrue(eeem.isJavaPackage());

    CvsFile eeeef = eeem.getSubFile("essp2/esspfw/src/c2s/t.java");
    assertNotNull(eeeef);
    assertTrue(eeeef.isClassFile());
    JavaClass eeeej = (JavaClass) eeeef;
    assertEquals("t.java", eeeej.getName());
    assertEquals("essp2/esspfw/src/c2s/t.java", eeeej.getFullName());
    assertEquals("t", eeeej.getClassName());
    assertEquals("c2s.t", eeeej.getFullClassName());
    assertEquals(ep, eeeej.getJavaProject());
    assertEquals(eeem, eeeej.getJavaPackage());

    CvsFile eeeef2 = eeem.getSubFile("essp2/esspfw/src/c2s/tt.xml");
    assertNotNull(eeeef2);
    assertFalse(eeeef2.isClassFile());
    assertEquals("tt.xml", eeeef2.getName());
    assertEquals("essp2/esspfw/src/c2s/tt.xml", eeeef2.getFullName());
    assertEquals(ep, eeeef2.getJavaProject());
  }

  public void ttestAddRevisionDetail(){
    CvsRevision rev = new CvsRevision();
    r.addRevisionDetail(rev, "date: 2006/02/22 08:23:54;  author: rongxiao;  state: Exp;  lines: +11 -10");
    assertEquals("rongxiao", rev.getAuthor());
    assertEquals(11, rev.getLineAdd());
    assertEquals(10, rev.getLineSubstract());
    assertNull(rev.getMsg());
    assertEquals("Exp", rev.getState());
    assertEquals(2006, rev.getDate().get(Calendar.YEAR));
    System.out.println(rev.getDate().getTime());
    assertEquals(1, rev.getDate().get(Calendar.MONTH));
    assertEquals(22, rev.getDate().get(Calendar.DAY_OF_MONTH));
    assertEquals(8, rev.getDate().get(Calendar.HOUR_OF_DAY));
    assertEquals(23, rev.getDate().get(Calendar.MINUTE));
    assertEquals(54, rev.getDate().get(Calendar.SECOND));
  }

  public void ttestFetchDate(){
    try {
      r.fetchDate();
    } catch (Exception ex) {
      ex.printStackTrace();
      fail();
    }
  }

  public void testallUsersCreateFileNum(){

  }


}
