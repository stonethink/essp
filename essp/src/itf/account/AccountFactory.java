package itf.account;


public class AccountFactory {
    static final String implClassName = "server.essp.pms.account.logic.LgAccountUtilImpl";
    static Class implClass = null;

    public AccountFactory() {
    }

    public static IAccountUtil create() {
      if (implClass == null) {
        try {
          implClass = Class.forName(implClassName);
        } catch (ClassNotFoundException ex) {
          return null;
        }
      }
      IAccountUtil iObj = null;
      try {
        iObj = (IAccountUtil) implClass.newInstance();
      } catch (IllegalAccessException ex1) {
      } catch (InstantiationException ex1) {
      }
      return iObj;
    }

    public static IAccountListUtil createListUtil(){
        try {
            return (IAccountListUtil) Class.forName("server.essp.pms.account.logic.LgAccountListUtilImp").newInstance();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static IAccountInfoUtil createInfoUtil(){
        try {
            return  (IAccountInfoUtil) Class.forName("server.essp.pms.account.logic.LgAccountInfoUtilImp").newInstance();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    /** @link dependency */
    /*# IAccount lnkIAccount; */

    /** @link dependency */
    /*# IAccountUtil lnkIAccountUtil; */
}
