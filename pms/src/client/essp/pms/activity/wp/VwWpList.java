package client.essp.pms.activity.wp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.pwm.wp.DtoPwWp;
import client.essp.common.TableUitl.TableColumnChooseDisplay;
import client.essp.common.view.VWAccountLabel;
import client.essp.common.view.VWTableWorkArea;
import client.essp.common.view.VWWorkArea;
import client.essp.pwm.wp.FPW01000PwWp;
import client.framework.common.Constant;
import client.framework.model.VMColumnConfig;
import client.framework.view.common.comMSG;
import client.framework.view.vwcomp.VWJDate;
import client.framework.view.vwcomp.VWJNumber;
import client.framework.view.vwcomp.VWJReal;
import client.framework.view.vwcomp.VWJText;
import com.wits.util.Parameter;
import com.wits.util.TestPanel;
import client.framework.view.vwcomp.VWJTable;
import client.framework.view.vwcomp.VWJComboBox;
import client.framework.model.VMComboBox;
import c2s.essp.pms.wbs.DtoWbsActivity;
import com.wits.util.Parameter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

import client.framework.view.event.RowSelectedListener;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import client.framework.model.VMTable2;
import c2s.dto.IDto;
import client.essp.common.loginId.VWJLoginId;
import client.essp.tc.weeklyreport.GetCodeList;
import java.util.Vector;
import c2s.dto.DtoComboItem;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.math.BigDecimal;
import client.essp.pwm.wp.FPW01010General;
import client.essp.pms.workerPop.VwAllocateWorker;


public class VwWpList extends VWTableWorkArea {
    private String actionIdList = "FPWWpListAction";
    private String actionIdUpdate = "FPWWpUpdateAction";
    private String actionIdDelete = "FPWWpDeleteAction";
    protected final static String[] wpWorkerStatus = new String[] {"Assigned",
        "Finish",
        "Rejected"};
    protected final static String[] wpAssignerStatus = new String[] {
        "Assigned", "Closed", "Rework",
        "Cancel"
    };


    /*parameter*/
    protected Long activityRid;
    private DtoWbsActivity activity = null;

    /**
     * define common data (globe)
     */
    protected List wpList = new ArrayList();
    protected List wpSelectList = new ArrayList();
    protected boolean hasNewWp = false;
    private boolean isSaveOk = true;
    protected boolean isParameterValid = true;
    private int selectedRowId = -1;
    boolean isReadonly = true;
    JButton btnfilter = null;
    JButton btnUpdate = null;
    JButton btnSave = null;
    JButton btnLoad = null;
    JButton btnDel = null;
    JButton btnAdd = null;
    JButton btnWorker = null;

    Long codingUtRid = null;
    BigDecimal pcbDensity = null;
    BigDecimal pcbDefectRate = null;
    String pcbDesityUnit = null;
    String pcbSizeUnit = null;
    String pcbDefectRateUnit = null;

    //private Long acntRid = null;
    VWAccountLabel lblAccountName = null;
    private GetCodeList getCodeList = new GetCodeList();
    VWJComboBox cmbWpType;
    protected VwAllocateWorker workers = null;
    private VWJText workerText = new VWJLoginId();
    FPW01000PwWp fpW01000PwWp;

    public VwWpList() {
        VWJText text = new VWJText();
        VWJNumber number = new VWJNumber();
        VWJDate date = new VWJDate();
        cmbWpType = new VWJComboBox();
        VWJComboBox wpStatus = new VWJComboBox();
        wpStatus.setModel(VMComboBox.toVMComboBox(wpAssignerStatus));
        date.setCanSelect(true);
        VWJReal real = new VWJReal();
        real.setMaxInputDecimalDigit(2);
        real.setMaxInputIntegerDigit(8);
        Object[][] configs = { {"WP Code", "wpCode", VMColumnConfig.EDITABLE,
                             text, Boolean.FALSE}
//                             , {"Type", "wpType", VMColumnConfig.EDITABLE,
//                             cmbWpType, Boolean.FALSE}
                             , {"WP Name", "wpName", VMColumnConfig.EDITABLE,
                             text, Boolean.FALSE}
                             , {"Activity Name", "activityName",
                             VMColumnConfig.UNEDITABLE, text, Boolean.FALSE}
                             , {"Planned Start", "wpPlanStart",
                             VMColumnConfig.EDITABLE, date, Boolean.FALSE}
                             , {"Planned Finish", "wpPlanFihish",
                             VMColumnConfig.EDITABLE, date, Boolean.FALSE}
                             , {"Required Work Hours", "wpReqWkhr",
                             VMColumnConfig.EDITABLE, real, Boolean.FALSE}
                             , {"Status", "wpStatus", VMColumnConfig.EDITABLE,
                             wpStatus}
                             , {"Worker", "wpWorker", VMColumnConfig.EDITABLE,
                             workerText, Boolean.FALSE}
                             , {"%Complete", "wpCmpltrate",
                             VMColumnConfig.EDITABLE, number, Boolean.TRUE}
                             , {"Activity Code", "activityCode",
                             VMColumnConfig.EDITABLE, text, Boolean.TRUE}
                             , {"Assign By", "wpAssignby",
                             VMColumnConfig.EDITABLE, new VWJLoginId(), Boolean.TRUE}
                             , {"Assign date", "wpAssigndate",
                             VMColumnConfig.EDITABLE, date, Boolean.TRUE}
                             , {"Planned Work Hours", "wpPlanWkhr",
                             VMColumnConfig.EDITABLE, real, Boolean.TRUE}
                             , {"Actual Work Hours", "wpActWkhr",
                             VMColumnConfig.EDITABLE, real, Boolean.TRUE}
                             , {"Actual Start", "wpActStart",
                             VMColumnConfig.EDITABLE, date, Boolean.TRUE}
                             , {"Actual Finish", "wpActFinish",
                             VMColumnConfig.EDITABLE, date, Boolean.TRUE}
                             , {"Planned Size", "wpSizePlanAndUnit",
                             VMColumnConfig.UNEDITABLE, text, Boolean.TRUE}
                             , {"Planned Density Rate",
                             "wpDensityratePlanAndUnit",
                             VMColumnConfig.UNEDITABLE, text, Boolean.TRUE}
                             , {"Planned Defect Rate",
                             "wpDefectratePlanAndUnit", VMColumnConfig.UNEDITABLE,
                             text, Boolean.TRUE}

        };

        try {
            model = new VMWpListModel(configs);
            model.setDtoType(DtoPwWp.class);
            table = new VWJTable(model);
            this.add(table.getScrollPane(), null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        getTable().setSortable(true);
        addUICEvent();
        //调整列的宽度
        JTableHeader header = this.getTable().getTableHeader();
        TableColumnModel tcModel = header.getColumnModel();
//        tcModel.getColumn(5).setPreferredWidth(40);
        tcModel.getColumn(6).setPreferredWidth(40);
        tcModel.getColumn(7).setPreferredWidth(120);
        setButtonVisible();
        //接受拖放事件
        (new WorkersDropTarget(getTable())).create();
    }

    private void addUICEvent() {
        lblAccountName = new VWAccountLabel();
        this.getButtonPanel().add(lblAccountName);

        //捕获事件－－－－
        btnAdd = this.getButtonPanel().addAddButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedAdd(e);
            }
        });

        btnUpdate = this.getButtonPanel().addEditButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedUpdate(e);
            }
        });
        btnUpdate.setToolTipText("edit/show detail data");

        btnDel = this.getButtonPanel().addDelButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedDel(e);
            }
        });
        btnSave = this.getButtonPanel().addSaveButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedSave(e);
            }
        });
        btnWorker = this.getButtonPanel().addButton("worker.png");
        btnWorker.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedWorker(e);
            }
        });
        btnWorker.setToolTipText("Workers");

        btnLoad = this.getButtonPanel().addLoadButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedLoad(e);
            }
        });
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {

                VMTable2 model = (VMTable2) table.getModel();
                int selectedIndex = table.getSelectedColumn();
                if(selectedIndex < 0) { return ; }
                String selectedColName = model.getColumnName(selectedIndex);
                if (table.getSelectedRow() == selectedRowId
                    && "Worker".equals(selectedColName)) {
                    btnWorker.doClick();
                    selectedRowId = -1;
                } else {
                    selectedRowId = table.getSelectedRow();
                }
//                if (e.getClickCount() == 2) {
//                    btnWorker.doClick();
//                }
            }
        });
        table.addRowSelectedListener(new RowSelectedListener() {
            public void processRowSelected() {
                if (table.getSelectedRowCount() > 0) {
                    DtoPwWp wp = (DtoPwWp) table.getSelectedData();
                    if (wp.getOp().equals(IDto.OP_INSERT)) {
                        btnUpdate.setEnabled(false);
                    } else {
                        btnUpdate.setEnabled(true);
                    }
                }
            }
        });

        cmbWpType.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                   cmbWpTypeProcessDataChanged();
                }
            }
        });

        JButton btnDisplay = new TableColumnChooseDisplay(this.getTable(), this).
                             getDisplayButton();
        this.getButtonPanel().addButton(btnDisplay);
    }

    protected void actionPerformedAdd(ActionEvent e) {
        DtoPwWp dtoPwWp = new DtoPwWp();
        this.getTable().addRow(dtoPwWp);
    }

    protected void actionPerformedDel(ActionEvent e) {
        DtoPwWp dto = (DtoPwWp)this.getSelectedData();
        if (dto == null) {
            return;
        }

        int option = comMSG.dispConfirmDialog("Do you delete the data?");
        if (option == Constant.OK) {
            if (dto.isInsert() == false) {
                InputInfo inputInfo = new InputInfo();
                inputInfo.setInputObj("dto", dto);
                inputInfo.setActionId(this.actionIdDelete);

                ReturnInfo returnInfo = accessData(inputInfo);
                if (returnInfo.isError() == false) {
                    getTable().deleteRow();
                }
            } else {
                getTable().deleteRow();
            }
        }
    }

    protected void actionPerformedUpdate(ActionEvent e) {
    }

    protected void actionPerformedWorker(ActionEvent e) {
    }

    protected void cmbWpTypeProcessDataChanged() {
        if(codingUtRid != null && codingUtRid.equals(cmbWpType.getUICValue())) {
            DtoPwWp dtoWp = (DtoPwWp)table.getSelectedData();
            dtoWp.setWpDefectratePlan(pcbDefectRate);
            dtoWp.setWpDensityratePlan(pcbDensity);
            dtoWp.setWpDefectrateUnit(pcbDefectRateUnit);
            dtoWp.setWpDensityrateUnit(pcbDesityUnit);
            dtoWp.setWpSizeUnit(pcbSizeUnit);
        }
    }


    private boolean checkModified() {
        if (wpList == null) {
            return false;
        }

        for (Iterator it = this.wpList.iterator();
                           it.hasNext(); ) {
            DtoPwWp dtoPwWp = (DtoPwWp) it.next();

            if (dtoPwWp.isChanged() == true) {
                return true;
            }
        }

        return false;
    }

    protected void actionPerformedLoad(ActionEvent e) {
        resetUI();
    }

    protected void actionPerformedSave(ActionEvent e) {
    }


    private boolean validateData() {
        return true;
    }

    private boolean saveData() {
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.actionIdUpdate);

        inputInfo.setInputObj("wpList", wpList);

        ReturnInfo returnInfo = accessData(inputInfo);
        if (returnInfo.isError() == false) {
            wpList = (List) returnInfo.getReturnObj("wpList");

            this.getTable().setRows(wpList);
            return true;
        } else {
            return false;
        }
    }

    public void setParameter(Parameter param) {
        super.setParameter(param);
        this.activityRid = (Long) (param.get("activityRid"));
        this.activity = (DtoWbsActivity) (param.get("dtoActivity"));
        Boolean readonly = (Boolean) param.get("isReadonly");
        if (readonly == null) {
            readonly = Boolean.FALSE;
        }
        this.isReadonly = readonly.booleanValue();
        this.isParameterValid = true;
    }

    //页面刷新－－－－－
    protected void resetUI() {
        setButtonVisible();
        if (this.isParameterValid == false) {
            wpList = new ArrayList();
            getTable().setRows(wpList);
            return;
        }

        InputInfo inputInfo = new InputInfo();

        inputInfo.setActionId(this.actionIdList);

        inputInfo.setInputObj("activityRid", this.getParameterActivityRid());
        inputInfo.setInputObj("Activity", this.activity);
        ReturnInfo returnInfo = accessData(inputInfo);

        if (returnInfo.isError() == false) {
            wpList = (List) returnInfo.getReturnObj("wpList");
            String acntCode = (String) returnInfo.getReturnObj("acntCode");
            String acntName = (String) returnInfo.getReturnObj("acntName");

            lblAccountName.setModel(lblAccountName.createtDefaultModel(acntCode,
                acntName));
            wpSelectList = wpList;
            getTable().setRows(wpList);
            getCodeList(activity.getAcntRid());
            getCUTPCB(activity.getAcntRid());
        }
    }

    protected void getCodeList(Long acntRid) {
        DtoComboItem nullElement = new DtoComboItem("", null);
        getCodeList.getCodeList(acntRid);
        Vector vaCodeList = getCodeList.getCodeList(acntRid);
        if (vaCodeList != null) {
            VMComboBox vmCodeList = new VMComboBox(vaCodeList);
            vmCodeList.insertElementAt(nullElement, 0);
            cmbWpType.setModel(vmCodeList);
        }
        cmbWpType.setUICValue(null);
    }

    private void getCUTPCB(Long acntRid) {
        InputInfo inputInfo = new InputInfo();
        inputInfo.setInputObj("acntRid", acntRid);
        inputInfo.setActionId(FPW01010General.actionGetCUTPCB);
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


    protected void setButtonVisible() {
        if (isParameterValid == true &&
            this.getIsReadOnly() == false) {
            this.btnAdd.setVisible(true);
            this.btnDel.setVisible(true);
            this.btnUpdate.setVisible(false);
        } else {
            this.btnAdd.setVisible(false);
            this.btnDel.setVisible(false);
            this.btnUpdate.setVisible(false);
        }
    }

    public void saveWorkArea() {
        if (checkModified()) {
            this.isSaveOk = false;
            if (confirmSaveWorkArea("Do you save the these wp?") == true) {
                if (validateData() == true) {
                    isSaveOk = saveData();
                }
            } else {
                isSaveOk = true;
            }
        } else {
            isSaveOk = true;
        }
    }

    public boolean onClickCancel(ActionEvent e) {
        return true;
    }

    public boolean isSaveOk() {
        return this.isSaveOk;
    }

//    public Long getAccountRid(){
//        return this.acntRid;
//    }

    public static void main(String args[]) {
        VWWorkArea w1 = new VWWorkArea();
        VWWorkArea workArea = new VwWpList();

        w1.addTab("Wp", workArea);
        TestPanel.show(w1);

        Parameter param = new Parameter();
        param.put("isReadonly", Boolean.FALSE);
        workArea.setParameter(param);
        workArea.refreshWorkArea();

    }

    //为使本workarea能在两个位置（）用，所以这里做一个补丁
    protected Long getParameterActivityRid() {
        return this.activityRid;
    }

    protected boolean getIsReadOnly() {
        return this.isReadonly;
    }
}
