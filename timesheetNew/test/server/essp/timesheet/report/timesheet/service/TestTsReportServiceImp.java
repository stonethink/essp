package server.essp.timesheet.report.timesheet.service;

import junit.framework.TestCase;
import static org.easymock.EasyMock.*;
import server.essp.timesheet.account.dao.IAccountDao;
import server.essp.timesheet.report.timesheet.dao.ITsReportDao;
import server.essp.timesheet.account.labor.dao.ILaborDao;
import java.util.Map;
import java.util.HashMap;
import db.essp.timesheet.TsAccount;
import java.util.List;
import java.util.ArrayList;
import db.essp.timesheet.TsLaborResource;
import c2s.essp.timesheet.report.DtoQueryCondition;
import com.wits.util.comDate;
import c2s.essp.timesheet.report.DtoTsDetailReport;
import c2s.essp.common.user.DtoUser;
import com.wits.util.ThreadVariant;
import java.util.Vector;
import c2s.dto.DtoComboItem;
import java.util.Date;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author wenhaizheng
 * @version 1.0
 */
public class TestTsReportServiceImp extends TestCase {
    private TsReportServiceImp tsReportService;
    private ITsReportDao tsReportDaoMock;
    private IAccountDao accountDaoMock;
    private ILaborDao laborDaoMock;
    private static Map<String, Object> dataMap;

static{
    dataMap = new HashMap<String, Object>();
    List<TsAccount> deptList = new ArrayList();
    TsAccount tsAccount = new TsAccount();
    tsAccount.setAccountId("W6000");
    deptList.add(tsAccount);
    dataMap.put("deptList", deptList);
    tsAccount = new TsAccount();
    tsAccount.setAccountId("W6000");
    tsAccount.setAccountName("TestDept");
    tsAccount.setRid(Long.valueOf(6));
    dataMap.put("tsAccount", tsAccount);
    tsAccount = new TsAccount();
    tsAccount.setAccountId("W6006");
    tsAccount.setAccountName("TestDept2");
    dataMap.put("dept", tsAccount);
    List<TsLaborResource> personList = new ArrayList<TsLaborResource>();
    TsLaborResource person = new TsLaborResource();
    person.setLoginId("WH0607015");
    person.setName("wenhaizheng");
    personList.add(person);
    dataMap.put("personList", personList);
    List<TsAccount> projectList = new ArrayList<TsAccount>();
    tsAccount = new TsAccount();
    tsAccount.setAccountId("006600W");
    tsAccount.setAccountName("TestProject");
    tsAccount.setRid(Long.valueOf(66));
    dataMap.put("project", tsAccount);
    projectList.add(tsAccount);
    dataMap.put("projectList", projectList);
    List<TsAccount> projectListByManager = new ArrayList<TsAccount>();
    tsAccount = new TsAccount();
    tsAccount.setAccountId("006600W");
    tsAccount.setOrgCode("W6000");
    tsAccount.setAccountName("TestProject");
    projectListByManager.add(tsAccount);
    tsAccount = new TsAccount();
    tsAccount.setAccountId("006606W");
    tsAccount.setOrgCode("W6006");
    tsAccount.setAccountName("TestProject2");
    dataMap.put("project2", tsAccount);
    projectListByManager.add(tsAccount);
    dataMap.put("projectListByManager", projectListByManager);
    Object[] obj = new Object[9];
    obj[0] = comDate.toDate("2006-06-06", "yyyy-MM-dd");
    obj[1] = "006600W";
    obj[2] = "TestProject";
    obj[3] = "WH0607015";
    obj[4] = "01";
    obj[5] = false;
    obj[6] = "Coding";
    obj[7] = Double.valueOf(8.0);
    obj[8] = Double.valueOf(0);
    List tsDetailReportList = new ArrayList();
    tsDetailReportList.add(obj);
    dataMap.put("tsDetailReportList", tsDetailReportList);
    Vector<DtoComboItem> projectItem = new Vector<DtoComboItem>();
    DtoComboItem item = new DtoComboItem("Select All", "");
    projectItem.add(item);
    item = new DtoComboItem("006600W--TestProject", "006600W");
    projectItem.add(item);
    dataMap.put("projectItem", projectItem);
    Vector<DtoComboItem> personItem = new Vector<DtoComboItem>();
    item = new DtoComboItem("Select All", "");
    personItem.add(item);
    item = new DtoComboItem("wenhaizheng", "WH0607015");
    personItem.add(item);
    dataMap.put("personItem", personItem);
    obj = new Object[9];
    obj[0] = "W3501";
    obj[1] = "006600W";
    obj[2] = "TestProject";
    obj[3] = Double.valueOf(176);
    obj[4] = false;
    obj[5] = "0";
    List tsGatherReportList = new ArrayList();
    tsGatherReportList.add(obj);
    dataMap.put("tsGatherReportList", tsGatherReportList);
    Object[] objSum = new Object[2];
    objSum[0] = Double.valueOf(40);
    objSum[1] = Double.valueOf(10);
    List sumList = new ArrayList();
    sumList.add(objSum);
    dataMap.put("sumList", sumList);
}

    protected void setUp() throws Exception {
        super.setUp();
        tsReportService = new TsReportServiceImp();
        accountDaoMock = createMock(IAccountDao.class);
        tsReportDaoMock = createMock(ITsReportDao.class);
        laborDaoMock = createMock(ILaborDao.class);
    }

    protected void tearDown() throws Exception {
        tsReportService = null;
        super.tearDown();
    }
    public void testGetDeptsPersons() {
        expect(accountDaoMock.listDeptByManager("WH0607015"))
                .andReturn((List)dataMap.get("deptList"));
        expect(accountDaoMock.listChildrenDeptByParentId("W6000"))
                .andReturn(new ArrayList());
        expect(accountDaoMock.loadByAccountId("W6000"))
                .andReturn((TsAccount)dataMap.get("tsAccount")).anyTimes();
        expect(laborDaoMock.listLabor(Long.valueOf(6)))
                .andReturn((List)dataMap.get("personList"));
        replay(accountDaoMock);
        replay(laborDaoMock);
        tsReportService.setAccountDao(accountDaoMock);
        tsReportService.setLaborDao(laborDaoMock);
        Map result = tsReportService.getDeptsPersons("WH0607015");
        assertNotNull("result dept is null",result.get(DtoTsDetailReport.DTO_DEPT_LIST));
        assertNotNull("result person is null",result.get(DtoTsDetailReport.DTO_PERSON_LIST));
        assertNotNull("result relation is null",result.get(DtoTsDetailReport.DTO_RELATION_MAP));

    }
    public void testGetDeptsProjects() {
        expect(accountDaoMock.listDeptByManager("WH0607015"))
                .andReturn((List)dataMap.get("deptList"));
        expect(accountDaoMock.listChildrenDeptByParentId("W6000"))
                .andReturn(new ArrayList());
        expect(accountDaoMock.listProjectsByDept("('W6000')"))
                .andReturn((List)dataMap.get("projectList"));
        expect(accountDaoMock.listProjects("WH0607015"))
                .andReturn((List)dataMap.get("projectListByManager"));
        expect(accountDaoMock.loadByAccountId("W6000"))
                .andReturn((TsAccount)dataMap.get("tsAccount")).anyTimes();
        expect(accountDaoMock.loadByAccountId("W6006"))
                .andReturn((TsAccount)dataMap.get("dept")).anyTimes();
        expect(accountDaoMock.loadByAccountId("006600W"))
                .andReturn((TsAccount)dataMap.get("project")).anyTimes();
        expect(accountDaoMock.loadByAccountId("006606W"))
                .andReturn((TsAccount)dataMap.get("project2")).anyTimes();
        replay(accountDaoMock);
        tsReportService.setAccountDao(accountDaoMock);
        Map result = tsReportService.getDeptsProjects("WH0607015");
        assertNotNull("result dept is null",result.get(DtoTsDetailReport.DTO_DEPT_LIST));
        assertNotNull("result project is null",result.get(DtoTsDetailReport.DTO_PROJECT_LIST));
        assertNotNull("result relation is null",result.get(DtoTsDetailReport.DTO_RELATION_MAP));
    }
    public void testQueryReport_emp() {
        DtoQueryCondition dtoCondition = new DtoQueryCondition();
        dtoCondition.setQueryWay(DtoQueryCondition.DTO_QUERY_WAY_EMP);
        expect(tsReportDaoMock.queryReport(dtoCondition, "('WH0607015')", ""))
                .andReturn((List)dataMap.get("tsDetailReportList"));
        replay(tsReportDaoMock);
        tsReportService.setTsReportDao(tsReportDaoMock);
        tsReportService.setExcelDto(false);
        ThreadVariant thread = ThreadVariant.getInstance();
        DtoUser user = new DtoUser();
        user.setUserLoginId("WH0607015");
        user.setPassword("666");
        user.setDomain("wh");
        user.setUserName("wenhaizheng");
        thread.set(DtoUser.SESSION_USER, user);
        thread.set(DtoUser.SESSION_LOGIN_USER, user);

        List result = tsReportService.queryReport(dtoCondition, "WH0607015");
        assertNotNull("result is null", result);
    }
    public void testQueryReport_pm_all() {
        DtoQueryCondition dtoCondition = new DtoQueryCondition();
        dtoCondition.setProjectId("");
        dtoCondition.setProjectItem((Vector)dataMap.get("projectItem"));
        dtoCondition.setQueryWay(DtoQueryCondition.DTO_QUERY_WAY_PM);
        expect(accountDaoMock.loadByAccountId("006600W"))
                .andReturn((TsAccount)dataMap.get("project"));
        expect(accountDaoMock.loadByAccountId(""))
                .andReturn(new TsAccount());
        expect(laborDaoMock.listLabor(Long.valueOf(66)))
                .andReturn((List)dataMap.get("personList"));
        expect(laborDaoMock.listLabor(null))
                .andReturn(null);
        expect(tsReportDaoMock.queryReport(dtoCondition, "('WH0607015')", "order by a.accountId"))
                .andReturn((List)dataMap.get("tsDetailReportList"));
        replay(accountDaoMock);
        replay(laborDaoMock);
        replay(tsReportDaoMock);
        tsReportService.setExcelDto(false);
        tsReportService.setAccountDao(accountDaoMock);
        tsReportService.setLaborDao(laborDaoMock);
        tsReportService.setTsReportDao(tsReportDaoMock);
        List result = tsReportService.queryReport(dtoCondition, "WH0607015");
        assertNotNull("result is null", result);
    }
    public void testQueryReport_pm_one(){
        DtoQueryCondition dtoCondition = new DtoQueryCondition();
        dtoCondition.setProjectId("006600W");
        dtoCondition.setQueryWay(DtoQueryCondition.DTO_QUERY_WAY_PM);
        expect(accountDaoMock.loadByAccountId("006600W"))
                .andReturn((TsAccount)dataMap.get("project"));
        expect(laborDaoMock.listLabor(Long.valueOf(66)))
                .andReturn((List)dataMap.get("personList"));
        expect(tsReportDaoMock.queryReport(dtoCondition, "('WH0607015')", "order by a.accountId"))
                .andReturn((List)dataMap.get("tsDetailReportList"));
        replay(accountDaoMock);
        replay(laborDaoMock);
        replay(tsReportDaoMock);
        tsReportService.setExcelDto(false);
        tsReportService.setAccountDao(accountDaoMock);
        tsReportService.setLaborDao(laborDaoMock);
        tsReportService.setTsReportDao(tsReportDaoMock);
        List result = tsReportService.queryReport(dtoCondition, "WH0607015");
        assertNotNull("result is null", result);
    }
    public void testQueryReport_rmp_all(){
        DtoQueryCondition dtoCondition = new DtoQueryCondition();
        dtoCondition.setQueryWay(DtoQueryCondition.DTO_QUERY_WAY_RMP);
        dtoCondition.setLoginId("");
        dtoCondition.setPersonItem((Vector)dataMap.get("personItem"));
        expect(tsReportDaoMock.queryReport(dtoCondition, "('', 'WH0607015')", "order by m.loginId"))
                .andReturn((List)dataMap.get("tsDetailReportList"));
        replay(tsReportDaoMock);
        tsReportService.setTsReportDao(tsReportDaoMock);
        tsReportService.setExcelDto(false);
        List result = tsReportService.queryReport(dtoCondition, "WH0607015");
        assertNotNull("result is null", result);
    }
    public void testQueryReport_rmp_one(){
        DtoQueryCondition dtoCondition = new DtoQueryCondition();
        dtoCondition.setQueryWay(DtoQueryCondition.DTO_QUERY_WAY_RMP);
        dtoCondition.setLoginId("WH0607015");
        expect(tsReportDaoMock.queryReport(dtoCondition, "('WH0607015')",
                                           "order by m.loginId"))
                .andReturn((List)dataMap.get("tsDetailReportList"));
        replay(tsReportDaoMock);
        tsReportService.setTsReportDao(tsReportDaoMock);
        tsReportService.setExcelDto(false);
        List result = tsReportService.queryReport(dtoCondition, "WH0607015");
        assertNotNull("result is null", result);

    }
    public void testQueryGatherReport_all() {
        Date begin = comDate.toDate("2006-06-01", "yyyy-MM-dd");
        Date end = comDate.toDate("2006-06-07", "yyyy-MM-dd");
        DtoQueryCondition dtoCondition = new DtoQueryCondition();
        dtoCondition.setProjectId("");
        dtoCondition.setBegin(begin);
        dtoCondition.setEnd(end);
        dtoCondition.setProjectItem((Vector)dataMap.get("projectItem"));
        expect(tsReportDaoMock.queryGatherReport(dtoCondition, "('', '006600W')"))
                .andReturn((List)dataMap.get("tsGatherReportList"));
        expect(tsReportDaoMock.getSum("006600W",begin,end, "0"))
                .andReturn((List)dataMap.get("sumList"));
        replay(tsReportDaoMock);
        tsReportService.setTsReportDao(tsReportDaoMock);
        tsReportService.setExcelDto(false);
        List result = tsReportService.queryGatherReport(dtoCondition, "WH0607015");
        assertNotNull("result is null", result);

    }
     public void testQueryGatherReport_one() {
         Date begin = comDate.toDate("2006-06-01", "yyyy-MM-dd");
         Date end = comDate.toDate("2006-06-07", "yyyy-MM-dd");
         DtoQueryCondition dtoCondition = new DtoQueryCondition();
         dtoCondition.setProjectId("006600W");
         dtoCondition.setBegin(begin);
         dtoCondition.setEnd(end);
         expect(tsReportDaoMock.queryGatherReport(dtoCondition, "('006600W')"))
                 .andReturn((List) dataMap.get("tsGatherReportList"));
         expect(tsReportDaoMock.getSum("006600W", begin, end, "0"))
                 .andReturn((List) dataMap.get("sumList"));
         replay(tsReportDaoMock);
         tsReportService.setTsReportDao(tsReportDaoMock);
         tsReportService.setExcelDto(false);
         List result = tsReportService.queryGatherReport(dtoCondition, "WH0607015");
         assertNotNull("result is null", result);

     }
     public void testQueryForExport_emp() {
         DtoQueryCondition dtoCondition = new DtoQueryCondition();
        dtoCondition.setQueryWay(DtoQueryCondition.DTO_QUERY_WAY_EMP);
        expect(tsReportDaoMock.queryReport(dtoCondition, "('WH0607015')", ""))
                .andReturn((List)dataMap.get("tsDetailReportList"));
        replay(tsReportDaoMock);
        tsReportService.setTsReportDao(tsReportDaoMock);
        tsReportService.setExcelDto(true);
        ThreadVariant thread = ThreadVariant.getInstance();
        DtoUser user = new DtoUser();
        user.setUserLoginId("WH0607015");
        user.setPassword("666");
        user.setDomain("wh");
        user.setUserName("wenhaizheng");
        thread.set(DtoUser.SESSION_USER, user);
        thread.set(DtoUser.SESSION_LOGIN_USER, user);

        List result = tsReportService.queryReport(dtoCondition, "WH0607015");
        assertNotNull("result is null", result);

     }
      public void testQueryForExport_pm() {
          DtoQueryCondition dtoCondition = new DtoQueryCondition();
       dtoCondition.setProjectId("");
       dtoCondition.setDeptId("");
       dtoCondition.setProjectItem((Vector)dataMap.get("projectItem"));
       dtoCondition.setQueryWay(DtoQueryCondition.DTO_QUERY_WAY_PM);
       expect(accountDaoMock.loadByAccountId("006600W"))
               .andReturn((TsAccount)dataMap.get("project"));
       expect(accountDaoMock.loadByAccountId(""))
               .andReturn(new TsAccount());
       expect(laborDaoMock.listLabor(Long.valueOf(66)))
               .andReturn((List)dataMap.get("personList")).anyTimes();
       expect(laborDaoMock.listLabor(null))
               .andReturn(null).anyTimes();
       expect(tsReportDaoMock.queryReport(dtoCondition, "('WH0607015')", "order by a.accountId"))
               .andReturn((List)dataMap.get("tsDetailReportList"));
       expect(accountDaoMock.listDeptByManager("WH0607015"))
               .andReturn((List)dataMap.get("deptList"));
       expect(accountDaoMock.listChildrenDeptByParentId("W6000"))
               .andReturn(new ArrayList());
       expect(accountDaoMock.listProjectsByDept("('W6000')"))
               .andReturn((List)dataMap.get("projectList"));
       expect(accountDaoMock.listProjects("WH0607015"))
               .andReturn((List)dataMap.get("projectListByManager"));
       expect(accountDaoMock.loadByAccountId("W6000"))
               .andReturn((TsAccount)dataMap.get("tsAccount")).anyTimes();
       expect(accountDaoMock.loadByAccountId("W6006"))
               .andReturn((TsAccount)dataMap.get("dept")).anyTimes();
       expect(accountDaoMock.loadByAccountId("006600W"))
               .andReturn((TsAccount)dataMap.get("project")).anyTimes();
       expect(accountDaoMock.loadByAccountId("006606W"))
               .andReturn((TsAccount)dataMap.get("project2")).anyTimes();

       replay(accountDaoMock);
       replay(laborDaoMock);
       replay(tsReportDaoMock);
       tsReportService.setExcelDto(true);
       tsReportService.setAccountDao(accountDaoMock);
       tsReportService.setLaborDao(laborDaoMock);
       tsReportService.setTsReportDao(tsReportDaoMock);
       List result = tsReportService.queryForExport(dtoCondition, "WH0607015", false);
       assertNotNull("result is null", result);

      }
      public void testQueryForExport_rmp() {
          DtoQueryCondition dtoCondition = new DtoQueryCondition();
          dtoCondition.setQueryWay(DtoQueryCondition.DTO_QUERY_WAY_RMP);
          dtoCondition.setLoginId("");
          dtoCondition.setDeptId("");
          dtoCondition.setPersonItem((Vector) dataMap.get("personItem"));
          expect(tsReportDaoMock.queryReport(dtoCondition, "('', 'WH0607015')",
                                             "order by m.loginId"))
                  .andReturn((List) dataMap.get("tsDetailReportList"));
          expect(accountDaoMock.listDeptByManager("WH0607015"))
                  .andReturn((List) dataMap.get("deptList"));
          expect(accountDaoMock.listChildrenDeptByParentId("W6000"))
                  .andReturn(new ArrayList());
          expect(accountDaoMock.loadByAccountId("W6000"))
                  .andReturn((TsAccount) dataMap.get("tsAccount")).anyTimes();
          expect(laborDaoMock.listLabor(Long.valueOf(6)))
                  .andReturn((List) dataMap.get("personList"));

          replay(tsReportDaoMock);
          replay(laborDaoMock);
          replay(accountDaoMock);
          tsReportService.setTsReportDao(tsReportDaoMock);
          tsReportService.setLaborDao(laborDaoMock);
          tsReportService.setAccountDao(accountDaoMock);
          tsReportService.setExcelDto(true);
          List result = tsReportService.queryForExport(dtoCondition, "WH0607015", false);
          assertNotNull("result is null", result);

      }
      public void testQueryGatherForExport() {
          Date begin = comDate.toDate("2006-06-01", "yyyy-MM-dd");
          Date end = comDate.toDate("2006-06-07", "yyyy-MM-dd");
          DtoQueryCondition dtoCondition = new DtoQueryCondition();
          dtoCondition.setProjectId("");
          dtoCondition.setDeptId("");
          dtoCondition.setBegin(begin);
          dtoCondition.setEnd(end);
          dtoCondition.setProjectItem((Vector) dataMap.get("projectItem"));
          expect(tsReportDaoMock.queryGatherReport(dtoCondition,
                  "('', '006600W', '006606W')"))
                  .andReturn((List) dataMap.get("tsGatherReportList"));
          expect(tsReportDaoMock.getSum("006600W", begin, end, "0"))
                  .andReturn((List) dataMap.get("sumList"));
          expect(accountDaoMock.listDeptByManager("WH0607015"))
                  .andReturn((List) dataMap.get("deptList"));
          expect(accountDaoMock.listChildrenDeptByParentId("W6000"))
                  .andReturn(new ArrayList());
          expect(accountDaoMock.listProjectsByDept("('W6000')"))
                  .andReturn((List) dataMap.get("projectList"));
          expect(accountDaoMock.listProjects("WH0607015"))
                  .andReturn((List) dataMap.get("projectListByManager"));
          expect(accountDaoMock.loadByAccountId("W6000"))
                  .andReturn((TsAccount) dataMap.get("tsAccount")).anyTimes();
          expect(accountDaoMock.loadByAccountId("W6006"))
                  .andReturn((TsAccount) dataMap.get("dept")).anyTimes();
          expect(accountDaoMock.loadByAccountId("006600W"))
                  .andReturn((TsAccount) dataMap.get("project")).anyTimes();
          expect(accountDaoMock.loadByAccountId("006606W"))
                  .andReturn((TsAccount) dataMap.get("project2")).anyTimes();
          replay(tsReportDaoMock);
          replay(accountDaoMock);
          tsReportService.setTsReportDao(tsReportDaoMock);
          tsReportService.setAccountDao(accountDaoMock);
          tsReportService.setExcelDto(true);
          List result = tsReportService.queryGatherForExport(dtoCondition, "WH0607015", false);
          assertNotNull("result is null", result);

      }








}
