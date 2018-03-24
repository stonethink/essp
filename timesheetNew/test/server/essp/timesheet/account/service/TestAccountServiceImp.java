package server.essp.timesheet.account.service;

import junit.framework.TestCase;
import server.essp.timesheet.account.dao.IAccountDao;
import server.essp.timesheet.code.codetype.dao.ICodeTypeDao;

import java.util.List;
import db.essp.timesheet.TsAccount;
import java.util.ArrayList;
import com.wits.util.comDate;
import c2s.essp.timesheet.account.DtoAccount;

import static org.easymock.EasyMock.*;
import java.util.Map;
import java.util.HashMap;

public class TestAccountServiceImp extends TestCase {
     private AccountServiceImp accountServiceImp;
     private IAccountDao accountDaoMock;
     private ICodeTypeDao codeTypeDaoMock;
     private static Map<String, Object> dataMap;

static{
            dataMap = new HashMap<String, Object>();
            List list = new ArrayList();
            TsAccount tsAccount = new TsAccount();
            tsAccount.setRid(Long.valueOf(1));
            tsAccount.setAccountId("TestAccountId1");
            tsAccount.setAccountName("TestAccountName1");
            tsAccount.setAccountStatus("Normal");
            tsAccount.setAccountType("TestType1");
            tsAccount.setPlannedStart(comDate.toDate("2007-01-01", "yyyy-MM-dd"));
            tsAccount.setPlannedFinish(comDate.toDate("2007-12-31","yyyy-MM-dd"));
            tsAccount.setActualStart(comDate.toDate("2007-01-02","yyyy-MM-dd"));
            tsAccount.setActualFinish(comDate.toDate("2007-12-30","yyyy-MM-dd"));
            tsAccount.setManager("wh0607015");
            tsAccount.setAccountBrief("TestBrief1");
            dataMap.put("AccountData", tsAccount);
            list.add(tsAccount);
            tsAccount = new TsAccount();
            tsAccount.setRid(Long.valueOf(2));
            tsAccount.setAccountId("TestAccountId2");
            tsAccount.setAccountName("TestAccountName2");
            tsAccount.setAccountStatus("Normal");
            tsAccount.setAccountType("TestType2");
            tsAccount.setPlannedStart(comDate.toDate("2007-01-01", "yyyy-MM-dd"));
            tsAccount.setPlannedFinish(comDate.toDate("2007-12-31","yyyy-MM-dd"));
            tsAccount.setActualStart(comDate.toDate("2007-01-02","yyyy-MM-dd"));
            tsAccount.setActualFinish(comDate.toDate("2007-12-30","yyyy-MM-dd"));
            tsAccount.setManager("wh0607015");
            tsAccount.setAccountBrief("TestBrief2");
            list.add(tsAccount);
            dataMap.put("AccountList", list);
}
     protected void setUp() throws Exception {
         super.setUp();
         accountServiceImp = new AccountServiceImp();
         accountDaoMock = createMock(IAccountDao.class);
         codeTypeDaoMock = createMock(ICodeTypeDao.class);
     }
     protected void tearDown() throws Exception {
          accountServiceImp = null;
          super.tearDown();
     }

    public void testListAccounts_hasdata(){
        List dataList = (List)dataMap.get("AccountList");
        String userLoginId = "wh0607015";
        expect(accountDaoMock.listAccounts(userLoginId)).andReturn(dataList);
        expect(accountDaoMock.sumWorkHoursByAccount(Long.valueOf(1), userLoginId))
                .andReturn(Double.valueOf(40));
        expect(accountDaoMock.sumWorkHoursByAccount(Long.valueOf(2), userLoginId))
                .andReturn(Double.valueOf(40));
        expect(codeTypeDaoMock.getCodeType((Long)anyObject())).andReturn(null);
        replay(accountDaoMock);
        replay(codeTypeDaoMock);
        accountServiceImp.setAccountDao(accountDaoMock);
        List list = accountServiceImp.listAccounts(userLoginId,false);
        this.assertNotNull("Result is null",list);
        DtoAccount dtoAccount = (DtoAccount) list.get(0);
        this.assertNotNull("The first record is  null",dtoAccount);
        this.assertEquals("AccountId is not TestAccountId1",
                          "TestAccountId1", dtoAccount.getAccountId());
        dtoAccount = (DtoAccount) list.get(1);
        this.assertNotNull("The second record is  null",dtoAccount);
        this.assertEquals("AccountId is not TestAccountId2",
                          "TestAccountId2",dtoAccount.getAccountId());

    }
    public void testLoadAccount_hasdata(){
        TsAccount tsAccount = (TsAccount)dataMap.get("AccountData");
        expect(accountDaoMock.loadAccount(Long.valueOf(1))).andReturn(tsAccount);
        replay(accountDaoMock);

        accountServiceImp.setAccountDao(accountDaoMock);
        DtoAccount dtoAccount = accountServiceImp.loadAccount(Long.valueOf(1));
        this.assertNotNull("Result is null",dtoAccount);
        this.assertEquals("Account id is not TestAccountId1",
                          "TestAccountId1", dtoAccount.getAccountId());
        this.assertEquals("Account name is not TestAccountName1",
                          "TestAccountName1", dtoAccount.getAccountName());
        this.assertEquals("Status is not normal",
                          "Normal", dtoAccount.getAccountStatus());
    }
    public void testSaveAccount_success(){
        TsAccount tsAccount = (TsAccount)dataMap.get("AccountData");
        expect(accountDaoMock.loadAccount(Long.valueOf(1))).andReturn(tsAccount);
        tsAccount.setCodeTypeRid(Long.valueOf(2));
        accountDaoMock.saveAccount(tsAccount);
        expectLastCall().once();
        replay(accountDaoMock);

        accountServiceImp.setAccountDao(accountDaoMock);
        DtoAccount dtoAccount = new DtoAccount();
        dtoAccount.setRid(Long.valueOf(1));
        dtoAccount.setCodeTypeRid(Long.valueOf(2));
        accountServiceImp.saveAccount(dtoAccount);
        this.assertEquals("code type rid is error", Long.valueOf(2),
                          tsAccount.getCodeTypeRid());
        verify(accountDaoMock);
    }
    public void testListAccounts_nodata(){
        String userLoginId = "wh0607016";
        expect(accountDaoMock.listAccounts(userLoginId)).andReturn(null);
        accountServiceImp.setAccountDao(accountDaoMock);

        List list = accountServiceImp.listAccounts(userLoginId,false);
        this.assertNotNull("result is null", list);
        this.assertEquals("result's size is not 0", 0, list.size());

    }
    public void testLoadAccount_nodata(){
         expect(accountDaoMock.loadAccount(Long.valueOf(2))).andReturn(new TsAccount());
         replay(accountDaoMock);
         accountServiceImp.setAccountDao(accountDaoMock);
         DtoAccount dtoAccount = accountServiceImp.loadAccount(Long.valueOf(2));
         this.assertNotNull("result is null", dtoAccount);
         this.assertNull("rid is not null", dtoAccount.getRid());
    }

}
