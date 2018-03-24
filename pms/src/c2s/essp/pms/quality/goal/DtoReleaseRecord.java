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
public class DtoReleaseRecord extends DtoBase {


    private Long rid;
    private Long goalRid;

    private String version;
    private Date releaseDate;
    private String description;
    private Date statDate;
    private Long size;
    private Long problems;
    private Long defects;


    private String rst;
    private Date rct;
    private Date rut;
    private Double qualificationRate;

    public DtoReleaseRecord() {
    }

    public Long getDefects() {
        return defects;
    }

    public String getDescription() {
        return description;
    }

    public Long getGoalRid() {
        return goalRid;
    }

    public Long getProblems() {
        return problems;
    }


    public Date getRct() {
        return rct;
    }

    public Date getReleaseDate() {
        return releaseDate;
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

    public Long getSize() {
        return size;
    }

    public Date getStatDate() {
        return statDate;
    }

    public String getVersion() {
        return version;
    }

    public Double getQualificationRate() {
        return qualificationRate;
    }

    public void setDefects(Long defects) {
        this.defects = defects;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setGoalRid(Long goalRid) {
        this.goalRid = goalRid;
    }

    public void setProblems(Long problems) {
        this.problems = problems;
    }


    public void setRct(Date rct) {
        this.rct = rct;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
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

    public void setSize(Long size) {
        this.size = size;
    }

    public void setStatDate(Date statDate) {
        this.statDate = statDate;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setQualificationRate(Double qualificationRate) {
        this.qualificationRate = qualificationRate;
    }


}
