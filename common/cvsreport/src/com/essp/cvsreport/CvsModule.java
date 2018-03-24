package com.essp.cvsreport;
import java.util.*;
import java.io.PrintStream;

public class CvsModule {
  PrintStream out = System.out;

  JavaProject javaProject;
  CvsModule superModule;
  Map subModules = new TreeMap(new StringComparator());
  Map subFiles = new TreeMap(new StringComparator());

  String name;
  String fullName;
  boolean isDead = false;

  //本包 含有的所有文件/class/module/package数（包括子包的）
  int allFileNum = 0;
  boolean toOutputFileNum = false;
  int allClassNum = 0;
  boolean toOutputClassNum = false;
  int allModuleNum = 0;
  boolean toOutputModuleNum = false;
  int allPackageNum = 0;
  boolean toOutputPackageNum = false;
  int allDeletedFileNum = 0;
  boolean toOutputDeletedFileNum = false;

  public CvsModule() {
  }

  public void clear() {
    for (Iterator iter = subModules.values().iterator(); iter.hasNext(); ) {
      CvsModule subModule = (CvsModule)iter.next();
      subModule.clear();
    }

    subModules.clear();
    subFiles.clear();
    allFileNum = 0;
    toOutputFileNum = false;
    allClassNum = 0;
    toOutputClassNum = false;
    allModuleNum = 0;
    toOutputModuleNum = false;
    allPackageNum = 0;
    toOutputPackageNum = false;
    allDeletedFileNum = 0;
    toOutputDeletedFileNum = false;
  }

  //加入在本Module下的文件（可能直接在本module下，也可能在子module下）
  //本程序会为子module创建CvsModule对象
  public void addSubFile(CvsFile f) {
    String fileFullName = f.getFullName();
    if (fileFullName.startsWith(getFullName()) == false) {
      throw new RuntimeException(
          "Error add a file to module.The file is not in the module");
    }

    int nextModuleStartAt = getFullName().length() + 1;
    if (nextModuleStartAt >= fileFullName.length()) {
      throw new RuntimeException(
          "Error add a file to module.It's has none file name.");
    }
    int nextModuleEndAt = fileFullName.indexOf("/", nextModuleStartAt);
    if (nextModuleEndAt == -1) {
      //该文件就在本module下

      f.setCvsModule(this);
      if( this.isJavaProject() ){
          f.setJavaProject((JavaProject)this);
        }else{
          f.setJavaProject(getJavaProject());
        }

      subFiles.put(f.getFullName(), f);
    } else {
      //该文件在子module下

      String subModuleName = fileFullName.substring(nextModuleStartAt,
          nextModuleEndAt);
      if (Constant.MODULE_FILTER.indexOf(";"+subModuleName+";") != -1) {
        return;
      }

      String subModuleFullName = getFullName() + "/" + subModuleName;
      CvsModule subModule = getSubModule(subModuleFullName);
      if (subModule == null) {
        if (subModuleName.equals("src")) {
          //这是一个java package，它没有任何名字.
          JavaPackage javaPackage = new JavaPackage();
          javaPackage.setSuperPackage(null);
          javaPackage.setFullPackageName(null);
          subModule = javaPackage;
        } else if (this.isJavaPackage()) {
          //这是一个java package
          JavaPackage thisPackage = (JavaPackage)this;
          JavaPackage javaPackage = new JavaPackage();
          javaPackage.setSuperPackage(thisPackage);
          if (thisPackage.getFullPackageName() != null) {
            javaPackage.setFullPackageName(thisPackage.getFullPackageName() +
                                           "." +
                                           subModuleName);
          } else {
            javaPackage.setFullPackageName(subModuleName);
          }
          subModule = javaPackage;
        } else {
          //这是个普通的module
          subModule = new CvsModule();
        }
        subModule.setName(subModuleName);
        subModule.setSuperModule(this);
        subModule.setFullName(subModuleFullName);
        if( this.isJavaProject() ){
          subModule.setJavaProject((JavaProject)this);
        }else{
          subModule.setJavaProject(getJavaProject());
        }

        this.subModules.put(subModule.getFullName(), subModule);
      }

      subModule.addSubFile(f);
    }
  }

  public void secondPass(){
    this.isDead = true;
    List RemovedFiles = new ArrayList();
    List RemovedModules = new ArrayList();
    for (Iterator iter = getSubFiles().values().iterator(); iter.hasNext(); ) {
      CvsFile f = (CvsFile)iter.next();
      f.secondPass();

      if(isDead && f.isDead() == false ){
        this.isDead = false;
      }
      addToAllFiles(f);

      if (f.isDead()) {
        RemovedFiles.add(f.getFullName());
      }
    }

    for (Iterator iter = getSubModules().values().iterator(); iter.hasNext(); ) {
      CvsModule m = (CvsModule)iter.next();
      m.secondPass();

      if(isDead && m.isDead() == false ){
        this.isDead = false;
      }
      addToAllModules(m);

      if (m.isDead()) {
        RemovedModules.add(m.getFullName());
      }
    }

    //将其从Map -- subFiles中删除
    for (Iterator iter = RemovedFiles.iterator(); iter.hasNext(); ) {
      String item = (String)iter.next();
      subFiles.remove(item);
    }

    //将其从Map -- subModules中删除
    for (Iterator iter = RemovedModules.iterator(); iter.hasNext(); ) {
      String item = (String)iter.next();
      subModules.remove(item);
    }
  }


  protected void addToAllFiles(CvsFile f) {
    if( f.isDead() ){
      allDeletedFileNum++;

      //System.out.println("dead file["+f.getFullName()+"]");
    }else{

      allFileNum++;
      if (f.isClassFile()) {
        allClassNum++;
      }
    }

    if (getSuperModule() != null) {
      getSuperModule().addToAllFiles(f);
    }

//    if ((allFileNum + 1 % 500) == 0) {
//      if (toOutputClassNum) {
//        out.println("Add " + allFileNum + " file.");
//        this.toOutputFileNum = false;
//      }
//    } else {
//      this.toOutputFileNum = true;
//    }
//
//    if( (allClassNum+1 % 500) == 0 ){
//      if (toOutputClassNum) {
//        out.println("Add "+allClassNum+" class file.");
//        this.toOutputClassNum = false;
//      }
//    } else {
//      this.toOutputClassNum = true;
//    }

  }

  protected void addToAllModules(CvsModule m) {
    if( m.isDead() == true ){

    }else{
      allModuleNum++;
      if (m.isJavaPackage()) {
        allPackageNum++;
      }
    }

    if( getSuperModule() != null ){
      getSuperModule().addToAllModules(m);
    }

    if ((allModuleNum + 1 % 500) == 0) {
      if (toOutputModuleNum) {
        out.println("Add " + allModuleNum + " module.");
        this.toOutputModuleNum = false;
      }
    } else {
      this.toOutputModuleNum = true;
    }

    if ((allPackageNum + 1 % 500) == 0) {
      if (toOutputPackageNum) {
        out.println("Add " + allPackageNum + " class package.");
        this.toOutputPackageNum = false;
      }
    } else {
      this.toOutputPackageNum = true;
    }
  }


  public CvsModule getSubModule(String fullName) {
    return (CvsModule)this.subModules.get(fullName);
  }

  public CvsFile getSubFile(String fullName) {
    return (CvsFile)this.subFiles.get(fullName);
  }

  public String getFullName() {
    return fullName;
  }

  public JavaProject getJavaProject() {
    return javaProject;
  }

  public void setJavaProject(JavaProject javaProject) {
    this.javaProject = javaProject;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public CvsModule getSuperModule() {
    return superModule;
  }

  public void setSuperModule(CvsModule superModule) {
    this.superModule = superModule;
  }

  public int getAllFileNum() {
    return allFileNum;
  }

  public int getAllModuleNum() {
    return allModuleNum;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public boolean isJavaPackage() {
    return false;
  }

  public boolean isJavaProject() {
    return false;
  }


  public int getAllPackageNum() {
    return allPackageNum;
  }

  public int getAllClassNum() {
    return allClassNum;
  }
  public Map getSubFiles() {
    return subFiles;
  }
  public Map getSubModules() {
    return subModules;
  }

  public boolean isDead(){
    return this.isDead;
  }
  public int getAllDeletedFileNum() {
    return allDeletedFileNum;
  }
}
