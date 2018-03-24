package server.essp.timesheet.period.service;

import java.util.Date;
import java.util.List;

import c2s.essp.timesheet.weeklyreport.DtoTimeSheetPeriod;

/**
 * <p>Title: IPeriodService</p>
 *
 * <p>Description: ����������Ĳ���ҵ���߼�</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author wenhaizheng
 * @version 1.0
 */
public interface IPeriodService {
    /**
     * ���ݴ������ڲ�ѯ��ǰ�������ڵ���������
     * ��ת����DtoTimeSheetPeriod����
     * @param day Date
     * @return DtoTimeSheetPeriod
     */
    public DtoTimeSheetPeriod queryPeriodByDay(Date day);

    /**
     * �Ե�ǰ����Ϊ��׼��ǰ������Ƶ�һ������
     * @param date Date
     * @param moveWay String
     * @return DtoTimeSheetPeriod
     */
    public DtoTimeSheetPeriod moveToNextOrPrePeriod(Date date, String moveWay);
    
    /**
     * ����ƶ�step����������
     * @param endDate Date
     * @param step int
     * @return DtoTimeSheetPeriod
     * @throws Exception
     */
    public DtoTimeSheetPeriod getNextPeriod(Date endDate, int step);
    
    /**
     * ��������ȡ�����ڼ�Ĺ�ʱ����
     * @param begin
     * @param end
     * @return
     */
    public List getPeriodByDate(Date begin, Date end);

}
