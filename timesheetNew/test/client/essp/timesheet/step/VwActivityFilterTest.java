package client.essp.timesheet.step;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import junitpack.HttpServletRequestMock;
import c2s.essp.common.user.DtoUser;
import client.essp.common.view.VWWorkArea;
import client.framework.common.Global;
import client.framework.view.vwcomp.VWJButton;
import client.framework.view.vwcomp.VWJText;

import com.wits.util.Parameter;
import com.wits.util.TestPanel;
import com.wits.util.ThreadVariant;

public class VwActivityFilterTest extends VWWorkArea {

    VWWorkArea userPanel = new VWWorkArea();
    VWJText userName = new VWJText();
    VWJButton loginBtn = new VWJButton("login");
    VwActivityFilter vwFilter;

    public VwActivityFilterTest() {
        login("WH0607014");
        try {
            jbInit();
        } catch (Exception e) {
            log.error(e);
        } 
        addUICEvent();
    }

    private void jbInit() throws Exception {
    	vwFilter = new VwActivityFilter();

        userName.setMaxByteLength(20);
        userName.setInput2ByteOk(true);
        userName.setText("WH0607014");

        userPanel.setLayout(null);
        userPanel.setPreferredSize(new Dimension(400, 300));
        userPanel.add(userName);
        userPanel.add(loginBtn);
        userName.setBounds(0, 0, 100, 20);
        loginBtn.setBounds(110, 0, 60, 20);

        this.addTab("LOGIN", userPanel);

        this.addTab("Filter", vwFilter);
    }

    private void addUICEvent() {
        loginBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                login(userName.getText());
                Global.userId = userName.getText();
                Global.userName = userName.getText();
                vwFilter.setParameter(new Parameter());
                vwFilter.refreshWorkArea();
            }
        });
        vwFilter.setParameter(new Parameter());
        vwFilter.refreshWorkArea();
    }

    public static void login(String loginId) {
        DtoUser user = new DtoUser();
        Global.userId = loginId;
        user.setUserID(loginId);
        user.setUserLoginId("WH0607014");
        user.setPassword("/*-+");
        user.setDomain("wh");
        HttpServletRequest request = new HttpServletRequestMock();
        HttpSession session = request.getSession();
        session.setAttribute(DtoUser.SESSION_USER, user);
        ThreadVariant.getInstance().set(DtoUser.SESSION_USER, user);
        ThreadVariant.getInstance().set(DtoUser.SESSION_LOGIN_USER, user);
        ThreadVariant.getInstance().set("LOGIN_ID", loginId);
    }

    public static void main(String[] args) {
        VWWorkArea area = new VwActivityFilterTest();
        TestPanel.show(area);
    }
}
