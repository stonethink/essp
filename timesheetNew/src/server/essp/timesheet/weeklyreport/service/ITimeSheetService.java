package server.essp.timesheet.weeklyreport.service;

import c2s.essp.timesheet.weeklyreport.DtoTimeSheet;
import c2s.essp.timesheet.weeklyreport.DtoTimeSheetDetail;
import java.util.Date;
import java.util.List;

/**
 *
 * <p>Title: ITimeSheetService</p>
 *
 * <p>Description: 与工时单填写相关的功能定义
 *                   包括工时单的增、删、改、查。工时单Note保存</p>
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
     * 保存TimeSheet( 含ResourceHour list)
     * @param dto DtoTimeSheet
     */
    public void saveTimeSheet(DtoTimeSheet dto);

    /**
     * 保存TimeSheet notes
     * @param note String
     * @param tsRid Long
     * @return String
     */
    public String saveTimeSheetNotes(String note, Long tsRid);

    /**
     * submit timesheet
     * 将状态为Active, Rejected的TimeSheet的状态设为Submited
     * @param dtoTimeSheet DtoTimeSheet
     * @param loginId String
     */
    public void submitTimeSheet(DtoTimeSheet dtoTimeSheet);

    /**
     * active timesheet
     * 将状态为Rejected的TimeSheet的状态设为Active
     * @param dtoTimeSheet DtoTimeSheet
     */
    public void activeTimeSheet(DtoTimeSheet dtoTimeSheet);
    
    /**
     * 获取最近张有记录的工时单
     * @param loginId
     * @param nowDate Date 当前工时单的开始时间
     * @return DtoTimeSheet
     */
    public DtoTimeSheet copyFromLastTs(DtoTimeSheet dtoTimeSheet);

    /**
     * 获取TimeSheet
     *  含DtoTimeSheetDetail, DtoTimeSheetDay
     * @param tsId Long
     * @param rsrcId Long
     * @return DtoTimeSheet
     */
    public DtoTimeSheet getTimeSheet(Long tsId, String loginId);
    
    /**
     * 判断工时单是否已经存在
     * @param tsId
     * @param loginId
     * @return
     */
    public boolean isTimeSheetExist(Long tsId, String loginId);

    /**
     * 获取T当前用户workDay所在的DtoTimeSheet
     * @param workDay Date
     * @return DtoTimeSheet
     */
    public DtoTimeSheet getTimeSheet(String loginId, Date workDay);

    /**
     * 根据Rid获取DtoTimeSheet
     * @param rid Long
     * @return DtoTimeSheet
     */
    public DtoTimeSheet getTimeSheet(Long rid);

    /**
     * 删除TimeSheet的一条记录
     * @param dtoActivity DtoTimeSheetActivity
     */
    public void deleteTimeSheetDetail(DtoTimeSheetDetail dtoTsDetail);

    /**
     * 获取Timesheet的Notes
     * @param tsRid Long
     * @return String
     */
    public String getTimeSheetNotes(Long tsRid);
    
    /**
     * 获取指定用户指定指定时间的标准工时
     * @param loginId
     * @param dateList
     * @return
     */
    public List listStandarHours(String loginId, List<Date> dateList);
    
    /**
     * 根据日报生成周报
     * @param loginId
     * @param dto
     */
    public DtoTimeSheet InitTimesheetByDaily(String loginId, DtoTimeSheet dto);

}
