package com.essp.cvsreport;
import java.io.*;
import java.util.*;

public class CvsFile {
  PrintStream out = System.out;
  CvsModule cvsModule;
  JavaProject javaProject;

  //fullname = rootpackage + "/" + projectInfo + "/" + packageInfo + "/" + name
  String fullName;
  String projectInfo;
  String packageInfo;
  String name;
  //在cvs馆中，从root开始的路径(不含root)
  String extend;
  String rcsFile;

  List symbolics = new ArrayList();
  List revisions = new ArrayList();
  int revisionNum;
  CvsRevision firstRevision;//初始版本
  CvsRevision lastRevision;//最新的版本

  boolean isDead = false;

  public CvsFile() {
  }

  public void addSymbolic(CvsSymbolic sym){
    symbolics.add(sym);
  }

  public void addRevisions(CvsRevision rev) {
    revisions.add(rev);
    revisionNum++;
  }

  public void secondPass(){
    //设置版本
    CvsRevision early = null;
    CvsRevision curr = null;
    for (int i = getRevisionNum()-1; i >= 0 ; i--) {
      curr = (CvsRevision)getRevision(i);

      if (i == 0) {
        //最早的版本
        firstRevision = curr;
      }

      curr.secondPass(early);
      early = curr;
    }

    //最新的版本
    this.lastRevision = curr;
  }

  public CvsRevision getRevision(String id){
    for (int i = 0; i < getRevisions().size(); i++) {
      CvsRevision rev = (CvsRevision)getRevision(i);
      if( id.equals( rev.getId() ) ){
        return rev;
      }
    }

    return null;
  }

  public int getRevisionIndex(String id){
    for (int i = 0; i < getRevisions().size(); i++) {
      CvsRevision rev = (CvsRevision)getRevision(i);
      if( rev.getId().equals(id) ){
        return i;
      }
    }

    return -1;
  }

  public CvsRevision getRevision(int index){
      return (CvsRevision)getRevisions().get(index);
  }


  public CvsModule getCvsModule() {
    return cvsModule;
  }

  public String getExtend() {
    return extend;
  }

  public String getFullName() {
    return fullName;
  }

  //解析fullName
  public void setFullName(String fullName) {
    this.fullName = fullName;
    String rootpackage = Constant.ROOT_PACKAGE;

    if( fullName.startsWith(rootpackage) == false ){
      throw new CvsException(CvsException.PACKAGE_INVALID,"Error.The file["+fullName+"] is not in the root package[" +rootpackage+ "]");
    }

    int projectStartAt = (rootpackage + "/").length();
    if (projectStartAt >= fullName.length()) {
      throw new RuntimeException("Error file's fullName[" + fullName +
                                 "].It's has no name info.");
    }
    int projectEndAt = fullName.indexOf("/", projectStartAt);
    if (projectEndAt == -1) {
      //文件直接在root模块下
      setProjectInfo(null);
      setPackageInfo(null);
      setName(fullName.substring(projectStartAt));
      return;
    }
    String projectName = fullName.substring(projectStartAt, projectEndAt);
    setProjectInfo(projectName);

    int packageStartAt = projectEndAt + 1;
    if (packageStartAt >= fullName.length()) {
      throw new RuntimeException("Error file's fullName[" + fullName +
                                 "].It's has no name info.");
    }
    int packageEndAt = fullName.lastIndexOf("/");
    if (packageEndAt<packageStartAt || packageEndAt == -1) {
      //文件直接在project模块下
      setPackageInfo(null);
      setName(fullName.substring(packageStartAt));
      return;
    }
    String packageName = fullName.substring(packageStartAt, packageEndAt);
    setPackageInfo(packageName);

    int fileNameStartAt = packageEndAt + 1;
    if (fileNameStartAt >= fullName.length()) {
      throw new RuntimeException("Error file's fullName[" + fullName +
                                 "].It's has no name info.");
    }

    setName(fullName.substring(fileNameStartAt));
  }

  public boolean isClassFile(){
    return false;
  }

  public JavaProject getJavaProject() {
    return javaProject;
  }

  public String getName() {
    return name;
  }

  public String getRcsFile() {
    return rcsFile;
  }

  public int getRevisionNum() {
    return revisionNum;
  }

  public List getRevisions() {
    return revisions;
  }

  public List getSymbolics() {
    return symbolics;
  }

  public void setRevisionNum(int revisionNum) {
    this.revisionNum = revisionNum;
  }

  public void setRcsFile(String rcsFile) {
    int tagStartAt = rcsFile.indexOf( Constant.DELETE_MODULE_TAG );
    if( tagStartAt != -1 ){
      int tagEndAt = rcsFile.indexOf("/", tagStartAt);

      if (rcsFile.indexOf(Constant.DELETE_MODULE_TAG, tagEndAt) != -1) {
        //DELETE_MODULE_TAG出现两次,这不正常
        throw new CvsException(CvsException.DATA_INVALID, "The tag[" +
                               CvsException.DATA_INVALID +
                               "] appear twice in the rcs file[" + rcsFile +
                               "].Don't name the module with this name.");
      }

      rcsFile =rcsFile.substring(0, tagStartAt)
               +rcsFile.substring(tagEndAt+1);

      this.isDead = true;
    }

    this.rcsFile = rcsFile;
  }

  public void setName(String name) {
    this.name = name;
    int ext = name.indexOf(".");
    if( ext == -1 || ext == name.length()-1){
      setExtend(null);
    }else{
      setExtend( name.substring(ext+1) );
    }
  }

  public void setJavaProject(JavaProject javaProject) {
    this.javaProject = javaProject;
  }


  public void setExtend(String extend) {
    this.extend = extend;
  }

  public void setCvsModule(CvsModule cvsModule) {
    this.cvsModule = cvsModule;
  }

  public String getPackageInfo() {
    return packageInfo;
  }
  public String getProjectInfo() {
    return projectInfo;
  }
  public void setProjectInfo(String projectInfo) {
    this.projectInfo = projectInfo;
  }
  public void setPackageInfo(String packageInfo) {
    this.packageInfo = packageInfo;
  }

  public boolean isDead(){
    return this.isDead;
  }
  public CvsRevision getFirstRevision() {
    return firstRevision;
  }
  public CvsRevision getLastRevision() {
    return lastRevision;
  }

}
