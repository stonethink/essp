/*
 * Created on 2008-5-26
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.timesheet.step.monitoring.dao;


import java.util.*;

import server.essp.common.primavera.PrimaveraApiBase;
import c2s.essp.common.calendar.WorkCalendar;
import c2s.essp.timesheet.dailyreport.DtoDailyExport;

import com.primavera.common.value.ObjectId;
import com.primavera.integration.client.bo.enm.ActivityStatus;
import com.primavera.integration.client.bo.object.*;
import com.primavera.integration.util.WhereClauseHelper;

public class MonitoringDaoP3ApiImp extends PrimaveraApiBase
       implements IMonitoringP3ApiDao{
     
     
       private static String strWhereProcessingActivity
                          = " Status ='" + ActivityStatus.IN_PROGRESS.getValue() + "'";
    
       /**
        * 根據prjObjId得到Project
        * @param prjObjId
        * @return Project
        */
       public Project loadProject(String projectId) {
              String where  = "Id = '"+projectId + "'";
              try {
                Iterator iter = this.getGOM().loadProjects(new String[]{"Id",
                        "WBSObjectId","Name","AnticipatedStartDate",
                        "AnticipatedFinishDate"},where,null);
                if(iter.hasNext()){
                    Project project = (Project)iter.next();
                    return project;
                }
               } catch (Exception e) {
                   log.error(e);
               } 
               return null;
      }

       /**
        * 得到project對應的所有WBS
        * @param project
        * @return List
        */
       public List getAllWbs(Project project) {
               List wbsList = new ArrayList();
               try{
               Iterator iter = project.loadAllWBS(new String[]{"ObjectId","Code","Name",
                       "ParentObjectId","StartDate","FinishDate","SummaryActualStartDate",
                       "SummaryActualFinishDate", "SummaryActualLaborUnits", "SequenceNumber"},null,"SequenceNumber");
               while(iter.hasNext()){
                   WBS wbs = (WBS)iter.next();
                   wbsList.add(wbs);
               }
               }catch (Exception e) {
                   log.error(e);
                } 
               return wbsList;
       }

      /**
       * 得到project對應的所有Activity
       * @param project
       * @param dtoQry
       * @return List
       */
      public List loadAllActivity(Project project) {
            List activityList = new ArrayList();
            try {
               Iterator iter = project.loadAllActivities(new String[]{"Name","PlannedStartDate",
                               "PlannedFinishDate","PlannedLaborUnits","ActualStartDate",
                               "ActualFinishDate","ActualLaborUnits","PrimaryResourceName",
                               "PrimaryResourceId","PercentComplete", "Status"},null,"Id asc");
                while(iter.hasNext()){
                    Activity act = (Activity)iter.next();
                    activityList.add(act);
            }
         } catch (Exception e) {
           log.error(e);
         }
        return activityList;
    }
      /**
       * 根据专案代码查询projId
       * @param accountId
       * @return
       */
      public List listActivityByAcntId(String accountId, Date day, String date) {
		List activityList = new ArrayList();
		String where = "Id = '" + accountId + "'";
		String strBeginDate = WhereClauseHelper.formatDate(WorkCalendar.resetBeginDate(day));
		String strEndDate = WhereClauseHelper.formatDate(WorkCalendar.resetEndDate(day));
		String today = WhereClauseHelper.formatDate(WorkCalendar.resetEndDate(new Date()));
		
		String strWhere = "(PlannedFinishDate >= " + strBeginDate
				+ " and PlannedStartDate <= " + strEndDate + ")"
				+ " or (ActualFinishDate >= " + strBeginDate 
				+ " and ActualStartDate <= " + strEndDate + ")";
//				+ " or (PlannedStartDate <= " + today + " and Status <> '"+ActivityStatus.COMPLETED.getValue()+"')";
		if(DtoDailyExport.DTO_TODAY.equals(date)) {
			strWhere += " or (PlannedStartDate <= " + today + " and Status <> '"+ActivityStatus.COMPLETED.getValue()+"')";
		}
		try {
			Iterator iter = this.getGOM().loadProjects(
					new String[]{"ObjectId"}, where, null);
			Iterator iterAct = null;
			if (iter.hasNext()) {
				Project project = (Project) iter.next();
				iterAct = project.loadAllActivities(
						Activity.getAllFields(), strWhere, "ObjectId");
				while (iterAct.hasNext()) {
					Activity act = (Activity) iterAct.next();
					activityList.add(act);
				}
			}
		} catch (Exception e) {
			log.error(e);
		}
		return activityList;
	 }
      /**
       * 得到project對應的所有ResourceAssignment
       * @param project
       * @return List
       */
        public List loadResourceAssignment(Project project) {
                List resAssginList = new ArrayList();
                try{
                  Iterator iterator =  project.loadAllResourceAssignments(new String[]{
                            "ActivityObjectId","ResourceObjectId"},null,null);
                  while(iterator.hasNext()){
                      ResourceAssignment resAssgin = (ResourceAssignment)iterator.next();
                      resAssginList.add(resAssgin);
                  }
                } catch (Exception e) {
                    log.error(e);
                }
            return resAssginList;
        }
    
        /**
         * 得到project對應的所有Resources
         * @param project
         * @return List
         */
        public List loadResources(Project project) {
            List resList = new ArrayList();
            try{
              Iterator iterator =  project.loadAllResources(new String[]{
                        "ObjectId","Id","Name"},null,null);
              while(iterator.hasNext()){
                  Resource res = (Resource)iterator.next();
                  resList.add(res);
              }
            } catch (Exception e) {
                log.error(e);
            }
            return resList;
        }
    
}
