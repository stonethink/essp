package client.essp.tc.hrmanager.attendance;

import client.essp.common.view.VWGeneralWorkArea;
import java.awt.*;
import client.framework.view.vwcomp.VWJLabel;
import client.framework.view.vwcomp.VWJText;
import client.framework.view.vwcomp.VWJDate;
import client.framework.view.vwcomp.VWJTextArea;
import java.util.List;
import java.util.ArrayList;
import client.framework.view.common.comFORM;
import client.framework.view.vwcomp.VWJComboBox;
import client.essp.common.loginId.VWJLoginId;



public class VwAttendanceGeneralBase extends VWGeneralWorkArea {
    public VwAttendanceGeneralBase() {
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
       inputAttendanceType.setName("attendanceType");
       inputAttendanceDate.setName("attendanceDate");
       inputRemark.setName("remark");

   }

   private void setTabOrder(){
       List compList = new ArrayList();
       compList.add(inputLoginId);
       compList.add(inputAttendanceType);
       compList.add(inputAttendanceDate);
       compList.add(inputRemark);
       comFORM.setTABOrder(this, compList);
   }

   private void setEnterOrder(){
       List compList = new ArrayList();
       compList.add(inputLoginId);
       compList.add(inputAttendanceType);
       compList.add(inputAttendanceDate);
       compList.add(inputRemark);

        comFORM.setEnterOrder(this, compList);
    }

    public void jbInit() throws Exception {
        this.setLayout(null);
        setPreferredSize(new Dimension(480,200));
        Worker.setText("Worker");
        Worker.setBounds(new Rectangle(39, 33, 53, 20));

        inputLoginId.setBounds(new Rectangle(119, 33, 240, 20));
        inputLoginId.setEnabled(false);
        attendanceType.setText("Type");
        attendanceType.setBounds(new Rectangle(41, 77, 63, 20));
        inputAttendanceType.setBounds(new Rectangle(119, 78, 109, 20));
        attendanceDate.setText("Date");
        attendanceDate.setBounds(new Rectangle(240, 75, 36, 20));
        inputAttendanceDate.setCanSelect(true);
        inputAttendanceDate.setBounds(new Rectangle(274, 77, 83, 20));
        Remark.setText("Remark");
        Remark.setBounds(new Rectangle(42, 138, 51, 20));
        inputRemark.setBounds(new Rectangle(119, 130, 240, 40));
        this.add(inputRemark);
        this.add(inputLoginId);
        this.add(inputAttendanceType);
        this.add(inputAttendanceDate);
        this.add(attendanceDate);
        this.add(Worker);
        this.add(attendanceType);
        this.add(Remark);
    }

    VWJLabel Worker = new VWJLabel();
    VWJText inputLoginId = new VWJLoginId();
    VWJLabel attendanceType = new VWJLabel();
    VWJComboBox inputAttendanceType = new VWJComboBox();
    VWJLabel attendanceDate = new VWJLabel();
    VWJDate inputAttendanceDate = new VWJDate();
    VWJLabel Remark = new VWJLabel();
    VWJTextArea inputRemark = new VWJTextArea();

}


