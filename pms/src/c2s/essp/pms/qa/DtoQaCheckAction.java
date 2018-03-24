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
public class DtoQaCheckAction extends DtoBase{

    public final static String[] chkActionOccasion = new String[] {
       "Plan Start", "Plan Finish", "Actual Start", "Actual Finish",
       "Customize"};
   public final static String[] chkActionStatus = new String[] {
                                                 "UnStarted", "Processing",
                                                 "Closed"};
    //Pms Check Action list
    public final static String PMS_CHECK_ACTION_LIST = "pmsCheckActionList";

    //Qa Account Labor list
    public final static String PMS_QA_LABORRES_LIST = "pmsQaLaborResList";

    //Dto Pms Check Action
    public final static String DTO_PMS_CHECK_ACTION = "dtoPmsCheckAction";

    //Dto Pms Check Action Code
    public final static String DTO_PMS_CHECK_ACTION_CODE = "dtoPmsCheckActionCode";

    //Dto Pms Check Action Rid
    public final static String DTO_PMS_CHECK_ACTION_RID = "dtoPmsCheckActionRid";

    //Pms Check Action Type
    public final static String DTO_PMS_CHECK_ACTION_TYPE = "qaCheckAction";

    // Fields

      private Long rid;
      private Long cpRid;
    private Date planDate;
      private String planPerformer;
      private Date actDate;
      private String actPerformer;
      private String content;
      private String result;
      private String nrcNo;
      private String rst;
      private Date rct;
      private Date rut;
    private Long acntRid;
    private String occasion;
    private String status;


    // Constructors

     /** default constructor */
     public DtoQaCheckAction() {
     }

     /** minimal constructor */
     public DtoQaCheckAction(Long rid, Long cpRid) {
         this.rid = rid;
         this.cpRid = cpRid;
     }

     /** full constructor */
     public DtoQaCheckAction(Long rid, Long cpRid, Date planDate, String planPerformer, Date actDate, String actPerformer, String content, String result, String nrcNo, String rst, Date rct, Date rut) {
         this.rid = rid;
         this.cpRid = cpRid;
         this.planDate = planDate;
         this.planPerformer = planPerformer;
         this.actDate = actDate;
         this.actPerformer = actPerformer;
         this.content = content;
         this.result = result;
         this.nrcNo = nrcNo;
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

     public Long getCpRid() {
         return this.cpRid;
     }

     public void setCpRid(Long cpRid) {
         this.cpRid = cpRid;
     }

    public Date getPlanDate() {
         return this.planDate;
     }

     public void setPlanDate(Date planDate) {
         this.planDate = planDate;
     }

     public String getPlanPerformer() {
         return this.planPerformer;
     }

     public void setPlanPerformer(String planPerformer) {
         this.planPerformer = planPerformer;
     }

     public Date getActDate() {
         return this.actDate;
     }

     public void setActDate(Date actDate) {
         this.actDate = actDate;
     }

     public String getActPerformer() {
         return this.actPerformer;
     }

     public void setActPerformer(String actPerformer) {
         this.actPerformer = actPerformer;
     }

     public String getContent() {
         return this.content;
     }

     public void setContent(String content) {
         this.content = content;
     }

     public String getResult() {
         return this.result;
     }

     public void setResult(String result) {
         this.result = result;
     }

     public String getNrcNo() {
         return this.nrcNo;
     }

     public void setNrcNo(String nrcNo) {
         this.nrcNo = nrcNo;
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

    public Long getAcntRid() {
        return acntRid;
    }

    public String getOccasion() {
        return occasion;
    }

    public String getStatus() {
        return status;
    }

    public void setRut(Date rut) {
         this.rut = rut;
     }

    public void setAcntRid(Long acntRid) {
        this.acntRid = acntRid;
    }

    public void setOccasion(String occasion) {
        this.occasion = occasion;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
