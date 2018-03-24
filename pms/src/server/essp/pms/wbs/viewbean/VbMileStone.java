package server.essp.pms.wbs.viewbean;

public class VbMileStone {
    private String acntRid;
    private String wbsRid;
    private String code;
    private String name;
    private String manager;
    private String type;
    private String status;
    private String anticipatedFinish;
    private String actualFinish;
    private String reachedCondition;
    private String remark;
    public String getAcntRid() {
        return acntRid;
    }

    public String getWbsRid() {
        return wbsRid;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getManager() {
        return manager;
    }

    public String getType() {
        return type;
    }

    public String getStatus() {
        return status;
    }

    public String getAnticipatedFinish() {
        return anticipatedFinish;
    }

    public String getActualFinish() {
        return actualFinish;
    }

    public String getReachedCondition() {
        return reachedCondition;
    }

    public String getRemark() {
        return remark;
    }

    public void setAcntRid(String acntRid) {
        this.acntRid = acntRid;
    }

    public void setWbsRid(String wbsRid) {
        this.wbsRid = wbsRid;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setAnticipatedFinish(String anticipatedFinish) {
        this.anticipatedFinish = anticipatedFinish;
    }

    public void setActualFinish(String actualFinish) {
        this.actualFinish = actualFinish;
    }

    public void setReachedCondition(String reachedCondition) {
        this.reachedCondition = reachedCondition;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
