/*
 * Created on 2008-1-3
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package c2s.essp.timesheet.report;

import java.util.Date;

import c2s.dto.DtoBase;

public class DtoSalaryWkHrQuery extends DtoBase{
        public final static String DTO_SALARY_QUERY="dtoSalaryQuery";
        public final static String DTO_QUERY_LIST="dtoQueryList";
        public final static String DTO_BEGIN_DATE = "beginDate";
        public final static String DTO_BALANCE_ONT = "balanceOne";
        public final static String DTO_BALANCE_TWO = "balanceTwo";
        public final static String DTO_SITE_LIST = "siteList";
        public final static String DTO_SITE = "site";
        public final static String DTO_IS_PMO="isPMO";
       
        private Date beginDate;
        private Date endDate;
        private String site;
        private Boolean isBalanceOne;
        private Boolean isBalanceTwo;
  
        public Boolean getIsBalanceTwo() {
        return isBalanceTwo;
        }
        public void setIsBalanceTwo(Boolean isBalanceTwo) {
            this.isBalanceTwo = isBalanceTwo;
        }
        public Boolean getIsBalanceOne() {
            return isBalanceOne;
        }
        public void setIsBalanceOne(Boolean isBalanceOne) {
            this.isBalanceOne = isBalanceOne;
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

}
