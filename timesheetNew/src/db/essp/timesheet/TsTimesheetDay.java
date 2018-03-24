package db.essp.timesheet;

import java.util.Date;


public class TsTimesheetDay implements java.io.Serializable {


    // Fields

     private Long rid;
     private TsTimesheetDetail tsTimesheetDetail;
     private Long tsDetailRid;
     private Double workHours;
     private Double overtimeHours;
     private Date workDate;
     private Date rct;
     private Date rut;
     private String rcu;
     private String ruu;


    // Property accessors

    public Long getRid() {
        return this.rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public Long getTsDetailRid() {

        return tsDetailRid;
    }

    public void setTsDetailRid(Long tsDetailRid) {

        this.tsDetailRid = tsDetailRid;
    }

    public Double getWorkHours() {
        return this.workHours;
    }

    public void setWorkHours(Double workHours) {
        this.workHours = workHours;
    }

    public Double getOvertimeHours() {
        return this.overtimeHours;
    }

    public void setOvertimeHours(Double overtimeHours) {
        this.overtimeHours = overtimeHours;
    }

    public Date getWorkDate() {
        return this.workDate;
    }

    public void setWorkDate(Date workDate) {
        this.workDate = workDate;
    }

    public Date getRct() {
        return this.rct;
    }

    public void setRct(Date rct) {
        this.rct = rct;
    }

    public Date getRut() {
        return this.rut;
    }

    public void setRut(Date rut) {
        this.rut = rut;
    }

    public String getRcu() {
        return this.rcu;
    }

    public void setRcu(String rcu) {
        this.rcu = rcu;
    }

    public String getRuu() {
        return this.ruu;
    }

    public TsTimesheetDetail getTsTimesheetDetail() {
        return tsTimesheetDetail;
    }

    public void setRuu(String ruu) {
        this.ruu = ruu;
    }

    public void setTsTimesheetDetail(TsTimesheetDetail tsTimesheetDetail) {
        this.tsTimesheetDetail = tsTimesheetDetail;
    }


}
