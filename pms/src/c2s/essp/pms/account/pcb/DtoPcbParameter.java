package c2s.essp.pms.account.pcb;

import java.util.Date;

import c2s.dto.DtoBase;

public class DtoPcbParameter extends DtoBase {
    private Long rid;
    private Long acntRid;
    private Long itemRid;
    private String name;
    private String id;
    private String unit;
    private Double ucl;
    private Double mean;
    private Double lcl;
    private Double plan;
    private String remark;
    private Double actual;

    private String rst;
    private Date rct;
    private Date rut;
    private Long seq;

    public Double getActual() {
        return actual;
    }

    public Long getItemRid() {
        return itemRid;
    }

    public Double getLcl() {
        return lcl;
    }

    public Double getMean() {
        return mean;
    }

    public String getName() {
        return name;
    }

    public Double getPlan() {
        return plan;
    }

    public String getRemark() {
        return remark;
    }

    public Long getRid() {
        return rid;
    }

    public Double getUcl() {
        return ucl;
    }

    public String getUnit() {
        return unit;
    }

    public String getId() {
        return id;
    }

    public Long getAcntRid() {
        return acntRid;
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

    public Long getSeq() {
        return seq;
    }

    public void setActual(Double actual) {
        this.actual = actual;
    }

    public void setItemRid(Long itemRid) {
        this.itemRid = itemRid;
    }

    public void setLcl(Double lcl) {
        this.lcl = lcl;
    }

    public void setMean(Double mean) {
        this.mean = mean;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPlan(Double plan) {
        this.plan = plan;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public void setUcl(Double ucl) {
        this.ucl = ucl;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAcntRid(Long acntRid) {
        this.acntRid = acntRid;
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

    public void setSeq(Long seq) {
        this.seq = seq;
    }


}
