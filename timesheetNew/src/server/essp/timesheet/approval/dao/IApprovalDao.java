package server.essp.timesheet.approval.dao;

import java.util.Date;
import java.util.List;

import c2s.essp.common.user.DtoUserBase;

/**
 * <p>Title: Approval Data Access Object</p>
 *
 * <p>Description: 定义与工时审批相关的数据存取功能</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public interface IApprovalDao {
    /**
     * 列出否签核者下的所有人员
     * @param managerId String
     * @return List
     */
    public List<DtoUserBase> listPersonByManager(String managerId);
    
    /**
     * 根据所选的专案，开始日期，结束日期，以及工时单状态查询工时单
     * @param accountRids
     * @param begin
     * @param end
     * @param status
     * @return
     */
    public List listPmApproval(String accountRids, Date begin, Date end, String status);

}
