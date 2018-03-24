package server.essp.timesheet.report.humanreport.dao;

import java.util.Date;
import java.util.List;

public interface IHumanReportDao {
	
	/**
	 * 根据日期site查询人力报表资料
	 * @param begin
	 * @param end
	 * @param site
	 * @return
	 */
	public List queryHumanReportByDate(Date begin, Date end, String site);
	
	/**
	 * 根据日期site查询员工对应的专案总工时
	 * @param begin
	 * @param end
	 * @param site
	 * @return
	 */
	public List getTotalHoursList(Date begin, Date end, String site);
	
	/**
	 * 根据日期site查询员工对应专案
	 * @param begin
	 * @param end
	 * @param site
	 * @return
	 */
	public List queryNameAndProject(Date begin, Date end, String site);
	
	/**
	 * 根据专案ID查询专案信息
	 * @param accountId
	 * @return
	 */
	public List getProjectInfo(String accountId);
	
	/**
	 * 查询专案下一段时间内某人的总加班时间
	 * @param begin
	 * @param end
	 * @param site
	 * @param queryWay
	 * @return
	 */
	public List queryOvertimeHours(final Date begin, final Date end, String site, String queryWay);
	
	/**
	 * 查询部门下一段时间内某人的总请假时间
	 * @param begin
	 * @param end
	 * @param site
	 * @return
	 */
	public List queryLeaveHours(final Date begin, final Date end, String site);
	
	/**
	 * 查询部门下一段时间内某人的总工作时数
	 * @param begin
	 * @param end
	 * @param site
	 * @return
	 */
	public List queryHumanTimes(final Date begin, final Date end, String site);
	
	/**
	 * 查询某人在某专案中的工作起止日期
	 * @param accountId
	 * @param loginId
	 * @return
	 */
	public List queryBeginEnd(String accountId, String loginId, final Date begin, final Date end);

}
