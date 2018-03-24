package server.essp.timesheet.report.timesheet.service;

import java.util.*;

import c2s.essp.timesheet.report.DtoQueryCondition;

/**
 * <p>Title: ITsReportService</p>
 *
 * <p>Description: ��ѯ��ʱ�����Լ����������Service</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author wenhaizheng
 * @version 1.0
 */
public interface ITsReportService {

    public List queryGatherForExport(DtoQueryCondition dtoCondition, String loginId, boolean isPMO);

    public List queryForExport(DtoQueryCondition dtoCondition, String loginId, boolean isPMO);

    /**
     * ���ݲ�ѯ�����г���������
     * @param dtoCondition DtoQueryCondition
     * @param loginId String
     * @return List
     */
    public List queryReport(DtoQueryCondition dtoCondition, String loginId);

    /**
     * ��ѯ���ܱ�����Ϣ
     * @param dtoCondition DtoQueryCondition
     * @return List
     */
    public List queryGatherReport(DtoQueryCondition dtoCondition, String loginId);
    /**
     * ��ȡ���ź�ר������ѡ��
     * @param loginId String
     * @return Map
     */
    public Map getDeptsProjects(String loginId);

    /**
     * ��ȡ���ź���Ա������
     * @param loginId String
     * @return Map
     */
    public Map getDeptsPersons(String loginId);

    /**
     * ����Serviceʹ�ô�Excel��ʽ��Dto
     * @param excelDto boolean
     */
    public void setExcelDto(boolean excelDto);
    
    /**
     * PMO��ʱ�г����в���
     * @param roleId
     * @return
     */
    public Vector getDeptsForPmo(String loginId, String roleId);
    
    /**
     * PMO��ѯʱ���ز����µ�ר��
     * @param deptId
     * @return
     */
    public Vector getProjectsForPmo(String deptId);
    
    /**
     * PMO��ѯʱ���ز����µ���Ա
     * @param deptId
     * @return
     */
    public Vector getPersonsForPmo(String deptId);
    
    /**
     *  ���������ʱ�䷶Χ����ʱ�����ֳ����ɸ����ڣ���ѯ��ʵ�ʹ�ʱ���Ӱ࣬��ٹ�ʱ
     * @param beginDate
     * @param endDate
     * @param loginId
     * @return Map
     */
    public Map queryByPeriodForExport(Date beginDate,Date endDate, String loginId);

}
