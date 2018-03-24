package c2s.essp.pms.pbs;

import java.io.Serializable;
import java.util.Date;

import c2s.dto.DtoBase;

/** @author Hibernate CodeGenerator */
public class DtoPmsPbs extends DtoBase implements Serializable {
    public final static int CODE_NUMBER_LENGTH = 5;

    /** identifier field */
    private Long acntRid;

    /** identifier field */
    private Long pbsRid;

    /** nullable persistent field */
    private Long pbsParentRid;

    /** nullable persistent field */
    private String productCode;

    /** persistent field */
    private String productName;

    /** nullable persistent field */
    private String pbsReferrence;

    /** nullable persistent field */
    private String pbsManager;

    /** nullable persistent field */
    private String pbsBrief;

    /** nullable persistent field */
    private Date plannedFinish;

    /** nullable persistent field */
    private Date actualFinish;

    /** nullable persistent field */
    private String pbsStatus;

    private boolean readonly=true;
    public Long getAcntRid() {
        return this.acntRid;
    }

    public void setAcntRid(Long acntRid) {
        this.acntRid = acntRid;
    }

    public Long getPbsRid() {
        return this.pbsRid;
    }

    public void setPbsRid(Long pbsRid) {
        this.pbsRid = pbsRid;
    }

    public Long getPbsParentRid() {
        return this.pbsParentRid;
    }

    public void setPbsParentRid(Long pbsParentRid) {
        this.pbsParentRid = pbsParentRid;
    }

    public String getProductCode() {
        return this.productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return this.productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPbsReferrence() {
        return this.pbsReferrence;
    }

    public void setPbsReferrence(String pbsReferrence) {
        this.pbsReferrence = pbsReferrence;
    }

    public String getPbsManager() {
        return this.pbsManager;
    }

    public void setPbsManager(String pbsManager) {
        this.pbsManager = pbsManager;
    }

    public String getPbsBrief() {
        return this.pbsBrief;
    }

    public void setPbsBrief(String pbsBrief) {
        this.pbsBrief = pbsBrief;
    }

    public Date getPlannedFinish() {
        return this.plannedFinish;
    }

    public void setPlannedFinish(Date plannedFinish) {
        this.plannedFinish = plannedFinish;
    }

    public Date getActualFinish() {
        return this.actualFinish;
    }

    public void setActualFinish(Date actualFinish) {
        this.actualFinish = actualFinish;
    }

    public String getPbsStatus() {
        return this.pbsStatus;
    }

    public void setPbsStatus(String pbsStatus) {
        this.pbsStatus = pbsStatus;
    }

    public String toString() {
        return this.getProductName();
    }

    public boolean isReadonly() {
        return readonly;
    }

    public void setReadonly(boolean readonly) {
        this.readonly = readonly;
    }

}
