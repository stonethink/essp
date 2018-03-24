/*
 * Created on 2008-6-27
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package c2s.essp.timesheet.report;

import java.util.Date;

import c2s.dto.DtoBase;

public class DtoBusyRateQuery extends DtoBase{
    
       public final static String DTO_USER_LIST = "userList";
    
       public final static String DTO_DEPT_VECTOR = "deptVector";
    
       public final static String DTO_DEPT_LIST = "deptList";
    
       public final static String DTO_UNTIL_RATE = "dtoUtilRate";
    
       public final static String DTO_UNTIL_RATE_QUERY = "dtoUtilRateQuery";
    
       public final static String DTO_UNTIL_RATE_GATHER = "dtoUtilRateGather";
    
       public final static String DTO_UTIL_RATE_TYPE="type";
    
       public final static String DTO_TYPE_PERIOD = "period";
    
       public final static String DTO_TYPE_TIMESHEET = "timesheet";
    
       public final static String DTO_TYPE_MONTH = "month";
    
       public final static String DTO_TYPE_USER_LIST = "userList";
    
       public final static String DTO_LOGINID = "loginId";
    
       public final static String DTO_BEGIN_DATE = "beginDate";
    
       public final static String DTO_END_DATE = "endDate";
       
       public final static String DTO_INCLUDE_SUB = "includeSub";
       
       public final static String DTO_ACCOUNT_ID="accountId";
    
        private String loginId;
        private String acntId;
        private Long acntRid;
        private Date begin;
        private Date end;
        private Boolean isSub = false;
        
        public String getAcntId() {
            return acntId;
        }
        public void setAcntId(String acntId) {
            this.acntId = acntId;
        }
        public Long getAcntRid() {
            return acntRid;
        }
        public void setAcntRid(Long acntRid) {
            this.acntRid = acntRid;
        }
        public Date getBegin() {
            return begin;
        }
        public void setBegin(Date begin) {
            this.begin = begin;
        }
        public Date getEnd() {
            return end;
        }
        public void setEnd(Date end) {
            this.end = end;
        }
        public Boolean getIsSub() {
            return isSub;
        }
        public void setIsSub(Boolean isSub) {
            this.isSub = isSub;
        }
        public String getLoginId() {
            return loginId;
        }
        public void setLoginId(String loginId) {
            this.loginId = loginId;
        }

}
