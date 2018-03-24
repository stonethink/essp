package com.essp.cvsreport;

import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

public class JavaPeriod {
  Calendar begin = null;
  Calendar end = null;

  public List users = new ArrayList();
  public List projects = new ArrayList();
  JavaRoot root ;

  //下面三个Map的组成为(projectName, CvsRevision list )
  public Map projectFile_Add = new TreeMap(new StringComparator());
  public Map projectFile_Modify = new TreeMap(new StringComparator());
  public Map projectFile_Remove = new TreeMap(new StringComparator());
  public Map projectFile_Delete = new TreeMap(new StringComparator());

  public Map userFile_Add = new TreeMap(new StringComparator());
  public Map userFile_Modify = new TreeMap(new StringComparator());
  public Map userFile_Remove = new TreeMap(new StringComparator());
  public Map userFile_Delete = new TreeMap(new StringComparator());

  public int fileNum_Add    = 0;
  public int fileNum_Modify = 0;
  public int fileNum_Remove = 0;
  public int fileNum_Delete = 0;

  public JavaPeriod( JavaRoot root, Calendar begin, Calendar end) {
    this.root = root;
    this.begin = begin;
    this.end = end;
    secondPass();
  }

  private void clear(){
    projectFile_Add.clear();
    projectFile_Modify.clear();
    projectFile_Remove.clear();
    projectFile_Delete.clear();

    userFile_Add.clear();
    userFile_Modify.clear();
    userFile_Remove.clear();
    userFile_Delete.clear();


    fileNum_Add    = 0;
    fileNum_Modify    = 0;
    fileNum_Remove    = 0;
    fileNum_Delete    = 0;

    projects.clear();
    users.clear();
  }

  public void secondPass() {
    clear();
    for (Iterator iterP = root.projects.values().iterator(); iterP.hasNext(); ) {
      JavaProject project = (JavaProject) iterP.next();

      for (Iterator iter = project.allFiles.values().iterator(); iter.hasNext(); ) {
        CvsFile f = (CvsFile) iter.next();

        for (Iterator iterRev = f.getRevisions().iterator(); iterRev.hasNext(); ) {
          CvsRevision rev = (CvsRevision) iterRev.next();

          addFile_Admin(project.getName(), rev);
        }
      }

      for (Iterator iter = project.allDeletedFiles.values().iterator();
                           iter.hasNext(); ) {
        CvsFile f = (CvsFile) iter.next();

        //被delete了的文件只看最后一个版本
        CvsRevision rev = (CvsRevision) f.lastRevision;
        if (rev == null) {
          throw new CvsException(CvsException.DATA_INVALID,
                                 "The deleted file[" + f.getFullName() +
                                 "] has no revision.");
        }

        addFile_Delete(rev);
      }
    }

    fetchProjects();
    fetchUsers();
  }

  private void addFile_Admin(String projectName, CvsRevision rev) {
    if (rev.getDate() == null || HtmlHelp.isInPeriod(begin, end, rev.getDate()) == false) {
      return;
    }
    String author = rev.getAuthor();
    if( author == null ){
      author = Constant.AUTHOR_UNKNOWN;
    }

    List adminsInProject;
    List adminsByUser;
    if (rev.isAddAction()) {
      adminsInProject = (List) projectFile_Add.get(projectName);
      adminsByUser = (List)userFile_Add.get(author);

      if (adminsInProject == null) {
        adminsInProject = new ArrayList();
        projectFile_Add.put(projectName, adminsInProject);
      }

      if( adminsByUser == null ){
        adminsByUser = new ArrayList();
        userFile_Add.put(author, adminsByUser);
      }
    } else if (rev.isRemoveAction()) {
      adminsInProject = (List) projectFile_Remove.get(projectName);
      adminsByUser = (List)userFile_Remove.get(author);

      if (adminsInProject == null) {
        adminsInProject = new ArrayList();
        projectFile_Remove.put(projectName, adminsInProject);
      }

      if( adminsByUser == null ){
        adminsByUser = new ArrayList();
        userFile_Remove.put(author, adminsByUser);
      }
    } else if (rev.isModifyAction()) {
      adminsInProject = (List) projectFile_Modify.get(projectName);
      adminsByUser = (List)userFile_Modify.get(author);

      if (adminsInProject == null) {
        adminsInProject = new ArrayList();
        projectFile_Modify.put(projectName, adminsInProject);
      }

      if( adminsByUser == null ){
        adminsByUser = new ArrayList();
        userFile_Modify.put(author, adminsByUser);
      }
    } else {
      throw new CvsException(CvsException.DATA_INVALID,
                             "Error.");
    }

    adminsInProject.add(rev);
    adminsByUser.add(rev);
  }

  private void addFile_Delete(CvsRevision rev) {
    if (rev.getDate() == null || HtmlHelp.isInPeriod(begin, end, rev.getDate()) == false) {
      return;
    }

    String author = rev.getAuthor();
    if (author == null) {
      author = Constant.AUTHOR_UNKNOWN;
    }

    List adminsInProject = (List) projectFile_Delete.get(rev.getCvsFile().getJavaProject().getName());
    List adminsByUser = (List) userFile_Delete.get(author);
    if (adminsInProject == null) {
      adminsInProject = new ArrayList();
      projectFile_Delete.put(rev.getCvsFile().getJavaProject().getName(), adminsInProject);
    }
    if (adminsByUser == null) {
      adminsByUser = new ArrayList();
      userFile_Delete.put(author, adminsByUser);
    }

    adminsInProject.add(rev);
    adminsByUser.add(rev);
  }

  private void fetchProjects(){
    Map[] maps = new Map[]{
               projectFile_Add
               , projectFile_Modify, projectFile_Delete
               , projectFile_Remove
    };
    int fileNum[] = new int[4];
    Map projectMap = new TreeMap(new StringComparator());
    for (int i = 0; i < maps.length; i++) {
      for (Iterator iter = maps[i].entrySet().iterator(); iter.hasNext(); ) {
        Entry entry = (Entry)iter.next();
        String projectName = (String)entry.getKey();
        List fileList = (List)entry.getValue();
        projectMap.put(projectName, projectName);
        fileNum[i] += fileList.size();
      }
    }
    for (Iterator iter = projectMap.keySet().iterator(); iter.hasNext(); ) {
      projects.add(iter.next());
    }

    fileNum_Add = fileNum[0];
    fileNum_Modify = fileNum[1];
    fileNum_Delete = fileNum[2];
    fileNum_Remove = fileNum[3];
  }

  private void fetchUsers(){
    Map[] maps = new Map[]{
               userFile_Add
               , userFile_Modify, userFile_Delete
               , userFile_Remove
    };
    Map userMap = new TreeMap(new StringComparator());
    for (int i = 0; i < maps.length; i++) {
      for (Iterator iter = maps[i].keySet().iterator(); iter.hasNext(); ) {
        String userName = (String)iter.next();
        userMap.put(userName, userName);
      }
    }
    for (Iterator iter = userMap.keySet().iterator(); iter.hasNext(); ) {
      users.add(iter.next());
    }
  }
  public Calendar getBegin() {
    return begin;
  }
  public Calendar getEnd() {
    return end;
  }

  public void last(){
    end.set(Calendar.DAY_OF_MONTH, begin.get(Calendar.DAY_OF_MONTH)-1);
    end.set(Calendar.HOUR_OF_DAY, 23);
    end.set(Calendar.MINUTE, 59);
    end.set(Calendar.SECOND, 59);

    begin = (Calendar)end.clone();
    begin.set(Calendar.HOUR_OF_DAY, 0);
    begin.set(Calendar.MINUTE, 0);
    begin.set(Calendar.SECOND, 0);

    secondPass();
  }

  public void next(){
    begin.set(Calendar.DAY_OF_MONTH, end.get(Calendar.DAY_OF_MONTH)+1);
    begin.set(Calendar.HOUR_OF_DAY, 0);
    begin.set(Calendar.MINUTE, 0);
    begin.set(Calendar.SECOND, 0);

    end = (Calendar)begin.clone();
    end.set(Calendar.HOUR_OF_DAY, 23);
    end.set(Calendar.MINUTE, 59);
    end.set(Calendar.SECOND, 59);

    secondPass();
  }

}
