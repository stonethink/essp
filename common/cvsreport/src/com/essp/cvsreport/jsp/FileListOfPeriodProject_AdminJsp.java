package com.essp.cvsreport.jsp;

import java.util.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.essp.cvsreport.*;
import java.util.Map.Entry;

public class FileListOfPeriodProject_AdminJsp extends BaseJsp {
  JavaPeriod cal;
  int lineInProject = 1;
  int lineInAllProject = 1;

  boolean isAdd;
  boolean isModify;
  boolean isDelete;
  boolean isRemove;
  boolean outputAllProject;
  String actionStr;

  public FileListOfPeriodProject_AdminJsp(HttpServletRequest request,
                                   HttpServletResponse response, JspWriter out) {
    super(request, response, out);
    cal = (JavaPeriod) request.getSession().getAttribute("JavaPeriod");
    String action = (String) request.getParameter("action");
    String last = (String) request.getParameter("last");
    String next = (String) request.getParameter("next");
    if( "true".equals(last) ){
      cal.last();
    }else if( "true".equals(next) ){
      cal.next();
    }

    isAdd = false;
    isModify = false;
    isDelete = false;
    isRemove = false;
    outputAllProject = false;
    if (Constant.ACTION_ADD.equals(action)) {
      isAdd = true;
      actionStr = "新增";
    } else if (Constant.ACTION_DELETE.equals(action)) {
      isDelete = true;
      actionStr = "Delete";
    } else if (Constant.ACTION_MODIFY.equals(action)) {
      isModify = true;
      actionStr = "修改";
    } else if (Constant.ACTION_REMOVE.equals(action)) {
      isRemove = true;
      actionStr = "Remove";
    } else {
      outputAllProject = true;
    }
  }

  public void outputAllProject() throws Exception {
    outputAllProject = true;

    outputGeneral();
    for (Iterator iterP = getAdminFileMap().entrySet().iterator();
                          iterP.hasNext(); ) {
      Entry itemP = (Entry) iterP.next();
      String projectName = (String) itemP.getKey();

      commonOutput(projectName);
    }
  }

  public void outputTheProject(String projectName) throws Exception {
    commonOutput(projectName);
  }

  private void commonOutput(String projectName) throws Exception {
    outputProjectGeneral(projectName);

    lineInProject = 1;
    List fileList = getAdminFileList(projectName);
    for (Iterator iter = fileList.iterator();
                         iter.hasNext(); ) {
        CvsRevision rev = (CvsRevision) iter.next();

        outputFile(rev);
    }
  }

  private void outputProjectGeneral(String projectName) throws Exception {
    if (outputAllProject) {
      out.print("<a name=\"" + projectName + "\">");
    }

    out.print("<h3>项目" + projectName + "在时间段&nbsp;[&nbsp;" + HtmlHelp.toDateTime(cal.getBegin()) + " ——〉 " +
              HtmlHelp.toDateTime(cal.getEnd())+ "&nbsp;]&nbsp;内<br>" + actionStr +
              "的File List(共" +
              getAdminFileList(projectName).size() + "次)");

    if (outputAllProject) {
      out.print("&nbsp;&nbsp;<a href=\"#top\">top</a>");
    }

    out.print("</h3>");
  }

  private void outputGeneral() throws Exception {
    int num = 0;
    if (isAdd) num = cal.fileNum_Add;
    if (isModify) num = cal.fileNum_Modify;
    if (isDelete) num = cal.fileNum_Delete;
    if (isRemove) num = cal.fileNum_Remove;

    if (outputAllProject) {
      out.print("<h2>时间段&nbsp;[&nbsp;" + HtmlHelp.toDateTime(cal.getBegin()) + " ——〉 "
                + HtmlHelp.toDateTime(cal.getEnd())+ "&nbsp;]&nbsp;内<br>" + actionStr +
                "的File List(共" + num + "次)。"
                +"有项目 " + getAdminFileMap().size() + "个：</h2>");
      for (Iterator iter = getAdminFileMap().keySet().iterator(); iter.hasNext(); ) {
        String projectName = (String) iter.next();
        out.print("<a href=\"#" + projectName + "\">" + projectName +
                  "</a>&nbsp;&nbsp;");
      }
    }
  }

  private void outputFile(CvsRevision rev) throws Exception {
    if (outputAllProject) {
      out.print(HtmlHelp.nSpace((lineInAllProject++) + ": ", 7));

    }

    out.print(HtmlHelp.nSpace((lineInProject++) + ": ", 7) +
              HtmlHelp.getFileLink(rev.getCvsFile()) +
              "&nbsp;&nbsp;" + rev.getId() + "<br>");
  }

  private Map getAdminFileMap() {
    if (isAdd)return cal.projectFile_Add;
    if (isModify)return cal.projectFile_Modify;
    if (isDelete)return cal.projectFile_Delete;
    if (isRemove)return cal.projectFile_Remove;

    return new HashMap();
  }

  private List getAdminFileList(String projectName) {
    List fileList = null;
    if (isAdd) fileList = (List) cal.projectFile_Add.get(projectName);
    if (isModify) fileList = (List) cal.projectFile_Modify.get(projectName);
    if (isDelete) fileList = (List) cal.projectFile_Delete.get(projectName);
    if (isRemove) fileList = (List) cal.projectFile_Remove.get(projectName);

    if (fileList == null) {
      return new ArrayList();
    } else {
      return fileList;
    }
  }


}
