package server.essp.timesheet.tsremind.service;

import java.util.Date;
import java.util.List;

public interface ITsRemindService {
	
	/**
	 * ��ѯû���ύ��û����˵��ܱ������ʼ��������Ա
	 *
	 */
	public void queryDataAndSendMail(Date begin, Date end, 
			String mailType, String site, List periodList);

}
