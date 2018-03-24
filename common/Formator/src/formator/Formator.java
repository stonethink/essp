package formator;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.FieldPosition;
import java.text.DecimalFormat;
import java.text.ParsePosition;

public class Formator {
  public static String format(Date inValue,String pattern) {
    String outValue="";
    if(inValue==null || pattern==null || pattern.trim().equals("")) {
      return "";
    }
    SimpleDateFormat sdf=new SimpleDateFormat(pattern);
    outValue=sdf.format(inValue,new StringBuffer(),new FieldPosition(0)).toString();
    return outValue;
  }

  public static String format(Long inValue,String pattern) {
    String outValue="";
    if(inValue==null || pattern==null || pattern.trim().equals("")) {
      return "";
    }
    DecimalFormat df=new DecimalFormat(pattern);
    outValue=df.format(inValue.longValue(),new StringBuffer(),new FieldPosition(0)).toString();
    return outValue;
  }

  public static String format(long inValue,String pattern) {
    String outValue="";
    if(pattern==null || pattern.trim().equals("")) {
      return "";
    }
    DecimalFormat df=new DecimalFormat(pattern);
    outValue=df.format(inValue,new StringBuffer(),new FieldPosition(0)).toString();
    return outValue;
  }

  public static String format(double inValue,String pattern) {
    String outValue="";
    if(pattern==null || pattern.trim().equals("")) {
      return "";
    }
    DecimalFormat df=new DecimalFormat(pattern);
    outValue=df.format(inValue,new StringBuffer(),new FieldPosition(0)).toString();
    return outValue;
  }

  public static String format(Double inValue,String pattern) {
    String outValue="";
    if(inValue==null || pattern==null || pattern.trim().equals("")) {
      return "";
    }
    DecimalFormat df=new DecimalFormat(pattern);
    outValue=df.format(inValue.doubleValue(),new StringBuffer(),new FieldPosition(0)).toString();
    return outValue;
  }

  public static String formatDate(String inValue,String inPattern,String outPattern) {
    if(inValue==null || inValue.trim().equals("")) {
      return "";
    }
    SimpleDateFormat inSdf=new SimpleDateFormat(inPattern);
    java.util.Date value=inSdf.parse(inValue,new ParsePosition(0));
    String outValue=format(value,outPattern);
    return outValue;
  }

  public static String formatLong(String inValue,String pattern) {
    if(inValue==null || inValue.trim().equals("")) {
      return "";
    }
    long value=Long.parseLong(inValue);
    String outValue=format(value,pattern);
    return outValue;
  }

  public static String formatDouble(String inValue,String pattern) {
    if(inValue==null || inValue.trim().equals("")) {
      return "";
    }
    double value=Double.parseDouble(inValue);
    String outValue=format(value,pattern);
    return outValue;
  }

}
