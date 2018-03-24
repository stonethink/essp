package server.essp.timesheet.step.management.dao;


import java.util.Date;
import java.util.List;
import org.springframework.orm.hibernate.support.HibernateDaoSupport;
import c2s.essp.common.calendar.WorkCalendar;
import c2s.essp.timesheet.dailyreport.DtoDailyExport;
import db.essp.timesheet.TsStep;

public class StepManagementDaoImpl extends HibernateDaoSupport implements
		IStepManagementDao {
	public List listStepByActivityId(Long activityId) {
		String sql = "from TsStep t where t.taskId= ?"
				+ " and t.rst='N' order by t.seqNum";
		return this.getHibernateTemplate().find(sql, activityId);
	}
	
	public List listDeaultStepsByLongId(String loginId, Long activityId) {
		String sql = "from TsStep t where (lower(t.resourceId)=lower(?) or t.isCorp='1') and t.taskId=?" +
				" and t.completeFlag='0' and t.rst='N' order by t.seqNum";
		return this.getHibernateTemplate().find(sql, new Object[]{loginId, activityId});
	}

	public void addStep(TsStep step) {
		this.getHibernateTemplate().save(step);
	}

	public void updateStep(TsStep step) {
		this.getHibernateTemplate().update(step);
	}

	public TsStep loadByRid(Long rid) {
		return (TsStep) this.getHibernateTemplate().load(TsStep.class, rid);
	}

    public List getAllStepByPrjId(Long projId) {
        String sql = "from TsStep t where t.projId= ?"
            + " and t.rst='N' order by t.seqNum asc";
           return this.getHibernateTemplate().find(sql, projId);
    }
    
    /**
     * 获取某Step下在日报中已填写的所有累计工时
     * @param stepRid Long
     * @return Double
     */
    public Double getStepCumulativeHours(Long stepRid) {
    	if(stepRid == null) {
    		return 0D;
    	}
    	String hql = "select sum(t.workTime) from TsDailyReport t where t.stepRid = ?";
    	List list = this.getHibernateTemplate().find(hql, stepRid);
    	if(list != null && list.size() > 0) {
    		return (Double) list.get(0);
    	}
    	return 0D;
    }
    
    /**
     * 列出某专案下某人今天昨天的step信息
     * @param projId
     * @param resourceId
     * @param today
     * @param yesterday
     * @return
     */
    public List listPersonStep(final Long taskId, final String resourceId, 
    		final Date today, final Date yesterday) {
    	final String sql = "from TsStep t where t.taskId=? "
    			   + " and ((?>=t.planStart and ?<=t.planFinish)"
    			   + "   or (?>=t.planStart and ?<=t.planFinish))"
    			   + " and t.resourceId=? and t.rst='N' order by t.taskId";
    	return this.getHibernateTemplate().find(sql, new Object[]{taskId, today, today, 
    			                                   yesterday, yesterday, resourceId});
    	
    }
    /**
     * 列出某专案下某人今天昨天的step信息
     * @param projId
     * @param today
     * @param yesterday
     * @return
     */
    public List listProjectStep(Long taskId,
    		Date day, String date) {
    	Date begin = WorkCalendar.resetBeginDate(day);
    	Date end = WorkCalendar.resetEndDate(day);
    	Object[] args = null;
    	String sql = "";
    	if(DtoDailyExport.DTO_YESTERDAY.equals(date)) {
    		sql = "from TsStep t where t.taskId=? "
 		       + " and ((t.planStart <=? and t.planFinish>=?)"
 			   + " or (t.actualStart <= ? and t.actualFinish>= ?))"
 			   + " and t.rst='N' order by t.taskId";
    		args = new  Object[]{taskId, end, begin, 
					end, begin};
    	} else if(DtoDailyExport.DTO_TODAY.equals(date)){
    		sql = "from TsStep t where t.taskId=? "
 		       + " and ((t.planStart <=? and t.planFinish>=?)"
 			   + " or (t.actualStart <= ? and t.actualFinish>= ?)"
 			   + " or (t.planStart <= ? and t.completeFlag = ?))"
 			   + " and t.rst='N' order by t.taskId";
    		args = new Object[]{taskId, end, begin, 
					end, begin, new Date(), "0"};
    	}
    	return this.getHibernateTemplate().find(sql, args);
    	
    }

    public List getStepDetailInfo() {
        String sql = "from TsStepTDetail t";
        return this.getHibernateTemplate().find(sql);
    }

}
