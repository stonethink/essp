package client.essp.pms.pbs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.pms.pbs.DtoPmsPbs;
import client.essp.common.util.TestPanelParam;
import client.framework.view.common.comFORM;
import client.framework.view.common.comMSG;
import client.framework.view.vwcomp.VWUtil;
import com.wits.util.Parameter;
import javax.swing.JButton;
import c2s.dto.DtoUtil;
import c2s.dto.IDto;
import com.wits.util.comDate;
import client.framework.model.VMComboBox;

public class VwPbsGeneral extends VwPbsGeneralBase {
    String actionIdAdd = "FPbsAddAction";
    String actionIdUpdate = "FPbsUpdateAction";
    String actionIdInit = "FPbsInitAction";

    String statusList[] = {"Approved","Rejected","On Hold","Completed"};

    /**
     * define common data (globe)
     */
    private DtoPmsPbs dataPmsPbs = new DtoPmsPbs();

    private boolean isSaveOk = true;
    private boolean isParameterValid = false;
    JButton btnSave = null;
    boolean isNeedSave = true;

    public VwPbsGeneral() {
        super();

        addUICEvent();
        initUI();
    }

    public void addUICEvent() {
        //捕获事件－－－－
       btnSave = this.getButtonPanel().addSaveButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedSave(e);
            }
        });
    }

    private void initUI(){
        VMComboBox vmComboBox = new VMComboBox(statusList);
        vmComboBox.addNullElement();
        this.cmbStatus.setModel(vmComboBox);

        setEnableMode();
        setButtonVisible();
    }

    public void actionPerformedSave(ActionEvent e) {
        isNeedSave = true;
        DtoPmsPbs dtoBak = (DtoPmsPbs)DtoUtil.deepClone(dataPmsPbs);
        if (checkModified()) {
            if (validateData() == true) {
                if( saveData() == false ){
                    DtoUtil.copyBeanToBean(dataPmsPbs, dtoBak);
                }
            }else{
                DtoUtil.copyBeanToBean(dataPmsPbs, dtoBak);
            }
        }
    }

    public void setParameter(Parameter param) {
        super.setParameter(param);

        dataPmsPbs = (DtoPmsPbs) param.get("dto");
        if (dataPmsPbs == null) {
            dataPmsPbs = new DtoPmsPbs();
            isParameterValid = false;
        } else {
            isParameterValid = true;
        }
    }

    protected void resetUI() {
        if( dataPmsPbs.isInsert() == true ){
            //generat pbs code
            InputInfo inputInfo = new InputInfo();
            inputInfo.setActionId(actionIdInit);

            ReturnInfo returnInfo = accessData(inputInfo);
            Long maxPbsCode = (Long)returnInfo.getReturnObj("maxCode");

            if( maxPbsCode != null ){
                String maxPbsCodeNum = maxPbsCode.toString();
                int len = maxPbsCodeNum.length();
                for (int i = len; i < DtoPmsPbs.CODE_NUMBER_LENGTH; i++) {
                    maxPbsCodeNum = "0" + maxPbsCodeNum;
                }

                dataPmsPbs.setProductCode("P"+maxPbsCodeNum);
            }else{
                dataPmsPbs.setProductCode(null);
            }
        }

        VWUtil.bindDto2UI(dataPmsPbs, this);
        this.txtPbsManager.setAcntRid(dataPmsPbs.getAcntRid());
        this.txtPbsManager.setTitle("manager of pbs");

        setButtonVisible();
        setEnableMode();
        comFORM.setFocus(this.txtProductName);
    }

    private void setEnableMode(){
       if( this.isParameterValid == true && this.dataPmsPbs.isReadonly()==false) {

            VWUtil.setUIEnabled(this, true);
        }else{

            VWUtil.setUIEnabled(this, false);
        }
    }

    private void setButtonVisible(){
       if( this.isParameterValid == true && this.dataPmsPbs.isReadonly()==false) {

            btnSave.setVisible(true);
        }else{
            btnSave.setVisible(false);
        }
    }


    public void saveWorkArea() {
        if( isNeedSave == false ){
            return;
        }

        DtoPmsPbs dtoBak = (DtoPmsPbs)DtoUtil.deepClone(dataPmsPbs);

        if (checkModified()) {
            isSaveOk = false;
            if (confirmSaveWorkArea("Do you save the pbs?") == true) {
                if (validateData() == true) {
                    isSaveOk = saveData();

                    if( isSaveOk == false ){
                        DtoUtil.copyBeanToBean(dataPmsPbs, dtoBak);
                    }
                }else{
                    DtoUtil.copyBeanToBean(dataPmsPbs, dtoBak);
                }
            } else {

                //用户选择"不保存",认为isSaveOk=true
                isSaveOk = true;
                isNeedSave = false;
                DtoUtil.copyBeanToBean(dataPmsPbs, dtoBak);
            }
        } else {
            isSaveOk = true;
        }
    }

    public boolean validateData() {
        //进行额外的检查
        String productName = this.dataPmsPbs.getProductName().trim();
        if (productName.equals("") == true) {
            comMSG.dispErrorDialog("Not input product name.");
            txtProductName.setErrorField(true);
            comFORM.setFocus(txtProductName);
            return false;
        }else{
            txtProductName.setErrorField(false);
        }

        String productCode = this.dataPmsPbs.getProductCode().trim();
        if (productCode.equals("") == true) {
            comMSG.dispErrorDialog("Not input product code.");
            txtProductCode.setErrorField(true);
            comFORM.setFocus(txtProductCode);
            return false;
        }else{
            txtProductCode.setErrorField(false);
        }

        //planned finish and actual finish
        String plannedFinish = this.dtePlannedFinish.getValueText();
        if (plannedFinish.equals("") == false &&
            comDate.checkDate(plannedFinish) == false) {
            comMSG.dispErrorDialog("The format of planned finish time is wrong.");
            dtePlannedFinish.requestFocus();
            dtePlannedFinish.setErrorField(true);

            return false;
        }else{
            dtePlannedFinish.setErrorField(false);
        }

        String actualFinish = this.dteActualFinish.getValueText();
        if (actualFinish.equals("") == false &&
            comDate.checkDate(actualFinish) == false) {
            comMSG.dispErrorDialog("The format of actual finish time is wrong.");
            dteActualFinish.requestFocus();
            dteActualFinish.setErrorField(true);

            return false;
        }else{
            dteActualFinish.setErrorField(false);
        }

        return true;
    }

    private boolean checkModified() {
        VWUtil.convertUI2Dto(dataPmsPbs, this);

        return dataPmsPbs.isChanged();
    }

    private boolean saveData() {
        InputInfo inputInfo = new InputInfo();
        boolean isAdd;
        if (dataPmsPbs.getPbsRid() == null) {
            isAdd = true;
            inputInfo.setActionId(this.actionIdAdd);
        } else {
            isAdd = false;
            inputInfo.setActionId(this.actionIdUpdate);
        }

        inputInfo.setInputObj("dto", this.dataPmsPbs);

        ReturnInfo returnInfo = accessData(inputInfo);
        if (returnInfo.isError() == false) {
            DtoPmsPbs retPmsPbs = (DtoPmsPbs) returnInfo.getReturnObj("dto");
            retPmsPbs.setOp(IDto.OP_NOCHANGE);

            DtoUtil.copyBeanToBean(dataPmsPbs, retPmsPbs);
            fireDataChanged();

            VWUtil.bindDto2UI(this.dataPmsPbs, this);
            comFORM.setFocus(this.txtProductName);

            if( isAdd ){
                fireDataCreate();
            }
            return true;
        }else{
            return false;
        }
    }

    public boolean isSaveOk(){
        return this.isSaveOk;
    }

    public static void main(String[] args) {
        TestPanelParam.show(new VwPbsGeneral());
    }

}
