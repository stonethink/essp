package server.essp.pms.account.logic;

import junit.framework.*;
import server.framework.common.*;
import java.util.*;
import com.wits.util.comDate;
import c2s.essp.common.account.IDtoAccount;
import c2s.dto.DtoComboItem;
import server.framework.taglib.util.ISelectOption;

public class TestLgAccountListUtilImpNoUser extends TestCase {
    private LgAccountListUtilImp lgAccountListUtilImp = null;
    protected void setUp() throws Exception {
        super.setUp();
        lgAccountListUtilImp = new LgAccountListUtilImp();
    }

    protected void tearDown() throws Exception {
        lgAccountListUtilImp = null;
        super.tearDown();
    }


//    ///先删除所有记录,插入一条新记录,再查询,比较查询结果和插入记录
//    public void testListAllAccounts() throws BusinessException, Exception {
//        lgAccountListUtilImp.getDbAccessor().followTx();
//        //删除
//        lgAccountListUtilImp.getDbAccessor().executeUpdate("delete from pms_acnt cascade");
//        System.out.println("sql:\n"+sql);
//        this.lgAccountListUtilImp.getDbAccessor().executeUpdate(sql);
//
//        List actualReturn = lgAccountListUtilImp.listAllAccounts();
//        //判断结果集大小
//        this.assertEquals("data size:",1,actualReturn.size());
//        //判断每个字段
//        IDtoAccount account = (IDtoAccount) actualReturn.get(0);
//
//        this.assertEquals("acnt_actual_finish:",comDate.dateToString(account.getActualFinish(),"yyyy/MM/dd"),acnt_actual_finish);
//        this.assertEquals("acnt_actual_start:",comDate.dateToString(account.getActualStart(),"yyyy/MM/dd"),acnt_actual_start);
//        this.assertEquals("acnt_anticipated_finish:",comDate.dateToString(account.getAnticipatedFinish(),"yyyy/MM/dd"),acnt_anticipated_finish);
//        this.assertEquals("acnt_anticipated_start:",comDate.dateToString(account.getAnticipatedStart(),"yyyy/MM/dd"),acnt_anticipated_start);
//        this.assertEquals("acnt_brief:",account.getBrief(),acnt_brief);
//        this.assertEquals("acnt_currency:",account.getCurrency(),acnt_currency);
//        this.assertEquals("acnt_id:",account.getId(),acnt_id);
//        this.assertEquals("acnt_inner:",account.getInner(),acnt_inner);
//        this.assertEquals("acnt_organization:",account.getOrganization(),acnt_organization);
//        System.out.println(comDate.dateToString(account.getPlannedFinish(),"yyyy/MM/dd"));
//        this.assertEquals("acnt_planned_finish:",comDate.dateToString(account.getPlannedFinish(),"yyyy/MM/dd"),acnt_planned_finish);
//
//        this.assertEquals("acnt_planned_start:",comDate.dateToString(account.getPlannedStart(),"yyyy/MM/dd"),acnt_planned_start);
//        this.assertEquals("acnt_status:",account.getStatus(),acnt_status);
//        this.assertEquals("acnt_type:",account.getType(),acnt_type);
//
//        lgAccountListUtilImp.getDbAccessor().rollback();
//        /**@todo fill in the test code*/
//    }
    //测试组合查询条件,无字段值
    public void testContructSearchConditionNoValues() throws BusinessException, Exception {
        StringBuffer actualReturn = lgAccountListUtilImp.contructFieldCondition("acnt_status",null);
        this.assertEquals("no field values","",actualReturn.toString());

        StringBuffer actualReturn2 = lgAccountListUtilImp.contructFieldCondition("acnt_status",new String[]{});
        this.assertEquals("no field values","",actualReturn2.toString());

    }
    //测试组合查询条件
   public void testContructSearchCondition() throws BusinessException, Exception {
       String[] values = new String[]{"normal"};
       StringBuffer actualReturn = lgAccountListUtilImp.contructFieldCondition("acnt_status",values);
       String expect = "( acnt_status IN ( 'normal' ))";
       this.assertEquals("1 field values",expect,actualReturn.toString());

   }
   //测试组合status和type查询条件
   public void testAppendFieldsCondition(){
       //测试状态条件为空时
       String[] statusValues = null;
       lgAccountListUtilImp.statusCondition(statusValues);
       String[] typeValues =  new String[]{"project"};
       lgAccountListUtilImp.typeCondition(typeValues);
       String[] innerValues = null;
       lgAccountListUtilImp.innerCondition(innerValues);
       String expect = " AND ( acnt_type IN ( 'project' ))";
       StringBuffer actualReturn = lgAccountListUtilImp.appendFieldsCondition();
       System.out.println(actualReturn);
       this.assertEquals("only status values",expect,actualReturn.toString());
       //测试状态和类型条件联合
       statusValues =  new String[]{"normal","closed"};;
       lgAccountListUtilImp.statusCondition(statusValues);
       expect = " AND ( acnt_status IN ( 'normal','closed' )) AND ( acnt_type IN ( 'project' ))";
       actualReturn = lgAccountListUtilImp.appendFieldsCondition();
       System.out.println(actualReturn);
       this.assertEquals("type and status values",expect,actualReturn.toString());
        //测试状态,范围和类型条件联合
        innerValues = new String[]{"I","O"};
        lgAccountListUtilImp.innerCondition(innerValues);
        expect = " AND ( acnt_status IN ( 'normal','closed' )) AND ( acnt_type IN ( 'project' )) AND ( acnt_inner IN ( 'I','O' ))";
        actualReturn = lgAccountListUtilImp.appendFieldsCondition();
        System.out.println(actualReturn);
        this.assertEquals("type and status values",expect,actualReturn.toString());
   }
//   public void testlistAccountsByStatus() throws Exception {
//       lgAccountListUtilImp.getDbAccessor().followTx();
//       //删除
//       lgAccountListUtilImp.getDbAccessor().executeUpdate("delete from pms_acnt");
//       System.out.println("sql:\n"+sql);
//       this.lgAccountListUtilImp.getDbAccessor().executeUpdate(sql);
//       //设置查询状态为normal的记录
//       String[] values = new String[]{acnt_status};
//       lgAccountListUtilImp.statusCondition(values);
//       List actualReturn = lgAccountListUtilImp.listAccounts();
//       this.assertEquals("1 normal status",1,actualReturn.size());
//
//       //设置查询状态为Closed的记录
//       values = new String[]{"Closed"};
//       lgAccountListUtilImp.statusCondition(values);
//       actualReturn = lgAccountListUtilImp.listAccounts();
//       this.assertEquals("0 closed status",0,actualReturn.size());
//
//       lgAccountListUtilImp.getDbAccessor().rollback();
//   }
   //测试查询结果变成选项
//   public void testlistSelectAccounts() throws Exception {
//       lgAccountListUtilImp.getDbAccessor().followTx();
//              //删除
//              lgAccountListUtilImp.getDbAccessor().executeUpdate("delete from pms_acnt");
//              System.out.println("sql:\n"+sql);
//              this.lgAccountListUtilImp.getDbAccessor().executeUpdate(sql);
//              //设置查询状态为normal的记录
//              String[] values = new String[]{acnt_status};
//       lgAccountListUtilImp.statusCondition(values);
//       List actualReturn = lgAccountListUtilImp.listComboAccounts();
//       DtoComboItem item = (DtoComboItem) actualReturn.get(0);
//       this.assertEquals("combo diaplay name","002645W -- essp",item.getItemName());
//
//       actualReturn = lgAccountListUtilImp.listOptAccounts();
//       ISelectOption op = (ISelectOption) actualReturn.get(0);
//       this.assertEquals("option diaplay name","002645W -- essp",op.getLabel());
//
//       lgAccountListUtilImp.getDbAccessor().rollback();
//   }
   ////////////////////////////////////测试数据////////////////////////////////////////
   long rid = 100;
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
