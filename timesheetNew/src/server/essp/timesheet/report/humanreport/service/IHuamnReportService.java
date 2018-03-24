package server.essp.timesheet.report.humanreport.service;

import java.util.*;

import c2s.essp.timesheet.report.DtoHumanTimes;

public interface IHuamnReportService {
	
	/**
	 * 查询人力报表数据
	 * @param begin
	 * @param end
	 * @param site
	 * @return
	 */
	public Map queryHumanReport(Date begin, Date end, String site);
	
	public List queryHumanReportForExport(Date begin, Date end, String site);
	
	/**
	 * 获取可以选择的site
	 */
	public Vector getSites();
	
	/**
     * 设置Service使用带Excel格式的Dto
     * @param excelDto boolean
     */
    public void setExcelDto(boolean excelDto);
    
    /**
     * 清空Map
     */
    public void clearMap();
    
    /**
     * 发送催缴邮件
     * @param sendList
     */
    public void sendMails(List<DtoHumanTimes> sendList);
		
	

}
