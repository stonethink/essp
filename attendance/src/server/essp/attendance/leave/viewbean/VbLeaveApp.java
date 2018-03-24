package server.essp.attendance.leave.viewbean;

import java.util.*;

import c2s.essp.attendance.*;
import server.framework.taglib.util.*;

public class VbLeaveApp {
    private List settlementWayList = Constant.SETTLEMENT_WAY_OPTIONS;
    private String loginId;
    private String orgId;
    private List leaveOptList;
    private VbLeavePersonalStatus leaveStatus;
    private String organization;
    private List accountList;
    public String getLoginId() {
        return loginId;
    }

    public String getOrgId() {

        return orgId;
    }

    public List getSettlementWayList() {
        return settlementWayList;
    }

    public VbLeavePersonalStatus getLeaveStatus() {
        return leaveStatus;
    }

    public List getLeaveOptList() {
        return leaveOptList;
    }

    public String getOrganization() {
        return organization;
    }

    public List getAccountList() {
        return accountList;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public void setOrgId(String orgId) {

        this.orgId = orgId;
    }

    public void setLeaveStatus(VbLeavePersonalStatus leaveStatus) {
        this.leaveStatus = leaveStatus;
        this.leaveStatus = leaveStatus;
        if(leaveStatus != null && leaveStatus.getLeaveTypeList() != null ){
            List leaveTypeList = leaveStatus.getLeaveTypeList();
            List leaveOptList = new ArrayList();
            for (int i = 0; i < leaveTypeList.size(); i++) {
                VbLeaveType leaveType = (VbLeaveType) leaveTypeList.get(i);
                SelectOptionImpl opt = new SelectOptionImpl(leaveType.getLeaveName()
                        , leaveType.getLeaveName());
                leaveOptList.add(opt);
            }
            this.setLeaveOptList(leaveOptList);
        }
    }

    public void setLeaveOptList(List leaveOptList) {
        this.leaveOptList = leaveOptList;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public void setAccountList(List accountList) {
        this.accountList = accountList;
    }

}
