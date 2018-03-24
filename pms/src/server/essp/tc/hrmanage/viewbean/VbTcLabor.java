package server.essp.tc.hrmanage.viewbean;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 人力报表每人所在项目的记录
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Rong.Xiao
 * @version 1.0
 */
public class VbTcLabor {
    public VbTcLabor() {
    }

    private String accountId;
    private String accountName;
    private String loginId;
    private String userName;
    private String type;
    private String jobCode;
    private String positionType;
    private Date actualBeginDate;
    private Date actualEndDate;
    private BigDecimal actualWorkTime;
    private BigDecimal overTime;
    private BigDecimal standardTime;
    private BigDecimal assignedTime;


    private BigDecimal accountActualTime;//项目实际工时
    private BigDecimal accountEarnedTime;//项目总认列工时

    private String orgName;
    public String getAccountId() {
        return accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserType() {
        return type;
    }

    public String getJobCode() {
        return jobCode;
    }

    public Date getBeginDate() {
        return actualBeginDate;
    }

    public Date getEndDate() {
        return actualEndDate;
    }

    public BigDecimal getActualWorkTime() {
        return actualWorkTime;
    }
    //正常工时=实际工时-加班工时
    public BigDecimal getNormalWorkTime() {
        if(actualWorkTime == null)
            return new BigDecimal(0);
        else if(overTime == null)
            return overTime;
        else
            return actualWorkTime.subtract(overTime);

    }

    public BigDecimal getOverTime() {
        return overTime;
    }

    public BigDecimal getAssignedTime() {
        return assignedTime;
    }

    public String getType() {
        return type;
    }

    public Date getActualBeginDate() {
        return actualBeginDate;
    }

    public Date getActualEndDate() {
        return actualEndDate;
    }

    public BigDecimal getStandardTime() {
        return standardTime;
    }

    public BigDecimal getAccountEarnedTime() {
        return accountEarnedTime;
    }

    public BigDecimal getAccountActualTime() {
        return accountActualTime;
    }

    public String getLoginId() {
        return loginId;
    }

    public String getPositionType() {
        return positionType;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserType(String userType) {
        this.type = userType;
    }

    public void setJobCode(String jobCode) {
        this.jobCode = jobCode;
    }

    public void setBeginDate(Date beginDate) {
        this.actualBeginDate = beginDate;
    }

    public void setEndDate(Date endDate) {
        this.actualEndDate = endDate;
    }

    public void setActualWorkTime(BigDecimal actualWorkTime) {
        this.actualWorkTime = actualWorkTime;
    }

    public void setOverTime(BigDecimal overTime) {
        this.overTime = overTime;
    }

    public void setAssignedTime(BigDecimal assignedTime) {
        this.assignedTime = assignedTime;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setActualBeginDate(Date actualBeginDate) {
        this.actualBeginDate = actualBeginDate;
    }

    public void setActualEndDate(Date actualEndDate) {
        this.actualEndDate = actualEndDate;
    }

    public void setStandardTime(BigDecimal standardTime) {
        this.standardTime = standardTime;
    }

    public void setAccountActualTime(BigDecimal accountActualTime) {
        this.accountActualTime = accountActualTime;
    }

    public void setAccountEarnedTime(BigDecimal accountEarnedTime) {
        this.accountEarnedTime = accountEarnedTime;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public void setPositionType(String positionType) {
        this.positionType = positionType;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

}
