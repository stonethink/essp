package itf.user;

import c2s.essp.common.user.DtoUser;

public class UserFactory {
//  static final String implClassName =
//      "itf.orgnization.LgOrgnizationUtilImpl";
//  static Class implClass = null;

  static final String empImplClassName =
      "itf.user.LgEmpUserUtilImpl";
  static Class empImplClass = null;

  static final String custImplClassName =
      "itf.user.LgCustUserUtilImpl";
  static Class custImplClass = null;

//  static final String queryUserImpClassName=
//      "itf.user.LgEmpUserUtilImpl";
  static final String queryUserImpClassName=
      "server.essp.projectpre.service.account.AccountServiceImpl";
  static Class queryUserImpClass = null;

  public UserFactory() {
  }

  public static IUserUtil create() {
    return create(DtoUser.USER_TYPE_EMPLOYEE);
  }

  public static IUserUtil create(String userType) {
    Class implClass = null;
    String implClassName = "";

    if (DtoUser.USER_TYPE_EMPLOYEE.equals(userType)) {
      implClassName = empImplClassName;
      implClass = empImplClass;
    } else if (DtoUser.USER_TYPE_CUST.equals(userType)) {
      implClassName = custImplClassName;
      implClass = custImplClass;
    } else {
      return null;
    }

    if (implClass == null) {
      try {
        implClass = Class.forName(implClassName);
      } catch (ClassNotFoundException ex) {
        return null;
      }
    }
    IUserUtil iObj = null;
    try {
      iObj = (IUserUtil) implClass.newInstance();
    } catch (IllegalAccessException ex1) {
    } catch (InstantiationException ex1) {
    }

    if (DtoUser.USER_TYPE_EMPLOYEE.equals(userType)) {
      empImplClass = implClass;
    } else if (DtoUser.USER_TYPE_CUST.equals(userType)) {
      custImplClass = implClass;
    }

    return iObj;
  }

  public static IHrQueryUser createQueryUser() {
    if (queryUserImpClass == null) {
      try {
        queryUserImpClass = Class.forName(queryUserImpClassName);
      } catch (ClassNotFoundException ex) {
        return null;
      }
    }
    IHrQueryUser iObj = null;
    try {
      iObj = (IHrQueryUser) queryUserImpClass.newInstance();
    } catch (IllegalAccessException ex1) {
    } catch (InstantiationException ex1) {
    }
    return iObj;
  }



  /** @link dependency */
  /*# IUser lnkIUser; */

  /** @link dependency */
  /*# IUserUtil lnkLgUserUtil; */

  public static void main(String[] args) {
    IUserUtil lg = UserFactory.create();
    DtoUser user = null;
//    user = lg.getUserByCode("000099");
//    user = lg.getUserByLoginId("stone.shi");
//    user = lg.getUserByLoginId(DtoUser.USER_TYPE_CUST, "bali");
  }

}
