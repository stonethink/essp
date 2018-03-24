package server.essp.timesheet.report.utilizationRate.gather.dao;

import java.util.List;
import java.util.Date;

import db.essp.timesheet.TsAccount;

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
public interface IUtilRateGatherDao {
    /**
     * 根据部门RID得到相关信息
     * @param acntRid Long
     * @return List
     */
    public TsAccount getDeptInfoByMonths(Long acntRid);

    /**
     * 根据查询条件得到有产值工时
     * @param beginDate Date
     * @param endDate Date
     * @param acntIdStr String
     * @return Double
     */
    public List getValidHours(final Date beginDate, final Date endDate,
                               final String acntIdStr);

    /**
     * 根據查詢條件得到實際工時數
     * @param beginDate Date
     * @param endDate Date
     * @param acntIdStr String
     * @return Double
     */
    public List getActualHours(final Date beginDate, final Date endDate,
                                final String acntIdStr);


    /**
    * 根据主管权限得到所管辖的部门集合
    * @param loginId String
    * @return List
    */
    public List listDeptInfo(String acntId);
    
    /**
     * PMO查询时列出所有部门
     * @return
     */
    public List listDept();
    
    /**
     * 根据项目代号得到当前项目下的员工
     * @param acntId
     * @return List
     */
    public List getLoginIdsByCondition(final String acntId);

}
