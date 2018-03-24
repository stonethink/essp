package server.essp.projectpre.service.mailprivilege;

import java.util.ArrayList;
import java.util.List;

import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import server.essp.projectpre.db.MailPrivilege;
import server.essp.projectpre.service.constant.Constant;
import server.framework.common.BusinessException;
import server.framework.logic.AbstractBusinessLogic;

public class MailPrivilegeServiceImpl extends AbstractBusinessLogic implements
		IMailPrivilegeService {

	public List listAllUser() throws BusinessException {
		List userList = new ArrayList();
		try {
			// 先获得Session
			Session session = this.getDbAccessor().getSession();

			// 创建HQL查询对象
			Query query = session
					.createQuery("from MailPrivilege as t order by t.rid");
			// 查询
			userList = query.list();
		} catch (Exception e) {
			throw new BusinessException("error.system.db", e);
		}
		return userList;
	}

	public void deleteByLoginid(String loginId) throws BusinessException {
		try {
			MailPrivilege mailP = this.loadByLoginid(loginId);
			if (mailP != null) {
				this.getDbAccessor().delete(mailP);
			}
		} catch (Exception e) {
			throw new BusinessException("error.system.db", e);
		}

	}

	public void update(MailPrivilege mailP) {
		this.getDbAccessor().update(mailP);
	}

	public void add(MailPrivilege mailP) {
		this.getDbAccessor().save(mailP);
	}

	public MailPrivilege loadByLoginid(String Id) throws BusinessException {
		MailPrivilege mailP = null;
		List userList = null;
		try {
			Session session = this.getDbAccessor().getSession();
			Query query = session.createQuery(
					"from MailPrivilege as t where t.loginId=:LoginId")
					.setString("LoginId", Id);
			userList = query.list();
			if (userList != null && userList.size() > 0) {
				mailP = (MailPrivilege) userList.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("error.system.db", e);
		}
		return mailP;
	}

	public List listToNotice(String type, String site) {
		List list = null;
		try {
			Session session = this.getDbAccessor().getSession();
			String sql = "from MailPrivilege as t where ";
			if(Constant.PROJECTADDAPP.equals(type)) {
				sql += "t.addInform='1'";
			} else if(Constant.PROJECTCHANGEAPP.equals(type)){
				sql += "t.changeInform='1'";
			} else if(Constant.PROJECTCONFIRMAPP.equals(type)) {
				sql += "t.closeInform='1'";
			}
			sql += " and t.dataScope like '%"+site+"%'";
			Query q = session.createQuery(sql);
			list = q.list();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("error.system.db", e);
		}
		return list;
	}

}
