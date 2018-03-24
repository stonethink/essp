package server.essp.projectpre.service.parameter;

import java.util.List;

import server.essp.projectpre.db.Parameter;
import server.essp.projectpre.db.ParameterId;
import server.framework.common.BusinessException;

public interface IParameterService {
  
	/**
	 * ����kind�����ͻ�ȡ���е�code
	 * @param kind
	 * @return
	 * @throws BusinessException
	 */
	public List listAllByKind(String kind) throws BusinessException;
	
	/**
	 * ���ݲ��������г�����StatusΪtrue�Ĳ�������
	 * @param kind
	 * @return
	 * @throws BusinessException
	 */
	public List listAllByKindEnable(String kind) throws BusinessException;
	
	/**
	 * �����������
	 * @param parameter
	 */
	public void save(Parameter parameter);
	
	/**
	 * ���²�������
	 * @param parameter
	 */
	public void update(Parameter parameter);
	
	/**
	 * ��������ɾ����¼
	 * @param parameterId
	 * @throws BusinessException
	 */
	public void delete(ParameterId parameterId) throws BusinessException;
	
	/**
	 * ����������ȡ��¼
	 * @param parameterId
	 * @return
	 * @throws BusinessException
	 */
	public Parameter loadByKey(ParameterId parameterId) throws BusinessException;
    
	/**
	 * ���ݲ������ͺʹ����ѯ��������
	 * @param kind
	 * @param code
	 * @return
	 * @throws BusinessException
	 */
    public Parameter loadByKindCode(String kind, String code) throws BusinessException;
	
}
