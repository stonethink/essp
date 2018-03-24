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
        //����loginId��Ӧ��user_id
        ResultSet rt = lgAccountListUtilImp.getDbAccessor().executeQuery("select user_id from essp_upms_loginuser where loginname='"+loginId+"'");
        rt.next();
        String user_id = rt.getString(1);
        newOrg = "insert into essp_upms_unit(unit_id,name,manager) values('"+unit_id+"','"+name+"','"+user_id+"')";
    }

    protected void tearDown() throws Exception {
        lgAccountListUtilImp = null;
        super.tearDown();
    }
    //���Բ���LoginId��Ϊ�����ߵĲ��Ŵ���
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
    //���Բ�����Ա�����˼�����Ŀ
    public void testGetAcntRidOfParticipant() throws Exception {
        //ɾ��
        lgAccountListUtilImp.getDbAccessor().followTx();
        lgAccountListUtilImp.getDbAccessor().executeUpdate("delete from pms_labor_resources");
        lgAccountListUtilImp.getDbAccessor().executeUpdate(newLabor1);
        lgAccountListUtilImp.getDbAccessor().executeUpdate(newLabor2);

        Set list = lgAccountListUtilImp.getAcntRidOfParticipant("RongXiao");
        assertEquals("account size of participant", 2, list.size());
        lgAccountListUtilImp.getDbAccessor().rollback();
    }
    //���Բ�����Ա��ΪKeyPerson����Ŀ
//    public void testGetAcntRidOfKeyPersonal() throws Exception {
//        //ɾ��
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
    //���Բ�����Ա��ΪEBSManager����Ŀ
    public void testGetAcntRidOfEBSManager() throws Exception {
        //ɾ��
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
//        //��ʼ����
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
//        //����Ĭ����Ա����,PM�Ͳ�����
//        StringBuffer condition = lgAccountListUtilImp.appendPersonCondition("RongXiao");
//        System.out.println(condition);
//        String result = " ( acnt_manager='RongXiao' )  OR ( rid IN ( '2','1' )) ";
//        assertEquals("default usertype condition ", result, condition.toString());
//        //������Ա����:ִ�е�λ����,ebs_manager
//        String[] userType = new String[]{IDtoAccount.USER_TYPE_EXEC_UNIT_MANAGER,
//        IDtoAccount.USER_TYPE_EBS_MANAGER};
//        lgAccountListUtilImp.userTypeCondition(userType);
//        condition = lgAccountListUtilImp.appendPersonCondition("RongXiao");
//        System.out.println(condition);
//        result = "( acnt_organization IN ( 'aaaa' )) OR ( rid IN ( '2','1' )) ";
//        assertEquals("exec unit ", result, condition.toString());
//        //������Ա����:tc_signer
//        userType = new String[]{IDtoAccount.USER_TYPE_TC_SIGNER};
//        lgAccountListUtilImp.userTypeCondition(userType);
//        result = " ( tc_signer='RongXiao' ) ";
//        condition = lgAccountListUtilImp.appendPersonCondition("RongXiao");
//        assertEquals("tc_signer ", result, condition.toString());
//        lgAccountListUtilImp.getDbAccessor().rollback();
//
//    }
//    public void testlistAccount() throws Exception {
//        //ɾ��
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
    //////////////////////////////////////////��������//////////////////////////////////
    String loginId = "RongXiao";
    //Labor Resource��������
    private final String newLabor1 = "insert into pms_labor_resources(rid,acnt_rid,login_id) values('1','1','RongXiao')";
    private final String newLabor2 = "insert into pms_labor_resources(rid,acnt_rid,login_id) values('2','2','RongXiao')";
    //Key personal��������
    private final String newKP1 =
        "insert into pms_keypesonal(rid,acnt_rid,login_id) values('1','1','RongXiao')";
    private final String newKP2 =
        "insert into pms_keypesonal(rid,acnt_rid,login_id) values('2','3','RongXiao')";
    ////orgnization��������
    String unit_id="aaaa";
    String name="dept1";
    String newOrg = null;
    ////ebs��������
    String newEBS = "insert into ebs_ebs(rid,ebs_id,ebs_manager) values ('1','new_ebs','"+loginId+"')";
    String newEBSAcnt2 = "insert into ebs_ebs9acnt(ebs_rid,acnt_rid) values ('1','2') ";
    String newEBSAcnt1 = "insert into ebs_ebs9acnt(ebs_rid,acnt_rid) values ('1','1') ";

}
