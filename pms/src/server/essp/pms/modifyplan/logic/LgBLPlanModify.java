package server.essp.pms.modifyplan.logic;

import server.essp.framework.logic.AbstractESSPLogic;
import c2s.dto.ITreeNode;
import c2s.essp.pms.wbs.DtoWbsActivity;
import server.essp.pms.wbs.logic.LgWbs;
import java.util.List;
import server.essp.pms.activity.logic.LgActivity;
import c2s.dto.IDto;

public class LgBLPlanModify extends AbstractESSPLogic {
    public LgBLPlanModify() {
    }

    public void saveBLPlan(ITreeNode root) {
        DtoWbsActivity dataBean = (DtoWbsActivity) root.getDataBean();
        if (!dataBean.isReadonly() && (dataBean.isModify() || dataBean.isInsert())) {
            if (dataBean.isWbs()) {
                LgWbs lgWbs = new LgWbs();
                if (dataBean.isInsert()) {
//                    System.out.println(dataBean.getCode());
                    lgWbs.add(root,false);
                    dataBean.setOp(IDto.OP_NOCHANGE);
                }
                if (dataBean.isModify()) {
                    lgWbs.update(dataBean);
                    dataBean.setOp(IDto.OP_NOCHANGE);
                }
            }
            if (dataBean.isActivity()) {
                LgActivity lgActivity = new LgActivity();
                if (dataBean.isInsert()) {
                    lgActivity.add(dataBean);
                    dataBean.setOp(IDto.OP_NOCHANGE);
                }
                if (dataBean.isModify()) {
                    lgActivity.update(dataBean);
                    dataBean.setOp(IDto.OP_NOCHANGE);
                }
            }

        } //插入或更新操作结束

        if (!root.isLeaf()) {
            List childs = root.children();
            for (int i = 0; i < childs.size(); i++) {
                saveBLPlan((ITreeNode) childs.get(i));
            }
        } else {
            return;
        }

    }
}
