package server.essp.timesheet.report.humanreport.service;

import java.util.*;

import c2s.essp.timesheet.report.DtoHumanTimes;

public interface IHuamnReportService {
	
	/**
	 * ��ѯ������������
	 * @param begin
	 * @param end
	 * @param site
	 * @return
	 */
	public Map queryHumanReport(Date begin, Date end, String site);
	
	public List queryHumanReportForExport(Date begin, Date end, String site);
	
	/**
	 * ��ȡ����ѡ���site
	 */
	public Vector getSites();
	
	/**
     * ����Serviceʹ�ô�Excel��ʽ��Dto
     * @param excelDto boolean
     */
    public void setExcelDto(boolean excelDto);
    
    /**
     * ���Map
     */
    public void clearMap();
    
    /**
     * ���ʹ߽��ʼ�
     * @param sendList
     */
    public void sendMails(List<DtoHumanTimes> sendList);
		
	

}
