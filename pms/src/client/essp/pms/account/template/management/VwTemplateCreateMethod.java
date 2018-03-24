package client.essp.pms.account.template.management;

import client.essp.common.view.VWGeneralWorkArea;
import java.awt.*;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import client.framework.view.vwcomp.VWJLabel;
import client.framework.view.vwcomp.VWJComboBox;
import client.framework.view.vwcomp.VWJText;
import client.framework.view.vwcomp.VWJRadioButton;
import client.essp.pms.account.VwAccountList;
import com.wits.util.TestPanel;
import client.essp.common.view.VWWorkArea;
import javax.swing.JScrollBar;
import javax.swing.ButtonGroup;
import javax.swing.*;
import client.framework.view.vwcomp.VWButtonGroup;
import client.essp.common.view.VWJPanel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import c2s.dto.ReturnInfo;
import c2s.dto.InputInfo;
import client.framework.model.VMComboBox;
import java.util.Vector;
import client.framework.view.vwcomp.IVWWizardEditorEvent;
import c2s.essp.pms.account.DtoPmsAcnt;
import client.framework.view.vwcomp.VWUtil;
import c2s.essp.pms.account.DtoAcntKEY;
import c2s.dto.DtoUtil;
import db.essp.pms.Account;
import com.wits.util.Parameter;


public class VwTemplateCreateMethod extends VWGeneralWorkArea implements IVWWizardEditorEvent{

    static final String actionId = "FAcntTemplateCreateMethod";
    static final String methodAdd = "FAccountTemplateAdd";
    static final String generalAdd ="FAccountAddAction";
    private DtoPmsAcnt dataPmsAcc = new DtoPmsAcnt();
    Long Acntrid = null;
    private boolean isParameterValid = true;
    private Parameter para;

    public VwTemplateCreateMethod() {
        try {
            jbInit();
            setEnableMode();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void jbInit() throws Exception {
        this.setLayout(null);
        this.setPreferredSize(new Dimension(600,400));
        lbMethod.setText("Create Method");
        lbMethod.setBounds(new Rectangle(36, 24, 79, 22));
        VWJRadioButton1.setFocusPainted(false);
        VWJRadioButton1.setText("VWJRadioButton1");
        VWJRadioButton1.setBounds(new Rectangle(70, 58, 20, 18));
        VWJRadioButton2.setFocusPainted(false);
        VWJRadioButton2.setText("VWJRadioButton2");
        VWJRadioButton2.setBounds(new Rectangle(70, 88, 20, 18));
        VWJRadioButton3.setKeyType(true);
        VWJRadioButton3.setFocusPainted(false);
        VWJRadioButton3.setText("VWJRadioButton3");
        VWJRadioButton3.setBounds(new Rectangle(70, 118, 20, 18));
        VWJRadioButton4.setKeyType(true);
        VWJRadioButton4.setFocusPainted(false);
        VWJRadioButton4.setText("VWJRadioButton4");
        VWJRadioButton4.setBounds(new Rectangle(70, 148, 20, 18));

        lbScratch.setText("Create a template from scratch");
        lbScratch.setBounds(new Rectangle(120, 58, 180, 15));
        lbAccount.setText("Create a template from a account");
        lbAccount.setBounds(new Rectangle(120, 88, 192, 15));
        lbTemplate.setText("Create a template from a template");
        lbTemplate.setBounds(new Rectangle(120, 118, 203, 15));
        lbOsp.setText("Create a template from OSP");
        lbOsp.setBounds(new Rectangle(120, 148, 203, 15));
        lbSelect.setText("Template Type");
        lbSelect.setBounds(new Rectangle(38, 178, 80, 19));
        inputSelect.setBounds(new Rectangle(120, 178, 183, 22));
        buttonGroup1.add(VWJRadioButton1);
        buttonGroup1.add(VWJRadioButton2);
        buttonGroup1.add(VWJRadioButton3);
        buttonGroup1.add(VWJRadioButton4);
        this.add(VWJRadioButton1);
        this.add(VWJRadioButton2);
        this.add(VWJRadioButton3);
        this.add(VWJRadioButton4);
        this.add(lbScratch);
        this.add(lbTemplate);
        this.add(lbAccount);
        this.add(lbOsp);
        this.add(lbSelect);
        this.add(inputSelect);
        this.add(lbMethod);
        VWJRadioButton1.setSelected(true);
        lbSelect.setVisible(false);
        inputSelect.setVisible(false);

        VWJRadioButton1.addActionListener(new ActionListener(){
               public void actionPerformed(ActionEvent e){
                      if(e.getActionCommand().equals("VWJRadioButton1")){
                           lbSelect.setVisible(false);
                           inputSelect.setVisible(false);
                      }
               }
        });

        VWJRadioButton2.addActionListener(new ActionListener(){
               public void actionPerformed(ActionEvent e){
                      if(e.getActionCommand().equals("VWJRadioButton2")){
                           lbSelect.setVisible(true);
                           lbSelect.setText("Account");
                           inputSelect.setVisible(true);
                           initAccount();
                      }
               }
        });

        VWJRadioButton3.addActionListener(new ActionListener(){
               public void actionPerformed(ActionEvent e){
                      if(e.getActionCommand().equals("VWJRadioButton3")){
                           lbSelect.setVisible(true);
                           lbSelect.setText("Template");
                           inputSelect.setVisible(true);
                           initTemplate();
                      }
               }
        });

        VWJRadioButton4.addActionListener(new ActionListener(){
               public void actionPerformed(ActionEvent e){
                      if(e.getActionCommand().equals("VWJRadioButton4")){
                           lbSelect.setVisible(true);
                           lbSelect.setText("OSP");
                           inputSelect.setVisible(true);
                           initOSP();
                      }
               }
        });

    }

    public void initAccount(){

        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.actionId);
        inputInfo.setInputObj(DtoAcntKEY.DTO,this.dataPmsAcc);

        ReturnInfo returnInfo = accessData(inputInfo);
        if (returnInfo.isError() == true) {
            return;
        }
        Vector accountList = (Vector)returnInfo.getReturnObj("Account");
        VMComboBox vmAcntType = new VMComboBox(accountList);
        inputSelect.setModel(vmAcntType);

    }

    public void initTemplate(){

        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.actionId);
        inputInfo.setInputObj(DtoAcntKEY.DTO,this.dataPmsAcc);

        ReturnInfo returnInfo = accessData(inputInfo);
        if (returnInfo.isError() == true) {
            return;
        }
        Vector templateList = (Vector)returnInfo.getReturnObj("template");
        VMComboBox vmAcntType = new VMComboBox(templateList);
        inputSelect.setModel(vmAcntType);
    }

    public void initOSP(){

       InputInfo inputInfo = new InputInfo();
       inputInfo.setActionId(this.actionId);
       inputInfo.setInputObj(DtoAcntKEY.DTO,this.dataPmsAcc);

       ReturnInfo returnInfo = accessData(inputInfo);
       if (returnInfo.isError() == true) {
           return;
       }
       Vector templateList = (Vector)returnInfo.getReturnObj("OSP");
       VMComboBox vmAcntType = new VMComboBox(templateList);
       inputSelect.setModel(vmAcntType);
   }

    public boolean onClickBack(ActionEvent e){
        return true;
    }

    public boolean onClickNext(ActionEvent e){

        if(inputSelect.isVisible()==false){
            this.para.put("acntrid", null);
        }else{
            String acntrid = (String)this.inputSelect.getUICValue();
            if (acntrid == null) {
                client.framework.view.common.comMSG.dispMessageDialog(
                    "    Please select a "+lbSelect.getText()+"!  ");
                return false;
            } else {
                this.Acntrid = new Long(acntrid);
            }

            this.para.put("acntrid", this.Acntrid);
        }
        return true;
    }

    public boolean onClickFinish(ActionEvent e){

//        if(saveGeneralData()){
            SaveTemplateData();
//        }
        return true;
    }

    public boolean onClickCancel(ActionEvent e){
        return true;
    }

    public void refresh(){
        if(this.para !=null){
            this.dataPmsAcc = (DtoPmsAcnt)para.get("dataPmsAcc");
        }
//        VWJRadioButton1.setSelected(true);
       if(VWJRadioButton2.isSelected()){
           lbSelect.setVisible(true);
           lbSelect.setText("Account");
           inputSelect.setVisible(true);
          // initAccount();
       }else if(VWJRadioButton3.isSelected()){
           lbSelect.setVisible(true);
           lbSelect.setText("Template");
           inputSelect.setVisible(true);
           //initTemplate();
       }else{
           lbSelect.setVisible(false);
           inputSelect.setVisible(false);
       }
    }


//    public boolean saveGeneralData(){
//
//        InputInfo inputInfo = new InputInfo();
//        inputInfo.setActionId(this.generalAdd);
//        inputInfo.setInputObj(DtoAcntKEY.DTO, this.dataPmsAcc);
//        ReturnInfo returnInfo = accessData(inputInfo);
//        return true;
//    }

    public void SaveTemplateData(){
        if(inputSelect.isVisible()==true){
            InputInfo inputInfo = new InputInfo();
            inputInfo.setActionId(this.methodAdd);

            String acntrid = (String)this.inputSelect.getUICValue();
            this.Acntrid = new Long(acntrid);
            inputInfo.setInputObj("Account", this.Acntrid);
            inputInfo.setInputObj(DtoAcntKEY.DTO, this.dataPmsAcc);
            ReturnInfo returnInfo = accessData(inputInfo);
        }
    }

    public void setParameter(Parameter para) {
        super.setParameter(para);
        this.para=para;
        if(para!=null){
            dataPmsAcc = (DtoPmsAcnt)para.get("dataPmsAcc");
        }
    }

    private void setEnableMode(){
    if ( isParameterValid == true) {
        VWUtil.setUIEnabled(this, true);
    }else{
        VWUtil.setUIEnabled(this, false);
    }
}




    VWJLabel lbMethod = new VWJLabel();
    VWJRadioButton VWJRadioButton1 = new VWJRadioButton();
    VWJRadioButton VWJRadioButton2 = new VWJRadioButton();
    VWJRadioButton VWJRadioButton3 = new VWJRadioButton();
    VWJRadioButton VWJRadioButton4 = new VWJRadioButton();
    VWJLabel lbScratch = new VWJLabel();
    VWJLabel lbAccount = new VWJLabel();
    VWJLabel lbTemplate = new VWJLabel();
    VWJLabel lbOsp = new VWJLabel();
    VWJLabel lbSelect = new VWJLabel();
    VWJComboBox inputSelect = new VWJComboBox();
    VWButtonGroup buttonGroup1 = new VWButtonGroup();
    ButtonGroup buttonGroup2 = new ButtonGroup();


    public static void main(String[] args){
        VWWorkArea workArea = new VwAccountList();
        workArea.addTab("template", new VwTemplateCreateMethod());
        workArea.refreshWorkArea();
        TestPanel.show(workArea);
    }
}
