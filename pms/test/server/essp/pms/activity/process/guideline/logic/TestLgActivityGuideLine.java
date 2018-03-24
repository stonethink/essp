package server.essp.pms.activity.process.guideline.logic;

import java.util.List;

import c2s.essp.pms.wbs.DtoWbsGuideLine;
import db.essp.pms.PmsActivityGuideline;
import junit.framework.TestCase;

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
public class TestLgActivityGuideLine extends TestCase {
    private LgActivityGuideLine lgActivityGuideLine = null;

    public TestLgActivityGuideLine(String name) {
        super(name);
    }

    protected void setUp() throws Exception {
        super.setUp();
        lgActivityGuideLine = new LgActivityGuideLine();
        lgActivityGuideLine.getDbAccessor().followTx();
        initData();
    }

    protected void tearDown() throws Exception {
        lgActivityGuideLine.getDbAccessor().endTxRollback();
        lgActivityGuideLine = null;
        super.tearDown();
    }

    public void testGetActivityDecription() {
        Long acntRid = new Long(1111111);
        Long activityRid = new Long(1);
        String expectedReturn = "test Description";
        String actualReturn = lgActivityGuideLine.getActivityDecription(acntRid,
            activityRid);
        assertEquals("testGetActivityDecription ", expectedReturn, actualReturn);
    }

    public void testGetDtoWbsGuideLineTemplate() {
        Long acntRid = new Long(1111111);
        Long activityRid = new Long(1);
        DtoWbsGuideLine actualReturn = lgActivityGuideLine.getActivityGuideLineDto(
            acntRid, activityRid);
        assertEquals("testGetTemplateDtoWbsGuideLine ", "test Description",
                     actualReturn.getDescription());
    }

    public void testGetDtoWbsGuideLineAcntNoDesc() {
        Long acntRid = new Long(2222222);
        Long activityRid = new Long(11);
        DtoWbsGuideLine actualReturn = lgActivityGuideLine.getActivityGuideLineDto(
            acntRid, activityRid);
        this.assertEquals("获取Account的WBsGuideline Desc", "test Description",
                          actualReturn.getDescription());
        this.assertEquals("获取Account的WBsGuideline refAcntRid", new Long(1111111),
                          actualReturn.getRefAcntRid());
        this.assertEquals("获取Account的WBsGuideline getRefActivityRid", new Long(1),
                          actualReturn.getRefActivityRid());
    }

    public void testGetDtoWbsGuideLineAcntHasDesc() {
        Long acntRid = new Long(2222222);
        Long activityRid = new Long(22);
        DtoWbsGuideLine actualReturn = lgActivityGuideLine.getActivityGuideLineDto(
            acntRid, activityRid);
        this.assertEquals("获取Account的WBsGuideline Desc", "asfse",
                          actualReturn.getDescription());
        this.assertEquals("获取Account的WBsGuideline refAcntRid", new Long(1111111),
                          actualReturn.getRefAcntRid());
        this.assertEquals("获取Account的WBsGuideline getRefActivityRid", new Long(1),
                          actualReturn.getRefActivityRid());
    }



    public void testHasChangedReference() {
        boolean isChanged = lgActivityGuideLine.hasChangedReference(null, null, null, null);
        this.assertEquals("前后都为空时", false, isChanged);

        isChanged = lgActivityGuideLine.hasChangedReference(null, null,
            new Long(1), new Long(2));
        this.assertEquals("前为空，后有值时", true, isChanged);

        isChanged = lgActivityGuideLine.hasChangedReference(new Long(1),
            new Long(2), null, null);
        this.assertEquals("前有值，后为空时", true, isChanged);

        isChanged = lgActivityGuideLine.hasChangedReference(new Long(1),
            new Long(2), new Long(1), new Long(2));
        this.assertEquals("前后都有值，且一致时", false, isChanged);

        isChanged = lgActivityGuideLine.hasChangedReference(new Long(1),
            new Long(3), new Long(1), new Long(2));
        this.assertEquals("前后都有值，不一致时", true, isChanged);
    }


    public void testSaveActivityGuideLineInfoOfTemplate() throws Exception {
        DtoWbsGuideLine dto = new DtoWbsGuideLine();
        dto.setAcntRid(new Long(1111111));
        dto.setActivityRid(new Long(4));
        dto.setRefAcntRid(new Long(1111112));
        dto.setRefActivityRid(new Long(2));
        dto.setDescription("sdalkfjslddjf");

        lgActivityGuideLine.saveActivityGuideLineInfo(dto);

        List l = lgActivityGuideLine.getDbAccessor().getSession().createQuery(
            "from PmsActivityGuideline t where t.id.acntRid='1111111' and t.id.actRid='4'").
                 list();
        this.assertEquals("插入一笔Template的ActivityGuideline", 1, l.size());
        PmsActivityGuideline result = (PmsActivityGuideline) l.get(0);
        this.assertEquals("插入一笔Template的ActivityGuideline refAcntRid",
                          dto.getRefAcntRid(), result.getRefAcntRid());
        this.assertEquals("插入一笔Template的ActivityGuideline refActivityRid",
                          dto.getRefActivityRid(), result.getRefActRid());
        this.assertEquals("插入一笔Template的ActivityGuideline Desc",
                          dto.getDescription(),
                          result.getDescription());
    }

    public void testSaveActivityGuideLineInfoOfAcntDiffDesc() throws Exception {
        DtoWbsGuideLine dto = new DtoWbsGuideLine();
        dto.setAcntRid(new Long(2222222));
        dto.setActivityRid(new Long(1));
        dto.setRefAcntRid(new Long(1111111));
        dto.setRefActivityRid(new Long(1));
        dto.setDescription("dddddd");

        lgActivityGuideLine.saveActivityGuideLineInfo(dto);

        List l = lgActivityGuideLine.getDbAccessor().getSession().createQuery(
            "from PmsActivityGuideline t where t.id.acntRid='2222222' and t.id.actRid='1'").
                 list();
        this.assertEquals("插入一笔Account的ActivityGuideline(Desc与Ref不同)", 1, l.size());
        PmsActivityGuideline result = (PmsActivityGuideline) l.get(0);
        this.assertEquals("插入一笔Account的ActivityGuideline(Desc与Ref不同) refAcntRid",
                          dto.getRefAcntRid(), result.getRefAcntRid());
        this.assertEquals("插入一笔Account的ActivityGuideline(Desc与Ref不同) refActivityRid",
                          dto.getRefActivityRid(), result.getRefActRid());
        this.assertEquals("插入一笔Account的ActivityGuideline(Desc与Ref不同) Desc",
                          dto.getDescription(),
                          result.getDescription());
    }

    public void testSaveActivityGuideLineInfoOfAcntSameDesc() throws Exception {
        DtoWbsGuideLine dto = new DtoWbsGuideLine();
        dto.setAcntRid(new Long(2222222));
        dto.setActivityRid(new Long(2));
        dto.setRefAcntRid(new Long(1111111));
        dto.setRefActivityRid(new Long(1));
        dto.setDescription("test Description");

        lgActivityGuideLine.saveActivityGuideLineInfo(dto);

        List l = lgActivityGuideLine.getDbAccessor().getSession().createQuery(
            "from PmsActivityGuideline t where t.id.acntRid='2222222' and t.id.actRid='2'").
                 list();
        this.assertEquals("插入一笔Account的ActivityGuideline", 1, l.size());
        PmsActivityGuideline result = (PmsActivityGuideline) l.get(0);
        this.assertEquals("插入一笔Account的ActivityGuideline(Desc与Ref相同) refAcntRid",
                          dto.getRefAcntRid(), result.getRefAcntRid());
        this.assertEquals("插入一笔Account的ActivityGuideline(Desc与Ref相同) refActivityRid",
                          dto.getRefActivityRid(), result.getRefActRid());
        this.assertNull("插入一笔Account的ActivityGuideline(Desc与Ref相同) Desc",
                          result.getDescription());
    }

    public void testSaveGetQualityFlag() {
        String activitySql = "insert into pms_activity(acnt_rid,activity_rid,code) values(2222222,2,'codeT')";
        lgActivityGuideLine.getDbAccessor().executeUpdate(activitySql);
        DtoWbsGuideLine dto = new DtoWbsGuideLine();
        Long acntRid = new Long(2222222);
        Long activityRid = new Long(2);
        dto.setAcntRid(acntRid);
        dto.setActivityRid(activityRid);
        dto.setIsQuality("1");
        lgActivityGuideLine.saveActivityGuideLineInfo(dto);
        String flag = lgActivityGuideLine.getQualityFlag(acntRid, activityRid);
        this.assertEquals("Save and Get Flag 1", "1", flag);

        dto.setIsQuality("0");
        lgActivityGuideLine.saveActivityGuideLineInfo(dto);
        flag = lgActivityGuideLine.getQualityFlag(acntRid, activityRid);
        this.assertEquals("Save and Get Flag 0", "0", flag);
    }


    private void initData() {
        String templateSql = "insert into pms_acnt(rid,acnt_id,is_template) values('1111111','111111111','1')";
        String accountSql = "insert into pms_acnt(rid,acnt_id,is_template) values('2222222','222222222','0')";
        String actGuildline = "insert into pms_activity_guideline(acnt_rid,act_rid,description) values(1111111,1,'test Description')";
        String acntActGuildline1 = "insert into pms_activity_guideline(acnt_rid,act_rid,ref_acnt_rid,ref_act_rid,description) values(2222222,11,1111111,1,'')";
        String acntActGuildline2 = "insert into pms_activity_guideline(acnt_rid,act_rid,ref_acnt_rid,ref_act_rid,description) values(2222222,22,1111111,1,'asfse')";
        lgActivityGuideLine.getDbAccessor().executeUpdate(templateSql);
        lgActivityGuideLine.getDbAccessor().executeUpdate(accountSql);
        lgActivityGuideLine.getDbAccessor().executeUpdate(actGuildline);
        lgActivityGuideLine.getDbAccessor().executeUpdate(acntActGuildline1);
        lgActivityGuideLine.getDbAccessor().executeUpdate(acntActGuildline2);
    }

}
