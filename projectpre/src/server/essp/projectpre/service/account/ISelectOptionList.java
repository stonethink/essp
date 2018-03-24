package server.essp.projectpre.service.account;

import java.util.List;

public interface ISelectOptionList {
	
	/**
	 * ��ȡ���Г��c
	 * @return
	 */
	public List getExecSiteList();
	
	/**
	 * ��ȡ�ɱ��w�ن�λ
	 * @return
	 */
	public List getCostAttachBdList();
	
	/**
	 * ��ȡ�I�Ձ�Դ
	 * @return
	 */
	public List getBizSourceList();
	
	/**
	 * ���ҵ������
	 * @return
	 */
	public List getAchieveBelongList();
	
	/**
	 * ��ȡ��������
	 *  ������Customer��Research��Dept
	 * @return
	 */
	public List getAcntAttributeList();
	
	/**
	 * ��ȡ�������
	 * @return
	 */
	public List getAcntTypeList();
	
	/**
	 * ��ȡ�aƷ���
	 * @return
	 */
	public List getProductTypeList();
	
	/**
	 * ��ȡ�aƷ����
	 * @return
	 */
	public List getProductPropertyList();
	
	/**
	 * ��ȡ�����Ŀ
	 * @return
	 */
	public List getWorkItemList();
	
	/**
	 * ��ȡ���g�I��
	 * @return
	 */
	public List getTechnicalAreaList();
	
	/**
	 * ֧Ԯ�Z��
	 * @return
	 */
	List getSupportLangugeList();
	
	/**
	 *  ����kind���ͻ�ȡ��Ӧ��CODE LIST
	 *  kind�����У�
	 *  ProjectType��ProductType��ProductAttribute��WorkItem��TechnicalDomain��OriginalLanguage��TranslationLanguage��BusinessType��CountryCode
	 * @param kind
	 * @return
	 */
	public List getParameterListByKind(String kind);
    
	/**
     * �������ͻ�ȡ���Բ���
     * @param kind
     * @return
     */
    public List getLanguageParameterListByKind(String kind);
}
