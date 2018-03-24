/*
 * Created on 2007-11-27
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.timesheet.report.timesheetStatus.dao;

import java.util.List;
import c2s.essp.timesheet.report.DtoTsStatusQuery;

public interface ITimesheetStatusDao {
       /**
        * 根據時間區間和狀態得到符合條件的集合
        * @param begin
        * @param end
        * @param status
        * @return List
        */
        public List queryByPeriod(DtoTsStatusQuery dtoQuery,
                String status)throws Exception;
        
        /**
         * 根據時間區域得到未填寫工時單的數據
         * @param begin
         * @param end
         * @param status
         * @return List
         */
        public List queryUnfilledByPeriod(DtoTsStatusQuery dtoQuery)throws Exception;
        
         /**
          * 得到需填寫工時單人數集合
          * @param dtoQuery
          * @param flag
          * @return List
          * @throws Exception
          */
        public List getFillTSEmpIdList(DtoTsStatusQuery dtoQuery,
                boolean flag)throws Exception;
  
        
        /**
         * 得到指定部門下需要填寫工時單人員集合
         * @param dtoQuery
         * @param site
         * @param viewAll
         * @return List
         * @throws Exception
         */
        public List getEmpIdList(DtoTsStatusQuery dtoQuery
              )throws Exception;


}
