package client.essp.pms.activity.worker;

import client.essp.common.view.VWGeneralWorkArea;
import client.framework.view.vwcomp.VWJDate;
import client.framework.view.vwcomp.VWJText;
import javax.swing.JTextArea;
import client.framework.view.vwcomp.VWJLabel;
import client.framework.view.vwcomp.VWJNumber;
import client.framework.view.vwcomp.VWJComboBox;
import java.awt.Rectangle;
import java.util.List;
import java.util.ArrayList;
import client.framework.view.common.comFORM;
import client.framework.view.vwcomp.VWJTextArea;
import java.awt.*;

public class VwActivityWorkerGeneralBase extends VWGeneralWorkArea {
    //定义标签
    VWJLabel lbLoginId = new VWJLabel();
    VWJLabel lbName = new VWJLabel();
    VWJLabel lbRoleName = new VWJLabel();
    VWJLabel lbJobCode = new VWJLabel();
    VWJLabel lbPlannedStart = new VWJLabel();
    VWJLabel lbPlannedFinish = new VWJLabel();
    VWJLabel lbPlannWorkTime = new VWJLabel();
//    VWJLabel lbActualStart = new VWJLabel();
//    VWJLabel lbActualFinish = new VWJLabel();
//    VWJLabel lbActualWorkTime = new VWJLabel();
//    VWJLabel lbAtComplet = new VWJLabel();
    VWJLabel lbRemark = new VWJLabel();
    VWJLabel lbJobDescription = new VWJLabel();
    //定义界面控件
    VWJComboBox inputLoginId = new VWJComboBox();
    VWJText inputRoleName = new VWJText();
    VWJDate inputPlannedStart = new VWJDate();
    VWJNumber inputPlanWorkTime = new VWJNumber();
//    VWJDate inputActualStart = new VWJDate();
//    VWJNumber inputActualWorkTime = new VWJNumber();
    VWJText inputName = new VWJText();
    VWJText inputJobCode = new VWJText();
    VWJDate inputPlannedFinish = new VWJDate();
//    VWJDate inputActualFinish = new VWJDate();
//    VWJNumber inputAcCompletion = new VWJNumber();
    VWJTextArea inputRemark = new VWJTextArea();
    VWJTextArea inputJobDescription = new VWJTextArea();

    public VwActivityWorkerGeneralBase() {
        try {
            jbInit();
            setTabOrder();
            setEnterOrder();
            setUIComponentName();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void jbInit() throws Exception {
        this.setLayout(null);
        lbLoginId.setText("Login Id");
        lbLoginId.setBounds(new Rectangle(40, 30, 80, 30));

        lbName.setText("Name");
        lbName.setBounds(new Rectangle(350, 30, 80, 30));

        lbRoleName.setText("Role Name");
        lbRoleName.setBounds(new Rectangle(40, 80, 80, 30));

        lbJobCode.setText("JobCode");
        lbJobCode.setBounds(new Rectangle(350, 80, 80, 30));

        lbPlannedStart.setText("Planned Start");
        lbPlannedStart.setBounds(new Rectangle(40, 130, 80, 30));

        lbPlannedFinish.setText("Planned Finish");
        lbPlannedFinish.setBounds(new Rectangle(350, 130, 100, 30));

        lbPlannWorkTime.setText("Plan Work Time(p.h)");
        lbPlannWorkTime.setBounds(new Rectangle(40, 180, 115, 30));

//        lbActualStart.setText("Actual Start");
//        lbActualStart.setBounds(new Rectangle(40, 230, 80, 30));
//
//        lbActualFinish.setText("Actual Finish");
//        lbActualFinish.setBounds(new Rectangle(350, 230, 80, 30));
//
//        lbActualWorkTime.setText("Actual Work Time(p.h)");
//        lbActualWorkTime.setBounds(new Rectangle(40, 280, 129, 30));
//
//        lbAtComplet.setText("At Completion(p.h)");
//        lbAtComplet.setBounds(new Rectangle(350, 280, 121, 30));

        lbJobDescription.setText("Job Description");
        lbJobDescription.setBounds(new Rectangle(40, 230, 93, 30));

        lbRemark.setText("Remark");
        lbRemark.setBounds(new Rectangle(40, 330, 80, 30));

        inputLoginId.setBounds(new Rectangle(165, 30, 160, 25));

        inputRoleName.setBounds(new Rectangle(165, 80, 160, 25));

        inputPlannedStart.setBounds(new Rectangle(165, 130, 160, 25));
        inputPlannedStart.setCanSelect(true);

        inputPlanWorkTime.setBounds(new Rectangle(165, 180, 160, 25));
        inputPlanWorkTime.setMaxInputIntegerDigit(8);

//        inputActualStart.setBounds(new Rectangle(165, 230, 160, 25));
//        inputActualStart.setCanSelect(true);
//
//        inputActualWorkTime.setBounds(new Rectangle(165, 280, 160, 25));
//        inputActualWorkTime.setMaxInputIntegerDigit(8);

        inputJobDescription.setBounds(new Rectangle(165, 230, 455, 100));
        inputRemark.setBounds(new Rectangle(165, 340, 455, 100));

        inputName.setBounds(new Rectangle(458, 30, 160, 25));

        inputJobCode.setBounds(new Rectangle(458, 80, 160, 25));

        inputPlannedFinish.setBounds(new Rectangle(458, 130, 160, 25));
        inputPlannedFinish.setCanSelect(true);

//        inputActualFinish.setBounds(new Rectangle(458, 230, 160, 25));
//        inputActualFinish.setCanSelect(true);
//
//        inputAcCompletion.setBounds(new Rectangle(458, 280, 160, 25));
//        inputAcCompletion.setMaxInputIntegerDigit(8);
        this.add(lbLoginId);
        this.add(lbRoleName);
        this.add(lbPlannedStart);
        this.add(lbPlannWorkTime);
//        this.add(lbActualWorkTime);
        this.add(lbRemark);
        this.add(inputLoginId);
        this.add(inputPlannedStart);
        this.add(inputPlanWorkTime);
//        this.add(inputActualStart);
        this.add(inputRemark);
//        this.add(lbActualStart);
        this.add(lbName);
        this.add(lbJobCode);
        this.add(lbPlannedFinish);
//        this.add(lbActualFinish);
//        this.add(lbAtComplet);
        this.add(lbJobDescription);
        this.add(inputJobCode);
        this.add(inputPlannedFinish);
        this.add(inputName);
        this.add(inputRoleName);
//        this.add(inputActualFinish);
//        this.add(inputActualWorkTime);
//        this.add(inputAcCompletion);
        this.add(inputJobDescription);
    }

    private void setTabOrder() {
       List compList = new ArrayList();
       compList.add(inputLoginId);
       compList.add(inputName);
       compList.add(inputRoleName);
       compList.add(inputJobCode);
       compList.add(inputPlannedStart);
       compList.add(inputPlannedFinish);
       compList.add(inputPlanWorkTime);
//       compList.add(inputActualStart);
//       compList.add(inputActualFinish);
//       compList.add(inputActualWorkTime);
//       compList.add(inputAcCompletion);
       compList.add(inputJobDescription);
       compList.add(inputRemark);
       comFORM.setTABOrder(this, compList);
   }

   private void setEnterOrder() {
       List compList = new ArrayList();
       compList.add(inputLoginId);
       compList.add(inputName);
       compList.add(inputRoleName);
       compList.add(inputJobCode);
       compList.add(inputPlannedStart);
       compList.add(inputPlannedFinish);
       compList.add(inputPlanWorkTime);
//       compList.add(inputActualStart);
//       compList.add(inputActualFinish);
//       compList.add(inputActualWorkTime);
//       compList.add(inputAcCompletion);
       compList.add(inputJobDescription);
       compList.add(inputRemark);
       comFORM.setEnterOrder(this, compList);
   }

   private void setUIComponentName() {
       inputLoginId.setName("loginId");
       inputName.setName("empName");
       inputRoleName.setName("roleName");
       inputJobCode.setName("jobCode");
       inputPlannedStart.setName("planStart");
       inputPlannedFinish.setName("planFinish");
       inputPlanWorkTime.setName("planWorkTime");
//       inputActualStart.setName("actualStart");
//       inputActualFinish.setName("actualFinish");
//       inputActualWorkTime.setName("actualWorkTime");
//       inputAcCompletion.setName("etcWorkTime");
       inputJobDescription.setName("jobDescription");
       inputRemark.setName("remark");
    }
}
