package com.essp.cvsreport.test;


import junit.framework.TestCase;
import java.io.PrintStream;
import com.essp.cvsreport.CvsFile;
import com.essp.cvsreport.JavaRoot;
import com.essp.cvsreport.JavaProject;
import com.essp.cvsreport.CvsModule;
import com.essp.cvsreport.JavaClass;
import com.essp.cvsreport.JavaPackage;

public class JavaRootTest extends TestCase {
  CvsFile f;
  CvsFile af[];
  JavaRoot m;
  PrintStream out = System.out;

  protected void setUp() throws Exception {
    m = new JavaRoot();
    m.setFullName("essp2");
    m.setName("essp2");
  }

  public void testAddSumFile() {
    f = new CvsFile();
    f.setFullName("essp2/t.xml");
    m.addSubFile(f);
    m.secondPass();

    assertEquals(1, m.getAllFileNum());
    assertEquals(0, m.getAllModuleNum());
    assertEquals(0, m.getAllClassNum());
    assertEquals(0, m.getAllPackageNum());
    CvsFile ef = m.getSubFile("essp2/t.xml");
    assertNotNull(ef);
    assertEquals("t.xml", ef.getName());
    assertEquals("essp2/t.xml", ef.getFullName());
    assertEquals("xml", ef.getExtend());
    assertNull(ef.getPackageInfo());
    assertNull(ef.getProjectInfo());
    assertEquals(m, ef.getCvsModule());

    assertEquals(0, m.getProjectNum());
  }

  public void testAddSumFile02() {
    f = new CvsFile();
    f.setFullName("essp2/esspfw/t.xml");
    m.addSubFile(f);
    m.secondPass();

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



  public void testAddSubFile2() {
    af = new CvsFile[4];
    af[0] = new CvsFile();
    af[0].setFullName("essp2/pms/t.xml");

    af[1] = new CvsFile();
    af[1].setFullName("essp2/esspfw/tt.xml");

    af[2] = new CvsFile();
    af[2].setFullName("essp2/esspfw/config/s.xml");

    af[3] = new CvsFile();
    af[3].setFullName("essp2/esspfw/config/dir/ss.xml");

    for (int i = 0; i < af.length; i++) {
      m.addSubFile(af[i]);
    }
    m.secondPass();

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

  public void testAddSumFile3() {
    af = new CvsFile[5];
    af[0] = new JavaClass();
    af[0].setFullName("essp2/esspfw/src/c2s/t.java");

    af[1] = new CvsFile();
    af[1].setFullName("essp2/esspfw/src/c2s/tt.xml");

    af[2] = new JavaClass();
    af[2].setFullName("essp2/esspfw/src/s.java");

    af[3] = new JavaClass();
    af[3].setFullName("essp2/esspfw/src/dir/ss.java");

    af[4] = new CvsFile();
    af[4].setFullName("essp2/esspfw/notjava.xml");

    for (int i = 0; i < af.length; i++) {
      m.addSubFile(af[i]);
    }
    m.secondPass();

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
    JavaPackage eep = (JavaPackage)eem;
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
    JavaClass eeeej = (JavaClass)eeeef;
    assertEquals("t.java", eeeej.getName());
    assertEquals("essp2/esspfw/src/c2s/t.java", eeeej.getFullName());
    assertEquals("t", eeeej.getClassName());
    assertEquals("c2s.t", eeeej.getFullClassName());
    assertEquals( ep, eeeej.getJavaProject());
    assertEquals( eeem, eeeej.getJavaPackage());

    CvsFile eeeef2 = eeem.getSubFile("essp2/esspfw/src/c2s/tt.xml");
    assertNotNull(eeeef2);
    assertFalse(eeeef2.isClassFile());
    assertEquals("tt.xml", eeeef2.getName());
    assertEquals("essp2/esspfw/src/c2s/tt.xml", eeeef2.getFullName());
    assertEquals( ep, eeeef2.getJavaProject());
  }

}
