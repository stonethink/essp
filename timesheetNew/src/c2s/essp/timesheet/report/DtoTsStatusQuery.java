/*
 * Created on 2007-11-27
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package c2s.essp.timesheet.report;

import java.util.Date;

import c2s.dto.DtoBase;

public class DtoTsStatusQuery extends DtoBase implements ISumLevel{
        public final static String DTO_RESULT_LIST = "TsResultList";
        public final static String DTO_TS_QUERY = "TsQueryDto";
        public final static String DTO_UNFILLED="Unfilled";
        public final static String DTO_SUBMITTED="Submitted";
        public final static String DTO_APPROVED="Approved";
        public final static String DTO_ACTIVE="Active";
        public final static String DTO_REJECTED="Rejected";
        public final static String DTO_PM_APPROVED="PmApproved";
        public final static String DTO_RM_APPROVED="RmApproved";
        public final static String DTO_STATUS_HUMAN="StausHuman";
        public final static String DTO_BEGIN_DATE="beginDate";
        public final static String DTO_END_DATE="endDate";
        public final static String DTO_STATUS="status";
        public final static String DTO_DEPTLIST="deptList";
        public final static String DTO_CURRENT_DATE="currentDate";
        public final static String DTO_DATE_SCOPE="dateScope";
        public final static String DTO_COUNT_NEED="countYes";
        public final static String DTO_COUNT_NO="countNo";
        public final static String DTO_IS_FILL="isFill";
        public final static String DTO_UNFILL="unfill";
        public final static String DTO_DEPT_ID="deptId";
        public final static String DTO_IS_DEPT_QUERY="isDeptQuery";
        public final static String DTO_INCLUDE_SUB="includeSub";
        public final static String DTO_FILLED_RATE="filledRate";
        public final static String DTO_FILLED_RATE_GATHER="filledRateGather";
        public final static String DTO_PERIOD_NUM="periodNum";
        public final static String DTO_ISFILL_YES="Yes";
        public final static String DTO_ISFILL_NO="No";
        public final static String DTO_SITE="site";
               
        private Date beginDate;
        
        private Date endDate;
        
        private String deptId;
        
        private String deptIdStr;
        
        private String site;
        
        private Boolean isSub;
        
        private Boolean isDeptQuery;
        
        private String empId;
        
        public String getEmpId() {
            return empId;
        }

        public void setEmpId(String empId) {
            this.empId = empId;
        }

        public Boolean getIsDeptQuery() {
            return isDeptQuery;
        }
    
        public void setIsDeptQuery(Boolean isDeptQuery) {
            this.isDeptQuery = isDeptQuery;
        }
    
        public Boolean getIsSub() {
            return isSub;
        }
    
        public void setIsSub(Boolean isSub) {
            this.isSub = isSub;
        }
    
        public Date getBeginDate() {
            return beginDate;
        }
    
        public String getDeptId() {
            return deptId;
        }
    
        public void setDeptId(String deptId) {
            this.deptId = deptId;
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
    
        public int getSumLevel() {
            return 0;
        }
    
        public String getDeptIdStr() {
            return deptIdStr;
        }
    
        public void setDeptIdStr(String deptIdStr) {
            this.deptIdStr = deptIdStr;
        }

        public String getSite() {
            return site;
        }

        public void setSite(String site) {
            this.site = site;
        }
}
