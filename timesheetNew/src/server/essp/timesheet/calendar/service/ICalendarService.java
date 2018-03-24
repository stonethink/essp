package server.essp.timesheet.calendar.service;

import com.primavera.integration.client.bo.object.Calendar;
/**
 *
 * <p>Title: ICalendarService</p>
 *
 * <p>Description: ��ȡ����������Ϣ�Ľӿ�</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author wenhaizheng
 * @version 1.0
 */
public interface ICalendarService {
    /**
     * ����P3�е����û�ȡ���������ڵ�˳��
     * @return int
     */
    public int getWeekStrat();
    /**
     *  ��ѯP3���ݻ�ȡȫ�깤�������õ��ַ�������ʽΪ��yyyy-MM-dd~yyyy-MM-dd+00011001111100111110011111001111100111110011110000000000111110011111001111100111110011111001111100111110011111001111111100000001111001111100111110011111001111100111110011111001111100111110011111001111100111110011111001111100111110011111001111100111110011111001111100111111100000001111100111110011111001111100111110011111001111100111110011111001111100111110011111001��
     * 1.���ݴ���������ѭ��ȡ�������һ�쵽���һ���ÿ�������
     * 2.��ѯP3�и����ڵĹ���ʱ��Ϊ����
     * 3.�������ʱ�����0������ڹ����ձ�ʶΪ0,�����ʶ1
     * 4.��ȫ��Ĺ����ձ�ʶƴ�ӳ�ָ����ʽ���ַ�������
     *
     * @param tmpYear Integer
     * @param ca Calendar
     * @return String
     */
    public String getWorkDayString(Integer tmpYear, Calendar ca);
    /**
     * ��ȡP3����
     * @return Calendar
     */
    public Calendar getCalendar();
}
