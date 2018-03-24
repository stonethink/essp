package server.essp.timesheet.step.management.service;

import java.util.Date;
import java.util.List;
import java.util.Vector;

import c2s.dto.ITreeNode;
import c2s.essp.timesheet.step.management.DtoActivityForStep;
import c2s.essp.timesheet.step.management.DtoStep;

public interface IStepManagementService {

	/**
	 * 根據項目ID獲取對應資源的活動列表
	 * @param projectObjectId
	 * @return
	 */
    public List listActivity(String loginId,String projectObjectId);
    
    /**
     * 根據活動ID獲取Step列表
     * @param actId
     * @return
     */
    public List listStepByActivityId(Long activityId);
    
    /**
     * 保存Step列表
     * @param step
     */
    public void saveStepList(List<DtoStep> stepList);
	

	/**
	 * 獲取項目列表
	 * @return
	 */
	public Vector listAcnt(String loginId);
	
	/**
	 * 根据Activity　ObjectId，开始日期，结束日期查询所有的工作日
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
