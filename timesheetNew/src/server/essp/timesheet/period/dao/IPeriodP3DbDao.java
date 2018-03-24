package server.essp.timesheet.period.dao;

import c2s.essp.timesheet.weeklyreport.DtoTimeSheetPeriod;

public interface IPeriodP3DbDao {
	
	/**
	 * 新增TimeSheet period
	 * @param dtoPeriod
	 */
	public void insertPeriod(DtoTimeSheetPeriod dtoPeriod);
	
	/**
	 * 获取新的TsDates主键值
	 * @return
	 */
	public Long getNewTsDatesId();
	
	/**
	 * 删除TimeSheet period
	 * @param tsId
	 */
	public void deletePeriod(Long tsId);

}
