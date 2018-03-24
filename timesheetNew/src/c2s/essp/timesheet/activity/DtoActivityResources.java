package c2s.essp.timesheet.activity;

import java.util.Date;

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
public class DtoActivityResources {
    private String name;
    private String id;
    private String remainingUnits;
    private String roleName;
    //private String primary; meizhaodao
    private String resourceType;
    private Date startDate;
    private Date finishDate;
    public DtoActivityResources() {
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public String getId() {
        return id;
    }

    public String getRemainingUnits() {
        return remainingUnits;
    }

    public String getResourceType() {
        return resourceType;
    }

    public String getRoleName() {
        return roleName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public String getName() {
        return name;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public void setId(String id) {
        this.id = id;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }


}
