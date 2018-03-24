package server.essp.pms.quality.activity.logic;
import server.essp.framework.logic.AbstractESSPLogic;
import net.sf.hibernate.Query;
import server.framework.common.BusinessException;
import java.util.List;
import net.sf.hibernate.Session;
import c2s.essp.pms.quality.activity.DtoTestStat;
import db.essp.pms.ActivityTestStat;

public class LgTestStat extends AbstractESSPLogic {
    public LgTestStat() {
    }

    public List listTestStat(Long testRid) {
        Session session = null;
        List list;
        try {
            session = this.getDbAccessor().getSession();
            Query query = session.createQuery(
                "from ActivityTestStat t where t.testRid=:testRid");
            query.setLong("testRid", testRid.longValue());
            list = query.list();
        } catch (Exception ex) {
            throw new BusinessException(ex);
        }
        return list;
    }

    public void saveTestStat(DtoTestStat dtoTestStat) {
        Session session = null;
        ActivityTestStat activityTestStat = new ActivityTestStat();

        try {
            activityTestStat.setAcntRid(dtoTestStat.getAcntRid());
            activityTestStat.setDefectNum(dtoTestStat.getNumber());
            activityTestStat.setInjectedPhase(dtoTestStat.getInjectedPhase());
            activityTestStat.setTestRid(dtoTestStat.getTestRid());
            activityTestStat.setRid(this.getDbAccessor().assignedRid(
                activityTestStat));
            this.getDbAccessor().getSession().save(activityTestStat);
        } catch (Exception ex) {
            throw new BusinessException(ex);
        }

    }

    public void deleteTestStat(DtoTestStat dtoTestStat) throws
        BusinessException {

        try {
            ActivityTestStat activityTestStat = (ActivityTestStat)this.
                                                getDbAccessor().load(
                ActivityTestStat.class, dtoTestStat.getRid());
//            activityTestStat.setRid(dtoTestStat.getRid());
            getDbAccessor().delete(activityTestStat);

        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("", ex);
            throw new BusinessException("E000000", "delete pms account error.");
        }
    }


    public void updateTestStat(DtoTestStat dtoTestStat) throws
        BusinessException {

        try {
            ActivityTestStat activityTestStat = (ActivityTestStat)this.
                                                getDbAccessor().load(
                ActivityTestStat.class, dtoTestStat.getRid());
            activityTestStat.setRid(dtoTestStat.getRid());
            activityTestStat.setAcntRid(dtoTestStat.getAcntRid());
            activityTestStat.setDefectNum(dtoTestStat.getNumber());
            activityTestStat.setInjectedPhase(dtoTestStat.getInjectedPhase());

            getDbAccessor().update(activityTestStat);

        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("", ex);
            throw new BusinessException("E000000", "delete pms account error.");
        }
    }


}
