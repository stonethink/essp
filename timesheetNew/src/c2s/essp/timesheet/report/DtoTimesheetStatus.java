/*
 * Created on 2007-11-27
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package c2s.essp.timesheet.report;

import java.util.Date;

import c2s.dto.DtoBase;

public class DtoTimesheetStatus extends DtoBase {

        private String acntName;
        private String managerId;
        private String managerName;
        private String empId;
        private String empName;
        private String resManagerId;
        private String resManagerName;
        private String status;
        private String isFillTimesheet;
        private Date begin;
        private Date end;
        private String unitCode;
        private String site;
        private int num = 0;
        private int needFillNum = 0;
    
        public int getNum() {
            return num;
        }
        public void setNum(int num) {
            this.num = num;
        }
        public String getAcntName() {
            return acntName;
        }
        public void setAcntName(String acntName) {
            this.acntName = acntName;
        }
        public Date getBegin() {
            return begin;
        }
        public void setBegin(Date begin) {
            this.begin = begin;
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
        public Date getEnd() {
            return end;
        }
        public void setEnd(Date end) {
            this.end = end;
        }
        public String getManagerId() {
            return managerId;
        }
        public void setManagerId(String managerId) {
            this.managerId = managerId;
        }
        public String getManagerName() {
            return managerName;
        }
        public void setManagerName(String managerName) {
            this.managerName = managerName;
        }
        public String getResManagerId() {
            return resManagerId;
        }
        public void setResManagerId(String resManagerId) {
            this.resManagerId = resManagerId;
        }
        public String getResManagerName() {
            return resManagerName;
        }
        public void setResManagerName(String resManagerName) {
            this.resManagerName = resManagerName;
        }
        public String getStatus() {
            return status;
        }
        public void setStatus(String status) {
            this.status = status;
        }
        public String getIsFillTimesheet() {
            return isFillTimesheet;
        }
        public void setIsFillTimesheet(String isFillTimesheet) {
            this.isFillTimesheet = isFillTimesheet;
        }
        public String getUnitCode() {
            return unitCode;
        }
        public void setUnitCode(String unitCode) {
            this.unitCode = unitCode;
        }
        public int getNeedFillNum() {
            return needFillNum;
        }
        public void setNeedFillNum(int needFillNum) {
            this.needFillNum = needFillNum;
        }
        public String getSite() {
            return site;
        }
        public void setSite(String site) {
            this.site = site;
        }
}
