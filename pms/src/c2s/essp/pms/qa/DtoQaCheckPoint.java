package c2s.essp.pms.qa;

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
public class DtoQaCheckPoint extends DtoBase{

    //Pms Check Point list
    public final static String PMS_CHECK_POINT_LIST = "pmsCheckPointList";

    //Pms Check Point
    public final static String DTO_PMS_CHECK_POINT = "dtoPmsCheckPoint";

    //Pms Check Point Code
    public final static String DTO_PMS_CHECK_POINT_CODE = "dtoPmsCheckPointCode";

    //Pms Check Point Rid
    public final static String DTO_PMS_CHECK_POINT_RID= "dtoPmsCheckPointRid";

    //Pms Check Point Type
    public final static String DTO_PMS_CHECK_POINT_TYPE = "qaCheckPoint";

    // Fields

     private Long rid;
     private Long acntRid;
     private Long belongRid;
     private String belongTo;
    private String name;
     private String method;
    private String rst;
     private Date rct;
     private Date rut;
    private Long seq;
    private String remark;


    // Constructors

    /** default constructor */
    public DtoQaCheckPoint() {
    }

    /** minimal constructor */
    public DtoQaCheckPoint(Long rid, Long acntRid, Long belongRid, String belongTo, String code) {
        this.rid = rid;
        this.acntRid = acntRid;
        this.belongRid = belongRid;
        this.belongTo = belongTo;
    }

    /** full constructor */
    public DtoQaCheckPoint(Long rid, Long acntRid, Long belongRid, String belongTo, String name, String method, String rst, Date rct, Date rut) {
        this.rid = rid;
        this.acntRid = acntRid;
        this.belongRid = belongRid;
        this.belongTo = belongTo;
        this.name = name;
        this.method = method;
        this.rst = rst;
        this.rct = rct;
        this.rut = rut;
    }


    // Property accessors

    public Long getRid() {
        return this.rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public Long getAcntRid() {
        return this.acntRid;
    }

    public void setAcntRid(Long acntRid) {
        this.acntRid = acntRid;
    }

    public Long getBelongRid() {
        return this.belongRid;
    }

    public void setBelongRid(Long belongRid) {
        this.belongRid = belongRid;
    }

    public String getBelongTo() {
        return this.belongTo;
    }

    public void setBelongTo(String belongTo) {
        this.belongTo = belongTo;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMethod() {
        return this.method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getRst() {
        return this.rst;
    }

    public void setRst(String rst) {
        this.rst = rst;
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

    public Long getSeq() {
        return seq;
    }

    public String getRemark() {
        return remark;
    }

    public void setRut(Date rut) {
        this.rut = rut;
    }

    public void setSeq(Long seq) {
        this.seq = seq;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
