package client.essp.timesheet.admin.code;

import client.essp.common.view.VWGeneralWorkArea;
import com.wits.util.TestPanel;
import javax.servlet.http.HttpSession;
import junitpack.HttpServletRequestMock;
import javax.servlet.http.HttpServletRequest;
import c2s.essp.common.user.DtoUser;
import client.essp.timesheet.admin.code.VwCodeMaintenance;
import client.essp.timesheet.admin.code.codevalue.leave.VwLeaveCode;
import client.essp.timesheet.admin.code.codevalue.VwCodeValue;

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
public class VwCodeTest extends VWGeneralWorkArea {

    private VwCodeMaintenance vwCode= new VwCodeMaintenance();
    private VwLeaveCodeMaint vwLeave = new VwLeaveCodeMaint();
    private VwCodeValue vwValue = new VwCodeValue();
    private VwLeaveCode vwLeaveCode = new VwLeaveCode();
    public VwCodeTest() {
        login("WH0607014");
        this.addTab("Code", vwCode);
        this.addTab("Value", vwValue);
        this.addTab("Leave Code", vwLeaveCode);
        this.addTab("Leave Code Type", vwLeave);
        vwCode.setParameter(null);
        vwValue.setParameter(null);
        vwLeaveCode.setParameter(null);
        vwLeave.setParameter(null);
        vwCode.refreshWorkArea();
        vwLeave.refreshWorkArea();
    }
    private void login(String loginId) {
            DtoUser user = new DtoUser();
            user.setUserID(loginId);
            user.setUserLoginId(loginId);
            HttpServletRequest request = new HttpServletRequestMock();
            HttpSession session = request.getSession();
            session.setAttribute(DtoUser.SESSION_USER, user);
    }
    public static void main(String[] args) {
        TestPanel.show(new VwCodeTest());
    }
}
