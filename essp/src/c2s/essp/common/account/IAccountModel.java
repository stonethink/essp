package c2s.essp.common.account;

public interface IAccountModel {
	String getAccountCode();
	String getAccountName();
    void setAccountCode(String code);
	void setAccountName(String name);
}
