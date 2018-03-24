package com.essp.cvsreport.jsp;

import java.util.*;
import java.util.Map.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.essp.cvsreport.*;

public class FileListOfProjectUser_AdminJsp extends BaseJsp {
  JavaProject project;
  String projectName;
  boolean outputAllUser = false;
  int lineInUser = 1;
  int lineInAll = 1;

  boolean isAdd;
  boolean isModify;
  boolean isDelete;
  boolean isRemove;
  boolean isCreate;
  String actionStr;

  public FileListOfProjectUser_AdminJsp(HttpServletRequest request,
                                        HttpServletResponse response,
                                        JspWriter out,
                                        String projectName, String action) {
    super(request, response, out);
    this.projectName = projectName;
    project = root.getProject(projectName);
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
      actionStr = "Today Delete";
    } else if (Constant.ACTION_MODIFY.equals(action)) {
      isModify = true;
      actionStr = "Today 修改";
    } else if (Constant.ACTION_REMOVE.equals(action)) {
      isRemove = true;
      actionStr = "Today Remove";
    } else if (Constant.ACTION_CREATE.equals(action)) {
      isCreate = true;
      actionStr = "创建";
    }
  }

  public void outputAllUser() throws Exception {
    outputAllUser = true;

    outputGeneral();
    for (Iterator iterP = getAdminFileMap().entrySet().iterator();
                          iterP.hasNext(); ) {
      Entry itemP = (Entry) iterP.next();
      String userName = (String) itemP.getKey();

      commonOutput(userName);
    }
  }

  public void outputTheUser(String userName) throws Exception {

    commonOutput(userName);
  }

  private void commonOutput(String userName) throws Exception {
    outputUserGeneral(userName);

    List fileList = getAdminFileList(userName);
    for (Iterator iter = fileList.iterator();
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

  private void outputUserGeneral(String userName) throws Exception {
    if (outputAllUser) {
      out.print("<a name=\"" + userName + "\">");
    }

    out.print("<h2>" + projectName + "中" + userName + " " + actionStr +
              "的File List(共" +
              getAdminFileList(userName).size() + "次)");

    if (outputAllUser) {
      out.print("&nbsp;&nbsp;<a href=\"#top\">top</a>");
    }

    out.print("</h2>");
  }

  private void outputGeneral() throws Exception {
    if (outputAllUser) {
      out.print("<h1>" + projectName + "中" + actionStr + "的File List(共" +
                getAdminFileList().size() + "次)。有user " +
                getAdminFileMap().size() + "个：</h1>");

      for (Iterator iter = getAdminFileMap().keySet().iterator(); iter.hasNext(); ) {
        String userName = (String) iter.next();
        out.print("<a href=\"#" + userName + "\">" + userName +
                  "</a>&nbsp;&nbsp;");
      }

    }
  }

  private void outputFile(CvsFile f) throws Exception {
    if (outputAllUser) {
      out.print(HtmlHelp.nSpace((lineInAll++) + ": ", 7));
    }

    out.print(HtmlHelp.nSpace((lineInUser++) + ": ", 7) + HtmlHelp.getFileLink(f) +
              "<br>");
  }


  private void outputFile(CvsRevision rev) throws Exception {
    if (outputAllUser) {
      out.print(HtmlHelp.nSpace((lineInAll++) + ": ", 7));

    }

    out.print(HtmlHelp.nSpace((lineInUser++) + ": ", 7) + HtmlHelp.getFileLink(rev.getCvsFile()) +
              "&nbsp;&nbsp;" + rev.getId() + "<br>");
  }

  private List getAdminFileList() {
    if (isAdd)return project.file_AddToday;
    if (isModify)return project.file_ModifyToday;
    if (isDelete)return project.file_DeleteToday;
    if (isRemove)return project.file_RemoveToday;
    if (isCreate)return project.file_Create;

    return new ArrayList();
  }

  private Map getAdminFileMap() {
    if (isAdd)return project.userFile_AddToday;
    if (isModify)return project.userFile_ModifyToday;
    if (isDelete)return project.userFile_DeleteToday;
    if (isRemove)return project.userFile_RemoveToday;
    if (isCreate)return project.userFile_Create;

    return new HashMap();
  }

  private List getAdminFileList(String userName) {
    List fileList = null;
    if (isAdd) fileList = (List) project.userFile_AddToday.get(userName);
    if (isModify) fileList = (List) project.userFile_ModifyToday.get(userName);
    if (isDelete) fileList = (List) project.userFile_DeleteToday.get(userName);
    if (isRemove) fileList = (List) project.userFile_RemoveToday.get(userName);
    if (isCreate) fileList = (List) project.userFile_Create.get(userName);

    if (fileList == null) {
      return new ArrayList();
    } else {
      return fileList;
    }
  }

}
