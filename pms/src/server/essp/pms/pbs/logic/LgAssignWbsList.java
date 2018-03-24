package server.essp.pms.pbs.logic;

import java.util.Iterator;

import c2s.dto.DtoTreeNode;
import c2s.dto.DtoUtil;
import c2s.dto.IDto;
import c2s.dto.ITreeNode;
import c2s.essp.pms.pbs.DtoAssignWbs;
import c2s.essp.pms.pbs.DtoPmsPbsAssignment;
import db.essp.pms.Activity;
import db.essp.pms.Wbs;
import server.framework.common.BusinessException;
import server.framework.hibernate.HBComAccess;
import server.essp.framework.logic.AbstractESSPLogic;

public class LgAssignWbsList extends AbstractESSPLogic {

    public ITreeNode list(Long acntRid) throws BusinessException {
        ITreeNode root = null;
        try {
            //get root
            String sqlSel = " from Wbs t where t.parent.pk.wbsRid is null "
                            + " and t.pk.acntRid=:acntRid";
            Iterator it = getDbAccessor().getSession().createQuery(sqlSel)
                          .setLong("acntRid",acntRid.longValue())
                          .iterate();
            if (it.hasNext()) {
                Wbs wbs = (Wbs) it.next();

                root = buildTree(wbs);
            }
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("", ex);
            throw new BusinessException("E000000",
                                        "Select wbs error.");
        }

        return root;
    }

    public static void main(String args[]) {
        try {
            LgAssignWbsList logic = new LgAssignWbsList();
            HBComAccess hb = new HBComAccess();
            logic.setDbAccessor(hb);
            ITreeNode root = logic.list(new Long(7));
            if (root != null) {
                System.out.println(DtoUtil.dumpBean(((DtoTreeNode) root).getDataBean()));
            }
        } catch (BusinessException ex) {

        }
    }

    private ITreeNode buildTree(Wbs wbs) {
        if (wbs == null) {
            return null;
        }

        ITreeNode root;
        DtoAssignWbs dtoWbs = new DtoAssignWbs();

        try {
            DtoUtil.copyBeanToBean(dtoWbs, wbs);
        } catch (Exception ex) {
        }
        dtoWbs.setAcntRid( wbs.getPk().getAcntRid());
        dtoWbs.setJoinRid( wbs.getPk().getWbsRid() );
        dtoWbs.setJoinType( DtoPmsPbsAssignment.JOINTYPEWBS );

        root = new DtoTreeNode(dtoWbs);

        //process wbs - wbs
        for (Iterator it = wbs.getChilds().iterator(); it.hasNext(); ) {
            Wbs child = (Wbs) it.next();

            ITreeNode childTreeRoot = buildTree(child);
            if (childTreeRoot != null) {
                root.addChild(childTreeRoot, IDto.OP_NOCHANGE);
            }
        }

        //process wbs -activity
        for (Iterator it = wbs.getActivities().iterator(); it.hasNext(); ) {
            Activity activity = (Activity) it.next();

            DtoAssignWbs dtoActvitiy = new DtoAssignWbs();

            try {
                DtoUtil.copyBeanToBean(dtoActvitiy, activity);
            } catch (Exception ex) {
            }
            dtoActvitiy.setAcntRid(activity.getPk().getAcntRid());
            dtoActvitiy.setJoinRid(activity.getPk().getActivityId());
            dtoActvitiy.setJoinType(DtoPmsPbsAssignment.JOINTYPEACT);

            ITreeNode node = new DtoTreeNode(dtoActvitiy);
            root.addChild(node, IDto.OP_NOCHANGE);
        }

        return root;
    }

}
