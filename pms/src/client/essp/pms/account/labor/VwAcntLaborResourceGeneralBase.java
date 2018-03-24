package client.essp.pms.account.labor;

import client.essp.common.humanAllocate.VWJHrAllocateButton;
import client.essp.common.view.VWGeneralWorkArea;
import client.framework.view.vwcomp.VWJCheckBox;
import client.framework.view.vwcomp.VWJComboBox;
import client.framework.view.vwcomp.VWJDate;
import client.framework.view.vwcomp.VWJLabel;
import client.framework.view.vwcomp.VWJText;
import client.framework.view.vwcomp.VWJTextArea;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.List;
import java.util.ArrayList;
import client.framework.view.common.comFORM;
import c2s.essp.pms.account.DtoAcntLoaborRes;
import client.framework.model.VMComboBox;
import client.essp.common.view.VWWorkArea;
import com.wits.util.TestPanel;
import client.essp.pms.account.labor.plan.VwLaborPlanDetailList;
import java.awt.*;
import client.framework.view.vwcomp.VWJReal;

public class VwAcntLaborResourceGeneralBase extends VWGeneralWorkArea {
    //标签
    VWJLabel lblLoginId = new VWJLabel();
    VWJLabel lblName = new VWJLabel();
    VWJLabel lblJobCode = new VWJLabel();
    VWJLabel lblStartDate = new VWJLabel();
    VWJLabel lblFinishDate = new VWJLabel();
    VWJLabel lblRoleName = new VWJLabel();
    VWJLabel lblResStatus = new VWJLabel();
    VWJLabel lblDescription = new VWJLabel();
    VWJLabel lblIsInternal = new VWJLabel();
    VWJLabel lblRemark = new VWJLabel();
    VWJLabel lblPlanHours = new VWJLabel();

    //输入控件
    VWJHrAllocateButton inputLoginId = new VWJHrAllocateButton();
    VWJText inputName = new VWJText();
    VWJComboBox inputJobCode = new VWJComboBox();
    VWJDate inputStartDate = new VWJDate();
    VWJDate inputFinishDate = new VWJDate();
    VWJText inputRoleName = new VWJText();
    VWJTextArea inputDescription = new VWJTextArea();
    VWJCheckBox inputIsInternal = new VWJCheckBox();
    VWJComboBox inputResStatus = new VWJComboBox();
    VWJTextArea inputRemark = new VWJTextArea();
    VWJReal inputPlanWorkHours = new VWJReal();

    //计划详细维护
    VWWorkArea detailPane = new VWWorkArea();
    VwLaborPlanDetailList detailList = new VwLaborPlanDetailList();

    public VwAcntLaborResourceGeneralBase() {
        try {
            jbInit();
            setUIComponentName();
            setTabOrder();
            setEnterOrder();
            initUI();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void jbInit() throws Exception {
        this.setPreferredSize(new Dimension(450, 400));
        this.setLayout(null);
        lblLoginId.setText("Login Id");
        lblLoginId.setBounds(55, 30, 80, 30);

        lblName.setText("Name");
        lblName.setBounds(55, 65, 80, 30);

        lblJobCode.setText("Job Code");
        lblJobCode.setBounds(410, 65, 80, 30);

        lblRoleName.setText("Role Name");
        lblRoleName.setBounds(55, 100, 80, 30);
//        lblRoleName.setBounds(55, 135, 80, 30);

        lblResStatus.setText("Resouce Status");
        lblResStatus.setBounds(new Rectangle(410, 100, 98, 30));
        lblResStatus.setBounds(410, 100, 100, 30);
//        lblResStatus.setBounds(410, 135, 100, 30);

        lblStartDate.setText("Start Date");
//        lblStartDate.setBounds(55, 100, 80, 30);
        lblStartDate.setBounds(55, 135, 80, 30);

        lblFinishDate.setText("Finish Date");
        lblFinishDate.setBounds(410, 135, 100, 30);
//        lblFinishDate.setBounds(410, 100, 80, 30);

        lblPlanHours.setBounds(55, 170, 108, 30);
        lblPlanHours.setText("Plan Work Hours");

        lblDescription.setText("Job Description");
        lblDescription.setBounds(55, 205, 108, 30);

        lblIsInternal.setText("Is Internal");
        lblIsInternal.setBounds(new Rectangle(410, 30, 80, 30));

        lblRemark.setText("Remark");
        lblRemark.setBounds(55, 260, 108, 30);
        //初始控件

        inputLoginId.setBounds(190, 35, 180, 25);
        inputLoginId.getTextComp().setMaxByteLength(50);
        inputName.setBounds(190, 70, 180, 25);
        inputName.setMaxByteLength(100);
        inputJobCode.setBounds(510, 70, 180, 25);

        inputRoleName.setBounds(190, 105, 180, 25);
//        inputRoleName.setBounds(190, 140, 180, 25);
        inputRoleName.setMaxByteLength(100);
        inputResStatus.setBounds(510, 105, 180, 25);
//        inputResStatus.setBounds(510, 140, 180, 25);

        inputStartDate.setBounds(190, 140, 180, 25);
//        inputStartDate.setBounds(190, 105, 180, 25);
        inputStartDate.setCanSelect(true);
        inputStartDate.setEnabled(false);

        inputFinishDate.setBounds(510, 140, 180, 25);
//        inputFinishDate.setBounds(510, 105, 180, 25);
        inputFinishDate.setCanSelect(true);
        inputFinishDate.setEnabled(false);

        inputPlanWorkHours.setMaxInputDecimalDigit(2);
        inputPlanWorkHours.setMaxInputIntegerDigit(8);
        inputPlanWorkHours.setBounds(190, 175, 180, 25);
        inputPlanWorkHours.setEnabled(false);

        inputDescription.setBounds(190, 205, 504, 42);
        inputIsInternal.setBounds(new Rectangle(519, 40, 15, 15));
        inputRemark.setBounds(190, 260, 504, 40);

        detailPane.setBounds(160,305,504,70);
        detailPane.setBounds(new Rectangle(55, 308, 637, 140));
        detailPane.addTab("Detail",detailList);

        this.add(lblLoginId);
        this.add(lblName);
        this.add(lblJobCode);
        this.add(lblStartDate);
        this.add(inputLoginId);
        this.add(inputName);
        this.add(inputJobCode);
        this.add(inputStartDate);
        this.add(inputFinishDate);
        this.add(inputRoleName);
        this.add(lblRoleName);
        this.add(lblDescription);
        this.add(lblFinishDate);
        this.add(lblIsInternal);
        this.add(lblRemark);
        this.add(inputIsInternal);
        this.add(inputDescription);
        this.add(inputRemark);
        this.add(lblResStatus);
        this.add(inputResStatus);

        this.add(lblPlanHours);
        this.add(inputPlanWorkHours);
        this.add(detailPane);
    }

    private void setUIComponentName() {
        inputLoginId.setName("loginId");
        inputName.setName("empName");
        inputJobCode.setName("jobcodeId");
        inputStartDate.setName("planStart");
        inputFinishDate.setName("planFinish");
        inputPlanWorkHours.setName("planWorkTime");
        inputRoleName.setName("roleName");
        inputDescription.setName("jobDescription");
        //inputIsInternal.setName("loginidStatus");
        inputResStatus.setName("resStatus");
        inputRemark.setName("remark");
    }

    private void setTabOrder() {
        List compList = new ArrayList();
        compList.add(inputLoginId.getTextComp());
        compList.add(inputIsInternal);
        compList.add(inputName);
        compList.add(inputJobCode);
//        compList.add(inputStartDate);
//        compList.add(inputFinishDate);
        compList.add(inputRoleName);
        compList.add(inputResStatus);
        compList.add(inputDescription);
        compList.add(inputRemark);
        comFORM.setTABOrder(this, compList);
    }

    private void setEnterOrder() {
        List compList = new ArrayList();
        compList.add(inputLoginId.getTextComp());
        compList.add(inputIsInternal);
        compList.add(inputName);
        compList.add(inputJobCode);
//        compList.add(inputStartDate);
//        compList.add(inputFinishDate);
        compList.add(inputRoleName);
        compList.add(inputResStatus);
        compList.add(inputDescription);
        compList.add(inputRemark);
        comFORM.setEnterOrder(this, compList);
    }

    private void initUI() {
        inputResStatus.setVMComboBox(VMComboBox.toVMComboBox(
            DtoAcntLoaborRes.RESOURCE_STATUS_NAME,
            DtoAcntLoaborRes.RESOURCE_STATUS_VALUE));
    }
    public static void main(String[] args) {
        VWWorkArea resource = new VWWorkArea();
        VwAcntLaborResourceGeneralBase general = new VwAcntLaborResourceGeneralBase();
        resource.addTab("add", general);
        TestPanel.show(resource);
    }

}
