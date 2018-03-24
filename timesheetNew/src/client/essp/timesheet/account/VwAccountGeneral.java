package client.essp.timesheet.account;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.*;

import c2s.dto.*;
import c2s.essp.timesheet.account.DtoAccount;
import c2s.essp.timesheet.code.DtoCodeType;
import client.essp.common.loginId.VWJLoginId;
import client.essp.common.view.VWGeneralWorkArea;
import client.framework.model.VMComboBox;
import client.framework.view.common.comFORM;
import client.framework.view.vwcomp.*;

import com.wits.util.Parameter;

/**
 * <p>Title: Account general</p>
 *
 * <p>Description: 项目一般信息视图，在此可以设置此项目下成员可以填写的Job Code类别</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class VwAccountGeneral extends VWGeneralWorkArea {

    private final static String actionId_SaveAccount = "FTSSaveAccountGeneral";
    private final static String actionId_LoadAccount = "FTSLoadAccountGeneral";

    private DtoAccount dtoAccount = null;


    VWJLabel lblAcntOrganization = new VWJLabel();
    VWJText txtAcntOrganization = new VWJText();
    VWJLabel lblBrief = new VWJLabel();
    VWJText txtAcntType = new VWJText();
    VWJLabel lblJobCodeType = new VWJLabel();
    VWJComboBox cmbJobCodeType = new VWJComboBox();
    VWJLabel lblLeaveCodeType = new VWJLabel();
    VWJComboBox cmbLeaveCodeType = new VWJComboBox();
    VWJLabel lblAcntManager = new VWJLabel();
    VWJLoginId txtAcntManager = new VWJLoginId();

    VWJLabel lblAcntType = new VWJLabel();
    VWJTextArea txaAcntBrief = new VWJTextArea();
    VWJDate txtAcntActualFinish = new VWJDate();
    VWJLabel lblAcntStatus = new VWJLabel();
    VWJDate txtAcntPlannedFinish = new VWJDate();
    VWJLabel lblPlannedFinish = new VWJLabel();
    VWJText txtAcntStatus = new VWJText();
    VWJLabel lblActualFinish = new VWJLabel();
    VWJDate txtAcntPlannedStart = new VWJDate();
    VWJLabel lblPlannedStart = new VWJLabel();
    VWJDisp txtAcntName = new VWJDisp();
    VWJLabel lblAcntName = new VWJLabel();
    VWJDisp txtAcntId = new VWJDisp();
    VWJLabel lblAcntId = new VWJLabel();
    VWJLabel lblActualStart = new VWJLabel();
    VWJDate txtAcntActualStart = new VWJDate();
    VWJLabel lblMethod = new VWJLabel();
    VWJComboBox cmbMethod = new VWJComboBox();
    VWJLabel lblIsMail = new VWJLabel();
    VWJCheckBox chekIsMail = new VWJCheckBox();
    VWJLabel lblP6Id = new VWJLabel();
    VWJText txtP6Id = new VWJText();

   
    public VwAccountGeneral() {
        try {
            jbInit();
            setTabOrder();
            setEnterOrder();
            setUIComponentName();
        } catch (Exception e) {
            log.error(e);
        }
        addUICEvent();
    }

    /**
     * 初始化界面
     * @throws Exception
     */
    private void jbInit() throws Exception {
        this.setLayout(null);
        this.setPreferredSize(new Dimension(700, 280));
        
        txtAcntId.setBounds(new Rectangle(169, 24, 212, 20));
        lblAcntId.setText("rsid.timesheet.accountCode");
        lblAcntId.setBounds(new Rectangle(35, 24, 128, 20));
        txtAcntId.setEnabled(false);
        
        lblAcntName.setText("rsid.timesheet.accountName");
        lblAcntName.setBounds(new Rectangle(402, 24, 133, 20));
        txtAcntName.setBounds(new Rectangle(544, 24, 212, 20));
        txtAcntName.setEnabled(false);
        
        lblAcntManager.setText("rsid.timesheet.accountManager");
        lblAcntManager.setBounds(new Rectangle(35, 49, 128, 20));
        txtAcntManager.setBounds(new Rectangle(169, 49, 212, 20));
        txtAcntManager.setEnabled(false);

        lblAcntOrganization.setText("rsid.timesheet.organization");
        lblAcntOrganization.setBounds(new Rectangle(402, 49, 133, 20));
        txtAcntOrganization.setBounds(new Rectangle(544, 49, 212, 20));
        txtAcntOrganization.setEnabled(false);

        lblAcntType.setText("rsid.timesheet.accountType");
        lblAcntType.setBounds(new Rectangle(35, 74, 128, 20));
        txtAcntType.setBounds(new Rectangle(169, 74, 212, 20));
        txtAcntType.setEnabled(false);
        
        lblAcntStatus.setText("rsid.timesheet.closed");
        lblAcntStatus.setBounds(new Rectangle(402, 74, 128, 20));
        txtAcntStatus.setBounds(new Rectangle(544, 74, 212, 20));
        txtAcntStatus.setEnabled(false);
        
        lblPlannedStart.setText("rsid.timesheet.plannedStart");
        lblPlannedStart.setBounds(new Rectangle(35, 97, 104, 20));
        txtAcntPlannedStart.setBounds(new Rectangle(169, 97, 212, 20));
        txtAcntPlannedStart.setEnabled(false);
        
        lblPlannedFinish.setText("rsid.timesheet.plannedFinish");
        lblPlannedFinish.setBounds(new Rectangle(402, 97, 133, 20));
        txtAcntPlannedFinish.setBounds(new Rectangle(544, 97, 212, 20));
        txtAcntPlannedFinish.setEnabled(false);
        
        lblActualStart.setText("rsid.timesheet.actualStart");
        lblActualStart.setBounds(new Rectangle(35, 123, 113, 20));
        txtAcntActualStart.setBounds(new Rectangle(169, 123, 212, 20));
        txtAcntActualStart.setEnabled(false);
        
        lblActualFinish.setText("rsid.timesheet.actualFinish");
        lblActualFinish.setBounds(new Rectangle(403, 123, 133, 20));
        txtAcntActualFinish.setBounds(new Rectangle(544, 123, 212, 20));
        txtAcntActualFinish.setEnabled(false);

        lblJobCodeType.setText("rsid.timesheet.jobCodeType");
        lblJobCodeType.setBounds(new Rectangle(35, 148, 103, 20));
        cmbJobCodeType.setBounds(new Rectangle(169, 148, 212, 20));
        
        lblLeaveCodeType.setText("rsid.timesheet.leaveCodeType");
        lblLeaveCodeType.setBounds(new Rectangle(402, 148, 103, 20));
        cmbLeaveCodeType.setBounds(new Rectangle(544, 148, 212, 20));
        
        lblMethod.setText("rsid.timesheet.methodOlogy");
        lblMethod.setBounds(new Rectangle(402, 148, 103, 20));
        cmbMethod.setBounds(new Rectangle(544, 148, 212, 20));

        lblBrief.setText("rsid.common.brief");
        lblBrief.setBounds(new Rectangle(35, 180, 101, 20));
        txaAcntBrief.setBounds(new Rectangle(169, 174, 585, 70));
        txaAcntBrief.setEnabled(false);
        
        lblIsMail.setText("rsid.timesheet.isMailDaily");
        lblIsMail.setBounds(new Rectangle(35, 245, 103, 20));
        chekIsMail.setBounds(new Rectangle(169, 245, 16, 20));
        chekIsMail.setEnabled(false);
        
        lblP6Id.setText("rsid.timesheet.p6Id");
        lblP6Id.setBounds(new Rectangle(402, 245, 103, 20));
        txtP6Id.setBounds(new Rectangle(544, 245, 212, 20));
        lblP6Id.setVisible(false);
        txtP6Id.setVisible(false);
        
        this.add(lblAcntType);
        this.add(lblAcntId);
        this.add(lblAcntManager);
        this.add(txtAcntId);
        this.add(txtAcntManager);
        this.add(txtAcntName);
        this.add(lblAcntName);
        this.add(lblJobCodeType);
        this.add(lblLeaveCodeType);
        this.add(lblActualStart);
        this.add(lblPlannedStart);
        this.add(lblBrief);
        this.add(txtAcntType);
        this.add(txtAcntPlannedStart);
        this.add(txtAcntActualStart);
        this.add(cmbJobCodeType);
        this.add(cmbLeaveCodeType);
        this.add(txaAcntBrief);
        this.add(lblAcntStatus);
        this.add(lblPlannedFinish);
        this.add(lblAcntOrganization);
        this.add(lblActualFinish);
        this.add(txtAcntPlannedFinish);
        this.add(txtAcntStatus);
        this.add(txtAcntOrganization);
        this.add(txtAcntActualFinish);
        this.add(lblMethod);
        this.add(cmbMethod);
        this.add(lblIsMail);
        this.add(chekIsMail);
        this.add(lblP6Id);
        this.add(txtP6Id);
    }

    private void setTabOrder() {
        List compList = new ArrayList();
        compList.add(txtAcntId);
        compList.add(txtAcntName);
        compList.add(txtAcntManager);
        compList.add(cmbJobCodeType);
        compList.add(cmbLeaveCodeType);
        compList.add(txtAcntType);
        compList.add(txtAcntOrganization);
        compList.add(txtAcntPlannedStart);
        compList.add(txtAcntPlannedFinish);
        compList.add(txtAcntActualStart);
        compList.add(txtAcntActualFinish);
        compList.add(txtAcntStatus);
        compList.add(cmbMethod);
        compList.add(txaAcntBrief);
        compList.add(chekIsMail);
        compList.add(txtP6Id);

        comFORM.setTABOrder(this, compList);
    }

    private void setEnterOrder() {
        List compList = new ArrayList();
        compList.add(txtAcntId);
        compList.add(txtAcntName);
        compList.add(txtAcntManager);
        compList.add(cmbJobCodeType);
        compList.add(cmbLeaveCodeType);
        compList.add(txtAcntType);
        compList.add(txtAcntOrganization);
        compList.add(txtAcntPlannedStart);
        compList.add(txtAcntPlannedFinish);
        compList.add(txtAcntActualStart);
        compList.add(txtAcntActualFinish);
        compList.add(txtAcntStatus);
        compList.add(cmbMethod);
        compList.add(txaAcntBrief);
        compList.add(chekIsMail);
        compList.add(txtP6Id);
        
        comFORM.setEnterOrder(this, compList);
    }

    private void setUIComponentName() {
        txtAcntId.setName("accountId");
        txtAcntName.setName("accountName");
        txtAcntManager.setName("manager");
        cmbJobCodeType.setName("codeTypeRid");
        cmbLeaveCodeType.setName("leaveCodeTypeRid");
        txtAcntType.setName("accountType");
        txtAcntOrganization.setName("orgCode");
        txtAcntPlannedStart.setName("plannedStart");
        txtAcntPlannedFinish.setName("plannedFinish");
        txtAcntActualStart.setName("actualStart");
        txtAcntActualFinish.setName("actualFinish");
        txtAcntStatus.setName("statusName");
        cmbMethod.setName("methodRid");
        txaAcntBrief.setName("accountBrief");
        chekIsMail.setName("isMail");
        txtP6Id.setName("p6Id");
    }

    /**
     * 事件处理
     */
    private void addUICEvent() {
 
    }

    /**
     * 保存项目的一般信息
     *   目前只涉及到Job Code Type
     */
    public boolean processSaveAccountGeneral() {
        VWUtil.convertUI2Dto(dtoAccount, this);
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(actionId_SaveAccount);
        inputInfo.setInputObj(DtoAccount.DTO, dtoAccount);
        ReturnInfo returnInfo = this.accessData(inputInfo);
        if(returnInfo.isError()) {
     	   return false;
        }
        DtoComboItem item =  (DtoComboItem) cmbJobCodeType.getSelectedItem();
        dtoAccount.setCodeType(item.getItemName());
        return true;
    }

    /**
     * 参数设置
     */
    public void setParameter(Parameter param) {
        super.setParameter(param);
        dtoAccount = (DtoAccount) param.get(DtoAccount.DTO);
    }

    /**
     * 刷新界面
     */
    protected void resetUI() {
        VWUtil.clearUI(this);
        if(dtoAccount != null) {
        	cmbJobCodeType.setEnabled(true);
     	    cmbLeaveCodeType.setEnabled(true);
     	    cmbMethod.setEnabled(true);
     	   txtP6Id.setEnabled(true);
            InputInfo inputInfo = new InputInfo();
            inputInfo.setActionId(actionId_LoadAccount);
            inputInfo.setInputObj(DtoAccount.DTO_RID,
                                  dtoAccount.getRid());
            ReturnInfo returnInfo = this.accessData(inputInfo);
            if (returnInfo.isError())
				return;
			dtoAccount = (DtoAccount) returnInfo.getReturnObj(DtoAccount.DTO);
			Vector codeTypeItem = (Vector) returnInfo
												.getReturnObj(DtoCodeType.DTO);
			VMComboBox item = new VMComboBox(codeTypeItem);
			cmbJobCodeType.setModel(item);
			if (DtoAccount.DEPT_FLAG_DEPT.equals(dtoAccount.getDeptFlag())) {
				Vector leaveCodeTypeItem = (Vector) returnInfo
						.getReturnObj(DtoCodeType.DTO_IS_LEAVE_TYPE);
				
				item = new VMComboBox(leaveCodeTypeItem);
				cmbLeaveCodeType.setModel(item);
				lblLeaveCodeType.setVisible(true);
				cmbLeaveCodeType.setVisible(true);
				lblMethod.setVisible(false);
				cmbMethod.setVisible(false);
				chekIsMail.setEnabled(false);
				lblP6Id.setVisible(false);
				txtP6Id.setVisible(false);
			} else {
				lblLeaveCodeType.setVisible(false);
				cmbLeaveCodeType.setVisible(false);
				Vector methods = (Vector)returnInfo.getReturnObj(DtoAccount.DTO_METHOD);
				item = new VMComboBox(methods);
				cmbMethod.setModel(item);
				lblMethod.setVisible(true);
				cmbMethod.setVisible(true);
				chekIsMail.setEnabled(true);
				lblP6Id.setVisible(true);
				txtP6Id.setVisible(true);
			}
            VWUtil.bindDto2UI(dtoAccount, this);
        } else {
    	   cmbJobCodeType.setEnabled(false);
    	   cmbLeaveCodeType.setEnabled(false);
    	   cmbMethod.setEnabled(false);
    	   chekIsMail.setEnabled(false);
    	   txtP6Id.setEnabled(false);
       }
        
    }

	public DtoAccount getDtoAccount() {
		return dtoAccount;
	}
}
