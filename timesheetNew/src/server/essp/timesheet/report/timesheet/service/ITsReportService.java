package server.essp.timesheet.report.timesheet.service;

import java.util.*;

import c2s.essp.timesheet.report.DtoQueryCondition;

/**
 * <p>Title: ITsReportService</p>
 *
 * <p>Description: 查询工时报表以及导出报表的Service</p>
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
     * 根据查询条件列出报表数据
     * @param dtoCondition DtoQueryCondition
     * @param loginId String
     * @return List
     */
    public List queryReport(DtoQueryCondition dtoCondition, String loginId);

    /**
     * 查询汇总报表信息
     * @param dtoCondition DtoQueryCondition
     * @return List
     */
    public List queryGatherReport(DtoQueryCondition dtoCondition, String loginId);
    /**
     * 获取部门和专案下拉选项
     * @param loginId String
     * @return Map
     */
    public Map getDeptsProjects(String loginId);

    /**
     * 获取部门和人员下拉框
     * @param loginId String
     * @return Map
     */
    public Map getDeptsPersons(String loginId);

    /**
     * 设置Service使用带Excel格式的Dto
     * @param excelDto boolean
     */
    public void setExcelDto(boolean excelDto);
    
    /**
     * PMO查时列出所有部门
     * @param roleId
     * @return
     */
    public Vector getDeptsForPmo(String loginId, String roleId);
    
    /**
     * PMO查询时返回部门下的专案
     * @param deptId
     * @return
     */
    public Vector getProjectsForPmo(String deptId);
    
    /**
     * PMO查询时返回部门下的人员
     * @param deptId
     * @return
     */
    public Vector getPersonsForPmo(String deptId);
    
    /**
     *  根据输入的时间范围将工时单划分成若干个周期，查询出实际工时，加班，请假工时
     * @param beginDate
     * @param endDate
     * @param loginId
     * @return Map
     */
    public Map queryByPeriodForExport(Date beginDate,Date endDate, String loginId);

}
