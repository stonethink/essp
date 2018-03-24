package client.essp.tc.hrmanager.leave;

import client.essp.common.view.VWGeneralWorkArea;
import client.framework.view.vwcomp.VWJDate;
import client.framework.view.vwcomp.VWJText;
import client.framework.view.vwcomp.VWJButton;
import client.framework.view.vwcomp.VWJLabel;
import client.framework.view.vwcomp.VWJComboBox;
import client.framework.view.vwcomp.VWJTextArea;
import java.awt.Rectangle;
import java.util.List;
import java.util.ArrayList;
import client.framework.view.common.comFORM;
import java.awt.Dimension;
import client.framework.view.vwcomp.VWJReal;
import javax.swing.border.TitledBorder;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JTextField;
import client.essp.common.loginId.VWJLoginId;

public class VwLeaveGeneralBase extends VWGeneralWorkArea {
    public VwLeaveGeneralBase() {
        try {
            jbInit();
            setUIComponentName();
            setTabOrder();
            setEnterOrder();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    private void setUIComponentName(){
        inputLoginId.setName("loginId");
        inputDept.setName("orgName");
        inputLeaveType.setName("leaveName");
        inputSettlteMentWay.setName("settlementWay");
        inputDateFrom.setName("actualDateFrom");
        inputDateTo.setName("actualDateTo");
//        inputTimeFrom.setName("actualTimeFrom");
//        inputTimeTo.setName("actualTimeTo");
        inputTotalHours.setName("actualTotalHours");
        inputCause.setName("cause");
    }
    private void setTabOrder(){
        List compList = new ArrayList();
        compList.add(inputLoginId);
        compList.add(inputDept);
        compList.add(inputLeaveType);
        compList.add(inputDateFrom);
        compList.add(inputDateTo);
        compList.add(inputTimeFrom);
        compList.add(inputTimeTo);
        compList.add(inputTotalHours);
        compList.add(inputCause);
        comFORM.setTABOrder(this, compList);
    }
    private void setEnterOrder(){
        List compList = new ArrayList();
        compList.add(inputLoginId);
        compList.add(inputDept);
        compList.add(inputLeaveType);
        compList.add(inputDateFrom);
        compList.add(inputDateTo);
        compList.add(inputTimeFrom);
        compList.add(inputTimeTo);
        compList.add(inputTotalHours);
        compList.add(inputCause);
        comFORM.setTABOrder(this, compList);
    }


    public void jbInit() throws Exception {
        this.setLayout(null);
        setPreferredSize(new Dimension(530,450));

        lbLoginId.setText("Worker");
        lbLoginId.setBounds(new Rectangle(20, 20, 70, 20));
        inputLoginId.setBounds(new Rectangle(100, 20, 120, 20));
        inputLoginId.setEnabled(false);

        lbDept.setText("Dept");
        lbDept.setBounds(new Rectangle(250, 20, 70, 20));
        inputDept.setBounds(new Rectangle(340, 20, 120, 20));
        inputDept.setEnabled(false);

        lbLeaveType.setText("Leave Type");
        lbLeaveType.setBounds(new Rectangle(20, 50, 70, 20));
        inputLeaveType.setBounds(new Rectangle(100, 50, 120, 20));
//        inputLeaveType.setEnabled(false);

        lbSettlteMentWay.setText("Settlement Way");
        lbSettlteMentWay.setBounds(new Rectangle(250, 50, 93, 20));
        inputSettlteMentWay.setBounds(new Rectangle(340, 50, 120, 20));
        inputSettlteMentWay.setEnabled(false);

        lbUsableHours.setText("Usable Hours");
        lbUsableHours.setBounds(new Rectangle(20, 80, 93, 20));
        inputUsableHours.setBounds(new Rectangle(100, 80, 120, 20));
        inputUsableHours.setEnabled(false);
        inputUsableHours.setHorizontalAlignment(JTextField.RIGHT);

        lbMaxHours.setText("Max Hours");
        lbMaxHours.setBounds(new Rectangle(250, 80, 93, 20));
        inputMaxHours.setBounds(new Rectangle(340, 80, 120, 20));
        inputMaxHours.setEnabled(false);
        inputMaxHours.setMaxInputIntegerDigit(8);
        inputMaxHours.setMaxInputDecimalDigit(1);

        inputTotalHours.setMaxInputIntegerDigit(8);
        inputTotalHours.setMaxInputDecimalDigit(2);

        lbDataFrom.setText("Date:From");
        lbDataFrom.setBounds(new Rectangle(20, 110, 70, 20));
        inputDateFrom.setBounds(new Rectangle(100, 110, 120, 20));
        inputDateFrom.setCanSelect(true);

        lbDateTo.setText("To");
        lbDateTo.setBounds(new Rectangle(250, 110, 70, 20));
        inputDateTo.setBounds(new Rectangle(340, 110, 120, 20));
        inputDateTo.setCanSelect(true);

        lbTimeFrom.setText("Time:From");
        lbTimeFrom.setBounds(new Rectangle(20, 140, 70, 20));
        inputTimeFrom.setBounds(new Rectangle(100, 140, 120, 20));
//        inputTimeFrom.setCanSelect(true);
        inputTimeFrom.setDataType(VWJDate._DATA_PRO_HM);

        lbTimeTo.setText("To");
        lbTimeTo.setBounds(new Rectangle(250, 140, 30, 20));
        inputTimeTo.setBounds(new Rectangle(340, 140, 120, 20));
//        inputTimeTo.setCanSelect(true);
        inputTimeTo.setDataType(VWJDate._DATA_PRO_HM);

        lbTotalHours.setText("Total Hours");
        lbTotalHours.setBounds(new Rectangle(20, 170, 80, 20));
        inputTotalHours.setBounds(new Rectangle(100, 170, 120, 20));
        inputTotalHours.setEnabled(false);
        inputTotalHours.setMaxInputIntegerDigit(8);
        inputTotalHours.setMaxInputDecimalDigit(2);
        lbHours.setText("(Hours)");
        lbHours.setBounds(new Rectangle(250, 170, 50, 20));

        btCaculate.setBounds(new Rectangle(340, 170, 60, 20));
        btCaculate.setText("Calculate");

        lbCause.setText("Cause");
        lbCause.setBounds(new Rectangle(20, 210, 60, 20));
        inputCause.setBounds(new Rectangle(100, 210, 360, 45));

        TitledBorder titledBorder1 = new TitledBorder(BorderFactory.
                                                      createEtchedBorder(Color.white,
                new Color(164, 163, 165)), "Detail");
        vwLeaveDetailList.setBounds(20, 265, 500, 180);
        vwLeaveDetailList.setBorder(titledBorder1);

        this.add(vwLeaveDetailList);
        this.add(lbLoginId);
        this.add(inputLoginId);
        this.add(lbDataFrom);
        this.add(inputDateFrom);
        this.add(lbDateTo);
        this.add(inputDateTo);
        this.add(lbTimeFrom);
        this.add(inputTimeFrom);
        this.add(lbTimeTo);
        this.add(inputTimeTo);
        this.add(lbTotalHours);
        this.add(btCaculate);
        this.add(lbHours);
        this.add(inputTotalHours);
        this.add(inputDept);
        this.add(lbDept);
        this.add(lbCause);
        this.add(inputCause);
        this.add(lbLeaveType);
        this.add(inputLeaveType);
        this.add(lbSettlteMentWay);
        this.add(inputSettlteMentWay);
        this.add(lbUsableHours);
        this.add(inputUsableHours);
        this.add(lbMaxHours);
        this.add(inputMaxHours);
    }

    VWJLabel lbLoginId = new VWJLabel();
    VWJText inputLoginId = new VWJLoginId();
    VWJLabel lbDept = new VWJLabel();
    VWJText inputDept = new VWJText();

    VWJLabel lbDataFrom = new VWJLabel();
    VWJDate inputDateFrom = new VWJDate();
    VWJLabel lbDateTo = new VWJLabel();
    VWJDate inputDateTo = new VWJDate();
    VWJLabel lbTimeFrom = new VWJLabel();
    VWJDate inputTimeFrom = new VWJDate();
    VWJLabel lbTimeTo = new VWJLabel();
    VWJDate inputTimeTo = new VWJDate();
    VWJLabel lbTotalHours = new VWJLabel();
    VWJReal inputTotalHours = new VWJReal();
    VWJButton btCaculate = new VWJButton();
    VWJLabel lbHours = new VWJLabel();
    VWJLabel lbCause = new VWJLabel();
    VWJTextArea inputCause = new VWJTextArea();
    VWJLabel lbLeaveType = new VWJLabel();
    VWJComboBox inputLeaveType = new VWJComboBox();
    VWJLabel lbSettlteMentWay = new VWJLabel();
    VWJText inputSettlteMentWay = new VWJText();
    VWJLabel lbUsableHours = new VWJLabel();
    VWJText inputUsableHours = new VWJText();
    VWJLabel lbMaxHours = new VWJLabel();
    VWJReal inputMaxHours = new VWJReal();

    VwLeaveDetailList vwLeaveDetailList = new VwLeaveDetailList();
}
