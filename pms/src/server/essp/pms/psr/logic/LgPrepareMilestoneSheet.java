package server.essp.pms.psr.logic;

import server.essp.framework.logic.AbstractESSPLogic;
import db.essp.pms.MilestoneHistory;
import net.sf.hibernate.Query;
import net.sf.hibernate.*;
import java.util.List;
import java.util.ArrayList;
import db.essp.pms.Activity;
import c2s.essp.pms.activity.DtoMilestone;

/**
 * <p>Title: ��������׼��״̬�����Milestoneҳ������</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class LgPrepareMilestoneSheet extends AbstractESSPLogic {


    private List getMilestoneHistory(Long acntRid) {
        List result = null;
        //��ѯ���һ�α����ͨ���Ļ��߼ƻ���Milestone
        String sql = "select {ttt.*} from pms_milestone_approved_h ttt "
                     + "where ttt.acnt_rid=" + acntRid.toString()
                     + " and ttt.baseline_id="
                     + "(select tt.baseline_id from pms_acnt_baseline_log tt "
                     + "where tt.rid in ("
                     + "select max(t.rid) from pms_acnt_baseline_log t "
                     + "where t.acnt_rid=" + acntRid.toString() +
                     " and t.App_status='Approved')) order by ttt.anticipated_finish,ttt.planned_finish";
        try {
            Query query = this.getDbAccessor().getSession().createSQLQuery(sql,
                "ttt", MilestoneHistory.class);
            result = query.list();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    private Activity getMilestone(Long acntRid, Long activityRid) {
        String sql = "select {t.*} from pms_activity t where t.acnt_rid=" +
                     acntRid.toString() + " and activity_rid=" + activityRid.toString()
                     + " and t.milestone='1'";
        try {
            Query query = this.getDbAccessor().getSession().createSQLQuery(sql,
                "t", Activity.class);
            return (Activity) query.setMaxResults(1).uniqueResult();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public List getMilestoneSheetData(Long acntRid) {
        List result=new ArrayList();
        List milestoneHistory = getMilestoneHistory(acntRid);
        for (int i = 0; i < milestoneHistory.size(); i++) {
            MilestoneHistory history = (MilestoneHistory) milestoneHistory.
                                       get(i);
            Activity milestone = getMilestone(acntRid, history.getActivityRid());
            //�Ѹ������ݺϲ���ȥ
            DtoMilestone bean = new DtoMilestone();
            bean.setName(history.getName());
            bean.setType(history.getMilestoneType());
            bean.setPlanStart(history.getPlannedStart());
            bean.setPlanFinish(history.getPlannedFinish());
            bean.setAntiStart(history.getAnticipatedStart());
            bean.setAntiFinish(history.getAnticipatedFinish());
            bean.setCompeleteDate(milestone.getActualFinish());
            if (milestone == null) {
                //���Ϊ�գ�֤��û�в鵽����״̬ΪDeleted
                bean.setStatus(DtoMilestone.MILESTONE_DELETED);
            }
            result.add(bean);
        }
        return result;
    }


    public static void main(String[] args) {
        LgPrepareMilestoneSheet lgstatusreport = new LgPrepareMilestoneSheet();
        List list=lgstatusreport.getMilestoneSheetData(new Long(6022));
        System.out.println(list);
    }

}
