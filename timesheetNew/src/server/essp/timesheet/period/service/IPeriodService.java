package server.essp.timesheet.period.service;

import java.util.Date;
import java.util.List;

import c2s.essp.timesheet.weeklyreport.DtoTimeSheetPeriod;

/**
 * <p>Title: IPeriodService</p>
 *
 * <p>Description: 对日期区间的操作业务逻辑</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author wenhaizheng
 * @version 1.0
 */
public interface IPeriodService {
    /**
     * 根据传入日期查询当前日期所在的日期区间
     * 并转化成DtoTimeSheetPeriod返回
     * @param day Date
     * @return DtoTimeSheetPeriod
     */
    public DtoTimeSheetPeriod queryPeriodByDay(Date day);

    /**
     * 以当前区间为基准向前或向后移到一个区间
     * @param date Date
     * @param moveWay String
     * @return DtoTimeSheetPeriod
     */
    public DtoTimeSheetPeriod moveToNextOrPrePeriod(Date date, String moveWay);
    
    /**
     * 向后移动step个日期区间
     * @param endDate Date
     * @param step int
     * @return DtoTimeSheetPeriod
     * @throws Exception
     */
    public DtoTimeSheetPeriod getNextPeriod(Date endDate, int step);
    
    /**
     * 根据日期取得日期间的工时区间
     * @param begin
     * @param end
     * @return
     */
    public List getPeriodByDate(Date begin, Date end);

}
