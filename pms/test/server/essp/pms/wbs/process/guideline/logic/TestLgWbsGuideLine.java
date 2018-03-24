package server.essp.pms.wbs.process.guideline.logic;

import junit.framework.*;
import c2s.essp.pms.wbs.*;
import net.sf.hibernate.HibernateException;
import java.util.List;
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
public class TestLgWbsGuideLine extends TestCase {
    private LgWbsGuideLine lgWbsGuideLine = null;

    public TestLgWbsGuideLine(String name) {
        super(name);
    }

    protected void setUp() throws Exception {
        super.setUp();
        lgWbsGuideLine = new LgWbsGuideLine();
        lgWbsGuideLine.getDbAccessor().followTx();
        initData();
    }

    protected void tearDown() throws Exception {
        lgWbsGuideLine.getDbAccessor().rollback();
        lgWbsGuideLine = null;
        super.tearDown();
    }

    //���Ա���ģ���WbsGuideline
    public void testSaveWbsGuideLineInfoOfTemplate() throws HibernateException,
        Exception {
        DtoWbsGuideLine dto = new DtoWbsGuideLine();
        dto.setAcntRid(new Long(1111111));
        dto.setWbsRid(new Long(4));
        dto.setRefAcntRid(new Long(1111112));
        dto.setRefWbsRid(new Long(2));
        dto.setDescription("sdalkfjslddjf");
        lgWbsGuideLine.saveWbsGuideLineInfo(dto);

        List l = lgWbsGuideLine.getDbAccessor().getSession().createQuery(
            "from PmsWbsGuideline t where t.id.acntRid='1111111' and t.id.wbsRid='4'").
                 list();
        this.assertEquals("����һ��Template��WBsGuideline", 1, l.size());
        PmsWbsGuideline result = (PmsWbsGuideline) l.get(0);
        this.assertEquals("����һ��Template��WBsGuideline refAcntRid",
                          dto.getRefAcntRid(), result.getRefAcntRid());
        this.assertEquals("����һ��Template��WBsGuideline refWbsRid",
                          dto.getRefWbsRid(), result.getRefWbsRid());
        this.assertEquals("����һ��Template��WBsGuideline Desc", dto.getDescription(),
                          result.getDescription());
        /**@todo fill in the test code*/
    }

    public void testSaveWbsGuideLineInfoOfAccountDiffDesc() throws
        HibernateException, Exception {
        DtoWbsGuideLine dto = new DtoWbsGuideLine();
        dto.setAcntRid(new Long(2222222));
        dto.setWbsRid(new Long(1));
        dto.setRefAcntRid(new Long(1111111));
        dto.setRefWbsRid(new Long(1));
        dto.setDescription("dddddd");
        lgWbsGuideLine.saveWbsGuideLineInfo(dto);

        List l = lgWbsGuideLine.getDbAccessor().getSession().createQuery(
            "from PmsWbsGuideline t where t.id.acntRid='2222222' and t.id.wbsRid='1'").
                 list();
        this.assertEquals("����һ��Account��WBsGuideline(Desc��Ref��ͬ)", 1, l.size());
        PmsWbsGuideline result = (PmsWbsGuideline) l.get(0);
        this.assertEquals("����һ��Account��WBsGuideline(Desc��Ref��ͬ) refAcntRid",
                          dto.getRefAcntRid(), result.getRefAcntRid());
        this.assertEquals("����һ��Account��WBsGuideline(Desc��Ref��ͬ) refWbsRid",
                          dto.getRefWbsRid(), result.getRefWbsRid());
        this.assertEquals("����һ��Account��WBsGuideline(Desc��Ref��ͬ) Desc",
                          dto.getDescription(), result.getDescription());
        /**@todo fill in the test code*/

    }

    public void testSaveWbsGuideLineInfoOfAccountSameDesc() throws
        HibernateException, Exception {
        DtoWbsGuideLine dto = new DtoWbsGuideLine();
        dto.setAcntRid(new Long(2222222));
        dto.setWbsRid(new Long(2));
        dto.setRefAcntRid(new Long(1111111));
        dto.setRefWbsRid(new Long(1));
        dto.setDescription("test Description");
        lgWbsGuideLine.saveWbsGuideLineInfo(dto);

        List l = lgWbsGuideLine.getDbAccessor().getSession().createQuery(
            "from PmsWbsGuideline t where t.id.acntRid='2222222' and t.id.wbsRid='2'").
                 list();
        this.assertEquals("����һ��Account��WBsGuideline(Desc��Ref��ͬ)", 1, l.size());
        PmsWbsGuideline result = (PmsWbsGuideline) l.get(0);
        this.assertEquals("����һ��Account��WBsGuideline(Desc��Ref��ͬ) refAcntRid",
                          dto.getRefAcntRid(), result.getRefAcntRid());
        this.assertEquals("����һ��Account��WBsGuideline(Desc��Ref��ͬ) refWbsRid",
                          dto.getRefWbsRid(), result.getRefWbsRid());
        this.assertNull("����һ��Account��WBsGuideline(Desc��Ref��ͬ) Desc",
                        result.getDescription());
        /**@todo fill in the test code*/

    }

    public void testGetTemplateWbsGuideline() throws Exception {
        DtoWbsGuideLine dto = lgWbsGuideLine.getDtoWbsGuideline(new Long(
            1111111), new Long(1));
        this.assertEquals("��ȡTemplate��WBsGuideline Desc", "test Description",
                          dto.getDescription());
        this.assertNull("��ȡTemplate��WBsGuideline refAcntRid", dto.getRefAcntRid());
        this.assertNull("��ȡTemplate��WBsGuideline refWbsRid", dto.getRefWbsRid());
    }

    public void testGetAcntWbsGuidelineNoDesc() throws Exception {
        DtoWbsGuideLine dto = lgWbsGuideLine.getDtoWbsGuideline(new Long(
            2222222), new Long(11));
        this.assertEquals("��ȡAccount��WBsGuideline Desc", "test Description",
                          dto.getDescription());
        this.assertEquals("��ȡAccount��WBsGuideline refAcntRid", new Long(1111111),
                          dto.getRefAcntRid());
        this.assertEquals("��ȡAccount��WBsGuideline refWbsRid", new Long(1),
                          dto.getRefWbsRid());
    }

    public void testGetAcntWbsGuidelineHasDesc() throws Exception {
        DtoWbsGuideLine dto = lgWbsGuideLine.getDtoWbsGuideline(new Long(
            2222222), new Long(22));
        this.assertEquals("��ȡAccount��WBsGuideline Desc", "asfse",
                          dto.getDescription());
        this.assertEquals("��ȡAccount��WBsGuideline refAcntRid", new Long(1111111),
                          dto.getRefAcntRid());
        this.assertEquals("��ȡAccount��WBsGuideline refWbsRid", new Long(1),
                          dto.getRefWbsRid());
    }

    public void testHasChangedReference() {
        boolean isChanged = lgWbsGuideLine.hasChangedReference(null,null,null,null);
        this.assertEquals("ǰ��Ϊ��ʱ", false, isChanged);

        isChanged = lgWbsGuideLine.hasChangedReference(null,null,new Long(1), new Long(2));
        this.assertEquals("ǰΪ�գ�����ֵʱ", true, isChanged);

        isChanged = lgWbsGuideLine.hasChangedReference(new Long(1), new Long(2),null,null);
        this.assertEquals("ǰ��ֵ����Ϊ��ʱ", true, isChanged);

        isChanged = lgWbsGuideLine.hasChangedReference(new Long(1), new Long(2),new Long(1), new Long(2));
        this.assertEquals("ǰ����ֵ����һ��ʱ", false, isChanged);

        isChanged = lgWbsGuideLine.hasChangedReference(new Long(1), new Long(3),new Long(1), new Long(2));
        this.assertEquals("ǰ����ֵ����һ��ʱ", true, isChanged);
    }

    private void initData() {
        String templateSql = "insert into pms_acnt(rid,acnt_id,is_template) values('1111111','111111111','1')";
        String accountSql = "insert into pms_acnt(rid,acnt_id,is_template) values('2222222','222222222','0')";
        String wbsGuildline = "insert into pms_wbs_guideline(acnt_rid,wbs_rid,description) values(1111111,1,'test Description')";
        String acntWbsGuildline1 = "insert into pms_wbs_guideline(acnt_rid,wbs_rid,ref_acnt_rid,ref_wbs_rid,description) values(2222222,11,1111111,1,'')";
        String acntWbsGuildline2 = "insert into pms_wbs_guideline(acnt_rid,wbs_rid,ref_acnt_rid,ref_wbs_rid,description) values(2222222,22,1111111,1,'asfse')";
        lgWbsGuideLine.getDbAccessor().executeUpdate(templateSql);
        lgWbsGuideLine.getDbAccessor().executeUpdate(accountSql);
        lgWbsGuideLine.getDbAccessor().executeUpdate(wbsGuildline);
        lgWbsGuideLine.getDbAccessor().executeUpdate(acntWbsGuildline1);
        lgWbsGuideLine.getDbAccessor().executeUpdate(acntWbsGuildline2);
    }
}
