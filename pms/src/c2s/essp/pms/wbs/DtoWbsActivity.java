package c2s.essp.pms.wbs;

import java.io.Serializable;
import java.util.Date;

import c2s.dto.DtoBase;
import c2s.essp.common.account.IAccountModel;
import c2s.essp.common.calendar.WorkCalendar;

public class DtoWbsActivity extends DtoBase implements IDtoWbsActivity,
    IAccountModel, Serializable {
    //// 共通参数区 /////////////////////////////////////////////////////////
    public final static String WBS_COMPLETE_BY_ACTIVITY = "ACTIVITY"; //1.	依据下级权重计算：WBS下各Activity或WBS*完成率*权重之和/100*权重之和。
    public final static String WBS_COMPLETE_BY_CHECKPOINT = "CHECKPOINT"; //2.	按Checkpoint计算：依据WBS下的完成的Checkpoint的权重之和/所有Checkpoint的权重之和
    public final static String WBS_COMPLETE_BY_MANUAL = "MANUAL"; //3.	手动填写WBS完成率

    public final static String ACTIVITY_COMPLETE_BY_WP = "WP"; //1.	依据WP的完工状况计算：汇总WP的权重*WP完成率/所有WP的权重之和
    public final static String ACTIVITY_COMPLETE_BY_DURATION = "DURATION"; //2.	依据工期的完成状况计算：即按工期（Duration）的执行占所有工期的比例为完成比例
    public final static String ACTIVITY_COMPLETE_BY_0_100 = "0_100"; //3.	0/100：Activity完成时间小于等于当天时，Activity完成率为100%，否则Activity完成率为0%
    public final static String ACTIVITY_COMPLETE_BY_50_50 = "50_50"; //4.	50/50:Activity开始做，完成率是50%，当Activity完成时间小于等于当天时，完成率为100%
    //public final static String ACTIVITY_COMPLETE_BY_CHECKPOINT = "CHECKPOINT"; //5.	按Checkpoint计算：依据Activity下的完成的Checkpoint的权重之和/所有Checkpoint的权重之和
    public final static String ACTIVITY_COMPLETE_BY_MANUAL = "MANUAL"; //6.	手动填写Activity完成率

    public final static String ACTIVITY_COMPLETE_BY_WP_LABEL = "WP";
    public final static String ACTIVITY_COMPLETE_BY_DURATION_LABEL = "Duration";
    public final static String ACTIVITY_COMPLETE_BY_0_100_LABEL = "0/100";
    public final static String ACTIVITY_COMPLETE_BY_50_50_LABEL = "50/50";
    public final static String ACTIVITY_COMPLETE_BY_MANUAL_LABEL =
        "Custom Percent Complete";
    public final static String[] ACTIVITY_TIME_LIMIT_TYPE = {"FLOATING",
        "CHANGELESS"}; //Activity的工期类型:非固定，固定
    public final static String[] ACTIVITY_COMPLETE_METHOD = {
        DtoWbsActivity.ACTIVITY_COMPLETE_BY_WP,
        DtoWbsActivity.ACTIVITY_COMPLETE_BY_DURATION,
        DtoWbsActivity.ACTIVITY_COMPLETE_BY_0_100,
        DtoWbsActivity.ACTIVITY_COMPLETE_BY_50_50,
        DtoWbsActivity.ACTIVITY_COMPLETE_BY_MANUAL};
    public final static String[] ACTIVITY_COMPLETE_METHOD_TITLE = {
        DtoWbsActivity.ACTIVITY_COMPLETE_BY_WP_LABEL,
        DtoWbsActivity.ACTIVITY_COMPLETE_BY_DURATION_LABEL,
        DtoWbsActivity.ACTIVITY_COMPLETE_BY_0_100_LABEL,
        DtoWbsActivity.ACTIVITY_COMPLETE_BY_50_50_LABEL,
        DtoWbsActivity.ACTIVITY_COMPLETE_BY_MANUAL_LABEL};

    private boolean hasActivity = false;
    private boolean readonly = false;
    private boolean wbs = false;
    private Long acntRid;
    private String acntCode;
    private String acntName;
    private Long wbsRid;
    private String code;
    private String autoCode;
    private String name;
    private String manager;
    private Double weight;
    private String brief;
    private Date anticipatedStart;
    private Date anticipatedFinish;
    private Date plannedStart;
    private Date plannedFinish;
    private Date actualStart;
    private Date actualFinish;
    private Double completeRate;
    private String completeMethod;
    private String ectMethod;
    private String rst;
    private Date rct;
    private Date rut;
    private Double timeLimit;
    public DtoWbsActivity() {
        setOp(OP_NOCHANGE);
    }

    public Long getAcntRid() {
        return acntRid;
    }

    public String getAcntCode() {
        return acntCode;
    }

    public void setAcntCode(String acntCode) {
        this.acntCode = acntCode;
    }

    public Date getActualFinish() {
        return actualFinish;
    }

    public Date getActualStart() {
        return actualStart;
    }

    public Date getAnticipatedFinish() {
        return anticipatedFinish;
    }

    public Date getAnticipatedStart() {
        return anticipatedStart;
    }

    public String getBrief() {
        return brief;
    }

    public String getCode() {
        return code;
    }

    public String getCompleteMethod() {
        return completeMethod;
    }

    public Double getCompleteRate() {
        if (completeRate == null) {
            return new Double("0");
        }
        return completeRate;
    }

    public String getEctMethod() {
        return ectMethod;
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

    public Date getRct() {
        return rct;
    }

    public String getRst() {
        return rst;
    }

    public Date getRut() {
        return rut;
    }

    public Long getWbsRid() {
        return wbsRid;
    }

    public Double getWeight() {
        return weight;
    }

    public void setAcntRid(Long acntRid) {
        this.acntRid = acntRid;
    }

    public void setActualFinish(Date actualFinish) {
        this.actualFinish = actualFinish;
    }

    public void setActualStart(Date actualStart) {
        this.actualStart = actualStart;
    }

    public void setAnticipatedFinish(Date anticipatedFinish) {
        this.anticipatedFinish = anticipatedFinish;
    }

    public void setAnticipatedStart(Date anticipatedStart) {
        this.anticipatedStart = anticipatedStart;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setCompleteMethod(String completeMethod) {
        this.completeMethod = completeMethod;
    }

    public void setCompleteRate(Double completeRate) {
        this.completeRate = completeRate;
    }

    public void setEctMethod(String ectMethod) {
        this.ectMethod = ectMethod;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPlannedFinish(Date plannedFinish) {
        this.plannedFinish = plannedFinish;
    }

    public void setPlannedStart(Date plannedStart) {
        this.plannedStart = plannedStart;
    }

    public void setRct(Date rct) {
        this.rct = rct;
    }

    public void setRst(String rst) {
        this.rst = rst;
    }

    public void setRut(Date rut) {
        this.rut = rut;
    }

    public void setWbsRid(Long wbsRid) {
        this.wbsRid = wbsRid;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public boolean isWbs() {
        return wbs;
    }

    public boolean isReadonly() {
        return readonly;
    }

    public boolean isActivity() {
        return!wbs;
    }

    public void setWbs(boolean iswbs) {
        wbs = iswbs;
    }

    public void setReadonly(boolean readonly) {
        this.readonly = readonly;
    }

    public void setActivity(boolean isactivity) {
        wbs = !isactivity;
    }

    public String toString() {
        return this.name;
    }

    ////Activity 参数专属区 //////////////////////////////////////////////////
    private Long activityRid;
    private Long durationPlan;
    private Long durationActual;
    private Long durationRemain;
    private Double durationComplete;
    private Boolean start;
    private Boolean keyPath;
    private Boolean finish;
    private String timeLimitType;
    private Boolean milestone;
    private String milestoneType;
    private String reachCondition;

    public Long getDurationActual() {
        return durationActual;
    }

    public Double getDurationComplete() {
        return durationComplete;
    }

    public Long getDurationPlan() {
        return durationPlan;
    }

    public Long getDurationRemain() {
        return durationRemain;
    }

    public Long getActivityRid() {
        return activityRid;
    }

    public Boolean isFinish() {
        return finish;
    }

    public Boolean isKeyPath() {
        return keyPath;
    }

    public Boolean isStart() {
        return start;
    }

    public void setDurationActual(Long durationActual) {
        this.durationActual = durationActual;
    }

    public void setDurationComplete(Double durationComplete) {
        this.durationComplete = durationComplete;
    }

    public void setDurationPlan(Long durationPlan) {
        this.durationPlan = durationPlan;
    }

    public void setDurationRemain(Long durationRemain) {
        this.durationRemain = durationRemain;
    }

    public void setFinish(Boolean finish) {
        this.finish = finish;
    }

    public void setKeyPath(Boolean keyPath) {
        this.keyPath = keyPath;
    }

    public void setStart(Boolean start) {
        this.start = start;
    }

    public void setActivityRid(Long activityRid) {
        this.activityRid = activityRid;
    }

    //// Wbs 参数专属区 /////////////////////////////////////////////////////
    private Long parentRid;
    private String isActivityQuality;


    public Boolean isMilestone() {
        return milestone == null ? Boolean.FALSE : milestone;
    }

    public Long getParentRid() {
        return parentRid;
    }

    public String getAutoCode() {
        return autoCode;
    }

    public String getAcntName() {
        return acntName;
    }

    //Time Limit 的计算方法为：由计划时间得到工期，默认为0
    public Double getTimeLimit() {
        Double result;
        if (this.getPlannedStart() == null || this.getPlannedFinish() == null) {
            result = new Double(0);
        } else {
            result = WorkCalendar.calculateTimeLimit(this.
                getPlannedStart(), this.getPlannedFinish());
        }
        return result;
    }

    public String getTimeLimitType() {
        if (this.timeLimitType == null || this.timeLimitType.length() < 1) {
            return this.ACTIVITY_TIME_LIMIT_TYPE[0];
        }
        return timeLimitType;
    }

    public String getReachCondition() {
        return reachCondition;
    }

    public String getMilestoneType() {
        return milestoneType;
    }

    public String getIsActivityQuality() {
        return isActivityQuality;
    }

    public void setMilestone(Boolean milestone) {
        this.milestone = milestone;
    }

    public void setParentRid(Long parentRid) {
        this.parentRid = parentRid;
    }

    public void setAutoCode(String autoCode) {
        this.autoCode = autoCode;
    }

    public boolean equals(Object other) {
        if (other == null || !(other instanceof DtoWbsActivity)) {
            return false;
        }
        DtoWbsActivity otherDto = (DtoWbsActivity) other;
        if (this.isWbs() && otherDto.isWbs()) {
            if (otherDto.getWbsRid() != null && otherDto.getAcntRid() != null &&
                this.getWbsRid() != null && this.getAcntRid() != null) {
                if (otherDto.getWbsRid().equals(this.getWbsRid()) &&
                    otherDto.getAcntRid().equals(this.getAcntRid())) {
                    return true;
                }
            }
        }
        if (this.isActivity() && otherDto.isActivity()) {
            if (otherDto.getActivityRid() != null && otherDto.getAcntRid() != null &&
                this.getActivityRid() != null && this.getAcntRid() != null) {
                if (otherDto.getActivityRid().equals(this.getActivityRid()) &&
                    otherDto.getAcntRid().equals(this.getAcntRid())) {
                    return true;
                }
            }
        }

        if (this.toString().equals(other.toString())) {
            return true;
        }
        return false;
    }

    public boolean hasActivity() {
        return this.hasActivity;
    }

    public void setHasActivity(boolean hasActivity) {
        this.hasActivity = hasActivity;
    }

    public void setAcntName(String acntName) {
        this.acntName = acntName;
    }

    public void setTimeLimit(Double timeLimit) {
        this.timeLimit = timeLimit;
    }

    public void setTimeLimitType(String timeLimitType) {
        this.timeLimitType = timeLimitType;
    }

    public void setReachCondition(String reachCondition) {
        this.reachCondition = reachCondition;
    }

    public void setMilestoneType(String milestoneType) {
        this.milestoneType = milestoneType;
    }

    public void setIsActivityQuality(String isActivityQuality) {
        this.isActivityQuality = isActivityQuality;
    }

    public String getAccountCode() {
        return this.acntCode;
    }

    public void setAccountCode(String accountCode) {
        this.acntCode = accountCode;
    }

    public String getAccountName() {
        return this.acntName;
    }

    public void setAccountName(String accountName) {
        this.acntName = accountName;
    }

}
