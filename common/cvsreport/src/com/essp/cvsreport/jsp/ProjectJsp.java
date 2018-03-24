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
    out.print("��Ŀ�й����ļ�<a href=\"fileListOfProject_Admin.jsp?projectName=" +projectName+ "&action=" +Constant.ACTION_CREATE+ "\">" +project.getAllFileNum()+ "��</a>"
              +"��������ɾ���ģ���ɾ���˵��ļ�����<a href=\"specialFileListOfProject.jsp?projectName=" +projectName+ "&fileType=" +SpecialFileListOfProject.FILE_DELETE+ "\">" +project.getAllDeletedFileNum()+ "��</a>��"
              +"������Java�ļ�<a href=\"specialFileListOfProject.jsp?projectName=" +projectName+ "&fileType=" +SpecialFileListOfProject.FILE_CLASS+ "\">" +project.getAllClassNum()+ "��</a>��");
    out.print("<br>&nbsp;&nbsp;&nbsp;&nbsp;���������ļ�" +project.file_AddToday.size()
              + "�����޸��ļ�" +project.file_ModifyToday.size()
              + "����delete�ļ�" +project.file_DeleteToday.size()
              + "����remove�ļ�" +project.file_RemoveToday.size()+ "����");
    out.print("<br>&nbsp;&nbsp;&nbsp;&nbsp;��Ŀ��user��ϸ���£�");
  }

  public void outputTableHeader() throws Exception {
    out.print("<tr>");
    out.print("<td valign=\"top\"><b>user</b></td>");
    out.print("<td valign=\"top\"><b>�������ļ���</b></td>");
    out.print("<td align=\"center\" valign=\"top\"><b>��������</b><br>���Σ�</td>");
    out.print("<td align=\"center\" valign=\"top\"><b>�����޸�</b><br>���Σ�</td>");
    out.print("<td align=\"center\" valign=\"top\"><b>����delete</b><br>���Σ�</td>");
    out.print("<td align=\"center\" valign=\"top\"><b>����remove</b><br>���Σ�</td>");
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
