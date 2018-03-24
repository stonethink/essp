package com.essp.cvsreport;

import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.jsp.JspWriter;

public class HtmlHelp {
  public HtmlHelp() {
  }

  public static String nSpace(int n){
    String str = "";
    for (int i = 0; i < n; i++) {
      str += "&nbsp;";
    }
    return str;
  }

  public static String nSpace(String base, int n){
    for (int i = base.length(); i < n; i++) {
      base += "&nbsp;";
    }
    return base;
  }

  final static SimpleDateFormat formatDate = new SimpleDateFormat(Constant.
      DATE_PATTEN.substring(0, Constant.DATE_PATTEN.indexOf(' ')).trim());
  final static SimpleDateFormat formatTime = new SimpleDateFormat(Constant.
      DATE_PATTEN.substring(Constant.DATE_PATTEN.indexOf(' ')));
  final static SimpleDateFormat formatDateTime = new SimpleDateFormat(Constant.
      DATE_PATTEN);
  public static String toDate(Calendar c) {
    if( c != null ){
      return formatDate.format(c.getTime());
    }else{
      return "";
    }
  }

  public static String toTime(Calendar c) {
    if( c != null ){
      return formatTime.format(c.getTime());
    }else{
      return "";
    }
  }

  public static String toDateTime(Calendar c) {
    if( c != null ){
      return formatDateTime.format(c.getTime());
    }else{
      return "";
    }
  }

  public static int getNumFromMap(Map map , Object key){
      Integer num = (Integer)map.get(key);
      if( num == null ){
          return 0;
      }else{
          return num.intValue();
      }
    }

    public static List getListFromMap(Map map, Object key) {
      List list = (List)map.get(key);
      if( list == null ){
        list = new ArrayList();
      }
      return list;
    }

  public static boolean isInToday(Calendar d){
    Calendar today = Calendar.getInstance();
    today.set(Calendar.HOUR_OF_DAY, 0);
    today.set(Calendar.MINUTE, 0);
    today.set(Calendar.SECOND, 0);
    today.set(Calendar.MILLISECOND, 0);
    Calendar date = (Calendar)d.clone();
    date.set(Calendar.HOUR_OF_DAY, 0);
    date.set(Calendar.MINUTE, 0);
    date.set(Calendar.SECOND, 0);
    date.set(Calendar.MILLISECOND, 0);

    long minus = date.getTimeInMillis() - today.getTimeInMillis();
    if( minus >= 0 && minus < 24*3600*1000 ){
      return true;
    }else{
      return false;
    }
  }

  public static boolean isInPeriod(Calendar b, Calendar e, Calendar d){
    if( (b==null || d.after(b))
        && (e==null || d.before(e)) ){
      return true;
    }else{
      return false;
    }
  }

  public static String getFileLink(CvsFile f ){
      return ("<a href=\"file.jsp?fileFullName="+f.getFullName()+"\">"
                + f.getFullName() + "</a>");
  }

  public static String outputFile(String fillFullName ){
      return ("<a href=\"file.jsp?fileFullName="+fillFullName+"\">"
                + fillFullName + "</a>");
  }


  public static String getClassLink(JavaClass f ){
      return ("<a href=\"file.jsp?fileFullName="+f.getFullName()+"\">"
                + f.getFullClassName() + "</a>");
  }
}
