package antpack;

import org.apache.tools.ant.Task;
import java.io.*;

/**
 * <p>Title: Ant Task Pack</p>
 * <p>Description:
 * </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author yery
 * @version 1.0
 *  Example:
 *  <taskdef name="ChangeEncoding" classname="antpack.ChangeEncoding">
 *     <classpath>
 *         <pathelement location="${AntPack_LIB}/antpack.jar"/>
 *     </classpath>
 *  </taskdef>
 *  <target name="ChangeEncoding">
 *     <ChangeEncoding src=""
 *                     dest=""
 *                     srcEncoding=""
 *                     destEncoding=""
 *                     ext="java,properties,conf"/>
 *   </target>
 */
public class ChangeEncoding extends Task{
  public Log log=new Log();

  private String srcEncoding="";
  private String destEncoding="";
  private String src="";
  private String dest="";
  private String ext="";
  public ChangeEncoding() {
  }

  public void execute() {
    try {
      if(ext!=null) {
        String[] extList = ext.split(",");
        log.info("trans encoding from "+srcEncoding+" to "+destEncoding);
        log.info("src : "+src);
        log.info("dest : "+dest);
        for(int i=0;i<extList.length;i++) {
          log.info("begin trans : all '."+extList[i]+"' files");
          //this.trans(src, dest,extList[i]);
          log.info("end trans : all '."+extList[i]+"' files");
        }
      }
    } catch(Exception e) {
      e.printStackTrace();
    }
  }

  public String getDest() {
    return dest;
  }
  public String getDestEncoding() {
    return destEncoding;
  }
  public String getExt() {
    return ext;
  }
  public String getSrc() {
    return src;
  }
  public String getSrcEncoding() {
    return srcEncoding;
  }
  public void setDest(String dest) {
    this.dest = dest;
  }
  public void setExt(String sExt) {
    this.ext = sExt;
  }
  public void setDestEncoding(String destEncoding) {
    this.destEncoding = destEncoding;
  }
  public void setSrc(String src) {
    this.src = src;
  }
  public void setSrcEncoding(String srcEncoding) {
    this.srcEncoding = srcEncoding;
  }

  public void trans(String inputPath,String outputPath,String extName)  throws Exception {
      File inputDir = new File(inputPath);

      if (inputDir.isDirectory()) {
          transToShift_JIS(inputDir, inputPath, outputPath,extName);
      }
  }
  private  void transToShift_JIS(File file, String inputPath, String outputPath,String extName)
      throws Exception {
      if (file.exists()) {
          if (file.isDirectory()) {
              File[] files = file.listFiles(new DirFilter(extName));

              for (int i = 0; (files != null) && (i < files.length); i++) {
                  transToShift_JIS(files[i], inputPath, outputPath,extName);
              }
          } else {
              String path = file.getAbsolutePath();
              String filepath=outputPath+path.substring(inputPath.length());

              log.info(extName+": src path=" + path);
              log.info(extName+": dest path="+filepath);

              FileInputStream fis = new FileInputStream(path);


                              int index=filepath.lastIndexOf("\\");

              File outputFilePath=new File(filepath.substring(0,index));
              log.info("mkdir : "+filepath.substring(0,index));
                              outputFilePath.mkdirs();

              FileOutputStream fos = new FileOutputStream(filepath);


              InputStreamReader isr = new InputStreamReader(fis, this.srcEncoding);
              OutputStreamWriter osw = new OutputStreamWriter(fos, this.destEncoding);

              do {
                  int size = 0;
                  char[] buf = new char[200];
                  size = isr.read(buf, 0, 200);

                  if (size > 0) {
                      osw.write(buf, 0, size);
                  }

                  if (size < 200) {
                      break;
                  }
              } while (true);

              osw.flush();
              osw.close();
              fos.close();
              isr.close();
              fis.close();
          }
      }
  }

  class DirFilter implements FilenameFilter {
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
