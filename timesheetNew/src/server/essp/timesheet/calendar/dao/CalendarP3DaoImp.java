package server.essp.timesheet.calendar.dao;

import java.util.Date;

import server.essp.common.primavera.PrimaveraApiBase;
import com.primavera.integration.client.bo.object.Calendar;
import c2s.essp.common.calendar.WorkCalendar;


/**
 *
 * <p>Title: CalendarP3DaoImp</p>
 *
 * <p>Description: 访问P3API查询P3中日历的相关信息</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author wenhaizheng
 * @version 1.0
 */
public class CalendarP3DaoImp extends PrimaveraApiBase implements ICalendarP3Dao{
    /**
     * 查询P3数据获取某天的工作时间数
     * @param date Date
     * @return double
     * @throws Exception
     */
    public double getWorkHours(Calendar ca, Date date) throws Exception {
        return ca.getTotalWorkHours(date);
    }
    /**
     * 获取P3日历
     * @return Calendar
     */
    public Calendar getCalendar() {
        return this.getCurrentCalendar();
    }
    /**
     * 获取一段时间内的标准工作时间
     * @param begin Date
     * @param end Date
     * @return Double
     * @throws Exception
     */
    public Double getWorkHoursBetweenDates(Date begin, Date end) throws
            Exception {
        Calendar caP3 = this.getCurrentCalendar();
        Double standarHours = new Double(0);
        java.util.Calendar ca = java.util.Calendar.getInstance();
        ca.setTime(begin);
        Double hour = new Double(0);
        //循环开始和结束日期之间的每天获取对应的工作时间
          while (end.after(begin) || end.getTime() == begin.getTime()) {
              hour = caP3.getTotalWorkHours(begin);
              ca = WorkCalendar.getNextDay(ca, 1);
              begin = ca.getTime();
              standarHours += hour;
          }
        return standarHours;
    }
    /**
     * 根据工号获取一段时间内给人员的标准工作时间
     * @param begin Date
     * @param end Date
     * @param loginId String
     * @return Double
     * @throws Exception
     */
	public Double getWorkHoursBetweenDatesByLoginId(Date begin, Date end, String loginId) throws Exception {
		Calendar caP3 = this.getCalendar(loginId);
        Double standarHours = new Double(0);
        java.util.Calendar ca = java.util.Calendar.getInstance();
        ca.setTime(begin);
        Double hour = new Double(0);
        //循环开始和结束日期之间的每天获取对应的工作时间
          while (end.after(begin) || end.getTime() == begin.getTime()) {
              hour = caP3.getTotalWorkHours(begin);
              ca = WorkCalendar.getNextDay(ca, 1);
              begin = ca.getTime();
              standarHours += hour;
          }
        return standarHours;
	}
    
}
