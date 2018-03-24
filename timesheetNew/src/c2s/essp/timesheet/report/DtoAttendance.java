/*
 * Created on 2008-6-17
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package c2s.essp.timesheet.report;

import c2s.dto.DtoBase;

public class DtoAttendance extends DtoBase{
       private String seq;
       private String unitCode;
       private String projCode;
       private String empName;
       private String empId;
       private String lateNum;
       private Double fullHours = Double.valueOf(0);
       private Double actualHours = Double.valueOf(0);
       private Double projActHours = Double.valueOf(0);
       private Double inProjRate = Double.valueOf(0);
       private Double personMath = Double.valueOf(0);
       private Double outProjHours = Double.valueOf(0);
       private Double sickLeave = Double.valueOf(0);
       private Double privateLeave = Double.valueOf(0);
       private Double absentLeave = Double.valueOf(0);
       private Double shiftAdjsut = Double.valueOf(0);
       private Double annualLeave = Double.valueOf(0);
       private Double marrigeLeave = Double.valueOf(0);
       private Double funeralLeave = Double.valueOf(0);
       private Double maternityLeave = Double.valueOf(0);
       private Double medicalLeave = Double.valueOf(0);
       private Double workInjuryLeave = Double.valueOf(0);
       private Double lactationLeave = Double.valueOf(0);
       private Double othersDuduct = Double.valueOf(0);
       private Double othersNondeduct = Double.valueOf(0);
       private Double deductSum = Double.valueOf(0);
       private Double nondeductSum = Double.valueOf(0);
       private Double absenteeism = Double.valueOf(0);
       private String remark;
       
        public String getRemark() {
         return remark;
        }
        public void setRemark(String remark) {
        this.remark = remark;
        }
        public Double getAbsentLeave() {
            return absentLeave;
        }
        public void setAbsentLeave(Double absentLeave) {
            this.absentLeave = absentLeave;
        }
        public Double getActualHours() {
            return actualHours;
        }
        public void setActualHours(Double actualHours) {
            this.actualHours = actualHours;
        }
        public Double getAnnualLeave() {
            return annualLeave;
        }
        public void setAnnualLeave(Double annualLeave) {
            this.annualLeave = annualLeave;
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
        public Double getFullHours() {
            return fullHours;
        }
        public void setFullHours(Double fullHours) {
            this.fullHours = fullHours;
        }
        public Double getFuneralLeave() {
            return funeralLeave;
        }
        public void setFuneralLeave(Double funeralLeave) {
            this.funeralLeave = funeralLeave;
        }
        public Double getInProjRate() {
            return inProjRate;
        }
        public void setInProjRate(Double inProjRate) {
            this.inProjRate = inProjRate;
        }
 
        public Double getMarrigeLeave() {
            return marrigeLeave;
        }
        public void setMarrigeLeave(Double marrigeLeave) {
            this.marrigeLeave = marrigeLeave;
        }
        public Double getMaternityLeave() {
            return maternityLeave;
        }
        public void setMaternityLeave(Double maternityLeave) {
            this.maternityLeave = maternityLeave;
        }
        public Double getOutProjHours() {
            return outProjHours;
        }
        public void setOutProjHours(Double outProjHours) {
            this.outProjHours = outProjHours;
        }
        public Double getPersonMath() {
            return personMath;
        }
        public void setPersonMath(Double personMath) {
            this.personMath = personMath;
        }
        public Double getPrivateLeave() {
            return privateLeave;
        }
        public void setPrivateLeave(Double privateLeave) {
            this.privateLeave = privateLeave;
        }
        public Double getProjActHours() {
            return projActHours;
        }
        public void setProjActHours(Double projActHours) {
            this.projActHours = projActHours;
        }
        public String getProjCode() {
            return projCode;
        }
        public void setProjCode(String projCode) {
            this.projCode = projCode;
        }
        public String getSeq() {
            return seq;
        }
        public void setSeq(String seq) {
            this.seq = seq;
        }
        public Double getShiftAdjsut() {
            return shiftAdjsut;
        }
        public void setShiftAdjsut(Double shiftAdjsut) {
            this.shiftAdjsut = shiftAdjsut;
        }
        public Double getSickLeave() {
            return sickLeave;
        }
        public void setSickLeave(Double sickLeave) {
            this.sickLeave = sickLeave;
        }
        public Double getDeductSum() {
            return deductSum;
        }
        public void setDeductSum(Double deductSum) {
            this.deductSum = deductSum;
        }
        public Double getNondeductSum() {
            return nondeductSum;
        }
        public void setNondeductSum(Double nondeductSum) {
            this.nondeductSum = nondeductSum;
        }
        public Double getOthersDuduct() {
            return othersDuduct;
        }
        public void setOthersDuduct(Double othersDuduct) {
            this.othersDuduct = othersDuduct;
        }
        public Double getOthersNondeduct() {
            return othersNondeduct;
        }
        public void setOthersNondeduct(Double othersNondeduct) {
            this.othersNondeduct = othersNondeduct;
        }
        public String getUnitCode() {
            return unitCode;
        }
        public void setUnitCode(String unitCode) {
            this.unitCode = unitCode;
        }
        public Double getWorkInjuryLeave() {
            return workInjuryLeave;
        }
        public void setWorkInjuryLeave(Double workInjuryLeave) {
            this.workInjuryLeave = workInjuryLeave;
        }
        public Double getMedicalLeave() {
            return medicalLeave;
        }
        public void setMedicalLeave(Double medicalLeave) {
            this.medicalLeave = medicalLeave;
        }
        public Double getLactationLeave() {
            return lactationLeave;
        }
        public void setLactationLeave(Double lactationLeave) {
            this.lactationLeave = lactationLeave;
        }
        public String getLateNum() {
            return lateNum;
        }
        public void setLateNum(String lateNum) {
            this.lateNum = lateNum;
        }
        public Double getAbsenteeism() {
            return absenteeism;
        }
        public void setAbsenteeism(Double absenteeism) {
          this.absenteeism = absenteeism;
        }
}
