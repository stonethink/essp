/*
 * Created on 2008-2-20
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package c2s.essp.timesheet.report;

import c2s.dto.DtoBase;

public class DtoStatusHuman extends DtoBase implements ISumLevel{
        private String begin;
        private String end;
        private int unfillNum;
        private int activeNum;
        private int submittedNum;
        private int approvedNum;
        private int pmApprovedNum;
        private int rmApprovedNum;
        private int rejectedNum;
        private int sum;
        private int needFillNum;
        private int sumLevel;
        
        public int getSum() {
            return sum;
        }
        public void setSum(int sum) {
            this.sum = sum;
        }
        public String getBegin() {
            return begin;
        }
        public void setBegin(String begin) {
            this.begin = begin;
        }
        public String getEnd() {
            return end;
        }
        public void setEnd(String end) {
            this.end = end;
        }
        public int getActiveNum() {
            return activeNum;
        }
        public void setActiveNum(int activeNum) {
            this.activeNum = activeNum;
        }
        public int getApprovedNum() {
            return approvedNum;
        }
        public void setApprovedNum(int approvedNum) {
            this.approvedNum = approvedNum;
        }
        public int getPmApprovedNum() {
            return pmApprovedNum;
        }
        public void setPmApprovedNum(int pmApprovedNum) {
            this.pmApprovedNum = pmApprovedNum;
        }
        public int getRejectedNum() {
            return rejectedNum;
        }
        public void setRejectedNum(int rejectedNum) {
            this.rejectedNum = rejectedNum;
        }
        public int getSubmittedNum() {
            return submittedNum;
        }
        public void setSubmittedNum(int submittedNum) {
            this.submittedNum = submittedNum;
        }
        public int getUnfillNum() {
            return unfillNum;
        }
        public void setUnfillNum(int unfillNum) {
            this.unfillNum = unfillNum;
        }
        
        public void setSumLevel(int sumLevel) {
            this.sumLevel = sumLevel;
        }
    
        public int getSumLevel() {
            return sumLevel;
        }
        public int getNeedFillNum() {
            return needFillNum;
        }
        public void setNeedFillNum(int needFillNum) {
            this.needFillNum = needFillNum;
        }
        public int getRmApprovedNum() {
            return rmApprovedNum;
        }
        public void setRmApprovedNum(int rmApprovedNum) {
            this.rmApprovedNum = rmApprovedNum;
        }
    
}
