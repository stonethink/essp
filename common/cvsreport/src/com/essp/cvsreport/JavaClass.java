package com.essp.cvsreport;



public class JavaClass extends CvsFile{
  String className;
  String fullClassName;

  public JavaClass() {
  }

  public boolean isClassFile(){
    return true;
  }

  public String getClassName() {
    return className;
  }
  public String getFullClassName() {
    return fullClassName;
  }
  public JavaPackage getJavaPackage() {
    return (JavaPackage)getCvsModule();
  }
  public void setFullClassName(String fullClassName) {
    this.fullClassName = fullClassName;
  }
  public void setClassName(String className) {
    this.className = className;
  }

  public void setFullName(String fullName){
    super.setFullName(fullName);

    if( this.getPackageInfo() == null || getPackageInfo().startsWith("src") == false ){
      throw new CvsException(CvsException.PACKAGE_INVALID,"Error class file[" +fullName+ "].It's not in the 'src' directory.");
    }

    String packageClass="";
    if( getPackageInfo().length() > 4 ){
      packageClass = getPackageInfo().substring(4)+"/";
    }
    packageClass += getClassName();
    setFullClassName(packageClass.replaceAll("/","."));
  }

  public void setName(String name){
    super.setName(name);
    int end = name.lastIndexOf(".java");
    if( end == -1 ){
      throw new RuntimeException("Error class file name["+name+"].It's not end with '.java'");
    }

    if( end == 0 ){
      throw new RuntimeException("Error class file name["+name+"].It's has no class name.");
    }

    setClassName(name.substring(0, end));
  }
}
