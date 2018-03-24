package server.essp.timesheet.weeklyreport.dao;

import java.sql.SQLException;
import java.util.*;

import db.essp.timesheet.TsTimesheetDay;
import db.essp.timesheet.TsTimesheetDetail;
import db.essp.timesheet.TsTimesheetMaster;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import net.sf.hibernate.type.*;

import org.springframework.orm.hibernate.HibernateCallback;
import org.springframework.orm.hibernate.support.HibernateDaoSupport;

import c2s.essp.timesheet.weeklyreport.DtoTimeSheet;


/**
 * <p>Title: time sheet dao class </p>
 *
 * <p>Description: access essp db for time sheet data </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class TimeSheetDaoImp extends HibernateDaoSupport implements ITimeSheetDao {
    private final static LongType longType = new LongType();
    public void addTimeSheetMaster(TsTimesheetMaster master) {
        this.getHibernateTemplate().save(master);
    }

    public void addTimeSheetDetail(TsTimesheetDetail detail) {
        this.getHibernateTemplate().save(detail);
    }

    public void addTimeSheetDay(TsTimesheetDay day) {
        //没有工时的不保存
//        if(day.getWorkHours() == null ||
//           Double.valueOf(0).equals(day.getWorkHours())) {
//            return;
//        }
        this.getHibernateTemplate().save(day);
    }

    public void updateTimeSheetMaster(TsTimesheetMaster master) {
        this.getHibernateTemplate().update(master);
    }

    public void updateTimeSheetMasterStatus(Long masterRid, String status) {
        TsTimesheetMaster tsMaster = (TsTimesheetMaster) this.getHibernateTemplate()
                                     .load(TsTimesheetMaster.class, masterRid);
        tsMaster.setStatus(status);
        this.getHibernateTemplate().update(tsMaster);
    }

    public void updateTimeSheetDetail(TsTimesheetDetail detail) {
        this.getHibernateTemplate().update(detail);
    }

    public void updateTimeSheetDetailStatus(Long detailRid, String status) {
        TsTimesheetDetail tsDetail = (TsTimesheetDetail) this.getHibernateTemplate()
                                     .load(TsTimesheetDetail.class, detailRid);
        tsDetail.setStatus(status);
        this.getHibernateTemplate().update(tsDetail);
    }

    public void updateTimeSheetDay(TsTimesheetDay day) {
        if(day == null || day.getRid() == null) {
            return;
        }

        this.getHibernateTemplate().update(day);
    }

    public void deleteTimeSheetMaster(TsTimesheetMaster master) {
        this.getHibernateTemplate().delete(master);
    }

    public void deleteTimeSheetDetail(TsTimesheetDetail detail) {
        deleteTimeSheetDays(detail.getRid());
        this.getHibernateTemplate().delete(detail);
    }

    private void deleteTimeSheetDays(Long detailRid) {
        this.getHibernateTemplate()
                .delete("from TsTimesheetDay where ts_detail_rid = ?",
                        detailRid, longType);
    }

    public void deleteTimeSheetDay(TsTimesheetDay day) {
        this.getHibernateTemplate().delete(day);
    }

    public TsTimesheetMaster getTimeSheetMaster(Long rid) {
        return (TsTimesheetMaster)
                this.getHibernateTemplate().load(TsTimesheetMaster.class, rid);
    }


    public TsTimesheetMaster getTimeSheetMaster(String loginId, Long tsId) {
         List<TsTimesheetMaster> list = (List)this.getHibernateTemplate()
                    .find("from TsTimesheetMaster where upper(login_id) = upper(?) and ts_id = ?",
                    new Object[] {loginId, tsId}, new AbstractType[]{new StringType(), longType});
         if(list != null && list.size() > 0) {
             return list.get(0);
         }
         return null;
    }
    
    public TsTimesheetMaster getLastTimeSheetMaster(String loginId, Date nowDate) {
    	String hql = "select t from TsTimesheetMaster t, TsTimesheetDetail d "
 		   + "where upper(t.loginId) = upper(?) and t.beginDate < ? "
 		   + "and t.rid = d.tsRid order by t.beginDate desc";
        List<TsTimesheetMaster> list = (List)this.getHibernateTemplate()
                   .find(hql , new Object[] {loginId, nowDate}, 
                		   new AbstractType[]{new StringType(), new DateType()});
        if(list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
   }
    
    public List getTimeSheetMasterList(String loginId, Long tsId) {
        return this.getHibernateTemplate()
                    .find("from TsTimesheetMaster where upper(login_id) = upper(?) and ts_id = ?",
                    new Object[] {loginId, tsId}, new AbstractType[]{new StringType(), longType});
        
    }

    public List<TsTimesheetDetail> listTimeSheetDetail(Long tsRid) {
        return this.getHibernateTemplate()
                .find("from TsTimesheetDetail where ts_rid = ?",
                tsRid);
    }

    public List<TsTimesheetDay> getTimeSheetDay(Long detailRid) {
        return this.getHibernateTemplate()
                .find("from TsTimesheetDay where ts_detail_rid = ?",
                detailRid);
    }
    /**
     * 根据TimesheetMaster表的rid查询TimesheetDetail记录
     * @param tsRid Long
     * @return List
     */
    public List<TsTimesheetDetail> listTimeSheetDetailByTsRid(Long tsRid) {
        String sql = "from TsTimesheetDetail where ts_rid=?";
        return this.getHibernateTemplate().find(sql, tsRid);
    }
    /**
     * 根据loginId,开始,结束日期查询TimesheetMaster记录
     * @param loginId String
     * @param begin Date
     * @param end Date
     * @return TsTimesheetMaster
     */
    public List<TsTimesheetMaster> listTimeSheetMasterByLoginIdDate(String loginId,
            Date begin, Date end) {
        String sql = "from TsTimesheetMaster where lower(login_id)=lower(?) " +
                     "and ((begin_date>=? and begin_date<=?) " +
                           "or (end_date>=? and end_date<=?) " +
                           "or (begin_date<=? and end_date>=?))";
        List<TsTimesheetMaster> list =
                this.getHibernateTemplate()
                .find(sql,new Object[]{loginId, begin, end, begin, end, begin, end},
                new AbstractType[]{new StringType(), new DateType(), new DateType(), 
                        new DateType(), new DateType(), new DateType(), new DateType()});
        return list;
    }
    /**
     * 根据loginId,开始,结束日期及状态查询TimesheetMaster记录
     * @param loginId String
     * @param begin Date
     * @param end Date
     * @param status String
     * @return TsTimesheetMaster
     */
    public List<TsTimesheetMaster> listTimeSheetMasterByDateStatus(String loginId,
            Date begin, Date end, String status) {
        String sql = "from TsTimesheetMaster where lower(login_id)=lower(?) " +
                     "and ((begin_date>=? and begin_date<=?) " +
                           "or (end_date>=? and end_date<=?) " +
                           "or (begin_date<=? and end_date>=?)) " +
                     "and rid in (select t.tsRid from TsTimesheetDetail t where t.status in "+status+")";
        List<TsTimesheetMaster> list =
                this.getHibernateTemplate()
                .find(sql,new Object[]{loginId, begin, end, begin, end, begin, end},
                new AbstractType[]{new StringType(), new DateType(), new DateType(), 
                        new DateType(), new DateType(), new DateType(), new DateType()});
        return list;
    }
    /**
     * 根据TimesheetDetail的rid查询TimesheetDay记录
     * @param detailRid Long
     * @return List
     */
    public List<TsTimesheetDay> listTimeSheetDayByDetailRid(Long detailRid) {
        String sql = "from TsTimesheetDay where ts_detail_rid=?";
        return this.getHibernateTemplate().find(sql, detailRid);
    }
    /**
     * 根据专案的rid和TimesheetMaster的rid查询
     * TimesheetDetail记录
     * @param acntRid Long
     * @param tsRid Long
     * @return List
     */
    public List<TsTimesheetDetail> listTimeSheetDetailByAcntRidTsRid(Long acntRid, Long tsRid) {
        String sql = "from TsTimesheetDetail where account_rid=? and ts_rid=?";
        return this.getHibernateTemplate().find(sql,
                                                new Object[]{acntRid, tsRid},
                                                new AbstractType[] {new LongType(), new LongType()});
    }
    /**
     * 查询detail表中没有被审核通过的记录
     * @param tsRid Long
     * @param status String
     * @return List
     */
    public List<TsTimesheetDetail> listTimeSheetDetailNotEqualsStatusByTsRid(Long tsRid,
            String status) {
        String sql = "from TsTimesheetDetail where ts_rid=? and status<>?";
        return this.getHibernateTemplate().find(sql,
                                                new Object[]{tsRid, status},
                                                new AbstractType[] {new LongType(), new StringType()});
    }
    /**
     * 根据TsMaster的rid和状态查询TsDetail资料
     * @param tsRid Long
     * @param status String
     * @return List
     */
    public List<TsTimesheetDetail> listTimeSheetDetailByTsRidStatus(Long tsRid, String status) {
        String sql = "from TsTimesheetDetail where ts_rid=? and status in "+status;
        return this.getHibernateTemplate().find(sql, tsRid);
    }

    public List<TsTimesheetDetail> listTimeSheetDetailByAcntRidTsRidByStatus(Long acntRid,
            Long tsRid, String status) {
        String sql = "from TsTimesheetDetail where account_rid=? and ts_rid=? and status in "+status;
        return this.getHibernateTemplate().find(sql,
                                                new Object[]{acntRid, tsRid},
                                                new AbstractType[] {new LongType(), new LongType()});

    }
    /**
     * 根据状态查询detail资料
     * @param status String
     * @return List
     */
    public List<TsTimesheetDetail> listTimeSheetDetailByStatus(String status) {
        String sql = "from TsTimesheetDetail where status in "+status;
        return this.getHibernateTemplate().find(sql);
    }
    /**
     * 
     * @param status
     * @param site
     * @return
     */
    public List listTimeSheetDetailByStatusSite(String status, String site) {
        final String sql = "select de.rid from TsTimesheetDetail de,TsTimesheetMaster m, TsHumanBase h "
                   + " where de.status in "+status+" "
                   + " and de.tsRid=m.rid "
                   + " and upper(m.loginId)=upper(h.employeeId) "
                   + " and lower(h.site)= lower('"+site+"')";
        List list = (List) this.getHibernateTemplate().execute(new  HibernateCallback(){
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {
                Query q = session.createQuery(sql);
                return q.list();
            }});
        return list;
    }
    public TsTimesheetDetail geTimeSheetDetail(Long rid) {
        return (TsTimesheetDetail) this.getHibernateTemplate()
                                           .load(TsTimesheetDetail.class, rid);
    }
    /**
     * 根据状态查询master资料
     * @param status String
     * @return List
     */
    public List<TsTimesheetMaster> listTimeSheetMasterByStatus(String status) {
        String sql = "from TsTimesheetMaster where status in "+status;
        return this.getHibernateTemplate().find(sql);
    }
    public List listTimeSheetMasterByStatusSite(String status, String site) {
        final String sql = "select m.rid from TsTimesheetMaster m, TsHumanBase h "
               + " where m.status in "+status+" "
               + " and upper(m.loginId)=upper(h.employeeId) "
               + " and lower(h.site)= lower('"+site+"')";
        List list = (List) this.getHibernateTemplate().execute(new  HibernateCallback(){
        public Object doInHibernate(Session session)
                throws HibernateException, SQLException {
            Query q = session.createQuery(sql);
            return q.list();
        }});
        return list;
    }
    /**
     * 查询不在status中的detail信息
     * @param tsRid Long
     * @param status String
     * @return List
     */
    public List<TsTimesheetDetail> listTimeSheetDetailNotInStatusByTsRid(Long tsRid, String status) {
        String sql = "from TsTimesheetDetail where ts_rid=? and status not in "+status;
        return this.getHibernateTemplate().find(sql, tsRid);
    }
    /**
     * 查询某人员填写的指定状态的周报信息
     */
    public List<Object[]> getDataForRemindMail(String status, final String loginId, final Date begin, final Date end) {
        final String sql = "select m.loginId,de.accountRid,a.manager,a.accountId,a.accountName,m.status,m.beginDate,m.endDate " +
                " from TsTimesheetMaster as m, TsTimesheetDetail as de,TsAccount as a " +
                " where m.status in "+status+
                " and upper(m.loginId)=upper(?)" +
                " and m.beginDate=?"+
                " and m.endDate=?"+
                " and de.tsRid=m.rid " +
                " and a.rid=de.accountRid"+
                " group by m.loginId,de.accountRid,a.manager,a.accountId,a.accountName,m.status,m.beginDate,m.endDate" +
                " order by m.loginId,de.accountRid";
        List<Object[]> list = (List) this.getHibernateTemplate().execute(new HibernateCallback(){
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query q = session.createQuery(sql)
                                  .setString(0, loginId)
                                  .setDate(1, begin)
                                  .setDate(2, end);
                return q.list();
            }
            
        });
        return list;
    }
    /**
     * 查询某专案经理下的指定状态的周报信息
     */
    public List<Object[]> getDataForRemindPmMail(String status, final String loginId,
                                           final Date begin, final Date end, String site) {
        final String sql = "select m.loginId,de.accountRid,a.manager,a.accountId,a.accountName,de.status,m.beginDate,m.endDate " +
                " from TsTimesheetMaster m, TsTimesheetDetail de,TsAccount a, TsHumanBase h " +
                " where de.status in "+status+
                " and a.manager=?" +
                " and m.beginDate=?" +
                " and m.endDate=?" +
                " and de.tsRid=m.rid " +
                " and upper(m.loginId)=upper(h.employeeId) "+
                " and lower(h.site)=lower('"+ site +"')"+
                " and a.rid=de.accountRid"+
                " group by m.loginId,de.accountRid,a.manager,a.accountId,a.accountName,de.status,m.beginDate,m.endDate" +
                " order by m.loginId,de.accountRid";
        List<Object[]> list = (List) this.getHibernateTemplate().execute(new HibernateCallback(){
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query q = session.createQuery(sql)
                                  .setString(0, loginId)
                                  .setDate(1, begin)
                                  .setDate(2, end);
               
                return q.list();
            }
            
        });
        return list;
    }
    /**
     * 查询某部门下的指定状态的周报信息
     */
    public List<Object[]> getDataForRemindRmMail(String status, final String loginId, 
                                                 final Date begin, final Date end) {
        final String sql = "select m.loginId,m.beginDate,m.endDate " +
                           " ,a.accountId,a.accountName,a.manager " +
                           " from TsTimesheetMaster m, "+
                           " TsTimesheetDetail de,TsAccount a " +
                           " where m.status in "+status+
                           " and de.tsRid=m.rid " +
                           " and a.rid=de.accountRid " +
                           " and upper(m.loginId)=upper(?)" +
                           " and m.beginDate=?" +
                           " and m.endDate=?" +
                           " group by m.loginId,m.beginDate,m.endDate " +
                           " ,a.accountId,a.accountName,a.manager" + 
                           " order by m.loginId,m.beginDate,a.accountId";
        List<Object[]> list = (List) this.getHibernateTemplate().execute(
                new HibernateCallback() {
                    public Object doInHibernate(Session session)
                            throws HibernateException, SQLException {
                        Query q = session.createQuery(sql)
                                         .setString(0, loginId)
                                         .setDate(1, begin)
                                         .setDate(2, end);

                        return q.list();
                    }

                });
        return list;
    }
    /**
     * 查询指定状态下的周报填写人的工号和项目代号及名称
     */
    public List<Object[]> getPersonAcntForRemindMail(String status, final Date begin, final Date end, String site) {
        final String sql = "select m.loginId,a.accountId,a.accountName from TsTimesheetMaster m, "+
                           " TsTimesheetDetail de,TsAccount a, TsHumanBase h " +
                           " where m.status in "+status+
                           " and de.tsRid=m.rid " +
                           " and m.beginDate=?" +
                           " and m.endDate=?" +
                           " and upper(m.loginId)=upper(h.employeeId)" +
                           " and lower(h.site)=lower('"+ site +"')"+
                           " and a.rid=de.accountRid " +
                           " group by m.loginId,a.accountId,a.accountName" +
                           " order by m.loginId,a.accountId";
        List<Object[]> list = (List<Object[]>) this.getHibernateTemplate()
                                                .execute(new HibernateCallback(){
            public Object doInHibernate(Session session) 
                            throws HibernateException, SQLException {
                Query q = session.createQuery(sql)
                                  .setDate(0, begin)
                                  .setDate(1, end);
                return q.list();
            }
        });
        return list;
    }
    /**
     * 查询指定状态下的周报填写人的工号
     */
    public List<String> getPersonForRemindMail(String status, final Date begin, final Date end, String site) {
        final String sql = "select m.loginId from TsTimesheetMaster m, "+
                           " TsTimesheetDetail de,TsAccount a, TsHumanBase h " +
                           " where m.status in "+status+
                           " and de.tsRid=m.rid " +
                           " and m.beginDate=?" +
                           " and m.endDate=?" +
                           " and upper(m.loginId)=upper(h.employeeId)" +
                           " and lower(h.site)=lower('"+ site +"')"+
                           " and a.rid=de.accountRid " +
                           " group by m.loginId" +
                           " order by m.loginId";
        List<String> list = (List<String>) this.getHibernateTemplate()
                                                .execute(new HibernateCallback(){
            public Object doInHibernate(Session session) 
                            throws HibernateException, SQLException {
                Query q = session.createQuery(sql)
                                  .setDate(0, begin)
                                  .setDate(1, end);
                return q.list();
            }
        });
        return list;
    }
    /**
     * 查询指定状态下的周报的专案经理
     */
    public List<String> getPmForRemindMail(String status, final Date begin, final Date end) {
        final String sql = "select a.manager from TsTimesheetMaster m, "
                + " TsTimesheetDetail de,TsAccount a " 
                + " where de.status in "+ status
                + " and de.tsRid=m.rid "
                + " and m.beginDate=?" 
                + " and m.endDate=?" 
                + " and a.rid=de.accountRid " 
                + " group by a.manager"
                + " order by a.manager";
        List<String> list = (List<String>) this.getHibernateTemplate().execute(
                new HibernateCallback() {
                    public Object doInHibernate(Session session)
                            throws HibernateException, SQLException {
                        Query q = session.createQuery(sql)
                                         .setDate(0, begin)
                                         .setDate(1, end);
                        return q.list();
                    }
                });
        return list;
    }
    
    /**
     * 根据rid取得TsTimesheetDay
     */
    public TsTimesheetDay loadByRid(Long rid) {   
        String sql = "from TsTimesheetDay where rid=?";
        List list =(List)this.getHibernateTemplate().find(sql, 
                rid,new LongType());
        if(list != null && list.size()>0){
            return (TsTimesheetDay)list.get(0);
        }else{
            return null;
        }
    }
    
    /**
     * 根据tsRid删除TsTimesheetDetail
     */
    private void deleteTsDetailByTsRid(final Long tsRid) {      
        this.getHibernateTemplate().delete("from TsTimesheetDetail d where d.tsRid=?",
                tsRid, longType);
       
    }

    public void deleteTimeSheetData(String loginId, Long tsId, Long masterRid) {
        //根据loginId，beginDate，endDate找到MASTER中指定记录
        List masterList = getTimeSheetMasterList(loginId,tsId);
        //根据tsRid得到DETAIL集合
        if(masterList != null){
         for(int i=0;i< masterList.size();i++){
          TsTimesheetMaster master =(TsTimesheetMaster)masterList.get(i);
          List detailList = listTimeSheetDetailByTsRid(master.getRid());
        //循环detailList，将得到的detailRid删除对应的timesheetDay
          if(detailList != null){
          for(int j=0;j<detailList.size();j++){
            TsTimesheetDetail detail = (TsTimesheetDetail)detailList.get(j);
            deleteTimeSheetDays(detail.getRid());
             }
          }
          deleteTsDetailByTsRid(master.getRid());
          if(masterRid == null || !masterRid.equals(master.getRid())) {
        	  deleteTimeSheetMaster(master);
          }
         }
        }
        
    }
    
    public void deleteTimeSheetData(String loginId, Long tsId) {
        this.deleteTimeSheetData(loginId, tsId, null);
        
    }
    
    public void deleteTimeSheetInnerData(Long masterRid) {
        if(masterRid == null) {
            return;
        }
          List detailList = listTimeSheetDetailByTsRid(masterRid);
        //循环detailList，将得到的detailRid删除对应的timesheetDay
          if(detailList != null){
          for(int j=0;j<detailList.size();j++){
            TsTimesheetDetail detail = (TsTimesheetDetail)detailList.get(j);
            deleteTimeSheetDays(detail.getRid());
             }
          }
          deleteTsDetailByTsRid(masterRid);
    }

    /**
     * 根据LOGINID和时间范围判断是否填写周报
     */
    public Boolean isFillTS(final String loginId,final Date begin,final Date end) {
        List list = (List) this.getHibernateTemplate().execute(new HibernateCallback(){
            public Object doInHibernate(Session session) throws SQLException,
                    HibernateException {
                Query q = session.createQuery("from TsTimesheetMaster as m,TsTimesheetDetail as d"
                          +" where m.rid=d.tsRid and upper(m.loginId)=upper(?) and m.beginDate=? and m.endDate=?")
                          .setString(0,loginId)
                          .setDate(1,begin)
                          .setDate(2,end);
                return q.list();
            }
        });
       if(list != null && list.size()>0){
           return true;
       }else{
           return false;
       }
     
    }
    
    /**
     * 根据LOGINID和时间范围判断是否填写周报
     */
    public Boolean isFillTS(final String loginId,final Long tsId) {
        List list = (List) this.getHibernateTemplate().execute(new HibernateCallback(){
            public Object doInHibernate(Session session) throws SQLException,
                    HibernateException {
                Query q = session.createQuery("from TsTimesheetMaster as m,TsTimesheetDetail as d"
                          +" where m.rid=d.tsRid and upper(m.loginId)=upper(?) and m.tsId=?")
                          .setString(0,loginId)
                          .setLong(1,tsId);
                return q.list();
            }
        });
       if(list != null && list.size()>0){
           return true;
       }else{
           return false;
       }
     
    }
    
    /**
     * 根据LOGINID和时间范围得到已提交的周报
     */
     public List getSubmitTsList(final Date begin,final Date end) {
         List list = (List) this.getHibernateTemplate().execute(new HibernateCallback(){
             public Object doInHibernate(Session session) throws SQLException,
                       HibernateException {
               Query q = session.createQuery("select distinct m.tsId,m.loginId,m.beginDate,m.endDate" 
                              +" from TsTimesheetMaster as m,TsTimesheetDetail as d"
                              +" where m.rid=d.tsRid and m.beginDate>=? and m.endDate<=?"
                              +" and m.status not in ('"
                              + DtoTimeSheet.STATUS_ACTIVE + "', '"
                              + DtoTimeSheet.STATUS_REJECTED + "')")
                              .setDate(0,begin)
                              .setDate(1,end);
                return q.list();
                }
            });
          return list;
        }
     
     /**
      * 根据LOGINID和时间范围得到已审批的周报
      */
      public List getApprovedTsList(final Date begin,final Date end) {
          List list = (List) this.getHibernateTemplate().execute(new HibernateCallback(){
              public Object doInHibernate(Session session) throws SQLException,
                        HibernateException {
                Query q = session.createQuery("select distinct m.tsId,m.loginId,m.beginDate,m.endDate"
                               +" from TsTimesheetMaster as m,TsTimesheetDetail as d"
                               +" where m.rid=d.tsRid and m.beginDate>=? and m.endDate<=?"
                               +" and m.status = '" + DtoTimeSheet.STATUS_APPROVED + "'")
                               .setDate(0,begin)
                               .setDate(1,end);
                 return q.list();
                 }
             });
           return list;
         }

}