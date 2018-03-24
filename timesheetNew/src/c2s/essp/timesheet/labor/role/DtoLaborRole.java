package c2s.essp.timesheet.labor.role;

import c2s.dto.DtoBase;
import java.util.Date;

/**
 * <p>Title: DtoLaborRole</p>
 *
 * <p>Description: Labor role data object</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class DtoLaborRole extends DtoBase {

    public final static String DTO_RID = "TS_DtoLaborRoleRid";
    public final static String DTO = "TS_DtoLaborRole";
    public final static String DTO_LIST = "TS_DtoLaborRoleList";

    private Long rid;
    private String name;
    private String description;
    private Boolean isEnable;
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

    public String getShowName(){
        return getName() +"--" +getDescription();
    }

}
