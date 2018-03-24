package client.essp.pms.quality.activity;

import client.essp.common.view.VWGeneralWorkArea;
import java.awt.Color;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import c2s.essp.pms.quality.activity.DtoQualityActivity;
import com.wits.util.Parameter;
import c2s.dto.ReturnInfo;
import c2s.dto.InputInfo;
import client.framework.view.vwcomp.VWJLabel;
import client.framework.view.vwcomp.VWJRadioButton;
import javax.swing.ImageIcon;
import client.framework.view.vwcomp.VWJComboBox;
import client.framework.model.VMComboBox;
import java.awt.Rectangle;
import client.framework.view.vwcomp.VWUtil;
import client.framework.view.vwcomp.VWJTextArea;
import java.awt.event.*;


public class VWActivityQualityConclusion extends VWGeneralWorkArea {
    DtoQualityActivity dtoQualityActivity;
    public final static String ACTIONID_QUALITYACTIVITY_CONCLUSION =
        "FQualityActClnAction";
    private final static String ACTIONID_GENERAL_SAVE = "FQuGeneralSaveAction";

    private boolean refreshFlag = false;

    public VWActivityQualityConclusion() {
        try {
            jbInit();
            addUIEvent();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private void addUIEvent() {

        this.jRadioButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Conclusion.setSelectedIndex(0);
            }
        });
        this.jRadioButton4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Conclusion.setSelectedIndex(1);
            }
        });
        this.Conclusion.addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent e) {
                if(jRadioButton1.isSelected() && Conclusion.getSelectedIndex() == 1) {
                    jRadioButton2.setSelected(true);
                } else if(jRadioButton4.isSelected() && Conclusion.getSelectedIndex() == 0) {
                    jRadioButton3.setSelected(true);
                }
            }
        });

        this.getButtonPanel().addSaveButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedSave(e);
            }
        });

        this.getButtonPanel().addLoadButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedRefresh(e);
            }
        });

    }


    public void jbInit() throws Exception {
        this.setLayout(null);
        this.setBackground(Color.white);
        jLabel1.setText("Fulfill the quality criteria.");
        jLabel1.setBounds(new Rectangle(36, 10, 200, 20));
        jLabel2.setText("Require more test case.");
        jLabel2.setBounds(new Rectangle(36, 40, 200, 20));
        jLabel3.setText("Others, judged by PM.");
        jLabel3.setBounds(new Rectangle(36, 70, 200, 20));
        jLabel4.setText("Redo.");
        jLabel4.setBounds(new Rectangle(36, 100, 200, 20));
        jLabel5.setText("Conclusion");
        jLabel5.setBounds(new Rectangle(10, 130, 65, 20));
        jLabel6.setText("Reason");
        jLabel6.setBounds(new Rectangle(10, 160, 65, 20));
        Reason.setText("");

        String[] typeName = new String[] {"Pass", "Not Pass"};
        String[] typeValue = new String[] {"1", "2"};

        Conclusion.setVMComboBox(VMComboBox.toVMComboBox(typeName, typeValue));

        Conclusion.setBounds(new Rectangle(75, 130, 161, 18));

        Reason.setBounds(new Rectangle(75, 160, 161, 104));

        jRadioButton1.setText("jRadioButton1");
        jRadioButton1.setBounds(new Rectangle(12, 10, 20, 20));
        jRadioButton2.setText("jRadioButton2");
        jRadioButton2.setBounds(new Rectangle(12, 40, 20, 20));
        jRadioButton3.setText("jRadioButton3");
        jRadioButton3.setBounds(new Rectangle(12, 70, 20, 20));
        jRadioButton4.setText("jRadioButton4");
        jRadioButton4.setBounds(new Rectangle(12, 100, 20, 20));
        TestResultChart.setText("");
        TestResultChart.setBounds(new Rectangle(245, 2, 500, 272));
        this.add(jLabel5);
        this.add(jLabel6);
        this.add(Conclusion);
        this.add(Reason);
        this.add(jRadioButton4);
        this.add(jRadioButton1);
        this.add(jRadioButton2);
        this.add(jRadioButton3);
        this.add(jLabel2);
        this.add(jLabel4);
        this.add(jLabel3);
        this.add(jLabel1);
        this.add(TestResultChart);
        buttonGroup1.add(jRadioButton1);
        buttonGroup1.add(jRadioButton2);
        buttonGroup1.add(jRadioButton3);
        buttonGroup1.add(jRadioButton4);
    }

    VWJLabel jLabel1 = new VWJLabel();
    VWJLabel jLabel2 = new VWJLabel();
    VWJLabel jLabel3 = new VWJLabel();
    VWJLabel jLabel4 = new VWJLabel();
    VWJLabel TestResultChart = new VWJLabel();
//    VWJText Conclusion = new VWJText();
    VWJLabel jLabel6 = new VWJLabel();
    VWJTextArea Reason = new VWJTextArea();
    VWJRadioButton jRadioButton1 = new VWJRadioButton();
    VWJRadioButton jRadioButton2 = new VWJRadioButton();
    VWJRadioButton jRadioButton3 = new VWJRadioButton();
    VWJRadioButton jRadioButton4 = new VWJRadioButton();
    VWJLabel jLabel5 = new VWJLabel();
    ButtonGroup buttonGroup1 = new ButtonGroup();
    VWJComboBox Conclusion = new VWJComboBox();

    public void setParameter(Parameter param) {
        super.setParameter(param);
        dtoQualityActivity = (DtoQualityActivity) param.get("qaParameter");
//        resetUI();

    }

    public void resetUI() {
        if (dtoQualityActivity != null) {
            InputInfo inputInfo = new InputInfo();
            inputInfo.setActionId(ACTIONID_QUALITYACTIVITY_CONCLUSION);
            inputInfo.setInputObj("dto_for_image", dtoQualityActivity);
            ReturnInfo returnInfo = accessData(inputInfo);
            if (returnInfo.isError() == false) {
                Byte[] imageByte = (Byte[]) returnInfo.getReturnObj("imageByte");

                byte[] image = new byte[imageByte.length];
                for (int i = 0; i < imageByte.length; i++) {
                    image[i] = imageByte[i].byteValue();
                }
                ImageIcon icon = new ImageIcon(image);
                TestResultChart.setIcon(icon);
                String conclusion = dtoQualityActivity.getConclusion();
                if(conclusion == null || "".equals(conclusion)) {
                    jRadioButton2.setSelected(true);
                } else if (conclusion.equals("1")) {
                    jRadioButton1.setSelected(true);
                } else if (conclusion.equals("2")) {
                    jRadioButton2.setSelected(true);
                } else if (conclusion.equals("3")) {
                    jRadioButton3.setSelected(true);
                } else if (conclusion.equals("4")) {
                    jRadioButton4.setSelected(true);
                } else {
                    jRadioButton2.setSelected(true);
                }
                String isPass = dtoQualityActivity.getIsPass();
                if(isPass == null || "".equals(isPass)) {
                    isPass = "2";
                }
                Conclusion.setUICValue(isPass);
                Reason.setUICValue(dtoQualityActivity.getReason());

            }
        }
    }

    public void actionPerformedSave(ActionEvent e) {

//        int f = comMSG.dispConfirmDialog("Confirm to save the record!");
//        if (f == Constant.OK) {
        dtoQualityActivity.setReason((String) Reason.getUICValue());
        if (jRadioButton1.isSelected()) {
            dtoQualityActivity.setConclusion("1");
        }
        if (jRadioButton2.isSelected()) {
            dtoQualityActivity.setConclusion("2");
        }
        if (jRadioButton3.isSelected()) {
            dtoQualityActivity.setConclusion("3");
        }
        if (jRadioButton4.isSelected()) {
            dtoQualityActivity.setConclusion("4");
        }
        dtoQualityActivity.setIsPass((String) Conclusion.getUICValue());
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.ACTIONID_GENERAL_SAVE);
        inputInfo.setInputObj("dtoQualityActivity",
                              dtoQualityActivity);
        inputInfo.setInputObj("conclusionBy",
                              "conclusionBy");
        ReturnInfo returnInfo = accessData(inputInfo);

//        }
    }

    public void actionPerformedRefresh(ActionEvent e) {
        resetUI();
    }

    public void refreshWorkArea() {
        if (refreshFlag) {
            VWUtil.clearUI(this);
            resetUI();
            refreshFlag = false;
        } else {
            super.refreshWorkArea();
        }
    }

    public void fireNeedRefresh() {
        refreshFlag = true;
    }

    public static void main(String[] args) {
        VWActivityQualityConclusion vwconclusion = new
            VWActivityQualityConclusion();
    }
}
