package server.essp.timesheet.period.service;

import java.util.List;

import c2s.essp.timesheet.tsdates.DtoTsDates;
import c2s.essp.timesheet.weeklyreport.DtoTimeSheetPeriod;

public interface ITsDatesService {
	
	/**
	 * 获取工时单区间列表
	 * @return List
	 */
	public List getTsDatesList();
	
	/**
	 * 获取最后一个工时单区间
	 * @return
	 */
	public DtoTimeSheetPeriod getLastTsDate();
	
	/**
	 * 根据条件产生工时单区间
	 * @param dtoTsDates
	 */
	public void createTsDates(DtoTsDates dtoTsDates);
	
	/**
	 * 新增一条工时单区间
	 * @param dtoPeriod
	 */
	public void addPeriod(DtoTimeSheetPeriod dtoPeriod);
	
	
	/**
	 * 删除一条或多条工时单区间
	 * @param tsId
	 */
	public void detelePeriod(List<DtoTimeSheetPeriod> tsDates);

}
