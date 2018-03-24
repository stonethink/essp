/*
 * Created on 2008-3-11
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package c2s.essp.timesheet.report;

import java.util.List;

import c2s.dto.DtoBase;

public class DtoTsByPeriod extends DtoBase{
    
        private String empName;
        private String empId;
        private String unitCode;
        private String dateStr;
        private List actHoursList;
        private List leaveHoursList;
        private List otHoursList;
        private List standardHoursList;
        
        public List getActHoursList() {
            return actHoursList;
        }
        public void setActHoursList(List actHoursList) {
            this.actHoursList = actHoursList;
        }
        public String getDateStr() {
            return dateStr;
        }
        public void setDateStr(String dateStr) {
            this.dateStr = dateStr;
        }
        public String getEmpId() {
            return empId;
        }
        public void setEmpId(String empId) {
            this.empId = empId;
        }
        public String getEmpName() {
            return empName;
        }
        public void setEmpName(String empName) {
            this.empName = empName;
        }
        public List getLeaveHoursList() {
            return leaveHoursList;
        }
        public void setLeaveHoursList(List leaveHoursList) {
            this.leaveHoursList = leaveHoursList;
        }
        public List getOtHoursList() {
            return otHoursList;
        }
        public void setOtHoursList(List otHoursList) {
            this.otHoursList = otHoursList;
        }
        public String getUnitCode() {
            return unitCode;
        }
        public void setUnitCode(String unitCode) {
            this.unitCode = unitCode;
        }
        public List getStandardHoursList() {
            return standardHoursList;
        }
        public void setStandardHoursList(List standardHoursList) {
            this.standardHoursList = standardHoursList;
        }
    

   
}
