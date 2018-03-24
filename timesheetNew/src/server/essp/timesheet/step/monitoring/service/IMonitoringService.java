/*
 * Created on 2008-5-26
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.timesheet.step.monitoring.service;

import java.util.Map;
import java.util.Vector;

import c2s.essp.timesheet.step.DtoActivityFilter;
import c2s.essp.timesheet.step.monitoring.DtoMonitoringQuery;

public interface IMonitoringService {
       
       /**
        * 得到專案集合
        * @param loginId
        * @return Vector
        */
       public Vector getProjectList(String loginId);
       
       /**
        * 根據查詢條件的得到WBS,Activity,step对应的子孙结点
        * @param loginId
        * @param projectId
        * @return Map
        */
       public Map loadMonitoringInfo(String loginId, String projectId);
      
       /**
        * 设置标志位
        * @param excelDto boolean
        */
       public void setExcelDto(boolean excelDto);
}
