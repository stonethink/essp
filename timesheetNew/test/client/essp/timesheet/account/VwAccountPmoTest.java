package client.essp.timesheet.account;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import junitpack.HttpServletRequestMock;
import c2s.essp.common.user.DtoUser;
import client.essp.timesheet.accountpmo.VwAccountPmoOuter;

import com.wits.util.Parameter;
import com.wits.util.TestPanel;

public class VwAccountPmoTest {
	public static void main(String[] args) {
        VwAccountPmoOuter vwAccount = new VwAccountPmoOuter();
        Parameter para = new Parameter();
        login("WH0607014");
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
