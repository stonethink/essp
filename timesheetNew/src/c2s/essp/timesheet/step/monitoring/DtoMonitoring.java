/*
 * Created on 2008-5-27
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package c2s.essp.timesheet.step.monitoring;

import java.util.Date;

import c2s.dto.DtoBase;
import c2s.essp.timesheet.step.DtoStatus;
import c2s.essp.timesheet.step.IDtoActivityFilterData;

public class DtoMonitoring extends DtoBase implements IDtoActivityFilterData {

    	public static final String BEAN_TYPE_WBS = "DtoMonitoring_WBS";
    
    	public static final String BEAN_TYPE_ACTIVITY = "DtoMonitoring_Activity";
    
    	public static final String BEAN_TYPE_STEP_GGROUP = "DtoMonitoring_Step_Group";
    
    	private String name;
    
    	private Long wbsId;
    
    	private String stepName;
    
    	private String status;
    
    	private String complete;
    
    	private String resourceNames;
    
        private String resourceIds;
        
        private String managerName;
        
    	private Date planStart;
    
    	private Date planFinish;
    
    	private Double planHours;
    
    	private Date actualStart;
    
    	private Date actualFinish;
    
    	private Double actualHours;
    	
    	private String beanType;
        
        private String workerId;
        
        private String managerId;
        
        private String wbsCode;
        
        private String categoryDes;
        
        private short colorIndex;
    
    	public short getColorIndex() {
            return colorIndex;
        }

        public void setColorIndex(short colorIndex) {
            this.colorIndex = colorIndex;
        }

        public void setWbsCode(String wbsCode) {
            this.wbsCode = wbsCode;
        }

        public String getWorkerId() {
            return workerId;
        }
    
        public void setWorkerId(String workerId) {
            this.workerId = workerId;
        }
    
        public void setManagerId(String managerId) {
            this.managerId = managerId;
        }
    
        public String getComplete() {
    		return complete;
    	}
    
    	public void setComplete(String complete) {
    		this.complete = complete;
    	}
    
    	public Date getActualFinish() {
    		return actualFinish;
    	}
    
    	public void setActualFinish(Date actualFinish) {
    		this.actualFinish = actualFinish;
    	}
    
    	public Double getActualHours() {
    		return actualHours;
    	}
    
    	public void setActualHours(Double actualHours) {
    		this.actualHours = actualHours;
    	}
    
    	public Date getActualStart() {
    		return actualStart;
    	}
    
    	public void setActualStart(Date actualStart) {
    		this.actualStart = actualStart;
    	}
    
    	public Double getPlanHours() {
    		return planHours;
    	}
    
    	public void setPlanHours(Double planHours) {
    		this.planHours = planHours;
    	}
    
    	public Date getPlanStart() {
    		return planStart;
    	}
    
    	public void setPlanStart(Date planStart) {
    		this.planStart = planStart;
    	}
    
    	public String getStatus() {
    		return status;
    	}
    
    	public void setStatus(String status) {
    		this.status = status;
    	}
    
    	public String getStepName() {
    		return stepName;
    	}
    
    	public void setStepName(String stepName) {
    		this.stepName = stepName;
    	}
        
    	public String getManagerName() {
            return managerName;
        }
    
        public void setManagerName(String managerName) {
            this.managerName = managerName;
        }
    
    
        public String getResourceNames() {
            return resourceNames;
        }
    
        public void setResourceNames(String resourceNames) {
            this.resourceNames = resourceNames;
        }
    
        public void setResourceIds(String resourceIds) {
            this.resourceIds = resourceIds;
        }
    
        public String getName() {
    		return name;
    	}
    
    	public void setName(String name) {
    		this.name = name;
    	}
    
    	public Long getWbsId() {
    		return wbsId;
    	}
    
    	public void setWbsId(Long wbsId) {
    		this.wbsId = wbsId;
    	}
    
    	public String getWbsCode() {
    		return wbsCode;
    	}
    
    	public String getWbsName() {
    		return name;
    	}
    
    	public String getManagerId() {
    		return managerId;
    	}
    
    	public String getResourceIds() {
    		return resourceIds;
    	}
    
    	/**
    	 * @return Returns the beanType.
    	 */
    	public String getBeanType() {
    		return beanType;
    	}
    
    	/**
    	 * @param beanType The beanType to set.
    	 */
    	public void setBeanType(String beanType) {
    		this.beanType = beanType;
    	}
    
    	/**
    	 * @return Returns the planFinish.
    	 */
    	public Date getPlanFinish() {
    		return planFinish;
    	}
    
    	/**
    	 * @param planFinish The planFinish to set.
    	 */
    	public void setPlanFinish(Date planFinish) {
    		this.planFinish = planFinish;
    	}
    	
    	public String getStatusIndicator() {
    		return DtoStatus.getStatus(planStart, planFinish, actualStart, actualFinish);
    	}

        public String getCategoryDes() {
            return categoryDes;
        }

        public void setCategoryDes(String categoryDes) {
            this.categoryDes = categoryDes;
        }

}
