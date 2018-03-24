package itf.hr;

import itf.hr.IHrUtil;

public class HrFactory {
  static final String implClassName = "itf.hr.LgHrUtilImpl";
  static Class implClass = null;

  public HrFactory() {
  }

  public static IHrUtil create() {
    if (implClass == null) {
      try {
        implClass = Class.forName(implClassName);
      } catch (ClassNotFoundException ex) {
        return null;
      }
    }
    IHrUtil iObj = null;
    try {
      iObj = (IHrUtil) implClass.newInstance();
    } catch (IllegalAccessException ex1) {
    } catch (InstantiationException ex1) {
    }
    return iObj;
  }


  /** @link dependency */
  /*# IHrUtil lnkIOrgnizationUtil; */
  public static void main(String[] args) {
    IHrUtil lg = HrFactory.create();
    lg.listOptJobCode();
  }
}
