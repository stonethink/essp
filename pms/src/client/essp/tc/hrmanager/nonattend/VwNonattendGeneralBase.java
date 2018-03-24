package client.essp.tc.hrmanager.nonattend;

import client.essp.common.view.VWGeneralWorkArea;
import java.awt.*;
import client.framework.view.vwcomp.VWJLabel;
import client.framework.view.vwcomp.VWJText;
import client.framework.view.vwcomp.VWJTextArea;
import client.framework.view.vwcomp.VWJButton;
import java.util.List;
import java.util.ArrayList;
import client.framework.view.common.comFORM;
import client.framework.view.vwcomp.VWJDate;
import client.framework.view.vwcomp.VWJReal;
import client.essp.common.loginId.VWJLoginId;

public class VwNonattendGeneralBase extends VWGeneralWorkArea {
    public VwNonattendGeneralBase() {
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
        inputDate.setName("date");
        inputTimeFrom.setName("timeFrom");
        inputTimeTo.setName("timeTo");
        inputTotalHours.setName("totalHours");
        inputRemark.setName("remark");
    }
    private void setTabOrder(){
        List compList = new ArrayList();
        compList.add(inputLoginId);
        compList.add(inputDate);
        compList.add(inputTimeFrom);
        compList.add(inputTimeTo);
        compList.add(inputRemark);
        compList.add(inputRemark);
        comFORM.setTABOrder(this, compList);

    }
    private void setEnterOrder(){
        List compList = new ArrayList();
        compList.add(inputLoginId);
        compList.add(inputDate);
        compList.add(inputTimeFrom);
        compList.add(inputTimeTo);
        compList.add(inputRemark);
        compList.add(inputRemark);
        comFORM.setEnterOrder(this, compList);

    }
    public void jbInit() throws Exception {
        this.setLayout(null);
        setPreferredSize(new Dimension(500,300));

        worker.setText("Worker");
        worker.setBounds(new Rectangle(36, 38, 56, 20));

        inputLoginId.setText("");
        inputLoginId.setBounds(new Rectangle(138, 36, 123, 20));
        inputLoginId.setEnabled(false);
        timeFrom.setText("Time(HHmm):From");
        timeFrom.setBounds(new Rectangle(37, 85, 95, 20));
        inputTimeFrom.setText("");
        inputTimeFrom.setBounds(new Rectangle(138, 86, 102, 20));
//        inputTimeFrom.setCanSelect(true);
        inputTimeFrom.setDataType(VWJDate._DATA_PRO_HM);
        timeTo.setText("To");
        timeTo.setBounds(new Rectangle(281, 85, 33, 20));
        inputTimeTo.setText("");
        inputTimeTo.setBounds(new Rectangle(317, 86, 102, 20));
//        inputTimeTo.setCanSelect(true);
        inputTimeTo.setDataType(VWJDate._DATA_PRO_HM);
        hours.setText("Hours");
        hours.setBounds(new Rectangle(38, 130, 42, 20));
        inputTotalHours.setText("");
        inputTotalHours.setEnabled(false);
        inputTotalHours.setMaxInputIntegerDigit(8);
        inputTotalHours.setMaxInputDecimalDigit(1);
        inputTotalHours.setBounds(new Rectangle(137, 136, 102, 20));
        calculate.setBounds(new Rectangle(325, 135, 91, 20));
        calculate.setText("calculate");
        remark.setText("Remark");
        remark.setBounds(new Rectangle(37, 189, 42, 20));
        inputRemark.setText("");
        inputRemark.setBounds(new Rectangle(138, 176, 280, 40));
        Date.setText("Date");
        Date.setBounds(new Rectangle(281, 37, 26, 20));
        inputDate.setBounds(new Rectangle(318, 38, 102, 20));
        inputDate.setCanSelect(true);
        inputDate.setDataType(VWJDate._DATA_PRO_YMD);
        this.add(inputLoginId);
        this.add(inputTimeFrom);
        this.add(inputRemark);
        this.add(inputTotalHours);
        this.add(timeFrom);
        this.add(hours);
        this.add(remark);
        this.add(worker);
        this.add(calculate);
        this.add(inputDate);
        this.add(timeTo);
        this.add(Date);
        this.add(inputTimeTo);
    }

    VWJLabel worker = new VWJLabel();
    VWJText inputLoginId = new VWJLoginId();
    VWJLabel timeFrom = new VWJLabel();
    VWJDate inputTimeFrom = new VWJDate();
    VWJLabel timeTo = new VWJLabel();
    VWJDate inputTimeTo = new VWJDate();
    VWJLabel hours = new VWJLabel();
    VWJReal inputTotalHours = new VWJReal();
    VWJButton calculate = new VWJButton();
    VWJLabel remark = new VWJLabel();
    VWJTextArea inputRemark = new VWJTextArea();
    VWJLabel Date = new VWJLabel();
    VWJDate inputDate = new VWJDate();
}
