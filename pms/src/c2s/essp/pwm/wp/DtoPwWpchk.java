package c2s.essp.pwm.wp;

import java.util.Date;
import c2s.dto.DtoBase;

public class DtoPwWpchk extends DtoBase{
    private Long rid;
    private Long wpId;

    private String wpchkName;
    private Date wpchkDate;
    private String wpchkStatus;

    private String rst;
    private Date rct;
    private Date rut;


    public Long getRid() {
        return rid;
    }

    public String getWpchkName() {
        return wpchkName;
    }

    public Long getWpId() {
        return wpId;
    }

    public String getWpchkStatus() {
        return wpchkStatus;
    }

    public void setWpchkDate(Date wpchkDate) {
        this.wpchkDate = wpchkDate;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public void setWpchkName(String wpchkName) {
        this.wpchkName = wpchkName;
    }

    public void setWpId(Long wpId) {
        this.wpId = wpId;
    }

    public void setWpchkStatus(String wpchkStatus) {
        this.wpchkStatus = wpchkStatus;
    }

    public void setRct(Date rct) {
        this.rct = rct;
    }

    public void setRst(String rst) {
        this.rst = rst;
    }

    public void setRut(Date rut) {
        this.rut = rut;
    }

    public Date getWpchkDate() {
        return wpchkDate;
    }

    public Date getRct() {
        return rct;
    }

    public String getRst() {
        return rst;
    }

    public Date getRut() {
        return rut;
    }

}
