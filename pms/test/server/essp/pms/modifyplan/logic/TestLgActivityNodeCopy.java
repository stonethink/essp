package server.essp.pms.modifyplan.logic;

import junit.framework.*;
import c2s.essp.pms.wbs.*;
import db.essp.pms.PmsActivityGuidelineId;
import db.essp.pms.PmsActivityGuideline;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class TestLgActivityNodeCopy extends TestCase {
    private LgActivityNodeCopy lgActivityNodeCopy = null;
    Long srcAcntRid = new Long(6666666);
    Long refAcntRid = new Long(8887888);
    Long otherAcntRid = new Long(779777);
    public TestLgActivityNodeCopy(String name) {
        super(name);
    }

    protected void setUp() throws Exception {
        super.setUp();
        lgActivityNodeCopy = new LgActivityNodeCopy();
        lgActivityNodeCopy.getDbAccessor().followTx();
        initData();
    }

    protected void tearDown() throws Exception {
        lgActivityNodeCopy.getDbAccessor().endTxRollback();
        lgActivityNodeCopy = null;
        super.tearDown();
    }

    public void testCopyActivityInSameAcnt() throws Exception {
        DtoWbsActivity srcActivity = new DtoWbsActivity();
        srcActivity.setAcntRid(srcAcntRid);
        srcActivity.setActivityRid(new Long(1));
        srcActivity.setWbs(false);

        DtoWbsActivity desWbs = new DtoWbsActivity();
        desWbs.setAcntRid(srcAcntRid);
        desWbs.setWbsRid(new Long(2));
        desWbs.setWbs(true);

        DtoWbsActivity actualReturn = lgActivityNodeCopy.copyActivity(srcActivity, desWbs);
        PmsActivityGuidelineId id = new PmsActivityGuidelineId();
         id.setAcntRid(actualReturn.getAcntRid());
         id.setActRid(actualReturn.getActivityRid());
         PmsActivityGuideline guideline = (PmsActivityGuideline)this.lgActivityNodeCopy
                                     .getDbAccessor().load(PmsActivityGuideline.class,
             id);
         assertEquals("判断同一项目中复制Activity时，是否正确复制guideline的refAcntRid", refAcntRid,
                      guideline.getRefAcntRid());
         assertEquals("判断同一项目中复制Activity时，是否正确复制guideline的refActRid", new Long(2),
                      guideline.getRefActRid());
         assertEquals("判断同一项目中复制Activity时，是否正确复制guideline的Description",
                      "testdescription", guideline.getDescription());

        /**@todo fill in the test code*/
    }

    public void testCopyActivityInDiffAcnt() throws Exception {
        DtoWbsActivity srcActivity = new DtoWbsActivity();
        srcActivity.setAcntRid(srcAcntRid);
        srcActivity.setActivityRid(new Long(1));
        srcActivity.setWbs(false);

        DtoWbsActivity desWbs = new DtoWbsActivity();
        desWbs.setAcntRid(otherAcntRid);
        desWbs.setWbsRid(new Long(3));
        desWbs.setWbs(true);

        DtoWbsActivity actualReturn = lgActivityNodeCopy.copyActivity(srcActivity, desWbs);
        PmsActivityGuidelineId id = new PmsActivityGuidelineId();
         id.setAcntRid(actualReturn.getAcntRid());
         id.setActRid(actualReturn.getActivityRid());
         PmsActivityGuideline guideline = (PmsActivityGuideline)this.lgActivityNodeCopy
                                     .getDbAccessor().load(PmsActivityGuideline.class,
             id);
         assertEquals("判断同一项目中复制Activity时，是否正确复制guideline的refAcntRid", srcAcntRid,
                      guideline.getRefAcntRid());
         assertEquals("判断同一项目中复制Activity时，是否正确复制guideline的refActRid", new Long(1),
                      guideline.getRefActRid());
         assertEquals("判断同一项目中复制Activity时，是否正确复制guideline的Description",
                      "testdescription", guideline.getDescription());

        /**@todo fill in the test code*/
    }


    /**
     * initData，一个WBS节点和一个Activity节点在同一个Account,另一个Wbs节点在不同的account
     */
    private void initData() {
        //同一个Account的wbs
        String srcAct1Sql = "insert into pms_activity (acnt_rid,activity_rid,code) values (" +
                            srcAcntRid + ",1,'tc1')";
        String srcWbs1GLSql =
            "insert into pms_activity_guideline (acnt_rid,act_rid,ref_acnt_rid,ref_act_rid,description) " +
            " values(" + srcAcntRid + ",1," + refAcntRid +
            ",2,'testdescription')";

            String srcWbs2Sql = "insert into pms_wbs (acnt_rid,wbs_rid) values (" +
                                srcAcntRid + ",2)";
            lgActivityNodeCopy.getDbAccessor().executeUpdate(srcAct1Sql);
            lgActivityNodeCopy.getDbAccessor().executeUpdate(srcWbs1GLSql);
            lgActivityNodeCopy.getDbAccessor().executeUpdate(srcWbs2Sql);

            //另一Account
            String srcWbs3Sql = "insert into pms_wbs (acnt_rid,wbs_rid) values ('" +
                                otherAcntRid
                                + "','3')";

        lgActivityNodeCopy.getDbAccessor().executeUpdate(srcWbs3Sql);

    }


}
