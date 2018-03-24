package server.essp.timesheet.synchronization.service;

import java.util.List;

import c2s.essp.timesheet.synchronization.DtoSync;

public interface ISyncMainService {
	
	/**
	 * ������ϵͳ��������
	 * @param dtoList
	 */
	public void insert(List<DtoSync> dtoList);
	
	/**
	 * ������ϵͳ��������
	 * @param dto
	 */
	public void update(DtoSync dto);
	
	/**
	 * ɾ������ϵͳ�е�����
	 * @param dto
	 */
	public void delete(String employeeId, Long accountRid);
	
	/**
	 * ������ת��Ϊ������ʽ
	 * @param loginIds
	 * @param accountRid
	 * @return
	 */
	public List dataToDtoList(List<String> loginIds, Long accountRid);

}
