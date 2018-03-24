package server.essp.timesheet.activity.steps.dao;

import server.essp.common.primavera.PrimaveraApiBase;

import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import java.util.HashMap;
import com.primavera.integration.client.bo.BOIterator;
import c2s.dto.DtoUtil;
import com.primavera.integration.client.bo.object.Activity;
import c2s.essp.timesheet.activity.DtoActivityStep;
import com.primavera.integration.client.bo.object.ActivityStep;

/**
 * <p>Description: StepsDao的实现</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author not attributable
 * @version 1.0
 */
public class StepsDaoImp extends PrimaveraApiBase implements
        IStepsDao {
    private final static Map<String, String> stepsPropertiesMap;
    /**
     * 根据activityRid得到Step
     * @param activityObjId Long
     * @return List
     * @throws Exception
     */
    public List queryStep(Long activityRid) throws Exception {
        String strWhere = "ObjectId = " + activityRid;
        String strStepWhere = "ActivityObjectId = " + activityRid;
        BOIterator boiActiviy = getCurrentResource().loadActivities(
                new String[]{"ObjectId"}, strWhere, "");
        if(boiActiviy.hasNext()){
             Activity activity = (Activity)boiActiviy.next();
             BOIterator stepBoi = activity.loadActivitySteps(new String[]{"ActivityObjectId",
                     "Name","IsCompleted","ObjectId","Description"},strStepWhere,null);
             if(stepBoi.hasNext()){
                 return DtoUtil.array2List(stepBoi.getAll(),
                                           DtoActivityStep.class,
                                           stepsPropertiesMap);
             }
        }
       return new ArrayList();
    }

    /**
     * 根据查询条件得到满足条件的ActivityStep
     * @param activityRid Long
     * @param rid Long
     * @return ActivityStep
     * @throws Exception
     */
    private ActivityStep getActivitySteps(Long activityRid, Long rid) throws Exception {
              ActivityStep activityStep;
              String strWhere = "ObjectId = " + activityRid;
              String strStepWhere = "ObjectId = " + rid;
              BOIterator boiActiviy = getCurrentResource().loadActivities(
                      new String[]{"ObjectId"}, strWhere, "");
              if(boiActiviy.hasNext()){
                   Activity activity = (Activity)boiActiviy.next();
                   BOIterator stepBoi = activity.loadActivitySteps(new String[]{"ObjectId",
                           "Description"},strStepWhere,null);
                   if(stepBoi.hasNext()){
                     activityStep = (ActivityStep)stepBoi.next();
                     return activityStep;
                   }
              }
              return null;
        }

        /**
         * 修改
         * @param stepList List
         * @return List
         * @throws Exception
         */
        public List updateSteps(List stepList) throws Exception {
            List sList = new ArrayList();
            if(stepList != null){
            for (int i = 0; i < stepList.size(); i++) {
                DtoActivityStep dtoStep = (DtoActivityStep) stepList.get(i);
                ActivityStep step = getActivitySteps(dtoStep.getActivityObjectId(),
                                                     dtoStep.getObjectId());
                step.setIsCompleted(dtoStep.isIsCompleted());
                step.update();
                sList.add(dtoStep);
              }
              return sList;
            }
            return new ArrayList();
        }


   static{
       stepsPropertiesMap = new HashMap();
       stepsPropertiesMap.put("activityObjectId","activityObjectId");
       stepsPropertiesMap.put("name","name");
       stepsPropertiesMap.put("isCompleted","isCompleted");
       stepsPropertiesMap.put("objectId","objectId");
       stepsPropertiesMap.put("description","description");
   }

}
