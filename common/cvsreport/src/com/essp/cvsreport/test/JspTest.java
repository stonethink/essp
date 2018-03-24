

package com.essp.cvsreport.test;

import java.util.*;
import com.essp.cvsreport.*;
import java.util.Calendar;
import java.io.PrintStream;

public class JspTest {
  ReportDate reportDate;
  public JspTest() {

    reportDate = ReportDate.getInstance();
    try {
      reportDate.fetchDate();
    } catch (Exception ex) {
      ex.printStackTrace();
    }

  }


  PrintStream out = System.out;
  public static void main(String[] args) {
    JspTest t = new JspTest();
//    t.b();
//    t.c();
//    t.d();
//    t.e();
  }
}
