package server.essp.timesheet.synchronization.service;

import c2s.essp.timesheet.synchronization.DtoSync;
import db.essp.timesheet.TsException;

public interface ISyncData {
	
	/**
	 * ��������
	 * @param dto
	 */
	public void add(DtoSync dto);
	
	/**
	 * ��������
	 * @param dto
	 */
	public void update(DtoSync dto);
	
	/**
	 * ɾ������
	 * @param dto
	 */
	public void delete(DtoSync dto);
	
	/**
	 * ��ȡ����ʵ������
	 * @return
	 */
	public String getFunctionName();
	
	/**
	 * ��ת����
	 * @param te
	 */
	public void carryForward(TsException te);

}
