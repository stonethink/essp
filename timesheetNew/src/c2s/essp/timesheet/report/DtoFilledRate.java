/*
 * Created on 2008-4-10
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package c2s.essp.timesheet.report;

import c2s.dto.DtoBase;

public class DtoFilledRate extends DtoBase implements ISumLevel{
        private String acntId;
        private int needFillNum = 0;
        private int needFillEmpNum = 0;
        private int unfillNum = 0;
        private Double fillRate = Double.valueOf(0);
        private int sumLevel;
    
        public int getSumLevel() {
            return sumLevel;
        }
        
        public void setSumLevel(int sumLevel) {
            this.sumLevel = sumLevel;
        }
    
        public String getAcntId() {
            return acntId;
        }
    
        public void setAcntId(String acntId) {
            this.acntId = acntId;
        }
    
        public Double getFillRate() {
            return fillRate;
        }
    
        public void setFillRate(Double fillRate) {
            this.fillRate = fillRate;
        }
    
        public int getNeedFillEmpNum() {
            return needFillEmpNum;
        }

        public void setNeedFillEmpNum(int needFillEmpNum) {
            this.needFillEmpNum = needFillEmpNum;
        }

        public int getNeedFillNum() {
            return needFillNum;
        }
    
        public void setNeedFillNum(int needFillNum) {
            this.needFillNum = needFillNum;
        }
    
        public int getUnfillNum() {
            return unfillNum;
        }
    
        public void setUnfillNum(int unfillNum) {
            this.unfillNum = unfillNum;
        }
        
}
