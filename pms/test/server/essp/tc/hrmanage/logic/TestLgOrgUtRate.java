package server.essp.tc.hrmanage.logic;

import java.util.Date;

import com.wits.util.comDate;
import junit.framework.TestCase;
import javax.sql.RowSet;
import java.sql.SQLException;
import c2s.essp.tc.hrmanage.DtoOrgUtRate;
import c2s.dto.ITreeNode;
import c2s.essp.tc.weeklyreport.DtoWeeklyReport;

public class TestLgOrgUtRate extends TestCase {
    private final static Long HR_ACNT_RID = new Long(4566879);
    private final static Date BEGIN_DATE = comDate.toDate("2100-01-26");
    private final static Date END_DATE = comDate.toDate("2100-02-25");

    private final static String ORG_ID_100 = "org_test_id_100";
    private final static String ORG_ID_110 = "org_test_id_110";
    private final static String ORG_ID_120 = "org_test_id_120";
    private final static String ORG_ID_111 = "org_test_id_111";
    private final static String ORG_ID_121 = "org_test_id_121";
    private final static String ORG_ID_122 = "org_test_id_122";

    private final static String ORG_NAME_100 = "org_test_name_100";
    private final static String ORG_NAME_110 = "org_test_name_110";
    private final static String ORG_NAME_120 = "org_test_name_120";
    private final static String ORG_NAME_111 = "org_test_name_111";
    private final static String ORG_NAME_121 = "org_test_name_121";
    private final static String ORG_NAME_122 = "org_test_name_122";

    private final static String ORG_CODE_100 = "0rg_test_code_100";
    private final static String ORG_CODE_110 = "org_test_code_110";
    private final static String ORG_CODE_120 = "org_test_code_120";
    private final static String ORG_CODE_111 = "org_test_code_111";
    private final static String ORG_CODE_121 = "org_test_code_121";
    private final static String ORG_CODE_122 = "org_test_code_122";

    private final static String ACNT_RID_100_1_P = "9910011";
    private final static String ACNT_RID_100_2_M = "9910020";
    private final static String ACNT_RID_110_1_P = "9911011";
    private final static String ACNT_RID_110_2_M = "9911020";
    private final static String ACNT_RID_111_1_P = "9911111";
    private final static String ACNT_RID_120_1_P = "9912011";
    private final static String ACNT_RID_120_2_M = "9912010";
    private final static String ACNT_RID_121_1_P = "9912111";
    private final static String ACNT_RID_121_2_P = "9912121";
    private final static String ACNT_RID_121_3_P = "9912131";

    private final static String EMP_LOGINID_1_P = "tl_1_p";
    private final static String EMP_LOGINID_2_P = "tl_2_p";
    private final static String EMP_LOGINID_3_P = "tl_3_p";
    private final static String EMP_LOGINID_4_N = "tl_4_n";
    private final static String EMP_LOGINID_5_N = "tl_5_n";

    private LgOrgUtRate lgOrgUtRate = null;

    public TestLgOrgUtRate(String name) {
        super(name);
    }

    protected void setUp() throws Exception {
        super.setUp();
        lgOrgUtRate = new LgOrgUtRate(HR_ACNT_RID, BEGIN_DATE, END_DATE);
        lgOrgUtRate.getDbAccessor().followTx();
        initData();
    }

    protected void tearDown() throws Exception {
        lgOrgUtRate.getDbAccessor().endTxRollback();
        lgOrgUtRate = null;
        super.tearDown();
    }

    public void testGetUtRateTreeNormal() {
        initWeeklyReport();
        ITreeNode root = lgOrgUtRate.getUtRateTree(true);

        assertNotNull("�õ�orgUtRate���ڵ�", root);
        ITreeNode node_100 = root.getChildAt(0);
        DtoOrgUtRate dto_100 = (DtoOrgUtRate) node_100.getDataBean();
        assertEquals("�ж�100���Ŵ���", ORG_CODE_100, dto_100.getUnitCode());
        assertEquals("�ж�100��������", ORG_NAME_100, dto_100.getUnitName());
        assertEquals("�ж�100ֱ��������", new Double(0.43), dto_100.getDirectPM());
        assertEquals("�ж�100���������", new Double(0.43), dto_100.getIndirectPM());
        assertEquals("�ж�100����������", new Double(0.39), dto_100.getIncomePM());
        assertEquals("�ж�100ʵ��������", new Double(0.87), dto_100.getActualPM());
        assertEquals("�ж�100��Աʹ����", new Double(90.00), dto_100.getUtRate());

        ITreeNode node_110 = node_100.getChildAt(0);
        DtoOrgUtRate dto_110 = (DtoOrgUtRate) node_110.getDataBean();
        assertEquals("�ж�110���Ŵ���", ORG_CODE_110, dto_110.getUnitCode());
        assertEquals("�ж�110��������", ORG_NAME_110, dto_110.getUnitName());
        assertEquals("�ж�110ֱ��������", new Double(0.10), dto_110.getDirectPM());
        assertEquals("�ж�110���������", new Double(0.09), dto_110.getIndirectPM());
        assertEquals("�ж�110����������", new Double(0.05), dto_110.getIncomePM());
        assertEquals("�ж�110ʵ��������", new Double(0.19), dto_110.getActualPM());
        assertEquals("�ж�110��Աʹ����", new Double(55.56), dto_110.getUtRate());

        ITreeNode node_120 = node_100.getChildAt(1);
        DtoOrgUtRate dto_120 = (DtoOrgUtRate) node_120.getDataBean();
        assertEquals("�ж�120���Ŵ���", ORG_CODE_120, dto_120.getUnitCode());
        assertEquals("�ж�120��������", ORG_NAME_120, dto_120.getUnitName());
        assertEquals("�ж�120ֱ��������", new Double(0.34), dto_120.getDirectPM());
        assertEquals("�ж�120���������", new Double(0.13), dto_120.getIndirectPM());
        assertEquals("�ж�120����������", new Double(0.34), dto_120.getIncomePM());
        assertEquals("�ж�120ʵ��������", new Double(0.46), dto_120.getActualPM());
        assertEquals("�ж�120��Աʹ����", new Double(100), dto_120.getUtRate());

        ITreeNode node_111 = node_110.getChildAt(0);
        DtoOrgUtRate dto_111 = (DtoOrgUtRate) node_111.getDataBean();
        assertEquals("�ж�111���Ŵ���", ORG_CODE_111, dto_111.getUnitCode());
        assertEquals("�ж�111��������", ORG_NAME_111, dto_111.getUnitName());
        assertEquals("�ж�111ֱ��������", new Double(0.05), dto_111.getDirectPM());
        assertEquals("�ж�111���������", new Double(0), dto_111.getIndirectPM());
        assertEquals("�ж�111����������", new Double(0.05), dto_111.getIncomePM());
        assertEquals("�ж�111ʵ��������", new Double(0.05), dto_111.getActualPM());
        assertEquals("�ж�111��Աʹ����", new Double(100), dto_111.getUtRate());

        ITreeNode node_121 = node_120.getChildAt(0);
        DtoOrgUtRate dto_121 = (DtoOrgUtRate) node_121.getDataBean();
        assertEquals("�ж�121���Ŵ���", ORG_CODE_121, dto_121.getUnitCode());
        assertEquals("�ж�121��������", ORG_NAME_121, dto_121.getUnitName());
        assertEquals("�ж�121ֱ��������", new Double(0.30), dto_121.getDirectPM());
        assertEquals("�ж�121���������", new Double(0), dto_121.getIndirectPM());
        assertEquals("�ж�121����������", new Double(0.30), dto_121.getIncomePM());
        assertEquals("�ж�121ʵ��������", new Double(0.30), dto_121.getActualPM());
        assertEquals("�ж�121��Աʹ����", new Double(100), dto_121.getUtRate());

        ITreeNode node_122 = node_120.getChildAt(1);
        DtoOrgUtRate dto_122 = (DtoOrgUtRate) node_122.getDataBean();
        assertEquals("�ж�122���Ŵ���", ORG_CODE_122, dto_122.getUnitCode());
        assertEquals("�ж�122��������", ORG_NAME_122, dto_122.getUnitName());
        assertEquals("�ж�122ֱ��������", new Double(0), dto_122.getDirectPM());
        assertEquals("�ж�122���������", new Double(0), dto_122.getIndirectPM());
        assertEquals("�ж�122����������", new Double(0), dto_122.getIncomePM());
        assertEquals("�ж�122ʵ��������", new Double(0), dto_122.getActualPM());
        assertEquals("�ж�122��Աʹ����", null, dto_122.getUtRate());
    }

    private void initData() throws Exception {
        initOrg();
        initAccount();
        initEmployee();
    }

    private void initOrg() {
        String sqlOrg100 = "insert into essp_upms_unit(unit_id, name, parent_id, unit_code)"
                          + " values('" + ORG_ID_100 + "', '" + ORG_NAME_100 + "', '" + lgOrgUtRate.getSiteOrgId() + "', '" + ORG_CODE_100 + "')";
        String sqlOrg110 = "insert into essp_upms_unit(unit_id, name, parent_id, unit_code)"
                          + " values('" + ORG_ID_110 + "', '" + ORG_NAME_110 + "', '" + ORG_ID_100 + "', '" + ORG_CODE_110 + "')";
        String sqlOrg120 = "insert into essp_upms_unit(unit_id, name, parent_id, unit_code)"
                          + " values('" + ORG_ID_120 + "', '" + ORG_NAME_120 + "', '" + ORG_ID_100 + "', '" + ORG_CODE_120 + "')";
        String sqlOrg111 = "insert into essp_upms_unit(unit_id, name, parent_id, unit_code)"
                          + " values('" + ORG_ID_111 + "', '" + ORG_NAME_111 + "', '" + ORG_ID_110 + "', '" + ORG_CODE_111 + "')";
        String sqlOrg121 = "insert into essp_upms_unit(unit_id, name, parent_id, unit_code)"
                          + " values('" + ORG_ID_121 + "', '" + ORG_NAME_121 + "', '" + ORG_ID_120 + "', '" + ORG_CODE_121 + "')";
        String sqlOrg122 = "insert into essp_upms_unit(unit_id, name, parent_id, unit_code)"
                          + " values('" + ORG_ID_122 + "', '" + ORG_NAME_122 + "', '" + ORG_ID_120 + "', '" + ORG_CODE_122 + "')";

       lgOrgUtRate.getDbAccessor().executeUpdate(sqlOrg100);
       lgOrgUtRate.getDbAccessor().executeUpdate(sqlOrg110);
       lgOrgUtRate.getDbAccessor().executeUpdate(sqlOrg120);
       lgOrgUtRate.getDbAccessor().executeUpdate(sqlOrg111);
       lgOrgUtRate.getDbAccessor().executeUpdate(sqlOrg121);
       lgOrgUtRate.getDbAccessor().executeUpdate(sqlOrg122);
    }

    private void initAccount() {
        String sqlAcnt100_1_P = "insert into pms_acnt(rid, acnt_id, acnt_name, ACNT_ORGANIZATION, acnt_type)"
                              + " values(" + ACNT_RID_100_1_P + ", 'acnt_test_id_100_1_P', 'acnt_test_name_100_1_P', '" + ORG_ID_100 + "', '" + lgOrgUtRate.PROJECT_TYPE + "')";
        String sqlAcnt100_2_M = "insert into pms_acnt(rid, acnt_id, acnt_name, ACNT_ORGANIZATION, acnt_type)"
                              + " values(" + ACNT_RID_100_2_M + ", 'acnt_test_id_100_2_M', 'acnt_test_name_100_2_M', '" + ORG_ID_100 + "', 'management')";
        String sqlAcnt110_1_P = "insert into pms_acnt(rid, acnt_id, acnt_name, ACNT_ORGANIZATION, acnt_type)"
                              + " values(" + ACNT_RID_110_1_P + ", 'acnt_test_id_110_1_P', 'acnt_test_name_110_1_P', '" + ORG_ID_110 + "', '" + lgOrgUtRate.PROJECT_TYPE + "')";
        String sqlAcnt110_2_M = "insert into pms_acnt(rid, acnt_id, acnt_name, ACNT_ORGANIZATION, acnt_type)"
                              + " values(" + ACNT_RID_110_2_M + ", 'acnt_test_id_110_2_M', 'acnt_test_name_110_2_M', '" + ORG_ID_110 + "', 'management')";
        String sqlAcnt111_1_P = "insert into pms_acnt(rid, acnt_id, acnt_name, ACNT_ORGANIZATION, acnt_type)"
                              + " values(" + ACNT_RID_111_1_P + ", 'acnt_test_id_111_1_P', 'acnt_test_name_111_1_P', '" + ORG_ID_111 + "', '" + lgOrgUtRate.PROJECT_TYPE + "')";
        String sqlAcnt120_1_P = "insert into pms_acnt(rid, acnt_id, acnt_name, ACNT_ORGANIZATION, acnt_type)"
                              + " values(" + ACNT_RID_120_1_P + ", 'acnt_test_id_120_1_P', 'acnt_test_name_120_1_P', '" + ORG_ID_120 + "', '" + lgOrgUtRate.PROJECT_TYPE + "')";
        String sqlAcnt120_2_M = "insert into pms_acnt(rid, acnt_id, acnt_name, ACNT_ORGANIZATION, acnt_type)"
                              + " values(" + ACNT_RID_120_2_M + ", 'acnt_test_id_120_2_M', 'acnt_test_name_120_2_M', '" + ORG_ID_120 + "', 'management')";
        String sqlAcnt121_1_P = "insert into pms_acnt(rid, acnt_id, acnt_name, ACNT_ORGANIZATION, acnt_type)"
                              + " values(" + ACNT_RID_121_1_P + ", 'acnt_test_id_121_1_P', 'acnt_test_name_121_1_P', '" + ORG_ID_121+ "', '" + lgOrgUtRate.PROJECT_TYPE + "')";
        String sqlAcnt121_2_P = "insert into pms_acnt(rid, acnt_id, acnt_name, ACNT_ORGANIZATION, acnt_type)"
                              + " values(" + ACNT_RID_121_2_P + ", 'acnt_test_id_121_2_P', 'acnt_test_name_121_2_P', '" + ORG_ID_121 + "', '" + lgOrgUtRate.PROJECT_TYPE + "')";
        String sqlAcnt121_3_P = "insert into pms_acnt(rid, acnt_id, acnt_name, ACNT_ORGANIZATION, acnt_type)"
                              + " values(" + ACNT_RID_121_3_P + ", 'acnt_test_id_121_3_P', 'acnt_test_name_121_3_P', '" + ORG_ID_121 + "', '" + lgOrgUtRate.PROJECT_TYPE + "')";

        lgOrgUtRate.getDbAccessor().executeUpdate(sqlAcnt100_1_P);
        lgOrgUtRate.getDbAccessor().executeUpdate(sqlAcnt100_2_M);
        lgOrgUtRate.getDbAccessor().executeUpdate(sqlAcnt110_1_P);
        lgOrgUtRate.getDbAccessor().executeUpdate(sqlAcnt110_2_M);
        lgOrgUtRate.getDbAccessor().executeUpdate(sqlAcnt111_1_P);
        lgOrgUtRate.getDbAccessor().executeUpdate(sqlAcnt120_1_P);
        lgOrgUtRate.getDbAccessor().executeUpdate(sqlAcnt120_2_M);
        lgOrgUtRate.getDbAccessor().executeUpdate(sqlAcnt121_1_P);
        lgOrgUtRate.getDbAccessor().executeUpdate(sqlAcnt121_2_P);
        lgOrgUtRate.getDbAccessor().executeUpdate(sqlAcnt121_3_P);
    }

    private void initEmployee() throws RuntimeException, SQLException {
        String sqlEmp_1_P = "insert into essp_hr_employee_main_t(name, indate, PRODUCTIVITY) values('" + EMP_LOGINID_1_P + "', current_date, '1')";
        lgOrgUtRate.getDbAccessor().executeUpdate(sqlEmp_1_P);

        RowSet rs =  lgOrgUtRate.getDbAccessor().executeQuery("select t.code from essp_hr_employee_main_t t where t.name ='" + EMP_LOGINID_1_P + "'");
        if(rs.next()) {
            String userId = rs.getString("code");
            String sqlScope = "insert into essp_hr_account_scope_t(account_id, scope_code, seq) values(" + HR_ACNT_RID + ", '" + userId + "', 1)";
            lgOrgUtRate.getDbAccessor().executeUpdate(sqlScope);
            String sqlUser="insert into upms_loginuser(login_id,user_id) values('" + EMP_LOGINID_1_P + "','" + userId + "')";
            lgOrgUtRate.getDbAccessor().executeUpdate(sqlUser);
        }

        String sqlEmp_2_P = "insert into essp_hr_employee_main_t(name, indate, PRODUCTIVITY) values('" + EMP_LOGINID_2_P + "',current_date, '1')";
        lgOrgUtRate.getDbAccessor().executeUpdate(sqlEmp_2_P);

        rs =  lgOrgUtRate.getDbAccessor().executeQuery("select t.code from essp_hr_employee_main_t t where t.name ='" + EMP_LOGINID_2_P + "'");
        if(rs.next()) {
            String userId = rs.getString("code");
            String sqlScope = "insert into essp_hr_account_scope_t(account_id, scope_code, seq) values(" + HR_ACNT_RID + ", '" + userId + "', 2)";
            lgOrgUtRate.getDbAccessor().executeUpdate(sqlScope);
            String sqlUser="insert into upms_loginuser(login_id,user_id) values('" + EMP_LOGINID_2_P + "','" + userId + "')";
            lgOrgUtRate.getDbAccessor().executeUpdate(sqlUser);
        }

//        String sqlEmp_3_P = "insert into essp_hr_employee_main_t(name, indate, PRODUCTIVITY) values('" + EMP_LOGINID_3_P + "',current_date, '1')";
//        lgOrgUtRate.getDbAccessor().executeUpdate(sqlEmp_3_P);
//
//        rs =  lgOrgUtRate.getDbAccessor().executeQuery("select t.code from essp_hr_employee_main_t t where t.name ='" + EMP_LOGINID_3_P + "'");
//        if(rs.next()) {
//            String userId = rs.getString("code");
//            String sqlScope = "insert into essp_hr_account_scope_t(account_id, scope_code, seq) values(" + HR_ACNT_RID + ", '" + userId + "', 3)";
//            lgOrgUtRate.getDbAccessor().executeUpdate(sqlScope);
//            String sqlUser="insert into upms_loginuser(login_id,user_id) values('" + EMP_LOGINID_3_P + "','" + userId + "')";
//            lgOrgUtRate.getDbAccessor().executeUpdate(sqlUser);
//        }

        String sqlEmp_4_N = "insert into essp_hr_employee_main_t(name, indate, PRODUCTIVITY) values('" + EMP_LOGINID_4_N + "',current_date, '0')";
        lgOrgUtRate.getDbAccessor().executeUpdate(sqlEmp_4_N);

        rs =  lgOrgUtRate.getDbAccessor().executeQuery("select t.code from essp_hr_employee_main_t t where t.name ='" + EMP_LOGINID_4_N + "'");
        if(rs.next()) {
            String userId = rs.getString("code");
            String sqlScope = "insert into essp_hr_account_scope_t(account_id, scope_code, seq) values(" + HR_ACNT_RID + ", '" + userId + "', 4)";
            lgOrgUtRate.getDbAccessor().executeUpdate(sqlScope);
            String sqlUser="insert into upms_loginuser(login_id,user_id) values('" + EMP_LOGINID_4_N + "','" + userId + "')";
            lgOrgUtRate.getDbAccessor().executeUpdate(sqlUser);
        }

        String sqlEmp_5_N = "insert into essp_hr_employee_main_t(name, indate, PRODUCTIVITY) values('" + EMP_LOGINID_5_N + "',current_date, '0')";
        lgOrgUtRate.getDbAccessor().executeUpdate(sqlEmp_5_N);

        rs =  lgOrgUtRate.getDbAccessor().executeQuery("select t.code from essp_hr_employee_main_t t where t.name ='" + EMP_LOGINID_5_N + "'");
        if(rs.next()) {
            String userId = rs.getString("code");
            String sqlScope = "insert into essp_hr_account_scope_t(account_id, scope_code, seq) values(" + HR_ACNT_RID + ", '" + userId + "', 5)";
            lgOrgUtRate.getDbAccessor().executeUpdate(sqlScope);
            String sqlUser="insert into upms_loginuser(login_id,user_id) values('" + EMP_LOGINID_5_N + "','" + userId + "')";
            lgOrgUtRate.getDbAccessor().executeUpdate(sqlUser);
        }
    }

    private void initWeeklyReport() {
        String sqlWR_01 = "insert into tc_weekly_report_sum(rid, user_id, acnt_rid, begin_period, end_period, mon_hour, tue_hour, wed_hour, thu_hour, fri_hour, sat_hour, sun_hour, CONFIRM_STATUS)"
                          + " values(4567901, '" + EMP_LOGINID_4_N + "', " + ACNT_RID_100_1_P + ", to_date('2100-02-01','yyyy-MM-dd'), to_date('2100-02-07','yyyy-MM-dd'), 10, 12, 0, 0, 0, 0, 0, '" + DtoWeeklyReport.STATUS_CONFIRMED + "')";

        String sqlWR_02 = "insert into tc_weekly_report_sum(rid, user_id, acnt_rid, begin_period, end_period, mon_hour, tue_hour, wed_hour, thu_hour, fri_hour, sat_hour, sun_hour, CONFIRM_STATUS)"
                          + " values(4567902, '" + EMP_LOGINID_4_N + "', " + ACNT_RID_100_2_M + ", to_date('2100-02-01','yyyy-MM-dd'), to_date('2100-02-07','yyyy-MM-dd'), 0, 0, 10, 8, 0, 0, 0, '" + DtoWeeklyReport.STATUS_CONFIRMED + "')";

        String sqlWR_03 = "insert into tc_weekly_report_sum(rid, user_id, acnt_rid, begin_period, end_period, mon_hour, tue_hour, wed_hour, thu_hour, fri_hour, sat_hour, sun_hour, CONFIRM_STATUS)"
                          + " values(4567903, '" + EMP_LOGINID_5_N + "', " + ACNT_RID_110_1_P + ", to_date('2100-02-01','yyyy-MM-dd'), to_date('2100-02-07','yyyy-MM-dd'), 0, 0, 0, 0, 5, 3, 0, '" + DtoWeeklyReport.STATUS_CONFIRMED + "')";

        String sqlWR_04 = "insert into tc_weekly_report_sum(rid, user_id, acnt_rid, begin_period, end_period, mon_hour, tue_hour, wed_hour, thu_hour, fri_hour, sat_hour, sun_hour, CONFIRM_STATUS)"
                          + " values(4567904, '" + EMP_LOGINID_5_N + "', " + ACNT_RID_110_2_M + ", to_date('2100-02-01','yyyy-MM-dd'), to_date('2100-02-07','yyyy-MM-dd'), 4, 0, 0, 0, 0, 0, 5, '" + DtoWeeklyReport.STATUS_CONFIRMED + "')";

        String sqlWR_05 = "insert into tc_weekly_report_sum(rid, user_id, acnt_rid, begin_period, end_period, mon_hour, tue_hour, wed_hour, thu_hour, fri_hour, sat_hour, sun_hour, CONFIRM_STATUS)"
                          + " values(4567905, '" + EMP_LOGINID_1_P + "', " + ACNT_RID_110_2_M + ", to_date('2100-02-01','yyyy-MM-dd'), to_date('2100-02-07','yyyy-MM-dd'), 4, 4, 0, 0, 0, 0, 0, '" + DtoWeeklyReport.STATUS_CONFIRMED + "')";

        String sqlWR_06 = "insert into tc_weekly_report_sum(rid, user_id, acnt_rid, begin_period, end_period, mon_hour, tue_hour, wed_hour, thu_hour, fri_hour, sat_hour, sun_hour, CONFIRM_STATUS)"
                          + " values(4567906, '" + EMP_LOGINID_1_P + "', " + ACNT_RID_120_1_P + ", to_date('2100-02-01','yyyy-MM-dd'), to_date('2100-02-07','yyyy-MM-dd'), 3, 4, 0, 0, 0, 0, 0, '" + DtoWeeklyReport.STATUS_CONFIRMED + "')";

        String sqlWR_07 = "insert into tc_weekly_report_sum(rid, user_id, acnt_rid, begin_period, end_period, mon_hour, tue_hour, wed_hour, thu_hour, fri_hour, sat_hour, sun_hour, CONFIRM_STATUS)"
                          + " values(4567907, '" + EMP_LOGINID_5_N + "', " + ACNT_RID_120_1_P + ", to_date('2100-02-15','yyyy-MM-dd'), to_date('2100-02-21','yyyy-MM-dd'), 10, 2, 0, 0, 0, 0, 0, '" + DtoWeeklyReport.STATUS_CONFIRMED + "')";

        String sqlWR_08 = "insert into tc_weekly_report_sum(rid, user_id, acnt_rid, begin_period, end_period, mon_hour, tue_hour, wed_hour, thu_hour, fri_hour, sat_hour, sun_hour, CONFIRM_STATUS)"
                          + " values(4567908, '" + EMP_LOGINID_5_N + "', " + ACNT_RID_120_2_M + ", to_date('2100-02-08','yyyy-MM-dd'), to_date('2100-02-14','yyyy-MM-dd'), 5, 6, 0, 0, 0, 0, 0, '" + DtoWeeklyReport.STATUS_CONFIRMED + "')";

        String sqlWR_09 = "insert into tc_weekly_report_sum(rid, user_id, acnt_rid, begin_period, end_period, mon_hour, tue_hour, wed_hour, thu_hour, fri_hour, sat_hour, sun_hour, CONFIRM_STATUS)"
                          + " values(4567909, '" + EMP_LOGINID_2_P + "', " + ACNT_RID_111_1_P + ", to_date('2100-02-01','yyyy-MM-dd'), to_date('2100-02-07','yyyy-MM-dd'), 9, 1, 0, 0, 0, 0, 0, '" + DtoWeeklyReport.STATUS_CONFIRMED + "')";

        String sqlWR_10 = "insert into tc_weekly_report_sum(rid, user_id, acnt_rid, begin_period, end_period, mon_hour, tue_hour, wed_hour, thu_hour, fri_hour, sat_hour, sun_hour, CONFIRM_STATUS)"
                          + " values(4567910, '" + EMP_LOGINID_3_P + "', " + ACNT_RID_111_1_P + ", to_date('2100-02-15','yyyy-MM-dd'), to_date('2100-02-21','yyyy-MM-dd'), 20, 1, 0, 0, 0, 0, 0, '" + DtoWeeklyReport.STATUS_CONFIRMED + "')";

        String sqlWR_11 = "insert into tc_weekly_report_sum(rid, user_id, acnt_rid, begin_period, end_period, mon_hour, tue_hour, wed_hour, thu_hour, fri_hour, sat_hour, sun_hour, CONFIRM_STATUS)"
                          + " values(4567911, '" + EMP_LOGINID_2_P + "', " + ACNT_RID_121_1_P + ", to_date('2100-02-08','yyyy-MM-dd'), to_date('2100-02-14','yyyy-MM-dd'), 6, 6, 0, 0, 0, 0, 0, '" + DtoWeeklyReport.STATUS_CONFIRMED + "')";

        String sqlWR_12 = "insert into tc_weekly_report_sum(rid, user_id, acnt_rid, begin_period, end_period, mon_hour, tue_hour, wed_hour, thu_hour, fri_hour, sat_hour, sun_hour, CONFIRM_STATUS)"
                          + " values(4567912, '" + EMP_LOGINID_2_P + "', " + ACNT_RID_121_2_P + ", to_date('2100-02-01','yyyy-MM-dd'), to_date('2100-02-07','yyyy-MM-dd'), 10, 8, 0, 0, 0, 0, 0, '" + DtoWeeklyReport.STATUS_CONFIRMED + "')";

        String sqlWR_13 = "insert into tc_weekly_report_sum(rid, user_id, acnt_rid, begin_period, end_period, mon_hour, tue_hour, wed_hour, thu_hour, fri_hour, sat_hour, sun_hour, CONFIRM_STATUS)"
                          + " values(4567913, '" + EMP_LOGINID_1_P + "', " + ACNT_RID_121_3_P + ", to_date('2100-02-15','yyyy-MM-dd'), to_date('2100-02-21','yyyy-MM-dd'), 10, 15, 0, 0, 0, 0, 0, '" + DtoWeeklyReport.STATUS_CONFIRMED + "')";

        String sqlWR_14 = "insert into tc_weekly_report_sum(rid, user_id, acnt_rid, begin_period, end_period, mon_hour, tue_hour, wed_hour, thu_hour, fri_hour, sat_hour, sun_hour, CONFIRM_STATUS)"
                          + " values(4567914, '" + EMP_LOGINID_3_P + "', " + ACNT_RID_121_3_P + ", to_date('2100-02-08','yyyy-MM-dd'), to_date('2100-02-14','yyyy-MM-dd'), 10, 9, 0, 0, 0, 0, 0, '" + DtoWeeklyReport.STATUS_CONFIRMED + "')";

        String sqlWR_15 = "insert into tc_weekly_report_sum(rid, user_id, acnt_rid, begin_period, end_period, mon_hour, tue_hour, wed_hour, thu_hour, fri_hour, sat_hour, sun_hour, CONFIRM_STATUS)"
                          + " values(4567915, '" + EMP_LOGINID_3_P + "', " + ACNT_RID_121_3_P + ", to_date('2100-01-08','yyyy-MM-dd'), to_date('2100-01-14','yyyy-MM-dd'), 10, 9, 8, 7, 5, 3, 0, '" + DtoWeeklyReport.STATUS_CONFIRMED + "')";

        String sqlWR_16 = "insert into tc_weekly_report_sum(rid, user_id, acnt_rid, begin_period, end_period, mon_hour, tue_hour, wed_hour, thu_hour, fri_hour, sat_hour, sun_hour, CONFIRM_STATUS)"
                          + " values(4567916, '" + EMP_LOGINID_3_P + "', " + ACNT_RID_121_3_P + ", to_date('2100-03-08','yyyy-MM-dd'), to_date('2100-03-14','yyyy-MM-dd'), 10, 9, 4, 5, 9, 0, 0, '" + DtoWeeklyReport.STATUS_CONFIRMED + "')";

        lgOrgUtRate.getDbAccessor().executeUpdate(sqlWR_01);
        lgOrgUtRate.getDbAccessor().executeUpdate(sqlWR_02);
        lgOrgUtRate.getDbAccessor().executeUpdate(sqlWR_03);
        lgOrgUtRate.getDbAccessor().executeUpdate(sqlWR_04);
        lgOrgUtRate.getDbAccessor().executeUpdate(sqlWR_05);
        lgOrgUtRate.getDbAccessor().executeUpdate(sqlWR_06);
        lgOrgUtRate.getDbAccessor().executeUpdate(sqlWR_07);
        lgOrgUtRate.getDbAccessor().executeUpdate(sqlWR_08);
        lgOrgUtRate.getDbAccessor().executeUpdate(sqlWR_09);
        lgOrgUtRate.getDbAccessor().executeUpdate(sqlWR_10);
        lgOrgUtRate.getDbAccessor().executeUpdate(sqlWR_11);
        lgOrgUtRate.getDbAccessor().executeUpdate(sqlWR_12);
        lgOrgUtRate.getDbAccessor().executeUpdate(sqlWR_13);
        lgOrgUtRate.getDbAccessor().executeUpdate(sqlWR_14);
        lgOrgUtRate.getDbAccessor().executeUpdate(sqlWR_15);
        lgOrgUtRate.getDbAccessor().executeUpdate(sqlWR_16);
    }

}
