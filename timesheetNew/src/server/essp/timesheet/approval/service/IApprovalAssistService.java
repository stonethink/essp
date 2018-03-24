package server.essp.timesheet.approval.service;

import db.essp.timesheet.TsTimesheetMaster;
import java.util.Date;
import c2s.essp.timesheet.approval.DtoTsApproval;
import java.util.List;

/**
 * <p>Title: IApprovalAssistService</p>
 *
 * <p>Description: 辅助审核功能用于进行审核过程中的一些特殊业务操作</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author wenhaizheng
 * @version 1.0
 */
public interface IApprovalAssistService {
	
	/**
     * 回写数据到P3数据库中
     * @param tsRid Long
     */
	public void writeBackToP3(Long tsRid);
	
    /**
     * 回写数据到P3数据库中
     * @param tsMaster TsTimesheetMaster
     */
    public void writeBackToP3(TsTimesheetMaster tsMaster);
    
    /**
     * 设置P3中工时单的状态
     * @param status String
     * @param tsMaster TsTimesheetMaster
     */
    public void setTimesheetStatusInP3(TsTimesheetMaster tsMaster, String status);
    /**
     * 发送邮件给相关人员
     * @param loginId String
     * @param ccId String[]
     * @param vmFile String
     * @param title String
     * @param obj Object
     */
    public void sendMail(final String loginId, final  String[] ccId,
                         final String vmFile, final  String title,
                         final Object obj);
    /**
     * 获取某一时间段内的标准工作时间
     * 循环时间段的每一天从P3中查出该日期的工作时间
     * 累计所有的工作时间
     * @param begin Date
     * @param end Date
     * @param loginId String
     * @return Double
     */
    public Double getStandarHours(Date begin, Date end, String loginId);

    /**
     * PM审核时
     * 计算否一个人的实际工作时间，加班时间及请假时间
     * 并设置到dto中
     * 1.根据当前的项目以及Timesheet Master的rid
     *   查询Timesheet Detail资料如果没有则返回错误
     * 2.按Timesheet Master的rid查询出所有的detail资料
     *   (因为需要计算该人员的请假时间,请假时间只会填到部门下)
     * 3.根据detail记录查询填写的相关的工作时间
     * 4.如果是本项目下的时间则累计实际工作时间,加班时间
     * 5.如果不是本项目查询code_value是否是假别code是则
     *   累计到请假时间上
     * @param dtoTsApproval DtoTsApproval
     * @param acntRid Long
     * @param tsRid Long
     * @param approvalLevel String
     * @return boolean
     */
    public boolean setHoursOk(DtoTsApproval dtoTsApproval, Long acntRid,
                              Long tsRid, String approvalLevel);

    /**
     * RM审核时
     * 计算否一个人的实际工作时间，加班时间及请假时间
     * 并设置到dto中
     * 1.根据当前的Timesheet Master的rid
     *   查询Timesheet Detail资料如果没有则返回错误
     * 2.按Timesheet Master的rid查询出所有的detail资料
     * 3.根据detail记录查询填写的相关的工作时间
     * 4.查询code_value是否是假别code
     *   是则累计到请假时间上，否则累计实际工作时间和加班时间
     * @param dtoTsApproval DtoTsApproval
     * @param tsRid Long
	 * @param approvalLevel String
     * @return boolean
     */
    public boolean setHoursOk(DtoTsApproval dtoTsApproval, Long tsRid, 
    		                  String approvalLevel);
    /**
     * 找到要发送邮件的相关人员（适用用于PM不同流程中的审核）
     * @param tsMaster TsTimesheetMaster
     * @param vmFile String
     * @param title String
     * @param object Object
     * @param type String
     * @param loginId String
     * @param acntRidList List
     * @param rejectedAcntRid Long
     * @param mailToRM boolean
     */
    public void searchPersonAndMailForPM(TsTimesheetMaster tsMaster,
                                    String vmFile, String type,
                                    String loginId, List<Long> acntRidList,
                                    Long rejectedAcntRid, boolean mailToRM, String reason);
    /**
     * 找到要发送邮件的相关人员（适用用于RM在不同流程中的审核）
     * @param tsMaster TsTimesheetMaster
     * @param vmFile String
     * @param title String
     * @param object Object
     * @param type String
     * @param loginId String
     * @param acntRidList List
     */
    public void searchPersonAndMailForRM(TsTimesheetMaster tsMaster,
                                    String vmFile, String type,
                                    String loginId, List<Long> acntRidList, String reason);

    /**
     * 处理审核流程变更时的操作
     * @param processType String
     */
    public void processLevelChanged(String processType, String site);
}
