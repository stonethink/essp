package server.essp.pms.wbs.logic;

import java.util.Date;

public class CheckPoint implements java.io.Serializable {
    private Long acntRid;
    private Long wbsRid;
    private Long chkRid;

    private String chkName;
    private Double chkWeight;
    private Date dueDate;
    private Date finishDate;
    private String isCompleted;
    private String remark;

    public CheckPoint() {
    }

    public static void main(String[] args) {
        CheckPoint checkpoint = new CheckPoint();
    }

    public Long getAcntRid() {
        return acntRid;
    }

    public String getChkName() {
        return chkName;
    }

    public Long getChkRid() {
        return chkRid;
    }

    public Double getChkWeight() {
        return chkWeight;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public String getIsCompleted() {
        return isCompleted;
    }

    public String getRemark() {
        return remark;
    }

    public Long getWbsRid() {
        return wbsRid;
    }

    public void setAcntRid(Long acntRid) {
        this.acntRid = acntRid;
    }

    public void setChkName(String chkName) {
        this.chkName = chkName;
    }

    public void setChkRid(Long chkRid) {
        this.chkRid = chkRid;
    }

    public void setChkWeight(Double chkWeight) {
        this.chkWeight = chkWeight;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public void setIsCompleted(String isCompleted) {
        this.isCompleted = isCompleted;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setWbsRid(Long wbsRid) {
        this.wbsRid = wbsRid;
    }
}
