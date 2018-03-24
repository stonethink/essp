package server.essp.pms.activity.logic;

import server.essp.framework.logic.AbstractESSPLogic;
import c2s.dto.ITreeNode;
import c2s.essp.pms.wbs.DtoWbsActivity;
import server.framework.common.BusinessException;
import db.essp.pms.ActivityPK;
import db.essp.pms.Activity;
import db.essp.pms.Wbs;
import java.util.List;

public class LgActivityMove extends AbstractESSPLogic {
    public LgActivityMove() {
    }

    public void upMove(ITreeNode node) {
        try {
            DtoWbsActivity dataBean = (DtoWbsActivity) node.getDataBean();
            ActivityPK pk = new ActivityPK(dataBean.getAcntRid(), dataBean.getActivityRid());
            Activity activity=(Activity)this.getDbAccessor().load(Activity.class,pk);
            Wbs wbs=(Wbs)activity.getWbs();
            List activities=wbs.getActivities();
            int index=activities.indexOf(activity);
            if(index>0) {
                activities.remove(activity);
                activities.add(index-1,activity);
                this.getDbAccessor().update(wbs);
            }
        }catch (Exception ex) {
            throw new BusinessException(ex);
        }
    }

    public void downMove(ITreeNode node) {
        try {
            DtoWbsActivity dataBean = (DtoWbsActivity) node.getDataBean();
            ActivityPK pk = new ActivityPK(dataBean.getAcntRid(), dataBean.getActivityRid());
            Activity activity=(Activity)this.getDbAccessor().load(Activity.class,pk);
            Wbs wbs=(Wbs)activity.getWbs();
            List activities=wbs.getActivities();
            int index=activities.indexOf(activity);
            if(index<activities.size()-1) {
                activities.remove(activity);
                activities.add(index+1,activity);
                this.getDbAccessor().update(wbs);
            }
        }catch (Exception ex) {
            throw new BusinessException(ex);
        }
    }
}
