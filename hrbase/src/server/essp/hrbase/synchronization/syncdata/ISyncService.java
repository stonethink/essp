package server.essp.hrbase.synchronization.syncdata;

import server.framework.common.BusinessException;
import db.essp.hrbase.*;

public interface ISyncService {
	
	
	/**
	 * ͬ����Ա��������
	 * @param humanBaseLog
	 */
	public void syncHuman(HrbHumanBaseLog humanBaseLog);
	
	/**
	 * ����������Ϣ������ϵͳ
	 * @param unitBase
	 * @throws BusinessException
	 */
	public void addUnit(HrbUnitBase unitBase) throws BusinessException;
	
	/**
	 * ���²�����Ϣ������ϵͳ
	 * @param unitBase
	 * @throws BusinessException
	 */
	public void updateUnit(HrbUnitBase unitBase) throws BusinessException;
	
	/**
	 * ������ϵͳɾ��������Ϣ��ʵ��Ϊ�޸�״̬��
	 * @param unitCode
	 */
	public void deleteUnit(String unitCode);
	
	/**
	 * ��ȡ����ʵ�ֵ�����
	 * @return
	 */
	public String getFunctionName();
	
	/**
	 * �����ת�쳣��¼ʹ���ٴν�ת
	 * @param exception
	 */
	public void carryForward(HrbExceptionTemp exception);

}
