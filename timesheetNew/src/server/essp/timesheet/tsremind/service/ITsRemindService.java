package server.essp.timesheet.tsremind.service;

import java.util.Date;
import java.util.List;

public interface ITsRemindService {
	
	/**
	 * 查询没有提交或没有审核的周报并发邮件给相关人员
	 *
	 */
	public void queryDataAndSendMail(Date begin, Date end, 
			String mailType, String site, List periodList);

}
