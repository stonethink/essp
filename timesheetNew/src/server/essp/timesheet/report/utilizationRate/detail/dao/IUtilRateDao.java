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
        * ��ĳʱ�������ڣ����ָ������ָ��Ա���ĸ�����ʱ��(������)
        * @param dtoQuery
        * @return List
        */
        public List getDataByDate(DtoUtilRateQuery dtoQuery);
    
        /**
         * ����loginId�õ�TS_ACCOUNT�ж�Ӧ��acntRid,�ٸ���acntRid��TS_LABOR_RESOURCE�еõ���Ӧ��Ա�����ż���
         * @param loginId String
         * @return List
         */
        public List listUsers(String acntId);
    
        /**
         * ����loginId�õ�TS_ACCOUNT�ж�Ӧ�Ĳ��Ŵ��źͲ�������
         * @param loginId String
         * @return List
         */
        public List listDeptInfo(String loginId);
    
        /**
         *  ��ĳʱ�������ڣ����ָ������ָ��Ա���ĸ�����ʱ��(����ʱ������)
         * @param dtoQuery
         * @return List
         */
        public List getDataByCycle(DtoUtilRateQuery dtoQuery);
    
       /**
        * �����·ݲ�ԃ�����H���r
        * @param dtoQuery
        * @return List
        */
        public List getAcutalHourByMonths(DtoUtilRateQuery dtoQuery);
    
      /**
       * ��ĳ�·������ڣ����ָ������ָ��Ա���ĸ�����ʱ��(���·�)
       * @param dtoQuery
       * @return List
       */
       public List getDataBeanByMonths(DtoUtilRateQuery dtoQuery);
       
       /**
        * �������ڲ�ԃ���Юa�Ŀ�Ĺ��r
        * @param dtoQuery
        * @return List
        */
       public List getValidHoursByDate(DtoUtilRateQuery dtoQuery);
    
       /**
        * �����·ݲ�ԃ���Юa�Ŀ�µ��Юa���r
        * @param dtoQuery
        * @return List
        */
       public List getValidHourByMonths(DtoUtilRateQuery dtoQuery);
       
       /**
        * �������ڲ�ԃ����Ч���r
        * @param dtoQuery
        * @return List
        */
       public List getValidHourByCycle(DtoUtilRateQuery dtoQuery);
       
       /**
        * PMO��ѯʱ�г����в���
        * @return
        */
       public List listDepts();

}
