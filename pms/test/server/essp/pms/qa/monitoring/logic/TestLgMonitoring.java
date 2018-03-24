package server.essp.pms.qa.monitoring.logic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import c2s.dto.IDto;
import c2s.essp.pms.qa.DtoMonitoringTreeNode;
import c2s.essp.pms.wbs.DtoQaMonitoring;
import com.wits.util.comDate;
import db.essp.pms.PmsQaCheckAction;
import db.essp.pms.PmsQaCheckActionPK;
import db.essp.pms.PmsQaCheckPoint;
import db.essp.pms.PmsQaCheckPointPK;
import junit.framework.TestCase;
import net.sf.hibernate.Session;
import c2s.essp.pms.qa.DtoQaCheckAction;
import c2s.essp.pms.qa.DtoQaCheckPoint;

public class TestLgMonitoring extends TestCase {
    private LgMonitoring lgMonitoring = null;

    private static final Long acntRid = new Long(8879576);
    private static final Long belongRid = new Long(53724);
    private static final String belongTo = "wbs";
    private static final Long cpRid = new Long(83526);
    private static final Long caRid = new Long(8954);
    private static final Long seq = new Long(99);
    private static final String name = "cp Name test 456sf";
    private static final String method = "cp method test 456sf";
    private static final String remark = "cp remark test 456sf";
    private static final Date planDate = comDate.toDate("2017-11-22");
    private static final Date actDate = comDate.toDate("2017-11-24");
    private static final String planPerformer = "ttTTest22Plan";
    private static final String actPerformer = "ttTTest22Act";
    private static final String content = "test Content 543wst5";
    private static final String result = "test result 543wst5";
    private static final String ncrNo = "test ncrNo 543wst5";
    private static final String occasion = "test occasion 543wst5";

    private static final Long seq_2 = new Long(77);
    private static final String name_2 = "cp Name test 456s2";
    private static final String method_2 = "cp method test 456s2";
    private static final String remark_2 = "cp remark test 456s2";
    private static final Date planDate_2 = comDate.toDate("2017-11-23");
    private static final Date actDate_2 = comDate.toDate("2017-11-25");
    private static final String planPerformer_2 = "ttTTest22Plan2";
    private static final String actPerformer_2 = "ttTTest22Act2";
    private static final String content_2 = "test Content 543wst52";
    private static final String result_2 = "test result 543wst52";
    private static final String ncrNo_2 = "test ncrNo 543wst52";
    private static final String occasion_2 = "test occasion 543wst52";


    public TestLgMonitoring(String name) {
        super(name);
    }

    protected void setUp() throws Exception {
        super.setUp();
        lgMonitoring = new LgMonitoring();
        lgMonitoring.getDbAccessor().followTx();
    }

    protected void tearDown() throws Exception {
        lgMonitoring.getDbAccessor().endTxRollback();
        lgMonitoring = null;
        super.tearDown();
    }

    public void testSaveDateInsert() throws Exception {

        lgMonitoring.saveDate(getDataListInsert());

        Session s = lgMonitoring.getDbAccessor().getSession();
        PmsQaCheckPointPK cpPk = new PmsQaCheckPointPK();
        cpPk.setAcntRid(acntRid);
        cpPk.setRid(cpRid);
        PmsQaCheckPoint cp = (PmsQaCheckPoint) s.load(PmsQaCheckPoint.class, cpPk);
        assertEquals("Insert check point belongRid: ", belongRid, cp.getBelongRid());
        assertEquals("Insert check point belongTo: ", belongTo, cp.getBelongTo());
        assertEquals("Insert check point name: ", name, cp.getName());
        assertEquals("Insert check point method: ", method, cp.getMethod());
        assertEquals("Insert check point remark: ", remark, cp.getRemark());

        PmsQaCheckActionPK caPk = new PmsQaCheckActionPK();
        caPk.setAcntRid(acntRid);
        caPk.setRid(caRid);
        PmsQaCheckAction ca = (PmsQaCheckAction) s.load(PmsQaCheckAction.class, caPk);
        assertEquals("Insert check action cpRid: ", cpRid, ca.getCpRid());
        assertEquals("Insert check action planDate: ", planDate, ca.getPlanDate());
        assertEquals("Insert check action actDate: ", actDate, ca.getActDate());
        assertEquals("Insert check action planPerformer: ", planPerformer, ca.getPlanPerformer());
        assertEquals("Insert check action actPerformer: ", actPerformer, ca.getActPerformer());
        assertEquals("Insert check action content: ", content, ca.getContent());
        assertEquals("Insert check action result: ", result, ca.getResult());
        assertEquals("Insert check action ncrNo: ", ncrNo, ca.getNrcNo());
        assertEquals("Insert check action occasion: ", occasion, ca.getOccasion());


    }

    public void testSaveDateUpdate() throws Exception {
        initData();
        lgMonitoring.saveDate(getDataListUpdate());
        Session s = lgMonitoring.getDbAccessor().getSession();

        PmsQaCheckPointPK cpPk = new PmsQaCheckPointPK();
        cpPk.setAcntRid(acntRid);
        cpPk.setRid(cpRid);
        PmsQaCheckPoint cp = (PmsQaCheckPoint) s.load(PmsQaCheckPoint.class, cpPk);
        assertEquals("update check point belongRid: ", belongRid, cp.getBelongRid());
        assertEquals("update check point belongTo: ", belongTo, cp.getBelongTo());
        assertEquals("update check point name: ", name_2, cp.getName());
        assertEquals("update check point method: ", method_2, cp.getMethod());
        assertEquals("update check point remark: ", remark_2, cp.getRemark());

        PmsQaCheckActionPK caPk = new PmsQaCheckActionPK();
        caPk.setAcntRid(acntRid);
        caPk.setRid(caRid);
        PmsQaCheckAction ca = (PmsQaCheckAction) s.load(PmsQaCheckAction.class, caPk);
        assertEquals("update check action cpRid: ", cpRid, ca.getCpRid());
        assertEquals("update check action planDate: ", planDate_2, ca.getPlanDate());
        assertEquals("update check action actDate: ", actDate_2, ca.getActDate());
        assertEquals("update check action planPerformer: ", planPerformer_2, ca.getPlanPerformer());
        assertEquals("update check action actPerformer: ", actPerformer_2, ca.getActPerformer());
        assertEquals("update check action content: ", content_2, ca.getContent());
        assertEquals("update check action result: ", result_2, ca.getResult());
        assertEquals("update check action ncrNo: ", ncrNo_2, ca.getNrcNo());
        assertEquals("update check action occasion: ", occasion_2, ca.getOccasion());
    }

    private List getDataListInsert() {
        List lst = new ArrayList();
        DtoQaMonitoring dtoCp = new DtoQaMonitoring();
        dtoCp.setType(DtoQaCheckPoint.DTO_PMS_CHECK_POINT_TYPE);
        dtoCp.setBelongTo(belongTo);
        dtoCp.setAcntRid(acntRid);
        dtoCp.setBelongRid(belongRid);
        dtoCp.setRid(cpRid);
        dtoCp.setName(name);
        dtoCp.setMethod(method);
        dtoCp.setRemark(remark);
        dtoCp.setOp(IDto.OP_INSERT);
        lst.add(new DtoMonitoringTreeNode(dtoCp));

        DtoQaMonitoring dtoCa = new DtoQaMonitoring();
        dtoCa.setType(DtoQaCheckAction.DTO_PMS_CHECK_ACTION_TYPE);
        dtoCa.setAcntRid(acntRid);
        dtoCa.setCpRid(cpRid);
        dtoCa.setRid(caRid);
        dtoCa.setPlanDate(planDate);
        dtoCa.setActDate(actDate);
        dtoCa.setPlanPerformer(planPerformer);
        dtoCa.setActPerformer(actPerformer);
        dtoCa.setContent(content);
        dtoCa.setResult(result);
        dtoCa.setNrcNo(ncrNo);
        dtoCa.setOccasion(occasion);
        dtoCa.setOp(IDto.OP_INSERT);
        lst.add(new DtoMonitoringTreeNode(dtoCa));
        return lst;
    }

    private void initData() {
        String sqlCp = "insert into pms_qa_check_point(rid, acnt_rid, belong_rid, belong_to, name, method, remark) "
                       + "values(" + cpRid + ", " + acntRid + ", " + belongRid + ", '" + belongTo + "', '" + name + "', '" + method + "', '" + remark + "')";
        String sqlCa = "insert into pms_qa_check_action(rid, acnt_rid, cp_rid,plan_performer, act_performer, content, result, nrc_no, occasion) "
                       + "values(" + caRid + ", " + acntRid + ", " + cpRid + ", '" + planPerformer + "', '" + actPerformer + "', '" + content + "', '" + result + "', '" + ncrNo + "', '" + occasion + "')";
        lgMonitoring.getDbAccessor().executeUpdate(sqlCp);
        lgMonitoring.getDbAccessor().executeUpdate(sqlCa);
    }

    private List getDataListUpdate() {
        List lst = new ArrayList();
        DtoQaMonitoring dtoCp = new DtoQaMonitoring();
        dtoCp.setType(DtoQaCheckPoint.DTO_PMS_CHECK_POINT_TYPE);
        dtoCp.setBelongTo(belongTo);
        dtoCp.setAcntRid(acntRid);
        dtoCp.setBelongRid(belongRid);
        dtoCp.setRid(cpRid);
        dtoCp.setName(name_2);
        dtoCp.setMethod(method_2);
        dtoCp.setRemark(remark_2);
        dtoCp.setOp(IDto.OP_MODIFY);
        lst.add(new DtoMonitoringTreeNode(dtoCp));

        DtoQaMonitoring dtoCa = new DtoQaMonitoring();
        dtoCa.setType(DtoQaCheckAction.DTO_PMS_CHECK_ACTION_TYPE);
        dtoCa.setAcntRid(acntRid);
        dtoCa.setCpRid(cpRid);
        dtoCa.setRid(caRid);
        dtoCa.setPlanDate(planDate_2);
        dtoCa.setActDate(actDate_2);
        dtoCa.setPlanPerformer(planPerformer_2);
        dtoCa.setActPerformer(actPerformer_2);
        dtoCa.setContent(content_2);
        dtoCa.setResult(result_2);
        dtoCa.setNrcNo(ncrNo_2);
        dtoCa.setOccasion(occasion_2);
        dtoCa.setOp(IDto.OP_MODIFY);
        lst.add(new DtoMonitoringTreeNode(dtoCa));
        return lst;
    }


}
