package antpack;

import java.io.File;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import org.apache.tools.ant.Task;

public class MailTask
    extends Task {
  class Log {

    public void debug(String msg) {
      System.out.println(msg);
    }

    public void info(String msg) {
      System.out.println(msg);
    }

    Log() {
    }
  }

  private String files;
  private String from;
  private Log log;
  private String mailhost;
  private String mailport;
  private String message;
  private Session session;
  private String subject;
  private String tolist;
  private String verbose;

  public MailTask() {
    log = new Log();
    mailhost = "";
    mailport = "";
    subject = "";
    from = "";
    tolist = "";
    message = "";
    files = "";
    verbose = "true";
  }

  public void execute() {
    try {
      Properties props = new Properties();
      props.put("mail.smtp.host", mailhost);
      if (session == null) {
        session = Session.getDefaultInstance(props, null);
        if (verbose != null && verbose.trim().toLowerCase().equals("true")) {
          session.setDebug(true);
        }
      }
      Message mesg = new MimeMessage(session);
      String toList[] = tolist.split(",");
      InternetAddress addresses[] = new InternetAddress[toList.length];
      for (int i = 0; i < addresses.length; i++) {
        addresses[i] = new InternetAddress(toList[i]);

      }
      mesg.setRecipients(javax.mail.Message.RecipientType.TO, addresses);
      mesg.setFrom(new InternetAddress(from));
      mesg.setSubject(subject);
      Multipart mp = new MimeMultipart();
      MimeBodyPart mbp = null;
      mbp = new MimeBodyPart();
      mbp.setText(message);
      mp.addBodyPart(mbp);
      String fileList[] = files.split(",");
      for (int i = 0; i < fileList.length; i++) {
        mbp = new MimeBodyPart();
        File f = new File(fileList[i]);
        javax.activation.DataSource ds = new FileDataSource(f);
        mbp.setDataHandler(new DataHandler(ds));
        int j = f.getName().lastIndexOf(File.separator);
        String uf = f.getName().substring(j + 1);
        mbp.setFileName(uf);
        mp.addBodyPart(mbp);
      }

      mesg.setContent(mp);
      Transport.send(mesg);
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public String getFiles() {
    return files;
  }

  public String getFrom() {
    return from;
  }

  public Log getLog() {
    return log;
  }

  public String getMailhost() {
    return mailhost;
  }

  public String getMailport() {
    return mailport;
  }

  public String getMessage() {
    return message;
  }

  public String getSubject() {
    return subject;
  }

  public String getTolist() {
    return tolist;
  }

  public String getVerbose() {
    return verbose;
  }

  public void setFiles(String files) {
    this.files = files;
  }

  public void setFrom(String from) {
    this.from = from;
  }

  public void setLog(Log log) {
    this.log = log;
  }

  public void setMailhost(String mailhost) {
    this.mailhost = mailhost;
  }

  public void setMailport(String mailport) {
    this.mailport = mailport;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public void setTolist(String tolist) {
    this.tolist = tolist;
  }

  public void setVerbose(String verbose) {
    this.verbose = verbose;
  }
}
