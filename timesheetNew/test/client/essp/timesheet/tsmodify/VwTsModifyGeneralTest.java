package client.essp.timesheet.tsmodify;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import junitpack.HttpServletRequestMock;
import c2s.essp.common.user.DtoUser;
import c2s.essp.timesheet.weeklyreport.DtoTimeSheet;
import c2s.essp.timesheet.workscope.DtoAccount;
import client.essp.common.view.VWWorkArea;
import client.essp.timesheet.VwWorkBench;
import client.essp.timesheet.VwWorkBenchTest;
import client.framework.common.Global;
import client.framework.view.vwcomp.VWJButton;
import client.framework.view.vwcomp.VWJNumber;
import client.framework.view.vwcomp.VWJReal;
import client.framework.view.vwcomp.VWJText;

import com.wits.util.Parameter;
import com.wits.util.TestPanel;
import com.wits.util.ThreadVariant;

public class VwTsModifyGeneralTest extends VWWorkArea {

    VWWorkArea userPanel = new VWWorkArea();
    VWJText userName = new VWJText();
    VWJNumber tsRid = new VWJNumber();
    VWJButton loginBtn = new VWJButton("login");
    VwTsModifyGeneral vwWorkBench;

    public VwTsModifyGeneralTest() {
        login("WH0607014");
        try {
            jbInit();
        } catch (Exception e) {
            log.error(e);
        } 
        addUICEvent();
    }

    private void jbInit() throws Exception {
        vwWorkBench = new VwTsModifyGeneral();

        userName.setMaxByteLength(20);
        userName.setInput2ByteOk(true);
        userName.setText("WH0607014");
        tsRid.setUICValue(5991L);

        userPanel.setLayout(null);
        userPanel.setPreferredSize(new Dimension(400, 300));
        userPanel.add(userName);
        userPanel.add(tsRid);
        userPanel.add(loginBtn);
        tsRid.setBounds(110, 0, 100, 20);
        userName.setBounds(0, 0, 100, 20);
        loginBtn.setBounds(220, 0, 60, 20);

        this.addTab("LOGIN", userPanel);

        this.addTab("Work Bench", vwWorkBench);
    }

    private void addUICEvent() {
    	final Parameter param = new Parameter();
    	param.put(DtoAccount.SCOPE_LOGIN_ID, "WH0607014");
    	param.put(DtoTimeSheet.DTO_RID, 5991L);
        loginBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                login(userName.getText());
                Global.userId = userName.getText();
                Global.userName = userName.getText();
                param.put(DtoTimeSheet.DTO_RID, tsRid.getUICValue());
                param.put(DtoAccount.SCOPE_LOGIN_ID, Global.userId);
                vwWorkBench.setParameter(param);
                vwWorkBench.refreshWorkArea();
            }
        });
        vwWorkBench.setParameter(param);
        vwWorkBench.refreshWorkArea();
    }

    private void login(String loginId) {
        DtoUser user = new DtoUser();
        user.setUserID(loginId);
        user.setUserLoginId(loginId);
        user.setUserName("nameOf" + loginId);
        HttpServletRequest request = new HttpServletRequestMock();
        HttpSession session = request.getSession();
        session.setAttribute(DtoUser.SESSION_USER, user);
        ThreadVariant.getInstance().set(DtoUser.SESSION_USER, user);
        ThreadVariant.getInstance().set("LOGIN_ID", loginId);
    }

    public static void main(String[] args) {
        VWWorkArea area = new VwTsModifyGeneralTest();
        TestPanel.show(area);
    }
}