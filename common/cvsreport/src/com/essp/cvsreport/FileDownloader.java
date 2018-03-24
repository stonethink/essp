package com.essp.cvsreport;

import java.io.*;
import java.nio.charset.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.JspWriter;
import com.essp.cvsreport.Command.UpdateRevisionCommand;

public class FileDownloader{

  int lineNum = 0;
  public String run2(HttpServletRequest request,
                   HttpServletResponse response, JspWriter out) throws Exception,
      IOException {
    String fileFullName = (String) request.getParameter("fileFullName");
    String revision = (String) request.getParameter("revision");
    if( fileFullName == null || revision == null
        ||fileFullName.equals("null")
        ||revision.equals("null")
        ){
      return "The fileFullName or revision is null.";
    }

    UpdateRevisionCommand command = new UpdateRevisionCommand(fileFullName, revision);
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
      sb.append(line+"\r\n");
      lineNum++;
    }

    return sb.toString();
  }

  public int getLineNum(){
    return lineNum;
  }
}
