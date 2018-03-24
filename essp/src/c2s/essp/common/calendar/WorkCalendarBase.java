package c2s.essp.common.calendar;

import java.io.*;
import java.util.*;

public class WorkCalendarBase implements Serializable {
  HashMap hashMap = new HashMap();
  static int weekBeginDay = WorkCalendarConstant.WEEK_BEGIN_DAY;
  static int monthBeginDay = WorkCalendarConstant.MONTH_BEGIN_DAY;
  static int yearBeginMonth = WorkCalendarConstant.YEAR_BEGIN_MONTH;

  static float dailyWorkHour = WorkCalendarConstant.DAILY_WORK_HOUR;

  public WorkCalendarBase() {
  }

  public WorkCalendarBase(List wdConfigs) {
    putYearWorkDayConfig(wdConfigs);
  }

  public void putYearWorkDayConfig(List wdConfigs) {
    if (wdConfigs == null) {
      return;
    }
    for (int i = 0; i < wdConfigs.size(); i++) {
      DtoTcWt dtoTcWt = (DtoTcWt) wdConfigs.get(i);
      putYearWorkDayConfig(dtoTcWt);
    }
  }

  public void putYearWorkDayConfig(DtoTcWt dtoTcWt) {
    if (dtoTcWt == null) {
      return;
    }
    hashMap.put(dtoTcWt.getWtsYear(), dtoTcWt.getWtsDays());
  }

  public void putYearWorkDayConfig(int year, String wtsDays) {
    Integer wtsYear = new Integer(year);
    if (wtsYear == null) {
      return;
    }
    hashMap.put(wtsYear, wtsDays);
  }

  public String getYearWorkDayConfig(int year) {
    String sYearWorkDayConfig = "";
    Integer wtsYear = new Integer(year);
    if (hashMap.containsKey(wtsYear)) {
      sYearWorkDayConfig = (String) hashMap.get(wtsYear);
      return sYearWorkDayConfig;
    } else {
      return getDefaultWorkDayConfig(year);
    }
  }


  /**
   * ��ȡ������ݵ�ȱʡ�������ã�������������Ϊ�ǹ����գ���������Ϊ������
   * @param year int
   * @return String
   */
  public static String getDefaultWorkDayConfig(int year) {
    String sYearWorkDayConfig = "";
    Calendar cStart = Calendar.getInstance();
    cStart.set(year, 0, 1);

    Calendar cEnd = Calendar.getInstance();
    cEnd.set(year, 11, 31);

    StringBuffer sb = new StringBuffer();
    Calendar day = cStart;
    while (true) {
      String sDayType = getDefualtWorkDayType(day);
      sb = sb.append(sDayType);
      day = getNextDay(day);
      //modified by xr  ���һ��û�м������� after()�ȽϿ��ܲ�׼,
      //�����һ�� day��cEnd���������,��day��MILLISECOND��cEnd��Ҫ��,after()����true
//      if (day.after(cEnd)) {
//        break;
//      }
       if(getYear(day) > getYear(cEnd)){
           break;
       }
    }
    sYearWorkDayConfig = sb.toString();
    return sYearWorkDayConfig;
  }


  /**
   * ��ȡ�������ڵ���һ��
   * @param day Calendar
   * @return Calendar
   */
  public static Calendar getNextDay(Calendar day) {
    return getNextDay(day, 1);
  }


  /**
   * ȡ��������(day)��NextDay(���iStep��0,��Ϊ������죬���iStep<0��Ϊǰ�����죩
   * @param day Calendar
   * @param iStep int
   * @return Calendar
   */
  public static Calendar getNextDay(Calendar day, int iStep) {
    Calendar nextDay = Calendar.getInstance();
    nextDay.set(Calendar.HOUR_OF_DAY,0);
    nextDay.set(Calendar.MINUTE,0);
    nextDay.set(Calendar.SECOND,0);
    nextDay.set(Calendar.MILLISECOND,0);
//    long lOneDayTm = 24 * 60 * 60 * 1000;
//    long lNextDayDayTM = day.getTimeInMillis() + lOneDayTm * iStep;
//    nextDay.setTimeInMillis(lNextDayDayTM);
    nextDay.set(day.get(Calendar.YEAR), day.get(Calendar.MONTH),
                day.get(Calendar.DATE) + iStep);
    return nextDay;
  }
  /**
   * ȡ��������(day)��Next Work Day(���iStep��0,��Ϊ������죬���iStep<0��Ϊǰ�����죩
   * @param day Calendar
   * @param iStep int
   * @return Calendar
   */
  public Calendar getNextWorkDay(Calendar day, int iStep) {
    Calendar nextDay = Calendar.getInstance();
    long lOneDayTm = 24 * 60 * 60 * 1000;
    if (iStep == 0) {
      nextDay.set(day.get(Calendar.YEAR), day.get(Calendar.MONTH),
                  day.get(Calendar.DATE));
      return nextDay;
    }

    int iFlag = 1;
    if (iStep < 0) {
      iFlag = -1;
    }

    int iAStep = iFlag * iStep;
    int iDayOfYear = dayNoOfYear(day) - 1;
    int iDayNum = 0;
    int iWorkDays = 0;
    int iStartYear = day.get(Calendar.YEAR);
    char[] sWDConfigs = getYearWorkDayConfig(iStartYear).toCharArray();

//    for (int iWorkDays = 0; iWorkDays <= iAStep; ) {
    while (true) {
      //�ȼ�鵱ǰ��ݵĹ������Ƿ��ڷ�Χ֮�ڣ����iFlag = 1,���ӵ�ǰ�������
      iDayOfYear = iDayOfYear + iFlag;
      if (iDayOfYear >= 0 && iDayOfYear < sWDConfigs.length) {
        iDayNum = iDayNum + iFlag;
        char c = sWDConfigs[iDayOfYear];
        if (c == WorkCalendarConstant.WORKDAY.charAt(0)) {
          iWorkDays++;
        }
        if (iWorkDays >= iAStep) {
          break;
        }
      } else { //��ȡ��һ�������
        iStartYear = iStartYear + iFlag;
        sWDConfigs = getYearWorkDayConfig(iStartYear).toCharArray();
        if (iFlag == 1) {
          iDayOfYear = -1;
        } else {
          iDayOfYear = sWDConfigs.length;
        }
      }
    }

//    long lNextDayDayTM = day.getTimeInMillis() + lOneDayTm * iDayNum;
//    nextDay.setTimeInMillis(lNextDayDayTM);
    nextDay.set(day.get(Calendar.YEAR), day.get(Calendar.MONTH),
                day.get(Calendar.DATE) + iDayNum);

    return nextDay;
  }
  public Date getNextNDay(Date old, int offset) {

      Calendar cOld = Calendar.getInstance();
      Calendar cNew = Calendar.getInstance();
      cOld.setTime(old);
      cNew = getNextWorkDay(cOld, offset);
      return cNew.getTime();
}
  /**
   * ȡ���������ڵ���(��Ȼ��)�ĵڼ���,1��1��Ϊ1
   * @param day Calendar
   * @return int
   */
  public static int dayNoOfYear(Calendar day) {
//    int iYear = day.get(Calendar.YEAR);
//    Calendar yearFirstDay = Calendar.getInstance();
//    yearFirstDay.set(iYear, 0, 1);
//    int iDayNo = getDayNum(yearFirstDay, day);
    int iDayNo = day.get(Calendar.DAY_OF_YEAR);
    return iDayNo;
  }

  public List getWorkDays(Calendar startDay, int iNum) {
    if(iNum==0){
      return null;
    }
//    List list = new ArrayList();
    Calendar endDay = getNextWorkDay(startDay,iNum);
    return getWorkDays(startDay,endDay);
//    return list;
  }


  /**
   * ȡ����ֹ����֮������й������б�
   * @param startDay Calendar
   * @param endDay Calendar
   * @return List{Calendar}
   */
  public List getWorkDays(Calendar startDay, Calendar endDay) {
      // added by xr
      if( startDay == null || endDay == null )  {
          throw new IllegalArgumentException("start and end date can  not be null!");
      }
    if (startDay.after(endDay)) {
      return null;
    }
    List list = new ArrayList();
    Calendar day = (Calendar) startDay;
    int iStartYear = day.get(Calendar.YEAR);
    char[] sWDConfigs = getYearWorkDayConfig(iStartYear).toCharArray();
    int iDayOfYear = dayNoOfYear(day) - 1;
    //char cWorkDay = WorkCalendarConstant.WORKDAY.charAt(0);

    while (true) {
      int iYear = day.get(Calendar.YEAR);
      if (iStartYear == iYear) {
        char c = sWDConfigs[iDayOfYear++];
        if (c == WorkCalendarConstant.WORKDAY.charAt(0)) {
          Calendar workDay = (Calendar) day.clone();
//                    WorkDay workDay = new WorkDay(day);
//                    workDay.setDayType(WORKDAY);
          list.add(workDay);
        }
      } else {
        iStartYear = iYear;
        sWDConfigs = getYearWorkDayConfig(iStartYear).toCharArray();
        iDayOfYear = 0;
        char c = sWDConfigs[iDayOfYear++];
        if (c == WorkCalendarConstant.WORKDAY.charAt(0)) {
          Calendar workDay = (Calendar) day.clone();
//                    WorkDay workDay = new WorkDay(day);
//                    workDay.setDayType(WORKDAY);
          list.add(workDay);
        }
      }
      day = getNextDay(day);
      if (day.after(endDay)) {
        break;
      }
    }
    return list;
  }


  /**
   * ��ȡ��ֹ����֮���ʵ������,����endDay - startDay + 1
   * @param startDay Calendar
   * @param endDay Calendar
   * @return int
   */
  public static int getDayNum(Calendar startDay, Calendar endDay) {
      //added by xr
      if( startDay == null || endDay == null )  {
          throw new IllegalArgumentException("start and end date can  not be null!");
      }
      if(startDay.after(endDay)){
          prt(startDay);
          prt(startDay);
          throw new IllegalArgumentException("start date can not be after end date!");
      }

    double dStartDayTM = startDay.getTimeInMillis();
    double dEndDayTM = endDay.getTimeInMillis();
    //modified by xr Ӧ�ý������� - ��ʼ����
//    int iDays = (int)( (dStartDayTM - dEndDayTM) / (24 * 60 * 60 * 1000)) + 1;
    int iDays = (int)( (dEndDayTM - dStartDayTM) / (24 * 60 * 60 * 1000)) + 1;
//        System.out.println("lDayTM="+lDayTM+";lYFTM="+lYFTM+";iDays=" + iDays);
    return iDays;
  }
  /**
   * �����ֹ����(����)�����е���
   * @param startDay Calendar
   * @param endDay Calendar
   * @return List
   */
  public static List getDays(Calendar startDay, Calendar endDay){
      int dayNum = getDayNum(startDay,endDay);
      List result = new ArrayList(dayNum);
      if(dayNum == 1){
          result.add(startDay);
      }else if(dayNum == 2){
          result.add(startDay);
          result.add(endDay);
      }else{
          Calendar day = startDay;
          while (dayNum > 0) {
              result.add(day);
              day = getNextDay(day);
              dayNum--;
          }
      }
      return result;
  }

  /**
   * ��ȡ��ֹ����֮��Ĺ�������,��endDay=startDay��Ϊ������ʱ����Ϊ1
   * @param startDay Calendar
   * @param endDay Calendar
   * @return int
   */
  public int getWorkDayNum(Calendar startDay, Calendar endDay) {
    //added by xr
    if( startDay == null || endDay == null )  {
        throw new IllegalArgumentException("start and end date can  not be null!");
    }
    int wdNum = 0;
    if (startDay.after(endDay)) {
      return (getWorkDayNum(endDay,startDay) * -1) ;
    }
    int iStartYear = startDay.get(Calendar.YEAR);
    int iStartDayNo = dayNoOfYear(startDay) - 1;
    int iEndYear = endDay.get(Calendar.YEAR);
    int iEndDayNo = dayNoOfYear(endDay) - 1;
//    System.out.println("startDay=" + startDay.getTime().toString()
//      +";endDay=" + endDay.getTime().toString()
//      +";iStartDayNo=" + iStartDayNo
//      +";iEndDayNo=" + iEndDayNo
//      );

    for (int i = iStartYear; i <= iEndYear; i++) {
      //ֻ��һ�������
      if (i == iStartYear && i == iEndYear) {
        char[] sWDConfigs = getYearWorkDayConfig(i).toCharArray();
        for (int j = iStartDayNo; j <= iEndDayNo && j < sWDConfigs.length; j++) {
          char c = sWDConfigs[j];
          if (c == WorkCalendarConstant.WORKDAY.charAt(0)) {
            wdNum++;
          }
        }
      }
      //�м�����
      if (i > iStartYear && i < iEndYear) {
        char[] sWDConfigs = getYearWorkDayConfig(i).toCharArray();
        for (int j = 0; j < sWDConfigs.length; j++) {
          char c = sWDConfigs[j];
          if (c == WorkCalendarConstant.WORKDAY.charAt(0)) {
            wdNum++;
          }
        }
      }
      //��һ��
      if (i == iStartYear && i < iEndYear) {
          char[] sWDConfigs = getYearWorkDayConfig(i).toCharArray();
          for (int j = iStartDayNo ; j < sWDConfigs.length; j++) {
              char c = sWDConfigs[j];
              if (c == WorkCalendarConstant.WORKDAY.charAt(0)) {
                  wdNum++;
              }
          }
      }
      //���һ��
      if (i > iStartYear && i == iEndYear) {
        char[] sWDConfigs = getYearWorkDayConfig(i).toCharArray();
        for (int j = 0; j <= iEndDayNo && j < sWDConfigs.length; j++) {
          char c = sWDConfigs[j];
          if (c == WorkCalendarConstant.WORKDAY.charAt(0)) {
            wdNum++;
          }
        }
      }
    }
    return wdNum;
  }


  /**
   * ��ȡ��������ȱʡ�Ĺ��������ͣ����Ϊ���������ռ�Ϊ�ǹ����գ�����Ϊ������
   * @param day Calendar
   * @return String
   */
  public static String getDefualtWorkDayType(Calendar day) {
    if ((day.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
        || (day.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)) {
      return WorkCalendarConstant.HOLIDAY;
    } else {
      return WorkCalendarConstant.WORKDAY;
    }
  }


  /**
   *�������û�ȡ��������ȱʡ�Ĺ���������
   * @param day Calendar
   * @return String
   */
  public String getWorkDayType(Calendar day) {
    String sWorkDayType = "";
    int iYear = day.get(Calendar.YEAR);
    char[] sWDConfigs = getYearWorkDayConfig(iYear).toCharArray();
    int iDayOfYear = dayNoOfYear(day) - 1;
    sWorkDayType = sWorkDayType + sWDConfigs[iDayOfYear];
    return sWorkDayType;
  }

  public boolean isWorkDay(Calendar day) {
    String sWorkDayType = getWorkDayType(day);
    if (sWorkDayType.equals(WorkCalendarConstant.WORKDAY)) {
      return true;
    } else {
      return false;
    }
  }

  public static String dayToString(Calendar ca) {
    int iYear = ca.get(Calendar.YEAR);
    int iMonth = ca.get(Calendar.MONTH) + 1;
    int iDay = ca.get(Calendar.DATE);
    String sRet = String.valueOf(iYear);

    if (iMonth < 10) {
      sRet = sRet + "-" + "0" + iMonth;
    } else {
      sRet = sRet + "-" + iMonth;
    }

    if (iDay < 10) {
      sRet = sRet + "-" + "0" + iDay;
    } else {
      sRet = sRet + "-" + iDay;
    }

    return sRet;
  }


  /**
   * �ж�ĳ���Ƿ�����������֮��
   * @param some Calendar
   * @param start Calendar
   * @param end Calendar
   * @return boolean
   */
  public static boolean isBetween(Calendar some, Calendar start, Calendar end) {
          //modified by xr
    if( start == null || end == null )  {
        throw new IllegalArgumentException("start and end date can  not be null!");
    }
    if(start.after(end)){
        prt(start);
        prt(end);
        throw new IllegalArgumentException("start date can not be after end date!");
    }
    if (some.before(start) || some.after(end)) {
      return false;
    } else {
      return true;
    }
  }


  /**
   * ���ָ�����ڶ�Ӧ����,��
   * @param date Date
   * @return int
   */
  public static int getYear(Calendar cl) {
    return cl.get(Calendar.YEAR);
  }

  public static int getMonth(Calendar cl) {
    return cl.get(Calendar.MONTH) + 1;
  }

  public int getMonthBeginDay() {
    return monthBeginDay;
  }

  public void setMonthBeginDay(int monthBeginDay) {
    this.monthBeginDay = monthBeginDay;
  }

  public int getWeekBeginDay() {
    return weekBeginDay;
  }

  public void setWeekBeginDay(int weekBeginDay) {
    this.weekBeginDay = weekBeginDay;
  }

  public int getYearBeginMonth() {
    return yearBeginMonth;
  }

  public void setYearBeginMonth(int yearBeginMonth) {
    this.yearBeginMonth = yearBeginMonth;
  }

  public static float getDailyWorkHour() {
    return dailyWorkHour;
  }

  public void setDailyWorkHour(float dailyWorkHour) {
    this.dailyWorkHour = dailyWorkHour;
  }

  public static void  prt(Calendar cal){
      System.out.println("Calendar;year:" + getYear(cal) + " month:" +getMonth(cal) + " day:" + cal.get(Calendar.DAY_OF_MONTH));
  }
  public static void main(String[] args){
      Calendar startDay = Calendar.getInstance();
      Calendar endDay = Calendar.getInstance();
      startDay.set(2005,10,12);
      System.out.println();
  }
}
