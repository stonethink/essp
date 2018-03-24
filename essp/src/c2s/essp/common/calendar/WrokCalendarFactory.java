package c2s.essp.common.calendar;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class WrokCalendarFactory {
  public static final String CLIENT_WORK_CALENDAR = "C";
  public static final String SERVER_WORK_CALENDAR = "S";

  static final String clinetImplClassName =
      "client.essp.common.calendar.WrokCalendarUtilImplC";
  static final String serverImplClassName =
      "server.essp.common.calendar.logic.WrokCalendarUtilImplS";

  public WrokCalendarFactory() {
  }

  public static WorkCalendar clientCreate() {
    return create(CLIENT_WORK_CALENDAR);
  }

  public static WorkCalendar serverCreate() {
    return create(SERVER_WORK_CALENDAR);
  }

  public static WorkCalendar create(String type) {
    return create(type, null);
  }

  public static WorkCalendar clientCreate(String calendarID) {
    return create(CLIENT_WORK_CALENDAR, calendarID);
  }

  public static WorkCalendar serverCreate(String calendarID) {
    return create(SERVER_WORK_CALENDAR, calendarID);
  }

  public static WorkCalendar create(String type, String calendarId) {
    IWorkCalendarUtil implObject = null;
    String implClassName = "";

    if (CLIENT_WORK_CALENDAR.equals(type)) {
      implClassName = clinetImplClassName;
    } else {
      implClassName = serverImplClassName;
    }

    try {
        Class implClass = Class.forName(implClassName);
        implObject = (IWorkCalendarUtil) implClass.newInstance();
    } catch (Exception ex) {
        ex.printStackTrace();
        return null;
    }

    WorkCalendar wc = implObject.getWorkCalendar(calendarId);
    return wc;
  }


  public static void main(String[] args) {
    String calendarId = "";
//    WorkCalendar workCalendar = WrokCalendarFactory.clientCreate(calendarId);
    WorkCalendar workCalendar = WrokCalendarFactory.serverCreate(calendarId);

    Calendar startDay = Calendar.getInstance();
    startDay.set(2005, 9, 9);
//    Calendar endDay = Calendar.getInstance();
    Calendar endDay = new GregorianCalendar();
//    endDay.set(2005, 9, 14);
//    endDay = Calendar.getInstance();

    //计算输入工期：输入起止日期，返回工作天数,工作小时
    int iWorkDays = workCalendar.getWorkDayNum(startDay, endDay);
    int iWorkDays2 = workCalendar.getWorkDayNum(startDay, endDay);
    float fWorkHours = workCalendar.getWorkHours(startDay, endDay);
    System.out.println("Begin:" + WorkCalendar.dayToString(startDay)
                       + ";End:" + WorkCalendar.dayToString(endDay)
                       + ";iWorkDays=" + iWorkDays
                       + ";iWorkDays2=" + iWorkDays2
                       + ";fWorkHours=" + fWorkHours
                      );

    //计算Next工作日:输入起始日期、前(或后)工作天数，返回Next的工作日期
    iWorkDays = 10;
    endDay = workCalendar.getNextWorkDay(startDay,iWorkDays);
    System.out.println("Begin:" + WorkCalendar.dayToString(startDay)
                       + ";End:" + WorkCalendar.dayToString(endDay)
                       + ";iWorkDays=" + iWorkDays
                      // + ";fWorkHours=" + fWorkHours
                      );

  }

}
