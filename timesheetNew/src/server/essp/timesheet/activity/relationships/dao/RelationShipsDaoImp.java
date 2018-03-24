package server.essp.timesheet.activity.relationships.dao;

import java.util.*;

import c2s.dto.DtoUtil;
import c2s.essp.timesheet.activity.DtoRelationShips;
import c2s.essp.timesheet.activity.DtoRelationShipsDetail;
import com.primavera.common.value.ObjectId;
import com.primavera.integration.client.bo.BOIterator;
import com.primavera.integration.client.bo.object.Activity;
import com.primavera.integration.client.bo.object.Resource;
import server.essp.common.primavera.PrimaveraApiBase;

/**
 * <p>Description:RelationShipsDao的实现 </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author not attributable
 * @version 1.0
 */
public class RelationShipsDaoImp extends PrimaveraApiBase implements
        IRelationShipsDao {
    private final static Map<String, String> predecessorPropertiesMap;
    private final static Map<String, String> successorPropertiesMap;
    private final static Map<String, String> relationDetailPropertiesMap;

    /**
     * 查询指定Activity的前紧作业
     * @param activityObjId Long
     * @return List
     * @throws Exception
     */
    public List queryPredecessor(Long activityObjId) throws Exception {
        Activity activity = getActivity(activityObjId);
        if(activity == null) return null;
        BOIterator boiRelation = activity.loadPredecessorRelationships(
                new String[] {"PredecessorActivityObjectId",
                "PredecessorActivityId", "PredecessorActivityName",
                "PredecessorProjectId", "Lag", "Type"}, null, null);
        if (boiRelation.hasNext()) {
            return DtoUtil.array2List(boiRelation.getAll(),
                                      DtoRelationShips.class,
                                      predecessorPropertiesMap);
        }
        return null;
    }

    /**
     * 查询指定Activity的后续作业
     * @param activityObjId Long
     * @return List
     * @throws Exception
     */
    public List querySuccessor(Long activityObjId) throws Exception {
        Activity activity = getActivity(activityObjId);
        if(activity == null) return null;
        BOIterator boiRelation = activity.loadSuccessorRelationships(
            new String[] { "SuccessorActivityId", "SuccessorActivityName",
            "SuccessorProjectId", "Lag", "Type"}, null, null);
        if (boiRelation.hasNext()) {
            return DtoUtil.array2List(boiRelation.getAll(),
                                      DtoRelationShips.class,
                                      successorPropertiesMap);
        }
        return null;

    }

    /**
     * 根据ObjectId获取Activity
     * @param activityObjId Long
     * @return Activity
     * @throws Exception
     */
    private Activity getActivity(Long activityObjId) throws Exception {
        ObjectId activityId = new ObjectId(activityObjId);
        return Activity.load(this.getSession(), null, activityId);
    }


    /**
     * 根据activityRid查询RelationDetail
     * @param activityObjId Long
     * @return DtoRelationShipsDetail
     * @throws Exception
     */
    public DtoRelationShipsDetail queryRelationDetail(Long activityObjId) throws
               Exception {
           DtoRelationShipsDetail dtoRelDetail = new DtoRelationShipsDetail();
           Activity activity;
           if (activityObjId != null) {
               activity = Activity.load(getSession(), new String[] {"Status", "StartDate",
                       "FinishDate", "PrimaryResourceObjectId"}, new ObjectId(activityObjId));
               if (activity != null) {
                   DtoUtil.copyBeanToBeanWithPropertyMap(dtoRelDetail,
                           activity,
                           relationDetailPropertiesMap);
                   if(activity.getStartDate() != null){
                       dtoRelDetail.setStartDate(new Date(activity.getStartDate().
                               getTime()));
                   }
                   if(activity.getFinishDate() != null){
                       dtoRelDetail.setFinishDate(new Date(activity.
                               getFinishDate().getTime()));
                   }
               }

              Resource resource = null;
              if(activity.getPrimaryResourceObjectId() != null) {
            	  resource = Resource.load(getSession(),
                                     new String[] {"Name","OfficePhone",
                                     "EmailAddress"},
                                     activity.getPrimaryResourceObjectId());
              }
              if(resource != null) {
                  dtoRelDetail.setEmail(resource.getEmailAddress());
                  dtoRelDetail.setOfficePhone(resource.getOfficePhone());
                  dtoRelDetail.setPrimaryResource(resource.getName());
              }

           }
               return dtoRelDetail;
         }

    static{
        predecessorPropertiesMap = new HashMap();
        successorPropertiesMap = new HashMap();
        predecessorPropertiesMap.put("lag","lag");
        predecessorPropertiesMap.put("type","type");
        predecessorPropertiesMap.put("predecessorActivityObjectId","activityObjectId");
        predecessorPropertiesMap.put("predecessorActivityId","activityId");
        predecessorPropertiesMap.put("predecessorActivityName","activityName");
        predecessorPropertiesMap.put("PredecessorProjectId","projectId");

        successorPropertiesMap.put("lag","lag");
        successorPropertiesMap.put("type","type");
        successorPropertiesMap.put("successorActivityId","activityId");
        successorPropertiesMap.put("successorActivityName","activityName");
        successorPropertiesMap.put("successorActivityObjectId","activityObjectId");
        successorPropertiesMap.put("SuccessorProjectId","projectId");

        relationDetailPropertiesMap = new HashMap();
        relationDetailPropertiesMap.put("status", "status");
        relationDetailPropertiesMap.put("primaryResourceName", "primaryResource");
        relationDetailPropertiesMap.put("startDate", "startDate");
        relationDetailPropertiesMap.put("emailAddress", "email");
        relationDetailPropertiesMap.put("finishDate", "finishDate");
        relationDetailPropertiesMap.put("officePhone", "officePhone");
    }


}
