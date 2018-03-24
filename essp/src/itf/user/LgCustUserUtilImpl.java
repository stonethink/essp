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
   * �����û�Code(ϵͳ�ڵı��)ʵ��һ���ڲ��û�(USER_TYPE_EMPLOYEE)
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
   * �����û���¼�����û����ͻ�ȡһ���ڲ��û�(USER_TYPE_EMPLOYEE)
   * @param userLoginName String
   * @return DtoUser*/
  public DtoUser getUserByLoginId(String userLoginId) throws
      BusinessException {

    String bakCustSQL = " where p.id  = '" + userLoginId + "' ";
    String sql = preCustSQL + bakCustSQL;

    return getUserBySQL(sql);
  }

  /**
   * �����û�Code(ϵͳ�ڵı��)���û����ͻ�ȡһ���û�
   * @param userCode String
   * @return DtoUser*/
  public DtoUser getUserByCode(String userType, String userCode) throws
      BusinessException {
    return getUserByCode(userCode);
  }


  /**
   * �����û���¼�����û����ͻ�ȡһ���û�
   * @param userLoginName String
   * @return DtoUser*/
  public DtoUser getUserByLoginId(String userType, String userLoginId) throws
      BusinessException {
    return getUserByLoginId(userLoginId);
  }


  /**
   * �����ڲ��û���¼�����������û��Ƿ��¼�ɹ�
   * @param userLoginName String
   * @return DtoUser*/
  public DtoUser checkUserLogin(String userLoginId, String passwd) throws
      BusinessException {
    boolean bRtn = true;
    return null;
  }


  /**
   * �����û����ͣ��û���¼�����������û��Ƿ��¼�ɹ�
   * @param userLoginName String
   * @return DtoUser*/
  public DtoUser checkUserLogin(String userType, String userLoginId,
                                String passwd) throws
      BusinessException {
    boolean bRtn = true;
    return null;
  }


  /**
   * �����ڲ��û���¼���޸��û�����
   * @param userLoginName String
   * @return boolean*/
  public void changeUserPasswd(String userLoginId, String passwd) throws
      BusinessException {
    return;
  }


  /**
   * �����û����ͣ��û���¼���޸��û�����
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
