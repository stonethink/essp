package com.essp.cvsreport.jsp;

import java.util.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.essp.cvsreport.*;
import java.util.Map.Entry;

public class FileListOfPeriodUser_AdminJsp extends BaseJsp {
  JavaPeriod cal;
  int lineInUser = 1;
  int lineInAllUser = 1;

  boolean isAdd;
  boolean isModify;
  boolean isDelete;
  boolean isRemove;
  boolean outputAllUser;
  String actionStr;

  public FileListOfPeriodUser_AdminJsp(HttpServletRequest request,
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
    outputAllUser = false;
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
      outputAllUser = true;
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

    lineInUser = 1;
    List fileList = getAdminFileList(userName);
    for (Iterator iter = fileList.iterator();
                         iter.hasNext(); ) {
        CvsRevision rev = (CvsRevision) iter.next();

        outputFile(rev);
    }
  }

  private void outputUserGeneral(String userName) throws Exception {
    if (outputAllUser) {
      out.print("<a name=\"" + userName + "\">");
    }

    out.print("<h3>" + userName + "在时间段&nbsp;[&nbsp;" + HtmlHelp.toDateTime(cal.getBegin()) + " ——〉 " +
              HtmlHelp.toDateTime(cal.getEnd())+ "&nbsp;]&nbsp;内<br>" + actionStr +
              "的File List(共" +
              getAdminFileList(userName).size() + "次)");

    if (outputAllUser) {
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

    if (outputAllUser) {
      out.print("<h2>时间段&nbsp;[&nbsp;" + HtmlHelp.toDateTime(cal.getBegin()) + " ——〉 "
                + HtmlHelp.toDateTime(cal.getEnd())+ "&nbsp;]&nbsp;内<br>" + actionStr +
                "的File List(共" + num + "次)。"
                +"有项目 " + getAdminFileMap().size() + "个：</h2>");
      for (Iterator iter = getAdminFileMap().keySet().iterator(); iter.hasNext(); ) {
        String userName = (String) iter.next();
        out.print("<a href=\"#" + userName + "\">" + userName +
                  "</a>&nbsp;&nbsp;");
      }
    }
  }

  private void outputFile(CvsRevision rev) throws Exception {
    if (outputAllUser) {
      out.print(HtmlHelp.nSpace((lineInAllUser++) + ": ", 7));

    }

    out.print(HtmlHelp.nSpace((lineInUser++) + ": ", 7) +
              HtmlHelp.getFileLink(rev.getCvsFile()) +
              "&nbsp;&nbsp;" + rev.getId() + "<br>");
  }

  private Map getAdminFileMap() {
    if (isAdd)return cal.userFile_Add;
    if (isModify)return cal.userFile_Modify;
    if (isDelete)return cal.userFile_Delete;
    if (isRemove)return cal.userFile_Remove;

    return new HashMap();
  }

  private List getAdminFileList(String userName) {
    List fileList = null;
    if (isAdd) fileList = (List) cal.userFile_Add.get(userName);
    if (isModify) fileList = (List) cal.userFile_Modify.get(userName);
    if (isDelete) fileList = (List) cal.userFile_Delete.get(userName);
    if (isRemove) fileList = (List) cal.userFile_Remove.get(userName);

    if (fileList == null) {
      return new ArrayList();
    } else {
      return fileList;
    }
  }


}
