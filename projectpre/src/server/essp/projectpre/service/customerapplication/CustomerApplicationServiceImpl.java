package server.essp.projectpre.service.customerapplication;

import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.util.ArrayList;
import java.util.List;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import server.essp.projectpre.db.CustomerApplication;
import server.framework.common.BusinessException;
import server.framework.hibernate.HBComAccess;
import server.framework.logic.AbstractBusinessLogic;

public class CustomerApplicationServiceImpl extends AbstractBusinessLogic
		implements ICustomerApplication {
    /**
     * 根据查询条件列出符合条件的所有记录
     */
	public List listAll(String loginId,String applicationType,String applicationStatus) throws BusinessException {
        List codeList = new ArrayList();
        try {
            // 先获得Session
            Session session = this.getDbAccessor().getSession();

            // 创建HQL查询对象
            Query query = session
                    .createQuery("from CustomerApplication as t where t.applicantId=:loginId and t.applicationType=:applicationType and t.applicationStatus<>:applicationStatus  order by t.rid")
                    .setString("loginId", loginId)
                    .setString("applicationType", applicationType)
                    .setString("applicationStatus", applicationStatus);
                    
            // 查询
            codeList = query.list();
        } catch (Exception e) {
            // 将错误抛至上层即可，此处产生的错误均为报出数据库操作错误
            throw new BusinessException("error.system.db", e);
        }
        return codeList;
    }
    
    
/**
 * 从申请表中选出已经提交的数据
 */
    public List listAllSub(String applicationStatus) throws BusinessException {
        List codeList = new ArrayList();
        try {
            // 先获得Session
            Session session = this.getDbAccessor().getSession();

            // 创建HQL查询对象
            Query query = session
                    .createQuery("from CustomerApplication as t where t.applicationStatus=:applicationStatus  order by t.rid")
                    .setString("applicationStatus", applicationStatus);
                    
            // 查询
            codeList = query.list();
        } catch (Exception e) {
            // 将错误抛至上层即可，此处产生的错误均为报出数据库操作错误
            throw new BusinessException("error.system.db", e);
        }
        return codeList;
    }
    /**
     * 保存
     */
	public void save(CustomerApplication customerApplication) {
        if(this.loadByRid(customerApplication.getRid())!=null) {
            throw new BusinessException("error.logic.SiteServiceImpl.codeduplicate");
        }
        this.getDbAccessor().save(customerApplication);
    }
    /**
     * 修改
     */
	public void update(CustomerApplication customerApplication) {
        this.getDbAccessor().update(customerApplication);

    }
    /**
     * 删除
     */
	public void delete(Long Rid) throws BusinessException {
        try {
            CustomerApplication customerApplication=this.loadByRid(Rid);
            this.getDbAccessor().delete(customerApplication);
        }catch(Exception e) {
            throw new BusinessException("error.system.db", e);
        }
    }
/**
 * 查询客户申请表中是否有与RID相同的记录
 */
	public CustomerApplication loadByRid(Long Rid) throws BusinessException {
           CustomerApplication customerApplication   = null;
        try {
            // 先获得Session
            Session session = this.getDbAccessor().getSession();

            // 查询对象
            customerApplication = (CustomerApplication) session.createQuery("from CustomerApplication as t where t.rid=:rid")
            .setParameter("rid",Rid)
            .setMaxResults(1)
            .uniqueResult();

        } catch (Exception e) {
            // 将错误抛至上层即可，此处产生的错误均为报出数据库操作错误
            throw new BusinessException("error.system.db", e);
        }
        return customerApplication;
    }
    

    /**
     * 从AcntApp表中获取最大的Rid，并将此值加1，格式化为10位字符串作为申请单号返回
     */
      
    public String createApplyNo() throws BusinessException {
    
        String IDFORMATER = "00000000";
        CustomerApplication customerApplication = new CustomerApplication();
        // maxRid = this.getDbAccessor().assignedRid(acntApp);
        HBComAccess.assignedRid(customerApplication);
        DecimalFormat df = new DecimalFormat(IDFORMATER);
        String maxRid = df.format(customerApplication.getRid(), new StringBuffer(),
                    new FieldPosition(0)).toString();

        return maxRid;
    }


/**
 * 根据申请类型，客户编号查询是否有满足条件的记录
 */
public CustomerApplication loadByCustomerId(String applicationType,String customerId) throws BusinessException {
    CustomerApplication customerApplication   = null;
 try {
     // 先获得Session
     Session session = this.getDbAccessor().getSession();

     // 查询对象
     customerApplication = (CustomerApplication) session.createQuery("from CustomerApplication as t where t.applicationType=:applicationType and t.customerId=:customerId")
     .setString("applicationType",applicationType)
     .setString("customerId",customerId)
     .setMaxResults(1)
     .uniqueResult();
 } catch (Exception e) {
     // 将错误抛至上层即可，此处产生的错误均为报出数据库操作错误
     throw new BusinessException("error.system.db", e);
 }
 return customerApplication;
}


/**
 * 根据客户编号查询是否有满足条件的数据
 */
public CustomerApplication loadByCustomerId(String CustomerId) throws BusinessException {
    CustomerApplication customerApplication  = null;
    try {
        Session session = this.getDbAccessor().getSession();

        customerApplication = (CustomerApplication) session.createQuery("from CustomerApplication as t where t.customerId=:customerId")
        .setParameter("customerId",CustomerId)
        .setMaxResults(1)
        .uniqueResult();

    } catch (Exception e) {
        throw new BusinessException("error.system.db", e);
    }
    return customerApplication;
   }



  

}