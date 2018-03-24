/*
 * Created on 2008-5-27
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package c2s.essp.timesheet.step.monitoring;

import c2s.dto.DtoBase;

public class DtoMonitoringQuery extends DtoBase{
       public static String DTO_PROJECT_LIST = "projectList";
       public static String DTO_TREE_NODE = "TreeNode";
       public static String DTO_NOT_START="notStart";
       public static String DTO_IN_PROCESS="inProcess";
       public static String DTO_COMPLETED="completed";
       public static String DTO_PROJECT_ID="projectId";
       public static String DTO_PROJECT_NAME="projectName";
       public static String DTO_CHECK_MANAGER="checkManager";
       public static String DTO_CHECK_RESOURCE="checkResource";
       public static String DTO_START="start";
       public static String DTO_FINISH="finish";
       public static String DTO_MANAGER="manager";
       public static String DTO_RESOURCE="resource";
       public static String DTO_ACTIVITY_NAME = "activityName";
       public static String DTO_WBS_NAME="wbs";
       public static String DTO_CHECK_OTHER="checkOthers";
       
       private Long projId;
       public boolean notStart = true;
       public boolean inProgress = true;
       public boolean completed = false;
    
        public boolean isCompleted() {
            return completed;
        }
        
        public void setCompleted(boolean completed) {
            this.completed = completed;
        }
        
        public boolean isInProgress() {
            return inProgress;
        }
        
        public void setInProgress(boolean inProgress) {
            this.inProgress = inProgress;
        }
        
        public boolean isNotStart() {
            return notStart;
        }
        
        public void setNotStart(boolean notStart) {
            this.notStart = notStart;
        }

        public Long getProjId() {
            return projId;
        }

        public void setProjId(Long projId) {
            this.projId = projId;
        }

       
      
        
    }
