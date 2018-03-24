package com.essp.cvsreport.jsp;

import java.util.*;
import java.util.Map.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.essp.cvsreport.*;
import com.essp.cvsreport.CvsRevision;

public class FileListOfProject_AdminJsp extends BaseJsp {
  boolean isAdd;
  boolean isModify;
  boolean isDelete;
  boolean isRemove;
  boolean isCreate;
  String actionStr;

  boolean outputAll = false;
  int lineInProject = 1;
  int lineInAll = 1;

  public FileListOfProject_AdminJsp(HttpServletRequest request,
                                    HttpServletResponse response,
                                    JspWriter out, String action) {
    super(request, response, out);
    isAdd = false;
    isModify = false;
    isDelete = false;
    isRemove = false;
    isCreate = false;
    if (Constant.ACTION_ADD.equals(action)) {
      isAdd = true;
      actionStr = "Today 新增";
    } else if (Constant.ACTION_DELETE.equals(action)) {
      isDelete = true;
      actionStr = "Today delete";
    } else if (Constant.ACTION_MODIFY.equals(action)) {
      isModify = true;
      actionStr = "Today 修改";
    } else if (Constant.ACTION_REMOVE.equals(action)) {
      isRemove = true;
      actionStr = "Today remove";
    } else if (Constant.ACTION_CREATE.equals(action)) {
      isCreate = true;
      actionStr = "创建";
    }

  }

  public void outputAllProject() throws Exception {
    outputAll = true;

    outputGeneral();
    for (Iterator iterP = root.projects.entrySet().iterator(); iterP.hasNext(); ) {
      Entry itemP = (Entry) iterP.next();
      String projectName = (String) itemP.getKey();

      commonOutput(projectName);
    }
  }

  public void outputTheProject(String projectName) throws Exception {
    outputAll = false;

    commonOutput(projectName);
  }

  private void commonOutput(String projectName) throws Exception {
    JavaProject project = root.getProject(projectName);
    outputProjectGeneral(project);

    lineInProject = 1;
    for (Iterator iter = getAdminFiles(project).iterator();
                         iter.hasNext(); ) {
      if( isCreate ){
        CvsFile f = (CvsFile) iter.next();
        outputFile(f);
      }else{
        CvsRevision rev = (CvsRevision) iter.next();

        outputFile(rev);
      }
    }
  }

  private List getAdminFiles(JavaProject project) {
    if (isAdd)return project.file_AddToday;
    if (isModify)return project.file_ModifyToday;
    if (isDelete)return project.file_DeleteToday;
    if (isRemove)return project.file_RemoveToday;
    if (isCreate)return project.file_Create;

    return new ArrayList();
  }

  private void outputProjectGeneral(JavaProject project) throws Exception {
    if (outputAll) {
      out.print("<a name=\"" + project.getName() + "\">");
    }

    out.print("<h2>" + project.getName() + "'s " + actionStr +
              "的File List(共" +
              getAdminFiles(project).size() + "次)");

    if (outputAll) {
      out.print("&nbsp;&nbsp;<a href=\"#top\">top</a>");
    }

    out.print("</h2>");

  }

  private void outputGeneral() throws Exception {
    int num = 0;

    if (isAdd) num = root.fileNum_AddToday;
    if (isModify) num = root.fileNum_ModifyToday;
    if (isDelete) num = root.fileNum_DeleteToday;
    if (isRemove) num = root.fileNum_RemoveToday;
    if (isCreate) num = root.fileNum_Create;

    out.print("<h1>" + actionStr + "的File List(共" + num + "次)。"
              + "有项目" + root.getProjectNum() + "个：</h1>");

    for (Iterator iter = root.projects.entrySet().iterator(); iter.hasNext(); ) {
      Entry item = (Entry) iter.next();
      String projectName = (String) item.getKey();
      out.println("<a href=\"#" + projectName + "\">" + projectName + "</a>");
    }

    if( isCreate ){
      out.print("<p>");
      for (Iterator iter = root.getSubFiles().values().iterator(); iter.hasNext(); ) {
        CvsFile f = (CvsFile) iter.next();
        outputFile(f);
      }
    }
  }

  private void outputFile(CvsFile f) throws Exception {
    if (outputAll) {
      out.print(HtmlHelp.nSpace((lineInAll++) + ": ", 7));
    }

    out.print(HtmlHelp.nSpace((lineInProject++) + ": ", 7) + HtmlHelp.getFileLink(f) +
              "<br>");
  }


  private void outputFile(CvsRevision rev) throws Exception {
    if (outputAll) {
      out.print(HtmlHelp.nSpace((lineInAll++) + ": ", 7));

    }

    out.print(HtmlHelp.nSpace((lineInProject++) + ": ", 7) + HtmlHelp.getFileLink(rev.getCvsFile()) + "&nbsp;&nbsp;" + rev.getId() + "<br>");
  }
}
