package client.essp.pms.account;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComponent;

import c2s.dto.DtoUtil;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.pms.account.DtoAcntKEY;
import c2s.essp.pms.account.DtoPmsAcnt;
import client.framework.model.VMComboBox;
import client.framework.view.common.comFORM;
import client.framework.view.common.comMSG;
import client.framework.view.vwcomp.IVWComponent;
import client.framework.view.vwcomp.VWUtil;
import com.wits.util.StringUtil;
import com.wits.util.comDate;
import client.essp.pms.modifyplan.VwBaseLinePlanModify;
import com.wits.util.Parameter;
import client.framework.view.vwcomp.VWJPopupEditor;
import c2s.essp.pms.account.DtoAcntBL;
import client.essp.common.excelUtil.VWJButtonExcel;
import c2s.essp.common.excelUtil.IExcelParameter;
import client.framework.common.Global;
import client.essp.pms.account.template.apply.VwTemplateApply;
import client.essp.pms.account.template.apply.VwApplyTemplatePreview;
import client.framework.view.vwcomp.VWJWizardEditor;
import javax.swing.WindowConstants;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import client.framework.common.Constant;
import com.wits.util.ProcessVariant;
import java.util.List;
import c2s.essp.pms.account.DtoAcntBLApp;
import client.essp.pms.account.template.apply.VwPcbAndTailorBase;
import client.framework.view.event.DataChangedListener;


public class VwAccountGeneral extends VwAccountGeneralBase {
    static final String actionIdUpdate = "FAccountUpdateAction";
    static final String actionIdSaveAs = "FAccountSaveAsOpsAction";
    static final String actionIdInit = "FAccountInitAction";
    static final String actionBaseLine = "FAcntBaseLineGenAction";
    static final String actionIdExcel = "FAcPmsExport";
    static final String actionIdChoose = "FAccountChooseAction";
    static final String actioncheck = "FAccountCheckAction";
    /**
     * define common data (globe)
     */
    private DtoPmsAcnt dataPmsAcc = new DtoPmsAcnt();
    private boolean isSaveOk = true;
    private boolean isParameterValid = true;
    private Long acntrid = null;
    JButton btnSave = null;
    JButton btnSaveAs = null;
    boolean isNeedSave = true;
    JButton importTempBtn = null;
    JButton modifyTempBtn = null;
    JButton exportBtn = null;

    public VwAccountGeneral() {
        super();
        try {
            addUICEvent();
            initUI();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void initUI() {
        //��ȡ��ʼ�����ݣ������� Combo{Acnt_status}, Combo{org}, Combo{currency}
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.actionIdInit);

        ReturnInfo returnInfo = accessData(inputInfo);
        if (returnInfo.isError() == true) {
            return;
        }

        Vector accountTypeList = (Vector) returnInfo.getReturnObj(
            "accountTypeList");
        if (accountTypeList == null) {
            accountTypeList = new Vector();
        }
        VMComboBox vmAcntType = new VMComboBox(accountTypeList);
        vmAcntType.addNullElement();
        cmbAcntType.setModel(vmAcntType);

        Vector currencyList = (Vector) returnInfo.getReturnObj("currencyList");
        if (currencyList == null) {
            currencyList = new Vector();
        }
        VMComboBox vmComboBoxCurrency = new VMComboBox(currencyList);
        vmComboBoxCurrency.addNullElement();
        cmbAcntCurrency.setModel(vmComboBoxCurrency);

        Vector orgList = (Vector) returnInfo.getReturnObj("orgList");
        if (orgList == null) {
            orgList = new Vector();
        }
        VMComboBox vmComboBoxOrg = new VMComboBox(orgList);
        vmComboBoxOrg.addNullElement();
        cmbAcntOrganization.setModel(vmComboBoxOrg);

        Vector accountStatusList = (Vector) returnInfo.getReturnObj(
            "accountStatusList");
        VMComboBox vmComboBoxEbsStatus = new VMComboBox(accountStatusList);
        vmComboBoxEbsStatus.addNullElement();
        cmbAcntStatus.setModel(vmComboBoxEbsStatus);

        setButtonVisible();
        setEnableMode();
    }

    public void addUICEvent() {
        //�����¼���������
        txtAcntName.addDataChangedListener(new DataChangedListener(){
            public void processDataChanged() {
                String acntId = txtAcntName.getText();
                if(!dataPmsAcc.getId().equalsIgnoreCase(acntId)){
                    if(checkAcntIdExist(acntId)){
                        txtAcntName.setText(dataPmsAcc.getId());
                    }
                }
            }

        });
        btnSave = this.getButtonPanel().addSaveButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedSave(e);
            }
        });
        //osp ���Ϊ
        btnSaveAs = this.getButtonPanel().addButton("saveAs.png");
        btnSaveAs.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedOspSaveAs();
            }
        });
        btnSaveAs.setToolTipText("Save as");
        //����ģ��
        importTempBtn = this.getButtonPanel().addButton("applyTemplate.png");
        importTempBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean successFlag = actionPerformedImport(e);
//                if (successFlag) {
//                    actionPerformedModify();
//                }
            }
        });
        importTempBtn.setToolTipText("Apply Template");

        //�޸�ģ��
//        modifyTempBtn = this.getButtonPanel().addButton("modifyBL.png");
//        modifyTempBtn.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                actionPerformedModify();
//            }
//        });
//        modifyTempBtn.setToolTipText("Modify BaseLine");

        exportBtn = new VWJButtonExcel(new IExcelParameter() {
            public String getUrlAddress() {
                StringBuffer sb = new StringBuffer(Global.appRoot);
                sb.append(IExcelParameter.DEFAULT_EXCEL_JSP_ADDRESS);
                sb.append("?");
                sb.append(IExcelParameter.ACTION_ID);
                sb.append("=");
                sb.append(actionIdExcel);
                sb.append("&");
                sb.append("acntRid=");
                sb.append(acntrid.longValue());
                return sb.toString();
            }
        });
        this.getButtonPanel().addButton(exportBtn);
        exportBtn.setToolTipText("Export Data");

    }

    public void actionPerformedSave(ActionEvent e) {
        isNeedSave = true;
        DtoPmsAcnt dtoBak = (DtoPmsAcnt) DtoUtil.deepClone(dataPmsAcc);
        if (checkModified()) {
            if (validateData() == true) {
                if (saveData() == false) {
                    DtoUtil.copyBeanToBean(dataPmsAcc, dtoBak);
                }
            } else {
                DtoUtil.copyBeanToBean(dataPmsAcc, dtoBak);
            }
        }
    }

    public void actionPerformedOspSaveAs() {
        isNeedSave = true;
        DtoPmsAcnt dtoBak = (DtoPmsAcnt) DtoUtil.deepClone(dataPmsAcc);
        if (validateData() == true) {
            if (saveAsOSP() == false) {
                DtoUtil.copyBeanToBean(dataPmsAcc, dtoBak);
            }
        } else {
            DtoUtil.copyBeanToBean(dataPmsAcc, dtoBak);
        }
    }

    public boolean actionPerformedImport(ActionEvent e) {
        Parameter param = new Parameter();
        param.put("accountType", dataPmsAcc.getType());
        param.put("accountRid", dataPmsAcc.getRid());

        //ʵ������һ�ſ�Ƭ
        VwTemplateApply vwTemplateApply = new VwTemplateApply();
        vwTemplateApply.setParameter(param);
        vwTemplateApply.refreshWorkArea();

        //ʵ�����ڶ��ſ�Ƭ
        VwApplyTemplatePreview vwApplyTemplatePreview = new
            VwApplyTemplatePreview();
        vwApplyTemplatePreview.setParameter(param);

        //ʵ���������ſ�Ƭ
        VwPcbAndTailorBase vwPcbAndTailor = new VwPcbAndTailorBase();
        vwPcbAndTailor.setParameter(param);

        Object[][] obj = new Object[][] { {vwTemplateApply, vwTemplateApply},
                         {vwApplyTemplatePreview, vwApplyTemplatePreview},
                         {vwPcbAndTailor, vwPcbAndTailor}
        };
        VWJWizardEditor vWJWizardEditor = new VWJWizardEditor(this.
            getParentWindow(),
            "Apply Template", obj);
        vWJWizardEditor.show();
        if (param.get("isFinish") != null) {
            return true;
        }
        return false;
    }

    protected void resetUI() {
        if (dataPmsAcc.getStatus() == null || dataPmsAcc.getStatus().equals("")) {
            if (!dataPmsAcc.isTemplate()) {
                dataPmsAcc.setStatus("Initial");
            } else {
                dataPmsAcc.setStatus("");
            }
        }

        VWUtil.bindDto2UI(dataPmsAcc, this);

        setButtonVisible();
        setEnableMode();

        comFORM.setFocus(this.txtAcntId);
    }

    private void setButtonVisible() {
        //ֻ��EBS��Manager����Ȩ��ȥ����Ŀ�Ļ�����Ϣ
        //��Ŀ����ֻ��Ȩ��ȥ�޸ļƻ�
        if (isParameterValid == true && dataPmsAcc.isManagement() == true) {
            btnSave.setVisible(true);
        } else {
            btnSave.setVisible(false);
        }
        //���ѡ�����OSP,��btnSaveAs�ɼ�
        if (dataPmsAcc.isOSP()) {
            btnSaveAs.setVisible(true);
        } else {
            btnSaveAs.setVisible(false);
        }
        //EBS��Manager��PM�����Ե�����
        if ((isParameterValid == true && dataPmsAcc.isManagement() == true) ||
            (isParameterValid && dataPmsAcc.isPm())) {
            exportBtn.setVisible(true);
        } else {
            exportBtn.setVisible(false);
        }
        //���߼ƻ�Ӧ��ģ��EBS��Manager��PM�ɵ�����Ҫ�ڻ��߼ƻ��������֮ǰ���ܵ�
        if ((isParameterValid == true && dataPmsAcc.isManagement() == true) ||
            (isParameterValid && dataPmsAcc.isPm())) {
            importTempBtn.setVisible(true);
            //���BaseLineͨ���Ժ�Import��ť�Ͳ��ɼ�
            InputInfo inputInfo = new InputInfo();
            inputInfo.setActionId(this.actionBaseLine);
            inputInfo.setInputObj("acntRid", dataPmsAcc.getRid());
            ReturnInfo returnInfo = accessData(inputInfo);
            if (!returnInfo.isError()) {
                List ProveList = (List) returnInfo.getReturnObj("BaseLineProve");
                if (ProveList != null && ProveList.size() > 0) {
                    for (int i = 0; i < ProveList.size(); i++) {
                        DtoAcntBLApp App = (DtoAcntBLApp) ProveList.get(i);
                        if (App != null) {
                            if (App.getAppStatus() != null &&
                                App.getAppStatus().equals("Approved")) {
                                importTempBtn.setVisible(false);
                                break;
                            }
                        }
                    }
                }
            }
        } else {
            importTempBtn.setVisible(false);
        }
    }

    private void setEnableMode() {
        if (isParameterValid == true && dataPmsAcc.isManagement()) {
            VWUtil.setUIEnabled(this, true);
        } else {
            VWUtil.setUIEnabled(this, false);
        }
        //�����osp,��ɱ༭account code��account name
        if(dataPmsAcc.isOSP()){
            txtAcntId.setEnabled(true);
            txtAcntName.setEnabled(true);
        }else{
            txtAcntId.setEnabled(false);
            txtAcntName.setEnabled(false);
        }
        //importTempBtn.setEnabled(true);
        //modifyTempBtn.setEnabled(true);
    }

    public void saveWorkArea() {
        if (isNeedSave == false) {
            return;
        }

        DtoPmsAcnt dtoBak = (DtoPmsAcnt) DtoUtil.deepClone(dataPmsAcc);
        if (checkModified()) {
            isSaveOk = false;
            if (confirmSaveWorkArea() == true) {
                if (validateData() == true) {
                    isSaveOk = saveData();

                    if (isSaveOk == false) {
                        DtoUtil.copyBeanToBean(dataPmsAcc, dtoBak);
                    }
                } else {
                    DtoUtil.copyBeanToBean(dataPmsAcc, dtoBak);
                }
            } else {

                //�û�ѡ��"������",��ΪisSaveOk=true
                isSaveOk = true;
                isNeedSave = false;
                DtoUtil.copyBeanToBean(dataPmsAcc, dtoBak);
            }
        } else {
            isSaveOk = true;
        }
    }

    public boolean isSaveOk() {
        return this.isSaveOk;
    }

    private boolean checkModified() {
        VWUtil.convertUI2Dto(dataPmsAcc, this);

        return dataPmsAcc.isChanged();
    }

    public boolean validateData() {
        //�����/Account Manager/Account Currency/Account Type/Organization/Status
        if (!dataPmsAcc.isTemplate()) {
            if (!dataPmsAcc.isOSP()) {
                if (checkNecessary(this.txtAcntManager, "Account manager") == false
                    || checkNecessary(this.cmbAcntCurrency, "Account currency") == false
                    || checkNecessary(this.cmbAcntType, "Account type") == false
                    || checkNecessary(this.cmbAcntOrganization, "Organization") == false
                    || checkNecessary(this.cmbAcntStatus, "Account status") == false
                    ) {
                    return false;
                }
            } else { //�����osp��ֻ���״̬�������Ķ������
                if (checkNecessary(this.cmbAcntStatus, "Account status") == false) {
                    return false;
                } else {
                    return true;
                }

            }
        } else { //�����ģ��Ļ���ֻ������ͺ�״̬�������Ķ������
            if (checkNecessary(this.cmbAcntType, "Account type") == false
                || checkNecessary(this.cmbAcntStatus, "Account status") == false
                ) {
                return false;
            } else {
                return true;
            }

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
            } else {
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
            } else {
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
            } else {
                txtAcntActualStart.setErrorField(false);
                txtAcntActualFinish.setErrorField(false);
            }
        }

        return true;
    }


    protected boolean checkNecessary(IVWComponent comp, String msg) {
        Object value = comp.getUICValue();
        if (value == null || StringUtil.nvl(value).equals("") == true) {
            comMSG.dispErrorDialog(" Must input " + msg + " .");
            comp.setErrorField(true);
            comFORM.setFocus((JComponent) comp);
            return false;
        } else {
            comp.setErrorField(false);
            return true;
        }
    }
    //���account code�Ƿ����
    protected boolean checkAcntIdExist(String value){
           InputInfo inputInfo = new InputInfo();
           inputInfo.setActionId(this.actioncheck);
           inputInfo.setInputObj("code", value);
           ReturnInfo returnInfo = accessData(inputInfo);
           if(returnInfo.isError()==false){
               String code = (String)returnInfo.getReturnObj("check");
               if(code.equals("true")){
                   comMSG.dispErrorDialog(String.valueOf(value) + " is exist, please input new code.");
                   txtAcntName.setErrorField(true);
                   comFORM.setFocus((JComponent) txtAcntName);
                   return true;
               }else{
                   return false;
               }
           }
         return true;
    }
    private boolean saveData() {
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.actionIdUpdate);

        inputInfo.setInputObj(DtoAcntKEY.DTO, this.dataPmsAcc);

        ReturnInfo returnInfo = accessData(inputInfo);
        if (returnInfo.isError() == false) {
            DtoPmsAcnt retPmsAcc = (DtoPmsAcnt) returnInfo.getReturnObj(
                DtoAcntKEY.DTO);

            DtoUtil.copyBeanToBean(dataPmsAcc, retPmsAcc);
            this.fireDataChanged();

            VWUtil.bindDto2UI(this.dataPmsAcc, this);
            comFORM.setFocus(this.txtAcntId);

            return true;
        } else {
            return false;
        }
    }

    //OSP ���Ϊ
    private boolean saveAsOSP() {
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.actionIdSaveAs);

        inputInfo.setInputObj(DtoAcntKEY.DTO, this.dataPmsAcc);

        ReturnInfo returnInfo = accessData(inputInfo);
        if (returnInfo.isError() == false) {
            DtoPmsAcnt retPmsAcc = (DtoPmsAcnt) returnInfo.getReturnObj(
                DtoAcntKEY.DTO);

            DtoUtil.copyBeanToBean(dataPmsAcc, retPmsAcc);
            this.fireDataChanged();

            VWUtil.bindDto2UI(this.dataPmsAcc, this);
            comFORM.setFocus(this.txtAcntId);

            return true;
        } else {
            return false;
        }
    }

    public void setParameter(DtoPmsAcnt dataPmsAcc) {
        super.setParameter(null);

        this.dataPmsAcc = dataPmsAcc;

        if (this.dataPmsAcc == null) {
            this.dataPmsAcc = new DtoPmsAcnt();
            this.isParameterValid = false;
        } else {
            isParameterValid = true;
            this.acntrid = this.dataPmsAcc.getRid();
        }

    }


    /*public void setParameter(Parameter param) {
        super.setParameter(param);

        this.dataPmsAcc = (DtoPmsAcnt)param.get(DtoAcntKEY.DTO);
        if( dataPmsAcc == null ){
            dataPmsAcc = new DtoPmsAcnt();
            this.isParameterValid = false;
        }else{
            isParameterValid = true;
        }

        dataPmsAcc = (DtoPmsAcnt)DtoUtil.deepClone(dataPmsAcc);
         }
     */

}
