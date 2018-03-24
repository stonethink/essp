package server.essp.timesheet.approval.service;

import java.util.Date;
import java.util.List;
import java.util.Vector;

/**
 * <p>Title: Approval Service interface</p>
 *
 * <p>Description: 定义Approval Service中需要实现的功能</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public interface IApprovalService {

    /**
     * 列出指定项目和时间段内的工时单
     * @param acntRid Long
     * @param begin Date
     * @param end Date
     * @param queryWay String
     * @param managerId String
     * @return List
     */
    public List listTsByProject(Long acntRid, Date begin, Date end, 
    		String queryWay, String managerId);

    /**
     * 列出当前用户为PM的所有状态正常项目
     * @param loginId
     * @return Vector
     */
    public Vector listProjectByManager(String loginId);

    /**
     * 列出当前用户为RM的所有员工在指定时间段内的工时单
     * @param managerId String
     * @param begin Date
     * @param end Date
     * @param queryWay String
     * @return List
     */
    public List listTsByRm(String managerId, Date begin, Date end, 
    		String queryWay);

    /**
     * 项目经理确认工时单
     * @param tsList List
     * @param employeeId String
     */
    public void approvalByPM(List tsList, String employeeId);

    /**
     * 资源经理确认工时单
     * @param tsList List
     * @param employeeId String
     */
    public void approvalByRM(List tsList, String employeeId);

    /**
     * 项目经理驳回工时单
     * @param tsList List
     * @param loginId String
     * @param employeeId String
     * @param reason String
     */
    public void rejectByPM(List tsList, String loginId, String employeeId, String reason);

    /**
     * 资源经理驳回工时单
     * @param tsList List
     * @param loginId String
     * @param employeeId String
     */
    public void rejectByRM(List tsList, String loginId, String employeeId, String reason);
}
