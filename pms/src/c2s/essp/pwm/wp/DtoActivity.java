package c2s.essp.pwm.wp;

public class DtoActivity {

    /** identifier field */
    private Long acntRid;
    private String acntName;

    /** identifier field */
    private Long activityId;

    /** persistent field */
    private String code;

    /** nullable persistent field */
    private String name;

    /** nullable persistent field */
    private String manager;
    public Long getAcntRid() {
        return acntRid;
    }

    public String getCode() {
        return code;
    }

    public String getManager() {
        return manager;
    }

    public String getName() {
        return name;
    }

    public Long getActivityId() {
        return activityId;
    }

    public String getAcntName() {
        return acntName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setAcntRid(Long acntRid) {
        this.acntRid = acntRid;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public void setAcntName(String acntName) {
        this.acntName = acntName;
    }


}
