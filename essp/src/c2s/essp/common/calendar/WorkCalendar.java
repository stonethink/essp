package c2s.essp.common.calendar;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;

public class WorkCalendar extends WorkCalendarBase {
  public WorkCalendar() {
  }

  public WorkCalendar(List wdConfigs) {
    super(wdConfigs);
  }


  /**
   * 以输入日期获取当前周的起止日期(以每周起始日的设定为依据)，
   * 且当该周有跨月时，仅获取当月的日期
   * @param day Calendar
   * @return Calendar[]
   */
  public static Calendar[] getBEWeekDay(Calendar day) {
    Calendar[] beDay = new Calendar[2];
    beDay[0] = beginDayOfWeek(day,
                              WorkCalendarConstant.SPLIT_WEEK_BY_MONTH_END);
    beDay[1] = endDayOfWeek(day,
                            WorkCalendarConstant.SPLIT_WEEK_BY_MONTH_END);
    return beDay;
  }

  /**
   * 以输入日期及标志获取当前周的起止日期(以每周起始日的设定为依据)，
   * 该标志标识是否将周按月份的起始日进行分隔，即设定为：
   * flag = WorkCalendarConstant.SPLIT_WEEK_BY_MONTH_END，时当该周有跨月时，仅获取当月的日期
   * 否则获取完整周的起止日期
   *
   * @param day Calendar
   * @param flag int
   * @return Calendar[]
   */
  public static Calendar[] getBEWeekDay(Calendar day, int flag) {
    Calendar[] beDay = new Calendar[2];
    beDay[0] = beginDayOfWeek(day, flag);
    beDay[1] = endDayOfWeek(day, flag);
    return beDay;
  }


  /**
   * 获取输入日期周的起始日(以每周起始日的设定为依据)，
   * 该标志标识是否将周按月份的起始日进行分隔，即设定为：
   * flag = WorkCalendarConstant.SPLIT_WEEK_BY_MONTH_END，时当该周有跨月时，仅获取当月的起始日期
   * 否则获取完整周的起始日期
   *
   * @param day Calendar
   * @param flag int
   * @return Calendar
   */
  public static Calendar beginDayOfWeek(Calendar day, int flag) {
//        System.out.println(dayToString(day));
    GregorianCalendar prmDay = new GregorianCalendar();
    int iYear = day.get(Calendar.YEAR);
    int iMonth = day.get(Calendar.MONTH);
    int iDay = day.get(Calendar.DATE);
    prmDay.set(iYear, iMonth, iDay);
    prmDay.setFirstDayOfWeek(weekBeginDay);
    prmDay.get(Calendar.DAY_OF_WEEK);//什么作用？
    prmDay.set(Calendar.HOUR_OF_DAY,0);
    prmDay.set(Calendar.MINUTE,0);
    prmDay.set(Calendar.SECOND,0);
    prmDay.set(Calendar.MILLISECOND,0);
//    int iWeek = prmDay.get(Calendar.WEEK_OF_YEAR);
//    prmDay.set(Calendar.YEAR, iYear);
//    prmDay.set(Calendar.MONTH, iMonth);
//    prmDay.set(Calendar.WEEK_OF_YEAR, iWeek);
    prmDay.set(Calendar.DAY_OF_WEEK, weekBeginDay);

    Calendar beginDayOfWeek = prmDay;

    Calendar beginDayOfMonth = beginDayOfMonth(day);

    if (flag == WorkCalendarConstant.SPLIT_WEEK_BY_MONTH_END) {
      if (beginDayOfWeek.before(beginDayOfMonth)) {
        return beginDayOfMonth;
      } else {
        return beginDayOfWeek;
      }
    } else {
      //return beginDayOfMonth;
      //如果不分隔,应返回当周的起始日期
      return beginDayOfWeek;
    }
  }


  /**
   * 获取输入日期周的结束日(以每周起始日的设定为依据)，
   * 该标志标识是否将周按月份的起始日进行分隔，即设定为：
   * flag = WorkCalendarConstant.SPLIT_WEEK_BY_MONTH_END，时当该周有跨月时，仅获取当月的结束日期
   * 否则获取完整周的结束日期
   *
   * @param day Calendar
   * @param flag int
   * @return Calendar
   */
  public static Calendar endDayOfWeek(Calendar day, int flag) {
          //modified by xr,若flag=1时,求出的endDayOfMonth是错误的

          Calendar beginDayOfWeek = beginDayOfWeek(day, 0);
          Calendar endDayOfWeek = getNextDay(beginDayOfWeek, 6);
    Calendar endDayOfMonth = endDayOfMonth(day);
    if (flag == WorkCalendarConstant.SPLIT_WEEK_BY_MONTH_END) {
      if (endDayOfWeek.after(endDayOfMonth)) {
        return endDayOfMonth;
      } else {
        return endDayOfWeek;
      }
    } else {
      return endDayOfWeek;
    }
  }

  /**
   * 获取最近iStep周的起始日期
   * @param day Calendar
   * @param iStep int
   * @return Calendar[]
   */
  public static Calendar[] getNextBEWeekDay(Calendar day, int iStep) {
    GregorianCalendar prmDay = new GregorianCalendar();
    int iYear = day.get(Calendar.YEAR);
    int iMonth = day.get(Calendar.MONTH);
    int iDay = day.get(Calendar.DATE);
    prmDay.set(iYear, iMonth, iDay);
    prmDay.setFirstDayOfWeek(weekBeginDay);
    int iWeek = weekOfYear(iYear, iMonth+1, iDay);
    Calendar[] week = getBEWeekDay(day);
    //iStep=0,返回本周的起始日期
    if(iStep == 0){
        return week;
    }
    Calendar[] result = new Calendar[2];
    int weekNum = getDayNum(week[0], week[1]);
    //iStep>0,从1开始往后遍历iStep对应每周,如果前一周跨月(不满7天)则下一周有影响
    if(iStep > 0){
        int i = 1;
        boolean formerWeekIsFull = true;
        if(week[1].get(Calendar.DAY_OF_MONTH) == WorkCalendarConstant.MONTH_BEGIN_DAY - 1){
            formerWeekIsFull= (weekNum == 7);
        }
        while( i <= iStep){
            if(formerWeekIsFull){
                prmDay.set(Calendar.YEAR,iYear);
                prmDay.set(Calendar.WEEK_OF_YEAR, iWeek + i);
                prmDay.set(Calendar.DAY_OF_WEEK, weekBeginDay);
                week = getBEWeekDay(prmDay);
                weekNum = getDayNum(week[0], week[1]);
                formerWeekIsFull = (weekNum == 7);
            }else{
                iWeek -= 1;
                prmDay.set(Calendar.YEAR,iYear);
                prmDay.getTime();
                prmDay.set(Calendar.WEEK_OF_YEAR, iWeek + i);
                prmDay.get(5);
                prmDay.set(Calendar.DAY_OF_MONTH, monthBeginDay);
                formerWeekIsFull = true;
                week = getBEWeekDay(prmDay);
            }
            i++;
        }
        result[0] = week[0];
        result[1] = week[1];
    }
    //iStep<0,从-1开始往后遍历iStep对应每周,如果该一周跨月(不满7天)则前一周有影响
    //此处有Bug
    if(iStep < 0){
        int i = -1;
        boolean nextWeekIsFull = true;
        if(week[0].get(Calendar.DAY_OF_MONTH) == WorkCalendarConstant.MONTH_BEGIN_DAY ){
            nextWeekIsFull= (weekNum == 7);
        }
        while( i >= iStep){
            if(nextWeekIsFull){
                prmDay.set(Calendar.YEAR,iYear);
                prmDay.set(Calendar.WEEK_OF_YEAR, iWeek + i);
                prmDay.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
                week = getBEWeekDay(prmDay);
                weekNum = getDayNum(week[0], week[1]);
                nextWeekIsFull = (weekNum == 7);
            }else{
                iWeek += 1;
                prmDay.set(Calendar.YEAR,iYear);
                prmDay.set(Calendar.WEEK_OF_YEAR, iWeek + i);
                prmDay.set(Calendar.DAY_OF_WEEK, weekBeginDay);
                prmDay.get(5);
                prmDay.set(Calendar.DAY_OF_MONTH, monthBeginDay - 2);
                nextWeekIsFull = true;
                week = getBEWeekDay(prmDay);
            }
            i--;
        }
        result[0] = week[0];
        result[1] = week[1];
    }
    return result;
  }

  /**
   * 获取输入日期的起始日(依据月的起始日)
   * @param day Calendar
   * @return Calendar[]
   */
  public static Calendar[] getBEMonthDay(Calendar day) {
    Calendar[] beDay = new Calendar[2];
    beDay[0] = beginDayOfMonth(day);
    beDay[1] = endDayOfMonth(day);
    return beDay;
  }
  /**
   * 获得起始日期内所有的月的起始日
   * @param begin Calendar
   * @param end Calendar
   * @return List
   */
  public static List getBEMonthList(Calendar begin,Calendar end){
      if( begin == null || end == null )  {
          throw new IllegalArgumentException("start and end date can  not be null!");
      }
      if(begin.getTimeInMillis() > end.getTimeInMillis()){
          throw new IllegalArgumentException("start can not be larger than end date!");
      }
      int bYear = getYear(begin);
      int bMonth = getMonth(begin) - 1;
      int bDay = begin.get(Calendar.DAY_OF_MONTH);
      if(bDay >= 26)
          bMonth ++;
      Calendar cal = Calendar.getInstance();
      cal.setTime(begin.getTime());
      Calendar end2 = getBEMonthDay(end)[1];
      List result = new ArrayList();
      while(cal.getTimeInMillis() <= end2.getTimeInMillis()){
          Calendar[] month = getBEMonthDay(cal);
          result.add(month);
          bMonth ++;
          cal.set(bYear,bMonth,25);
      }
      return result;
  }
  /**
   * 获取输入日期的最近(iStep)月的起始日期(依据月的起始日)
   * @param day Calendar
   * @param iStep int
   * @return Calendar[]
   */
  public static Calendar[] getNextBEMonthDay(Calendar day, int iStep) {
    GregorianCalendar prmDay = new GregorianCalendar();
    int iYear = day.get(Calendar.YEAR);
    int iMonth = day.get(Calendar.MONTH);
    int iDay = day.get(Calendar.DATE);
    prmDay.set(iYear, iMonth, iDay);
    int iNMonth = iMonth + iStep;
    prmDay.set(Calendar.MONTH, iNMonth);

    return getBEMonthDay(prmDay);
  }

  /**
   * 获取月的起始日(依据月的起始日)
   * @param day Calendar
   * @return Calendar
   */
  public static Calendar beginDayOfMonth(Calendar day) {
    Calendar bDay = Calendar.getInstance();
    bDay.set(Calendar.HOUR_OF_DAY,0);
    bDay.set(Calendar.MINUTE,0);
    bDay.set(Calendar.SECOND,0);
    bDay.set(Calendar.MILLISECOND,0);
    int iYear = day.get(Calendar.YEAR);
    int iMonth = day.get(Calendar.MONTH);
    int iDay = day.get(Calendar.DATE);

//        if (monthBeginDay == 1) {
//            bDay.set(iYear, iMonth, 1);
//        } else {
    if (iDay >= monthBeginDay) {
      bDay.set(iYear, iMonth, monthBeginDay);
    } else {
      int iBMonth = iMonth - 1;
      int iBYear = iYear;
      if (iBMonth < 0) {
        iBMonth = 11;
        iBYear = iYear - 1;
      }
      bDay.set(iBYear, iBMonth, monthBeginDay);
//            }
    }
    return bDay;
  }

  /**
   * 获取月的结束日(依据月的起始日)
   * @param day Calendar
   * @return Calendar
   */
  public static Calendar endDayOfMonth(Calendar day) {
    Calendar bDay = Calendar.getInstance();
    int iYear = day.get(Calendar.YEAR);
    int iMonth = day.get(Calendar.MONTH);
    int iDay = day.get(Calendar.DATE);

    if (monthBeginDay == 1) {
      int iEDay = day.getActualMaximum(Calendar.DATE);
      bDay.set(iYear, iMonth, iEDay);
    } else {
      if (iDay >= monthBeginDay) {
        bDay.set(iYear, iMonth + 1, monthBeginDay - 1);
      } else {
        bDay.set(iYear, iMonth, monthBeginDay - 1);
      }
    }
    return bDay;
  }

  /**
   * 获取起止日期之内所有周的起止的列表(该功能为取日期范围之间所有周列表的缺省调用,
   * 且周的起止会依据月的起始日进行分隔)
   * @param beginDay Calendar
   * @param endDay Calendar
   * @return List
   */
  public static List getBEWeekListMax(Calendar beginDay, Calendar endDay) {
    if (beginDay.after(endDay)) {
      return null;
    }
    List list = new ArrayList();
    Calendar day = beginDay;
    for (int i = 0; ; i++) {
      Calendar[] beDay = getNextBEWeekDay(day, i);
      list.add(beDay);
      if (!beDay[1].before(endDay)) { //Modify by lipengxu ">" --> ">="
        break;
      }
    }
    return list;
  }

  /**
   * 获取起止日期之内所有周的起止的列表，并且所有周的起止日均在输入日期范围之内
   * @param beginDay Calendar
   * @param endDay Calendar
   * @return List
   */
  public static List getBEWeekListMin(Calendar beginDay, Calendar endDay) {
    if (beginDay.after(endDay)) {
      return null;
    }
    List list = new ArrayList();
    Calendar day = beginDay;
    for (int i = 0; ; i++) {
      Calendar[] beDay = getNextBEWeekDay(day, i);
      if (!(beDay[0].before(beginDay) || beDay[1].after(endDay))) {
        list.add(beDay);
      }
      if (beDay[1].after(endDay)) {
        break;
      }
    }
    return list;
  }

  /**
   * 获取输入日期，当月的周起止日期列表
   * @param day Calendar
   * @return List
   */
  public static List getBEWeekListByMonth(Calendar day) {
    Calendar[] beDay = getBEMonthDay(day);
    return getBEWeekListMax(beDay[0], beDay[1]);
  }

  /**
   * 获取输入年、月，周起止日期列表
   * @param year int
   * @param month int
   * @return List
   */
  public static List getBEWeekListByMonth(int year, int month) {
    Calendar beginDay = Calendar.getInstance();
    beginDay.set(year, month, monthBeginDay);
    Calendar endDay = endDayOfMonth(beginDay);
    return getBEWeekListMax(beginDay, endDay);
  }

  /**
   * 获取起止日期之间的工作小时数
   * @param startDay Calendar
   * @param endDay Calendar
   * @return float
   */
  public float getWorkHours(Calendar startDay, Calendar endDay) {
    float wh = 0f;
    int iDayNum = getWorkDayNum(startDay, endDay);
    wh = iDayNum * getDailyWorkHour();
    return wh;
  }

  /**
   * 获取输入日期当周的工作小时数
   * @param day Calendar
   * @return float
   */
  public float getWeekWorkHours(Calendar day) {
    Calendar[] beDay = getBEWeekDay(day);
    return getWorkHours(beDay[0], beDay[1]);
  }

  public static float ph2pd(float ph) {
    float pd = 0f;
    pd = ph / WorkCalendarConstant.DAILY_WORK_HOUR;
    return pd;
  }

  public static float pd2ph(float pd) {
    float ph = 0f;
    ph = pd * WorkCalendarConstant.DAILY_WORK_HOUR;
    return ph;
  }

  public static float ph2pm(float ph) {
    float pm = 0f;
    pm = ph / WorkCalendarConstant.MONTHLY_WORK_HOUR;
    return pm;
  }

  public static float pm2ph(float pm) {
    float ph = 0f;
    ph = pm * WorkCalendarConstant.MONTHLY_WORK_HOUR;
    return ph;
  }

  public static float pd2pm(float pd) {
    float pm = 0f;
    pm = pd / WorkCalendarConstant.MONTHLY_WORK_DAY;
    return pm;
  }

  public static float pm2pd(float pm) {
    float pd = 0f;
    pd = pm * WorkCalendarConstant.MONTHLY_WORK_DAY;
    return pd;
  }


  /**
   * 输入年,月，返回当前月份有几个星期
   * @param year int
   * @param month int
   * @return int
   */
  public static int weekNumOfMonthMin(int year, int month) {
    GregorianCalendar prmDay = new GregorianCalendar();
    int iweeknum;
//        prmDay.setFirstDayOfWeek(prmDay.MONDAY);
    prmDay.setFirstDayOfWeek(weekBeginDay);
    prmDay.setMinimalDaysInFirstWeek(7);
    prmDay.set(Calendar.YEAR, year);
    prmDay.set(Calendar.MONTH, month - 1);

    prmDay.set(Calendar.DAY_OF_MONTH,
               prmDay.getActualMaximum(prmDay.DAY_OF_MONTH));
    iweeknum = prmDay.getActualMaximum(Calendar.WEEK_OF_MONTH);
    return iweeknum;
  }


  /**
   * 输入年,月，返回当前月份跨越几个星期
   * @param year int
   * @param month int
   * @return int
   */
  public static int weekNumOfMonthMax(int year, int month) {
    GregorianCalendar prmDay = new GregorianCalendar();
    int iweeknum;
//        prmDay.setFirstDayOfWeek(prmDay.MONDAY);
    prmDay.setFirstDayOfWeek(weekBeginDay);
    prmDay.setMinimalDaysInFirstWeek(7);
    prmDay.set(Calendar.YEAR, year);
    prmDay.set(Calendar.MONTH, month - 1);
    iweeknum = prmDay.getActualMaximum(Calendar.WEEK_OF_MONTH);
    return iweeknum;
  }


  /**
   * 输入日期，返回该日期是第几星期
   * @param year int
   * @param month int
   * @param date int
   * @return int
   */
  public static int weekOfYear(int year, int month, int date) {
    GregorianCalendar prmDay = new GregorianCalendar();
    int idate;
    prmDay.set(Calendar.YEAR, year);
    prmDay.set(Calendar.MONTH, month - 1);
    prmDay.set(Calendar.DATE, date);
    prmDay.setFirstDayOfWeek(weekBeginDay);
    idate = prmDay.get(Calendar.WEEK_OF_YEAR);
    //add by lipengxu
    //for a week bestride 2 years getted next year's week number.
    for(int i = 1; i < 8 && idate == 1 && month == 12; i++) {
        GregorianCalendar tmpDay = new GregorianCalendar();
        tmpDay.set(year, month-1, date - i);
        tmpDay.setFirstDayOfWeek(weekBeginDay);
        idate = tmpDay.get(Calendar.WEEK_OF_YEAR);
        if(idate > 1) {
            idate++;
        }
    }

    return idate;
  }

  public static int weekOfYear(int year){
      return weekOfYear(year,12,31);
  }
  /**
   * 判断某月的最后一周是否跨月
   * @param year int
   * @param month int
   * @return boolean
   */
  public static boolean isWeekBrigeMonth(int year, int month) {
    GregorianCalendar prmDay = new GregorianCalendar();
//        prmDay.setFirstDayOfWeek(prmDay.MONDAY);
    prmDay.setFirstDayOfWeek(weekBeginDay);
    //prmDay.setMinimalDaysInFirstWeek(7);
    prmDay.set(Calendar.YEAR, year);
    prmDay.set(Calendar.MONTH, month - 1);
//    prmDay.set(Calendar.DAY_OF_MONTH,1);
//    int iendday = prmDay.getActualMaximum(Calendar.DAY_OF_MONTH);
//    prmDay.set(weekBeginDay, iendday);
    //modified by xr 取当月的最后一天,判断该天是否为每周最后一天(周五)
    prmDay.set(Calendar.DAY_OF_MONTH,monthBeginDay - 1);
    if (prmDay.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
      return false;
    } else {
      return true;
    }
  }

  /**
   * 计算工期(工作日)
   * @param start Date
   * @param finish Date
   * @return Double
   */
  public static Double calculateTimeLimit(Date start, Date finish) {
      WorkCalendar wc = WrokCalendarFactory.clientCreate();
      Calendar cStart = Calendar.getInstance();
      Calendar cFinish = Calendar.getInstance();
      cStart.setTime(start);
      cFinish.setTime(finish);
      int days = wc.getWorkDayNum(cStart, cFinish);
      return new Double(days);
  }

  public static int getDayNum(Date begin, Date end) {
      WorkCalendar wc = WrokCalendarFactory.clientCreate();
      Calendar cBegin = Calendar.getInstance();
      Calendar cEnd = Calendar.getInstance();
      cBegin.setTime(begin);
      cEnd.setTime(end);
      int num = wc.getWorkDayNum(cBegin, cEnd);
      return num > 0 ? num - 1 : num + 1;
  }

  public static Date getNextWorkDay(Date old) {
      WorkCalendar wc = WrokCalendarFactory.clientCreate();
      Calendar cOld = Calendar.getInstance();
      cOld.setTime(old);
      return wc.isWorkDay(cOld) ? old : wc.getNextWorkDay(cOld, 1).getTime();
    }

    public static Date resetBeginDate(Date b) {
        Calendar c = Calendar.getInstance();
        c.setTime(b);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        Date b2 = c.getTime();
        return b2;
    }

    public static Date resetEndDate(Date e) {
        Calendar c = Calendar.getInstance();
        c.setTime(e);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);
        Date e2 = c.getTime();
        return e2;
    }
    
    public static int ignoreTimeCompare(Date d1, Date d2) {
    	return resetBeginDate(d1).compareTo(resetBeginDate(d2));
    }

}
