package c2s.essp.timesheet.report;

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
public class DtoMonthData extends DtoBase{
       private Double actualHour;
       private Double validHour;
       private Double standardHour;
       private Double invalidHour;
       private Double assignHours;
    
        public Double getActualHour() {
            return actualHour;
        }
    
        public Double getInvalidHour() {
            return invalidHour;
        }
    
        public Double getUtilRate() {
             if(getValidHour() == null || getStandardHour()==null || getStandardHour() == 0){
                 return Double.valueOf(0);
             }
             return getValidHour() / getStandardHour();
         }
    
        public Double getBusyRate() {
            if(getValidHour() == null || getAssignHours()==null || getAssignHours() == 0){
                return Double.valueOf(0);
            }
            return getValidHour() / getAssignHours();
        }
   
    
        public Double getValidHour() {
            return validHour;
        }
    
        public void setActualHour(Double actualHour) {
            this.actualHour = actualHour;
        }
    
        public void setInvalidHour(Double invalidHour) {
            this.invalidHour = invalidHour;
        }
    
    
        public void setValidHour(Double validHour) {
            this.validHour = validHour;
        }

        public Double getStandardHour() {
            return standardHour;
        }

        public void setStandardHour(Double standardHour) {
            this.standardHour = standardHour;
        }

        public Double getAssignHours() {
            return assignHours;
        }

        public void setAssignHours(Double assignHours) {
            this.assignHours = assignHours;
        }
}
