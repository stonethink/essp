package server.essp.pms.wbs.logic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.wits.util.comDate;
import net.sf.hibernate.Session;
import server.essp.framework.logic.AbstractESSPLogic;
import server.essp.pms.wbs.viewbean.VbMileStone;
import server.framework.common.BusinessException;
import c2s.essp.pms.activity.DtoMilestone;
import db.essp.pms.Activity;
//import c2s.essp.pms.activity.DtoMilestone;

public class LgMileStone extends AbstractESSPLogic {
    /**
     * MileStone±¨±í
     * @return List
     */
    public static final String DATE_STYLE = "yyyy/MM/dd";
    public List listMileStone(Long acntRid) {
        List result = new ArrayList();
        if (acntRid == null) {
            return result;
        }
        try {
            Session s = this.getDbAccessor().getSession();
            List l = s.createQuery("from Activity ms " +
                                   "where ms.pk.acntRid=:acntRid " +
                                   "and ms.milestone=:isMile " +
                                   "order by ms.wbs.anticipatedFinish"
                     )
                     .setParameter("acntRid", acntRid)
                     .setParameter("isMile", DtoMilestone.IS_MILESTONE)
                     .list();
            Iterator i = l.iterator();
            while (i.hasNext()) {
                Activity mileStone = (Activity) i.next();
                DtoMilestone report = new DtoMilestone();

                report.setAntiFinish(mileStone.getAnticipatedFinish());
                report.setAntiStart(mileStone.getAnticipatedStart());
                report.setCompeleteDate(mileStone.getActualFinish());
                report.setPlanStart(mileStone.getPlannedStart());
                report.setPlanFinish(mileStone.getPlannedFinish());
                report.setName(mileStone.getName());
                report.setCode(mileStone.getCode());
                report.setType(mileStone.getMilestoneType());
                report.setReachedCondition(mileStone.getReachCondition());
                report.setRemark(mileStone.getBrief());
                result.add(report);
            }
        } catch (Exception ex) {
            log.error(ex);
            throw new BusinessException("ACTIVITY_MS_003",
                                        "error while reporting milestone", ex);
        }
        return result;
    }

    public static void main(String[] args) {
        LgMileStone logic = new LgMileStone();
        //DtoWbsMileStone dto = logic.get(new Long(1),new Long(3));
        //logic.listCheckPoint(new Long(1),new Long(3));
        logic.listMileStone(new Long(1));
    }
}
