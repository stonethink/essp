package client.essp.pms.quality.activity;

import client.essp.common.view.VWGeneralWorkArea;
import java.awt.SystemColor;
import java.awt.*;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;
import client.framework.view.vwcomp.VWJComboBox;
import client.framework.model.VMComboBox;
import c2s.essp.pms.quality.activity.DtoQualityActivity;
import client.framework.view.vwcomp.VWJText;
import client.framework.view.vwcomp.VWJTextArea;
import com.wits.util.Parameter;
import client.framework.view.vwcomp.VWJReal;
import client.framework.view.vwcomp.IVWPopupEditorEvent;
import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;
import c2s.dto.ReturnInfo;
import c2s.dto.InputInfo;
import client.essp.common.view.VWTableWorkArea;
import client.framework.common.Constant;
import client.framework.view.common.comMSG;
import client.framework.view.vwcomp.VWUtil;

public class VWGeneral extends VWGeneralWorkArea implements IVWPopupEditorEvent {


    private DtoQualityActivity dtoQualityActivity;
    private Double actualSize;
    private Double actualDefectNum = new Double(0);
    private Double actualCaseNum = new Double(0);
    private final String ACTIONID_GENERAL_SAVE = "FQuGeneralSaveAction";
    VWTableWorkArea workArea;

    public VWGeneral() {
        super();
        this.dtoQualityActivity = dtoQualityActivity;
        try {
            jbInit();
            addUIEvent();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private void addUIEvent() {

        this.ActualSize.addFocusListener(new FocusListener() {

            public void focusGained(FocusEvent e) {
            }

            public void focusLost(FocusEvent e) {

                if (ActualCaseNum.getUICValue() != null) {
                    actualCaseNum = new Double(ActualCaseNum.getUICValue().toString());
                }
                if (ActualDefectNum.getUICValue() != null
                    ) {
                    actualDefectNum = new Double(ActualDefectNum.getUICValue().toString());
                }

                if (ActualSize.getUICValue() != null) {
                    actualSize = new Double(ActualSize.getUICValue().toString());
                    if (actualSize.doubleValue() != 0.0) {
                        Double actualDefectRate = new Double(actualDefectNum.
                            doubleValue() / actualSize.doubleValue());

                        ActualDefectRate.setUICValue(actualDefectRate);

                        Double actualDensity = new Double(actualCaseNum.
                            doubleValue() /
                            actualSize.doubleValue());
                        ActualDensity.setUICValue(actualDensity);
                    }
                }
            }
        });

        this.ActualCaseNum.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
            }

            public void focusLost(FocusEvent e) {

                if (ActualSize.getUICValue() != null) {
                    actualSize = new Double(ActualSize.getUICValue().toString());
                }
                if (ActualCaseNum.getUICValue() != null) {
                    actualCaseNum = new Double(ActualCaseNum.getUICValue().toString());
                }
                if (ActualDefectNum.getUICValue() != null
                    ) {
                    actualDefectNum = new Double(ActualDefectNum.getUICValue().toString());
                }
                if (actualSize.doubleValue() != 0.0) {
                    Double actualDensity = new Double(actualCaseNum.doubleValue() /
                        actualSize.doubleValue());
                    ActualDensity.setUICValue(actualDensity);
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

    public void setEnable(boolean flag) {
        VWUtil.clearUI(this);
        this.getButtonPanel().setVisible(flag);
        this.typeComboBox.setEnabled(flag);
        this.Production.setEnabled(flag);
        this.Method.setEnabled(flag);
        this.Criterion.setEnabled(flag);
        this.Remark.setEnabled(flag);
        this.PlannedSize.setEnabled(flag);
        this.ActualSize.setEnabled(flag);
        this.ActualCaseNum.setEnabled(flag);
    }


    public void jbInit() throws Exception {
        this.setLayout(null);
//           this.setBackground(SystemColor.menu);

        QualityActivity.setText("");
        QualityActivity.setBounds(new Rectangle(120, 20, 190, 20));
        QualityActivity.setEnabled(false);

        jLabel1.setText("Quality Activity");
        jLabel1.setBounds(new Rectangle(20, 20, 100, 20));

        typeComboBox.setBounds(new Rectangle(420, 20, 200, 20));

        jLabel2.setText("Type");
        jLabel2.setBounds(new Rectangle(390, 20, 100, 20));

        Production.setText("");
        Production.setBounds(new Rectangle(120, 50, 500, 20));

        jLabel3.setText("Production");
        jLabel3.setBounds(new Rectangle(55, 50, 100, 20));

        Method.setText("");
        Method.setBounds(new Rectangle(120, 80, 190, 20));

        jLabel4.setText("Method");
        jLabel4.setBounds(new Rectangle(80, 80, 100, 20));

        Criterion.setText("");
        Criterion.setBounds(new Rectangle(420, 80, 200, 20));

        jLabel5.setText(" Criterion");
        jLabel5.setBounds(new Rectangle(355, 80, 100, 20));

        DensityUnits.setText("");
        DensityUnits.setBounds(new Rectangle(120, 110, 190, 20));
        DensityUnits.setEnabled(false);

        jLabel6.setText("Density Units");
        jLabel6.setBounds(new Rectangle(40, 110, 100, 20));

        DefectRateUnits.setText("");
        DefectRateUnits.setBounds(new Rectangle(420, 110, 200, 20));
        DefectRateUnits.setEnabled(false);

        jLabel7.setText("Defect Rate Units");
        jLabel7.setBounds(new Rectangle(315, 110, 105, 20));
        Remark.setBorder(BorderFactory.createEtchedBorder());
        Remark.setText("");
        Remark.setOffset(2);
        Remark.setBounds(new Rectangle(120, 140, 500, 40));

        jLabel8.setText("Remark");
        jLabel8.setBounds(new Rectangle(80, 150, 100, 20));

        jLabel9.setText("Planned Size");
        jLabel9.setBounds(new Rectangle(45, 190, 100, 20));

        jLabel10.setText("Actual Size");
        jLabel10.setBounds(new Rectangle(50, 220, 100, 20));

        PlannedSize.setText("");
        PlannedSize.setBounds(new Rectangle(120, 190, 90, 20));
        PlannedSize.setMaxInputDecimalDigit(2);
        PlannedSize.setMaxInputIntegerDigit(8);

        ActualSize.setText("");
        ActualSize.setBounds(new Rectangle(120, 220, 90, 20));
        ActualSize.setMaxInputDecimalDigit(2);
        ActualSize.setMaxInputIntegerDigit(8);

        jLabel11.setText("Planned Density");
        jLabel11.setBounds(new Rectangle(220, 190, 100, 20));

        jLabel12.setText("Actual Density");
        jLabel12.setBounds(new Rectangle(225, 220, 100, 20));

        jLabel13.setText("Actual Case Num");
        jLabel13.setBounds(new Rectangle(220, 250, 100, 20));

        PlannedDensity.setText("");
        PlannedDensity.setBounds(new Rectangle(315, 190, 100, 20));
        PlannedDensity.setMaxInputDecimalDigit(2);
        PlannedDensity.setMaxInputIntegerDigit(8);
        PlannedDensity.setEnabled(false);

        ActualDensity.setText("");
        ActualDensity.setBounds(new Rectangle(315, 220, 100, 20));
        ActualDensity.setMaxInputDecimalDigit(2);
        ActualDensity.setMaxInputIntegerDigit(8);
        ActualDensity.setEnabled(false);

        ActualCaseNum.setText("");
        ActualCaseNum.setBounds(new Rectangle(315, 250, 100, 20));
        ActualCaseNum.setMaxInputDecimalDigit(0);

        jLabel14.setText("Planned Defect Rate");
        jLabel14.setBounds(new Rectangle(420, 190, 115, 20));

        jLabel15.setText("Actual Defect Rate");
        jLabel15.setBounds(new Rectangle(425, 220, 110, 20));

        jLabel16.setText("Actual Defect Num");
        jLabel16.setBounds(new Rectangle(430, 250, 110, 20));

        PlannedDefectRate.setText("");
        PlannedDefectRate.setBounds(new Rectangle(535, 190, 85, 20));
        PlannedDefectRate.setMaxInputDecimalDigit(2);
        PlannedDefectRate.setMaxInputIntegerDigit(8);
        PlannedDefectRate.setEnabled(false);

        ActualDefectRate.setText("");
        ActualDefectRate.setBounds(new Rectangle(535, 220, 85, 20));
        ActualDefectRate.setMaxInputDecimalDigit(2);
        ActualDefectRate.setMaxInputIntegerDigit(8);
        ActualDefectRate.setEnabled(false);

        ActualDefectNum.setText("");
        ActualDefectNum.setBounds(new Rectangle(535, 250, 85, 20));
        ActualDefectNum.setMaxInputDecimalDigit(0);
        ActualDefectNum.setEnabled(false);

        this.add(PlannedDefectRate);
        this.add(ActualDefectRate);
        this.add(ActualDefectNum);

        this.add(jLabel14);
        this.add(jLabel15);
        this.add(jLabel16);

        this.add(PlannedDensity);
        this.add(ActualDensity);
        this.add(ActualCaseNum);

        this.add(jLabel11);
        this.add(jLabel12);
        this.add(jLabel13);

        this.add(PlannedSize);
        this.add(ActualSize);

        this.add(jLabel9);
        this.add(jLabel10);

        this.add(jLabel8);
        this.add(Remark);

        this.add(jLabel7);
        this.add(DefectRateUnits);

        this.add(jLabel6);
        this.add(DensityUnits);

        this.add(jLabel5);
        this.add(Criterion);

        this.add(jLabel4);
        this.add(Method);

        this.add(jLabel3);
        this.add(Production);

        this.add(jLabel2);
        this.add(typeComboBox);

        this.add(jLabel1);
        this.add(QualityActivity);

        String[] typeName = new String[] {"RD Review", "AD Review", "HD Review",
                            "DD Review", "UT", "WT", "IT", "RT", "AT"};
        String[] typeValue = new String[] {"RD Review", "AD Review",
                             "HD Review", "DD Review", "UT", "WT", "IT", "RT",
                             "AT"};

        typeComboBox.setVMComboBox(VMComboBox.toVMComboBox(typeName, typeValue));
//       sexComboBox.setUICValue("\u00C4\u00D0");


    }

    public void setTextInfo(DtoQualityActivity qa) {
        this.ActualCaseNum.setUICValue(qa.getActualCaseNum());

    }

    public void setParameter(Parameter param) {
        super.setParameter(param);
        dtoQualityActivity = (DtoQualityActivity) param.get("qaParameter");
        workArea = (VWTableWorkArea) param.get("List");
//        resetUI();
    }

    public void resetUI() {
        if (dtoQualityActivity != null) {
            this.QualityActivity.setUICValue(dtoQualityActivity.getName());
            this.DefectRateUnits.setUICValue(dtoQualityActivity.
                                             getDefectRateUnits());
            this.DensityUnits.setUICValue(dtoQualityActivity.getDensityUnits());

            this.Method.setUICValue(dtoQualityActivity.getMethod());

            this.PlannedSize.setUICValue(dtoQualityActivity.getPlanSize());
            this.ActualSize.setUICValue(dtoQualityActivity.getActualSize());
            if (this.ActualSize.getUICValue() != null) {
                actualSize = new Double(this.ActualSize.getUICValue().toString());
            }

            this.PlannedDensity.setUICValue(dtoQualityActivity.getPlanDensity());
            this.PlannedDefectRate.setUICValue(dtoQualityActivity.getPlanDefectRate());
            this.typeComboBox.setUICValue(dtoQualityActivity.getType());
            this.Production.setUICValue(dtoQualityActivity.getProduction());
            this.Remark.setUICValue(dtoQualityActivity.getRemark());
            this.Criterion.setUICValue(dtoQualityActivity.getCriterion());
            this.ActualCaseNum.setUICValue(dtoQualityActivity.
                getActualCaseNum());

            if (this.ActualCaseNum.getUICValue() != null) {
                actualCaseNum = new Double(this.ActualCaseNum.getUICValue().toString());
            }
            this.ActualDefectNum.setUICValue(dtoQualityActivity.
                getActualDefectNum());

            if ((String)this.ActualDefectNum.
                getUICValue().
                toString() != null &&
                actualSize.doubleValue() != 0.0) {

                actualDefectNum = new Double(this.ActualDefectNum.getUICValue().toString());

                Double actualDefectRate = new Double(actualDefectNum.
                    doubleValue() / actualSize.doubleValue());

                this.ActualDefectRate.setUICValue(actualDefectRate);

                Double actualDensity = new Double(actualCaseNum.doubleValue() /
                                                  actualSize.doubleValue());
                this.ActualDensity.setUICValue(actualDensity);
            }
        }
    }

    public void actionPerformedRefresh(ActionEvent e) {
        resetUI();
    }

    public void actionPerformedSave(ActionEvent e) {
//        int f = comMSG.dispConfirmDialog("Confirm to save the record!");
//        if (f == Constant.OK) {
        dtoQualityActivity.setType((String)typeComboBox.getUICValue());
        dtoQualityActivity.setProduction((String)Production.
                                         getUICValue());
        dtoQualityActivity.setMethod((String)Method.getUICValue());
        dtoQualityActivity.setCriterion((String)Criterion.getUICValue());
        dtoQualityActivity.setRemark((String)Remark.getUICValue());
        dtoQualityActivity.setPlanSize(new Double(PlannedSize.getUICValue().toString()));
        dtoQualityActivity.setActualSize(new Double(ActualSize.getUICValue().toString()));
        dtoQualityActivity.setActualCaseNum(new Long(ActualCaseNum.getUICValue().toString()));
        dtoQualityActivity.setActualDensity(new Double(ActualDensity.getUICValue().toString()));
        dtoQualityActivity.setActualDefectRate(new Double(ActualDefectRate.getUICValue().toString()));
        dtoQualityActivity.setActualDefectNum(new Long(ActualDefectNum.getUICValue().toString()));

        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.ACTIONID_GENERAL_SAVE);
        inputInfo.setInputObj("dtoQualityActivity",
                              dtoQualityActivity);
        ReturnInfo returnInfo = accessData(inputInfo);

        if (returnInfo.isError() == false) {
            workArea.getTable().repaint();
//             fireDataChanged();

//            }
        }
    }


    VWJReal PlannedDefectRate = new VWJReal();
    VWJReal ActualDefectRate = new VWJReal();
    VWJReal ActualDefectNum = new VWJReal();

    JLabel jLabel16 = new JLabel();
    JLabel jLabel15 = new JLabel();
    JLabel jLabel14 = new JLabel();

    VWJReal PlannedDensity = new VWJReal();
    VWJReal ActualDensity = new VWJReal();
    VWJReal ActualCaseNum = new VWJReal();

    JLabel jLabel13 = new JLabel();
    JLabel jLabel12 = new JLabel();
    JLabel jLabel11 = new JLabel();

    VWJReal PlannedSize = new VWJReal();
    VWJReal ActualSize = new VWJReal();

    JLabel jLabel10 = new JLabel();
    JLabel jLabel9 = new JLabel();

    VWJTextArea Remark = new VWJTextArea();
//    VWJText   Remark = new VWJText();
    JLabel jLabel8 = new JLabel();

    VWJText DefectRateUnits = new VWJText();
    JLabel jLabel7 = new JLabel();

    VWJText DensityUnits = new VWJText();
    JLabel jLabel6 = new JLabel();


    VWJText Criterion = new VWJText();
    JLabel jLabel5 = new JLabel();

    VWJText Method = new VWJText();
    JLabel jLabel4 = new JLabel();

    VWJText Production = new VWJText();
    JLabel jLabel3 = new JLabel();

    VWJComboBox typeComboBox = new VWJComboBox();
    JLabel jLabel2 = new JLabel();

    VWJText QualityActivity = new VWJText();
    JLabel jLabel1 = new JLabel();


    public static void main(String[] args) {

//          Global.todayDateStr = "2005-12-03";
//          Global.todayDatePattern = "yyyy-MM-dd";
//          Global.userId = "stone.shi";
//          DtoUser user = new DtoUser();
//          user.setUserID(Global.userId);
//          user.setUserLoginId(Global.userId);
//          HttpServletRequest request = new HttpServletRequestMock();
//          HttpSession session = request.getSession();
//          session.setAttribute(DtoUser.SESSION_USER, user);

//        VWQualityActivityList vwqualityactivitylist = new VWQualityActivityList();
//        VWWorkArea w1 = new VWWorkArea();
//        VWGeneral resource = new VWGeneral();
//        w1.addTab("Genaral", resource);
//        TestPanel.show(w1);
//         resource.resetUI();

    }

    public boolean onClickOK(ActionEvent e) {
        return false;
    }

    public boolean onClickCancel(ActionEvent e) {
        return false;
    }

}
