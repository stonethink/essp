package client.essp.ebs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.ebs.DtoEbsEbs;
import client.essp.common.view.VWWorkArea;
import client.framework.model.VMComboBox;
import client.framework.view.common.comFORM;
import client.framework.view.common.comMSG;
import client.framework.view.vwcomp.VWUtil;
import com.wits.util.Parameter;
import com.wits.util.TestPanel;
import c2s.dto.DtoUtil;
import c2s.dto.IDto;

public class VwEbsGroup extends VwEbsGroupBase {
    String actionIdAdd = "FEbsAddAction";
    String actionIdUpdate = "FEbsUpdateAction";

    final Object status[] = new Object[] {"Initial", "Apporved", "Cancel",
                          "Pending", "Closed"};
    /**
     * input parameters
     */
    String entryFunType = "EbsMaintain"; //EbsMaintain, EbsMaintainHQ, EbsView
    /**
     * define control variable
     */
    private boolean isSaveOk = true;
    private boolean isParameterValid = true;

    /**
     * define common data (globe)
     */
    private DtoEbsEbs dataEbsEbs = new DtoEbsEbs();
    JButton btnSave = null;

    /////// ��1��������棺������棨����ؼ������ÿؼ����ƣ�������˳�򣩡���������¼�

    /**
     * default constructor
     */
    public VwEbsGroup() {
        super();

        addUICEvent();
        initUI();
    }

    private void addUICEvent() {
        //�����¼���������
        btnSave = this.getButtonPanel().addSaveButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedSave(e);
            }
        });
    }

    private void initUI() {
        VMComboBox vmComboBoxEbsStatus = new VMComboBox(status);
        vmComboBoxEbsStatus.addNullElement();
        txtEbsStatus.setModel(vmComboBoxEbsStatus);
    }

    /////// ��2�����ò�������ȡ�������
    public void setParameter(Parameter param) {
        super.setParameter(param);
        dataEbsEbs = (DtoEbsEbs) param.get("dto");

        if( dataEbsEbs == null ){
            dataEbsEbs = new DtoEbsEbs();
            isParameterValid = false;
        }else{
            isParameterValid = true;
        }

        entryFunType = (String) param.get("entryFunType");
        if (entryFunType == null || entryFunType.length() == 0) {
            entryFunType = "EbsMaintain";
        }

    }


    /////// ��3����ȡ���ݲ�ˢ�»���
    protected void resetUI() {
        VWUtil.bindDto2UI(dataEbsEbs, this);

        setButtonVisible();
        setEnableMode();

        comFORM.setFocus(this.txtEbsId);
    }


    private void setEnableMode(){
       if( this.isParameterValid == true &&
           (entryFunType.equals("EbsMaintain") ||
            entryFunType.equals("EbsMaintainHQ"))) {

            VWUtil.setUIEnabled(this, true);
        }else{

            //entryFunType.equals("EbsView")
            VWUtil.setUIEnabled(this, false);
        }
    }


    private void setButtonVisible(){
        //EbsMaintain, EbsMaintainHQ
        if( this.isParameterValid == true &&
           (entryFunType.equals("EbsMaintain") ||
            entryFunType.equals("EbsMaintainHQ"))) {

            btnSave.setVisible(true);
        }else{
            btnSave.setVisible(false);
        }
    }

    /////// ��4���¼�����
    public void actionPerformedSave(ActionEvent e) {
        DtoEbsEbs dtoBak = (DtoEbsEbs)DtoUtil.deepClone(dataEbsEbs);

        if (checkModified()) {
            if (validateData() == true) {
               if( saveData() == false ){
                   DtoUtil.copyBeanToBean(dataEbsEbs, dtoBak);
               }
            }else{
                DtoUtil.copyBeanToBean(dataEbsEbs, dtoBak);
            }
        }
    }

    /////// ��5����������
    public void saveWorkArea() {
        DtoEbsEbs dtoBak = (DtoEbsEbs)DtoUtil.deepClone(dataEbsEbs);

        if (checkModified()) {
            isSaveOk = false;
            if (confirmSaveWorkArea() == true) {
                if (validateData() == true) {
                    isSaveOk = saveData();

                    if( isSaveOk == false ){
                        DtoUtil.copyBeanToBean(dataEbsEbs, dtoBak);
                    }
                }else{
                    DtoUtil.copyBeanToBean(dataEbsEbs, dtoBak);
                }
            } else {

                //�û�ѡ��"������",��ΪisSaveOk=true
                isSaveOk = true;
                DtoUtil.copyBeanToBean(dataEbsEbs, dtoBak);
            }
        }else{
            isSaveOk = true;
        }
    }

    private boolean checkModified() {
        VWUtil.convertUI2Dto(dataEbsEbs, this);

        return dataEbsEbs.isChanged();
    }

    public boolean validateData() {
        boolean bRtn = true;
        //���ж���ļ��
        String id = dataEbsEbs.getEbsId();
        if (id.trim().equals("") == true) {
            comMSG.dispErrorDialog("Not input Ebs code.");
            txtEbsId.setErrorField(true);
            comFORM.setFocus(txtEbsId);
            return false;
        }else{
            txtEbsId.setErrorField(false);
        }

        String name = dataEbsEbs.getName();
        if (name.trim().equals("") == true) {
            comMSG.dispErrorDialog("Not input Ebs name.");
            txtEbsName.setErrorField(true);
            comFORM.setFocus(txtEbsName);
            return false;
        }else{
            txtEbsName.setErrorField(false);
        }

        return bRtn;
    }

    private boolean saveData() {
        InputInfo inputInfo = new InputInfo();

        if (dataEbsEbs.getRid() == null) {
            inputInfo.setActionId(this.actionIdAdd);
        } else {
            inputInfo.setActionId(this.actionIdUpdate);
        }

        inputInfo.setInputObj("dto", dataEbsEbs);

        ReturnInfo returnInfo = accessData(inputInfo);
        if (returnInfo.isError() == false) {
            DtoEbsEbs retEbs = (DtoEbsEbs) returnInfo.getReturnObj("dto");
            retEbs.setOp(IDto.OP_NOCHANGE);

            DtoUtil.copyBeanToBean(dataEbsEbs, retEbs);
            fireDataChanged();

            VWUtil.bindDto2UI(dataEbsEbs, this);
            comFORM.setFocus(this.txtEbsId);

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
        VwEbsGroup vwEbsGroup = new VwEbsGroup();
        w.addTab("",vwEbsGroup );

        Parameter para = new Parameter();
        para.put( "entryFunType", "XX" );
        vwEbsGroup.setParameter(para);
        vwEbsGroup.refreshWorkArea();
        TestPanel.show( w );
    }

}
