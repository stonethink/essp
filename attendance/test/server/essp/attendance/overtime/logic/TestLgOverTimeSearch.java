package server.essp.attendance.overtime.logic;

import java.sql.*;
import java.util.*;

import javax.sql.*;

import junit.framework.*;
import server.essp.attendance.overtime.form.*;
import server.essp.attendance.overtime.viewbean.*;
import c2s.essp.attendance.Constant;

public class TestLgOverTimeSearch extends TestCase {
    private LgOverTimeSearch lgOverTimeSearch = null;
    private final String ACNT_ID="BBBAS";
    private final String ACNT_RID = "998499";
    private final String ORG_ID="BBBIII";
    private final String ORG_NAME="ESSO";
    private final String LOGIN_ID="HK0608011";
    private final String USER_NAME="74s8GTy3k";
    private final String ACNT_NAME="UUUUU";
    private final String SQL_DATE_FORMAT = "yyyy-MM-dd hh:mi";
    private final String BEGIN_DATE_STR = "2107-01-20 09:00";
    private final String END_DATE_STR = "2107-01-20 11:00";



    public TestLgOverTimeSearch(String name) {
        super(name);
    }

    protected void setUp() throws Exception {
        super.setUp();
        lgOverTimeSearch = new LgOverTimeSearch();
        lgOverTimeSearch.getDbAccessor().followTx();

        initData();

    }

    protected void tearDown() throws Exception {
        lgOverTimeSearch.getDbAccessor().endTxRollback();
        lgOverTimeSearch = null;
        super.tearDown();
    }

    public void testQueryByConditionAcnt() {
        AfOverTimeSearch af = new AfOverTimeSearch();
        af.setAccount_id(ACNT_RID);
        List list = lgOverTimeSearch.queryByCondition(af);
        assertNotNull("Query by acnt list not null",list);
        assertEquals("Query by acnt list size", 1,list.size());
        VbOverTimeSearch vb = (VbOverTimeSearch)list.get(0);
        assertEquals("Query by acnt Org Name", ORG_NAME, vb.getOrgName());
        assertEquals("Query by acnt Acnt Name",ACNT_NAME,vb.getAcntName());
        assertEquals("Query by acnt Total Hours",Double.valueOf(5),vb.getTotalHours());
        assertEquals("Query by acnt Shift Hours",Double.valueOf(2),vb.getShiftHours());
        assertEquals("Query by acnt Payed Hours",Double.valueOf(2),vb.getPayedHours());
        assertEquals("Query by acnt Usable Hours",Double.valueOf(1),vb.getUsableHours());
    }

    public void testQueryByConditionName() {
        AfOverTimeSearch af = new AfOverTimeSearch();
        af.setEmpName("GTy3");
        List list = lgOverTimeSearch.queryByCondition(af);
        assertNotNull("Query by name list not null",list);
        assertEquals("Query by name list size", 1,list.size());
        VbOverTimeSearch vb = (VbOverTimeSearch)list.get(0);
        assertEquals("Query by name Org Name", ORG_NAME, vb.getOrgName());
        assertEquals("Query by name Acnt Name",ACNT_NAME,vb.getAcntName());
        assertEquals("Query by name Total Hours",Double.valueOf(5),vb.getTotalHours());
        assertEquals("Query by name Shift Hours",Double.valueOf(2),vb.getShiftHours());
        assertEquals("Query by name Payed Hours",Double.valueOf(2),vb.getPayedHours());
        assertEquals("Query by name Usable Hours",Double.valueOf(1),vb.getUsableHours());
    }

    public void testQueryByConditionOrg() {
        AfOverTimeSearch af = new AfOverTimeSearch();
        af.setDepart_code(ORG_ID);
        List list = lgOverTimeSearch.queryByCondition(af);
        assertNotNull("Query by Org list not null",list);
        assertEquals("Query by Org list size", 1,list.size());
        VbOverTimeSearch vb = (VbOverTimeSearch)list.get(0);
        assertEquals("Query by Org Org Name", ORG_NAME, vb.getOrgName());
        assertEquals("Query by Org Acnt Name",ACNT_NAME,vb.getAcntName());
        assertEquals("Query by Org Total Hours",Double.valueOf(5),vb.getTotalHours());
        assertEquals("Query by Org Shift Hours",Double.valueOf(2),vb.getShiftHours());
        assertEquals("Query by Org Payed Hours",Double.valueOf(2),vb.getPayedHours());
        assertEquals("Query by Org Usable Hours",Double.valueOf(1),vb.getUsableHours());
    }

    public void testQueryByConditionBeginDate() {
        AfOverTimeSearch af = new AfOverTimeSearch();
        af.setBeginDate(BEGIN_DATE_STR);
        List list = lgOverTimeSearch.queryByCondition(af);
        assertNotNull("Query by date and name list not null",list);
        assertEquals("Query by Org list size", 1,list.size());
        VbOverTimeSearch vb = (VbOverTimeSearch)list.get(0);
        assertEquals("Query by date and name Org Name", ORG_NAME, vb.getOrgName());
        assertEquals("Query by date and name Acnt Name",ACNT_NAME,vb.getAcntName());
        assertEquals("Query by date and name Total Hours",Double.valueOf(5),vb.getTotalHours());
        assertEquals("Query by date and name Shift Hours",Double.valueOf(2),vb.getShiftHours());
        assertEquals("Query by date and name Payed Hours",Double.valueOf(2),vb.getPayedHours());
        assertEquals("Query by date and sname Usable Hours",Double.valueOf(1),vb.getUsableHours());
    }

    public void testQueryByConditionEndDate() {
       AfOverTimeSearch af = new AfOverTimeSearch();
       af.setEndDate(END_DATE_STR);
       List list = lgOverTimeSearch.queryByCondition(af);
       assertNotNull("Query by date and name list not null",list);
       assertTrue("Query by Org list size", 1 <= list.size());
   }

   public void testQueryByConditionDate() {
     AfOverTimeSearch af = new AfOverTimeSearch();
     af.setBeginDate(BEGIN_DATE_STR);
     af.setEndDate(END_DATE_STR);
     List list = lgOverTimeSearch.queryByCondition(af);
     assertNotNull("Query by date and name list not null",list);
     assertEquals("Query by Org list size", 1,list.size());
     VbOverTimeSearch vb = (VbOverTimeSearch)list.get(0);
     assertEquals("Query by date and name Org Name", ORG_NAME, vb.getOrgName());
     assertEquals("Query by date and name Acnt Name",ACNT_NAME,vb.getAcntName());
     assertEquals("Query by date and name Total Hours",Double.valueOf(5),vb.getTotalHours());
     assertEquals("Query by date and name Shift Hours",Double.valueOf(2),vb.getShiftHours());
     assertEquals("Query by date and name Payed Hours",Double.valueOf(2),vb.getPayedHours());
     assertEquals("Query by date and sname Usable Hours",Double.valueOf(1),vb.getUsableHours());
 }


    public void testQueryByConditionAll() {
        AfOverTimeSearch af = new AfOverTimeSearch();
        af.setDepart_code(ORG_ID);
        af.setAccount_id(ACNT_RID);
        af.setBeginDate(BEGIN_DATE_STR);
        af.setEndDate(END_DATE_STR);
        af.setEmpName("GTy3");
        List list = lgOverTimeSearch.queryByCondition(af);
        assertNotNull("Query by all list not null",list);
        assertEquals("Query by Org list size", 1,list.size());
        VbOverTimeSearch vb = (VbOverTimeSearch)list.get(0);
        assertEquals("Query by all Org Name", ORG_NAME, vb.getOrgName());
        assertEquals("Query by all Acnt Name",ACNT_NAME,vb.getAcntName());
        assertEquals("Query by all Total Hours",Double.valueOf(5),vb.getTotalHours());
        assertEquals("Query by all Shift Hours",Double.valueOf(2),vb.getShiftHours());
        assertEquals("Query by all Payed Hours",Double.valueOf(2),vb.getPayedHours());
        assertEquals("Query by all Usable Hours",Double.valueOf(1),vb.getUsableHours());
    }





    private void initData() throws SQLException, RuntimeException {
        String sqlHr="insert into essp_hr_employee_main_t(name) values('"+USER_NAME+"')";
        lgOverTimeSearch.getDbAccessor().executeUpdate(sqlHr);

        RowSet rs =  lgOverTimeSearch.getDbAccessor().executeQuery("select t.code from essp_hr_employee_main_t t where t.name ='"+this.USER_NAME+"'");
        if(rs.next()) {
            String userId = rs.getString("code");
            String sqlUser="insert into upms_loginuser(login_id,user_id) values('"+LOGIN_ID+"','"+userId+"')";
            lgOverTimeSearch.getDbAccessor().executeUpdate(sqlUser);
        }


        String sqlOrg="insert into essp_upms_unit(unit_id,name) values('"+ORG_ID+"','"+ORG_NAME+"')";
        lgOverTimeSearch.getDbAccessor().executeUpdate(sqlOrg);

        String sql="insert into pms_acnt(rid,acnt_id,acnt_organization,rst,acnt_name) values("+ACNT_RID+",'"+ACNT_ID+"','"+ORG_ID+"','N','"+ACNT_NAME+"')";
        lgOverTimeSearch.getDbAccessor().executeUpdate(sql);

        String sqlTc="insert into tc_overtime(rid,acnt_Rid,total_hours,login_id,actual_datetime_start,actual_datetime_finish,status) "
                     + "values(56585,"+ACNT_RID+",5,'"+LOGIN_ID+"', to_date('" + BEGIN_DATE_STR + "','" + SQL_DATE_FORMAT + "'), "
                     + "to_date('" + END_DATE_STR + "','" + SQL_DATE_FORMAT + "'), '"+Constant.STATUS_FINISHED+"')";
        lgOverTimeSearch.getDbAccessor().executeUpdate(sqlTc);
        String sqlDetail="insert into tc_overtime_detail(rid,overtime_id,hours,payed_hours,shift_hours,overtime_day) values(35678,56585,5,2,2,to_date('" + BEGIN_DATE_STR + "','" + SQL_DATE_FORMAT + "'))";
        lgOverTimeSearch.getDbAccessor().executeUpdate(sqlDetail);
    }


}
