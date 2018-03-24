package server.essp.projectpre.ui.common.queryaccount;

import org.apache.struts.action.ActionForm;

public class AfQueryAccount extends ActionForm {
	private String accountId;
	private String accountName;
	private String paramKeys;
    private String personKeys;
	public String getPersonKeys() {
        return personKeys;
    }
    public void setPersonKeys(String personKeys) {
        this.personKeys = personKeys;
    }
    public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getParamKeys() {
		return paramKeys;
	}
	public void setParamKeys(String paramKeys) {
		this.paramKeys = paramKeys;
	}
	
}
