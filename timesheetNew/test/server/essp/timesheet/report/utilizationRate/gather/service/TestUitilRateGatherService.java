package server.essp.timesheet.report.utilizationRate.gather.service;

import java.util.Map;
import java.util.HashMap;
import java.util.Date;
import db.essp.timesheet.TsAccount;
import c2s.dto.DtoComboItem;
import static org.easymock.EasyMock.*;
import junit.framework.TestCase;
import java.util.ArrayList;
import c2s.essp.timesheet.report.DtoUtilRateQuery;
import java.util.List;
import com.wits.util.comDate;
import server.essp.timesheet.report.utilizationRate.gather.dao.IUtilRateGatherDao;
import server.essp.timesheet.account.dao.IAccountDao;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author tbh
 * @version 1.0
 */
public class TestUitilRateGatherService  extends TestCase {
    private UtilRateGatherServiceImp utilRateImp = null;
    private IUtilRateGatherDao gatherDaoMock;
    private IAccountDao accountDaoMock;
    private static Map dataMap;

    static{
        dataMap = new HashMap();
        List lstAccountId = new ArrayList();
        TsAccount tsAccount = new TsAccount();
        tsAccount.setAccountId("W100");
        tsAccount.setAccountName("SEPG");
        tsAccount.setManager("WH0607015");
        tsAccount.setDeptFlag("1");
        tsAccount.setRid(Long.valueOf(100));
        lstAccountId.add(tsAccount);

        tsAccount = new TsAccount();
        tsAccount.setAccountId("W101");
        tsAccount.setAccountName("SEPG");
        tsAccount.setManager("WH0607015");
        tsAccount.setDeptFlag("1");
        tsAccount.setRid(Long.valueOf(100));
        lstAccountId.add(tsAccount);
        dataMap.put("acntIdList",lstAccountId);

       tsAccount = new TsAccount();
       List lstAccount = new ArrayList();
       tsAccount.setAccountId("W100");
       tsAccount.setAccountName("SEPG");
       tsAccount.setManager("WH0607015");
       tsAccount.setDeptFlag("1");
       tsAccount.setRid(Long.valueOf(100));
       lstAccount.add(tsAccount);
       dataMap.put("acntList",lstAccount);

       tsAccount = new TsAccount();
       lstAccount = new ArrayList();
       tsAccount.setAccountId("S0007");
       tsAccount.setAccountName("SEPG");
       tsAccount.setManager("WH0607015");
       tsAccount.setRid(Long.valueOf(7));
       tsAccount.setAccountStatus("N");
       tsAccount.setOrgCode("W100");
       lstAccount.add(tsAccount);
       tsAccount = new TsAccount();
       lstAccount = new ArrayList();
       tsAccount.setAccountId("W100");
       tsAccount.setAccountName("SEPG");
       tsAccount.setManager("WH0607015");
       tsAccount.setRid(Long.valueOf(100));
       tsAccount.setAccountStatus("N");
       lstAccount.add(tsAccount);
       dataMap.put("accountList",lstAccount);

       tsAccount = new TsAccount();
       List lstSubAccount = new ArrayList();
       tsAccount.setAccountId("S0007");
       tsAccount.setAccountName("SEPG");
       tsAccount.setRid(Long.valueOf(97));
       tsAccount.setAccountStatus("N");
       tsAccount.setDeptFlag("1");
       tsAccount.setParentId("W100");
       lstSubAccount.add(tsAccount);
       tsAccount = new TsAccount();
       tsAccount.setAccountId("S0008");
       tsAccount.setAccountName("SEPG");
       tsAccount.setRid(Long.valueOf(99));
       tsAccount.setAccountStatus("N");
       tsAccount.setDeptFlag("1");
       tsAccount.setParentId("W100");
       lstSubAccount.add(tsAccount);
       dataMap.put("subAccountList",lstSubAccount);

       lstSubAccount = new ArrayList();
       tsAccount = new TsAccount();
       tsAccount.setAccountId("S0008");
       tsAccount.setAccountName("SEPG");
       tsAccount.setRid(Long.valueOf(100));
       tsAccount.setAccountStatus("N");
       tsAccount.setDeptFlag("1");
       tsAccount.setParentId("W100");
       lstSubAccount.add(tsAccount);
       dataMap.put("subList1", lstSubAccount);

       lstSubAccount = new ArrayList();
       tsAccount = new TsAccount();
       tsAccount.setAccountId("S0007");
       tsAccount.setAccountName("SEPG");
       tsAccount.setRid(Long.valueOf(101));
       tsAccount.setAccountStatus("N");
       tsAccount.setDeptFlag("1");
       tsAccount.setParentId("W100");
       lstSubAccount.add(tsAccount);
       dataMap.put("subList2", lstSubAccount);

       tsAccount = new TsAccount();
       lstAccount = new ArrayList();
       tsAccount.setAccountId("W200");
       tsAccount.setAccountName("SEPG");
       tsAccount.setManager("WH0607015");
       tsAccount.setDeptFlag("1");
       tsAccount.setRid(Long.valueOf(120));
       lstAccount.add(tsAccount);
       dataMap.put("aList", lstAccount);

       tsAccount = new TsAccount();
       lstAccount = new ArrayList();
       tsAccount.setAccountId("S001");
       tsAccount.setAccountName("SEPG");
       tsAccount.setManager("WH0607015");
       tsAccount.setRid(Long.valueOf(7));
       tsAccount.setAccountStatus("N");
       tsAccount.setOrgCode("W200");
       lstAccount.add(tsAccount);
       tsAccount = new TsAccount();
       lstAccount = new ArrayList();
       tsAccount.setAccountId("W200");
       tsAccount.setAccountName("SEPG");
       tsAccount.setManager("WH0607015");
       tsAccount.setRid(Long.valueOf(120));
       tsAccount.setAccountStatus("N");
       lstAccount.add(tsAccount);
       dataMap.put("accntList", lstAccount);

      tsAccount = new TsAccount();
      lstSubAccount = new ArrayList();
      tsAccount.setAccountId("S007");
      tsAccount.setAccountName("SEPG");
      tsAccount.setRid(Long.valueOf(98));
      tsAccount.setAccountStatus("N");
      tsAccount.setDeptFlag("1");
      tsAccount.setParentId("W200");
      lstSubAccount.add(tsAccount);
      tsAccount = new TsAccount();
      tsAccount.setAccountId("S008");
      tsAccount.setAccountName("SEPG");
      tsAccount.setRid(Long.valueOf(85));
      tsAccount.setAccountStatus("N");
      tsAccount.setDeptFlag("1");
      tsAccount.setParentId("W200");
      lstSubAccount.add(tsAccount);
      dataMap.put("subAcntList",lstSubAccount);

      lstSubAccount = new ArrayList();
      tsAccount = new TsAccount();
      tsAccount.setAccountId("S008");
      tsAccount.setAccountName("SEPG");
      tsAccount.setRid(Long.valueOf(105));
      tsAccount.setAccountStatus("N");
      tsAccount.setDeptFlag("1");
      tsAccount.setParentId("W200");
      lstSubAccount.add(tsAccount);
      dataMap.put("sList1", lstSubAccount);

      lstSubAccount = new ArrayList();
      tsAccount = new TsAccount();
      tsAccount.setAccountId("S007");
      tsAccount.setAccountName("SEPG");
      tsAccount.setRid(Long.valueOf(103));
      tsAccount.setAccountStatus("N");
      tsAccount.setDeptFlag("1");
      tsAccount.setParentId("W200");
      lstSubAccount.add(tsAccount);
      dataMap.put("sList2", lstSubAccount);


      }

    public TestUitilRateGatherService(String name) {
        super(name);
    }

    protected void setUp() throws Exception {
        super.setUp();
        utilRateImp = new UtilRateGatherServiceImp();
        gatherDaoMock = createMock(IUtilRateGatherDao.class);
        accountDaoMock = createMock(IAccountDao.class);
     }

    protected void tearDown() throws Exception {
        utilRateImp = null;
        super.tearDown();
    }

    /**
     * 列出所有符合条件的ACCOUNT记录
     * @throws Exception
     */
    public void testGetDeptListSuccess() throws Exception {
        List acntIdList = (List)dataMap.get("acntIdList");
        String userLoginId = "WH0607011";
        expect(gatherDaoMock.listDeptInfo(userLoginId)).andReturn(acntIdList);
        replay(gatherDaoMock);
        utilRateImp.setUtilRateGatherDao(gatherDaoMock);

        List lst = utilRateImp.getDeptList(userLoginId, "");
        assertNotNull("deptList list is not null", lst);
        assertEquals("deptList list size is 2", 2, lst.size());
        DtoComboItem dtoDept = (DtoComboItem) lst.get(0);
        assertEquals("Account accountId is right","W100",
                          dtoDept.getItemName());
        assertEquals("Account rid is right", Long.valueOf(100),
                          dtoDept.getItemValue());
        }


        public void testGetDataTreeByYearSuccess() throws Exception {
            Date beginDate = comDate.toDate("2006-02-02","yyyy-MM-dd");
            Date endDate = comDate.toDate("2006-02-15","yyyy-MM-dd");
            Date bDate = comDate.toDate("2006-02-26","yyyy-MM-dd");
            Date eDate = comDate.toDate("2006-03-25","yyyy-MM-dd");
            DtoUtilRateQuery dtoQuery = new DtoUtilRateQuery();
            dtoQuery.setAcntRid(Long.valueOf(110));
            dtoQuery.setLoginId("WH0607011");
            dtoQuery.setBegin(beginDate);
            dtoQuery.setEnd(endDate);
            List deptList = (List)dataMap.get("acntList");
            List accountList = (List)dataMap.get("accountList");
            List subAccountList = (List)dataMap.get("subAccountList");
            List subList1 = (List)dataMap.get("subList1");
            List subList2 = (List)dataMap.get("subList2");
            expect(accountDaoMock.listAccountByDept("W100")).andReturn(accountList);
//            expect(gatherDaoMock.getValidHours(bDate,eDate,"('W100')")).andReturn(Double.valueOf(80));
//            expect(gatherDaoMock.getInvalidHours(bDate,eDate,"('W100')")).andReturn(Double.valueOf(20));
//            expect(gatherDaoMock.getSubDeptIdList("W100")).andReturn(subAccountList);
//            expect(accountDaoMock.listAccountByDept("S0008")).andReturn(subList1);
//            expect(gatherDaoMock.getValidHours(bDate,eDate,"('S0008')")).andReturn(Double.valueOf(80));
//            expect(gatherDaoMock.getInvalidHours(bDate,eDate,"('S0008')")).andReturn(Double.valueOf(10));
//            expect(accountDaoMock.listAccountByDept("S0008")).andReturn(subList1);
//            expect(gatherDaoMock.getValidHours(bDate, eDate, "('S0008')")).andReturn(
//                    Double.valueOf(80));
//            expect(gatherDaoMock.getInvalidHours(bDate, eDate, "('S0008')")).andReturn(
//                    Double.valueOf(10));
//            expect(gatherDaoMock.getSubDeptIdList("S0008")).andReturn(new ArrayList());
//
//            expect(accountDaoMock.listAccountByDept("S0007")).andReturn(subList2);
//           expect(gatherDaoMock.getValidHours(bDate,eDate,"('S0007')")).andReturn(Double.valueOf(80));
//           expect(gatherDaoMock.getInvalidHours(bDate,eDate,"('S0007')")).andReturn(Double.valueOf(10));
//           expect(accountDaoMock.listAccountByDept("S0007")).andReturn(subList2);
//           expect(gatherDaoMock.getValidHours(bDate, eDate, "('S0007')")).andReturn(
//                   Double.valueOf(80));
//           expect(gatherDaoMock.getInvalidHours(bDate, eDate,"('S0007')")).andReturn(
//                   Double.valueOf(10));
//           expect(gatherDaoMock.getSubDeptIdList("S0007")).andReturn(new ArrayList());

           replay(gatherDaoMock);
           replay(accountDaoMock);
           utilRateImp.setUtilRateGatherDao(gatherDaoMock);
           utilRateImp.setAccountDao(accountDaoMock);
           Map root = utilRateImp.getDataTreeByYear(dtoQuery);
           assertNotNull("Tree is not null",root);
        }

        public void testGetDataListByYearSuccess() throws Exception {
            Date beginDate = comDate.toDate("2006-02-02", "yyyy-MM-dd");
            Date endDate = comDate.toDate("2006-02-15", "yyyy-MM-dd");
            Date bDate = comDate.toDate("2006-02-26", "yyyy-MM-dd");
            Date eDate = comDate.toDate("2006-03-25", "yyyy-MM-dd");

            DtoUtilRateQuery dtoQuery = new DtoUtilRateQuery();
            dtoQuery.setAcntRid(Long.valueOf(120));
            dtoQuery.setLoginId("WH0607011");
            dtoQuery.setBegin(beginDate);
            dtoQuery.setEnd(endDate);
            List aList = (List) dataMap.get("aList");
            List accountList = (List) dataMap.get("accntList");
            List subAccountList = (List) dataMap.get("subAcntList");
            List subList1 = (List) dataMap.get("sList1");
            List subList2 = (List) dataMap.get("sList2");
            expect(accountDaoMock.listAccountByDept("W200")).andReturn(accountList);
//            expect(gatherDaoMock.getValidHours(bDate, eDate, "('W200')")).andReturn(
//                    Double.valueOf(80));
//            expect(gatherDaoMock.getInvalidHours(bDate, eDate, "('W200')")).andReturn(
//                    Double.valueOf(20));
//            expect(gatherDaoMock.getSubDeptIdList("W200")).andReturn(subAccountList);
//            expect(accountDaoMock.listAccountByDept("S008")).andReturn(subList1);
//            expect(gatherDaoMock.getValidHours(bDate, eDate, "('S008')")).andReturn(
//                    Double.valueOf(80));
//            expect(gatherDaoMock.getInvalidHours(bDate, eDate, "('S008')")).andReturn(
//                    Double.valueOf(10));
//            expect(accountDaoMock.listAccountByDept("S008")).andReturn(subList1);
//            expect(gatherDaoMock.getValidHours(bDate, eDate, "('S008')")).andReturn(
//                    Double.valueOf(80));
//            expect(gatherDaoMock.getInvalidHours(bDate, eDate, "('S008')")).andReturn(
//                    Double.valueOf(10));
//            expect(gatherDaoMock.getSubDeptIdList("S008")).andReturn(new ArrayList());
//
//            expect(accountDaoMock.listAccountByDept("S007")).andReturn(subList2);
//            expect(gatherDaoMock.getValidHours(bDate, eDate, "('S007')")).andReturn(
//                    Double.valueOf(80));
//            expect(gatherDaoMock.getInvalidHours(bDate, eDate, "('S007')")).andReturn(
//                    Double.valueOf(10));
//            expect(accountDaoMock.listAccountByDept("S007")).andReturn(subList2);
//            expect(gatherDaoMock.getValidHours(bDate, eDate, "('S007')")).andReturn(
//                    Double.valueOf(80));
//            expect(gatherDaoMock.getInvalidHours(bDate, eDate, "('S007')")).andReturn(
//                    Double.valueOf(10));
//            expect(gatherDaoMock.getSubDeptIdList("S007")).andReturn(new ArrayList());

            replay(gatherDaoMock);
            replay(accountDaoMock);
            utilRateImp.setUtilRateGatherDao(gatherDaoMock);
            utilRateImp.setAccountDao(accountDaoMock);
//            List dataList = utilRateImp.getDataListByYear(dtoQuery);
//            assertNotNull("Tree is not null", dataList);
       }

      public void testGetDateStrSuccess() throws Exception {
          Date beginDate = comDate.toDate("2006-02-05","yyyy-MM-dd");
          Date endDate = comDate.toDate("2006-09-05","yyyy-MM-dd");
          String dateStr = utilRateImp.getDateStr(beginDate,endDate);
          assertEquals("DateStr is right","2006-03月 ~ 2006-09月",dateStr);
      }

      public void testGetYearsListSuccess() throws Exception {
        Date beginDate = comDate.toDate("2005-09-05","yyyy-MM-dd");
        Date endDate = comDate.toDate("2006-09-05","yyyy-MM-dd");
        List yearList = utilRateImp.getYearsList(beginDate,endDate);
        assertNotNull("yearList is not null",yearList);
        assertEquals("yearList size is 2",2,yearList.size());
    }


}
