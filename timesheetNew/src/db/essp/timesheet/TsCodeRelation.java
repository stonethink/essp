package db.essp.timesheet;

import java.util.Date;

public class TsCodeRelation implements java.io.Serializable {


    // Fields
    private Long rid;
    private Long typeRid;
    private Long valueRid;
    private Boolean isLeaveType;
    private Date rct;
    private Date rut;
    private String rcu;
    private String ruu;


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

    public Long getTypeRid() {

        return typeRid;
    }

    public Long getValueRid() {

        return valueRid;
    }

    public Long getRid() {
        return rid;
    }

    public void setRuu(String ruu) {
        this.ruu = ruu;
    }


    public void setTypeRid(Long typeRid) {

        this.typeRid = typeRid;
    }

    public void setValueRid(Long valueRid) {

        this.valueRid = valueRid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }
    public Boolean getIsLeaveType() {
        return this.isLeaveType;
    }

    public void setIsLeaveType(Boolean isLeaveType) {
        this.isLeaveType = isLeaveType;
    }

}
