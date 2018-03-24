package client.essp.pms.pbs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import c2s.dto.DtoUtil;
import c2s.dto.IDto;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.pms.pbs.DtoPmsPbsFiles;
import client.essp.common.util.TestPanelParam;
import client.framework.view.common.comFORM;
import client.framework.view.common.comMSG;
import client.framework.view.vwcomp.VWUtil;
import com.wits.util.Parameter;
import com.wits.util.comDate;
import com.wits.util.StringUtil;

public class VwPmsPbsFilesGeneral extends VwPmsPbsFilesGeneralBase {
    static final String actionIdUpdate = "FPbsFilesUpdateOneAction";
    static final String actionIdAdd = "FPbsFilesAddAction";

    /**
     * define common data (globe)
     */
    private DtoPmsPbsFiles dtoPmsPbsFiles = new DtoPmsPbsFiles();
    private DtoPmsPbsFiles dataPmsPbsFiles = new DtoPmsPbsFiles();

    private boolean isSaveOk = true;
    private boolean isParameterValid = false;
    private boolean isReadonly = true;
    JButton btnSave = null;
    private String acntCode;

    public VwPmsPbsFilesGeneral() {
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

    private void initUI() {
        setEnableMode();
        setButtonVisible();
    }

    public void actionPerformedSave(ActionEvent e) {
        if (checkModified()) {
            if (validateData() == true) {
                saveData();
            }
        }
    }

    public void setParameter(Parameter param) {
        super.setParameter(param);

        dtoPmsPbsFiles = (DtoPmsPbsFiles) param.get("dto");
        if (dtoPmsPbsFiles == null) {
            dtoPmsPbsFiles = new DtoPmsPbsFiles();
            isParameterValid = false;
        } else {
            isParameterValid = true;
        }

        Boolean bIsReadonly = (Boolean) param.get("isReadonly");
        if (bIsReadonly == null) {
            isReadonly = true;
        } else {
            isReadonly = bIsReadonly.booleanValue();
        }

        acntCode = (String)param.get("acntCode");

        dataPmsPbsFiles = (DtoPmsPbsFiles) DtoUtil.deepClone(dtoPmsPbsFiles);
    }

    protected void resetUI() {
        VWUtil.bindDto2UI(dataPmsPbsFiles, this);

        this.txtAuthor.setAcntRid(dataPmsPbsFiles.getAcntRid());
        this.txtAuthor.setTitle("author of file");
        this.txtAttachment.setAcntCode(acntCode);

        setButtonVisible();
        setEnableMode();
        comFORM.setFocus(this.txtName);
    }

    private void setEnableMode() {
        if (this.isParameterValid == true && this.isReadonly == false) {

            VWUtil.setUIEnabled(this, true);
        } else {

            VWUtil.setUIEnabled(this, false);
        }
    }

    private void setButtonVisible() {
        if (this.isParameterValid == true && this.isReadonly == false) {

            btnSave.setVisible(true);
        } else {
            btnSave.setVisible(false);
        }
    }


    public void saveWorkArea() {
        if (checkModified()) {
            isSaveOk = false;
//            if (confirmSaveWorkArea("Do you save the files?") == true) {
            if (validateData() == true) {
                isSaveOk = saveData();
            }
//            } else {
//
//                //用户选择"不保存",认为isSaveOk=true
//                isSaveOk = true;
//            }
        } else {
            isSaveOk = true;
        }
    }

    public boolean validateData() {
        //进行额外的检查
        String name = StringUtil.nvl(this.txtName.getUICValue());
        if (name.equals("") == true) {
            comMSG.dispErrorDialog("Not input file name.");
            txtName.setErrorField(true);
            comFORM.setFocus(txtName);
            return false;
        } else {
            txtName.setErrorField(false);
        }

        //create date
        String createDate = this.dteCreateDate.getValueText();
        if (createDate.equals("") == false &&
            comDate.checkDate(createDate) == false) {
            comMSG.dispErrorDialog("The format of create date is wrong.");
            dteCreateDate.requestFocus();
            dteCreateDate.setErrorField(true);

            return false;
        } else {
            dteCreateDate.setErrorField(false);
        }

        String modDate = this.dteModifyDate.getValueText();
        if (modDate.equals("") == false &&
            comDate.checkDate(modDate) == false) {
            comMSG.dispErrorDialog("The format of modify date is wrong.");
            dteModifyDate.requestFocus();
            dteModifyDate.setErrorField(true);

            return false;
        } else {
            dteModifyDate.setErrorField(false);
        }

        return true;
    }

    private boolean checkModified() {
        VWUtil.convertUI2Dto(dataPmsPbsFiles, this);

        return dataPmsPbsFiles.isChanged();
    }

    private boolean saveData() {
        InputInfo inputInfo = new InputInfo();
        if (dataPmsPbsFiles.getFilesRid() == null) {
            inputInfo.setActionId(this.actionIdAdd);
        } else {
            inputInfo.setActionId(this.actionIdUpdate);
        }

        inputInfo.setInputObj("dto", this.dataPmsPbsFiles);

        ReturnInfo returnInfo = accessData(inputInfo);
        if (returnInfo.isError() == false) {
            this.dataPmsPbsFiles = (DtoPmsPbsFiles) returnInfo.getReturnObj("dto");
            dataPmsPbsFiles.setOp(IDto.OP_NOCHANGE);

            VWUtil.bindDto2UI(this.dataPmsPbsFiles, this);
            comFORM.setFocus(this.txtName);

            setDataToDto();

            return true;
        } else {
            return false;
        }
    }

    //将画面上输入的值data保存到真正的dto中
    private void setDataToDto() {

        //set dto
        DtoUtil.copyBeanToBean(dtoPmsPbsFiles, dataPmsPbsFiles);
        fireDataChanged();
    }

    public boolean isSaveOk() {
        return this.isSaveOk;
    }

    public DtoPmsPbsFiles getDto(){
        return this.dtoPmsPbsFiles;
    }

    public static void main(String[] args) {
        TestPanelParam.show(new VwPmsPbsFilesGeneral());
    }

}
