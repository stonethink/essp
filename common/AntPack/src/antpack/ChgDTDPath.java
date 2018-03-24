package antpack;

import org.apache.tools.ant.Task;
import org.dom4j.*;
import org.dom4j.io.*;

import java.io.*;
import java.util.*;

public class ChgDTDPath
    extends Task {
  public Log log = new Log();

  private String xmlPath = "";
  private String dtdPath = "";

  public ChgDTDPath() {
  }

  public void execute() {
    try {
      File file = new File(xmlPath);
      changePath(file);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void changePath(File file) throws Exception {
    if (file.exists()) {
      if (file.isDirectory()) {
        File[] files = file.listFiles(new DirFilter("xml"));
        for (int i = 0; (files != null) && (i < files.length); i++) {
          changePath(files[i]);
        }
      }
      else {
        /**
         * read
         */
        StringBuffer sb = new StringBuffer("");
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String lineData = "";
        boolean isDTDWrited = false;
        while (true) {
          lineData = br.readLine();
          if (lineData == null) {
            break;
          }
          else {
            //log.debug(lineData);
          }
          if (!isDTDWrited && lineData.indexOf("<!DOCTYPE") >= 0) {
            StringBuffer sb2 = new StringBuffer("");
            int indexDoc=lineData.indexOf("<!DOCTYPE");
            if (lineData.substring(indexDoc).indexOf(">") >= 0) {
              int indexDocEnd=indexDoc+lineData.substring(indexDoc).indexOf(">");
              String oldDocTypeInfo = lineData.substring(indexDoc,indexDocEnd);
              if(indexDoc>0) {
                //log.debug(lineData.substring(0,indexDoc));
                sb.append(lineData.substring(0,indexDoc));
              }
              sb.append(changeDocType(oldDocTypeInfo));
              if(indexDocEnd<lineData.length()-1) {
                //log.debug(lineData.substring(indexDocEnd+1));
                sb.append(lineData.substring(indexDocEnd+1));
              }
              //log.debug(sb.toString());
            }
            else {
              sb2.append(lineData);
              String lineData2 = "";
              while ( (lineData2 = br.readLine()) != null) {
                sb2.append(lineData2 + " ");
                if (lineData2.indexOf(">") >= 0) {
                  break;
                }
              }
              String oldDocTypeInfo = sb2.toString();
              sb.append(changeDocType(oldDocTypeInfo));
            }
//            log.info("["+sb2.toString()+"]");
//            log.info("["+changeDocType(sb2.toString())+"]");
            isDTDWrited = true;
          }
          else {
            sb.append(lineData + "\r\n");
          }
        }
        br.close();
        fr.close();

        //log.debug(sb.toString());
        /**
         * write
         */
        FileWriter fw = new FileWriter(file);
        fw.write(sb.toString());
        fw.close();
      }
    }
  }

  private String changeDocType(String docInfo) {
    String result = docInfo;
    String[] docInfoList = {
        "<!DOCTYPE", "", "", "", ""};
    /**
     * skip the first party
     */
    String docInfo2 = docInfo.trim();
    docInfo2 = docInfo2.substring(9);
    docInfo2 = docInfo2.trim();

    /**
     * skip the second party
     */
    int i = 0;
    while (i < docInfo2.length() &&
           docInfo2.charAt(i) != ' ' &&
           docInfo2.charAt(i) != '\t' &&
           docInfo2.charAt(i) != '\n') {
      i++;
    }
    docInfoList[1] = docInfo2.substring(0, i);

    docInfo2 = docInfo2.substring(i + 1).trim();
    if (docInfo2.startsWith("PUBLIC")) {
      docInfoList[2] = "PUBLIC";

      docInfo2 = docInfo2.substring(6).trim();
      int index = docInfo2.substring(1).indexOf("\"") + 1;
      docInfoList[3] = docInfo2.substring(0, index + 1);
      docInfo2 = docInfo2.substring(index + 1).trim();
      index = docInfo2.substring(1).indexOf("\"") + 1;
      String oldStr = docInfo2.substring(1, index);
      index = oldStr.lastIndexOf("/");
      docInfo2 = dtdPath + oldStr.substring(index);
      //log.info("oldStr="+oldStr);
      //log.info("docInfo2="+docInfo2);
      docInfoList[4] = "\"" + docInfo2 + "\">";
      result = docInfoList[0] + " " + docInfoList[1] + " " +
          docInfoList[2] + " " + docInfoList[3] + " " +
          docInfoList[4] + "\r\n";
    }
    return result;
  }

  public String getDtdPath() {
    return dtdPath;
  }

  public void setDtdPath(String dtdPath) {
    if (dtdPath.trim().endsWith("/")) {
      this.dtdPath = dtdPath.trim();
      this.dtdPath = this.dtdPath.substring(0, this.dtdPath.length() - 1);
    }
    else {
      this.dtdPath = dtdPath;
    }
  }

  public String getXmlPath() {
    return xmlPath;
  }

  public void setXmlPath(String xmlPath) {
    this.xmlPath = xmlPath;
  }

  public static void main(String[] args) throws Exception {
    ChgDTDPath util = new ChgDTDPath();
    util.setXmlPath("E:/temp");
    util.setDtdPath("http://localhost:8080/essp/dtd");
    util.execute();
  }

  class DirFilter
      implements FilenameFilter {
    String afn;

    DirFilter(String afn) {
      this.afn = afn;
    }

    public boolean accept(File dir, String name) {
      File newFile = new File(dir + File.separator + name);

      if (newFile.isDirectory()) {
        return true;
      }

      String f = new File(name).getName();

      return f.indexOf(afn) != -1;
    }
  }

  class Log {
    public void debug(String msg) {
      System.out.println(msg);
    }

    public void info(String msg) {
      System.out.println(msg);
    }
  }
}
