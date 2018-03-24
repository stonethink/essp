package server.essp.timesheet.dailyreport.service;

import java.util.*;

import c2s.essp.timesheet.dailyreport.DtoDrActivity;
import c2s.essp.timesheet.dailyreport.DtoDrStep;

public interface IDailyReportService {
	
	/**
	 * ���ݹ��ź���ҵ�����ѯstep��Ϣ
	 * @param loginId
	 * @param activityId
	 * @return
	 */
	public List listByActivityId(String loginId, Long activityId, Long accountRid);
	
	/**
	 * �����ձ���Ϣ
	 * @param list
	 * @param loginId
	 * @param day
	 */
	public void saveDailyReport(List<DtoDrActivity> list, String loginId, Date day);
	
	/**
	 * �г�ĳ���Ѿ���д���ձ���Ϣ
	 * @param loginId
	 * @param day
	 * @return
	 */
	public List listActivityInDB(String loginId, Date day);
	
	/**
	 * �г�ĳ����ҵ�����е�step
	 * @param dto
	 * @return
	 */
	public List showAllSteps(DtoDrActivity dto, String loginId);
	
	/**
	 * ɾ��ĳ��ĳ����д�ĵ�ĳ����ҵ�µ��ձ�
	 * @param day
	 * @param dto
	 * @param loginId
	 */
	public void delDailyReportInAct(Date day, DtoDrActivity dto, String loginId);
	
	/**
	 * ��ʾ�����ձ�����
	 * @param day
	 * @param loginId
	 * @return
	 */
	public List displayAllData(Date day, String loginId);
	
	/**
	 * ɾ����Ӧ���ձ�����
	 * @param dtoDrStep
	 */
	public void deleteDailyReport(DtoDrStep dtoDrStep);
	
	
	/**
	 * ��ѯ���ϲ������ʼ�
	 * @throws Exception
	 */
	public void exportAndSendMail(Date today, Date yesterday) throws Exception;
}
