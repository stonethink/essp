package server.essp.timesheet.synchronization.service;

import java.util.List;

import c2s.essp.timesheet.synchronization.DtoSync;

public interface ISyncMainService {
	
	/**
	 * 向其他系统插入数据
	 * @param dtoList
	 */
	public void insert(List<DtoSync> dtoList);
	
	/**
	 * 向其他系统更新数据
	 * @param dto
	 */
	public void update(DtoSync dto);
	
	/**
	 * 删除其他系统中的数据
	 * @param dto
	 */
	public void delete(String employeeId, Long accountRid);
	
	/**
	 * 将数据转换为可用形式
	 * @param loginIds
	 * @param accountRid
	 * @return
	 */
	public List dataToDtoList(List<String> loginIds, Long accountRid);

}
