package server.essp.timesheet.account.labor.dao;

import java.util.List;
import db.essp.timesheet.TsLaborResource;
import org.springframework.orm.hibernate.support.HibernateDaoSupport;

import c2s.essp.timesheet.account.DtoAccount;
import c2s.essp.timesheet.labor.DtoLaborResource;
import org.springframework.orm.hibernate.HibernateCallback;
import net.sf.hibernate.Session;
import java.sql.SQLException;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.type.LongType;

/**
 * <p>Title: labor resource data access object</p>
 *
 * <p>Description: 与项目人力相关的数据访问</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class LaborDaoImp extends HibernateDaoSupport implements ILaborDao {
    private final static LongType longType = new LongType();

    /**
     * 为某项目增加人力资源
     *
     * @param labor TsLaborResource
     * @todo Implement this
     *   server.essp.timesheet.Account.labor.dao.ILaborDao method
     */
    public void addLabor(TsLaborResource labor) {
        this.getHibernateTemplate().save(labor);
    }

    /**
     * 把指定人力资源从项目中删除
     *
     * @param labor TsLaborResource
     * @todo Implement this
     *   server.essp.timesheet.Account.labor.dao.ILaborDao method
     */
    public void deleteLabor(TsLaborResource labor) {
        this.getHibernateTemplate().delete(labor);
    }

    /**
     * 列出指定项目下的人力资源
     *
     * @param AccountRid Long
     * @return List<TsLaborResource>
     * @todo Implement this
     *   server.essp.timesheet.Account.labor.dao.ILaborDao method
     */
    public List listLabor(Long accountRid) {
        String sql = "from TsLaborResource where Account_Rid=?";
        return (List)this.getHibernateTemplate()
                         .find(sql, accountRid, longType);
    }

    /**
     * 保存人力资源信息
     *
     * @param labor TsLaborResource
     * @todo Implement this
     *   server.essp.timesheet.Account.labor.dao.ILaborDao method
     */
    public void saveLabor(TsLaborResource labor) {
        this.getHibernateTemplate().update(labor);
    }
    /**
     * 根据rid查询labor资料
     * @param rid Long
     * @return TsLaborResource
     */
    public TsLaborResource loadLabor(Long rid) {
       return (TsLaborResource)this.getHibernateTemplate()
               .load(TsLaborResource.class, rid);
    }
    /**
     * 根据专案第id和login id查询labor资料
     * @param accountId String
     * @param loginId String
     * @return TsLaborResource
     */
    public TsLaborResource loadByAccountIdLoginId(Long accountRid,
                                                  String loginId) {
        String sql = "from TsLaborResource where Account_Rid=? and upper(Login_Id)=upper(?)";
        List list = this.getHibernateTemplate().find(sql,
                         new Object[]{accountRid, loginId});
        TsLaborResource tsLabor = null;
        if(list != null && list.size() > 0){
            tsLabor = (TsLaborResource)list.get(0);
        }
        return tsLabor;
    }

    /**
   * 根据loginId和状态得到AccountRid的集合
   * @param loginId String
   * @return List
   */
  public List getAccountRidByLabor(String loginId) {
      String status = DtoLaborResource.DTO_STATUS_IN;
      List list = this.getHibernateTemplate()
                  .find("from TsLaborResource where upper(login_id) = upper(?) and status = ?",
                        new String[]{loginId,status});
      return list;
  }
  /**
   * 列出对应资源经理管理的所有人员
   * @param managerId String
   * @return List
   */
  public List<String> listLoginIdByResourceManager(final String managerId) {
        List list = (List) this.getHibernateTemplate().execute(new HibernateCallback(){
            public Object doInHibernate(Session session) throws SQLException,
                    HibernateException {
                Query q = session.createQuery("select distinct t.loginId from TsLaborResource as t where t.rm=?");
                q.setString(0, managerId);
                return q.list();
            }
        });
        return list;
    }

  /**
   * 列出所有状态为IN的员工的LOGINID
   * @param deptStr
   * @return List
   */
   public List listAllEmpId(final String deptStr) {
       final String status = DtoLaborResource.DTO_STATUS_IN;
       final String accountStatus = DtoAccount.STATUS_NORMAL;
       List list = (List) this.getHibernateTemplate().execute(new HibernateCallback(){
           public Object doInHibernate(Session session) throws SQLException,
                   HibernateException {
               if(deptStr != null){
                Query q = session.createQuery("select distinct l.loginId from TsLaborResource as l,"
                       +"TsAccount as t where t.rid=l.accountRid and l.status=? and t.accountStatus=? and t.rid in "+deptStr+"")
                         .setString(0,status)
                         .setString(1,accountStatus);
                return q.list();
               }else{
                   Query q = session.createQuery("select distinct l.loginId from TsLaborResource as l,"
                           +"TsAccount as t where t.rid=l.accountRid and l.status=? and t.accountStatus=?")
                           .setString(0,status)
                           .setString(1,accountStatus);
                   return q.list();
               }
              
           }
       });
       return list;
   }

	public List listPersonInProject(final String accountId) {
		final String sql = "select l.loginId from TsLaborResource as l,TsAccount a " 
			       + " where a.rid=l.accountRid "
			       + " and l.status='"+DtoLaborResource.DTO_STATUS_IN+"'"
			       + " and a.accountId=?";
		List list = (List) this.getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query q = session.createQuery(sql)
				             .setString(0, accountId);
				return q.list();
			}
		});
		return list;
	}

}
