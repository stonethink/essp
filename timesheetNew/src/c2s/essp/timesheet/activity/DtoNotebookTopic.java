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
public class DtoNotebookTopic extends DtoBase{
    private Long activityObjectId;
    private String notebookTopicName;
    private String note;
    private Long objectId;

    public String getNotebookTopicName() {
        return notebookTopicName;
    }

    public Long getActivityObjectId() {
        return activityObjectId;
    }

    public Long getObjectId() {
        return objectId;
    }

    public String getNote() {
        return note;
    }

    public void setNotebookTopicName(String notebookTopicName) {
        this.notebookTopicName = notebookTopicName;
    }

    public void setActivityObjectId(Long activityObjectId) {
        this.activityObjectId = activityObjectId;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }

    public void setNote(String note) {
        this.note = note;
    }

}
