package server.essp.pms.activity.logic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import c2s.dto.DtoUtil;
import c2s.dto.IDto;
import c2s.essp.pms.wbs.DtoActivityWorker;
import db.essp.pms.Activity;
import db.essp.pms.ActivityPK;
import db.essp.pms.ActivityWorker;
import net.sf.hibernate.Session;
import server.essp.framework.logic.AbstractESSPLogic;
import server.framework.common.BusinessException;
import itf.hr.HrFactory;
import javax.sql.RowSet;
import java.util.Date;

public class LgActivityWorker extends AbstractESSPLogic {
    /**
     * 列出该Activity所有的Workers
     * @param acntRid Long
     * @param activityRid Long
     * @param jobCodeOptions List
     * @return List
     */
    public List listActivityWorkerDto(Long acntRid,Long activityRid,List jobCodeOptions){
        List result = new ArrayList();
        try {
            Session s = this.getDbAccessor().getSession();
            List l = s.createQuery("from ActivityWorker work " +
                                   "where work.activity.pk.acntRid=:acntRid and " +
                                   "work.activity.pk.activityId=:activityRid"
                     )
                     .setParameter("acntRid",acntRid)
                     .setParameter("activityRid",activityRid)
                     .list();
            Iterator i = l.iterator();
            while(i.hasNext()){
               ActivityWorker worker = (ActivityWorker) i.next();
               DtoActivityWorker dto = new DtoActivityWorker();
               DtoUtil.copyBeanToBean(dto,worker);
               dto.setAcntRid(worker.getActivity().getPk().getAcntRid());
               dto.setActivityRid(worker.getActivity().getPk().getActivityId());
               dto.setRid(worker.getRid());
               String jobCode =DtoUtil.getComboNameByValue(worker.getJobcodeId(),jobCodeOptions);
               dto.setJobCode(jobCode);
               String str = "select * from pms_act_worker_actualhours t where t.login_id='"+dto.getLoginId()
                            +"' and t.acnt_rid="+dto.getAcntRid()+" and t.activity_rid="
                            +dto.getActivityRid()+"";
               RowSet rowset = this.getDbAccessor().executeQuery(str);
               while(rowset.next()){
                  Date start = (Date)rowset.getDate("ACTUAL_START");
                  Date finish = (Date)rowset.getDate("ACTUAL_FINISH");
                  long hours = (long)rowset.getLong("ACTUAL_HOURS");
                  dto.setActualStart(start);
                  dto.setActualFinish(finish);
                  dto.setActualWorkTime(new Long(hours));
                  break;
               }
               result.add(dto);
            }
        } catch (Exception ex) {
            log.error(ex);
            throw new BusinessException("ACT_WORKER_001","error while listing workers of activity:["+activityRid+"] acount:["+acntRid+"]",ex);
        }
        return result;
    }

    public boolean isActivityWorkerDto(Long acntRid,Long activityRid,String loginId){
        boolean isWork = false;
        try {
            Session s = this.getDbAccessor().getSession();
            List l = s.createQuery("from ActivityWorker work " +
                                   "where work.activity.pk.acntRid=:acntRid and " +
                                   "work.activity.pk.activityId=:activityRid "+
                                   "and work.loginId=:loginId"
                     )
                     .setParameter("acntRid", acntRid)
                     .setParameter("activityRid", activityRid)
                     .setParameter("loginId", loginId)
                     .list();
            Iterator i = l.iterator();
            while (i.hasNext()) {
                 isWork = true;
                 break;
            }
        }catch (Exception ex) {
            log.error(ex);
            throw new BusinessException("ACT_WORKER_001","error while get isActivityWorkerDto",ex);
        }
        return isWork;
    }

    public List listActivityWorkerDto(Long acntRid,Long activityRid){
        List jobCodeList = HrFactory.create().listComboJobCode();
        return  listActivityWorkerDto(acntRid,activityRid,jobCodeList);
    }

    public void add(DtoActivityWorker dto){
        try {
            ActivityPK pk = new ActivityPK(dto.getAcntRid(),dto.getActivityRid());
            Activity  activity = new Activity();
            activity.setPk(pk);
            ActivityWorker worker = new ActivityWorker();
            DtoUtil.copyBeanToBean(worker,dto);
            worker.setActivity(activity);
            this.getDbAccessor().save(worker);
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error(ex);
            throw new BusinessException("ACT_WORKER_002","error while adding activity worker!",ex);
        }
    }

    public void delete(DtoActivityWorker dto){
        ActivityWorker worker = new ActivityWorker(dto.getRid());
        try {
            this.getDbAccessor().delete(worker);
        } catch (Exception ex) {
            throw new BusinessException("ACT_WORKER_003","error while delete worker:rid ["+dto.getRid()+"]",ex);
        }
    }

    public void update(DtoActivityWorker dto){
        try {
            Session s = this.getDbAccessor().getSession();
            ActivityWorker worker = (ActivityWorker) s.load(ActivityWorker.class,dto.getRid());
            DtoUtil.copyBeanToBean(worker,dto);
            s.flush();
        } catch (Exception ex) {
            throw new BusinessException("ACT_WORKER_004","error while update worker:rid ["+dto.getRid()+"]",ex);
        }
    }
    /**
     * 更新界面传入的Dto List
     * @param list List
     */
    public void updateDtoList(List list){
        Iterator i = list.iterator();
        while(i.hasNext()){
           DtoActivityWorker dto = (DtoActivityWorker) i.next();
           if (dto.isInsert()) {
               this.add(dto);
               dto.setOp(IDto.OP_NOCHANGE);
           } else if (dto.isDelete()) {
               this.delete(dto);
               i.remove();
           } else if (dto.isModify()) {
               this.update(dto);
               dto.setOp(IDto.OP_NOCHANGE);
            }
        }
    }
}
