package server.essp.timesheet.period.dao;

import java.util.Date;
import java.util.List;

import c2s.essp.timesheet.weeklyreport.DtoTimeSheetPeriod;
/**
 *
 * <p>Title: IPeriodDao</p>
 *
 * <p>Description: �����������ز���</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author wenhaizheng
 * @version 1.0
 */
public interface IPeriodDao {
    /**
     * �����������ڲ�ѯ��������
     * @param day Date
     * @return DtoTimeSheetPeriod
     * @throws Exception
     */
    public DtoTimeSheetPeriod getTimeSheetPeriod(Date day) throws Exception;
    /**
     * �������һ����������
     * @param date Date
     * @return DtoTimeSheetPeriod
     * @throws Exception
     */
    public DtoTimeSheetPeriod getNextPeriod(Date date) throws Exception;
    /**
     * ��ǰ����һ����������
     * @param date Date
     * @return DtoTimeSheetPeriod
     * @throws Exception
     */
    public DtoTimeSheetPeriod getPrePeriod(Date date) throws Exception;
    
    /**
     * ����ƶ�step����������
     * @param endDate Date
     * @param step int
     * @return DtoTimeSheetPeriod
     * @throws Exception
     */
    public DtoTimeSheetPeriod getNextPeriod(Date endDate, int step) throws Exception;
    
    /**
     * ���ݿ�ʼ�ͽ������ڻ�ȡ��ʱ������
     * @param begin
     * @param end
     * @return DtoTimeSheetPeriod
     * @throws Exception
     */
    public DtoTimeSheetPeriod getPeriod(Date begin, Date end) throws Exception;
    
    /**
     * ��ȡȫ���������б�
     * @return List
     * @throws Exception
     */
    public List getAllTimeSheetPeriod() throws Exception;
    
    /**
     * ��ȡ���һ����ʱ������
     * @return DtoTimeSheetPeriod
     * @throws Exception
     */
    public DtoTimeSheetPeriod getLastEndPeriod() throws Exception;
    
    /**
     * ��ѯһ���������Ƿ����Ĺ�ʱ�������ص�
     * @param startDate
     * @param endDate
     * @return
     */
    public boolean isDuplicate(Date startDate, Date endDate) throws Exception;
    /**
     * ����ʱ��β�ѯ����ʱ����ڵ�����
     * @param begin
     * @param end
     * @return List
     * @throws Exception
     */
    public List getPeriodByDate(Date begin,Date end) throws Exception;
}
