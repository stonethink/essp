package server.essp.timesheet.activity.steps.service;

import java.util.List;

/**
 * <p>Description:StepsService的接口 </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author not attributable
 * @version 1.0
 */
public interface IStepsService {
    /**
     * 根据activityRid得到Step
     * @param activityObjId Long
     * @return List
     * @throws Exception
     */
   public  List getStepsList(Long activityObjId);

   /**
    * 修改ActivityStep
    * @param stepList List
    * @return List
    * @throws Exception
    */
   public List updateActivityStep(List stepList);
}
