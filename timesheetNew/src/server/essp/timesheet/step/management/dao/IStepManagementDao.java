package server.essp.timesheet.step.management.dao;

import java.util.Date;
import java.util.List;

import db.essp.timesheet.TsStep;

public interface IStepManagementDao {
	/**
	 * ͨ����ҵID��ѯ����Step
	 * 
	 * @param activityId
	 * @return
	 */
	public List listStepByActivityId(Long activityId);
	
	public List listDeaultStepsByLongId(String loginId, Long activityId);
	
	public TsStep loadByRid(Long rid);

	/**
	 * ����һ��Step
	 * 
	 * @param step
	 */
	public void addStep(TsStep step);

	/**
	 * ����һ��Step
	 * 
	 * @param step
	 */
	public void updateStep(TsStep step);
    
    public List getAllStepByPrjId(Long projId);
    
    /**
     * ��ȡĳStep�����ձ�������д�������ۼƹ�ʱ
     * @param stepRid Long
     * @return Double
     */
    public Double getStepCumulativeHours(Long stepRid);
    
    /**
     * �г�ĳר����ĳ�˽��������step��Ϣ
     * @param projId
     * @param resourceId
     * @param today
     * @param yesterday
     * @return
     */
    public List listPersonStep(Long projId, String resourceId, 
    		Date today, Date yesterday);
    
    /**
     * �г�ĳר���������˽��������step��Ϣ
     * @param projId
     * @param today
     * @param yesterday
     * @return
     */
    public List listProjectStep(Long projId, Date day, String date);
    
    /**
     * �г�����StepDetail�е���Ϣ
     * @return List
     */
    public List getStepDetailInfo();
}
