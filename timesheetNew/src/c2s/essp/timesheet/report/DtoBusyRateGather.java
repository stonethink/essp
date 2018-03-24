/*
 * Created on 2008-6-27
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package c2s.essp.timesheet.report;

import java.util.Date;

import c2s.dto.DtoBase;

public class DtoBusyRateGather extends DtoBase implements ISumLevel{
        private Long acntRid;
        private String acntId;
        private String acntName;
        private Date date;
        private Double assginHours;
        private Double validHours;
        private Double invalidHours;
        private Double actualHours;
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
     
         public Double getBusyRate() {
            if(getValidHours() == null || getAssginHours()==null || getAssginHours() == 0){
                 return Double.valueOf(0);
             }
             return getValidHours() / getAssginHours();
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
     
         public Double getMayBusyRate() {
             if(dtoMonthData[4] == null) {
                 return null;
             } else {
                 return dtoMonthData[4].getBusyRate();
             }
         }
     
         public Double getAprBusyRate() {
             if(dtoMonthData[3] == null) {
                 return null;
             } else {
                 return dtoMonthData[3].getBusyRate();
             }
         }
     
         public Double getAugBusyRate() {
             if(dtoMonthData[7] == null) {
                 return null;
             } else {
                 return dtoMonthData[7].getBusyRate();
             }
         }
     
         public Double getFebBusyRate() {
             if(dtoMonthData[1] == null) {
                 return null;
             } else {
                 return dtoMonthData[1].getBusyRate();
             }
     
         }
     
         public Double getDecBusyRate() {
             if(dtoMonthData[11] == null) {
                return null;
            } else {
                return dtoMonthData[11].getBusyRate();
            }
         }
     
         public Double getJanBusyRate() {
             if(dtoMonthData[0] == null) {
                 return null;
             } else {
                 return dtoMonthData[0].getBusyRate();
             }
         }
     
         public Double getJulBusyRate() {
             if(dtoMonthData[6] == null) {
                 return null;
             } else {
                 return dtoMonthData[6].getBusyRate();
             }
         }
     
         public Double getJunBusyRate() {
             if(dtoMonthData[5] == null) {
                 return null;
             } else {
                 return dtoMonthData[5].getBusyRate();
             }
         }
     
         public Double getMarBusyRate() {
             if(dtoMonthData[2] == null) {
                 return null;
             } else {
                 return dtoMonthData[2].getBusyRate();
             }
         }
     
         public Double getNovBusyRate() {
             if(dtoMonthData[10] == null) {
                  return null;
              } else {
                  return dtoMonthData[10].getBusyRate();
              }
         }
     
         public Double getOctBusyRate() {
             if(dtoMonthData[9] == null) {
                 return null;
             } else {
                 return dtoMonthData[9].getBusyRate();
             }
         }
     
         public Double getSepBusyRate() {
             if(dtoMonthData[8] == null) {
                 return null;
             } else {
                 return dtoMonthData[8].getBusyRate();
             }
     
         }
    
         public Double getAssginHours() {
             assginHours = Double.valueOf(0);
             for(int i = 0; i < 12; i++) {
                 if(dtoMonthData[i] != null){
                     assginHours += nvl(dtoMonthData[i].getAssignHours());
                 }
             }
             return assginHours;
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
         
         public Double getStandardHours() {
             standardHours = Double.valueOf(0);
             for(int i = 0; i < 12; i++) {
                 if(dtoMonthData[i] != null){
                     standardHours += nvl(dtoMonthData[i].getStandardHour());
                 }
             }
             return standardHours;
         }
         
        public void setActualHours(Double actualHours) {
            this.actualHours = actualHours;
        }

        public void setStandardHours(Double standardHours) {
            this.standardHours = standardHours;
        }

        public void setAssginHours(Double assginHours) {
            this.assginHours = assginHours;
        }
}