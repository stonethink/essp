package server.essp.timesheet.report.utilizationRate.detail.dao;

import java.util.List;
import c2s.essp.timesheet.report.DtoUtilRateQuery;

/**
 * <p>Title: </p>
 *
 * <p>Description: IUtilRateDao</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author TuBaohui
 * @version 1.0
 */
public interface IUtilRateDao {

       /**
        * 在某时间区域内，获得指定部门指定员工的各个工时数(按日期)
        * @param dtoQuery
        * @return List
        */
        public List getDataByDate(DtoUtilRateQuery dtoQuery);
    
        /**
         * 根据loginId得到TS_ACCOUNT中对应的acntRid,再根据acntRid到TS_LABOR_RESOURCE中得到相应的员工代号集合
         * @param loginId String
         * @return List
         */
        public List listUsers(String acntId);
    
        /**
         * 根据loginId得到TS_ACCOUNT中对应的部门代号和部门名称
         * @param loginId String
         * @return List
         */
        public List listDeptInfo(String loginId);
    
        /**
         *  在某时间区域内，获得指定部门指定员工的各个工时数(按工时表周期)
         * @param dtoQuery
         * @return List
         */
        public List getDataByCycle(DtoUtilRateQuery dtoQuery);
    
       /**
        * 根據月份查詢出實際工時
        * @param dtoQuery
        * @return List
        */
        public List getAcutalHourByMonths(DtoUtilRateQuery dtoQuery);
    
      /**
       * 在某月份区域内，获得指定部门指定员工的各个工时数(按月份)
       * @param dtoQuery
       * @return List
       */
       public List getDataBeanByMonths(DtoUtilRateQuery dtoQuery);
       
       /**
        * 根據日期查詢出有產項目的工時
        * @param dtoQuery
        * @return List
        */
       public List getValidHoursByDate(DtoUtilRateQuery dtoQuery);
    
       /**
        * 根據月份查詢出有產項目下的有產工時
        * @param dtoQuery
        * @return List
        */
       public List getValidHourByMonths(DtoUtilRateQuery dtoQuery);
       
       /**
        * 根據周期查詢出有效工時
        * @param dtoQuery
        * @return List
        */
       public List getValidHourByCycle(DtoUtilRateQuery dtoQuery);
       
       /**
        * PMO查询时列出所有部门
        * @return
        */
       public List listDepts();

}
