package server.essp.hrbase.synchronization.dao;

import server.essp.hrbase.dept.viewbean.VbSyncUnitBase;
import server.essp.hrbase.humanbase.viewbean.VbHumanBase;
import server.essp.hrbase.humanbase.viewbean.VbHumanBaseLog;

public interface ISyncTimesheetDao {
	
	/**
	 * ��timesheetϵͳ��������Ա������Ϣ
	 * @param hrbHuman
	 */
	public void add(VbHumanBaseLog hrbHumanLog);
	
	/**
	 * ����timesheetϵͳ����Ա������Ϣ
	 * @param hrbHumanLog HrbHumanBaseLog
	 */
	public void update(VbHumanBaseLog hrbHumanLog);
	
	/**
	 * ɾ��timesheetϵͳ�е���Ա��Ϣ��¼
	 * @param employeeId
	 */
	public void delete(String employeeId);
	
	/**
	 * ����timesheetϵͳ�Ĳ�����Ϣ
	 * @param unitBase
	 */
	public void addUnit(VbSyncUnitBase unitBase);
	
	/**
	 * ����timesheetϵͳ�Ĳ�����Ϣ
	 * @param unitBase
	 */
	public void updateUnit(VbSyncUnitBase unitBase);
	
	/**
	 * ɾ��timesheetϵͳ�Ĳ�����Ϣ��ʵ���޸�״̬��
	 * @param unitCode
	 */
	public void deleteUnit(String unitCode);
	
	/**
	 * ������Ա��������
	 * @param hrbHumanBase
	 * @param accountRid
	 */
	public void insertLabor(VbHumanBase hrbHumanBase, Long accountRid);
	
	/**
	 * ���˼���Labor Resources
	 * @param hrbHumanLog
	 */
	public void addHuman2Labor(VbHumanBaseLog hrbHumanLog);
	
	/**
	 * ����Labor Resources�˵Ĳ���
	 * @param hrbHumanLog VbHumanBaseLog
	 */
	public boolean updateHumanInLabor(VbHumanBaseLog hrbHumanLog);
	
	/**
	 * ɾ��Labor Resources�в������Ѿ���ɾ���˵��˵�����
	 * @param employeeId
	 * @param unitCode
	 */
	public void deleteHumanInLabor(String employeeId, String unitCode);

}
