package client.essp.pms;

import client.essp.pms.wbs.VwWbs;
import client.essp.pms.activity.VwActivity;
import com.wits.util.TestPanel;
import client.essp.common.view.VWWorkArea;
import com.wits.util.Parameter;
import client.essp.pms.account.VwAccount;
import c2s.essp.common.user.DtoUser;
import javax.servlet.http.HttpServletRequest;
import junitpack.HttpServletRequestMock;
import javax.servlet.http.HttpSession;
import client.framework.view.vwcomp.VWJText;
import java.awt.Dimension;
import client.framework.view.vwcomp.VWJButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import client.essp.pms.pbs.VwPmsPbs;
import client.essp.ebs.VwEbs;
import client.essp.common.code.VwCode;
import client.essp.pwm.wpList.VwWp;
import client.essp.pwm.workbench.VwWorkbench;
import client.framework.common.Global;
import client.essp.pms.modifyplan.VwBaseLinePlanModify;
import c2s.essp.pms.account.DtoAcntKEY;
import client.essp.pms.modifyplan.VwBLMonitoring;
import client.essp.pms.qa.plan.VwPlan;
import client.essp.pms.qa.monitoring.*;
import client.essp.pms.quality.activity.VWQualityActivityMain;
import client.essp.pms.quality.goal.VwQualityGoal;
import client.essp.common.loginId.VWJLoginId;

public class PmsPanel extends VWWorkArea implements ActionListener{
//    VwEbs ebs=new VwEbs();
    VwAccount account = new VwAccount();
//    VwPmsPbs pbs=new VwPmsPbs();
    VwWbs wbsPanel = new VwWbs();
//    VwPmsPbs pbsPanel = new VwPmsPbs();
    VwActivity activityPanel = new VwActivity();
//    VwWorkbench vwWorkbench = new VwWorkbench();

//    VwCode code = new VwCode();
//    VwWp wp = new VwWp();
    VwBaseLinePlanModify blPlanModify = new VwBaseLinePlanModify();
    VwBLMonitoring blMonitoring = new VwBLMonitoring();
    VWWorkArea userPanel = new VWWorkArea();
    VWJText userName = new VWJLoginId();
    VWJButton loginBtn = new VWJButton("login");

    VwPlan qaPlan=new VwPlan();
    VwQaMonitoring qaMonitoring= new VwQaMonitoring();
    VWQualityActivityMain qualityActivity = new VWQualityActivityMain();
    VwQualityGoal qualityGoal = new VwQualityGoal();

    static{
        Global.todayDateStr = "2005-12-03";
        Global.todayDatePattern = "yyyy-MM-dd";
        Global.userId = "WH0302008";
        DtoUser user = new DtoUser();
        user.setUserID(Global.userId);
        user.setUserLoginId(Global.userId);
        HttpServletRequest request = new HttpServletRequestMock();
        HttpSession session = request.getSession();
        session.setAttribute(DtoUser.SESSION_USER, user);
    }

    public PmsPanel() {
        try {
            jbInit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        userName.setMaxByteLength(20);
        userName.setInput2ByteOk(true);
        userPanel.setLayout(null);
        userPanel.setPreferredSize(new Dimension(400, 300));
        userPanel.add(userName);
        userPanel.add(loginBtn);
        userName.setBounds(0, 0, 100, 20);
        userName.setText("WH0302008");
        loginBtn.setBounds(110, 0, 60, 20);

        loginBtn.addActionListener(this);

        Parameter param2 = new Parameter();
//        param2.put("entryFunType", DtoAcntKEY.SEPG_ACCOUNT_FUN);
        Parameter param = new Parameter();
        param.put("inAcntRid", new Long(6022));
        param.put("entryFunType", DtoAcntKEY.SEPG_ACCOUNT_FUN);
//        ebs.setParameter(param2);
        account.setParameter(param);
        wbsPanel.setParameter(param);
//        pbs.setParameter(param);
        activityPanel.setParameter(param);
        blPlanModify.setParameter(param);
        blMonitoring.setParameter(param);
//        blPlanModify.refreshWorkArea();
//        code.setParameter(param);
//        wp.setParameter(param);
//        vwWorkbench.setParameter(param);
        this.addTab("LOGIN", userPanel);
//        this.addTab("EBS", ebs);
        this.addTab("ACCOUNT", account);
        this.addTab("WBS", wbsPanel);
//        this.addTab("PBS", pbsPanel);
        this.addTab("ACTIVITY", activityPanel);
        this.addTab("Planning",blPlanModify);
        this.addTab("Monitoring",blMonitoring);

        qaPlan.setParameter(param);
        this.addTab("QaPlan",qaPlan);
        qaMonitoring.setParameter(param);
        this.addTab("QaMonitoring", qaMonitoring);
        this.addTab("Quality Activity", qualityActivity);
        this.addTab("Quality Goal", qualityGoal);

//        this.addTab("PBS", pbs);
//        this.addTab("Code", code);
//        this.addTab("vwWorkbench", vwWorkbench);
    }

    public void actionPerformed(ActionEvent e) {
        DtoUser user = new DtoUser();
        Global.userId = userName.getUICValue().toString();
        user.setUserID(userName.getUICValue().toString());
        user.setUserLoginId(userName.getUICValue().toString());
        HttpServletRequest request = new HttpServletRequestMock();
        HttpSession session = request.getSession();
        session.setAttribute(DtoUser.SESSION_USER, user);
        Parameter param = new Parameter();
        param.put("inAcntRid", new Long(7));
        param.put("entryFunType", DtoAcntKEY.SEPG_ACCOUNT_FUN);

        account.setParameter(param);
        wbsPanel.setParameter(param);
        activityPanel.setParameter(param);
        blPlanModify.setParameter(param);
        blMonitoring.setParameter(param);
//        pbsPanel.setParameter(param);
//        code.setParameter(param);
//        wp.setParameter(param);
//        vwWorkbench.setParameter(param);
    }

    public static void main(String[] args) {
        TestPanel.show(new PmsPanel());
    }
}
