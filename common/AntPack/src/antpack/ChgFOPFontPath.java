package antpack;

import org.apache.tools.ant.Task;
import org.dom4j.*;
import org.dom4j.io.*;

import java.io.*;
import java.util.*;


/**
 *
 * <p>Title: Ant Task Pack</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author yery
 * @version 1.0
 */
public class ChgFOPFontPath extends Task{
  private Log log=new Log();
  private String cfgPath="";
  private String fontPath="";

  public ChgFOPFontPath() {
  }

  public void execute() {
    log.info("Font path:"+fontPath);
    try {
      int iIndex=fontPath.indexOf(":///");
      fontPath=fontPath.substring(0,iIndex+4)+fontPath.substring(iIndex+4).replace('/','\\');
      log.info("Change font path to:"+fontPath);
    } catch (Exception e) {
      e.printStackTrace();
    }

    try {
      SAXReader saxReader = new SAXReader();
      Document document = saxReader.read(cfgPath);
      List list = document.selectNodes("//fonts/font" );

      Iterator iter = list.iterator();
      while (iter.hasNext()) {
        Element element = (Element) iter.next();
        String old_metrics_file=element.attribute("metrics-file").getValue();
        String old_embed_file=element.attribute("embed-file").getValue();
        String new_metrics_file="";
        String new_embed_file="";

        try {
          int iIndex=old_metrics_file.lastIndexOf("\\");
          new_metrics_file=fontPath+old_metrics_file.substring(iIndex+1);
        } catch(Exception e) {
          e.printStackTrace();
        }

        try {
          int iIndex=old_embed_file.lastIndexOf("\\");
          new_embed_file=fontPath+old_embed_file.substring(iIndex+1);
        } catch(Exception e) {
          e.printStackTrace();
        }

        log.info("Change "+old_metrics_file+" to "+new_metrics_file);
        log.info("Change "+old_embed_file+" to "+new_embed_file);

        element.attribute("metrics-file").setValue(new_metrics_file);
        element.attribute("embed-file").setValue(new_embed_file);
      }

      XMLWriter output = new XMLWriter(new FileWriter( new File(cfgPath) ));
      output.write( document );
      output.close();

    } catch(Exception e) {
      e.printStackTrace();
    }
  }
  public String getFontPath() {
    return fontPath;
  }
  public void setFontPath(String fontPath) {
    this.fontPath = fontPath;
  }

  public String getCfgPath() {
    return cfgPath;
  }
  public void setCfgPath(String cfgPath) {
    this.cfgPath = cfgPath;
  }

  class Log {
         public void debug(String msg) {
                 System.out.println(msg);
         }
         public void info(String msg) {
                 System.out.println(msg);
         }
  }

  public static void main(String[] args) {
    ChgFOPFontPath tool=new ChgFOPFontPath();
    tool.setCfgPath("file:///E:\\struts\\eclipse2.1.1\\workspace\\qualica\\WEB-INF\\userconfig.xml");
    tool.setFontPath("file:///E:\\struts\\");
    tool.execute();
  }

}
