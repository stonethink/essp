package server.essp.timesheet.weeklyreport.dao;

import c2s.essp.timesheet.weeklyreport.DtoRsrcHour;
import c2s.essp.timesheet.weeklyreport.DtoTimeSht;

/**
 * <p>Title: </p>
 * <p>Description: ��չprimavera API�ж�timesheet�Ĳ�����
 * �ṩ���棬���£�ɾ�����ύtimesheet�Ĺ���</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: Wistron ITS</p>
 * @author lipengxu
 * @version 1.0
 */
public interface ITimeSheetP3DbDao {

    /**
     * ����TimeSheet
     * @param dto DtoTimeSheet
     */
    public void insertTimeSheet(DtoTimeSht dto);
    
    /**
     * ����Timesheet��״̬
     * @param dto
     * @param status
     */
    public void updateTimeSheetStatus(DtoTimeSht dto, String status);

    /**
     * ����ResourceHour
     * @param dto DtoRsrcHour
     */
    public void insertResourceHours(DtoRsrcHour dto);

    /**
     * ��ȡ�µ�RsrcHour����ֵ
     * @return Long
     */
    public Long getNewResourceHursId();
    
    /**
     * ��ȡRsrcHour����ֵ
     * @return
     */
    public Long getResourceHursId();
    /**
     * ��ȡuserId
     * @param userName String
     * @return Long
     */
    public Long getUserId(String userName);
    /**
     * ��ȡrsrcId
     * @param userId Long
     * @return Long
     */
    public Long getRsrcId (Long userId);
    /**
     * ����taskId��rsrcId��ȡTaskRsrcId
     * @param taskId Long
     * @param rsrcId Long
     * @return Long
     */
    public Long getTaskRsrcId(Long taskId, Long rsrcId);
    /**
     * ɾ��RSRCHOUR����
     * @param rsId Long
     * @param rsrcId Long
     * @return Long
     */
    public void deleteRsrchour(Long rsId, Long rsrcId);
    /**
     * ɾ��TIMESHT����
     * @param rsId Long
     * @param rsrcId Long
     */
    public void deleteTimesht(Long rsId, Long rsrcId);
    /**
     * ����task_id(activity_id)��ȡproj_id
     * @param taskId Long
     * @return Long
     */
    public Long getProjIdFromTask(Long taskId);
}
