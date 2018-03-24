package com.essp.cvsreport;

import java.io.*;
import java.util.*;

public class JavaRoot extends CvsModule {
  public Map projects = new TreeMap(new StringComparator());
  public Map users = new TreeMap(new StringComparator());

  private PrintStream out = System.out;

  public int fileNum_Create = 0;
  public int fileNum_AddToday = 0;
  public int fileNum_ModifyToday = 0;
  public int fileNum_RemoveToday = 0;
  public int fileNum_DeleteToday = 0;

  public JavaRoot() {
  }

  public void addSubFile(CvsFile f) {
    //project
    String projectName = f.getProjectInfo();
    if (projectName == null) {
      //该文件直接在root

      f.setCvsModule(this);
      f.setJavaProject(null);
      subFiles.put(f.getFullName(), f);

      return;
    }

    if (Constant.PROJECT_FILTER.indexOf(";" + projectName + ";") != -1) {
      return;
    }

    JavaProject project = this.getProject(projectName);
    if (project == null) {
      project = new JavaProject();
      project.setName(projectName);
      project.setFullName(Constant.ROOT_PACKAGE + "/" + projectName);
      project.setJavaProject(null);
      project.setSuperModule(this);

      addProject(project);
    }

    project.addSubFile(f);
  }

  public void secondPass() {
    super.secondPass();

    for (Iterator iterP = projects.values().iterator(); iterP.hasNext(); ) {
      JavaProject project = (JavaProject) iterP.next();
      project.secondPass();

      for (Iterator iterU = project.users.iterator(); iterU.hasNext(); ) {
        String userName = (String)iterU.next();

        JavaUser user = (JavaUser)users.get(userName);
        if( user == null ){
          user = new JavaUser(userName);
          users.put(userName, user);
        }
        user.addProject(project);
      }

      fileNum_AddToday += project.file_AddToday.size();
      fileNum_ModifyToday += project.file_ModifyToday.size();
      fileNum_RemoveToday += project.file_RemoveToday.size();
      fileNum_DeleteToday += project.file_DeleteToday.size();
    }
    fileNum_Create += getAllFileNum();

    for (Iterator iterP = users.values().iterator(); iterP.hasNext(); ) {
      JavaUser user = (JavaUser) iterP.next();
      user.secondPass();
    }
  }

  public void clear(){
    super.clear();

    for (Iterator iterP = projects.values().iterator(); iterP.hasNext(); ) {
      JavaProject project = (JavaProject) iterP.next();
      project.clear();
    }

    for (Iterator iterP = users.values().iterator(); iterP.hasNext(); ) {
      JavaUser user = (JavaUser) iterP.next();
      user.clear();
    }

    projects.clear();
    users.clear();

    fileNum_Create = 0;
    fileNum_AddToday = 0;
    fileNum_ModifyToday = 0;
    fileNum_RemoveToday = 0;
    fileNum_DeleteToday = 0;
  }

  public JavaProject getProject(String name) {
    return (JavaProject) projects.get(name);
  }

  public JavaUser getUser(String name) {
    return (JavaUser) users.get(name);
  }

  public CvsFile getFile(String fileFullName) {
    CvsFile tmp = new CvsFile();
    tmp.setFullName(fileFullName);
    String projectName = tmp.getProjectInfo();

    CvsFile f = null;
    if( projectName == null ){
      f= (CvsFile)getSubFile(fileFullName);
    }else{
      f = (CvsFile) getProject(projectName).allFiles.get(fileFullName);
    }
    if( f== null ){
      f = (CvsFile) getProject(projectName).allDeletedFiles.get(fileFullName);
    }
    return f;
  }




  public void addProject(JavaProject project) {
    projects.put(project.getName(), project);
  }

  public int getProjectNum() {
    return projects.size();
  }

  public int getUserNum() {
    return users.size();
  }

  public void dump() {
    String t = "\t\t\t\t";
    out.println("+++++++++++++++++++++------- dump root[" + getName() +
                "] +++++++++++++++++++++-------");
    out.println("There are [" + getProjectNum() + "]projects "
                + " [" + getAllFileNum() + "]files "
                + " [" + getAllClassNum() + "]classes "
                );
    for (Iterator iter = projects.entrySet().iterator(); iter.hasNext(); ) {
      Map.Entry item = (Map.Entry) iter.next();
      String pName = (String) item.getKey();
      JavaProject project = (JavaProject) item.getValue();
      out.println("\t" + pName + ": " +
                  t.substring(0, (20 - pName.length()) / 8 + 1)
                  + " files[" + project.getAllFileNum() + "]\t"
                  + " folders[" + project.getAllModuleNum() + "]\t"
                  + " classes[" + project.getAllClassNum() + "]\t"
                  + " packages[" + project.getAllPackageNum() + "]\t"
                  );
    }
    out.println();
  }

}
