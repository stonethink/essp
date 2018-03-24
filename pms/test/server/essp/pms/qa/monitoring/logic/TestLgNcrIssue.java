package server.essp.pms.qa.monitoring.logic;

import junit.framework.*;
import java.util.*;
import javax.sql.RowSet;
import java.sql.SQLException;
import com.wits.util.comDate;
import c2s.essp.pms.qa.DtoNcrIssue;

public class TestLgNcrIssue extends TestCase {
    private LgNcrIssue lgNcrIssue = null;

    private final static Long acntRid = new Long(7513648);
    private final static String userLoginId_1 = "d4fl76am 1";
    private final static String userLoginId_2 = "d4fl76am 2";
    private final static String userChineseName_1 = "d4sdffl76am 1";
    private final static String userChineseName_2 = "d4fl7asd6am 2";
    private final static String issueId_1 = "s5pr7u testIssueId_1";
    private final static String issueId_2 = "s5pr7u testIssueId_2";
    private final static String issueId_3 = "s5pr7u testIssueId_3";
    private final static String issueId_4 = "s5pr7u testIssueId_4";
    private final static String issueName_1 = "alj2 fnv a5lw3pru testIssueName_1";
    private final static String issueName_2 = "alj2 fnv a5lw3pru testIssueName_2";
    private final static String issueName_3 = "alj2 fnv a5lw3pru testIssueName_3";
    private final static String issueName_4 = "alj2 fnv a5lw3pru testIssueName_4";

    private final static String fillDateStr_1 = "2122-01-22";
    private final static String fillDateStr_2 = "2122-02-22";
    private final static String fillDateStr_3 = "2122-03-22";
    private final static String fillDateStr_4 = "2122-04-22";
    private final static String dueDateStr_1 = "2122-01-25";
    private final static String dueDateStr_2 = "2122-02-25";
    private final static String dueDateStr_3 = "2122-03-25";
    private final static String dueDateStr_4 = "2122-04-25";
    private final static String finishDateStr_1 = "2122-01-27";
    private final static String finishDateStr_2 = "2122-02-27";
    private final static String finishDateStr_3 = "2122-03-27";
    private final static String finishDateStr_4 = "2122-04-27";

    private final static Date fillDate_1 = comDate.toDate(fillDateStr_1);
    private final static Date fillDate_2 = comDate.toDate(fillDateStr_2);
    private final static Date fillDate_3 = comDate.toDate(fillDateStr_3);
    private final static Date fillDate_4 = comDate.toDate(fillDateStr_4);
    private final static Date dueDate_1 = comDate.toDate(dueDateStr_1);
    private final static Date dueDate_2 = comDate.toDate(dueDateStr_2);
    private final static Date dueDate_3 = comDate.toDate(dueDateStr_3);
    private final static Date dueDate_4 = comDate.toDate(dueDateStr_4);
    private final static Date finishDate_1 = comDate.toDate(finishDateStr_1);
    private final static Date finishDate_2 = comDate.toDate(finishDateStr_2);
    private final static Date finishDate_3 = comDate.toDate(finishDateStr_3);
    private final static Date finishDate_4 = comDate.toDate(finishDateStr_4);



    public TestLgNcrIssue(String name) {
        super(name);
    }

    protected void setUp() throws Exception {
        super.setUp();
        lgNcrIssue = new LgNcrIssue();
        lgNcrIssue.getDbAccessor().followTx();
        initData();
    }

    protected void tearDown() throws Exception {
        lgNcrIssue.getDbAccessor().endTxRollback();
        lgNcrIssue = null;
        super.tearDown();
    }

    public void testListNormal() {
        List lst = lgNcrIssue.list(acntRid);
        assertNotNull("return list is not null", lst);
        assertEquals("return list size is 4", 4, lst.size());
        DtoNcrIssue dto = (DtoNcrIssue) lst.get(0);
        assertEquals("issue_1 id", issueId_1, dto.getIssueId());
        assertEquals("issue_1 name", issueName_1, dto.getIssueName());
        assertEquals("issue_1 principal", userChineseName_1 + "(" + userLoginId_1 + ")", dto.getPrincipal());
        assertEquals("issue_1 priority", "NORMAL", dto.getPriority());
        assertEquals("issue_1 fillDate",fillDate_1, dto.getFillDate());
        assertEquals("issue_1 dueDate",dueDate_1, dto.getDueDate());
        assertEquals("issue_1 finishDate",finishDate_1, dto.getFinishDate());
        assertEquals("issue_1 priority", "Received", dto.getIssueStatus());

        dto = (DtoNcrIssue) lst.get(1);
        assertEquals("issue_2 id", issueId_2, dto.getIssueId());
        assertEquals("issue_2 name", issueName_2, dto.getIssueName());
        assertEquals("issue_2 principal", userChineseName_1 + "(" + userLoginId_1 + ")", dto.getPrincipal());
        assertEquals("issue_2 priority", "NORMAL", dto.getPriority());
        assertEquals("issue_2 fillDate",fillDate_2, dto.getFillDate());
        assertEquals("issue_2 dueDate",dueDate_2, dto.getDueDate());
        assertEquals("issue_2 finishDate",finishDate_2, dto.getFinishDate());
        assertEquals("issue_2 priority", "Rejected", dto.getIssueStatus());

        dto = (DtoNcrIssue) lst.get(2);
        assertEquals("issue_3 id", issueId_3, dto.getIssueId());
        assertEquals("issue_3 name", issueName_3, dto.getIssueName());
        assertEquals("issue_3 principal", userChineseName_2 + "(" + userLoginId_2 + ")", dto.getPrincipal());
        assertEquals("issue_3 priority", "URGED", dto.getPriority());
        assertEquals("issue_3 fillDate",fillDate_3, dto.getFillDate());
        assertEquals("issue_3 dueDate",dueDate_3, dto.getDueDate());
        assertEquals("issue_3 finishDate",finishDate_3, dto.getFinishDate());
        assertEquals("issue_3 priority", "Finished", dto.getIssueStatus());

        dto = (DtoNcrIssue) lst.get(3);
        assertEquals("issue_4 id", issueId_4, dto.getIssueId());
        assertEquals("issue_4 name", issueName_4, dto.getIssueName());
        assertEquals("issue_4 principal", userChineseName_2 + "(" + userLoginId_2 + ")", dto.getPrincipal());
        assertEquals("issue_4 priority", "NORMAL", dto.getPriority());
        assertEquals("issue_4 fillDate",fillDate_4, dto.getFillDate());
        assertEquals("issue_4 dueDate",dueDate_4, dto.getDueDate());
        assertEquals("issue_4 finishDate",finishDate_4, dto.getFinishDate());
        assertEquals("issue_4 priority", "Closed", dto.getIssueStatus());
    }

    public void testListNoRecord() {
        List lst = lgNcrIssue.list(new Long(8884137));
        assertNotNull("noRecord list is not null", lst);
        assertEquals("noRecord list size is 0", 0, lst.size());
    }

    private void initData() throws SQLException {
        String sqlHr="insert into essp_hr_employee_main_t(name, chinese_name) values('"+userChineseName_1+"','"+userChineseName_1+"')";
        lgNcrIssue.getDbAccessor().executeUpdate(sqlHr);

        RowSet rs =  lgNcrIssue.getDbAccessor().executeQuery("select t.code from essp_hr_employee_main_t t where t.name ='"+this.userChineseName_1+"'");
        if(rs.next()) {
            String userId = rs.getString("code");
            String sqlUser="insert into upms_loginuser(login_id,user_id) values('"+userLoginId_1+"','"+userId+"')";
            lgNcrIssue.getDbAccessor().executeUpdate(sqlUser);
        }

        sqlHr="insert into essp_hr_employee_main_t(name, chinese_name) values('"+userChineseName_2+"','"+userChineseName_2+"')";
        lgNcrIssue.getDbAccessor().executeUpdate(sqlHr);

        rs =  lgNcrIssue.getDbAccessor().executeQuery("select code from essp_hr_employee_main_t t where t.name ='"+this.userChineseName_2+"'");
        if(rs.next()) {
            String userId = rs.getString("code");
            String sqlUser="insert into upms_loginuser(login_id,user_id) values('"+userLoginId_2+"','"+userId+"')";
            lgNcrIssue.getDbAccessor().executeUpdate(sqlUser);
        }


        String sqlIssue_1 = "insert into issue(rid, account_id, issue_id, issue_name, principal, priority, filledate, duedate, issue_status, type_name) "
                       + "values(9944531, " + acntRid + ", '" + issueId_1 + "', '" + issueName_1 + "', '" + userLoginId_1 + "', 'NORMAL', to_date('" + fillDateStr_1 + "', 'yyyy-MM-dd'), to_date('" + dueDateStr_1 + "', 'yyyy-MM-dd'), 'Received', 'NCR')";
        String sqlConclusion_1 = "insert into issue_conclusion(rid, finished_date) values(9944531, to_date('" + finishDateStr_1 + "', 'yyyy-MM-dd'))";
        lgNcrIssue.getDbAccessor().executeUpdate(sqlIssue_1);
        lgNcrIssue.getDbAccessor().executeUpdate(sqlConclusion_1);

        String sqlIssue_2 = "insert into issue(rid, account_id, issue_id, issue_name, principal, priority, filledate, duedate, issue_status, type_name) "
                       + "values(9944532, " + acntRid + ", '" + issueId_2 + "', '" + issueName_2 + "', '" + userLoginId_1 + "', 'NORMAL', to_date('" + fillDateStr_2 + "', 'yyyy-MM-dd'), to_date('" + dueDateStr_2 + "', 'yyyy-MM-dd'), 'Rejected', 'NCR')";
        String sqlConclusion_2 = "insert into issue_conclusion(rid, finished_date) values(9944532, to_date('" + finishDateStr_2 + "', 'yyyy-MM-dd'))";
        lgNcrIssue.getDbAccessor().executeUpdate(sqlIssue_2);
        lgNcrIssue.getDbAccessor().executeUpdate(sqlConclusion_2);

        String sqlIssue_3 = "insert into issue(rid, account_id, issue_id, issue_name, principal, priority, filledate, duedate, issue_status, type_name) "
                       + "values(9944533, " + acntRid + ", '" + issueId_3 + "', '" + issueName_3 + "', '" + userLoginId_2 + "', 'URGED', to_date('" + fillDateStr_3 + "', 'yyyy-MM-dd'), to_date('" + dueDateStr_3 + "', 'yyyy-MM-dd'), 'Finished', 'NCR')";
        String sqlConclusion_3 = "insert into issue_conclusion(rid, finished_date) values(9944533, to_date('" + finishDateStr_3 + "', 'yyyy-MM-dd'))";
        lgNcrIssue.getDbAccessor().executeUpdate(sqlIssue_3);
        lgNcrIssue.getDbAccessor().executeUpdate(sqlConclusion_3);

        String sqlIssue_4 = "insert into issue(rid, account_id, issue_id, issue_name, principal, priority, filledate, duedate, issue_status, type_name) "
                       + "values(9944534, " + acntRid + ", '" + issueId_4 + "', '" + issueName_4 + "', '" + userLoginId_2 + "', 'NORMAL', to_date('" + fillDateStr_4 + "', 'yyyy-MM-dd'), to_date('" + dueDateStr_4 + "', 'yyyy-MM-dd'), 'Closed', 'NCR')";
        String sqlConclusion_4 = "insert into issue_conclusion(rid, finished_date) values(9944534, to_date('" + finishDateStr_4 + "', 'yyyy-MM-dd'))";
        lgNcrIssue.getDbAccessor().executeUpdate(sqlIssue_4);
        lgNcrIssue.getDbAccessor().executeUpdate(sqlConclusion_4);

    }

}
