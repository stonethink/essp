/*
 * Created on 2008-6-27
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.timesheet.report.busyRate.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import c2s.essp.timesheet.report.DtoBusyRateQuery;

public interface IBusyRateGatherService {
    
        /**
         * �жϻ�õ�ʱ�������Ƿ���꣬��������ȡ��ÿ��Ŀ�ʼ�ͽ���ʱ��
         * @param bege Date
         * @param endDate Date
         * @return List
         */
        public List getYearsList(Date beginDate, Date endDate);
    
        /**
         * ���õ���ʱ������ת����"YYYY-MM��~YYYY-MM��"�ĸ�ʽ
         * @param beginDate Date
         * @param endDate Date
         * @return String
         */
        public String getDateStr(Date beginDate, Date endDate);
    
    
        /**
         * ���������ʱ�䷶Χ�ж��Ƿ���꣬�������������Ϊ��λȡ��ÿ���Ӧ������
         * @param dtoQuery DtoUtilRateQuery
         * @return Map
         */
        public Map getBusyRateInfoByYear(DtoBusyRateQuery dtoQuery);
    
        /**
         * ���ñ�־λ
         * @param excelDto boolean
         */
        public void setExcelDto(boolean excelDto);
 
  }
