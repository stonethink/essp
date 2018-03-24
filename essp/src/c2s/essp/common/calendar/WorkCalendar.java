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
   * ���������ڻ�ȡ��ǰ�ܵ���ֹ����(��ÿ����ʼ�յ��趨Ϊ����)��
   * �ҵ������п���ʱ������ȡ���µ�����
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
   * ���������ڼ���־��ȡ��ǰ�ܵ���ֹ����(��ÿ����ʼ�յ��趨Ϊ����)��
   * �ñ�־��ʶ�Ƿ��ܰ��·ݵ���ʼ�ս��зָ������趨Ϊ��
   * flag = WorkCalendarConstant.SPLIT_WEEK_BY_MONTH_END��ʱ�������п���ʱ������ȡ���µ�����
   * �����ȡ�����ܵ���ֹ����
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
   * ��ȡ���������ܵ���ʼ��(��ÿ����ʼ�յ��趨Ϊ����)��
   * �ñ�־��ʶ�Ƿ��ܰ��·ݵ���ʼ�ս��зָ������趨Ϊ��
   * flag = WorkCalendarConstant.SPLIT_WEEK_BY_MONTH_END��ʱ�������п���ʱ������ȡ���µ���ʼ����
   * �����ȡ�����ܵ���ʼ����
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
    prmDay.get(Calendar.DAY_OF_WEEK);//ʲô���ã�
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
      //������ָ�,Ӧ���ص��ܵ���ʼ����
      return beginDayOfWeek;
    }
  }


  /**
   * ��ȡ���������ܵĽ�����(��ÿ����ʼ�յ��趨Ϊ����)��
   * �ñ�־��ʶ�Ƿ��ܰ��·ݵ���ʼ�ս��зָ������趨Ϊ��
   * flag = WorkCalendarConstant.SPLIT_WEEK_BY_MONTH_END��ʱ�������п���ʱ������ȡ���µĽ�������
   * �����ȡ�����ܵĽ�������
   *
   * @param day Calendar
   * @param flag int
   * @return Calendar
   */
  public static Calendar endDayOfWeek(Calendar day, int flag) {
          //modified by xr,��flag=1ʱ,�����endDayOfMonth�Ǵ����

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
   * ��ȡ���iStep�ܵ���ʼ����
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
    //iStep=0,���ر��ܵ���ʼ����
    if(iStep == 0){
        return week;
    }
    Calendar[] result = new Calendar[2];
    int weekNum = getDayNum(week[0], week[1]);
    //iStep>0,��1��ʼ�������iStep��Ӧÿ��,���ǰһ�ܿ���(����7��)����һ����Ӱ��
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
    //iStep<0,��-1��ʼ�������iStep��Ӧÿ��,�����һ�ܿ���(����7��)��ǰһ����Ӱ��
    //�˴���Bug
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
   * ��ȡ�������ڵ���ʼ��(�����µ���ʼ��)
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
   * �����ʼ���������е��µ���ʼ��
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
   * ��ȡ�������ڵ����(iStep)�µ���ʼ����(�����µ���ʼ��)
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
   * ��ȡ�µ���ʼ��(�����µ���ʼ��)
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
   * ��ȡ�µĽ�����(�����µ���ʼ��)
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
   * ��ȡ��ֹ����֮�������ܵ���ֹ���б�(�ù���Ϊȡ���ڷ�Χ֮���������б��ȱʡ����,
   * ���ܵ���ֹ�������µ���ʼ�ս��зָ�)
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
   * ��ȡ��ֹ����֮�������ܵ���ֹ���б����������ܵ���ֹ�վ����������ڷ�Χ֮��
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
   * ��ȡ�������ڣ����µ�����ֹ�����б�
   * @param day Calendar
   * @return List
   */
  public static List getBEWeekListByMonth(Calendar day) {
    Calendar[] beDay = getBEMonthDay(day);
    return getBEWeekListMax(beDay[0], beDay[1]);
  }

  /**
   * ��ȡ�����ꡢ�£�����ֹ�����б�
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
   * ��ȡ��ֹ����֮��Ĺ���Сʱ��
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
   * ��ȡ�������ڵ��ܵĹ���Сʱ��
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
   * ������,�£����ص�ǰ�·��м�������
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
   * ������,�£����ص�ǰ�·ݿ�Խ��������
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
   * �������ڣ����ظ������ǵڼ�����
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
   * �ж�ĳ�µ����һ���Ƿ����
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
    //modified by xr ȡ���µ����һ��,�жϸ����Ƿ�Ϊÿ�����һ��(����)
    prmDay.set(Calendar.DAY_OF_MONTH,monthBeginDay - 1);
    if (prmDay.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
      return false;
    } else {
      return true;
    }
  }

  /**
   * ���㹤��(������)
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
