package server.essp.attendance.overtime.logic;

import junit.framework.*;
import org.apache.poi.hssf.usermodel.*;
import java.util.*;
import javax.sql.RowSet;
import java.sql.*;
import server.essp.attendance.overtime.viewbean.VbOverTimeClean;
import c2s.essp.attendance.Constant;
import net.sf.hibernate.HibernateException;

public class TestLgOverTimeClean extends TestCase {
    private LgOverTimeClean lgOverTimeClean = null;
    private static final String testLoginId_1 = "AYE010501";
    private static final String testLoginId_2 = "TTE010501";
    private static final String USER_NAME_1 = "H4H777H";
    private static final String USER_NAME_2 = "H4HH224";
    private static final String UNIT_CODE = "WE8888";
    private static final String UNIT_NAME = "IIII";

    public TestLgOverTimeClean(String name) {
        super(name);
    }

    protected void setUp() throws Exception {
        super.setUp();
        lgOverTimeClean = new LgOverTimeClean();
        lgOverTimeClean.getDbAccessor().followTx();
        initData();
    }



    protected void tearDown() throws Exception {
        lgOverTimeClean.getDbAccessor().endTxRollback();
        lgOverTimeClean = null;
        super.tearDown();
    }

    public void testImportCleanExcelConfirmNormal() {
        List list = lgOverTimeClean.importCleanExcelConfirm(getExcelNormal());
        assertNotNull("list not null", list);
        assertEquals("list size is 2", 2, list.size());
        VbOverTimeClean vb = (VbOverTimeClean) list.get(0);
        assertEquals("userName is correct", USER_NAME_1, vb.getName());
        assertEquals("orgId is correct", UNIT_CODE, vb.getOrgId());
        assertEquals("orgName is correct", UNIT_NAME, vb.getOrgName());
        assertEquals("usableHours is correct", Double.valueOf(7), vb.getUsableHours());
        assertEquals("cleanHours is correct", Double.valueOf(12), vb.getCleanHours());

        vb = (VbOverTimeClean) list.get(1);
        assertEquals("userName is correct", USER_NAME_2, vb.getName());
        assertEquals("orgId is correct", UNIT_CODE, vb.getOrgId());
        assertEquals("orgName is correct", UNIT_NAME, vb.getOrgName());
        assertEquals("usableHours is correct", Double.valueOf(0), vb.getUsableHours());
        assertEquals("cleanHours is correct", Double.valueOf(50), vb.getCleanHours());

    }

     public void testImportCleanExcelConfirmNoSuchUser() {
          String exptionStr = null;
          String expectMessage = "row: 3 No such user (14515/xulipeng) in system";
         try {
             lgOverTimeClean.importCleanExcelConfirm(getExcelNoSuchUser());
         } catch (Exception e) {
             exptionStr = e.getMessage();
         }
         assertNotNull("exception exist", exptionStr);
         assertEquals("exception message correct", expectMessage, exptionStr);
     }

     public void testImportCleanExcelConfirmNullUser() {
          String exptionStr = null;
          String expectMessage = "row: 3 loginId is required";
         try {
             lgOverTimeClean.importCleanExcelConfirm(getExcelNullUser());
         } catch (Exception e) {
             exptionStr = e.getMessage();
         }
         assertNotNull("exception exist", exptionStr);
         assertEquals("exception message correct", expectMessage, exptionStr);
     }


     public void testExecuteCleanNormal() throws SQLException,
            HibernateException, Exception {
         List list = getCleanBeanListNormal();
         List resultList = lgOverTimeClean.executeClean(list);
         assertNotNull("result list is not null", resultList);
         assertEquals("result list size is 1", 1 , resultList.size());
         VbOverTimeClean vb = (VbOverTimeClean) resultList.get(0);
         assertEquals("cleanHours is 0", Double.valueOf(0), vb.getCleanHours());
         assertEquals("usableHours is 5", Double.valueOf(5), vb.getUsableHours());

         lgOverTimeClean.getDbAccessor().getSession().flush();
         String sql = "select sum(d.hours - d.payed_hours -d.shift_hours) as usable_hours, sum(d.payed_hours) as payed_hours "
                      + "from tc_overtime t left join tc_overtime_detail d on t.rid = d.overtime_id "
                      + "where t.login_id = '"+ testLoginId_1 + "'";
         RowSet rs = lgOverTimeClean.getDbAccessor().executeQuery(sql);
         Double usableHours = null;
         Double payedHours = null;
         if (rs.next()) {
             usableHours = rs.getDouble("usable_hours");
             payedHours = rs.getDouble("payed_hours");
         }

         assertNotNull("usable_hours is not null", usableHours);
         assertNotNull("payed_hours is not null", payedHours);
         assertEquals("usable_hours is correct", Double.valueOf(5), usableHours);
         assertEquals("payed_hours is correct", Double.valueOf(14), payedHours);
     }

      public void testExecuteCleanNotEnough() throws SQLException{
          List list = getCleanBeanListNotEnough();
          String exMessage = null;
          String expectMessage = testLoginId_1 + "(4gsg) usableHours is not enough";
          try {
              lgOverTimeClean.executeClean(list);
          } catch(Exception ex) {
              exMessage = ex.getMessage();
          }
          assertNotNull("exception exist", exMessage);
          assertEquals("exception is correct", expectMessage, exMessage);
      }

     private List getCleanBeanListNormal() {
         List list = new ArrayList();
         VbOverTimeClean vb = new VbOverTimeClean();
         vb.setCleanHours(Double.valueOf(2));
         vb.setLoginId(testLoginId_1);
         vb.setName("4gsg");
         vb.setOrgId(UNIT_CODE);
         vb.setOrgName(UNIT_NAME);
         vb.setUsableHours(Double.valueOf(7));
         list.add(vb);
         return list;
     }

     private List getCleanBeanListNotEnough() {
       List list = new ArrayList();
       VbOverTimeClean vb = new VbOverTimeClean();
       vb.setCleanHours(Double.valueOf(8));
       vb.setLoginId(testLoginId_1);
       vb.setName("4gsg");
       vb.setOrgId(UNIT_CODE);
       vb.setOrgName(UNIT_NAME);
       vb.setUsableHours(Double.valueOf(7));
       list.add(vb);

       vb = new VbOverTimeClean();
       vb.setCleanHours(Double.valueOf(2));
       vb.setLoginId(testLoginId_2);
       vb.setName("gsgsg");
       vb.setOrgId(UNIT_CODE);
       vb.setOrgName(UNIT_NAME);
       vb.setUsableHours(Double.valueOf(5));
       list.add(vb);

       return list;
   }

    private HSSFWorkbook getExcelNormal() {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet();
        sheet.createRow(0);
        sheet.createRow(1);
        HSSFRow row = sheet.createRow(2);
        HSSFCell cellLoginId = row.createCell((short) 0);
        HSSFCell cellName = row.createCell((short) 1);
        HSSFCell cellCleanHours = row.createCell((short) 2);

        cellLoginId.setCellValue(new HSSFRichTextString(testLoginId_1));
        cellName.setCellValue(new HSSFRichTextString("xulipeng"));
        cellCleanHours.setCellValue(12);

        row = sheet.createRow(3);
        cellLoginId = row.createCell((short) 0);
        cellName = row.createCell((short) 1);
        cellCleanHours = row.createCell((short) 2);

        cellLoginId.setCellValue(new HSSFRichTextString(testLoginId_2));
        cellName.setCellValue(new HSSFRichTextString("peng"));
        cellCleanHours.setCellValue(50);

        return wb;
    }

    private HSSFWorkbook getExcelNoSuchUser() {
      HSSFWorkbook wb = new HSSFWorkbook();
      HSSFSheet sheet = wb.createSheet();
      sheet.createRow(0);
      sheet.createRow(1);
      HSSFRow row = sheet.createRow(2);
      HSSFCell cellLoginId = row.createCell((short) 0);
      HSSFCell cellName = row.createCell((short) 1);
      HSSFCell cellCleanHours = row.createCell((short) 2);

      cellLoginId.setCellValue(new HSSFRichTextString("14515"));
      cellName.setCellValue(new HSSFRichTextString("xulipeng"));
      cellCleanHours.setCellValue(12);

      row = sheet.createRow(3);
      cellLoginId = row.createCell((short) 0);
      cellName = row.createCell((short) 1);
      cellCleanHours = row.createCell((short) 2);

      cellLoginId.setCellValue(new HSSFRichTextString(testLoginId_2));
      cellName.setCellValue(new HSSFRichTextString("peng"));
      cellCleanHours.setCellValue(50);

      return wb;
  }

  private HSSFWorkbook getExcelNullUser() {
      HSSFWorkbook wb = new HSSFWorkbook();
      HSSFSheet sheet = wb.createSheet();
      sheet.createRow(0);
      sheet.createRow(1);
      HSSFRow row = sheet.createRow(2);
      HSSFCell cellLoginId = row.createCell((short) 0);
      HSSFCell cellName = row.createCell((short) 1);
      HSSFCell cellCleanHours = row.createCell((short) 2);

      cellName.setCellValue(new HSSFRichTextString("xulipeng"));
      cellCleanHours.setCellValue(12);
      return wb;
  }



    private void initData() throws SQLException, RuntimeException {
        String sqlOt = "insert into tc_overtime(rid, login_id, status) values(9998429, '" + testLoginId_1 + "', '" + Constant.STATUS_FINISHED + "')";
        lgOverTimeClean.getDbAccessor().executeUpdate(sqlOt);

        String sqlOtDetail = "insert into tc_overtime_detail(rid, overtime_id, hours, shift_hours, payed_hours) values(9876543, 9998429, 20, 1, 12)";
        lgOverTimeClean.getDbAccessor().executeUpdate(sqlOtDetail);

        String sqlHr="insert into essp_hr_employee_main_t(name,chinese_name, dept) values('rere', '"+USER_NAME_1+"', 'rwt')";
        lgOverTimeClean.getDbAccessor().executeUpdate(sqlHr);

        RowSet rs =  lgOverTimeClean.getDbAccessor().executeQuery("select t.code from essp_hr_employee_main_t t where t.chinese_name ='" + this.USER_NAME_1 + "'");
        if(rs.next()) {
            String userId = rs.getString("code");
            String sqlUser="insert into upms_loginuser(login_id,user_id) values('" + testLoginId_1 + "','" + userId + "')";
            lgOverTimeClean.getDbAccessor().executeUpdate(sqlUser);
        }

        String sqlHr2="insert into essp_hr_employee_main_t(name, chinese_name, dept) values('rwrw', '" + USER_NAME_2 + "', 'rwt')";
        lgOverTimeClean.getDbAccessor().executeUpdate(sqlHr2);

        RowSet rs1 =  lgOverTimeClean.getDbAccessor().executeQuery("select t.code from essp_hr_employee_main_t t where t.chinese_name ='" + this.USER_NAME_2 + "'");
        if(rs1.next()) {
            String userId = rs1.getString("code");
            String sqlUser2="insert into upms_loginuser(login_id,user_id) values('"+testLoginId_2+"','"+userId+"')";
            lgOverTimeClean.getDbAccessor().executeUpdate(sqlUser2);
        }

        String sqlOrg = "insert into essp_upms_unit(unit_id, name, unit_code) values('rwt', '" + UNIT_NAME + "', '" + UNIT_CODE + "')";
        lgOverTimeClean.getDbAccessor().executeUpdate(sqlOrg);
    }

}
