package server.essp.pms.activity.code.logic;

import java.util.Set;

import db.essp.pms.Activity;
import db.essp.pms.ActivityPK;
import server.framework.common.BusinessException;
import server.framework.hibernate.HBComAccess;
import java.util.List;
import server.essp.common.code.choose.logic.LgCodeChoose;

public class LgActivityCode extends LgCodeChoose {
    Activity activity = null;

    public LgActivityCode(Long acntRid, Long activityRid){
        ActivityPK pk = new ActivityPK(acntRid, activityRid);
        try {
            activity = (Activity) getDbAccessor().load(Activity.class, pk);
        }catch (Exception ex) {
            throw new BusinessException("E0000",
                                        "Error when select activity.",
                                        ex);
        }
    }

    public Object getCodeOwner(){
        return activity;
    }

    public Set getCodeSet(){
        return activity.getActivityCodes();
    }


    public static void main(String args[]) {
        try {
            HBComAccess hb = HBComAccess.newInstance();
            hb.newTx();
            LgCodeChoose logic = new LgActivityCode(new Long(1), new Long(1));
            List list = logic.list();
            if (list != null) {
                System.out.println(list.size());
            }
            hb.endTxCommit();
        } catch (Exception ex) {
        }
    }

}
