package server.essp.timesheet.weeklyreport.service;

import c2s.essp.timesheet.weeklyreport.DtoTimeSheet;
import c2s.essp.timesheet.weeklyreport.DtoTimeSheetDetail;
import java.util.Date;
import java.util.List;

/**
 *
 * <p>Title: ITimeSheetService</p>
 *
 * <p>Description: �빤ʱ����д��صĹ��ܶ���
 *                   ������ʱ��������ɾ���ġ��顣��ʱ��Note����</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public interface ITimeSheetService {

    /**
     * ����TimeSheet( ��ResourceHour list)
     * @param dto DtoTimeSheet
     */
    public void saveTimeSheet(DtoTimeSheet dto);

    /**
     * ����TimeSheet notes
     * @param note String
     * @param tsRid Long
     * @return String
     */
    public String saveTimeSheetNotes(String note, Long tsRid);

    /**
     * submit timesheet
     * ��״̬ΪActive, Rejected��TimeSheet��״̬��ΪSubmited
     * @param dtoTimeSheet DtoTimeSheet
     * @param loginId String
     */
    public void submitTimeSheet(DtoTimeSheet dtoTimeSheet);

    /**
     * active timesheet
     * ��״̬ΪRejected��TimeSheet��״̬��ΪActive
     * @param dtoTimeSheet DtoTimeSheet
     */
    public void activeTimeSheet(DtoTimeSheet dtoTimeSheet);
    
    /**
     * ��ȡ������м�¼�Ĺ�ʱ��
     * @param loginId
     * @param nowDate Date ��ǰ��ʱ���Ŀ�ʼʱ��
     * @return DtoTimeSheet
     */
    public DtoTimeSheet copyFromLastTs(DtoTimeSheet dtoTimeSheet);

    /**
     * ��ȡTimeSheet
     *  ��DtoTimeSheetDetail, DtoTimeSheetDay
     * @param tsId Long
     * @param rsrcId Long
     * @return DtoTimeSheet
     */
    public DtoTimeSheet getTimeSheet(Long tsId, String loginId);
    
    /**
     * �жϹ�ʱ���Ƿ��Ѿ�����
     * @param tsId
     * @param loginId
     * @return
     */
    public boolean isTimeSheetExist(Long tsId, String loginId);

    /**
     * ��ȡT��ǰ�û�workDay���ڵ�DtoTimeSheet
     * @param workDay Date
     * @return DtoTimeSheet
     */
    public DtoTimeSheet getTimeSheet(String loginId, Date workDay);

    /**
     * ����Rid��ȡDtoTimeSheet
     * @param rid Long
     * @return DtoTimeSheet
     */
    public DtoTimeSheet getTimeSheet(Long rid);

    /**
     * ɾ��TimeSheet��һ����¼
     * @param dtoActivity DtoTimeSheetActivity
     */
    public void deleteTimeSheetDetail(DtoTimeSheetDetail dtoTsDetail);

    /**
     * ��ȡTimesheet��Notes
     * @param tsRid Long
     * @return String
     */
    public String getTimeSheetNotes(Long tsRid);
    
    /**
     * ��ȡָ���û�ָ��ָ��ʱ��ı�׼��ʱ
     * @param loginId
     * @param dateList
     * @return
     */
    public List listStandarHours(String loginId, List<Date> dateList);
    
    /**
     * �����ձ������ܱ�
     * @param loginId
     * @param dto
     */
    public DtoTimeSheet InitTimesheetByDaily(String loginId, DtoTimeSheet dto);

}
