package db.essp.pms;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class Account implements Serializable {

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

    /** nullable persistent field */
    private String inner;

    /** nullable persistent field */
    private String rst;

    /** nullable persistent field */
    private Date rct;

    /** nullable persistent field */
    private Date rut;

    private Boolean isTemplate;

    private Long importTemplateRid;
    /** persistent field */
    private Set allwbs;

    /** persistent field */
    private Set ebss;

    /** persistent field */
    private Set laborResources;

    /** persistent field */
    private Set keyPersons;

    /** persistent field */
    private Set noneLaborResources;

    /** persistent field */
    private Set acntCodes;
    private String acntTailor;

    /** full constructor */
    public Account(Long rid, String id, String name, String currency, String manager, String type, String organization, Date anticipatedStart, Date anticipatedFinish, Date plannedStart, Date plannedFinish, Date actualStart, Date actualFinish, String status, String brief, String inner, String rst, Date rct, Date rut, Set allwbs, Set ebss, Set laborResources, Set keyPersons, Set noneLaborResources, Set acntCodes) {
        this.rid = rid;
        this.id = id;
        this.name = name;
        this.currency = currency;
        this.manager = manager;
        this.type = type;
        this.organization = organization;
        this.anticipatedStart = anticipatedStart;
        this.anticipatedFinish = anticipatedFinish;
        this.plannedStart = plannedStart;
        this.plannedFinish = plannedFinish;
        this.actualStart = actualStart;
        this.actualFinish = actualFinish;
        this.status = status;
        this.brief = brief;
        this.inner = inner;
        this.rst = rst;
        this.rct = rct;
        this.rut = rut;
        this.allwbs = allwbs;
        this.ebss = ebss;
        this.laborResources = laborResources;
        this.keyPersons = keyPersons;
        this.noneLaborResources = noneLaborResources;
        this.acntCodes = acntCodes;
    }

    /** default constructor */
    public Account() {
    }

    /** minimal constructor */
    public Account(Long rid, String id, Set allwbs, Set ebss, Set laborResources, Set keyPersons, Set noneLaborResources, Set acntCodes) {
        this.rid = rid;
        this.id = id;
        this.allwbs = allwbs;
        this.ebss = ebss;
        this.laborResources = laborResources;
        this.keyPersons = keyPersons;
        this.noneLaborResources = noneLaborResources;
        this.acntCodes = acntCodes;
    }

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

    public String getInner() {
        return this.inner;
    }

    public void setInner(String inner) {
        this.inner = inner;
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

    public Set getAllwbs() {
        return this.allwbs;
    }

    public void setAllwbs(Set allwbs) {
        this.allwbs = allwbs;
    }

    public Set getEbss() {
        return this.ebss;
    }

    public void setEbss(Set ebss) {
        this.ebss = ebss;
    }

    public Set getLaborResources() {
        return this.laborResources;
    }

    public void setLaborResources(Set laborResources) {
        this.laborResources = laborResources;
    }

    public Set getKeyPersons() {
        return this.keyPersons;
    }

    public void setKeyPersons(Set keyPersons) {
        this.keyPersons = keyPersons;
    }

    public Set getNoneLaborResources() {
        return this.noneLaborResources;
    }

    public void setNoneLaborResources(Set noneLaborResources) {
        this.noneLaborResources = noneLaborResources;
    }

    public Set getAcntCodes() {
        return this.acntCodes;
    }

    public Boolean getIsTemplate() {
        if(isTemplate == null) {
            return false;
        }
        return isTemplate;
    }

    public Long getImportTemplateRid() {
        return importTemplateRid;
    }

    public String getAcntTailor() {
        return acntTailor;
    }

    public void setAcntCodes(Set acntCodes) {
        this.acntCodes = acntCodes;
    }

    public void setIsTemplate(Boolean isTemplate) {
        this.isTemplate = isTemplate;
    }

    public void setImportTemplateRid(Long importTemplateRid) {
        this.importTemplateRid = importTemplateRid;
    }

    public void setAcntTailor(String acntTailor) {
        this.acntTailor = acntTailor;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("rid", getRid())
            .toString();
    }

}
