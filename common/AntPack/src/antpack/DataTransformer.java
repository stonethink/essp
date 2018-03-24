package antpack;

import org.apache.tools.ant.Task;
public class DataTransformer extends Task {
  private String DbUrl="";
  private String DbUserName="";
  private String DbPassword="";
  private DataFormat dataFormat=null;

  public DataTransformer() {
  }

  public void execute() {
    System.out.println("DbUrl=" + DbUrl + ",DbUserName=" + DbUserName +
                       ",DbPassword=" + DbPassword);
    System.out.println("dataFormat:" + dataFormat.type + "," +
                         dataFormat.value);
  }
  public String getDbPassword() {
    return DbPassword;
  }
  public void setDbPassword(String DbPassword) {
    this.DbPassword = DbPassword;
  }
  public String getDbUrl() {
    return DbUrl;
  }
  public void setDbUrl(String DbUrl) {
    this.DbUrl = DbUrl;
  }
  public String getDbUserName() {
    return DbUserName;
  }
  public void setDbUserName(String DbUserName) {
    this.DbUserName = DbUserName;
  }

  public DataFormat createDataFormat() {
    System.out.println("call createDataFormat");
    return new DataFormat();
  }

  public void addConfiguredDataFormat(DataFormat p) {
    if(p==null) {
      System.out.println("call addConfiguredPerson: null");
    } else {
      System.out.println("call addConfiguredPerson: not null");
    }
    this.dataFormat = p;
  }
}

