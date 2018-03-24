package c2s.essp.timesheet.activity;

import java.util.Date;

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
public class DtoResourceAssignment extends DtoBase {
    private Long activityObjectId;
    private Long ResourceObjectId;
    private String resourceName;
    private String resourceId;
    private String remainingUnits;
    private String roleId;
    private boolean isPrimaryResource;
    private String resourceType;
    private Date startDate;
    private Date finishDate;
    private String primary;
    private Long objectId;

    public DtoResourceAssignment() {
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public String getRemainingUnits() {
        return remainingUnits;
    }

    public String getResourceType() {
        return resourceType;
    }

    public Date getStartDate() {
        return startDate;
    }

    public String getResourceName() {
        return resourceName;
    }

    public String getResourceId() {
        return resourceId;
    }

    public Long getActivityObjectId() {
        return activityObjectId;
    }

    public boolean isIsPrimaryResource() {
        return isPrimaryResource;
    }

    public String getPrimary() {
        return primary;
    }

    public String getRoleId() {
        return roleId;
    }

    public Long getObjectId() {
        return objectId;
    }

    public Long getResourceObjectId() {
        return ResourceObjectId;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public void setRemainingUnits(String remainingUnits) {
        this.remainingUnits = remainingUnits;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public void setActivityObjectId(Long activityObjectId) {
        this.activityObjectId = activityObjectId;
    }

    public void setIsPrimaryResource(boolean isPrimaryResource) {
        this.isPrimaryResource = isPrimaryResource;
    }

    public void setPrimary(String primary) {
        this.primary = primary;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }

    public void setResourceObjectId(Long ResourceObjectId) {
        this.ResourceObjectId = ResourceObjectId;
    }


}
