package c2s.essp.common.calendar;

public interface IWorkCalendarUtil {
  public WorkCalendar getWorkCalendar(String calendarId);

  public void setWorkCalendar(String calendarId, WorkCalendar workCalendar);
}
