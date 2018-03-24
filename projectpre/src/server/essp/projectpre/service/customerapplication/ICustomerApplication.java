package server.essp.projectpre.service.customerapplication;

import java.util.List;

import server.essp.projectpre.db.CustomerApplication;
import server.framework.common.BusinessException;

public interface ICustomerApplication {
   /**
    * 根据查询条件列出符合条件的所有记录
    * @param loginId
    * @param applicationType
    * @param applicationStatus
    * @return List
    * @throws BusinessException
    */ 
   public List listAll(String loginId,String applicationType,String applicationStatus) throws BusinessException;
   /**
    * 从申请表中选出已经提交的数据
    * @param applicationStatus
    * @return List
    * @throws BusinessException
    */
   public List listAllSub(String applicationStatus) throws BusinessException;
   /**
    * 保存
    * @param customerApplication
    */
   public void save(CustomerApplication customerApplication);
   /**
    * 修改
    * @param customerApplication
    */
   public void update(CustomerApplication customerApplication);
   /**
    * 删除
    * @param Rid
    */
   public void delete(Long Rid);
   /**
    * 查询客户申请表中是否有与RID相同的记录
    * @param Rid
    * @return CustomerApplication
    * @throws BusinessException
    */
   public CustomerApplication loadByRid(Long Rid) throws BusinessException;
   /**
    * 根据申请类型，客户编号查询是否有满足条件的记录
    * @param applicationType
    * @param customerId
    * @return CustomerApplication
    * @throws BusinessException
    */
   public CustomerApplication loadByCustomerId(String applicationType,String customerId) throws BusinessException;
   /**
    * 创建申请单号
    * @return String
    * @throws BusinessException
    */
   public String createApplyNo()throws BusinessException;
   /**
    * 根据申请类型，客户编号查询是否有满足条件的记录
    * @param CustomerId
    * @return CustomerApplication
    * @throws BusinessException
    */
   public CustomerApplication loadByCustomerId(String CustomerId) throws BusinessException;
}
