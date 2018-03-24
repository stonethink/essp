package client.essp.common.code;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import c2s.dto.DtoUtil;
import c2s.dto.IDto;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.common.code.DtoCode;
import c2s.essp.common.code.DtoKey;
import client.essp.common.view.VWWorkArea;
import client.framework.view.common.comFORM;
import client.framework.view.common.comMSG;
import client.framework.view.vwcomp.VWUtil;
import com.wits.util.Parameter;
import com.wits.util.TestPanel;

public class VwCodeGeneral extends VwCodeGeneralBase {
    static final String actionIdAdd = "F00CodeAddAction";
    static final String actionIdUpdate = "F00CodeUpdateAction";

    private boolean isSaveOk = true;
    private boolean isParameterValid = false;
    boolean isNeedSave = true;

    /**
     * define common data (globe)
     */
    private DtoCode dataCode = new DtoCode();
    JButton btnSave = null;

    /**
     * default constructor
     */
    public VwCodeGeneral() {
        super();

        addUICEvent();
        setButtonVisible();
        setEnableMode();
    }

    private void addUICEvent() {
        //捕获事件－－－－
        btnSave = this.getButtonPanel().addSaveButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedSave(e);
            }
        });
    }

    /////// 段2，设置参数：获取传入参数
    public void setParameter(Parameter param) {
        super.setParameter(param);
        dataCode = (DtoCode) param.get(DtoKey.DTO);

        if( dataCode == null ){
            dataCode = new DtoCode();
            isParameterValid = false;
        }else{
            isParameterValid = true;
        }
    }


    /////// 段3，获取数据并刷新画面
    protected void resetUI() {
        VWUtil.bindDto2UI(dataCode, this);

        setButtonVisible();
        setEnableMode();

        comFORM.setFocus(this.txtName);
    }


    private void setEnableMode(){
       if( this.isParameterValid == true ) {

            VWUtil.setUIEnabled(this, true);
        }else{

            VWUtil.setUIEnabled(this, false);
        }
    }

    private void setButtonVisible(){
        if( this.isParameterValid == true ) {

            btnSave.setVisible(true);
        }else{
            btnSave.setVisible(false);
        }
    }

    /////// 段4，事件处理
    public void actionPerformedSave(ActionEvent e) {
        DtoCode dtoBak = (DtoCode)DtoUtil.deepClone(dataCode);

        isNeedSave = true;
        if (checkModified()) {
            if (validateData() == true) {
               if( saveData() == false ){
                   DtoUtil.copyBeanToBean(dataCode, dtoBak);
               }
            }else{
                DtoUtil.copyBeanToBean(dataCode, dtoBak);
            }
        }
    }

    /////// 段5，保存数据
    public void saveWorkArea() {
        if( isNeedSave == false ){
            return ;
        }

        DtoCode dtoBak = (DtoCode)DtoUtil.deepClone(dataCode);

        if (checkModified()) {
            isSaveOk = false;
            if (confirmSaveWorkArea() == true) {
                if (validateData() == true) {
                    isSaveOk = saveData();

                    if( isSaveOk == false ){
                        DtoUtil.copyBeanToBean(dataCode, dtoBak);
                    }
                }else{
                    DtoUtil.copyBeanToBean(dataCode, dtoBak);
                }
            } else {

                //用户选择"不保存",认为isSaveOk=true
                isSaveOk = true;
                isNeedSave = false;
                DtoUtil.copyBeanToBean(dataCode, dtoBak);
            }
        }else{
            isSaveOk = true;
        }
    }

    private boolean checkModified() {
        VWUtil.convertUI2Dto(dataCode, this);

        return dataCode.isChanged();
    }

    public boolean validateData() {
        String name = this.txtName.getUICValue().toString();
        if (name.trim().equals("") == true) {
            comMSG.dispErrorDialog("Not input Code name.");
            txtName.setErrorField(true);
            comFORM.setFocus(txtName);
            return false;
        }else{
            txtName.setErrorField(false);
        }

        return true;
    }

    private boolean saveData() {
        InputInfo inputInfo = new InputInfo();
        boolean isInsert = false;

        if (dataCode.getRid() == null) {
            inputInfo.setActionId(this.actionIdAdd);
            isInsert = true;
        } else {
            inputInfo.setActionId(this.actionIdUpdate);
        }

        inputInfo.setInputObj(DtoKey.DTO, dataCode);

        ReturnInfo returnInfo = accessData(inputInfo);
        if (returnInfo.isError() == false) {
            DtoCode retCode = (DtoCode) returnInfo.getReturnObj(DtoKey.DTO);
            retCode.setOp(IDto.OP_NOCHANGE);

            DtoUtil.copyBeanToBean(dataCode, retCode);
            fireDataChanged();
            if( isInsert == true ){
                fireDataCreate();
            }

            VWUtil.bindDto2UI(dataCode, this);
            comFORM.setFocus(this.txtName);

            return true;
        }else{
            return false;
        }
    }

    public boolean isSaveOk(){
        return this.isSaveOk;
    }

    public static void main(String args[]){
        VWWorkArea w = new VWWorkArea();
        VwCodeGeneral vwCodeGeneral = new VwCodeGeneral();
        w.addTab("General",vwCodeGeneral );

        TestPanel.show( w );

        Parameter para = new Parameter();
        para.put(DtoKey.DTO, new DtoCode());
        vwCodeGeneral.setParameter(para);
        vwCodeGeneral.refreshWorkArea();
    }

}
