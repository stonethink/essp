package client.essp.common.security.role;

import client.essp.common.view.VWWorkArea;
import client.framework.common.Global;
import c2s.essp.common.user.DtoUser;
import javax.servlet.http.HttpServletRequest;
import junitpack.HttpServletRequestMock;
import javax.servlet.http.HttpSession;
import com.wits.util.TestPanel;
import client.essp.common.code.VwCode;
import com.wits.util.Parameter;


public class test extends VWWorkArea{

    VwRoleMenu vwRoleMenu = new VwRoleMenu();
    VwCode vwCode = new VwCode();
    static{
        Global.todayDateStr = "2005-12-03";
        Global.todayDatePattern = "yyyy-MM-dd";
        Global.userId = "stone.shi";
        DtoUser user = new DtoUser();
        user.setUserID(Global.userId);
        user.setUserName(Global.userId);
        HttpServletRequest request = new HttpServletRequestMock();
        HttpSession session = request.getSession();
        session.setAttribute(DtoUser.SESSION_USER, user);
    }

    public test() {
        try {
            jbInit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void jbInit(){
        this.addTab("Role2Menu",vwRoleMenu);
        vwCode.setParameter(new Parameter());
        this.addTab("Code",vwCode);
    }

    public static void main(String[] args){
        TestPanel.show(new test());
    }
}
