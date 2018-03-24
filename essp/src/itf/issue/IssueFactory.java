package itf.issue;

import itf.account.IAccountListUtil;



public class IssueFactory {
    static final String implClassName = "server.essp.issue.common.logic.LgIssueType";
       static Class implClass = null;

       public IssueFactory() {
       }

       public static IIssueTypeUtil createIssueType() {
         if (implClass == null) {
           try {
             implClass = Class.forName(implClassName);
           } catch (ClassNotFoundException ex) {
             return null;
           }
         }
         IIssueTypeUtil iObj = null;
         try {
           iObj = (IIssueTypeUtil) implClass.newInstance();
         } catch (IllegalAccessException ex1) {
         } catch (InstantiationException ex1) {
         }
         return iObj;
    }
    public static IIssueUtil createIssue() {
        try {
           return (IIssueUtil) Class.forName("server.essp.issue.common.logic.LgIssueBase").newInstance();
       } catch (ClassNotFoundException ex) {
           ex.printStackTrace();
       } catch (IllegalAccessException ex) {
           ex.printStackTrace();
       } catch (InstantiationException ex) {
           ex.printStackTrace();
       }
       return null;
    }
    public static IIssueUtil createIssueUtil() {
       try {
          return (IIssueUtil) Class.forName("server.essp.issue.issue.logic.LgIssue").newInstance();
      } catch (ClassNotFoundException ex) {
          ex.printStackTrace();
      } catch (IllegalAccessException ex) {
          ex.printStackTrace();
      } catch (InstantiationException ex) {
          ex.printStackTrace();
      }
      return null;
   }

}
