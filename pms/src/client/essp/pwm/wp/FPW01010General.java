package client.essp.pwm.wp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;

import c2s.dto.DtoComboItem;
import c2s.dto.DtoUtil;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.pwm.wp.DtoPwWp;
import client.essp.common.util.TestPanelParam;
import client.framework.common.Global;
import client.framework.model.VMComboBox;
import client.framework.view.common.comFORM;
import client.framework.view.common.comMSG;
import client.framework.view.event.DataCreateListener;
import client.framework.view.vwcomp.VWUtil;
import com.wits.util.Parameter;
import com.wits.util.StringUtil;
import com.wits.util.comDate;
import org.xml.sax.SAXException;
import validator.Validator;
import validator.ValidatorResult;
import client.essp.common.view.VWWorkArea;
import c2s.dto.IDto;
import java.awt.event.*;
import c2s.dto.ITreeNode;
import c2s.essp.pms.wbs.DtoWbsActivity;

import java.util.Date;
import java.util.Calendar;
import c2s.essp.common.calendar.WorkCalendar;
import c2s.essp.common.calendar.WrokCalendarFactory;
import client.essp.tc.weeklyreport.GetCodeList;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: Wistron ITS Wuhan SDC</p>
 *
 * @author not attributable
 * @version 1.0
 */
public class FPW01010General extends FPW01010GeneralBase {
    protected final static String[] wpWorkerStatus = new String[] {"Assigned", "Finish",
                                             "Rejected"};
    protected final static String[] wpAssignerStatus = new String[] {
                                               "Assigned", "Closed", "Rework",
                                               "Cancel"
    };
    final static String defaultWpStatus = "Assigned";

    public final static String actionIdInit = "FPW01010GeneralInitAction";
    public final static String actionIdUpdate = "FPW01010GeneralAction";
    public final static String actionGetActivity="FPWGetActivityArea";
    public final static String actionGetCUTPCB="FPWGetCUTPCB";

    /*parameters*/
    protected DtoPwWp dataPwWp = new DtoPwWp();
    //private DtoPwWp dtoPwWp = new DtoPwWp();
    Long activityRid = null;
    Long codingUtRid = null;
    BigDecimal pcbDensity = null;
    BigDecimal pcbDefectRate = null;
    String pcbDesityUnit = null;
    String pcbSizeUnit = null;
    String pcbDefectRateUnit = null;

    /**
     * define control variable
     */
    private boolean bLockActivityIdSelected = false;
    private boolean bLockActivityNameSelected = false;

    protected boolean isSaveOk = true;
    private boolean isParameterValid = false;
    private boolean isNeedSave = true;
    private GetCodeList getCodeList = new GetCodeList();

    /**
     * define common data (globe)
     */
    private Validator validator;
    private List dataCreateLisenters = new ArrayList();
    VMComboBox vmWpWorkerStatus;
    VMComboBox vmWpAssignerStatus;
    GetActivityList getActivityList = new GetActivityList();
    JButton btnSave = null;
    String generatedWpCodeNum = null;

    /**
     * default constructor
     */
    public FPW01010General() {
        super();
        addUICEvent();
        initUI();

        try {
            /**
             * ����Validator������Ҫ������������һ��������validator��XML�ļ���
             * �ڶ��������Ǵ�����Ϣ�����ļ���
             * ��ע��:1)����������·��������classRootΪ��·����
             *         ��ͬ���ǵ�һ��������Ҫ��"/"��ʼ��
             *       2)�ڶ���������ĩβ��Ҫ����".properties"
             *
             */
            validator = new Validator("/client/essp/pwm/wp/validator.xml",
                                      "client/essp/pwm/wp/validator");

            /*
             * ��װvalidator
             */
            VWUtil.installValidator(this, validator, dataPwWp.getClass());

            /**
             * ��UI��ȡDTO�ĳ�ʼֵ
             */
            VWUtil.convertUI2Dto(dataPwWp, this, VWUtil.NOT_SET_FLAG);
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (SAXException ex) {
            ex.printStackTrace();
        }

    }

    public FPW01010General(Validator validator) {
        super();
        addUICEvent();
        initUI();

        this.validator = validator;
        VWUtil.installValidator(this, validator, dataPwWp.getClass());
        VWUtil.convertUI2Dto(dataPwWp, this, VWUtil.NOT_SET_FLAG);
    }

    private void addUICEvent() {

        //����button�¼���������
        btnSave = this.getButtonPanel().addSaveButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnSavActionPerformed(e);
            }
        });

        cmbActivityId.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    cmbActivityIdProcessDataChanged();
                }
            }
        });

        cmbActivityId.getEditor().getEditorComponent().addKeyListener(new
            KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    cmbActivityIdKeyPressed();
                }
            }
        });

        cmbClnitem.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    cmbClnitemProcessDataChanged();
                }
            }
        });

        rdoWpCmpltrateType1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                rdoWpCmpltrateType1ActionPerformed(e);
            }
        });
        rdoWpCmpltrateType2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                rdoWpCmpltrateType2ActionPerformed(e);
            }
        });
        numWpCmpltrate.addFocusListener(new FocusListener() {
            public void focusLost(FocusEvent e) {
                focusLostNumWpCmpltrate();
            }

            public void focusGained(FocusEvent e) {

            }
        });

        dteWpPlanStart.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    keyPressedDteWpPlanStart();
                }
            }
        });
        dteWpPlanFinish.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    keyPressedDteWpPlanFinish();
                }
            }
        });

        dteWpActStart.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    keyPressedDteWpActStart();
                }
            }
        });
        dteWpActFinish.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    keyPressedDteWpActFinish();
                }
            }
        });

        cmbWpStatus.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    cmbWpStatusProcessDataChanged(e);
                }
            }
        });

        numWpPlanWkhr.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                rdoWpCmpltrateType1ActionPerformed(null);
            }
        });

        dpnWpActWkhr.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                rdoWpCmpltrateType1ActionPerformed(null);
            }
        });

        cmbWpType.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                   cmbWpTypeProcessDataChanged();
                }
            }
        });


    }

    private void initUI() {
        this.cmbActivityId.setModel(new VMComboBox());
        this.cmbClnitem.setModel(new VMComboBox());

        FPW01010TypeAndUnit wpTypeAndUnit = new FPW01010TypeAndUnit();
        wpTypeAndUnit.setVWJComboBox(cmbWpType, cmbWpSizeUnit,
                                     cmbWpDensityrateUnit,
                                     cmbWpDefectrateUnit);
        vmWpAssignerStatus = VMComboBox.toVMComboBox(wpAssignerStatus);
        vmWpWorkerStatus = VMComboBox.toVMComboBox(wpWorkerStatus);

        setComboBoxWpStatus();

        setButtonVisible();
        setEnableMode();
    }
    public void getCodeList(Long lProjectId) {
        DtoComboItem nullElement = new DtoComboItem("", null);

        Vector vaCodeList = getCodeList.getCodeList(lProjectId);

        if (vaCodeList != null) {
            VMComboBox vmCodeList = new VMComboBox(vaCodeList);
            vmCodeList.insertElementAt(nullElement, 0);
            cmbWpType.setModel(vmCodeList);
        }
        cmbWpType.setUICValue(null);
    }


    private void getActivityList(Long acntRid) {
        Vector[] vaActivityList = getActivityList.getActivityList(acntRid.toString());

        if (vaActivityList != null) {
            VMComboBox vmActivityIdList = new VMComboBox(vaActivityList[0]);
            vmActivityIdList.addNullElement();
            cmbActivityId.setModel(vmActivityIdList);

            VMComboBox vmActivityNameList = new VMComboBox(vaActivityList[1]);
            vmActivityNameList.addNullElement();
            cmbClnitem.setModel(vmActivityNameList);
        }
    }

    private void setComboBoxWpStatus(){
        if( this.dataPwWp.isIsAssignBy() ){
            this.cmbWpStatus.setModel(vmWpAssignerStatus);
        }else{
            this.cmbWpStatus.setModel(vmWpWorkerStatus);
        }
    }

    /////// ��2�����ò�������ȡ�������
    /**
     * setParameter
     * @param param Parameter
     */
    public void setParameter(DtoPwWp dtoPmsAcc, Long activityRid) {
       super.setParameter(null);

       this.dataPwWp = dtoPmsAcc;
       if( dataPwWp == null ){
           this.dataPwWp = new DtoPwWp();
           this.isParameterValid = false;
       }else{
           isParameterValid = true;
       }
       this.activityRid = activityRid;
   }

    /////// ��3����ȡ���ݲ�ˢ�»���
    protected void resetUI() {
        //get account information
        InputInfo inputInfo = new InputInfo();
        inputInfo.setInputObj("isCreateWp", new Boolean(isCreateWp()));
        if( this.dataPwWp != null ){
            try {
                Long acntRid = new Long(dataPwWp.getAccountId());
                inputInfo.setInputObj("acntRid", acntRid);
            } catch (NumberFormatException ex) {
            }
        }
        inputInfo.setActionId(actionIdInit);
        ReturnInfo returnInfo = accessData(inputInfo);

        Long acntRid = null;
        String accountId = null;
        String accountName = null;
        String accountManagerName = null;
        String accountTypeName = null;
        Long maxWpCodeNum = null;

        if (returnInfo.isError() == false) {
            acntRid = (Long) returnInfo.getReturnObj("acntRid");
            accountId = (String) returnInfo.getReturnObj("accountId");
            accountName = (String) returnInfo.getReturnObj("accountName");
            accountManagerName = (String) returnInfo.getReturnObj("accountManagerName");
            accountTypeName = (String) returnInfo.getReturnObj("accountTypeName");
            maxWpCodeNum = (Long) returnInfo.getReturnObj("generatedWpCodeNum");

            if( maxWpCodeNum != null ){
                generatedWpCodeNum = maxWpCodeNum.toString();
                int len = generatedWpCodeNum.length();
                for (int i = len; i < DtoPwWp.CODE_NUMBER_LENGTH; i++) {
                    generatedWpCodeNum = "0" + generatedWpCodeNum;
                }
            }else{
                generatedWpCodeNum = null;
            }

            //���account�仯�˵Ļ�������ȡactivity,wpTypeCode
            if( this.cmbProjectId.getUICValue().equals(accountId) == false ){
                //get activity list
                getActivityList(acntRid);
                //get wp type code
                getCodeList(acntRid);
                //get cut pcb info
                getCUTPCB(acntRid);
            }
        } else {
            this.cmbActivityId.setModel(new VMComboBox());
            this.cmbClnitem.setModel(new VMComboBox());
        }

        if( isCreateWp() && isParameterValid == true){

            dataPwWp.setProjectId(acntRid);
            dataPwWp.setAccountId(accountId);
            dataPwWp.setAccountName(accountName);
            dataPwWp.setAccountManagerName(accountManagerName);
            dataPwWp.setAccountTypeName(accountTypeName);
            dataPwWp.setWpAssignby(Global.userId);
            dataPwWp.setWpAssignbyName(Global.userName);
            dataPwWp.setWpAssigndate(Global.todayDate);
            dataPwWp.setWpStatus(defaultWpStatus);
            dataPwWp.setWpWorker(Global.userId);
            dataPwWp.setWpWorkerName(Global.userName);
            dataPwWp.setWpCmpltrateType(DtoPwWp.COMPETE_RATE_TYPE_HOUR);
            dataPwWp.setIsAssignBy(true);
            dataPwWp.setWpTypeCode(codingUtRid);

            if( activityRid != null ){
                dataPwWp.setActivityId(activityRid);
            }
        }

        setComboBoxWpStatus();
        setButtonVisible();
        setEnableMode();

        //set hr allocate button
        this.dspWpWorker.setAcntRid(acntRid);
        this.dspWpWorker.setTitle("worker of work package");

        //����radio
        if (StringUtil.nvl(dataPwWp.getWpCmpltrateType()).equals(DtoPwWp.COMPETE_RATE_TYPE_HOUR) == true) {
            rdoWpCmpltrateType1.setSelected(true);
            rdoWpCmpltrateType2.setSelected(false);
        } else {
            rdoWpCmpltrateType1.setSelected(false);
            rdoWpCmpltrateType2.setSelected(true);
        }

        //��actvitiy�ر����ã�ʹ������ֻ����ʾ��û�����õĹ��ܣ����ı��������name
        //������������ֻ����Ŀ���������У�������Ҫ�ڸ��˹������������У�Ϊ�˼�˺��ߣ�������Ρ����õĽ���취��Ϊ����дһ���̳��ࡣ��
        if( this.cmbActivityId.isEnabled() == false ){
            this.cmbActivityId.setName("activityCode");
            this.cmbClnitem.setName("activityName");
        }else{
            this.cmbActivityId.setName("activityId");
            this.cmbClnitem.setName("activityId");
        }

        VWUtil.bindDto2UI(dataPwWp, this);

        //��actvitiy�ر����ã�ʹ������ֻ����ʾ��û�����õĹ��ܣ����ı��������name
        //������������ֻ����Ŀ���������У�������Ҫ�ڸ��˹������������У�Ϊ�˼�˺��ߣ�������Ρ����õĽ���취��Ϊ����дһ���̳��ࡣ��
        if( this.cmbActivityId.isEnabled() == false ){
            this.cmbActivityId.setUICValue(dataPwWp.getActivityCode());
            this.cmbClnitem.setUICValue(dataPwWp.getActivityName());
        }

        //��Ϊ��binddto2ui��ֵ�޷������������ǵ�˳�򣬶���typeʱ������������������
        //����type, size unit, defectrate unit, desityrate unit, status.
        cmbWpType.setUICValue(dataPwWp.getWpTypeCode());
        cmbWpSizeUnit.setUICValue(dataPwWp.getWpSizeUnit());
        cmbWpDensityrateUnit.setUICValue(dataPwWp.getWpDensityrateUnit());
        cmbWpDefectrateUnit.setUICValue(dataPwWp.getWpDefectrateUnit());
        cmbWpStatus.setUICValue(dataPwWp.getWpStatus());

        if( "Cancel".equals(dataPwWp.getWpStatus()) || "Closed".equals(dataPwWp.getWpStatus()) ){
            //dteWpActFinish.setUICValue(dataPwWp.getWpActFinish());
            numWpCmpltrate.setUICValue(new Integer(100));
        }else{
            //dteWpActFinish.setUICValue(null);
        }

        comFORM.setFocus(this.cmbActivityId);
    }

    private void getCUTPCB(Long acntRid) {
        InputInfo inputInfo = new InputInfo();
        inputInfo.setInputObj("acntRid", acntRid);
        inputInfo.setActionId(actionGetCUTPCB);
        ReturnInfo returnInfo = accessData(inputInfo);
        if(returnInfo.isError()) {
            pcbDensity = null;
            pcbDefectRate = null;
            pcbDesityUnit = null;
            pcbSizeUnit = null;
            pcbDefectRateUnit = null;
        } else {
            pcbDensity = (BigDecimal) returnInfo.getReturnObj("density");
            pcbDefectRate = (BigDecimal) returnInfo.getReturnObj("defectRate");
            pcbDesityUnit = (String) returnInfo.getReturnObj("desityUnit");
            pcbDefectRateUnit = (String) returnInfo.getReturnObj("defectRateUnit");
            pcbSizeUnit = (String) returnInfo.getReturnObj("sizeUnit");
            codingUtRid = (Long) returnInfo.getReturnObj("codingUtRid");
        }
    }

    private void setButtonVisible(){
        if ( isParameterValid == true) {
            btnSave.setVisible(true);
        }else{
            btnSave.setVisible(false);
        }
    }

    private void setEnableMode() {
        log.info("4 FPW01010General.setEnabledMode");
        if( isParameterValid == false ){
            VWUtil.setUIEnabled(this, false);
            rdoWpCmpltrateType1.setEnabled(false);
            rdoWpCmpltrateType2.setEnabled(false);
            return;
        }

        if (isCreateWp()) { //CreateWp
            VWUtil.setUIEnabled(this, true);

            if (this.activityRid != null) {
                cmbClnitem.setEnabled(false);
                cmbActivityId.setEnabled(false);
            } else {
                cmbClnitem.setEnabled(true);
                cmbActivityId.setEnabled(true);
            }

        } else { //ModifyWp

            log.info("5 modify work package");

            if (dataPwWp.isIsAssignBy()) { //Assigned User

                log.info("6 It's Assign by.Can modify almost all of the column.");

                VWUtil.setUIEnabled(this, true);

                //�������޸�activity��wp code
                cmbClnitem.setEnabled(false);
                cmbActivityId.setEnabled(false);
                txtWpCode.setEnabled(false);
            } else {

                log.info("6 It's not Assign by.Can modify 'complete rate' and 'status'.");

                //���������޸�WP,ֻ���޸���ɱ��ʣ�Complete Rate������Ŀ״̬(Status)
                VWUtil.setUIEnabled(this, false);
            }
        }

        //wp status �� wp complete rate���޸�
        cmbWpStatus.setEnabled(true);
        rdoWpCmpltrateType1.setEnabled(true);
        rdoWpCmpltrateType2.setEnabled(true);
        if (StringUtil.nvl(dataPwWp.getWpCmpltrateType()).equals(DtoPwWp.COMPETE_RATE_TYPE_HOUR) == true) {
            numWpCmpltrate.setEnabled(false);
        } else {
            numWpCmpltrate.setEnabled(true);
        }
        txaWpRequirement.setEnabled(true);
        dspManager.setEnabled(false);
    }

    public boolean isCreateWp() {
        boolean isCreateWp;
        //if (this.dataPwWp.getRid() == null) {
        if (this.dataPwWp.getRid() == null || this.dataPwWp.isInsert() == true) {
            isCreateWp = true;
        } else {
            isCreateWp = false;
        }

        return isCreateWp;
    }

    /////// ��4���¼�����
    private void cmbActivityIdProcessDataChanged() {
        if (
        this.cmbActivityId.isEnabled() == true&&
            this.bLockActivityIdSelected == false) {
            //���ж������ֵ�Ƿ��������б���
            VMComboBox vmComboBox = (VMComboBox) cmbActivityId.getModel();
            Object obj = cmbActivityId.getSelectedItem();
            Object selectItem = vmComboBox.findItemByName(obj);
            if (selectItem == null ||
                !(selectItem instanceof DtoComboItem)) {

                this.bLockActivityNameSelected = true;
                this.cmbClnitem.setUICValue(null);
                this.bLockActivityNameSelected = false;

                generatedWpCode();
                return;
            }

            Long lId = (Long) cmbActivityId.getUICValue();
            String activityName = getActivityList.getActivityNameById(lId);

            VMComboBox vmComboBoxCmbClnitem = (VMComboBox) cmbClnitem.getModel();
            Object objActivityName = vmComboBoxCmbClnitem.findItemByName(
                activityName);
            this.bLockActivityNameSelected = true;
            cmbClnitem.setSelectedItem(objActivityName);
            this.bLockActivityNameSelected = false;
//            cmbClnitem.setUICValue(activityName);

             generatedWpCode();
        }
    }

    private void cmbActivityIdKeyPressed() {
//        //�ж������ֵ�Ƿ��������б���
//        VMComboBox vmComboBox = (VMComboBox) cmbActivityId.getModel();
//        Object obj = ( (JTextComponent) (cmbActivityId.getEditor().getEditorComponent())).getText(); //���ڿ������ComboBox, ��ѡ���itemҪ��getselectitem�� ��finditembyname
//        Object selectItem = vmComboBox.findItemByName(obj);
//        if (selectItem == null ||
//            ! (selectItem instanceof DtoComboItem)) {
//            comMSG.dispErrorDialog("The inputed activity id is not existed.");
//            cmbActivityId.setErrorField(true);
//            cmbActivityId.requestFocus();
//            return;
//        }
    }


    private void cmbClnitemProcessDataChanged() {
        if (
        this.cmbActivityId.isEnabled() == true &&
            this.bLockActivityNameSelected == false) {
            Long lId = (Long) cmbClnitem.getUICValue();
            this.bLockActivityIdSelected = true;
            cmbActivityId.setUICValue(lId);
            this.bLockActivityIdSelected = false;

            generatedWpCode();
        }
    }

    private void generatedWpCode(){
        if( !isCreateWp() ){
            return;
        }

        Object selectedItem = cmbActivityId.getSelectedItem();
        if( selectedItem != null && selectedItem instanceof DtoComboItem ){
            String activityCode = ((DtoComboItem)selectedItem ).getItemName();
            Long activityRid = (Long)((DtoComboItem)selectedItem ).getItemValue();

            log.info("set activity activityCode --" +activityCode);
            if( activityRid != null ){
                String wpCode = this.getGeneratedWpCode(activityCode);
                this.txtWpCode.setUICValue(wpCode);
                this.dataPwWp.setWpCode(wpCode);
                log.info("set wp code --" +wpCode);
                //Ĭ��ʱ��ʾActivity�ļƻ���ֹʱ��
                Long acntRid = dataPwWp.getProjectId();
                InputInfo inputInfo = new InputInfo();
                inputInfo.setActionId(actionGetActivity);
                inputInfo.setInputObj("acntRid", acntRid);
                inputInfo.setInputObj("Activity", activityRid);
                ReturnInfo returnInfo = accessData(inputInfo);
                if (returnInfo.isError() == false) {
                    ITreeNode node = (ITreeNode) returnInfo.getReturnObj(
                        "WBSTREE");
                    Long activityCodeRid = (Long) returnInfo.getReturnObj("activityCodeRid");
                    DtoWbsActivity dtowbs = (DtoWbsActivity) node.getDataBean();
                    Date[] wpPlanDate = CalculateWPDefaultDate.calculateDefaultPlanDate(
                        dtowbs.getPlannedStart(), dtowbs.getPlannedFinish());

                    Calendar cStart = Calendar.getInstance();
                    Calendar cFinish = Calendar.getInstance();
                    cStart.setTime(wpPlanDate[0]);
                    cFinish.setTime(wpPlanDate[1]);
                    WorkCalendar wc = WrokCalendarFactory.clientCreate();
                    float reqWkHr = wc.getWorkHours(cStart, cFinish);
                    BigDecimal wpReqWkHr = new BigDecimal(reqWkHr);

                    this.dteWpPlanStart.setUICValue(wpPlanDate[0]);
                    this.dteWpPlanFinish.setUICValue(wpPlanDate[1]);
                    this.numWpReqWkhr.setUICValue(wpReqWkHr);
                    this.cmbWpType.setUICValue(activityCodeRid);
                    this.dataPwWp.setWpPlanStart(wpPlanDate[0]);
                    this.dataPwWp.setWpPlanFihish(wpPlanDate[1]);
                    this.dataPwWp.setWpReqWkhr(wpReqWkHr);
                    this.dataPwWp.setWpTypeCode(activityCodeRid);

                }

            } else {
                log.info("set wp code null");
                this.txtWpCode.setUICValue(null);
                this.dataPwWp.setWpCode(null);
            }

        }else{
            log.info("set wp code null");
            this.txtWpCode.setUICValue(null);
                this.dataPwWp.setWpCode(null);
        }
    }

    private void rdoWpCmpltrateType1ActionPerformed(ActionEvent e) {
        if (rdoWpCmpltrateType1.isSelected() ) {
            dataPwWp.setWpCmpltrateType(DtoPwWp.COMPETE_RATE_TYPE_HOUR);
            dataPwWp.setOp(IDto.OP_MODIFY);

            //��ѡBy Work Hours ʱ���깤���ʲ������룬
            //ֵ�ɹ�ʽ Complete Rate=Actual Work    Hours / Plan Work Hours�Զ�����;
            numWpCmpltrate.setEnabled(false);

            int completeRate = 0;

            BigDecimal wpPlanWkhr = (BigDecimal)this.numWpPlanWkhr.getUICValue();
            BigDecimal wpActWkhr = (BigDecimal)this.dpnWpActWkhr.getUICValue();
            BigDecimal b0 = new BigDecimal(0);
            BigDecimal b100 = new BigDecimal(100);
            if( wpPlanWkhr.compareTo(b0) == 1 ){
                completeRate = wpActWkhr.divide(wpPlanWkhr, BigDecimal.ROUND_HALF_UP).multiply(b100).intValue();
            }else{
                if( wpActWkhr.compareTo(b0) == 1 ){
                    completeRate = 100;
                }else{
                    completeRate = 0;
                }
            }

            if ( completeRate > 100 ){
                completeRate = 100;
            }

//            if ((dataPwWp.getWpPlanWkhr() != null)
//                && (dataPwWp.getWpPlanWkhr().intValue() != 0)) {
//                if (dataPwWp.getWpActWkhr() == null) {
//                    dataPwWp.setWpActWkhr(new BigDecimal(0));
//                }
//
//                completeRate = (int) (dataPwWp.getWpActWkhr().doubleValue() /
//                                      dataPwWp.getWpPlanWkhr()
//                                      .doubleValue());
//            } else {
//                if ((dataPwWp.getWpPlanWkhr() == null)
//                    || (dataPwWp.getWpPlanWkhr().intValue() == 0)) {
//                    completeRate = 0;
//                } else {
//                    completeRate = 100;
//                }
//            }

            dataPwWp.setWpCmpltrate(new Long(completeRate));
            numWpCmpltrate.setUICValue(new Integer(completeRate));
        }
    }

    private void rdoWpCmpltrateType2ActionPerformed(ActionEvent e) {
        if (this.rdoWpCmpltrateType2.isSelected()) {
            dataPwWp.setWpCmpltrateType(DtoPwWp.COMPETE_RATE_TYPE_MANUAL);
            dataPwWp.setOp(IDto.OP_MODIFY);

            //��ѡBy Manual Workʱ���깤���ʿ���
            this.numWpCmpltrate.setEnabled(true);
        }
    }

    private void focusLostNumWpCmpltrate() {
        rdoWpCmpltrateType1ActionPerformed(null);
    }

    private void keyPressedDteWpPlanStart() {
        String wpPlanStart = this.dteWpPlanStart.getValueText();

        if (wpPlanStart.equals("") == false) {
            if (comDate.checkDate(wpPlanStart) == false) {
                comMSG.dispErrorDialog("The planned start is not a date.");
                dteWpPlanStart.setErrorField(true);
                dteWpPlanStart.requestFocus();
            }
        }
    }

    private void keyPressedDteWpPlanFinish() {
        String wpPlanFinish = this.dteWpPlanFinish.getValueText();

        if (wpPlanFinish.equals("") == false) {
            if (comDate.checkDate(wpPlanFinish) == false) {
                comMSG.dispErrorDialog("The planned finish is not a date.");
                dteWpPlanFinish.setErrorField(true);
                dteWpPlanFinish.requestFocus();
            }
        }
    }

    private void keyPressedDteWpActStart() {
        String wpActStart = this.dteWpActStart.getValueText();

        if (wpActStart.equals("") == false) {
            if (comDate.checkDate(wpActStart) == false) {
                comMSG.dispErrorDialog("The actual start is not a date.");
                dteWpActStart.setErrorField(true);
                dteWpActStart.requestFocus();
            }
        }
    }

    private void keyPressedDteWpActFinish() {
        String wpActFinish = this.dteWpActFinish.getValueText();

        if (wpActFinish.equals("") == false) {
            if (comDate.checkDate(wpActFinish) == false) {
                comMSG.dispErrorDialog("The actual finish is not a date.");
                dteWpActFinish.setErrorField(true);
                dteWpActFinish.requestFocus();
            }
        }
    }
    public void btnSavActionPerformed(ActionEvent e) {
        isNeedSave = true;
        DtoPwWp dtoBak = (DtoPwWp)DtoUtil.deepClone(dataPwWp);

        if (checkModified()) {
            if (validateData() == true) {
                if( saveData() == false ){
                    DtoUtil.copyBeanToBean(dataPwWp, dtoBak);
                }
            }else{
                DtoUtil.copyBeanToBean(dataPwWp, dtoBak);
            }
        }
    }

    public void cmbWpStatusProcessDataChanged(ItemEvent e) {
        String status = (String) cmbWpStatus.getUICValue();
        if ("Closed".equals(status) == true
            || "Cancel".equals(status) == true) {

            numWpCmpltrate.setUICValue(new Integer(100));
            //dteWpActFinish.setUICValue(dataPwWp.getWpActFinish());
        }else{
            //dteWpActFinish.setUICValue(null);
        }
    }

    protected void cmbWpTypeProcessDataChanged() {
        if(codingUtRid == null) return;
        if(codingUtRid.equals(cmbWpType.getUICValue())) {
            ralWpDefectratePlan.setUICValue(pcbDefectRate);
            ralWpDefectratePlan.setEnabled(false);
            ralWpDensityratePlan.setUICValue(pcbDensity);
            ralWpDensityratePlan.setEnabled(false);
            cmbWpDensityrateUnit.setUICValue(pcbDesityUnit);
            cmbWpDensityrateUnit.setEnabled(false);
            cmbWpDefectrateUnit.setUICValue(pcbDefectRateUnit);
            cmbWpDefectrateUnit.setEnabled(false);
            cmbWpSizeUnit.setUICValue(pcbSizeUnit);
            cmbWpSizeUnit.setEnabled(false);
        } else {
//            ralWpDefectratePlan.setEnabled(true);
//            ralWpDensityratePlan.setEnabled(true);
//            cmbWpDensityrateUnit.setEnabled(true);
//            cmbWpDefectrateUnit.setEnabled(true);
//            cmbWpSizeUnit.setEnabled(true);
            setEnableMode();
        }
    }

    /////// ��5����������
    public void saveWorkArea() {
        if (isNeedSave == false) {
            return;
        }

        DtoPwWp dtoBak = (DtoPwWp)DtoUtil.deepClone(dataPwWp);

        if (checkModified()) {
            isSaveOk = false;
            if (confirmSaveWorkArea("Do you save the work package?") == true) {
                if (validateData() == true) {
                    isSaveOk = saveData();

                    if( isSaveOk == false ){
                        DtoUtil.copyBeanToBean(dataPwWp, dtoBak);
                    }
                }else{
                    DtoUtil.copyBeanToBean(dataPwWp, dtoBak);
                }
            }else{

                //�û�ѡ��"������",��ΪisSaveOk=true
                isSaveOk = true;
                isNeedSave = false;
                DtoUtil.copyBeanToBean(dataPwWp, dtoBak);
            }
        }else{
            isSaveOk = true;
        }
    }

    public void saveWorkAreaDirect() {
        DtoPwWp dtoBak = (DtoPwWp)DtoUtil.deepClone(dataPwWp);

        if (checkModified()) {
            isSaveOk = false;
            if (validateData() == true) {
                isSaveOk = saveData();

                if( isSaveOk == false ){
                    DtoUtil.copyBeanToBean(dataPwWp, dtoBak);
                }
            } else {
                DtoUtil.copyBeanToBean(dataPwWp, dtoBak);
            }
        } else {
            isSaveOk = true;
        }
    }

    public boolean isSaveOk(){
        return this.isSaveOk;
    }

    /**
     *
     * @return boolean ���Ķ���ʱ����true�����򷵻�false
     */
    protected boolean checkModified() {
        VWUtil.convertUI2Dto(dataPwWp, this);

        //ȡ�������activity��code��name��Ϣ
        VMComboBox vmComboBoxActivityCode = (VMComboBox) cmbActivityId.getModel();
        Object obj = cmbActivityId.getSelectedItem();
        Object selectItem = vmComboBoxActivityCode.findItemByName(obj);
        if (selectItem != null ){
            if(selectItem instanceof DtoComboItem){
                dataPwWp.setActivityCode(((DtoComboItem) selectItem).getItemName());
            }else{
                dataPwWp.setActivityCode(selectItem.toString());
            }
        }else{
            dataPwWp.setActivityCode(null);
        }

        Object selectItem2 = cmbClnitem.getSelectedItem();
        if (selectItem2 != null ){
            if(selectItem2 instanceof DtoComboItem){
                dataPwWp.setActivityName(((DtoComboItem) selectItem2).getItemName());
            }else{
                dataPwWp.setActivityName(selectItem2.toString());
            }
        }else{
            dataPwWp.setActivityName(null);
        }

        return dataPwWp.isChanged();
    }

    protected boolean validateData() {
        boolean bValid = true;
        ValidatorResult result = VWUtil.validate(this, dataPwWp, validator);
        bValid = result.isValid();

        if (bValid == false) {
            StringBuffer msg = new StringBuffer();

            for (int i = 0; i < result.getAllMsg().length; i++) {
                msg.append(result.getAllMsg()[i]);
                msg.append("\r\n");
            }

            comMSG.dispErrorDialog(msg.toString());
        } else {
            if( this.dataPwWp.getActivityId() == null ){
                comMSG.dispErrorDialog("The activity id is required.");

                return false;
            }

            String wpAssignBy = (String) dspWpAssignBy.getUICValue();
            if( "".equals(StringUtil.nvl(wpAssignBy)) ){
                comMSG.dispErrorDialog("The assign by is required.");
                return false;
            }

            //���ж���ļ��
            //planned start and finish
            String wpPlanStart = this.dteWpPlanStart.getValueText();
            if (wpPlanStart.equals("") == false) {
                if (comDate.checkDate(wpPlanStart) == false) {
                    comMSG.dispErrorDialog("The planned start is not a date.");
                    dteWpPlanStart.setErrorField(true);
                    dteWpPlanStart.requestFocus();

                    return false;
                }
            }

            String wpPlanFinish = this.dteWpPlanFinish.getValueText();
            if (wpPlanFinish.equals("") == false) {
                if (comDate.checkDate(wpPlanFinish) == false) {
                    comMSG.dispErrorDialog("The planned finish is not a date.");
                    dteWpPlanFinish.setErrorField(true);
                    dteWpPlanFinish.requestFocus();

                    return false;
                }
            }

            if (wpPlanStart.equals("") == false &&
                wpPlanFinish.equals("") == false) {
                if (comDate.compareDate(wpPlanStart, wpPlanFinish) > 0) {
                    dteWpPlanFinish.setErrorField(true);
                    dteWpPlanStart.setErrorField(true);
                    comMSG.dispErrorDialog(
                        "The plan start time cannot be bigger than the plan finish time.");
                    dteWpPlanStart.requestFocus();

                    return false;
                }
            }

            //actual start and finish
            String wpActStart = this.dteWpActStart.getValueText();
            if (wpActStart.equals("") == false) {
                if (comDate.checkDate(wpActStart) == false) {
                    comMSG.dispErrorDialog("The actual start is not a date.");
                    dteWpActStart.setErrorField(true);
                    dteWpActStart.requestFocus();

                    return false;
                }
            }

            String wpActFinish = this.dteWpActFinish.getValueText();
            if (wpActFinish.equals("") == false) {
                if (comDate.checkDate(wpActFinish) == false) {
                    comMSG.dispErrorDialog("The actual finish is not a date.");
                    dteWpActFinish.setErrorField(true);
                    dteWpActFinish.requestFocus();

                    return false;
                }
            }

            if (wpActStart.equals("") == false &&
                wpActFinish.equals("") == false) {
                if (comDate.compareDate(wpActStart, wpActFinish) > 0) {
                    dteWpActStart.setErrorField(true);
                    dteWpActFinish.setErrorField(true);
                    comMSG.dispErrorDialog(
                        "The actual start time cannot be bigger than the actual finish time.");
                    dteWpActStart.requestFocus();

                    return false;
                }
            }

            //��size�ĵ�λΪfpʱ������������С��
            if ("FP".equals(this.cmbWpSizeUnit.getUICValue()) == true) {
                double wpSize = this.ralWpSizePlan.getValue();
                if (Math.round(wpSize) < wpSize) {
                    comMSG.dispErrorDialog(
                        "The planned size is not an integer.");
                    cmbWpSizeUnit.requestFocus();

                    return false;
                }
            }
        }

        return bValid;
    }

    protected boolean saveData() {
        InputInfo inputInfo = new InputInfo();
        boolean isCreateWp = this.isCreateWp();
        if (isCreateWp) {
            inputInfo.setFunId("creatWp");
        } else {
            inputInfo.setFunId("modifyWp");
        }
        inputInfo.setInputObj("dto", dataPwWp);
        inputInfo.setActionId(actionIdUpdate);

        ReturnInfo returnInfo = accessData(inputInfo);

        if (returnInfo.isError() == false) {
            DtoPwWp retPwWp = (DtoPwWp) returnInfo.getReturnObj("dto");
            retPwWp.setOp(IDto.OP_NOCHANGE);

            DtoUtil.copyBeanToBean(dataPwWp, retPwWp);
            fireDataChanged();

            resetUI();
            /*
            setComboBoxWpStatus();
            VWUtil.bindDto2UI(dataPwWp, this);
            comFORM.setFocus(this.cmbActivityId);
            */
             if( isCreateWp ){
                this.fireDataCreate();
            }
            return true;
        }else{
            return false;
        }
    }

    public void addDataCreateListener(DataCreateListener lisenter) {
        this.dataCreateLisenters.add(lisenter);
    }

    //֪ͨ:wp������.
    public void fireDataCreate() {
        for (Iterator it = this.dataCreateLisenters.iterator(); it.hasNext(); ) {
            DataCreateListener listener = (DataCreateListener) it.next();
            listener.processDataCreate();
        }
    }

    public DtoPwWp getDto(){
        return this.dataPwWp;
    }


    public DtoPwWp getPwWp(Long wpRid){
        if( wpRid == null ){
            return null;
        }

        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(actionIdUpdate);
        inputInfo.setFunId("getWpInfo");
        inputInfo.setInputObj("inWpId", wpRid);

        ReturnInfo returnInfo = accessData(inputInfo);
        if(returnInfo.isError() == false ){
            return (DtoPwWp)returnInfo.getReturnObj("dto");
        }else{
            return null;
        }
    }

    String getGeneratedWpCode(String activityCode){
        if( generatedWpCodeNum != null ){
            return "WP-" + activityCode + "-" + this.generatedWpCodeNum;
        }else{
            return null;
        }
    }

    public static void main(String[] args) {
        FPW01010General vwPwWpGeneral = new FPW01010General();

        Parameter param = new Parameter();

        Global.userId = "stone.shi";
        Global.userName = "wenhui.ke";
        Global.todayDateStr = "20051018";
        Global.todayDatePattern = "yyyyMMdd";
        vwPwWpGeneral.setParameter(null, new Long(10060));
        vwPwWpGeneral.refreshWorkArea();

        VWWorkArea w = new VWWorkArea();
        w.addTab("wp",vwPwWpGeneral);
        TestPanelParam.show(w);
    }

}
