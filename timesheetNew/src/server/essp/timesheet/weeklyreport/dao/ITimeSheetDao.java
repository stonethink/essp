package server.essp.timesheet.weeklyreport.dao;

import db.essp.timesheet.TsTimesheetMaster;
import db.essp.timesheet.TsTimesheetDetail;
import db.essp.timesheet.TsTimesheetDay;
import java.util.Date;
import java.util.List;

/**
 * <p>Title: time sheet dao interface</p>
 *
 * <p>Description: access essp db for time sheet data  </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public interface ITimeSheetDao {

    public void addTimeSheetMaster(TsTimesheetMaster master);

    public void addTimeSheetDetail(TsTimesheetDetail detail);

    public void addTimeSheetDay(TsTimesheetDay day);

    public void updateTimeSheetMaster(TsTimesheetMaster master);

    public void updateTimeSheetMasterStatus(Long masterRid, String status);

    public void updateTimeSheetDetail(TsTimesheetDetail detail);

    public void updateTimeSheetDetailStatus(Long detailRid, String status);

    public void updateTimeSheetDay(TsTimesheetDay day);

    public void deleteTimeSheetMaster(TsTimesheetMaster master);

    public void deleteTimeSheetDetail(TsTimesheetDetail detail);

    public void deleteTimeSheetDay(TsTimesheetDay day);

    public TsTimesheetMaster getTimeSheetMaster(Long rid);
    
    public TsTimesheetDetail geTimeSheetDetail(Long rid);

    public TsTimesheetMaster getTimeSheetMaster(String loginId, Long tsId);
    
    public TsTimesheetMaster getLastTimeSheetMaster(String loginId, Date nowDate);
    
    public List getTimeSheetMasterList(String loginId, Long tsId);

    public List<TsTimesheetDetail> listTimeSheetDetail(Long tsRid);

    public List<TsTimesheetDay> getTimeSheetDay(Long detailRid);

    public List<TsTimesheetMaster> listTimeSheetMasterByLoginIdDate(String loginId, Date begin, Date end);
    
    public List<TsTimesheetMaster> listTimeSheetMasterByDateStatus(String loginId, Date begin, Date end, String status);

    public List<TsTimesheetDetail> listTimeSheetDetailByTsRid(Long tsRid);

    public List<TsTimesheetDetail> listTimeSheetDetailByTsRidStatus(Long tsRid, String status);

    public List<TsTimesheetDetail> listTimeSheetDetailNotEqualsStatusByTsRid(Long tsRid, String status);

    public List<TsTimesheetDetail> listTimeSheetDetailByAcntRidTsRid(Long acntRid, Long tsRid);

    public List<TsTimesheetDetail> listTimeSheetDetailByAcntRidTsRidByStatus(Long acntRid, Long tsRid, String status);

    public List<TsTimesheetDay> listTimeSheetDayByDetailRid(Long detailRid);

    public List<TsTimesheetDetail> listTimeSheetDetailByStatus(String status);

    public List<TsTimesheetMaster> listTimeSheetMasterByStatus(String status);

    public List<TsTimesheetDetail> listTimeSheetDetailNotInStatusByTsRid(Long tsRid, String status);
    
    public List<Object[]> getDataForRemindMail(String status, String loginId, Date begin, Date end);
    
    public List<String> getPersonForRemindMail(String status, Date begin, Date end, String site);
    
    public List<String> getPmForRemindMail(String status, Date begin, Date end);
    
    public List<Object[]> getDataForRemindPmMail(String status, String loginId, Date begin, Date end, String site);
    
    public List<Object[]> getDataForRemindRmMail(String status, String loginId, Date begin, Date end);
    
    public TsTimesheetDay loadByRid(Long rid);
    
    public void deleteTimeSheetData(String loginId,Long tsId, Long masterRid);
    
    public void deleteTimeSheetData(String loginId, Long tsId);
    
    public void deleteTimeSheetInnerData(Long masterRid);
    
    public Boolean isFillTS(String loginId,Date begin,Date end);
    
    public List<Object[]> getPersonAcntForRemindMail(String status, final Date begin, final Date end, String site);
    
    /**
     * 根据LOGINID和时间范围判断是否填写周报
     */
    public Boolean isFillTS(String loginId,Long tsId);
    
    /**
     * 根据LOGINID和时间范围得到已提交的周报
     */
    public List getSubmitTsList(Date begin,Date end);
    
    /**
     * 根据LOGINID和时间范围得到已审批的周报
     */
    public List getApprovedTsList(Date begin,Date end);
    
    public List listTimeSheetDetailByStatusSite(String status, String site);
    
    public List listTimeSheetMasterByStatusSite(String status, String site);
}