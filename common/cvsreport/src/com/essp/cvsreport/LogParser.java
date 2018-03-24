package com.essp.cvsreport;

import java.io.File;
import java.io.BufferedReader;
import java.util.Date;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.nio.charset.Charset;

public class LogParser {
  ReportDate reportDate;
  File logFile = null;

  private boolean isSymbolicName = false;
  private boolean isRevision = false;
  private CvsFile cvsFileParsing = null;
  private CvsRevision cvsRevisionParsing = null;

  public LogParser(ReportDate reportDate, String fileName) throws Exception {
      this.reportDate = reportDate;
      logFile = new File(fileName);
      if (logFile.exists() == false || logFile.isDirectory()) {
        throw new Exception("log file[" + logFile + "] is not found.");
      }
    }

  public void parse() throws Exception {
    System.out.println("Begin to parse log file:" + logFile + " at " + new Date());
    BufferedReader reader = null;
    int lineNum = 0;

    try {
      Charset CharsetUTF16 = Charset.forName("UTF-8");
      InputStreamReader input = new InputStreamReader(new FileInputStream(this.
          logFile), CharsetUTF16);
      reader = new BufferedReader(input);

      String line = null;
      while ((line = reader.readLine()) != null) {
        if( lineNum == 552 ){
          int i = 0;
        }
        parseLine(line.trim());
        lineNum++;
        //System.out.println(line);
      }
    } finally {
      System.out.println("end to parse log file:" + logFile + " at " +
                         new Date());
      System.out.println("There are " + lineNum + " lines");

      if (reader != null) {
        reader.close();
      }
    }
  }

  private void parseLine(String line) {
    if (line.equals("") == true) {
      return;
    }

    String rcsFileTag = "RCS file:";
    if (line.startsWith(rcsFileTag)) {
      if (rcsFileTag.length() < line.length()) {
        String rcsFile = line.substring(rcsFileTag.length()).trim();
        if (rcsFile.equals("") == false) {
          cvsFileParsing = reportDate.addCvsFile(rcsFile);
        }
      }
      return;
    }

    String symbolicNameTag1 = "symbolic names:";
    String symbolicNameTag2 = "\t";
    if (line.startsWith(symbolicNameTag1)) {

      this.isSymbolicName = true;
      return;
    }
    if (isSymbolicName == true
        && line.startsWith(symbolicNameTag2)) {

      String symbolicName = line.substring(symbolicNameTag2.length()).trim();
      if (symbolicName.equals("") == false) {
        reportDate.addSymbolic(cvsFileParsing, symbolicName);
      }

      return;
    } else {
      this.isSymbolicName = false;
    }

    String revisionTag = "revision";
    if (line.startsWith(revisionTag)) {

      String revisionId = line.substring(revisionTag.length()).trim();
      if (revisionId.equals("") == false) {
        cvsRevisionParsing = reportDate.addRevision(cvsFileParsing, revisionId);
      }

      this.isRevision = true;
      return;
    }
    if (isRevision == true) {
      String revisionTag2 = "date:";
      if (line.startsWith(revisionTag2)) {

        reportDate.addRevisionDetail(cvsRevisionParsing, line);

        return;
      }

      //判断revision信息是否结束
      if( line.startsWith("=====") ){
        this.isRevision = false;
      }else {
        reportDate.addRevisionMsg(cvsRevisionParsing, line);
        return;
      }
    }

  }


  public static void main(String args[]) {
    try {
      ReportDate r = ReportDate.getInstance();
      LogParser parser = new LogParser(r, Constant.WORK_DIR + "\\" + Constant.LOG_OUTPUT_FILE);
      parser.parse();
      r.getRoot().dump();
      System.out.println("Over.");
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

}


/*
 ----the log file's content like below:


 RCS file: /home/cvsroot/essp/essp2/essp/src/client/essp/common/view/VWWorkArea.java,v
 Working file: essp/src/client/essp/common/view/VWWorkArea.java
 head: 1.19
 branch:
 locks: strict
 access list:
 symbolic names:
        V2_0_0: 1.19
        V2_20060218: 1.19
        V2_20060109: 1.19
        V1_6_0: 1.19
        V1_5_3: 1.19
        V1_5_2: 1.19
        V1_5_Branch: 1.19.0.2
        V1_5_1: 1.19
        V1_5_0: 1.19
        V1_4_0: 1.19
        V1_2_0(B20051008): 1.12
        arelease: 1.1.1.1
        avendor: 1.1.1
 keyword substitution: kv
 total revisions: 20;	selected revisions: 20
 description:
 ----------------------------
 revision 1.19
 date: 2005/10/19 01:24:46;  author: yery;  state: Exp;  lines: +6 -0
 no message
 ----------------------------
 revision 1.16
 date: 2005/10/12 10:27:00;  author: yery;  state: Exp;  lines: +37 -0
 *** empty log message ***
 ----------------------------
 revision 1.15
 date: 2005/10/12 08:10:02;  author: huaxiao;  state: Exp;  lines: +4 -0
 no message
 ----------------------------
 revision 1.1
 date: 2005/08/15 06:10:54;  author: huaxiao;  state: Exp;
 branches:  1.1.1;
 Initial revision
 ----------------------------
 revision 1.1.1.1
 date: 2005/08/15 06:10:54;  author: huaxiao;  state: Exp;  lines: +0 -0
 no message
 =============================================================================
*/

