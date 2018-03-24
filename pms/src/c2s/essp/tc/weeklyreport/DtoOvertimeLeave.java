package c2s.essp.tc.weeklyreport;

public class DtoOvertimeLeave extends DtoHoursOnWeek {

    /** nullable persistent field */
    private String userId;

    /** nullable persistent field */
    private String belongTo;
    private String type;
    private String cause;
    private String comments;
    private String status;

    //当isOvertime=true时，acntRid有值
    private boolean isOvertime;
    private Long acntRid;

    public Long getAcntRid() {
        return acntRid;
    }

    public String getBelongTo() {
        return belongTo;
    }

    public String getCause() {
        return cause;
    }

    public String getComments() {
        return comments;
    }

    public boolean isIsOvertime() {
        return isOvertime;
    }

    public String getStatus() {
        return status;
    }

    public String getType() {
        return type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setIsOvertime(boolean isOvertime) {
        this.isOvertime = isOvertime;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public void setBelongTo(String belongTo) {
        this.belongTo = belongTo;
    }

    public void setAcntRid(Long acntRid) {
        this.acntRid = acntRid;
    }

    public String getCommentStatus(){
        if( status == null ){
            if( comments == null ){
                return "";
            }else{
                return comments;
            }
        }else{
            if( comments == null ){
                return status;
            }else{
                return status + "(" +comments+")";
            }
        }
    }
}
