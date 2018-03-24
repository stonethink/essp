package server.essp.pms.psr.logic;

import java.util.Date;
import java.util.Calendar;
import c2s.essp.common.calendar.WorkCalendar;
import java.util.GregorianCalendar;
import com.wits.util.comDate;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class SplitWeek {
    //���ݴ����������ڵó���ǰ�������ڵ���
    //��������ʼ�մ�������ʼ����������������ٸ��ݹ�˾���������֡�
    public static Date[] getWeekPeriod(Calendar cal, int i) {
        Date[] result = new Date[2];
        Calendar[] period = WorkCalendar.getNextBEWeekDay(cal, i);
        Date startDate = period[0].getTime();
        Date finishDate = period[1].getTime();
        int days = computeDays(startDate, finishDate);
        while (days < 7) {
            i--;
            //�������С��7�Ͱ�ʱ������ǰ��
            Calendar[] periodOther = WorkCalendar.getNextBEWeekDay(cal, i);
            startDate = periodOther[0].getTime();
            finishDate = periodOther[1].getTime();
            days = computeDays(startDate, finishDate);
        }
        result[0] = startDate;
        result[1] = finishDate;
        return result;
    }

    private static int computeDays(Date startDate, Date finishDate) {
        Calendar calStart = GregorianCalendar.getInstance();
        calStart.setTime(startDate);
        Calendar calFinish = GregorianCalendar.getInstance();
        calFinish.setTime(finishDate);
        int days = WorkCalendar.getDayNum(calStart, calFinish);
        return days;
    }

    public static void main(String[] args) {
        SplitWeek splitweek = new SplitWeek();
        Calendar cal = Calendar.getInstance();
        for (int i = 0; i < 20; i++) {
            Date[] d = splitweek.getWeekPeriod(cal, -i);
            System.out.println(comDate.dateToString(d[0]) + "~~~~~~~~" +
                               comDate.dateToString(d[1]));
        }
    }
}
