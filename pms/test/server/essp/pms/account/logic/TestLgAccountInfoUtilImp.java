package server.essp.pms.account.logic;

import junit.framework.*;
import server.framework.common.*;
import c2s.essp.common.account.*;
import java.util.List;

public class TestLgAccountInfoUtilImp extends TestCase {
    private LgAccountInfoUtilImp lgAccountInfoUtilImp = null;

    protected void setUp() throws Exception {
        super.setUp();
        lgAccountInfoUtilImp = new LgAccountInfoUtilImp();
    }

    protected void tearDown() throws Exception {
        lgAccountInfoUtilImp = null;
        super.tearDown();
    }
    public void testListLaborResourceByAcntRid() throws BusinessException, Exception {
        lgAccountInfoUtilImp.getDbAccessor().followTx();
        lgAccountInfoUtilImp.getDbAccessor().executeUpdate("delete from pms_labor_resources");
        lgAccountInfoUtilImp.getDbAccessor().executeUpdate("insert into pms_labor_resources(rid,acnt_rid,login_id) values('1','1','RongXiao')");
        lgAccountInfoUtilImp.getDbAccessor().executeUpdate("insert into pms_labor_resources(rid,acnt_rid,login_id) values('2','1','stone.shi')");
        List list = lgAccountInfoUtilImp.listLaborResourceByAcntRid(new Long(1));
        this.assertEquals("labor size:",2,list.size());
    }
//    //测试通过code查找Account
//    public void testGetAccountByCode() throws BusinessException, Exception {
//        lgAccountInfoUtilImp.getDbAccessor().followTx();
//        //删除
//        lgAccountInfoUtilImp.getDbAccessor().executeUpdate("delete from pms_acnt");
//        System.out.println("sql:\n"+sql);
//        this.lgAccountInfoUtilImp.getDbAccessor().executeUpdate(sql);
//        //查询存在的Account Code
//        String acntCode = acnt_id;
//        IDtoAccount account = lgAccountInfoUtilImp.getAccountByCode(acntCode);
//        this.assertEquals("acnt_brief:",account.getBrief(),acnt_brief);
//        this.assertEquals("acnt_currency:",account.getCurrency(),acnt_currency);
//        this.assertEquals("acnt_id:",account.getId(),acnt_id);
//        this.assertEquals("acnt_inner:",account.getInner(),acnt_inner);
//        this.assertEquals("acnt_organization:",account.getOrganization(),acnt_organization);
//        //查询不存在的Account Code
//        acntCode = "aass";
//        account = lgAccountInfoUtilImp.getAccountByCode(acntCode);
//        this.assertNull("no accoun!",account);
//        this.lgAccountInfoUtilImp.getDbAccessor().rollback();
//        /**@todo fill in the test code*/
//    }
    //测试通过rid查找Account
//    public void testGetAccountByRID() throws BusinessException, Exception {
//        lgAccountInfoUtilImp.getDbAccessor().followTx();
//        //删除
//        lgAccountInfoUtilImp.getDbAccessor().executeUpdate("delete from pms_acnt");
//        System.out.println("sql:\n"+sql);
//        this.lgAccountInfoUtilImp.getDbAccessor().executeUpdate(sql);
//        //查询存在的Account Code
//        IDtoAccount account = lgAccountInfoUtilImp.getAccountByRID(new Long(rid));
//        //查询不存在的Account Code
//        account = lgAccountInfoUtilImp.getAccountByRID(new Long(5555));
//        this.assertNull("no accoun!",account);
//        this.lgAccountInfoUtilImp.getDbAccessor().rollback();
//    }
    long rid = 55555555;
    String acnt_actual_finish = "2006/11/10";
    String acnt_actual_start = "2006/11/11";
    String acnt_anticipated_finish = "2006/11/12";
    String acnt_anticipated_start  = "2006/11/13";
    String  acnt_brief  = "i love this account!"          ;
    String  acnt_currency = "USD"         ;
    String  acnt_id = "002645W"               ;
    String  acnt_inner  = "I"           ;
    String  acnt_name  = "essp"             ;
    String  acnt_organization  = "uuuuu"   ;
    String  acnt_planned_finish = "2006/11/14";
    String  acnt_planned_start  = "2006/11/15";
    String  acnt_status  = "Normal"         ;
    String  acnt_type = "Project"             ;
         //插入新记录
         String sql = "insert into pms_acnt ( " +
          "acnt_actual_finish ," +
          "acnt_actual_start ," +
          "acnt_anticipated_finish  ," +
          "acnt_anticipated_start  ," +
          "acnt_brief   ," +
          "acnt_currency     ," +
          "acnt_id   ," +
          "acnt_inner   ," +
          "acnt_name  ," +
          "acnt_organization ," +
          "acnt_planned_finish   ," +
          "acnt_planned_start  ," +
          "acnt_status  ," +
          "acnt_type   ," +
          "rid " +

          ") values (to_date('"+acnt_actual_finish+"','yyyy/MM/dd')," +
          "to_date('"+acnt_actual_start+"','yyyy/MM/dd')," +
          "to_date('"+acnt_anticipated_finish+"','yyyy/MM/dd')," +
          "to_date('"+acnt_anticipated_start+"','yyyy/MM/dd')," +
          "'"+acnt_brief              +"'," +
          "'"+acnt_currency          +"'," +
          "'"+acnt_id                +"'," +
          "'"+acnt_inner            +"'," +
          "'"+acnt_name             +"'," +
          "'"+acnt_organization     +"'," +
          "to_date('"+acnt_planned_finish  +"','yyyy/MM/dd')," +
          "to_date('"+acnt_planned_start    +"','yyyy/MM/dd')," +
          "'"+acnt_status          +"'," +
          "'"+acnt_type             +"'," +
          "'"+rid  +"')";
}
