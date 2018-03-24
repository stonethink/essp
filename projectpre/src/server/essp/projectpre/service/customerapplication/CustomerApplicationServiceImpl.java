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
     * ���ݲ�ѯ�����г��������������м�¼
     */
	public List listAll(String loginId,String applicationType,String applicationStatus) throws BusinessException {
        List codeList = new ArrayList();
        try {
            // �Ȼ��Session
            Session session = this.getDbAccessor().getSession();

            // ����HQL��ѯ����
            Query query = session
                    .createQuery("from CustomerApplication as t where t.applicantId=:loginId and t.applicationType=:applicationType and t.applicationStatus<>:applicationStatus  order by t.rid")
                    .setString("loginId", loginId)
                    .setString("applicationType", applicationType)
                    .setString("applicationStatus", applicationStatus);
                    
            // ��ѯ
            codeList = query.list();
        } catch (Exception e) {
            // �����������ϲ㼴�ɣ��˴������Ĵ����Ϊ�������ݿ��������
            throw new BusinessException("error.system.db", e);
        }
        return codeList;
    }
    
    
/**
 * ���������ѡ���Ѿ��ύ������
 */
    public List listAllSub(String applicationStatus) throws BusinessException {
        List codeList = new ArrayList();
        try {
            // �Ȼ��Session
            Session session = this.getDbAccessor().getSession();

            // ����HQL��ѯ����
            Query query = session
                    .createQuery("from CustomerApplication as t where t.applicationStatus=:applicationStatus  order by t.rid")
                    .setString("applicationStatus", applicationStatus);
                    
            // ��ѯ
            codeList = query.list();
        } catch (Exception e) {
            // �����������ϲ㼴�ɣ��˴������Ĵ����Ϊ�������ݿ��������
            throw new BusinessException("error.system.db", e);
        }
        return codeList;
    }
    /**
     * ����
     */
	public void save(CustomerApplication customerApplication) {
        if(this.loadByRid(customerApplication.getRid())!=null) {
            throw new BusinessException("error.logic.SiteServiceImpl.codeduplicate");
        }
        this.getDbAccessor().save(customerApplication);
    }
    /**
     * �޸�
     */
	public void update(CustomerApplication customerApplication) {
        this.getDbAccessor().update(customerApplication);

    }
    /**
     * ɾ��
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
 * ��ѯ�ͻ���������Ƿ�����RID��ͬ�ļ�¼
 */
	public CustomerApplication loadByRid(Long Rid) throws BusinessException {
           CustomerApplication customerApplication   = null;
        try {
            // �Ȼ��Session
            Session session = this.getDbAccessor().getSession();

            // ��ѯ����
            customerApplication = (CustomerApplication) session.createQuery("from CustomerApplication as t where t.rid=:rid")
            .setParameter("rid",Rid)
            .setMaxResults(1)
            .uniqueResult();

        } catch (Exception e) {
            // �����������ϲ㼴�ɣ��˴������Ĵ����Ϊ�������ݿ��������
            throw new BusinessException("error.system.db", e);
        }
        return customerApplication;
    }
    

    /**
     * ��AcntApp���л�ȡ����Rid��������ֵ��1����ʽ��Ϊ10λ�ַ�����Ϊ���뵥�ŷ���
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
 * �����������ͣ��ͻ���Ų�ѯ�Ƿ������������ļ�¼
 */
public CustomerApplication loadByCustomerId(String applicationType,String customerId) throws BusinessException {
    CustomerApplication customerApplication   = null;
 try {
     // �Ȼ��Session
     Session session = this.getDbAccessor().getSession();

     // ��ѯ����
     customerApplication = (CustomerApplication) session.createQuery("from CustomerApplication as t where t.applicationType=:applicationType and t.customerId=:customerId")
     .setString("applicationType",applicationType)
     .setString("customerId",customerId)
     .setMaxResults(1)
     .uniqueResult();
 } catch (Exception e) {
     // �����������ϲ㼴�ɣ��˴������Ĵ����Ϊ�������ݿ��������
     throw new BusinessException("error.system.db", e);
 }
 return customerApplication;
}


/**
 * ���ݿͻ���Ų�ѯ�Ƿ�����������������
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