package server.essp.pms.pbs.logic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import c2s.dto.IDto;
import c2s.essp.pms.pbs.DtoPmsPbsAssignment;
import db.essp.pms.Activity;
import db.essp.pms.PbsAssignment;
import db.essp.pms.PbsAssignmentPK;
import db.essp.pms.Wbs;
import net.sf.hibernate.Session;
import server.framework.common.BusinessException;
import server.essp.framework.logic.AbstractESSPLogic;

public class LgPmsPbsAssignmentList extends AbstractESSPLogic {

    public List list(Long acntRid, Long pbsRid) throws BusinessException {

        List assignmentList = new ArrayList();
        try {
            String selSql = "from db.essp.pms.PbsAssignment t "
                            + " where t.pk.acntRid=:acntRid and t.pk.pbsRid=:pbsRid";
            Session session = this.getDbAccessor().getSession();
            List result = session.createQuery(selSql)
                          .setLong("acntRid", acntRid.longValue())
                          .setLong("pbsRid", pbsRid.longValue())
                          .list();
            for (int i = 0; i < result.size(); i++) {
                PbsAssignment assignment = (PbsAssignment) result.get(i);
                DtoPmsPbsAssignment dto = new DtoPmsPbsAssignment();

                dto.setAcntRid(assignment.getPk().getAcntRid());
                dto.setPbsRid(assignment.getPk().getPbsRid());
                dto.setJoinRid(assignment.getPk().getJoinRid());
                dto.setJoinType(assignment.getPk().getJoinType());

                dto.setIsWorkproduct(assignment.getIsWorkproduct());

                if (DtoPmsPbsAssignment.JOINTYPEWBS.equals(dto.getJoinType()) == true) {
                    //this pbs is assignmented to a wbs.
                    String selWbsSql = " from db.essp.pms.Wbs t "
                                       + " where t.pk.acntRid=:acntRid and t.pk.wbsRid=:wbsRid ";
                    Iterator wbs = session.createQuery(selWbsSql)
                                   .setLong("acntRid", acntRid.longValue())
                                   .setLong("wbsRid", dto.getJoinRid().longValue())
                                   .iterate();

                    while (wbs.hasNext()) {
                        Wbs pmsWbs = (Wbs) wbs.next();

                        dto.setName(pmsWbs.getName());
                        dto.setManager(pmsWbs.getManager());
                        dto.setCode(pmsWbs.getCode());
                        assignmentList.add(dto);
                    }
                } else if (DtoPmsPbsAssignment.JOINTYPEACT.equals(dto.getJoinType()) == true) {
                    //this pbs is assignmented to a activity.
                    String selWbsSql = " from db.essp.pms.Activity t "
                                       + " where t.pk.acntRid=:acntRid "
                                       + " and t.pk.activityId=:activityId "
                                       ;
                    Iterator activity = session.createQuery(selWbsSql)
                                        .setLong("acntRid", acntRid.longValue())
                                         .setLong("activityId", dto.getJoinRid().longValue())
                                        .iterate();
                    while (activity.hasNext()) {
                        Activity pmsActivity = (Activity) activity.next();

                        dto.setName(pmsActivity.getName());
                        dto.setManager(pmsActivity.getManager());
                        dto.setCode(pmsActivity.getCode());
                        assignmentList.add(dto);
                    }
                } else {
                    throw new BusinessException("E000000",
                                                "Invalid assignment: join type is " + dto.getJoinType() + "."
                                                + "Join type maybe " + DtoPmsPbsAssignment.JOINTYPEWBS.longValue() + ""
                                                + " or " + DtoPmsPbsAssignment.JOINTYPEWBS.longValue() + " .");
                }

            }
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("", ex);
            throw new BusinessException("E000000",
                                        "Select from PbsAssignment error.");
        }

        return assignmentList;
    }
    /**
    * 获取指定项目的所有pmsPbsAssignment
    * @param acntRid Long
    * @return List
    */
   public List listAllPmsPbsAssignmentByAcntRid(Long acntRid){
       List resultList = null;
       if (acntRid == null) {
           return null;
       }
       try {
           resultList = this.getDbAccessor().getSession()
                        .createQuery(
               " from PbsAssignment as t  where t.pk.acntRid=:acntRid")
                        .setLong("acntRid", acntRid.longValue())
                        .list();
           return resultList;
       } catch (Exception e) {
           log.error(e);
           throw new BusinessException("pmsPbsAssignment.list.error", e);
       }

   }

    public void update(List assignmentList) throws BusinessException {
        try {

            for (int i = 0; i < assignmentList.size(); i++) {
                DtoPmsPbsAssignment dtoPmsPbsAssignment = (DtoPmsPbsAssignment) assignmentList.get(i);

                if (dtoPmsPbsAssignment.isInsert()) {
                    PbsAssignment pmsPbsAssignment = new PbsAssignment();
                    PbsAssignmentPK pmsPbsAssignmentPK = new PbsAssignmentPK();
                    pmsPbsAssignmentPK.setAcntRid(dtoPmsPbsAssignment.getAcntRid());
                    pmsPbsAssignmentPK.setPbsRid(dtoPmsPbsAssignment.getPbsRid());
                    pmsPbsAssignmentPK.setJoinType(dtoPmsPbsAssignment.getJoinType());
                    pmsPbsAssignmentPK.setJoinRid(dtoPmsPbsAssignment.getJoinRid());
                    pmsPbsAssignment.setPk(pmsPbsAssignmentPK);
                    pmsPbsAssignment.setIsWorkproduct(dtoPmsPbsAssignment.getIsWorkproduct());

                    getDbAccessor().save(pmsPbsAssignment);

                    dtoPmsPbsAssignment.setOp(IDto.OP_NOCHANGE);
                } else if (dtoPmsPbsAssignment.isDelete()) {

                    assignmentList.remove(i);
                    i--;
                } else if (dtoPmsPbsAssignment.isChanged()) {
                    PbsAssignmentPK pmsPbsAssignmentPK = new PbsAssignmentPK();
                    pmsPbsAssignmentPK.setAcntRid(dtoPmsPbsAssignment.getAcntRid());
                    pmsPbsAssignmentPK.setPbsRid(dtoPmsPbsAssignment.getPbsRid());
                    pmsPbsAssignmentPK.setJoinType(dtoPmsPbsAssignment.getJoinType());
                    pmsPbsAssignmentPK.setJoinRid(dtoPmsPbsAssignment.getJoinRid());
                    PbsAssignment pmsPbsAssignment = (PbsAssignment) getDbAccessor().load(
                        PbsAssignment.class, pmsPbsAssignmentPK);

                    pmsPbsAssignment.setIsWorkproduct(dtoPmsPbsAssignment.getIsWorkproduct());

                    getDbAccessor().update(pmsPbsAssignment);

                    dtoPmsPbsAssignment.setOp(IDto.OP_NOCHANGE);
                }
            }

        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("", ex);
            throw new BusinessException("E000000", "Update PbsAssignment error.");
        }
    }
}
