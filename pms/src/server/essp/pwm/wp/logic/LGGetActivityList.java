package server.essp.pwm.wp.logic;

import org.apache.log4j.Category;
import server.framework.hibernate.HBComAccess;
import c2s.dto.TransactionData;
import net.sf.hibernate.Session;
import java.util.List;
import c2s.dto.ReturnInfo;
import net.sf.hibernate.Query;
import java.util.ArrayList;
import db.essp.pms.Activity;
import c2s.essp.pwm.wp.DtoActivityInfo;
import server.framework.logic.AbstractBusinessLogic;
import java.sql.ResultSet;

public class LGGetActivityList extends AbstractBusinessLogic {
    static Category log = Category.getInstance("server");

    public LGGetActivityList() {
    }

    HBComAccess hbAccess = new HBComAccess();

    public void getActivityList(TransactionData transData) {
        try {
            hbAccess.followTx();
            List activityList = new ArrayList();

            String inProjectId = (String) transData.getInputInfo().getInputObj("inProjectId");
            if (inProjectId == null || inProjectId.equals("")) {
                //activityList = null;
            } else {
                activityList = getActivityList(inProjectId);
            }

            ReturnInfo returnInfo = transData.getReturnInfo();
            returnInfo.setReturnObj("activityList", activityList);

            hbAccess.endTxCommit();
            returnInfo.setError(false);
        } catch (Exception e) {
            try {
                hbAccess.endTxRollback();
            } catch (Exception ee) {
                log.error("find the Activity list error!!");
            }
            transData.getReturnInfo().setError(e);
            log.debug("",e);
        }
    }

    public String getActivityName(Long activityRid,Long acntRid) throws Exception {
        String result="";
        String querySql="select t.name from pms_activity t where t.activity_rid='"
                        +activityRid.toString()+"' and t.acnt_rid='"
                        +acntRid.toString()+"'";
        try {
            ResultSet rs=this.getDbAccessor().executeQuery(querySql);
            if(rs.next()){
                result=rs.getString("name");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public List getActivityList(String inProjectId) throws Exception {
        Session s = hbAccess.getSession();
        /*Query q = s.createQuery(
            "select new c2s.essp.common.accountlist.DtoActivityInfo "
            + "( t.id,t.taskId,t.clnitem ) "
            + "from EsspPwpTemcln t "
            + "where t.account = :projectId and  t.status != 'Closed' order by t.taskId"
            );
         */
        Query q = s.createQuery(
            "from Activity t "
            + "where t.pk.acntRid=:projectId order by t.code"
            );

        Long lProjectId = new Long(inProjectId);
        q.setLong("projectId", lProjectId.longValue());
        //q.setBoolean("start", true);
        //q.setBoolean("finish", false);
        List activityList = q.list();
        List retActivityList = new ArrayList();
        for (int i = 0; i < activityList.size(); i++) {
            Activity activity = (Activity)activityList.get(i);
            DtoActivityInfo activityInfo = new DtoActivityInfo();
            activityInfo.setActivityId(activity.getPk().getActivityId());
            activityInfo.setClnitem(activity.getName());
            activityInfo.setSchno(activity.getCode());
            retActivityList.add(activityInfo);
        }

        return retActivityList;
    }

    public static void main(String[] args) {
        LGGetActivityList lgGetActivityList = new LGGetActivityList();
        TransactionData transData = new TransactionData();
        transData.getInputInfo().setInputObj("inProjectId","1");
        lgGetActivityList.getActivityList(transData);

        List list = (List) transData.getReturnInfo().getReturnObj("activityList");
        log.debug("count=" + list.size());

    }

}
