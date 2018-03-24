package client.essp.timesheet.account;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import c2s.essp.common.user.DtoUser;
import com.wits.util.Parameter;
import com.wits.util.TestPanel;
import junitpack.HttpServletRequestMock;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class VwAccountTest {
    public static void print(String... args) {
        for(String arg : args) {
            System.out.println(arg);
        }
    }
    public static void main(String[] args) {
        VwAccount vwAccount = new VwAccount();
        Parameter para = new Parameter();
        login("WH0607015");
        vwAccount.setParameter(para);
        vwAccount.refreshWorkArea();
        TestPanel.show(vwAccount);
    }
    private static void login(String loginId) {
       DtoUser user = new DtoUser();
       user.setUserID(loginId);
       user.setUserLoginId(loginId);
       HttpServletRequest request = new HttpServletRequestMock();
       HttpSession session = request.getSession();
       session.setAttribute(DtoUser.SESSION_USER, user);
       session.setAttribute(DtoUser.SESSION_LOGIN_USER, user);
   }

}
