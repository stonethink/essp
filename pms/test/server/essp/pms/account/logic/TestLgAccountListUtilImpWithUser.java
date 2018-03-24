package server.essp.pms.account.logic;

import junit.framework.*;
import java.util.*;
import c2s.essp.common.account.IDtoAccount;
import java.sql.ResultSet;

public class TestLgAccountListUtilImpWithUser extends TestCase {
    private LgAccountListUtilImp lgAccountListUtilImp = null;

    protected void setUp() throws Exception {
        super.setUp();
        lgAccountListUtilImp = new LgAccountListUtilImp();
        //查找loginId对应的user_id
        ResultSet rt = lgAccountListUtilImp.getDbAccessor().executeQuery("select user_id from essp_upms_loginuser where loginname='"+loginId+"'");
        rt.next();
        String user_id = rt.getString(1);
        newOrg = "insert into essp_upms_unit(unit_id,name,manager) values('"+unit_id+"','"+name+"','"+user_id+"')";
    }

    protected void tearDown() throws Exception {
        lgAccountListUtilImp = null;
        super.tearDown();
    }
    //测试查找LoginId作为管理者的部门代码
//    public void testGetOrgIdOfManager() throws Exception {
//       lgAccountListUtilImp.getDbAccessor().followTx();
//       lgAccountListUtilImp.getDbAccessor().executeUpdate("delete from essp_upms_unit");
//       lgAccountListUtilImp.getDbAccessor().executeUpdate(newOrg);
//       List list = lgAccountListUtilImp.getOrgIdOfManager(loginId);
//       assertEquals("dept size", 1, list.size());
//       String deptId = (String) list.get(0);
//       assertEquals("dept id", unit_id, deptId);
//       lgAccountListUtilImp.getDbAccessor().rollback();
//    }
    //测试查找人员参与了几个项目
    public void testGetAcntRidOfParticipant() throws Exception {
        //删除
        lgAccountListUtilImp.getDbAccessor().followTx();
        lgAccountListUtilImp.getDbAccessor().executeUpdate("delete from pms_labor_resources");
        lgAccountListUtilImp.getDbAccessor().executeUpdate(newLabor1);
        lgAccountListUtilImp.getDbAccessor().executeUpdate(newLabor2);

        Set list = lgAccountListUtilImp.getAcntRidOfParticipant("RongXiao");
        assertEquals("account size of participant", 2, list.size());
        lgAccountListUtilImp.getDbAccessor().rollback();
    }
    //测试查找人员作为KeyPerson个项目
//    public void testGetAcntRidOfKeyPersonal() throws Exception {
//        //删除
//        lgAccountListUtilImp.getDbAccessor().followTx();
//        lgAccountListUtilImp.getDbAccessor().executeUpdate("delete from pms_keypesonal");
//        lgAccountListUtilImp.getDbAccessor().executeUpdate(newKP1);
//        lgAccountListUtilImp.getDbAccessor().executeUpdate(newKP2);
//
//        Set list = lgAccountListUtilImp.getAcntRidOfParticipant("RongXiao");
//        assertEquals("account size of keypersonal", 2, list.size());
//        lgAccountListUtilImp.getDbAccessor().rollback();
//
//    }
    //测试查找人员作为EBSManager个项目
    public void testGetAcntRidOfEBSManager() throws Exception {
        //删除
        lgAccountListUtilImp.getDbAccessor().followTx();
        lgAccountListUtilImp.getDbAccessor().executeUpdate("delete from ebs_ebs");
        lgAccountListUtilImp.getDbAccessor().executeUpdate("delete from ebs_ebs9acnt");
        lgAccountListUtilImp.getDbAccessor().executeUpdate(newEBS);
        lgAccountListUtilImp.getDbAccessor().executeUpdate(newEBSAcnt1);
        lgAccountListUtilImp.getDbAccessor().executeUpdate(newEBSAcnt2);
        Set set = lgAccountListUtilImp.getAcntRidOfEBSManager(loginId);
        assertEquals("account size of ebsmanger", 2, set.size());
        lgAccountListUtilImp.getDbAccessor().rollback();
    }
//    public void testAppendPersonCondition() throws Exception{
//        //初始数据
//        lgAccountListUtilImp.getDbAccessor().followTx();
//        lgAccountListUtilImp.getDbAccessor().executeUpdate("delete from pms_labor_resources");
//        lgAccountListUtilImp.getDbAccessor().executeUpdate(newLabor1);
//        lgAccountListUtilImp.getDbAccessor().executeUpdate(newLabor2);
//        lgAccountListUtilImp.getDbAccessor().executeUpdate("delete from pms_keypesonal");
//        lgAccountListUtilImp.getDbAccessor().executeUpdate(newKP1);
//        lgAccountListUtilImp.getDbAccessor().executeUpdate(newKP2);
//        lgAccountListUtilImp.getDbAccessor().executeUpdate("delete from essp_upms_unit");
//        lgAccountListUtilImp.getDbAccessor().executeUpdate(newOrg);
//        lgAccountListUtilImp.getDbAccessor().executeUpdate("delete from ebs_ebs");
//        lgAccountListUtilImp.getDbAccessor().executeUpdate("delete from ebs_ebs9acnt");
//        lgAccountListUtilImp.getDbAccessor().executeUpdate(newEBS);
//        lgAccountListUtilImp.getDbAccessor().executeUpdate(newEBSAcnt1);
//        lgAccountListUtilImp.getDbAccessor().executeUpdate(newEBSAcnt2);
//
//
//        //测试默认人员类型,PM和参与者
//        StringBuffer condition = lgAccountListUtilImp.appendPersonCondition("RongXiao");
//        System.out.println(condition);
//        String result = " ( acnt_manager='RongXiao' )  OR ( rid IN ( '2','1' )) ";
//        assertEquals("default usertype condition ", result, condition.toString());
//        //测试人员类型:执行单位主管,ebs_manager
//        String[] userType = new String[]{IDtoAccount.USER_TYPE_EXEC_UNIT_MANAGER,
//        IDtoAccount.USER_TYPE_EBS_MANAGER};
//        lgAccountListUtilImp.userTypeCondition(userType);
//        condition = lgAccountListUtilImp.appendPersonCondition("RongXiao");
//        System.out.println(condition);
//        result = "( acnt_organization IN ( 'aaaa' )) OR ( rid IN ( '2','1' )) ";
//        assertEquals("exec unit ", result, condition.toString());
//        //测试人员类型:tc_signer
//        userType = new String[]{IDtoAccount.USER_TYPE_TC_SIGNER};
//        lgAccountListUtilImp.userTypeCondition(userType);
//        result = " ( tc_signer='RongXiao' ) ";
//        condition = lgAccountListUtilImp.appendPersonCondition("RongXiao");
//        assertEquals("tc_signer ", result, condition.toString());
//        lgAccountListUtilImp.getDbAccessor().rollback();
//
//    }
//    public void testlistAccount() throws Exception {
//        //删除
//        lgAccountListUtilImp.getDbAccessor().followTx();
//        lgAccountListUtilImp.getDbAccessor().followTx();
//        lgAccountListUtilImp.getDbAccessor().executeUpdate("delete from pms_labor_resources");
//        lgAccountListUtilImp.getDbAccessor().executeUpdate(newLabor1);
//        lgAccountListUtilImp.getDbAccessor().executeUpdate(newLabor2);
//        lgAccountListUtilImp.getDbAccessor().executeUpdate("delete from pms_keypesonal");
//        lgAccountListUtilImp.getDbAccessor().executeUpdate(newKP1);
//        lgAccountListUtilImp.getDbAccessor().executeUpdate(newKP2);
//
//        List list = lgAccountListUtilImp.listAccounts("RongXiao");
//        assertEquals("default query", 0, list.size());
//        lgAccountListUtilImp.getDbAccessor().rollback();
//    }
    //////////////////////////////////////////测试数据//////////////////////////////////
    String loginId = "RongXiao";
    //Labor Resource测试数据
    private final String newLabor1 = "insert into pms_labor_resources(rid,acnt_rid,login_id) values('1','1','RongXiao')";
    private final String newLabor2 = "insert into pms_labor_resources(rid,acnt_rid,login_id) values('2','2','RongXiao')";
    //Key personal测试数据
    private final String newKP1 =
        "insert into pms_keypesonal(rid,acnt_rid,login_id) values('1','1','RongXiao')";
    private final String newKP2 =
        "insert into pms_keypesonal(rid,acnt_rid,login_id) values('2','3','RongXiao')";
    ////orgnization测试数据
    String unit_id="aaaa";
    String name="dept1";
    String newOrg = null;
    ////ebs测试数据
    String newEBS = "insert into ebs_ebs(rid,ebs_id,ebs_manager) values ('1','new_ebs','"+loginId+"')";
    String newEBSAcnt2 = "insert into ebs_ebs9acnt(ebs_rid,acnt_rid) values ('1','2') ";
    String newEBSAcnt1 = "insert into ebs_ebs9acnt(ebs_rid,acnt_rid) values ('1','1') ";

}
