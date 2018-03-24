package server.essp.pms.account.logic;

import junit.framework.TestCase;
import javax.sql.RowSet;
import java.sql.SQLException;

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
public class TestLgTemplate extends TestCase {
    private LgTemplate lgTemplate = null;
    Long srcAcntRid = new Long(9991999);

    public TestLgTemplate(String name) {
        super(name);
    }

    protected void setUp() throws Exception {
        super.setUp();
        /**@todo verify the constructors*/
        lgTemplate = new LgTemplate();
        lgTemplate.getDbAccessor().followTx();
        initData();
    }

    protected void tearDown() throws Exception {
        lgTemplate.getDbAccessor().rollback();
        lgTemplate = null;
        super.tearDown();

    }

    //���Ը���WBSʱ��������Guideline�ļ�¼
    public void testCopyWbsWithGuideLine() throws SQLException {
        Long desc = new Long(9999998);
        lgTemplate.copyWbsActivityTree(srcAcntRid, desc);

        String qryWbsGuideline =
            "select * from pms_wbs_guideline where acnt_rid='" + desc +
            "' ";
        RowSet rt = lgTemplate.getDbAccessor().executeQuery(qryWbsGuideline);
        int count = 0;
        String description = null;
        while (rt.next()) {
            count ++;
            description = rt.getString("description");
        }
        this.assertEquals("�ж���������WBS Guideline", 1, count);
        this.assertEquals("�ж���������WBS Guideline��Description", "testdescription", description);

        /**@todo fill in the test code*/
    }

    //���Ը���Activityʱ��������Guideline�ļ�¼
    public void testCopyActivityWithGuideLine() throws SQLException {
        Long desc = new Long(9999998);
        lgTemplate.copyWbsActivityTree(srcAcntRid, desc);

        String qryAcitivityGuideline =
            "select * from pms_activity_guideline where acnt_rid='" +
            desc +
            "' ";
        RowSet rt = lgTemplate.getDbAccessor().executeQuery(
            qryAcitivityGuideline);
        String description = null;
        int count = 0;
        while (rt.next()) {
            count++;
            description = rt.getString("description");
        }
        this.assertEquals("�ж���������Activity Guideline", 1, count);
         this.assertNull("�ж���������Activity Guideline��Description",  description);
        /**@todo fill in the test code*/
    }

    /**
     * ��ʼ�������������
     */
    private void initData() {
        String srcWbsSql = "insert into pms_wbs (acnt_rid,wbs_rid) values (" +
                           srcAcntRid + ",1)";
        String srcWbsGLSql =
            "insert into pms_wbs_guideline (acnt_rid,wbs_rid,description) " +
            " values(" + srcAcntRid + ",1,'testdescription')";

        String srcAcitivitySql =
            "insert into pms_activity (acnt_rid,activity_rid,CODE) values(" +
            srcAcntRid + ",1,'test Code')";
//        String srcActivityGLSql =
//                "insert into pms_wbs_guideline (acnt_rid,wbs_rid,description) " +
//            " values(" + srcAcntRid + ",1,'')";

        lgTemplate.getDbAccessor().executeUpdate(srcWbsSql);
        lgTemplate.getDbAccessor().executeUpdate(srcWbsGLSql);
        lgTemplate.getDbAccessor().executeUpdate(srcAcitivitySql);
//        lgTemplate.getDbAccessor().executeUpdate(srcActivityGLSql);
    }
}
