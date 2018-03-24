package antpack;

import java.io.*;

import org.apache.tools.ant.*;

public class ChangeJspEncode
    extends Task {
  public Log log = new Log();
  private String serverEncode = "GBK";
  private String clientEncode = "GBK";
  private String srcEncoding="GBK";
  private String destEncoding="GBK";
  private String src = "";
  private String dest = "";
  public ChangeJspEncode() {
    super();
  }

  public static void main(String[] args) {
    ChangeJspEncode changejspencode = new ChangeJspEncode();
  }

  public String getClientEncode() {
    return clientEncode;
  }

  public String getServerEncode() {
    return serverEncode;
  }

  public String getDest() {
    return dest;
  }

  public String getSrc() {
    return src;
  }

  public String getDestEncoding() {
    return destEncoding;
  }

  public String getSrcEncoding() {
    return srcEncoding;
  }

  public void setClientEncode(String clientEncode) {
    this.clientEncode = clientEncode;
  }

  public void setServerEncode(String serverEncode) {
    this.serverEncode = serverEncode;
  }

  public void setDest(String dest) {
    this.dest = dest;
  }

  public void setSrc(String src) {
    this.src = src;
  }

  public void setDestEncoding(String destEncoding) {
    this.destEncoding = destEncoding;
  }

  public void setSrcEncoding(String srcEncoding) {
    this.srcEncoding = srcEncoding;
  }

  public void execute() {

  }

  public void trans(String inputPath, String outputPath) throws Exception {
    File inputDir = new File(inputPath);

    if (inputDir.isDirectory()) {
      transToShift_JIS(inputDir, inputPath, outputPath, "jsp");
    }
  }

  private void transToShift_JIS(File file, String inputPath, String outputPath,
                                String extName) throws Exception {
    if (file.exists()) {
      if (file.isDirectory()) {
        File[] files = file.listFiles(new DirFilter(extName));

        for (int i = 0; (files != null) && (i < files.length); i++) {
          transToShift_JIS(files[i], inputPath, outputPath, extName);
        }
      }
      else {
        String path = file.getAbsolutePath();
        String filepath = outputPath + path.substring(inputPath.length());

        log.info(extName + ": src path=" + path);
        log.info(extName + ": dest path=" + filepath);

        FileInputStream fis = new FileInputStream(path);

        int index = filepath.lastIndexOf("\\");

        File outputFilePath = new File(filepath.substring(0, index));
        log.info("mkdir : " + filepath.substring(0, index));
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
        }
        while (true);

        osw.flush();
        osw.close();
        fos.close();
        isr.close();
        fis.close();
      }
    }
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
