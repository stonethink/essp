package server.essp.timesheet.synchronization.service;

import c2s.essp.timesheet.synchronization.DtoSync;
import db.essp.timesheet.TsException;

public interface ISyncData {
	
	/**
	 * 新增资料
	 * @param dto
	 */
	public void add(DtoSync dto);
	
	/**
	 * 更新资料
	 * @param dto
	 */
	public void update(DtoSync dto);
	
	/**
	 * 删除资料
	 * @param dto
	 */
	public void delete(DtoSync dto);
	
	/**
	 * 获取具体实现名称
	 * @return
	 */
	public String getFunctionName();
	
	/**
	 * 结转资料
	 * @param te
	 */
	public void carryForward(TsException te);

}
