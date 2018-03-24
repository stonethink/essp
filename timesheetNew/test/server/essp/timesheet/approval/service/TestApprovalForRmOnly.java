package server.essp.timesheet.approval.service;

import java.util.*;

import c2s.essp.common.user.DtoUserBase;
import c2s.essp.timesheet.approval.DtoTsApproval;
import c2s.essp.timesheet.preference.DtoPreference;
import c2s.essp.timesheet.weeklyreport.DtoRsrcHour;
import c2s.essp.timesheet.weeklyreport.DtoTimeSheet;
import c2s.essp.timesheet.weeklyreport.DtoTimeSht;
import db.essp.timesheet.*;
import junit.framework.TestCase;
import static org.easymock.EasyMock.*;
import server.essp.timesheet.account.labor.dao.ILaborDao;
import server.essp.timesheet.approval.dao.IApprovalDao;
import server.essp.timesheet.calendar.dao.ICalendarP3Dao;
import server.essp.timesheet.code.codevalue.dao.ICodeValueDao;
import server.essp.timesheet.preference.dao.IPreferenceDao;
import server.essp.timesheet.rmmaint.dao.IRmMaintDao;
import server.essp.timesheet.weeklyreport.dao.ITimeSheetDao;
import server.essp.timesheet.weeklyreport.dao.ITimeSheetP3DbDao;

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
public class TestApprovalForRmOnly extends TestCase {

    private ApprovalProxy approvalProxy;
    private IApprovalDao approvalDaoMock;
    private ILaborDao laborDaoMock;
    private ITimeSheetDao timeSheetDaoMock;
    private ICalendarP3Dao calendarP3DaoMock;
    private ICodeValueDao codeValueDaoMock;
    private ITimeSheetP3DbDao timeSheetP3DaoMock;
    private IPreferenceDao preferenceDaoMock;
    private IRmMaintDao rmMaintDaoMock;
    private ApprovalAssistServiceImp approvalAssistService;
    private static Map<String, Object> dataMap = new HashMap<String,Object>();
    static{
         Calendar ca = Calendar.getInstance();
         ca.set(2007, Calendar.AUGUST, 25, 0, 0, 0);
         Date begin = ca.getTime();
         ca.set(2007, Calendar.AUGUST, 31, 0, 0, 0);
         Date end = ca.getTime();
         dataMap.put("begin", begin);
         dataMap.put("end", end);
         List laborList = new ArrayList();
         laborList.add("WH0607015");
         dataMap.put("laborList", laborList);
         TsTimesheetMaster tsMaster = new TsTimesheetMaster();
         tsMaster.setBeginDate(begin);
         tsMaster.setEndDate(end);
         tsMaster.setLoginId("WH0607015");
         tsMaster.setNotes("Test");
         tsMaster.setRid(Long.valueOf(1));
         tsMaster.setStatus(DtoTimeSheet.STATUS_SUBMITTED);
         tsMaster.setTsId(Long.valueOf(1));
         tsMaster.setStatusDate(begin);
         List list = new ArrayList();
         list.add(tsMaster);
         dataMap.put("tsMasterList", list);
         TsTimesheetDetail tsDetail = new TsTimesheetDetail();
         tsDetail.setAccountRid(Long.valueOf(1));
         tsDetail.setAccountName("test");
         tsDetail.setActivityId(Long.valueOf(11));
         tsDetail.setActivityName("testAct");
         tsDetail.setCodeValueName("testCodeValue");
         tsDetail.setCodeValueRid(Long.valueOf(10));
         tsDetail.setJobDescription("testdesc");
         tsDetail.setRid(Long.valueOf(1));
         tsDetail.setStatus("('" + DtoTimeSheet.STATUS_SUBMITTED + "')");
         tsDetail.setTsRid(Long.valueOf(1));
         List detailList = new ArrayList();
         List detailListOne = new ArrayList();
         detailListOne.add(tsDetail);
         detailList.add(tsDetail);
         dataMap.put("detailListOne", detailListOne);
         tsDetail = new TsTimesheetDetail();
         tsDetail.setAccountRid(Long.valueOf(2));
         tsDetail.setAccountName("test");
         tsDetail.setActivityId(Long.valueOf(11));
         tsDetail.setActivityName("testAct");
         tsDetail.setCodeValueName("testCodeValue");
         tsDetail.setCodeValueRid(Long.valueOf(11));
         tsDetail.setJobDescription("testdesc");
         tsDetail.setRid(Long.valueOf(1));
         tsDetail.setStatus("('" + DtoTimeSheet.STATUS_SUBMITTED + "')");
         tsDetail.setTsRid(Long.valueOf(1));
         detailList.add(tsDetail);
         dataMap.put("detailList", detailList);
         TsTimesheetDay tsDay = new TsTimesheetDay();
         tsDay.setRid(Long.valueOf(1));
         tsDay.setTsDetailRid(Long.valueOf(1));
         tsDay.setWorkHours(Double.valueOf(8));
         tsDay.setWorkDate(ca.getTime());
         List dayList = new ArrayList();
         dayList.add(tsDay);
         dataMap.put("dayList", dayList);
         TsCodeValue codeValue = new TsCodeValue();
         codeValue.setRid(Long.valueOf(11));
         codeValue.setIsLeaveType(true);
         dataMap.put("codeValueLeave", codeValue);
         codeValue = new TsCodeValue();
         codeValue.setRid(Long.valueOf(10));
         codeValue.setIsLeaveType(false);
         dataMap.put("codeValueNotLeave", codeValue);
         DtoTsApproval dtoApproval = new DtoTsApproval();
         dtoApproval.setAcntRid(Long.valueOf(1));
         dtoApproval.setLoginId("WH0607015");
         dtoApproval.setTsRid(Long.valueOf(1));
         List approvalList = new ArrayList();
         approvalList.add(dtoApproval);
         dataMap.put("approvalList", approvalList);
         TsParameter tsParameter = new TsParameter();
         tsParameter.setTsApprovalLevel(DtoPreference.APPROVAL_LEVEL_RM);
         dataMap.put("tsParameter", tsParameter);
         DtoUserBase dtoUserBase = new DtoUserBase();
         dtoUserBase.setUserLoginId("WH0607015");
         List dtoUserList = new ArrayList();
         dtoUserList.add(dtoUserBase);
         dataMap.put("dtoUserList", dtoUserList);
         TsRmMaint tsRmMaint = new TsRmMaint();
         

}

    public void setUp() throws Exception {
        super.setUp();
        approvalDaoMock = createMock(IApprovalDao.class);
        laborDaoMock = createMock(ILaborDao.class);
        timeSheetDaoMock = createMock(ITimeSheetDao.class);
        calendarP3DaoMock = createMock(ICalendarP3Dao.class);
        codeValueDaoMock = createMock(ICodeValueDao.class);
        timeSheetP3DaoMock = createMock(ITimeSheetP3DbDao.class);
        preferenceDaoMock = createMock(IPreferenceDao.class);
        rmMaintDaoMock = createMock(IRmMaintDao.class);
        approvalAssistService = new ApprovalAssistServiceImp();
        approvalAssistService.setTimeSheetDao(timeSheetDaoMock);
        approvalAssistService.setTimeSheetP3DbDao(timeSheetP3DaoMock);
        approvalAssistService.setCalendarP3Dao(calendarP3DaoMock);
        expect(preferenceDaoMock.loadPreference()).andReturn((TsParameter)dataMap.get("tsParameter"));
        replay(preferenceDaoMock);
        approvalProxy = new ApprovalProxy();
        approvalProxy.setPreferenceDao(preferenceDaoMock);
        approvalProxy.setApprovalAssistService(approvalAssistService);



    }
    public void tearDown() throws Exception {
        super.tearDown();
    }
     public void testRmList_Normal() throws Exception {
        approvalProxy.setApprovalDao(approvalDaoMock);
        approvalProxy.setTimeSheetDao(timeSheetDaoMock);
        expect(approvalDaoMock.listPersonByManager("WH0607015"))
                .andReturn((List)dataMap.get("dtoUserList"));
        expect(laborDaoMock.listLoginIdByResourceManager("WH0607015"))
               .andReturn((List)dataMap.get("laborList"));
        expect(timeSheetDaoMock.listTimeSheetMasterByLoginIdDate("WH0607015",
                (Date)dataMap.get("begin"), (Date)dataMap.get("end")))
                .andReturn((List)dataMap.get("tsMasterList"));
        expect(calendarP3DaoMock.getWorkHoursBetweenDates((Date)anyObject(),
                                              (Date)anyObject()))
                .andReturn(Double.valueOf(40));

        expect(timeSheetDaoMock.listTimeSheetDetailByTsRid(Long.valueOf(1)))
                .andReturn((List)dataMap.get("detailList")).anyTimes();
        expect(timeSheetDaoMock.listTimeSheetDayByDetailRid(Long.valueOf(1)))
                .andReturn((List)dataMap.get("dayList")).anyTimes();
        expect(codeValueDaoMock.getCodeValue(Long.valueOf(11)))
                .andReturn((TsCodeValue)dataMap.get("codeValueLeave"));
        expect(codeValueDaoMock.getCodeValue(Long.valueOf(10)))
                .andReturn((TsCodeValue)dataMap.get("codeValueNotLeave"));
        expect(rmMaintDaoMock.getRmByLoginId("WH0607015")).andReturn(null);
        expect(rmMaintDaoMock.getPersonUnderRm("WH0607015")).andReturn(null);
        replay(rmMaintDaoMock);
        replay(approvalDaoMock);
        replay(laborDaoMock);
        replay(timeSheetDaoMock);
        replay(calendarP3DaoMock);
        replay(codeValueDaoMock);
        List resultList = approvalProxy.rmList("WH0607015", (Date)dataMap.get("begin"),
                                                  (Date)dataMap.get("end"), DtoTsApproval.DTO_QUERY_WAY_ALL);
        this.assertNotNull("Result is null", resultList);

     }
      public void testRmApproval_Normal(){
          approvalProxy.setTimeSheetDao(timeSheetDaoMock);
        expect(timeSheetDaoMock.listTimeSheetDetailByTsRidStatus(
                Long.valueOf(1), "('"+DtoTimeSheet.STATUS_SUBMITTED+"')")).andReturn((List)dataMap.get("detailListOne"));
        timeSheetDaoMock.updateTimeSheetDetailStatus(Long.valueOf(1), DtoTimeSheet.STATUS_APPROVED);
        expectLastCall().anyTimes();
        expect(timeSheetDaoMock.listTimeSheetDetailNotEqualsStatusByTsRid(Long.valueOf(1),
                DtoTimeSheet.STATUS_APPROVED)).andReturn(null);
        timeSheetDaoMock.updateTimeSheetMasterStatus(Long.valueOf(1), DtoTimeSheet.STATUS_APPROVED);
        expectLastCall().anyTimes();
        expect(timeSheetDaoMock.getTimeSheetMaster(Long.valueOf(1)))
                .andReturn((TsTimesheetMaster)dataMap.get("tsMaster"));
        timeSheetDaoMock.updateTimeSheetMaster((TsTimesheetMaster)anyObject());
        expectLastCall().anyTimes();
        expect(timeSheetDaoMock.listTimeSheetDetailByTsRid(Long.valueOf(1)))
                .andReturn((List)dataMap.get("detailList")).anyTimes();
        expect(timeSheetDaoMock.listTimeSheetDayByDetailRid(Long.valueOf(1)))
                .andReturn((List)dataMap.get("dayList")).anyTimes();
        replay(timeSheetDaoMock);
        expect(timeSheetP3DaoMock.getUserId("WH0607015")).andReturn(Long.valueOf(1));
        expect(timeSheetP3DaoMock.getRsrcId(Long.valueOf(1))).andReturn(Long.valueOf(1));
        timeSheetP3DaoMock.deleteRsrchour(Long.valueOf(1), Long.valueOf(1));
        expectLastCall().once();
        timeSheetP3DaoMock.deleteTimesht(Long.valueOf(1), Long.valueOf(1));
        expectLastCall().once();
        timeSheetP3DaoMock.insertTimeSheet((DtoTimeSht)anyObject());
        expectLastCall().anyTimes();
        timeSheetP3DaoMock.insertResourceHours((DtoRsrcHour)anyObject());
        expectLastCall().anyTimes();
        expect(timeSheetP3DaoMock.getNewResourceHursId())
                .andReturn(Long.valueOf(1))
                .andReturn(Long.valueOf(2));
        expect(timeSheetP3DaoMock.getProjIdFromTask(Long.valueOf(11)))
                .andReturn(Long.valueOf(1)).anyTimes();
        expect(timeSheetP3DaoMock.getTaskRsrcId(Long.valueOf(11), Long.valueOf(1)))
                .andReturn(Long.valueOf(1)).anyTimes();
        replay(timeSheetP3DaoMock);

        approvalProxy.rmApproval((List)dataMap.get("approvalList"));

      }
}
