package server.essp.pms.pbs.pbsAndFiles.logic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import c2s.dto.DtoUtil;
import c2s.dto.IDto;
import c2s.essp.pms.pbs.pbsAndFiles.DtoPbsAssign;
import db.essp.pms.Pbs;
import db.essp.pms.PbsAssignment;
import db.essp.pms.PbsAssignmentPK;
import db.essp.pms.PbsPK;
import server.framework.common.BusinessException;
import server.framework.hibernate.HBComAccess;
import server.framework.logic.AbstractBusinessLogic;

public class LgPbsList extends AbstractBusinessLogic {

    public List list(Long acntRid, Long joinType, Long joinRid) throws BusinessException {
        List pbsList = new ArrayList();
        try {
            String sqlSel = " from PbsAssignment t "
                            +" where t.pk.acntRid =:acntRid "
                            +" and t.pk.joinType=:joinType "
                            +" and t.pk.joinRid=:joinRid ";
            Iterator it = getDbAccessor().getSession().createQuery( sqlSel )
                          .setLong("acntRid", acntRid.longValue())
                          .setLong("joinType",joinType.longValue())
                          .setLong("joinRid",joinRid.longValue())
                          .iterate();
            while( it.hasNext() ){
                PbsAssignment pbsAssignment = (PbsAssignment)it.next();

                //select pbs
                PbsPK pbsPK = new PbsPK( acntRid, pbsAssignment.getPk().getPbsRid() );
                Pbs pbs = (Pbs)getDbAccessor().getSession().get(Pbs.class, pbsPK);
                if( pbs != null ){
                    DtoPbsAssign dtoPbsAssign = new DtoPbsAssign();
                    dtoPbsAssign.setAcntRid(pbsAssignment.getPk().getAcntRid());
                    dtoPbsAssign.setJoinRid(pbsAssignment.getPk().getJoinRid());
                    dtoPbsAssign.setJoinType(pbsAssignment.getPk().getJoinType());
                    dtoPbsAssign.setPbsRid(pbsAssignment.getPk().getPbsRid());
                    dtoPbsAssign.setCode(pbs.getProductCode());
                    dtoPbsAssign.setName(pbs.getProductName());
                    dtoPbsAssign.setStatus(pbs.getPbsStatus());

                    pbsList.add(dtoPbsAssign);
                }
            }
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("", ex);
            throw new BusinessException("E000000",
                                        "Select pbs error.");
        }

        return pbsList;
    }

    public void update(List pbsList) throws BusinessException {
        try {

            for (int i = 0; i < pbsList.size(); i++) {
                DtoPbsAssign dtoPbsAssign = (DtoPbsAssign) pbsList.get(i);

                if (dtoPbsAssign.isInsert()) {
                    PbsAssignment pbsAssignment = new PbsAssignment();
                    copyPbsAssignment(pbsAssignment, dtoPbsAssign);

                    getDbAccessor().save(pbsAssignment);

                    dtoPbsAssign.setOp(IDto.OP_NOCHANGE);
                } else if (dtoPbsAssign.isDelete()) {

                    pbsList.remove(i);
                    i--;
                } else if (dtoPbsAssign.isModify()) {

                }
            }

        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new BusinessException("E000000", "Update PbsAssignment error.", ex);
        }
    }

    private void copyPbsAssignment(PbsAssignment dbBean, DtoPbsAssign dataBean) {
        PbsAssignmentPK pmsPbsAssignmentPK = new PbsAssignmentPK();
        pmsPbsAssignmentPK.setAcntRid(dataBean.getAcntRid());
        pmsPbsAssignmentPK.setPbsRid(dataBean.getPbsRid());
        pmsPbsAssignmentPK.setJoinType(dataBean.getJoinType());
        pmsPbsAssignmentPK.setJoinRid(dataBean.getJoinRid());
        dbBean.setPk(pmsPbsAssignmentPK);

        Pbs pbs = new Pbs();
        PbsPK pbsPK = new PbsPK( dataBean.getAcntRid(), dataBean.getPbsRid() );
        pbs.setPk( pbsPK );
        dbBean.setPbs(pbs);
    }

    public void delete(DtoPbsAssign dto) {
        PbsAssignment pbsAssignment = new PbsAssignment();
        copyPbsAssignment(pbsAssignment, dto);

        try {
            getDbAccessor().delete(pbsAssignment);
        } catch (Exception ex) {
            throw new BusinessException("E000", "Error when delete pbs", ex);
        }
    }

    public void add(DtoPbsAssign dto) {
        PbsAssignment pbsAssignment = new PbsAssignment();
        copyPbsAssignment(pbsAssignment, dto);

        try {
            getDbAccessor().save(pbsAssignment);
        } catch (Exception ex) {
            throw new BusinessException("E000", "Error when add pbs", ex);
        }
    }


    public static void main(String args[] ){
        try {
            LgPbsList logic = new LgPbsList();
            HBComAccess hb = new HBComAccess();
            logic.setDbAccessor(hb);
            List pbsList = logic.list(new Long(7), new Long(1), new Long(1));
            if (pbsList.size() > 0) {
                System.out.println(DtoUtil.dumpBean((pbsList.get(0))));
            }
        } catch (BusinessException ex) {

        }
    }

}
