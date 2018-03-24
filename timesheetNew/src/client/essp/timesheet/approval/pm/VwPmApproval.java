package client.essp.timesheet.approval.pm;

import client.essp.timesheet.approval.VwAbstractApproval;
import client.essp.timesheet.approval.VwAbstractApprovalList;
import client.essp.timesheet.approval.VwWorkGeneral;

/**
 * <p>Title: VwPmApproval</p>
 *
 * <p>Description: PM审批主界面</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class VwPmApproval extends VwAbstractApproval {

    /**
     * 返回VwAbstractApprovalList实例
     * @return VwAbstractApprovalList
     */
    protected VwAbstractApprovalList getApprovalList() {
        return new VwPmApprovalList();
    }
    /**
     * 返回PM审核情况下构造的VwWorkGeneral
     * @return VwWorkGeneral
     */
    protected VwWorkGeneral getWorkGeneral() {
        return new VwWorkGeneral(true);
    }
}
