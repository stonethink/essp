package server.essp.pms.pbs.gantt.logic;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import c2s.dto.DtoUtil;
import c2s.dto.IDto;
import c2s.dto.ITreeNode;
import c2s.essp.common.gantt.IGanttDto;
import c2s.essp.pms.gantt.DtoActivityGantt;
import c2s.essp.pms.gantt.DtoWbsGantt;
import c2s.essp.pms.wbs.DtoActivity;
import c2s.essp.pms.wbs.DtoWbs;
import c2s.essp.pms.wbs.DtoWbsTreeNode;
import db.essp.pms.Activity;
import db.essp.pms.ActivityRelation;
import db.essp.pms.Wbs;
import server.essp.pms.wbs.logic.LgWbs;
import c2s.essp.pms.wbs.DtoWbsActivity;

public class LgWbsGantt extends LgWbs {
    Map activityDtoMap = new HashMap();

    protected ITreeNode createTreeNode(IDto dataBean) {
        ITreeNode node = new DtoWbsTreeNode(dataBean);
        if (dataBean instanceof DtoActivityGantt) {
            ((DtoActivityGantt) dataBean).setThisNode(node);
        }

        return node;
    }

    protected DtoWbs createDTO(Wbs wbs) {
        DtoWbs dtoWbs = super.createDTO(wbs);

        DtoWbsGantt dtoWbsGantt = new DtoWbsGantt();
        try {
            DtoUtil.copyBeanToBean(dtoWbsGantt, dtoWbs);
        } catch (Exception ex) {
        }

        return dtoWbsGantt;
    }

    protected DtoWbsActivity createFirstWbs() {
        DtoWbsActivity dtoWbs = super.createFirstWbs();
        DtoWbsGantt dtoWbsGantt = new DtoWbsGantt();

        try {
            DtoUtil.copyBeanToBean(dtoWbsGantt, dtoWbs);
        } catch (Exception ex) {
        }

        return dtoWbsGantt;
    }

    protected DtoActivity createDTO(Activity activity) {
        DtoActivity dtoActivity = super.createDTO(activity);

        Long activityRid = dtoActivity.getActivityRid();
        DtoActivityGantt dtoActivityGantt = (DtoActivityGantt)this.activityDtoMap.get(activityRid);
        if (dtoActivityGantt == null) {
            dtoActivityGantt = new DtoActivityGantt();
            this.activityDtoMap.put(activityRid, dtoActivityGantt);
        }

        try {
            DtoUtil.copyBeanToBean(dtoActivityGantt, dtoActivity);
        } catch (Exception ex) {
        }

        //add its relations
        Set relations = activity.getActivityRelations();
        if (relations != null) {
            for (Iterator it = relations.iterator(); it.hasNext(); ) {
                ActivityRelation relation = (ActivityRelation) it.next();
                if (relation == null) {
                    continue;
                }

                Activity rActivity = relation.getRelationActivity();
                Long rActivityRid = rActivity.getPk().getActivityId();
                DtoActivityGantt rDtoActivityGantt = (DtoActivityGantt)this.activityDtoMap.get(rActivityRid);
                if (rDtoActivityGantt == null) {
                    rDtoActivityGantt = (DtoActivityGantt) createDTO(rActivity);
                }

                String type = relation.getStartFinishType();
                int iType = convertType(type);
                dtoActivityGantt.addRelation(rDtoActivityGantt, iType);
            }
        }
        return dtoActivityGantt;
    }

    protected int convertType(String type) {
        if (type.equalsIgnoreCase("ss") == true) {
            return IGanttDto.LINK_SS;
        } else if (type.equalsIgnoreCase("sf") == true) {
            return IGanttDto.LINK_SF;
        } else if (type.equalsIgnoreCase("fs") == true) {
            return IGanttDto.LINK_FS;
        } else if (type.equalsIgnoreCase("ff") == true) {
            return IGanttDto.LINK_FF;
        } else {
            return IGanttDto.NONE_STYLE;
        }
    }

    public static void main(String[] args) throws Exception {
        com.wits.util.ThreadVariant thread = com.wits.util.ThreadVariant.
                                             getInstance();
        c2s.essp.common.user.DtoUser user = new c2s.essp.common.user.DtoUser();
        user.setUserID("0");
        thread.set(c2s.essp.common.user.DtoUser.SESSION_USER, user);
        LgWbs lgwbs = new LgWbs();
        lgwbs.getWbsTree(1);
    }
}
