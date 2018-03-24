package com.essp.cvsreport;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class Index extends HttpServlet {
  ReportDate reportDate = null;

  public Index() {
  }

  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws
      ServletException, IOException {
    run(req, resp);
  }

  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws
      ServletException, IOException {
    run(req, resp);
  }

  private void run(HttpServletRequest req, HttpServletResponse resp) throws
      ServletException, IOException {
    reportDate = reportDate.getNewInstance();

    resp.sendRedirect("index.jsp");
  }

}
