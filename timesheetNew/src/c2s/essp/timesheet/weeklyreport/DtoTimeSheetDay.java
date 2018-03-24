package c2s.essp.timesheet.weeklyreport;

import java.util.Date;

import c2s.dto.DtoBase;

/**
 * <p>Title: DtoTimesheetDay</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class DtoTimeSheetDay extends DtoBase {

    private Long rid;
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

    public void setRuu(String ruu) {
        this.ruu = ruu;
    }

    /**
     * 返回显示数据
     * @return  a string representation of the object.
     */
    public String toString() {
        if(workHours == null) {
            return "";
        }
        //如果有加班时间，则显示
        if (overtimeHours != null
            && !overtimeHours.equals(Double.valueOf(0))) {
            return workHours + "/" + overtimeHours;
        } else {
            return workHours.toString();
        }
    }

}
