package client.essp.pms.account.template.apply;

import client.essp.common.view.VWGeneralWorkArea;
import client.framework.view.vwcomp.VWJRadioButton;
import client.framework.view.vwcomp.VWJLabel;
import java.awt.Rectangle;
import client.framework.view.vwcomp.VWButtonGroup;
import client.framework.view.vwcomp.VWJComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import client.framework.model.VMComboBox;
import c2s.dto.ReturnInfo;
import c2s.dto.InputInfo;
import java.util.Vector;
import client.essp.common.view.VWWorkArea;
import com.wits.util.Parameter;
import com.wits.util.TestPanel;
import client.framework.view.vwcomp.IVWWizardEditorEvent;
import java.awt.Dimension;
import client.framework.view.vwcomp.VWJText;
import client.framework.common.Constant;
import client.framework.view.common.comMSG;
import client.framework.view.vwcomp.VWJTextArea;


public class VwTemplateApply extends VWGeneralWorkArea implements
    IVWWizardEditorEvent {
    final String actionId = "FAcntTemplateApplyAction";
    final String actionIdFin = "FAcntTemplateApplyFinAction";
    final String actionIdSave = "FAcntTemplateApplySaveAction";
    Long acntRid;
    String apprAcntRid;
    String acntType;
    Vector accountList;
    Vector templateList;
    private Parameter para;

    VWJRadioButton VWJRadioButtonStandard = new VWJRadioButton();
    VWJRadioButton VWJRadioButtonCurrent = new VWJRadioButton();
    VWJLabel lbChoose = new VWJLabel();
    VWJLabel lbStandard = new VWJLabel();
    VWJLabel lbCurrent = new VWJLabel();
    VWJLabel lbLastTemplate = new VWJLabel();
    VWJTextArea txLastTemplate = new VWJTextArea();
    VWButtonGroup buttonGroup1 = new VWButtonGroup();
    VWJComboBox comboBoxTemplate = new VWJComboBox();
    VWJComboBox comboBoxAccount = new VWJComboBox();
    public VwTemplateApply() {
        try {
            jbInit();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void jbInit() throws Exception {
        this.setLayout(null);
        this.setPreferredSize(new Dimension(600, 400));
        lbChoose.setText("Choose Template");
        lbChoose.setBounds(new Rectangle(36, 24, 179, 22));
        VWJRadioButtonStandard.setFocusPainted(false);
        VWJRadioButtonStandard.setText("VWJRadioButton1");
        VWJRadioButtonStandard.setBounds(new Rectangle(70, 58, 20, 18));
        VWJRadioButtonStandard.setSelected(true);
        VWJRadioButtonCurrent.setFocusPainted(false);
        VWJRadioButtonCurrent.setText("VWJRadioButton2");
        VWJRadioButtonCurrent.setBounds(new Rectangle(70, 88, 20, 18));
        lbStandard.setText("Standard Template");
        lbStandard.setBounds(new Rectangle(120, 58, 180, 15));
        lbCurrent.setText("Current Account");
        lbCurrent.setBounds(new Rectangle(120, 88, 180, 15));
        lbLastTemplate.setText("Last Applied Template");
        lbLastTemplate.setBounds(new Rectangle(36, 160, 192, 15));
        txLastTemplate.setText("");
        txLastTemplate.setBounds(new Rectangle(36, 188, 300, 30));
        comboBoxTemplate.setBounds(new Rectangle(300, 58, 183, 22));

        comboBoxAccount.setBounds(new Rectangle(300, 88, 183, 22));
        buttonGroup1.add(VWJRadioButtonStandard);
        buttonGroup1.add(VWJRadioButtonCurrent);
        this.add(VWJRadioButtonStandard);
        this.add(VWJRadioButtonCurrent);
        this.add(lbChoose);
        this.add(lbStandard);
        this.add(lbCurrent);
        this.add(lbLastTemplate);
        this.add(txLastTemplate);
        this.add(comboBoxTemplate);
        this.add(comboBoxAccount);
        comboBoxTemplate.setVisible(true);
        comboBoxAccount.setVisible(false);
        VWJRadioButtonStandard.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("VWJRadioButton1")) {
                    comboBoxTemplate.setVisible(true);
                    comboBoxAccount.setVisible(false);

                }
            }
        });
        VWJRadioButtonCurrent.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("VWJRadioButton2")) {
                    comboBoxAccount.setVisible(true);
                    comboBoxTemplate.setVisible(false);

                }
            }
        });
    }

    public void initAccount() {
//         if(comboBox2.getModel()!=null){
//                    return;
//       }

        VMComboBox vmAcntType = new VMComboBox(accountList);
        vmAcntType.addNullElement();
        vmAcntType.setSelectedItem(vmAcntType.getElementAt(0));
        comboBoxAccount.setModel(vmAcntType);
    }

    public void initTemplate() {
//       if(comboBox1.getModel().!=null){
//           return;
//       }
        VMComboBox vmAcntType = new VMComboBox(templateList);
        vmAcntType.addNullElement();
        vmAcntType.setSelectedItem(vmAcntType.getElementAt(0));
        comboBoxTemplate.setModel(vmAcntType);
    }

    public void setParameter(Parameter param) {
        super.setParameter(param);
        this.para = param;
        this.acntRid = (Long) param.get("accountRid");
        this.acntType=(String)param.get("accountType");
    }

    public void resetUI() {
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(actionId);
        inputInfo.setInputObj("acntRid", this.acntRid);
        inputInfo.setInputObj("accountType",acntType);
        ReturnInfo returnInfo = accessData(inputInfo);
        if (returnInfo.isError() == true) {
            return;
        }
        accountList = (Vector) returnInfo.getReturnObj("Account");

        templateList = (Vector) returnInfo.getReturnObj("template");

        String lastProject = (String) returnInfo.getReturnObj("ProjectName");
        txLastTemplate.setText(lastProject);
        txLastTemplate.setEnabled(false);
        txLastTemplate.setToolTipText(lastProject);
        initTemplate();
        initAccount();
    }

    public boolean onClickBack(ActionEvent e) {
        return true;
    }

    public boolean onClickNext(ActionEvent e) {
        String acntrid=null;//所选择的template的rid
        if (comboBoxTemplate.isVisible() == true) {
            acntrid = (String)this.comboBoxTemplate.getUICValue();

            if(acntrid==null){
            comMSG.dispErrorDialog("Please choose a template!");
            return false;}
           else {
               this.para.put("acntrid", new Long(acntrid));
               return true;
           }
        } else if (comboBoxAccount.isVisible() == true) {
            acntrid = (String)this.comboBoxAccount.getUICValue();

            if(acntrid==null){
           comMSG.dispErrorDialog("Please choose a template!");
           return false;} else{
           this.para.put("acntrid", new Long(acntrid));
            return true;}
        } else {

           comMSG.dispErrorDialog("Please choose a template!");
           return false;
        }

    }

    public boolean onClickFinish(ActionEvent e) {
        if ((this.comboBoxTemplate.getUICValue() == null) &&
            (this.comboBoxAccount.getUICValue() == null)) {
            comMSG.dispErrorDialog("Please choose a template!");
            return false;
        }
//       client.framework.view.common.comMSG.dispMessageDialog("Are you sure you want to delete PBS,WBS,ACTIVITY and WP?");
        int retValue = client.framework.view.common.comMSG.dispConfirmDialog(
            "Are you sure to delete original PBS,WBS,Activity and WP?");
        if (retValue == Constant.CANCEL) {
            return false;
        } else {
            if (comboBoxTemplate.isVisible() == true) {
                apprAcntRid = (String)this.comboBoxTemplate.getUICValue();
//                this.para.put("acntrid", new Long(apprAcntRid));
            } else if (comboBoxAccount.isVisible() == true) {
                apprAcntRid = (String)this.comboBoxAccount.getUICValue();
//                this.para.put("acntrid", new Long(apprAcntRid));
            }
            firstDelete();
            SaveMethodData();
            para.put("isFinish","Finish");//成功的完成
        }
        return true;
    }

    public boolean onClickCancel(ActionEvent e) {
        return true;
    }

    public void refresh() {
//           resetUI();
    }

    public void firstDelete() {
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(actionIdFin);
        inputInfo.setInputObj("acntRid", this.acntRid);
        inputInfo.setInputObj("apprAcntRid", new Long(apprAcntRid));
        ReturnInfo returnInfo = accessData(inputInfo);
        if (returnInfo.isError() == true) {
            return;
        }
    }

    public void SaveMethodData() {

        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(actionIdSave);

        inputInfo.setInputObj("Account", new Long(apprAcntRid));
        inputInfo.setInputObj("acntRid", this.acntRid);
        ReturnInfo returnInfo = accessData(inputInfo);

    }

    public static void main(String[] args) {
        VWWorkArea workArea = new VwTemplateApply();
        Parameter param = new Parameter();
        param.put("acntRid", new Long(7));

        workArea.setParameter(param);
        VWWorkArea workArea2 = new VWWorkArea();
        workArea2.addTab("Template", workArea);

        TestPanel.show(workArea2);
        workArea.refreshWorkArea();

    }

}
