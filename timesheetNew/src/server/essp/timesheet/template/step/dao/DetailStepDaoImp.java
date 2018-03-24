package server.essp.timesheet.template.step.dao;

import java.util.List;

import org.springframework.orm.hibernate.support.HibernateDaoSupport;

import db.essp.timesheet.TsStepTDetail;

public class DetailStepDaoImp extends HibernateDaoSupport implements IDetailStepDao{

	public void delete(TsStepTDetail stepDetail) {
		this.getHibernateTemplate().delete(stepDetail);			
	}

	public void save(TsStepTDetail stepDetail) {
		this.getHibernateTemplate().save(stepDetail);
	}

	public void update(TsStepTDetail stepDetail) {
		this.getHibernateTemplate().update(stepDetail);
		
	}

	public TsStepTDetail load(Long rid) {
		 return (TsStepTDetail) this.getHibernateTemplate()
         .load(TsStepTDetail.class, rid);
	}

	public List getSteptByTid(Long rid) {
		final String sql="from TsStepTDetail t where t.tempRid = ? order by t.seqNum asc";
		List list = (List)this.getHibernateTemplate().find(sql,rid);
		return list;		
	}
	

}
