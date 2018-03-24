package server.essp.attendance.overtime.logic;

import junit.framework.*;
import java.util.*;
import javax.sql.RowSet;
import java.sql.SQLException;
import com.wits.util.comDate;
import server.essp.attendance.overtime.viewbean.VbOverTimeReport;
import c2s.essp.attendance.Constant;

public class TestLgOverTimeReport extends TestCase {
    private LgOverTimeReport lgOverTimeReport = null;
    private final String ACNT_ID="BBBAS";
    private final String ACNT_ID_2="BBBAS2";
    private final String ACNT_RID = "998499";
    private final String ACNT_RID_2 = "998498";
    private final String ORG_ID="BBBIII";
    private final String ORG_NAME="ESSO";
    private final String LOGIN_ID="HK0608011";
    private final String USER_NAME="74s8GTy3k";
    private final String PT_LOGIN_ID="fsK0f60df1";
    private final String PT_USER_NAME="m4tsG74y3k";
    private final String ACNT_NAME="UUUUU";
    private final String ACNT_NAME_2="UUUUU2";
    private final String START_STR_1 = "2107-12-20 10:00:00";
    private final String START_STR_2 = "2107-12-21 10:00:00";
    private final String START_STR_3 = "2107-12-22 09:00:00";
    private final String FINISH_STR_1 = "2107-12-20 11:00:00";
    private final String FINISH_STR_2 = "2107-12-21 11:00:00";
    private final String FINISH_STR_3 = "2107-12-22 11:00:00";
    private final String UTIL_DATE_FORMAT = "yyyy-MM-dd hh:mm:ss";
    private final String SQL_DATE_FORMAT = "yyyy-MM-dd hh:mi:ss";
    private final String IN_DATE_STR = "2006-07-10 09:00:00";
    private final Date inDate = comDate.toDate(IN_DATE_STR, UTIL_DATE_FORMAT);
    public TestLgOverTimeReport(String name) {
        super(name);
    }

    protected void setUp() throws Exception {
        super.setUp();
        lgOverTimeReport = new LgOverTimeReport();
        lgOverTimeReport.getDbAccessor().followTx();
        initData();
    }

    protected void tearDown() throws Exception {
        lgOverTimeReport.getDbAccessor().rollback();
        lgOverTimeReport = null;
        super.tearDown();
    }

    public void testGetDataByHRAcnt() {
        Long acntRid = Long.valueOf(ACNT_RID);

        List list = lgOverTimeReport.getData(acntRid, null, null);
        assertNotNull("list not null",list);
        assertEquals("两笔加班记录和一笔汇总记录 list size is 3", 3, list.size());
        VbOverTimeReport vb = (VbOverTimeReport)list.get(0);
        assertEquals("sum ORG_NAME correct", ORG_NAME, vb.getUnitName());
        assertEquals("sum LOGIN_ID correct", LOGIN_ID, vb.getLoginId());
        assertEquals("sum USER_NAME correct", USER_NAME, vb.getName());
        assertNull("sum ACNT_NAME null", vb.getAcntName());
        assertEquals("sum IN_DATE correct", inDate, vb.getInDate());
        assertEquals("sum sumHours correct", Double.valueOf(15), vb.getSumHours());
        assertEquals("sum usableHours correct", Double.valueOf(3), vb.getUsableHours());
        vb = (VbOverTimeReport)list.get(2);
        assertNull("detail ORG_NAME null", vb.getUnitName());
        assertNull("detail LOGIN_ID null", vb.getLoginId());
        assertNull("detail USER_NAME null", vb.getName());
        assertEquals("detail ACNT_NAME correct", ACNT_NAME, vb.getAcntName());
        assertEquals("detail totalHours correct", Double.valueOf(10), vb.getTotalHours());
        assertEquals("detail usableHours correct", Double.valueOf(2), vb.getUsableHours());
    }

    public void testGetDataByHRAcntAndPeriod() {
        Long acntRid = Long.valueOf(ACNT_RID);
        Date beginDate = comDate.toDate(START_STR_1, UTIL_DATE_FORMAT);
        Date endDate = comDate.toDate(FINISH_STR_1, UTIL_DATE_FORMAT);

        List list = lgOverTimeReport.getData(acntRid, beginDate, endDate);
        assertNotNull("list not null",list);
        assertEquals("list size is 2", 2, list.size());
        VbOverTimeReport vb = (VbOverTimeReport)list.get(0);
        assertNull("ACNT_ID is null", vb.getAcntId());
        assertEquals("ORG_NAME correct", ORG_NAME, vb.getUnitName());
        assertEquals("LOGIN_ID correct", LOGIN_ID, vb.getLoginId());
        assertEquals("USER_NAME correct", USER_NAME, vb.getName());
        assertNull("sum ACNT_NAME null", vb.getAcntName());
        assertEquals("sum IN_DATE correct", inDate, vb.getInDate());
        assertEquals("sum sumHours correct", Double.valueOf(5), vb.getSumHours());
        assertEquals("sum usableHours correct", Double.valueOf(1), vb.getUsableHours());

    }
    public void testGetDataByHrAcntAndStartDate() {
        Long acntRid = Long.valueOf(ACNT_RID);
        Date beginDate = comDate.toDate(START_STR_2, UTIL_DATE_FORMAT);

        List list = lgOverTimeReport.getData(acntRid, beginDate, null);
        assertNotNull("list not null",list);
        assertEquals("list size is 3", 3, list.size());
    }

    public void testGetDataByHrAcntAndFinishDate() {
            Long acntRid = Long.valueOf(ACNT_RID);
            Date endDate = comDate.toDate(FINISH_STR_2, UTIL_DATE_FORMAT);

            List list = lgOverTimeReport.getData(acntRid, null, endDate);
            assertNotNull("list not null",list);
            assertEquals("list size is 2", 2, list.size());
    }


    private void initData() throws SQLException, RuntimeException {
        String sqlHr="insert into essp_hr_employee_main_t(name, indate) values('"+USER_NAME+"',to_date('"+IN_DATE_STR+"','"+SQL_DATE_FORMAT+"'))";
        lgOverTimeReport.getDbAccessor().executeUpdate(sqlHr);

        RowSet rs =  lgOverTimeReport.getDbAccessor().executeQuery("select t.code from essp_hr_employee_main_t t where t.name ='"+this.USER_NAME+"'");
        if(rs.next()) {
            String userId = rs.getString("code");
            String sqlScope = "insert into essp_hr_account_scope_t(account_id, scope_code, seq) values("+ACNT_RID+", '"+userId+"', 1)";
            lgOverTimeReport.getDbAccessor().executeUpdate(sqlScope);
            String sqlUser="insert into upms_loginuser(login_id,user_id) values('"+LOGIN_ID+"','"+userId+"')";
            lgOverTimeReport.getDbAccessor().executeUpdate(sqlUser);
        }

        String sqlHrPt="insert into essp_hr_employee_main_t(name, indate, types) values('"+PT_USER_NAME+"',to_date('"+IN_DATE_STR+"','"+SQL_DATE_FORMAT+"'),'1')";
        lgOverTimeReport.getDbAccessor().executeUpdate(sqlHrPt);

        RowSet rsPt =  lgOverTimeReport.getDbAccessor().executeQuery("select t.code from essp_hr_employee_main_t t where t.name ='"+this.PT_USER_NAME+"'");
        if(rsPt.next()) {
            String userId = rsPt.getString("code");
            String sqlScope = "insert into essp_hr_account_scope_t(account_id, scope_code, seq) values("+ACNT_RID+", '"+userId+"', 1)";
            lgOverTimeReport.getDbAccessor().executeUpdate(sqlScope);
            String sqlUser="insert into upms_loginuser(login_id,user_id) values('"+PT_LOGIN_ID+"','"+userId+"')";
            lgOverTimeReport.getDbAccessor().executeUpdate(sqlUser);
        }


        String sqlOrg="insert into essp_upms_unit(unit_id,name) values('"+ORG_ID+"','"+ORG_NAME+"')";
        lgOverTimeReport.getDbAccessor().executeUpdate(sqlOrg);

        String sqlAcnt="insert into pms_acnt(rid,acnt_id,acnt_organization,rst,acnt_name) values("+ACNT_RID+",'"+ACNT_ID+"','"+ORG_ID+"','N','"+ACNT_NAME+"')";
        lgOverTimeReport.getDbAccessor().executeUpdate(sqlAcnt);

        String sqlAcnt2="insert into pms_acnt(rid,acnt_id,acnt_organization,rst,acnt_name) values("+ACNT_RID_2+",'"+ACNT_ID_2+"','"+ORG_ID+"','N','"+ACNT_NAME_2+"')";
        lgOverTimeReport.getDbAccessor().executeUpdate(sqlAcnt2);

        String sqlTc="insert into tc_overtime(rid,acnt_Rid,total_hours,login_id,actual_datetime_start,actual_datetime_finish,status) values(56585,"+ACNT_RID+",5,'"+LOGIN_ID+"',to_date('"+START_STR_1+"', '"+SQL_DATE_FORMAT+"'),to_date('"+FINISH_STR_1+"', '"+SQL_DATE_FORMAT+"'), '"+Constant.STATUS_FINISHED+"')";
        lgOverTimeReport.getDbAccessor().executeUpdate(sqlTc);
        String sqlDetail="insert into tc_overtime_detail(rid,overtime_id,hours,payed_hours,shift_hours,overtime_day) values(35678,56585,5,2,2,to_date('"+START_STR_1+"','"+SQL_DATE_FORMAT+"'))";
        lgOverTimeReport.getDbAccessor().executeUpdate(sqlDetail);

        String sqlTc2="insert into tc_overtime(rid,acnt_Rid,total_hours,login_id,actual_datetime_start,actual_datetime_finish,status) values(56586,"+ACNT_RID+",5,'"+LOGIN_ID+"',to_date('"+START_STR_2+"', '"+SQL_DATE_FORMAT+"'),to_date('"+FINISH_STR_2+"', '"+SQL_DATE_FORMAT+"'), '"+Constant.STATUS_FINISHED+"')";
        lgOverTimeReport.getDbAccessor().executeUpdate(sqlTc2);
        String sqlDetail2="insert into tc_overtime_detail(rid,overtime_id,hours,payed_hours,shift_hours,overtime_day) values(35679,56586,5,2,2,to_date('"+START_STR_2+"','"+SQL_DATE_FORMAT+"'))";
        lgOverTimeReport.getDbAccessor().executeUpdate(sqlDetail2);

        String sqlTc3="insert into tc_overtime(rid,acnt_Rid,total_hours,login_id,actual_datetime_start,actual_datetime_finish,status) values(56587,"+ACNT_RID_2+",5,'"+LOGIN_ID+"',to_date('"+START_STR_3+"', '"+SQL_DATE_FORMAT+"'),to_date('"+FINISH_STR_3+"', '"+SQL_DATE_FORMAT+"'), '"+Constant.STATUS_FINISHED+"')";
        lgOverTimeReport.getDbAccessor().executeUpdate(sqlTc3);
        String sqlDetail3="insert into tc_overtime_detail(rid,overtime_id,hours,payed_hours,shift_hours,overtime_day) values(35671,56587,5,2,2,to_date('"+START_STR_3+"','"+SQL_DATE_FORMAT+"'))";
        lgOverTimeReport.getDbAccessor().executeUpdate(sqlDetail3);

        String sqlTc4="insert into tc_overtime(rid,acnt_Rid,total_hours,login_id,actual_datetime_start,actual_datetime_finish,status) values(56087,"+ACNT_RID_2+",5,'"+PT_LOGIN_ID+"',to_date('"+START_STR_3+"', '"+SQL_DATE_FORMAT+"'),to_date('"+FINISH_STR_3+"', '"+SQL_DATE_FORMAT+"'), '"+Constant.STATUS_FINISHED+"')";
        lgOverTimeReport.getDbAccessor().executeUpdate(sqlTc4);
        String sqlDetail4="insert into tc_overtime_detail(rid,overtime_id,hours,payed_hours,shift_hours,overtime_day) values(35609,56087,5,2,2,to_date('"+START_STR_3+"','"+SQL_DATE_FORMAT+"'))";
        lgOverTimeReport.getDbAccessor().executeUpdate(sqlDetail4);

    }
}
