package server.essp.hrbase.synchronization.syncexception.dao;

import java.util.List;

import db.essp.hrbase.HrbExceptionTemp;

public interface ISyncExceptionDao {
	
	/**
	 * 新增一条异常结转信息的记录
	 * @param exceptionTemp
	 */
	public void addException(HrbExceptionTemp exceptionTemp);

	/**
	 * 列出状态为Pending的结转异常信息记录
	 * @return
	 */
	public List listException();
	
	/**
	 * 根据RID获取某条异常结转信息记录
	 * @param rid
	 * @return
	 */
	public HrbExceptionTemp loadExceptionBy(Long rid);
	
	/**
	 * 更新某条异常结转信息记录
	 * @param exception
	 */
	public void updateException(HrbExceptionTemp exception);
}
