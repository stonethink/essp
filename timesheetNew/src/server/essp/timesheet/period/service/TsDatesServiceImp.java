package server.essp.timesheet.period.service;

import java.util.*;

import server.essp.timesheet.period.dao.IPeriodDao;
import server.essp.timesheet.period.dao.IPeriodP3DbDao;
import server.essp.timesheet.preference.dao.IPreferenceDao;
import server.framework.common.BusinessException;
import c2s.essp.timesheet.preference.DtoPreference;
import c2s.essp.timesheet.tsdates.DtoTsDates;
import c2s.essp.timesheet.weeklyreport.DtoTimeSheetPeriod;

import com.wits.util.comDate;

import db.essp.timesheet.TsParameter;

public class TsDatesServiceImp implements ITsDatesService{
	
	private IPeriodDao periodDao;
	private IPreferenceDao preferenceDao;
	private IPeriodP3DbDao periodP3DbDao;
	private int monthStartDay;
	private int monthStartDayTwo;
	public void setPeriodP3DbDao(IPeriodP3DbDao periodP3DbDao) {
		this.periodP3DbDao = periodP3DbDao;
	}
	public void setPreferenceDao(IPreferenceDao preferenceDao) {
		this.preferenceDao = preferenceDao;
	}
	public void setPeriodDao(IPeriodDao periodDao) {
		this.periodDao = periodDao;
	}
	/**
	 * 获取工时单区间列表
	 */
	public List getTsDatesList() {
		try {
			return periodDao.getAllTimeSheetPeriod();
		} catch (Exception e) {
			throw new BusinessException("error.logic.tsDates.getPeriodErr",
                    "get period error", e);
		}
	}
	/**
	 * 获取最后一个工时单区间
	 */
	public DtoTimeSheetPeriod getLastTsDate() {
		try {
			return periodDao.getLastEndPeriod();
		} catch (Exception e) {
			throw new BusinessException("error.logic.tsDates.getPeriodErr",
                    "get period error", e);
		}
	}
	/**
	 * 根据条件产生工时单区间
	 */
	public void createTsDates(DtoTsDates dtoTsDates) {
		boolean isDup = false;
		try {
			//判断是否和其他工时区间重叠
			isDup = periodDao.isDuplicate(dtoTsDates.getStartDate(), 
										  dtoTsDates.getEndDate());
		} catch (Exception e) {
			throw new BusinessException("error.logic.tsDates.getPeriodErr",
                    "get period error", e);
		}
		if(isDup) {
			throw new BusinessException("error.logic.TsDatesServiceImp.tsDatesDup",
                    "create period error");
		}
		TsParameter tsParameter = preferenceDao.loadPreferenceBySite(DtoPreference.DTO_SITE_GLOBAL);
		//月开始时间
		monthStartDay = tsParameter.getMonthStartDay().intValue();
	    monthStartDayTwo = tsParameter.getMonthStartDayTwo().intValue();
	    if(monthStartDay < monthStartDayTwo) {
	    	int tmp = monthStartDayTwo;
	    	monthStartDayTwo = monthStartDay;
	    	monthStartDay = tmp;
	    }
		List<DtoTimeSheetPeriod> tsDates = new ArrayList<DtoTimeSheetPeriod>();
		if(!DtoTimeSheetPeriod.CREATE_WAY_EVERYMONTH
				     .equals(dtoTsDates.getCreateWay())) {
			tsDates = createTsDatesForWeeks(dtoTsDates);
		} else if(DtoTimeSheetPeriod.CREATE_WAY_EVERYMONTH
			     			.equals(dtoTsDates.getCreateWay())){
			tsDates = createTsDatesForMonth(dtoTsDates);
		}
		insertIntoDB(tsDates);
	}
	/**
	 * 根据按月产生方式产生工时单区间
	 * @param dtoTsDates
	 * @return
	 */
	private List<DtoTimeSheetPeriod> createTsDatesForMonth(DtoTsDates dtoTsDates) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(dtoTsDates.getStartDate());
		int year = ca.get(Calendar.YEAR);
		int month = ca.get(Calendar.MONTH);
		Calendar caEnd = Calendar.getInstance();
		caEnd.setTime(dtoTsDates.getEndDate());
		int targetMonth = caEnd.get(Calendar.MONTH);//获取要产生到的月份
		int maxDay = ca.getActualMaximum(Calendar.DAY_OF_MONTH);
		DtoTimeSheetPeriod dto = null;
		List<DtoTimeSheetPeriod> tsDates = new ArrayList<DtoTimeSheetPeriod>();
		if(month == targetMonth) {//当输入开始和结束日期在同一月内时，产生当月的工时区间
			ca.set(year, month, 1, 0, 0, 0);//设置为当月第一天日期
			ca.set(Calendar.MILLISECOND, 0);
			dto = new DtoTimeSheetPeriod();
			dto.setBeginDate(ca.getTime());
			ca.set(year, month, maxDay, 0, 0, 0);//设置为当月最后一天日期
			ca.set(Calendar.MILLISECOND, 0);
			dto.setEndDate(ca.getTime());
			divByMonth(tsDates, dto);//按设置的月开始日期分割工时单区间
		} else {//若输入日期不在同一月，则从下月开始产生
			ca.add(Calendar.MONTH, 1);
			month = ca.get(Calendar.MONTH);
			year = ca.get(Calendar.YEAR);
			maxDay = ca.getActualMaximum(Calendar.DAY_OF_MONTH);
			ca.set(year, month, 1, 0, 0, 0);//设置为当月第一天日期
			ca.set(Calendar.MILLISECOND, 0);
			dto = new DtoTimeSheetPeriod();
			dto.setBeginDate(ca.getTime());
			ca.set(year, month, maxDay, 0, 0, 0);//设置为当月最后一天日期
			ca.set(Calendar.MILLISECOND, 0);
			dto.setEndDate(ca.getTime());
			divByMonth(tsDates, dto);//按设置的月开始日期分割工时单区间
			while(month < targetMonth) {//如果产生月份还小于结束日期月份则继续产生
				ca.add(Calendar.MONTH, 1);
				year = ca.get(Calendar.YEAR);
				month++;
				maxDay = ca.getActualMaximum(Calendar.DAY_OF_MONTH);
				ca.set(year, month, 1, 0, 0, 0);
				ca.set(Calendar.MILLISECOND, 0);
				dto = new DtoTimeSheetPeriod();
				dto.setBeginDate(ca.getTime());
				ca.set(year, month, maxDay, 0, 0, 0);
				ca.set(Calendar.MILLISECOND, 0);
				dto.setEndDate(ca.getTime());
				divByMonth(tsDates, dto);
			}
		}
		return tsDates;
	}
	/**
	 * 按星期方式产生工时单区间
	 * @param dtoTsDates
	 * @return
	 */
	private List<DtoTimeSheetPeriod> createTsDatesForWeeks(DtoTsDates dtoTsDates) {
		//工时单区间产生周期
		int createCycle = getCreateCycle(dtoTsDates.getCreateWay());
		DtoTimeSheetPeriod dto = new DtoTimeSheetPeriod();
		dto.setBeginDate(getStartDate(dtoTsDates.getStartDate(), 
				                      dtoTsDates.getEndsOn()));
		dto.setEndDate(getEndDate(dto.getBeginDate(), createCycle));
		List<DtoTimeSheetPeriod> tsDates = new ArrayList<DtoTimeSheetPeriod>();
		divByMonth(tsDates, dto);//按月分割工时区间
		Calendar ca = Calendar.getInstance();
		//如果产生的工时单区间的结束日期与输入的结束日期之间仍包含产生周期则继续产生工时单区间
		while(comDate.getBetweenDays(dto.getEndDate(), 
				dtoTsDates.getEndDate()) > createCycle){
			ca.setTime(dto.getEndDate());
			ca.add(Calendar.DATE, 1);
			dto = new DtoTimeSheetPeriod();
			dto.setBeginDate(ca.getTime());
			dto.setEndDate(getEndDate(dto.getBeginDate(), createCycle));
			divByMonth(tsDates, dto);
		}
		return tsDates;
	}
	
	/**
	 * 根据月开始时间分割工时单区间
	 * @param tsDates
	 * @param dto
	 */
	private void divByMonth(List<DtoTimeSheetPeriod> tsDates, 
			                DtoTimeSheetPeriod dto) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(dto.getBeginDate());

		Calendar caMonthStartDay = Calendar.getInstance();
		caMonthStartDay.set(ca.get(Calendar.YEAR), ca.get(Calendar.MONTH),
							monthStartDay, 0, 0, 0);
		caMonthStartDay.set(Calendar.MILLISECOND, 0);
		Date monthStart = caMonthStartDay.getTime();
		//如果工时单区间包含月开始日期则分割工时单区间
		if((monthStart.after(dto.getBeginDate())
		&& monthStart.before(dto.getEndDate()))
		|| (monthStart.compareTo(dto.getEndDate()) == 0)) {
			//月开始时间的前一天
			caMonthStartDay.add(Calendar.DATE, -1);
			//第一段工时单区间结束于月开始日期的前一天
			DtoTimeSheetPeriod dtoDiv = new DtoTimeSheetPeriod();
			dtoDiv.setBeginDate(dto.getBeginDate());
			dtoDiv.setEndDate(caMonthStartDay.getTime());
			divByFirst(tsDates, dtoDiv);
			//后一段工时单区间开始于月开始日期
			caMonthStartDay.add(Calendar.DATE, 1);
			dtoDiv = new DtoTimeSheetPeriod();
			dtoDiv.setBeginDate(caMonthStartDay.getTime());
			dtoDiv.setEndDate(dto.getEndDate());
			divByFirst(tsDates, dtoDiv);
		} else {
			divByFirst(tsDates, dto);
		}
	}
	/**
	 * 按第二分割日将工时单日期分割
	 * @param tsDates
	 * @param dto
	 */
	private void divByFirst(List<DtoTimeSheetPeriod> tsDates, 
            				DtoTimeSheetPeriod dto) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(dto.getEndDate());
		Calendar caFirst = Calendar.getInstance();
		caFirst.set(ca.get(Calendar.YEAR), ca.get(Calendar.MONTH), monthStartDayTwo, 0, 0, 0);
		caFirst.set(Calendar.MILLISECOND, 0);
		Date firstDay = caFirst.getTime();
		//如果工时单区间包含1日则分割工时单区间
		if((firstDay.after(dto.getBeginDate())
		&& firstDay.before(dto.getEndDate()))
		|| (firstDay.compareTo(dto.getEndDate()) == 0)) {
			//第二分割日的前一天
			caFirst.add(Calendar.DATE, -1);
			//第一段工时单区间结束于第二分割日的前一天
			DtoTimeSheetPeriod dtoDiv = new DtoTimeSheetPeriod();
			dtoDiv.setBeginDate(dto.getBeginDate());
			dtoDiv.setEndDate(caFirst.getTime());
			tsDates.add(dtoDiv);
			//后一段工时单区间开始于第二分割日
			caFirst.add(Calendar.DATE, 1);
			dtoDiv = new DtoTimeSheetPeriod();
			dtoDiv.setBeginDate(caFirst.getTime());
			dtoDiv.setEndDate(dto.getEndDate());
			tsDates.add(dtoDiv);
		} else {
			dto.setBeginDate(dto.getBeginDate());
			dto.setEndDate(dto.getEndDate());
			tsDates.add(dto);
		}
	}
	/**
	 * 向数据库插入产生的工时区间
	 * @param tsDates
	 */
	private void insertIntoDB(List<DtoTimeSheetPeriod> tsDates) {
		if(tsDates == null || tsDates.size() == 0) {
			return;
		}
		for(DtoTimeSheetPeriod dto : tsDates) {
			dto.setTsId(periodP3DbDao.getNewTsDatesId());//获取新的Id
			periodP3DbDao.insertPeriod(dto);
		}
	}
	/**
	 * 获取工时单产生周期
	 * @param createWay
	 * @return
	 */
	private int getCreateCycle(String createWay) {
		if(DtoTimeSheetPeriod.CREATE_WAY_EVERYTWOWEEKS.equals(createWay)) {
			return 13;
		} else if(DtoTimeSheetPeriod.CREATE_WAY_EVERYFOURWEEKS.equals(createWay)) {
			return 27;
		}
		return 6;
	}
	/**
	 * 获取要产生的工时单区间的开始日期
	 * @param begin
	 * @param endsOn
	 * @return
	 */
	private Date getStartDate(Date begin, int endsOn) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(begin);
		while(endsOn - ca.get(Calendar.DAY_OF_WEEK) != -1){
			ca.add(Calendar.DATE, 1);
		}
		return ca.getTime();
	}
	/**
	 * 获取要产生的工时单区间的结束日期
	 * @param begin
	 * @param createCycle
	 * @return
	 */
	private Date getEndDate(Date begin, int createCycle) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(begin);
		ca.add(Calendar.DATE, createCycle);
		return ca.getTime();
	}
	/**
	 * 新增一条工时单区间
	 */
	public void addPeriod(DtoTimeSheetPeriod dtoPeriod) {
		boolean isDup = false;
		try {
			//判断是否和其他工时区间重叠
			isDup = periodDao.isDuplicate(dtoPeriod.getBeginDate(), 
										  dtoPeriod.getEndDate());
		} catch (Exception e) {
			throw new BusinessException("error.logic.tsDates.getPeriodErr",
                    "get period error", e);
		}
		if(isDup) {
			throw new BusinessException("error.logic.TsDatesServiceImp.tsDatesDup",
                    "create period error");
		}
		TsParameter tsParameter = preferenceDao.loadPreferenceBySite(DtoPreference.DTO_SITE_GLOBAL);
		//月开始时间
		monthStartDay = tsParameter.getMonthStartDay().intValue();
	    monthStartDayTwo = tsParameter.getMonthStartDayTwo().intValue();
	    if(monthStartDay < monthStartDayTwo) {
	    	int tmp = monthStartDayTwo;
	    	monthStartDayTwo = monthStartDay;
	    	monthStartDay = tmp;
	    }
		List<DtoTimeSheetPeriod> tsDates = new ArrayList<DtoTimeSheetPeriod>();
		divByMonth(tsDates, dtoPeriod);
		insertIntoDB(tsDates);
		
	}
	/**
	 * 删除一条或多条工时单区间
	 */
	public void detelePeriod(List<DtoTimeSheetPeriod> tsDates) {
		try {
			for (DtoTimeSheetPeriod dtoPreiod : tsDates) {
				periodP3DbDao.deletePeriod(dtoPreiod.getTsId());
			}
		} catch (Exception e) {
			throw new BusinessException("error.logic.tsDates.delPeriodErr",
					"delete period error", e);
		}
	}
	
}
