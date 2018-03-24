package com.essp.cvsreport.jsp;

import java.util.*;
import java.util.Map.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.essp.cvsreport.*;

public class UserJsp extends BaseJsp {
  String userName;
  JavaUser user;
  public UserJsp(HttpServletRequest request,
                              HttpServletResponse response, JspWriter out, String userName) {
    super(request, response, out);
    this.userName = userName;
    user = root.getUser(userName);
  }

  public void outputGeneral() throws Exception {
    out.print("<h2>" +userName+ "</h2>");
    out.print("<p>");
    out.print("<br>&nbsp;&nbsp;&nbsp;&nbsp;参与项目明细如下：");
  }

  public void outputTableHeader() throws Exception {
    out.print("<tr>");
    out.print("<td valign=\"top\"><b>项目</b></td>");
    out.print("<td valign=\"top\"><b>创建的文件数</b></td>");
    out.print("<td align=\"center\" valign=\"top\"><b>今日新增</b><br>（次）</td>");
    out.print("<td align=\"center\" valign=\"top\"><b>今日修改</b><br>（次）</td>");
    out.print("<td align=\"center\" valign=\"top\"><b>今日delete</b><br>（次）</td>");
    out.print("<td align=\"center\" valign=\"top\"><b>今日remove</b><br>（次）</td>");
    out.print("</tr>");
  }

  public void outputTableTr() throws Exception {
    for (Iterator iter = user.projects.iterator(); iter.hasNext(); ) {
      String projectName = (String)iter.next();

      out.print("<tr>");
      out.print("<td valign=\"top\"><b>" +projectName+ "</b</td>");
      out.print("<td align=\"center\" valign=\"top\"><a href=\"fileListOfUserProject_Admin.jsp?userName=" +userName+ "&projectName=" +projectName+ "&action=" +Constant.ACTION_CREATE+ "\">" +HtmlHelp.getListFromMap( user.projectFile_Create, projectName ).size()+ " </a></td>");
      out.print("<td align=\"center\" valign=\"top\"><a href=\"fileListOfUserProject_Admin.jsp?userName=" +userName+ "&projectName=" +projectName+ "&action=" +Constant.ACTION_ADD+ "\">" +HtmlHelp.getListFromMap( user.projectFile_AddToday, projectName ).size()+ " </a></td>");
      out.print("<td align=\"center\" valign=\"top\"><a href=\"fileListOfUserProject_Admin.jsp?userName=" +userName+ "&projectName=" +projectName+ "&action=" +Constant.ACTION_MODIFY+ "\">" +HtmlHelp.getListFromMap( user.projectFile_ModifyToday, projectName ).size()+ " </a></td>");
      out.print("<td align=\"center\" valign=\"top\"><a href=\"fileListOfUserProject_Admin.jsp?userName=" +userName+ "&projectName=" +projectName+ "&action=" +Constant.ACTION_DELETE+ "\">" +HtmlHelp.getListFromMap( user.projectFile_DeleteToday, projectName ).size()+ " </a></td>");
      out.print("<td align=\"center\" valign=\"top\"><a href=\"fileListOfUserProject_Admin.jsp?userName=" +userName+ "&projectName=" +projectName+ "&action=" +Constant.ACTION_REMOVE+ "\">" +HtmlHelp.getListFromMap( user.projectFile_RemoveToday, projectName ).size()+ " </a></td>");
      out.print("</tr>");

    }
  }

  public void outputTableTotal() throws Exception {
    out.print("<tr>");
    out.print("<td valign=\"top\"><b>total</b></td>");
    out.print("<td align=\"center\" valign=\"top\"><a href=\"fileListOfUserProject_Admin.jsp?userName=" +userName+ "&action=" +Constant.ACTION_CREATE+ "\"> " +user.fileNum_Create+ " </a></td>");
    out.print("<td align=\"center\" valign=\"top\"><a href=\"fileListOfUserProject_Admin.jsp?userName=" +userName+ "&action=" +Constant.ACTION_ADD+ "\"> " +user.fileNum_AddToday+ " </a></td>");
    out.print("<td align=\"center\" valign=\"top\"><a href=\"fileListOfUserProject_Admin.jsp?userName=" +userName+ "&action=" +Constant.ACTION_MODIFY+ "\"> " +user.fileNum_ModifyToday+ " </a></td>");
    out.print("<td align=\"center\" valign=\"top\"><a href=\"fileListOfUserProject_Admin.jsp?userName=" +userName+ "&action=" +Constant.ACTION_DELETE+ "\"> " +user.fileNum_DeleteToday+ " </a></td>");
    out.print("<td align=\"center\" valign=\"top\"><a href=\"fileListOfUserProject_Admin.jsp?userName=" +userName+ "&action=" +Constant.ACTION_REMOVE+ "\"> " +user.fileNum_RemoveToday+ " </a></td>");
    out.print("</tr>");
  }
}
