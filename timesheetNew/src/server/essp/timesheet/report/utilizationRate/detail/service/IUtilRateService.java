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
     * 以DAY为单位查询在时间区域内的人员使用率及实际工时和有产值工时
     * @param dtoQuery DtoUtilRateQuery
     * @param userList Vector
     * @return List
     */
    public List getUtilRateData(DtoUtilRateQuery dtoQuery,Vector userList);

    /**
     * 以TIMESHEET周期为单位查询人员使用率及实际工时和有产值工时
     * @param dtoQuery DtoUtilRateQuery
     * @param userList Vector
     * @return List
     */
    public List getUtilRateDataByCycle(DtoUtilRateQuery dtoQuery,Vector userList);

    /**
     * 以月为单位查询人员使用率及实际工时和有产值工时
     * @param dtoQuery DtoUtilRateQuery
     * @param userList Vector
     * @return List
     */
    public List getUtilRateDataByMonths(DtoUtilRateQuery dtoQuery,Vector userList);


    /**
     * 得到当前登录者所管辖部门的员工集合
     * @param loginId String
     * @return List
     */
    public Vector getUserList(String acntId);

    /**
     * 得到当前登陆者所管辖的所有部门集合
     * @param loginId String
     * @param roleId String
     * @return List
     */
    public Vector getDeptList(String loginId, String roleId);

    /**
     * 设置标记位
     * @param excelDto boolean
     */
    public void setExcelDto(boolean excelDto) ;
}
