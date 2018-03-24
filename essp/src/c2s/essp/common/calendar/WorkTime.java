package c2s.essp.common.calendar;

import java.io.*;
import java.util.*;
/**
 *
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: WistonITS</p>
 *
 * @author Rong.Xiao
 * @version 1.0
 */
public class WorkTime implements Serializable {
  private List workTimeList = null;
  private float dailyWorkHours ;

  public WorkTime() {
    this.dailyWorkHours = WorkCalendarConstant.DAILY_WORK_HOUR;
  }

  public WorkTime(List workTimeList) {
    this.workTimeList = workTimeList;
    this.dailyWorkHours = getDailyWorkHours(this.workTimeList);
  }

  public List getWorkTimeList() {
    return workTimeList;
  }

  public void setWorkTimeList(List workTimeList) {
    this.workTimeList = workTimeList;
    this.dailyWorkHours = getDailyWorkHours(this.workTimeList);
  }


  /**
   * 计算输入起止时间内的工作时间
   * 遍历所有的DtoWorkTimeItem,累加其与当前区间的重复的时间,该值即工作时间
   * @param timeStart String
   * @param timeEnd String
   * @return float
   */
  public float getWorkHours(String timeStart, String timeEnd) {
      float  fWorkHours = 0;
      DtoWorkTimeItem period = new DtoWorkTimeItem(timeStart,timeEnd);
      if(workTimeList == null || workTimeList.size() == 0)
          return period.getHours();
      for(int i = 0;i < workTimeList.size();i ++){
          DtoWorkTimeItem item = (DtoWorkTimeItem) workTimeList.get(i);
          fWorkHours += period.getCrossHours(item);
      }
      return fWorkHours;
  }

  /**
   * 计算两个时间区间内除工作时间外的时间,即加班时间
   * @param timeStart String
   * @param timeEnd String
   * @param isWorkDay boolean
   * @return float
   */
  public float getOverTimeHours(String timeStart, String timeEnd,boolean isWorkDay){
      DtoWorkTimeItem period = new DtoWorkTimeItem(timeStart,timeEnd);
      if(isWorkDay)
          return period.getHours() - getWorkHours(timeStart,timeEnd);
      else{
          return period.getHours();
      }
  }
  /**
   * 每天工作小时
   * @return float
   */
  public float getDailyWorkHours() {
    return this.dailyWorkHours;
  }


  /**
   * 依据参数配置计算每日法定工作时间
   * @param workTimeList List
   * @return float
   */
  private float getDailyWorkHours(List workTimeList) {
    float fWorkHours = 0;
    if (workTimeList == null) {
      return WorkCalendarConstant.DAILY_WORK_HOUR;
    }
    for (int i = 0; i < workTimeList.size(); i++) {
      DtoWorkTimeItem dwti = (DtoWorkTimeItem)
                             workTimeList.get(i);
      fWorkHours = fWorkHours + dwti.getHours();
    }

    if (fWorkHours == 0) {
      return WorkCalendarConstant.DAILY_WORK_HOUR;
    }
    return fWorkHours;
  }
//  /**
//   * 计算两时间差间的小时数,timeEnd小于timeStart时会返回负数
//   * @param timeStart String
//   * @param timeEnd String
//   * @return float
//   */
//  public static float getHours(String timeStart, String timeEnd) {
//      int[] timeArrayStart = parseTime(timeStart);
//      int[] timeArrayEnd = parseTime(timeEnd);
//      return getHours(timeArrayStart,timeArrayEnd);
//  }
//  public static float getHours(int[] fromHM, int[] toHM) {
//      float result = (float)(toHM[0] - fromHM[0]) + //小时之差
//                     (float)(toHM[1] - fromHM[1]) / 60 ; //分之差
//      return result;
//  }
//  //解析HH:MM格式的实现,放入int数组中;
//  public static int[] parseTime(String timeStr) {
//      if (timeStr == null) {
//          throw new IllegalArgumentException();
//      }
//      if(!timeStr.matches(WorkCalendarConstant.TIME_HHMM_REG)){
//          throw new IllegalArgumentException("The time ["+timeStr+"] does not match format HH:MM");
//      }
//      String[] temp = timeStr.split(WorkCalendarConstant.DEFAULT_TIME_DELIM);
//      if (temp == null || temp.length != 2) {
//          throw new IllegalArgumentException("The time ["+timeStr+"] does not match format HH:MM");
//      }
//      int[] time = new int[2];
//      for (int i = 0; i < temp.length; i++) {
//          time[i] = Integer.parseInt(temp[i]);
//      }
//      return time;
//    }
}
