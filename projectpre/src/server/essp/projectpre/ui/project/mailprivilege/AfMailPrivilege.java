package server.essp.projectpre.ui.project.mailprivilege;

import java.util.List;

import org.apache.struts.action.ActionForm;

public class AfMailPrivilege extends ActionForm{
	private String rid;
	private String loginId;
	private String loginName;
    private String domain;
	private String addInform;
	private String addAudit;
	private String changeInform;
	private String changeAudit;
	private String closeAudit;
	private String closeInform;
	private String dataScope;
	private List dataScopeList;
	
	public String getAddAudit() {
		return addAudit;
	}
	public void setAddAudit(String addAudit) {
		this.addAudit = addAudit;
	}
	public String getAddInform() {
		return addInform;
	}
	public void setAddInform(String addInform) {
		this.addInform = addInform;
	}
	public String getChangeAudit() {
		return changeAudit;
	}
	public void setChangeAudit(String changeAudit) {
		this.changeAudit = changeAudit;
	}
	public String getChangeInform() {
		return changeInform;
	}
	public void setChangeInform(String changeInform) {
		this.changeInform = changeInform;
	}
	public String getCloseAudit() {
		return closeAudit;
	}
	public void setCloseAudit(String closeAudit) {
		this.closeAudit = closeAudit;
	}
	public String getCloseInform() {
		return closeInform;
	}
	public void setCloseInform(String closeInform) {
		this.closeInform = closeInform;
	}

	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public List getDataScopeList() {
		return dataScopeList;
	}
	public void setDataScopeList(List dataScopeList) {
		this.dataScopeList = dataScopeList;
	}
	public String getRid() {
		return rid;
	}
	public void setRid(String rid) {
		this.rid = rid;
	}
	public String getDataScope() {
		return dataScope;
	}
	public void setDataScope(String dataScope) {
		this.dataScope = dataScope;
	}

	
}
