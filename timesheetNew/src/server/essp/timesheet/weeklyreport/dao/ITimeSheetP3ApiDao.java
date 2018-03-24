package server.essp.timesheet.weeklyreport.dao;

import c2s.essp.timesheet.weeklyreport.DtoTimeSheetPeriod;
import java.util.Date;
import java.util.List;

import com.primavera.integration.client.bo.object.Calendar;

import c2s.essp.timesheet.weeklyreport.DtoTimeSheetDetail;

/**
 *
 * <p>Title: ITimeSheetP3ApiDao</p>
 *
 * <p>Description: �빤ʱ����ط���P3 API�Ķ���</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public interface ITimeSheetP3ApiDao {

    /**
     * ��ȡday���ڵ�TimeSheetPeriod
     * @param day Date
     * @return DtoTimeSheetPeriod
     * @throws Exception
     */
    public DtoTimeSheetPeriod getTimeSheetPeriod(Date day) throws Exception;

    /**
     * ��ѯ����ʱ������Period֮���ж��ٸ�Period
     * @param begin Date
     * @param end Date
     * @return int
     * @throws Exception
     */
    public int getPeriodNum(Date begin, Date end) throws Exception;

    /**
     * ��ȡ��ǰ�û���Resource Id
     * @return Long
     */
    public Long getCurrentResourceId() throws Exception ;

    /**
     * �г���ǰ�û�ָ�������б�ı�׼��ʱ�������ǵĺ�
     * @param dateList List
     * @return List<Double>  size = dateList.size + 1 (sumHour)
     */
    public List listStandarHours(List<Date> dateList);
    
    /**
     * ��ȡ��Դ����ְ����ְ����
     *      ��ְ���ڣ���Դ�������͵����������һ������λʱ�����������Ϊ0�ġ���Ч���ڡ�
     *      ��ְ���ڣ�����һ������λʱ���������Ϊ0�ġ���Ч���ڡ�����������ְ����֮��
     * @param loginId String
     * @return Date[2]
     * @throws Exception
     */
    public Date[] getInOutDate(String loginId);
    
    /**
     * ��ȡָ���û�ָ��ָ��ʱ��ı�׼��ʱ
     * @param loginId
     * @param dateList
     * @return
     */
    public List listStandarHours(String loginId, List<Date> dateList);
    
    public Calendar getCalendar(String loginId);

    /**
     * ����DtoTimeSheetDetail�е�activityId��ȡ����ҵ�ļƻ�ʱ��
     * @param dtoDetail DtoTimeSheetDetail
     */
    public void getActivityPlanDate(DtoTimeSheetDetail dtoDetail)throws Exception;
    
    public Double getSumStandarHours(Calendar c, List<Date> dateList,
            Date inDate, Date outDate);
}
