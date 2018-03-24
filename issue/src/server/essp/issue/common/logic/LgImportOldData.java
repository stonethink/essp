package server.essp.issue.common.logic;

import server.framework.logic.*;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.*;
import server.framework.common.BusinessException;
import db.essp.issue.Issue;
import db.essp.issue.IssueResolution;
import db.essp.issue.IssueConclusion;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Map;
import java.util.HashMap;
import java.io.File;
import java.util.Iterator;

/**
 *
 * <p>Title: </p>
 *
 * <p>Description: 此程序用于从旧的ESSP系统中将问题单数据导入到ISSUE系统</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class LgImportOldData extends AbstractBusinessLogic {
    Connection conn = null;

    public LgImportOldData() {
        conn = this.getDbAccessor().getConnect();
    }

    public void executeLogic() throws Exception {
//        clearHistory();
//        importSPR();
//        importQA();
        importFiles();
    }

    private void clearHistory() throws Exception {
        Statement stmt = conn.createStatement();
        stmt.executeUpdate("delete from ISSUE s where s.account_id in (select distinct(PROJNAME) as account_id  from ESSP_CRM_SOFT_CLIENT    )");
        stmt.executeUpdate("delete from ISSUE s where s.account_id in (select distinct(PROJNAME) as account_id  from ESSP_CRM_SOFT_PROBLEM_QA)");
        stmt.executeUpdate("delete from ISSUE_RESOLUTION s where s.account_id in (select distinct(PROJNAME) as account_id  from ESSP_CRM_SOFT_CLIENT    )");
        stmt.executeUpdate("delete from ISSUE_RESOLUTION s where s.account_id in (select distinct(PROJNAME) as account_id  from ESSP_CRM_SOFT_PROBLEM_QA)");
        stmt.executeUpdate("delete from ISSUE_CONCLUSION s where s.account_id in (select distinct(PROJNAME) as account_id  from ESSP_CRM_SOFT_CLIENT    )");
        stmt.executeUpdate("delete from ISSUE_CONCLUSION s where s.account_id in (select distinct(PROJNAME) as account_id  from ESSP_CRM_SOFT_PROBLEM_QA)");
        ResultSet rs=stmt.executeQuery("select max(rid) as rid from ISSUE");
        String maxRid=rs.getString("rid");
        rs.close();
        stmt.executeUpdate("update essp_hbseq set SEQ_NO='"+maxRid+"' where SQL_TYPE='ISSUE'");
        stmt.close();
    }

    private void importSPR() throws Exception {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(
            "select * from ESSP_CRM_SOFT_CLIENT order by PROJNAME");
        while (rs.next()) {
            Issue issue = new Issue();
            issue.setAccountId(Long.valueOf(rs.getString("PROJNAME")));
            issue.setAttachment(rs.getString("ATTACHMENT"));
            issue.setAttachmentdesc(rs.getString("ATTACHMENT_DESC"));
            String attachment = rs.getString("ATTACHMENT");
            if (attachment != null) {
                attachment = attachment.replace('\\', '/');
                int pos = attachment.lastIndexOf("/");
                String attachmentid = attachment.substring(pos + 1);
                if (attachmentid.indexOf(".") >= 0) {
                    pos = attachmentid.indexOf(".");
                    attachmentid = attachmentid.substring(0, pos);
                }
                issue.setAttachmentId(attachmentid);
            }
            issue.setDescription(rs.getString("PROBABSTRACT"));
            issue.setEmail(rs.getString("CLIENTEMAIL"));
            issue.setIssueType("SPR");
            issue.setPriority("NORMAL");
            issue.setFilleBy(rs.getString("FILLER"));
            issue.setFilleDate(rs.getDate("STARTDATE"));
            java.util.Date dueDate=rs.getDate("PREDICTEDDATE");
            if(dueDate==null) {
                dueDate=rs.getDate("STARTDATE");
            }
            issue.setDueDate(dueDate);
            issue.setDueDate(rs.getDate("PREDICTEDDATE"));
            issue.setPhone(rs.getString("CLIENTPHONE"));
            issue.setFax(rs.getString("CLIENTFAX"));
            //issue.setScope(rs.getString(""));//
            issue.setIssueId(rs.getString("PROBNO"));
            issue.setIssueName(rs.getString("PROBNAME"));
            String principal = rs.getString("CLIENTNAME");
            if (principal != null &&
                (principal.equals("Hua Li") || principal.equals("Hua Li"))) {
                principal = "hua.li";
            }
            issue.setPrincipal(rs.getString("CLIENTNAME"));
            issue.setIssueStatus("Closed");

            //issue.setFax(rs.get);
            this.getDbAccessor().save(issue);

            IssueResolution resolution = new IssueResolution();
            resolution.setRid(issue.getRid());
            resolution.setResolution(rs.getString("RESOLUTION"));
            this.getDbAccessor().save(resolution);

            IssueConclusion conclusion = new IssueConclusion();
            conclusion.setRid(issue.getRid());
            conclusion.setFinishedDate(rs.getDate("ENDDATE"));
            conclusion.setDeliveredDate(rs.getDate("DELIVERDATE"));
            conclusion.setConfirmDate(rs.getDate("ENDDATE"));
            conclusion.setClosureStatus("Normal Closed");
            this.getDbAccessor().save(conclusion);
        }
        rs.close();
        stmt.close();
    }

    private void importQA() throws Exception {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(
            "select * from ESSP_CRM_SOFT_PROBLEM_QA order by PROJNAME");
        while (rs.next()) {
            Issue issue = new Issue();
            issue.setAccountId(Long.valueOf(rs.getString("PROJNAME")));
            issue.setAttachment(rs.getString("ATTACHMENT"));
            issue.setAttachmentdesc(rs.getString("ATTACHMENT_DESC"));
            String attachment = rs.getString("ATTACHMENT");
            if (attachment != null) {
                attachment = attachment.replace('\\', '/');
                int pos = attachment.lastIndexOf("/");
                String attachmentid = attachment.substring(pos + 1);
                if (attachmentid.indexOf(".") >= 0) {
                    pos = attachmentid.indexOf(".");
                    attachmentid = attachmentid.substring(0, pos);
                }
                issue.setAttachmentId(attachmentid);
            }
            issue.setDescription(rs.getString("PROBABSTRACT"));
            issue.setEmail(rs.getString("CLIENTEMAIL"));
            issue.setIssueType("Q＆A");
            issue.setPriority("NORMAL");
            //issue.setFilleBy(rs.getString("CLIENTNAME"));
            issue.setFilleBy(rs.getString("FILLER"));
            issue.setFilleDate(rs.getDate("STARTDATE")); //
            java.util.Date dueDate=rs.getDate("PREDICTEDDATE");
            if(dueDate==null) {
                dueDate=rs.getDate("STARTDATE");
            }
            issue.setDueDate(dueDate);
            issue.setPhone(rs.getString("CLIENTPHONE"));
            issue.setFax(rs.getString("CLIENTFAX"));
            //issue.setScope(rs.getString(""));//
            issue.setIssueId(rs.getString("PROBNO"));
            issue.setIssueName(rs.getString("PROBNAME"));
            //issue.setPrincipal(rs.getString("FILLER"));
            issue.setPrincipal(rs.getString("CLIENTNAME"));
            //issue.setIssueStatus(rs.getString("ALTERSTATUS"));
            issue.setIssueStatus("Closed");
            this.getDbAccessor().save(issue);

            IssueResolution resolution = new IssueResolution();
            resolution.setRid(issue.getRid());
            resolution.setResolution(rs.getString("RESOLUTION"));
            this.getDbAccessor().save(resolution);

            IssueConclusion conclusion = new IssueConclusion();
            conclusion.setRid(issue.getRid());
            conclusion.setFinishedDate(rs.getDate("ENDDATE"));
            conclusion.setDeliveredDate(rs.getDate("DELIVERDATE"));
            conclusion.setConfirmDate(rs.getDate("ENDDATE"));
            conclusion.setClosureStatus("Normal Closed");
            this.getDbAccessor().save(conclusion);
        }
        rs.close();
        stmt.close();
    }

    private String mkTempdir(String root,String accountCode) throws Exception {
        String tempDir=root+"/ISSUE/temp/"+accountCode;
        File file=new File(tempDir);
        file.mkdirs();
        return tempDir;
    }

    private void removeTempdir(String root) throws Exception {
        String tempDir=root+"/ISSUE/temp";
        File file=new File(tempDir);
        removeDir(file);
    }

    private void removeDir(File file) throws Exception {
        if(file.isFile()) {
            file.delete();
        } else {
            File[] files=file.listFiles();
            if(files!=null) {
                for (int i = 0; i < files.length; i++) {
                    removeDir(files[i]);
                }
            }
            file.delete();
        }
    }

    private void copyFiles(String root,String accountCode) throws Exception {
        String tempDir=root+"/ISSUE/temp/"+accountCode;
        String srcDir=root+"/ISSUE/"+accountCode;
        File file=new File(srcDir);
        File[] files=file.listFiles();
        if(files!=null) {
            for(int i=0;i<files.length;i++) {
                String fileName=files[i].getName();
                String path=files[i].getPath();
                String newFileName=tempDir+"/"+fileName;
                File newFile=new File(newFileName);
                files[i].renameTo(newFile);
            }
        }
    }

    private void moveFile(String root,String accountCode,String fileName) throws Exception {
        String tempDir=root+"/ISSUE/temp/"+accountCode;
        String targetDir=root+"/ISSUE/"+accountCode;
        String srcFileName=tempDir+"/"+fileName;
        String targetFileName=targetDir+"/"+fileName;
        File srcFile=new File(srcFileName);
        File targetFile=new File(targetFileName);
        srcFile.renameTo(targetFile);
    }

    private void importFiles() throws Exception {
        String root = "";
        try {
            ResourceBundle resources = ResourceBundle.getBundle(
                "fileserver", Locale.getDefault());
            String realDir = resources.getString("real_directory");
            if (realDir != null && !realDir.trim().equals("")) {
                root = realDir;
            }
            root=root.replace('\\','/');
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map processStatusMap=new HashMap();

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(
            "select s.*,a.acnt_id as account_code from ISSUE s,PMS_ACNT a "+
            "where (s.account_id=2841 or s.account_id=3686 or s.account_id=4798) and s.account_id=a.rid ");
        while (rs.next()) {
            String accountCode = rs.getString("account_code");
            if(processStatusMap.get(accountCode)==null) {
                processStatusMap.put(accountCode,"processing");
                mkTempdir(root,accountCode);
                copyFiles(root,accountCode);
            }
            String attachment = rs.getString("ATTACHMENT");
            if (attachment != null) {
                attachment = attachment.replace('\\', '/');
                int pos = attachment.lastIndexOf("/");
                String attachmentid = attachment.substring(pos + 1);
                moveFile(root,accountCode,attachmentid);
            }
        }

        removeTempdir(root);
    }

    public static void main(String[] args) {
        LgImportOldData logic = new LgImportOldData();
        try {
            logic.getDbAccessor().newTx();
            logic.executeLogic();
            logic.getDbAccessor().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                logic.getDbAccessor().rollback();
            } catch (Exception ex2) {

            }
        } finally {
            try {
                logic.getDbAccessor().close();
            } catch (Exception ex2) {

            }
        }
    }
}
