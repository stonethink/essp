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
public class DtoResourceDetail extends DtoBase{
   private Long resourceObjectId;
   private String resourceName;
   private String officePhone;
   private String email;
   private String roleName;
   private boolean isPrimaryResource;
   private boolean isResourceType;

    public String getEmail() {
        return email;
    }

    public boolean isIsPrimaryResource() {
        return isPrimaryResource;
    }

    public boolean isIsResourceType() {
        return isResourceType;
    }

    public String getOfficePhone() {
        return officePhone;
    }

    public String getResourceName() {
        return resourceName;
    }

    public Long getResourceObjectId() {
        return resourceObjectId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public void setOfficePhone(String officePhone) {
        this.officePhone = officePhone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setIsPrimaryResource(boolean isPrimaryResource) {
        this.isPrimaryResource = isPrimaryResource;
    }

    public void setIsResourceType(boolean isResourceType) {
        this.isResourceType = isResourceType;
    }

    public void setResourceObjectId(Long resourceObjectId) {
        this.resourceObjectId = resourceObjectId;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
