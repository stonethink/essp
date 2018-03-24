package server.essp.timesheet.account.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import net.sf.hibernate.*;
import org.springframework.orm.hibernate.HibernateCallback;
import org.springframework.orm.hibernate.support.HibernateDaoSupport;
import c2s.essp.common.calendar.WorkCalendar;
import c2s.essp.timesheet.account.DtoAccount;
import c2s.essp.timesheet.labor.DtoLaborResource;
import c2s.essp.timesheet.weeklyreport.DtoTimeSheet;
import db.essp.timesheet.TsAccount;
import db.essp.timesheet.TsSelectAccount;

/**
 * <p>Title: Account data access object</p>
 *
 * <p>Description: ����Ŀ��ص����ݷ���</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class AccountDaoImp extends HibernateDaoSupport implements IAccountDao {

    /**
     * �г�ָ���û�Ϊ��Ŀ�����״̬��������Ŀ
     *
     * @param loginId String
     * @return List
     */
    public List listAccounts(String loginId) {
    	String sql = "from TsAccount where upper(manager)=upper(?) and account_status='"+DtoAccount.STATUS_NORMAL+"' order by Account_Id";
        return (List)this.getHibernateTemplate()
                         .find(sql, new Object[]{loginId});
    }
    
    /**
     * �г�ָ��OU��״̬��������Ŀ
     * 	ou��Ϊ������Ŵ��룬��","�ָ���s
     * @param ou String
     * @return List<TsAccount>
     */
    public List listAccountByOu(String ou) {
    	if(ou == null || "".equals(ou)) {
    		return new ArrayList();
    	}
    	
    	String orgCodes = ou.replaceAll(",", "','");
    	String sql = "from TsAccount where org_code in (?) and account_status='"+DtoAccount.STATUS_NORMAL+"' order by Account_Id";
        return (List)this.getHibernateTemplate()
                         .find(sql, orgCodes);
    }

    /**
     * ��ȡָ��rid����Ŀ
     *
     * @param rid Long
     * @return TsAccount
     */
    public TsAccount loadAccount(Long rid) {
            return (TsAccount) this.getHibernateTemplate()
                               .load(TsAccount.class, rid);
    }

    /**
     * ������Ŀ
     *
     * @param Account TsAccount
     */
    public void saveAccount(TsAccount account) {
        this.getHibernateTemplate().update(account);
    }
    /**
     * ������Ŀ
     *
     * @param Account TsAccount
     */
    public void addPorject(TsAccount account) {
        this.getHibernateTemplate().save(account);
    }

    /**
     * ����ר�������ȡר����Ϣ
     *
     * @param AccountId String
     * @return TsAccount
     */
    public TsAccount loadByAccountId(String accountId) {
    	if(accountId == null || "".equals(accountId)) {
    		return null;
    	}
        String sql = "from TsAccount where Account_Id=?";
        List list = (List)this.getHibernateTemplate()
                              .find(sql, new Object[]{accountId});
        TsAccount Account = null;
        if(list!=null&&list.size()>0){
            Account = (TsAccount) list.get(0);
        }
        return Account;
    }

    public List listProjects(String loginId) {
        String sql = "from TsAccount where upper(manager)=upper(?) and account_status='"+DtoAccount.STATUS_NORMAL+"'"
                     +" and dept_flag='0' order by Account_Id";
         return (List)this.getHibernateTemplate()
                          .find(sql, new Object[]{loginId});

    }

    /**
     * ����accountRid�õ���ǰ��Ŀ��ʵ���ۼ�ʱ��
     * @param accountRid Long
     * @return BigDecimal
     */
    public Double sumWorkHoursByAccount(final Long accountRid,
                                        final String loginId) {
        final String sql = "select sum(d.workHours) from TsAccount as a " +
                           ", TsTimesheetDetail as t " +
                           ", TsTimesheetDay as d , TsTimesheetMaster as m" +
                           " where a.rid=t.accountRid and t.rid=d.tsDetailRid " +
                           " and m.rid=t.tsRid and m.status = '"+DtoTimeSheet.STATUS_APPROVED+"' and a.rid=? and upper(manager)=upper(?)";
        Double amount = null;
        amount = (Double)this.getHibernateTemplate().execute(new
                HibernateCallback() {
            public Object doInHibernate(Session session) throws SQLException,
                    HibernateException {
                Query q = session.createQuery(sql)
                          .setLong(0, accountRid)
                          .setString(1, loginId);
                List list = q.list();
                return (Double) list.get(0);
            }
        });
        return amount;
    }
    public List listMailProject() {
    	final String sql = "select t.accountId from TsAccount t where t.isMail='1'";
    	List list = (List)this.getHibernateTemplate().execute(new
                HibernateCallback() {
            public Object doInHibernate(Session session) throws SQLException,
                    HibernateException {
                Query q = session.createQuery(sql);
                return q.list();
            }
        });
    	return list;
    }
    /**
     * ����accountRid�õ���ǰ��Ŀ��ʵ���ۼ�ʱ��(PMOʹ�ò���ר����������)
     * @param accountRid Long
     * @return BigDecimal
     */
    public Double sumWorkHoursByAccount(final Long accountRid) {
        final String sql = "select sum(d.workHours) from TsAccount as a " +
                           ", TsTimesheetDetail as t " +
                           ", TsTimesheetDay as d , TsTimesheetMaster as m" +
                           " where a.rid=t.accountRid and t.rid=d.tsDetailRid " +
                           " and m.rid=t.tsRid and m.status = '"+DtoTimeSheet.STATUS_APPROVED+"' and a.rid=? ";
        Double amount = null;
        amount = (Double)this.getHibernateTemplate().execute(new
                HibernateCallback() {
            public Object doInHibernate(Session session) throws SQLException,
                    HibernateException {
                Query q = session.createQuery(sql)
                          .setLong(0, accountRid);
                List list = q.list();
                return (Double) list.get(0);
            }
        });
        return amount;
    }
    /**
     * ���ݲ������ܵ�loginId�г�״̬Ϊ��Ч�Ĳ�������
     * @param loginId String
     * @return List
     */
    public List listDeptByManager(String loginId) {
        String sql = "from TsAccount where upper(manager)=upper(?) and dept_flag='1' and account_status='"
                     +DtoAccount.STATUS_ENABLE+"' order by Account_Id";
        return (List)this.getHibernateTemplate()
                         .find(sql, new Object[]{loginId});

    }
    /**
     * ���ݸ����ŵ�id�г������Ӳ���
     * @param parentId String
     * @return List
     */
    public List listChildrenDeptByParentId(String parentId) {
        String sql = "from TsAccount where parent_Id=? and dept_flag='1' and account_status='"
                     +DtoAccount.STATUS_ENABLE+"' order by Account_Id";
        return (List)this.getHibernateTemplate()
                         .find(sql, new Object[]{parentId});

    }
    /**
     * ���ݲ���Id�г������µ�״̬Ϊ����������ר��
     * deptIds��ʽΪ('deptId1','deptId2','deptId3')
     * @param deptId String
     * @return List
     */
    public List listProjectsByDept(String deptIds) {
        String sql = "from TsAccount where Org_Code in " +deptIds
                     + " and account_status='"
                     +DtoAccount.STATUS_NORMAL+"' order by Account_Id";
        return (List)this.getHibernateTemplate()
                         .find(sql);

    }

    /**
     * ���ݲ���Id�г������µ�״̬Ϊ����������ר��
     * deptIds��ʽΪ('deptId1','deptId2','deptId3')
     * @param deptId String
     * @return List
     */
 public List listAccountByDept(String deptIds) {
     String sql = "from TsAccount where Org_Code in '" + deptIds+"' "
                  + " or Account_Id = '" + deptIds+"'"
                  + " and account_status='"
                  +DtoAccount.STATUS_NORMAL+"' order by Account_Id";
     return (List)this.getHibernateTemplate()
                      .find(sql);

 }

    /**
     * ����ר�������loginId�г�ĳ�����µ�ר��
     * @param deptId String
     * @param loginId String
     * @return List
     */
    public List listProjectsByLoginIdInDept(String deptId, String loginId) {
        String sql = "from TsAccount where org_Code=? and upper(manager)=upper(?)"
                     + " and dept_flag='0' and account_status='"
                     +DtoAccount.STATUS_NORMAL+"' order by Account_Id";
        return (List)this.getHibernateTemplate()
                         .find(sql, new Object[]{deptId, loginId});

    }
    /**
     * �г�״̬����������Account���������ţ�
     * @return
     */
	public List listNormalAccounts() {
		String sql = "from TsAccount where account_status='"+DtoAccount.STATUS_NORMAL+"' order by Account_Id";
        return (List)this.getHibernateTemplate()
                         .find(sql);
	}

	public List listByCondtion(DtoAccount dto, String privilegeOu) {
		StringBuffer sql = new StringBuffer();
		Date begin = null;
		Date end = null;
		boolean existBegin = false;
		boolean existEnd = false;
		sql.append("from TsAccount where account_status='"
			+DtoAccount.STATUS_NORMAL+"' ");
		if(dto.getAccountId() != null && !dto.getAccountId().equals("")) {
			sql.append(" and lower(account_id) like lower('%"+dto.getAccountId()+"%') ");
		} 
		if(dto.getAccountName() != null && !dto.getAccountName().equals("")) {
			sql.append(" and lower(account_name) like lower('%"+dto.getAccountName()+"%') ");
		}
		if(dto.getManager() != null && !dto.getManager().equals("")) {
			sql.append(" and lower(manager)=lower('"+dto.getManager()+"') ");
		}
		if(dto.getOrgCode() != null && !dto.getOrgCode().equals("")) {
			sql.append(" and lower(org_code) like lower('%"+dto.getOrgCode()+"%') ");
		}
		if(privilegeOu != null && "".equals(privilegeOu) == false) {
			String pOus = privilegeOu.replaceAll(",", "','").toLowerCase();
			sql.append(" and (lower(org_code) in ('" + pOus + "') or lower(account_id) in ('" + pOus + "')) ");
		}
		if(dto.getPlannedStart() != null) {
			sql.append(" and planned_start>=? ");
			begin = WorkCalendar.resetBeginDate(dto.getPlannedStart());
			existBegin = true;
		}
		if(dto.getPlannedFinish() != null) {
			sql.append(" and planned_finish<=? ");
			end = WorkCalendar.resetEndDate(dto.getPlannedFinish());
			existEnd = true;
		}
		sql.append("order by Account_Id");
		Object[] objs = null;
		if(existBegin && !existEnd) {
			objs = new Object[]{begin};
		} else if(!existBegin && existEnd) {
			objs = new Object[]{end};
		} else if(existBegin && existEnd) {
			objs = new Object[]{begin, end};
		} else {
			return this.getHibernateTemplate().find(sql.toString());
		}
		return this.getHibernateTemplate().find(sql.toString(), objs);
	}

    /**
     * �г����Р�B��N�Ĳ��T
     * @return List
     */
    public List listAllDept() {
        String sql = "from TsAccount where account_status='"+DtoAccount.STATUS_NORMAL+"'" +
                " and dept_flag='"+DtoAccount.DEPT_FLAG_DEPT+"' order by Account_Id";
        return (List)this.getHibernateTemplate()
                         .find(sql);
    }
    
   /**
    * ����loginId��ѯ����Ŀ
    * @param loginId
    * @return List
    */ 
   public List listAccountByLabResLoginId(final String loginId) {
       String status = DtoLaborResource.DTO_STATUS_IN;
        final String sql = "from TsAccount as a, TsLaborResource as t "
                +" where a.rid=t.accountRid and t.status='"+status+"'" 
                +" and upper(t.loginId)=upper(?) order by a.accountId asc";
        List accountList = null;
        accountList = (List)this.getHibernateTemplate().execute(new
         HibernateCallback() {
         public Object doInHibernate(Session session) throws SQLException,
         HibernateException {
           Query q = session.createQuery(sql)
                          .setString(0, loginId);
           return q.list();
         }
         });
        return accountList;
        }
    
   /**
    * ��ȡ�û�ѡ����Ŀ����
    * @param loginId String
    * @return String
    */
   public String getSelectAccount(String loginId) {
   	TsSelectAccount bean = this.getTSSelectAccount(loginId);
   	if(bean != null) {
   		return bean.getAccountId();
   	} else {
   		return null;
   	}
   }
   
   private TsSelectAccount getTSSelectAccount(String loginId) {
   	if(loginId == null || "".equals(loginId)) {
   		return null;
   	}
   	String hql = "from TsSelectAccount t where upper(t.loginId) = upper(?)";
   	List<TsSelectAccount> list = this.getHibernateTemplate().find(hql, loginId);
   	if(list != null && list.size() > 0) {
   		return list.get(0);
   	}
   	return null;
   }
   
   /**
    * �����û�ѡ����Ŀ
    * @param loginId String
    * @param accountId String
    */
   public void setSelectAccount(String loginId, String accountId) {
   	if(loginId == null || "".equals(loginId)
   			|| accountId == null || "".equals(accountId)) {
   		return;
   	}
   	
   	TsSelectAccount bean = this.getTSSelectAccount(loginId);
   	if(bean != null) {
   		bean.setAccountId(accountId);
   		this.getHibernateTemplate().update(bean);
   	} else {
   		bean = new TsSelectAccount();
   		bean.setLoginId(loginId);
   		bean.setAccountId(accountId);
   		this.getHibernateTemplate().save(bean);
   	}
   }
}
