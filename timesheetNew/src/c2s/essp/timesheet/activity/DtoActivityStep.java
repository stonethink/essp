package c2s.essp.timesheet.activity;

import c2s.dto.DtoBase;
import com.primavera.common.value.LongString;

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
public class DtoActivityStep extends DtoBase{
   private Long activityObjectId;
   private Long objectId;
   private String name;
   private Boolean isCompleted;
   private String description;
    public Long getActivityObjectId() {
        return activityObjectId;
    }

    public String getDescription() {
        return description;
    }

    public Boolean isIsCompleted() {
        return isCompleted;
    }

    public String getName() {
        return name;
    }

    public Long getObjectId() {
        return objectId;
    }

    public void setIsCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public void setActivityObjectId(Long activityObjectId) {
        this.activityObjectId = activityObjectId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }
}
