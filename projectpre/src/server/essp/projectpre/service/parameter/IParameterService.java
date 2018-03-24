package server.essp.projectpre.service.parameter;

import java.util.List;

import server.essp.projectpre.db.Parameter;
import server.essp.projectpre.db.ParameterId;
import server.framework.common.BusinessException;

public interface IParameterService {
  
	/**
	 * 根据kind的类型获取所有的code
	 * @param kind
	 * @return
	 * @throws BusinessException
	 */
	public List listAllByKind(String kind) throws BusinessException;
	
	/**
	 * 根据参数类型列出所有Status为true的参数资料
	 * @param kind
	 * @return
	 * @throws BusinessException
	 */
	public List listAllByKindEnable(String kind) throws BusinessException;
	
	/**
	 * 保存参数资料
	 * @param parameter
	 */
	public void save(Parameter parameter);
	
	/**
	 * 更新参数资料
	 * @param parameter
	 */
	public void update(Parameter parameter);
	
	/**
	 * 根据主键删除记录
	 * @param parameterId
	 * @throws BusinessException
	 */
	public void delete(ParameterId parameterId) throws BusinessException;
	
	/**
	 * 根据主键获取记录
	 * @param parameterId
	 * @return
	 * @throws BusinessException
	 */
	public Parameter loadByKey(ParameterId parameterId) throws BusinessException;
    
	/**
	 * 根据参数类型和代码查询参数资料
	 * @param kind
	 * @param code
	 * @return
	 * @throws BusinessException
	 */
    public Parameter loadByKindCode(String kind, String code) throws BusinessException;
	
}
