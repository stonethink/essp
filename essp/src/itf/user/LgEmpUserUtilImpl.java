package itf.user;

import c2s.essp.common.user.DtoUser;
import server.framework.common.BusinessException;

import java.util.*;
import javax.sql.RowSet;
import server.framework.logic.AbstractBusinessLogic;

//import server.essp.common.ldap.LDAP;
import server.framework.hibernate.HBComAccess;
import itf.hr.LgHrUtilImpl;
import c2s.essp.common.user.DtoUserBase;
import server.essp.common.ldap.LDAPConfig;

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
 * @version 1.0*/
public class LgEmpUserUtilImpl extends AbstractBusinessLogic implements
        IUserUtil,IHrQueryUser {

    static final String preEmpSQL =
            " SELECT h.code AS USERID , h.name AS USERNAME , t.login_id  AS LONGINNAME , '"
            + DtoUser.USER_TYPE_EMPLOYEE
            + "' as USERTYPE,h.e_mail as EMAIL,h.CELL_PJONE as PHONE,h.CALL as FAX, h.DEPT as ORG_ID, (select NAME from essp_upms_unit where unit_id = h.DEPT and rownum=1) as ORG_NAME from "+LgHrUtilImpl.LOGIN_TABLE+" t , essp_hr_employee_main_t h where t.user_id = h.code "
            ;

    private String dimissionFLAG = " 1=1 ";

    private static String LOGIN_TYPE_LDAP = "LDAP";
    private static String LOGIN_TYPE_GENERAL = "General";


    /**
     * 依据用户Code(系统内的编号)实现一个内部用户(USER_TYPE_EMPLOYEE)
     * @param userCode String
     * @return DtoUser*/
    public DtoUser getUserByCode(String userCode) throws
            BusinessException {
//    DtoUser user = new DtoUser();
//
        String bakEmpSQL = " AND h.code = '" + userCode + "' ";
        String sql = preEmpSQL + bakEmpSQL;

        return getUserBySQL(sql);
    }


    /**
     * 依据用户登录名及用户类型获取一个内部用户(USER_TYPE_EMPLOYEE)
     * @param userLoginName String
     * @return DtoUser*/
    public DtoUser getUserByLoginId(String userLoginId) throws
            BusinessException {

        String bakEmpSQL = " AND Upper(t.login_id) = Upper('" + userLoginId + "') AND " +
                           dimissionFLAG;
        String sql = preEmpSQL + bakEmpSQL;

        return getUserBySQL(sql);
    }


    /**
     * 依据用户Code(系统内的编号)及用户类型获取一个用户
     * @param userCode String
     * @return DtoUser*/
    public DtoUser getUserByCode(String userType, String userCode) throws
            BusinessException {
        return getUserByCode(userCode);
    }


    /**
     * 依据用户登录名及用户类型获取一个用户
     * @param userLoginName String
     * @return DtoUser*/
    public DtoUser getUserByLoginId(String userType, String userLoginId) throws
            BusinessException {
        return getUserByLoginId(userLoginId);
    }


    /**
     * 依据内部用户登录名及密码检查用户是否登录成功
     * @param userLoginName String
     * @return DtoUser*/
    public DtoUser checkUserLogin(String userLoginId, String passwd) throws
            BusinessException {

        dimissionFLAG = " DIMISSION_FLAG = '0'";

        boolean bRtn = false;
        bRtn = checkUserByLDAP(userLoginId, passwd);
        if (bRtn == true) {
            return getUserByLDAP(userLoginId);
        }

        bRtn = checkUserByLogin(userLoginId, passwd);

        if (bRtn == true) {
            return getUserByLoginId(userLoginId);
        }

        throw new BusinessException("LOGIN", "Login ID or Password error!");

    }


    /**
     * 依据用户类型，用户登录名及密码检查用户是否登录成功
     * @param userLoginName String
     * @return DtoUser*/
    public DtoUser checkUserLogin(String userType, String userLoginId,
                                  String passwd) throws
            BusinessException {
        boolean bRtn = true;
        return null;
    }


    /**
     * 依据内部用户登录名修改用户密码
     * @param userLoginName String
     * @return boolean*/
    public void changeUserPasswd(String userLoginId, String passwd) throws
            BusinessException {
        return;
    }


    /**
     * 依据用户类型，用户登录名修改用户密码
     * @param userLoginName String
     * @return boolean*/
    public void changeUserPasswd(String userType, String userLoginId,
                                 String passwd) throws
            BusinessException {
        return;
    }

    protected DtoUser getUserBySQL(String sql) throws BusinessException {
        DtoUser user = new DtoUser();

        try {
            RowSet rs = this.getDbAccessor().executeQuery(sql);
            while (rs.next()) {
                user.setUserID(rs.getString("USERID"));
                user.setUserName(rs.getString("USERNAME"));
                user.setUserType(rs.getString("USERTYPE"));
                user.setUserLoginId(rs.getString("LONGINNAME"));
                user.setEmail(rs.getString("EMAIL"));
                user.setPhone(rs.getString("PHONE"));
                user.setFax(rs.getString("FAX"));
                user.setOrgId(rs.getString("ORG_ID"));
                user.setOrganization(rs.getString("ORG_NAME"));
            }
        } catch (Exception ex) {
            throw new BusinessException(ex);
        }

        return user;

    }

    private boolean checkUserByLogin(String userLoginID, String passwd) {
        boolean bRtn = false;
        String sql =
                " select * from "+LgHrUtilImpl.LOGIN_TABLE+" l , essp_hr_employee_main_t e "
                + " where  l.user_id = e.code and  l.login_id = '"
                + userLoginID + "' and l.password = '" + passwd + "' ";
        try {
            RowSet rs = this.getDbAccessor().executeQuery(sql);
            if (rs.next()) {
                String dismissionFlag = rs.getString("dimission_flag");
                if("1".equals(dismissionFlag))
                    throw new BusinessException("LOG0002", "Login Error:The user["+userLoginID+"] has been dismissed!");
                bRtn = true;
            }
        } catch(BusinessException ex){
            throw ex;
        }
        catch (Exception ex) {
            throw new BusinessException("LOG0001", "Login Error--UnKnow Error",ex);
        }

        return bRtn;
    }

    private boolean checkUserByLDAP(String userLoginId, String passwd) {
        boolean bRtn = false;

        try {
//            LDAP ldap = new LDAP();
//
//            bRtn = ldap.authUser(userLoginId, passwd);
        } catch (Exception ex) {
        }

        return bRtn;
    }


    public DtoUser getUserByLDAP(String ldapUserLoginId) {
        String bakEmpSQL = " AND h.LDAP = '" + ldapUserLoginId + "' AND " +
                           dimissionFLAG;
        String sql = preEmpSQL + bakEmpSQL;

        return getUserBySQL(sql);
    }

    /**
     * 根据姓名模糊查询人员
     * @param name
     * @return
     */
    public List queryByName(String searchName) throws BusinessException {
        String sql = "select l.login_id as user_login_id,h.name as user_name,'"
                     + LDAPConfig.getDefaultDomainId()
                     + "' as domain, '" + DtoUserBase.USER_TYPE_EMPLOYEE + "' as user_type "
                +"from upms_loginuser l left join essp_hr_employee_main_t h on l.user_id = h.code"
                +"where lower(h.name) like lower('"+searchName+"%') ";
        List list = this.getDbAccessor().executeQueryToList(sql, DtoUserBase.class);
        return list;
    }


    public static void main(String args[]){
        try {
            HBComAccess dba = new HBComAccess();
            LgEmpUserUtilImpl logic = new LgEmpUserUtilImpl();
            logic.setDbAccessor(dba);
            dba.newTx();

            DtoUser user = logic.getUserByCode("000010");
            if( user != null ){
                log.info(user.getOrganization());
            }
            dba.endTxCommit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //  public void test(String userID) throws BusinessException {
//    ProjectSelectList ps = new ProjectSelectList();
//    List ls = ps.getPMAvailableProjectList(super.getDbAccessor(), userID);
//  }

}
