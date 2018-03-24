/*
 * Created on 2008-1-2
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.timesheet.report.salaryApportion.service;

import java.util.List;
import java.util.Map;

import c2s.essp.timesheet.report.DtoSalaryWkHrQuery;

public interface ISalaryWorkHourService {
    
    /**
     * 根据查询条件列出工时汇总记录
     * @param loginId
     * @param beginDate
     * @param endDate
     * @return List
     * @throws Exception
     */
    public List queryByCondition(String loginId,DtoSalaryWkHrQuery dtoQuery);
    
    /**
     * 
     * @param rolesList
     * @param loginId
     * @return Map
     */
    public Map getSiteList(List rolesList,String loginId);
}
