package server.essp.timesheet.activity.steps.service;

import java.util.List;

import server.framework.common.BusinessException;
import server.essp.timesheet.activity.steps.dao.IStepsDao;

/**
 * <p>Description:StepsService的实现 </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author not attributable
 * @version 1.0
 */
public class StepsServiceImp implements IStepsService {
   private IStepsDao istepDao;
   /**
    * 列出StepsList
    * @param activityObjId Long
    * @return List
    */
   public List getStepsList(Long activityObjId) {
        List stepList = null;
        try {
           stepList = istepDao.queryStep(activityObjId);
        } catch (Exception ex) {
            throw new BusinessException("error.logic.TimeSheetServiceImp.loadStepListErr",
                                       "load StepList error", ex);
        }
       return stepList;
    }

    /**
     * 更新
     * @param stepList List
     * @return List
     */
    public List updateActivityStep(List stepList) {
        try {
        stepList = istepDao.updateSteps(stepList);
       } catch (Exception ex) {
           throw new BusinessException("error.logic.TimeSheetServiceImp.updateStepListErr",
                                      "Update Step error", ex);
       }
         return stepList;
     }

    public void setIstepDao(IStepsDao istepDao) {
        this.istepDao = istepDao;
    }


}
