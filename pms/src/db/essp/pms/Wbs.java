package db.essp.pms;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class Wbs implements Serializable {

    /** identifier field */
    private db.essp.pms.WbsPK pk;

    /** nullable persistent field */
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
    private Double completeRate;

    /** nullable persistent field */
    private String completeMethod;

    /** nullable persistent field */
    private String ectMethod;

    /** nullable persistent field */
    private String rst;

    /** nullable persistent field */
    private Date rct;

    /** nullable persistent field */
    private Date rut;

    /** nullable persistent field */
//    private db.essp.pms.Milestone milestoneData;

    /** nullable persistent field */
    private db.essp.pms.Account account;

    /** nullable persistent field */
    private db.essp.pms.Wbs parent;

    /** persistent field */
    private List childs;

    /** persistent field */
    private List activities;

    /** persistent field */
    private Set wbsCodes;

    /** full constructor */
    public Wbs(db.essp.pms.WbsPK pk, String code, String name, String manager, Double weight, String brief, Date anticipatedStart, Date anticipatedFinish, Date plannedStart, Date plannedFinish, Date actualStart, Date actualFinish, Double completeRate, String completeMethod, String ectMethod, String rst, Date rct, Date rut, db.essp.pms.Account account, db.essp.pms.Wbs parent, List childs, List activities, Set wbsCodes) {
        this.pk = pk;
        this.code = code;
        this.name = name;
        this.manager = manager;
        this.weight = weight;
        this.brief = brief;
        this.anticipatedStart = anticipatedStart;
        this.anticipatedFinish = anticipatedFinish;
        this.plannedStart = plannedStart;
        this.plannedFinish = plannedFinish;
        this.actualStart = actualStart;
        this.actualFinish = actualFinish;
        this.completeRate = completeRate;
        this.completeMethod = completeMethod;
        this.ectMethod = ectMethod;
        this.rst = rst;
        this.rct = rct;
        this.rut = rut;
//        this.milestoneData = milestoneData;
        this.account = account;
        this.parent = parent;
        this.childs = childs;
        this.activities = activities;
        this.wbsCodes = wbsCodes;
    }

    /** default constructor */
    public Wbs() {
    }

    /** minimal constructor */
    public Wbs(db.essp.pms.WbsPK pk, List childs, List activities, Set wbsCodes) {
        this.pk = pk;
        this.childs = childs;
        this.activities = activities;
        this.wbsCodes = wbsCodes;
    }

    public db.essp.pms.WbsPK getPk() {
        return this.pk;
    }

    public void setPk(db.essp.pms.WbsPK pk) {
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

    public String getEctMethod() {
        return this.ectMethod;
    }

    public void setEctMethod(String ectMethod) {
        this.ectMethod = ectMethod;
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

//    public db.essp.pms.Milestone getMilestoneData() {
//        return this.milestoneData;
//    }
//
//    public void setMilestoneData(db.essp.pms.Milestone milestoneData) {
//        this.milestoneData = milestoneData;
//    }

    public db.essp.pms.Account getAccount() {
        return this.account;
    }

    public void setAccount(db.essp.pms.Account account) {
        this.account = account;
    }

    public db.essp.pms.Wbs getParent() {
        return this.parent;
    }

    public void setParent(db.essp.pms.Wbs parent) {
        this.parent = parent;
    }

    public List getChilds() {
        return this.childs;
    }

    public void setChilds(List childs) {
        this.childs = childs;
    }

    public List getActivities() {
        return this.activities;
    }

    public void setActivities(List activities) {
        this.activities = activities;
    }

    public Set getWbsCodes() {
        return this.wbsCodes;
    }

    public void setWbsCodes(Set wbsCodes) {
        this.wbsCodes = wbsCodes;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("pk", getPk())
            .toString();
    }

}
