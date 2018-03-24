package client.essp.timesheet.weeklyreport;

import javax.servlet.http.HttpSession;
import junitpack.HttpServletRequestMock;
import javax.servlet.http.HttpServletRequest;
import c2s.essp.common.user.DtoUser;
import com.wits.util.TestPanel;
import client.essp.common.view.VWWorkArea;
import client.framework.view.common.DefaultComp;

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
public class VWJTimesheetsNotesTest {
    private static void login(String loginId) {
        DtoUser user = new DtoUser();
        user.setUserID(loginId);
        user.setUserLoginId(loginId);
        HttpServletRequest request = new HttpServletRequestMock();
        HttpSession session = request.getSession();
        session.setAttribute(DtoUser.SESSION_USER, user);
        session.setAttribute(DtoUser.SESSION_LOGIN_USER, user);
    }

    public static void main(String[] args) {
        login("WH0607015");
        VWJTimesheetsNotes vwjtimesheetsnotes = new VWJTimesheetsNotes();
        vwjtimesheetsnotes.setBounds(100, 150, 200, DefaultComp.TEXT_HEIGHT);
        vwjtimesheetsnotes.setTsRid(Long.valueOf(8));
        VWWorkArea workArea = new VWWorkArea();
        workArea.setLayout(null);
        workArea.add(vwjtimesheetsnotes);
        TestPanel.show(workArea);

    }


}
