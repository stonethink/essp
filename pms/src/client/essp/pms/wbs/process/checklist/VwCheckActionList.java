package client.essp.pms.wbs.process.checklist;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;

import c2s.dto.IDto;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.common.code.DtoKey;
import c2s.essp.pms.account.DtoAcntKEY;
import c2s.essp.pms.account.DtoAcntLoaborRes;
import c2s.essp.pms.qa.DtoQaCheckAction;
import c2s.essp.pms.qa.DtoQaCheckPoint;
import c2s.essp.pms.wbs.DtoWbsActivity;
import client.essp.common.TableUitl.TableColumnChooseDisplay;
import client.essp.common.issue.VWJIssue;
import client.essp.common.view.VWTableWorkArea;
import client.framework.common.Constant;
import client.framework.model.VMColumnConfig;
import client.framework.model.VMComboBox;
import client.framework.view.common.comMSG;
import client.framework.view.event.RowSelectedListener;
import client.framework.view.vwcomp.VWJComboBox;
import client.framework.view.vwcomp.VWJDate;
import client.framework.view.vwcomp.VWJTable;
import client.framework.view.vwcomp.VWJText;
import com.wits.util.Parameter;
import com.wits.util.StringUtil;

public class VwCheckActionList extends VWTableWorkArea {
    public static final String actionIdList = "FWbsQaCheckActionListAction";
    public static final String actionIdDelete = "FWbsQaCheckActionDeleteAction";
    public static final String actionIdUpdate =
        "FWbsQaCheckActionListSaveAction";
    public static final String actionIdGetCode =
        "FWbsGetQaCheckActionCodeAction";

    public final static String[] chkActionOccasion = DtoQaCheckAction.chkActionOccasion;
    public final static String[] chkActionStatus = DtoQaCheckAction.chkActionStatus;

    //cotrol data
    boolean isSaveOk = true;

    /**
     * define common data
     */
    private List qaCheckActionList = new ArrayList();
    protected String[] qaLaborResNames;
    private DtoQaCheckPoint dtoQaCheckPoint;
    private DtoWbsActivity dtoWbsActivity;
    private String entryFunType;

    private JButton btnSave;
    private JButton btnDel;
    private JButton btnLoad;
    private JButton btnAdd;
    private VWJIssue issue;
    private VWJComboBox cmbPerformer;
    private VWJComboBox cmbOccasion;
    private VWJComboBox cmbStatus;

    public VwCheckActionList() {
        VWJDate date = new VWJDate();
        date.setCanSelect(true);
        issue = new VWJIssue();
        issue.setIssueType("NCR");
        cmbPerformer = new VWJComboBox();
        cmbStatus = new VWJComboBox();
        cmbStatus.setModel(VMComboBox.toVMComboBox(chkActionStatus));
        cmbOccasion = new VWJComboBox();
        cmbOccasion.setModel(VMComboBox.toVMComboBox(chkActionOccasion));

        Object[][] configs = new Object[][] { {"Occasion", "occasion",
                             VMColumnConfig.EDITABLE, cmbOccasion},
                             {"Plan Date", "planDate", VMColumnConfig.EDITABLE,
                             date}, {"Actual Date", "actDate",
                             VMColumnConfig.EDITABLE, date}, {"Performer",
                             "planPerformer", VMColumnConfig.EDITABLE,
                             cmbPerformer}, {"Content", "content",
                             VMColumnConfig.EDITABLE, new VWJText(),Boolean.TRUE},
                             {"Result", "result", VMColumnConfig.EDITABLE,
                             new VWJText(), Boolean.TRUE}, {"Status", "status",
                             VMColumnConfig.EDITABLE, cmbStatus}, {"NCR No.",
                             "nrcNo", VMColumnConfig.EDITABLE, issue}
        };
        try {
            model = new VMCheckActionModel(configs);
            model.setDtoType(DtoQaCheckAction.class);
            table = new VWJTable(model);
            this.add(table.getScrollPane(), null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        getTable().setSortable(true);
        addUICEvent();
    }

    private void addUICEvent() {
        //捕获事件－－－－
        btnAdd = this.getButtonPanel().addAddButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedAdd(e);
            }
        });

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
        //Display
        TableColumnChooseDisplay chooseDisplay =
            new TableColumnChooseDisplay(this.getTable(), this);
        JButton button = chooseDisplay.getDisplayButton();
        this.getButtonPanel().addButton(button);

        btnLoad = this.getButtonPanel().addLoadButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedLoad(e);
            }
        });
        this.getTable().addRowSelectedListener(new RowSelectedListener() {
            public void processRowSelected() {
                if (getTable().getSelectedData() == null) {
                    btnDel.setEnabled(false);
                } else {
                    btnDel.setEnabled(true);
                }
            }
        });

    }

    public void actionPerformedAdd(ActionEvent e) {
        DtoQaCheckAction dto = new DtoQaCheckAction();
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.actionIdGetCode);
        inputInfo.setInputObj(DtoQaCheckPoint.DTO_PMS_CHECK_POINT,
                              dtoQaCheckPoint);

        ReturnInfo returnInfo = accessData(inputInfo);

        Long newRid = null;
        if (returnInfo.isError() == false) {
            newRid = (Long) returnInfo.getReturnObj(DtoQaCheckAction.
                DTO_PMS_CHECK_ACTION_RID);
        }
        dto.setRid(newRid);
        dto.setOccasion(chkActionOccasion[0]);
        dto.setPlanDate(dtoWbsActivity.getPlannedStart());
        dto.setStatus(chkActionStatus[0]);
        dto.setCpRid(dtoQaCheckPoint.getRid());
        dto.setAcntRid(dtoQaCheckPoint.getAcntRid());
        getTable().addRow(dto);
    }

    public void actionPerformedDel(ActionEvent e) {
        int option = comMSG.dispConfirmDialog("Do you delete the data?");
        if (option == Constant.OK) {
            DtoQaCheckAction dto = (DtoQaCheckAction)this.getSelectedData();
            if (dto != null) {
                if (dto.isInsert() == false) {
                    InputInfo inputInfo = new InputInfo();
                    inputInfo.setInputObj(DtoQaCheckAction.DTO_PMS_CHECK_ACTION,
                                          dto);
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
    }

    public void actionPerformedSave(ActionEvent e) {
        if (checkModified()) {
            if (validateData() == true) {
                saveData();
            }
        }
    }

    private boolean checkModified() {
        for (Iterator it = this.qaCheckActionList.iterator();
                           it.hasNext(); ) {
            DtoQaCheckAction DtoQaCheckAction = (DtoQaCheckAction) it.next();

            if (DtoQaCheckAction.isChanged() == true) {
                return true;
            }
        }

        return false;
    }


    public void actionPerformedLoad(ActionEvent e) {
        resetUI();
    }

    private boolean validateData() {
        boolean bValid = true;
        StringBuffer msg = new StringBuffer("");

        for (int i = 0; i < this.getTable().getRowCount(); i++) {
            DtoQaCheckAction dto = (DtoQaCheckAction)this.getModel().getRow(i);
            if (StringUtil.nvl(dto.getOccasion()).equals("") == true) {
                msg.append("Row " + (i + 1) + " ： Must input occasion.\r\n");
                bValid = false;
            }
        }
        if (bValid == false) {
            comMSG.dispErrorDialog(msg.toString());
        }
        return bValid;
    }

    private void saveData() {
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.actionIdUpdate);

        inputInfo.setInputObj(DtoQaCheckAction.PMS_CHECK_ACTION_LIST,
                              qaCheckActionList);

        ReturnInfo returnInfo = accessData(inputInfo);
        if (returnInfo.isError() == false) {
            qaCheckActionList = (List) returnInfo.getReturnObj(DtoQaCheckAction.
                PMS_CHECK_ACTION_LIST);

            this.getTable().setRows(qaCheckActionList);
        }
    }

    //本workArea不需要外界的parameter
    public void setParameter(Parameter param) {
        super.setParameter(param);
        dtoWbsActivity = (DtoWbsActivity) param.get(DtoKey.DTO);
        entryFunType = (String) param.get("entryFunType");
        this.dtoQaCheckPoint = (DtoQaCheckPoint) (param.get(dtoQaCheckPoint.
            DTO_PMS_CHECK_POINT));
        ((VMCheckActionModel) model).setDtoWbsActivity(dtoWbsActivity);
        if (dtoQaCheckPoint != null && dtoQaCheckPoint.getAcntRid() != null) {
            issue.setAcntRid(dtoQaCheckPoint.getAcntRid());
            issue.setIssueType("NCR");
        }
    }

    //页面刷新－－－－－
    protected void resetUI() {
        if (this.dtoQaCheckPoint == null ||
            dtoQaCheckPoint.getOp().equals(IDto.OP_INSERT)) {
            setButtonVisible(false);
            this.btnLoad.setVisible(false);
            qaCheckActionList = new ArrayList();
            getTable().setRows(qaCheckActionList);
            return;
        }

        //无权限，或从PMS功能进来，只读
        setButtonVisible(!(dtoWbsActivity.isReadonly() ||
                         DtoAcntKEY.PMS_ACCOUNT_FUN.equals(entryFunType)));

        this.btnLoad.setVisible(true);
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.actionIdList);
        inputInfo.setInputObj(dtoQaCheckPoint.DTO_PMS_CHECK_POINT,
                              dtoQaCheckPoint);

        ReturnInfo returnInfo = accessData(inputInfo);

        if (returnInfo.isError() == false) {
            qaCheckActionList = (List) returnInfo.getReturnObj(DtoQaCheckAction.
                PMS_CHECK_ACTION_LIST);
            List qaLaborResList = (List) returnInfo.getReturnObj(
                DtoQaCheckAction.PMS_QA_LABORRES_LIST);
            int laborCount = qaLaborResList.size();
            String[] qaLaborResNames = new String[laborCount];
            String[] qaLaborResIds = new String[laborCount];
            for (int i = 0; i < laborCount; i++) {
                DtoAcntLoaborRes dtoLabor = (DtoAcntLoaborRes) (qaLaborResList.
                    get(i));
                qaLaborResNames[i] = dtoLabor.getEmpName();
                qaLaborResIds[i] = dtoLabor.getLoginId();
            }
            cmbPerformer.setModel(VMComboBox.toVMComboBox(qaLaborResNames,
                qaLaborResIds));
            //reference parent wbs/activity date
            for(int j = 0; j < qaCheckActionList.size(); j++) {
                DtoQaCheckAction dto = (DtoQaCheckAction) qaCheckActionList.get(j);
                Date planDate = VwCheckActionList.getQaCheckAtionPlanDate(
                    dto.getOccasion(), dtoWbsActivity);
                if(planDate != null) {
                    dto.setPlanDate(planDate);
                }
            }
            getTable().setRows(qaCheckActionList);
        }
    }

    private void setButtonVisible(boolean isVisible) {
        this.btnAdd.setVisible(isVisible);
        this.btnDel.setVisible(isVisible);
        this.btnSave.setVisible(isVisible);
        getTable().setEnabled(isVisible);
    }

    public static Date getQaCheckAtionPlanDate(String occasion,
                                               DtoWbsActivity pDto) {
        if(pDto == null) {
            return null;
        }

        if (chkActionOccasion[0].equals(occasion)) {
            return pDto.getPlannedStart();
        } else if (chkActionOccasion[1].equals(occasion)) {
            return pDto.getPlannedFinish();
        } else if (chkActionOccasion[2].equals(occasion)) {
            return pDto.getActualStart();
        } else if (chkActionOccasion[3].equals(occasion)) {
            return pDto.getActualFinish();
        } else {
            return null;
        }
    }

    public void saveWorkArea() {
        if (checkModified()) {
            isSaveOk = false;
            if (confirmSaveWorkArea("Do you save the check action?") == true) {
                if (validateData() == true) {
                    saveData();
                    this.isSaveOk = true;
                }
            } else {
                isSaveOk = true;
            }
        } else {
            isSaveOk = true;
        }
    }

    public boolean isSaveOk() {
        return this.isSaveOk;
    }
}
