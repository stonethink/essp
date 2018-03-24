package client.essp.tc.hrmanager.overtime;

import java.awt.Rectangle;

import client.essp.common.view.VWGeneralWorkArea;
import client.framework.view.vwcomp.VWJButton;
import client.framework.view.vwcomp.VWJCheckBox;
import client.framework.view.vwcomp.VWJComboBox;
import client.framework.view.vwcomp.VWJDate;
import client.framework.view.vwcomp.VWJLabel;
import client.framework.view.vwcomp.VWJNumber;
import client.framework.view.vwcomp.VWJText;
import java.awt.*;
import client.framework.view.vwcomp.VWJTextArea;
import java.util.List;
import java.util.ArrayList;
import client.framework.view.common.comFORM;
import javax.swing.border.TitledBorder;
import javax.swing.BorderFactory;
import client.essp.common.loginId.VWJLoginId;

public class VwOverTimeGeneralBase extends VWGeneralWorkArea {
    public VwOverTimeGeneralBase() {
        try {
            setPreferredSize(new Dimension(600,400));
            jbInit();
            setUIComponentName();
            setTabOrder();
            setEnterOrder();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * setUIComponentName
     */
    private void setUIComponentName() {
        inputLoginId.setName("loginId");
        inputAccount.setName("acntRid");
        inputDateFrom.setName("actualDateFrom");
        inputDateTo.setName("actualDateTo");
//        inputTimeFrom.setName("actualTimeFrom");
//        inputTimeTo.setName("actualTimeTo");
        inputEachDay.setName("actualIsEachDay");
        inputTotalHours.setName("actualTotalHours");
        inputCause.setName("cause");
    }

    /**
     * setTabOrder
     */
    private void setTabOrder() {
        List compList = new ArrayList();
        compList.add(inputLoginId);
        compList.add(inputAccount);
        compList.add(inputDateFrom);
        compList.add(inputDateTo);
        compList.add(inputTimeFrom);
        compList.add(inputTimeTo);
        compList.add(inputEachDay);
        compList.add(inputTotalHours);
        compList.add(inputCause);
        comFORM.setTABOrder(this, compList);
    }

    /**
     * setEnterOrder
     */
    private void setEnterOrder() {
        List compList = new ArrayList();
        compList.add(inputLoginId);
        compList.add(inputAccount);
        compList.add(inputDateFrom);
        compList.add(inputDateTo);
        compList.add(inputTimeFrom);
        compList.add(inputTimeTo);
        compList.add(inputEachDay);
        compList.add(inputTotalHours);
        compList.add(inputCause);
        comFORM.setTABOrder(this, compList);
    }

    public void jbInit() throws Exception {
        this.setLayout(null);

        lbLoginId.setText("Worker");
        lbLoginId.setBounds(new Rectangle(20, 20, 70, 20));
        inputLoginId.setBounds(new Rectangle(100, 20, 110, 20));
        inputLoginId.setEnabled(false);

        lbAccount.setText("Account");
        lbAccount.setBounds(new Rectangle(250, 20, 70, 20));
        inputAccount.setBounds(new Rectangle(300, 20, 175, 20));

        lbDataFrom.setText("Date:From");
        lbDataFrom.setBounds(new Rectangle(20, 50, 70, 20));
        inputDateFrom.setBounds(new Rectangle(100, 50, 110, 20));
        inputDateFrom.setCanSelect(true);

        lbDateTo.setText("To");
        lbDateTo.setBounds(new Rectangle(250, 50, 70, 20));
        inputDateTo.setBounds(new Rectangle(300, 50, 110, 20));
        inputDateTo.setCanSelect(true);

        lbEachDay.setText("Each Day");
        lbEachDay.setBounds(new Rectangle(410, 50, 50, 20));
        inputEachDay.setBounds(new Rectangle(460, 50, 15, 15));

        lbTimeFrom.setText("Time:From");
        lbTimeFrom.setBounds(new Rectangle(20, 80, 70, 20));
        inputTimeFrom.setBounds(new Rectangle(100, 80, 110, 20));
//        inputTimeFrom.setCanSelect(true);
        inputTimeFrom.setDataType(VWJDate._DATA_PRO_HM);

        lbTimeTo.setText("To");
        lbTimeTo.setBounds(new Rectangle(250, 80, 30, 20));
        inputTimeTo.setBounds(new Rectangle(300, 80, 110, 20));
//        inputTimeTo.setCanSelect(true);
        inputTimeTo.setDataType(VWJDate._DATA_PRO_HM);

        lbTotalHours.setText("Total Hours");
        lbTotalHours.setBounds(new Rectangle(20, 110, 80, 20));
        inputTotalHours.setBounds(new Rectangle(100, 110, 110, 20));
        inputTotalHours.setEnabled(false);
        lbHours.setText("(Hours)");
        lbHours.setBounds(new Rectangle(250, 110, 50, 20));

        btCaculate.setBounds(new Rectangle(300, 110, 60, 20));
        btCaculate.setText("Calculate");

        lbCause.setText("Cause");
        lbCause.setBounds(new Rectangle(20, 140, 60, 20));
        inputCause.setBounds(new Rectangle(100, 140, 375, 45));

        TitledBorder titledBorder1 = new TitledBorder(BorderFactory.
                                                      createEtchedBorder(Color.white,
                new Color(164, 163, 165)), "Detail");
        vwOverTimeDetailList.setBounds(20, 200, 550, 180);
        vwOverTimeDetailList.setBorder(titledBorder1);
        this.add(vwOverTimeDetailList);
        this.add(lbLoginId);
        this.add(inputLoginId);
        this.add(lbDataFrom);
        this.add(inputDateFrom);
        this.add(lbDateTo);
        this.add(inputDateTo);
        this.add(lbEachDay);
        this.add(inputEachDay);
        this.add(lbTimeFrom);
        this.add(inputTimeFrom);
        this.add(lbTimeTo);
        this.add(inputTimeTo);
        this.add(lbTotalHours);
        this.add(btCaculate);
        this.add(lbHours);
        this.add(inputTotalHours);
        this.add(inputAccount);
        this.add(lbAccount);
        this.add(lbCause);
        this.add(inputCause);
    }

    VWJLabel lbLoginId = new VWJLabel();
    VWJText inputLoginId = new VWJLoginId();
    VWJLabel lbAccount = new VWJLabel();
    VWJComboBox inputAccount = new VWJComboBox();
    VWJLabel lbDataFrom = new VWJLabel();
    VWJDate inputDateFrom = new VWJDate();
    VWJLabel lbDateTo = new VWJLabel();
    VWJDate inputDateTo = new VWJDate();
    VWJLabel lbEachDay = new VWJLabel();
    VWJCheckBox inputEachDay = new VWJCheckBox();
    VWJLabel lbTimeFrom = new VWJLabel();
    VWJDate inputTimeFrom = new VWJDate();
    VWJLabel lbTimeTo = new VWJLabel();
    VWJDate inputTimeTo = new VWJDate();
    VWJLabel lbTotalHours = new VWJLabel();
    VWJNumber inputTotalHours = new VWJNumber();
    VWJButton btCaculate = new VWJButton();
    VWJLabel lbHours = new VWJLabel();
    VWJLabel lbCause = new VWJLabel();
    VWJTextArea inputCause = new VWJTextArea();

    VwOverTimeDetailList vwOverTimeDetailList = new VwOverTimeDetailList();
}
