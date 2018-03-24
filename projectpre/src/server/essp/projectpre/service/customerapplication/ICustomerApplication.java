package server.essp.projectpre.service.customerapplication;

import java.util.List;

import server.essp.projectpre.db.CustomerApplication;
import server.framework.common.BusinessException;

public interface ICustomerApplication {
   /**
    * ���ݲ�ѯ�����г��������������м�¼
    * @param loginId
    * @param applicationType
    * @param applicationStatus
    * @return List
    * @throws BusinessException
    */ 
   public List listAll(String loginId,String applicationType,String applicationStatus) throws BusinessException;
   /**
    * ���������ѡ���Ѿ��ύ������
    * @param applicationStatus
    * @return List
    * @throws BusinessException
    */
   public List listAllSub(String applicationStatus) throws BusinessException;
   /**
    * ����
    * @param customerApplication
    */
   public void save(CustomerApplication customerApplication);
   /**
    * �޸�
    * @param customerApplication
    */
   public void update(CustomerApplication customerApplication);
   /**
    * ɾ��
    * @param Rid
    */
   public void delete(Long Rid);
   /**
    * ��ѯ�ͻ���������Ƿ�����RID��ͬ�ļ�¼
    * @param Rid
    * @return CustomerApplication
    * @throws BusinessException
    */
   public CustomerApplication loadByRid(Long Rid) throws BusinessException;
   /**
    * �����������ͣ��ͻ���Ų�ѯ�Ƿ������������ļ�¼
    * @param applicationType
    * @param customerId
    * @return CustomerApplication
    * @throws BusinessException
    */
   public CustomerApplication loadByCustomerId(String applicationType,String customerId) throws BusinessException;
   /**
    * �������뵥��
    * @return String
    * @throws BusinessException
    */
   public String createApplyNo()throws BusinessException;
   /**
    * �����������ͣ��ͻ���Ų�ѯ�Ƿ������������ļ�¼
    * @param CustomerId
    * @return CustomerApplication
    * @throws BusinessException
    */
   public CustomerApplication loadByCustomerId(String CustomerId) throws BusinessException;
}
