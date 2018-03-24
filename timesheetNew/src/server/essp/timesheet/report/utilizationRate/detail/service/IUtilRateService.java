package server.essp.timesheet.report.utilizationRate.detail.service;

import java.util.List;
import java.util.Vector;
import c2s.essp.timesheet.report.DtoUtilRateQuery;

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
public interface IUtilRateService {
    /**
     * ��DAYΪ��λ��ѯ��ʱ�������ڵ���Աʹ���ʼ�ʵ�ʹ�ʱ���в�ֵ��ʱ
     * @param dtoQuery DtoUtilRateQuery
     * @param userList Vector
     * @return List
     */
    public List getUtilRateData(DtoUtilRateQuery dtoQuery,Vector userList);

    /**
     * ��TIMESHEET����Ϊ��λ��ѯ��Աʹ���ʼ�ʵ�ʹ�ʱ���в�ֵ��ʱ
     * @param dtoQuery DtoUtilRateQuery
     * @param userList Vector
     * @return List
     */
    public List getUtilRateDataByCycle(DtoUtilRateQuery dtoQuery,Vector userList);

    /**
     * ����Ϊ��λ��ѯ��Աʹ���ʼ�ʵ�ʹ�ʱ���в�ֵ��ʱ
     * @param dtoQuery DtoUtilRateQuery
     * @param userList Vector
     * @return List
     */
    public List getUtilRateDataByMonths(DtoUtilRateQuery dtoQuery,Vector userList);


    /**
     * �õ���ǰ��¼������Ͻ���ŵ�Ա������
     * @param loginId String
     * @return List
     */
    public Vector getUserList(String acntId);

    /**
     * �õ���ǰ��½������Ͻ�����в��ż���
     * @param loginId String
     * @param roleId String
     * @return List
     */
    public Vector getDeptList(String loginId, String roleId);

    /**
     * ���ñ��λ
     * @param excelDto boolean
     */
    public void setExcelDto(boolean excelDto) ;
}
