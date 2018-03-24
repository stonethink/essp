/*
 * Created on 2008-1-31
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package c2s.essp.timesheet.report;

import java.util.Date;

import c2s.dto.DtoBase;

public class DtoAttVariantQuery extends DtoBase{
    public final static String DTO_RESULT_LIST = "dtoResultList";
    public final static String DTO_LEAVE_HOURS = "dtoLeaveHours";
    public final static String DTO_OVERTIME_HOURS = "dtoOvertimeHours";
    public final static String DTO_ATTVARIANT_QUERY="dtoAttVariantQuery";
    public final static String DTO_SITE_LIST="siteList";
    public final static String DTO_IS_PMO="isPMO";
    public final static String DTO_BEGIN_DATE="beginDate";
    public final static String DTO_END_DATE="endDate";
    public final static String DTO_EMP_ID="empId";
    public final static String DTO_SITE="site";
    public final static String DTO_LEAVE_LIST="leaveList";
    public final static String DTO_OVERTIME_LIST="otList";
    public final static String DTO_SEND_LIST="sendList";
    public final static String DTO_SELECT_ALL="selectAll";
    
    private Date beginDate;
    private Date endDate;
    private String empId;
    private String site;
    private Boolean isPMO;
    private String loginId;
    private Boolean selectAll;
    private Boolean selectVariant;
    
    public Boolean getSelectAll() {
        return selectAll;
    }
    public void setSelectAll(Boolean selectAll) {
        this.selectAll = selectAll;
    }
    public Boolean getSelectVariant() {
        return selectVariant;
    }
    public void setSelectVariant(Boolean selectVariant) {
        this.selectVariant = selectVariant;
    }
    public String getLoginId() {
        return loginId;
    }
    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }
    public Boolean getIsPMO() {
        return isPMO;
    }
    public void setIsPMO(Boolean isPMO) {
        this.isPMO = isPMO;
    }
    public Date getBeginDate() {
        return beginDate;
    }
    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }
    public Date getEndDate() {
        return endDate;
    }
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
  
    public String getSite() {
        return site;
    }
    public void setSite(String site) {
        this.site = site;
    }
    
    public String getEmpId() {
        return empId;
    }
    public void setEmpId(String empId) {
        this.empId = empId;
    }

   

}
