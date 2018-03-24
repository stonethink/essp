package server.essp.timesheet.dailyreport.service;

import java.util.*;

import c2s.essp.timesheet.dailyreport.DtoDrActivity;
import c2s.essp.timesheet.dailyreport.DtoDrStep;

public interface IDailyReportService {
	
	/**
	 * 根据工号和作业代码查询step信息
	 * @param loginId
	 * @param activityId
	 * @return
	 */
	public List listByActivityId(String loginId, Long activityId, Long accountRid);
	
	/**
	 * 保存日报信息
	 * @param list
	 * @param loginId
	 * @param day
	 */
	public void saveDailyReport(List<DtoDrActivity> list, String loginId, Date day);
	
	/**
	 * 列出某人已经填写的日报信息
	 * @param loginId
	 * @param day
	 * @return
	 */
	public List listActivityInDB(String loginId, Date day);
	
	/**
	 * 列出某个作业下所有的step
	 * @param dto
	 * @return
	 */
	public List showAllSteps(DtoDrActivity dto, String loginId);
	
	/**
	 * 删除某天某人填写的的某条作业下的日报
	 * @param day
	 * @param dto
	 * @param loginId
	 */
	public void delDailyReportInAct(Date day, DtoDrActivity dto, String loginId);
	
	/**
	 * 显示所有日报数据
	 * @param day
	 * @param loginId
	 * @return
	 */
	public List displayAllData(Date day, String loginId);
	
	/**
	 * 删除相应的日报数据
	 * @param dtoDrStep
	 */
	public void deleteDailyReport(DtoDrStep dtoDrStep);
	
	
	/**
	 * 查询资料并发送邮件
	 * @throws Exception
	 */
	public void exportAndSendMail(Date today, Date yesterday) throws Exception;
}
