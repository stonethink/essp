package server.essp.timesheet.period.service;

import java.util.List;

import c2s.essp.timesheet.tsdates.DtoTsDates;
import c2s.essp.timesheet.weeklyreport.DtoTimeSheetPeriod;

public interface ITsDatesService {
	
	/**
	 * ��ȡ��ʱ�������б�
	 * @return List
	 */
	public List getTsDatesList();
	
	/**
	 * ��ȡ���һ����ʱ������
	 * @return
	 */
	public DtoTimeSheetPeriod getLastTsDate();
	
	/**
	 * ��������������ʱ������
	 * @param dtoTsDates
	 */
	public void createTsDates(DtoTsDates dtoTsDates);
	
	/**
	 * ����һ����ʱ������
	 * @param dtoPeriod
	 */
	public void addPeriod(DtoTimeSheetPeriod dtoPeriod);
	
	
	/**
	 * ɾ��һ���������ʱ������
	 * @param tsId
	 */
	public void detelePeriod(List<DtoTimeSheetPeriod> tsDates);

}
