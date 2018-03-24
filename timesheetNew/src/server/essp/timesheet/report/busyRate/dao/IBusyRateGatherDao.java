/*
 * Created on 2008-6-27
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.timesheet.report.busyRate.dao;

import java.util.Date;
import java.util.List;

import db.essp.timesheet.TsAccount;


public interface IBusyRateGatherDao {
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
        public List getValidHours(Date beginDate, Date endDate,String deptIds);
    
        /**
         * 根据查询条件得到无产值工时
         * @param beginDate Date
         * @param endDate Date
         * @param acntIdStr String
         * @return Double
         */
        public List getActualHours(Date beginDate,Date endDate,String deptIds);
    
        
        /**
         * 按部門代號得到對應部門下的所有員工集合
         * @param acntId
         * @return List
         */
        public List getLoginIdsByDeptIds(String deptIds);
    
}
