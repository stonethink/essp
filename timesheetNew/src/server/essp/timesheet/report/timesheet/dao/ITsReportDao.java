package server.essp.timesheet.report.timesheet.dao;

import java.util.List;
import c2s.essp.timesheet.report.DtoQueryCondition;
import java.util.Date;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author wenhaizheng
 * @version 1.0
 */
public interface ITsReportDao {
    /**
     * 根据条件查询工时报表数据
     * @param dto DtoQueryCondition
     * @param loginIds String
     * @param orderBy String
     * @return List
     */
    public List queryReport(DtoQueryCondition dto, String loginIds, String orderBy);
    
    /**
     * 根据条件查询工时报表数据
     * @param dto
     * @param deptIds
     * @return
     */
    public List queryReportByDept(final DtoQueryCondition dto, final String deptIds);
    
    /**
     * 根据条件查询工时报表数据
     * @param dto
     * @param deptIds
     * @param loginId
     * @return
     */
    public List queryReportForPmDept(final DtoQueryCondition dto,
			final String deptIds, String loginId);
    /**
     * 根据条件查询工时报表数据
     * @param dto DtoQueryCondition
     * @param loginIds String
     * @param orderBy String
     * @param acntRids String
     * @return List
     */
    public List queryReportForPm(DtoQueryCondition dto, String loginIds, String orderBy,
    		                String acntRids);
    /**
     * 根据条件查询工时汇总报表数据
     * @param dto DtoQueryCondition
     * @param projectIds String
     * @return List
     */
    public List queryGatherReport(DtoQueryCondition dto, String projectIds);
    
    /**
     * 根据条件查询工时汇总报表数据
     * @param dto
     * @param deptIds
     * @param loginId
     * @return
     */
    public List queryGatherReportDept(final DtoQueryCondition dto,String deptIds, String loginId);
    /**
     * 根据条件查询某专案下的工时和
     * @param projectId String
     * @return List
     */
    public List getSum(String projectId, Date begin, Date end, String isLeaveType);
    
    /**
     * 查询实际工时
     * @param loginId
     * @param begin
     * @param end
     * @return List
     */
    public List getActualHoursInfo(String loginId,Date begin,Date end);
    
    /**
     * 查询請假工时
     * @param loginId
     * @param begin
     * @param end
     * @return List
     */
    public List getLeaveHoursInfo(final String loginId,final Date begin,final Date end);
    
    /**
     * 查询加班工时
     * @param loginId
     * @param begin
     * @param end
     * @return List
     */
    public List getOvertimeHoursInfo(final String loginId,final Date begin,final Date end);
}
