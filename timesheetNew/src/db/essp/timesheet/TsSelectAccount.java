package db.essp.timesheet;

import java.util.Date;

public class TsSelectAccount implements java.io.Serializable {

	// Fields
	private String loginId;

	private String accountId;

	private Date rct;

	private Date rut;

	private String rcu;

	private String ruu;

	/**
	 * @return Returns the rct.
	 */
	public Date getRct() {
		return rct;
	}

	/**
	 * @param rct
	 *            The rct to set.
	 */
	public void setRct(Date rct) {
		this.rct = rct;
	}

	/**
	 * @return Returns the rcu.
	 */
	public String getRcu() {
		return rcu;
	}

	/**
	 * @param rcu
	 *            The rcu to set.
	 */
	public void setRcu(String rcu) {
		this.rcu = rcu;
	}

	/**
	 * @return Returns the rut.
	 */
	public Date getRut() {
		return rut;
	}

	/**
	 * @param rut
	 *            The rut to set.
	 */
	public void setRut(Date rut) {
		this.rut = rut;
	}

	/**
	 * @return Returns the ruu.
	 */
	public String getRuu() {
		return ruu;
	}

	/**
	 * @param ruu
	 *            The ruu to set.
	 */
	public void setRuu(String ruu) {
		this.ruu = ruu;
	}

	/**
	 * @return Returns the accountId.
	 */
	public String getAccountId() {
		return accountId;
	}

	/**
	 * @param accountId
	 *            The accountId to set.
	 */
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	/**
	 * @return Returns the loginId.
	 */
	public String getLoginId() {
		return loginId;
	}

	/**
	 * @param loginId
	 *            The loginId to set.
	 */
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

}
