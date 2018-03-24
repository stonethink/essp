package server.essp.timesheet.activity.steps.dao;

import java.util.List;

/**
 * <p>Description:StepsDao的接口</p>
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
     * 根据activityRid得到Step
     * @param activityObjId Long
     * @return List
     * @throws Exception
     */
    public List queryStep(Long activityRid) throws Exception;
    /**
     * 修改
     * @param stepList List
     * @return List
     * @throws Exception
     */
    public List updateSteps(List stepList) throws Exception;
}
