package antpack;

public class DataFormat {
  String type="";
  String value="";
  public void setType(String sType) {
    System.out.println("set type:"+sType);
    this.type=sType;
  }
  public String getType() {
    return this.type;
  }
  public void setValue(String sValue) {
    this.value=sValue;
    System.out.println("set value:"+sValue);
  }
  public String getValue() {
    return this.value;
  }
}
