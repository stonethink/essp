package client.essp.ebs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComponent;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.ebs.DtoPmsAcnt;
import client.essp.common.view.VWWorkArea;
import client.framework.model.VMComboBox;
import client.framework.view.common.comFORM;
import client.framework.view.common.comMSG;
import client.framework.view.vwcomp.IVWComponent;
import client.framework.view.vwcomp.VWUtil;
import com.wits.util.Parameter;
import com.wits.util.StringUtil;
import com.wits.util.TestPanel;
import com.wits.util.comDate;
import c2s.dto.DtoUtil;
import c2s.dto.IDto;

public class VwEbsAcnt extends VwEbsAcntBase {
    final String actionIdInit = "FEbsAcntInitAction";
    final String actionIdUpdate = "FEbsAcntUpdateAction";
    final String actionIdAdd = "FEbsAcntAddAction";
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
    private DtoPmsAcnt dtoPmsAcnt = new DtoPmsAcnt();
    private Long parentId = null;
    JButton btnSave = null;
    VMComboBox vmComboBoxCurrency;
    VMComboBox vmAcntType;
    VMComboBox vmComboBoxOrg;
    VMComboBox vmComboBoxEbsStatus;

    public VwEbsAcnt() {
        super();
        addUICEvent();
        initUI();
    }

    public void addUICEvent() {
        //捕获事件－－－－
        //EbsMaintain, EbsMaintainHQ
        this.btnSave = this.getButtonPanel().addSaveButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedSave(e);
            }
        });
    }

    private void initUI() {
        //获取初始的数据，包括： Combo{Acnt_status}, Combo{org}, Combo{currency}
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.actionIdInit);

        ReturnInfo returnInfo = accessData(inputInfo);
        if (returnInfo.isError() == true) {
            return;
        }

        Vector accountTypeList = (Vector) returnInfo.getReturnObj(
            "accountTypeList");
        if( accountTypeList == null ){
            accountTypeList = new Vector();
        }
        vmAcntType = new VMComboBox(accountTypeList);
        vmAcntType.addNullElement();
        cmbAcntType.setModel(vmAcntType);

        Vector currencyList = (Vector) returnInfo.getReturnObj("currencyList");
        if( currencyList == null ){
            currencyList = new Vector();
        }
        vmComboBoxCurrency = new VMComboBox(currencyList);
        vmComboBoxCurrency.addNullElement();
        cmbAcntCurrency.setModel(vmComboBoxCurrency);

        Vector orgList = (Vector) returnInfo.getReturnObj("orgList");
        if( orgList == null ){
            orgList = new Vector();
        }
        vmComboBoxOrg = new VMComboBox(orgList);
        vmComboBoxOrg.addNullElement();
        cmbAcntOrganization.setModel(vmComboBoxOrg);

        Object status[] = new Object[] {"Initial", "Approved", "Cancel",
                          "Pending", "Closed"};
        vmComboBoxEbsStatus = new VMComboBox(status);
        //vmComboBoxEbsStatus.addNullElement();
        cmbAcntStatus.setModel(vmComboBoxEbsStatus);
    }

    public void setParameter(Parameter param) {
        super.setParameter(param);
        entryFunType = (String) param.get("entryFunType");
        if (entryFunType == null || entryFunType.length() == 0) {
            entryFunType = "EbsMaintain";
        }

        dtoPmsAcnt = (DtoPmsAcnt) param.get("dto");
        if( dtoPmsAcnt == null ){
            dtoPmsAcnt = new DtoPmsAcnt();
            isParameterValid = false;
        }else{
            isParameterValid = true;
        }

        parentId = (Long) param.get("parentId");
    }

    protected void resetUI() {
        if (dtoPmsAcnt.getStatus() == null || dtoPmsAcnt.getStatus().equals("")) {
            dtoPmsAcnt.setStatus("Initial");
        }

        VWUtil.bindDto2UI(dtoPmsAcnt, this);

        setButtonVisible();
        setEnableMode();
        comFORM.setFocus(this.txtAcntId);
    }

    private void setEnableMode(){
       if ( this.isParameterValid == true
          && ( entryFunType.equals("EbsMaintain") ||
            entryFunType.equals("EbsMaintainHQ")) ) {

            VWUtil.setUIEnabled(this, true);
        }else{

            //EbsView
            VWUtil.setUIEnabled(this, false);
        }
    }

    private void setButtonVisible(){
        //EbsMaintain, EbsMaintainHQ
        if ( this.isParameterValid == true
          && ( entryFunType.equals("EbsMaintain") ||
            entryFunType.equals("EbsMaintainHQ")) ) {

            btnSave.setVisible(true);
        }else{
            btnSave.setVisible(false);
        }
    }

    /////// 段5，保存数据
    public void saveWorkArea() {
        DtoPmsAcnt dtoBak = (DtoPmsAcnt)DtoUtil.deepClone(this.dtoPmsAcnt);

        if (checkModified()) {
            isSaveOk = false;
            if (confirmSaveWorkArea() == true) {
                if (validateData() == true) {
                    isSaveOk =saveData();

                    if( isSaveOk == false ){
                        DtoUtil.copyBeanToBean(dtoPmsAcnt, dtoBak);
                    }
                }else{
                    DtoUtil.copyBeanToBean(dtoPmsAcnt, dtoBak);
                }
            } else {

                isSaveOk = true;
                DtoUtil.copyBeanToBean(dtoPmsAcnt, dtoBak);
            }
        }else{
            isSaveOk = true;
        }
    }

    public void actionPerformedSave(ActionEvent e) {
        DtoPmsAcnt dtoBak = (DtoPmsAcnt)DtoUtil.deepClone(this.dtoPmsAcnt);

        if (checkModified()) {
            if (validateData() == true) {
                if( saveData() == false ){
                    DtoUtil.copyBeanToBean(dtoPmsAcnt, dtoBak);
                }
            }else{
                DtoUtil.copyBeanToBean(dtoPmsAcnt, dtoBak);
            }
        }
    }

    protected boolean checkNecessary(IVWComponent comp, String msg){
        Object value = comp.getUICValue();
        if( value == null || StringUtil.nvl(value).equals("") ==true ){
            comMSG.dispErrorDialog( " Must input " + msg + " .");
            comp.setErrorField(true);
            comFORM.setFocus((JComponent)comp);
            return false;
        }else{
            comp.setErrorField(false);
            return true;
        }
    }

    public boolean validateData() {
        //进行额外的检查
        //必输项：Account Name/Account Code/Account Manager/Account Currency/Account Type/Organization/Status
        if( checkNecessary( this.txtAcntId, "Account code" ) == false
            || checkNecessary( this.txtAcntName, "Account Name"  ) == false
            || checkNecessary( this.txtAcntManager, "Account manager"  ) == false
            || checkNecessary( this.cmbAcntCurrency, "Account currency"  ) == false
            || checkNecessary( this.cmbAcntType, "Account type"  ) == false
            || checkNecessary( this.cmbAcntOrganization, "Organization"  ) == false
            || checkNecessary( this.cmbAcntStatus, "Account status"  ) == false
            ){
             return false;
        }


        //anticipated start and finish
        String acntAnticipatedStart = this.txtAcntAnticipatedStart.getValueText();
        String acntAnticipatedFinish = this.txtAcntAnticipatedFinish.
                                       getValueText();

        if (acntAnticipatedStart.equals("") == false &&
            acntAnticipatedFinish.equals("") == false) {
            if (comDate.compareDate(acntAnticipatedStart, acntAnticipatedFinish) >
                0) {
                comMSG.dispErrorDialog(
                    "The Anticipated start time cannot be bigger than the Anticipated finish time.");
                txtAcntAnticipatedStart.setErrorField(true);
                txtAcntAnticipatedFinish.setErrorField(true);
                txtAcntAnticipatedStart.requestFocus();

                return false;
            }else{
                txtAcntAnticipatedStart.setErrorField(false);
                txtAcntAnticipatedFinish.setErrorField(false);
            }
        }

        //planned start and finish
        String acntPlannedStart = this.txtAcntPlannedStart.getValueText();
        String acntPlannedFinish = this.txtAcntPlannedFinish.getValueText();

        if (acntPlannedStart.equals("") == false &&
            acntPlannedFinish.equals("") == false) {
            if (comDate.compareDate(acntPlannedStart, acntPlannedFinish) > 0) {
                comMSG.dispErrorDialog("The Planned start time cannot be bigger than the Planned finish time.Please save it first.");
                txtAcntPlannedStart.requestFocus();
                txtAcntPlannedStart.setErrorField(true);
                txtAcntPlannedFinish.setErrorField(true);

                return false;
            }else{
                txtAcntPlannedStart.setErrorField(false);
                txtAcntPlannedFinish.setErrorField(false);
            }

        }

        //actual start and finish
        String acntActualStart = this.txtAcntActualStart.getValueText();
        String acntActualFinish = this.txtAcntActualFinish.getValueText();

        if (acntActualStart.equals("") == false &&
            acntActualFinish.equals("") == false) {
            if (comDate.compareDate(acntActualStart, acntActualFinish) > 0) {
                comMSG.dispErrorDialog("The actual start time cannot be bigger than the actual finish time.Please save it first.");
                txtAcntActualStart.requestFocus();
                txtAcntActualStart.setErrorField(true);
                txtAcntActualFinish.setErrorField(true);

                return false;
            }else{
                txtAcntActualStart.setErrorField(false);
                txtAcntActualFinish.setErrorField(false);
            }
        }

        return true;
    }

    private boolean checkModified() {
        VWUtil.convertUI2Dto(dtoPmsAcnt, this);

        return dtoPmsAcnt.isChanged();
    }

    private boolean saveData() {
        InputInfo inputInfo = new InputInfo();

        if (dtoPmsAcnt.getRid() == null) {
            inputInfo.setActionId(this.actionIdAdd);
        } else {
            inputInfo.setActionId(this.actionIdUpdate);
        }

        inputInfo.setInputObj("dto", dtoPmsAcnt);
        inputInfo.setInputObj("parentId", this.parentId);

        ReturnInfo returnInfo = accessData(inputInfo);
        if (returnInfo.isError() == false) {
            DtoPmsAcnt retPmsAcnt = (DtoPmsAcnt) returnInfo.getReturnObj("dto");
            retPmsAcnt.setOp(IDto.OP_NOCHANGE);

            DtoUtil.copyBeanToBean(dtoPmsAcnt, retPmsAcnt);
            fireDataChanged();

            VWUtil.bindDto2UI(dtoPmsAcnt, this);
            comFORM.setFocus(this.txtAcntId);

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
        VwEbsAcnt vwEbsAcnt = new VwEbsAcnt();
        w.addTab("",vwEbsAcnt );

        Parameter para = new Parameter();
        para.put( "entryFunType", "" );
        para.put( "dto", new DtoPmsAcnt() );
        para.put( "parentId", new Long(0) );
        vwEbsAcnt.setParameter(para);
        vwEbsAcnt.refreshWorkArea();
        TestPanel.show( w );
    }

}
