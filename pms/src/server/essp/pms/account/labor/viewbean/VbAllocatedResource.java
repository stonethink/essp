package server.essp.pms.account.labor.viewbean;

import java.util.*;

public class VbAllocatedResource {
    private String rid;
    private String loginId;
    private String empName;
    private String accountId;
    /**
     * ����Աÿ�ܼƻ�״����VbResourceWeekPlan
     */
    private List weekPlan = new ArrayList();
    /**
     * ����Ա���мƻ���ʱ���
     */
    private List planDateItem = new ArrayList();
    public String getRid() {

        return rid;
    }

    public String getLoginId() {

        return loginId;
    }

    public String getEmpName() {

        return empName;
    }

    public List getWeekPlan() {
        return weekPlan;
    }

    public List getPlanDateItem() {
        return planDateItem;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setRid(String rid) {

        this.rid = rid;
    }

    public void setLoginId(String loginId) {

        this.loginId = loginId;
    }

    public void setEmpName(String empName) {

        this.empName = empName;
    }

    public void setPlanDateItem(List planDateItem) {
        this.planDateItem = planDateItem;
    }

    public void setWeekPlan(List weekPlan) {
        this.weekPlan = weekPlan;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }


}
