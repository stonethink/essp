package server.essp.hrbase.synchronization.dao;

import db.essp.hrbase.HrbUnitBase;

public interface ISync104HrmsDao {
	
	/**
	 * ��������
	 * @param unitBase
	 */
	public void add(HrbUnitBase unitBase);
	
	/**
	 * ���²���
	 * @param unitBase
	 */
	public void update(HrbUnitBase unitBase);
	
	/**
	 * ɾ������
	 * @param unitBase
	 */
	public void delete(HrbUnitBase unitBase);

}
