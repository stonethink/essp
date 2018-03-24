package db.essp.pms;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class Activity implements Serializable {

    /** identifier field */
    private db.essp.pms.ActivityPK pk;

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

    /** nullable persistent field */
    private db.essp.pms.Wbs wbs;

    /** persistent field */
    private Set activityRelations;

    /** persistent field */
    private Set activityCodes;
    private Double timeLimit;
    private String timeLimitType;
    private String isActivityQuality;
    private Boolean milestone;
    private String milestoneType;
    private String reachCondition;
    private db.essp.pms.ActivityQuality activityQuality;

    /** full constructor */
    public Activity(db.essp.pms.ActivityPK pk, String code,
                    String name, String manager, Double weight, String brief,
                    Boolean keyPath, Date anticipatedStart,
                    Date anticipatedFinish, Date plannedStart,
                    Date plannedFinish, Date actualStart, Date actualFinish,
                    Long durationPlan, Long durationActual, Long durationRemain,
                    Double durationComplete, Boolean start, Boolean finish,
                    Double completeRate, String completeMethod,
                    String etcMethod, Long preRid, String rst, Date rct,
                    Date rut, db.essp.pms.Wbs wbs, Set activityRelations,
                    Set activityCodes, Double timeLimit, String timeLimitType,
                    ActivityQuality activityQuality) {
        this.pk = pk;
        this.code = code;
        this.name = name;
        this.manager = manager;
        this.weight = weight;
        this.brief = brief;
        this.keyPath = keyPath;
        this.anticipatedStart = anticipatedStart;
        this.anticipatedFinish = anticipatedFinish;
        this.plannedStart = plannedStart;
        this.plannedFinish = plannedFinish;
        this.actualStart = actualStart;
        this.actualFinish = actualFinish;
        this.durationPlan = durationPlan;
        this.durationActual = durationActual;
        this.durationRemain = durationRemain;
        this.durationComplete = durationComplete;
        this.start = start;
        this.finish = finish;
        this.completeRate = completeRate;
        this.completeMethod = completeMethod;
        this.etcMethod = etcMethod;
        this.preRid = preRid;
        this.rst = rst;
        this.rct = rct;
        this.rut = rut;
        this.wbs = wbs;
        this.activityRelations = activityRelations;
        this.activityCodes = activityCodes;
        this.timeLimit = timeLimit;
        this.timeLimitType = timeLimitType;
        this.activityQuality = activityQuality;
    }

    /** default constructor */
    public Activity() {
    }

    /** minimal constructor */
    public Activity(db.essp.pms.ActivityPK pk, String code,
                    Set activityRelations, Set activityCodes) {
        this.pk = pk;
        this.code = code;
        this.activityRelations = activityRelations;
        this.activityCodes = activityCodes;
    }

    public db.essp.pms.ActivityPK getPk() {
        return this.pk;
    }

    public void setPk(db.essp.pms.ActivityPK pk) {
        this.pk = pk;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManager() {
        return this.manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public Double getWeight() {
        return this.weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getBrief() {
        return this.brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public Boolean getKeyPath() {
        return this.keyPath;
    }

    public void setKeyPath(Boolean keyPath) {
        this.keyPath = keyPath;
    }

    public Date getAnticipatedStart() {
        return this.anticipatedStart;
    }

    public void setAnticipatedStart(Date anticipatedStart) {
        this.anticipatedStart = anticipatedStart;
    }

    public Date getAnticipatedFinish() {
        return this.anticipatedFinish;
    }

    public void setAnticipatedFinish(Date anticipatedFinish) {
        this.anticipatedFinish = anticipatedFinish;
    }

    public Date getPlannedStart() {
        return this.plannedStart;
    }

    public void setPlannedStart(Date plannedStart) {
        this.plannedStart = plannedStart;
    }

    public Date getPlannedFinish() {
        return this.plannedFinish;
    }

    public void setPlannedFinish(Date plannedFinish) {
        this.plannedFinish = plannedFinish;
    }

    public Date getActualStart() {
        return this.actualStart;
    }

    public void setActualStart(Date actualStart) {
        this.actualStart = actualStart;
    }

    public Date getActualFinish() {
        return this.actualFinish;
    }

    public void setActualFinish(Date actualFinish) {
        this.actualFinish = actualFinish;
    }

    public Long getDurationPlan() {
        return this.durationPlan;
    }

    public void setDurationPlan(Long durationPlan) {
        this.durationPlan = durationPlan;
    }

    public Long getDurationActual() {
        return this.durationActual;
    }

    public void setDurationActual(Long durationActual) {
        this.durationActual = durationActual;
    }

    public Long getDurationRemain() {
        return this.durationRemain;
    }

    public void setDurationRemain(Long durationRemain) {
        this.durationRemain = durationRemain;
    }

    public Double getDurationComplete() {
        return this.durationComplete;
    }

    public void setDurationComplete(Double durationComplete) {
        this.durationComplete = durationComplete;
    }

    public Boolean getStart() {
        return this.start;
    }

    public void setStart(Boolean start) {
        this.start = start;
    }

    public Boolean getFinish() {
        return this.finish;
    }

    public void setFinish(Boolean finish) {
        this.finish = finish;
    }

    public Double getCompleteRate() {
        return this.completeRate;
    }

    public void setCompleteRate(Double completeRate) {
        this.completeRate = completeRate;
    }

    public String getCompleteMethod() {
        return this.completeMethod;
    }

    public void setCompleteMethod(String completeMethod) {
        this.completeMethod = completeMethod;
    }

    public String getEtcMethod() {
        return this.etcMethod;
    }

    public void setEtcMethod(String etcMethod) {
        this.etcMethod = etcMethod;
    }

    public Long getPreRid() {
        return this.preRid;
    }

    public void setPreRid(Long preRid) {
        this.preRid = preRid;
    }

    public String getRst() {
        return this.rst;
    }

    public void setRst(String rst) {
        this.rst = rst;
    }

    public Date getRct() {
        return this.rct;
    }

    public void setRct(Date rct) {
        this.rct = rct;
    }

    public Date getRut() {
        return this.rut;
    }

    public void setRut(Date rut) {
        this.rut = rut;
    }

    public db.essp.pms.Wbs getWbs() {
        return this.wbs;
    }

    public void setWbs(db.essp.pms.Wbs wbs) {
        this.wbs = wbs;
    }

    public Set getActivityRelations() {
        return this.activityRelations;
    }

    public void setActivityRelations(Set activityRelations) {
        this.activityRelations = activityRelations;
    }

    public Set getActivityCodes() {
        return this.activityCodes;
    }

    public Double getTimeLimit() {
        return timeLimit;
    }

    public String getTimeLimitType() {
        return timeLimitType;
    }

    public String getIsActivityQuality() {
        return isActivityQuality;
    }

    public Boolean getMilestone() {
        return milestone;
    }

    public String getMilestoneType() {
        return milestoneType;
    }

    public String getReachCondition() {
        return reachCondition;
    }

    public ActivityQuality getActivityQuality() {
        return activityQuality;
    }

    public void setActivityCodes(Set activityCodes) {
        this.activityCodes = activityCodes;
    }

    public void setTimeLimit(Double timeLimit) {
        this.timeLimit = timeLimit;
    }

    public void setTimeLimitType(String timeLimitType) {
        this.timeLimitType = timeLimitType;
    }

    public void setIsActivityQuality(String isActivityQuality) {
        this.isActivityQuality = isActivityQuality;
    }

    public void setMilestone(Boolean milestone) {
        this.milestone = milestone;
    }

    public void setMilestoneType(String milestoneType) {
        this.milestoneType = milestoneType;
    }

    public void setReachCondition(String reachCondition) {
        this.reachCondition = reachCondition;
    }

    public void setActivityQuality(ActivityQuality activityQuality) {
        this.activityQuality = activityQuality;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("pk", getPk())
            .toString();
    }

}
