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
public class DtoActivityGeneral extends DtoBase {

    public final static String DTO_RID = "DtoActivityGeneral_rid";
    public final static String KEY_FEEDBACK = "DtoActivityGeneral_feedback";

    private Long rid;
    private String id;
    private String name;
    private boolean started;
    private boolean finished;
    private Date startDate;
    private String plannedDuration;
    private Date finishDate;
    private Date expectedFinishDate;
    private Date suspendDate;
    private Date resumeDate;
    private String wbsName;
    private String feedBack;
    private String noteTo;

    public DtoActivityGeneral() {
    }

    public Date getExpectedFinishDate() {
        return expectedFinishDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public boolean isFinished() {
        return finished;
    }

    public String getPlannedDuration() {
        return plannedDuration;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getSuspendDate() {
        return suspendDate;
    }

    public Date getResumeDate() {
        return resumeDate;
    }

    public boolean isStarted() {
        return started;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getRid() {
        return rid;
    }

    public String getWbsName() {
        return wbsName;
    }

    public String getFeedBack() {
        return feedBack;
    }

    public String getNoteTo() {
        return noteTo;
    }

    public void setResumeDate(Date resumeDate) {
        this.resumeDate = resumeDate;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setSuspendDate(Date suspendDate) {
        this.suspendDate = suspendDate;
    }

    public void setPlannedDuration(String plannedDuration) {
        this.plannedDuration = plannedDuration;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setExpectedFinishDate(Date expectedFinishDate) {
        this.expectedFinishDate = expectedFinishDate;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public void setWbsName(String wbsName) {
        this.wbsName = wbsName;
    }

    public void setFeedBack(String feedBack) {
        this.feedBack = feedBack;
    }

    public void setNoteTo(String noteTo) {
        this.noteTo = noteTo;
    }

}
