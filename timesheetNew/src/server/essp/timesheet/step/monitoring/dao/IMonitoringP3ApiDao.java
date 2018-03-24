/*
 * Created on 2008-5-26
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.timesheet.step.monitoring.dao;

import java.util.Date;
import java.util.List;
import c2s.essp.timesheet.step.monitoring.DtoMonitoringQuery;
import com.primavera.common.value.ObjectId;
import com.primavera.integration.client.bo.object.Project;


public interface IMonitoringP3ApiDao {
    
        /**
         * 根據projectId得到Project
         * @param projectId
         * @return Project
         */
        public Project loadProject(String projectId);
              
        /**
         * 得到project對應的所有WBS
         * @param project
         * @return List
         */
        public List getAllWbs(Project project);
        
        /**
         * 得到project對應的所有Activity
         * @param project
         * @param dtoQry
         * @return List
         */
        public List loadAllActivity(Project project);
        
        /**
         * 得到project對應的所有ResourceAssignment
         * @param project
         * @return List
         */
        public List loadResourceAssignment(Project project);
        
        
        /**
         * 得到project對應的所有Resources
         * @param project
         * @return List
         */
        public List loadResources(Project project);
        
        /**
         * 根据专案代码查询projId
         * @param accountId
         * @return
         */
        public List listActivityByAcntId(String accountId, Date day, String date);
        
}

