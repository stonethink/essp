package com.essp.cvsreport;

import org.apache.tools.ant.taskdefs.CVSPass;
import org.apache.tools.ant.Project;
import java.io.File;
import org.apache.tools.ant.taskdefs.Cvs;
import java.io.PrintStream;


/**
 *
 * <p>Title: </p>
 * <p>Description: </p>
 * workDir----|
 *            |- rootPackage----|
 *                              |-esspfw
 *                              |-essp
 *                              |-tc
 *                              |-......
 *
 *
 *
 */
public class Command {
  String commandLine = "";
  String outputFile = "";
  String cvsPackage = "";
  Cvs cvsTask = null;
  PrintStream out = System.out;

  public Command() {
    cvsTask = new Cvs();
    Project project = new Project();
    cvsTask.setProject(project);
  }

  public int execute() throws Exception {
    if( preExecute() ){

      if (getCommandLine().startsWith("-d") == false) {
        cvsTask.setCommand(Constant.CVSROOT + " " + getCommandLine());
      }
      cvsTask.setOutput(new File(getAbsolutOutputFile()));
      cvsTask.setFailOnError(true);
      cvsTask.setPassfile(getCvspassFile());

      out.println("command line: " + getCommandLine());
      out.println("dest: " + cvsTask.getDest());
      out.println("pass file: " + cvsTask.getPassFile());
      out.println("outputFile: " + getAbsolutOutputFile());
      cvsTask.execute();
    }

    out.println("over.");
    return 0;
  }

  public boolean preExecute() throws Exception {
    generatorCVSPass();

    if (commandLine.startsWith("history")) {
      return preExecuteLog();
    } else if (commandLine.startsWith("log")) {
      return preExecuteLog();
    } else if (commandLine.startsWith("checkout")) {
      return preExecuteCheckout();
    } else if (commandLine.startsWith("update")) {
      return preExecuteLog();
    } else if (commandLine.startsWith("diff")) {
      return preExecuteLog();
    }


    return true;
  }

  protected int generatorCVSPass() {
    File passFile = getCvspassFile();
    if (passFile.exists() == false || passFile.isFile() == false) {
      CVSPass cvsPassTask = new CVSPass();
      cvsPassTask.setProject(new Project());
      cvsPassTask.setCvsroot(Constant.CVSROOT);
      cvsPassTask.setPassword("huaxiao");
      cvsPassTask.setPassfile(new File(Constant.WORK_DIR + "\\cvspass.tmp"));
      cvsPassTask.execute();
    }

    return 0;
  }

  private boolean preExecuteLog() throws Exception {
    checkCheckout();
    cvsTask.setDest(new File(getLogWorkDir()));
    return true;
  }

  private boolean preExecuteCheckout() throws Exception {
    File f = new File(getCheckoutWorkDir() + "\\" + Constant.ROOT_PACKAGE);

    deleteFile(f);

    cvsTask.setDest(new File(getCheckoutWorkDir()));
    return true;
  }

  private void deleteFile(File file) {
    if (file.exists() == false) {
      return;
    }

    if (file.isFile()) {
      file.delete();
      return;
    }

    File[] children = file.listFiles();
    for (int i = 0; i < children.length; i++) {
      deleteFile(children[i]);
    }
    file.delete();
  }

  void checkCheckout() throws Exception {
    String root = Constant.WORK_DIR + "\\" + Constant.ROOT_PACKAGE + "\\CVS";
    File f = new File(root);
    if (f.exists() && f.isDirectory()) {
      return;
    }

    CheckoutCommand checkoutCommand = new CheckoutCommand();
    checkoutCommand.execute();
  }

  private File getCvspassFile() {
    return new File(Constant.WORK_DIR + "\\cvspass.tmp");
  }

  public String getCommandLine() {
    return commandLine;
  }

  public String getOutputFile() {
    if (outputFile == null) {
      return Constant.DEFAULT_OUTPUT_FILE;
    } else {
      return outputFile;
    }
  }

  public String getAbsolutOutputFile()throws Exception {
    String fileName = Constant.WORK_DIR + "\\" + getOutputFile();
    File f = new File(fileName);
    if( f.exists() == false || f.isDirectory() ){
      File p = f.getParentFile();
      if( p.exists() == false || p.isFile() ){
        p.mkdirs();
      }
      //f.createNewFile();
    }
    return fileName;
  }

  public void setCommandLine(String commandLine) {
    this.commandLine = commandLine.trim();
  }

  public void setOutputFile(String outputFile) {
    this.outputFile = outputFile;
  }

  public String getCvsPackage() {
    if (this.cvsPackage == null) {
      return Constant.ROOT_PACKAGE;
    } else {
      return cvsPackage;
    }
  }

  public String getCheckoutWorkDir() throws Exception {
    String workDir = null;
    if (this.cvsPackage == null || cvsPackage.equals("") ||
        this.cvsPackage.equals(Constant.ROOT_PACKAGE)) {
      workDir = Constant.WORK_DIR;
    } else {
      workDir = Constant.WORK_DIR + "\\" + Constant.ROOT_PACKAGE;
    }

    File f = new File(workDir);
    if (f.exists() == false || f.isDirectory() == false) {

      f.mkdirs();
    }
    return workDir;
  }

  public String getLogWorkDir() throws Exception {
    String workDir = null;
    if (this.cvsPackage == null || this.cvsPackage.equals(Constant.ROOT_PACKAGE)) {
      workDir = Constant.WORK_DIR + "\\" + Constant.ROOT_PACKAGE;
    } else {
      workDir = Constant.WORK_DIR + "\\" + Constant.ROOT_PACKAGE + "\\" +
                cvsPackage;
    }

    File f = new File(workDir);
    if (f.exists() == false || f.isDirectory() == false) {
      f.mkdirs();
    }
    return workDir;
  }

  public void setCvsPackage(String cvsPackage) {
    this.cvsPackage = cvsPackage;
  }

  public static class CheckoutCommand extends Command {
    public CheckoutCommand() {
      setCommandLine("checkout -r HEAD -d " + Constant.ROOT_PACKAGE + " " +
                     Constant.ROOT_PACKAGE + "");
      setOutputFile("checkout.log");
    }
  }


  public static class LogCommand extends Command {
    public LogCommand() {
      setCommandLine("log");
      setOutputFile(Constant.LOG_OUTPUT_FILE);
    }
  }

  public static class HistoryCommand extends Command {
    public HistoryCommand() {
      setCommandLine("history -c -l");
      setOutputFile("history.log");
    }
  }

  public static class UpdateCommand extends Command{
    public UpdateCommand() {
      setCommandLine("update -P -d -r HEAD " + Constant.ROOT_PACKAGE);
      setOutputFile("update.log");
    }

    public boolean preExecute() throws Exception {
      generatorCVSPass();
      checkCheckout();
      cvsTask.setDest(new File(Constant.WORK_DIR));
      return true;
    }

  }

  public static class UpdateRevisionCommand extends Command{
    String updateFile = null;
    boolean isExist = false;
    public UpdateRevisionCommand(String fileFullName, String revision)throws Exception{
      String rootpackage = Constant.ROOT_PACKAGE;
      if( fileFullName.indexOf(rootpackage) == -1 ){
        throw new CvsException(CvsException.DATA_INVALID, "The file to update must start with the root package[" +Constant.ROOT_PACKAGE+ "]");
      }
      String moduleName = fileFullName.substring(rootpackage.length()+1);

      String relativePath = "update/" + moduleName;
      relativePath = getRevisionUpdateFileName(relativePath, revision);
      String cmd = "update  -p -r " + revision + " " + moduleName;
      setCommandLine(cmd);
      setOutputFile(relativePath);

      this.updateFile = this.getAbsolutOutputFile();
      File f = new File(updateFile);
      if (f.exists() == false || f.isDirectory()) {
      } else {
        System.out.println("The " + revision + " of file[" + fileFullName +
                           "] already get from cvs.");
        isExist = true;
      }
    }

    public boolean preExecute() throws Exception {
      if( isExist ){
        return false;
      }else{
        return super.preExecute();
      }
    }

    public String getUpdateFile(){
      return this.updateFile;
    }

    private String getRevisionUpdateFileName(String fileName, String revision){
      int extAt = fileName.lastIndexOf(".");
      if(extAt == -1 ){
        return fileName+"_"+revision;
      }else{
        return fileName.substring(0, extAt) + "_" + revision + fileName.substring(extAt);
      }
    }
  }

  public static class DiffCommand extends Command{
    String updateFile = null;
    boolean isExist = false;
    public DiffCommand(String fileFullName, String leftRev, String rightRev)throws Exception{
      String rootpackage = Constant.ROOT_PACKAGE;
      if( fileFullName.indexOf(rootpackage) == -1 ){
        throw new CvsException(CvsException.DATA_INVALID, "The file to diff must start with the root package[" +Constant.ROOT_PACKAGE+ "]");
      }
      String moduleName = fileFullName.substring(rootpackage.length()+1);

      String relativePath = "diff/" + moduleName;
      relativePath = getDiffOutputFileName(relativePath, leftRev, rightRev);
      String cmd = "diff -r " + leftRev + " -r " + rightRev + " " + moduleName;
      setCommandLine(cmd);
      setOutputFile(relativePath);

      this.updateFile = this.getAbsolutOutputFile();
      File f = new File(updateFile);
      if (f.exists() == false || f.isDirectory()) {
      } else {
        System.out.println("The diff between " + leftRev + " and " +rightRev+ " of file[" + fileFullName +
                           "] already get from cvs.");
        isExist = true;
      }
    }

    public boolean preExecute() throws Exception {
      if( isExist ){
        return false;
      }else{
        return super.preExecute();
      }
    }

    public String getUpdateFile(){
      return this.updateFile;
    }

    private String getDiffOutputFileName(String fileName, String leftRev, String rightRev){
      int extAt = fileName.lastIndexOf(".");
      if(extAt == -1 ){
        return fileName+"_"+leftRev+"="+rightRev;
      }else{
        return fileName.substring(0, extAt) + "_" + leftRev+"="+rightRev + fileName.substring(extAt);
      }
    }

    public int execute() throws Exception {
      //不知道为什么这个diff老报异常，结果都产生了。
      try {
        return super.execute();
      } catch (Exception ex) {
        ex.printStackTrace();
        return 0;
      }
    }
  }

  public static void main(String args[]) {
    try {
      //Command command = new UpdateRevisionCommand("essp2/tc/build.xml", "1.1");
      Command command = new UpdateCommand();
      command.execute();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

}
