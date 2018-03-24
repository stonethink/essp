package server.essp.pms.pbs.logic;

import c2s.essp.pms.pbs.DtoPmsPbsAssignment;
import db.essp.pms.PbsAssignment;
import db.essp.pms.PbsAssignmentPK;
import server.framework.common.BusinessException;
import server.essp.framework.logic.AbstractESSPLogic;

public class LgPmsPbsAssignment extends AbstractESSPLogic {
    public void delete(DtoPmsPbsAssignment dtoPmsPbsAssignment) throws
        BusinessException {
        try {
            PbsAssignmentPK pmsPbsAssignmentPK = new PbsAssignmentPK();
            pmsPbsAssignmentPK.setAcntRid(dtoPmsPbsAssignment.getAcntRid());
            pmsPbsAssignmentPK.setPbsRid(dtoPmsPbsAssignment.getPbsRid());
            pmsPbsAssignmentPK.setJoinType(dtoPmsPbsAssignment.getJoinType());
            pmsPbsAssignmentPK.setJoinRid(dtoPmsPbsAssignment.getJoinRid());
            PbsAssignment pmsPbsAssignment = (PbsAssignment) getDbAccessor().
                                             load(
                                                 PbsAssignment.class,
                                                 pmsPbsAssignmentPK);

            getDbAccessor().delete(pmsPbsAssignment);

        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new BusinessException("E000000",
                                        "Delete assignment error.", ex);
        }
    }

    public void add(DtoPmsPbsAssignment dtoPmsPbsAssignment) throws
        BusinessException {
        try {
            PbsAssignment pmsPbsAssignment = createPbsAssignment(dtoPmsPbsAssignment);

            getDbAccessor().save(pmsPbsAssignment);
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new BusinessException("E000000",
                                        "Add assignment error.", ex);
        }
    }

    private PbsAssignment createPbsAssignment(DtoPmsPbsAssignment dto) {
        PbsAssignment pmsPbsAssignment = new PbsAssignment();
        pmsPbsAssignment.setIsWorkproduct(dto.getIsWorkproduct());

        PbsAssignmentPK pmsPbsAssignmentPK = new PbsAssignmentPK();
        pmsPbsAssignmentPK.setAcntRid(dto.getAcntRid());
        pmsPbsAssignmentPK.setPbsRid(dto.getPbsRid());
        pmsPbsAssignmentPK.setJoinType(dto.getJoinType());
        pmsPbsAssignmentPK.setJoinRid(dto.getJoinRid());
        pmsPbsAssignment.setPk(pmsPbsAssignmentPK);
        return pmsPbsAssignment;
    }
}
