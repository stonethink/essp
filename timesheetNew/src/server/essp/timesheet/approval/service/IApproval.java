package server.essp.timesheet.approval.service;

import java.util.List;
import java.util.Date;
import c2s.essp.timesheet.approval.DtoTsApproval;

/**
 * <p>Title: Approval Interface</p>
 *
 * <p>Description: 定义工时单审批需要实现的功能
 *   1.PM，RM列出待审批的工时单
 *   2.PM，RM批准工时单
 *   3.PM，RM驳回工时单</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public interface IApproval {

    /**
     * 列出指定项目和时间区间的工时单
     * @param acntRid Long
     * @param begin Date
     * @param end Date
     * @param queryWay String 
     * @param managerId String
     * @return List<DtoTsApproval>
     */
    public List<DtoTsApproval> pmList(Long acntRid, Date begin, Date end, 
    		                          String queryWay, String managerId);

    /**
     * 列出指定时间区间内，当前用户为RM的所有员工的工时单
     * @param managerId String
     * @param begin Date
     * @param end Date
     * @param queryWay String
     * @return List<DtoTsApproval>
     */
    public List<DtoTsApproval> rmList(String managerId, Date begin, Date end, String queryWay);

    /**
     * PM批准工时单
     * @param tsList List<DtoTsApproval>
     */
    public void pmApproval(List<DtoTsApproval> tsList);

    /**
     * RM批准工时单
     * @param tsList List<DtoTsApproval>
     */
    public void rmApproval(List<DtoTsApproval> tsList);

    /**
     * PM驳回工时单，需要eMail通知相关人员
     * @param tsList List<DtoTsApproval>
     * @param loginId String
     */
    public void pmReject(List<DtoTsApproval> tsList, String loginId, String reason);

    /**
     * RM驳回工时单，需要eMail通知相关人员
     * @param tsList List<DtoTsApproval>
     * @param loginId String
     */
    public void rmReject(List<DtoTsApproval> tsList, String loginId, String reason);
}
