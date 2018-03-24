package com.essp.cvsreport;

import java.util.TreeMap;
import java.util.Map;

public class JavaPackage extends CvsModule{
  String fullPackageName;
  JavaPackage superPackage;

  //本package下的直接java文件(不含子package的)
  Map subClasses = new TreeMap(new StringComparator());

  public JavaPackage() {
  }

  public void addSubFile(CvsFile f) {
    super.addSubFile(f);

    if (f.isClassFile()) {
      if (getSubFile(f.getFullName()) != null) {
        JavaClass javaClass = (JavaClass) f;
        subClasses.put(javaClass.getFullClassName(), javaClass);
      }
    }
  }

  public JavaPackage getSuperPackage(){
    return superPackage;
  }

  public void setSuperPackage(JavaPackage superPackage){
    this.superPackage = superPackage;
  }


  public boolean isJavaPackage(){
    return true;
  }
  public String getFullPackageName() {
    return fullPackageName;
  }
  public void setFullPackageName(String fullPackageName) {
    this.fullPackageName = fullPackageName;
  }

}
