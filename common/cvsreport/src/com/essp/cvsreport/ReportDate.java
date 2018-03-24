package com.essp.cvsreport;

import java.text.*;
import java.util.*;
import com.essp.cvsreport.Command.LogCommand;
import com.essp.cvsreport.Command.UpdateCommand;
public class ReportDate {
  JavaRoot root = new JavaRoot();
  Map exceptionMap = new TreeMap(new StringComparator());
  int exceptionNum = 0;

  private  static ReportDate instance = null;
  Calendar beginFetchTime;
  Calendar endFetchTime;

  private ReportDate() {
    root.setName(Constant.ROOT_PACKAGE );
    root.setFullName(Constant.ROOT_PACKAGE);
  }

  public static ReportDate getInstance() {
    return instance;
  }

  public static ReportDate getNewInstance() {
    if( instance == null ){
      instance = new ReportDate();
      instance.fetchDate();
    }

    return instance;
  }

  public String getFetchStr(){
    StringBuffer sb = new StringBuffer();
    sb.append("从");
    sb.append(HtmlHelp.toDateTime(beginFetchTime));
    sb.append("开始<br>到");
    sb.append(HtmlHelp.toDateTime(endFetchTime));
    sb.append("结束<br>.共花时:");
    long t = endFetchTime.getTimeInMillis() - beginFetchTime.getTimeInMillis();
    int h = (int)(t/(3600*1000));
    t = t%(3600*1000);
    int m = (int)(t/(60*1000));
    t = t%(60*1000);
    int s = (int)(t/1000);
    int ms = (int)(t%1000);
    if( h > 0 ){
      sb.append(h + "小时");
    }
    if( m >0 ){
      sb.append(m + "分钟");
    }
    sb.append(s+"."+ms+"秒。<br>");

    sb.append(Constant.ROOT_PACKAGE+"下面的被过滤掉的子目录：");
    for (int i = 0; i < Constant.PROJECT_FILTER.length(); i++) {
      char c = Constant.PROJECT_FILTER.charAt(i);
      if( c == ';' ){
        sb.append("&nbsp;&nbsp;");
      }else{
        sb.append(c);
      }
    }

    return sb.toString();
  }

  public void fetchDate() {
    beginFetchTime = Calendar.getInstance();

    try {
      clear();

      UpdateCommand updateCommand = new UpdateCommand();
      updateCommand.execute();

      LogCommand logCommand = new LogCommand();
      logCommand.execute();
      String logOutputFile  = logCommand.getAbsolutOutputFile();

      LogParser parser = new LogParser(this, logOutputFile);
      parser.parse();
      System.out.println("second pass.");
      this.secondPass();
      System.out.println("second pass over.");

    } catch (Exception ex) {
      System.out.println("Exception.");
      ex.printStackTrace();
    }

    endFetchTime = Calendar.getInstance();
  }

  public void clear(){
    this.exceptionMap.clear();
    exceptionNum = 0;
    root.clear();
  }

  public CvsFile addCvsFile(String rcsFile){
    CvsFile cvsFile = createCvsFile(rcsFile);
    //此方法里面会处理rcsFile
    cvsFile.setRcsFile(rcsFile);

    rcsFile = cvsFile.getRcsFile();
    String cvsroot = "/home/cvsroot/essp/";
    String rcsEnd = ",v";
    if( rcsFile.startsWith(cvsroot) == false
        || rcsFile.endsWith(rcsEnd) == false ){
      throw new RuntimeException("Error rcsFile[" +rcsFile+ "].");
    }

    int fullNameStartAt = cvsroot.length();
    int fullNameEndAt =rcsFile.lastIndexOf(rcsEnd);
    if( fullNameEndAt < fullNameStartAt ){
      throw new RuntimeException("Error rcsFile[" +rcsFile+ "].");
    }



    if( fullNameEndAt <= fullNameStartAt ){
      throw new RuntimeException("Error rcsFile[" +rcsFile+ "].");
    }
    String fullName = rcsFile.substring(fullNameStartAt, fullNameEndAt);
    try {
      cvsFile.setFullName(fullName);
    } catch (CvsException ex) {
      String type = ex.getType();
      List eList = (List)exceptionMap.get(type);
      if( eList == null ){
        eList = new ArrayList();
        exceptionMap.put(type, eList);
      }
      eList.add(ex);
      exceptionNum++;

      System.out.println(exceptionNum+" -- Found an \"" + type + "\" exception: "+ex.getMessage());
    }

    root.addSubFile(cvsFile);
    return cvsFile;
  }

  public void addSymbolic(CvsFile f, String symbolicName){
    String key=null;
    String value=null;
    int i = symbolicName.indexOf(":");
    if( i == -1 ){
      key = symbolicName;
    }else{
      key = symbolicName.substring(0,i);
      value = symbolicName.substring(i+1).trim();
    }
    CvsSymbolic sym = new CvsSymbolic();
    sym.setName(key);
    sym.setRevision(value);
    sym.setCvsFile(f);

    f.addSymbolic(sym);
  }

  public CvsRevision addRevision(CvsFile f, String revisionId){

    CvsRevision rev = new CvsRevision();
    rev.setId(revisionId);
    rev.setCvsFile(f);

    f.addRevisions(rev);
    return rev;
  }

  public void addRevisionDetail(CvsRevision rev, String detail) {
    //date
    int dateStartAt = "date:".length();
    int dateEndAt = detail.indexOf(";", dateStartAt);
    if (dateEndAt > dateStartAt) {
      String dateStr = detail.substring(dateStartAt, dateEndAt).trim();
      SimpleDateFormat format = new SimpleDateFormat(Constant.DATE_PATTEN);
      try {
        Date date = format.parse(dateStr);
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        rev.setDate(c);
      } catch (ParseException ex) {
        ex.printStackTrace();
      }
    }

    int authorStartAt = dateEndAt+1;
    int authorEndAt = detail.indexOf(";", authorStartAt);
    if (authorEndAt > authorStartAt) {
      String author = detail.substring(authorStartAt, authorEndAt).trim();
      rev.setAuthor(author.substring("author:".length()).trim());
    }

    int stateStartAt = authorEndAt+1;
    int stateEndAt = detail.indexOf(";", stateStartAt);
    if (stateEndAt > stateStartAt) {
      String state = detail.substring(stateStartAt, stateEndAt).trim();
      rev.setState(state.substring("state:".length()).trim());
    }

    int linesAt = stateEndAt+1;
    int linesEndAt = detail.indexOf(";", linesAt);
    if( linesEndAt == - 1 ){
      linesEndAt = detail.length();
    }
    if (linesEndAt > linesAt) {
      String lines = detail.substring(linesAt, linesEndAt).trim();

      int addStartAt = lines.indexOf("+");
      int addEndAt = lines.indexOf("-");
      String addStr = lines.substring(addStartAt+1, addEndAt).trim();
      String substractStr = lines.substring(addEndAt+1).trim();
      rev.setLineAdd((new Integer(addStr)).intValue());
      rev.setLineSubstract((new Integer(substractStr)).intValue());
    }
  }

  public void addRevisionMsg(CvsRevision rev, String detail) {
    rev.setMsg(detail);
  }

  public void secondPass(){
    root.secondPass();
  }

  private CvsFile createCvsFile(String rcsFile){
    if( rcsFile.endsWith(".java,v") ){
      return new JavaClass();
    }

    return new CvsFile();
  }
  public JavaRoot getRoot() {
    return root;
  }

}
