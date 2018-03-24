package com.essp.cvsreport.jsp;

import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.essp.cvsreport.*;

public class BaseJsp {
  HttpServletRequest request;
  HttpServletResponse response;
  JspWriter out;

  JavaRoot root;
  ReportDate reportDate;

  public BaseJsp(HttpServletRequest request, HttpServletResponse response, JspWriter out) {
    this.request = request;
    this.response = response;
    this.out = out;

    //reportDate = (ReportDate) request.getSession().getAttribute("reportDate");
    reportDate = ReportDate.getInstance();
    root = reportDate.getRoot();
  }



}
