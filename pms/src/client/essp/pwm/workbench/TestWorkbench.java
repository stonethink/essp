package client.essp.pwm.workbench;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import c2s.essp.common.user.DtoUser;
import c2s.essp.pms.account.DtoPmsAcnt;
import client.essp.common.view.VWWorkArea;
import client.essp.ebs.VwEbs;
import client.essp.pms.account.VwAccount;
import client.essp.pms.activity.VwActivity;
import client.essp.pms.pbs.VwPmsPbs;
import client.essp.pms.wbs.VwWbs;
import client.essp.pwm.workbench.workitem.VwDailyReportList;
import client.essp.pwm.workbench.workitem.VwWorkItemBelongTo;
import client.essp.pwm.wpList.VwWp;
import client.framework.common.Global;
import client.framework.view.vwcomp.VWJButton;
import client.framework.view.vwcomp.VWJText;
import com.wits.util.Parameter;
import com.wits.util.TestPanel;
import com.wits.util.comDate;
import junitpack.HttpServletRequestMock;
import client.essp.pms.gantt.VwWbsGantt;

public class TestWorkbench extends VWWorkArea {
    VwAccount account ;
    VwAccount accountView ;
    VwLeftBench leftBench ;
    VwEbs ebs ;
    VwPmsPbs pbsPanel ;
    VwWbs wbs ;
    VwActivity activityPanel ;
    VWWorkArea userPanel ;
    VWJText userName;
    VWJButton loginBtn;
    VwWp wpPanel;
    VwWorkbench wkitemList;
    VwDailyReportList vwDailyReportList;
    VwWorkItemBelongTo vwWorkItemBelongTo;
    VwWbsGantt vwWbsGantt;

    VWJText acntRid = new VWJText();

    public TestWorkbench() {
        DtoUser user = new DtoUser();
        user.setUserID("stone.shi");
        user.setUserLoginId("stone.shi");
        HttpServletRequest request = new HttpServletRequestMock();
        HttpSession session = request.getSession();
        session.setAttribute(DtoUser.SESSION_USER, user);

        account = new VwAccount();
        accountView = new VwAccount();
        leftBench = new VwLeftBench();
        pbsPanel = new VwPmsPbs();
        ebs = new VwEbs();
        activityPanel = new VwActivity();
        userPanel = new VWWorkArea();
        userName = new VWJText();
        loginBtn = new VWJButton("login");
        wpPanel = new VwWp();
        wkitemList = new VwWorkbench();
        vwDailyReportList = new VwDailyReportList();
        vwWorkItemBelongTo = new VwWorkItemBelongTo();
        vwWbsGantt = new VwWbsGantt();

        try {
            jbInit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void jbInit() throws Exception {

        userName.setMaxByteLength(20);
        userName.setInput2ByteOk(true);
        userName.setText("stone.shi");
        acntRid.setMaxByteLength(20);
        acntRid.setInput2ByteOk(true);
        acntRid.setText("6");

        userPanel.setLayout(null);
        userPanel.setPreferredSize(new Dimension(400, 300));
        userPanel.add(userName);
        userPanel.add(loginBtn);
        userPanel.add(acntRid);
        userName.setBounds(0, 0, 100, 20);
        acntRid.setBounds(0, 25, 100, 20);
        loginBtn.setBounds(110, 0, 60, 20);

        loginBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DtoUser user = new DtoUser();
                user.setUserID(userName.getText());
                user.setUserLoginId(userName.getText());
                HttpServletRequest request = new HttpServletRequestMock();
                HttpSession session = request.getSession();
                session.setAttribute(DtoUser.SESSION_USER, user);
                Parameter param = new Parameter();
                param.put("inAcntRid", new Long(7));
                Calendar c = Calendar.getInstance();
                c.set(2005, 9, 10);
                param.put("selectedDate", c.getTime());
                param.put("entryFunType", "PmsAccountFun");

                DtoPmsAcnt dtoAccount = new DtoPmsAcnt();
                dtoAccount.setRid(new Long(acntRid.getText()));
                dtoAccount.setName("test acc name");
                dtoAccount.setId("test acc id");
//                dtoAccount.setManagerName("test acc m");
//                dtoAccount.setTypeName("test acc type");
                session.setAttribute(DtoPmsAcnt.SESSION_KEY, dtoAccount);

                Global.userId=userName.getText();
                Global.userName=userName.getText();

                account.setParameter(param);
                leftBench.setParameter(param);
                activityPanel.setParameter(param);
                pbsPanel.setParameter(param);
                wpPanel.setParameter(param);
                wkitemList.setParameter(param);
                vwDailyReportList.setParameter(param);
                vwWorkItemBelongTo.setParameter(param);
                param.put("entryFunType", "EbsMaintain");
                ebs.setParameter(param);

                param.put("entryFunType", "EbsAccountFun");
                accountView.setParameter(param);

                param.put("inAcntRid", acntRid.getText());
                vwWbsGantt.setParameter(param);
            }
        });

        Parameter param = new Parameter();
        param.put("inAcntRid", new Long(7));
        param.put("entryFunType", "PmsAccountFun");
        Calendar c = Calendar.getInstance();
        c.set(2005,9,10);
        param.put("selectedDate",c.getTime());
        account.setParameter(param);
        leftBench.setParameter(param);
        activityPanel.setParameter(param);
        wpPanel.setParameter(param);
        pbsPanel.setParameter(param);
        vwDailyReportList.setParameter(param);
        vwWorkItemBelongTo.setParameter(param);
        ebs.setParameter(param);
        this.addTab("LOGIN", userPanel);
        this.addTab("ACCOUNT", account);
        this.addTab("ACCOUNT view", accountView);

        param.put("inAcntRid", "6");
        vwWbsGantt.setParameter(param);

        VWWorkArea w = new VWWorkArea();
        w.setLayout(new BorderLayout());
        w.add(leftBench, BorderLayout.WEST);
        w.add(new VWWorkArea(), BorderLayout.CENTER);
        this.addTab("leftBench", w);

        this.addTab("Ebs",ebs);
        this.addTab("PBS", pbsPanel);
        this.addTab("ACTIVITY", activityPanel);
        this.addTab("wp", wpPanel);
        this.addTab("daily report", wkitemList);
        this.addTab("vwDailyReportList", vwDailyReportList);
        this.addTab("vwWorkItemBelongTo", vwWorkItemBelongTo);

        VWWorkArea v = new VWWorkArea(){
            public void setParameter(Parameter p){
                vwWbsGantt.setParameter(p);
            }
            public void refreshWorkArea(){
                vwWbsGantt.refreshWorkArea();
            }
        };
        v.addTab("Gantt", vwWbsGantt);
        this.addTab("vwWbsGantt", v);
    }

    public static void main(String[] args) {
        Calendar c = Calendar.getInstance();
        c.set(2005,9,10);
        Global.todayDateStr = comDate.dateToString(c.getTime(), "yyyyMMdd");
        Global.todayDatePattern="yyyyMMdd";

        TestPanel.show(new TestWorkbench());
    }
}
