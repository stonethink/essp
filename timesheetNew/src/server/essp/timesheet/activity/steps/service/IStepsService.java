package server.essp.timesheet.activity.steps.service;

import java.util.List;

/**
 * <p>Description:StepsService�Ľӿ� </p>
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
     * ����activityRid�õ�Step
     * @param activityObjId Long
     * @return List
     * @throws Exception
     */
   public  List getStepsList(Long activityObjId);

   /**
    * �޸�ActivityStep
    * @param stepList List
    * @return List
    * @throws Exception
    */
   public List updateActivityStep(List stepList);
}
