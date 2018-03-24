package server.essp.projectpre.service.idsetting;

import java.util.List;

import server.essp.projectpre.db.IdSetting;
import server.framework.common.BusinessException;

public interface IIdSettingService {

	/**
	 * 根据kind的类型获取所有记录
	 * @param kind
	 * @return
	 * @throws BusinessException
	 */
	public List listAllByKinds(String[] kind) throws BusinessException;
	
	/**
	 * 保存代码设定资料
	 * @param idSetting
	 */
	public void save(IdSetting idSetting);
	
	/**
	 * 更新代码设定资料
	 * @param idSetting
	 */
	public void update(IdSetting idSetting);
	
	/**
	 * 根据主键删除记录
	 * @param kind
	 * @throws BusinessException
	 */
	public void delete(String kind) throws BusinessException;
	
	/**
	 * 根据主键获取记录
	 * @param kind
	 * @return
	 * @throws BusinessException
	 */
	public IdSetting loadByKey(String kind) throws BusinessException;
}
