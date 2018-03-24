package db.essp.timesheet;

import java.util.Date;


public class TsCodeType implements java.io.Serializable {


    // Fields

    private Long rid;
    private String name;
    private String description;
    private Boolean isEnable;
    private Boolean isLeaveType;
    private Long seq;
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

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIsEnable() {
        return this.isEnable;
    }

    public void setIsEnable(Boolean isEnable) {
        this.isEnable = isEnable;
    }

    public Long getSeq() {
        return this.seq;
    }

    public void setSeq(Long seq) {
        this.seq = seq;
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
    public Boolean getIsLeaveType() {
        return this.isLeaveType;
    }

    public void setIsLeaveType(Boolean isLeaveType) {
        this.isLeaveType = isLeaveType;
    }
}
