package server.essp.issue.common.logic;

import server.framework.hibernate.HBComAccess;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import c2s.essp.common.account.IDtoAccount;
import java.text.DecimalFormat;
import server.framework.common.BusinessException;
import itf.account.AccountFactory;
import itf.account.IAccountUtil;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import net.sf.hibernate.Session;
import net.sf.hibernate.Query;
import server.framework.taglib.util.SelectOptionImpl;
import db.essp.issue.Issue;
import server.framework.common.Constant;
import itf.issue.IIssueUtil;
import c2s.essp.common.issue.IDtoIssue;
import db.essp.pms.Account;
import c2s.dto.DtoUtil;
import c2s.essp.pms.account.DtoPmsAcnt;

public class LgIssueBase extends AbstractISSUELogic implements IIssueUtil{
    public LgIssueBase() {
    }

    private boolean isExistTable(Connection conn, String tableName) throws
        Exception {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(
            "select count(*) as RECORDCOUNT from tab where TNAME='" + tableName +
            "'");
        rs.next();
        int recordCount = rs.getInt("RECORDCOUNT");
        rs.close();
        stmt.close();
        if (recordCount == 0) {
            return false;
        }
        return true;
    }

    private boolean isExistRecord(Connection conn, long accountRid) throws
        Exception {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(
            "select count(*) as RECORDCOUNT from ISSUE_ID_SEQ where ACCOUNT_ID=" +
            accountRid);
        rs.next();
        int recordCount = rs.getInt("RECORDCOUNT");
        rs.close();
        stmt.close();
        if (recordCount == 0) {
            return false;
        }
        return true;
    }

    private IDtoAccount getAccount(long accountRid) {
        IAccountUtil accountUtil = AccountFactory.create();
        IDtoAccount account = accountUtil.getAccountByRID(new Long(accountRid));
        return account;
    }

    private String formatSeq(String issueTypeName, String accountCode, long seq) {
        DecimalFormat df = new DecimalFormat( "0000");
        String seqStr = issueTypeName + "." + accountCode+"."+df.format(seq);
        return seqStr;
    }

    /**
     * 根据account rid自动产生一个issue id
     * @param issueTypeName String
     * @param accountRid long
     * @return String
     */
    public String getIssueId(String issueTypeName, long accountRid) {
        HBComAccess dbAccessor = new HBComAccess();
        String issueId = null;
        try {
            dbAccessor.newTx();
            IDtoAccount account = getAccount(accountRid);
            Connection conn = dbAccessor.getConnect();

            /**
             * 检查ISSUE_ID_SEQ表是否存在，如果不存在ISSUE_ID_SEQ表，则创建它
             */
            if (!isExistTable(conn, "ISSUE_ID_SEQ")) {
                Statement stmt = conn.createStatement();
                stmt.executeUpdate(
                    "create table ISSUE_ID_SEQ ( ACCOUNT_ID NUMBER(8) Primary key,SEQ NUMBER(8))");
            }

            /**
             * 检查记录是否存在，如果不存在，则创建它
             */
            if (!isExistRecord(conn, accountRid)) {
                Statement stmt = conn.createStatement();
                stmt.executeUpdate(
                    "insert into ISSUE_ID_SEQ ( ACCOUNT_ID ,SEQ ) values (" +
                    accountRid + ",1) ");
                issueId = formatSeq(issueTypeName, account.getId(), 1);
            } else {
                /**
                 * 如果记录存在，则取出Sequence，加1后更新到数据库中
                 */
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(
                    "select SEQ from ISSUE_ID_SEQ where ACCOUNT_ID=" +
                    accountRid);
                rs.next();
                long seq = rs.getLong("SEQ") + 1;
                rs.close();
                stmt.close();
                stmt = conn.createStatement();
                stmt.executeUpdate("update ISSUE_ID_SEQ set SEQ=" + seq +
                                   " where ACCOUNT_ID=" + accountRid);
                stmt.close();
                issueId = formatSeq(issueTypeName, account.getId(), seq);
            }
            dbAccessor.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                dbAccessor.rollback();
            } catch (Exception e) {
            }
            throw new BusinessException(ex);
        } finally {
            try {
                dbAccessor.close();
            } catch (Exception e) {
            }
        }
        return issueId;
    }

    /**
     * 检查issue id是否重复
     * @param accountRid long
     * @param issueId String
     * @return boolean
     */
    public boolean checkExistId(long accountRid,String issueId) {
        try {
            Connection conn = this.getDbAccessor().getConnect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(
                "select count(*) as RECORDCOUNT from ISSUE where ACCOUNT_ID=" +
                accountRid+" and ISSUE_ID='"+issueId+"'");
            rs.next();
            int recordCount = rs.getInt("RECORDCOUNT");
            rs.close();
            stmt.close();
            if (recordCount == 0) {
                return false;
            }
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new BusinessException(ex);
        }
    }

    public List getDuplationIssues(String accountId,String issueRid) {
        List results=new ArrayList();
        if(accountId!=null && !accountId.trim().equals("")) {
            try {
                Session session = this.getDbAccessor().getSession();
                String queryStr = "from Issue s where s.rst='"+Constant.RST_NORMAL+"' and s.accountId=" + accountId;
                if (issueRid != null && !issueRid.trim().equals("")) {
                    queryStr = queryStr + " and s.rid<>" + issueRid;
                }
                Query q = session.createQuery(queryStr);
                List dbResults=q.list();
                if(dbResults!=null) {
                    results.addAll(dbResults);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return results;
    }

    public List getDuplationIssueOptions(String accountId,String issueRid) {
        List results=getDuplationIssues(accountId,issueRid);
        List options=new ArrayList();
        SelectOptionImpl defaultOption = new SelectOptionImpl(
            "  ----  Please Select  ----  ", "");
        options.add(defaultOption);

        for(int i=0;i<results.size();i++) {
            Issue issue=(Issue)results.get(i);
            SelectOptionImpl option=new SelectOptionImpl(issue.getIssueId(),issue.getRid().toString());
            options.add(option);
        }
        return options;
    }

    public static void main(String[] args) throws Exception {
        LgIssueBase logic = new LgIssueBase();
        logic.getIssueId("PPR", 1012);
    }

    public String addIssue(IDtoIssue issue, boolean isMail) {
        return "";
    }

    public String updateIssue(IDtoIssue issue, boolean isMail) {
        return "";
    }

    public IDtoIssue getIssue(String issueId) {
        return null;
    }

    public String generateIssueId(String issueTypeName, Long accountRid,  IDtoAccount account) {
        HBComAccess dbAccessor = new HBComAccess();
       String issueId = null;
       try {
           dbAccessor.newTx();
//           IDtoAccount account = new DtoPmsAcnt();
//           Session s = this.getDbAccessor().getSession();
//           Object obj  =  (Account)s.load(Account.class, accountRid);
//           DtoUtil.copyProperties(account, obj);
           Connection conn = dbAccessor.getConnect();


           /**
            * 检查ISSUE_ID_SEQ表是否存在，如果不存在ISSUE_ID_SEQ表，则创建它
            */
           if (!isExistTable(conn, "ISSUE_ID_SEQ")) {
               Statement stmt = conn.createStatement();
               stmt.executeUpdate(
                   "create table ISSUE_ID_SEQ ( ACCOUNT_ID NUMBER(8) Primary key,SEQ NUMBER(8))");
           }

           /**
            * 检查记录是否存在，如果不存在，则创建它
            */
           if (!isExistRecord(conn, accountRid.longValue())) {
               Statement stmt = conn.createStatement();
               stmt.executeUpdate(
                   "insert into ISSUE_ID_SEQ ( ACCOUNT_ID ,SEQ ) values (" +
                   accountRid + ",1) ");
               issueId = formatSeq(issueTypeName, account.getId(), 1);
           } else {
               /**
                * 如果记录存在，则取出Sequence，加1后更新到数据库中
                */
               Statement stmt = conn.createStatement();
               ResultSet rs = stmt.executeQuery(
                   "select SEQ from ISSUE_ID_SEQ where ACCOUNT_ID=" +
                   accountRid);
               rs.next();
               long seq = rs.getLong("SEQ") + 1;
               rs.close();
               stmt.close();
               stmt = conn.createStatement();
               stmt.executeUpdate("update ISSUE_ID_SEQ set SEQ=" + seq +
                                  " where ACCOUNT_ID=" + accountRid);
               stmt.close();
               issueId = formatSeq(issueTypeName, account.getId(), seq);
           }
           dbAccessor.commit();
       } catch (Exception ex) {
           ex.printStackTrace();
           try {
               dbAccessor.rollback();
           } catch (Exception e) {
           }
           throw new BusinessException(ex);
       } finally {
           try {
               dbAccessor.close();
           } catch (Exception e) {
           }
       }
       return issueId;

    }
}
