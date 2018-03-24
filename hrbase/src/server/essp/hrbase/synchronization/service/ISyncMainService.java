package server.essp.hrbase.synchronization.service;

import java.util.List;

import db.essp.hrbase.HrbHumanBaseLog;
import db.essp.hrbase.HrbUnitBase;

public interface ISyncMainService {
	
	/**
	 * ͬ����Ա�������ϵ�����ϵͳ
	 * @param dataList
	 */
	public boolean synchronise(List<HrbHumanBaseLog> dataList);
	
	/**
	 * ��ѯ��Ҫͬ������Ա��������
	 * @return
	 */
	public List<HrbHumanBaseLog> searchDataForSync();
	
	/**
	 * ͬ������������Ϣ�Ĳ���
	 * @param unitBase
	 */
	public boolean addUnit(HrbUnitBase unitBase);
	
	/**
	 * ͬ�����²�����Ϣ�Ĳ���
	 * @param unitBase
	 */
	public boolean updateUnit(HrbUnitBase unitBase);
	
	/**
	 * ͬ��ɾ��������Ϣ��ʵ��Ϊ�޸Ĳ���״̬��
	 * @param unitBase
	 * @return
	 */
	public boolean deleteUnit(HrbUnitBase unitBase);

}
