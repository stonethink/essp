package server.essp.attendance.overtime.logic;

import java.util.*;

import c2s.essp.attendance.*;
import c2s.essp.common.user.*;
import com.wits.util.*;
import db.essp.attendance.*;
import junit.framework.*;
import org.apache.poi.hssf.usermodel.*;
import server.essp.attendance.overtime.viewbean.*;
import javax.sql.RowSet;
import java.sql.SQLException;

public class TestLgOverTimeImport extends TestCase {
    private LgOverTimeImport lgOverTimeImport = null;
    private Long acntRid = new Long(8971462);
    private String acntId = "plcaIdvst";
    private Date beginDate = comDate.toDate("2007-04-02 10:00:00","yyyy-MM-dd hh:mm:ss");

    private Date endDate = comDate.toDate("2007-04-02 15:00:00","yyyy-MM-dd hh:mm:ss");
    private String testLoginId_1 = "t5tda80fteq";
    private String testLoginId_2 = "befdsd3etew";
    private String testLoginId_3 = "met42te23ke";
    private String desc_2 = "Essp pp";
    private ThreadVariant thread=ThreadVariant.getInstance();
    private static final String USER_NAME = "UOADA7777D";
    private static final String USER_NAME_2 = "UODDAD424AD";

    public TestLgOverTimeImport(String name) {
        super(name);
    }

    protected void setUp() throws Exception {
        super.setUp();
        lgOverTimeImport = new LgOverTimeImport();
        lgOverTimeImport.getDbAccessor().followTx();
        DtoUser dto = new DtoUser();
        dto.setUserLoginId(testLoginId_3);
        thread.put(DtoUser.SESSION_USER, dto);
        initData();
    }

    protected void tearDown() throws Exception {
        lgOverTimeImport.getDbAccessor().endTxRollback();
        lgOverTimeImport = null;
        super.tearDown();
    }

    public void testImportExcelNormal() throws Exception {

        //test excel data
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet();
        sheet.createRow(0);
        sheet.createRow(1);
        HSSFRow row = sheet.createRow(2);
        HSSFCell cellAcntId = row.createCell((short) 0);
        HSSFCell cellLoginId = row.createCell((short) 1);
        HSSFCell cellName = row.createCell((short) 2);
        HSSFCell cellBeginDate = row.createCell((short) 3);
        HSSFCell cellEndDate = row.createCell((short) 4);
        HSSFCell cellHours = row.createCell((short) 5);
        HSSFCell cellDesc = row.createCell((short) 6);

        cellLoginId.setCellValue(new HSSFRichTextString(testLoginId_1));
        cellAcntId.setCellValue(new HSSFRichTextString(acntId));
        cellName.setCellValue(new HSSFRichTextString("xulipeng"));
        cellBeginDate.setCellValue(beginDate);
        cellEndDate.setCellValue(endDate);
        cellHours.setCellValue(1);
        cellDesc.setCellValue(new HSSFRichTextString("Essp v2.7"));
        row = sheet.createRow(3);
        cellAcntId = row.createCell((short) 0);
        cellLoginId = row.createCell((short) 1);
        cellName = row.createCell((short) 2);
        cellBeginDate = row.createCell((short) 3);
        cellEndDate = row.createCell((short) 4);
        cellHours = row.createCell((short) 5);
        cellDesc = row.createCell((short) 6);

        cellLoginId.setCellValue(new HSSFRichTextString(testLoginId_2));
        cellAcntId.setCellValue(new HSSFRichTextString(acntId));
        cellName.setCellValue(new HSSFRichTextString("xulipeng"));
        cellBeginDate.setCellValue(beginDate);
        cellEndDate.setCellValue(endDate);
        cellHours.setCellValue(2);
        cellDesc.setCellValue(new HSSFRichTextString(desc_2));


        //execute
        VbOverTimeImportInfo info = lgOverTimeImport.importExcel(wb);

        //assert return info
        assertEquals("return info remark", "successful", info.getRemark());
        assertEquals("return info totalRow", new Long(2), info.getTotalRows());
        assertEquals("return info totalHours", new Double(3), info.getTotalHours());

        //assert DB data
        String hql="from TcOvertime as t where t.loginId=:loginId and t.status =:status ";
        List list = lgOverTimeImport.getDbAccessor().getSession().createQuery(hql)
               .setString("loginId", testLoginId_2)
               .setString("status", Constant.STATUS_FINISHED)
               .list();

        //main table
        assertNotNull("Over Time list not null", list);
        assertEquals("Over Time list size", 1, list.size());
        TcOvertime tc = (TcOvertime) list.get(0);
        assertEquals("Over Time acntRid", acntRid, tc.getAcntRid());
        assertEquals("Over Time hours", new Double(2), tc.getTotalHours());
        assertEquals("Over Time start date", beginDate, tc.getActualDatetimeStart());
        assertEquals("Over Time finish date", endDate, tc.getActualDatetimeFinish());
        assertEquals("Over Time desc", desc_2, tc.getCause());

        list = lgOverTimeImport.getDbAccessor().getSession().createQuery("from TcOvertimeDetail t where t.tcOvertime.rid=:oRid")
                .setParameter("oRid",tc.getRid())
                .list();
        //detail table
        assertEquals("Over Time Detail set size", 1, list.size());
        TcOvertimeDetail detail = (TcOvertimeDetail) list.get(0);
        assertEquals("Over Time Detail Hours", new Double(2), detail.getHours());
        assertEquals("Over Time Detail day", beginDate, detail.getOvertimeDay());
        assertEquals("Over Time Detail shift hours", new Double(0), detail.getShiftHours());
        assertEquals("Over Time Detail payed hours", new Double(0), detail.getPayedHours());
        list = lgOverTimeImport.getDbAccessor().getSession().createQuery("from TcOvertimeLog t where t.tcOvertime.rid=:oRid")
                        .setParameter("oRid",tc.getRid())
                .list();
        //log table
        assertEquals("Over Time Log set size", 1, list.size());
        TcOvertimeLog otLog = (TcOvertimeLog) list.get(0);
        assertTrue("Over Time Log logDate", (new Date()).after(otLog.getLogDate()));
        assertEquals("Over Time Log loginId", testLoginId_3, otLog.getLoginId());
        assertEquals("Over Time Log decision", LgOverTimeImport.COMMENTS, otLog.getDecision());
    }

    public void testImportExcelLoginIdNull() throws Exception {

        //test excel data
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet();
        sheet.createRow(0);
        sheet.createRow(1);
        HSSFRow row = sheet.createRow(2);
        HSSFCell cellAcntId = row.createCell((short) 0);
        HSSFCell cellName = row.createCell((short) 2);
        HSSFCell cellBeginDate = row.createCell((short) 3);
        HSSFCell cellEndDate = row.createCell((short) 4);
        HSSFCell cellHours = row.createCell((short) 5);
        HSSFCell cellDesc = row.createCell((short) 6);

        cellAcntId.setCellValue(new HSSFRichTextString(acntId));
        cellName.setCellValue(new HSSFRichTextString("xulipeng"));
        cellBeginDate.setCellValue(beginDate);
        cellEndDate.setCellValue(endDate);
        cellHours.setCellValue(1);
        cellDesc.setCellValue(new HSSFRichTextString("Essp v2.7"));


        String msgString = null;
        String expectMsg = "row: 3 loginId is required";
        try {
            lgOverTimeImport.importExcel(wb);
            fail("exception must be existed!");
        } catch(Exception e) {
            msgString = e.getMessage();
        }
        assertNotNull("exception exist", msgString);
        assertEquals("exception correct", expectMsg, msgString);
    }

    public void testImportExcelNoSuchUser() throws Exception {

        //test excel data
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet();
        sheet.createRow(0);
        sheet.createRow(1);
        HSSFRow row = sheet.createRow(2);
        HSSFCell cellAcntId = row.createCell((short) 0);
        HSSFCell cellLoginId = row.createCell((short) 1);
        HSSFCell cellName = row.createCell((short) 2);
        HSSFCell cellBeginDate = row.createCell((short) 3);
        HSSFCell cellEndDate = row.createCell((short) 4);
        HSSFCell cellHours = row.createCell((short) 5);
        HSSFCell cellDesc = row.createCell((short) 6);

        cellLoginId.setCellValue(new HSSFRichTextString("noThisUserLoginId"));
        cellAcntId.setCellValue(new HSSFRichTextString(acntId));
        cellName.setCellValue(new HSSFRichTextString("xulipeng"));
        cellBeginDate.setCellValue(beginDate);
        cellEndDate.setCellValue(endDate);
        cellHours.setCellValue(1);
        cellDesc.setCellValue(new HSSFRichTextString("Essp v2.7"));


        String msgString = null;
        String expectMsg = "row: 3 noThisUserLoginId doesn't exist in system";
        try {
            lgOverTimeImport.importExcel(wb);
            fail("exception must be existed!");
        } catch(Exception e) {
            msgString = e.getMessage();
        }
        assertNotNull("exception exist", msgString);
        assertEquals("exception correct", expectMsg, msgString);
    }



    public void testImportExcelTimeOverlap() throws Exception {

        //test excel data
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet();
        sheet.createRow(0);
        sheet.createRow(1);
        HSSFRow row = sheet.createRow(2);
        HSSFCell cellAcntId = row.createCell((short) 0);
        HSSFCell cellLoginId = row.createCell((short) 1);
        HSSFCell cellName = row.createCell((short) 2);
        HSSFCell cellBeginDate = row.createCell((short) 3);
        HSSFCell cellEndDate = row.createCell((short) 4);
        HSSFCell cellHours = row.createCell((short) 5);
        HSSFCell cellDesc = row.createCell((short) 6);

        cellLoginId.setCellValue(new HSSFRichTextString(testLoginId_1));
        cellAcntId.setCellValue(new HSSFRichTextString(acntId));
        cellName.setCellValue(new HSSFRichTextString("xulipeng"));
        cellBeginDate.setCellValue(beginDate);
        cellEndDate.setCellValue(endDate);
        cellHours.setCellValue(1);
        cellDesc.setCellValue(new HSSFRichTextString("Essp v2.7"));

        row = sheet.createRow(3);
        cellAcntId = row.createCell((short) 0);
        cellLoginId = row.createCell((short) 1);
        cellName = row.createCell((short) 2);
        cellBeginDate = row.createCell((short) 3);
        cellEndDate = row.createCell((short) 4);
        cellHours = row.createCell((short) 5);
        cellDesc = row.createCell((short) 6);

        cellLoginId.setCellValue(new HSSFRichTextString(testLoginId_1));
        cellAcntId.setCellValue(new HSSFRichTextString(acntId));
        cellName.setCellValue(new HSSFRichTextString("xulipeng"));
        cellBeginDate.setCellValue(beginDate);
        cellEndDate.setCellValue(endDate);
        cellHours.setCellValue(2);
        cellDesc.setCellValue(new HSSFRichTextString(desc_2));

        String msgString = null;
        String expectMsg = "row: 4 xulipeng(" + testLoginId_1 + ") had overtime between "
                              + beginDate +" and " + endDate;
        try {
            lgOverTimeImport.importExcel(wb);
            fail("exception must be existed!");
        } catch(Exception e) {
            msgString = e.getMessage();
        }
        assertNotNull("exception exist", msgString);
        assertEquals("exception correct", expectMsg, msgString);
    }

    public void testImportExcelNoSuchAccount() throws Exception {

        //test excel data
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet();
        sheet.createRow(0);
        sheet.createRow(1);
        HSSFRow row = sheet.createRow(2);
        HSSFCell cellAcntId = row.createCell((short) 0);
        HSSFCell cellLoginId = row.createCell((short) 1);
        HSSFCell cellName = row.createCell((short) 2);
        HSSFCell cellBeginDate = row.createCell((short) 3);
        HSSFCell cellEndDate = row.createCell((short) 4);
        HSSFCell cellHours = row.createCell((short) 5);
        HSSFCell cellDesc = row.createCell((short) 6);

        cellLoginId.setCellValue(new HSSFRichTextString(testLoginId_1));
        cellAcntId.setCellValue(new HSSFRichTextString("noSuchAccount0"));
        cellName.setCellValue(new HSSFRichTextString("xulipeng"));
        cellBeginDate.setCellValue(beginDate);
        cellEndDate.setCellValue(endDate);
        cellHours.setCellValue(1);
        cellDesc.setCellValue(new HSSFRichTextString("Essp v2.7"));


        String msgString = null;
        String expectMsg = "row: 3 get account noSuchAccount0 error";
        try {
            lgOverTimeImport.importExcel(wb);
            fail("exception must be existed!");
        } catch(Exception e) {
            msgString = e.getMessage();
        }
        assertNotNull("exception exist", msgString);
        assertTrue("exception correct", msgString.endsWith(expectMsg));
    }


    private void initData() throws RuntimeException, SQLException {

        String sqlHr="insert into essp_hr_employee_main_t(name) values('"+USER_NAME+"')";
        lgOverTimeImport.getDbAccessor().executeUpdate(sqlHr);

        RowSet rs =  lgOverTimeImport.getDbAccessor().executeQuery("select t.code from essp_hr_employee_main_t t where t.name ='"+this.USER_NAME+"'");
        if(rs.next()) {
            String userId = rs.getString("code");
            String sqlUser="insert into upms_loginuser(login_id,user_id) values('"+testLoginId_1+"','"+userId+"')";
            lgOverTimeImport.getDbAccessor().executeUpdate(sqlUser);
        }

        String sqlHR="insert into essp_hr_employee_main_t(name) values('"+USER_NAME_2+"')";
        lgOverTimeImport.getDbAccessor().executeUpdate(sqlHR);

        RowSet rs2 =  lgOverTimeImport.getDbAccessor().executeQuery("select t.code from essp_hr_employee_main_t t where t.name ='"+this.USER_NAME_2+"'");
        if(rs2.next()) {
            String userId = rs2.getString("code");
            String sqlUser="insert into upms_loginuser(login_id,user_id) values('"+testLoginId_2+"','"+userId+"')";
            lgOverTimeImport.getDbAccessor().executeUpdate(sqlUser);
        }


        //add test account
        String sqlAcnt = "insert into pms_acnt(rid, acnt_id, rst) values("
                         + acntRid + ",'" + acntId + "','N')";
        lgOverTimeImport.getDbAccessor().executeUpdate(sqlAcnt);
    }
}
