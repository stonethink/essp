package db.essp.pms;

public class QualityActivityPK {
    public ActivityPK pk;

    public Long acntRid;

    public Long activityId;

    public QualityActivityPK() {
    }

    public void setPk(ActivityPK pk) {
        this.pk = pk;
    }

    public void setAcntRid(Long acntRid) {
        this.acntRid = acntRid;

    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;

    }

    public ActivityPK getPk() {
        return pk;
    }

    public Long getAcntRid() {
        return acntRid;
    }

    public Long getActivityId() {
        return activityId;
    }
}
