package c2s.essp.pms.wbs;

import java.util.Date;

import c2s.dto.DtoBase;

public class DtoActivityRelation extends DtoBase {
    /**
     * 关联类型的名称
     */
    public static final String[] RELATION_TYPE_NAME= {"Finish to Start",
        "Finish to Finish","Start to Finish","Start to Start"};
    /**
     * 关联类型的值
     */
    public static final String[] RELATION_TYPE_VALUE= {"FS",
        "FF","SF","SS"};
    //表前后置关系
    private Long acntRid;
    private Long activityId;
    private Long postActivityId;
    private String startFinishType;
    private Long delayDays;
    //为显示定义的冗余字段
    private String code;
    private String name;
    private String wbsName;
    private Double completeRate;
    private String manager;
    private Date plannedStart;
    private Date plannedFinish;
    private Date actualStart;
    private Date actualFinish;
    public Long getAcntRid() {
        return acntRid;
    }

    public Long getActivityId() {
        return activityId;
    }

    public Date getActualFinish() {
        return actualFinish;
    }

    public Date getActualStart() {
        return actualStart;
    }

    public String getCode() {
        return code;
    }

    public Double getCompleteRate() {
        return completeRate;
    }

    public Long getDelayDays() {
        return delayDays;
    }

    public String getManager() {
        return manager;
    }

    public String getName() {
        return name;
    }

    public Date getPlannedFinish() {
        return plannedFinish;
    }

    public Date getPlannedStart() {
        return plannedStart;
    }

    public Long getPostActivityId() {
        return postActivityId;
    }

    public String getStartFinishType() {
        return startFinishType;
    }

    public String getWbsName() {
        return wbsName;
    }

    public void setWbsName(String wbsName) {
        this.wbsName = wbsName;
    }

    public void setStartFinishType(String startFinishType) {
        this.startFinishType = startFinishType;
    }

    public void setPostActivityId(Long postActivityId) {
        this.postActivityId = postActivityId;
    }

    public void setPlannedStart(Date plannedStart) {
        this.plannedStart = plannedStart;
    }

    public void setPlannedFinish(Date plannedFinish) {
        this.plannedFinish = plannedFinish;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public void setDelayDays(Long delayDays) {
        this.delayDays = delayDays;
    }

    public void setCompleteRate(Double completeRate) {
        this.completeRate = completeRate;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setActualStart(Date actualStart) {
        this.actualStart = actualStart;
    }

    public void setActualFinish(Date actualFinish) {
        this.actualFinish = actualFinish;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public void setAcntRid(Long acntRid) {
        this.acntRid = acntRid;
    }

}
