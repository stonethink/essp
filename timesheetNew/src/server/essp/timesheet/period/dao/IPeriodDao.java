package server.essp.timesheet.period.dao;

import java.util.Date;
import java.util.List;

import c2s.essp.timesheet.weeklyreport.DtoTimeSheetPeriod;
/**
 *
 * <p>Title: IPeriodDao</p>
 *
 * <p>Description: 日期区间的相关操作</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author wenhaizheng
 * @version 1.0
 */
public interface IPeriodDao {
    /**
     * 根据输入日期查询日期区间
     * @param day Date
     * @return DtoTimeSheetPeriod
     * @throws Exception
     */
    public DtoTimeSheetPeriod getTimeSheetPeriod(Date day) throws Exception;
    /**
     * 向后推移一个日期区间
     * @param date Date
     * @return DtoTimeSheetPeriod
     * @throws Exception
     */
    public DtoTimeSheetPeriod getNextPeriod(Date date) throws Exception;
    /**
     * 向前推移一个日期区间
     * @param date Date
     * @return DtoTimeSheetPeriod
     * @throws Exception
     */
    public DtoTimeSheetPeriod getPrePeriod(Date date) throws Exception;
    
    /**
     * 向后移动step个日期区间
     * @param endDate Date
     * @param step int
     * @return DtoTimeSheetPeriod
     * @throws Exception
     */
    public DtoTimeSheetPeriod getNextPeriod(Date endDate, int step) throws Exception;
    
    /**
     * 根据开始和结束日期获取工时单区间
     * @param begin
     * @param end
     * @return DtoTimeSheetPeriod
     * @throws Exception
     */
    public DtoTimeSheetPeriod getPeriod(Date begin, Date end) throws Exception;
    
    /**
     * 获取全部的区间列表
     * @return List
     * @throws Exception
     */
    public List getAllTimeSheetPeriod() throws Exception;
    
    /**
     * 获取最后一个工时单区间
     * @return DtoTimeSheetPeriod
     * @throws Exception
     */
    public DtoTimeSheetPeriod getLastEndPeriod() throws Exception;
    
    /**
     * 查询一个区间内是否与别的工时单区间重叠
     * @param startDate
     * @param endDate
     * @return
     */
    public boolean isDuplicate(Date startDate, Date endDate) throws Exception;
    /**
     * 根据时间段查询在这时间段内的周期
     * @param begin
     * @param end
     * @return List
     * @throws Exception
     */
    public List getPeriodByDate(Date begin,Date end) throws Exception;
}
