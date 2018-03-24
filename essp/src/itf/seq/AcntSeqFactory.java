package itf.seq;

public class AcntSeqFactory {
    static final String implClassName = "server.essp.pms.wbs.logic.AcntSeqUtilImpl";
    static Class implClass = null;

    public AcntSeqFactory() {
    }

    public static IAcntSeqUtil create() {
      if (implClass == null) {
        try {
          implClass = Class.forName(implClassName);
        } catch (ClassNotFoundException ex) {
          return null;
        }
      }
      IAcntSeqUtil iObj = null;
      try {
        iObj = (IAcntSeqUtil) implClass.newInstance();
      } catch (IllegalAccessException ex1) {
      } catch (InstantiationException ex1) {
      }
      return iObj;
    }

    /** @link dependency */
    /*# IAccount lnkIAccount; */

    /** @link dependency */
    /*# IAccountUtil lnkIAccountUtil; */
}
