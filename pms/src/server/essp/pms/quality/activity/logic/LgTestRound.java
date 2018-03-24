package server.essp.pms.quality.activity.logic;
import server.essp.framework.logic.AbstractESSPLogic;
import net.sf.hibernate.Session;
import net.sf.hibernate.Query;
import java.util.List;
import db.essp.pms.ActivityTestStat;
import db.essp.pms.ActivityTest;
import server.framework.common.BusinessException;
import c2s.essp.pms.quality.activity.DtoTestRound;

public class LgTestRound extends AbstractESSPLogic {
    public LgTestRound() {
    }

// sum up the total number of defect in the test round;
    public int FindTestRoundDefectNum(Long acntRid, Long testRid) {
        int rounfDefectNum = 0;
        Session session = null;
        List list;
        try {
            session = this.getDbAccessor().getSession();
            Query query = session.createQuery(
                "from ActivityTestStat t where t.acntRid=:acntRid  and  t.testRid=:testRid");
            query.setParameter("acntRid", acntRid);
            query.setParameter("testRid", testRid);
            list = query.list();
            for (int i = 0; i < list.size(); i++) {
                ActivityTestStat ts = (ActivityTestStat) list.get(i);
                rounfDefectNum += ts.getDefectNum().intValue();
            }
        } catch (Exception ex) {
        }
        return rounfDefectNum;
    }

    // sum up the total number of defect in the activity;
    public int FindActivityDefectNum(Long acntRid, Long activityRid) {
        int activityDefectNum = 0;
        Session session = null;
        List list;
        try {
            session = this.getDbAccessor().getSession();
            Query query = session.createQuery(
                "from ActivityTest t where t.acntRid=:acntRid and t.activityRid=:activityRid");
            query.setLong("acntRid", acntRid.longValue());
            query.setLong("activityRid", activityRid.longValue());

//            Query query = session.createQuery("from ActivityTest t");

            list = query.list();
            for (int i = 0; i < list.size(); i++) {
                ActivityTest ts = (ActivityTest) list.get(i);
                Long testRid = ts.getRid();

                activityDefectNum +=
                    this.FindTestRoundDefectNum(acntRid, testRid);
            }
        } catch (Exception ex) {
            throw new BusinessException(ex);
        }
        return activityDefectNum;
    }


    public List FindActivityTest(Long acntRid, Long activityRid) {
        int activityDefectNum = 0;
        Session session = null;
        List list;
        try {
            session = this.getDbAccessor().getSession();
            Query query = session.createQuery(
                "from ActivityTest t where t.acntRid=:acntRid and t.activityRid=:activityRid");
            query.setLong("acntRid", acntRid.longValue());
            query.setLong("activityRid", activityRid.longValue());
            list = query.list();
        } catch (Exception ex) {
            throw new BusinessException(ex);
        }
        return list;
    }


    public void deleteTestRound(DtoTestRound dtoTestRound) throws
        BusinessException {
        Session session = null;
        try {
            session = this.getDbAccessor().getSession();
            ActivityTest activityTest = (ActivityTest)this.
                                        getDbAccessor().load(
                                            ActivityTest.class,
                                            dtoTestRound.getRid());
//
            String sql = "from ActivityTestStat as c where c.testRid=" +
                         activityTest.getRid().toString();

            getDbAccessor().delete(activityTest);
            session.delete(sql);
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("", ex);
            throw new BusinessException("E000000", "delete test round error.");
        }
    }


    public Long updateTestRound(DtoTestRound dtoTestRound) throws
        BusinessException {

        try {
            ActivityTest activityTest = (ActivityTest)this.
                                        getDbAccessor().load(
                                            ActivityTest.class,
                                            dtoTestRound.getRid());
            activityTest.setAcntRid(dtoTestRound.getAcntRid());
            activityTest.setActivityRid(dtoTestRound.getQulityRid());
            activityTest.setTestRound(dtoTestRound.getTestRound());
            activityTest.setComment(dtoTestRound.getComment());
            activityTest.setFinish(dtoTestRound.getFinish());
            activityTest.setStart(dtoTestRound.getStart());
            activityTest.setRemovedDefectNum(dtoTestRound.getRemovedDefectNum());
            activityTest.setTotalTestCase(dtoTestRound.getTotalTestCase());
            activityTest.setUsingTestCase(dtoTestRound.getUsingTestCase());
            activityTest.setSeq(dtoTestRound.getSeq());

            getDbAccessor().update(activityTest);
            return activityTest.getRid();
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("", ex);
            throw new BusinessException("E000000", "update test round error.");
        }

    }

    public Long addTestRound(DtoTestRound dtoTestRound) throws
        BusinessException {

        try {
            ActivityTest activityTest = new ActivityTest();

            activityTest.setRid(this.getDbAccessor().assignedRid(
                activityTest));
            activityTest.setAcntRid(dtoTestRound.getAcntRid());
            activityTest.setActivityRid(dtoTestRound.getQulityRid());
            activityTest.setTestRound(dtoTestRound.getTestRound());
            activityTest.setComment(dtoTestRound.getComment());
            activityTest.setFinish(dtoTestRound.getFinish());
            activityTest.setStart(dtoTestRound.getStart());
            activityTest.setRemovedDefectNum(dtoTestRound.getRemovedDefectNum());
            activityTest.setTotalTestCase(dtoTestRound.getTotalTestCase());
            activityTest.setUsingTestCase(dtoTestRound.getUsingTestCase());
            activityTest.setSeq(dtoTestRound.getSeq());

            getDbAccessor().save(activityTest);
            return activityTest.getRid();
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("", ex);
            throw new BusinessException("E000000", "add test round error.");
        }
    }


    public static void main(String[] args) {

        LgTestRound lg = new LgTestRound();

        int pcb = lg.FindActivityDefectNum(new Long(6042), new Long(2));
        System.out.println(pcb);

    }
}
