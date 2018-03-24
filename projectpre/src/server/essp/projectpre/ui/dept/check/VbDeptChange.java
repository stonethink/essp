package server.essp.projectpre.ui.dept.check;

import java.lang.reflect.Field;

public class VbDeptChange {
	
	public String applicantName;
	public String applicationDate;
	public String acntId;
	public String acntName;
	public String acntFullName;
	public String achieveBelong;
	public String parentDept;
	public String deptManager;
	public String bdName;
	public String tcsName;
	public String isEnable;
	
	public String acntIdBefore;
	public String acntNameBefore;
	public String acntFullNameBefore;
	public String achieveBelongBefore;
	public String parentDeptBefore;
	public String deptManagerBefore;
	public String bdNameBefore;
	public String tcsNameBefore;
	public String isEnableBefore;
	
	public String remark;
	
	 public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPropertyHtml(String property){
	        String propertyBefore = property + "Before";
	        try {
	            Field field = this.getClass().getField(property);
	            Field fieldBefore = this.getClass().getField(propertyBefore);
	            String value = "";
	            String valueBefore ="";
	            if(field.get(this)!=null){
	               value = field.get(this).toString();
	            }
	            if(fieldBefore.get(this)!=null){
	               valueBefore = fieldBefore.get(this).toString();         
	            }
	            if(!value.equals(valueBefore)){
	                return "<font color='red'>"+value+"</font>";
	            }
	            return value;
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return "";
	    }

	public String getAchieveBelong() {
		return getPropertyHtml("achieveBelong");
	}

	public String getAcntFullName() {
		return getPropertyHtml("acntFullName");
	}

	public String getAcntId() {
		return getPropertyHtml("acntId");
	}

	public String getAcntName() {
		return getPropertyHtml("acntName");
	}

	public String getBdName() {
		return getPropertyHtml("bdName");
	}

	public String getDeptManager() {
		return getPropertyHtml("deptManager");
	}

	public String getIsEnable() {
		return getPropertyHtml("isEnable");
	}

	public String getParentDept() {
		return getPropertyHtml("parentDept");
	}

	public String getAchieveBelongBefore() {
		return achieveBelongBefore;
	}

	public void setAchieveBelongBefore(String achieveBelongBefore) {
		this.achieveBelongBefore = achieveBelongBefore;
	}

	public String getAcntFullNameBefore() {
		return acntFullNameBefore;
	}

	public void setAcntFullNameBefore(String acntFullNameBefore) {
		this.acntFullNameBefore = acntFullNameBefore;
	}

	public String getAcntIdBefore() {
		return acntIdBefore;
	}

	public void setAcntIdBefore(String acntIdBefore) {
		this.acntIdBefore = acntIdBefore;
	}

	public String getAcntNameBefore() {
		return acntNameBefore;
	}

	public void setAcntNameBefore(String acntNameBefore) {
		this.acntNameBefore = acntNameBefore;
	}

	public String getBdNameBefore() {
		return bdNameBefore;
	}

	public void setBdNameBefore(String bdNameBefore) {
		this.bdNameBefore = bdNameBefore;
	}

	public String getDeptManagerBefore() {
		return deptManagerBefore;
	}

	public void setDeptManagerBefore(String deptManagerBefore) {
		this.deptManagerBefore = deptManagerBefore;
	}

	public String getIsEnableBefore() {
		return isEnableBefore;
	}

	public void setIsEnableBefore(String isEnableBefore) {
		this.isEnableBefore = isEnableBefore;
	}

	public String getParentDeptBefore() {
		return parentDeptBefore;
	}

	public void setParentDeptBefore(String parentDeptBefore) {
		this.parentDeptBefore = parentDeptBefore;
	}

	public String getTcsNameBefore() {
		return tcsNameBefore;
	}

	public void setTcsNameBefore(String tcsNameBefore) {
		this.tcsNameBefore = tcsNameBefore;
	}

	public void setAchieveBelong(String achieveBelong) {
		this.achieveBelong = achieveBelong;
	}

	public void setAcntFullName(String acntFullName) {
		this.acntFullName = acntFullName;
	}

	public void setAcntId(String acntId) {
		this.acntId = acntId;
	}

	public void setAcntName(String acntName) {
		this.acntName = acntName;
	}

	public void setBdName(String bdName) {
		this.bdName = bdName;
	}

	public void setDeptManager(String deptManager) {
		this.deptManager = deptManager;
	}

	public void setIsEnable(String isEnable) {
		this.isEnable = isEnable;
	}

	public void setParentDept(String parentDept) {
		this.parentDept = parentDept;
	}

	public void setTcsName(String tcsName) {
		this.tcsName = tcsName;
	}

	public String getTcsName() {
		return getPropertyHtml("tcsName");
	}

	public String getApplicantName() {
		return applicantName;
	}

	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}

	public String getApplicationDate() {
		return applicationDate;
	}

	public void setApplicationDate(String applicationDate) {
		this.applicationDate = applicationDate;
	}
	
	

}
