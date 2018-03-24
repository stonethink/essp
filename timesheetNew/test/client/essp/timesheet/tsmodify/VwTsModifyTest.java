package client.essp.timesheet.tsmodify;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import junitpack.HttpServletRequestMock;
import c2s.essp.common.user.DtoUser;

import com.wits.util.Parameter;
import com.wits.util.TestPanel;

public class VwTsModifyTest {
	public static void main(String[] args) {
		 VwTsModifyOuter vwTsModifyOuter = new VwTsModifyOuter();
		 Parameter para = new Parameter();
	     login("WH0607015");
	     vwTsModifyOuter.setParameter(para);
	     vwTsModifyOuter.refreshWorkArea();
	     TestPanel.show(vwTsModifyOuter);
		
	}
	private static void login(String loginId) {
	       DtoUser user = new DtoUser();
	       user.setUserID(loginId);
	       user.setUserLoginId(loginId);
	       user.setPassword("666");
	       user.setDomain("wh");
	       HttpServletRequest request = new HttpServletRequestMock();
	       HttpSession session = request.getSession();
	       session.setAttribute(DtoUser.SESSION_USER, user);
	       session.setAttribute(DtoUser.SESSION_LOGIN_USER, user);
	   }
}
