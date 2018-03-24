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
public class DtoRelationShips extends DtoBase{
    private Long activityObjectId;
    private String activityId;
    private String activityName;
    private String projectId;
    private String type;
    private Double lag;
    public Double getLag() {
        return lag;
    }


    public String getType() {
        return type;
    }

    public String getProjectId() {
        return projectId;
    }

    public Long getActivityObjectId() {
        return activityObjectId;
    }

    public String getActivityName() {
        return activityName;
    }

    public String getActivityId() {
        return activityId;
    }


    public void setLag(Double lag) {
        this.lag = lag;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public void setActivityObjectId(Long activityObjectId) {
        this.activityObjectId = activityObjectId;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }


}
