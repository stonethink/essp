package antpack;

import java.io.*;
import java.util.*;

import org.apache.tools.ant.*;
import org.dom4j.*;
import org.dom4j.io.*;

public class CopyMapping
    extends Task {
  private Log log = new Log();
  private String dest;
  private String src;
  private String encoding;

  public CopyMapping() {
    super();
    dest = null;
    src = null;
    encoding = "GBK";
  }

  public void execute() {
    try {
      SAXReader srcReader = new SAXReader();
      Document srcDoc = srcReader.read(src);

      SAXReader destReader = new SAXReader();
      Document destDoc = destReader.read(dest);

      List list = srcDoc.selectNodes(
          "//hibernate-configuration/session-factory/mapping");
      if (list != null) {
        for (int i = 0; i < list.size(); i++) {
          Element e = (Element) list.get(i);
          e.setParent(null);
        }

        List factNodes = destDoc.selectNodes(
            "//hibernate-configuration/session-factory");
        if (factNodes != null && factNodes.size() > 0) {
          Element factNode = (Element) factNodes.get(0);
          List origList = factNode.elements();
          origList.addAll(list);
          factNode.setContent(origList);
          OutputFormat format = OutputFormat.createPrettyPrint();
          format.setEncoding(encoding);
          XMLWriter output = new XMLWriter(
              new FileWriter(new File(
              "F:/work/essp2005/newCVS/essp2/pms/test.xml")), format);
          output.write(destDoc);
          output.close();
        }
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    CopyMapping task = new CopyMapping();
    task.setDest("F:/work/essp2005/newCVS/essp2/pms/dest.xml");
    task.setSrc("F:/work/essp2005/newCVS/essp2/pms/src.xml");
    task.execute();
  }

  class Log {
    public void debug(String msg) {
      System.out.println(msg);
    }

    public void info(String msg) {
      System.out.println(msg);
    }
  }

  public String getDest() {
    return dest;
  }

  public String getSrc() {
    return src;
  }

  public String getEncoding() {
    return encoding;
  }

  public void setDest(String dest) {
    this.dest = dest;
  }

  public void setSrc(String src) {
    this.src = src;
  }

  public void setEncoding(String encoding) {
    this.encoding = encoding;
  }
}
