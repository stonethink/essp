package server.essp.timesheet.step.management.service;

import java.util.Date;
import java.util.List;
import java.util.Vector;

import c2s.dto.ITreeNode;
import c2s.essp.timesheet.step.management.DtoActivityForStep;
import c2s.essp.timesheet.step.management.DtoStep;

public interface IStepManagementService {

	/**
	 * �����ĿID�@ȡ�����YԴ�Ļ���б�
	 * @param projectObjectId
	 * @return
	 */
    public List listActivity(String loginId,String projectObjectId);
    
    /**
     * �������ID�@ȡStep�б�
     * @param actId
     * @return
     */
    public List listStepByActivityId(Long activityId);
    
    /**
     * ����Step�б�
     * @param step
     */
    public void saveStepList(List<DtoStep> stepList);
	

	/**
	 * �@ȡ�Ŀ�б�
	 * @return
	 */
	public Vector listAcnt(String loginId);
	
	/**
	 * ����Activity��ObjectId����ʼ���ڣ��������ڲ�ѯ���еĹ�����
	 * @param activityObjId
	 * @param start
	 * @param finish
	 * @return
	 */
	public List<Date> getAllWorkDayByProjectCalendar(String activityObjId,
			Date start, Date finish);

	public ITreeNode getDateByActObjId(String actStr);
	
	public DtoActivityForStep getActivity(Long activityId);
	
	public void setExcelDto(boolean excelDto);
}
