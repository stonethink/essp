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
    out.print("cvs���й����ļ�<a href=\"fileListOfProject_Admin.jsp?action=" +Constant.ACTION_CREATE+ "\">" +root.getAllFileNum()+ "��</a>"
              +"��������ɾ���ģ���ɾ���˵��ļ�����<a href=\"specialFileListOfProject.jsp?fileType=" +SpecialFileListOfProject.FILE_DELETE+ "\">" +root.getAllDeletedFileNum()+ "��</a>��"
              +"������Java�ļ�<a href=\"specialFileListOfProject.jsp?fileType=" +SpecialFileListOfProject.FILE_CLASS+ "\">" +root.getAllClassNum()+ "��</a>��");

    out.print("<br>&nbsp;&nbsp;&nbsp;&nbsp;���������ļ�" +root.fileNum_AddToday
              + "�����޸��ļ�" +root.fileNum_ModifyToday
              + "����delete�ļ�" +root.fileNum_DeleteToday
              + "����remove�ļ�" +root.fileNum_RemoveToday+ "����	");
    out.print("<br>&nbsp;&nbsp;&nbsp;&nbsp;����Ŀ��ϸ���£�");
  }

  public void outputTableHeader() throws Exception {
    out.print("<tr>");
    out.print("<td valign=\"top\"><b>��Ŀ</b></td>");
    out.print("<td valign=\"top\"><b>�ļ���</b></td>");
    out.print("<td align=\"center\" valign=\"top\"><b>��delete��<br>�ļ���</b></td>");
    out.print("<td align=\"center\" valign=\"top\"><b>���ļ�</b></td>");
    out.print("<td align=\"center\" valign=\"top\"><b>��������</b><br>���Σ�</td>");
    out.print("<td align=\"center\" valign=\"top\"><b>�����޸�</b><br>���Σ�</td>");
    out.print("<td align=\"center\" valign=\"top\"><b>����delete</b><br>���Σ�</td>");
    out.print("<td align=\"center\" valign=\"top\"><b>����remove</b><br>���Σ�</td>");
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
