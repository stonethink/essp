package server.essp.timesheet.calendar.service;

import java.util.Calendar;
import java.util.Date;

import c2s.essp.common.calendar.WorkCalendar;
import com.wits.util.comDate;
import server.essp.timesheet.calendar.dao.ICalendarP3Dao;
import server.essp.timesheet.preference.dao.IPreferenceP3DbDao;
import server.framework.common.BusinessException;

/**
 *
 * <p>Title: CalendarServiceImp</p>
 *
 * <p>Description: 获取P3中的日历设置信息</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author wenhaizheng
 * @version 1.0
 */
public class CalendarServiceImp implements ICalendarService {
    private Calendar beginDayOfYear;
    private Calendar endDayOfYear;
    private Date currentDay;
    private ICalendarP3Dao calendarP3Dao;
    private IPreferenceP3DbDao preferenceP3DbDao;


    /**
     * Spring Dao 注入
     * @param iadminPreferenceDao IAdminPreferenceDao
     */
    public void setPreferenceP3DbDao(IPreferenceP3DbDao preferenceP3DbDao) {

        this.preferenceP3DbDao = preferenceP3DbDao;
    }
    /**
     * Spring Dao 注入
     * @param calendarP3Dao ICalendarP3Dao
     */
    public void setCalendarP3Dao(ICalendarP3Dao calendarP3Dao) {
        this.calendarP3Dao = calendarP3Dao;
    }

    /**
    * 根据P3中的设置获取日历中星期的顺序
    * @return int
    */
    public int getWeekStrat() {
        return preferenceP3DbDao.getWeekStartDayNum();
    }
    /**
     * 查询P3数据获取全年工作日设置的字符串（格式为：yyyy-MM-dd~yyyy-MM-dd+00011001111100111110011111001111100111110011110000000000111110011111001111100111110011111001111100111110011111001111111100000001111001111100111110011111001111100111110011111001111100111110011111001111100111110011111001111100111110011111001111100111110011111001111100111111100000001111100111110011111001111100111110011111001111100111110011111001111100111110011111001）
     * 1.根据传入的年参数循环取出该年第一天到最后一天的每天的日期
     * 2.查询P3中该日期的工作时间为多少
     * 3.如果工作时间大于0则该日期工作日标识为0,否则标识1
     * 4.将全年的工作日标识拼接成指定格式的字符串返回
     *
     * @param tmpYear Integer
     * @return String
     */
    public String getWorkDayString(Integer tmpYear,
                                   com.primavera.integration
                                   .client.bo.object.Calendar ca)
            throws BusinessException{
        String workDayString = "";
        try {
            initData(tmpYear);
            Double workHours = -1.0;
            do {
                workHours = calendarP3Dao.getWorkHours(ca, currentDay);
                if(workHours.equals(new Double(0))){
                    workDayString += "0";
                } else {
                    workDayString += "1";
                }
            } while (getNextDay());
        } catch (Exception ex) {
            throw new BusinessException("error.logic.CalendarServiceImp.loadHoursErr",
                                        "load workHours error", ex);
        }
        return getHeadString() + workDayString;
    }
    /**
     * 初始化传入年份的开始日期和结束日期,设置当前循环开始日期为该年的第一天
     * @param year int
     */
    private void initData(int year) {
        beginDayOfYear = Calendar.getInstance();
        beginDayOfYear.set(year, Calendar.JANUARY, 1, 0, 0, 0);
        endDayOfYear = Calendar.getInstance();
        endDayOfYear.set(year, Calendar.DECEMBER, 31, 0, 0, 0);
        currentDay= beginDayOfYear.getTime();
    }
    /**
     * 将某年的开始日期与结束日期拼接成指定格式的字符串
     * 格式为:yyyy-MM-dd~yyyy-MM-dd+
     * @return String
     */
    private String getHeadString() {
        return comDate.dateToString(beginDayOfYear.getTime()) + "~"
                + comDate.dateToString(endDayOfYear.getTime()) + "+";
    }
    /**
     * 将当前日期后移一天
     * 如果当前日期大于这一年的最后一天返回false
     * 否则返回true
     * @return boolean
     */
    private boolean getNextDay() {
        Calendar ca  = Calendar.getInstance();
        ca.setTime(currentDay);
        ca  = WorkCalendar.getNextDay(ca, 1);
        currentDay = ca.getTime();
        if(ca.after(endDayOfYear)) {
            return false;
        }
        return true;
    }
    /**
     * 获取P3日历
     * @return Calendar
     */
    public com.primavera.integration.client.bo.object.Calendar getCalendar() {
    	try {
        return calendarP3Dao.getCalendar();
    	} catch (Exception ex) {
            throw new BusinessException("error.logic.CalendarServiceImp.getCalendarErr",
                                        "load workHours error", ex);
        }
        
    }

}
