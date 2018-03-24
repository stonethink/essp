package c2s.essp.pms.account;

import java.util.Date;

import c2s.dto.DtoBase;
import db.essp.pms.MilestoneHistory;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class DtoAcntTiming extends DtoBase {

    private Long rid;

    private Long acntRid;
/** identifier field */
    private Long activityRid;

    private Long wbsAcntRid;
    /** identifier field */
    private Long wbsRid;

    private String baseLineId;
    /** persistent field */
    private String code;

    /** nullable persistent field */
    private String name;

    /** nullable persistent field */
    private String manager;

    /** nullable persistent field */
    private Double weight;

    /** nullable persistent field */
    private String brief;

    /** nullable persistent field */
    private Boolean keyPath;

    /** nullable persistent field */
    private Date anticipatedStart;

    /** nullable persistent field */
    private Date anticipatedFinish;

    /** nullable persistent field */
    private Date plannedStart;

    /** nullable persistent field */
    private Date plannedFinish;

    /** nullable persistent field */
    private Date actualStart;

    /** nullable persistent field */
    private Date actualFinish;

    /** nullable persistent field */
    private Long durationPlan;

    /** nullable persistent field */
    private Long durationActual;

    /** nullable persistent field */
    private Long durationRemain;

    /** nullable persistent field */
    private Double durationComplete;

    /** nullable persistent field */
    private Boolean start;

    /** nullable persistent field */
    private Boolean finish;

    /** nullable persistent field */
    private Double completeRate;

    /** nullable persistent field */
    private String completeMethod;

    /** nullable persistent field */
    private String etcMethod;

    /** nullable persistent field */
    private Long preRid;

    /** nullable persistent field */
    private String rst;

    /** nullable persistent field */
    private Date rct;

    /** nullable persistent field */
    private Date rut;

    private Double timeLimit;
    private String timeLimitType;
    private String isActivityQuality;
    private Boolean milestone;
    private String milestoneType;
    private String reachCondition; /** full constructor */


    private int flag=-1;
    /** default constructor */
    public DtoAcntTiming() {
    }

    public int getFlag() {
        return flag;
    }

    public Long getAcntRid() {
        return acntRid;
    }

    public Long getActivityRid() {
        return activityRid;
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

    public String getBaseLineId() {
        return baseLineId;
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
        return completeRate;
    }

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

    public String getEtcMethod() {
        return etcMethod;
    }

    public Boolean getFinish() {
        return finish;
    }

    public String getIsActivityQuality() {
        return isActivityQuality;
    }

    public Boolean getKeyPath() {
        return keyPath;
    }

    public String getManager() {
        return manager;
    }

    public Boolean getMilestone() {
        return milestone;
    }

    public String getMilestoneType() {
        return milestoneType;
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

    public Long getPreRid() {
        return preRid;
    }

    public Date getRct() {
        return rct;
    }

    public String getReachCondition() {
        return reachCondition;
    }

    public Long getRid() {
        return rid;
    }

    public String getRst() {
        return rst;
    }

    public Date getRut() {
        return rut;
    }

    public Boolean getStart() {
        return start;
    }

    public Double getTimeLimit() {
        return timeLimit;
    }

    public String getTimeLimitType() {
        return timeLimitType;
    }

    public Long getWbsAcntRid() {
        return wbsAcntRid;
    }

    public Long getWbsRid() {
        return wbsRid;
    }

    public Double getWeight() {
        return weight;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public void setWbsRid(Long wbsRid) {
        this.wbsRid = wbsRid;
    }

    public void setWbsAcntRid(Long wbsAcntRid) {
        this.wbsAcntRid = wbsAcntRid;
    }

    public void setTimeLimitType(String timeLimitType) {
        this.timeLimitType = timeLimitType;
    }

    public void setTimeLimit(Double timeLimit) {
        this.timeLimit = timeLimit;
    }

    public void setStart(Boolean start) {
        this.start = start;
    }

    public void setRut(Date rut) {
        this.rut = rut;
    }

    public void setRst(String rst) {
        this.rst = rst;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public void setReachCondition(String reachCondition) {
        this.reachCondition = reachCondition;
    }

    public void setRct(Date rct) {
        this.rct = rct;
    }

    public void setPreRid(Long preRid) {
        this.preRid = preRid;
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

    public void setMilestoneType(String milestoneType) {
        this.milestoneType = milestoneType;
    }

    public void setMilestone(Boolean milestone) {
        this.milestone = milestone;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public void setKeyPath(Boolean keyPath) {
        this.keyPath = keyPath;
    }

    public void setIsActivityQuality(String isActivityQuality) {
        this.isActivityQuality = isActivityQuality;
    }

    public void setFinish(Boolean finish) {
        this.finish = finish;
    }

    public void setEtcMethod(String etcMethod) {
        this.etcMethod = etcMethod;
    }

    public void setDurationRemain(Long durationRemain) {
        this.durationRemain = durationRemain;
    }

    public void setDurationPlan(Long durationPlan) {
        this.durationPlan = durationPlan;
    }

    public void setDurationComplete(Double durationComplete) {
        this.durationComplete = durationComplete;
    }

    public void setDurationActual(Long durationActual) {
        this.durationActual = durationActual;
    }

    public void setCompleteRate(Double completeRate) {
        this.completeRate = completeRate;
    }

    public void setCompleteMethod(String completeMethod) {
        this.completeMethod = completeMethod;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public void setBaseLineId(String baseLineId) {
        this.baseLineId = baseLineId;
    }

    public void setAnticipatedStart(Date anticipatedStart) {
        this.anticipatedStart = anticipatedStart;
    }

    public void setAnticipatedFinish(Date anticipatedFinish) {
        this.anticipatedFinish = anticipatedFinish;
    }

    public void setActualStart(Date actualStart) {
        this.actualStart = actualStart;
    }

    public void setActualFinish(Date actualFinish) {
        this.actualFinish = actualFinish;
    }

    public void setActivityRid(Long activityRid) {
        this.activityRid = activityRid;
    }

    public void setAcntRid(Long acntRid) {
        this.acntRid = acntRid;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("rid", getRid())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof MilestoneHistory) ) return false;
        DtoAcntTiming castOther = (DtoAcntTiming) other;
        return new EqualsBuilder()
            .append(this.getRid(), castOther.getRid())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getRid())
            .toHashCode();
    }

}
