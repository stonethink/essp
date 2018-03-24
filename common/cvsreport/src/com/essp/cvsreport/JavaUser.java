package com.essp.cvsreport;

import java.util.*;
import java.util.Map.Entry;

public class JavaUser {
  String userName;
  public JavaUser(String userName) {
    this.userName = userName;
  }

  //组成为(projectName, CvsFile list )
  public Map projectFile_Create = new TreeMap(new StringComparator());

  //下面三个Map的组成为(projectName, CvsRevision list )
  public Map projectFile_AddToday = new TreeMap(new StringComparator());
  public Map projectFile_ModifyToday = new TreeMap(new StringComparator());
  public Map projectFile_RemoveToday = new TreeMap(new StringComparator());
  public Map projectFile_DeleteToday = new TreeMap(new StringComparator());

  public int fileNum_Create = 0;
  public int fileNum_AddToday = 0;
  public int fileNum_ModifyToday = 0;
  public int fileNum_RemoveToday = 0;
  public int fileNum_DeleteToday = 0;

  //projectName list
  public List projects = new ArrayList();

  public void clear() {
    projectFile_Create.clear();

    projectFile_AddToday.clear();
    projectFile_ModifyToday.clear();
    projectFile_RemoveToday.clear();
    projectFile_DeleteToday.clear();

    fileNum_Create = 0;
    fileNum_AddToday = 0;
    fileNum_ModifyToday = 0;
    fileNum_RemoveToday = 0;
    fileNum_DeleteToday = 0;
  }

  public void addProject(JavaProject project) {
    String projectName = project.getName();
    for (Iterator iter = project.allFiles.values().iterator(); iter.hasNext(); ) {
      CvsFile f = (CvsFile) iter.next();

      addFile_Create(projectName, f);

      for (Iterator iterRev = f.getRevisions().iterator(); iterRev.hasNext(); ) {
        CvsRevision rev = (CvsRevision) iterRev.next();

        String author = rev.getAuthor();
        if (author == null) {
          author = Constant.AUTHOR_UNKNOWN;
        }
        if (author.equals(this.userName)) {
          addFile_AdminToday(projectName, rev);
        }
      }
    }

    for (Iterator iter = project.allDeletedFiles.values().iterator(); iter.hasNext(); ) {
      CvsFile f = (CvsFile) iter.next();

      //被delete了的文件只看最后一个版本
      CvsRevision rev = (CvsRevision) f.lastRevision;
      if (rev == null) {
        throw new CvsException(CvsException.DATA_INVALID,
                               "The deleted file[" + f.getFullName() +
                               "] has no revision.");
      }

      String author = rev.getAuthor();
      if (author == null) {
        author = Constant.AUTHOR_UNKNOWN;
      }
      if (author.equals(this.userName)) {
        addFile_DeleteToday(rev);
      }
    }

  }

  private void addFile_Create(String projectName, CvsFile f) {

    if (f.getFirstRevision() != null) {
      String author = f.getFirstRevision().getAuthor();
      if (author == null) {
        author = Constant.AUTHOR_UNKNOWN;
      }

      if (author.equals(this.userName)) {

        List files = (List) projectFile_Create.get(projectName);
        if (files == null) {
          files = new ArrayList();
          projectFile_Create.put(projectName, files);
        }

        files.add(f);
      }
    }
  }

  private void addFile_AdminToday(String projectName, CvsRevision rev) {

    if (rev.getDate() == null || HtmlHelp.isInToday(rev.getDate()) == false) {
      return;
    }

    List admins;
    if (rev.isAddAction()) {
      admins = (List) projectFile_AddToday.get(projectName);

      if (admins == null) {
        admins = new ArrayList();
        projectFile_AddToday.put(projectName, admins);
      }
    } else if (rev.isRemoveAction()) {
      admins = (List) projectFile_RemoveToday.get(projectName);

      if (admins == null) {
        admins = new ArrayList();
        projectFile_RemoveToday.put(projectName, admins);
      }
    } else if (rev.isModifyAction()) {
      admins = (List) projectFile_ModifyToday.get(projectName);

      if (admins == null) {
        admins = new ArrayList();
        projectFile_ModifyToday.put(projectName, admins);
      }
    } else {
      throw new CvsException(CvsException.DATA_INVALID,
                             "Error.");
    }

    admins.add(rev);
  }

  private void addFile_DeleteToday(CvsRevision rev) {
    if (rev.getDate() == null || HtmlHelp.isInToday(rev.getDate()) == false) {
      return;
    }

    String author = rev.getAuthor();
    if (author == null) {
      author = Constant.AUTHOR_UNKNOWN;
    }

    List admins = (List) projectFile_DeleteToday.get(author);
    if (admins == null) {
      admins = new ArrayList();
      projectFile_DeleteToday.put(author, admins);
    }

    admins.add(rev);
  }


  public void secondPass() {
    Map projectMap = new TreeMap(new StringComparator());

    Map[] maps = new Map[] {
                 projectFile_Create, projectFile_AddToday
                 , projectFile_ModifyToday, projectFile_DeleteToday
                 , projectFile_RemoveToday
    };
    int nums[] = new int[5];
    for (int i = 0; i < maps.length; i++) {
      Map map = maps[i];
      for (Iterator iterP = map.entrySet().iterator(); iterP.hasNext(); ) {
        Entry item = (Entry) iterP.next();
        String projectName = (String)item.getKey();
        List fileList = (List)item.getValue();

        nums[i] += fileList.size();
        projectMap.put(projectName, projectName);
      }
    }
    fileNum_Create = nums[0];
    fileNum_AddToday = nums[1];
    fileNum_ModifyToday = nums[2];
    fileNum_DeleteToday = nums[3];
    fileNum_RemoveToday = nums[4];

    for (Iterator iter = projectMap.keySet().iterator(); iter.hasNext(); ) {
      String projectName = (String)iter.next();
      projects.add(projectName);
    }
  }

  public String getName(){
    return this.userName;
  }
}
