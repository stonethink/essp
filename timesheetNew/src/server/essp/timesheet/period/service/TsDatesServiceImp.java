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
	 * ��ȡ��ʱ�������б�
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
	 * ��ȡ���һ����ʱ������
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
	 * ��������������ʱ������
	 */
	public void createTsDates(DtoTsDates dtoTsDates) {
		boolean isDup = false;
		try {
			//�ж��Ƿ��������ʱ�����ص�
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
		//�¿�ʼʱ��
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
	 * ���ݰ��²�����ʽ������ʱ������
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
		int targetMonth = caEnd.get(Calendar.MONTH);//��ȡҪ���������·�
		int maxDay = ca.getActualMaximum(Calendar.DAY_OF_MONTH);
		DtoTimeSheetPeriod dto = null;
		List<DtoTimeSheetPeriod> tsDates = new ArrayList<DtoTimeSheetPeriod>();
		if(month == targetMonth) {//�����뿪ʼ�ͽ���������ͬһ����ʱ���������µĹ�ʱ����
			ca.set(year, month, 1, 0, 0, 0);//����Ϊ���µ�һ������
			ca.set(Calendar.MILLISECOND, 0);
			dto = new DtoTimeSheetPeriod();
			dto.setBeginDate(ca.getTime());
			ca.set(year, month, maxDay, 0, 0, 0);//����Ϊ�������һ������
			ca.set(Calendar.MILLISECOND, 0);
			dto.setEndDate(ca.getTime());
			divByMonth(tsDates, dto);//�����õ��¿�ʼ���ڷָʱ������
		} else {//���������ڲ���ͬһ�£�������¿�ʼ����
			ca.add(Calendar.MONTH, 1);
			month = ca.get(Calendar.MONTH);
			year = ca.get(Calendar.YEAR);
			maxDay = ca.getActualMaximum(Calendar.DAY_OF_MONTH);
			ca.set(year, month, 1, 0, 0, 0);//����Ϊ���µ�һ������
			ca.set(Calendar.MILLISECOND, 0);
			dto = new DtoTimeSheetPeriod();
			dto.setBeginDate(ca.getTime());
			ca.set(year, month, maxDay, 0, 0, 0);//����Ϊ�������һ������
			ca.set(Calendar.MILLISECOND, 0);
			dto.setEndDate(ca.getTime());
			divByMonth(tsDates, dto);//�����õ��¿�ʼ���ڷָʱ������
			while(month < targetMonth) {//��������·ݻ�С�ڽ��������·����������
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
	 * �����ڷ�ʽ������ʱ������
	 * @param dtoTsDates
	 * @return
	 */
	private List<DtoTimeSheetPeriod> createTsDatesForWeeks(DtoTsDates dtoTsDates) {
		//��ʱ�������������
		int createCycle = getCreateCycle(dtoTsDates.getCreateWay());
		DtoTimeSheetPeriod dto = new DtoTimeSheetPeriod();
		dto.setBeginDate(getStartDate(dtoTsDates.getStartDate(), 
				                      dtoTsDates.getEndsOn()));
		dto.setEndDate(getEndDate(dto.getBeginDate(), createCycle));
		List<DtoTimeSheetPeriod> tsDates = new ArrayList<DtoTimeSheetPeriod>();
		divByMonth(tsDates, dto);//���·ָʱ����
		Calendar ca = Calendar.getInstance();
		//��������Ĺ�ʱ������Ľ�������������Ľ�������֮���԰����������������������ʱ������
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
	 * �����¿�ʼʱ��ָʱ������
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
		//�����ʱ����������¿�ʼ������ָʱ������
		if((monthStart.after(dto.getBeginDate())
		&& monthStart.before(dto.getEndDate()))
		|| (monthStart.compareTo(dto.getEndDate()) == 0)) {
			//�¿�ʼʱ���ǰһ��
			caMonthStartDay.add(Calendar.DATE, -1);
			//��һ�ι�ʱ������������¿�ʼ���ڵ�ǰһ��
			DtoTimeSheetPeriod dtoDiv = new DtoTimeSheetPeriod();
			dtoDiv.setBeginDate(dto.getBeginDate());
			dtoDiv.setEndDate(caMonthStartDay.getTime());
			divByFirst(tsDates, dtoDiv);
			//��һ�ι�ʱ�����俪ʼ���¿�ʼ����
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
	 * ���ڶ��ָ��ս���ʱ�����ڷָ�
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
		//�����ʱ���������1����ָʱ������
		if((firstDay.after(dto.getBeginDate())
		&& firstDay.before(dto.getEndDate()))
		|| (firstDay.compareTo(dto.getEndDate()) == 0)) {
			//�ڶ��ָ��յ�ǰһ��
			caFirst.add(Calendar.DATE, -1);
			//��һ�ι�ʱ����������ڵڶ��ָ��յ�ǰһ��
			DtoTimeSheetPeriod dtoDiv = new DtoTimeSheetPeriod();
			dtoDiv.setBeginDate(dto.getBeginDate());
			dtoDiv.setEndDate(caFirst.getTime());
			tsDates.add(dtoDiv);
			//��һ�ι�ʱ�����俪ʼ�ڵڶ��ָ���
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
	 * �����ݿ��������Ĺ�ʱ����
	 * @param tsDates
	 */
	private void insertIntoDB(List<DtoTimeSheetPeriod> tsDates) {
		if(tsDates == null || tsDates.size() == 0) {
			return;
		}
		for(DtoTimeSheetPeriod dto : tsDates) {
			dto.setTsId(periodP3DbDao.getNewTsDatesId());//��ȡ�µ�Id
			periodP3DbDao.insertPeriod(dto);
		}
	}
	/**
	 * ��ȡ��ʱ����������
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
	 * ��ȡҪ�����Ĺ�ʱ������Ŀ�ʼ����
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
	 * ��ȡҪ�����Ĺ�ʱ������Ľ�������
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
	 * ����һ����ʱ������
	 */
	public void addPeriod(DtoTimeSheetPeriod dtoPeriod) {
		boolean isDup = false;
		try {
			//�ж��Ƿ��������ʱ�����ص�
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
		//�¿�ʼʱ��
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
	 * ɾ��һ���������ʱ������
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
