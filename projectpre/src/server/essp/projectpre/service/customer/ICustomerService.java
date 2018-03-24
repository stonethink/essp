package server.essp.projectpre.service.customer;

import java.util.List;
import server.essp.projectpre.db.Customer;
import server.framework.common.BusinessException;

public interface ICustomerService {
    
    /**
     * ����creator��ȡcustomer�������еļ�¼
     *
     */   
	public List listAll(String creator, List roleList) throws BusinessException;
	
	public void save(Customer customer);
	/**
     * �޸ļ�¼
     * @param customer
	 */
	public void update(Customer customer);
    /**
     * �����Ƿ�����Rid��ͬ�ļ�¼
     * @param Rid
     * @return Customer
     * @throws BusinessException
     */
	public Customer loadByRid(Long Rid) throws BusinessException;
    
     
     /**
      * �������뵥��
      * @return String
      * @throws BusinessException
      */
     public String createApplyNo()throws BusinessException;
 /**
  * ���ݲ�ѯ������ѯ���������ļ�¼
  * @param customer
  * @return List
  * @throws BusinessException
  */
	public List listByKey (Customer customer) throws BusinessException;
/**
 * ���ݿͻ���ţ���ƣ������ؼ��ֲ��ҷ��������ļ�¼
 * @param customerId
 * @param shortName
 * @param paramKesys
 * @return List
 * @throws BusinessException
 */
	public List queryCustomer(String customerId, String shortName, String paramKesys) throws BusinessException;
  
	public List queryCustNameById(String customerId) throws BusinessException;
    /**
     * ���ݿͻ���Ų�ѯ���������ļ�¼
     * @param CustomerId
     * @return Customer
     * @throws BusinessException
     */
     public Customer loadByCustomerId(String CustomerId) throws BusinessException;
    
}
