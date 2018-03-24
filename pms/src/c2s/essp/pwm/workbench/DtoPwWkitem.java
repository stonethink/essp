package c2s.essp.pwm.workbench;

import java.math.BigDecimal;
import java.util.Date;

import c2s.dto.DtoBase;

public class DtoPwWkitem extends DtoBase {

    /** identifier field */
    private Long rid;

    /** nullable persistent field */
    private Long projectId;

    /** nullable persistent field */
    private Long activityId;

    /** nullable persistent field */
    private Long wpId;

    /** nullable persistent field */
    private String wkitemOwner;

    /** nullable persistent field */
    private String wkitemPlace;

    /** nullable persistent field */
    private String wkitemBelongto;

    /** persistent field */
    private String wkitemName;

    /** persistent field */
    private Date wkitemDate;

    /** nullable persistent field */
    private Date wkitemStarttime;

    /** nullable persistent field */
    private Date wkitemFinishtime;

    /** persistent field */
    private BigDecimal wkitemWkhours;

    /** persistent field */
    private String wkitemIsdlrpt;

    /** nullable persistent field */
    private Long wkitemCopyfrom;

    private Long wpIdOld;
    private BigDecimal wkitemWkhoursOld;
    //private String wkitemIsdlrptOld;

    public Long getActivityId() {
        return activityId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public Long getRid() {
        return rid;
    }

    public String getWkitemBelongto() {
        return wkitemBelongto;
    }

    public Long getWkitemCopyfrom() {
        return wkitemCopyfrom;
    }

    public Date getWkitemDate() {
        return wkitemDate;
    }

    public Date getWkitemFinishtime() {
        return wkitemFinishtime;
    }

    public String getWkitemIsdlrpt() {
        return wkitemIsdlrpt;
    }

    public boolean isdlrpt(){
        if( wkitemIsdlrpt != null && wkitemIsdlrpt.equals("1")){
            return true;
        }else{
            return false;
        }
    }

    public String getWkitemName() {
        return wkitemName;
    }

    public String getWkitemOwner() {
        return wkitemOwner;
    }

    public String getWkitemPlace() {
        return wkitemPlace;
    }

    public Date getWkitemStarttime() {
        return wkitemStarttime;
    }

    public BigDecimal getWkitemWkhours() {
        return wkitemWkhours;
    }

    public Long getWpId() {
        return wpId;
    }

    public BigDecimal getWkitemWkhoursOld() {
        return wkitemWkhoursOld;
    }

//    public String getWkitemIsdlrptOld() {
//        return wkitemIsdlrptOld;
//    }

    public Long getWpIdOld() {
        return wpIdOld;
    }

    public void setWpId(Long wpId) {
        this.wpId = wpId;
    }

    public void setWkitemWkhours(BigDecimal wkitemWkhours) {
        this.wkitemWkhours = wkitemWkhours;
    }

    public void setWkitemStarttime(Date wkitemStarttime) {
        this.wkitemStarttime = wkitemStarttime;
   }

    public void setWkitemPlace(String wkitemPlace) {
        this.wkitemPlace = wkitemPlace;
    }

    public void setWkitemOwner(String wkitemOwner) {
        this.wkitemOwner = wkitemOwner;
    }

    public void setWkitemName(String wkitemName) {
        this.wkitemName = wkitemName;
    }

    public void setWkitemIsdlrpt(String wkitemIsdlrpt) {
        this.wkitemIsdlrpt = wkitemIsdlrpt;
    }

    public void setWkitemFinishtime(Date wkitemFinishtime) {
        this.wkitemFinishtime = wkitemFinishtime;
   }

    public void setWkitemDate(Date wkitemDate) {
        this.wkitemDate = wkitemDate;
    }

    public void setWkitemCopyfrom(Long wkitemCopyfrom) {
        this.wkitemCopyfrom = wkitemCopyfrom;
    }

    public void setWkitemBelongto(String wkitemBelongto) {
        this.wkitemBelongto = wkitemBelongto;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public void setWkitemWkhoursOld(BigDecimal wkitemWkhoursOld) {
        this.wkitemWkhoursOld = wkitemWkhoursOld;
    }

//    public void setWkitemIsdlrptOld(String wkitemIsdlrptOld) {
//        this.wkitemIsdlrptOld = wkitemIsdlrptOld;
//    }

    public void setWpIdOld(Long wpIdOld) {
        this.wpIdOld = wpIdOld;
    }
}
