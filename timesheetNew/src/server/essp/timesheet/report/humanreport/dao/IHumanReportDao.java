package server.essp.timesheet.report.humanreport.dao;

import java.util.Date;
import java.util.List;

public interface IHumanReportDao {
	
	/**
	 * ��������site��ѯ������������
	 * @param begin
	 * @param end
	 * @param site
	 * @return
	 */
	public List queryHumanReportByDate(Date begin, Date end, String site);
	
	/**
	 * ��������site��ѯԱ����Ӧ��ר���ܹ�ʱ
	 * @param begin
	 * @param end
	 * @param site
	 * @return
	 */
	public List getTotalHoursList(Date begin, Date end, String site);
	
	/**
	 * ��������site��ѯԱ����Ӧר��
	 * @param begin
	 * @param end
	 * @param site
	 * @return
	 */
	public List queryNameAndProject(Date begin, Date end, String site);
	
	/**
	 * ����ר��ID��ѯר����Ϣ
	 * @param accountId
	 * @return
	 */
	public List getProjectInfo(String accountId);
	
	/**
	 * ��ѯר����һ��ʱ����ĳ�˵��ܼӰ�ʱ��
	 * @param begin
	 * @param end
	 * @param site
	 * @param queryWay
	 * @return
	 */
	public List queryOvertimeHours(final Date begin, final Date end, String site, String queryWay);
	
	/**
	 * ��ѯ������һ��ʱ����ĳ�˵������ʱ��
	 * @param begin
	 * @param end
	 * @param site
	 * @return
	 */
	public List queryLeaveHours(final Date begin, final Date end, String site);
	
	/**
	 * ��ѯ������һ��ʱ����ĳ�˵��ܹ���ʱ��
	 * @param begin
	 * @param end
	 * @param site
	 * @return
	 */
	public List queryHumanTimes(final Date begin, final Date end, String site);
	
	/**
	 * ��ѯĳ����ĳר���еĹ�����ֹ����
	 * @param accountId
	 * @param loginId
	 * @return
	 */
	public List queryBeginEnd(String accountId, String loginId, final Date begin, final Date end);

}
