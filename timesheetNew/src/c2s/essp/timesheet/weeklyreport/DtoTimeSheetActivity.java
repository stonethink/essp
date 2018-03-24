package c2s.essp.timesheet.weeklyreport;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import c2s.dto.DtoBase;

public class DtoTimeSheetActivity extends DtoBase {
    private Long rsrcId;
    private Long tsId;
    private Long taskrsrcId;
    private Long projId;
    private String statusCode;
    private Map<Date, DtoRsrcHour> hourMap = new HashMap<Date, DtoRsrcHour>();
    private String projectName;
    private String activityName;
    private Date planStart;
    private Date planFinish;
    public Long getProjId() {
        return projId;
    }

    public Long getRsrcId() {
        return rsrcId;
    }

    public Long getTaskrsrcId() {
        return taskrsrcId;
    }

    public Long getTsId() {
        return tsId;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public Map getHourMap() {
        return hourMap;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getActivityName() {
        return activityName;
    }

    public Date getPlanStart() {
        return planStart;
    }

    public Date getPlanFinish() {

        return planFinish;
    }

    public DtoRsrcHour getHour(Date day) {
        return hourMap.get(day);
    }

    public void setProjId(Long projId) {

        this.projId = projId;
    }

    public void setRsrcId(Long rsrcId) {
        this.rsrcId = rsrcId;
    }

    public void setTaskrsrcId(Long taskrsrcId) {
        this.taskrsrcId = taskrsrcId;
    }

    public void setTsId(Long tsId) {
        this.tsId = tsId;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public void setHourMap(Map hourMap) {
        this.hourMap = hourMap;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public void setPlanStart(Date planStart) {
        this.planStart = planStart;
    }

    public void setPlanFinish(Date planFinish) {

        this.planFinish = planFinish;
    }

    public void setHour(Date day, Double workHour) {
        DtoRsrcHour dto = hourMap.get(day);
        if(dto != null) {
            dto.setWorkHour(workHour);
        } else {
            dto = new DtoRsrcHour();
            dto.setTsId(tsId);
            dto.setProjId(projId);
            dto.setRsrcId(rsrcId);
            dto.setTaskrsrcId(taskrsrcId);
            dto.setStatusCode(statusCode);
            dto.setWorkDate(day);
            dto.setTaskTsFlag(DtoRsrcHour.TASK_TS_FLAG_FALSE);
            dto.setWorkHour(workHour);
            hourMap.put(day, dto);
        }
    }
}
