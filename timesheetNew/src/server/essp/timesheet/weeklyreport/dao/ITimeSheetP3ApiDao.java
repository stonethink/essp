package server.essp.timesheet.weeklyreport.dao;

import c2s.essp.timesheet.weeklyreport.DtoTimeSheetPeriod;
import java.util.Date;
import java.util.List;

import com.primavera.integration.client.bo.object.Calendar;

import c2s.essp.timesheet.weeklyreport.DtoTimeSheetDetail;

/**
 *
 * <p>Title: ITimeSheetP3ApiDao</p>
 *
 * <p>Description: 与工时单相关访问P3 API的定义</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public interface ITimeSheetP3ApiDao {

    /**
     * 获取day所在的TimeSheetPeriod
     * @param day Date
     * @return DtoTimeSheetPeriod
     * @throws Exception
     */
    public DtoTimeSheetPeriod getTimeSheetPeriod(Date day) throws Exception;

    /**
     * 查询两个时间所在Period之间有多少个Period
     * @param begin Date
     * @param end Date
     * @return int
     * @throws Exception
     */
    public int getPeriodNum(Date begin, Date end) throws Exception;

    /**
     * 获取当前用户的Resource Id
     * @return Long
     */
    public Long getCurrentResourceId() throws Exception ;

    /**
     * 列出当前用户指定日期列表的标准工时，和它们的和
     * @param dateList List
     * @return List<Double>  size = dateList.size + 1 (sumHour)
     */
    public List listStandarHours(List<Date> dateList);
    
    /**
     * 获取资源的入职，离职日期
     *      入职日期：资源的数量和单价中最早的一个“单位时间最大量”不为0的“生效日期”
     *      离职日期：最晚一个“单位时间最大量”为0的“生效日期”，且需在入职日期之后。
     * @param loginId String
     * @return Date[2]
     * @throws Exception
     */
    public Date[] getInOutDate(String loginId);
    
    /**
     * 获取指定用户指定指定时间的标准工时
     * @param loginId
     * @param dateList
     * @return
     */
    public List listStandarHours(String loginId, List<Date> dateList);
    
    public Calendar getCalendar(String loginId);

    /**
     * 根据DtoTimeSheetDetail中的activityId获取该作业的计划时间
     * @param dtoDetail DtoTimeSheetDetail
     */
    public void getActivityPlanDate(DtoTimeSheetDetail dtoDetail)throws Exception;
    
    public Double getSumStandarHours(Calendar c, List<Date> dateList,
            Date inDate, Date outDate);
}
