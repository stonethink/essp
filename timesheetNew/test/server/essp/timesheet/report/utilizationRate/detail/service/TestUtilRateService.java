package server.essp.timesheet.report.utilizationRate.detail.service;

import db.essp.timesheet.TsAccount;
import static org.easymock.EasyMock.*;
import junit.framework.TestCase;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import server.essp.timesheet.report.utilizationRate.detail.dao.IUtilRateDao;
import java.util.Vector;
import java.util.ArrayList;
import c2s.dto.DtoComboItem;
import db.essp.timesheet.TsLaborResource;
import com.wits.util.comDate;
import java.util.Date;
import c2s.essp.timesheet.report.DtoUtilRateQuery;

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
public class TestUtilRateService extends TestCase {
    private UtilRateServiceImp utilRateImp = null;
    private IUtilRateDao utilRateDaoMock;
    private static Map dataMap;

    static{
        dataMap = new HashMap();
        List acntList = new ArrayList();
        TsAccount tsAccount = new TsAccount();
        tsAccount.setAccountId("110");
        tsAccount.setAccountName("W00111");
        tsAccount.setAccountStatus("N");
        tsAccount.setRid(Long.valueOf(111));
        tsAccount.setDeptFlag("1");
        tsAccount.setManager("WH0607011");
        acntList.add(tsAccount);
        tsAccount = new TsAccount();
        tsAccount.setAccountId("120");
        tsAccount.setAccountName("W00112");
        tsAccount.setAccountStatus("N");
        tsAccount.setRid(Long.valueOf(112));
        tsAccount.setDeptFlag("1");
        tsAccount.setManager("WH0607011");
        acntList.add(tsAccount);
        dataMap.put("AcntList", acntList);

        List labResList = new ArrayList();
        TsLaborResource tsLaborRes = new TsLaborResource();
        tsLaborRes.setAccountRid(Long.valueOf(110));
        tsLaborRes.setLoginId("WH0607015");
        tsLaborRes.setName("zwh");
        labResList.add(tsLaborRes);
        tsLaborRes = new TsLaborResource();
        tsLaborRes.setAccountRid(Long.valueOf(110));
        tsLaborRes.setLoginId("WH0607016");
        tsLaborRes.setName("tbh");
        labResList.add(tsLaborRes);
        dataMap.put("labResList", labResList);

        Vector uesrList = new Vector();
        DtoComboItem dtoCom = new DtoComboItem();
        dtoCom.setItemName("sssss");
        dtoCom.setItemValue("WH0607011");
        uesrList.add(dtoCom);
        dataMap.put("userList", uesrList);

        List objList = new ArrayList();
        Object[] object = new Object[4];
        object[0]="110";
        object[1]="WH0607011";
        object[2]="ssss";
        object[3]=comDate.toDate("2006-09-02","yyyy-MM-dd");
        objList.add(object);
        object = new Object[4];
        object[0]="110";
        object[1]="WH0607011";
        object[2]="ssss";
        object[3]=comDate.toDate("2006-09-05","yyyy-MM-dd");
        objList.add(object);
        dataMap.put("objList", objList);

        objList = new ArrayList();
        object = new Object[5];
        object[0] = "110";
        object[1] = "WH0607011";
        object[2] = "ssss";
        object[3] = comDate.toDate("2006-09-02", "yyyy-MM-dd");
        object[4] = comDate.toDate("2006-09-05", "yyyy-MM-dd");
        objList.add(object);
        object = new Object[5];
        object[0] = "110";
        object[1] = "WH0607011";
        object[2] = "ssss";
        object[3] = comDate.toDate("2006-09-07", "yyyy-MM-dd");
        object[4] = comDate.toDate("2006-09-10", "yyyy-MM-dd");
        objList.add(object);
        dataMap.put("objCycList", objList);

        objList = new ArrayList();
        object = new Object[6];
        object[0] = "110";
        object[1] = "WH0607011";
        object[2] = "ssss";
        object[3] = comDate.toDate("2006-09-04", "yyyy-MM-dd");
        object[4] = comDate.toDate("2006-09-02", "yyyy-MM-dd");
        object[5] = comDate.toDate("2006-09-05", "yyyy-MM-dd");
        objList.add(object);
        dataMap.put("objMonthList", objList);
      }

    public TestUtilRateService(String name) {
        super(name);
    }

    protected void setUp() throws Exception {
        super.setUp();
        utilRateImp = new UtilRateServiceImp();
        utilRateDaoMock = createMock(IUtilRateDao.class);
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
        List accountList = (List)dataMap.get("AcntList");
        String userLoginId = "WH0607011";
        expect(utilRateDaoMock.listDeptInfo(userLoginId)).andReturn(accountList);
        replay(utilRateDaoMock);
        utilRateImp.setUtilRateDao(utilRateDaoMock);

        Vector deptVector = utilRateImp.getDeptList(userLoginId, "");
        assertNotNull("deptVector list is not null", deptVector);
        assertEquals("deptVector list size is 2", 2, deptVector.size());
        DtoComboItem dtoDept = (DtoComboItem) deptVector.get(0);
        assertEquals("Account 111 accountId is right","110",
                          dtoDept.getItemName());
        assertEquals("Account 111 rid is right", Long.valueOf(111),
                          dtoDept.getItemValue());
        }

        /**
         * 测试得到指定部门下的员工集合是否成功
         * @throws Exception
         */
        public void testGetUserListSuccess() throws Exception {
            List labResList = (List) dataMap.get("labResList");
            Long acntRid = Long.valueOf(110);
            expect(utilRateDaoMock.listUsers("")).andReturn(labResList);
            replay(utilRateDaoMock);
            utilRateImp.setUtilRateDao(utilRateDaoMock);
            Vector userVector = utilRateImp.getUserList("");
            assertNotNull("userVector list is not null", userVector);
            assertEquals("userVector list size is 3", 3, userVector.size());
            DtoComboItem dtoDept = (DtoComboItem) userVector.get(1);
            assertEquals("userName is right","zwh",dtoDept.getItemName());
            assertEquals("loginId is right", "WH0607015",dtoDept.getItemValue());
        }


        public void testGetUtilRateDataByDateSuccess() throws Exception {
            Date beginDate = comDate.toDate("2006-09-02","yyyy-MM-dd");
            Date endDate = comDate.toDate("2006-09-05","yyyy-MM-dd");
            DtoUtilRateQuery dtoQuery = new DtoUtilRateQuery();
            dtoQuery.setAcntRid(Long.valueOf(110));
            dtoQuery.setLoginId("WH0607011");
            dtoQuery.setBegin(beginDate);
            dtoQuery.setEnd(endDate);
            Vector userList = (Vector)dataMap.get("userList");
            List objList = (List) dataMap.get("objList");
            Double validHour = Double.valueOf(7);
            Double actHour = Double.valueOf(8);
//            expect(utilRateDaoMock.getValidHoursByDate("WH0607011",
//                                                       "",endDate)).andReturn(validHour).anyTimes();

            replay(utilRateDaoMock);
            utilRateImp.setUtilRateDao(utilRateDaoMock);
            List resultList = utilRateImp.getUtilRateData(dtoQuery,userList);
            assertNotNull("resultList list is not null", resultList);

        }

        public void testGetUtilRateDataByCycleSuccess() throws Exception {
            Date beginDate = comDate.toDate("2006-09-02", "yyyy-MM-dd");
            Date endDate = comDate.toDate("2006-09-05", "yyyy-MM-dd");
            Date bDate = comDate.toDate("2006-09-07", "yyyy-MM-dd");
            Date eDate = comDate.toDate("2006-09-10", "yyyy-MM-dd");
            DtoUtilRateQuery dtoQuery = new DtoUtilRateQuery();
            dtoQuery.setAcntRid(Long.valueOf(110));
            dtoQuery.setLoginId("WH0607011");
            dtoQuery.setBegin(beginDate);
            dtoQuery.setEnd(endDate);
            Vector userList = (Vector) dataMap.get("userList");
            List objList = (List) dataMap.get("objCycList");
            Double validHour = Double.valueOf(16);
            Double actHour = Double.valueOf(16);
            Double validHour2 = Double.valueOf(20);
            Double actHour2 = Double.valueOf(24);
//            expect(utilRateDaoMock.getAcutalHourByMonths("WH0607011",
//                                                             "",beginDate,endDate)).andReturn(actHour).anyTimes();
//            expect(utilRateDaoMock.getValidHourByMonths("WH0607011",
//                                                             "",beginDate,endDate)).andReturn(validHour).anyTimes();
//            expect(utilRateDaoMock.getAcutalHourByMonths("WH0607011",
//                                                              "",bDate,eDate)).andReturn(actHour2).anyTimes();
//            expect(utilRateDaoMock.getValidHourByMonths("WH0607011",
//                                                              "",bDate,eDate)).andReturn(validHour2).anyTimes();

            replay(utilRateDaoMock);
            utilRateImp.setUtilRateDao(utilRateDaoMock);
            List resList =  utilRateImp.getUtilRateDataByCycle(dtoQuery,userList);
            assertNotNull("resultList list is not null", resList);

        }

        public void testGetUtilRateDataByMonthsSuccess() throws Exception {
            Date beginDate = comDate.toDate("2006-08-26", "yyyy-MM-dd");
            Date endDate = comDate.toDate("2006-09-25", "yyyy-MM-dd");
            DtoUtilRateQuery dtoQuery = new DtoUtilRateQuery();
            dtoQuery.setAcntRid(Long.valueOf(110));
            dtoQuery.setLoginId("WH0607011");
            dtoQuery.setBegin(beginDate);
            dtoQuery.setEnd(endDate);
            Vector userList = (Vector) dataMap.get("userList");
            List objList = (List) dataMap.get("objMonthList");
            Double validHour = Double.valueOf(16);
            Double actHour = Double.valueOf(16);
//            expect(utilRateDaoMock.getDataBeanByMonths("WH0607011",
//                                         Long.valueOf(110), beginDate, endDate)).
//                          andReturn(objList).anyTimes();
//            expect(utilRateDaoMock.getAcutalHourByMonths("WH0607011",
//                          Long.valueOf(110), beginDate, endDate)).andReturn(actHour).
//                          anyTimes();
//            expect(utilRateDaoMock.getValidHourByMonths("WH0607011",
//                          Long.valueOf(110), beginDate, endDate)).andReturn(validHour).
//                          anyTimes();

            replay(utilRateDaoMock);
            utilRateImp.setUtilRateDao(utilRateDaoMock);
            List resList = utilRateImp.getUtilRateDataByMonths(dtoQuery,userList);
            assertNotNull("resultList list is not null", resList);
            assertEquals("resultList list size is 3", 3, resList.size());
          }

}
