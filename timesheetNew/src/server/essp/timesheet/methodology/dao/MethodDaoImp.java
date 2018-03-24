package server.essp.timesheet.methodology.dao;

import java.util.List;

import org.springframework.orm.hibernate.support.HibernateDaoSupport;

import db.essp.timesheet.TsMethod;
import db.essp.timesheet.TsMethodTMap;

public class MethodDaoImp extends HibernateDaoSupport implements IMethodDao {

	public void deleteMethodType(TsMethod method) {
		this.getHibernateTemplate().delete(method);
	}

	public TsMethod getMethodType(Long rid) {
		return (TsMethod) this.getHibernateTemplate().load(TsMethod.class, rid);
	}

	public void updateMethodType(TsMethod method) {
		this.getHibernateTemplate().update(method);
	}

	public List listAllMethodType() {
		final String sql = "from TsMethod";
		List list = (List) this.getHibernateTemplate().find(sql);
		return list;
	}
	public List listNormalMethod() {
		final String sql = "from TsMethod t where t.rst='N' order by t.name";
		List list = (List) this.getHibernateTemplate().find(sql);
		return list;
	}

	public Long addMethodType(TsMethod method) {
		this.getHibernateTemplate().save(method);
		return method.getRid();
	}

	public List getTemplateByMethod(Long rid) {
		String subSql = "from TsMethodTMap map where map.id.methodRid=?";
		List list = this.getHibernateTemplate().find(subSql, rid);
		String condition = null;
		for (int i = 0; i < list.size(); i++) {
			TsMethodTMap map = (TsMethodTMap) list.get(i);
			if (condition == null) {
				condition = map.getId().getTemplateRid().toString();
			} else {
				condition = condition + ","
						+ map.getId().getTemplateRid().toString();
			}
		}
		String sql = "from TsStepT t where t.rid in (" + condition
				+ ") and t.rst = 'N'";
		return this.getHibernateTemplate().find(sql);
	}

}
