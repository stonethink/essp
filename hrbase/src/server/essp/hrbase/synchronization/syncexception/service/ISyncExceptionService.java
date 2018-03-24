package server.essp.hrbase.synchronization.syncexception.service;

import java.util.List;

import server.essp.hrbase.synchronization.syncexception.viewbean.VbSyncException;
import db.essp.hrbase.*;

public interface ISyncExceptionService {
	
	/**
	 * 列出状态为Pending的异常记录
	 * @return
	 */
	public List listException();
	
	/**
	 * 根据rid获取异常信息记录的viewbean
	 * @param rid
	 * @return
	 */
	public VbSyncException loadExceptionByRid(Long rid);
	
	/**
	 * 更新异常记录信息
	 * @param exception
	 */
	public void updateException(HrbExceptionTemp exception);
	
	/**
	 * 新增异常记录
	 * @param exceptionTemp
	 */
	public void addException(HrbExceptionTemp exceptionTemp);
	
	/**
	 * 通过RID获取异常记录
	 * @param rid
	 * @return
	 */
	public HrbExceptionTemp getExceptionByRid(Long rid);

}
