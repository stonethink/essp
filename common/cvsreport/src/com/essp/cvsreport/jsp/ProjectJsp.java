package com.essp.cvsreport.jsp;

import java.util.*;
import java.util.Map.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.essp.cvsreport.*;

public class ProjectJsp extends BaseJsp {
  String projectName;
  JavaProject project;
  public ProjectJsp(HttpServletRequest request,
                              HttpServletResponse response, JspWriter out, String projectName) {
    super(request, response, out);
    this.projectName = projectName;
    project = root.getProject(projectName);
  }

  public void outputGeneral() throws Exception {
    out.print("<h2>" +projectName+ "</h2>");
    out.print("<p>");
    out.print("项目中共有文件<a href=\"fileListOfProject_Admin.jsp?projectName=" +projectName+ "&action=" +Constant.ACTION_CREATE+ "\">" +project.getAllFileNum()+ "个</a>"
              +"（不含被删除的，被删除了的文件共有<a href=\"specialFileListOfProject.jsp?projectName=" +projectName+ "&fileType=" +SpecialFileListOfProject.FILE_DELETE+ "\">" +project.getAllDeletedFileNum()+ "个</a>）"
              +"，其中Java文件<a href=\"specialFileListOfProject.jsp?projectName=" +projectName+ "&fileType=" +SpecialFileListOfProject.FILE_CLASS+ "\">" +project.getAllClassNum()+ "个</a>。");
    out.print("<br>&nbsp;&nbsp;&nbsp;&nbsp;今天新增文件" +project.file_AddToday.size()
              + "个，修改文件" +project.file_ModifyToday.size()
              + "个，delete文件" +project.file_DeleteToday.size()
              + "个，remove文件" +project.file_RemoveToday.size()+ "个。");
    out.print("<br>&nbsp;&nbsp;&nbsp;&nbsp;项目中user明细如下：");
  }

  public void outputTableHeader() throws Exception {
    out.print("<tr>");
    out.print("<td valign=\"top\"><b>user</b></td>");
    out.print("<td valign=\"top\"><b>创建的文件数</b></td>");
    out.print("<td align=\"center\" valign=\"top\"><b>今日新增</b><br>（次）</td>");
    out.print("<td align=\"center\" valign=\"top\"><b>今日修改</b><br>（次）</td>");
    out.print("<td align=\"center\" valign=\"top\"><b>今日delete</b><br>（次）</td>");
    out.print("<td align=\"center\" valign=\"top\"><b>今日remove</b><br>（次）</td>");
    out.print("</tr>");
  }

  public void outputTableTr() throws Exception {
    for (Iterator iter = project.users.iterator(); iter.hasNext(); ) {
      String userName = (String)iter.next();

      out.print("<tr>");
      out.print("<td valign=\"top\"><b>" +userName+ "</b</td>");
      out.print("<td align=\"center\" valign=\"top\"><a href=\"fileListOfProjectUser_Admin.jsp?projectName=" +projectName+ "&userName=" +userName+ "&action=" +Constant.ACTION_CREATE+ "\">" +HtmlHelp.getListFromMap( project.userFile_Create, userName ).size()+ " </a></td>");
      out.print("<td align=\"center\" valign=\"top\"><a href=\"fileListOfProjectUser_Admin.jsp?projectName=" +projectName+ "&userName=" +userName+ "&action=" +Constant.ACTION_ADD+ "\">" +HtmlHelp.getListFromMap( project.userFile_AddToday, userName ).size()+ " </a></td>");
      out.print("<td align=\"center\" valign=\"top\"><a href=\"fileListOfProjectUser_Admin.jsp?projectName=" +projectName+ "&userName=" +userName+ "&action=" +Constant.ACTION_MODIFY+ "\">" +HtmlHelp.getListFromMap( project.userFile_ModifyToday, userName ).size()+ " </a></td>");
      out.print("<td align=\"center\" valign=\"top\"><a href=\"fileListOfProjectUser_Admin.jsp?projectName=" +projectName+ "&userName=" +userName+ "&action=" +Constant.ACTION_DELETE+ "\">" +HtmlHelp.getListFromMap( project.userFile_DeleteToday, userName ).size()+ " </a></td>");
      out.print("<td align=\"center\" valign=\"top\"><a href=\"fileListOfProjectUser_Admin.jsp?projectName=" +projectName+ "&userName=" +userName+ "&action=" +Constant.ACTION_REMOVE+ "\">" +HtmlHelp.getListFromMap( project.userFile_RemoveToday, userName ).size()+ " </a></td>");
      out.print("</tr>");

    }
  }

  public void outputTableTotal() throws Exception {
    out.print("<tr>");
    out.print("<td valign=\"top\"><b>total</b></td>");
    out.print("<td align=\"center\" valign=\"top\"><a href=\"fileListOfProjectUser_Admin.jsp?projectName=" +projectName+ "&action=" +Constant.ACTION_CREATE+ "\"> " +project.getAllFileNum()+ " </a></td>");
    out.print("<td align=\"center\" valign=\"top\"><a href=\"fileListOfProjectUser_Admin.jsp?projectName=" +projectName+ "&action=" +Constant.ACTION_ADD+ "\"> " +project.file_AddToday.size()+ " </a></td>");
    out.print("<td align=\"center\" valign=\"top\"><a href=\"fileListOfProjectUser_Admin.jsp?projectName=" +projectName+ "&action=" +Constant.ACTION_MODIFY+ "\"> " +project.file_ModifyToday.size()+ " </a></td>");
    out.print("<td align=\"center\" valign=\"top\"><a href=\"fileListOfProjectUser_Admin.jsp?projectName=" +projectName+ "&action=" +Constant.ACTION_DELETE+ "\"> " +project.file_DeleteToday.size()+ " </a></td>");
    out.print("<td align=\"center\" valign=\"top\"><a href=\"fileListOfProjectUser_Admin.jsp?projectName=" +projectName+ "&action=" +Constant.ACTION_REMOVE+ "\"> " +project.file_RemoveToday.size()+ " </a></td>");
    out.print("</tr>");
  }
}
