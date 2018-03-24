package c2s.essp.pwm.wp;

import java.math.BigDecimal;
import java.util.Date;

import c2s.dto.DtoBase;

public class DtoPwWkitem extends DtoBase {
    /** identifier field */
    private Long rid;

    /** nullable persistent field */
    private Long projectId;
    private String projectName;

    private Long activityId;
    private String activityName;

    /** nullable persistent field */
    private Long wpId;
    private String wpName;

    /** nullable persistent field */
    private String wkitemOwner;
    private String wkitemOwnerName;

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
    private BigDecimal wkitemWkhours = new BigDecimal( Double.parseDouble("0"));

    /** persistent field */
    private String wkitemIsdlrpt = "0";

    /** nullable persistent field */
    private String wkitemCopyfrom;

    /** nullable persistent field */
    private String rst;

    /** nullable persistent field */
    private Date rct;

    /** nullable persistent field */
    private Date rut;

    private BigDecimal wkitemWkhoursOld;

    /** full constructor */
    public DtoPwWkitem(Long rid, Long projectId, Long wpId, String wkitemOwner, String wkitemPlace, String wkitemBelongto, String wkitemName, Date wkitemDate, Date wkitemStarttime, Date wkitemFinishtime, BigDecimal wkitemWkhours, String wkitemIsdlrpt, String wkitemCopyfrom, String rst, Date rct, Date rut) {
        this.rid = rid;
        this.projectId = projectId;
        this.wpId = wpId;
        this.wkitemOwner = wkitemOwner;
        this.wkitemPlace = wkitemPlace;
        this.wkitemBelongto = wkitemBelongto;
        this.wkitemName = wkitemName;
        this.wkitemDate = wkitemDate;
        this.wkitemStarttime = wkitemStarttime;
        this.wkitemFinishtime = wkitemFinishtime;
        this.wkitemWkhours = wkitemWkhours;
        this.wkitemIsdlrpt = wkitemIsdlrpt;
        this.wkitemCopyfrom = wkitemCopyfrom;
        this.rst = rst;
        this.rct = rct;
        this.rut = rut;
    }

    /** default constructor */
    public DtoPwWkitem() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /** minimal constructor */
    public DtoPwWkitem(Long rid, String wkitemName, Date wkitemDate, BigDecimal wkitemWkhours, String wkitemIsdlrpt) {
        this.rid = rid;
        this.wkitemName = wkitemName;
        this.wkitemDate = wkitemDate;
        this.wkitemWkhours = wkitemWkhours;
        this.wkitemIsdlrpt = wkitemIsdlrpt;
    }

    /**
     *            @hibernate.id
     *             generator-class="assigned"
     *             type="java.lang.Long"
     *             column="RID"
     *
     */
    public Long getRid() {
        return this.rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    /**
     *            @hibernate.property
     *             column="PROJECT_ID"
     *             length="8"
     *
     */
    public Long getProjectId() {
        return this.projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    /**
     *            @hibernate.property
     *             column="WP_ID"
     *             length="8"
     *
     */
    public Long getWpId() {
        return this.wpId;
    }

    public void setWpId(Long wpId) {
        this.wpId = wpId;
    }

    /**
     *            @hibernate.property
     *             column="WKITEM_OWNER"
     *             length="50"
     *
     */
    public String getWkitemOwner() {
        return this.wkitemOwner;
    }

    public void setWkitemOwner(String wkitemOwner) {
        this.wkitemOwner = wkitemOwner;
    }

    /**
     *            @hibernate.property
     *             column="WKITEM_PLACE"
     *             length="100"
     *
     */
    public String getWkitemPlace() {
        return this.wkitemPlace;
    }

    public void setWkitemPlace(String wkitemPlace) {
        this.wkitemPlace = wkitemPlace;
    }

    /**
     *            @hibernate.property
     *             column="WKITEM_BELONGT0"
     *             length="100"
     *
     */
    public String getWkitemBelongto() {

        return wkitemBelongto;
    }

    public void setWkitemBelongto(String wkitemBelongto) {

        this.wkitemBelongto = wkitemBelongto;
    }

    /**
     *            @hibernate.property
     *             column="WKITEM_NAME"
     *             length="65535"
     *             not-null="true"
     *
     */
    public String getWkitemName() {
        return this.wkitemName;
    }

    public void setWkitemName(String wkitemName) {
        this.wkitemName = wkitemName;
    }

    /**
     *            @hibernate.property
     *             column="WKITEM_DATE"
     *             length="10"
     *             not-null="true"
     *
     */
    public Date getWkitemDate() {
        return this.wkitemDate;
    }

    public void setWkitemDate(Date wkitemDate) {
        this.wkitemDate = wkitemDate;
    }

    /**
     *            @hibernate.property
     *             column="WKITEM_STARTTIME"
     *             length="10"
     *
     */
    public Date getWkitemStarttime() {
        return this.wkitemStarttime;
    }

    public void setWkitemStarttime(Date wkitemStarttime) {
        this.wkitemStarttime = wkitemStarttime;
    }

    /**
     *            @hibernate.property
     *             column="WKITEM_FINISHTIME"
     *             length="10"
     *
     */
    public Date getWkitemFinishtime() {
        return this.wkitemFinishtime;
    }

    public void setWkitemFinishtime(Date wkitemFinishtime) {
        this.wkitemFinishtime = wkitemFinishtime;
    }

    /**
     *            @hibernate.property
     *             column="WKITEM_WKHOURS"
     *             length="8"
     *             not-null="true"
     *
     */
    public BigDecimal getWkitemWkhours() {
        return this.wkitemWkhours;
    }

    public void setWkitemWkhours(BigDecimal wkitemWkhours) {
        this.wkitemWkhours = wkitemWkhours;
        this.wkitemWkhours = this.wkitemWkhours.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     *            @hibernate.property
     *             column="WKITEM_ISDLRPT"
     *             length="1"
     *             not-null="true"
     *
     */
    public String getWkitemIsdlrpt() {
        return this.wkitemIsdlrpt;
    }

    public void setWkitemIsdlrpt(String wkitemIsdlrpt) {
        this.wkitemIsdlrpt = wkitemIsdlrpt;
    }

    /**
     *            @hibernate.property
     *             column="WKITEM_COPYFROM"
     *             length="8"
     *
     */
    public String getWkitemCopyfrom() {
        return this.wkitemCopyfrom;
    }

    public void setWkitemCopyfrom(String wkitemCopyfrom) {
        this.wkitemCopyfrom = wkitemCopyfrom;
    }

    /**
     *            @hibernate.property
     *             column="RST"
     *             length="1"
     *
     */
    public String getRst() {
        return this.rst;
    }

    public void setRst(String rst) {
        this.rst = rst;
    }

    /**
     *            @hibernate.property
     *             column="RCT"
     *             length="10"
     *
     */
    public Date getRct() {
        return this.rct;
    }

    public void setRct(Date rct) {
        this.rct = rct;
    }

    /**
     *            @hibernate.property
     *             column="RUT"
     *             length="10"
     *
     */
    public Date getRut() {
        return this.rut;
    }

    public void setRut(Date rut) {
        this.rut = rut;
    }

         public String getProjectName() {
             return projectName;
         }
         public void setProjectName(String projectName) {
             this.projectName = projectName;
        }
         public String getWpName() {
             return wpName;
         }
         public void setWpName(String wpName) {
             this.wpName = wpName;
         }


    public String getWkitemOwnerName() {
        return wkitemOwnerName;
    }



    public void setWkitemOwnerName(String wkitemOwnerName) {
        this.wkitemOwnerName = wkitemOwnerName;
    }


    public Long getActivityId() {
        return activityId;
    }

    public String getActivityName() {
        return activityName;
    }

    public BigDecimal getWkitemWkhoursOld() {
        return wkitemWkhoursOld;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public void setWkitemWkhoursOld(BigDecimal wkitemWkhoursOld) {
        this.wkitemWkhoursOld = wkitemWkhoursOld;
    }

    public boolean isdlrpt(){
        if( wkitemIsdlrpt != null && wkitemIsdlrpt.equals("1")){
            return true;
        }else{
            return false;
        }
    }

    private void jbInit() throws Exception {
    }
}
