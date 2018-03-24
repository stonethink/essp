package client.essp.common.code.value;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import c2s.dto.DtoUtil;
import c2s.dto.IDto;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.common.code.DtoCodeValue;
import c2s.essp.common.code.DtoKey;
import client.essp.common.view.VWWorkArea;
import client.framework.view.common.comFORM;
import client.framework.view.common.comMSG;
import client.framework.view.vwcomp.VWUtil;
import com.wits.util.Parameter;
import com.wits.util.TestPanel;

public class VwCodeValueGeneral extends VwCodeValueGeneralBase {
    static final String actionIdAdd = "F00CodeValueAddAction";
    static final String actionIdUpdate = "F00CodeValueUpdateAction";

    private boolean isSaveOk = true;
    private boolean isParameterValid = false;
    boolean isNeedSave = true;

    /**
     * define common data (globe)
     */
    private DtoCodeValue dataCodeValue = new DtoCodeValue();
    JButton btnSave = null;

    /**
     * default constructor
     */
    public VwCodeValueGeneral() {
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
        dataCodeValue = (DtoCodeValue) param.get(DtoKey.DTO);

        if( dataCodeValue == null ){
            dataCodeValue = new DtoCodeValue();
            isParameterValid = false;
        }else{
            isParameterValid = true;
        }
    }


    /////// 段3，获取数据并刷新画面
    protected void resetUI() {
        VWUtil.bindDto2UI(dataCodeValue, this);

        setButtonVisible();
        setEnableMode();

        comFORM.setFocus(this.txtValue);
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
        DtoCodeValue dtoBak = (DtoCodeValue)DtoUtil.deepClone(dataCodeValue);

        isNeedSave = true;
        if (checkModified()) {
            if (validateData() == true) {
               if( saveData() == false ){
                   DtoUtil.copyBeanToBean(dataCodeValue, dtoBak);
               }
            }else{
                DtoUtil.copyBeanToBean(dataCodeValue, dtoBak);
            }
        }
    }

    /////// 段5，保存数据
    public void saveWorkArea() {
        if( isNeedSave == false ){
            return ;
        }

        DtoCodeValue dtoBak = (DtoCodeValue)DtoUtil.deepClone(dataCodeValue);

        if (checkModified()) {
            isSaveOk = false;
            //if (confirmSaveWorkArea() == true) {
                if (validateData() == true) {
                    isSaveOk = saveData();

                    if( isSaveOk == false ){
                        DtoUtil.copyBeanToBean(dataCodeValue, dtoBak);
                    }
                }else{
                    DtoUtil.copyBeanToBean(dataCodeValue, dtoBak);
                }
            /*} else {

                //用户选择"不保存",认为isSaveOk=true
                isSaveOk = true;
                isNeedSave = false;
                DtoUtil.copyBeanToBean(dataCodeValue, dtoBak);
            }
            */
        }else{
            isSaveOk = true;
        }
    }

    private boolean checkModified() {
        VWUtil.convertUI2Dto(dataCodeValue, this);

        //return dataCodeValue.isChanged();
        return true;
    }

    public boolean validateData() {
        String name = this.txtValue.getUICValue().toString();
        if (name.trim().equals("") == true) {
            comMSG.dispErrorDialog("Not input code value name.");
            txtValue.setErrorField(true);
            comFORM.setFocus(txtValue);
            return false;
        }else{
            txtValue.setErrorField(false);
        }

        return true;
    }

    private boolean saveData() {
        InputInfo inputInfo = new InputInfo();

        if (dataCodeValue.getRid() == null) {
            inputInfo.setActionId(this.actionIdAdd);
        } else {
            inputInfo.setActionId(this.actionIdUpdate);
        }

        inputInfo.setInputObj(DtoKey.DTO, dataCodeValue);

        ReturnInfo returnInfo = accessData(inputInfo);
        if (returnInfo.isError() == false) {
            DtoCodeValue retCodeValue = (DtoCodeValue) returnInfo.getReturnObj(DtoKey.DTO);
            retCodeValue.setOp(IDto.OP_NOCHANGE);

            DtoUtil.copyBeanToBean(dataCodeValue, retCodeValue);
            fireDataChanged();

            VWUtil.bindDto2UI(dataCodeValue, this);
            comFORM.setFocus(this.txtValue);

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
        VwCodeValueGeneral vwCodeValueGeneral = new VwCodeValueGeneral();
        w.addTab("General",vwCodeValueGeneral );

        TestPanel.show( w );

        Parameter para = new Parameter();
        para.put(DtoKey.DTO, new DtoCodeValue());
        vwCodeValueGeneral.setParameter(para);
        vwCodeValueGeneral.refreshWorkArea();
    }

}
