package server.essp.timesheet.weeklyreport.dao;

import c2s.essp.timesheet.weeklyreport.DtoRsrcHour;
import c2s.essp.timesheet.weeklyreport.DtoTimeSht;

/**
 * <p>Title: </p>
 * <p>Description: 扩展primavera API中对timesheet的操作，
 * 提供保存，更新，删除和提交timesheet的功能</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: Wistron ITS</p>
 * @author lipengxu
 * @version 1.0
 */
public interface ITimeSheetP3DbDao {

    /**
     * 新增TimeSheet
     * @param dto DtoTimeSheet
     */
    public void insertTimeSheet(DtoTimeSht dto);
    
    /**
     * 更新Timesheet的状态
     * @param dto
     * @param status
     */
    public void updateTimeSheetStatus(DtoTimeSht dto, String status);

    /**
     * 插入ResourceHour
     * @param dto DtoRsrcHour
     */
    public void insertResourceHours(DtoRsrcHour dto);

    /**
     * 获取新的RsrcHour主键值
     * @return Long
     */
    public Long getNewResourceHursId();
    
    /**
     * 获取RsrcHour主键值
     * @return
     */
    public Long getResourceHursId();
    /**
     * 获取userId
     * @param userName String
     * @return Long
     */
    public Long getUserId(String userName);
    /**
     * 获取rsrcId
     * @param userId Long
     * @return Long
     */
    public Long getRsrcId (Long userId);
    /**
     * 根据taskId和rsrcId获取TaskRsrcId
     * @param taskId Long
     * @param rsrcId Long
     * @return Long
     */
    public Long getTaskRsrcId(Long taskId, Long rsrcId);
    /**
     * 删除RSRCHOUR资料
     * @param rsId Long
     * @param rsrcId Long
     * @return Long
     */
    public void deleteRsrchour(Long rsId, Long rsrcId);
    /**
     * 删除TIMESHT资料
     * @param rsId Long
     * @param rsrcId Long
     */
    public void deleteTimesht(Long rsId, Long rsrcId);
    /**
     * 根据task_id(activity_id)获取proj_id
     * @param taskId Long
     * @return Long
     */
    public Long getProjIdFromTask(Long taskId);
}
