package itf.orgnization;

public class OrgnizationFactory {
  static final String implClassName = "itf.orgnization.LgOrgnizationUtilImpl";
  static Class implClass = null;

  public OrgnizationFactory() {
  }

  public static IOrgnizationUtil create() {
    return createOrgnizationUtil();
  }

  public static IOrgnizationUtil createOrgnizationUtil() {
    if (implClass == null) {
      try {
        implClass = Class.forName(implClassName);
      } catch (ClassNotFoundException ex) {
        return null;
      }
    }
    IOrgnizationUtil iObj = null;
    try {
      iObj = (IOrgnizationUtil) implClass.newInstance();
    } catch (IllegalAccessException ex1) {
    } catch (InstantiationException ex1) {
    }
    return iObj;
  }


  /** @link dependency */
  /*# IOrgnizationUtil lnkIOrgnizationUtil; */

  public static void main(String[] args) {
    IOrgnizationUtil lg = OrgnizationFactory.createOrgnizationUtil();
    lg.listOptOrgnization();
    String loginId = lg.getOrgManagerLoginId("UNIT00000248");
    System.out.println("ManagerLoginId=" + loginId);
  }

}
