package c2s.essp.timesheet.activity;

import c2s.dto.DtoBase;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author not attributable
 * @version 1.0
 */
public class DtoResourceUnits extends DtoBase {
    private Double plannedUnits;
    private Double remainingUnits;

    private Double priorActualUnits;
    private Double newRemainingUnits;
    private boolean isPrimaryResource;
    private Long resourceRid;
    private Long objectId;


    public boolean isIsPrimaryResource() {
        return isPrimaryResource;
    }

    public Long getResourceRid() {
        return resourceRid;
    }

    public Double getNewRemainingUnits() {
        return newRemainingUnits;
    }

    public Double getPlannedUnits() {
        return plannedUnits;
    }

    public Double getPriorActualUnits() {
        return priorActualUnits;
    }

    public Double getRemainingUnits() {
        return remainingUnits;
    }

    public Long getObjectId() {

        return objectId;
    }

    public void setIsPrimaryResource(boolean isPrimaryResource) {
        this.isPrimaryResource = isPrimaryResource;
    }

    public void setResourceRid(Long resourceRid) {
        this.resourceRid = resourceRid;
    }

    public void setNewRemainingUnits(Double newRemainingUnits) {
        this.newRemainingUnits = newRemainingUnits;
    }

    public void setPlannedUnits(Double plannedUnits) {
        this.plannedUnits = plannedUnits;
    }

    public void setPriorActualUnits(Double priorActualUnits) {
        this.priorActualUnits = priorActualUnits;
    }

    public void setRemainingUnits(Double remainingUnits) {
        this.remainingUnits = remainingUnits;
    }

    public void setObjectId(Long objectId) {

        this.objectId = objectId;
    }
}
