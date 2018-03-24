package client.essp.timesheet.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import c2s.essp.common.user.DtoUser;
import client.essp.common.view.VWWorkArea;
import client.essp.timesheet.admin.level.VwLaborLevel;
import client.essp.timesheet.admin.role.VwLaborRole;
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
public class adminTest {
    public static void main(String[] args) {
        login("WH0607014");
        VWWorkArea area = new VWWorkArea();
        VwLaborLevel level = new VwLaborLevel();
        VwLaborRole role = new VwLaborRole();
        level.setParameter(null);
        level.refreshWorkArea();
        role.setParameter(null);
        role.refreshWorkArea();
        area.addTab("Level", level);
        area.addTab("Role", role);
        TestPanel.show(area);
    }

    public static void login(String loginId) {
        DtoUser user = new DtoUser();
        user.setUserID(loginId);
        user.setUserLoginId(loginId);
        HttpServletRequest request = new HttpServletRequestMock();
        HttpSession session = request.getSession();
        session.setAttribute(DtoUser.SESSION_USER, user);
    }

}
