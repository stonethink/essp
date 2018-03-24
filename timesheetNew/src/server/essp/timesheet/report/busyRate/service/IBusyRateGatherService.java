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
         * 判断获得的时间区域是否跨年，若跨年则取得每年的开始和结束时间
         * @param bege Date
         * @param endDate Date
         * @return List
         */
        public List getYearsList(Date beginDate, Date endDate);
    
        /**
         * 将得到的时间区域转换成"YYYY-MM月~YYYY-MM月"的格式
         * @param beginDate Date
         * @param endDate Date
         * @return String
         */
        public String getDateStr(Date beginDate, Date endDate);
    
    
        /**
         * 根据输入的时间范围判断是否跨年，如果跨年则以年为单位取得每年对应的数据
         * @param dtoQuery DtoUtilRateQuery
         * @return Map
         */
        public Map getBusyRateInfoByYear(DtoBusyRateQuery dtoQuery);
    
        /**
         * 设置标志位
         * @param excelDto boolean
         */
        public void setExcelDto(boolean excelDto);
 
  }
