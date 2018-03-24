package server.essp.timesheet.calendar.dao;

import java.util.Date;
import com.primavera.integration.client.bo.object.Calendar;
/**
 *
 * <p>Title: ICalendarP3Dao</p>
 *
 * <p>Description: 访问P3获取日历相关信息的接口</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author wenhaizheng
 * @version 1.0
 */
public interface ICalendarP3Dao {
    /**
     * 查询P3数据获取某天的工作时间数
     * @param date Date
     * @return double
     * @throws Exception
     */
    public double getWorkHours(Calendar ca, Date date) throws Exception;
    /**
     * 获取P3日历
     * @return Calendar
     */
    public Calendar getCalendar();
    /**
     * 获取一段时间内的标准工作时间
     * @param begin Date
     * @param end Date
     * @return Double
     * @throws Exception
     */
    public Double getWorkHoursBetweenDates(Date begin ,Date end) throws Exception;
    /**
     * 根据工号获取一段时间内给人员的标准工作时间
     * @param begin Date
     * @param end Date
     * @param loginId String
     * @return Double
     * @throws Exception
     */
    public Double getWorkHoursBetweenDatesByLoginId(Date begin ,Date end, String loginId) throws Exception;
}
