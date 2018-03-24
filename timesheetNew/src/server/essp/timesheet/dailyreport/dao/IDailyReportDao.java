package server.essp.timesheet.dailyreport.dao;

import java.util.Date;
import java.util.List;

import c2s.essp.timesheet.dailyreport.DtoDrActivity;

import db.essp.timesheet.TsDailyReport;

public interface IDailyReportDao {
	
	public List listDailyReport(Long activityId);
	
	public TsDailyReport loadDailtReportByRid(Long rid);
	
	public void updateDailyReport(TsDailyReport tsDailyReport);
	
	public void addDailyReport(TsDailyReport tsDailyReport);
	
	public List listTodayDailyReportByLoginId(String loginId, Date day);
	
	public void delete(TsDailyReport tsDailyReport);
	
	public void delDailyReport(Date day, DtoDrActivity dto, String employeeId);
	
	public List listDailyReportByDate(String loginId, Date begin, Date end);
	
	public List listHoursByDate(String loginId, Date begin, Date end);
	
	public TsDailyReport loadLastReport(Long stepRid);
}
