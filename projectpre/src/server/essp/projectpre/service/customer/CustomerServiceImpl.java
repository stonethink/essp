package server.essp.projectpre.service.customer;

import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.util.ArrayList;
import java.util.List;

import c2s.essp.common.user.DtoRole;

import com.wits.util.StringUtil;
import net.sf.hibernate.Criteria;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import net.sf.hibernate.expression.Expression;
import server.essp.projectpre.db.Customer;
import server.framework.common.BusinessException;
import server.framework.hibernate.HBComAccess;
import server.framework.logic.AbstractBusinessLogic;

public class CustomerServiceImpl extends AbstractBusinessLogic implements
		ICustomerService {

	/**
	 * 获取customer表中所有的记录
	 */
	public List listAll(String creator, List roleList) throws BusinessException {
        List codeList = new ArrayList();
        try {
        	int size = roleList.size();
        	boolean isProjectOfficer = false;
        	for(int i=0;i<size;i++){
        		String roleId = ((DtoRole)roleList.get(i)).getRoleId();
        		if(DtoRole.ROLE_UAP.equalsIgnoreCase(roleId)
        	               ||DtoRole.ROLE_HAP.equalsIgnoreCase(roleId)
        	               ||DtoRole.ROLE_APO.equalsIgnoreCase(roleId)){
        			isProjectOfficer = true; 
        			break;
        	    } 
        	}
        	Query query = null;
            Session session = this.getDbAccessor().getSession();
            
            if(isProjectOfficer){
            	query = session
                .createQuery("from Customer");
            } else {
            	query = session
                .createQuery("from Customer as t where t.creator=:creator ")
                .setString("creator", creator);
            }
            codeList = query.list();
        } catch (Exception e) {
            throw new BusinessException("error.system.db", e);
        }
        return codeList;
    }
    /**
     * 保存
     */
	public void save(Customer customer) {
        if(this.loadByRid(customer.getRid())!=null) {
            throw new BusinessException("error.logic.SiteServiceImpl.codeduplicate");
        }
        this.getDbAccessor().save(customer);
    }
    /**
     * 查找是否有与Rid相同的记录
     */
	public  Customer loadByRid(Long Rid) throws BusinessException {
           Customer customer  = null;
        try {
            Session session = this.getDbAccessor().getSession();

            customer = (Customer) session.createQuery("from Customer as t where t.rid=:rid")
            .setParameter("rid",Rid)
            .setMaxResults(1)
            .uniqueResult();

        } catch (Exception e) {
            throw new BusinessException("error.system.db", e);
        }
        return customer;
    }
    
    /**
     * 根据客户编号，简称，参数关键字查找符合条件的记录
     */
	public List queryCustomer(String customerId, String shortName,
			String paramKesys) throws BusinessException {

		List resultList = new ArrayList();
		String subSqlId = "";
		String subSqlShort = "";
		String subSqlParamKeys = "";
		boolean exits = false;
		if (customerId != null && !customerId.equals("")) {
			subSqlId = "lower(id) like lower('%" + customerId + "%')";
			exits = true;
		}
		if (shortName != null && !shortName.equals("")) {
			subSqlShort = "lower(short) like lower('%" + shortName + "%')";
			if (exits) {
				subSqlShort = " or " + subSqlShort + ")";
				subSqlId = "(" + subSqlId;
			}
			exits = true;
		}
		if (paramKesys != null && !paramKesys.equals("")) {
			subSqlParamKeys = paramKesys;
			if (exits) {
				subSqlParamKeys = " and (" + subSqlParamKeys + ")";
			}
			exits = true;
		}
		String hqlStr = "from Customer as t";
		if (exits) {
			hqlStr += " where " + subSqlId + subSqlShort + subSqlParamKeys;
		}

		try {
			Session session = this.getDbAccessor().getSession();
			resultList = session.createQuery(hqlStr).list();

		} catch (Exception e) {
			throw new BusinessException("error.system.db", e);
		}

		return resultList;
	}
   
	public List queryCustNameById(String customerId) {
		List resultList = new ArrayList();
		
		Session session;
		try {
			StringBuffer hqlBuff = new StringBuffer();
			session = this.getDbAccessor().getSession();
			hqlBuff.append("from Customer as a where 1=1");
			if (customerId != null && !customerId.equals("")) {
				hqlBuff.append(" and a.customerId='"+customerId+"'");		
			}
			 Query query = session.createQuery(hqlBuff.toString());
			 resultList = query.list();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}    
	   return resultList;
	}
/**
 * 根据查询条件查询符合条件的记录
 */
public List listByKey (Customer customer) throws BusinessException {      
        List resultList = new ArrayList();
        String groupId=StringUtil.nvl(customer.getGroupId());
        String attribute=StringUtil.nvl(customer.getAttribute());
        String customerId=StringUtil.nvl(customer.getCustomerId());
        String belongBd =StringUtil.nvl(customer.getBelongBd());
        String belongSite = StringUtil.nvl(customer.getBelongSite());
        String class_ = StringUtil.nvl(customer.getClass_());
        String country = StringUtil.nvl(customer.getCountry());
        String short_= StringUtil.nvl(customer.getShort_());
        try{
            Session session = this.getDbAccessor().getSession();  
            Criteria criteria = session.createCriteria(Customer.class);
            if(short_!="")
                criteria.add(Expression.like("short_","%" + short_+ "%"));
            if(groupId!="")
                criteria.add(Expression.like("groupId","%" + groupId+ "%"));
            if(attribute!="")
                criteria.add(Expression.like("attribute","%" + attribute+ "%"));
            if(customerId!="")
                criteria.add(Expression.like("customerId","%" + customerId + "%"));
            if(belongBd!="")
                criteria.add(Expression.like("belongBd","%" + belongBd+ "%"));
            if(belongSite!="")criteria.add(Expression.like("belongSite","%" + belongSite+ "%"));
            if(class_!="")
                criteria.add(Expression.like("class_","%" + class_+ "%"));
            if(country!="")
                criteria.add(Expression.like("country","%" + country+ "%"));
            resultList = criteria.list();            
    
        } catch(Exception e) {
            throw new BusinessException("error.system.db", e);
        }
    
        return resultList;
    }

/**
 * 修改记录
 */
    public void update(Customer customer) {
        this.getDbAccessor().update(customer);
    }


/**
 * 创建申请单号
 */
public String createApplyNo() throws BusinessException {
    
    String IDFORMATER = "00000000";
    Customer customer = new Customer();
    // maxRid = this.getDbAccessor().assignedRid(acntApp);
    HBComAccess.assignedRid(customer);
    DecimalFormat df = new DecimalFormat(IDFORMATER);
    String maxRid = df.format(customer.getRid(), new StringBuffer(),
                new FieldPosition(0)).toString();

    return maxRid;
}


/**
 * 根据客户编号在客户正式表中查询是否有符合条件的记录
 */
public Customer loadByCustomerId(String CustomerId) throws BusinessException {
    Customer customer  = null;
 try {
     Session session = this.getDbAccessor().getSession();

     customer = (Customer) session.createQuery("from Customer as t where t.customerId=:customerId")
     .setParameter("customerId",CustomerId)
     .setMaxResults(1)
     .uniqueResult();

 } catch (Exception e) {
     throw new BusinessException("error.system.db", e);
 }
 return customer;
}

}

