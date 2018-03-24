package com.essp.cvsreport.jsp;

import java.util.*;
import java.util.Map.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.essp.cvsreport.*;

public class ContentJsp  extends BaseJsp {
  public ContentJsp(HttpServletRequest request,
                              HttpServletResponse response, JspWriter out) {
    super(request, response, out);
  }

  public void outputGeneral() throws Exception {
    out.print("cvs馆中共有文件<a href=\"fileListOfProject_Admin.jsp?action=" +Constant.ACTION_CREATE+ "\">" +root.getAllFileNum()+ "个</a>"
              +"（不含被删除的，被删除了的文件共有<a href=\"specialFileListOfProject.jsp?fileType=" +SpecialFileListOfProject.FILE_DELETE+ "\">" +root.getAllDeletedFileNum()+ "个</a>）"
              +"，其中Java文件<a href=\"specialFileListOfProject.jsp?fileType=" +SpecialFileListOfProject.FILE_CLASS+ "\">" +root.getAllClassNum()+ "个</a>。");

    out.print("<br>&nbsp;&nbsp;&nbsp;&nbsp;今天新增文件" +root.fileNum_AddToday
              + "个，修改文件" +root.fileNum_ModifyToday
              + "个，delete文件" +root.fileNum_DeleteToday
              + "个，remove文件" +root.fileNum_RemoveToday+ "个。	");
    out.print("<br>&nbsp;&nbsp;&nbsp;&nbsp;各项目明细如下：");
  }

  public void outputTableHeader() throws Exception {
    out.print("<tr>");
    out.print("<td valign=\"top\"><b>项目</b></td>");
    out.print("<td valign=\"top\"><b>文件数</b></td>");
    out.print("<td align=\"center\" valign=\"top\"><b>已delete的<br>文件数</b></td>");
    out.print("<td align=\"center\" valign=\"top\"><b>类文件</b></td>");
    out.print("<td align=\"center\" valign=\"top\"><b>今日新增</b><br>（次）</td>");
    out.print("<td align=\"center\" valign=\"top\"><b>今日修改</b><br>（次）</td>");
    out.print("<td align=\"center\" valign=\"top\"><b>今日delete</b><br>（次）</td>");
    out.print("<td align=\"center\" valign=\"top\"><b>今日remove</b><br>（次）</td>");
    out.print("</tr>");
  }

  public void outputTableTr() throws Exception {
    for (Iterator iter = root.projects.entrySet().iterator(); iter.hasNext(); ) {
      Entry item = (Entry)iter.next();
      String projectName = (String)item.getKey();
      JavaProject project = (JavaProject)item.getValue();

        out.print("<tr>");
        out.print("<td valign=\"top\"><a href=\"project.jsp?projectName=" +projectName+ "\"><b>" +projectName+ "</b></a></td>");
        out.print("<td align=\"center\" valign=\"top\"><a href=\"fileListOfProject_Admin.jsp?projectName=" +projectName+ "&action=" +Constant.ACTION_CREATE+ "\"> " +project.getAllFileNum()+ " </a></td>");
        out.print("<td align=\"center\" valign=\"top\"><a href=\"specialFileListOfProject.jsp?projectName=" +projectName+ "&fileType=" +SpecialFileListOfProject.FILE_DELETE+ "\"> " +project.getAllDeletedFileNum()+ " </a></td>");
        out.print("<td align=\"center\" valign=\"top\"><a href=\"specialFileListOfProject.jsp?projectName=" +projectName+ "&fileType=" +SpecialFileListOfProject.FILE_CLASS+ "\"> " +project.getAllClassNum()+ " </a></td>");
        out.print("<td align=\"center\" valign=\"top\"><a href=\"fileListOfProject_Admin.jsp?projectName=" +projectName+ "&action=" +Constant.ACTION_ADD+ "\"> " +project.file_AddToday.size()+ " </a></td>");
        out.print("<td align=\"center\" valign=\"top\"><a href=\"fileListOfProject_Admin.jsp?projectName=" +projectName+ "&action=" +Constant.ACTION_MODIFY+ "\"> " +project.file_ModifyToday.size()+ " </a></td>");
        out.print("<td align=\"center\" valign=\"top\"><a href=\"fileListOfProject_Admin.jsp?projectName=" +projectName+ "&action=" +Constant.ACTION_DELETE+ "\"> " +project.file_DeleteToday.size()+ " </a></td>");
        out.print("<td align=\"center\" valign=\"top\"><a href=\"fileListOfProject_Admin.jsp?projectName=" +projectName+ "&action=" +Constant.ACTION_REMOVE+ "\"> " +project.file_RemoveToday.size()+ " </a></td>");
        out.print("</tr>");
    }
  }

  public void outputTableTotal() throws Exception {
    out.print("<tr>");
    out.print("<td valign=\"top\"><b>total</b></td>");
    out.print("<td align=\"center\" valign=\"top\"><a href=\"fileListOfProject_Admin.jsp?action=" +Constant.ACTION_CREATE+ "\"> " +root.getAllFileNum()+ "</a></td>");
    out.print("<td align=\"center\" valign=\"top\"><a href=\"specialFileListOfProject.jsp?fileType=" +SpecialFileListOfProject.FILE_DELETE+ "\"> " +root.getAllDeletedFileNum()+ " </a></td>");
    out.print("<td align=\"center\" valign=\"top\"><a href=\"specialFileListOfProject.jsp?fileType=" +SpecialFileListOfProject.FILE_CLASS+ "\"> " +root.getAllClassNum()+ " </a></td>");
    out.print("<td align=\"center\" valign=\"top\"><a href=\"fileListOfProject_Admin.jsp?action=" +Constant.ACTION_ADD+ "\"> " +root.fileNum_AddToday+ " </a></td>");
    out.print("<td align=\"center\" valign=\"top\"><a href=\"fileListOfProject_Admin.jsp?action=" +Constant.ACTION_MODIFY+ "\"> " +root.fileNum_ModifyToday+ " </a></td>");
    out.print("<td align=\"center\" valign=\"top\"><a href=\"fileListOfProject_Admin.jsp?action=" +Constant.ACTION_DELETE+ "\"> " +root.fileNum_DeleteToday+ " </a></td>");
    out.print("<td align=\"center\" valign=\"top\"><a href=\"fileListOfProject_Admin.jsp?action=" +Constant.ACTION_REMOVE+ "\"> " +root.fileNum_RemoveToday+ " </a></td>");
    out.print("</tr>");

  }
}
