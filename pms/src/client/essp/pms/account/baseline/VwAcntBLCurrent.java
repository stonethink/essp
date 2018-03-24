package client.essp.pms.account.baseline;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.pms.account.DtoAcntBL;
import c2s.essp.pms.account.DtoAcntBLLog;
import c2s.essp.pms.account.DtoPmsAcnt;
import client.essp.common.view.VWWorkArea;
import client.framework.common.Global;
import client.framework.model.VMColumnConfig;
import client.framework.model.VMComboBox;
import client.framework.model.VMTable2;
import client.framework.view.common.comFORM;
import client.framework.view.common.comMSG;
import client.framework.view.vwcomp.VWJPopupEditor;
import client.framework.view.vwcomp.VWJTable;
import client.framework.view.vwcomp.VWJText;
import client.framework.view.vwcomp.VWUtil;
import com.wits.util.Parameter;
import com.wits.util.TestPanel;
import client.essp.pms.account.baseline.compare.VwBaseLineCompare;
import client.essp.common.loginId.VWJLoginId;



public class VwAcntBLCurrent extends VwAcntBLCurrentBase{
    String actionQur = "FAcntBaseLineGenAction";
    String actionSave = "FAcntBaseLineSave";
    //String actionIdUpdate = "FAcntBLAction";

    /**
     * input parameters
     */
    DtoPmsAcnt dtoAccount = null; //传入项目
    /**
     * define common data (globe)
     */
    private DtoAcntBL dtoAcntBL = null;
    private List lApp;

    VMTable2 vmTblAppLog; //表的内容
    VWJTable vwTblAppLog;
    VMComboBox vmComboBoxBLStatus;

    //申请的种类，默认为-1，即不是项目经理且不能发出申请
    //如果Baseline为空，且是项目经理，申请是0
    //如果Baseline不为空，且是项目经理，申请是1
    String appKind = "-1";

    /**
     * 0 : 不能申请Baseline
     * 1 : 可以申请Baseline
     * 2 : 已初始化申请Baseline，但未保存
     * 3 : 可以修改Application信息 (isPM)
     * 4 : 可以修改Approved信息 (isManager)
     * 9 : 已被Approved
     */
    String appCntlType = "0";

    boolean isPM = false;
    boolean isManager = false;
    boolean isApplication = false;
    boolean isAproved = false;


//    boolean isModifyByApplication = false;
//    boolean isModifyByApproved = false;

    /////// 段1，定义界面：定义界面（定义控件，设置控件名称，光标控制顺序）、定义界面事件
    JButton jAddBt = null;
    JButton jSaveBt = null;
    JButton jLoadBt = null;
    //add by zj
    JButton jCompare=null;//点击弹出基线计划比较窗口(最近一个版本和当前)状况对比


    /**
     * default constructor
     */
    public VwAcntBLCurrent() {
        super();

        vmTblAppLog = new VMTable2(
            new Object[][] { {"Filled By", "filledBy",
            VMColumnConfig.UNEDITABLE, new VWJLoginId()}
            , {"Filled Date", "filledDate",
            VMColumnConfig.UNEDITABLE, new VWJText()}
            , {"Status", "appStatus",
            VMColumnConfig.UNEDITABLE, new VWJText()}
            , {"Comment", "remark",
            VMColumnConfig.UNEDITABLE, new VWJText()}
            ,
        });
        vmTblAppLog.setDtoType(DtoAcntBLLog.class);
        vwTblAppLog = new VWJTable(vmTblAppLog);

        TitledBorder titledBorder1 = new TitledBorder(BorderFactory.
            createEtchedBorder(Color.white,
                               new Color(164, 163, 165)), "Approval Log");

        JPanel jpLogList = new JPanel();
        jpLogList.setBorder(titledBorder1);
        jpLogList.setBounds(30, 230, 700, 125);
        jpLogList.setLayout(new BorderLayout());
        jpLogList.add(vwTblAppLog.getScrollPane());

        this.add(jpLogList);

        addUICEvent();
    }

    public void addUICEvent() {
        //捕获事件－－－－
        jCompare=this.getButtonPanel().addButton("milestoneCompare.png");

        jCompare.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                actionPerformedBLCompare(e);
            }
        });
        jCompare.setToolTipText("Milestone Compare");


        jAddBt = this.getButtonPanel().addAddButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedAdd(e);
            }
        });
        jAddBt.setToolTipText("Add Baseline");
        jSaveBt = this.getButtonPanel().addSaveButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedSave(e);
            }
        });

        jLoadBt = this.getButtonPanel().addLoadButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedFlash(e);
            }
        });

    }


    /////// 段2，设置参数：获取传入参数
    public void setParameter(Parameter param) {
        super.setParameter(param);
        isPM = false;
        isManager = false;
        isApplication = false;
        isAproved = false;
        appCntlType = "0";

        if (param.get("dtoAccount") != null) {
            dtoAccount = (DtoPmsAcnt) param.get("dtoAccount");
            if (dtoAccount.isPm()) {
                isPM = true;
            }
            if (dtoAccount.isManagement()) {
                isManager = true;
            }
//            setStatusDisp();
            log.info("AccoutRID=" + dtoAccount.getRid()
                     + ";AccoutID=" + dtoAccount.getId()
                     + ";Manager=" + dtoAccount.getManager()
                     + ";isPM=" + dtoAccount.isPm()
                     + ";isManager=" + dtoAccount.isManagement()
                     );

        } else {
            log.info("Account is null");
        }
    }

    protected void resetUI() {
        appCntlType = "0";
        setStatusDisp();

        log.info("in resetUI appCntlType=" + appCntlType
                 );
        if (dtoAccount != null) {
            InputInfo inputInfo = new InputInfo();

            inputInfo.setActionId(this.actionQur);
            inputInfo.setInputObj("acntRid", dtoAccount.getRid());

            ReturnInfo returnInfo = accessData(inputInfo);

            if (returnInfo.isError() == true) {
                //
                return;
            }

            dtoAcntBL = (DtoAcntBL) returnInfo.getReturnObj("AcntBaseLine");
            if (dtoAcntBL == null) {
                if (isPM) {
                    appCntlType = "1"; //可以申请Baseline
                }
            } else {
                String blAppStatus = dtoAcntBL.getAppStatus();
                if (DtoAcntBL.STATUS_APPROVED.equals(blAppStatus)) {
                    if (isPM) {
                        appCntlType = "1"; //可以申请Baseline
                    }
                    isAproved = true;
                }
            }
            resetPreUI();
            lApp = (List) returnInfo.getReturnObj("BaseLineProve");
            if (lApp == null) {
                lApp = new ArrayList();
            }
            this.vmTblAppLog.setRows(lApp);
        }
    }

    private void resetPreUI() {
        log.info("in resetPreUI appCntlType=" + appCntlType
                 );
        jSaveBt.setVisible(true);
        jSaveBt.setEnabled(false);
        jLoadBt.setVisible(true);
        jLoadBt.setEnabled(true);
        if (appCntlType.equals("1")) {
            jAddBt.setVisible(true);
            jAddBt.setEnabled(true);
        } else {
            jAddBt.setVisible(false);
            jAddBt.setEnabled(false);
        }

        boolean isBaselineIdNull = true;
        if (dtoAcntBL != null) {
            String appStatus = dtoAcntBL.getAppStatus();
            String baselineId = dtoAcntBL.getBaselineId();
            if (baselineId == null || baselineId.equals("")) {
                isBaselineIdNull = true;
            }else{
                isBaselineIdNull = false;
            }

            VWUtil.bindDto2UI(dtoAcntBL, this);
        }

        setInitApplicationInfo(false);
        setInitAproveInfo(false);
        if (!appCntlType.equals("1")) {
            if (isPM) {
                setInitApplicationInfo(true);
            }

            if (isManager
                && !isAproved
                && !isBaselineIdNull) {
                setInitAproveInfo(true);
            }
        }
    }

    private void setInitApplicationInfo(boolean editable) {
        if (editable) {
            jSaveBt.setVisible(true);
            jSaveBt.setEnabled(true);
        }
        if (isApplication && appCntlType.equals("1") && editable) {
            txtCurrentBaseline.setEnabled(true);
        } else {
            txtCurrentBaseline.setEnabled(false);
        }
        txtApplicationDate.setEnabled(editable);
        txaApplicationReason.setEnabled(editable);
        txtSatus.setEnabled(editable);
    }

    private void setInitAproveInfo(boolean editable) {
        if (editable) {
            jSaveBt.setVisible(true);
            jSaveBt.setEnabled(true);
        }
        txtSatus.setEnabled(editable);
        txtApprovedBy.setEnabled(false);
        txtApprovedDate.setEnabled(editable);
        txaComment.setEnabled(editable);
        if (editable) {
            txtApprovedBy.setUICValue(Global.userId);
//            txtApprovedBy.setUICValue(dtoAccount.getLoginId());
            txtApprovedDate.setUICValue(new java.util.Date());
        }
    }


    /////// 段4，事件处理
    public void actionPerformedBLCompare(ActionEvent e){
        VwBaseLineCompare bsComp=new VwBaseLineCompare();
        Parameter param = new Parameter();
        param.put("acntRid",dtoAccount.getRid());
        bsComp.setParameter(param);
        bsComp.refreshWorkArea();
        //参数1表示仅仅只显示OK按钮
        VWJPopupEditor pop=new VWJPopupEditor(this.getParentWindow(),
                                               "Milestone Compare",
                                               bsComp);
         pop.show();
    }


    public void actionPerformedAdd(ActionEvent e) {
        if (!appCntlType.equals("1")) {
            return;
        }
        setStatusDisp();

        isApplication = true;
        isAproved = false;

        String oldBaselineId = "";
        String newBaselineId = "";
        boolean isAdd = true;
        Long acntBLRid = dtoAccount.getRid();
        if (dtoAcntBL != null) {
            oldBaselineId = dtoAcntBL.getBaselineId();
            isAdd = dtoAcntBL.isAdd();
        }
        newBaselineId = DtoAcntBL.getNextBaselineId(oldBaselineId);

        dtoAcntBL = new DtoAcntBL();
        dtoAcntBL.setRid(acntBLRid);
        dtoAcntBL.setAdd(isAdd);
        dtoAcntBL.setAppStatus(DtoAcntBL.STATUS_APPLICATION);

        VWUtil.bindDto2UI(dtoAcntBL, this);
        if (isPM) {
            setInitApplicationInfo(true);
        }
        if (isManager) {
            setInitAproveInfo(true);
        }

        txtCurrentBaseline.setUICValue(newBaselineId);
        txtApplicationDate.setUICValue(new java.util.Date());
        List list = new ArrayList();
        this.vmTblAppLog.setRows(list);

        appCntlType = "2";
    }

    public void actionPerformedSave(ActionEvent e) {
        if (dtoAcntBL == null) {
            return;
        }
        VWUtil.convertUI2Dto(dtoAcntBL, this);
        dtoAcntBL.setLoginUserId(Global.userId);

        if (dtoAcntBL.isChanged()) {
            if (validateData() == true) {
                if (saveData()) {
                } else {
                    resetUI();
                }
            }
        }
    }

    public boolean validateData() {
        //进行额外的检查
        if (isPM) {
            if (this.txtCurrentBaseline.getUICValue().toString().equals("")) {
                comMSG.dispErrorDialog("The Current Baseline could not null");
                comFORM.setFocus(this.txtCurrentBaseline);
                return false;
            }

            if (this.txtApplicationDate.getUICValue().toString().equals("")) {
                comMSG.dispErrorDialog("The Application Date could not null");
                comFORM.setFocus(this.txtApplicationDate);
                return false;
            }
        }

        if (isManager &&
            !DtoAcntBL.STATUS_APPLICATION.equals(dtoAcntBL.getAppStatus())) {
            if (this.txtApprovedBy.getUICValue().toString().equals("")) {
                comMSG.dispErrorDialog("The Approveby could not null");
                comFORM.setFocus(this.txtCurrentBaseline);
                return false;
            }

            if (this.txtApprovedDate.getUICValue().toString().equals("")) {
                comMSG.dispErrorDialog("The Approved Date could not null");
                comFORM.setFocus(this.txtApplicationDate);
                return false;
            }
        }
        return true;
    }

    private boolean saveData() {
        InputInfo inputInfo = new InputInfo();

        inputInfo.setActionId(this.actionSave);

        Boolean bisPM = new Boolean(isPM);
        Boolean bisManager = new Boolean(isManager);
        Boolean bisApplication = new Boolean(isApplication);
        inputInfo.setInputObj("IsPM", bisPM);
        inputInfo.setInputObj("IsManager", bisManager);
        inputInfo.setInputObj("IsApplication", bisApplication);
        inputInfo.setInputObj("AcntBaseLine", this.dtoAcntBL);

        ReturnInfo returnInfo = accessData(inputInfo);

        return returnInfo.isError();
    }

    public void actionPerformedFlash(ActionEvent e) {
        this.resetUI();
    }


    private void setStatusDisp() {
        Object[] objs = null;
        objs = DtoAcntBL.getAllStatus();
        if (isPM) {
            objs = DtoAcntBL.getPMStatus();
        }
        if (isManager) {
            objs = DtoAcntBL.getManagerStatus();
        }
        if (isPM && isManager) {
            objs = DtoAcntBL.getAllStatus();
        }

        vmComboBoxBLStatus = new VMComboBox(objs);
        this.txtSatus.setModel(vmComboBoxBLStatus);
    }

    public static void main(String[] args) {
        DtoPmsAcnt dtoPmsAcnt = new DtoPmsAcnt();
        dtoPmsAcnt.setRid(new Long(4));
        dtoPmsAcnt.setPm(true);
        dtoPmsAcnt.setManagement(true);
        Parameter param = new Parameter();
        param.put("dtoAccount", dtoPmsAcnt);
        Global.userId = "pm&manager";
        VwAcntBLCurrent workArea = new VwAcntBLCurrent();
        workArea.setParameter(param);
        VWWorkArea workArea2 = new VWWorkArea();
        workArea2.addTab("Account", workArea);

        TestPanel.show(workArea2);
        workArea.refreshWorkArea();
    }

}
