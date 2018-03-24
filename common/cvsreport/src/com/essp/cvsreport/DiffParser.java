package com.essp.cvsreport;

import java.io.*;
import java.nio.charset.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.essp.cvsreport.Command.DiffCommand;

public class DiffParser {

  int lineNum = 0;
  int diffNum = 0;
  public String parser(HttpServletRequest request,
                   HttpServletResponse response, JspWriter out) throws Exception,
      IOException {
    String fileFullName = (String) request.getParameter("fileFullName");
    String leftRevision = (String) request.getParameter("leftRevision");
    String rightRevision = (String) request.getParameter("rightRevision");
    if( fileFullName == null || leftRevision == null || rightRevision == null
        ||fileFullName.equals("null")
        ||leftRevision.equals("null")
        ||rightRevision.equals("null")
        ){
      return "The fileFullName or revision is null.";
    }

    DiffCommand command = new DiffCommand(fileFullName, leftRevision, rightRevision);
    command.execute();

    String fileAbsolutName = command.getAbsolutOutputFile();
    //fileAbsolutName="E:\\len\\update\\tc\\build.xml";//for test
    System.out.println(fileAbsolutName);
    File f = new File(fileAbsolutName);
    Charset CharsetUTF16 = Charset.forName("UTF-8");
    InputStreamReader input = new InputStreamReader(new FileInputStream(f),
        CharsetUTF16);
    BufferedReader reader = new BufferedReader(input);

    String line = null;

    StringBuffer sb = new StringBuffer((int)f.length());
    while ((line = reader.readLine()) != null) {
      lineNum++;

      sb.append(line+"\r\n");
    }

    return sb.toString();
  }

  public int getLineNum(){
    return lineNum;
  }
}
