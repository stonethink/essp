package c2s.essp.timesheet.code;

import c2s.dto.DtoBase;
import java.util.Date;

public class DtoCodeValue extends DtoBase {

    public final static String DTO = "TS_DtoCodeValue";
    public final static String DTO_TREE = "TS_DtoCodeValueTree";
    public final static String DTO_LIST = "TS_DtoCodeValueList";
    public final static String DTO_IS_LEAVE_TYPE = "TS_DtoIsLeaveType";
    public final static String DTO_LEAVE_TYPE = "1";
    public final static String DTO_NON_LEAVE_TYPE = "0";
    // Fields

    private Long rid;
    private String name;
    private String description;
    private Long parentRid;
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

    public Long getParentRid() {
        return this.parentRid;
    }

    public void setParentRid(Long parentRid) {
        this.parentRid = parentRid;
    }

    public Boolean getIsEnable() {
        return this.isEnable;
    }

    public void setIsEnable(Boolean isEnable) {
        this.isEnable = isEnable;
    }

    public Boolean getIsLeaveType() {
        return this.isLeaveType;
    }

    public void setIsLeaveType(Boolean isLeaveType) {
        this.isLeaveType = isLeaveType;
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

    public String getShowLeaveCodeName(){
        if(getDescription() != null){
            return getName() + " -- " + getDescription();
        }else{
            return getName();
        }
    }
}
