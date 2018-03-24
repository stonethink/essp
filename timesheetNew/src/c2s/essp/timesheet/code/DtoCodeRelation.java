package c2s.essp.timesheet.code;

import c2s.dto.DtoBase;
import java.util.Date;

/**
 * <p>Title: </p>
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
public class DtoCodeRelation extends DtoBase {

    // Fields
    private Long rid;
    private Long tsCodeType;
    private Long tsCodeValue;
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

    public Long getTsCodeType() {
        return tsCodeType;
    }

    public Long getTsCodeValue() {
        return tsCodeValue;
    }

    public Long getRid() {
        return rid;
    }

    public void setRuu(String ruu) {
        this.ruu = ruu;
    }


    public void setTsCodeType(Long tsCodeType) {
        this.tsCodeType = tsCodeType;
    }

    public void setTsCodeValue(Long tsCodeValue) {
        this.tsCodeValue = tsCodeValue;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

}
