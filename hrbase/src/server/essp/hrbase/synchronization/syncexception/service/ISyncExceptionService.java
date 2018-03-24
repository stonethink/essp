package server.essp.hrbase.synchronization.syncexception.service;

import java.util.List;

import server.essp.hrbase.synchronization.syncexception.viewbean.VbSyncException;
import db.essp.hrbase.*;

public interface ISyncExceptionService {
	
	/**
	 * �г�״̬ΪPending���쳣��¼
	 * @return
	 */
	public List listException();
	
	/**
	 * ����rid��ȡ�쳣��Ϣ��¼��viewbean
	 * @param rid
	 * @return
	 */
	public VbSyncException loadExceptionByRid(Long rid);
	
	/**
	 * �����쳣��¼��Ϣ
	 * @param exception
	 */
	public void updateException(HrbExceptionTemp exception);
	
	/**
	 * �����쳣��¼
	 * @param exceptionTemp
	 */
	public void addException(HrbExceptionTemp exceptionTemp);
	
	/**
	 * ͨ��RID��ȡ�쳣��¼
	 * @param rid
	 * @return
	 */
	public HrbExceptionTemp getExceptionByRid(Long rid);

}
