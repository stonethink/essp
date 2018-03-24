package client.essp.pms.account;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.pms.account.DtoAcntKEY;
import c2s.essp.pms.account.DtoPmsAcnt;
import client.essp.common.view.VWTableWorkArea;
import client.essp.common.view.VWWorkArea;
import client.framework.model.VMColumnConfig;
import client.framework.view.vwcomp.VWJCheckBox;
import client.framework.view.vwcomp.VWJText;
import com.wits.util.Parameter;
import com.wits.util.ProcessVariant;
import com.wits.util.TestPanel;
import java.util.ArrayList;
import client.framework.view.vwcomp.VWJTable;
import client.framework.view.vwcomp.VWUtil;
import javax.swing.JButton;
import client.essp.common.TableUitl.TableColumnChooseDisplay;
import client.framework.model.VMComboBox;
import client.framework.view.vwcomp.VWJComboBox;
import java.util.Vector;
import client.essp.pms.account.template.management.VwTemplateGeneral;
import client.essp.pms.account.template.management.VwTemplateCreateMethod;
import client.framework.view.vwcomp.VWJWizardEditor;
import client.essp.pms.account.template.management.VwManagementTemplatePreview;
import client.framework.view.vwcomp.VWJDate;
import javax.swing.table.TableColumnModel;
import javax.swing.table.JTableHeader;
import client.essp.pms.account.template.management.VwPcbAndTailorBase;
import client.essp.common.loginId.VWJLoginId;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class VwAccountList extends VWTableWorkArea {

    String entryFunType = DtoAcntKEY.PMS_ACCOUNT_FUN; //PmsAccountFun, EbsAccountFun,SepgAccountFun
    static final String actionIdList = "FAccountListAction";
    static final String actionIdChoose = "FAccountChooseAction";
    static final String COLUMN_CHOOSE = "selected";
    static final String PARTITION_STRING = "###$$$###";
    static final String DEFAULT_STATUS = "Approved";
    static final String DEFAULT_ACCOUNT_TYPE = null;
    static final String DEFAULT_TEMPLATE_SEPG = "Template";
    static final String DEFAULT_TEMPLATE_NOT_SEPG = "Account";

    /**
     * define common data (globe)
     */
    private List accountList;
    private int chooseRowIndex;
    List acntConfigs = new ArrayList();
    List ebsConfigs = new ArrayList();

    private VWJComboBox inputAccountType = new VWJComboBox();
    private VWJComboBox inputStatus = new VWJComboBox();
    private VWJComboBox inputTemplate = new VWJComboBox(); //选择查看模板,项目或所有的Account
    private String status = null; //AccountList查询条件
    private String accountType = null;
    private String template = null;
    private String ALlAccount = null;
    private String currentCondition = "Approved###$$$###null###$$$###Template";
    private JButton addTemplateBtn = null;
    private boolean canFresh = false;
    private boolean isSelectedOSP = false;
    /**
     * default constructor
     */
    public VwAccountList() {
        VMComboBox vmComboTemplate = new VMComboBox();
        vmComboTemplate.addElement(DtoAcntKEY.ONLY_OSP,
                                   DtoAcntKEY.ONLY_OSP);
        vmComboTemplate.addElement(DtoAcntKEY.ONLY_TEMPLATE,
                                   DtoAcntKEY.ONLY_TEMPLATE);
        vmComboTemplate.addElement(DtoAcntKEY.ONLY_ACCOUNT,
                                   DtoAcntKEY.ONLY_ACCOUNT);
        vmComboTemplate.addElement(DtoAcntKEY.ALL, DtoAcntKEY.ALL);
        inputTemplate.setVMComboBox(vmComboTemplate);

        VWJCheckBox checkBox = new VWJCheckBox();
        checkBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedChoose(e);
            }
        });
        Object[][] configs = new Object[][] { {"Code", "id",
                             VMColumnConfig.UNEDITABLE, new VWJText(),
                             Boolean.FALSE}, {"Name", "name",
                             VMColumnConfig.UNEDITABLE, new VWJText(),
                             Boolean.FALSE}, {"Manager", "manager",
                             VMColumnConfig.UNEDITABLE, new VWJLoginId(),
                             Boolean.FALSE}, {"Anticipated Start", "anticipatedStart",
                             VMColumnConfig.UNEDITABLE, new VWJDate(),
                             Boolean.TRUE}, {"Anticipated Finish", "anticipatedFinish",
                             VMColumnConfig.UNEDITABLE, new VWJDate(),
                             Boolean.TRUE},{"Planned Start", "plannedStart",
                             VMColumnConfig.UNEDITABLE, new VWJDate(),
                             Boolean.FALSE}, {"Planned Finish", "plannedFinish",
                             VMColumnConfig.UNEDITABLE, new VWJDate(),
                             Boolean.FALSE},{"Actual Start", "actualStart",
                             VMColumnConfig.UNEDITABLE, new VWJDate(),
                             Boolean.FALSE}, {"Actual Finish", "actualFinish",
                             VMColumnConfig.UNEDITABLE, new VWJDate(),
                             Boolean.FALSE},{"Type", "type",
                             VMColumnConfig.UNEDITABLE, new VWJText(),
                             Boolean.FALSE}, {"Status", "status",
                             VMColumnConfig.UNEDITABLE, new VWJText(),
                             Boolean.FALSE}, {"Current", COLUMN_CHOOSE,
                             VMColumnConfig.EDITABLE, checkBox, Boolean.FALSE}
        };

        for (int i = 0; i < configs.length; i++) {
            VMColumnConfig config = new VMColumnConfig(configs[i]);
            acntConfigs.add(config);
            ebsConfigs.add(config);
        }
        //在ebs中table不显示选择account的列
        ebsConfigs.remove(ebsConfigs.size() - 1);

        //table
        model = new VMAccountListModel(configs);
        model.setDtoType(DtoPmsAcnt.class);
        table = new VWJTable(model);

        this.add(table.getScrollPane(), null);
        //调整列的宽度
        JTableHeader header = this.getTable().getTableHeader();
        TableColumnModel tcModel = header.getColumnModel();
        tcModel.getColumn(0).setPreferredWidth(60);
        tcModel.getColumn(1).setPreferredWidth(80);
        tcModel.getColumn(2).setPreferredWidth(80);
        tcModel.getColumn(3).setPreferredWidth(95);
        tcModel.getColumn(4).setPreferredWidth(95);
        tcModel.getColumn(5).setPreferredWidth(95);
        tcModel.getColumn(6).setPreferredWidth(95);
        tcModel.getColumn(7).setPreferredWidth(60);
        tcModel.getColumn(8).setPreferredWidth(55);
        tcModel.getColumn(9).setPreferredWidth(60);


        addUICEvent();
    }

    private void addUICEvent() {
        this.getButtonPanel().add(inputStatus);
        this.getButtonPanel().add(inputAccountType);
        this.getButtonPanel().add(inputTemplate);
        inputStatus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedSelect();
            }
        });

        inputAccountType.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedSelect();
            }
        });

        inputTemplate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedSelect();
            }
        });

        addTemplateBtn = this.getButtonPanel().addAddButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedNewTemplate();
            }
        });
        addTemplateBtn.setToolTipText("new template");
        this.getButtonPanel().addLoadButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedLoad(e);
            }
        });

        TableColumnChooseDisplay chooseDisplay =
            new TableColumnChooseDisplay(this.getTable(), this);
        JButton btnDisplay = chooseDisplay.getDisplayButton();
        this.getButtonPanel().addButton(btnDisplay);
    }

    protected void actionPerformedNewTemplate() {
        Parameter para = new Parameter();

        //实例化第一张卡片
        VwTemplateGeneral vwTemplateGeneral = new VwTemplateGeneral();
        vwTemplateGeneral.setParameter(para);

        //实例化第二张卡片
        VwTemplateCreateMethod vwTemplateCreateMethod = new
            VwTemplateCreateMethod();
        vwTemplateCreateMethod.setParameter(para);

        //实例化第三张卡片
        VwManagementTemplatePreview vwTemplateAccount = new
            VwManagementTemplatePreview();
        vwTemplateAccount.setParameter(para);

        //实例化第四张卡片
        VwPcbAndTailorBase vwPcbAndTailor = new VwPcbAndTailorBase();
        vwPcbAndTailor.setParameter(para);

        //构造对象数组，第一个参数为Component,第二个参数为IVWWizardEditorEvent
        Object[][] obj = new Object[][] { {vwTemplateGeneral, vwTemplateGeneral},
                         {vwTemplateCreateMethod, vwTemplateCreateMethod},
                         {vwTemplateAccount, vwTemplateAccount},
                         {vwPcbAndTailor,vwPcbAndTailor}
        };

        VWJWizardEditor vWJWizardEditor =
            new VWJWizardEditor(this.getParentWindow(), "Create Template", obj);

        vWJWizardEditor.show();
        this.resetUI();

    }

    //刷新界面
    protected void actionPerformedLoad(ActionEvent e) {
        resetUI();
    }

    //选择过滤条件,重新查询
    protected void actionPerformedSelect() {
        if (canFresh && isSelectChanged()) {
            template = (String) inputTemplate.getUICValue();
            status = (String) inputStatus.getUICValue();
            if(DtoAcntKEY.ONLY_OSP.equalsIgnoreCase(template.trim())){
                inputAccountType.setVisible(false);
                currentCondition = status + PARTITION_STRING + "" +
                                   PARTITION_STRING + template;
            }else{
                inputAccountType.setVisible(true);
                accountType = (String) inputAccountType.getUICValue();

                currentCondition = status + PARTITION_STRING + accountType +
                                   PARTITION_STRING + template;
            }
            resetUI();
        }
    }

    //判断查询条件是否更改
    private boolean isSelectChanged() {
        String template = (String) inputTemplate.getUICValue();
        String status = (String) inputStatus.getUICValue();
        String type ="";
        if(!DtoAcntKEY.ONLY_OSP.equalsIgnoreCase(template.trim())){
             type = (String) inputAccountType.getUICValue();
             isSelectedOSP = false;
        }else{
            isSelectedOSP = true;
        }
        //如果选择了OSP，只须判断status是否改变了
        if (DtoAcntKEY.ONLY_OSP.equalsIgnoreCase(template.trim())) {
            return (this.status == null && status != null) ||
                (this.status != null && !this.status.equals(status)||
                (this.template == null && template != null) ||
                (this.template != null && !this.template.equals(template)));

        } else {
            return (this.status == null && status != null) ||
                (this.status != null && !this.status.equals(status)) ||
                (this.accountType == null && type != null) ||
                (this.accountType != null && !this.accountType.equals(type)) ||
                (this.template == null && template != null) ||
                (this.template != null && !this.template.equals(template));
        }
    }
    //点选项目
    protected void actionPerformedChoose(ActionEvent e) {
        VWJCheckBox choose = (VWJCheckBox) e.getSource();
        int selectedRow = this.getTable().getSelectedRow();
        if (selectedRow == -1) {
            return;
        }

        DtoPmsAcnt selectedAccount = (DtoPmsAcnt)this.accountList.get(
            selectedRow);
        if (selectedRow != chooseRowIndex) {
            chooseRowIndex = selectedRow;

            //取消其它的已选择account
            for (int i = 0; i < this.accountList.size(); i++) {
                DtoPmsAcnt account = (DtoPmsAcnt)this.accountList.get(i);
                if (account.isSelected() == true) {
                    account.setSelected(false);
                    this.getModel().fireTableRowsUpdated(i, i);
                    break;
                }
            }

            saveRowMark(selectedAccount);
        }

        choose.setUICValue(Boolean.TRUE);
    }

    private void saveRowMark(DtoPmsAcnt selectedAccount) {
        //set choose account in session
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(actionIdChoose);
        inputInfo.setInputObj(DtoAcntKEY.ACCOUNT_CHOOSED, selectedAccount);
        inputInfo.setInputObj(DtoAcntKEY.ACCOUNT_LIST_CONDITION,
                              currentCondition);
        inputInfo.setInputObj(DtoAcntKEY.ACCOUNT_ENTRY_FUN_TYPE,
                              entryFunType);

        ReturnInfo returnInfo = accessData(inputInfo);
        if (returnInfo.isError() == false) {
            ProcessVariant.set("account", selectedAccount.getRid());
            ProcessVariant.fireDataChange("account");
        }
    }

    public void setParameter(Parameter param) {
        super.setParameter(param);

        String oldEntryFunType = entryFunType;
        entryFunType = (String) param.get(DtoAcntKEY.ACCOUNT_ENTRY_FUN_TYPE);
        log.debug("entryFunType=" + entryFunType);

        if (entryFunType == null || entryFunType.length() == 0) {
            entryFunType = DtoAcntKEY.PMS_ACCOUNT_FUN;
        }

        System.out.println("setParameter :entryFunType" + entryFunType);
        //默认只显示项目,不显示模板
        template = DtoAcntKEY.ONLY_ACCOUNT;

        if (entryFunType.equals(oldEntryFunType) == false) {
            if (entryFunType.equals(DtoAcntKEY.EBS_ACCOUNT_FUN)) {
                this.getModel().setColumnConfigs(ebsConfigs);
            } else {
                this.getModel().setColumnConfigs(acntConfigs);
            }

            VWUtil.setTableRender(getTable());
            VWUtil.setTableEditor(getTable());
            getTable().setHeaderRender();
        }

        this.accountType = DEFAULT_ACCOUNT_TYPE;
        this.status = DEFAULT_STATUS;
        //只有SEPG才能选择模板
        if (DtoAcntKEY.SEPG_ACCOUNT_FUN.equals(entryFunType)) {
            inputTemplate.setVisible(true);
            addTemplateBtn.setVisible(true);
            this.ALlAccount = "AllAccount";
            this.template = DEFAULT_TEMPLATE_SEPG;
        } else {
            inputTemplate.setVisible(false);
            addTemplateBtn.setVisible(false);
            this.template = DEFAULT_TEMPLATE_NOT_SEPG;
        }
    }

    public void saveWorkArea() {
    }

    protected void resetUI() {
        InputInfo inputInfo = new InputInfo();

        inputInfo.setActionId(this.actionIdList);
        inputInfo.setFunId(entryFunType);
        inputInfo.setInputObj("accountType", accountType);
        inputInfo.setInputObj("status", status);
        inputInfo.setInputObj("template", template);
        inputInfo.setInputObj("AllAccount", ALlAccount);
        inputInfo.setInputObj(DtoAcntKEY.ACCOUNT_LIST_CONDITION,
                              currentCondition);
        inputInfo.setInputObj(DtoAcntKEY.ACCOUNT_ENTRY_FUN_TYPE, entryFunType);
//        inputInfo.setInputObj("entryFunType",entryFunType);

        ReturnInfo returnInfo = accessData(inputInfo);

        if (returnInfo.isError() == false) {
            accountList = (List) returnInfo.getReturnObj(DtoAcntKEY.
                ACCOUNT_LIST);
            Integer iChooseRowIndex = ((Integer) returnInfo.getReturnObj(
                DtoAcntKEY.
                ROW_INDEX_CHOOSED));

            getTable().setRows(accountList);

            if (iChooseRowIndex != null) {
                chooseRowIndex = iChooseRowIndex.intValue();
                getTable().setSelectRow(chooseRowIndex);
            }

            Vector accountTypeList = (Vector) returnInfo.getReturnObj(
                "accountTypeList");
            if (accountTypeList == null) {
                accountTypeList = new Vector();
            }
            VMComboBox vmComboType = new VMComboBox(accountTypeList);
            vmComboType.addNullElement("--please select type--", null);
            inputAccountType.setVMComboBox(vmComboType);

            Vector accountStatusList = (Vector) returnInfo.getReturnObj(
                "accountStatusList");
            if (accountStatusList == null) {
                accountStatusList = new Vector();
            }
            VMComboBox vmComboStatus = new VMComboBox(accountStatusList);
            vmComboStatus.addNullElement("--please select status--", null);
            inputStatus.setVMComboBox(vmComboStatus);

            //锁定不让刷新
            canFresh = false;
            inputStatus.setUICValue(status);
            inputAccountType.setUICValue(accountType);

            //设置完毕后刷新一次
            canFresh = true;
            inputTemplate.setUICValue(template);
            if (chooseRowIndex > -1) {
                saveRowMark((DtoPmsAcnt) accountList.get(chooseRowIndex));
            }
        }
    }
    public boolean isSelectedOSP(){
        return this.isSelectedOSP;
    }
    public static void main(String[] args) {
        VWWorkArea workArea = new VwAccountList();
        Parameter param = new Parameter();
        param.put("entryFunType", "PmsAccountFun");
        workArea.setParameter(param);

//        workArea.refreshWorkArea();
        TestPanel.show(workArea);
    }


}
