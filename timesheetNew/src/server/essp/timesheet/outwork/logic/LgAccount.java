package server.essp.timesheet.outwork.logic;

import java.util.List;

import net.sf.hibernate.HibernateException;

import db.essp.timesheet.TsAccount;
import db.essp.timesheet.TsCodeValue;
import db.essp.timesheet.TsOutworkerPrivilege;
import server.essp.framework.logic.AbstractESSPLogic;
import server.framework.common.BusinessException;
import server.framework.taglib.util.SelectList;

public class LgAccount extends AbstractESSPLogic {
	
	public String getNameByDeptId(String deptId) {
		try {
			List list = this.getDbAccessor().getSession()
			.createQuery("from TsAccount where account_Id = :accountId order by account_id")
			.setString("accountId", deptId)
			.list();
			if(list != null && list.size() > 0) {
				return ((TsAccount)list.get(0)).getAccountName();
			}
		} catch (Exception e) {
			log.error(e);
			throw new BusinessException("error.logic.LgAccount.listAccount",
					"list account by deptId error", e);
		}
		return deptId;
	}
	
	public synchronized List listPrivilegeDeptByLoginId(String loginId) {
		List list;
		String hql = "from TsOutworkerPrivilege where lower(login_id) = lower(:loginId)";
		try {
			list = this.getDbAccessor().getSession()
			.createQuery(hql)
			.setString("loginId", loginId)
			.list();
		} catch (Exception e) {
			log.error(e);
			throw new BusinessException("error.logic.LgAccount.listAccount",
					"list privilege dept error", e);
		}
		return list;
	}
	
	public List listAccountByDeptId(String deptId) {
		List list = null;
		try {
			list = this.getDbAccessor().getSession()
			.createQuery("from TsAccount where org_code = :orgId and account_Status = 'N' order by account_id")
			.setString("orgId", deptId)
			.list();
		} catch (Exception e) {
			log.error(e);
			throw new BusinessException("error.logic.LgAccount.listAccount",
					"list account by deptId error", e);
		}
		return list;
	}
	
	public List listLaborByAcntRid(Long acntRid) {
		List list = null;
		String hql = "from TsLaborResource where account_Rid= :acntRid";
		try {
			list = this.getDbAccessor().getSession()
			.createQuery(hql).setLong("acntRid", acntRid)
			.list();
		} catch (Exception e) {
			log.error(e);
			throw new BusinessException("error.logic.LgAccount.listLabor",
					"list labor by deptId error", e);
		}
		return list;
	}
	
	public List listCodeByCodeTypeRid(Long codeTypeRid) {
		List list = null;
		String hql = "select v from TsCodeValue v "
			+ ",TsCodeRelation r where v.rid = r.valueRid "
			+ " and r.typeRid = :typeRid";
		try {
			list = this.getDbAccessor().getSession()
			.createQuery(hql).setLong("typeRid", codeTypeRid)
			.list();
		} catch (Exception e) {
			log.error(e);
			throw new BusinessException("error.logic.LgAccount.listcode",
					"list code by deptId error", e);
		}
		leafFilter(list);
		return list;
	}
	
	/**
	 * 过滤TsCodeValue列表，只留下叶子节点
	 * @param list
	 */
	private void leafFilter(List list) {
		String hql = "from TsCodeValue where parent_rid = :pRid ";
		try {
			for(int i = 0; i < list.size(); i ++) {
				TsCodeValue value = (TsCodeValue)list.get(i);
				List children = this.getDbAccessor().getSession()
				.createQuery(hql).setLong("pRid", value.getRid())
				.list();
				if(children != null && children.size() > 0) {
					list.remove(i);
				}
			}
		} catch (Exception e) {
			log.error(e);
			throw new BusinessException("error.logic.LgAccount.leafFilter",
					"list leaf filter error", e);
		}
	}
	
	public TsAccount getTsAccount(Long rid) {
		return (TsAccount) this.getDbAccessor().load(TsAccount.class, rid);
	}
	
	public String getAccountOrgCode(Long acntRid) {
		TsAccount acnt = getTsAccount(acntRid);
		if(acnt == null) {
			return "";
		} else {
			return acnt.getOrgCode();
		}
	}
	
	public static void main(String args[]) {
		LgAccount lg = new LgAccount();
		List privilegeList = lg.listPrivilegeDeptByLoginId("WH0607014");
		for (int i = 0; i < privilegeList.size(); i++) {
        	TsOutworkerPrivilege bean = (TsOutworkerPrivilege)privilegeList.get(i);
            System.out.println(bean.getAcntId()+ " -- " + bean.getAcntName());
        }
	}

}
