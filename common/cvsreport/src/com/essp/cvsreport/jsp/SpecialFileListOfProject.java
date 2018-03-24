package com.essp.cvsreport.jsp;

import java.util.*;
import java.util.Map.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.essp.cvsreport.*;

public class SpecialFileListOfProject extends BaseJsp {
  public final static String FILE_DELETE = "delete";
  public final static String FILE_CLASS = "class";

  boolean outputDeleteFiles=false;
  boolean outputClassFiles=false;

  boolean outputAll = false;
  int lineInProject = 1;
  int lineInAll = 1;

  String typeStr = "";

  public SpecialFileListOfProject(HttpServletRequest request,
                              HttpServletResponse response, JspWriter out, String type) {
    super(request, response, out);

    if( FILE_CLASS.equals(type) ){
      outputClassFiles = true;
      typeStr = "Class";
    }else if( FILE_DELETE.equals(type) ){
      outputDeleteFiles = true;
      typeStr = "Delete";
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

    if( outputDeleteFiles ){
      out.print("<h2>root下有："+(root.getAllDeletedFileNum()-lineInAll+1)+"个</h2>");
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
    for (Iterator iter = getSpecialFileMap(project).entrySet().iterator();
                         iter.hasNext(); ) {
      Map.Entry item = (Map.Entry) iter.next();
      String fileFullName = (String) item.getKey();
      CvsFile file = (CvsFile) item.getValue();

      outputFile(file);
    }
  }

  private void outputProjectGeneral(JavaProject project) throws Exception {
    if (outputAll) {
      out.print("<a name=\"" + project.getName() + "\">");
    }

    out.print("<h2>" + project.getName() + "'s All " +typeStr+ " File List(共"
              + getSpecialFileMap(project).size() + "个)");

    if (outputAll) {
      out.print("&nbsp;&nbsp;<a href=\"#top\">top</a>");
    }

    out.print("</h2>");

  }

  private void outputGeneral() throws Exception {
    if( outputAll ){
      int num = 0;
      if( outputClassFiles ){
        num = root.getAllClassNum();
      }else if( outputDeleteFiles ){
        num = root.getAllDeletedFileNum();
      }

      out.print("<h1>All " +typeStr+ " File List(共" + num + "个)。有项目" +
                root.getProjectNum() + "个：</h1>");

      for (Iterator iter = root.projects.entrySet().iterator(); iter.hasNext(); ) {
        Entry item = (Entry) iter.next();
        String projectName = (String) item.getKey();
        out.println("<a href=\"#" + projectName + "\">" + projectName + "</a>");
      }
    }
  }

  private void outputFile(CvsFile f) throws Exception {
    if (outputAll) {
      out.print(HtmlHelp.nSpace((lineInAll++) + ": ", 7));
    }

    if( outputClassFiles ){
      out.print(HtmlHelp.nSpace((lineInProject++) + ": ", 7) + HtmlHelp.getClassLink((JavaClass)f) +
                "<br>");
    }else{
      out.print(HtmlHelp.nSpace((lineInProject++) + ": ", 7) + HtmlHelp.getFileLink(f)+
                "<br>");
    }
  }

  private Map getSpecialFileMap(JavaProject project){
    if( outputClassFiles ){
      return project.allClasses;
    }else if( outputDeleteFiles ){
      return project.allDeletedFiles;
    }else{
      return new HashMap();
    }
  }

}
