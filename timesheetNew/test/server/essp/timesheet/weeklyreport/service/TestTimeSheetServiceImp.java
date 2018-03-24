package server.essp.timesheet.weeklyreport.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.primavera.integration.client.bo.object.Calendar;

import c2s.essp.common.user.DtoUserBase;
import c2s.essp.timesheet.preference.DtoPreference;
import c2s.essp.timesheet.report.DtoTsStatusQuery;
import c2s.essp.timesheet.weeklyreport.DtoTimeSheet;
import c2s.essp.timesheet.weeklyreport.DtoTimeSheetDetail;
import c2s.essp.timesheet.weeklyreport.DtoTimeSheetPeriod;
import db.essp.timesheet.*;
import junit.framework.TestCase;
import server.essp.timesheet.preference.dao.IPreferenceDao;
import server.essp.timesheet.rmmaint.dao.IRmMaintDao;
import server.essp.timesheet.weeklyreport.dao.ITimeSheetDao;
import server.essp.timesheet.weeklyreport.dao.ITimeSheetP3ApiDao;
import c2s.dto.DtoUtil;
import c2s.essp.timesheet.weeklyreport.DtoTimeSheetDay;

/**
 * <p>Title: TestTimeSheetServiceImp1</p>
 *
 * <p>Description: TimeSheetServiceImp1 µ•‘™≤‚ ‘¿‡</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class TestTimeSheetServiceImp extends TestCase {
    private TimeSheetServiceImp timeSheetServiceImp = null;

    public TestTimeSheetServiceImp(String name) {
        super(name);
    }

    protected void setUp() throws Exception {
        super.setUp();
        timeSheetServiceImp = new TimeSheetServiceImp();
        timeSheetServiceImp.setTimeSheetDao(new TestTimeSheetDaoImpNormal());
        timeSheetServiceImp.setPreferenceDao(new TestPreferenceDaoImpNormal());
        timeSheetServiceImp.setTimeSheetApiDao(new TestTimeSheetP3ApiDaoImpNormal());
        timeSheetServiceImp.setRmMaintDao(new TestRmMaintDaoNormal());
    }

    protected void tearDown() throws Exception {
        timeSheetServiceImp = null;
        super.tearDown();
    }

    public void testActiveTimeSheetNoDetail() {
        DtoTimeSheet dto = new DtoTimeSheet();
        dto.setStatus(DtoTimeSheet.STATUS_REJECTED);
        timeSheetServiceImp.activeTimeSheet(dto);
        assertEquals("testActiveTimeSheet status ", dto.getStatus(), DtoTimeSheet.STATUS_ACTIVE);
    }

    public void testActiveTimeSheetNormal() {
        DtoTimeSheet dto = new DtoTimeSheet();
        dto.setStatus(DtoTimeSheet.STATUS_REJECTED);
        DtoTimeSheetDetail dtoDetail = new DtoTimeSheetDetail();
        List detailList = new ArrayList();
        detailList.add(dtoDetail);
        dto.setTsDetails(detailList);
        timeSheetServiceImp.activeTimeSheet(dto);
        assertEquals("testActiveTimeSheet Ts status ", dto.getStatus(), DtoTimeSheet.STATUS_ACTIVE);
        assertEquals("testActiveTimeSheet TsDetail status ", dtoDetail.getStatus(), DtoTimeSheet.STATUS_ACTIVE);
    }


    public void testDeleteTimeSheetDetail() {
        DtoTimeSheetDetail dtoDetail = new DtoTimeSheetDetail();
        timeSheetServiceImp.deleteTimeSheetDetail(dtoDetail);
    }

    public void testGetTimeSheet() {
        Long rid = Long.valueOf(33);
        DtoTimeSheet actualReturn = timeSheetServiceImp.getTimeSheet(rid);
        assertNotNull("testGetTimeSheet return value not null",  actualReturn);
        assertEquals("testGetTimeSheet loginId", rid.toString() ,actualReturn.getLoginId());
        assertEquals("testGetTimeSheet TsDetails size", 2, actualReturn.getTsDetails().size());
    }

    public void testGetTimeSheet1() {
        Long tsId = Long.valueOf(33);
        String loginId = "";
        TsTimesheetMaster expectedReturn = getTsTimesheetMaster(tsId);
        DtoTimeSheet actualReturn = timeSheetServiceImp.getTimeSheet(tsId, loginId);
        assertNotNull("testGetTimeSheet1 return value not null",  actualReturn);
        assertEquals("testGetTimeSheet1 loginId", expectedReturn.getLoginId(), actualReturn.getLoginId());
        assertEquals("testGetTimeSheet1 loginId", expectedReturn.getNotes(), actualReturn.getNotes());

    }

    public void testGetTimeSheet2() {
        String loginId = "";
        Date workDay = new Date();
        TsTimesheetMaster expectedReturn = getTsTimesheetMaster(Long.valueOf(55));
        DtoTimeSheet actualReturn = timeSheetServiceImp.getTimeSheet(loginId, workDay);
        assertNotNull("testGetTimeSheet1 return value not null",  actualReturn);
        assertEquals("testGetTimeSheet1 loginId", expectedReturn.getLoginId(), actualReturn.getLoginId());
        assertEquals("testGetTimeSheet1 loginId", expectedReturn.getNotes(), actualReturn.getNotes());
    }

    public void testSaveTimeSheet() {
        DtoTimeSheet dto = new DtoTimeSheet();
        timeSheetServiceImp.saveTimeSheet(dto);
    }

    public void testSaveTimeSheetNotes() {
        timeSheetServiceImp.saveTimeSheetNotes("test notes", Long.valueOf(1));
    }

    public void testSubmitTimeSheet() {
        DtoTimeSheet dtoTimeSheet = new DtoTimeSheet();
        timeSheetServiceImp.submitTimeSheet(dtoTimeSheet);
    }

    private class TestTimeSheetDaoImpNormal implements ITimeSheetDao {
        public void addTimeSheetMaster(TsTimesheetMaster master) {
        }

        public void addTimeSheetDetail(TsTimesheetDetail detail) {
        }

        public void addTimeSheetDay(TsTimesheetDay day) {
        }

        public void updateTimeSheetMaster(TsTimesheetMaster master) {
        }

        public void updateTimeSheetMasterStatus(Long masterRid, String status) {
        }

        public void updateTimeSheetDetail(TsTimesheetDetail detail) {
        }

        public void updateTimeSheetDetailStatus(Long detailRid, String status) {
        }

        public void updateTimeSheetDay(TsTimesheetDay day) {
        }

        public void deleteTimeSheetMaster(TsTimesheetMaster master) {
        }

        public void deleteTimeSheetDetail(TsTimesheetDetail detail) {
        }

        public void deleteTimeSheetDay(TsTimesheetDay day) {
        }

        public TsTimesheetMaster getTimeSheetMaster(Long rid) {
            return getTsTimesheetMaster(rid);
        }

        public TsTimesheetMaster getTimeSheetMaster(String loginId, Long tsId) {
            return getTsTimesheetMaster(tsId);
        }

        public List listTimeSheetDetail(Long tsRid) {
            List list = new ArrayList();
            list.add(getTsTimesheetDetail(tsRid + 1));
            list.add(getTsTimesheetDetail(tsRid + 2));
            return list;
        }

        public List getTimeSheetDay(Long detailRid) {
            List list = new ArrayList();
            list.add(getTsTimesheetDay(detailRid + 1));
            list.add(getTsTimesheetDay(detailRid + 2));
            return list;
        }

        public TsTimesheetMaster loadTimeSheetMasterByRidDate(String loginId,
                Date begin, Date end, String status) {
            return getTsTimesheetMaster(Long.valueOf(9));
        }

        public List listTimeSheetDetailByTsRid(Long tsRid) {
            return listTimeSheetDetail(tsRid);
        }

        public List listTimeSheetDetailByTsRidStatus(Long tsRid, String status) {
            return listTimeSheetDetail(tsRid);
        }

        public List listTimeSheetDetailNotComfirmedByTsRid(Long tsRid,
                String status) {
            return listTimeSheetDetail(tsRid);
        }

        public List listTimeSheetDetailByAcntRidTsRid(Long acntRid, Long tsRid,
                String status) {
            return listTimeSheetDetail(tsRid);
        }

        public List listTimeSheetDayByDetailRid(Long detailRid) {
            return getTimeSheetDay(detailRid);
        }

        public TsTimesheetMaster loadTimeSheetMasterByRidDate(String loginId,
                Date begin, Date end) {
            return null;
        }

        public List listTimeSheetDetailNotEqualsStatusByTsRid(Long tsRid,
                String status) {
            return null;
        }

        public List listTimeSheetDetailByAcntRidTsRid(Long acntRid, Long tsRid) {
            return null;
        }

        public List listTimeSheetDetailByAcntRidTsRidByStatus(Long acntRid,
                Long tsRid, String status) {
            return null;
        }

        public List listTimeSheetDetailByStatus(String status) {
            return null;
        }

        public List listTimeSheetMasterByStatus(String status) {
            return null;
        }

        public List listTimeSheetDetailNotInStatusByTsRid(Long tsRid,
                String status) {
            return null;
        }

		public void deleteTimeSheetData(String loginId, Long tsId) {
			
		}

		public List<Object[]> getDataForRemindMail(String status, String loginId) {
			return null;
		}

		public List<Object[]> getDataForRemindPmMail(String status, String loginId) {
			return null;
		}

		public List<Object[]> getDataForRemindRmMail(String status, String deptId) {
			return null;
		}

		public List<String> getPersonForRemindMail(String status) {
			return null;
		}

		public List<String> getPmForRemindMail(String status) {
			return null;
		}

		public List<Object[]> getRmForRemindMail(String status) {
			return null;
		}

		public TsTimesheetDay loadByRid(Long rid) {
			return null;
		}

		public List getTimeSheetMasterList(String loginId, Long tsId) {
			
			return null;
		}

		public Boolean isFillTS(String loginId, Date begin, Date end) {
			
			return null;
		}

		public List<TsTimesheetMaster> listTimeSheetMasterByDateStatus(String loginId, Date begin, Date end, String status) {
			
			return null;
		}

		public List<Object[]> getDataForRemindMail(String status, String loginId, Date begin, Date end) {
			
			return null;
		}

		public List<Object[]> getDataForRemindPmMail(String status, String loginId, Date begin, Date end) {
			
			return null;
		}

		public List<Object[]> getDataForRemindRmMail(String status, String loginId, Date begin, Date end) {
			
			return null;
		}

		public List<String> getPersonForRemindMail(String status, Date begin, Date end) {
			
			return null;
		}

		public List<String> getPmForRemindMail(String status, Date begin, Date end) {
			
			return null;
		}

	

		public List<Object[]> getDataForRemindPmMail(String status, String loginId, Date begin, Date end, String site) {
			
			return null;
		}

		public List<Object[]> getPersonAcntForRemindMail(String status, Date begin, Date end, String site) {
			
			return null;
		}

		public List<String> getPersonForRemindMail(String status, Date begin, Date end, String site) {
			
			return null;
		}

		public Boolean isFillTS(String loginId, Long tsId) {
			
			return null;
		}

		public List isFillTSOrUnapprove(String loginId, Date begin, Date end) {
			
			return null;
		}

		public List isFillTSOrUnsubmit(String loginId, Date begin, Date end) {
			
			return null;
		}

		public TsTimesheetDetail geTimeSheetDetail(Long rid) {
			
			return null;
		}

		public List listTimeSheetDetailByStatusSite(String status, String site) {
			
			return null;
		}

		public List listTimeSheetMasterByStatusSite(String status, String site) {
			
			return null;
		}

		public void deleteTimeSheetInnerData(Long masterRid) {
			
			
		}

		public List isFillTSOrUnapprove(Date begin, Date end) {
			
			return null;
		}

		public List isFillTSOrUnsubmit(Date begin, Date end) {
			
			return null;
		}

		public List getApprovedTsList(Date begin, Date end) {
			// TODO Auto-generated method stub
			return null;
		}

		public List getSubmitTsList(Date begin, Date end) {
			// TODO Auto-generated method stub
			return null;
		}

		public List<TsTimesheetMaster> listTimeSheetMasterByLoginIdDate(String loginId, Date begin, Date end) {
			// TODO Auto-generated method stub
			return null;
		}

		public TsTimesheetMaster getLastTimeSheetMaster(String loginId, Date nowDate) {
			// TODO Auto-generated method stub
			return null;
		}

    }

    private class TestPreferenceDaoImpNormal implements IPreferenceDao {
        public TsParameter loadPreference() {
            TsParameter ts = new TsParameter();
            ts.setCompleteTaskHrsFlag(true);
            ts.setFutureTsCnt(Long.valueOf(4));
            ts.setFutureTsHrsFlag(true);
            ts.setPastTsCnt(Long.valueOf(4));
            ts.setPostendTaskHrsFlag(true);
            ts.setPrestartTaskHrsFlag(true);
            ts.setHrDecimalCnt(Long.valueOf(2));
            ts.setTsApprovalLevel(DtoPreference.APPROVAL_LEVEL_PM_AND_RM);
            ts.setXferCompleteDayCnt(Long.valueOf(9999));
            ts.setXferNostartDayCnt(Long.valueOf(9999));
            return ts;
        }

        public void saveOrUpdatePreference(TsParameter tsParameter) {
        }

		public TsParameter loadPreferenceBySite(String site) {
			
			return null;
		}

		public List<String> listSitesInTsHumanbase() {
			
			return null;
		}

		public TsParameter loadPreferenceByLoginId(String loginId) {
			
			return null;
		}

    }

    private class TestTimeSheetP3ApiDaoImpNormal implements ITimeSheetP3ApiDao {
        public DtoTimeSheetPeriod getTimeSheetPeriod(Date day) throws Exception {
            DtoTimeSheetPeriod dto = new DtoTimeSheetPeriod();
            dto.setBeginDate(day);
            dto.setEndDate(day);
            dto.setTsId(Long.valueOf(55));
            return dto;
        }

        public int getPeriodNum(Date begin, Date end) throws Exception {
            return 1;
        }

        public Long getCurrentResourceId() throws Exception {
            return Long.valueOf(556);
        }

        public void getActivityPlanDate(DtoTimeSheetDetail dtoDetail) throws
                Exception {
        }

		public List listStandarHours(List<Date> dateList) {
			return null;
		}

		public List listStandarHours(String loginId, List<Date> dateList) {
			return null;
		}

		public Calendar getCalendar(String loginId) {
			
			return null;
		}

		public Date[] getInOutDate(String loginId) {
			
			return null;
		}

		public Double getSumStandarHours(Calendar c, List<Date> dateList, Date inDate, Date outDate) {
			
			return null;
		}

    }

    private static DtoTimeSheet getDtoTimeSheet(Long rid) {
        DtoTimeSheet dto = new DtoTimeSheet();
        DtoUtil.copyBeanToBean(dto, getTsTimesheetMaster(rid));
        return dto;
    }

    private static TsTimesheetMaster getTsTimesheetMaster(Long rid) {
        TsTimesheetMaster ts = new TsTimesheetMaster();
        ts.setRid(rid);
        ts.setBeginDate(new Date());
        ts.setEndDate(new Date());
        ts.setLoginId(rid.toString());
        ts.setNotes(rid.toString());
        ts.setStatus(DtoTimeSheet.STATUS_ACTIVE);
        ts.setStatusDate(new Date());
        ts.setTsId(rid);
        return ts;
    }

    private static DtoTimeSheetDetail getDtoTimeSheetDetail(Long rid) {
        DtoTimeSheetDetail dto = new DtoTimeSheetDetail();
        DtoUtil.copyBeanToBean(dto, getTsTimesheetDetail(rid));
        return dto;
    }

    private static TsTimesheetDetail getTsTimesheetDetail(Long rid) {
        TsTimesheetDetail ts = new TsTimesheetDetail();
        ts.setAccountName(rid.toString());
        ts.setAccountRid(rid);
        ts.setActivityId(rid);
        ts.setActivityName(rid.toString());
        ts.setCodeValueName(rid.toString());
        ts.setCodeValueRid(rid);
        ts.setJobDescription(rid.toString());
        ts.setRid(rid);
        ts.setStatus(DtoTimeSheet.STATUS_ACTIVE);
        ts.setTsRid(rid);
        return ts;
    }

    private static DtoTimeSheetDay getDtoTimeSheetDay(Long rid) {
        DtoTimeSheetDay dto = new DtoTimeSheetDay();
        DtoUtil.copyBeanToBean(dto, getTsTimesheetDay(rid));
        return dto;
    }

    private static TsTimesheetDay getTsTimesheetDay(Long rid) {
        TsTimesheetDay ts = new TsTimesheetDay();
        ts.setOvertimeHours(Double.valueOf(rid));
        ts.setTsDetailRid(rid);
        ts.setWorkDate(new Date());
        ts.setWorkHours(Double.valueOf(rid));
        return ts;
    }
    
    private class TestRmMaintDaoNormal implements IRmMaintDao {

		public TsRmMaint getRmByLoginId(String loginId) {
			
			return null;
		}

		public void addRmMaint(TsRmMaint tsRmMaint) {
			
			
		}

		public void updateRmMaint(TsRmMaint tsRmMaint) {
			
			
		}

		public void delRmMaint(TsRmMaint tsRmMaint) {
			
			
		}

		public List<TsRmMaint> getPersonUnderRm(String rmId) {
			
			return null;
		}

		public List<TsRmMaint> getAllUserMainted() {
			
			return null;
		}

		public TsHumanBase loadHumanById(String employeeId) {
			
			return null;
		}

		public List<DtoUserBase> listPersonByManagerFromAD(String managerId) {
			
			return null;
		}

		public List<TsHumanBase> listHumanByRmFromDB(String rmId) {
			
			return null;
		}

		public List<TsHumanBase> listHumanByRmFromDBBySite(String rmId, String site) {
			
			return null;
		}

		public List getLoginIdList(String site, boolean viewAll) {
			
			return null;
		}

		public List listEnableLoginId(String site, Date begin, Date end) {
			
			return null;
		}

		public List<TsHumanBase> listAllHuman(Date begin, Date end, String site) {
			
			return null;
		}

		public List getSiteFromHumanBase(String site) {
			
			return null;
		}

		public List listEnableLoginId(String site, DtoTsStatusQuery dtoQuery, boolean viewAll) {
			
			return null;
		}

		public List listAllEmployee() {
			// TODO Auto-generated method stub
			return null;
		}

		public List getLoginIdsByDept(String deptIds) {
			// TODO Auto-generated method stub
			return null;
		}

		public List getSumLeaveHoursByDate(Date begin, Date end, String site) {
			// TODO Auto-generated method stub
			return null;
		}

		public List listDirectHuman() {
			// TODO Auto-generated method stub
			return null;
		}

		public List listPersonsByDept(String deptId) {
			// TODO Auto-generated method stub
			return null;
		}
    	
    }

}
