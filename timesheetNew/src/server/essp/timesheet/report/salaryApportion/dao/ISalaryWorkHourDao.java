/*
 * Created on 2008-1-2
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.timesheet.report.salaryApportion.dao;

import java.util.Date;
import java.util.List;
/**
 * ISalaryWorkHourDao
 * 
 * @author Tubaohui
 *
 */
public interface ISalaryWorkHourDao {
    /**
     * 根据查询条件查询
     * @param site
     * @param beginDate
     * @param endDate
     * @return List
     */
    public List queryByCondition(String site,Date beginDate,
            Date endDate, boolean needApprove);
  
}
