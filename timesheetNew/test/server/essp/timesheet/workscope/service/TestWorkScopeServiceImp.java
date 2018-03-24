package server.essp.timesheet.workscope.service;

import java.util.ArrayList;
import java.util.List;
import c2s.essp.timesheet.workscope.DtoActivity;
import c2s.essp.timesheet.workscope.DtoAccount;
import junit.framework.TestCase;
import server.essp.timesheet.workscope.dao.IWorkScopeP3ApiDao;
import static org.easymock.EasyMock.*;
import java.util.Map;
import java.util.HashMap;
import db.essp.timesheet.TsAccount;
import db.essp.timesheet.TsLaborResource;
import db.essp.timesheet.TsCodeRelation;
import db.essp.timesheet.TsCodeType;
import db.essp.timesheet.TsCodeValue;
import c2s.essp.timesheet.code.DtoCodeValue;
import c2s.essp.timesheet.code.DtoCodeType;
import server.essp.timesheet.account.labor.dao.ILaborDao;
import server.essp.timesheet.code.coderelation.dao.ICodeRelationDao;
import server.essp.timesheet.code.codetype.dao.ICodeTypeDao;
import server.essp.timesheet.code.codevalue.dao.ICodeValueDao;
import c2s.essp.timesheet.code.DtoCodeKey;
import server.essp.timesheet.account.dao.IAccountDao;
import db.essp.timesheet.TsParameter;
import java.util.Date;
import server.essp.timesheet.preference.dao.IPreferenceDao;
import server.essp.timesheet.code.coderelation.service.ICodeRelationService;
import c2s.dto.ITreeNode;
import c2s.dto.DtoTreeNode;

public class TestWorkScopeServiceImp extends TestCase {
    private WorkScopeServiceImp workScopeServiceImp = null;
    private IAccountDao accountDaoMock;
    private IWorkScopeP3ApiDao workScopeP3DaoMock;
    private ILaborDao laborDaoMock;
    private ICodeRelationDao codeRelationMock;
    private ICodeTypeDao codeTypeMock;
    private ICodeValueDao codeValueMock;
    private IPreferenceDao preferenceMock;
    private ICodeRelationService codeRelationServiceMock;
    private static Map dataMap;

    static{
           dataMap = new HashMap();
           TsAccount tsAccount = new TsAccount();
           tsAccount.setRid(Long.valueOf(11));
           tsAccount.setAccountId("111");
           tsAccount.setAccountName("Test1");
           tsAccount.setCodeTypeRid(Long.valueOf(55));
           tsAccount.setDeptFlag("0");
           tsAccount.setManager("wh0607016");
           tsAccount.setAccountStatus(DtoCodeKey.ACCOUNT_NORMAL);
           dataMap.put("TsAccount1", tsAccount);
           tsAccount = new TsAccount();
           tsAccount.setRid(Long.valueOf(12));
           tsAccount.setAccountId("112");
           tsAccount.setAccountName("Test2");
           tsAccount.setCodeTypeRid(Long.valueOf(55));
           tsAccount.setDeptFlag("1");
           tsAccount.setManager("wh0607016");
           tsAccount.setAccountStatus(DtoCodeKey.ACCOUNT_NORMAL);
           dataMap.put("TsAccount2", tsAccount);

           tsAccount = new TsAccount();
           tsAccount.setRid(Long.valueOf(13));
           tsAccount.setAccountId("113");
           tsAccount.setAccountName("Test2");
           tsAccount.setCodeTypeRid(Long.valueOf(100));
           tsAccount.setDeptFlag("1");
           tsAccount.setManager("wh0607016");
           tsAccount.setAccountStatus(DtoCodeKey.ACCOUNT_NORMAL);
           dataMap.put("TsAccount3", tsAccount);


           List listResource = new ArrayList();
           TsLaborResource tsResource = new TsLaborResource();
           tsResource.setRid(Long.valueOf(98));
           tsResource.setAccountRid(Long.valueOf(11));
           tsResource.setLoginId("wh0607016");
           tsResource.setName("tbh");
           listResource.add(tsResource);
           tsResource = new TsLaborResource();
           tsResource.setRid(Long.valueOf(99));
           tsResource.setAccountRid(Long.valueOf(12));
           tsResource.setLoginId("wh0607016");
           tsResource.setName("tbh");
           listResource.add(tsResource);
           dataMap.put("resourceList", listResource);

           List listRelation = new ArrayList();
           TsCodeRelation tsRelation = new TsCodeRelation();
           tsRelation.setRid(Long.valueOf(88));
           tsRelation.setTypeRid(Long.valueOf(55));
           tsRelation.setValueRid(Long.valueOf(1));
           listRelation.add(tsRelation);
           dataMap.put("relationList", listRelation);

           TsCodeType tsType = new TsCodeType();
           tsType.setRid(Long.valueOf(55));
           tsType.setDescription("CodeType Description");
           tsType.setName("CodeType Name");
           tsType.setIsEnable(true);
           dataMap.put("tsCodeType", tsType);

           List listCodeValue = new ArrayList();
           TsCodeValue tsCodeValue = new TsCodeValue();
           tsCodeValue.setRid(Long.valueOf(1));
           tsCodeValue.setIsLeaveType(true);
           tsCodeValue.setDescription("sales surport");
           tsCodeValue.setName("SS--sales suport");
           tsCodeValue.setIsEnable(true);
           tsCodeValue.setIsLeaveType(true);
           listCodeValue.add(tsCodeValue);
           tsCodeValue = new TsCodeValue();
           tsCodeValue.setRid(Long.valueOf(2));
           tsCodeValue.setIsLeaveType(true);
           tsCodeValue.setDescription("private leave");
           tsCodeValue.setName("PL--private leave");
           tsCodeValue.setIsEnable(true);
           tsCodeValue.setIsLeaveType(true);
           listCodeValue.add(tsCodeValue);
           dataMap.put("codeValueList", listCodeValue);

           List listActivity = new ArrayList();
           DtoActivity dtoActivity = new DtoActivity();
           dtoActivity.setCode("As22");
           dtoActivity.setId(Integer.valueOf(110));
           dtoActivity.setName("ActivityName");
           dtoActivity.setProjectId(Integer.valueOf(990));
           dtoActivity.setPlannedStartDate(new Date());
           dtoActivity.setPlannedFinishDate(new Date());
           listActivity.add(dtoActivity);
           dataMap.put("listActivity", listActivity);

          DtoAccount dtoAccount = new DtoAccount();
          dtoAccount.setCode("111");
          dtoAccount.setIsP3(true);
          dtoAccount.setName("P3Account");
          dtoAccount.setId(Integer.valueOf(990));
          dtoAccount.setCodeTypeRid(Long.valueOf(55));
          dtoAccount.setActivities(new ArrayList());
          dtoAccount.setCodeTypeList(new ArrayList());
          dataMap.put("DtoAccountP3", dtoAccount);

          tsAccount = new TsAccount();
          tsAccount.setRid(Long.valueOf(13));
          tsAccount.setAccountId("990");
          tsAccount.setAccountName("Test2");
          tsAccount.setCodeTypeRid(Long.valueOf(55));
          dataMap.put("tsAccount", tsAccount);

          TsParameter tsParameter = new TsParameter();
          tsParameter.setXferCompleteDayCnt(Long.valueOf(2));
          tsParameter.setXferNostartDayCnt(Long.valueOf(3));
          tsParameter.setRid(Long.valueOf(00052));
          dataMap.put("tsParameter", tsParameter);

          DtoCodeValue dto = new DtoCodeValue();
          dto.setRid(Long.valueOf(100));
          dto.setIsEnable(true);
          ITreeNode root = new DtoTreeNode(dto);
          DtoCodeValue dto2 = new DtoCodeValue();
          dto2.setRid(Long.valueOf(101));
          dto2.setIsEnable(false);
          dto2.setName("CodeValue Name");
          dto2.setDescription("CodeValue Description");
          root.addChild(new DtoTreeNode(dto2));
          dataMap.put("root", root);

          List relList = new ArrayList();
          TsCodeRelation tsRel = new TsCodeRelation();
          tsRel.setRid(Long.valueOf(200));
          tsRel.setTypeRid(Long.valueOf(100));
          tsRel.setValueRid(Long.valueOf(100));
          relList.add(tsRel);
          tsRel.setRid(Long.valueOf(201));
          tsRel.setTypeRid(Long.valueOf(100));
          tsRel.setValueRid(Long.valueOf(101));
          relList.add(tsRel);
          dataMap.put("relList", relList);

      }

    public TestWorkScopeServiceImp(String name) {
        super(name);
    }

    protected void setUp() throws Exception {
        super.setUp();
        workScopeServiceImp = new WorkScopeServiceImp();
        accountDaoMock = createMock(IAccountDao.class);
        workScopeP3DaoMock = createMock(IWorkScopeP3ApiDao.class);
        laborDaoMock = createMock(ILaborDao.class);
        codeRelationMock = createMock(ICodeRelationDao.class);
        codeTypeMock = createMock(ICodeTypeDao.class);
        codeValueMock = createMock(ICodeValueDao.class);
        preferenceMock = createMock(IPreferenceDao.class);
        codeRelationServiceMock = createMock(ICodeRelationService.class);
    }

    protected void tearDown() throws Exception {
        workScopeServiceImp = null;
        super.tearDown();
    }

    /**
     * 列出所有符合条件的ACCOUNT记录
     * @throws Exception
     */
    public void testGetAccountListSuccess() throws Exception {
        List resourceList = (List)dataMap.get("resourceList");
        TsAccount tsAccount1 = (TsAccount)dataMap.get("TsAccount1");
        TsAccount tsAccount2 = (TsAccount)dataMap.get("TsAccount2");
        List relationList = (List)dataMap.get("relationList");
        TsCodeType tsCodeType = (TsCodeType)dataMap.get("tsCodeType");

        String userLoginId = "wh0607016";
//        expect(laborDaoMock.getLaborResource(userLoginId)).andReturn(resourceList);
        expect(accountDaoMock.loadAccount(Long.valueOf(11))).andReturn(tsAccount1);
        expect(accountDaoMock.loadAccount(Long.valueOf(12))).andReturn(tsAccount2);
        expect(codeRelationMock.listRelation(Long.valueOf(55), "0")).andReturn(relationList).anyTimes();
        expect(codeTypeMock.getCodeType(Long.valueOf(55))).andReturn(tsCodeType).anyTimes();


        List activityList = (List)dataMap.get("listActivity");
        TsParameter tsParameter = (TsParameter)dataMap.get("tsParameter");
        DtoAccount dtoAccountP3 = (DtoAccount)dataMap.get("DtoAccountP3");
        TsAccount tsAccount = (TsAccount)dataMap.get("tsAccount");
        expect(accountDaoMock.loadByAccountId("990")).andReturn(tsAccount).anyTimes();
        expect(preferenceMock.loadPreference()).andReturn(tsParameter);
//        expect(workScopeP3DaoMock.getActivityList(null,null)).andReturn(activityList);
        expect(workScopeP3DaoMock.getAccount(Integer.valueOf(990))).
                    andReturn(dtoAccountP3);
        replay(accountDaoMock);
        replay(workScopeP3DaoMock);
        replay(laborDaoMock);
        replay(codeRelationMock);
        replay(codeTypeMock);
        replay(codeValueMock);
        workScopeServiceImp.setAccountDao(accountDaoMock);
        workScopeServiceImp.setWorkScopeP3ApiDao(workScopeP3DaoMock);
        workScopeServiceImp.setCodeRelationDao(codeRelationMock);
        workScopeServiceImp.setPreferenceDao(preferenceMock);
        List listAcnt = workScopeServiceImp.getAccountList(userLoginId);
        assertNotNull("Account list is not null", listAcnt);
        assertEquals("Account list size is 3", 3, listAcnt.size());
        DtoAccount dtoP111 = (DtoAccount) listAcnt.get(0);
        assertEquals("Account 111 id is right", new Integer(11),
                          dtoP111.getId());
        assertEquals("Account 111 code is right", "111",
                          dtoP111.getCode());
        assertEquals("Account 111 name is right", "Test1",
                          dtoP111.getName());

        DtoAccount dtoP3 = (DtoAccount) listAcnt.get(2);
        assertEquals("dtoP3 name is right","P3Account",dtoP3.getName());
        assertEquals("dtoP3 code is right","111",dtoP3.getCode());
        assertEquals("dtoP3 isP3 is right",true,dtoP3.getIsP3());

        List listActivity = dtoP3.getActivities();
        assertNotNull("listActivity is not null",listActivity);
        DtoActivity dtoActivity = (DtoActivity)listActivity.get(0);
        assertEquals("activity code is right","As22",dtoActivity.getCode());
        assertEquals("activity name is right","ActivityName",dtoActivity.getName());
        assertEquals("activity projectId is right",Integer.valueOf(990),dtoActivity.getProjectId());
    }

     //测试根据选中某一条P3的ACCOUNT记录显示对应的CodeTypeList
      public void testGetCodeTypeListFromP3() throws Exception {
          TsAccount tsAccount = (TsAccount)dataMap.get("TsAccount3");
          ITreeNode node = (ITreeNode)dataMap.get("root");
          List relList = (List)dataMap.get("relList");
          expect(accountDaoMock.loadByAccountId("113")).andReturn(tsAccount).anyTimes();
          expect(codeRelationServiceMock.getRelationTree(Long.valueOf(100), false)).andReturn(node).anyTimes();
          expect(codeRelationMock.listRelation(Long.valueOf(100), "0")).andReturn(relList);
          replay(accountDaoMock);
          replay(codeRelationMock);
          replay(codeRelationServiceMock);

          workScopeServiceImp.setAccountDao(accountDaoMock);
          workScopeServiceImp.setCodeRelationDao(codeRelationMock);
          workScopeServiceImp.setCodeRelationService(codeRelationServiceMock);
          List listCodeType = workScopeServiceImp.getJobCode(Long.valueOf(01));
//          assertNotNull("listAcnt is not null",listCodeType);
      }

      //列出所有的假别记录
      public void testGetLeaveCodeList() throws Exception {
       List codeLeaveList = (List)dataMap.get("codeValueList");
       expect(codeValueMock.getLeaveCode()).andReturn(codeLeaveList).anyTimes();
       replay(codeValueMock);
       List listLeaveCode = workScopeServiceImp.getCodeLeave(Long.valueOf(1));
       assertNotNull("listLeaveCode is not null",listLeaveCode);
       assertEquals("listLeaveCode size is right", 2, listLeaveCode.size());
       DtoCodeValue dtoCodeValue = (DtoCodeValue)listLeaveCode.get(0);
       assertEquals("LeaveCode description  is right","sales surport",dtoCodeValue.getDescription());
       assertEquals("LeaveCode name is right", "SS--sales suport",dtoCodeValue.getName());

   }

    /**
     * 查询出的结果为空的情况
     * @throws Exception
     */
    public void testGetAccountListNull() throws Exception {
      String userLoginId = "wh0607018";
      TsParameter tsPara = new TsParameter();
      tsPara.setXferCompleteDayCnt(Long.valueOf(0));
      tsPara.setXferNostartDayCnt(Long.valueOf(0));
//      expect(laborDaoMock.getLaborResource(userLoginId)).andReturn(new ArrayList());
      expect(accountDaoMock.loadAccount(Long.valueOf(11))).andReturn(new TsAccount());
      expect(workScopeP3DaoMock.getAccount(Integer.valueOf(10))).andReturn(new DtoAccount());
      expect(preferenceMock.loadPreference()).andReturn(tsPara);
//      expect(workScopeP3DaoMock.getActivityList(null,null)).andReturn(new ArrayList());
      replay(accountDaoMock);
      replay(workScopeP3DaoMock);
      replay(laborDaoMock);
      replay(preferenceMock);
      workScopeServiceImp.setAccountDao(accountDaoMock);
      workScopeServiceImp.setWorkScopeP3ApiDao(workScopeP3DaoMock);
      workScopeServiceImp.setPreferenceDao(preferenceMock);
      List listAcnt = workScopeServiceImp.getAccountList(userLoginId);
      assertEquals("listAcnt size is 0",0,listAcnt.size());
     }
}
