package server.essp.hrbase.synchronization.dao;

import server.essp.hrbase.dept.viewbean.VbSyncUnitBase;

public interface ISyncProjectpreDao {
	
	
	/**
	 * ��projectpreϵͳ���벿����Ϣ��¼
	 * @param unitBase
	 */
	public void add(VbSyncUnitBase unitBase);
	
	/**
	 * ����projectpreϵͳ�Ĳ�����Ϣ
	 * @param unitBase
	 */
	public void update(VbSyncUnitBase unitBase);
	
	/**
	 * ɾ��projectpreϵͳ�Ĳ��ţ��޸�is_enable״̬Ϊ0��
	 * @param unitCode
	 */
	public void delete(String unitCode);
	
	/**
	 * �����벿�Ź�������Ա
	 * @param rid
	 * @param acntRid
	 * @param personType
	 * @param loginId
	 * @param name
	 */
	public void addPerson(Long rid, Long acntRid, 
			       String personType, String loginId, String name);
	
	/**
	 * �����벿����ص���Ա����
	 * @param acntRid
	 * @param personType
	 * @param loginId
	 * @param name
	 */
	public void updatePerson(Long acntRid, String personType, 
			                 String loginId, String name);
	
	/**
	 * ����ר�������ȡ�ñʼ�¼��RID
	 * @param acntId
	 * @return
	 */
	public Long getPPAcntRid(String acntId);

}
