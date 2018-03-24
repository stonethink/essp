package client.essp.timesheet.approval.pm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import c2s.essp.common.user.DtoUser;
import client.essp.common.view.VWGeneralWorkArea;
import com.wits.util.TestPanel;
import junitpack.HttpServletRequestMock;

public class VwPmApprovalTest extends VWGeneralWorkArea{
    public VwPmApprovalTest() {
        login("WH0607015");
        VwPmApproval vw = new VwPmApproval();
        vw.setParameter(null);
        this.addTab("PM", vw);
    }

    public static void main(String[] args) {
        VwPmApprovalTest vwPmApproval =new VwPmApprovalTest();
        TestPanel.show(vwPmApproval);
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
