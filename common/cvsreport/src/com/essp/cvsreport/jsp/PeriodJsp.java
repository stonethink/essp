package com.essp.cvsreport.jsp;

import java.util.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.essp.cvsreport.*;
import java.text.SimpleDateFormat;

public class PeriodJsp extends BaseJsp {
  JavaPeriod cal;
  public PeriodJsp(HttpServletRequest request,
                   HttpServletResponse response, JspWriter out) {
    super(request, response, out);

    String last = (String) request.getParameter("last");
    String next = (String) request.getParameter("next");
    if ("true".equals(last)) {
      cal = (JavaPeriod) request.getSession().getAttribute("JavaPeriod");
      cal.last();
    } else if ("true".equals(next)) {
      cal = (JavaPeriod) request.getSession().getAttribute("JavaPeriod");
      cal.next();
    } else {
      Calendar begin = null;
      Calendar end = null;

      String datePattern = (String) request.getParameter("datePattern");
      if (datePattern != null) {

        begin = Calendar.getInstance();
        begin.set(Calendar.HOUR_OF_DAY, 0);
        begin.set(Calendar.MINUTE, 0);
        begin.set(Calendar.SECOND, 0);
        begin.set(Calendar.MILLISECOND, 0);
        end = Calendar.getInstance();
        end.set(Calendar.HOUR_OF_DAY, 23);
        end.set(Calendar.MINUTE, 59);
        end.set(Calendar.SECOND, 59);
        end.set(Calendar.MILLISECOND, 999);
        if ("today".equals(datePattern)) {

        } else if ("today-1".equals(datePattern)) {
          begin.set(Calendar.DAY_OF_MONTH, begin.get(Calendar.DAY_OF_MONTH) - 1);
        } else if ("today-2".equals(datePattern)) {
          begin.set(Calendar.DAY_OF_MONTH, begin.get(Calendar.DAY_OF_MONTH) - 1);
        } else if ("today-3".equals(datePattern)) {
          begin.set(Calendar.DAY_OF_MONTH, begin.get(Calendar.DAY_OF_MONTH) - 3);
        } else if ("today-7".equals(datePattern)) {
          begin.set(Calendar.DAY_OF_MONTH, begin.get(Calendar.DAY_OF_MONTH) - 7);
        }
      } else {
        String bStr = (String) request.getParameter("begin");
        String eStr = (String) request.getParameter("end");

        if (bStr != null && bStr.equals("") == false) {
          begin = parseCal(bStr);
        }
        if (eStr != null && eStr.equals("") == false) {
          end = parseCal(eStr);
        }
      }

      ReportDate reportDate = ReportDate.getInstance();
      cal = new JavaPeriod(reportDate.getRoot(), begin, end);
      request.getSession().setAttribute("JavaPeriod", cal);
    }
  }

  private Calendar parseCal(String str){
    //20060102 030405
    StringBuffer sb = new StringBuffer(14);
    int i = 0;
    for (; i < str.length(); i++) {
      char c = str.charAt(i);
      if( c >= '0' && c <= '9' ){
        sb.append(c);
      }
    }
    for( ; i < 14; i++ ){
      sb.append('0');
    }

    int year = Integer.parseInt( sb.substring(0,4) );
    int month = Integer.parseInt(sb.substring(4,6));
    month = month>0?month-1:month;
    int day = Integer.parseInt(sb.substring(6,8));
    int hour = Integer.parseInt(sb.substring(8,10));
    int minute = Integer.parseInt(sb.substring(10,12));
    int second = Integer.parseInt(sb.substring(12,14));

    Calendar c = Calendar.getInstance();
    c.set(year,month,day,hour,minute,second);
    return c;
  }

  public void outputCondition() throws Exception {
    SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd HHmmss");
    String bs = "";
    if( cal.getBegin() != null ){
      bs = format.format(cal.getBegin().getTime());
    }
    String es = "";
    if( cal.getEnd() != null ){
      es = format.format(cal.getEnd().getTime());
    }

    out.print("&nbsp;&nbsp;<a href='period.jsp?datePattern=today'>今天的</a>");
    out.print("&nbsp;&nbsp;<a href='period.jsp?datePattern=today-1'>这两天的</a>");
    out.print("&nbsp;&nbsp;<a href='period.jsp?datePattern=today-2'>这三天的</a>");
    out.print("&nbsp;&nbsp;<a href='period.jsp?datePattern=today-7'>这周的</a><p>");
    out.print("<form action='period.jsp'>&nbsp;&nbsp;自定义区间&nbsp;&nbsp;<input type=text length=25 name=begin value='" +bs+ "'/>&nbsp;&nbsp;——&nbsp;&nbsp;<input type=text length=25 name=end value='" +es+ "'/>&nbsp;&nbsp;<input type=submit value='Go'/>（忽略非数字字符，不足补零）</form>");
  }

  public void outputPeriod() throws Exception {
    out.print("<p>");
    out.print("<hr>");
    out.print("<h3>时间段&nbsp;[&nbsp;" + HtmlHelp.toDateTime(cal.getBegin()) + " ——〉 " +
              HtmlHelp.toDateTime(cal.getEnd())+ "&nbsp;]&nbsp;内的变更</h3>");
  }

  public void outputProjectTable() throws Exception {
    out.print("<b>按项目：</b>");
    out.print("<table border='1' cellpadding='5' cellspacing='0'>");
    out.print("<tr>");
    out.print("<td valign=\"top\"><b>项目</b></td>");
    out.print("<td align=\"center\" valign=\"top\"><b>新增</b><br>（次）</td>");
    out.print("<td align=\"center\" valign=\"top\"><b>修改</b><br>（次）</td>");
    out.print("<td align=\"center\" valign=\"top\"><b>delete</b><br>（次）</td>");
    out.print("<td align=\"center\" valign=\"top\"><b>remove</b><br>（次）</td>");
    out.print("</tr>");

    for (Iterator iter = cal.projects.iterator(); iter.hasNext(); ) {
      String projectName = (String)iter.next();

      out.print("<tr>");
      out.print("<td valign=\"top\"><b>" +projectName+ "</b</td>");
      out.print("<td align=\"center\" valign=\"top\"><a href=\"fileListOfPeriodProject_Admin.jsp?projectName=" +projectName+ "&action=" +Constant.ACTION_ADD+ "\">" +HtmlHelp.getListFromMap( cal.projectFile_Add, projectName ).size()+ " </a></td>");
      out.print("<td align=\"center\" valign=\"top\"><a href=\"fileListOfPeriodProject_Admin.jsp?projectName=" +projectName+ "&action=" +Constant.ACTION_MODIFY+ "\">" +HtmlHelp.getListFromMap( cal.projectFile_Modify, projectName ).size()+ " </a></td>");
      out.print("<td align=\"center\" valign=\"top\"><a href=\"fileListOfPeriodProject_Admin.jsp?projectName=" +projectName+ "&action=" +Constant.ACTION_DELETE+ "\">" +HtmlHelp.getListFromMap( cal.projectFile_Delete, projectName ).size()+ " </a></td>");
      out.print("<td align=\"center\" valign=\"top\"><a href=\"fileListOfPeriodProject_Admin.jsp?projectName=" +projectName+ "&action=" +Constant.ACTION_REMOVE+ "\">" +HtmlHelp.getListFromMap( cal.projectFile_Remove, projectName ).size()+ " </a></td>");
      out.print("</tr>");
    }

    out.print("<tr>");
    out.print("<td valign=\"top\"><b>total</b></td>");
    out.print("<td align=\"center\" valign=\"top\"><a href=\"fileListOfPeriodProject_Admin.jsp?action=" +Constant.ACTION_ADD+ "\">" +cal.fileNum_Add+ " </a></td>");
    out.print("<td align=\"center\" valign=\"top\"><a href=\"fileListOfPeriodProject_Admin.jsp?action=" +Constant.ACTION_MODIFY+ "\">" +cal.fileNum_Modify+ " </a></td>");
    out.print("<td align=\"center\" valign=\"top\"><a href=\"fileListOfPeriodProject_Admin.jsp?action=" +Constant.ACTION_DELETE+ "\">" +cal.fileNum_Delete+ " </a></td>");
    out.print("<td align=\"center\" valign=\"top\"><a href=\"fileListOfPeriodProject_Admin.jsp?action=" +Constant.ACTION_REMOVE+ "\">" +cal.fileNum_Remove+ " </a></td>");
    out.print("</tr>");
    out.print("</table>");
  }


  public void outputUserTable() throws Exception {
    out.print("<p>");
    out.print("<b>按User：</b>");
    out.print("<table border='1' cellpadding='5' cellspacing='0'>");
    out.print("<tr>");
    out.print("<td valign=\"top\"><b>User</b></td>");
    out.print("<td align=\"center\" valign=\"top\"><b>新增</b><br>（次）</td>");
    out.print("<td align=\"center\" valign=\"top\"><b>修改</b><br>（次）</td>");
    out.print("<td align=\"center\" valign=\"top\"><b>delete</b><br>（次）</td>");
    out.print("<td align=\"center\" valign=\"top\"><b>remove</b><br>（次）</td>");
    out.print("</tr>");

    for (Iterator iter = cal.users.iterator(); iter.hasNext(); ) {
      String userName = (String)iter.next();

      out.print("<tr>");
      out.print("<td valign=\"top\"><b>" +userName+ "</b</td>");
      out.print("<td align=\"center\" valign=\"top\"><a href=\"fileListOfPeriodUser_Admin.jsp?userName=" +userName+ "&action=" +Constant.ACTION_ADD+ "\">" +HtmlHelp.getListFromMap( cal.userFile_Add, userName ).size()+ " </a></td>");
      out.print("<td align=\"center\" valign=\"top\"><a href=\"fileListOfPeriodUser_Admin.jsp?userName=" +userName+ "&action=" +Constant.ACTION_MODIFY+ "\">" +HtmlHelp.getListFromMap( cal.userFile_Modify, userName ).size()+ " </a></td>");
      out.print("<td align=\"center\" valign=\"top\"><a href=\"fileListOfPeriodUser_Admin.jsp?userName=" +userName+ "&action=" +Constant.ACTION_DELETE+ "\">" +HtmlHelp.getListFromMap( cal.userFile_Delete, userName ).size()+ " </a></td>");
      out.print("<td align=\"center\" valign=\"top\"><a href=\"fileListOfPeriodUser_Admin.jsp?userName=" +userName+ "&action=" +Constant.ACTION_REMOVE+ "\">" +HtmlHelp.getListFromMap( cal.userFile_Remove, userName ).size()+ " </a></td>");
      out.print("</tr>");
    }

    out.print("<tr>");
    out.print("<td valign=\"top\"><b>total</b></td>");
    out.print("<td align=\"center\" valign=\"top\"><a href=\"fileListOfPeriodUser_Admin.jsp?action=" +Constant.ACTION_ADD+ "\">" +cal.fileNum_Add+ " </a></td>");
    out.print("<td align=\"center\" valign=\"top\"><a href=\"fileListOfPeriodUser_Admin.jsp?action=" +Constant.ACTION_MODIFY+ "\">" +cal.fileNum_Modify+ " </a></td>");
    out.print("<td align=\"center\" valign=\"top\"><a href=\"fileListOfPeriodUser_Admin.jsp?action=" +Constant.ACTION_DELETE+ "\">" +cal.fileNum_Delete+ " </a></td>");
    out.print("<td align=\"center\" valign=\"top\"><a href=\"fileListOfPeriodUser_Admin.jsp?action=" +Constant.ACTION_REMOVE+ "\">" +cal.fileNum_Remove+ " </a></td>");
    out.print("</tr>");
    out.print("</table>");
  }

}
