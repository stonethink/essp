package c2s.essp.common.calendar;

public interface IWorkTimeUtil {
//  public WorkTime getWorkTime();
//
//  public void setWorkTime(WorkTime workTime);

  public WorkTime getWorkTime(String workTimeId);

  public void setWorkTime(String workTimeId, WorkTime workTime);
}
