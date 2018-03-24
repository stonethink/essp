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
         * ����projectId�õ�Project
         * @param projectId
         * @return Project
         */
        public Project loadProject(String projectId);
              
        /**
         * �õ�project����������WBS
         * @param project
         * @return List
         */
        public List getAllWbs(Project project);
        
        /**
         * �õ�project����������Activity
         * @param project
         * @param dtoQry
         * @return List
         */
        public List loadAllActivity(Project project);
        
        /**
         * �õ�project����������ResourceAssignment
         * @param project
         * @return List
         */
        public List loadResourceAssignment(Project project);
        
        
        /**
         * �õ�project����������Resources
         * @param project
         * @return List
         */
        public List loadResources(Project project);
        
        /**
         * ����ר�������ѯprojId
         * @param accountId
         * @return
         */
        public List listActivityByAcntId(String accountId, Date day, String date);
        
}

