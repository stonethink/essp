package server.essp.timesheet.calendar.dao;

import java.util.Date;
import com.primavera.integration.client.bo.object.Calendar;
/**
 *
 * <p>Title: ICalendarP3Dao</p>
 *
 * <p>Description: ����P3��ȡ���������Ϣ�Ľӿ�</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author wenhaizheng
 * @version 1.0
 */
public interface ICalendarP3Dao {
    /**
     * ��ѯP3���ݻ�ȡĳ��Ĺ���ʱ����
     * @param date Date
     * @return double
     * @throws Exception
     */
    public double getWorkHours(Calendar ca, Date date) throws Exception;
    /**
     * ��ȡP3����
     * @return Calendar
     */
    public Calendar getCalendar();
    /**
     * ��ȡһ��ʱ���ڵı�׼����ʱ��
     * @param begin Date
     * @param end Date
     * @return Double
     * @throws Exception
     */
    public Double getWorkHoursBetweenDates(Date begin ,Date end) throws Exception;
    /**
     * ���ݹ��Ż�ȡһ��ʱ���ڸ���Ա�ı�׼����ʱ��
     * @param begin Date
     * @param end Date
     * @param loginId String
     * @return Double
     * @throws Exception
     */
    public Double getWorkHoursBetweenDatesByLoginId(Date begin ,Date end, String loginId) throws Exception;
}
