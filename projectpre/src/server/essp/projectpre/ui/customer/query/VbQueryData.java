package server.essp.projectpre.ui.customer.query;

import java.util.List;

public class VbQueryData {
	/**
     * װbd����Դ��LIST
	 */
	private List bdList;
    /**
     *  װsite����Դ��LIST
     */
	private List siteList;
    /**
     *  װ�ͻ����������Դ��LIST
     */
	private List parameterClassList;
    /**
     *  װ�ͻ�����������Դ��LIST
     */
	private List parameterCountryList;

	
	/**
     * setBdList
     * @param bdList
	 */
	public void setBdList(List bdList) {
		this.bdList = bdList;
	}
	/**
     * getBdList
     * @return List
	 */
	public List getBdList() {
		return bdList;
	}
	
	/**
     * setSiteList
     * @param siteList
	 */
	public void setSiteList(List siteList) {
		this.siteList = siteList;
	}
	/**
     * getSiteList
     * @return List
	 */
	public List getSiteList() {
		return siteList;
	}

	/**
     * setParameterClassList
     * @param parameterClassList
	 */
	public void setParameterClassList(List parameterClassList) {
		this.parameterClassList = parameterClassList;
	}
	/**
     * getParameterClassList
     * @return List
	 */
	public List getParameterClassList() {
		return parameterClassList;
	}
	
	/**
     * setParameterCountryList
     * @param parameterCountryList
	 */
	public void setParameterCountryList(List parameterCountryList) {
		this.parameterCountryList = parameterCountryList;
	}
	/**
     * getParameterCountryList
     * @return List
	 */
	public List getParameterCountryList() {
		return parameterCountryList;
	}

}
