package server.essp.timesheet.rmmaint.dao;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import net.sf.hibernate.type.DateType;
import net.sf.hibernate.type.StringType;
import net.sf.hibernate.type.Type;

import org.springframework.orm.hibernate.HibernateCallback;
import org.springframework.orm.hibernate.support.HibernateDaoSupport;

import server.essp.common.ldap.LDAPUtil;
import c2s.essp.common.user.DtoUserBase;
import c2s.essp.timesheet.approval.DtoTsApproval;
import c2s.essp.timesheet.report.DtoTsStatusQuery;
import db.essp.timesheet.TsHumanBase;
import db.essp.timesheet.TsRmMaint;

public class RmMaintDaoImp extends HibernateDaoSupport implements IRmMaintDao {
	/**
	 * 根据loginId查询特殊RM对应关系
	 */
	public TsRmMaint getRmByLoginId(String loginId) {
		String sql = "from TsRmMaint where lower(login_id)=lower(?)";
		List list = this.getHibernateTemplate().find(sql, loginId);
		TsRmMaint tsRmMaint = null;
		if(list != null && list.size() > 0) {
			tsRmMaint = (TsRmMaint)list.get(0);
		}
		return tsRmMaint;
	}
	/**
	 * 新增RM对应关系
	 */
	public void addRmMaint(TsRmMaint tsRmMaint) {
		this.getHibernateTemplate().save(tsRmMaint);
		
	}
	/**
	 * 删除RM对应关系
	 */
	public void delRmMaint(TsRmMaint tsRmMaint) {
		this.getHibernateTemplate().delete(tsRmMaint);
		
	}
	/**
	 * 更新RM对应关系
	 */
	public void updateRmMaint(TsRmMaint tsRmMaint) {
		this.getHibernateTemplate().update(tsRmMaint);
		
	}
	/**
	 * 查询RM下的人员
	 */
	public List<TsRmMaint> getPersonUnderRm(String rmId) {
		String sql = "from TsRmMaint where lower(rm_id)=lower(?)";
		return this.getHibernateTemplate().find(sql, rmId);
	}
	/**
	 * 获取所有维护了RM的人员
	 */
	public List<TsRmMaint> getAllUserMainted() {
		String sql = "from TsRmMaint";
		return this.getHibernateTemplate().find(sql);
	}
	/**
	 * 根据loginId 查询员工信息
	 */
	public TsHumanBase loadHumanById(String employeeId) {
		String sql = "from TsHumanBase where lower(employee_id)=lower(?)";
		List list = this.getHibernateTemplate().find(sql, employeeId);
		if(list != null && list.size() > 0) {
			return (TsHumanBase)list.get(0);
		}
		return null;
	}
	/**
     * 列出否签核者下的所有人员
     * @param managerId String
     * @return List
     */
    public List<DtoUserBase> listPersonByManagerFromAD(String managerId) {
        LDAPUtil ldap = new LDAPUtil();
        return ldap.searchUserBasesByManager(LDAPUtil.DOMAIN_ALL, managerId);
    }
	public List<TsHumanBase> listHumanByRmFromDB(String rmId) {
		String sql = "from TsHumanBase where lower(res_manager_id)=lower(?)";
		return this.getHibernateTemplate().find(sql, rmId);
	}

    
     /**
     * 根据SITE得到SITE下对应的人员
     * @param site
     * @return List
     */
    public List getLoginIdList(String site, boolean viewAll) {
        String sql = "from TsHumanBase as t";
                
        if(viewAll == false) {
            sql += " where lower(site)=lower('" + site + "')";
        }
        sql+=" order by t.employeeId";
        List list = this.getHibernateTemplate().find(sql);
        return list;
    }
    
    /**
     * 根据SITE得到SITE下对应有效期（入职、离职日期）范围内的人员
     * @param site
     * @param begin
     * @param end
     * @return List
     */
    public List listEnableLoginId(String site, Date begin, Date end) {
    	String sql = "from TsHumanBase as t";
        sql += " where lower(site)=lower('" + site + "')";
        sql += " and in_date<=? and (out_date is null or out_date>?)";
        sql+=" order by t.employeeId";
        List list = this.getHibernateTemplate().find(sql, 
        		new Object[]{end, begin},
        		new Type[]{new DateType(), new DateType()});
        return list;
    }
    
      /**
       * 得到不重复的SITE集合
       * @return List
       */
    public List getSiteFromHumanBase(final String site) {
        final String sql = "select distinct t.site"
            +" from TsHumanBase as t where lower(site)<>lower(?)";
        List list = (List)this.getHibernateTemplate().execute(new
          HibernateCallback() {
        public Object doInHibernate(Session session) throws SQLException,
              HibernateException {
          Query query = session.createQuery(sql)
                        .setString(0,site);
           return query.list();
          }
    });
    return list;
    }
    /**
     * 列出所有人员(其入职日期和离职日期与指定时间有交集的)
     */
	public List<TsHumanBase> listAllHuman(Date begin, Date end, String site) {
		String sql = "from TsHumanBase where in_date<=? and " +
				     " (out_date is null or out_date>?) "
		            +" and lower(site)=lower(?) order by unit_code, employee_Id";
		return this.getHibernateTemplate().find(sql, new Object[]{end, begin, site}, 
				new Type[]{new DateType(), new DateType(), new StringType()});
	}
	/**
	 * 通过site从DB中获取RM下的人员
	 */
	public List<TsHumanBase> listHumanByRmFromDBBySite(String rmId, String site) {
		String sql = "from TsHumanBase where lower(res_manager_id)=lower(?) "
			        +" and lower(site)=lower(?)";
		return this.getHibernateTemplate().find(sql, new Object[]{rmId, site});
	}
    
    
    /**
     * 根据SITE得到SITE下对应有效期（入职、离职日期）范围内的人员
     * @param site
     * @param begin
     * @param end
     * @return List
     */
    public List listEnableLoginId(String site, DtoTsStatusQuery dtoQuery,boolean viewAll) {
        Date begin = dtoQuery.getBeginDate();
        Date end = dtoQuery.getEndDate();
        String deptId = dtoQuery.getDeptId();
        String deptIdStr = dtoQuery.getDeptIdStr();
        String sql = "from TsHumanBase as t";
        sql += " where in_date<=? and (out_date is null or out_date>?)";
        if(viewAll == false && !dtoQuery.getIsDeptQuery()) {
            sql += " and lower(site)=lower('" + site + "')";
        }
        if(deptId != null && dtoQuery.getIsSub() != null && dtoQuery.getIsSub()){
            sql +=" and t.unitCode in(select a.accountId from TsAccount as a "
                    +"where a.accountId ='"+deptId+"' or a.parentId in "+deptIdStr+")";
        }else if(deptId != null && dtoQuery.getIsSub() != null && !dtoQuery.getIsSub()){
            sql +=" and t.unitCode in(select a.accountId from TsAccount as a "
                +"where a.accountId ='"+deptId+"')";
        }
        sql+=" order by t.employeeId";
        List list = this.getHibernateTemplate().find(sql, 
                new Object[]{end, begin},
                new Type[]{new DateType(), new DateType()});
        return list;
    }
    
    public List listAllEmployee() {
        String sql = "from TsHumanBase ";
        return (List)this.getHibernateTemplate().find(sql);
    }
    
    /**
     * 根據部門得到該部門對應下的所有員工集合
     * @param deptIds
     * @return List
     */
    public List getLoginIdsByDept(String deptIds) {
            final String sql = "select h.employeeId from TsHumanBase as h " +
                               "where h.unitCode in "+deptIds+"";
              List loginIdList = (List)this.getHibernateTemplate().execute(new
                      HibernateCallback() {
                public Object doInHibernate(Session session) throws SQLException,
                 HibernateException {
                Query query = session.createQuery(sql);
                 return query.list();
                   }
             });
            return loginIdList;
          }
    /**
     * 根据部门代码查询部门下的人员
     * @param deptId
     * @return
     */
     public List listPersonsByDept(String deptId) {
    	 final String sql = "from TsHumanBase as h " +
         					"where h.unitCode = '"+deptId+"'";
    	 List list = (List)this.getHibernateTemplate().execute(new
                 HibernateCallback() {
           public Object doInHibernate(Session session) throws SQLException,
            HibernateException {
           Query query = session.createQuery(sql);
            return query.list();
              }
         });
    	 return list;
     }
    
    /**
     * 根據SITE和時間范圍查詢出每個員工在每個假別中的總工時數
     * @param begin
     * @param end
     * @return List
     */
     public List getSumLeaveHoursByDate(final Date begin,final Date end,final String site) {
               String status = DtoTsApproval.STATUS_APPROVED;
               final String sql = "select upper(m.loginId),c.name,sum(y.workHours) "
                   +" from TsTimesheetMaster as m, TsTimesheetDetail as d,"
                   +" TsTimesheetDay as y,TsHumanBase as h,TsCodeValue as c"
                   +" where y.workDate >=? and y.workDate <= ? and h.site=? and m.status='"+status+"'"
                   +" and c.isLeaveType='1' and upper(m.loginId)=upper(h.employeeId)"
                   +" and m.rid = d.tsRid  and d.rid=y.tsDetailRid and d.codeValueRid=c.rid"
                   +" group by upper(m.loginId),c.name"
                   +" order by upper(m.loginId),c.name";
               List list = (List)this.getHibernateTemplate().execute(new
                       HibernateCallback() {
                     public Object doInHibernate(Session session) throws SQLException,
                           HibernateException {
                       Query query = session.createQuery(sql)
                                     .setDate(0,begin)
                                     .setDate(1,end)
                                     .setString(2,site);
                        return query.list();
                       }
                 });
                return list;
        }
  
         /**
          * 取得直接人力
          * @return List
          */
         public List listDirectHuman() {
             String sql = "from TsHumanBase where is_Direct='D'";
             return (List)this.getHibernateTemplate().find(sql);
        }
    
}
