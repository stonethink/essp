package com.essp.cvsreport;
public class CvsSymbolic {
  String name;
  CvsFile cvsFile;
  private String revision;

  public CvsSymbolic() {
  }
  public String getName() {
    return name;
  }
  public String getRevision() {
    return revision;
  }
  public void setName(String name) {
    this.name = name;
  }
  public void setRevision(String revision) {
    this.revision = revision;
  }
  public CvsFile getCvsFile() {
    return cvsFile;
  }
  public void setCvsFile(CvsFile cvsFile) {
    this.cvsFile = cvsFile;
  }
}
