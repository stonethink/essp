package server.essp.pms.wbs.logic;

import server.essp.framework.logic.AbstractESSPLogic;
import c2s.dto.ITreeNode;
import c2s.essp.pms.wbs.DtoWbsActivity;
import db.essp.pms.WbsPK;
import server.framework.common.BusinessException;
import db.essp.pms.Wbs;
import java.util.List;

public class LgWbsMove extends LgWbs {
    public LgWbsMove() {
        super();
    }

    public void upMove(ITreeNode node) {
        try {
            DtoWbsActivity dataBean = (DtoWbsActivity) node.getDataBean();
            WbsPK pk = new WbsPK(dataBean.getAcntRid(), dataBean.getWbsRid());
            Wbs wbs = (Wbs)this.getDbAccessor().load(Wbs.class, pk);
//            wbs.setAnticipatedStart(null);
//            wbs.setAnticipatedFinish(null);
//            wbs.setPlannedStart(null);
//            wbs.setPlannedFinish(null);
//            wbs.setActualStart(null);
//            wbs.setActualFinish(null);
            Wbs parent = wbs.getParent();
            if (parent != null) {
                int index=parent.getChilds().indexOf(wbs);
                if(index>0) {
                    parent.getChilds().remove(wbs);
                    parent.getChilds().add(index-1,wbs);
                    this.getDbAccessor().update(parent);
                }
            }
        } catch (Exception ex) {
            throw new BusinessException(ex);
        }
    }

    public void downMove(ITreeNode node) {
        try {
            DtoWbsActivity dataBean = (DtoWbsActivity) node.getDataBean();
            WbsPK pk = new WbsPK(dataBean.getAcntRid(), dataBean.getWbsRid());
            Wbs wbs = (Wbs)this.getDbAccessor().load(Wbs.class, pk);
            Wbs parent = wbs.getParent();
            if (parent != null) {
                int index=parent.getChilds().indexOf(wbs);
                if(index<parent.getChilds().size()-1) {
                    parent.getChilds().remove(wbs);
                    parent.getChilds().add(index+1,wbs);
                    this.getDbAccessor().update(parent);
                }
            }
        } catch (Exception ex) {
            throw new BusinessException(ex);
        }
    }

    public void leftMove(ITreeNode node) {
        try {
            DtoWbsActivity dataBean = (DtoWbsActivity) node.getDataBean();
            WbsPK pk = new WbsPK(dataBean.getAcntRid(), dataBean.getWbsRid());
            Wbs wbs = (Wbs)this.getDbAccessor().load(Wbs.class, pk);

            Wbs parent = wbs.getParent();
            if (parent != null) {
                Wbs gradFather = parent.getParent();
                if (gradFather != null) {
                    int index=gradFather.getChilds().indexOf(parent);
                    parent.getChilds().remove(wbs);
                    gradFather.getChilds().add(index+1,wbs);
                    wbs.setParent(gradFather);
                    this.getDbAccessor().update(wbs);
                    this.getDbAccessor().update(parent);
                    this.getDbAccessor().update(gradFather);
                }
            }
        } catch (Exception ex) {
            throw new BusinessException(ex);
        }
    }

    public void rightMove(ITreeNode node) {
        try {
            DtoWbsActivity dataBean = (DtoWbsActivity) node.getDataBean();
            WbsPK pk = new WbsPK(dataBean.getAcntRid(), dataBean.getWbsRid());
            Wbs wbs = (Wbs)this.getDbAccessor().load(Wbs.class, pk);
            Wbs parent = wbs.getParent();
            if (parent != null) {
                int preIndex=parent.getChilds().indexOf(wbs)-1;
                if(preIndex>=0) {
                    Wbs preWbs = (Wbs) parent.getChilds().get(preIndex);
                    parent.getChilds().remove(wbs);
                    preWbs.getChilds().add(wbs);
                    wbs.setParent(preWbs);
                    this.getDbAccessor().update(wbs);
                    this.getDbAccessor().update(preWbs);
                    this.getDbAccessor().update(parent);
                }
            }
//            this.modifyWbsDate(wbs);
        } catch (Exception ex) {
            throw new BusinessException(ex);
        }
    }
}
