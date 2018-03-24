package server.essp.pms.wbs.process.checklist.logic;

import junit.framework.*;
import javax.sql.RowSet;
import db.essp.pms.PmsQaCheckPoint;
import net.sf.hibernate.HibernateException;
import java.util.List;
import db.essp.pms.PmsQaCheckAction;
import db.essp.pms.PmsWbsGuideline;
import db.essp.pms.PmsWbsGuidelineId;

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
public class TestLgQaCheckPoint extends TestCase {
    private LgQaCheckPoint lgQaCheckPoint = null;
    private Long acntRid1 = new Long(7896541);
    private Long acntRid2 = new Long(7896542);
    private Long acntRid3 = new Long(7896543);
    private Long wbsRid1 = new Long(1);
    private Long wbsRid2 = new Long(2);

    public TestLgQaCheckPoint(String name) {
        super(name);
    }

    protected void setUp() throws Exception {
        super.setUp();
        lgQaCheckPoint = new LgQaCheckPoint();
        lgQaCheckPoint.getDbAccessor().followTx();
        initData();
    }

    protected void tearDown() throws Exception {
        lgQaCheckPoint.getDbAccessor().rollback();
        lgQaCheckPoint = null;
        super.tearDown();

    }

    public void testWbsReferenceChangeNone2Have() throws Exception {
        PmsWbsGuideline gl = createGl(new Long(555555), new Long(1), acntRid1, wbsRid1);

        lgQaCheckPoint.wbsReferenceChange(gl, null, null);

        //check CP
        String qCpSql = "from PmsQaCheckPoint t where t.PK.acntRid = 555555";
        List lcp = lgQaCheckPoint.getDbAccessor().getSession().createQuery(qCpSql).list();
        this.assertEquals("Ref 从无到有，Copy的CP条数是否正确", 2, lcp.size());
        PmsQaCheckPoint cp = (PmsQaCheckPoint) lcp.get(0);
        this.assertEquals("Ref 从无到有，Copy的CP是否正确", "acnt 1 wbs 1 name1", cp.getName());

        //check CA
        String qCaSql = "from PmsQaCheckAction t where t.PK.acntRid = 555555";
        List lca = lgQaCheckPoint.getDbAccessor().getSession().createQuery(qCaSql).list();
        this.assertEquals("Ref 从无到有，Copy的CA条数是否正确", 1, lca.size());
        PmsQaCheckAction ca = (PmsQaCheckAction) lca.get(0);
        this.assertEquals("Ref 从无到有，Copy的CA是否正确", "acnt 1 cp 1 content", ca.getContent());


    }

    private PmsWbsGuideline createGl(Long acntRid, Long wbsRid, Long refAcntRid, Long refWbsRid) {
        PmsWbsGuideline gl = new PmsWbsGuideline();
        PmsWbsGuidelineId glId = new PmsWbsGuidelineId();
        glId.setAcntRid(acntRid);
        glId.setWbsRid(wbsRid);
        gl.setId(glId);
        gl.setRefAcntRid(refAcntRid);
        gl.setRefWbsRid(refWbsRid);
        return gl;
    }

    public void testWbsReferenceChangeHave2None() throws Exception {
       lgQaCheckPoint.wbsReferenceChange(createGl(acntRid2, wbsRid1, null, null),null,null);

       //check CP
        String qCpSql = "from PmsQaCheckPoint t where t.PK.acntRid =:acntRid";
        List lcp = lgQaCheckPoint.getDbAccessor().getSession().createQuery(qCpSql)
                   .setParameter("acntRid",acntRid2)
                   .list();
        this.assertEquals("Ref 有无到无，Copy的CP条数是否正确", 0, lcp.size());

        //check CA
        String qCaSql = "from PmsQaCheckAction t where t.PK.acntRid =:acntRid";
        List lca = lgQaCheckPoint.getDbAccessor().getSession().createQuery(qCaSql)
                   .setParameter("acntRid",acntRid2)
                   .list();
        this.assertEquals("Ref 有无到无，Copy的CA条数是否正确", 0, lca.size());
   }


    public void testWbsReferenceChangeHave2Have() throws Exception {
        lgQaCheckPoint.wbsReferenceChange(createGl(acntRid3, wbsRid1, acntRid1, wbsRid1),null,null);

        //check CP
        String qCpSql = "from PmsQaCheckPoint t where t.PK.acntRid = :acntRid";
        List lcp = lgQaCheckPoint.getDbAccessor().getSession().createQuery(qCpSql)
                   .setParameter("acntRid",acntRid3)
                   .list();
        this.assertEquals("Ref 从无到有，Copy的CP条数是否正确", 2, lcp.size());
        PmsQaCheckPoint cp = (PmsQaCheckPoint) lcp.get(0);
        this.assertEquals("Ref 从无到有，Copy的CP是否正确", "acnt 1 wbs 1 name1", cp.getName());

        //check CA
        String qCaSql = "from PmsQaCheckAction t where t.PK.acntRid = :acntRid";
        List lca = lgQaCheckPoint.getDbAccessor().getSession().createQuery(qCaSql)
                   .setParameter("acntRid",acntRid3)
                   .list();
        this.assertEquals("Ref 从无到有，Copy的CA条数是否正确", 1, lca.size());
        PmsQaCheckAction ca = (PmsQaCheckAction) lca.get(0);
        this.assertEquals("Ref 从无到有，Copy的CA是否正确", "acnt 1 cp 1 content", ca.getContent());

    }


    private void initData() {
        lgQaCheckPoint.getDbAccessor().executeUpdate("delete from pms_qa_check_point");
        lgQaCheckPoint.getDbAccessor().executeUpdate("delete from pms_qa_check_action");

        String cpSql1 = "insert into pms_qa_check_point(rid,acnt_rid,belong_rid,belong_to,name,rst) values(1,"+acntRid1+","+wbsRid1+",'wbs','acnt 1 wbs 1 name1','N')";
        String cpSql2 = "insert into pms_qa_check_point(rid,acnt_rid,belong_rid,belong_to,name,rst) values(2,"+acntRid1+","+wbsRid1+",'wbs','acnt 1 wbs 1 name2','N')";
        String cpSql3 = "insert into pms_qa_check_point(rid,acnt_rid,belong_rid,belong_to,name,rst) values(3,"+acntRid3+","+wbsRid1+",'wbs','acnt 1 wbs 2 name','N')";
        String cpSql4 = "insert into pms_qa_check_point(rid,acnt_rid,belong_rid,belong_to,name,rst) values(1,"+acntRid2+","+wbsRid1+",'wbs','acnt 2 wbs 1 name','N')";
        String caSql1 = "insert into pms_qa_check_action(rid, acnt_rid, cp_rid, content,rst) values(1,"+acntRid1+",1,'acnt 1 cp 1 content','N')";
        String caSql2 = "insert into pms_qa_check_action(rid, acnt_rid, cp_rid, content,rst) values(1,"+acntRid2+",1,'acnt 2 cp 1 content','N')";
        String caSql3 = "insert into pms_qa_check_action(rid, acnt_rid, cp_rid, content,rst) values(1,"+acntRid3+",3,'acnt 3 cp 1 content','N')";
        lgQaCheckPoint.getDbAccessor().executeUpdate(cpSql1);
        lgQaCheckPoint.getDbAccessor().executeUpdate(cpSql2);
        lgQaCheckPoint.getDbAccessor().executeUpdate(cpSql3);
        lgQaCheckPoint.getDbAccessor().executeUpdate(cpSql4);
        lgQaCheckPoint.getDbAccessor().executeUpdate(caSql1);
        lgQaCheckPoint.getDbAccessor().executeUpdate(caSql2);
        lgQaCheckPoint.getDbAccessor().executeUpdate(caSql3);
    }

}
