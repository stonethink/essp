/*
 * Created on 2007-11-27
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.timesheet.report.timesheetStatus.service;

import java.util.List;
import java.util.Map;
import c2s.essp.timesheet.report.DtoTsStatusQuery;

public interface ITimesheetStausService {
    
    /**
     * �����r�g�^�g�õ����ϲ�ԃ�l����ӛ䛣�������B�˔��yӋ��δ���δ�ύ�����ύ���ь�����PM�ь��������ܽ^�Ĺ��r����Ϣ��
     * @param dtoQuery
     * @return Map
     * @throws Exception 
     */
    public Map queryByPeriod(DtoTsStatusQuery dtoQuery,String loginId,
            Boolean isExp) throws Exception;
    
    public void setExcelDto(boolean excelDto);
    
    /**
     * �õ�δ��܈�ĆT���Y��
     * @param periodLst
     * @param dtoQuery
     * @param loginId
     * @return
     */
    public List getUnfilled(List periodLst, DtoTsStatusQuery dtoQuery, String loginId);
    
    /**
     * ��ʼMAP
     * @throws Exception
     */
    public void initMap() throws Exception;
}
