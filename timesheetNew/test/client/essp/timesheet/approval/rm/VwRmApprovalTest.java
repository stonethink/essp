package client.essp.timesheet.approval.rm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import c2s.essp.common.user.DtoUser;
import client.essp.common.view.VWGeneralWorkArea;
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
 * @author wenhaizheng
 * @version 1.0
 */
public class VwRmApprovalTest extends VWGeneralWorkArea{
    public VwRmApprovalTest() {
        login("WH0607015");
        VwRmApproval vw = new VwRmApproval();
        vw.setParameter(null);
        this.addTab("RM", vw);
    }

    public static void main(String[] args) {
        VwRmApprovalTest vwrmapprovaltest = new VwRmApprovalTest();
        TestPanel.show(vwrmapprovaltest);
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
