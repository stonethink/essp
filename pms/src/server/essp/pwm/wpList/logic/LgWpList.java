package server.essp.pwm.wpList.logic;

import java.util.ArrayList;
import java.util.List;

import c2s.essp.common.user.DtoUser;
import c2s.essp.pwm.wp.DtoPwWp;
import com.wits.util.StringUtil;
import essp.tables.PwWp;
import net.sf.hibernate.Query;
import server.essp.framework.logic.AbstractESSPLogic;
import server.essp.pwm.wp.logic.FPW01000CommonLogic;
import server.framework.common.BusinessException;
import java.util.*;
import essp.tables.PwWpchk;
import c2s.dto.DtoUtil;
import c2s.dto.IDto;
import javax.sql.RowSet;
import java.math.BigDecimal;

public class LgWpList extends AbstractESSPLogic {
    String loginId = "";

    public LgWpList(){
        DtoUser user = this.getUser();
        if(user != null){
            loginId = user.getUserLoginId();
        }

        if( StringUtil.nvl(loginId).equals("") == true ){
            //for test
            //loginId = "stone.shi";
        }
    }

    public DtoPwWp getDtoPmWpSum(Long acntRid,Long activityRid,String loginId){
        String sql = "select min(t.wp_plan_start) as plan_start, max(t.wp_plan_fihish) as plan_finish,sum(t.wp_req_wkhr) as req_wkhr from pw_wp t where t.project_id='" +
                     acntRid.toString() + "' and t.activity_id='" +
                     activityRid.toString() + "' and t.wp_worker like '%" + loginId +
                     "%'";
        DtoPwWp dto = new DtoPwWp();
        try{
            RowSet rowset = this.getDbAccessor().executeQuery(sql);
            if(rowset.next()){
               Date plan_start = rowset.getDate("plan_start");
               Date plan_finish = rowset.getDate("plan_finish");
               int req_wkhr = rowset.getInt("req_wkhr");
               dto.setWpPlanStart(plan_start);
               dto.setWpPlanFihish(plan_finish);
               dto.setWpReqWkhr(new BigDecimal(req_wkhr));
            }
        }catch(Exception ex){
            throw new BusinessException("Error when getDtoPmWp.", ex);
        }
        return dto;
    }

    public DtoPwWp getDtoPmWp(Long rid){
        String sql =" select t.wp_plan_start as plan_start, t.wp_plan_fihish as plan_finish, "
                    +"t.wp_req_wkhr as req_wkhr from pw_wp t where t.rid="+rid;
        DtoPwWp dto = new DtoPwWp();
        try{
            RowSet rowset = this.getDbAccessor().executeQuery(sql);
            if(rowset.next()){
               Date plan_start = rowset.getDate("plan_start");
               Date plan_finish = rowset.getDate("plan_finish");
               int req_wkhr = rowset.getInt("req_wkhr");
               dto.setWpPlanStart(plan_start);
               dto.setWpPlanFihish(plan_finish);
               dto.setWpReqWkhr(new BigDecimal(req_wkhr));
            }
        }catch(Exception ex){
            throw new BusinessException("Error when getDtoPmWp.", ex);
        }
        return dto;
    }

    public List list(Long acntRid, Long activityRid){
        log.info("01:loginid is" + loginId + ". list(acntRid - " + acntRid + " | activityRid - " + activityRid + ")");

        String sql;
        List pwWpList;
        List dtoPwWpList = new ArrayList();
        try {
            if (activityRid == null) {
                sql = " from PwWp t where t.account.rid=:acntRid ";
                Query q=this.getDbAccessor().getSession().createQuery(sql);
                q.setLong("acntRid", acntRid.longValue());
                pwWpList = q.list();
            } else {
                sql = " from PwWp t where t.account.rid=:acntRid "
                      + " and t.activity.pk.activityId=:activityRid ";
                pwWpList = this.getDbAccessor().getSession().createQuery(sql)
                           .setLong("acntRid", acntRid.longValue())
                           .setLong("activityRid", activityRid.longValue())
                           .list();
            }

            for (int i = 0; i < pwWpList.size(); i++) {

                PwWp pwWp = (PwWp)pwWpList.get(i);
                DtoPwWp dtoPwWp = FPW01000CommonLogic.createDToPwWp(getDbAccessor(),pwWp,loginId);
                dtoPwWpList.add(dtoPwWp);
            }
        }catch (Exception ex) {
            throw new BusinessException("E0001","Error when select wp list.", ex);
        }

        return dtoPwWpList;
    }

    public List list(Long acntRid,String startTime,String endTime){
        log.info("01:loginid is" + loginId + ". list(acntRid - " + acntRid + ")");

        String sql;
        List pwWpList;
        List dtoPwWpList = new ArrayList();
        try {

            sql = " from PwWp t where t.account.rid=:acntRid and to_char(t.wpPlanStart,'YYYY-MM-DD')>=:wpPlanStart "+
                      " and  to_char(t.wpPlanFihish,'YYYY-MM-DD')<=:wpPlanFihish";
            Query q=this.getDbAccessor().getSession().createQuery(sql);
                  q.setLong("acntRid", acntRid.longValue());
                  q.setString("wpPlanStart",startTime);
                  q.setString("wpPlanFihish",endTime);
                  pwWpList = q.list();
            if(pwWpList ==null || pwWpList.size()==0){
                return dtoPwWpList;
            }

            for (int i = 0; i < pwWpList.size(); i++) {
                PwWp pwWp = (PwWp)pwWpList.get(i);
                DtoPwWp dtoPwWp = FPW01000CommonLogic.createDToPwWp(getDbAccessor(),pwWp,loginId);
                dtoPwWpList.add(dtoPwWp);
            }
        }catch (Exception ex) {
            throw new BusinessException("E0001","Error when select wp list.", ex);
        }

        return dtoPwWpList;
    }

    public void updateList(List wpList) throws BusinessException {
        for(int i = 0;i<wpList.size();i++) {
           DtoPwWp dtoWp = (DtoPwWp)wpList.get(i);
           if(IDto.OP_INSERT.equals(dtoWp.getOp())) {
               insert(dtoWp);
               FPW01000CommonLogic.CheckData(dtoWp,"creatWp");
           } else if(IDto.OP_MODIFY.equals(dtoWp.getOp())) {
               update(dtoWp);
               FPW01000CommonLogic.CheckData(dtoWp,"modifyWp");
           }
        }
    }

    public void insert(DtoPwWp wp) throws BusinessException {
        try {
            PwWp pwWp =new PwWp();
            DtoUtil.copyProperties(pwWp,wp);
            this.getDbAccessor().save(pwWp);
        }catch (Exception ex) {
            throw new BusinessException("E0001","Error when insert wp.", ex);
        }
    }

    public void update(DtoPwWp wp) throws BusinessException {
        try {
            PwWp pwWp =new PwWp();
            DtoUtil.copyProperties(pwWp,wp);
            this.getDbAccessor().update(pwWp);
        }catch (Exception ex) {
            throw new BusinessException("E0001","Error when update wp.", ex);
        }

    }


    public void delete(DtoPwWp wp) throws BusinessException {
        try {
            PwWp pwWp = (PwWp)this.getDbAccessor().load(PwWp.class, wp.getRid());
            if (pwWp != null) {

                //delete check point
                String sSelChk = " from PwWpchk t where t.wpId =:wpId ";
                List chkList = getDbAccessor().getSession().createQuery(sSelChk)
                               .setLong("wpId", pwWp.getRid().longValue())
                               .list();
                for (Iterator iter = chkList.iterator(); iter.hasNext(); ) {
                    PwWpchk item = (PwWpchk) iter.next();

                    //delete check point log
                    String sSelLog = " from PwWpchklog t where t.wpchkId =:wpchkId ";
                    List logList = getDbAccessor().getSession().createQuery(sSelLog)
                               .setLong("wpchkId", item.getRid().longValue())
                               .list();
                    getDbAccessor().delete(logList);
                }
                getDbAccessor().delete(chkList);

                //delete summary
                //getDbAccessor().delete(pwWp.getWpSum());

                //delete review
                String sSelReview = " from PwWprev t where t.wpId =:wpId";
                List revList = getDbAccessor().getSession().createQuery(sSelReview)
                               .setLong("wpId", pwWp.getRid().longValue())
                               .list();
                getDbAccessor().delete(revList);

                this.getDbAccessor().delete(pwWp);
            }
        } catch (Exception ex) {
            throw new BusinessException("E0001","Error when delete wp.", ex);
        }
    }

    public static void main(String args[]){
        LgWpList logic = new LgWpList();
        List wpList = logic.list(new Long(12), null);
        System.out.println(wpList.size());

    }
}
