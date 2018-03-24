package server.essp.pms.modifyplan.logic;

import c2s.essp.pms.wbs.DtoWbsActivity;
import junit.framework.TestCase;
import db.essp.pms.PmsWbsGuidelineId;
import db.essp.pms.PmsWbsGuideline;

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
public class TestLgWbsNodeCopy extends TestCase {
    private LgWbsNodeCopy lgWbsNodeCopy = null;
    Long srcAcntRid = new Long(9999929);
    Long refAcntRid = new Long(8888488);
    Long otherAcntRid = new Long(777677);
    public TestLgWbsNodeCopy(String name) {
        super(name);
    }

    protected void setUp() throws Exception {
        super.setUp();
        lgWbsNodeCopy = new LgWbsNodeCopy();
        lgWbsNodeCopy.getDbAccessor().followTx();
        initData();
    }

    protected void tearDown() throws Exception {
        lgWbsNodeCopy.getDbAccessor().endTxRollback();
        lgWbsNodeCopy = null;
        super.tearDown();
    }

    public void testCopyWBSInSameAccount() throws Exception {
        DtoWbsActivity srcWbs = new DtoWbsActivity();
        srcWbs.setAcntRid(srcAcntRid);
        srcWbs.setWbsRid(new Long(1));

        DtoWbsActivity desWbs = new DtoWbsActivity();
        desWbs.setAcntRid(srcAcntRid);
        desWbs.setWbsRid(new Long(2));

        DtoWbsActivity actualReturn = lgWbsNodeCopy.copyWBS(srcWbs, desWbs);
        PmsWbsGuidelineId id = new PmsWbsGuidelineId();
        id.setAcntRid(actualReturn.getAcntRid());
        id.setWbsRid(actualReturn.getWbsRid());
        PmsWbsGuideline guideline = (PmsWbsGuideline)this.lgWbsNodeCopy
                                    .getDbAccessor().load(PmsWbsGuideline.class,
            id);
        assertEquals("判断同一项目中复制wbs时，是否正确复制guideline的refAcntRid", refAcntRid,
                     guideline.getRefAcntRid());
        assertEquals("判断同一项目中复制wbs时，是否正确复制guideline的refWBSRid", new Long(2),
                     guideline.getRefWbsRid());
        assertEquals("判断同一项目中复制wbs时，是否正确复制guideline的Description",
                     "testdescription", guideline.getDescription());
        /**@todo fill in the test code*/
    }

    public void testCopyWBSInDifferentAccount() throws Exception {
        //把srcAcntRid连接到otherAcntRid
        DtoWbsActivity srcWbs = new DtoWbsActivity();
        srcWbs.setAcntRid(srcAcntRid);
        srcWbs.setWbsRid(new Long(1));

        DtoWbsActivity desWbs = new DtoWbsActivity();
        desWbs.setAcntRid(otherAcntRid);
        desWbs.setWbsRid(new Long(3));
        DtoWbsActivity actualReturn = lgWbsNodeCopy.copyWBS(srcWbs, desWbs);

        PmsWbsGuidelineId id = new PmsWbsGuidelineId();
        id.setAcntRid(actualReturn.getAcntRid());
        id.setWbsRid(actualReturn.getWbsRid());
        PmsWbsGuideline guideline = (PmsWbsGuideline)this.lgWbsNodeCopy
                                    .getDbAccessor().load(PmsWbsGuideline.class,
            id);

        assertEquals("判断不同一项目中复制wbs时，是否正确复制guideline的refAcntRid", srcAcntRid,
                     guideline.getRefAcntRid());
        assertEquals("判断不同一项目中复制wbs时，是否正确复制guideline的refWBSRid", new Long(1),
                     guideline.getRefWbsRid());
        assertEquals("判断不同一项目中复制wbs时，是否正确复制guideline的Description",
                     "testdescription", guideline.getDescription());

        /**@todo fill in the test code*/
    }

    /**
     * initData，两个WBS节点在同一个Account,另一个在不同的account
     */
    private void initData() {
        //同一个Account的wbs
        String srcWbs1Sql = "insert into pms_wbs (acnt_rid,wbs_rid) values (" +
                            srcAcntRid + ",1)";
        String srcWbs1GLSql =
            "insert into pms_wbs_guideline (acnt_rid,wbs_rid,ref_acnt_rid,ref_wbs_rid,description) " +
            " values(" + srcAcntRid + ",1," + refAcntRid +
            ",2,'testdescription')";

        String srcWbs2Sql = "insert into pms_wbs (acnt_rid,wbs_rid) values (" +
                            srcAcntRid + ",2)";
        lgWbsNodeCopy.getDbAccessor().executeUpdate(srcWbs1Sql);
        lgWbsNodeCopy.getDbAccessor().executeUpdate(srcWbs1GLSql);
        lgWbsNodeCopy.getDbAccessor().executeUpdate(srcWbs2Sql);

        //另一Account
        String srcWbs3Sql = "insert into pms_wbs (acnt_rid,wbs_rid) values ('" +
                            otherAcntRid
                            + "','3')";
        lgWbsNodeCopy.getDbAccessor().executeUpdate(srcWbs3Sql);

    }
}
