/*
 * Created on 2007-12-11
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.hrbase.humanbase.dao;

import java.util.*;

import net.sf.hibernate.type.*;

import org.springframework.orm.hibernate.support.HibernateDaoSupport;

import server.essp.hrbase.humanbase.form.AfHumanBaseQuery;
import c2s.dto.IDto;
import c2s.essp.common.calendar.WorkCalendar;

import com.wits.util.comDate;

import db.essp.hrbase.HrbHumanBase;
import db.essp.hrbase.HrbHumanBaseLog;

public class HumanBaseDaoImp extends HibernateDaoSupport implements
		IHumanBaseDao {

	public List queryByCondition(AfHumanBaseQuery af) {
		StringBuffer sql = new StringBuffer();
		List args = new ArrayList();
		List<Type> types = new ArrayList<Type>();
		sql.append("from HrbHumanBase where rst = '" + IDto.OP_NOCHANGE + "' ");
		
		String employee = af.getEmployee();
		if (employee != null && "".equals(employee.trim()) == false) {
			String trimed = employee.trim();
			sql.append(" and (lower(employee_id) like lower('%" + trimed
					+ "%') ");
			sql.append(" or lower(english_name) like lower('%" + trimed
					+ "%') ");
			sql.append(" or lower(chinese_name) like lower('%" + trimed
					+ "%')) ");
		}
		
		String rm = af.getRm();
		if (rm != null && "".equals(rm.trim()) == false) {
			String trimed = rm.trim();
			sql.append(" and (lower(res_manager_id) like lower('%" + trimed
					+ "%') ");
			sql.append(" or lower(res_manager_name) like lower('%" + trimed
					+ "%')) ");
		}
		
		String site = af.getSite();
		if (site != null && "".equals(site.trim()) == false) {
			site = site.trim().replaceAll(",", "','");
			sql.append(" and site in ('" + site + "') ");
		} else {
			return new ArrayList();
		}

		String unitCode = af.getUnitCode();
		if (unitCode != null && "".equals(unitCode.trim()) == false) {
			sql.append(" and lower(unit_code) like lower('%" + unitCode.trim() + "%') ");
		}
		
		String email = af.getEmail();
		if (email != null && "".equals(email.trim()) == false) {
			sql.append(" and lower(email) like lower('%" + email.trim() + "%') ");
		}

		String inDateBegin = af.getInDateBegin();
		if (inDateBegin != null && "".equals(inDateBegin.trim()) == false) {
			sql.append(" and in_date >= ? ");
			Date d = comDate.toDate(inDateBegin.trim());
			args.add(d);
			types.add(new DateType());
		}

		String inDateEnd = af.getInDateEnd();
		if (inDateEnd != null && "".equals(inDateEnd.trim()) == false) {
			sql.append(" and in_date <= ? ");
			Date d = comDate.toDate(inDateEnd.trim());
			args.add(d);
			types.add(new DateType());
		}

		String outDateBegin = af.getOutDateBegin();
		if (outDateBegin != null && "".equals(outDateBegin.trim()) == false) {
			sql.append(" and out_date >= ? ");
			Date d = comDate.toDate(outDateBegin.trim());
			args.add(d);
			types.add(new DateType());
		}

		String outDateEnd = af.getOutDateEnd();
		if (outDateEnd != null && "".equals(outDateEnd.trim()) == false) {
			sql.append(" and out_date <= ? ");
			Date d = comDate.toDate(outDateEnd.trim());
			args.add(d);
			types.add(new DateType());
		}

		String attribute = af.getAttribute();
		String formal = af.getFormal();
		if ((attribute != null && "".equals(attribute.trim()) == false)
				|| (formal != null && "".equals(formal.trim()) == false)) {
			String groupRidSet = this.getAttGroupRidSet(attribute, formal);
			sql.append(" and attribute_Group_Rid in (" + groupRidSet + ") ");
		}
		
		String isDirect = af.getIsDirect();
		if (isDirect != null && "".equals(isDirect.trim()) == false) {
			sql.append(" and lower(is_Direct) like lower('%" + isDirect.trim() + "%') ");
		}

		String title = af.getTitle();
		if (title != null && "".equals(title.trim()) == false) {
			sql.append(" and lower(title) like lower('%" + title.trim() + "%') ");
		}

		String rank = af.getRank();
		if (rank != null && "".equals(rank.trim()) == false) {
			sql.append(" and lower(rank) like lower('%" + rank.trim() + "%') ");
		}
		String onJob = af.getOnJob();
		if(onJob != null && "".equals(onJob)== false) {
			args.add(new Date());
			types.add(new DateType());
			if("1".equals(onJob)) {
				sql.append(" and (out_date is null or out_date > ?)");
			} else {
				sql.append(" and (out_date <= ?)");
			}
		}
		sql.append(" order by employee_id");
		if (args.size() > 0) {
			return this.getHibernateTemplate().find(sql.toString(),
					args.toArray(), (Type[]) list2Types(types));
		} else {
			return this.getHibernateTemplate().find(sql.toString());
		}
	}
	
	private final static Type[] list2Types(List<Type> list) {
		if(list == null || list.size() < 1) {
			return null;
		}
		Type[] types = new Type[list.size()];
		for(int i = 0; i < list.size(); i ++) {
			types[i] = list.get(i);
		}
		return types;
	}

	public HrbHumanBase loadHumanBase(Long rid) {
		String sql = "from HrbHumanBase where rid=? and rst='" 
													+ IDto.OP_NOCHANGE + "' ";
		List list = this.getHibernateTemplate().find(sql, rid, new LongType());
		if (list != null && list.size() > 0) {
			return (HrbHumanBase) list.get(0);
		}
		return null;
	}

	public void saveHumanBaseLog(HrbHumanBaseLog hrb) {
		this.getHibernateTemplate().save(hrb);
	}

	public void updateHumanBaseLog(HrbHumanBaseLog hrb) {
		this.getHibernateTemplate().update(hrb);

	}

	public void deleteHumanBase(Long rid) {
		String sql = "from HrbHumanBase where rid=?";
		this.getHibernateTemplate().delete(sql);
	}
	
	public void cancelHumanBaseLog(Long rid) {
		HrbHumanBaseLog log = this.loadHumanBaseLog(rid);
		log.setStatus(HrbHumanBaseLog.STATUS_CANCELED);
		this.updateHumanBaseLog(log);
	}
	
	public List<HrbHumanBase> queryHumanBaseUnderUnit(String unitCode) {
		String sql = "from HrbHumanBase where unit_code=? and rst='"+IDto.OP_NOCHANGE+"'";
		return this.getHibernateTemplate().find(sql, unitCode);
	}

	public List listUpdateLog(Long rid) {
		String sql = "from HrbHumanBaseLog where operation in ('" 
			+ HrbHumanBaseLog.OPERATION_UPDATE + "','" 
			+ HrbHumanBaseLog.OPERATION_DELETE + "')"
			+ " and base_rid = ? order by rid desc,rut desc";
		return this.getHibernateTemplate().find(sql, rid, new LongType());
	}

	public List listAddLog(String sites) {
		if(sites == null || "".equals(sites)) {
			return new ArrayList();
		}
		String pStite = sites.replaceAll(",", "','");
		String sql = "from HrbHumanBaseLog where operation='" 
			+ HrbHumanBaseLog.OPERATION_INSERT + "' and status='" 
			+ HrbHumanBaseLog.STATUS_PENDING + "' "
			+ " and site in ('" + pStite + "') order by rid desc,rut desc";
		return this.getHibernateTemplate().find(sql);
	}

	public HrbHumanBaseLog loadHumanBaseLog(Long rid) {
		String sql = "from HrbHumanBaseLog where rid=?";
		List list = this.getHibernateTemplate().find(sql, rid, new LongType());
		if (list != null && list.size() > 0) {
			return (HrbHumanBaseLog) list.get(0);
		}
		return null;
	}

	public void saveHumanBase(HrbHumanBase humanBase) {
		this.getHibernateTemplate().save(humanBase);
	}

	public void updateHumanBase(HrbHumanBase humanBase) {
		this.getHibernateTemplate().update(humanBase);
	}

	public List<HrbHumanBaseLog> queryHumanBaseLogForSync() {
		String sql = "from HrbHumanBaseLog where status='"
				+ HrbHumanBaseLog.STATUS_PENDING + "'"
				+ " and (effective_Date<=? or effective_Date is null)";
		Date today = WorkCalendar.resetEndDate(new Date());
		return this.getHibernateTemplate().find(sql, today);
	}

	public HrbHumanBase findHumanBase(String employeeId) {
		String sql = "from HrbHumanBase where lower(employee_id)=lower(?) and rst='" 
			+ IDto.OP_NOCHANGE + "'";
		List list = this.getHibernateTemplate().find(sql, employeeId, new StringType());
		if (list != null && list.size() > 0) {
			return (HrbHumanBase) list.get(0);
		}
		return null;
	}

	public HrbHumanBaseLog findInsertingHumanBaseLog(String employeeId) {
		String sql = "from HrbHumanBaseLog where lower(employee_id)=lower(?) and rst='" 
			+ IDto.OP_NOCHANGE + "' and operation='" 
			+ HrbHumanBaseLog.OPERATION_INSERT + "' and status='" 
			+ HrbHumanBaseLog.STATUS_PENDING + "' ";
		List list = this.getHibernateTemplate().find(sql, employeeId, new StringType());
		if (list != null && list.size() > 0) {
			return (HrbHumanBaseLog) list.get(0);
		}
		return null;
	}
	
	private String getAttGroupRidSet(String attribute, String formal) {
		String hql = "select grp.rid from HrbAttributeGroup grp, HrbAttribute att "
			+ "where grp.attributeRid = att.rid ";
		if(attribute != null && !"".equals(attribute)) {
			hql += "and (lower(grp.code) like lower('%"  + attribute + "%') "
				+ "or lower(grp.description) like lower('%"  + attribute + "%') "
				+ "or lower(att.code) like lower('%"  + attribute + "%') "
				+ "or lower(att.description) like lower('%"  + attribute + "%')) ";
		}
		if(formal != null && !"".equals(formal)) {
			hql += "and grp.isFormal='" + formal + "' ";
		}
		List<Long> list = this.getHibernateTemplate().find(hql);
		String rids = "-1";
		for (Long rid : list) {
			if("-1".equals(rids)) {
				rids = rid + "";
			} else {
				rids += "," + rid;
			}
		}
		return rids;
	}
	
	/**
     * 根据属性群组rid获取属性相关信息：属性群组代码，属性代码，是否正式。
     * @param groupRid Long
     * @return Object[], groupRid Long, groupCode String, attributeCode String, isFormal String
     */
	public Object[] getAttributeInfoByGroupRid(Long groupRid) {
		if(groupRid == null) {
			return null;
		}
		
		String hql = "select grp.rid, grp.code, att.code, grp.isFormal from HrbAttributeGroup grp, HrbAttribute att "
			+ "where grp.attributeRid = att.rid "
			+ " and grp.rid = " + groupRid;
		List list = this.getHibernateTemplate().find(hql);
		if(list != null && list.size() > 0) {
			return (Object[]) list.get(0);
		}
		return null;
	}

}