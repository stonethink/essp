package c2s.essp.timesheet.report;

import java.util.Date;

import c2s.dto.DtoBase;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author tbh
 * @version 1.0
 */
public class DtoUtilRate extends DtoBase implements ISumLevel{
       private Long acntRid;
       private String acntId;
       private String loginId;
       private String loginName;
       private Date date;
       private Double actualHours;
       private Double validHours;
       private Double standardHours;
       private Double utilRate;
       private Date beginDate;
       private Date endDate;
       private String dateStr;
        private int sumLevel;
    
        public String getAcntId() {
            return acntId;
        }
    
        public Long getAcntRid() {
            return acntRid;
        }
    
    
        public Date getDate() {
            return date;
        }
    
        public String getLoginId() {
            return loginId;
        }
    
        public String getLoginName() {
            return loginName;
        }
    
        public Double getUtilRate() {
            return utilRate;
        }
    
        public Double getValidHours() {
            return validHours;
        }
    
        public Double getActualHours() {
            return actualHours;
        }
    
        public Date getEndDate() {
            return endDate;
        }
    
        public Date getBeginDate() {
            return beginDate;
        }
    
        public String getDateStr() {
            return dateStr;
        }
    
        public void setAcntId(String acntId) {
            this.acntId = acntId;
        }
    
        public void setAcntRid(Long acntRid) {
            this.acntRid = acntRid;
        }
    
        public void setDate(Date date) {
            this.date = date;
        }
    
        public void setLoginId(String loginId) {
            this.loginId = loginId;
        }
    
        public void setLoginName(String loginName) {
            this.loginName = loginName;
        }
    
        public void setUtilRate(Double utilRate) {
            this.utilRate = utilRate;
        }
    
        public void setValidHours(Double validHours) {
            this.validHours = validHours;
        }
    
        public void setActualHours(Double actualHours) {
            this.actualHours = actualHours;
        }
    
        public void setEndDate(Date endDate) {
            this.endDate = endDate;
        }
    
        public void setBeginDate(Date beginDate) {
            this.beginDate = beginDate;
        }
    
        public void setDateStr(String dateStr) {
            this.dateStr = dateStr;
        }
    
        public void setSumLevel(int sumLevel) {
            this.sumLevel = sumLevel;
        }
    
        public int getSumLevel() {
            return sumLevel;
        }

        public Double getStandardHours() {
            return standardHours;
        }

        public void setStandardHours(Double standardHours) {
            this.standardHours = standardHours;
        }

}
