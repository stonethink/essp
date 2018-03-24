package server.essp.hrbase.synchronization.syncexception.dao;

import java.util.List;

import db.essp.hrbase.HrbExceptionTemp;

public interface ISyncExceptionDao {
	
	/**
	 * ����һ���쳣��ת��Ϣ�ļ�¼
	 * @param exceptionTemp
	 */
	public void addException(HrbExceptionTemp exceptionTemp);

	/**
	 * �г�״̬ΪPending�Ľ�ת�쳣��Ϣ��¼
	 * @return
	 */
	public List listException();
	
	/**
	 * ����RID��ȡĳ���쳣��ת��Ϣ��¼
	 * @param rid
	 * @return
	 */
	public HrbExceptionTemp loadExceptionBy(Long rid);
	
	/**
	 * ����ĳ���쳣��ת��Ϣ��¼
	 * @param exception
	 */
	public void updateException(HrbExceptionTemp exception);
}
