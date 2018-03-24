package server.essp.projectpre.service.idsetting;

import java.util.List;

import server.essp.projectpre.db.IdSetting;
import server.framework.common.BusinessException;

public interface IIdSettingService {

	/**
	 * ����kind�����ͻ�ȡ���м�¼
	 * @param kind
	 * @return
	 * @throws BusinessException
	 */
	public List listAllByKinds(String[] kind) throws BusinessException;
	
	/**
	 * ��������趨����
	 * @param idSetting
	 */
	public void save(IdSetting idSetting);
	
	/**
	 * ���´����趨����
	 * @param idSetting
	 */
	public void update(IdSetting idSetting);
	
	/**
	 * ��������ɾ����¼
	 * @param kind
	 * @throws BusinessException
	 */
	public void delete(String kind) throws BusinessException;
	
	/**
	 * ����������ȡ��¼
	 * @param kind
	 * @return
	 * @throws BusinessException
	 */
	public IdSetting loadByKey(String kind) throws BusinessException;
}
