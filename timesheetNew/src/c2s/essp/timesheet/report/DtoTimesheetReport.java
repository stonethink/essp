/*
 * Created on 2008-3-11
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package c2s.essp.timesheet.report;

import c2s.dto.DtoBase;

public class DtoTimesheetReport extends DtoBase {
       public final static String DTO_RESULT_LIST = "resultList";   
    
       private String projId;
       private String projName;
       private String unitCode;
       private String description;
       private String managerName;
       private Long detailRid;
       private Double monHours = 0D;
       private Double tueHours = 0D;
       private Double wedHours = 0D;
       private Double thursHours = 0D;
       private Double friHours = 0D;
       private Double satHours = 0D;
       private Double sunHours = 0D;
       private Double sumHours = 0D;
       private short colorIndex;
       
        public String getDescription() {
            return description;
        }
        public void setDescription(String description) {
            this.description = description;
        }
        public Double getFriHours() {
            return friHours;
        }
        public void setFriHours(Double friHours) {
            this.friHours = friHours;
        }
        public Double getMonHours() {
            return monHours;
        }
        public void setMonHours(Double monHours) {
            this.monHours = monHours;
        }
        public String getProjId() {
            return projId;
        }
        public void setProjId(String projId) {
            this.projId = projId;
        }
        public String getProjName() {
            return projName;
        }
        public void setProjName(String projName) {
            this.projName = projName;
        }
        public Double getSatHours() {
            return satHours;
        }
        public void setSatHours(Double satHours) {
            this.satHours = satHours;
        }
        public Double getSumHours() {
            return sumHours;
        }
        public void setSumHours(Double sumHours) {
            this.sumHours = sumHours;
        }
        public Double getSunHours() {
            return sunHours;
        }
        public void setSunHours(Double sunHours) {
            this.sunHours = sunHours;
        }
        public Double getThursHours() {
            return thursHours;
        }
        public void setThursHours(Double thursHours) {
            this.thursHours = thursHours;
        }
        public Double getTueHours() {
            return tueHours;
        }
        public void setTueHours(Double tueHours) {
            this.tueHours = tueHours;
        }
        public String getUnitCode() {
            return unitCode;
        }
        public void setUnitCode(String unitCode) {
            this.unitCode = unitCode;
        }
        public Double getWedHours() {
            return wedHours;
        }
        public void setWedHours(Double wedHours) {
            this.wedHours = wedHours;
        }
        public short getColorIndex() {
            return colorIndex;
        }
        public void setColorIndex(short colorIndex) {
            this.colorIndex = colorIndex;
        }
        public String getManagerName() {
            return managerName;
        }
        public void setManagerName(String managerName) {
            this.managerName = managerName;
        }
        public Long getDetailRid() {
            return detailRid;
        }
        public void setDetailRid(Long detailRid) {
            this.detailRid = detailRid;
        }
           
}
