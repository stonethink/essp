package itf.user;

import c2s.essp.common.user.DtoUser;
import server.framework.common.BusinessException;
import server.framework.logic.AbstractBusinessLogic;
import javax.sql.RowSet;


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
public class LgCustUserUtilImpl extends AbstractBusinessLogic implements
    IUserUtil {

  static final String preCustSQL =
      " SELECT p.id AS USERID , p.name AS USERNAME  , p.user_name AS LONGINNAME , '" +
      DtoUser.USER_TYPE_CUST + "' AS USERTYPE,p.email as EMAIL,p.phone as PHONE,p.fax as FAX from essp_sys_project_customer p";

  LgCustUserUtilImpl() {
  }

  /**
   * 依据用户Code(系统内的编号)实现一个内部用户(USER_TYPE_EMPLOYEE)
   * @param userCode String
   * @return DtoUser*/
  public DtoUser getUserByCode(String userCode) throws
      BusinessException {
//    DtoUser user = new DtoUser();
//
//    String bakEmpSQL = " AND h.code = '" + userCode + "' ";
//    String sql = preCustSQL + bakEmpSQL;

    return null;
  }


  /**
   * 依据用户登录名及用户类型获取一个内部用户(USER_TYPE_EMPLOYEE)
   * @param userLoginName String
   * @return DtoUser*/
  public DtoUser getUserByLoginId(String userLoginId) throws
      BusinessException {

    String bakCustSQL = " where p.id  = '" + userLoginId + "' ";
    String sql = preCustSQL + bakCustSQL;

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
    boolean bRtn = true;
    return null;
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
      }
    } catch (Exception ex) {
      throw new BusinessException(ex);
    }

    return user;

  }

}
