package server.essp.timesheet.period.dao;

import c2s.essp.timesheet.weeklyreport.DtoTimeSheetPeriod;

public interface IPeriodP3DbDao {
	
	/**
	 * ����TimeSheet period
	 * @param dtoPeriod
	 */
	public void insertPeriod(DtoTimeSheetPeriod dtoPeriod);
	
	/**
	 * ��ȡ�µ�TsDates����ֵ
	 * @return
	 */
	public Long getNewTsDatesId();
	
	/**
	 * ɾ��TimeSheet period
	 * @param tsId
	 */
	public void deletePeriod(Long tsId);

}
