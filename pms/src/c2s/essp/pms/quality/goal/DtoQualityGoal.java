package c2s.essp.pms.quality.goal;

import java.util.Date;

import c2s.dto.DtoBase;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class DtoQualityGoal extends DtoBase {
    public static final String DTO_QUALITY_GOAL = "dtoQualityGoal";


    private Long rid;
    private Long acntRid;
    private String production;
    private Long actualSize;
    private Long actualDefect;

    private Boolean isSumSize ;
    private Boolean isSumDefect ;
    private Long seq;
    private String unit;
    private Double planRate;
    private Double actualRate;
    private String description;


    private String rst;
    private Date rct;
    private Date rut;


    public DtoQualityGoal() {
    }

    public Double getActualRate() {
        return actualRate;
    }

    public Long getActualSize() {
        return actualSize;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getIsSumDefect() {
        return isSumDefect;
    }

    public Boolean getIsSumSize() {
        return isSumSize;
    }

    public Double getPlanRate() {
        return planRate;
    }

    public String getProduction() {
        return production;
    }

    public Date getRct() {
        return rct;
    }

    public Long getRid() {
        return rid;
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

    public String getUnit() {
        return unit;
    }

    public Long getAcntRid() {
        return acntRid;
    }

    public Long getActualDefect() {
        return actualDefect;
    }

    public void setActualRate(Double actualRate) {
        this.actualRate = actualRate;
    }

    public void setActualSize(Long actualSize) {
        this.actualSize = actualSize;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPlanRate(Double planRate) {
        this.planRate = planRate;
    }

    public void setProduction(String production) {
        this.production = production;
    }

    public void setRct(Date rct) {
        this.rct = rct;
    }

    public void setRid(Long rid) {
        this.rid = rid;
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

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setAcntRid(Long acntRid) {
        this.acntRid = acntRid;
    }

    public void setActualDefect(Long actualDefect) {
        this.actualDefect = actualDefect;
    }

    public void setIsSumDefect(Boolean isSumDefect) {
        this.isSumDefect = isSumDefect;
    }

    public void setIsSumSize(Boolean isSumSize) {
        this.isSumSize = isSumSize;
    }

}
