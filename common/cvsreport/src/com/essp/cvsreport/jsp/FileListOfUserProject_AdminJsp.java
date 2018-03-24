package com.essp.cvsreport.jsp;

import java.util.*;
import java.util.Map.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.essp.cvsreport.*;

public class FileListOfUserProject_AdminJsp extends BaseJsp {
  JavaUser user;
  String userName;
  boolean outputAllProject = false;
  int lineInProject = 1;
  int lineInAll = 1;

  boolean isAdd;
  boolean isModify;
  boolean isDelete;
  boolean isRemove;
  boolean isCreate;
  String actionStr;

  public FileListOfUserProject_AdminJsp(HttpServletRequest request,
                                        HttpServletResponse response,
                                        JspWriter out,
                                        String userName, String action) {
    super(request, response, out);
    this.userName = userName;
    user = root.getUser(userName);
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
    outputAllProject = false;

    commonOutput(projectName);
  }

  private void commonOutput(String projectName) throws Exception {
    outputProjectGeneral(projectName);

    lineInProject = 1;
    List fileList = getAdminFileList(projectName);
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

  private void outputProjectGeneral(String projectName) throws Exception {
    if (outputAllProject) {
      out.print("<a name=\"" + projectName + "\">");
    }

    out.print("<h2>"+ userName +"在项目" + projectName + "中" + actionStr +
              "的File List(共" +
              getAdminFileList(projectName).size() + "次)");

    if (outputAllProject) {
      out.print("&nbsp;&nbsp;<a href=\"#top\">top</a>");
    }

    out.print("</h2>");
  }

  private void outputGeneral() throws Exception {
    int num = 0;
    if (isCreate)num = user.fileNum_Create;
    if (isAdd)num = user.fileNum_AddToday;
    if (isModify)num = user.fileNum_ModifyToday;
    if (isDelete)num = user.fileNum_DeleteToday;
    if (isRemove)num = user.fileNum_RemoveToday;

    if (outputAllProject) {
      out.print("<h1>" + userName + " " + actionStr + "的File List(共" +
                 num + "次)。有项目 " +
                getAdminFileMap().size() + "个：</h1>");
      for (Iterator iter = getAdminFileMap().keySet().iterator(); iter.hasNext(); ) {
        String projectName = (String)iter.next();
        out.print("<a href=\"#" +projectName+ "\">" +projectName+ "</a>&nbsp;&nbsp;");
      }
    }
  }

  private void outputFile(CvsFile f) throws Exception {
    if (outputAllProject) {
      out.print(HtmlHelp.nSpace((lineInAll++) + ": ", 7));
    }

    out.print(HtmlHelp.nSpace((lineInProject++) + ": ", 7) + HtmlHelp.getFileLink( f ) +
              "<br>");
  }


  private void outputFile(CvsRevision rev) throws Exception {
    if (outputAllProject) {
      out.print(HtmlHelp.nSpace((lineInAll++) + ": ", 7));

    }

    out.print(HtmlHelp.nSpace((lineInProject++) + ": ", 7) + HtmlHelp.getFileLink( rev.getCvsFile() ) +
              "&nbsp;&nbsp;" + rev.getId() + "<br>");
  }

  private Map getAdminFileMap() {
    if (isAdd)return user.projectFile_AddToday;
    if (isModify)return user.projectFile_ModifyToday;
    if (isDelete)return user.projectFile_DeleteToday;
    if (isRemove)return user.projectFile_RemoveToday;
    if (isCreate)return user.projectFile_Create;

    return new HashMap();
  }

  private List getAdminFileList(String userName) {
    List fileList = null;
    if (isAdd) fileList = (List) user.projectFile_AddToday.get(userName);
    if (isModify) fileList = (List) user.projectFile_ModifyToday.get(userName);
    if (isDelete) fileList = (List) user.projectFile_DeleteToday.get(userName);
    if (isRemove) fileList = (List) user.projectFile_RemoveToday.get(userName);
    if (isCreate) fileList = (List) user.projectFile_Create.get(userName);

    if (fileList == null) {
      return new ArrayList();
    } else {
      return fileList;
    }
  }

}
