package server.essp.projectpre.service.customer;

import java.util.List;
import server.essp.projectpre.db.Customer;
import server.framework.common.BusinessException;

public interface ICustomerService {
    
    /**
     * 根据creator获取customer表中所有的记录
     *
     */   
	public List listAll(String creator, List roleList) throws BusinessException;
	
	public void save(Customer customer);
	/**
     * 修改记录
     * @param customer
	 */
	public void update(Customer customer);
    /**
     * 查找是否有与Rid相同的记录
     * @param Rid
     * @return Customer
     * @throws BusinessException
     */
	public Customer loadByRid(Long Rid) throws BusinessException;
    
     
     /**
      * 创建申请单号
      * @return String
      * @throws BusinessException
      */
     public String createApplyNo()throws BusinessException;
 /**
  * 根据查询条件查询符合条件的记录
  * @param customer
  * @return List
  * @throws BusinessException
  */
	public List listByKey (Customer customer) throws BusinessException;
/**
 * 根据客户编号，简称，参数关键字查找符合条件的记录
 * @param customerId
 * @param shortName
 * @param paramKesys
 * @return List
 * @throws BusinessException
 */
	public List queryCustomer(String customerId, String shortName, String paramKesys) throws BusinessException;
  
	public List queryCustNameById(String customerId) throws BusinessException;
    /**
     * 根据客户编号查询符合条件的记录
     * @param CustomerId
     * @return Customer
     * @throws BusinessException
     */
     public Customer loadByCustomerId(String CustomerId) throws BusinessException;
    
}
