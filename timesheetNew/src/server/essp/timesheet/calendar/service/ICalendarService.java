package server.essp.timesheet.calendar.service;

import com.primavera.integration.client.bo.object.Calendar;
/**
 *
 * <p>Title: ICalendarService</p>
 *
 * <p>Description: 获取日历设置信息的接口</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author wenhaizheng
 * @version 1.0
 */
public interface ICalendarService {
    /**
     * 根据P3中的设置获取日历中星期的顺序
     * @return int
     */
    public int getWeekStrat();
    /**
     *  查询P3数据获取全年工作日设置的字符串（格式为：yyyy-MM-dd~yyyy-MM-dd+00011001111100111110011111001111100111110011110000000000111110011111001111100111110011111001111100111110011111001111111100000001111001111100111110011111001111100111110011111001111100111110011111001111100111110011111001111100111110011111001111100111110011111001111100111111100000001111100111110011111001111100111110011111001111100111110011111001111100111110011111001）
     * 1.根据传入的年参数循环取出该年第一天到最后一天的每天的日期
     * 2.查询P3中该日期的工作时间为多少
     * 3.如果工作时间大于0则该日期工作日标识为0,否则标识1
     * 4.将全年的工作日标识拼接成指定格式的字符串返回
     *
     * @param tmpYear Integer
     * @param ca Calendar
     * @return String
     */
    public String getWorkDayString(Integer tmpYear, Calendar ca);
    /**
     * 获取P3日历
     * @return Calendar
     */
    public Calendar getCalendar();
}
