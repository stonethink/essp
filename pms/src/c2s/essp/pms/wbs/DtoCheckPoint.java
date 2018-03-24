package c2s.essp.pms.wbs;

import java.util.Date;

import c2s.dto.DtoBase;

public class DtoCheckPoint extends DtoBase {
    public static final String UNCOMPLETED = "N";
    public static final String COMPLETED = "Y";

    private Long acntRid;

    private Long wbsRid;

    private Long rid;

    private String name;
    /** nullable persistent field */
    private Double weight;

    /** nullable persistent field */
    private Date dueDate;

    /** nullable persistent field */
    private Date finishDate;

    /** nullable persistent field */
    private String completed;

    /** nullable persistent field */
    private String remark;
    public String getCompleted() {
        return completed;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public String getName() {
        return name;
    }

    public String getRemark() {
        return remark;
    }

    public Double getWeight() {
        return weight;
    }

    public Long getWbsRid() {
        return wbsRid;
    }

    public Long getRid() {
        return rid;
    }

    public Long getAcntRid() {
        return acntRid;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public void setCompleted(String completed) {
        this.completed = completed;
    }

    public void setWbsRid(Long wbsRid) {
        this.wbsRid = wbsRid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public void setAcntRid(Long acntRid) {
        this.acntRid = acntRid;
    }

}
