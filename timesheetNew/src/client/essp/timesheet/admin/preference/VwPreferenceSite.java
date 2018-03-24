package client.essp.timesheet.admin.preference;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.*;
import javax.swing.border.Border;

import com.wits.util.Parameter;
import com.wits.util.comDate;

import c2s.dto.*;
import c2s.essp.timesheet.preference.DtoPreference;
import c2s.essp.timesheet.weeklyreport.DtoTimeSheet;
import client.essp.common.loginId.VWJLoginId;
import client.essp.common.view.VWGeneralWorkArea;
import client.essp.common.view.ui.MessageUtil;
import client.framework.common.Constant;
import client.framework.model.VMComboBox;
import client.framework.view.common.comMSG;
import client.framework.view.vwcomp.*;

public class VwPreferenceSite extends VWGeneralWorkArea {
	
	private final static String actionId_Load = "FTSLoadPreference";
    private final static String actionId_Save = "FTSSavePreference";
    private final static String actionId_List = "FTSListSite";
    private JButton saveBtn = new JButton();
    private JButton refreshBtn = new JButton();
    private final static Vector sites;
    private Vector exportTypes;
    private VMComboBox exportComModel = null;
    private VMComboBox otEffectCmbModel = null;
    private VMComboBox lvEffectCmbModel = null;
    private VWJComboBox cmbSite = new VWJComboBox(); 
    private boolean firstView = true;

    private DtoPreference dtoPreference;

    public VwPreferenceSite() {
        try {
            jbInit();
        } catch (Exception e) {
            log.error(e);
        }
        addUICEvent();
    }

    private void jbInit(){
        this.setLayout(null);
        
        cmbSite.setName("site");
        
        lblHourDecimal.setText(
                "rsid.timesheet.recordHours");
        lblHourDecimal.setBounds(new Rectangle(21, 10, 450, 22));
        relHourDecimal.setBounds(new Rectangle(540, 10, 47, 22));
        relHourDecimal.setMaxInputDecimalDigit(0);
        relHourDecimal.setName("hrDecimalCnt");
        relHourDecimal.setMaxInputIntegerDigit(4);
        
        
        chkFutureTS.setText("rsid.timesheet.logHoursFuture");
        chkFutureTS.setBounds(new Rectangle(21, 40, 429, 22));
        chkFutureTS.setHorizontalAlignment(SwingConstants.LEFT);
        chkFutureTS.setName("futureTsHrsFlag");
        
        lblFutureTS.setText(
        "rsid.timesheet.futureNumAllowed");
        lblFutureTS.setBounds(new Rectangle(40, 70, 500, 22));
        relFutureTS.setBounds(new Rectangle(540, 70, 47, 22));
        lblFutureSheets.setText("rsid.timesheet.sheet");
        lblFutureSheets.setBounds(new Rectangle(590, 70, 60, 22));
        relFutureTS.setMaxInputDecimalDigit(0);
        relFutureTS.setName("futureTsCnt");
        relFutureTS.setMaxInputIntegerDigit(4);

        lblPastTS.setText("rsid.timesheet.pastNumAllowed");
        lblPastTS.setBounds(new Rectangle(21, 100, 500, 22));
        relPastTS.setBounds(new Rectangle(540, 100, 47, 22));
        lblPastSheets.setText("rsid.timesheet.sheet");
        lblPastSheets.setBounds(new Rectangle(590, 100, 60, 22));
        relPastTS.setMaxInputDecimalDigit(0);
        relPastTS.setName("pastTsCnt");
        relPastTS.setMaxInputIntegerDigit(4);
        
        chkNotStartAct.setText("rsid.timesheet.logHoursNotStart");
        chkNotStartAct.setBounds(new Rectangle(21, 130, 429, 22));
        chkNotStartAct.setHorizontalAlignment(SwingConstants.LEFT);
        chkNotStartAct.setName("nostartTaskHrsFlag");
        
        chkCompletedAct.setText("rsid.timesheet.logHoursCompeleted");
        chkCompletedAct.setBounds(new Rectangle(21, 160, 429, 22));
        chkCompletedAct.setHorizontalAlignment(SwingConstants.LEFT);
        chkCompletedAct.setName("completeTaskHrsFlag");
        
        chkBeforeAct.setText("rsid.timesheet.logHoursBeforeItStartDate");
        chkBeforeAct.setBounds(new Rectangle(21, 190, 429, 22));
        chkBeforeAct.setHorizontalAlignment(SwingConstants.LEFT);
        chkBeforeAct.setName("prestartTaskHrsFlag");
        
        lblNotStartAct.setText("rsid.timesheet.notStart");
        lblNotStartAct.setBounds(new Rectangle(40, 220, 400, 22));
        relNotStartActDay.setBounds(new Rectangle(540, 220, 47, 22));
        lblNotStartDays.setText("rsid.timesheet.days");
        lblNotStartDays.setBounds(new Rectangle(590, 220, 60, 22));
        relNotStartActDay.setMaxInputDecimalDigit(0);
        relNotStartActDay.setName("xferNostartDayCnt");
        relNotStartActDay.setMaxInputIntegerDigit(4);
        
        chkAfterAct.setText("rsid.timesheet.logHoursAfterItFinish");
        chkAfterAct.setBounds(new Rectangle(21, 250, 429, 22));
        chkAfterAct.setHorizontalAlignment(SwingConstants.LEFT);
        chkAfterAct.setName("postendTaskHrsFlag");

        lblCompletedAct.setText("rsid.timesheet.completeActivity");
        lblCompletedAct.setBounds(new Rectangle(40, 280, 400, 22));
        relCompletedActDay.setBounds(new Rectangle(540, 280, 47, 22));
        lblCompleteDays.setText("rsid.timesheet.days");
        lblCompleteDays.setBounds(new Rectangle(590, 280, 60, 22));
        relCompletedActDay.setMaxInputDecimalDigit(0);
        relCompletedActDay.setName("xferCompleteDayCnt");
        relCompletedActDay.setMaxInputIntegerDigit(4);
  
        chkGenTsNeedApproval.setText("rsid.timesheet.genTsNeedApproval");
        chkGenTsNeedApproval.setBounds(new Rectangle(21, 310, 429, 22));
        chkGenTsNeedApproval.setHorizontalAlignment(SwingConstants.LEFT);
        chkGenTsNeedApproval.setName("genTsNeedApproval");
        
        cmbOvertimeEffective.setBounds(new Rectangle(21, 335, 429, 22));
        cmbOvertimeEffective.setName("overtimeEffective");
        cmbLeaveEffective.setBounds(new Rectangle(21, 365, 429, 22));
        cmbLeaveEffective.setName("leaveEffective");
        
        cmbExportType.setBounds(new Rectangle(21, 395, 429, 22));
        cmbExportType.setName("exportType");
        
        
        radPmApproval.setText(
                        "rsid.timesheet.projectManagerApprove");
        radPmApproval.setBounds(new Rectangle(2, 5, 470, 22));

        radRmApproval.setText(
                "rsid.timesheet.resourceManagerApprove");
        radRmApproval.setBounds(new Rectangle(2, 35, 470, 22));

        radPmRmApproval.setText(
                "rsid.timesheet.projectAndResourceManager");
        radPmRmApproval.setBounds(new Rectangle(2, 65, 470, 22));
        
        chkPmBeforeRm.setText(
            "rsid.timesheet.projectManagerApproveBefore");
        chkPmBeforeRm.setBounds(new Rectangle(15, 95, 450, 22));
        chkPmBeforeRm.setHorizontalAlignment(SwingConstants.LEFT);

        grpApprovalLevel.add(radPmApproval);
        grpApprovalLevel.add(radRmApproval);
        grpApprovalLevel.add(radPmRmApproval);
      
        Border hourBorder = BorderFactory.createTitledBorder("");
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(20, 430, 490,130);
        panel.setBorder(hourBorder);
        
        panel.add(radPmApproval);
        panel.add(radRmApproval);
        panel.add(radPmRmApproval);
        panel.add(chkPmBeforeRm);
        
        lblRuu.setText("rsid.common.ruu");
        lblRuu.setBounds(new Rectangle(21, 565, 130, 22));
        txtRuu.setBounds(new Rectangle(160, 565, 140, 22));
        txtRuu.setName("ruu");
        txtRuu.setEnabled(false);
        
        lblRut.setText("rsid.common.rut");
        lblRut.setBounds(new Rectangle(310, 565, 130, 22));
        txtRut.setBounds(new Rectangle(450, 565, 140, 22));
        txtRut.setName("rut");
        
        this.add(lblCompletedAct);
        this.add(lblHourDecimal);
        this.add(relHourDecimal);
        this.add(lblPastTS);
        this.add(relPastTS);
        this.add(lblNotStartAct);
        this.add(relNotStartActDay);
        this.add(relCompletedActDay);
        this.add(chkNotStartAct);
        this.add(chkCompletedAct);
        this.add(chkBeforeAct);
        this.add(chkAfterAct);
        this.add(lblFutureTS);
        this.add(relFutureTS);
        this.add(chkFutureTS);
        this.add(lblCompleteDays);
        this.add(lblNotStartDays);
        this.add(lblFutureSheets);
        this.add(lblPastSheets);
        this.add(chkGenTsNeedApproval);
        this.add(cmbOvertimeEffective);
        this.add(cmbLeaveEffective);
        this.add(panel);
        this.add(lblRuu);
        this.add(lblRut);
        this.add(txtRuu);
        this.add(txtRut);
        this.add(cmbExportType);
    }

    private void addUICEvent() {
    	
    	this.getButtonPanel().add(cmbSite);
    	cmbSite.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				actionPerformedSelectSite();
			}
    	});
    	
        //保存
        saveBtn = this.getButtonPanel().addSaveButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                processSave();
            }
        });
        saveBtn.setToolTipText("rsid.common.save");

        //刷新
        this.getButtonPanel().addLoadButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	firstView = false;
                resetUI();
            }
        });
        refreshBtn.setToolTipText("rsid.common.refresh");
        
        //如果选中未来工时单则可以选择填写存取的工时单数目，如果没选中则变灰
        chkFutureTS.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedSelectFuture(chkFutureTS.isSelected());
            }
        });
        
        //选中则可以填写未开始作业的天数，否则不能填写
        chkBeforeAct.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedSelectBeforeAct(chkBeforeAct.isSelected());
            }
        });
        
//      选中则可以填写已完成作业的天数，否则不能填写
        chkAfterAct.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               actionPerformedSelectAfterAct(chkAfterAct.isSelected());
            }
        });
        
        //Approval Level :只需PM的时候 Disable chkPmBeforeRm
        radPmApproval.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                chkPmBeforeRm.setSelected(false);
                chkPmBeforeRm.setEnabled(false);
            }
        });

        //Approval Level :只需RM的时候 Disable chkPmBeforeRm
        radRmApproval.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                chkPmBeforeRm.setSelected(false);
                chkPmBeforeRm.setEnabled(false);
            }
        });

        //Approval Level :需 PM & RM 的时候 Enabled chkPmBeforeRm
        radPmRmApproval.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                chkPmBeforeRm.setEnabled(true);
            }
        });
    }
    /**
     * 根据所选site显示设定信息
     *
     */
   private void actionPerformedSelectSite() {
	   firstView = false;
	   resetUI();
   }
    /**
     * 如果选中未来工时单则可以选择填写存取的工时单数目，如果没选中则变灰
     * @param flag
     */
    private void actionPerformedSelectFuture(Boolean flag){
        if(flag){
            lblFutureTS.setEnabled(true);
            relFutureTS.setEnabled(true);
            lblFutureSheets.setEnabled(true);
         }else{
             lblFutureTS.setEnabled(false);
             relFutureTS.setEnabled(false);
             lblFutureSheets.setEnabled(false);
         }
    }
     /**
      * 选中则可以填写未开始作业的天数，否则不能填写
      * @param flag
      */
     private void actionPerformedSelectBeforeAct(Boolean flag){
        if(flag){
            lblNotStartAct.setEnabled(true);
            relNotStartActDay.setEnabled(true);
            lblNotStartDays.setEnabled(true);
         }else{
             lblNotStartAct.setEnabled(false);
             relNotStartActDay.setEnabled(false);
             lblNotStartDays.setEnabled(false);
         }
    }
    
     /**
      * 选中则可以填写已完成作业的天数，否则不能填写
      * @param flag
      */
    private void actionPerformedSelectAfterAct(Boolean flag){
        if(flag){
            lblCompletedAct.setEnabled(true);
            lblCompleteDays.setEnabled(true);
            relCompletedActDay.setEnabled(true);
         }else{
             lblCompletedAct.setEnabled(false);
             lblCompleteDays.setEnabled(false);
             relCompletedActDay.setEnabled(false);
         }
    }

    /**
     * 设置参数
     */
    public void setParameter(Parameter param) {
        super.setParameter(param);
    }

    /**
     * 刷新UI
     */
    protected void resetUI() {
        processLoad();
        
        if(exportComModel == null) {
	        exportTypes = new Vector();
	        exportTypes.add(new DtoComboItem(MessageUtil.getMessage("rsid.timesheet.exportTypeSub"), 
	        		                         DtoTimeSheet.STATUS_SUBMITTED));
	        exportTypes.add(new DtoComboItem(MessageUtil.getMessage("rsid.timesheet.exportTypeApp"), 
	        								 DtoTimeSheet.STATUS_APPROVED));
	        exportComModel = new VMComboBox(exportTypes);
	        cmbExportType.setModel(exportComModel);
        }
        
        if(otEffectCmbModel == null) {
        	Vector otEffectVector = new Vector();
        	otEffectVector.add(new DtoComboItem(MessageUtil.getMessage("rsid.timesheet.otEffect.none"), 
	        		                         DtoPreference.OVERTIME_EFFECTIVE_NONE));
        	otEffectVector.add(new DtoComboItem(MessageUtil.getMessage("rsid.timesheet.otEffect.refer"), 
                    DtoPreference.OVERTIME_EFFECTIVE_REFER));
        	otEffectCmbModel = new VMComboBox(otEffectVector);
        	this.cmbOvertimeEffective.setModel(otEffectCmbModel);
        }
        
        if(lvEffectCmbModel == null) {
        	Vector lvEffectVector = new Vector();
        	lvEffectVector.add(new DtoComboItem(MessageUtil.getMessage("rsid.timesheet.lvEffect.none"), 
	        		                         DtoPreference.LEAVE_EFFECTIVE_NONE));
        	lvEffectVector.add(new DtoComboItem(MessageUtil.getMessage("rsid.timesheet.lvEffect.refer"), 
                    DtoPreference.LEAVE_EFFECTIVE_REFER));
        	lvEffectVector.add(new DtoComboItem(MessageUtil.getMessage("rsid.timesheet.lvEffect.generate"), 
                    DtoPreference.LEAVE_EFFECTIVE_GENERATE));
        	lvEffectCmbModel = new VMComboBox(lvEffectVector);
        	this.cmbLeaveEffective.setModel(lvEffectCmbModel);
        }
        
        VWUtil.bindDto2UI(dtoPreference, this);
        String level = dtoPreference.getTsApprovalLevel();
        actionPerformedSelectFuture(chkFutureTS.isSelected());
        actionPerformedSelectBeforeAct(chkBeforeAct.isSelected());
        actionPerformedSelectAfterAct(chkAfterAct.isSelected());
        txtRut.setText(comDate.dateToString(dtoPreference.getRut(), "yyyy-MM-dd HH:mm:ss"));
        if(DtoPreference.APPROVAL_LEVEL_PM.equals(level)) {
            radPmApproval.setSelected(true);
            radRmApproval.setSelected(false);
            radPmRmApproval.setSelected(false);
            chkPmBeforeRm.setSelected(false);
            chkPmBeforeRm.setEnabled(false);
        } else if(DtoPreference.APPROVAL_LEVEL_PM_AND_RM.equals(level)) {
            radPmApproval.setSelected(false);
            radRmApproval.setSelected(false);
            radPmRmApproval.setSelected(true);
            chkPmBeforeRm.setSelected(false);
            chkPmBeforeRm.setEnabled(true);
        } else if(DtoPreference.APPROVAL_LEVEL_PM_BEFORE_RM.equals(level)) {
            radPmApproval.setSelected(false);
            radRmApproval.setSelected(false);
            radPmRmApproval.setSelected(true);
            chkPmBeforeRm.setSelected(true);
            chkPmBeforeRm.setEnabled(true);
        } else if(DtoPreference.APPROVAL_LEVEL_RM.equals(level)) {
            radPmApproval.setSelected(false);
            radRmApproval.setSelected(true);
            radPmRmApproval.setSelected(false);
            chkPmBeforeRm.setSelected(false);
            chkPmBeforeRm.setEnabled(false);
        }
        
    }

    /**
     * 加载数据
     */
    private void processLoad() {
        InputInfo inputInfo = null;
        ReturnInfo returnInfo = null;
        if(firstView) {
        	inputInfo = new InputInfo();
        	inputInfo.setActionId(actionId_List);
            returnInfo = this.accessData(inputInfo);
            if(returnInfo.isError()) {
            	cmbSite.setModel(new VMComboBox(new Vector()));
            } else {
            	Vector v = (Vector) returnInfo.getReturnObj(DtoPreference.DTO_SITE);
            	cmbSite.setModel(new VMComboBox(v));
            }
        }
        inputInfo = new InputInfo();
        inputInfo.setActionId(actionId_Load);
        inputInfo.setInputObj(DtoPreference.DTO_SITE, cmbSite.getUICValue());
        returnInfo = this.accessData(inputInfo);
        if(returnInfo.isError()) {
            dtoPreference = new DtoPreference();
        } else {
            dtoPreference = (DtoPreference)
                            returnInfo.getReturnObj(DtoPreference.DTO);
        }
    }

    /**
     * 保存数据
     */
    private void processSave() {
        String level = getApprovalLevel();
        String processType = "";
         int option = 1;
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(actionId_Load);
        inputInfo.setInputObj(DtoPreference.DTO_SITE, cmbSite.getUICValue());
        ReturnInfo returnInfo = this.accessData(inputInfo);
        String oldLevel = "";
       if(!returnInfo.isError()) {
           DtoPreference dtoPreference = (DtoPreference)
                           returnInfo.getReturnObj(DtoPreference.DTO);
           oldLevel = dtoPreference.getTsApprovalLevel();
       }
       //审核流程由PM在RM之前审核变成PM单独审核或RM单独审核
       //需要做相应的状态处理
       //将TS_PMApprov状态的改为TS_Approv
       if(DtoPreference.APPROVAL_LEVEL_PM_BEFORE_RM.equals(oldLevel)
          &&(DtoPreference.APPROVAL_LEVEL_PM.equals(level)
           ||DtoPreference.APPROVAL_LEVEL_RM.equals(level))) {
           processType = oldLevel+level;
           option = comMSG.dispConfirmDialog(
                   "error.client.VwPreference.changeApproval");
       }
       //审核流程由PM和RM不分先后顺序审核变成PM单独审核或RM单独审核
       //将TS_PMApprov状态和TS_RMApprov的改为TS_Approv
       if(DtoPreference.APPROVAL_LEVEL_PM_AND_RM.equals(oldLevel)
          &&(DtoPreference.APPROVAL_LEVEL_PM.equals(level)
           ||DtoPreference.APPROVAL_LEVEL_RM.equals(level))) {
           processType = oldLevel+level;
           option = comMSG.dispConfirmDialog(
                   "error.client.VwPreference.changeApproval");
       }
       //审核流程由PM和RM不分先后顺序审核变成PM在RM之前审核
       //将TS_RMApprov的改为TS_Approv
       if(DtoPreference.APPROVAL_LEVEL_PM_AND_RM.equals(oldLevel)
        &&DtoPreference.APPROVAL_LEVEL_PM_BEFORE_RM.equals(level)) {
          processType = oldLevel+level;
          option = comMSG.dispConfirmDialog(
                   "error.client.VwPreference.changeApprovalBefore");
       }
       if (option != Constant.OK) {
           return;
       }
        VWUtil.convertUI2Dto(dtoPreference, this);
        dtoPreference.setTsApprovalLevel(getApprovalLevel());
        dtoPreference.setSite((String)cmbSite.getUICValue());
        if(cmbExportType.getUICValue() == null) {
        	dtoPreference.setExportType(DtoTimeSheet.STATUS_APPROVED);
        }
        inputInfo = new InputInfo();
        inputInfo.setInputObj(DtoPreference.DTO, dtoPreference);
        inputInfo.setInputObj(DtoPreference.PROCESS_TYPE, processType);
        inputInfo.setActionId(actionId_Save);
        inputInfo.setInputObj(DtoPreference.DTO_SITE, cmbSite.getUICValue());
        ReturnInfo returnInfoSave = this.accessData(inputInfo);
        if(!returnInfoSave.isError()) {
        	resetUI();//保存后刷新页面
        	comMSG.dispMessageDialog("rsid.common.saveComplete");
        }
        
    }


    /**
     * 根据radRmApproval, radPmRmApproval, chkPmBeforeRm来确定Approval Level
     * @return String
     */
    private String getApprovalLevel() {
        if (radPmApproval.isSelected()) {
            return DtoPreference.APPROVAL_LEVEL_PM;
        } else if (radRmApproval.isSelected()) {
            return DtoPreference.APPROVAL_LEVEL_RM;
        } else if (chkPmBeforeRm.isSelected()) {
            return DtoPreference.APPROVAL_LEVEL_PM_BEFORE_RM;
        } else {
            return DtoPreference.APPROVAL_LEVEL_PM_AND_RM;
        }
    }




    VWJLabel lblHourDecimal = new VWJLabel();
    VWJLabel lblFutureTS = new VWJLabel();
    VWJLabel lblFutureSheets = new VWJLabel();
    VWJLabel lblPastSheets = new VWJLabel();
    VWJLabel lblPastTS = new VWJLabel();
    VWJReal relHourDecimal = new VWJReal();
    VWJReal relFutureTS = new VWJReal();
    VWJReal relPastTS = new VWJReal();
    VWJRadioButton radRmApproval = new VWJRadioButton();
    VWJRadioButton radPmRmApproval = new VWJRadioButton();
    VWJCheckBox chkPmBeforeRm = new VWJCheckBox();
    VWJLabel lblNotStartAct = new VWJLabel();
    VWJLabel lblNotStartDays = new VWJLabel();
    VWJLabel lblCompleteDays = new VWJLabel();
    VWJLabel lblCompletedAct = new VWJLabel();
    VWJReal relNotStartActDay = new VWJReal();
    VWJReal relCompletedActDay = new VWJReal();
    VWJCheckBox chkFutureTS = new VWJCheckBox();
    VWJCheckBox chkNotStartAct = new VWJCheckBox();
    VWJCheckBox chkCompletedAct = new VWJCheckBox();
    VWJCheckBox chkBeforeAct = new VWJCheckBox();
    VWJCheckBox chkAfterAct = new VWJCheckBox();
    VWJCheckBox chkGenTsNeedApproval = new VWJCheckBox();
    VWButtonGroup grpApprovalLevel = new VWButtonGroup();
    VWJRadioButton radPmApproval = new VWJRadioButton();
    VWJLabel lblRuu = new VWJLabel();
    VWJLabel lblRut = new VWJLabel();
    VWJLoginId txtRuu = new VWJLoginId();
    VWJLabel txtRut = new VWJLabel();
    VWJLabel lblExportType = new VWJLabel();
    VWJComboBox cmbExportType = new VWJComboBox();
    VWJComboBox cmbOvertimeEffective= new VWJComboBox();
    VWJComboBox cmbLeaveEffective= new VWJComboBox();
    

    static {
        sites = new Vector();
        sites.add(new DtoComboItem("--"+DtoPreference.DTO_SITE_TP+"--", DtoPreference.DTO_SITE_TP));
    }

}
