package c2s.essp.ebs;

import java.util.Date;

import c2s.dto.DtoBase;

public class DtoPmsAcnt extends DtoBase implements IDtoEbsAcnt{

    /** identifier field */
    private Long rid;

    /** persistent field */
    private String id;

    /** nullable persistent field */
    private String name;

    /** nullable persistent field */
    private String currency;

    /** nullable persistent field */
    private String manager;

    /** nullable persistent field */
    private String type;

    /** nullable persistent field */
    private String organization;

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
    private String status;

    /** nullable persistent field */
    private String brief;


    public Long getRid() {
        return this.rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getManager() {
        return this.manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOrganization() {
        return this.organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
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

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBrief() {
        return this.brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getCode(){
        return this.id;
    }

    public void setCode( String code ){
        this.id = code;
    }


    public String toString(){
        if(this.getCode() == null ){
            return "";
        }else{
            return this.getCode();
        }
    }

    public boolean isEbs(){
        return false;
    }

    public boolean isAcnt(){
        return true;
    }
}
