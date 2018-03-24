package server.essp.timesheet.activity.notebook.dao;

import server.essp.common.primavera.PrimaveraApiBase;
import java.util.List;
import com.primavera.integration.client.bo.BOIterator;
import c2s.dto.DtoUtil;
import com.primavera.integration.client.bo.object.Activity;
import c2s.essp.timesheet.activity.DtoNotebookTopic;
import java.util.HashMap;
import java.util.Map;
import com.primavera.integration.client.bo.object.ActivityNote;

/**
 * <p>Description:NoteBookDao的实现 </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author not attributable
 * @version 1.0
 */
public class NoteBookDaoImp extends PrimaveraApiBase implements
        INoteBookDao {
     private final static Map<String, String> activityStepPropertiesMap;
     /**
      * 根据activityRid查询出符合条件的记录
      * @param activityRid Long
      * @return List
      * @throws Exception
      */
    public List queryNoteBook(Long activityRid) throws Exception{
        String strWhere = "ObjectId = " + activityRid;
        String strStepWhere = "ActivityObjectId = " + activityRid;
        BOIterator boiActiviy = getCurrentResource().loadActivities(
                new String[]{"ObjectId"}, strWhere, "");
        if(boiActiviy.hasNext()){
             Activity activity = (Activity)boiActiviy.next();
             BOIterator stepBoi = activity.loadActivityNotes(new String[]{"ActivityObjectId",
                     "NotebookTopicName","ObjectId","Note"},strStepWhere,null);
             if(stepBoi.hasNext()){
                 return DtoUtil.array2List(stepBoi.getAll(),
                                           DtoNotebookTopic.class,
                                           activityStepPropertiesMap);
             }
        }
       return null;
    }

    static {
           activityStepPropertiesMap = new HashMap();
           activityStepPropertiesMap.put("activityObjectId", "activityObjectId");
           activityStepPropertiesMap.put("notebookTopicName", "notebookTopicName");
           activityStepPropertiesMap.put("objectId", "objectId");
           activityStepPropertiesMap.put("note", "note");
    }


}
