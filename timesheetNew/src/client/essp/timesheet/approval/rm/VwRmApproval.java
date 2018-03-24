package client.essp.timesheet.approval.rm;

import client.essp.timesheet.approval.VwAbstractApproval;
import client.essp.timesheet.approval.VwAbstractApprovalList;
import client.essp.timesheet.approval.VwWorkGeneral;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class VwRmApproval extends VwAbstractApproval {

    /**
     * ����VwAbstractApprovalListʵ��
     * @return VwAbstractApprovalList
     */
    protected VwAbstractApprovalList getApprovalList() {
        return new VwRmApprovalList();
    }
    /**
     * ����RM����¹����VwWorkGeneral
     * @return VwWorkGeneral
     */
    protected VwWorkGeneral getWorkGeneral() {
        return new VwWorkGeneral(false);
    }
}
