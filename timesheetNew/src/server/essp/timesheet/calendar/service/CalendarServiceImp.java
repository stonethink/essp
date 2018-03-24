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
 * <p>Description: ��ȡP3�е�����������Ϣ</p>
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
     * Spring Dao ע��
     * @param iadminPreferenceDao IAdminPreferenceDao
     */
    public void setPreferenceP3DbDao(IPreferenceP3DbDao preferenceP3DbDao) {

        this.preferenceP3DbDao = preferenceP3DbDao;
    }
    /**
     * Spring Dao ע��
     * @param calendarP3Dao ICalendarP3Dao
     */
    public void setCalendarP3Dao(ICalendarP3Dao calendarP3Dao) {
        this.calendarP3Dao = calendarP3Dao;
    }

    /**
    * ����P3�е����û�ȡ���������ڵ�˳��
    * @return int
    */
    public int getWeekStrat() {
        return preferenceP3DbDao.getWeekStartDayNum();
    }
    /**
     * ��ѯP3���ݻ�ȡȫ�깤�������õ��ַ�������ʽΪ��yyyy-MM-dd~yyyy-MM-dd+00011001111100111110011111001111100111110011110000000000111110011111001111100111110011111001111100111110011111001111111100000001111001111100111110011111001111100111110011111001111100111110011111001111100111110011111001111100111110011111001111100111110011111001111100111111100000001111100111110011111001111100111110011111001111100111110011111001111100111110011111001��
     * 1.���ݴ���������ѭ��ȡ�������һ�쵽���һ���ÿ�������
     * 2.��ѯP3�и����ڵĹ���ʱ��Ϊ����
     * 3.�������ʱ�����0������ڹ����ձ�ʶΪ0,�����ʶ1
     * 4.��ȫ��Ĺ����ձ�ʶƴ�ӳ�ָ����ʽ���ַ�������
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
     * ��ʼ��������ݵĿ�ʼ���ںͽ�������,���õ�ǰѭ����ʼ����Ϊ����ĵ�һ��
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
     * ��ĳ��Ŀ�ʼ�������������ƴ�ӳ�ָ����ʽ���ַ���
     * ��ʽΪ:yyyy-MM-dd~yyyy-MM-dd+
     * @return String
     */
    private String getHeadString() {
        return comDate.dateToString(beginDayOfYear.getTime()) + "~"
                + comDate.dateToString(endDayOfYear.getTime()) + "+";
    }
    /**
     * ����ǰ���ں���һ��
     * �����ǰ���ڴ�����һ������һ�췵��false
     * ���򷵻�true
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
     * ��ȡP3����
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
