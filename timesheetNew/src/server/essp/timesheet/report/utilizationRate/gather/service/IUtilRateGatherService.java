package server.essp.timesheet.report.utilizationRate.gather.service;

import java.util.List;
import c2s.essp.timesheet.report.DtoUtilRateQuery;
import java.util.Date;
import java.util.Map;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author tbh
 * @version 1.0
 */
public interface IUtilRateGatherService {

    /**
     * ����loginId�õ�����Ͻ�Ĳ��ż���
     * @param loginId String
     * @param roleId
     * @return List
     */
    public List getDeptList(String loginId, String roleId);

    /**
     * �жϻ�õ�ʱ�������Ƿ���꣬��������ȡ��ÿ��Ŀ�ʼ�ͽ���ʱ��
     * @param beginDate Date
     * @param endDate Date
     * @return List
     */
    public List getYearsList(Date beginDate, Date endDate);

    /**
     * ���õ���ʱ������ת����"YYYY-MM��~YYYY-MM��"�ĸ�ʽ
     * @param beginDate Date
     * @param endDate Date
     * @return String
     */
    public String getDateStr(Date beginDate, Date endDate);


    /**
     * ���������ʱ�䷶Χ�ж��Ƿ���꣬�������������Ϊ��λȡ��ÿ���Ӧ������
     * @param dtoQuery DtoUtilRateQuery
     * @return Map
     */
    public Map getDataTreeByYear(DtoUtilRateQuery dtoQuery);

    /**
     * ���ñ�־λ
     * @param excelDto boolean
     */
    public void setExcelDto(boolean excelDto);
}
