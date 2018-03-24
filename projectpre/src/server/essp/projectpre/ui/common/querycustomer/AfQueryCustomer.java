package server.essp.projectpre.ui.common.querycustomer;

import org.apache.struts.action.ActionForm;

public class AfQueryCustomer extends ActionForm {
	private String customerId;
	private String shortName;
	private String paramKeys;
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getParamKeys() {
		return paramKeys;
	}
	public void setParamKeys(String paramKeys) {
		this.paramKeys = paramKeys;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
}
