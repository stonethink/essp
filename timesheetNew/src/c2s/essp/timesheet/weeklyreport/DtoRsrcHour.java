package c2s.essp.timesheet.weeklyreport;

import java.util.Date;

public class DtoRsrcHour extends c2s.dto.DtoBase {

    public final static String TASK_TS_FLAG_TRUE = "Y";
    public final static String TASK_TS_FLAG_FALSE = "N";

    private Long rsrcHrId;
    private Long rsrcId;
    private Long tsId;
    private Long taskrsrcId;
    private String taskTsFlag;
    private Long projId;
    private Double pendHrCnt;
    private Double hrCnt;
    private Double pendOtHrCnt;
    private Double otHrCnt;
    private Date workDate;
    private String statusCode;
    private Double workHour;

    public Long getRsrcHrId() {
        return this.rsrcHrId;
    }

    public void setRsrcHrId(Long rsrcHrId) {
        this.rsrcHrId = rsrcHrId;
    }

    public Long getRsrcId() {

        return rsrcId;
    }

    public void setRsrcId(Long rsrcId) {

        this.rsrcId = rsrcId;
    }

    public Long getTsId() {

        return tsId;
    }

    public void setTsId(Long tsId) {

        this.tsId = tsId;
    }

    public Long getTaskrsrcId() {

        return taskrsrcId;
    }

    public void setTaskrsrcId(Long taskrsrcId) {

        this.taskrsrcId = taskrsrcId;
    }

    public String getTaskTsFlag() {
        return this.taskTsFlag;
    }

    public void setTaskTsFlag(String taskTsFlag) {
        this.taskTsFlag = taskTsFlag;
    }

    public Long getProjId() {
        return this.projId;
    }

    public void setProjId(Long projId) {
        this.projId = projId;
    }

    public Date getWorkDate() {
        return this.workDate;
    }

    public void setWorkDate(Date workDate) {
        this.workDate = workDate;
    }

    public String getStatusCode() {
        return this.statusCode;
    }

    public Double getWorkHour() {
        return workHour;
    }

    public Double getHrCnt() {
        return hrCnt;
    }

    public Double getOtHrCnt() {
        return otHrCnt;
    }

    public Double getPendHrCnt() {
        return pendHrCnt;
    }

    public Double getPendOtHrCnt() {
        return pendOtHrCnt;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public void setWorkHour(Double workHour) {
        this.workHour = workHour;
    }

    public void setHrCnt(Double hrCnt) {
        this.hrCnt = hrCnt;
    }

    public void setOtHrCnt(Double otHrCnt) {
        this.otHrCnt = otHrCnt;
    }

    public void setPendHrCnt(Double pendHrCnt) {
        this.pendHrCnt = pendHrCnt;
    }

    public void setPendOtHrCnt(Double pendOtHrCnt) {
        this.pendOtHrCnt = pendOtHrCnt;
    }

}
