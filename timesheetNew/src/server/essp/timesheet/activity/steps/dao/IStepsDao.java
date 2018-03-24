package server.essp.timesheet.activity.steps.dao;

import java.util.List;

/**
 * <p>Description:StepsDao�Ľӿ�</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author not attributable
 * @version 1.0
 */
public interface IStepsDao {
    /**
     * ����activityRid�õ�Step
     * @param activityObjId Long
     * @return List
     * @throws Exception
     */
    public List queryStep(Long activityRid) throws Exception;
    /**
     * �޸�
     * @param stepList List
     * @return List
     * @throws Exception
     */
    public List updateSteps(List stepList) throws Exception;
}
