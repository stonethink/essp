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
        * �����r�g�^�g�͠�B�õ����ϗl���ļ���
        * @param begin
        * @param end
        * @param status
        * @return List
        */
        public List queryByPeriod(DtoTsStatusQuery dtoQuery,
                String status)throws Exception;
        
        /**
         * �����r�g�^��õ�δ����r�εĔ���
         * @param begin
         * @param end
         * @param status
         * @return List
         */
        public List queryUnfilledByPeriod(DtoTsStatusQuery dtoQuery)throws Exception;
        
         /**
          * �õ�������r���˔�����
          * @param dtoQuery
          * @param flag
          * @return List
          * @throws Exception
          */
        public List getFillTSEmpIdList(DtoTsStatusQuery dtoQuery,
                boolean flag)throws Exception;
  
        
        /**
         * �õ�ָ�����T����Ҫ����r���ˆT����
         * @param dtoQuery
         * @param site
         * @param viewAll
         * @return List
         * @throws Exception
         */
        public List getEmpIdList(DtoTsStatusQuery dtoQuery
              )throws Exception;


}
