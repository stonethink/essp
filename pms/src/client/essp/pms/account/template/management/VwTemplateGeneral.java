package client.essp.pms.account.template.management;

import client.essp.common.view.VWGeneralWorkArea;
import client.framework.view.vwcomp.VWJLabel;
import client.framework.view.vwcomp.VWJComboBox;
import client.framework.view.vwcomp.VWJText;
import java.awt.Rectangle;
import client.framework.view.vwcomp.VWJTextArea;
import client.essp.common.view.VWWorkArea;
import com.wits.util.TestPanel;
import client.essp.pms.account.VwAccountList;
import com.wits.util.Parameter;
import java.awt.Dimension;
import java.util.Vector;
import server.essp.pms.account.action.AcAcntInit;
import server.essp.common.syscode.LgSysParameter;
import java.util.ArrayList;
import java.util.List;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import client.framework.model.VMComboBox;
import java.awt.*;
import client.framework.view.vwcomp.IVWWizardEditorEvent;
import java.awt.event.ActionEvent;
import client.framework.view.vwcomp.IVWComponent;
import javax.swing.JComponent;
import client.framework.view.common.comMSG;
import com.wits.util.StringUtil;
import client.framework.view.common.comFORM;
import c2s.essp.pms.account.DtoPmsAcnt;
import client.framework.view.vwcomp.VWUtil;
import c2s.essp.pms.account.DtoAcntKEY;
import c2s.dto.DtoUtil;
import java.awt.event.ActionListener;


public class VwTemplateGeneral extends VWGeneralWorkArea implements IVWWizardEditorEvent{

    static final String actionId = "FAcntTemplateGeneralAction";
    static final String actionIdAdd = "FAccountAddAction";
    static final String actioncheck = "FAccountCheckAction";
    private Parameter parameter;

    private DtoPmsAcnt dataPmsAcc = new DtoPmsAcnt();
    boolean isNeedSave = false;
    public VwTemplateGeneral() {
        try {
            jbInit();
            initUI();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void initUI() {
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(actionId);

        ReturnInfo returnInfo = accessData(inputInfo);
        if (returnInfo.isError() == true) {
            return;
        }
        Vector accountTypeList = (Vector) returnInfo.getReturnObj(
            "accountTypeList");

        VMComboBox vmAcntType = new VMComboBox(accountTypeList);
        inputType.setModel(vmAcntType);

    }

    public void jbInit() throws Exception {
        this.setLayout(null);
        this.setPreferredSize(new Dimension(600,400));

        lbCode.setText("Template Code");
        lbCode.setBounds(new Rectangle(15, 15, 90, 20));
        inputCode.setBounds(new Rectangle(105, 15, 130, 20));

        lbName.setText("Template Name");
        lbName.setBounds(new Rectangle(330, 14, 90, 20));
        inputName.setBounds(new Rectangle(420, 15, 130, 20));
        inputName.addActionListener(new
                                    VwTemplateGeneral_inputName_actionAdapter(this));

        lbType.setText("Template Type");
        lbType.setBounds(new Rectangle(15, 50, 90, 20));
        inputType.setBounds(new Rectangle(105, 50, 133, 20));

        lbBrief.setText("Brief");
        lbBrief.setBounds(new Rectangle(15, 85, 90, 20));
        inputBrief.setBounds(new Rectangle(105, 85, 445, 80));


        this.add(inputBrief);
        this.add(lbBrief);
        this.add(lbCode);
        this.add(inputCode);
        this.add(lbType);
        this.add(inputType);
        this.add(inputName);
        this.add(lbName);
    }

    public boolean onClickBack(ActionEvent e){
        return false;
    }

    public boolean onClickNext(ActionEvent e){

        if (validateData() == true) {
            getDate();
            parameter.put("dataPmsAcc",this.dataPmsAcc);
            return true;
        }
        return false;

    }

    public boolean onClickFinish(ActionEvent e){

        if (validateData() == true) {
             saveData();
             return true;
        }

        return false;
    }

    public boolean onClickCancel(ActionEvent e){
        return true;
    }
    public void refresh(){

    }
    public boolean validateData() {


        //±ÿ ‰œÓ£∫/Template Name/Template Code/Template type
        if(  checkNecessary( this.inputCode, "Template Code"  ) == false
            || checkNecessary( this.inputName, "Template Name"  ) == false
            || checkNecessary( this.inputType, "Template type"  ) == false
            ){
             return false;
        }
        String brief = this.inputBrief.getText();
        if(brief.equals("")==false){
            inputName.setErrorField(false);
        }
        return true;
    }

    public void setParameter(Parameter para) {
        super.setParameter(para);
        this.parameter=para;
    }

    protected boolean checkNecessary(IVWComponent comp, String msg) {
        Object value = comp.getUICValue();
        if (value == null || StringUtil.nvl(value).equals("") == true) {
            comMSG.dispErrorDialog(" Please input " + msg + " .");
            comp.setErrorField(true);
            comFORM.setFocus((JComponent) comp);
            return false;
        }
        if(msg.equals("Template Code")){
            InputInfo inputInfo = new InputInfo();
            inputInfo.setActionId(this.actioncheck);
            inputInfo.setInputObj("code", value);
            ReturnInfo returnInfo = accessData(inputInfo);
            if(returnInfo.isError()==false){
                String code = (String)returnInfo.getReturnObj("check");
                if(code.equals("true")){
                    comMSG.dispErrorDialog(String.valueOf(value) + " is exist, please input new code.");
                    comp.setErrorField(true);
                    comFORM.setFocus((JComponent) comp);
                    return false;
                }
            }
        }
        comp.setErrorField(false);
        return true;

    }

    private void saveData() {

        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.actionIdAdd);
        getDate();
        inputInfo.setInputObj(DtoAcntKEY.DTO, this.dataPmsAcc);
        ReturnInfo returnInfo = accessData(inputInfo);
    }

    public void getDate(){
        String code = this.inputCode.getText();
        String name = this.inputName.getText();
        String type = (String)this.inputType.getUICValue();
        String brief = this.inputBrief.getText();
        dataPmsAcc.setId(code);
        dataPmsAcc.setName(name);
        dataPmsAcc.setType(type);
        dataPmsAcc.setBrief(brief);
    }





    VWJLabel lbCode = new VWJLabel();
    VWJText inputCode = new VWJText();
    VWJLabel lbName = new VWJLabel();
    VWJText inputName = new VWJText();
    VWJLabel lbBrief = new VWJLabel();
    VWJTextArea inputBrief = new VWJTextArea();
    VWJLabel lbType = new VWJLabel();
    VWJComboBox inputType = new VWJComboBox();


    public static void main(String[] args){
//        VWWorkArea workArea = new VwAccountList();
//        //workArea.addTab("template",new VwTemplateGeneral());
//        workArea.refreshWorkArea();
//        TestPanel.show(workArea);


    }

    public void inputName_actionPerformed(ActionEvent e) {

    }
}


class VwTemplateGeneral_inputName_actionAdapter implements ActionListener {
    private VwTemplateGeneral adaptee;
    VwTemplateGeneral_inputName_actionAdapter(VwTemplateGeneral adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.inputName_actionPerformed(e);
    }
}
