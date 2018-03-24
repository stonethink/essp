package com.essp.cvsreport;

import java.util.*;
import java.util.Map.Entry;

public class JavaProject extends CvsModule{
  public Map allFiles = new TreeMap(new StringComparator());
  public Map allDeletedFiles = new TreeMap(new StringComparator());
  public Map allClasses = new TreeMap(new StringComparator());
  public Map allModules = new TreeMap(new StringComparator());
  public Map allPackages = new TreeMap(new StringComparator());


  //组成为(userName, CvsFile list )
  public Map userFile_Create = new TreeMap(new StringComparator());

  //下面三个Map的组成为(userName, CvsRevision list )
  public Map userFile_AddToday = new TreeMap(new StringComparator());
  public Map userFile_ModifyToday = new TreeMap(new StringComparator());
  public Map userFile_RemoveToday = new TreeMap(new StringComparator());
  public Map userFile_DeleteToday = new TreeMap(new StringComparator());

  public List file_Create = new ArrayList();
  public List file_AddToday = new ArrayList();
  public List file_ModifyToday = new ArrayList();
  public List file_RemoveToday = new ArrayList();
  public List file_DeleteToday = new ArrayList();

  //userName list
  public List users = new ArrayList();

  public JavaProject() {
  }

  public void clear(){
    super.clear();
    allFiles.clear();
    allDeletedFiles.clear();
    allClasses.clear();
    allModules.clear();
    allPackages.clear();
    userFile_Create.clear();
    userFile_AddToday.clear();
    userFile_ModifyToday.clear();
    userFile_RemoveToday.clear();
    userFile_DeleteToday.clear();
    file_Create.clear();
    file_AddToday.clear();
    file_ModifyToday.clear();
    file_RemoveToday.clear();
    file_DeleteToday.clear();
    users.clear();
  }

  public void secondPass(){
    super.secondPass();

    for (Iterator iter = allFiles.values().iterator(); iter.hasNext(); ) {
      CvsFile f = (CvsFile) iter.next();

      addFile_Create(f);

      for (Iterator iterRev = f.getRevisions().iterator(); iterRev.hasNext(); ) {
        CvsRevision rev = (CvsRevision) iterRev.next();

        addFile_AdminToday(rev);
      }
    }

    for (Iterator iter = allDeletedFiles.values().iterator(); iter.hasNext(); ) {
      CvsFile f = (CvsFile) iter.next();

      //被delete了的文件只看最后一个版本
      CvsRevision rev = (CvsRevision) f.lastRevision;
      if (rev == null) {
        throw new CvsException(CvsException.DATA_INVALID,
                               "The deleted file[" + f.getFullName() +
                               "] has no revision.");
      }
      addFile_DeleteToday(rev);
    }

    fetchUsers();

    for (Iterator iter = allFiles.values().iterator(); iter.hasNext(); ) {
      file_Create.add(iter.next());
    }

  }

  protected void addToAllFiles(CvsFile f) {
    super.addToAllFiles(f);

    if( f.isDead() ){
      allDeletedFiles.put(f.getFullName(), f);
    }else{
      allFiles.put(f.getFullName(), f);

      if (f.isClassFile()) {
        allClasses.put(((JavaClass) f).getFullClassName(), f);
      }
    }
  }

  protected void addToAllModules(CvsModule m) {
    super.addToAllModules(m);

    if( m.isDead() == false ){

      allModules.put(m.getFullName(), m);

      if (m.isJavaPackage()) {
        allPackages.put(((JavaPackage) m).getFullPackageName(), m);
      }
    }
  }

  public boolean isJavaProject() {
    return true;
  }

  private void addFile_Create(CvsFile f) {
    if( f.getFirstRevision() != null ){
      String author = f.getFirstRevision().getAuthor();
      if (author == null) {
        author = Constant.AUTHOR_UNKNOWN;
      }

      List files = (List) userFile_Create.get(author);
      if (files == null) {
        files = new ArrayList();
        userFile_Create.put(author, files);
      }

      files.add(f);
    }
  }

  private void addFile_AdminToday(CvsRevision rev) {

    if (rev.getDate() == null || HtmlHelp.isInToday(rev.getDate()) == false) {
      return;
    }

    String author = rev.getAuthor();
    if (author == null) {
      author = Constant.AUTHOR_UNKNOWN;
    }

    List admins;

    if (rev.isAddAction()) {
      admins = (List) userFile_AddToday.get(author);

      if (admins == null) {
        admins = new ArrayList();
        userFile_AddToday.put(author, admins);
      }
      file_AddToday.add(rev);
    } else if (rev.isRemoveAction()) {
      admins = (List) userFile_RemoveToday.get(author);

      if (admins == null) {
        admins = new ArrayList();
        userFile_RemoveToday.put(author, admins);
      }
      file_RemoveToday.add(rev);
    } else if (rev.isModifyAction()) {
      admins = (List) userFile_ModifyToday.get(author);

      if (admins == null) {
        admins = new ArrayList();
        userFile_ModifyToday.put(author, admins);
      }
      file_ModifyToday.add(rev);
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

    List admins = (List) userFile_DeleteToday.get(author);
    if (admins == null) {
      admins = new ArrayList();
      userFile_DeleteToday.put(author, admins);
    }
    file_DeleteToday.add(rev);

    admins.add(rev);
  }

  private void fetchUsers(){
    Map[] maps = new Map[]{
               userFile_Create, userFile_AddToday
               , userFile_ModifyToday, userFile_DeleteToday
               , userFile_RemoveToday
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
}
