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
public class DtoUtilRateGather  extends DtoBase implements ISumLevel{
       private Long acntRid;
       private String acntId;
       private String acntName;
       private Date date;
       private Double actualHours;
       private Double validHours;
       private Double invalidHours;
       private Double standardHours;
       private Date beginDate;
       private Date endDate;
       private String dateStr;
       private DtoMonthData[] dtoMonthData = new DtoMonthData[12];
    
       private int sumLevel;
    
        public int getSumLevel() {
            return sumLevel;
        }
    
        public Long getAcntRid() {
            return acntRid;
        }
    
        public String getAcntId() {
            return acntId;
        }
    
        public Double getActualHours() {
        	actualHours = Double.valueOf(0);
        	for(int i = 0; i < 12; i++) {
        		if(dtoMonthData[i] != null){
        			actualHours += nvl(dtoMonthData[i].getActualHour());
        		}
        	}
            return actualHours;
        }
    
        public Date getBeginDate() {
            return beginDate;
        }
    
        public Date getDate() {
            return date;
        }
    
        public String getDateStr() {
            return dateStr;
        }
    
        public Date getEndDate() {
            return endDate;
        }
    
        public Double getUtilRate() {
        	if(getValidHours() == null || getActualHours()==null || getActualHours() == 0){
                return Double.valueOf(0);
            }
            return getValidHours() / getActualHours();
        }
    
        public Double getValidHours() {
        	validHours = Double.valueOf(0);
        	for(int i = 0; i < 12; i++) {
        		if(dtoMonthData[i] != null){
        			validHours += nvl(dtoMonthData[i].getValidHour());
        		}
        	}
            return validHours;
        }
    
        public Double getInvalidHours() {
        	invalidHours = Double.valueOf(0);
        	for(int i = 0; i < 12; i++) {
        		if(dtoMonthData[i] != null){
        			invalidHours += nvl(dtoMonthData[i].getInvalidHour());
        		}
        	}
            return invalidHours;
        }
    
        public String getAcntName() {
            return acntName;
        }
    
        public DtoMonthData getMonthData(int i) {
            return dtoMonthData[i];
        }
    
        public void setValidHours(Double validHours) {
            this.validHours = validHours;
        }
    
        public void setEndDate(Date endDate) {
            this.endDate = endDate;
        }
    
        public void setDateStr(String dateStr) {
            this.dateStr = dateStr;
        }
    
        public void setDate(Date date) {
            this.date = date;
        }
    
        public void setBeginDate(Date beginDate) {
            this.beginDate = beginDate;
        }
    
        public void setActualHours(Double actualHours) {
            this.actualHours = actualHours;
        }
    
        public void setAcntId(String acntId) {
            this.acntId = acntId;
        }
    
        public void setAcntRid(Long acntRid) {
            this.acntRid = acntRid;
        }
    
        public void setInvalidHours(Double invalidHours) {
            this.invalidHours = invalidHours;
        }
    
        public void setAcntName(String acntName) {
            this.acntName = acntName;
        }
    
        public void setSumLevel(int sumLevel) {
            this.sumLevel = sumLevel;
        }
    
        public void setMonthData(int index, DtoMonthData dto) {
            dtoMonthData[index] = dto;
        }
        
        private Double nvl(Double d) {
        	if(d != null) {
        		return d;
        	} else {
        		return Double.valueOf(0);
        	}
        }
    
        public Double getMayUtilRate() {
            if(dtoMonthData[4] == null) {
                return null;
            } else {
                return dtoMonthData[4].getUtilRate();
            }
        }
    
        public Double getAprUtilRate() {
            if(dtoMonthData[3] == null) {
                return null;
            } else {
                return dtoMonthData[3].getUtilRate();
            }
        }
    
        public Double getAugUtilRate() {
            if(dtoMonthData[7] == null) {
                return null;
            } else {
                return dtoMonthData[7].getUtilRate();
            }
        }
    
        public Double getFebUtilRate() {
            if(dtoMonthData[1] == null) {
                return null;
            } else {
                return dtoMonthData[1].getUtilRate();
            }
    
        }
    
        public Double getDecUtilRate() {
            if(dtoMonthData[11] == null) {
               return null;
           } else {
               return dtoMonthData[11].getUtilRate();
           }
        }
    
        public Double getJanUtilRate() {
            if(dtoMonthData[0] == null) {
                return null;
            } else {
                return dtoMonthData[0].getUtilRate();
            }
        }
    
        public Double getJulUtilRate() {
            if(dtoMonthData[6] == null) {
                return null;
            } else {
                return dtoMonthData[6].getUtilRate();
            }
        }
    
        public Double getJunUtilRate() {
            if(dtoMonthData[5] == null) {
                return null;
            } else {
                return dtoMonthData[5].getUtilRate();
            }
        }
    
        public Double getMarUtilRate() {
            if(dtoMonthData[2] == null) {
                return null;
            } else {
                return dtoMonthData[2].getUtilRate();
            }
        }
    
        public Double getNovUtilRate() {
            if(dtoMonthData[10] == null) {
                 return null;
             } else {
                 return dtoMonthData[10].getUtilRate();
             }
        }
    
        public Double getOctUtilRate() {
            if(dtoMonthData[9] == null) {
                return null;
            } else {
                return dtoMonthData[9].getUtilRate();
            }
        }
    
        public Double getSepUtilRate() {
            if(dtoMonthData[8] == null) {
                return null;
            } else {
                return dtoMonthData[8].getUtilRate();
            }
    
        }

        public Double getStandardHours() {
            standardHours = Double.valueOf(0);
            for(int i = 0; i < 12; i++) {
                if(dtoMonthData[i] != null){
                    standardHours += nvl(dtoMonthData[i].getStandardHour());
                }
            }
            return standardHours;
        }

        public void setStandardHours(Double standardHours) {
            this.standardHours = standardHours;
        }

}
