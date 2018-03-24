package server.essp.pms.account.bean;

import java.util.Date;


public class AccountLaborResBase {
    private String name;
    private String role;
    private String job_desc;
    private Date start;
    private Date finish;
    private Long percent;
    private Double time;
    private String remark;
    public AccountLaborResBase() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setJob_desc(String job_desc) {
        this.job_desc = job_desc;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public void setFinish(Date finish) {
        this.finish = finish;
    }

    public void setPercent(Long percent) {
        this.percent = percent;
    }

    public void setTime(Double time) {
        this.time = time;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public String getJob_desc() {
        return job_desc;
    }

    public Date getStart() {
        return start;
    }

    public Date getFinish() {
        return finish;
    }

    public Long getPercent() {
        return percent;
    }

    public Double getTime() {
        return time;
    }

    public String getRemark() {
        return remark;
    }

}
